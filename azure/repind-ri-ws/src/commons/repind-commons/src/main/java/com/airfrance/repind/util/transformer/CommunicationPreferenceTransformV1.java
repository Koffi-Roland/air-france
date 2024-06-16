package com.airfrance.repind.util.transformer;

import com.airfrance.ref.util.UList;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.ws.CommunicationPreferencesResponseDTO;
import com.airfrance.repind.entity.individu.MarketLanguage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Transformer List of {@value com.airfrance.repind.entity.individu.CommunicationPreferences}
 * Communication preferences de DTO --> TO 
 *
 */
public final class CommunicationPreferenceTransformV1 {

	public static CommunicationPreferencesResponseDTO transformToCommunicationPreferenceResponse(
			List<com.airfrance.repind.entity.individu.CommunicationPreferences> listeComPrefDTO) {
		CommunicationPreferencesResponseDTO communicationsPreferenceResponse = new CommunicationPreferencesResponseDTO();
		if (!UList.isNullOrEmpty(listeComPrefDTO)) {
			List<CommunicationPreferencesDTO> communicationPreferencesList = new ArrayList<CommunicationPreferencesDTO>(); 
			for (com.airfrance.repind.entity.individu.CommunicationPreferences communicationPreferences : listeComPrefDTO) {
				communicationPreferencesList.add(bo2Dto(communicationPreferences));
			}
			
			communicationsPreferenceResponse.getCommunicationPreferencesDTO().addAll(communicationPreferencesList);
		}
		return communicationsPreferenceResponse;
	}

	/**
	 * * Transformer of
	 * {@value com.airfrance.repind.entity.individu.CommunicationPreferences}
	 * Communication preferences de DTO --> TO
	 * 
	 * @param comPrefDTO
	 * @return
	 */
	/*public static CommunicationPreferences dto2Bo(
			com.airfrance.repind.entity.individu.CommunicationPreferences comPrefDTO) {
		CommunicationPreferences communicationPreferenceBO = new CommunicationPreferences();
		if (comPrefDTO != null) {
			communicationPreferenceBO.setDomain(comPrefDTO.getDomain());
			communicationPreferenceBO.setCommunicationGroupeType(comPrefDTO.getComGroupType());
			communicationPreferenceBO.setCommunicationType(comPrefDTO.getComType());
			communicationPreferenceBO.setOptIn(comPrefDTO.getSubscribe());
			communicationPreferenceBO.setDateOfConsent(comPrefDTO.getDateOptin());
			communicationPreferenceBO.setSubscriptionChannel(comPrefDTO.getChannel());
			communicationPreferenceBO.setOptInPartner(comPrefDTO.getOptinPartners());
			communicationPreferenceBO.setDateOfConsentPartner(comPrefDTO.getDateOptinPartners());
			communicationPreferenceBO.setDateOfEntry(comPrefDTO.getDateOfEntry());
			Media media = new Media();
			media.setMedia1(comPrefDTO.getMedia1());
			media.setMedia2(comPrefDTO.getMedia2());
			media.setMedia3(comPrefDTO.getMedia3());
			media.setMedia4(comPrefDTO.getMedia4());
			media.setMedia5(comPrefDTO.getMedia5());
			communicationPreferenceBO.setMedia(media);

			if (!UList.isNullOrEmpty(comPrefDTO.getMarketLanguage())) {
				for (com.airfrance.repind.entity.individu.MarketLanguage marketLanguage : comPrefDTO.getMarketLanguage()) {
					communicationPreferenceBO.getMarketLanguage().add(dto2Bo(marketLanguage));
				}
			}
			
			Signature signatureCreation = new Signature();
			signatureCreation.setDate(comPrefDTO.getCreationDate());
			signatureCreation.setSignature(comPrefDTO.getCreationSignature());
			signatureCreation.setSignatureSite(comPrefDTO.getCreationSite());
			signatureCreation.setSignatureType("C");
			communicationPreferenceBO.getSignature().add(signatureCreation);
		}
		return communicationPreferenceBO;
	}

	private static MarketLanguage dto2Bo(
			com.airfrance.repind.entity.individu.MarketLanguage marketLanguageDTO) {
		MarketLanguage marketLanguagetTO = new MarketLanguage();
		marketLanguagetTO.setDateOfConsent(marketLanguageDTO.getDateOfConsent());
		marketLanguagetTO.setLanguage(LanguageCodesEnum.fromValue(marketLanguageDTO.getLanguage()));
		marketLanguagetTO.setMarket(marketLanguageDTO.getMarket());
		Media media = new Media();
		media.setMedia1(marketLanguageDTO.getCommunicationMedia1());
		media.setMedia2(marketLanguageDTO.getCommunicationMedia2());
		media.setMedia3(marketLanguageDTO.getCommunicationMedia3());
		media.setMedia4(marketLanguageDTO.getCommunicationMedia4());
		media.setMedia5(marketLanguageDTO.getCommunicationMedia5());
		marketLanguagetTO.setMedia(media);
		marketLanguagetTO.setOptIn(marketLanguageDTO.getOptIn());
		return marketLanguagetTO;
	}*/

	private static MarketLanguageDTO bo2Dto(
			com.airfrance.repind.entity.individu.MarketLanguage marketLanguage) {
		MarketLanguageDTO result = new MarketLanguageDTO();
		
		result.setMarket(marketLanguage.getMarket());
		result.setLanguage(marketLanguage.getLanguage());
		result.setOptIn(marketLanguage.getOptIn());
		result.setCreationDate(marketLanguage.getCreationDate());
		result.setCreationSignature(marketLanguage.getCreationSignature());
		result.setCreationSite(marketLanguage.getCreationSite());
		result.setModificationDate(marketLanguage.getModificationDate());
		result.setModificationSignature(marketLanguage.getModificationSignature());
		result.setModificationSite(marketLanguage.getModificationSite());
		result.setDateOfConsent(marketLanguage.getDateOfConsent());
		result.setMedia1(marketLanguage.getCommunicationMedia1());
		result.setMedia2(marketLanguage.getCommunicationMedia2());
		result.setMedia3(marketLanguage.getCommunicationMedia3());
		result.setMedia4(marketLanguage.getCommunicationMedia4());
		result.setMedia5(marketLanguage.getCommunicationMedia5());
				
		return result;
	}
	
	private static CommunicationPreferencesDTO bo2Dto(com.airfrance.repind.entity.individu.CommunicationPreferences compref) {
		
		CommunicationPreferencesDTO communicationPreferenceDTO = new CommunicationPreferencesDTO();
		
		if (compref != null) {
			communicationPreferenceDTO.setDomain(compref.getDomain());
			communicationPreferenceDTO.setComGroupType(compref.getComGroupType());
			communicationPreferenceDTO.setComType(compref.getComType());
			communicationPreferenceDTO.setSubscribe(compref.getSubscribe());
			communicationPreferenceDTO.setDateOptin(compref.getDateOptin());
			communicationPreferenceDTO.setChannel(compref.getChannel());
			communicationPreferenceDTO.setOptinPartners(compref.getOptinPartners());
			communicationPreferenceDTO.setDateOptinPartners(compref.getDateOptinPartners());
			communicationPreferenceDTO.setDateOfEntry(compref.getDateOfEntry());
			communicationPreferenceDTO.setMedia1(compref.getMedia1());
			communicationPreferenceDTO.setMedia2(compref.getMedia2());
			communicationPreferenceDTO.setMedia3(compref.getMedia3());
			communicationPreferenceDTO.setMedia4(compref.getMedia4());
			communicationPreferenceDTO.setMedia5(compref.getMedia5());
			
			communicationPreferenceDTO.setCreationDate(compref.getCreationDate());
			communicationPreferenceDTO.setCreationSignature(compref.getCreationSignature());
			communicationPreferenceDTO.setCreationSite(compref.getCreationSite());
			communicationPreferenceDTO.setModificationDate(compref.getModificationDate());
			communicationPreferenceDTO.setModificationSignature(compref.getModificationSignature());
			communicationPreferenceDTO.setModificationSite(compref.getModificationSite());
			
			Set<MarketLanguage> marketLanguages = compref.getMarketLanguage();
			Set<MarketLanguageDTO> marketLanguagesDTO = new HashSet<MarketLanguageDTO>();
			if(!UList.isNullOrEmpty(marketLanguages)){
				for(MarketLanguage ml : marketLanguages) {
					marketLanguagesDTO.add(bo2Dto(ml));
				}
			}
			communicationPreferenceDTO.setMarketLanguageDTO(marketLanguagesDTO);
		}
		
		return communicationPreferenceDTO;
	}

	/*private static com.airfrance.repind.entity.individu.CommunicationPreferences bo2Dto(CommunicationPreferences compref) {
		com.airfrance.repind.entity.individu.CommunicationPreferences communicationPreferenceDTO = new com.airfrance.repind.entity.individu.CommunicationPreferences();
		
		if (compref != null) {
			communicationPreferenceDTO.setDomain(compref.getDomain());
			communicationPreferenceDTO.setComGroupType(compref.getCommunicationGroupeType());
			communicationPreferenceDTO.setComType(compref.getCommunicationType());
			communicationPreferenceDTO.setSubscribe(compref.getOptIn());
			communicationPreferenceDTO.setDateOptin(compref.getDateOfConsent());
			communicationPreferenceDTO.setChannel(compref.getSubscriptionChannel());
			communicationPreferenceDTO.setOptinPartners(compref.getOptInPartner());
			communicationPreferenceDTO.setDateOptinPartners(compref.getDateOfConsentPartner());
			communicationPreferenceDTO.setDateOfEntry(compref.getDateOfEntry());
			Media media = compref.getMedia();
			if (media != null) {
				communicationPreferenceDTO.setMedia1(media.getMedia1());
				communicationPreferenceDTO.setMedia2(media.getMedia2());
				communicationPreferenceDTO.setMedia3(media.getMedia3());
				communicationPreferenceDTO.setMedia4(media.getMedia4());
				communicationPreferenceDTO.setMedia5(media.getMedia5());
			}
			
			List<Signature> listSignatures = compref.getSignature();
			if(UList.isNullOrEmpty(listSignatures)){
				Signature signature = listSignatures.get(0);
				communicationPreferenceDTO.setCreationDate(signature.getDate());
				communicationPreferenceDTO.setCreationSignature(signature.getSignature());
				communicationPreferenceDTO.setCreationSite(signature.getSignatureSite());
			} 
		}
		
		return communicationPreferenceDTO;
	}*/
}
