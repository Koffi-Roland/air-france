package com.afklm.repind.msv.handicap.controller.ut;

import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateHandicapControllerTest {

    MockMvc mockMvc;
    
    private ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapRepository handicapRepository;
    
    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @Test
    void testWithMissingId() throws Exception {
    	
        mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void testWithWrongGin() throws Exception {
    	
    	List<HandicapDataCreateModel> listHandicapData = new ArrayList<HandicapDataCreateModel>();
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("oxygOutput");
    	handicapDataCreateModel.setValue("abcd");
    	listHandicapData.add(handicapDataCreateModel);
    	
    	String handicap = mapper.writeValueAsString(listHandicapData);
    	
        mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON)
        	.param("id", new Long(10).toString())
        	.param("gin", "4002720363070")
        	.param("application", "app")
        	.content(handicap))
            .andExpect(status().isPreconditionFailed());
    }
    
    @Test
    void testWithMissingGin() throws Exception {
    	
        mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON)
        	.param("id", new Long(10).toString()))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void testWithWrongApplicationCode() throws Exception {
    	
    	List<HandicapDataCreateModel> listHandicapData = new ArrayList<HandicapDataCreateModel>();
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("oxygOutput");
    	handicapDataCreateModel.setValue("abcd");
    	listHandicapData.add(handicapDataCreateModel);
    	
    	String handicap = mapper.writeValueAsString(listHandicapData);
    	
        mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON)
        	.param("id", new Long(10).toString())
        	.param("gin", "400272036307")
        	.param("application", "applicationcodeistoolong")
        	.content(handicap))
            .andExpect(status().isPreconditionFailed());
    }
    
    @Test
    void testWithMissingApplicationCode() throws Exception {
    	
        mockMvc.perform(put("/").contentType(MediaType.APPLICATION_JSON)
        	.param("id", new Long(10).toString())
        	.param("gin", "400272036307"))
            .andExpect(status().isBadRequest());
    } 
}
