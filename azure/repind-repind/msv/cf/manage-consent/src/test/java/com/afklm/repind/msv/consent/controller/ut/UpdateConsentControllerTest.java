package com.afklm.repind.msv.consent.controller.ut;

import com.afklm.repind.msv.consent.controller.ConsentController;
import com.afklm.repind.msv.consent.entity.Consent;
import com.afklm.repind.msv.consent.entity.ConsentData;
import com.afklm.repind.msv.consent.entity.Individual;
import com.afklm.repind.msv.consent.model.UpdateConsentModel;
import com.afklm.repind.msv.consent.model.error.BusinessErrorList;
import com.afklm.repind.msv.consent.repository.ConsentDataRepository;
import com.afklm.repind.msv.consent.repository.ConsentRepository;
import com.afklm.repind.msv.consent.repository.IndividualRepository;
import com.afklm.repind.msv.consent.repository.MoralPersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
class UpdateConsentControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    ConsentController consentDataController;
    
    @MockBean
    ConsentRepository consentRepository;
    
    @MockBean
    ConsentDataRepository consentDataRepository;
    
    @MockBean
    IndividualRepository individualRepository;
    
    @MockBean
    MoralPersonRepository moralPersonRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	List<String> defaultStatus;
        
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        defaultStatus = new ArrayList<String>();
        defaultStatus.add("C");
    }
    
    @Test
    void testUpdateConsentData() throws Exception {

    	String type = "DATA_PROCESSING";
    	String typeConsentData = "GLOBAL";
    	String gin = "123456678784";
    	String isConsent = "Y";
    	Long id = 230L;
        
    	UpdateConsentModel consentModel = createConsentDataToSave(id, gin, "N", "Test");
        
        Consent consentToUpdate = createConsentToUpdate(gin, type);
        
        ConsentData consentDataToUpdate = createConsentDataToUpdate(id, typeConsentData, isConsent, new Date());
    	
		String params = mapper.writeValueAsString(consentModel);

		when(consentRepository.findByConsentDataConsentDataId(id)).thenReturn(consentToUpdate);
		when(consentDataRepository.findById(id)).thenReturn(Optional.of(consentDataToUpdate));
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
    }
    
    @Test
    void testUpdateConsentDataWithWrongId() throws Exception {

    	Long id = 230L;
    	String gin = "123456678784";

    	UpdateConsentModel consentModel = createConsentDataToSave(id, gin, "N", "Test");
    	
		String params = mapper.writeValueAsString(consentModel);

		when(consentRepository.findById(id)).thenReturn(Optional.empty());
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CONSENT_NOT_FOUND.getError().getName())));
        
    }
    
    @Test
    void testUpdateWithConsentNotAllowed() throws Exception {

    	String type = "DATA_PROCESSING";
    	String typeConsentData = "GLOBAL";
    	String gin = "123456678784";
    	String isConsent = "G";
    	Long id = 230L;

    	UpdateConsentModel consentModel = createConsentDataToSave(id, gin, isConsent, "Test");
        
        Consent consentToUpdate = createConsentToUpdate(gin, type);
        
        ConsentData consentDataToUpdate = createConsentDataToUpdate(id, typeConsentData, "N", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);

		when(consentRepository.findByConsentDataConsentDataId(id)).thenReturn(consentToUpdate);
		when(consentDataRepository.findById(id)).thenReturn(Optional.of(consentDataToUpdate));
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CONSENT_NOT_ALLOWED.getError().getName())));
        
    }

    @Test
    void testUpdateWithApplicationTooLong() throws Exception {

    	String gin = "123456678784";
    	Long id = 230L;

    	UpdateConsentModel consentModel = createConsentDataToSave(id, gin, "N", "ApplicationNameTooLong");
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_APPLICATION.getError().getName())));
        
    }

    @Test
    void testUpdateWithoutApplication() throws Exception {

    	String gin = "123456678784";
    	Long id = 230L;

    	UpdateConsentModel consentModel = createConsentDataToSave(id, gin, "N", null);
    	
		String params = mapper.writeValueAsString(consentModel);
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_APPLICATION_IS_MISSING.getError().getName())));
        
    }

    @Test
    void testUpdateWithoutConsent() throws Exception {

    	String gin = "123456678784";
    	Long id = 230L;
        
    	UpdateConsentModel consentModel = createConsentDataToSave(id, gin, null, "Test");
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CONSENT_IS_MISSING.getError().getName())));
        
    }

    @Test
    void testUpdateWithConsentTooLong() throws Exception {

    	String gin = "123456678784";
    	Long id = 230L;
        
    	UpdateConsentModel consentModel = createConsentDataToSave(id, gin, "Yes", "Test");
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_CONSENT.getError().getName())));
        
    }
    
    UpdateConsentModel createConsentDataToSave(Long id, String gin, String consent, String application) {

    	
    	UpdateConsentModel consentModel = new UpdateConsentModel();
        consentModel.setId(id);
        consentModel.setGin(gin);
        consentModel.setIsConsent(consent);
        consentModel.setApplication(application);
        
        return consentModel;
    }

    Consent createConsentToUpdate(String gin, String type) {
    	
        Consent consent = new Consent();
        consent.setGin(gin);
        consent.setType(type);
        
        return consent;
        
    }

    ConsentData createConsentDataToUpdate(Long id, String typeConsentData, String isConsent, Date dateConsent) {
    	
        ConsentData consentData = new ConsentData();
        consentData.setType(typeConsentData);
        consentData.setIsConsent(isConsent);
        consentData.setDateConsent(dateConsent);
        consentData.setSignatureCreation("Test");
        consentData.setSignatureModification("Test");
        consentData.setSiteCreation("Test");
        consentData.setSiteModification("Test");
        
        return consentData;
        
    }
}
