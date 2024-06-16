package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.ContractConstantValues;
import com.airfrance.ref.type.ContractDataKeyEnum;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.ContractDataDTO;
import com.airfrance.repind.dto.individu.ContractV2DTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleUCCRDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleUCCRDS;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("createOrUpdateRoleUCCRHelper")
public class CreateOrUpdateRoleUCCRHelper {

	private Log LOGGER = LogFactory.getLog(CreateOrUpdateRoleUCCRHelper.class);

	@Autowired
	protected RoleUCCRDS roleUCCRDS;

	@Autowired
	protected BusinessRoleDS businessRoleDS;

	@Autowired
	protected IndividuDS individuDS;

	@Autowired
	protected AccountDataDS accountDataDS;

	public String createRoleUCCR(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException, JrafApplicativeException {

		String corporateEnvironmentID = checkMandatoryField(request);
		checkContractNumber(request);

		ContractV2DTO contract = request.getContractRequest().getContract();

		AccountDataDTO accountDataDTO = accountDataDS.getByGin(request.getGin());
		if(accountDataDTO == null) {
			throw new AccountNotFoundException("UCCR creation is forbidden for individual with no account data");
		}
		
		Long nbOfUCCRIDFound = 0L;
		try {
			nbOfUCCRIDFound = (Long) roleUCCRDS.countNbOfUCCRIDWByGINAndCEID(request.getGin(), corporateEnvironmentID);
		} catch (Exception e) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Unknown Exception : gin <"+  request.getGin() +">, CEID <" + corporateEnvironmentID + ">");
			LOGGER.error(e.getMessage());
			throw new InternalError("Unknown Exception : Contract not created");
		}
		if (nbOfUCCRIDFound.intValue() > 0) {
			throw new InvalidParameterException("corporate environment ID <" + corporateEnvironmentID + "> already use for GIN : <" + request.getGin() + ">");
		}
//		String seq = roleUCCRDS.getSequence();
//		String contractNumber = EncryptionUtils.processCheckDigit(seq);
		String contractNumber = request.getContractRequest().getContract().getContractNumber();
		while(contractNumber.length() != ContractConstantValues.CONTRACT_NUMBER_MAX_SIZE) {
			contractNumber = "0".concat(contractNumber);
		}
		contract.setContractNumber(contractNumber);
			LOGGER.info("CreateOrUpdateRoleUCCRHelper : createOrUpdateRoleUCC input UCCR_ID : " + contractNumber);

		SignatureDTO signatureDTO = new SignatureDTO();
		signatureDTO.setSignature(request.getRequestor().getSignature());
		signatureDTO.setSite(request.getRequestor().getSite());
		signatureDTO.setDate(Calendar.getInstance().getTime());
		String uccrID = createRoleUCCRAndBusinessRole(request.getGin(), contract, corporateEnvironmentID, signatureDTO);

		if(uccrID == null || uccrID.isEmpty()) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : UCCR not saved");
			throw new InternalError("UCCR not saved");
		}
		return uccrID;
	}
	

	private String createRoleUCCRAndBusinessRole(String sgin, ContractV2DTO contract, String corporateEnvironmentID, SignatureDTO signature) throws JrafDomainException {
		String contractNumber = contract.getContractNumber();
		String contractType = contract.getProductType();
		Date startDate = contract.getValidityStartDate();
		if(startDate == null) {
			startDate = signature.getDate();
		}
		Date endDate = contract.getValidityEndDate();
		// Creation mother class of business role
		BusinessRoleDTO businessRoleDTO = new BusinessRoleDTO();
		businessRoleDTO.setNumeroContrat(contractNumber);
		businessRoleDTO.setDateCreation(signature.getDate());
		businessRoleDTO.setSignatureCreation(signature.getSignature());
		businessRoleDTO.setSiteCreation(signature.getSite());
		businessRoleDTO.setDateModification(signature.getDate());
		businessRoleDTO.setSignatureModification(signature.getSignature());
		businessRoleDTO.setSiteModification(signature.getSite());
		businessRoleDTO.setGinInd(sgin);
		businessRoleDTO.setType("U");

		// Creation of Role Contrat
		RoleUCCRDTO roleUCCRDTO = new RoleUCCRDTO();
		roleUCCRDTO.setCleRole(businessRoleDTO.getCleRole());
		roleUCCRDTO.setDateCreation(signature.getDate());
		roleUCCRDTO.setDebutValidite(startDate);
		roleUCCRDTO.setFinValidite(endDate);
		roleUCCRDTO.setEtat(contract.getContractStatus());
		roleUCCRDTO.setGin(sgin);
		roleUCCRDTO.setUccrID(contractNumber);
		roleUCCRDTO.setSignatureCreation(signature.getSignature());
		roleUCCRDTO.setSiteCreation(signature.getSite());
		roleUCCRDTO.setSignatureModification(signature.getSignature());
		roleUCCRDTO.setSiteModification(signature.getSite());
		roleUCCRDTO.setDateModification(signature.getDate());
		roleUCCRDTO.setType(contractType);
		roleUCCRDTO.setCeID(corporateEnvironmentID);
		businessRoleDTO.setRoleUCCRDTO(roleUCCRDTO);

		businessRoleDS.createABusinessRole(businessRoleDTO);

		return roleUCCRDTO.getUccrID();
	}
	
	public String updateRoleUCCR(CreateUpdateRoleContractRequestDTO request)
			throws MissingParameterException, JrafDomainException, JrafApplicativeException, AccountNotFoundException {

		String corporateEnvironmentID = checkMandatoryField(request);
		ContractV2DTO contract = request.getContractRequest().getContract();
		String contractNumber = contract.getContractNumber();
		BusinessRoleDTO businessRoleDTO = null;
		
		try {
			businessRoleDTO = (BusinessRoleDTO) roleUCCRDS.getBusinessRoleByUCCRIDAndCEID(contractNumber, corporateEnvironmentID);
		} catch (NoResultException e) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Contract not found : contract number <"+ contractNumber +"> not found for CEID <" + corporateEnvironmentID + ">");
			throw new NoResultException("contract number "+ contractNumber +" not found for CEID " + corporateEnvironmentID);
		} catch (Exception e) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Contract not found : contract number <"+ contractNumber +"> not found for CEID <" + corporateEnvironmentID+ ">");
			throw new InternalError("Contract not updated");
		}
		
		if(businessRoleDTO == null){
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Contract not found : contract number <"+ contractNumber +"> not found for CEID <" + corporateEnvironmentID + ">");
			throw new NoResultException("contract number "+ contractNumber +" not found for CEID " + corporateEnvironmentID);
		}
		
		// If we doesn't found a contract we create it
		// New request from DOCT, if not found, no creation but error 212 [REPIND-529]
/*		if(businessRoleDTO.getRoleUCCRDTO() == null) {
			request.setActionCode("C");
			actionManager.processByActionCode(request);
		}
*/
		// Avoid update existing business role if GIN in input is different from GIN in database
		if (request.getGin() != null) {
			if (!request.getGin().equalsIgnoreCase(businessRoleDTO.getGinInd())) {
				LOGGER.error("UCCR_ID already used by another GIN : " + businessRoleDTO.getGinInd());
				throw new ContractExistException("UUCR_ID " + businessRoleDTO.getNumeroContrat() + " already used by GIN " + businessRoleDTO.getGinInd());
			}
		} else {
			request.setGin(businessRoleDTO.getGinInd());
		}
		
		businessRoleDTO.setDateModification(Calendar.getInstance().getTime());
		businessRoleDTO.setSignatureModification(request.getRequestor().getSignature());
		businessRoleDTO.setSiteModification(request.getRequestor().getSite());
		RoleUCCRDTO roleUCCRDTO = businessRoleDTO.getRoleUCCRDTO();
		if (contract.getValidityStartDate() != null) {
			roleUCCRDTO.setDebutValidite(contract.getValidityStartDate());
		}
		if (contract.getValidityEndDate() != null) {
			roleUCCRDTO.setFinValidite(contract.getValidityEndDate());
		}
		roleUCCRDTO.setEtat(contract.getContractStatus());
		roleUCCRDTO.setSignatureModification(request.getRequestor().getSignature());
		roleUCCRDTO.setSiteModification(request.getRequestor().getSite());
		roleUCCRDTO.setDateModification(Calendar.getInstance().getTime());
		roleUCCRDTO.setGin(request.getGin());
		
		// Commented due to a null pointer if no GIN set in request input
//		if(request.getGin() != null && !request.getGin().equals(businessRoleDTO.getGinInd())) {
//			//We check if the individual exist
//			IndividuDTO individuDTO = individuDS.getByGin(request.getGin());
//			// If individual is not found we return a NotFoundException
//			if(individuDTO == null) {
//				LOGGER.error("CreateOrUpdateRoleUCCRHelper : Individual not found, Gin = " + request.getGin());
//				throw new NotFoundException("Gin = " + request.getGin());
//			}
//			businessRoleDTO.setGinInd(request.getGin());
//		}
		
		businessRoleDS.updateARoleUCCRBusinessRole(businessRoleDTO);
		
		if(roleUCCRDTO.getUccrID() == null || roleUCCRDTO.getUccrID().isEmpty()) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : UCCR not updated");
			throw new InternalError("UCCR not updated");
		}
		return roleUCCRDTO.getUccrID();
	}
	
	public boolean deleteRoleUCCR(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException, JrafApplicativeException {

		String corporateEnvironmentID = checkMandatoryField(request);
		ContractV2DTO contract = request.getContractRequest().getContract();
		String contractNumber = contract.getContractNumber();

		try {
			BusinessRoleDTO businessRoleDTO = (BusinessRoleDTO) roleUCCRDS.getBusinessRoleByUCCRIDAndCEID(contractNumber, corporateEnvironmentID);
			businessRoleDS.deleteBusinessRole(businessRoleDTO);
		} catch (NoResultException e) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Contract not deleted : contract number <"+ contractNumber +"> not found for CEID <" + corporateEnvironmentID + ">");
			throw new NotFoundException("contract number "+ contractNumber +" not found for CEID " + corporateEnvironmentID, e);
		} catch (Exception e) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Contract not deleted : contract number <"+ contractNumber +"> not found for CEID <" + corporateEnvironmentID + ">");
			throw new InternalError("Contract not deleted");
		}
		return true;
	}
	
	private String checkMandatoryField(CreateUpdateRoleContractRequestDTO request)
			throws MissingParameterException, JrafDomainException, JrafApplicativeException {
		String corporateEnvironmentID = checkMandatoryFieldCEID(request);
		checkMandatoryFieldContractNumber(request);
		return corporateEnvironmentID;
	}
	
	private void checkMandatoryFieldContractNumber(CreateUpdateRoleContractRequestDTO request) throws MissingParameterException, InvalidParameterException, JrafApplicativeException {
		ContractV2DTO contract = request.getContractRequest().getContract();
		String contractNumber = contract.getContractNumber();
		if(contractNumber == null || contractNumber.isEmpty()) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Missing Parameter contractNumber is mandatory");
			throw new MissingParameterException("contractNumber is mandatory");
		}
		if(!SicStringUtils.isValidIdentifier(contractNumber)) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Invalid Parameter contractNumber wrong format");
			throw new InvalidParameterException("contractNumber must be a number with twelve digits");
		}
	}
	
	private String checkMandatoryFieldCEID(CreateUpdateRoleContractRequestDTO request)
			throws MissingParameterException, JrafDomainException {
		String corporateEnvironmentID = null;
		if(request.getContractRequest().getContractData() == null ) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Missing Parameter ContractData is mandatory");
			throw new MissingParameterException("ContractData is Mandatory");
		}
		//Vérify if contractNumber already exist
		for(ContractDataDTO contractDataLoop : request.getContractRequest().getContractData()) {
			if(contractDataLoop.getKey() != null && contractDataLoop.getValue() != null 
					&& ContractDataKeyEnum.CEID.getKey().equalsIgnoreCase(contractDataLoop.getKey().toUpperCase())) {
				corporateEnvironmentID = contractDataLoop.getValue();
				break;
			}
		}
		if(corporateEnvironmentID == null || corporateEnvironmentID.isEmpty()) {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Missing Parameter corporateEnvironmentID is mandatory");
			throw new MissingParameterException("corporateEnvironmentID is mandatory");
		}
		return corporateEnvironmentID;
	}

	private void checkContractNumber(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException {
		if (request != null && request.getContractRequest() != null && request.getContractRequest().getContract() != null) {
			if (request.getContractRequest().getContract().getContractNumber() != null && !"".equals(request.getContractRequest().getContract().getContractNumber())) {
				RoleUCCRDTO bo = new RoleUCCRDTO();
				bo.setUccrID(request.getContractRequest().getContract().getContractNumber());
				List<RoleUCCRDTO> uccrFromDB = roleUCCRDS.findByExample(bo);
				if (uccrFromDB != null && (uccrFromDB.size() > 0)) {
					LOGGER.error("CreateOrUpdateRoleUCCRHelper : UCCR identification number already exists");
					throw new ContractExistException("contract " + request.getContractRequest().getContract().getContractNumber() + " already exists");
				}
			} else {
				LOGGER.error("CreateOrUpdateRoleUCCRHelper : Missing Parameter contractNumber is mandatory");
				throw new MissingParameterException("contractNumber is mandatory");
			}
		} else {
			LOGGER.error("CreateOrUpdateRoleUCCRHelper : Missing Parameter contractNumber is mandatory");
			throw new MissingParameterException("contractNumber is mandatory");
		}
	}
}
