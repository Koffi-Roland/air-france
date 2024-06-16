package com.afklm.repind.msv.inferred.controller.it;

import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.entity.InferredData;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
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
class ProvideInferredDataControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    InferredRepository inferredRepository;
    
    private List<Inferred> listInferred;
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listInferred = new ArrayList<>();
        
        purgeTestData("400476875686");
        
        listInferred.add(initTestData("400476875686", "C", "TXT", "TEXT", "Aime Zidane"));
        listInferred.add(initTestData("400476875686", "X", "TXT", "TEXT", "Adore le fromage"));
        listInferred.add(initTestData("400476875686", "V", "EML", "EMAIL", "tdlinares-ext@airfrance.fr"));
    }
    
    @Test
    void testProvideInferredDataByGin() throws Exception {
    	
        mockMvc.perform(get("/400476875686").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.inferredData", hasSize(3)))
			.andExpect(jsonPath("$.inferredData[0].gin", is("400476875686")))
			.andExpect(jsonPath("$.inferredData[0].status", is("C")))
			.andExpect(jsonPath("$.inferredData[0].type", is("TXT")))
			.andExpect(jsonPath("$.inferredData[0].data[0].key", is("TEXT")))
			.andExpect(jsonPath("$.inferredData[0].data[0].value", is("Aime Zidane")));
        
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
        
        List<Inferred> listInferred = inferredRepository.findByGin(gin);
        
        for (Inferred inferred : listInferred) {
        	inferredRepository.delete(inferred);
		}
        
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
