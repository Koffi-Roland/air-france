package com.afklm.repind.msv.consent.controller.ut;

import com.afklm.repind.msv.consent.controller.ConsentController;
import com.afklm.repind.msv.consent.entity.Individual;
import com.afklm.repind.msv.consent.model.CreateConsentDataModel;
import com.afklm.repind.msv.consent.model.CreateConsentModel;
import com.afklm.repind.msv.consent.model.error.BusinessErrorList;
import com.afklm.repind.msv.consent.repository.ConsentRepository;
import com.afklm.repind.msv.consent.repository.IndividualRepository;
import com.afklm.repind.msv.consent.repository.MoralPersonRepository;
import com.afklm.repind.msv.consent.repository.RefConsentTypeDataTypeRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
class CreateConsentControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    ConsentController consentDataController;
    
    @MockBean
    ConsentRepository consentRepository;
    
    @MockBean
    RefConsentTypeDataTypeRepository refConsentTypeDataTypeRepository;
        
    @MockBean
    IndividualRepository individualRepository;
    
    @MockBean
    MoralPersonRepository moralPersonRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	List<String> defaultKeys;
        
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        defaultKeys = new ArrayList<String>();
        defaultKeys.add("GLOBAL");
    }
    
    @Test
    void testCreateConsentData() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Test", "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);

		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
    }
    
    @Test
    void testCreateConsentDataWithUpperCaseKey() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Test", "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);

		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
    }
    
    @Test
    void testCreateConsentDataWithWrongGin() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Test", "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);
		
		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.empty());
		when(moralPersonRepository.findMoralPersonNotDeleted(gin)).thenReturn(Optional.empty());
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError().getName())));
        
    }

    @Test
    void testCreateConsentDataWithoutGin() throws Exception {

    	String type = "DATA_PROCESSING";
        
        CreateConsentModel consentModel = createConsentDataToSave(null, type, "Test", "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_GIN_IS_MISSING.getError().getName())));
        
    }
    @Test
    void testCreateWithTypeConsentDataNotAllowed() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Test", "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);

		List<String> keys = new ArrayList<String>();

		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(keys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_NOT_ALLOWED.getError().getName())));
        
    }

    @Test
    void testCreateWithTypeNotAllowed() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Test", "Test", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);

		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_CONSENT_DATA_NOT_ALLOWED.getError().getName())));
        
    }

    @Test
    void testCreateWithIsConsentBlank() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Test", "GLOBAL", " ", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);
		
		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_CONSENT_DATA.getError().getName())));
        
    }

    @Test
    void testCreateWithApplicationTooLong() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "ApplicationNameTooLong", "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_APPLICATION.getError().getName())));
        
    }

    @Test
    void testCreateWithoutApplication() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, null, "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_APPLICATION_IS_MISSING.getError().getName())));
        
    }

    @Test
    void testCreateWithoutType() throws Exception {

    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, null, "Application", "GLOBAL", "Y", new Date());
    	
		String params = mapper.writeValueAsString(consentModel);
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_IS_MISSING.getError().getName())));
        
    }

    @Test
    void testCreateWithTwoSameKeysForConsentData() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Application", "GLOBAL", "Y", new Date());

        
        CreateConsentDataModel consentDataModel = new CreateConsentDataModel();
        consentDataModel.setType("GLOBAL");
        consentDataModel.setIsConsent("N");
        consentDataModel.setDateConsent(new Date());
        
        consentModel.getData().add(consentDataModel);
        
		String params = mapper.writeValueAsString(consentModel);

		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_PRESENT_MORE_THAN_ONCE.getError().getName())));
        
    }

    @Test
    void testCreateWithoutConsentData() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Application", "GLOBAL", "Y", new Date());
    	
        consentModel.setData(null);
        
		String params = mapper.writeValueAsString(consentModel);

		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_DATA_ARE_MISSING.getError().getName())));
        
    }

    @Test
    void testCreateWithEmptyConsentData() throws Exception {

    	String type = "DATA_PROCESSING";
    	String gin = "123456678784";
        
        CreateConsentModel consentModel = createConsentDataToSave(gin, type, "Application", "GLOBAL", "Y", new Date());
    	
        consentModel.setData(new ArrayList<CreateConsentDataModel>());
        
		String params = mapper.writeValueAsString(consentModel);

		when(refConsentTypeDataTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
		mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_DATA_NOT_FOUND.getError().getName())));
        
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
