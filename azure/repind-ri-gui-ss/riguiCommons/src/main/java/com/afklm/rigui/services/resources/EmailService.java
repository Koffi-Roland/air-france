package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.model.individual.ModelEmail;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.EmailRequestBuilder;
import com.afklm.rigui.services.helper.resources.EmailHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.adresse.EmailRepository;
import com.afklm.rigui.entity.adresse.Email;

@Service
public class EmailService {
	
	@Autowired
	private EmailRequestBuilder requestBuilder;
	
	@Autowired
	private EmailHelper emailHelper;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private CreateOrUpdateIndividualService createOrUpdateIndividualService;
	
	/**
	 * This method CREATE a new email
	 */
	public boolean create(ModelEmail email, String id) throws ServiceException, SystemException {
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildCreateRequest(id, email));

	}

	/**
	 * This method GET all the mails according to the GIN in parameter
	 * @param gin
	 * @return a list of ModelEmail
	 * @throws ServiceException
	 */
    public List<ModelEmail> getAll(String gin) throws ServiceException {
    	
		List<Email> emailsTmp = emailRepository.findBySgin(gin);
    	
    	List<ModelEmail> modelEmails = new ArrayList<>();
		for (Email emailTemp : emailsTmp) {
    		modelEmails.add(emailHelper.buildEmailModel(emailTemp));
    	}
    	
    	return emailHelper.sortEmails(modelEmails);

    }

    /**
     * This method UPDATE an email
     */
	public boolean update(ModelEmail email, String id) throws SystemException, ServiceException {
		
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildUpdateRequest(id, email));
	}

	/**
	 * This method DELETE an email
	 */
	public boolean delete(String gin, String id) throws ServiceException, SystemException {
		
		Email email = emailRepository.findBySain(id);
		
		ModelEmail modelEmail = emailHelper.buildEmailModel(email);

		return createOrUpdateIndividualService.callWebService(requestBuilder.buildDeleteRequest(gin, modelEmail));
		
	}

}
