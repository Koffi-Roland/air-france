package com.airfrance.repind.dto.ws;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommunicationPreferencesRequestTransform {
	
	
	public static List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> requestToDto(List<CommunicationPreferencesRequestDTO> requestDTO) {
		if (requestDTO == null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> result = new ArrayList<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO>();
		
		for (CommunicationPreferencesRequestDTO comPrefReq : requestDTO) {
			com.airfrance.repind.dto.individu.CommunicationPreferencesDTO comPrefDTO = requestToDto(comPrefReq);
			result.add(comPrefDTO);
		}
		
		return result;
	}

	private static com.airfrance.repind.dto.individu.CommunicationPreferencesDTO requestToDto(CommunicationPreferencesRequestDTO req) {
		if (req == null) {
			return null;
		}
		
		com.airfrance.repind.dto.individu.CommunicationPreferencesDTO dto = new com.airfrance.repind.dto.individu.CommunicationPreferencesDTO();
		CommunicationPreferencesDTO wsDto = new CommunicationPreferencesDTO();
		
		if (req.getCommunicationPreferencesDTO() != null) {
			wsDto = req.getCommunicationPreferencesDTO();
		}
		
		if (wsDto != null) {
			if (wsDto.getCommunicationGroupeType() != null) {
				dto.setComGroupType(wsDto.getCommunicationGroupeType());
			}
			if (wsDto.getCommunicationType() != null) {
				dto.setComType(wsDto.getCommunicationType());
			}
			if (wsDto.getDomain() != null) {
				dto.setDomain(wsDto.getDomain());
			}
			if (wsDto.getDateOfConsent() != null) {
				dto.setDateOptin(wsDto.getDateOfConsent());
			}
			if (wsDto.getDateOfConsentPartner() != null) {
				dto.setDateOptinPartners(wsDto.getDateOfConsentPartner());
			}
			if (wsDto.getDateOfEntry() != null) {
				dto.setDateOfEntry(wsDto.getDateOfEntry());
			}
			if (wsDto.getOptIn() != null) {
				dto.setSubscribe(wsDto.getOptIn());
			}
			if (wsDto.getOptInPartner() != null) {
				dto.setOptinPartners(wsDto.getOptInPartner());
			}
			if (wsDto.getSubscriptionChannel() != null) {
				dto.setChannel(wsDto.getSubscriptionChannel());
			}
			if (wsDto.getMediaDTO() != null) {
				
				MediaDTO media = wsDto.getMediaDTO();
				
				if (media.getMedia1() != null) {
					dto.setMedia1(media.getMedia1());
				}
				if (media.getMedia2() != null) {
					dto.setMedia2(media.getMedia2());
				}
				if (media.getMedia3() != null) {
					dto.setMedia3(media.getMedia3());
				}
				if (media.getMedia4() != null) {
					dto.setMedia4(media.getMedia4());
				}
				if (media.getMedia5() != null) {
					dto.setMedia5(media.getMedia5());
				}
			}
			if (wsDto.getMarketLanguageDTO() != null) {
				List<MarketLanguageDTO> mlWsList = wsDto.getMarketLanguageDTO();
				Set<com.airfrance.repind.dto.individu.MarketLanguageDTO> setMl = new HashSet<com.airfrance.repind.dto.individu.MarketLanguageDTO>();
				
				for (MarketLanguageDTO mlWs : mlWsList) {
					com.airfrance.repind.dto.individu.MarketLanguageDTO mlDto = new com.airfrance.repind.dto.individu.MarketLanguageDTO();
					if (mlWs.getMarket() != null) {
						mlDto.setMarket(mlWs.getMarket());
					}
					if (mlWs.getLanguage() != null) {
						mlDto.setLanguage(mlWs.getLanguage());
					}
					if (mlWs.getDateOfConsent() != null) {
						mlDto.setDateOfConsent(mlWs.getDateOfConsent());
					}
					if (mlWs.getOptIn() != null) {
						mlDto.setOptIn(mlWs.getOptIn());
					}
					if (mlWs.getMediaDTO() != null) {
						
						MediaDTO media = mlWs.getMediaDTO();
						if (media.getMedia1() != null) {
							mlDto.setMedia1(media.getMedia1());
						}
						if (media.getMedia2() != null) {
							mlDto.setMedia2(media.getMedia2());
						}
						if (media.getMedia3() != null) {
							mlDto.setMedia3(media.getMedia3());
						}
						if (media.getMedia4() != null) {
							mlDto.setMedia4(media.getMedia4());
						}
						if (media.getMedia5() != null) {
							mlDto.setMedia5(media.getMedia5());
						}
					}
					setMl.add(mlDto);
					
				}
				dto.setMarketLanguageDTO(setMl);
			}
		}
		
		return dto;
	}
}
