package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.rigui.model.individual.ModelPreference;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.PreferenceRequestBuilder;
import com.afklm.rigui.services.helper.resources.PreferenceHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.preference.PreferenceRepository;
import com.afklm.rigui.entity.preference.Preference;

@Service
public class PreferenceService {
	
	@Autowired
	private CreateOrUpdateIndividualService createOrUpdateIndividualService;
	
	@Autowired
	private PreferenceRepository preferencesRepository;
	
	@Autowired
	private PreferenceHelper preferenceHelper;
	
	@Autowired
	private PreferenceRequestBuilder requestBuilder;
	
	/**
	 * This method CREATE a new preference.
	 */
	public boolean create(ModelPreference preference, String id) throws ServiceException, SystemException {
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildCreateRequest(id, preference));
		
	}
	
	/**
	 * This method GET all the preferences of an individual according to its GIN given in parameter.
	 * @param gin
	 * @return a list of ModelPreference
	 * @throws ServiceException
	 */
	@Transactional
	public List<ModelPreference> getAll(String gin) throws ServiceException {
		
		List<ModelPreference> modelPreferences = new ArrayList<>();
		
		// Fetch all the preference entities according to the GIN by using the appropriate repository
		List<Preference> preferences = preferencesRepository.findByGin(gin);
		
		// Loop through all the preference entities
		for (Preference preference : preferences) {
			// Convert the entity into a ModelPreference and then add it to the list of models
			modelPreferences.add(preferenceHelper.buildPreferenceModel(preference));
		}
		
		return modelPreferences;
		
	}
	
	/**
	 * This method UPDATE a preference.
	 */
	public boolean update(ModelPreference preference, String id) throws SystemException, ServiceException {
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildUpdateRequest(id, preference));
		
	}
	
	/**
	 * This method DELETE a preference.
	 */
	@Transactional
	public boolean delete(String gin, String id) throws ServiceException, SystemException {
		
		// Fetch the preference entity according to its ID
		Preference preference = preferencesRepository.findByPreferenceId(Long.parseLong(id));
		
		// Convert the entity in a ModelPreference
		ModelPreference modelPreference = preferenceHelper.buildPreferenceModel(preference);
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildDeleteRequest(gin, modelPreference));
		
	}

}
