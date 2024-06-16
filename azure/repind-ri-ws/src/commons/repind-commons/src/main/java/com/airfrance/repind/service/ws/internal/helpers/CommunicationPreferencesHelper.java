package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.compref.CommunicationPreferenceException;
import com.airfrance.ref.exception.compref.MarketLanguageException;
import com.airfrance.ref.exception.compref.MarketLanguageNotFoundException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.CommunicationPreferencesConstantValues;
import com.airfrance.ref.type.NATFieldsEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.reference.RefComPrefCountryMarketRepository;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InformationResponseDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.entity.reference.RefComPref;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommunicationPreferencesHelper {

	@Autowired
	protected ReferenceDS referencesDS;

	@Autowired
	protected IndividuDS individuDS;
	
	@Autowired
	protected RefComPrefRepository refComPrefRepository;
	
	
	@Autowired
	protected RefComPrefCountryMarketRepository refComPrefCountryMarketRepository;
	
	@Autowired
	protected CommunicationPreferencesRepository communicationPreferencesRepository;
	

	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;
	
	/**
	 * Check given communication preferences according the following rules:
	 * 
	 * <ul>
	 *   <li>0..10 communication preferences</li>
	 *   <li>0..10 marketing language</li>
	 * </ul>
	 * 
	 * @param commPrefList
	 * @throws InvalidParameterException
	 */
	public void checkCommPrefList(List<CommunicationPreferencesDTO> commPrefList, UpdateModeEnum updateMode) throws InvalidParameterException, JrafDomainException {

		if (commPrefList == null || commPrefList.size() == 0) {
			return;
		}

		if (commPrefList.size() > 10) {
			throw new InvalidParameterException("No more than 10 communication preferences allowed in input");
		}

		for (CommunicationPreferencesDTO commPref : commPrefList) {

			checkCommPref(commPref);

			Set<MarketLanguageDTO> marketLanguageSet = commPref.getMarketLanguageDTO();

			checkMarketLanguage(marketLanguageSet);
			
			if (marketLanguageSet != null && marketLanguageSet.size() > 10) {
				throw new InvalidParameterException("No more than 10 market language for 1 communication preference in input");
			}

		}

	}

	/**
	 * Check a communication preference
	 * 
	 * @param commPref
	 * @throws InvalidParameterException
	 */
	protected void checkCommPref(CommunicationPreferencesDTO commPref) throws InvalidParameterException, JrafDomainException {

		if (commPref == null) {
			return;
		}

		boolean commPrefExists = false;
		String domain = commPref.getDomain();
		String comGroupType = commPref.getComGroupType();
		String comType = commPref.getComType();

		if (comGroupType == null || comType == null) { // NULL domain is authorized: it means SALES
			throw new InvalidParameterException("Invalid communication preference data");
		}

		try {
			commPrefExists = referencesDS.refComPrefExistForIndividual(domain, comGroupType, comType);
		} catch (JrafDomainException e) {
			throw new InvalidParameterException("Invalid communication preference data", e);
		}

		if (!commPrefExists) {
			throw new InvalidParameterException("Unknown domain, group type, or type");
		}
	}

	/**
	 * This methods checks that each marketLanguage has the mandatories field.
	 * @param mls a list of market languages
	 * @throws MissingParameterException
	 * @throws InvalidParameterException 
	 */
	protected void checkMarketLanguage(Set<MarketLanguageDTO> mls) throws MissingParameterException, InvalidParameterException {
		//REPIND-975 : remove duplicates
		Set<String> uniqueMl = new HashSet<String>();
		
		// To not have NPE
		if (mls != null) {
			for (Iterator<MarketLanguageDTO> it = mls.iterator(); it.hasNext() ;) {
				MarketLanguageDTO mlDTO = it.next();
				
				if(!uniqueMl.add(mlDTO.getMarket() + mlDTO.getLanguage())) {
					it.remove();
					continue;
				}
				if (StringUtils.isBlank(mlDTO.getMarket())) {
					throw new MissingParameterException("Market is a mandatory field for marketLanguage");
				}
				/*if (StringUtils.isBlank(mlDTO.getOptIn())) {
					throw new MissingParameterException("Optin is a mandatory field for marketLanguage");
				}*/
				if (StringUtils.isBlank(mlDTO.getLanguage())) {
					throw new MissingParameterException("Language is a mandatory field for marketLanguage");
				}
			}
		}
	}
	
	/**
	 * Transform a communication preferences list according to CRM Optin business rules
	 * 
	 * @param comPrefsList the Communication Preferences to transform
	 * @throws JrafDaoException
	 * @throws CommunicationPreferenceException 
	 */
	public CreateModifyIndividualResponseDTO crmOptinComPrefs(List<CommunicationPreferencesDTO> comPrefsList, CreateModifyIndividualResponseDTO responseDTO) throws JrafDaoException, CommunicationPreferenceException {
		
		if (comPrefsList == null) {
			return responseDTO;
		}
		boolean warningSalesComPref = false;
		for(CommunicationPreferencesDTO cp : comPrefsList) {
			
			if(cp.getDomain() == null || "S".equals(cp.getDomain())) { // null means Sales too
				// SALES
				if(!comPrefAndMarketLanguageExistInRefComPref(cp)) {
					warningSalesComPref = true;
				}
			} else if("F".equals(cp.getDomain())) {
				//FLYING BLUE
				
				// We check if there is a corresponding refcompref 
				List<RefComPref> rcpList = findComPrefInRefComPref(cp);
				if(rcpList == null || rcpList.size() == 0) {
					throw new CommunicationPreferenceException("Cannot create communication preferences, no COMPREF defined ",
							"", "", "", "");
				} 
				
				// CRM Optin rules on Com Pref
				cp.setSubscribe(ruleMandatoryOptin(rcpList.get(0), cp.getSubscribe()));
				if (rcpList.get(0).getMedia() != null) {
					cp.setMedia1(rcpList.get(0).getMedia().getCodeMedia());
				}				
				
				// Market Languages
				Set<MarketLanguageDTO> marketLanguages = cp.getMarketLanguageDTO();
				if(marketLanguages != null) {
					for(MarketLanguageDTO ml : marketLanguages) {
						
						if("*".equals(ml.getMarket())) {
							throw new CommunicationPreferenceException(
									"Cannot create communication preferences with market=* ", "", "", "", "");
						}
						
						String wantedLanguage = ml.getLanguage();
						
						// We check if there is a corresponding refcompref for this language
						RefComPref refComPrefOptin = findRefComPrefByMarket(cp.getDomain(), cp.getComType(), cp.getComGroupType(), ml.getMarket());
						RefComPref reComprefLanguages = refComPrefOptin;
						if(refComPrefOptin == null) { //if not
						
							// Is there an entry for market=* ?
							refComPrefOptin = findRefComPrefByAllMarket(cp.getDomain(), cp.getComType(), cp.getComGroupType());
							reComprefLanguages = refComPrefOptin;
							if(refComPrefOptin == null) {
								throw new CommunicationPreferenceException("Cannot create communication preferences, no COMPREF defined ",
										"", "", "", "");
							}
							
							// Yes. Next, what are the available languages of the associated FB Essential Ref Com Pref ?
							reComprefLanguages = findRefComPrefByMarket(cp.getDomain(), "FB_ESS", cp.getComGroupType(), ml.getMarket());
							if(reComprefLanguages == null) {
								throw new CommunicationPreferenceException("Cannot create communication preferences, no COMPREF defined ",
										"", "", "", "");
							}
						}

						// CRM Optin rules on optin and languages
						ml.setLanguage(ruleLanguage(reComprefLanguages, wantedLanguage));
						ml.setOptIn(ruleMandatoryOptin(refComPrefOptin, ml.getOptIn()));
					}
				}
			}
		}
		if(warningSalesComPref) {
			InformationDTO info = new InformationDTO();
			info.setInformationCode("OTHER");
			info.setInformationDetails("Warning : One or many market languages created with an unknown value");
			
			InformationResponseDTO infoResponse = new InformationResponseDTO();
			infoResponse.setInformation(info);
			
			responseDTO.getInformationResponse().add(infoResponse);
		}
		
		return responseDTO;
	}
	
	private List<RefComPref> findComPrefInRefComPref(CommunicationPreferencesDTO cp) throws JrafDaoException {
		return refComPrefRepository.findComPrefByDomainComTypeComGroupType(cp.getDomain(), cp.getComType(), cp.getComGroupType());
	}
	
	private boolean comPrefAndMarketLanguageExistInRefComPref(CommunicationPreferencesDTO cp) throws JrafDaoException, CommunicationPreferenceException {
		List<RefComPref> rcpList = findComPrefInRefComPref(cp);
		// REPIND-555 : remove other warning
		if(rcpList == null || rcpList.size() == 0) {
			return false;
		}
		else {
			Set<MarketLanguageDTO> marketLanguages = cp.getMarketLanguageDTO();
			for(MarketLanguageDTO ml : marketLanguages) {
				rcpList = refComPrefRepository.findComPrefByMarketComTypeLanguage(ml.getMarket(), cp.getDomain(), cp.getComType(), cp.getComGroupType(), ml.getLanguage());
				if(rcpList == null || rcpList.size() == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	private RefComPref findRefComPrefByMarket(String domain, String comType, String comGType, String market) throws JrafDaoException {
		List<RefComPref> refComPrefs = refComPrefRepository.findComPerfByMarketComTypeComGType(market, domain, comType, comGType);
		if(refComPrefs == null || refComPrefs.size() == 0) {
			return null;
		}
		return refComPrefs.get(0);
	}
	
	private RefComPref findRefComPrefByAllMarket(String domain, String comType, String comGType) throws JrafDaoException {
		return findRefComPrefByMarket(domain, comType, comGType, "*");
	}

	private String ruleLanguage(RefComPref refComPref, String wantedLanguage) {
		if (null != wantedLanguage) {
			if (wantedLanguage.equals(refComPref.getDefaultLanguage1())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage2())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage3())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage4())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage5())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage6())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage7())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage8())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage9())) {
				return wantedLanguage;
			}
			if (wantedLanguage.equals(refComPref.getDefaultLanguage10())) {
				return wantedLanguage;
			}
		}
		return refComPref.getDefaultLanguage1();
	}

	private String ruleLanguage(RefComPref refComPref, String wantedLanguage1, String wantedLanguage2) {
		if (null != wantedLanguage1) {
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage1())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage2())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage3())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage4())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage5())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage6())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage7())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage8())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage9())) {
				return wantedLanguage1;
			}
			if (wantedLanguage1.equals(refComPref.getDefaultLanguage10())) {
				return wantedLanguage1;
			}
		}
		if (null != wantedLanguage2) {
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage1())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage2())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage3())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage4())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage5())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage6())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage7())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage8())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage9())) {
				return wantedLanguage2;
			}
			if (wantedLanguage2.equals(refComPref.getDefaultLanguage10())) {
				return wantedLanguage2;
			}
		}
		return refComPref.getDefaultLanguage1();
	}
	
	private String ruleNAT(RefComPref refCompRef, Profils profils) {
		String mailingAutorise = profils.getSmailing_autorise();
		String fieldA = refCompRef.getFieldA();
		String fieldT = refCompRef.getFieldT();
		String fieldN = refCompRef.getFieldN();
		if (NATFieldsEnum.AIRFRANCE.getValue().equals(mailingAutorise)) {
			return fieldA;
		}
		if (NATFieldsEnum.ALL.getValue().equals(mailingAutorise)) {
			return fieldT;
		}
		if (NATFieldsEnum.NONE.getValue().equals(mailingAutorise)) {
			return fieldN;
		}
		return null;
	}
	
	private String ruleMandatoryOptin(RefComPref refCompRef, String optin) {
		if("Y".equals(refCompRef.getMandatoryOptin())) {
			return "Y";
		} else {
			return optin;
		}
	}
	
	/**
	 * Update the FB market and languages when the individual
	 * is changing his country of residence/spoken language.
	 * See : REPIND-1215
	 * @throws JrafDomainException 
	 * 
	 */
	public void updateFBMarketLanguagesWhenMoving(IndividuDTO individualFromWS, List<PostalAddressDTO> postalAddressListFromWS, List<CommunicationPreferencesDTO> communicationPreferencesFromWS, IndividuDTO individualFromDB, SignatureDTO signature) throws JrafDomainException {

		//Retrieve the old spoken language from DB
		String oldSpokenLanguage = getSpokenLanguage(individualFromDB);
		
		//Retrieve the new spoken language from request
		String newSpokenLanguage = getSpokenLanguage(individualFromWS);
		newSpokenLanguage = newSpokenLanguage == null ? oldSpokenLanguage : newSpokenLanguage;
				
		//Retrieve the old country code from DB
		String oldCountryCode = getFbCountryOfResidence(individualFromDB.getPostaladdressdto());
		
		//Retrieve the new country code from request
		String newCountryCode = getFbCountryOfResidence(postalAddressListFromWS, true);
		newCountryCode = newCountryCode == null ? oldCountryCode : newCountryCode;
				
		//If the spoken language or the country code have changed
		if(	(oldCountryCode == null && newCountryCode != null) ||
				(oldCountryCode != null && newCountryCode!= null && !oldCountryCode.equals(newCountryCode)) ||
				(oldSpokenLanguage == null && newSpokenLanguage != null) ||
				(oldSpokenLanguage != null && newSpokenLanguage != null && !oldSpokenLanguage.equals(newSpokenLanguage))) {
			
			// The individual has moved to another country or change his spoken language
			// We must update his FB market/language
						
			//First we calculate the new market
			String newMarket = null;
			List<String> newMarkets = refComPrefCountryMarketRepository.findMarketByCodePays(newCountryCode);
			if (UList.isNullOrEmpty(newMarkets)) {
				throw new MarketLanguageException("No Market is associated to country: " + newCountryCode, null, null, null);
			} else {
				newMarket = newMarkets.get(0);
			}
			
			//Then we get the refComPref for this new market
			// to calculate the new "available FB languages"
			List<RefComPref> listRefComPref = refComPrefRepository.findComPerfByMarketComTypeComGType(newMarket, CommunicationPreferencesConstantValues.DOMAIN_F, CommunicationPreferencesConstantValues.FB_ESS, CommunicationPreferencesConstantValues.NEWSLETTER);
			if (!UList.isNullOrEmpty(listRefComPref)) {			
				if (listRefComPref.size() > 1) {
					throw new MarketLanguageException("Too many communications preferences are defined", null, null, null);
				}
			} else {
				throw new MarketLanguageException("No communication preferences is defined", null, null, null);
			}
			RefComPref refComPrefFB_ESS =  listRefComPref.get(0);
			
			// We have to update the comprefs in DB and the ones in the request
			// (or else they will replace the one in DB...)
			List<CommunicationPreferencesDTO> communicationPreferencesFromDB = individualFromDB.getCommunicationpreferencesdto();
			updateFBMarketLanguageWhenMoving(communicationPreferencesFromWS, newMarket, newSpokenLanguage, refComPrefFB_ESS);
			updateFBMarketLanguageWhenMoving(communicationPreferencesFromDB, newMarket, newSpokenLanguage, refComPrefFB_ESS);
			
			//update the comprefs in DB now. The ones in the request will be processed normally			
			if(!UList.isNullOrEmpty(communicationPreferencesFromDB)) {
				Set<CommunicationPreferences> comprefSet = new HashSet<CommunicationPreferences>();
				String gin = individualFromDB.getSgin();
				if(gin != null) {
					for(CommunicationPreferencesDTO cpDto : communicationPreferencesFromDB) {
						CommunicationPreferences cp = communicationPreferencesRepository.findComPrefId(
								gin,
								cpDto.getDomain(),
								cpDto.getComGroupType(),
								cpDto.getComType());
						Set<MarketLanguage> mlSet = cp.getMarketLanguage();
						boolean mlWasUpdated = false;
						for(MarketLanguage ml : mlSet) {
							Optional<MarketLanguageDTO> mlDto = cpDto.getMarketLanguageDTO().stream().filter(dto ->
									dto.getMarketLanguageId().equals(ml.getMarketLanguageId())
							).findFirst();
							if (mlDto.isPresent()) {
								String newMLMarket = mlDto.get().getMarket();
								String newMLLanguage = mlDto.get().getLanguage();
								if (!newMLLanguage.equals(ml.getLanguage()) || !newMLMarket.equals(ml.getMarket())) {
									mlWasUpdated = true;
									ml.setMarket(newMLMarket);
									ml.setLanguage(newMLLanguage);
									ml.setModificationDate(signature.getDate());
									ml.setModificationSignature(signature.getSignature());
									ml.setModificationSite(signature.getSite());
								}
							}
						}
						if (mlWasUpdated) {
							cp.setModificationDate(signature.getDate());
							cp.setModificationSignature(signature.getSignature());
							cp.setModificationSite(signature.getSite());
						}
						comprefSet.add(cp);
					}
					individuDS.updateIndividualComPref(comprefSet, gin);
				}
			}
		}
	}
	
	private void updateFBMarketLanguageWhenMoving(List<CommunicationPreferencesDTO> communicationPreferencesToUpdate, String newMarket, String newSpokenLanguage, RefComPref refComPrefFB_ESS) throws JrafDaoException, MarketLanguageException {
		if(!UList.isNullOrEmpty(communicationPreferencesToUpdate)) {
			for(CommunicationPreferencesDTO comprefDTO : communicationPreferencesToUpdate) {
				if(CommunicationPreferencesConstantValues.DOMAIN_F.equals(comprefDTO.getDomain())) {
			
					Set<MarketLanguageDTO> mlDtoSet = comprefDTO.getMarketLanguageDTO();
					if (UList.isNullOrEmpty(mlDtoSet) || mlDtoSet.size() > 1) {
						throw new MarketLanguageException("No communication preferences is defined", null, null, null);
					} else {
						MarketLanguageDTO mlDto = mlDtoSet.iterator().next();
						mlDto.setMarket(newMarket);
						// we find the corresponding refcompref
						List<RefComPref> refComprefs = refComPrefRepository.findComPrefByAllMarketsAndComType(newMarket, CommunicationPreferencesConstantValues.DOMAIN_F, comprefDTO.getComType(), CommunicationPreferencesConstantValues.MARKET_WORLD);
						if (UList.isNullOrEmpty(refComprefs) || refComprefs.size() > 1) {
							throw new MarketLanguageException("No communication preferences is defined", null, null, null);
						} else {
							RefComPref refCompref = refComprefs.get(0);
							if(CommunicationPreferencesConstantValues.MARKET_WORLD.equals(refCompref.getMarket())) {
								mlDto.setLanguage(ruleLanguage(refComPrefFB_ESS, newSpokenLanguage, mlDto.getLanguage()));
							} else {
								mlDto.setLanguage(ruleLanguage(refCompref, newSpokenLanguage, mlDto.getLanguage()));
							}	
						}
					}
				}	
			}
		}
	}
	
	private String getSpokenLanguage(IndividuDTO individual) {
		if(individual.getProfilsdto() != null && individual.getProfilsdto().getScode_langue() != null) {
			return individual.getProfilsdto().getScode_langue();
		}
		return null;
	}
	
	private String getFbCountryOfResidence(List<PostalAddressDTO> postalAddressList) {
		return getFbCountryOfResidence(postalAddressList, false);
	}
	
	private String getFbCountryOfResidence(List<PostalAddressDTO> postalAddressList, Boolean getOnlyValidIsiAddress) {
		// first we retrieve the valid ISI/M adress
		if(!UList.isNullOrEmpty(postalAddressList)) {
			for(PostalAddressDTO postalAdressDto : postalAddressList) {
				if("V".equals(postalAdressDto.getSstatut_medium())) {
					if(!UList.isNullOrEmpty(postalAdressDto.getUsage_mediumdto())) {
						for(Usage_mediumDTO usageMediumDto : postalAdressDto.getUsage_mediumdto()) {
							if("ISI".equals(usageMediumDto.getScode_application()) && "M".equals(usageMediumDto.getSrole1())) {
								return postalAdressDto.getScode_pays();
							}
						}
					}
				}
			}
			if(!getOnlyValidIsiAddress) {
				//if needed, we retrieve a non-valid ISI/M address
				for(PostalAddressDTO postalAdressDto : postalAddressList) {
					if(!UList.isNullOrEmpty(postalAdressDto.getUsage_mediumdto())) {
						for(Usage_mediumDTO usageMediumDto : postalAdressDto.getUsage_mediumdto()) {
							if("ISI".equals(usageMediumDto.getScode_application()) && "M".equals(usageMediumDto.getSrole1())) {
								return postalAdressDto.getScode_pays();
							}
						}
					}
				}
				//If needed, we retrieve a valid (non ISI) address
				for(PostalAddressDTO postalAdressDto : postalAddressList) {
					if("V".equals(postalAdressDto.getSstatut_medium())) {
						return postalAdressDto.getScode_pays();
					}
				}
			}
		}
		return null;
	}
	
	public String retrieveWantedMarketLanguage(List<CommunicationPreferencesDTO> cpWS) {
		if (!UList.isNullOrEmpty(cpWS)) {
			for (CommunicationPreferencesDTO cp : cpWS) {
				if (CommunicationPreferencesConstantValues.FB_ESS.equalsIgnoreCase(cp.getComType())) {
					if (!UList.isNullOrEmpty(cp.getMarketLanguageDTO())) {
						List<MarketLanguageDTO> listML = new ArrayList<MarketLanguageDTO>(cp.getMarketLanguageDTO());
						return listML.get(0).getLanguage();
					}
				}
			}
		}
		
		return "";
	}
	
	/**
	 * 
	 * @param newMarket
	 * @return FB new language obtained from the input country code
	 * @throws MarketLanguageException
	 * @throws JrafDaoException
	 */
	private String getFbNewLanguage(String newMarket) throws MarketLanguageException, JrafDaoException {

		// First we get the refComPref for this new market
		// to calculate the new "available FB languages"
		List<RefComPref> listRefComPref = refComPrefRepository.findComPerfByMarketComTypeComGType(newMarket,
				CommunicationPreferencesConstantValues.DOMAIN_F, CommunicationPreferencesConstantValues.FB_ESS,
				CommunicationPreferencesConstantValues.NEWSLETTER);

		if (!UList.isNullOrEmpty(listRefComPref)) {
			if (listRefComPref.size() > 1) {
				throw new MarketLanguageException("Too many communications preferences are defined", null, null, null);
			}

		} else {
			throw new MarketLanguageException("No communication preferences is defined", null, null, null);
		}

		// And then we return the new language
		RefComPref refComPrefFBESS = listRefComPref.get(0);
		return refComPrefFBESS.getDefaultLanguage1();
	}
	
	/**
	 * 
	 * @param dtoToSave
	 * @return FB new language obtained from the input country code
	 * @throws MarketLanguageException
	 */
	private String getFbNewMarket(String codePays) throws MarketLanguageException {
		
		String newMarket = null;
		List<String> newMarkets = refComPrefCountryMarketRepository.findMarketByCodePays(codePays);

		if (UList.isNullOrEmpty(newMarkets)) {
			throw new MarketLanguageNotFoundException("No Market is associated to country: " + codePays, null, null,
					null);

		} else {
			newMarket = newMarkets.get(0);
		}

		return newMarket;
	}
	
	/**
	 * Update markets language if:
	 * the updated address has an ISI mailing usage
	 * <ul>
	 * 		<li>1. Country address has changed</li>
	 * 		<li>2. The updated address has an ISI mailing usage</li>
	 * </ul>
	 * 
	 * @param dtoToSave
	 * @param signatureAPP
	 * @param mustUpdateMarketLanguage
	 * @throws JrafDomainException
	 * @throws MarketLanguageException
	 * @throws JrafDaoException
	 */
	public void updateMarketLanguage(String codePays, String gin, SignatureDTO signatureAPP,
			boolean mustUpdateMarketLanguage) throws JrafDomainException, MarketLanguageException, JrafDaoException {

		if (mustUpdateMarketLanguage) {
			// FB individu commuinication preferences
			List<CommunicationPreferencesDTO> comPrefList = communicationPreferencesDS
					.findComPrefIdByDomain(gin, "F");

			if (!comPrefList.isEmpty()) {
				// FB new market obtained from the input country code
				String newMarket = getFbNewMarket(codePays);

				// FB new language obtained from the input country code
				String newLanguage = getFbNewLanguage(newMarket);

				for (CommunicationPreferencesDTO cp : comPrefList) {
					Set<MarketLanguageDTO> marketLanguageList = cp.getMarketLanguageDTO();

					if (!UList.isNullOrEmpty(marketLanguageList)) {
						// FB comp pref => 1 market language
						MarketLanguageDTO ml = marketLanguageList.iterator().next();

						// New market and language
						ml.setMarket(newMarket);
						ml.setLanguage(newLanguage);

						// Market language signature
						ml.setModificationDate(signatureAPP.getDate());
						ml.setModificationSignature(signatureAPP.getSignature());
						ml.setModificationSite(signatureAPP.getSite());
					}

					// Com pref signature
					cp.setModificationDate(signatureAPP.getDate());
					cp.setModificationSignature(signatureAPP.getSignature());
					cp.setModificationSite(signatureAPP.getSite());
				}

				// Save
				communicationPreferencesDS.updateListWithMarketLanguage(comPrefList);
			}
		}
	}
}
