package com.afklm.repind.msv.inferred.controller.ut;

import com.afklm.repind.msv.inferred.controller.InferredDataController;
import com.afklm.repind.msv.inferred.entity.Inferred;
import com.afklm.repind.msv.inferred.entity.InferredData;
import com.afklm.repind.msv.inferred.repository.InferredRepository;
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
class ProvideInferredDataControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    InferredDataController inferredDataController;
    
    @MockBean
    InferredRepository inferredRepository;
    
    private List<Inferred> listInferred;
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listInferred = new ArrayList<>();
        listInferred.add(initTestData("400473353290", "C", "TXT", "TEXT", "Aime Zidane"));
        listInferred.add(initTestData("400473353290", "X", "TXT", "TEXT", "Adore le fromage"));
        listInferred.add(initTestData("400473353290", "V", "EML", "EMAIL", "tdlinares-ext@airfrance.fr"));
    }
    
    @Test
    void testProvideInferredDataByGin() throws Exception {
    	
        when(inferredRepository.findByGin(any(String.class))).thenReturn(listInferred);        
        mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.inferredData", hasSize(3)))
			.andExpect(jsonPath("$.inferredData[0].gin", is("400473353290")));
        
    }
    
    @Test
    void testProvideOneInferredDataByGin() throws Exception {

    	List<Inferred> newListInferred = new ArrayList<>();
    	newListInferred.add(listInferred.get(0));
    	
        when(inferredRepository.findByGin(any(String.class))).thenReturn(newListInferred);        
        mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.inferredData", hasSize(1)))
			.andExpect(jsonPath("$.inferredData[0].gin", is("400473353290")))
			.andExpect(jsonPath("$.inferredData[0].status", is("C")))
			.andExpect(jsonPath("$.inferredData[0].type", is("TXT")))
			.andExpect(jsonPath("$.inferredData[0].data[0].key", is("TEXT")))
			.andExpect(jsonPath("$.inferredData[0].data[0].value", is("Aime Zidane")));
        
    }
    
    @Test
    void testProvideWithNotFound() throws Exception {

    	List<Inferred> newListInferred = new ArrayList<>();
        when(inferredRepository.findByGin(any(String.class))).thenReturn(newListInferred);        
        mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
        
    }

    
    @Test
    void testWithWrongGin() throws Exception {
    	
        when(inferredRepository.findByGin(any(String.class))).thenReturn(listInferred);        
        mockMvc.perform(get("/40047380290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed());
        
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
        
        listInferredData.add(inferredData);
        
        inferred.setInferredData(listInferredData);
        
        return inferred;
        
    }
    
}
