package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.model.individual.ModelCommunicationPreference;
import com.afklm.rigui.model.individual.ModelMarketLanguage;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.CommPrefRequestBuilder;
import com.afklm.rigui.services.helper.resources.CommPrefHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.individu.CommunicationPreferencesRepository;
import com.afklm.rigui.dao.individu.MarketLanguageRepository;
import com.afklm.rigui.entity.individu.CommunicationPreferences;
import com.afklm.rigui.entity.individu.MarketLanguage;

@Service
public class CommunicationPreferenceService {
	
	@Autowired
	private CommunicationPreferencesRepository commPrefRepository;
	
	@Autowired
	public MarketLanguageRepository marketLanguageRepository;
	
	@Autowired
	private CommPrefHelper commPrefHelper;
	
	@Autowired
	private CommPrefRequestBuilder requestBuilder;
	
	@Autowired
	private CreateOrUpdateIndividualService createOrUpdateIndividualService;
	
	/**
	 * This method GET all the communication preferences of an individual according to the GIN in parameter.
	 * @param gin
	 * @return a list of ModelCommunicationPreference
	 * @throws ServiceException
	 */
	public List<ModelCommunicationPreference> getAll(String gin) throws ServiceException {
		
		// Fetch all the communication preferences of the individual using the repository
		List<CommunicationPreferences> communicationPreferences = commPrefRepository.findByGin(gin);
		
		List<ModelCommunicationPreference> modelCommunicationPreferences = new ArrayList<>();
		for (CommunicationPreferences commPref : communicationPreferences) {
			
			ModelCommunicationPreference modelCommunicationPreference = commPrefHelper.buildCommPrefModel(commPref);
			
			// Loop through all the market languages...
			for (ModelMarketLanguage modelMarketLanguage : modelCommunicationPreference.getMarketLanguage()) {
				// Fetch information about the MarketLanguage
				MarketLanguage marketLanguage = marketLanguageRepository.findByMarketLanguageId(modelMarketLanguage.getMarketLanguageId());
				// Set the signature of the model MarketLanguage
				commPrefHelper.setMarketLanguageSignature(modelMarketLanguage, marketLanguage);
			}
			
			modelCommunicationPreferences.add(modelCommunicationPreference);
		}
		
		return modelCommunicationPreferences;

	}

	/**
	 * This method UPDATE a communication preference
	 */
	public boolean update(ModelCommunicationPreference commPref, String identifiant) throws ServiceException, SystemException {
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildUpdateRequest(identifiant, commPref));
		
	}

}
