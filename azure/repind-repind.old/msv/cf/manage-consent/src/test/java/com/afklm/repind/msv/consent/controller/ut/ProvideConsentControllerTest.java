package com.afklm.repind.msv.consent.controller.ut;

import com.afklm.repind.msv.consent.controller.ConsentController;
import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.entity.ConsentData;
import com.afklm.repind.msv.consent.repository.ConsentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
class ProvideConsentControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    ConsentController consentDataController;
    
    @MockBean
    ConsentRepository consentRepository;
    
    private List<Consent> listConsent;
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listConsent = new ArrayList<>();
        listConsent.add(initTestData("400476875686", "DATA_PROCESSING", "GLOBAL", "Y", new Date()));
        listConsent.add(initTestData("400476875686", "DATA_PROCESSING", "GLOBAL", "N", new Date()));
    }
    
    @Test
    void testProvideConsentDataByGin() throws Exception {
    	
        when(consentRepository.findByGin(any(String.class))).thenReturn(listConsent);        
		mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is("400473353290")))
			.andExpect(jsonPath("$.consent", hasSize(2)))
			.andExpect(jsonPath("$.consent[0].type", is("DATA_PROCESSING")));
        
    }
    
    @Test
    void testProvideOneConsentDataByGin() throws Exception {

    	List<Consent> newListConsent = new ArrayList<>();
    	newListConsent.add(listConsent.get(0));
    	
        when(consentRepository.findByGin(any(String.class))).thenReturn(newListConsent);        
		mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.consent", hasSize(1)))
			.andExpect(jsonPath("$.gin", is("400473353290")))
			.andExpect(jsonPath("$.consent[0].type", is("DATA_PROCESSING")))
			.andExpect(jsonPath("$.consent[0].data[0].type", is("GLOBAL")))
			.andExpect(jsonPath("$.consent[0].data[0].isConsent", is("Y")));
        
    }
    
    @Test
    void testProvideWithNotFound() throws Exception {

    	List<Consent> newListConsent = new ArrayList<>();
        when(consentRepository.findByGin(any(String.class))).thenReturn(newListConsent);        
		mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
    }

    
    @Test
    void testWithWrongGin() throws Exception {
    	
        when(consentRepository.findByGin(any(String.class))).thenReturn(listConsent);        
		mockMvc.perform(get("/40047380290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed());
        
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
        
        listConsentData.add(consentData);
        
        consent.setConsentData(listConsentData);
        
        return consent;
        
    }
    
}
