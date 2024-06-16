package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.model.individual.ModelTelecom;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.TelecomRequestBuilder;
import com.afklm.rigui.services.helper.resources.TelecomHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.adresse.TelecomsRepository;
import com.afklm.rigui.entity.adresse.Telecoms;

@Service
public class TelecomService {
	
	@Autowired
	private TelecomsRepository telecomsRepository;
	
	@Autowired
	private TelecomRequestBuilder requestBuilder;
	
	@Autowired
	private TelecomHelper telecomHelper;
	
	@Autowired
	private CreateOrUpdateIndividualService createOrUpdateIndividualService;
	
	/**
	 * This method CREATE a new telecom.
	 */
	public boolean createTelecom(ModelTelecom telecom, String id) throws SystemException, ServiceException {
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildCreateRequest(id, telecom));
		
	}

	/**
	 * This method GET all the telecoms according to the GIN in parameter.
	 * @param gin
	 * @return a list of ModelTelecom
	 * @throws ServiceException
	 */
	public List<ModelTelecom> getAll(String gin) throws ServiceException {
		
		List<ModelTelecom> telecoms = new ArrayList<>();
		
		// Fetch all the telecom entities using the telecom repository
		List<Telecoms> telecomsList = telecomsRepository.findBySgin(gin);
		// Loop through all the entities
		for (Telecoms telecom : telecomsList) {
			// Convert the entity into a model and add it to the list of models
			telecoms.add(telecomHelper.buildTelecomModel(telecom));
		}
		
		// Return the sorted list of telecom models
		return telecomHelper.sort(telecoms);
		
	}
	
	/**
	 * This method UPDATE a telecom.
	 */
	public boolean update(ModelTelecom telecom, String id) throws ServiceException, SystemException {
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildUpdateRequest(id, telecom));
		
	}
	
	/**
	 * This method DELETE a telecom.
	 */
	public boolean delete(String gin, String id) throws SystemException, ServiceException {
		
		// Fetch the telecom to delete using the telecom repository and its ID
		Telecoms telecom = telecomsRepository.findBySain(id);
		
		// Convert the entity into a model
		ModelTelecom modelTelecom = telecomHelper.buildTelecomModel(telecom);
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildDeleteRequest(gin, modelTelecom));
		
	}

}
