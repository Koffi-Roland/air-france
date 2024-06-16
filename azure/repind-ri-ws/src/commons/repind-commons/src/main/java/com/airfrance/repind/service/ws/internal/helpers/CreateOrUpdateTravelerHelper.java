package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dto.individu.ContractDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleTravelersDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleTravelersDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("createTravelerHelperV7")
public class CreateOrUpdateTravelerHelper {

	/**
	 * DS for business role
	 */
	@Autowired
	protected BusinessRoleDS businessRoleDS;

	/**
	 * DS for role traveler
	 */
	@Autowired
	protected RoleTravelersDS roleTravelersDS;

	/**
	 * DS for individual
	 */
	@Autowired
	protected IndividuDS individuDS;
	
	// REPIND-555: Prospect migration
//	/**
//	 * DS for prospect
//	 */
//	@Autowired
//	protected IProspectDS prospectDS;
	
	/**
	 * This method allows to create or update a role traveler
	 * @param request
	 * @throws JrafDomainException
	 */
	public String createOrUpdateRoleTr(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException {
		String icle_role = "";
		
		// Check mandatory fields
		Map<String, String> datasTraveler = checkMandatoryFields(request);

		// Get signature
		SignatureDTO signatureDTO = new SignatureDTO();
		signatureDTO.setSignature(request.getRequestor().getSignature());
		signatureDTO.setSite(request.getRequestor().getSite());
		signatureDTO.setDate(Calendar.getInstance().getTime());

		// Insert role traveler in database
		icle_role = createOrUpdateRoleTravelerAndBusinessRole(request.getGin(), datasTraveler, signatureDTO);
		
		return icle_role;
	}

	/**
	 * Check mandatory fields for roleTraveler (contractData)
	 * @param request {@link CreateUpdateRoleContractRequest}
	 * @return contractDatas converted into a map
	 * @throws MissingParameterException
	 * @throws InvalidParameterException
	 */
	private Map<String, String> checkMandatoryFields(CreateUpdateRoleContractRequestDTO request) throws MissingParameterException, InvalidParameterException {
		if (request.getContractRequest().getContractData() == null || request.getContractRequest().getContractData().isEmpty()) {
			throw new MissingParameterException("ContractData is mandatory for travelers");
		}

		Map<String, String> datasTraveler = new HashMap<String, String>(2);
		for (ContractDataDTO contractData : request.getContractRequest().getContractData()) {
			if (datasTraveler.containsKey(contractData.getKey().toUpperCase())) {
				throw new InvalidParameterException("Key must be unique in contractData");
			} else if (!ContractDataKeyEnum.existKeyForType(ContractConstantValues.CONTRACT_TYPE_TRAVELER, contractData.getKey())){
				throw new InvalidParameterException(contractData.getKey() + " is not a valid key for traveler");
			} else {
				datasTraveler.put(contractData.getKey().toUpperCase(), contractData.getValue().toUpperCase());
			}
		}

		// Controler que le lastRecognitionDate est saisi a minima
		String lastRecognitionDate = datasTraveler.get(ContractDataKeyEnum.LASTRECOGNITIONDATE.getKey().toUpperCase());
		if (lastRecognitionDate == null || "".equals(lastRecognitionDate)) {
			throw new InvalidParameterException("LastRecognitionDate is mandatory for traveler");
		}
		
		return datasTraveler;
	}

	/**
	 * Create or update a business role for the gin
	 * @param gin
	 * @return ICLE_ROLE which is the primary key value of businessRole
	 * @throws JrafDomainException 
	 */
	private String createOrUpdateRoleTravelerAndBusinessRole(String gin, Map<String, String> datasTraveler, SignatureDTO signDTO) throws JrafDomainException {
		String icle_role = "";
		
		RoleTravelersDTO rtDto = roleTravelersDS.getRoleTravelersByGin(gin);

		BusinessRoleDTO brDTO = createBusinessRoleDTO(gin, datasTraveler);
		// Il n'y a pas de RoleTraveler
		if (rtDto == null) {

			// Est ce parcequ il n y a pas de BR ou d'Individu ?
			IndividuDTO id = individuDS.getByGin(gin);
			
			// REPIND-555 : Check if prospect
			if (id == null) {
				
				throw new NotFoundException("GIN " + gin + " not found as INDIVIDUS or PROSPECT");		
				
			} else {
				// REPIND-555 : We allow the creation of Traveler Role on a Prospect because Migration have been done
				// Est ce que le gars est un Prospect ?
				// if (id.getType().equals("W")) {
					// C'est un prospect
					// On leve une exception de non compatibilite
					// throw new RoleTravelerNotCompatibleWithProspectException("GIN " + gin);
				// }
				// else {
					// We Create a Traveler Role
					addSignatureToBusinessRoleDTO(signDTO, brDTO);
					addSignatureToRoleTravelersDTO(brDTO, brDTO.getRoleTravelers());
					icle_role = businessRoleDS.createABusinessRole(brDTO);
				// }
			}
		} else {
			brDTO.setCleRole(rtDto.getCleRole());
			brDTO.getRoleTravelers().setCleRole(brDTO.getCleRole());
			addSignatureToBusinessRoleDTO(signDTO, brDTO);
			addSignatureToRoleTravelersDTO(brDTO, brDTO.getRoleTravelers());
			icle_role = businessRoleDS.updateATravelerBusinessRole(brDTO);
		}
		return icle_role;
	}

	/**
	 * Create a business role
	 * @param gin {@link String}
	 * @param mapDatas {@link Map}<{@link String}, {@link String}>
	 * @return {@link BusinessRoleDTO}
	 * @throws InvalidParameterException
	 */
	private BusinessRoleDTO createBusinessRoleDTO(String gin, Map<String, String> mapDatas) throws InvalidParameterException {
		// Create Business role
		BusinessRoleDTO brDTO = new BusinessRoleDTO();
		brDTO.setGinInd(gin);
		brDTO.setType(ProcessEnum.T.toString());
		// Role Traveler creation
		RoleTravelersDTO rtDTO = new RoleTravelersDTO();
		rtDTO.setCleRole(brDTO.getCleRole());
		rtDTO.setGin(brDTO.getGinInd());
		Date lastRecognitionDate = formatToDate(mapDatas.get(ContractDataKeyEnum.LASTRECOGNITIONDATE.getKey().toUpperCase()));
		rtDTO.setLastRecognitionDate(lastRecognitionDate);
		String matchingRecognitionCode = mapDatas.get(ContractDataKeyEnum.MATCHINGRECOGNITION.getKey().toUpperCase());
		if (matchingRecognitionCode == null || "".equals(matchingRecognitionCode)) {
			matchingRecognitionCode = RecognitionType.DEF.toString();
		}
		rtDTO.setMatchingRecognitionCode(matchingRecognitionCode);
		brDTO.setRoleTravelers(rtDTO);
		return brDTO;
	}

	/**
	 * Format the string into a Date
	 * @param dateToFormat
	 * @return {@link Date}
	 * @throws InvalidParameterException
	 */
	private Date formatToDate(String dateToFormat) throws InvalidParameterException {
		if (dateToFormat == null || "".equals(dateToFormat)) {
			throw new InvalidParameterException("Date can not be null. Use pattern dd/MM/yyyy"); 
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(dateToFormat);
		} catch (ParseException pe) {
			throw new InvalidParameterException("Date must use the pattern dd/MM/yyyy");
		}
	}

	/**
	 * Add the signature to the {@link BusinessRoleDTO}
	 * @param signDTO {@link SignatureDTO}
	 * @param dto {@link BusinessRoleDTO}
	 */
	private void addSignatureToBusinessRoleDTO(SignatureDTO signDTO, BusinessRoleDTO dto) {

		switch (dto.getCleRole() == null ? SignatureTypeEnum.CREATION : SignatureTypeEnum.MODIFICATION) {
		case CREATION:
			dto.setDateCreation(signDTO.getDate());
			dto.setSignatureCreation(signDTO.getSignature());
			dto.setSiteCreation(signDTO.getSite());
			dto.setDateModification(signDTO.getDate());
			dto.setSignatureModification(signDTO.getSignature());
			dto.setSiteModification(signDTO.getSite());
			break;
		case MODIFICATION:
			dto.setDateModification(signDTO.getDate());
			dto.setSignatureModification(signDTO.getSignature());
			dto.setSiteModification(signDTO.getSite());
			break;
		}
	}

	/**
	 * Add the signature to the {@link RoleTravelersDTO} from the {@link BusinessRoleDTO}
	 * @param dto {@link BusinessRoleDTO}
	 * @param rtDto {@link RoleTravelersDTO}
	 */
	private void addSignatureToRoleTravelersDTO(BusinessRoleDTO dto, RoleTravelersDTO rtDto) {
		rtDto.setDateCreation(dto.getDateCreation());
		rtDto.setSignatureCreation(dto.getSignatureCreation());
		rtDto.setSiteCreation(dto.getSiteCreation());
		rtDto.setDateModification(dto.getDateModification());
		rtDto.setSignatureModification(dto.getSignatureModification());
		rtDto.setSiteModification(dto.getSiteModification());
	}
	
}
