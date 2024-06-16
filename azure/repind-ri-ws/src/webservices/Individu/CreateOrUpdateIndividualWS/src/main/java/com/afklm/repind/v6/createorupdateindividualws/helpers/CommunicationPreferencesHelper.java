package com.afklm.repind.v6.createorupdateindividualws.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("communicationPreferencesHelperV6")
public class CommunicationPreferencesHelper {

	@Autowired
	protected ReferenceDS referencesDS;
	

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
	public void checkCommPrefList(List<CommunicationPreferencesDTO> commPrefList, UpdateModeEnum updateMode) throws InvalidParameterException {

		if (commPrefList == null || commPrefList.size() == 0) {
			return;
		}

		if (commPrefList.size() > 10) {
			throw new InvalidParameterException("No more than 10 communication preferences allowed in input");
		}

		for (CommunicationPreferencesDTO commPref : commPrefList) {

			checkCommPref(commPref);

			Set<MarketLanguageDTO> marketLanguageSet = commPref.getMarketLanguageDTO();

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
	protected void checkCommPref(CommunicationPreferencesDTO commPref) throws InvalidParameterException {

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
			// REPIND-555 : Before migration Prospect, ComPref get from SICUTF8 database
			if(domain != null && domain.equalsIgnoreCase("S")) {
				commPrefExists = referencesDS.refComPrefExistForIndividual(domain, comGroupType, comType);
			}
		} catch (JrafDomainException e) {
			throw new InvalidParameterException("Invalid communication preference data", e);
		}

		if (!commPrefExists) {
			throw new InvalidParameterException("Unknown domain, group type, or type");
		}
	}
}
