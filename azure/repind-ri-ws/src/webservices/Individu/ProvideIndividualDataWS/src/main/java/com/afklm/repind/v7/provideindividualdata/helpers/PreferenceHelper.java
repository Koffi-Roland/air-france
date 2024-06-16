package com.afklm.repind.v7.provideindividualdata.helpers;

import com.afklm.soa.stubs.w000410.v2.common.MarketingDataV2;
import com.afklm.soa.stubs.w000410.v2.common.TravelCompanionV2;
import com.afklm.soa.stubs.w000410.v2.common.TravelDocument;
import com.afklm.soa.stubs.w000410.v2.common.TravelPreferences;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.preference.PreferenceTransform;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.util.SicStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("preferenceHelperV7")
public class PreferenceHelper {

	@Autowired
	private PreferenceDS preferenceDS;

	
	/**
	 * Calculate gauge for marketing information
	 * <p>
	 * The gauge is calculated from the following rules :
	 * <ul>
	 * <li>Preferred arrival airport = <b>+5%</b></li>
	 * <li>Preferred departure airport = <b>+5%</b></li>
	 * <li>Travel document (PA type) = <b>+10%</b></li>
	 * <li>Travel companion = <b>+10%</b></li>
	 * <li>Preferred seat = <b>+5%</b></li>
	 * <li>Preferred travel class = <b>+5%</b></li>
	 * </ul>
	 * <p>
	 * @param MarketingInformationV2
	 * @return gauge (%)
	 */
	public int calculateMarketingInformationGauge(MarketingDataV2 marketingInformation) {
		
		int percentage = 0;
		
		if(marketingInformation==null) {
			return percentage;
		}
		
		TravelPreferences travelPreferences = marketingInformation.getTravelPreferences();
		List<TravelDocument> travelDocumentList = marketingInformation.getTravelDocumentList();
		List<TravelCompanionV2> travelCompanionV2List = marketingInformation.getTravelCompanionV2List();
		
	    // PREFERRED ARRIVAL AIRPORT = 5%
	    if(travelPreferences != null && travelPreferences.getArrivalAirport() != null) {
	        percentage+=5;
	    }
	    
	    // PREFERRED DEPARTURE AIRPORT = 5%
	    if(travelPreferences != null && travelPreferences.getDepartureAirport() != null) {
	            percentage+=5;
	    }
	    
	    // TRAVEL DOCUMENT = 10%
	    if(travelDocumentList != null && travelDocumentList.size() > 0) {
	        for(TravelDocument doc : travelDocumentList) {
	            if("PA".equals(doc.getType()) && !isMaccTravelDocumentEmpty(doc)) {
	                percentage+=10;
	                break;
	            }
	        }
	    }
	
	    // TRAVEL COMPANIONS = 10%
	    if(travelCompanionV2List != null && travelCompanionV2List.size() > 0) {
			for (TravelCompanionV2 companion : travelCompanionV2List) {
				if(!isMaccTravelCompanionV2Empty(companion)) {
					 percentage+=10;
					 break;
				}
			}
	    }
	    
	    // SEAT = 5%
	    if(travelPreferences != null && travelPreferences.getSeat() != null) {
	        percentage+=5;
	    }
	
	    // TRAVEL CLASS = 5%
	    if(travelPreferences != null && travelPreferences.getTravelClass() != null) {
	        percentage+=5;
	    }
	    
		return percentage;
	}

	/**
	 * Checks if a MaccTravelDocument object is empty
	 * <p>
	 * A MaccTravelDocument object is not empty if the following attributes are filled :<br/>
	 * <code>number, issueDate, nationality, countryIssued, expirationDate</code>
	 * </p>
	 * @param doc
	 * @return true/false
	 */
	protected boolean isMaccTravelDocumentEmpty(TravelDocument doc) {
		boolean empty = true;
		empty &= (doc.getNumber() == null);
		empty &= (doc.getIssueDate() == null); 
		empty &= (doc.getNationality() == null);
		empty &= (doc.getCountryIssued() == null);
		empty &= (doc.getExpirationDate() == null);
		return empty;
	}
	
	/**
	 * Checks if a MaccTravelCompanionV2 object is empty
	 * <p>
	 * A MaccTravelCompanionV2 object is not empty if the following attributes are filled :<br/>
	 * <code>civility, firstName, lastName</code>
	 * </p>
	 * @param companion
	 * @return true/false
	 */
	protected boolean isMaccTravelCompanionV2Empty(TravelCompanionV2 companion) {
		boolean empty = true;
		empty &= (companion.getCivility() == null);
		empty &= (companion.getFirstName() == null);
		empty &= (companion.getLastName() == null); 
		return empty;
	}
	
	public PreferenceDTO normalizePreferenceDataKey(PreferenceDTO preferenceDTO) throws JrafDomainException {
		Preference preferenceToNormalize = PreferenceTransform.dto2BoLight(preferenceDTO);
		preferenceDS.normalizePreferenceDataKey(preferenceToNormalize);
		return PreferenceTransform.bo2DtoLight(preferenceToNormalize);
	}
	
	public List<PreferenceDTO> cleanNonPrintableChars(List<PreferenceDTO> preferencesDTO){
		if (preferencesDTO != null) {
			for(PreferenceDTO preferenceDTO : preferencesDTO){
				if("TDC".equalsIgnoreCase(preferenceDTO.getType())){
					for(PreferenceDataDTO preferenceDataDTO : preferenceDTO.getPreferenceDataDTO()){
						if("lastname".equalsIgnoreCase(preferenceDataDTO.getKey()) 
								|| ("firstname".equalsIgnoreCase(preferenceDataDTO.getKey()))){

							preferenceDataDTO.setValue(SicStringUtils.replaceNonPrintableChars(preferenceDataDTO.getValue()));
						}
					}
				}
			}
		}
		return preferencesDTO;
	}
}
