package com.afklm.repind.msv.preferences.services.builder;


import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.PreferenceDataV2;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.PreferenceDatasV2;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd0.PreferenceV2;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd6.PreferenceRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd7.CreateUpdateIndividualRequest;
import org.springframework.stereotype.Component;

@Component
public class PreferenceRequestBuilder extends W000442RequestBuilder {

	@Override
	public CreateUpdateIndividualRequest buildCreateRequest(String id, Object model) {
		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		PreferenceRequest preferenceRequest = getPreferenceRequest(RequestType.CREATE, (ModelPreference) model);
		request.setPreferenceRequest(preferenceRequest);
		
		return request;
	}

	@Override
	public CreateUpdateIndividualRequest buildUpdateRequest(String id, Object model) {
		
		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		PreferenceRequest preferenceRequest = getPreferenceRequest(RequestType.UPDATE, (ModelPreference) model);
		request.setPreferenceRequest(preferenceRequest);
		
		return request;
		
	}

	@Override
	public CreateUpdateIndividualRequest buildDeleteRequest(String id, Object model) {

		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		PreferenceRequest preferenceRequest = getPreferenceRequest(RequestType.DELETE, (ModelPreference) model);
		request.setPreferenceRequest(preferenceRequest);
		
		return request;
		
	}

	private PreferenceRequest getPreferenceRequest(RequestType type, ModelPreference model) {

		PreferenceRequest preferenceRequest = new PreferenceRequest();
		
		PreferenceV2 preference = new PreferenceV2();
		if (type == RequestType.DELETE || type == RequestType.UPDATE) {
			preference.setId(String.valueOf(model.getPreferenceId()));
		}
		preference.setType(model.getType());

		if (type == RequestType.UPDATE || type == RequestType.CREATE) {
			PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
			for (ModelPreferenceData modelPreferenceData : model.getPreferenceData()) {
				PreferenceDataV2 data = new PreferenceDataV2();
				data.setKey(modelPreferenceData.getKey());
				if (!("".equals(modelPreferenceData.getValue()))) {
					data.setValue(modelPreferenceData.getValue());
				}
				preferenceDatas.getPreferenceData().add(data);
			}
			preference.setPreferenceDatas(preferenceDatas);
		}
		
		preferenceRequest.getPreference().add(preference);
		
		return preferenceRequest;
		
		
	}

}
