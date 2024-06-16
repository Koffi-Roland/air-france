package com.afklm.repind.msv.handicap.controller.it;

import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.HandicapData;
import com.afklm.repind.msv.handicap.model.CreateHandicapModel;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapDataRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CreateHandicapControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapRepository handicapRepository;
    
    @Autowired
    HandicapDataRepository handicapDataRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private String defaultGin = "400622632011";
	
	private String defaultApplication = "REPIND";
	
	private String defaultType = "MAT";
	
	private String defaultCode = "WCMP";
	
	private String defaultKey = "length";
        
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        purgeTestData(defaultGin);
        
    }
    
    @Test
    @Transactional
    void testCreateHandicap() throws Exception {
    	String value = "abc";
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, value);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("gin", defaultGin)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(defaultGin)));
        
        checkHandicapCreated(defaultGin, defaultType, defaultCode, defaultApplication, defaultKey, value);
        
    }
    
    @Test
    void testCreateHandicapWithWrongKey() throws Exception {
    	
    	String key = "test";
    	String value = "test";
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, key, value);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("gin", defaultGin)
        		.content(params))
        .andExpect(status().isPreconditionFailed())
		.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_KEY.getError().getName())));
        
        checkHandicapNotCreated(defaultGin);
        
    }

    
    @Test
    void testCreateHandicapWithWrongType() throws Exception {
    	
    	String type = "test";
    	String value = "test";
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, type, defaultKey, value);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("gin", defaultGin)
        		.content(params))
        .andExpect(status().isPreconditionFailed())
		.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_TYPE.getError().getName())));
        
        checkHandicapNotCreated(defaultGin);
        
    }

    
    @Test
    void testCreateHandicapWithWrongCode() throws Exception {
    	
    	String code = "test";
    	String value = "test";
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(code, defaultType, defaultKey, value);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("gin", defaultGin)
        		.content(params))
        .andExpect(status().isPreconditionFailed())
		.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_TYPE_OR_CODE_NOT_ALLOWED.getError().getName())));
        
        checkHandicapNotCreated(defaultGin);
        
    }

    void purgeTestData(String gin) {
        
        List<Handicap> listHandicap = handicapRepository.findByGin(gin);
        
        for (Handicap handicap : listHandicap) {
        	handicapRepository.delete(handicap);
		}
        
    }
    
    void checkHandicapNotCreated(String gin) {

    	List<Handicap> listHandicap = handicapRepository.findByGin(gin);
    	assertEquals(0, listHandicap.size());
    	
    }
    
    void checkHandicapCreated(String gin, String type, String code, String application, String key, String value) {

    	List<Handicap> listHandicap = handicapRepository.findByGin(gin);
    	assertEquals(1, listHandicap.size());
    	
    	Handicap handicap = listHandicap.get(0);
    	assertEquals(gin, handicap.getGin());
    	assertEquals(code, handicap.getCode());
    	assertEquals(type, handicap.getType());
    	assertEquals(application, handicap.getSignatureCreation());
    	assertEquals(application, handicap.getSignatureModification());
    	assertEquals("REPINDMSV", handicap.getSiteCreation());
    	assertEquals("REPINDMSV", handicap.getSiteModification());

    	    	
    	List<HandicapData> listHandicapData = handicap.getHandicapData();
    	assertEquals(1, listHandicapData.size());
    	
    	Optional<HandicapData> handicapData = listHandicapData.stream()
    			.filter(attr -> key.equalsIgnoreCase(attr.getKey())).findFirst();
    	
    	assertTrue(handicapData.isPresent());
    	assertEquals(value, handicapData.get().getValue());
    	assertEquals(application, handicapData.get().getSignatureCreation());
    	assertEquals(application, handicapData.get().getSignatureModification());
    	assertEquals("REPINDMSV", handicapData.get().getSiteCreation());
    	assertEquals("REPINDMSV", handicapData.get().getSiteModification());
    	
    }

    
    CreateHandicapModel createHandicapDataToSave(String code, String type, String key, String value) {

    	
    	CreateHandicapModel createHandicapModel = new CreateHandicapModel();
    	createHandicapModel.setCode(code);
        createHandicapModel.setType(type);
        
        List<HandicapDataCreateModel> listHandicapModel = new ArrayList<HandicapDataCreateModel>();
        
        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey(key);
        handicapDataModel.setValue(value);
        
        listHandicapModel.add(handicapDataModel);
        
        createHandicapModel.setData(listHandicapModel);
        
        return createHandicapModel;
    }
    
    
    
}
