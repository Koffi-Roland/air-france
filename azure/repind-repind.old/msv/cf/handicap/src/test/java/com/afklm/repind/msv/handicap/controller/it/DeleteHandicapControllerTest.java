package com.afklm.repind.msv.handicap.controller.it;

import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.HandicapData;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.repository.RefHandicapTypeCodeDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class DeleteHandicapControllerTest {

    MockMvc mockMvc;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapRepository handicapRepository;

	@Autowired
	private RefHandicapTypeCodeDataRepository refHandicapTypeCodeDataRepository;
    
    private String defaultGin = "400272036307";
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        purgeTestData(defaultGin);
    }
    
    @Test
    void testDeleteHandicapByGin() throws Exception {

    	initTestData(defaultGin, "HCP", "SVAN", Stream.of(new AbstractMap.SimpleEntry<>("dogGuideBreed", "Toto"), new AbstractMap.SimpleEntry<>("dogGuideFlag", "Abcd")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

    	MvcResult result = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
    		.param("gin", defaultGin))
            .andExpect(status().isOk())
            .andReturn();

    	Integer idToDelete = JsonPath.read(result.getResponse().getContentAsString(), "$.handicaps[0].handicapId");

        mockMvc.perform(delete("/").contentType(MediaType.APPLICATION_JSON)
        	.param("id", idToDelete.toString()))
        	.andExpect(status().isOk());

        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
        	.param("gin", defaultGin))
        	.andExpect(status().isNotFound());
    }

    @Test
    void testDeleteHandicapByGinAndKey() throws Exception {

    	initTestData(defaultGin, "HCP", "SVAN", Stream.of(new AbstractMap.SimpleEntry<>("dogBreed", "Toto"), new AbstractMap.SimpleEntry<>("handicapText", "Abcd")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

    	MvcResult result = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
    		.param("gin", defaultGin))
            .andExpect(status().isOk())
            .andReturn();

    	Integer idToDelete = JsonPath.read(result.getResponse().getContentAsString(), "$.handicaps[0].handicapId");

    	List<String> keysToDelete = new ArrayList<>();
		keysToDelete.add("handicapText");
    	String params = mapper.writeValueAsString(keysToDelete);

        mockMvc.perform(delete("/").contentType(MediaType.APPLICATION_JSON)
        	.param("id", idToDelete.toString())
        	.content(params))
        	.andExpect(status().isOk());

        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
        	.param("gin", defaultGin))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.handicaps", hasSize(1)))
        	.andExpect(jsonPath("$.handicaps[0].data", hasSize(1)));
    }

    @Test
    void testDeleteHandicapByGinAndKeys() throws Exception {

    	initTestData(defaultGin, "HCP", "SVAN", Stream.of( new AbstractMap.SimpleEntry<>("handicapText", "Abcd")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

    	MvcResult result = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
    		.param("gin", defaultGin))
            .andExpect(status().isOk())
            .andReturn();

    	Integer idToDelete = JsonPath.read(result.getResponse().getContentAsString(), "$.handicaps[0].handicapId");

    	List<String> keysToDelete = new ArrayList<>();
    	keysToDelete.add("handicapText");
    	String params = mapper.writeValueAsString(keysToDelete);
    	      
        mockMvc.perform(delete("/").contentType(MediaType.APPLICATION_JSON)
        	.param("id", idToDelete.toString())
        	.content(params))
        	.andExpect(status().isOk());
       
        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)
        	.param("gin", defaultGin))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.handicaps", hasSize(1)));
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
        
        List<HandicapData> listHandicapData = new ArrayList<>();
       
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
