package com.afklm.repind.msv.mapping.controller.it;

import com.afklm.repind.msv.mapping.controller.MappingController;
import com.afklm.repind.msv.mapping.model.MappingLanguageModel;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;
import com.afklm.repind.msv.mapping.wrapper.WrapperMappingTableForContext;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MappingControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    MappingController mappingController;
        
    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testProvideWithNotFound() throws Exception {
        mockMvc.perform(get("/mapping/language/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testWithWrongGin() throws Exception {
        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }


    // FROM KLM TO AF
    
    @Test
	void provideMappingFromNoneISOLanguage_TestBRBRCSM() throws ServiceException {
    	
    	
		ResponseEntity<MappingLanguageModel> response = mappingController.provideMappingFromNoneISOLanguage("BR", "BR",
				"CSM");
    	
    	Assert.assertNotNull(response);
    	Assert.assertEquals(200, response.getStatusCodeValue());
    	
    	Assert.assertNotNull(response.getBody());
    	
    	
		MappingLanguageModel res = (MappingLanguageModel) response.getBody();
    	Assert.assertNotNull(res);
    	
		Assert.assertNotNull(res.getMarket());
		Assert.assertNotNull(res.getCodeLanguageISO());
		Assert.assertNotNull(res.getCodeLanguageNoISO());
    }
    
    @Test
	void provideMappingLanguageFromISOLanguage_TestBRPTCSM() throws ServiceException {
    	
    	
		ResponseEntity<MappingLanguageModel> response = mappingController.provideMappingLanguageFromISOLanguage("BR",
				"PT", "CSM");
    	
    	Assert.assertNotNull(response);
    	Assert.assertEquals(200, response.getStatusCodeValue());
    	
    	Assert.assertNotNull(response.getBody());
    	
    	
		MappingLanguageModel res = (MappingLanguageModel) response.getBody();
    	Assert.assertNotNull(res);
    	
		Assert.assertNotNull(res.getMarket());
		Assert.assertNotNull(res.getCodeLanguageISO());
		Assert.assertNotNull(res.getCodeLanguageNoISO());
    }

	@Test
	void provideMappingLanguageTable() throws ServiceException {

		ResponseEntity<List<WrapperMappingTableForContext>> response = mappingController
				.provideMappingLanguageTable("CSM");

		Assert.assertNotNull(response);
		Assert.assertEquals(200, response.getStatusCodeValue());

		Assert.assertNotNull(response.getBody());

		WrapperMappingTableForContext res = (WrapperMappingTableForContext) response.getBody().get(0);
		Assert.assertNotNull(res);

		Assert.assertNotNull(res.getContext());
		Assert.assertEquals("CSM", res.getContext());
		Assert.assertNotNull(res.getMappingLanguages());

		Assert.assertNotNull(res.getMappingLanguages().get(0).getCodeLanguageISO());
		Assert.assertNotNull(res.getMappingLanguages().get(0).getCodeLanguageNoISO());
		Assert.assertNotNull(res.getMappingLanguages().get(0).getMarket());

	}
}
