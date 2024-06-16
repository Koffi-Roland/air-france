package com.afklm.repind.msv.consent.controller.it;

import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.entity.ConsentData;
import com.afklm.repind.msv.consent.repository.ConsentRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProvideConsentControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    ConsentRepository consentRepository;
    
    private List<Consent> listConsent;
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listConsent = new ArrayList<>();
        
        purgeTestData("400622632011");
        
		// listConsent.add(initTestData("400476875686", "DATA_PROCESSING", "GLOBAL", "Y", new Date()));
		initTestData("400622632011", "AF_PERSONALIZED_SERVICING", "DEFAULT", "Y", new Date());
    }
    
    @Test
    void testProvideConsentDataByGin() throws Exception {
    	
		mockMvc.perform(get("/400622632011").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.consent", hasSize(1)))
			.andExpect(jsonPath("$.gin", is("400622632011")))
			.andExpect(jsonPath("$.consent[0].type", is("AF_PERSONALIZED_SERVICING")))
			.andExpect(jsonPath("$.consent[0].data[0].type", is("DEFAULT")))
			.andExpect(jsonPath("$.consent[0].data[0].isConsent", is("Y")));
        
    }
    
    @Test
    void testProvideWithNotFound() throws Exception {
    	
		mockMvc.perform(get("/510000040101").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
    }
    
    @Test
    void testWithWrongGin() throws Exception {
    	
		mockMvc.perform(get("/11000000101").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed());
        
    }

    void purgeTestData(String gin) {
        
        List<Consent> listConsent = consentRepository.findByGin(gin);
        
        for (Consent consent : listConsent) {
        	consentRepository.delete(consent);
		}
        
    }

    Consent initTestData(String gin, String type, String typeConsentData, String isConsent, Date dateConsent) {
    	
    	Date now = new Date();
    	
        Consent consent = new Consent();
        consent.setGin(gin);
        consent.setType(type);
        consent.setSignatureCreation("Test");
        consent.setSignatureModification("Test");
        consent.setSiteCreation("Test");
        consent.setSiteModification("Test");
        consent.setDateCreation(now);
        consent.setDateModification(now);
        
        List<ConsentData> listConsentData = new ArrayList<ConsentData>();
        
        ConsentData consentData = new ConsentData();
        consentData.setType(typeConsentData);
        consentData.setIsConsent(isConsent);
        consentData.setDateConsent(dateConsent);
        consentData.setConsent(consent);
        
        listConsentData.add(consentData);
        
        consent.setConsentData(listConsentData);
        
        consentRepository.saveAndFlush(consent);
        
        return consent;
        
    }
    
}
