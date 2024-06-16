package com.afklm.repind.v8.createorupdateindividualws.helpers;

import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceV2;
import com.airfrance.ref.exception.InvalidParameterException;
import org.springframework.stereotype.Service;

@Service
public class PreferenceCheckHelper {

	public void controlProspectPreference(CreateUpdateIndividualRequest request) throws InvalidParameterException {
		if (request.getPreferenceRequest() != null && request.getPreferenceRequest().getPreference() != null) {
			if (request.getPreferenceRequest().getPreference().size() > 1) {
				throw new InvalidParameterException("Only 1 preference of type TPC is allowed for prospect");
			}
			
			for (PreferenceV2 preference : request.getPreferenceRequest().getPreference()) {
				if (preference.getType() == null) {
					throw new InvalidParameterException("Prospect preference type must not be null");
				} 
				else {
					// Type not mandatory for Update
					if (!"TPC".equalsIgnoreCase(preference.getType()) && (preference.getId() != null && preference.getPreferenceDatas() != null)) {
						throw new InvalidParameterException("Only TPC type is allowed for prospect preference");
					}
					// REPIND-768 : We do not need anymore to identify origin of the preference
					/*
					else {
						if (preference.getPreferenceDatas() != null && !preference.getPreferenceDatas().getPreferenceData().isEmpty()) {
							
							boolean type = false;
							
							for (PreferenceDataV2 prefData : preference.getPreferenceDatas().getPreferenceData()) {
								
								if(prefData != null && prefData.getKey() != null && prefData.getValue() != null) {
									// check authorized fields wich are type = WWP and preferredAirport only									
									if("type".equalsIgnoreCase(prefData.getKey())) {
										if(!"WWP".equalsIgnoreCase(prefData.getValue())) {
											throw new InvalidParameterException("Only WWP for type is allowed for prospect preference data");	
										}
										type = true;
									}
									
								}
							}
							
							if(!type && preference.getId() == null) {
								throw new InvalidParameterException("type is mandatory for prospect preference data");
							}
							
						}
					}
					*/
				}
			}
			
		}
	}
}
