package com.afklm.repind.msv.handicap.controller.ut;

import com.afklm.repind.msv.handicap.repository.HandicapRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class DeleteHandicapControllerTest {

    MockMvc mockMvc;
    
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
    	
        mockMvc.perform(delete("/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }   
}
