package com.afklm.repind.v7.createorupdateindividualws.helpers;

import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.response.InformationResponse;
import com.afklm.soa.stubs.w000442.v7.siccommontype.Information;
import com.afklm.soa.stubs.w001567.v1.response.BusinessErrorCodeEnum;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.compref.CommunicationPreferenceException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.entity.reference.RefComPref;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
// import com.airfrance.sicutf8.dto.prospect.ProspectCommunicationPreferencesDTO;
// import com.airfrance.sicutf8.dto.prospect.ProspectMarketLanguageDTO;

@Service("communicationPreferencesHelperV7")
public class CommunicationPreferencesHelper {

	@Autowired
	protected ReferenceDS referencesDS;

	@Autowired
	protected RefComPrefRepository refComPrefRepository;
	
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
		
		if (!commPref.getSubscribe().matches("[YN]")
				&& !("U".equals(commPref.getDomain()) && "P".equals(commPref.getSubscribe()))) {
			throw new InvalidParameterException("Unknonw optIn, should be Y, N or P (for Ultimate only)");
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
	public void crmOptinComPrefs(List<CommunicationPreferencesDTO> comPrefsList, CreateUpdateIndividualResponse response) throws JrafDaoException, CommunicationPreferenceException {
		
		if (comPrefsList == null) {
			return;
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
					throw new CommunicationPreferenceException("Cannot create communication preferences, no REF_COMPREF defined ",
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
								throw new CommunicationPreferenceException("Cannot create communication preferences, no COMPREF defined for Market ",
										"", "", "", "");
							}
							
							// Yes. Next, what are the available languages of the associated FB Essential Ref Com Pref ?
							reComprefLanguages = findRefComPrefByMarket(cp.getDomain(), "FB_ESS", cp.getComGroupType(), ml.getMarket());
							if(reComprefLanguages == null) {
								throw new CommunicationPreferenceException("Cannot create communication preferences, no COMPREF defined for FB_ESS ",
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
			Information info = new Information();
			info.setInformationCode(BusinessErrorCodeEnum.OTHER.name());
			info.setInformationDetails("Warning : One or many market languages created with an unknown value");
			
			InformationResponse infoResponse = new InformationResponse();
			infoResponse.setInformation(info);
			
			response.getInformationResponse().add(infoResponse);
		}
	}

	/**
	 * Transform a prospect communication preferences set according to CRM Optin business rules
	 * 
	 * @param comPrefsSet the Communication Preferences to transform
	 * @throws JrafDaoException
	 * @throws CommunicationPreferenceException 
	 */
/*	
	public void crmOptinComPrefs(Set<ProspectCommunicationPreferencesDTO> comPrefsSet, CreateUpdateIndividualResponse response) throws JrafDaoException, CommunicationPreferenceException {
		
		if (comPrefsSet == null) {
			return;
		}
		boolean warningSalesComPref = false;
		for(ProspectCommunicationPreferencesDTO cp : comPrefsSet) {
			
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
				cp.setOptIn(ruleMandatoryOptin(rcpList.get(0), cp.getOptIn()));
				
				// Market Languages
				Set<ProspectMarketLanguageDTO> marketLanguages = cp.getMarketLanguageDTO();
				if(marketLanguages != null) {
					for(ProspectMarketLanguageDTO ml : marketLanguages) {
						
						if("*".equals(ml.getMarket())) {
							throw new CommunicationPreferenceException(
									"Cannot create communication preferences with market=* ", "", "", "", "");
						}
						
						String wantedLanguage = ml.getLanguage();
						
						// We check if there is a corresponding refcompref for this language
						RefComPref refComPrefOptin = findRefComPrefByMarket(cp.getDomain(), cp.getCommunicationType(), cp.getCommunicationGroupType(), ml.getMarket());
						RefComPref reComprefLanguages = refComPrefOptin;
						if(refComPrefOptin == null) { //if not
						
							// Is there an entry for market=* ?
							refComPrefOptin = findRefComPrefByAllMarket(cp.getDomain(), cp.getCommunicationType(), cp.getCommunicationGroupType());
							reComprefLanguages = refComPrefOptin;
							if(refComPrefOptin == null) {
								throw new CommunicationPreferenceException("Cannot create communication preferences, no COMPREF defined ",
										"", "", "", "");
							}
							
							// Yes. Next, what are the available languages of the associated FB Essential Ref Com Pref ?
							reComprefLanguages = findRefComPrefByMarket(cp.getDomain(), "FB_ESS", cp.getCommunicationGroupType(), ml.getMarket());
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
			Information info = new Information();
			info.setInformationCode(BusinessErrorCodeEnum.OTHER.name());
			info.setInformationDetails("Warning : One or many market languages created with an unknown value");
			
			InformationResponse infoResponse = new InformationResponse();
			infoResponse.setInformation(info);
			
			response.getInformationResponse().add(infoResponse);
		}
	}
*/	
	private List<RefComPref> findComPrefInRefComPref(CommunicationPreferencesDTO cp) throws JrafDaoException {
		return refComPrefRepository.findComPrefByDomainComTypeComGroupType(cp.getDomain(), cp.getComType(), cp.getComGroupType());
	}
	
/*	private List<RefComPref> findComPrefInRefComPref(ProspectCommunicationPreferencesDTO cp) throws JrafDaoException {
		return refComPrefDAO.findComPrefByDomainComTypeComGroupType(cp.getDomain(), cp.getCommunicationType(), cp.getCommunicationGroupType());
	}
*/	
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
/*	
	private boolean comPrefAndMarketLanguageExistInRefComPref(ProspectCommunicationPreferencesDTO cp) throws JrafDaoException, CommunicationPreferenceException {
		List<RefComPref> rcpList = findComPrefInRefComPref(cp);
		if(rcpList == null || rcpList.isEmpty()) {
			return false;
		}
		else {
			Set<ProspectMarketLanguageDTO> marketLanguages = cp.getMarketLanguageDTO();
			for(ProspectMarketLanguageDTO ml : marketLanguages) {
				rcpList = refComPrefDAO.findComPrefByMarketComTypeLanguage(ml.getMarket(), cp.getDomain(), cp.getCommunicationType(), cp.getCommunicationGroupType(), ml.getLanguage());
				if(rcpList == null || rcpList.size() == 0) {
					return false;
				}
			}
		}
		return true;
	}
*/	
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
			if (wantedLanguage.equals(refComPref.getDefaultLanguage4())) {
				return wantedLanguage;
			}
		}
		return refComPref.getDefaultLanguage1();
	}
	
	private String ruleMandatoryOptin(RefComPref refCompRef, String optin) {
		if("Y".equals(refCompRef.getMandatoryOptin())) {
			return "Y";
		} else {
			return optin;
		}
	}
}
