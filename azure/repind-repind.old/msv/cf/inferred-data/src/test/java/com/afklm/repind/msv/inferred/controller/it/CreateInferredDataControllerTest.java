package com.afklm.repind.msv.inferred.controller.it;

import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.entity.InferredData;
import com.afklm.repind.msv.inferred.model.CreateInferredDataModel;
import com.afklm.repind.msv.inferred.model.InferredDataModel;
import com.afklm.repind.msv.inferred.model.error.BusinessErrorList;
import com.afklm.repind.msv.inferred.repository.InferredDataRepository;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
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
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CreateInferredDataControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    InferredRepository inferredRepository;
    
    @Autowired
    InferredDataRepository inferredDataRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private final String statusByDefault= "C";
        
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        purgeTestData("400373314201");
        purgeTestData("400272036090");
        
    }
    
    @Test
    void testCreateInferredData() throws Exception {
    	
    	String gin = "400373314201";
    	String type = "EML";
    	String application = "TEST";
    	String key = "EMAIL";
    	String value = "testvalue";

        CreateInferredDataModel inferredModel = createInferredDataToSave(gin, type, application, key, value);
    	
		String params = mapper.writeValueAsString(inferredModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
        checkInferredDataCreated(gin, type, application, key, value);
        
    }
    
    @Test
    void testCreateInferredDataTelecom() throws Exception {
    	
    	String gin = "400373314201";
    	String type = "TLC";
    	String application = "TEST";
    	String key = "INTERNATIONAL_PHONE_NUMBER";
    	String value = "0487965687";

        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, application, key, value);
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
        checkInferredDataCreated(gin, type, application, key, value);
        
    }
    
    @Test
    void testCreateInferredWithWrongKey() throws Exception {
    	
    	String gin = "400622632011";
    	String type = "TLC";
    	String application = "TEST";
    	String key = "KEY_TEST";
    	String value = "0487965687";

        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, application, key, value);
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_KEY.getError().getName())));
        
        checkInferredDataNotCreated(gin);
        
    }
    
    @Test
    void testCreateInferredWithWrongType() throws Exception {
    	
    	String gin = "400622632011";
    	String type = "TLB";
    	String application = "TEST";
    	String key = "INTERNATIONAL_PHONE_NUMBER";
    	String value = "0487965687";

        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, application, key, value);
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_NOT_ALLOWED.getError().getName())));
        
        checkInferredDataNotCreated(gin);
        
    }

    
    @Test
    void testCreateInferredWithFalseGin() throws Exception {
    	
    	String gin = "011112036090";
    	String type = "TLC";
    	String application = "TEST";
    	String key = "INTERNATIONAL_PHONE_NUMBER";
    	String value = "0487965687";

        CreateInferredDataModel createInferredDataModel = createInferredDataToSave(gin, type, application, key, value);
    	
		String params = mapper.writeValueAsString(createInferredDataModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(params))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError().getName())));
        
        checkInferredDataNotCreated(gin);
        
    }

    void purgeTestData(String gin) {
        
        List<Inferred> listInferred = inferredRepository.findByGin(gin);
        
        for (Inferred inferred : listInferred) {
        	inferredRepository.delete(inferred);
		}
        
    }
    
    void checkInferredDataNotCreated(String gin) {

    	List<Inferred> listInferred = inferredRepository.findByGin(gin);
    	assertEquals(0, listInferred.size());
    	
    }
    
    void checkInferredDataCreated(String gin, String type, String application, String key, String value) {

    	List<Inferred> listInferred = inferredRepository.findByGin(gin);
    	assertEquals(1, listInferred.size());
    	
    	Inferred inferred = listInferred.get(0);
    	assertEquals(gin, inferred.getGin());
    	assertEquals(statusByDefault, inferred.getStatus());
    	assertEquals(type, inferred.getType());
    	assertEquals(application, inferred.getSignatureCreation());
    	assertEquals(application, inferred.getSignatureModification());
    	assertEquals("REPINDMSV", inferred.getSiteCreation());
    	assertEquals("REPINDMSV", inferred.getSiteModification());

    	List<InferredData> listInferredData = inferredDataRepository.findByInferredInferredId(inferred.getInferredId());
    	assertEquals(1, listInferredData.size());
    	InferredData inferredData = listInferredData.get(0);

    	assertEquals(key, inferredData.getKey());
    	assertEquals(value, inferredData.getValue());
    	assertEquals(application, inferredData.getSignatureCreation());
    	assertEquals(application, inferredData.getSignatureModification());
    	assertEquals("REPINDMSV", inferredData.getSiteCreation());
    	assertEquals("REPINDMSV", inferredData.getSiteModification());
    	
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
