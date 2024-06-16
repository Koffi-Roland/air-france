package com.afklm.repind.msv.consent.controller.it;

import com.afklm.repind.msv.consent.criteria.ConsentCriteria;
import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.entity.ConsentData;
import com.afklm.repind.msv.consent.model.CreateConsentDataModel;
import com.afklm.repind.msv.consent.model.CreateConsentModel;
import com.afklm.repind.msv.consent.model.error.BusinessErrorList;
import com.afklm.repind.msv.consent.repository.ConsentDataRepository;
import com.afklm.repind.msv.consent.repository.ConsentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CreateConsentControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    ConsentRepository consentRepository;
    
    @Autowired
    ConsentDataRepository consentDataRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
        
    @BeforeEach
    void setup() {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        purgeTestData("400622632011");
        purgeTestData("400272036090");
        
    }
    
    @Test
    void testCreateConsentData() throws Exception {
    	
    	String gin = "400622632011";
    	String type = "AF_PERSONALIZED_SERVICING";
    	String application = "TEST";
    	String typeConsentData = "DEFAULT";
    	String isConsent = "Y";
    	Date dateConsent = new Date();

        CreateConsentModel consentModel = createConsentDataToSave(gin, type, application, typeConsentData, isConsent, dateConsent);
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
        checkConsentDataCreated(gin, type, application, typeConsentData, isConsent, dateConsent);
        
    }
    
    @Test
	void testCreateDefaultConsentData() throws Exception {

		String gin = "400373314201";
		String application = "TEST";

		ConsentCriteria consentModel = new ConsentCriteria();
		consentModel.setGin(gin);
		consentModel.setApplication(application);

		String params = mapper.writeValueAsString(consentModel);

		mockMvc.perform(post("/default/").contentType(MediaType.APPLICATION_JSON).content(params))
				.andExpect(status().isOk()).andExpect(jsonPath("$.gin", is(gin)));
	}

	@Test
    void testCreateConsentDataProcessing_Global() throws Exception {
    	
    	String gin = "400622632011";
    	String type = "AF_PERSONALIZED_SERVICING";
    	String application = "TEST";
    	String typeConsentData = "DEFAULT";
    	String isConsent = "Y";
    	Date dateConsent = new Date();

        CreateConsentModel consentModel = createConsentDataToSave(gin, type, application, typeConsentData, isConsent, dateConsent);
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));

        checkConsentDataCreated(gin, type, application, typeConsentData, isConsent, dateConsent);
        
    }
    
    @Test
    void testCreateConsentWithWrongTypeConsentData() throws Exception {
    	
    	String gin = "400622632011";
		String type = "AF_PERSONALIZED_SERVICING";
    	String application = "TEST";
		String typeConsentData = "WRONG_TYPE";
    	String isConsent = "Y";
    	Date dateConsent = new Date();

        CreateConsentModel consentModel = createConsentDataToSave(gin, type, application, typeConsentData, isConsent, dateConsent);
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_CONSENT_DATA_NOT_ALLOWED.getError().getName())));
        
        checkConsentDataNotCreated(gin);
        
    }
    
    @Test
    void testCreateConsentWithWrongType() throws Exception {
    	
    	String gin = "400622632011";
    	String type = "FALSE";
    	String application = "TEST";
    	String typeConsentData = "DEFAULT";
    	String isConsent = "Y";
    	Date dateConsent = new Date();

        CreateConsentModel consentModel = createConsentDataToSave(gin, type, application, typeConsentData, isConsent, dateConsent);
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_NOT_ALLOWED.getError().getName())));
        
        checkConsentDataNotCreated(gin);
        
    }

    
    @Test
    void testCreateConsentWithFalseGin() throws Exception {
    	
    	String gin = "011112036090";
    	String type = "AF_PERSONALIZED_SERVICING";
    	String application = "TEST";
    	String typeConsentData = "DEFAULT";
    	String isConsent = "Y";
    	Date dateConsent = new Date();

        CreateConsentModel consentModel = createConsentDataToSave(gin, type, application, typeConsentData, isConsent, dateConsent);
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError().getName())));
        
        checkConsentDataNotCreated(gin);
        
    }

    void purgeTestData(String gin) {
        
        List<Consent> listConsent = consentRepository.findByGin(gin);
        
        for (Consent consent : listConsent) {
        	consentRepository.delete(consent);
		}
        
    }
    
    void checkConsentDataNotCreated(String gin) {

    	List<Consent> listConsent = consentRepository.findByGin(gin);
    	assertEquals(0, listConsent.size());
    	
    }
    
    void checkConsentDataCreated(String gin, String type, String application, String typeConsentData, String isConsent, Date dateConsent) {

    	List<Consent> listConsent = consentRepository.findByGin(gin);
    	assertEquals(1, listConsent.size());
    	
    	Consent consent = listConsent.get(0);
    	assertEquals(gin, consent.getGin());
    	assertEquals(type, consent.getType());
    	assertEquals(application, consent.getSignatureCreation());
    	assertEquals(application, consent.getSignatureModification());
    	assertEquals("REPINDMSV", consent.getSiteCreation());
    	assertEquals("REPINDMSV", consent.getSiteModification());

    	List<ConsentData> listConsentData = consentDataRepository.findByConsentConsentId(consent.getConsentId());
    	assertEquals(1, listConsentData.size());
    	ConsentData consentData = listConsentData.get(0);

    	assertEquals(typeConsentData, consentData.getType());
    	assertEquals(isConsent, consentData.getIsConsent());
    	assertEquals(application, consentData.getSignatureCreation());
    	assertEquals(application, consentData.getSignatureModification());
    	assertEquals("REPINDMSV", consentData.getSiteCreation());
    	assertEquals("REPINDMSV", consentData.getSiteModification());
    	
    }
    
    CreateConsentModel createConsentDataToSave(String gin, String type, String application, String typeConsentData, String isConsent, Date dateConsent) {

    	
        CreateConsentModel consentModel = new CreateConsentModel();
        consentModel.setGin(gin);
        consentModel.setType(type);
        consentModel.setApplication(application);
        
        List<CreateConsentDataModel> listConsentDataModel = new ArrayList<CreateConsentDataModel>();
        
        CreateConsentDataModel consentDataModel = new CreateConsentDataModel();
        consentDataModel.setType(typeConsentData);
        consentDataModel.setIsConsent(isConsent);
        consentDataModel.setDateConsent(dateConsent);
        
        listConsentDataModel.add(consentDataModel);
        
        consentModel.setData(listConsentDataModel);
        
        return consentModel;
    }
    
    
}
