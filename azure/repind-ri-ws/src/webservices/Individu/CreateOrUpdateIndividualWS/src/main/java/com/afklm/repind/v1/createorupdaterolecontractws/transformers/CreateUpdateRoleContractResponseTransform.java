package com.afklm.repind.v1.createorupdaterolecontractws.transformers;

import com.afklm.soa.stubs.w001567.v1.data.CreateUpdateRoleContractResponse;
import com.afklm.soa.stubs.w001567.v1.response.CommunicationPreferenceResponse;
import com.afklm.soa.stubs.w001567.v1.response.WarningResponse;
import com.afklm.soa.stubs.w001567.v1.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w001567.v1.siccommontype.Signature;
import com.afklm.soa.stubs.w001567.v1.siccommontype.Warning;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.CommunicationPreferences;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.MarketLanguage;
import com.afklm.soa.stubs.w001567.v1.sicindividutype.Media;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.WarningDTO;
import com.airfrance.repind.dto.individu.WarningResponseDTO;
import com.airfrance.repind.dto.ws.CommunicationPreferencesResponseDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractResponseDTO;

import java.util.List;
import java.util.Set;

/**
 * Transformer
 * CreateUpdateRoleContractResponse BO --> DTO 
 *
 */
public final class CreateUpdateRoleContractResponseTransform {

	public static CreateUpdateRoleContractResponse dto2Bo(CreateUpdateRoleContractResponseDTO response) {
		CreateUpdateRoleContractResponse result = new CreateUpdateRoleContractResponse();
		result.setSuccess(response.getSuccess());
		result.setContractNumber(response.getContractNumber());
		result.setCommunicationPreferenceResponse(dto2boComPref(response.getCommunicationPreferenceResponse()));
		result.setWarningResponse(dto2boWarm(response.getWarningResponse()));
				
		return result;
	}
	
	private static CommunicationPreferenceResponse dto2boComPref(CommunicationPreferencesResponseDTO response) {
		if(response == null) {
			return null;
		}
		CommunicationPreferenceResponse result = new CommunicationPreferenceResponse();
		List<CommunicationPreferencesDTO> responseComPrefs = response.getCommunicationPreferencesDTO();
		List<CommunicationPreferences> resultComPrefs = result.getCommunicationpreferences();
		
		if(!UList.isNullOrEmpty(responseComPrefs)) {
			for(CommunicationPreferencesDTO comPrefDto : responseComPrefs) {
				resultComPrefs.add(dto2boDTO(comPrefDto));
			}
		}
		
		return result;
	}
	
	private static CommunicationPreferences dto2boDTO(CommunicationPreferencesDTO cpDto) {
		CommunicationPreferences result = new CommunicationPreferences();
		
		result.setDomain(cpDto.getDomain());
		result.setCommunicationGroupeType(cpDto.getComGroupType());
		result.setCommunicationType(cpDto.getComType());
		result.setOptIn(cpDto.getSubscribe());
		result.setDateOfConsent(cpDto.getDateOptin());
		result.setDateOfConsentPartner(cpDto.getDateOptinPartners());
		result.setDateOfEntry(cpDto.getDateOfEntry());
		result.setOptInPartner(cpDto.getOptinPartners());
		result.setSubscriptionChannel(cpDto.getChannel());
		
		Media media = new Media();
		media.setMedia1(cpDto.getMedia1());
		media.setMedia2(cpDto.getMedia2());
		media.setMedia3(cpDto.getMedia3());
		media.setMedia4(cpDto.getMedia4());
		media.setMedia5(cpDto.getMedia5());
		result.setMedia(media);
		
		List<MarketLanguage> resultMarketLanguages = result.getMarketLanguage();
		Set<MarketLanguageDTO> marketLanguagesDTO = cpDto.getMarketLanguageDTO();
		
		if(!UList.isNullOrEmpty(marketLanguagesDTO)) {
			for(MarketLanguageDTO mlDto : marketLanguagesDTO) {
				resultMarketLanguages.add(dto2boMarket(mlDto));
			}
		}
		
		Signature sigCreation = new Signature();
		sigCreation.setSignature(cpDto.getCreationSignature());
		sigCreation.setSignatureSite(cpDto.getCreationSite());
		sigCreation.setDate(cpDto.getCreationDate());
		sigCreation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		Signature sigModification = new Signature();
		sigModification.setSignature(cpDto.getModificationSignature());
		sigModification.setSignatureSite(cpDto.getModificationSite());
		sigModification.setDate(cpDto.getModificationDate());
		sigModification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		List<Signature> resultSignature = result.getSignature();
		resultSignature.add(sigCreation);
		resultSignature.add(sigModification);
		
		return result;
		
	}
	
	private static MarketLanguage dto2boMarket(MarketLanguageDTO mlDto) {
		MarketLanguage result = new MarketLanguage();
		result.setMarket(mlDto.getMarket());
		result.setLanguage(LanguageCodesEnum.fromValue(mlDto.getLanguage()));
		result.setOptIn(mlDto.getOptIn());
		result.setDateOfConsent(mlDto.getDateOfConsent());
		
		Media media = new Media();
		media.setMedia1(mlDto.getMedia1());
		media.setMedia2(mlDto.getMedia2());
		media.setMedia3(mlDto.getMedia3());
		media.setMedia4(mlDto.getMedia4());
		media.setMedia5(mlDto.getMedia5());
		result.setMedia(media);
		
		Signature sigCreation = new Signature();
		sigCreation.setSignature(mlDto.getCreationSignature());
		sigCreation.setSignatureSite(mlDto.getCreationSite());
		sigCreation.setDate(mlDto.getCreationDate());
		sigCreation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		Signature sigModification = new Signature();
		sigModification.setSignature(mlDto.getModificationSignature());
		sigModification.setSignatureSite(mlDto.getModificationSite());
		sigModification.setDate(mlDto.getModificationDate());
		sigModification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		List<Signature> resultSignature = result.getSignature();
		resultSignature.add(sigCreation);
		resultSignature.add(sigModification);
		
		return result;
	}
	
	private static WarningResponse dto2boWarm(WarningResponseDTO response) {
		if(response == null) {
			return null;
		}
		WarningResponse result = new WarningResponse();
		
		List<Warning> warnings = result.getWarning();
		List<WarningDTO> warningsDto = response.getWarning();
		if(!UList.isNullOrEmpty(warningsDto)) {
			for(WarningDTO wDto : warningsDto) {
				warnings.add(dto2Bo(wDto));
			}
		}
		
		return result;
	}
	
	private static Warning dto2Bo(WarningDTO warningDto) {
		Warning result = new Warning();
		result.setWarningLabel(warningDto.getWarningCode());
		result.setWarningDetail(warningDto.getWarningDetails());
		return result;
		
	}
}
