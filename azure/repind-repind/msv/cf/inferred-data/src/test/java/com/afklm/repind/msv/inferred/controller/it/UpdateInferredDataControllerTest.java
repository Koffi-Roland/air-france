package com.afklm.repind.msv.inferred.controller.it;

import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.entity.InferredData;
import com.afklm.repind.msv.inferred.model.UpdateInferredDataModel;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateInferredDataControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    InferredRepository inferredRepository;
    
    @Autowired
    InferredDataRepository inferredDataRepository;
	
	private ObjectMapper mapper = new ObjectMapper();

    
    private Long inferredIdByDefault;
	private String valueByDefault = "tdlinares-ext@airfrance.fr";
	private String keyByDefault = "EMAIL";
	private String statusByDefault = "C";
	private String typeByDefault = "EML";
	private String ginByDefault = "400476875804";
        
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        purgeTestData(ginByDefault);
        
        inferredIdByDefault = initTestData(ginByDefault, statusByDefault, typeByDefault, keyByDefault, valueByDefault).getInferredId();
        
    }
    
    @Test
    void testUpdateInferredData() throws Exception {
    	
    	String status = "V";
    	String application = "TEST";

        UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(inferredIdByDefault, ginByDefault, status, application);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("gin", ginByDefault);
        queryParams.add("id", inferredIdByDefault.toString());
        queryParams.add("status", updateInferredDataModel.getStatus());
        queryParams.add("application", updateInferredDataModel.getApplication());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(ginByDefault)));
        
        checkInferredDataUpdated(ginByDefault, status, application);
        
    }
    
    @Test
    void testUpdateInferredWithWrongStatus() throws Exception {
    	
    	String status = "B";
    	String application = "TEST";

    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(inferredIdByDefault, ginByDefault, status, application);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("gin", ginByDefault);
        queryParams.add("id", inferredIdByDefault.toString());
        queryParams.add("status", updateInferredDataModel.getStatus());
        queryParams.add("application", updateInferredDataModel.getApplication());
    	
        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_STATUS_NOT_ALLOWED.getError().getName())));
        
        checkInferredDataNotUpdated(ginByDefault);
        
    }
    
    @Test
    void testUpdateInferredWithWrongId() throws Exception {
    	
    	String status = "R";
    	String application = "TEST";

    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(230L, ginByDefault, status, application);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("gin", ginByDefault);
        queryParams.add("id", "230");
        queryParams.add("status", updateInferredDataModel.getStatus());
        queryParams.add("application", updateInferredDataModel.getApplication());
    	
        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INFERRED_DATA_NOT_FOUND.getError().getName())));
        
    }

    void purgeTestData(String gin) {
        
        List<Inferred> listInferred = inferredRepository.findByGin(gin);
        
        for (Inferred inferred : listInferred) {
        	inferredRepository.delete(inferred);
		}
        
    }
    
    void checkInferredDataNotUpdated(String gin) {

    	List<Inferred> listInferred = inferredRepository.findByGin(gin);
    	assertEquals(1, listInferred.size());
    	
    	Inferred inferred = listInferred.get(0);
    	
    	assertEquals(statusByDefault, inferred.getStatus());
    	assertEquals(typeByDefault, inferred.getType());

    	List<InferredData> listInferredData = inferredDataRepository.findByInferredInferredId(inferred.getInferredId());
    	
    	assertEquals(1, listInferredData.size());
    	InferredData inferredData = listInferredData.get(0);

    	assertEquals(keyByDefault, inferredData.getKey());
    	assertEquals(valueByDefault, inferredData.getValue());
    	
    }
    
    void checkInferredDataUpdated(String gin, String status, String application) {

    	List<Inferred> listInferred = inferredRepository.findByGin(gin);
    	assertEquals(1, listInferred.size());
    	
    	Inferred inferred = listInferred.get(0);
    	assertEquals(gin, inferred.getGin());
    	assertEquals(status, inferred.getStatus());
    	assertEquals(application, inferred.getSignatureModification());
    	assertEquals("REPINDMSV", inferred.getSiteModification());

    	List<InferredData> listInferredData = inferredDataRepository.findByInferredInferredId(inferred.getInferredId());
    	assertEquals(1, listInferredData.size());
    	
    }
    
    UpdateInferredDataModel createInferredDataToSave(Long id, String gin, String status, String application) {

    	
    	UpdateInferredDataModel updateInferredDataModel = new UpdateInferredDataModel();
    	updateInferredDataModel.setId(id);
    	updateInferredDataModel.setGin(gin);
    	updateInferredDataModel.setStatus(status);
    	updateInferredDataModel.setApplication(application);
        
        return updateInferredDataModel;
    }

    Inferred initTestData(String gin, String status, String type, String key, String value) {
    	
    	Date now = new Date();
    	
        Inferred inferred = new Inferred();
        inferred.setGin(gin);
        inferred.setStatus(status);
        inferred.setType(type);
        inferred.setSignatureCreation("Test");
        inferred.setSignatureModification("Test");
        inferred.setSiteCreation("Test");
        inferred.setSiteModification("Test");
        inferred.setDateCreation(now);
        inferred.setDateModification(now);
        
        List<InferredData> listInferredData = new ArrayList<InferredData>();
        
        InferredData inferredData = new InferredData();
        inferredData.setKey(key);
        inferredData.setValue(value);
        inferredData.setInferred(inferred);
        
        listInferredData.add(inferredData);
        
        inferred.setInferredData(listInferredData);
        
        inferredRepository.saveAndFlush(inferred);
        
        return inferred;
        
    }
    
    
}
