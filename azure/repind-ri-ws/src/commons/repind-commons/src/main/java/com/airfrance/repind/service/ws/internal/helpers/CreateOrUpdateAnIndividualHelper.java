package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.*;
import com.airfrance.repind.dto.ws.CommunicationPreferencesRequestTransform;
import com.airfrance.repind.dto.ws.EmailRequestDTO;
import com.airfrance.repind.dto.ws.IndividualRequestTransform;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.NormalizedStringUtils;
import com.airfrance.repind.util.transformer.IndividuTransform;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreateOrUpdateAnIndividualHelper {

	private static final Log LOG = LogFactory.getLog(CreateOrUpdateAnIndividualHelper.class);

	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	private MyAccountDS myAccountDS;

	@Autowired
	private RoleDS roleDS;
	
	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;
	
	private static String CONTEXT_FB_ENROLMENT = "FB_ENROLMENT";
	
	@Deprecated
	public void createIndividual(CreateModifyIndividualResquestDTO requestDTO, CreateModifyIndividualResponseDTO responseDTO) throws JrafApplicativeException, JrafDomainException {
		
		RequeteDTO requeteDTO = requestDTO.getRequete();
		InfosIndividuDTO infosIndividuDTO = requestDTO.getIndividu();
		
		// Only for FB_ENROLMENT, the GIN is kept from the prospect
		// And a new Individual must be created before the call to the S08924
		if(requeteDTO==null) {
			return;
		}

		// Get communication preferences
		List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
		String prospectGin = getProspectGin(requestDTO.getEmail(), listComPref);
		communicationPreferencesHelper.crmOptinComPrefs(listComPref, responseDTO);
		
		// Il s agit d un enrolment FB depuis le B2C
		if (CONTEXT_FB_ENROLMENT.equalsIgnoreCase(requeteDTO.getContext()) && StringUtils.isNotEmpty(prospectGin)) {
			flyingBlueEnrollment(prospectGin, requestDTO, listComPref);
		}
		// Il s'agit d'un upgrade  depuis le B2C
		else if(infosIndividuDTO!=null && StringUtils.isNotEmpty(infosIndividuDTO.getNumeroClient()) && StringUtils.isNotEmpty(prospectGin)) {
			upgradeFromB2C(requestDTO, listComPref);
		}
		
	}
	@Transactional(rollbackFor = JrafDomainRollbackException.class)
	public void prepareIndividualCreation(CreateUpdateIndividualRequestDTO requestDTO, CreateModifyIndividualResponseDTO responseDTO) throws JrafDomainException {
		// Only for FB_ENROLMENT, the GIN is kept from the prospect
		if(requestDTO == null) {
			return;
		}
		
		if (requestDTO.getRequestorDTO() == null) {
			throw new MissingParameterException("Requestor is null");
		}
		
		List<CommunicationPreferencesDTO> listComPref = new ArrayList<CommunicationPreferencesDTO>();
		if (requestDTO.getCommunicationPreferencesRequestDTO() != null) {
			listComPref = CommunicationPreferencesRequestTransform.requestToDto(requestDTO.getCommunicationPreferencesRequestDTO());
		}
		String prospectGin = getProspectGin(requestDTO.getEmailRequestDTO(), listComPref);
		
		// FB enrollment from B2C
		// REPIND-3351: Desactiver le FB_ENROLMENT process
		// Upgrade from B2C
		 if (requestDTO.getIndividualRequestDTO() != null
				&& requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO() != null
				&& StringUtils.isNotEmpty(requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier()) 
				&& StringUtils.isNotEmpty(prospectGin)) {
			
			upgradeFromB2C(requestDTO, listComPref);
			
		}
		
		communicationPreferencesHelper.crmOptinComPrefs(listComPref, responseDTO);
	}
	
	private void upgradeFromB2C(CreateUpdateIndividualRequestDTO requestDTO, List<CommunicationPreferencesDTO> listComPref) throws JrafDomainException {
		Date today = new Date();
		
		SignatureDTO signature = new SignatureDTO();
		signature.setTypeSignature(SignatureTypeEnum.MODIFICATION.toString());
		signature.setSignature(requestDTO.getRequestorDTO().getSignature());
		signature.setSite(requestDTO.getRequestorDTO().getSite());
		signature.setDate(today);
		
		if (listComPref.size() > 0) {
			// true for isFlyingBlue
			myAccountDS.updateCommunicationPreferencesForNewIndividual(requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier(), listComPref, UpdateModeEnum.UPDATE.toString(), signature, true);
		}
	}

	@Transactional
	public void flyingBlueEnrollment(String prospectGin, CreateUpdateIndividualRequestDTO requestDTO, List<CommunicationPreferencesDTO> listComPref) throws JrafDomainException {
		Date today = new Date();
		
		// Create the individual
		IndividuDTO individuDto = new IndividuDTO();
		individuDto = individuDS.getByGin(prospectGin);
		
		individuDto = IndividualRequestTransform.transformIndividualRequestFromWsToIndividuDTO(individuDto, requestDTO.getIndividualRequestDTO());

		// check if lastname is normalizable
		if (!NormalizedStringUtils.isNormalizableString(individuDto.getNom())) {
			throw new InvalidParameterException("Invalid character in lastname");
		}
		
		// check if firstname is normalizable
		if (!NormalizedStringUtils.isNormalizableString(individuDto.getPrenom())) {
			throw new InvalidParameterException("Invalid character in firstname");
		}
		
		// Set Signature parameters
		individuDto.setSignatureModification(requestDTO.getRequestorDTO().getSignature());
		individuDto.setSiteModification(requestDTO.getRequestorDTO().getSite());
		individuDto.setDateModification(today);
		
		// Clean individual attibutes to avoid detached entity
		if (individuDto.getEmaildto() != null) {
			individuDto.getEmaildto().clear();
		}
		if (individuDto.getTelecoms() != null) {
			individuDto.getTelecoms().clear();
		}
		if (individuDto.getPostaladdressdto() != null) {
			individuDto.getPostaladdressdto().clear();
		}
		
		// Create
		individuDS.updateAProspectToIndividual(individuDto);
		
		// REPIND-555: Increase number of version
		// modify requestDTO to send to the 08924
//		int version = individuDto.getVersion()+3;
//		requestDTO.getIndividu().setVersion(String.valueOf(version));
//		requestDTO.getIndividu().setNumeroClient(prospectGin);
		requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().setIdentifier(prospectGin);
//		requestDTO.getSignature().setTypeSignature(SignatureTypeEnum.MODIFICATION.toString());
	
		SignatureDTO signature = new SignatureDTO();
		signature.setTypeSignature(SignatureTypeEnum.CREATION.toString());
		signature.setSignature(requestDTO.getRequestorDTO().getSignature());
		signature.setSite(requestDTO.getRequestorDTO().getSite());
		signature.setDate(today);
	
		// Add/Modify Preferences Communication
		if (listComPref.size() > 0) {
			// Call the update comm pref method
			// true for isFlyingBlue
			myAccountDS.updateCommunicationPreferencesForNewIndividual(individuDto.getSgin(), listComPref, UpdateModeEnum.REPLACE.toString(), signature, true);
		}
	}

	@Deprecated
	public void flyingBlueEnrollment(String prospectGin, CreateModifyIndividualResquestDTO requestDTO, List<CommunicationPreferencesDTO> listComPref) throws JrafApplicativeException, JrafDomainException {
		
		Date today = new Date();
		
		// Create the individual
		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(prospectGin);
		individuDto = individuDS.get(individuDto);
		
		
		individuDto = IndividuTransform.transformToIndividuDTO(individuDto, requestDTO.getIndividu());
		
		// Set Signature parameters
		individuDto.setSignatureModification(requestDTO.getSignature().getSignature());
		individuDto.setSiteModification(requestDTO.getSignature().getSite());
		individuDto.setDateModification(today);
		
		// check if lastname is normalizable
		if (!NormalizedStringUtils.isNormalizableString(individuDto.getNom())) {
    		throw new InvalidParameterException("Invalid character in lastname");
    	}
		
		// check if firstname is normalizable
    	if (!NormalizedStringUtils.isNormalizableString(individuDto.getPrenom())) {
    		throw new InvalidParameterException("Invalid character in firstname");
    	}
		
		// Create
		individuDto.setNomSC(NormalizedStringUtils.normalizeString(individuDto.getNom()));
		individuDto.setPrenomSC(NormalizedStringUtils.normalizeString(individuDto.getPrenom()));
		individuDS.updateAProspectToIndividual(individuDto);

		// REPIND-555: Increase number of version
		// modify requestDTO to send to the 08924
		int version = individuDto.getVersion()+3;
		requestDTO.getIndividu().setVersion(String.valueOf(version));
		requestDTO.getIndividu().setNumeroClient(prospectGin);
		requestDTO.getSignature().setTypeSignature(SignatureTypeEnum.MODIFICATION.toString());
	
		SignatureDTO signature = new SignatureDTO();
		signature.setTypeSignature(SignatureTypeEnum.CREATION.toString());
		signature.setSignature(requestDTO.getSignature().getSignature());
		signature.setSite(requestDTO.getSignature().getSite());
		signature.setDate(today);
	
		// Add/Modify Preferences Communication
		if (listComPref.size() > 0) {
			// Call the update comm pref method
			// true for isFlyingBlue
			myAccountDS.updateCommunicationPreferencesForNewIndividual(individuDto.getSgin(), listComPref, UpdateModeEnum.REPLACE.toString(), signature, true);
		}
	}
	
	@Deprecated
	public void upgradeFromB2C(CreateModifyIndividualResquestDTO requestDTO, List<CommunicationPreferencesDTO> listComPref) throws JrafDomainException {
		
		Date today = new Date();
		
		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(requestDTO.getIndividu().getNumeroClient());
		individuDto = individuDS.get(individuDto);
		
		int version = individuDto.getVersion()+1;

		SignatureDTO signature = new SignatureDTO();
		signature.setTypeSignature(SignatureTypeEnum.MODIFICATION.toString());
		signature.setSignature(requestDTO.getSignature().getSignature());
		signature.setSite(requestDTO.getSignature().getSite());
		signature.setDate(today);

		requestDTO.getIndividu().setVersion(String.valueOf(version));
		requestDTO.getSignature().setTypeSignature(SignatureTypeEnum.MODIFICATION.toString());
		
		if (listComPref.size() > 0) {
			// Call the update comm pref method
			// true for isFlyingBlue
			myAccountDS.updateCommunicationPreferencesForNewIndividual(individuDto.getSgin(), listComPref, UpdateModeEnum.UPDATE.toString(), signature, true);
		}
	}
	
	@Deprecated
	private String getProspectGin(Set<EmailDTO> emailDTOSet, List<CommunicationPreferencesDTO> listComPref) throws JrafDomainException {
		
		if(emailDTOSet==null) {
			return null;
		}
		
		// V3 for Prospect
		// Check the input context (FB_ENROLMENT or not)
		String prospectGin = "";
		
		// For only one email
		// if there is more than 1 email, we can't determine the last one, so don't search the prospect
		if (emailDTOSet.size() >= 1) {
			
			// If the list of email is greater than 1 (take only the first one) 
			EmailDTO email = (EmailDTO)(emailDTOSet.toArray())[0];
			
			// Search if email not shared with my account or fb individual
			// If the email is shared between others individuals we can't determine a uniq individual 
			List<String> listInd = individuDS.getAnIndividualIdentification(email.getEmail());
			
			if (listInd.size() == 0) {
				// Search if a prospect exists for its email addresses
				// Get ComPref and delete the prospect
				prospectGin = myAccountDS.getProspectComPref(email.getEmail(), listComPref);
			}
		}
		
		return prospectGin;
		
	}

	private String getProspectGin(List<EmailRequestDTO> emailRequestDTO, List<CommunicationPreferencesDTO> listComPref) throws JrafDomainException {
		if(emailRequestDTO == null) {
			return null;
		}
		
		// V3 for Prospect
		// Check the input context (FB_ENROLMENT or not)
		String prospectGin = "";
		
		// For only one email
		// if there is more than 1 email, we can't determine the last one, so don't search the prospect
		if (emailRequestDTO.size() >= 1) {
			
			// If the list of email is greater than 1 (take only the first one) 
			EmailRequestDTO emailRequest = emailRequestDTO.get(0);
			
			// Search if email not shared with my account or fb individual
			// If the email is shared between others individuals we can't determine a uniq individual 
			Integer nbInd = individuDS.countIndividualIdentification(emailRequest.getEmailDTO().getEmail());
			
			if (nbInd == 0) {
				// Search if a prospect exists for its email addresses
				// Get ComPref and delete the prospect
				prospectGin = myAccountDS.getProspectComPref(emailRequest.getEmailDTO().getEmail(), listComPref);
			}
		}
		
		return prospectGin;
	}

	public IndividuDTO filterUniqueProspectOrIndividual(List<IndividuDTO> foundIndividuals, String email, String cinReco, String ginReco) {
		IndividuDTO foundGin = null;

		if (CollectionUtils.isNotEmpty(foundIndividuals)) {
			if (foundIndividuals.size() == 1) {
				return foundIndividuals.get(0);
			}
			else {
				foundGin = filterGinReco(foundIndividuals, ginReco);
			}

			if (foundGin == null) {
				foundGin = filterCinReco(foundIndividuals, cinReco);
			}
		}

		return foundGin;
	}

	private IndividuDTO filterCinReco(List<IndividuDTO> foundIndividuals, String cinReco) {
		if (StringUtils.isBlank(cinReco)) {
			return null;
		}
		else {
			Optional<IndividuDTO> matchIndividuals = foundIndividuals
					.stream()
					.filter( ind -> { return roleDS.cinBelongsToGin(cinReco, ind.getSgin()); })
					.findFirst();
			return matchIndividuals.isPresent() ? matchIndividuals.get() : null;
		}
	}

	private IndividuDTO filterGinReco(@NotNull List<IndividuDTO> foundIndividuals, String ginReco) {
		if (StringUtils.isBlank(ginReco)) {
			return null;
		}
		else {
			Optional<IndividuDTO> matchIndividuals = foundIndividuals
					.stream()
					.filter( ind -> { return ginReco.equals(ind.getSgin()); })
					.findFirst();
			return matchIndividuals.isPresent() ? matchIndividuals.get() : null;
		}
	}

	public List<IndividuDTO> filterNamesAndBirthdate(List<IndividuDTO> foundIndividuals, String firstName, String lastName, Date birthDate) {
		if (CollectionUtils.isEmpty(foundIndividuals)) {
			return null;
		}
		else {
			return foundIndividuals
					.stream()
					.filter( ind -> {return (sameIndividual(ind, firstName, lastName, birthDate));})
					.collect(Collectors.toList());
		}
	}

	private boolean sameIndividual(IndividuDTO individuDTO, String firstName, String lastName, Date birthDate) {
		if (individuDTO == null) {
			return false;
		}

		if (StringUtils.isBlank(individuDTO.getNom()) && StringUtils.isBlank(individuDTO.getPrenom())) {
			return false;
		}

		if (StringUtils.isNotBlank(individuDTO.getNom()) && !individuDTO.getNom().equalsIgnoreCase(lastName)) {
			return false;
		}

		if (StringUtils.isNotBlank(individuDTO.getPrenom()) && !individuDTO.getPrenom().equalsIgnoreCase(firstName)) {
			return false;
		}

		return true;
	}
}
