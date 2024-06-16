package com.airfrance.repind.util;

import com.airfrance.ref.type.PreferenceTypeEnum;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.preference.PreferenceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferenceUtils {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PreferenceUtils.class);
	
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public static String getDataValueFromPreference(PreferenceDTO preferenceDTO, String key) {
		
		if (preferenceDTO.getPreferenceDataDTO() != null) {
			for (PreferenceDataDTO prefDataDTOLoop : preferenceDTO.getPreferenceDataDTO()) {
				if (prefDataDTOLoop.getKey().equalsIgnoreCase(key)) {
					return prefDataDTOLoop.getValue();
				}
			}
		}
		
		return null;
	}
	
	public static String getDataValueFromPreference(Preference preference, String key) {
		
		if (preference.getPreferenceData() != null) {
			for (PreferenceData prefDataLoop : preference.getPreferenceData()) {
				if (prefDataLoop.getKey().equalsIgnoreCase(key)) {
					return prefDataLoop.getValue();
				}
			}
		}
		
		return null;
	}
	
	public static Long getDataIdFromPreference(Preference preference, String key) {
		
		if (preference.getPreferenceData() != null) {
			for (PreferenceData prefDataLoop : preference.getPreferenceData()) {
				if (prefDataLoop.getKey().equalsIgnoreCase(key)) {
					return prefDataLoop.getPreferenceDataId();
				}
			}
		}
		
		return null;
	}
	
	public static Date getDateFromString(String date) {
		Date dateToReturn = null;
		
		if (date != null) {
			try {
				dateToReturn = dateFormatter.parse(date);
			} catch (ParseException e) {
				String msg = "Can't parse the date";
				LOGGER.error(msg, e);
			}
		}
		
		return dateToReturn;
	}
	
	public static boolean isRiScopePreference(com.airfrance.repind.dto.ws.PreferenceDTO pref) {
		boolean response = false;
		
		if (pref.getType() != null) {
			// TRAVEL DOC OK
			if (PreferenceTypeEnum.TRAVEL_DOC.toString().equalsIgnoreCase(pref.getType())) {
				response = true;
			}
			// APIS DATA OK
			if (PreferenceTypeEnum.APIS_DATA.toString().equalsIgnoreCase(pref.getType())) {
				response = true;
			}
			// POSTAL ADR OK
			if (PreferenceTypeEnum.TRAVEL_ADR.toString().equalsIgnoreCase(pref.getType())) {
				response = true;
			}
		}
		return response;
	}
}
