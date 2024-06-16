package com.afklm.repind.msv.consent.controller.it;

import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.entity.ConsentData;
import com.afklm.repind.msv.consent.model.UpdateConsentModel;
import com.afklm.repind.msv.consent.model.error.BusinessErrorList;
import com.afklm.repind.msv.consent.repository.ConsentDataRepository;
import com.afklm.repind.msv.consent.repository.ConsentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateConsentControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    ConsentRepository consentRepository;
    
    @Autowired
    ConsentDataRepository consentDataRepository;
	
	private ObjectMapper mapper = new ObjectMapper();

    
    private Long consentIdByDefault;
	private String typeByDefault = "AF_PERSONALIZED_SERVICING";
	private String typeConsentDataByDefault = "DEFAULT";
	private String consentByDefault = "Y";
	private String ginByDefault = "400622632011";
	private String applicationByDefault = "TEST";
	private String siteByDefault = "REPINDMSV";
	
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        purgeTestData(ginByDefault);
        consentIdByDefault = initTestData(ginByDefault, typeByDefault, typeConsentDataByDefault, consentByDefault, new Date())
        		.getConsentData()
        		.get(0)
        		.getConsentDataId();
        
    }
    
    @Test
    void testUpdateConsentData() throws Exception {
    	
    	String application = "TEST";
    	
		UpdateConsentModel consentModel = createConsentDataToSave(consentIdByDefault, ginByDefault, application, "N");
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON).content(params))
				.andExpect(status().isOk()).andExpect(jsonPath("$.gin", is(ginByDefault)));
        
        checkConsentDataUpdated(consentIdByDefault, "N", application);
        
    }

	@ParameterizedTest
	@ValueSource(strings = {"TOOMANYCHARINTHIS", "", "@-/"})
	void testUpdateConsentFromYtoNData() throws Exception {

		String application = "TEST";

		UpdateConsentModel consentModel = createConsentDataToSave(consentIdByDefault, ginByDefault, application, "N");

		String params = mapper.writeValueAsString(consentModel);

		mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON).content(params)).andExpect(status().isOk())
				.andExpect(jsonPath("$.gin", is(ginByDefault)));

		checkConsentDataAndDateUpdated(consentIdByDefault, "N", application, consentModel.getDateConsent());

	}

	@Test
	void testUpdateConsentFromYtoPData() throws Exception {

		String application = "TEST";

		UpdateConsentModel consentModel = createConsentDataToSave(consentIdByDefault, ginByDefault, application, "P");

		String params = mapper.writeValueAsString(consentModel);

		mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON).content(params))
				.andExpect(status().isPreconditionFailed());

	}

	@Test
	void testUpdateConsentFromYtoYData() throws Exception {

		String application = "TEST";

		UpdateConsentModel consentModel = createConsentDataToSave(consentIdByDefault, ginByDefault, application, "Y");

		String params = mapper.writeValueAsString(consentModel);

		mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON).content(params))
				.andExpect(status().isPreconditionFailed());

	}

	@Test
	void testUpdateConsentFromNtoYData() throws Exception {

		String application = "TEST";

		createDataWithSpecificData(ginByDefault, typeByDefault, typeConsentDataByDefault, "N");
		UpdateConsentModel consentModel = createConsentDataToSave(consentIdByDefault, ginByDefault, application, "Y");

		String params = mapper.writeValueAsString(consentModel);

		mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON).content(params)).andExpect(status().isOk())
				.andExpect(jsonPath("$.gin", is(ginByDefault)));

		checkConsentDataAndDateUpdated(consentIdByDefault, "Y", application, consentModel.getDateConsent());

	}

	@ParameterizedTest
	@CsvSource({"N,P", "N,N", "P,T", "P,P"})
	void testUpdateConsentFromNtoPData(String initConsent, String consentAfterUpdate) throws Exception {

		String application = "TEST";

		createDataWithSpecificData(ginByDefault, typeByDefault, typeConsentDataByDefault, initConsent);
		UpdateConsentModel consentModel = createConsentDataToSave(consentIdByDefault, ginByDefault, application, consentAfterUpdate);

		String params = mapper.writeValueAsString(consentModel);

		mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON).content(params))
				.andExpect(status().isPreconditionFailed());

	}

	@Test
    void testUpdateConsentWithWrongConsent() throws Exception {
    	
    	String application = "TEST";

    	UpdateConsentModel consentModel = createConsentDataToSave(consentIdByDefault, ginByDefault, application, "P");
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CONSENT_NOT_ALLOWED.getError().getName())));

        checkConsentDataUpdated(consentIdByDefault, consentByDefault, applicationByDefault);
        
    }
    
    @Test
    void testUpdateConsentWithWrongId() throws Exception {
    	
    	String application = "TEST";

    	UpdateConsentModel consentModel = createConsentDataToSave(230L, ginByDefault, application, "Y");
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CONSENT_NOT_FOUND.getError().getName())));
        
        checkConsentDataUpdated(consentIdByDefault, consentByDefault, applicationByDefault);
        
    }

    void purgeTestData(String gin) {
        
        List<Consent> listConsent = consentRepository.findByGin(gin);
        
        for (Consent consent : listConsent) {
        	consentRepository.delete(consent);
		}
        
    }
    
    void checkConsentDataUpdated(Long id, String consent, String application) {

    	List<ConsentData> listConsentData = consentDataRepository.findByConsentDataId(id);

    	ConsentData consentData = listConsentData.get(0);
    	assertEquals(consent, consentData.getIsConsent());
    	assertEquals(application, consentData.getSignatureModification());
    	assertEquals(siteByDefault, consentData.getSiteModification());
    	
    }
    
	void checkConsentDataAndDateUpdated(Long id, String consent, String application, Date dateConsent) {

		List<ConsentData> listConsentData = consentDataRepository.findByConsentDataId(id);

		ConsentData consentData = listConsentData.get(0);
		checkConsentDataUpdated(id, consent, application);
		assertNotEquals(consentData.getDateConsent(), dateConsent);

	}

	void createDataWithSpecificData(String gin, String type, String typeConsentData, String consent) {
		purgeTestData(ginByDefault);
		consentIdByDefault = initTestData(gin, type, typeConsentData, consent, new Date()).getConsentData().get(0)
				.getConsentDataId();
	}

    UpdateConsentModel createConsentDataToSave(Long id, String gin, String application, String isConsent) {
    	
    	UpdateConsentModel consentModel = new UpdateConsentModel();
        consentModel.setId(id);
        consentModel.setGin(gin);
        consentModel.setIsConsent(isConsent);
        consentModel.setApplication(application);
        
        return consentModel;
        
    }

    Consent initTestData(String gin, String type, String typeConsentData, String isConsent, Date dateConsent) {
    	
    	Date now = new Date();
    	
        Consent consent = new Consent();
        consent.setGin(gin);
        consent.setType(type);
        consent.setSignatureCreation(applicationByDefault);
        consent.setSignatureModification(applicationByDefault);
        consent.setSiteCreation(siteByDefault);
        consent.setSiteModification(siteByDefault);
        consent.setDateCreation(now);
        consent.setDateModification(now);
        
        List<ConsentData> listConsentData = new ArrayList<ConsentData>();
        
        ConsentData consentData = new ConsentData();
        consentData.setType(typeConsentData);
        consentData.setIsConsent(isConsent);
        consentData.setDateConsent(dateConsent);
        consentData.setSignatureCreation(applicationByDefault);
        consentData.setSignatureModification(applicationByDefault);
        consentData.setSiteCreation(siteByDefault);
        consentData.setSiteModification(siteByDefault);
        consentData.setConsent(consent);
        
        listConsentData.add(consentData);
        
        consent.setConsentData(listConsentData);
        
        consentRepository.saveAndFlush(consent);
        
        return consent;
        
    }
    
    
}
