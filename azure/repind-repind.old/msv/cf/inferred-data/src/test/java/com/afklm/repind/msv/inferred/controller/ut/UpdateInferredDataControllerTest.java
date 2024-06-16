package com.afklm.repind.msv.inferred.controller.ut;

import com.afklm.repind.msv.inferred.controller.InferredDataController;
import com.afklm.repind.msv.inferred.entity.Individual;
import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.entity.InferredData;
import com.afklm.repind.msv.inferred.model.UpdateInferredDataModel;
import com.afklm.repind.msv.inferred.model.error.BusinessErrorList;
import com.afklm.repind.msv.inferred.repository.IndividualRepository;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
import com.afklm.repind.msv.inferred.repository.MoralPersonRepository;
import com.afklm.repind.msv.inferred.repository.RefInfrdStatusRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
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
class UpdateInferredDataControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    InferredDataController inferredDataController;
    
    @MockBean
    InferredRepository inferredRepository;
    
    @MockBean
    RefInfrdStatusRepository refInfrdStatusRepository;
    
    @MockBean
    IndividualRepository individualRepository;
    
    @MockBean
    MoralPersonRepository moralPersonRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	List<String> defaultStatus;
        
    @BeforeEach
    void setup() {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        defaultStatus = new ArrayList<>();
        defaultStatus.add("C");
    }
    
    @Test
    void testUpdateInferredData() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
    	Long id = 230L;
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id, gin, "C", "Test");

        Inferred inferredToUpdate = createInferredDataToUpdate(id, gin, type, "C", "Test", "email", "testUpdate");

		when(inferredRepository.findById(id)).thenReturn(Optional.of(inferredToUpdate));
		when(refInfrdStatusRepository.findStatusForUpdate()).thenReturn(defaultStatus);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("status", updateInferredDataModel.getStatus());
		queryParams.add("application", updateInferredDataModel.getApplication());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(gin)));
        
    }
    
    @Test
    void testUpdateInferredDataWithWrongId() throws Exception {

    	Long id = 230L;
    	String gin = "123456678784";
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id, gin, "C", "Test");

		when(inferredRepository.findById(id)).thenReturn(Optional.empty());
		when(refInfrdStatusRepository.findStatusForUpdate()).thenReturn(defaultStatus);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("status", updateInferredDataModel.getStatus());
		queryParams.add("application", updateInferredDataModel.getApplication());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isNotFound())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INFERRED_DATA_NOT_FOUND.getError().getName())));
        
    }
    
    @Test
    void testUpdateWithStatusNotAllowed() throws Exception {

    	String type = "EML";
    	String gin = "123456678784";
    	Long id = 230L;
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id, gin, "A", "Test");

        Inferred inferredToUpdate = createInferredDataToUpdate(id, gin, type, "C", "Test", "email", "testUpdate");
    	
		String params = mapper.writeValueAsString(updateInferredDataModel);

		when(inferredRepository.findById(id)).thenReturn(Optional.of(inferredToUpdate));
		when(refInfrdStatusRepository.findStatusForUpdate()).thenReturn(defaultStatus);
		when(individualRepository.findIndividualNotDeleted(gin)).thenReturn(Optional.of(new Individual()));

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("status", updateInferredDataModel.getStatus());
		queryParams.add("application", updateInferredDataModel.getApplication());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_STATUS_NOT_ALLOWED.getError().getName())));
        
    }

    @Test
    void testUpdateWithApplicationTooLong() throws Exception {

    	Long id = 230L;
    	String gin = "123456678784";
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id, gin, "C", "ApplicationNameTooLong");

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("status", updateInferredDataModel.getStatus());
		queryParams.add("application", updateInferredDataModel.getApplication());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_APPLICATION.getError().getName())));
        
    }

    @Test
    void testUpdateWithoutApplication() throws Exception {

    	Long id = 230L;
    	String gin = "123456678784";
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id, gin, "C", null);

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("status", updateInferredDataModel.getStatus());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.restError.code", is(BusinessErrorList.API_MISSING_REQUEST_PARAMETER.getError().getCode())));
        
    }

    @Test
    void testUpdateWithoutStatus() throws Exception {

    	Long id = 230L;
    	String gin = "123456678784";
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id,  gin, null, "Application");
    	
		String params = mapper.writeValueAsString(updateInferredDataModel);

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("application", updateInferredDataModel.getApplication());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.restError.code", is(BusinessErrorList.API_MISSING_REQUEST_PARAMETER.getError().getCode())));
        
    }

    @Test
    void testUpdateWithWrongGin() throws Exception {

    	Long id = 230L;
    	String gin = "12345667878454545";
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id, gin, "C", "Application");

		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("status", updateInferredDataModel.getStatus());
		queryParams.add("application", updateInferredDataModel.getApplication());

    	
        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_GIN.getError().getName())));
        
    }

    @Test
    void testUpdateWithStatusTooLong() throws Exception {

    	Long id = 230L;
    	String gin = "123456678784";
        
    	UpdateInferredDataModel updateInferredDataModel = createInferredDataToSave(id, gin, "CC", "Application");


		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("gin", gin);
		queryParams.add("id", id.toString());
		queryParams.add("status", updateInferredDataModel.getStatus());
		queryParams.add("application", updateInferredDataModel.getApplication());

        mockMvc.perform(put("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.queryParams(queryParams))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_STATUS.getError().getName())));
        
    }
    
    UpdateInferredDataModel createInferredDataToSave(Long id, String gin, String status, String application) {

    	
    	UpdateInferredDataModel updateInferredDataModel = new UpdateInferredDataModel();
    	updateInferredDataModel.setId(id);
    	updateInferredDataModel.setGin(gin);
    	updateInferredDataModel.setStatus(status);
    	updateInferredDataModel.setApplication(application);
        
        return updateInferredDataModel;
    }

    Inferred createInferredDataToUpdate(Long id, String gin, String type, String status, String application, String key, String value) {

    	
        Inferred inferred = new Inferred();
        inferred.setInferredId(id);
        inferred.setGin(gin);
        inferred.setStatus(status);
        inferred.setType(type);
        inferred.setSignatureCreation(application);
        
        List<InferredData> listInferredData = new ArrayList<InferredData>();
        
        InferredData inferredData = new InferredData();
        inferredData.setKey(key);
        inferredData.setValue(value);
        
        listInferredData.add(inferredData);
        
        inferred.setInferredData(listInferredData);
        
        return inferred;
    }
}
