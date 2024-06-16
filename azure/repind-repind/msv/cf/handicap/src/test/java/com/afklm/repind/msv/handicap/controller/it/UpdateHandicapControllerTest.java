package com.afklm.repind.msv.handicap.controller.it;

import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.HandicapData;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateHandicapControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapRepository handicapRepository;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    private List<Handicap> listHandicap;
    
    private String defaultGin = "400622632011";
    
    private String defaultApplication = "REPIND";
        
	@BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listHandicap = new ArrayList<>();
        
        purgeTestData(defaultGin);
                
        listHandicap.add(initTestData(defaultGin, "HCP", "SVAN", Stream.of(new AbstractMap.SimpleEntry<>("handicapText", "Toto")).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
    }
    
    @Test
    @Transactional
    void testUpdateHandicap_Add_Data() throws Exception {
    	   	
    	List<HandicapDataCreateModel> listHandicapData = new ArrayList<>();
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("handicapText");
    	handicapDataCreateModel.setValue("abcd");
    	listHandicapData.add(handicapDataCreateModel);
    	
    	String handicap = mapper.writeValueAsString(listHandicapData);
    	
        mockMvc.perform(put("/")
        	.contentType(MediaType.APPLICATION_JSON)
        	.param("id", listHandicap.get(0).getHandicapId().toString())
        	.param("gin", defaultGin)
        	.param("application", defaultApplication)
        	.content(handicap))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(defaultGin)));
        
        List<Handicap> result = handicapRepository.findByGin(defaultGin);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.get(0).getHandicapData().size());
        Assert.assertEquals(true, result.get(0).getHandicapData().stream().filter(e -> e.getKey().equalsIgnoreCase("handicapText")).findFirst().isPresent());
        Assert.assertEquals("abcd", result.get(0).getHandicapData().stream().filter(e -> e.getKey().equalsIgnoreCase("handicapText")).findFirst().get().getValue());
    }
    
    @Test
    @Transactional
    void testUpdateHandicap_Update_Data() throws Exception {
    	   	
    	List<HandicapDataCreateModel> listHandicapData = new ArrayList<HandicapDataCreateModel>();
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("handicapText");
    	handicapDataCreateModel.setValue("Test");
    	listHandicapData.add(handicapDataCreateModel);
    	
    	String handicap = mapper.writeValueAsString(listHandicapData);
    	
        mockMvc.perform(put("/")
        	.contentType(MediaType.APPLICATION_JSON)
        	.param("id", listHandicap.get(0).getHandicapId().toString())
        	.param("gin", defaultGin)
        	.param("application", defaultApplication)
        	.content(handicap))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(defaultGin)));
        
        List<Handicap> result = handicapRepository.findByGin(defaultGin);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.get(0).getHandicapData().size());
        Assert.assertEquals(true, result.get(0).getHandicapData().stream().filter(e -> e.getKey().equalsIgnoreCase("handicapText")).findFirst().isPresent());
        Assert.assertEquals("Test", result.get(0).getHandicapData().stream().filter(e -> e.getKey().equalsIgnoreCase("handicapText")).findFirst().get().getValue());
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
