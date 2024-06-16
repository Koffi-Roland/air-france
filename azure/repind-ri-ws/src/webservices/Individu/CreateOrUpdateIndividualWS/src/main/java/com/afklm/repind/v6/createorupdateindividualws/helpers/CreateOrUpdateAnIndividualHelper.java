package com.afklm.repind.v6.createorupdateindividualws.helpers;

import com.afklm.repind.v6.createorupdateindividualws.transformers.IndividuRequestTransformV6;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResquestDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.EmailDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InfosIndividuDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.RequeteDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.util.NormalizedStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("createOrUpdateAnIndividualHelperV6")
public class CreateOrUpdateAnIndividualHelper {

	@Autowired
	private IndividuDS individuDS;
	
	@Autowired
	private MyAccountDS myAccountDS;
	
	private static String CONTEXT_FB_ENROLMENT = "FB_ENROLMENT";
	
	public void createIndividual(CreateModifyIndividualResquestDTO requestDTO) throws JrafApplicativeException, JrafDomainException {
		
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
		
		// Il s agit d un enrolment FB depuis le B2C
		// REPIND-3351: Desactiver le FB_ENROLMENT process
		// Il s'agit d'un upgrade  depuis le B2C
		if(infosIndividuDTO!=null && StringUtils.isNotEmpty(infosIndividuDTO.getNumeroClient()) && StringUtils.isNotEmpty(prospectGin)) {
			upgradeFromB2C(requestDTO, listComPref);
		}
		
	}
	
	public void flyingBlueEnrollment(String prospectGin, CreateModifyIndividualResquestDTO requestDTO, List<CommunicationPreferencesDTO> listComPref) throws JrafApplicativeException, JrafDomainException {
		
		Date today = new Date();
		
		// Create the individual
		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(prospectGin);
		individuDto = individuDS.get(individuDto);
		individuDto = IndividuRequestTransformV6.transformToIndividuDTO(individuDto, requestDTO.getIndividu());
		
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
			Integer nbInd = individuDS.countIndividualIdentification(email.getEmail());
			
			if (nbInd == 0) {
				// Search if a prospect exists for its email addresses
				// Get ComPref and delete the prospect
				prospectGin = myAccountDS.getProspectComPref(email.getEmail(), listComPref);
			}
		}
		
		return prospectGin;
		
	}
	
}
