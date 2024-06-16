package com.afklm.repind.msv.inferred.controller.ut;

import com.afklm.repind.msv.inferred.controller.InferredDataController;
import com.afklm.repind.msv.inferred.entity.Individual;
import com.afklm.repind.msv.inferred.model.CreateInferredDataModel;
import com.afklm.repind.msv.inferred.model.InferredDataModel;
import com.afklm.repind.msv.inferred.model.error.BusinessErrorList;
import com.afklm.repind.msv.inferred.repository.IndividualRepository;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
import com.afklm.repind.msv.inferred.repository.MoralPersonRepository;
import com.afklm.repind.msv.inferred.repository.RefInfrdKeyTypeRepository;
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
class CreateInferredDataControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    InferredDataController inferredDataController;
    
    @MockBean
    InferredRepository inferredRepository;
    
    @MockBean
    RefInfrdKeyTypeRepository refInfrdKeyTypeRepository;
        
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
        defaultKeys.add("EMAIL");
    }
    
    @Test
    void testCreateInferredData() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Test", "email", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);

		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
    }
    
    @Test
    void testCreateInferredDataWithUpperCaseKey() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Test", "EMAIL", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);

		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
    }
    
    @Test
    void testCreateInferredDataWithWrongGin() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Test", "email", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
		
		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.empty());
		when(moralPersonRepository.findMoralPersonNotDeleted(gin)).thenReturn(Optional.empty());
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError().getName())));
        
    }

    @Test
    void testCreateInferredDataWithoutGin() throws Exception {

    	String type = "EML";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(null, type, "Test", "email", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_GIN_IS_MISSING.getError().getName())));
        
    }
    @Test
    void testCreateWithTypeNotAllowed() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel inferredModel = createInferredDataToSave(gin, type, "Test", "email", "test");
    	
		String params = mapper.writeValueAsString(inferredModel);

		List<String> keys = new ArrayList<String>();

		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(keys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_NOT_ALLOWED.getError().getName())));
        
    }

    @Test
    void testCreateWithKeyNotAllowed() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Test", "emai", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);

		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_KEY.getError().getName())));
        
    }

    @Test
    void testCreateWithValueBlank() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Test", "email", " ");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
		
		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_INFERRED_DATA.getError().getName())));
        
    }

    @Test
    void testCreateWithApplicationTooLong() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "ApplicationNameTooLong", "email", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_APPLICATION.getError().getName())));
        
    }

    @Test
    void testCreateWithoutApplication() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, null, "email", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_APPLICATION_IS_MISSING.getError().getName())));
        
    }

    @Test
    void testCreateWithoutType() throws Exception {

    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, null, "Application", "email", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_IS_MISSING.getError().getName())));
        
    }

    @Test
    void testCreateWithTypeTooLong() throws Exception {

    	String type = "EMLL";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Application", "email", "test");
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_TYPE.getError().getName())));
        
    }

    @Test
    void testCreateWithTwoSameKeysForInferredData() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
    	String key = "email";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Application", key, "test");

        
        InferredDataModel inferredDataModel = new InferredDataModel();
        inferredDataModel.setKey(key);
        inferredDataModel.setValue("test2");
        
        createInferredDataModel.getData().add(inferredDataModel);
        
		String params = mapper.writeValueAsString(createInferredDataModel);

		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_PRESENT_MORE_THAN_ONCE.getError().getName())));
        
    }

    @Test
    void testCreateWithoutInferredData() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Application", "email", "test");
    	
        createInferredDataModel.setData(null);
        
		String params = mapper.writeValueAsString(createInferredDataModel);

		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_DATA_ARE_MISSING.getError().getName())));
        
    }

    @Test
    void testCreateWithEmptyInferredData() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
        
        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, "Application", "email", "test");
    	
        createInferredDataModel.setData(new ArrayList<InferredDataModel>());
        
		String params = mapper.writeValueAsString(createInferredDataModel);

		when(refInfrdKeyTypeRepository.findByType(type)).thenReturn(defaultKeys);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_DATA_NOT_FOUND.getError().getName())));
        
    }
    CreateInferredDataModel createInferredDataToSave(String gin, String type, String application, String key, String value) {

    	
        CreateInferredDataModel createInferredDataModel = new CreateInferredDataModel();
        createInferredDataModel.setGin(gin);
        createInferredDataModel.setType(type);
        createInferredDataModel.setApplication(application);
        
        List<InferredDataModel> listInferredDataModel = new ArrayList<InferredDataModel>();
        
        InferredDataModel inferredDataModel = new InferredDataModel();
        inferredDataModel.setKey(key);
        inferredDataModel.setValue(value);
        
        listInferredDataModel.add(inferredDataModel);
        
        createInferredDataModel.setData(listInferredDataModel);
        
        return createInferredDataModel;
    }
    
}
