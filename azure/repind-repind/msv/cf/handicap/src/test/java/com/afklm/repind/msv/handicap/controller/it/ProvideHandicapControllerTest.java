package com.afklm.repind.msv.handicap.controller.it;

import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.HandicapData;
import com.afklm.repind.msv.handicap.model.HandicapDataModel;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapProvideResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProvideHandicapControllerTest {

    MockMvc mockMvc;
    
    ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapRepository handicapRepository;
    
    private List<Handicap> listHandicap;
    
    private String gin = "400272036307";
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listHandicap = new ArrayList<>();
        
        purgeTestData(gin);
                
        listHandicap.add(initTestData(gin, "HCP", "SVAN", Stream.of(new AbstractMap.SimpleEntry<>("dogGuideBreed", "Toto"), new AbstractMap.SimpleEntry<>("dogGuideFlag", "Abcd")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
    }
    
    @Test
    void testProvideHandicapByGin() throws Exception {
    	
    	MvcResult output = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
       		.param("gin", gin))
            .andExpect(status().isOk())
			.andReturn();
    	
    	WrapperHandicapProvideResponse result = mapper.readValue(output.getResponse().getContentAsString(), WrapperHandicapProvideResponse.class);
    	Assert.assertEquals(1, result.getHandicaps().size());
    	Assert.assertEquals("HCP", result.getHandicaps().get(0).getType());
    	Assert.assertEquals("SVAN", result.getHandicaps().get(0).getCode());
    	Assert.assertEquals(2, result.getHandicaps().get(0).getData().size());
    	
    	HandicapDataModel handicapDataModelA = result.getHandicaps().get(0).getData().stream()
    		.filter(e -> e.getKey().equalsIgnoreCase("dogGuideBreed"))
    		.findFirst()
    		.orElse(null);
    	Assert.assertNotNull(handicapDataModelA);
    	Assert.assertEquals("Toto", handicapDataModelA.getValue());
    	
    	HandicapDataModel handicapDataModelB = result.getHandicaps().get(0).getData().stream()
        		.filter(e -> e.getKey().equalsIgnoreCase("dogGuideFlag"))
        		.findFirst()
        		.orElse(null);
        	Assert.assertNotNull(handicapDataModelB);
        	Assert.assertEquals("Abcd", handicapDataModelB.getValue());
    }
    
    @Test
    void testProvideHandicapByGinAndType() throws Exception {
    	
    	MvcResult output = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
    		.param("gin", gin)
    		.param("type", "HCP"))
            .andExpect(status().isOk())
			.andReturn();
       
       	WrapperHandicapProvideResponse result = mapper.readValue(output.getResponse().getContentAsString(), WrapperHandicapProvideResponse.class);
	   	Assert.assertEquals(1, result.getHandicaps().size());
	   	Assert.assertEquals("HCP", result.getHandicaps().get(0).getType());
	   	Assert.assertEquals("SVAN", result.getHandicaps().get(0).getCode());
	   	Assert.assertEquals(2, result.getHandicaps().get(0).getData().size());
   	
	   	HandicapDataModel handicapDataModelA = result.getHandicaps().get(0).getData().stream()
	   		.filter(e -> e.getKey().equalsIgnoreCase("dogGuideBreed"))
	   		.findFirst()
	   		.orElse(null);
	   	Assert.assertNotNull(handicapDataModelA);
	   	Assert.assertEquals("Toto", handicapDataModelA.getValue());
   	
	   	HandicapDataModel handicapDataModelB = result.getHandicaps().get(0).getData().stream()
       		.filter(e -> e.getKey().equalsIgnoreCase("dogGuideFlag"))
       		.findFirst()
       		.orElse(null);
       	Assert.assertNotNull(handicapDataModelB);
       	Assert.assertEquals("Abcd", handicapDataModelB.getValue());
    }
    
    @Test
    void testProvideHandicapByGinAndTypeAndCode() throws Exception {
    	
    	MvcResult output =  mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
    		.param("gin", gin)
    		.param("type", "HCP")
    		.param("code", "SVAN"))
            .andExpect(status().isOk())
            .andReturn();
       
       WrapperHandicapProvideResponse result = mapper.readValue(output.getResponse().getContentAsString(), WrapperHandicapProvideResponse.class);
	   	Assert.assertEquals(1, result.getHandicaps().size());
	   	Assert.assertEquals("HCP", result.getHandicaps().get(0).getType());
	   	Assert.assertEquals("SVAN", result.getHandicaps().get(0).getCode());
	   	Assert.assertEquals(2, result.getHandicaps().get(0).getData().size());
  	
	   	HandicapDataModel handicapDataModelA = result.getHandicaps().get(0).getData().stream()
	   		.filter(e -> e.getKey().equalsIgnoreCase("dogGuideBreed"))
	   		.findFirst()
	   		.orElse(null);
	   	Assert.assertNotNull(handicapDataModelA);
	   	Assert.assertEquals("Toto", handicapDataModelA.getValue());
  	
	   	HandicapDataModel handicapDataModelB = result.getHandicaps().get(0).getData().stream()
      		.filter(e -> e.getKey().equalsIgnoreCase("dogGuideFlag"))
      		.findFirst()
      		.orElse(null);
      	Assert.assertNotNull(handicapDataModelB);
      	Assert.assertEquals("Abcd", handicapDataModelB.getValue());
    }
    

    void purgeTestData(String gin) {
        
        List<Handicap> listHandicap = handicapRepository.findByGin(gin);
        
        for (Handicap handicap : listHandicap) {
        	handicapRepository.delete(handicap);
		}
    }

    Handicap initTestData(String gin, String type, String code, Map<String, String> data) {
    	
    	Date now = new Date();
    	
        Handicap handicap = new Handicap();
        handicap.setGin(gin);
        handicap.setType(type);
        handicap.setCode(code);        
        handicap.setSignatureCreation("Test");
        handicap.setSignatureModification("Test");
        handicap.setSiteCreation("Test");
        handicap.setSiteModification("Test");
        handicap.setDateCreation(now);
        handicap.setDateModification(now);
        
        List<HandicapData> listHandicapData = new ArrayList<HandicapData>();
       
        for (Map.Entry<String,String> entry : data.entrySet()) {
        	HandicapData handicapData = new HandicapData();
        	handicapData.setKey(entry.getKey());
        	handicapData.setValue(entry.getValue());
        	handicapData.setSignatureCreation("Test");
        	handicapData.setSignatureModification("Test");
        	handicapData.setSiteCreation("Test");
        	handicapData.setSiteModification("Test");
        	handicapData.setDateCreation(now);
        	handicapData.setDateModification(now);
        	handicapData.setHandicap(handicap);
        	listHandicapData.add(handicapData);
    	}
        
        handicap.setHandicapData(listHandicapData);
        
        handicapRepository.saveAndFlush(handicap);
                
        return handicap;
    }  
}
