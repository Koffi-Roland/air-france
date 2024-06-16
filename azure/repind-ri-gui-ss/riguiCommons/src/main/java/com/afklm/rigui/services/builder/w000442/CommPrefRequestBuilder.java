package com.afklm.rigui.services.builder.w000442;

import java.util.List;

import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.CommunicationPreferences;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd2.MarketLanguage;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd3.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd4.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd6.LanguageCodesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.model.individual.ModelCommunicationPreference;
import com.afklm.rigui.model.individual.ModelMarketLanguage;
import com.afklm.rigui.services.IndividualService;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.RequestType;
import com.afklm.rigui.dto.individu.IndividuDTO;

@Component
public class CommPrefRequestBuilder extends W000442RequestBuilder {
	
	private static final String COMM_PREF_UPDATE_MODE = "U";
	
	@Autowired
	private IndividualService individualService;

	@Override
	public CreateUpdateIndividualRequest buildCreateRequest(String id, Object model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateUpdateIndividualRequest buildUpdateRequest(String id, Object model) {

		CreateUpdateIndividualRequest request = null;

		request = mergeCommonRequestElements(id);
		ComunicationPreferencesRequest comunicationPreferencesRequest = getCommPrefRequest(RequestType.UPDATE, (ModelCommunicationPreference) model);
		request.getComunicationPreferencesRequest().add(comunicationPreferencesRequest);
		request.setUpdateCommunicationPrefMode(COMM_PREF_UPDATE_MODE);

		addSpecificParameters(request, (ModelCommunicationPreference) model);
		
		return request;
		
	}

	@Override
	public CreateUpdateIndividualRequest buildDeleteRequest(String id, Object model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Get the communication preference request according to the parameters (the type of request and the commpref's model)
	 * @param type (RequestType)
	 * @param modelCommunicationPreference (ModelCommunicationPreference)
	 * @return a CommunicationPreferencesRequest
	 */
	private ComunicationPreferencesRequest getCommPrefRequest(RequestType type, ModelCommunicationPreference modelCommunicationPreference) {
		
		ComunicationPreferencesRequest request = new ComunicationPreferencesRequest();
		
		CommunicationPreferences communicationPreferences = new CommunicationPreferences();
		
		// Add all the communication preferences' data to the request
		communicationPreferences.setDomain(modelCommunicationPreference.getDomain());
		communicationPreferences.setCommunicationGroupeType(modelCommunicationPreference.getComGroupType());
		communicationPreferences.setCommunicationType(modelCommunicationPreference.getComType());
		communicationPreferences.setOptIn(modelCommunicationPreference.getSubscribe());
		communicationPreferences.setSubscriptionChannel(modelCommunicationPreference.getChannel());
		
		request.setCommunicationPreferences(communicationPreferences);
		
		return request;
		
	}
	
	/**
	 * add more parameters in the request to the WS
	 * @param request
	 */
	private void addSpecificParameters(CreateUpdateIndividualRequest request, ModelCommunicationPreference modelCommunicationPreference) {

		ComunicationPreferencesRequest req = request.getComunicationPreferencesRequest().get(0);
		
		CommunicationPreferences commPref = req.getCommunicationPreferences();

		if ("S".equals(commPref.getDomain())) {
			List<MarketLanguage> languages = commPref.getMarketLanguage();

			// Add the market languages
			for (ModelMarketLanguage marketLanguage: modelCommunicationPreference.getMarketLanguage()) {
				MarketLanguage ml = new MarketLanguage();
				ml.setMarket(marketLanguage.getMarket());
				ml.setLanguage(LanguageCodesEnum.fromValue(marketLanguage.getLanguage()));
				ml.setOptIn(commPref.getOptIn());
				ml.setDateOfConsent(marketLanguage.getDateOfConsent());
				languages.add(ml);
			}
		}
		
	}

}
