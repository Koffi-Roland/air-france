package com.afklm.repind.msv.handicap.controller.ut;

import com.afklm.repind.msv.handicap.controller.HandicapController;
import com.afklm.repind.msv.handicap.entity.*;
import com.afklm.repind.msv.handicap.model.CreateHandicapModel;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
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
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
class CreateHandicapControllerTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapController handicapController;
    
    @MockBean
    HandicapRepository handicapRepository;
    
    @MockBean
    RefHandicapTypeCodeRepository refHandicapTypeCodeRepository;
    
    @MockBean
    RefHandicapTypeCodeDataRepository refHandicapTypeCodeDataRepository;
    
    @MockBean
    RefHandicapTypeRepository refHandicapTypeRepository;
        
    @MockBean
    IndividualRepository individualRepository;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private String defaultGin = "110000054731";
	
	private String defaultApplication = "REPIND";
	
	private String defaultType = "HCP";
	
	private String defaultCode = "WCHR";
	
	private String defaultKey = "length";
	
	private List<RefHandicapTypeCodeData> listRefHandicapTypeCodeData = new ArrayList<RefHandicapTypeCodeData>();
	
	private List<RefHandicapType> listRefHandicapType = new ArrayList<RefHandicapType>();
	
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        initData();
        
    }
    
    @Test
    void testCreateHandicap() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);

		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);
		
		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeData);
    	
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("gin", defaultGin)
        		.content(params))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.gin", is(defaultGin)));
        
    }
    

    @Test
	@Ignore
    void testCreateHandicapWithNoApplication() throws Exception {
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
		
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("gin", defaultGin)
        		.content(params))
            .andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.status", is(BusinessErrorList.API_MISSING_REQUEST_PARAMETER.getError().getCode())));
        //TODO fix later
    }

    @Test
	@Ignore
    void testCreateHandicapWithBlankApplication() throws Exception {
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
		
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", " ")
        		.param("gin", defaultGin)
        		.content(params))
            .andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_APPLICATION_IS_MISSING.getError().getName())));
        
    }

    @Test
    void testCreateHandicapWithApplicationTooLong() throws Exception {
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
		
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", "TEST_APPLICATION_TOO_LONG")
        		.param("gin", defaultGin)
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_APPLICATION.getError().getName())));
        
    }

    @Test
    void testCreateHandicapWithNoGin() throws Exception {
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
		
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.content(params))
            .andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.restError.code", is(BusinessErrorList.API_MISSING_REQUEST_PARAMETER.getError().getCode())));
        
    }

    @Test
    void testCreateHandicapWithWrongGinLength() throws Exception {
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
		
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("gin", "12345678910")
        		.content(params))
            .andExpect(status().isPreconditionFailed())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_INVALID_GIN.getError().getName())));
        
    }


    @Test
    void testCreateHandicapWithBlankGin() throws Exception {
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	
		String params = mapper.writeValueAsString(listCreateHandicapModel);
		
        mockMvc.perform(post("/")
        		.contentType(MediaType.APPLICATION_JSON)
        		.param("application", defaultApplication)
        		.param("gin", " ")
        		.content(params))
            .andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.restError.name", is(BusinessErrorList.API_GIN_IS_MISSING.getError().getName())));
        
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
    
    void initData() {
    	
    	RefHandicapType refHandicapType = new RefHandicapType();
    	refHandicapType.setCode(defaultType);
    	refHandicapType.setNbMax(2);
    	listRefHandicapType.add(refHandicapType);
    	
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey(defaultKey);
    	
    	refHandicapTypeCodeData.setCondition("M");
    	refHandicapTypeCodeData.setDataType("String");
    	refHandicapTypeCodeData.setMaxLength(4);
    	refHandicapTypeCodeData.setMinLength(2);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeData.add(refHandicapTypeCodeData);
    }
    
}
