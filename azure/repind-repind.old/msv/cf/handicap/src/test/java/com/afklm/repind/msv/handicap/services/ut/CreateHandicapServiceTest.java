package com.afklm.repind.msv.handicap.services.ut;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.entity.*;
import com.afklm.repind.msv.handicap.model.CreateHandicapModel;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.*;
import com.afklm.repind.msv.handicap.services.HandicapCreateService;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import com.afklm.repind.msv.handicap.wrapper.WrapperHandicapCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
class CreateHandicapServiceTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapCreateService handicapCreateService;
    
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
	
	private String defaultGin = "110000054731";
	
	private String defaultApplication = "REPIND";
	
	private String defaultType = "HCP";
	
	private String defaultCode = "WCHR";
	
	private String defaultKey = "length";
	
	private List<RefHandicapTypeCodeData> listRefHandicapTypeCodeData = new ArrayList<RefHandicapTypeCodeData>();
	
	private List<RefHandicapType> listRefHandicapType = new ArrayList<RefHandicapType>();
	
    @BeforeEach
    void setup() throws Exception {
        initData(defaultType, 2, defaultKey, "M", "String", 4, 2);
    }
    
    @Test
    void testCreateHandicap() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);
		
		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeData);
    	
		ResponseEntity<WrapperHandicapCreateResponse> response = handicapCreateService.createHandicap(handicapCriteria);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(defaultGin, response.getBody().gin);
        
    }
    
    @Test
    void testCreateHandicapWithCustomerWhoDoesntExist() throws Exception {
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.empty());
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
			assertEquals(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    
    
    @Test
    void testCreateHandicapWithDuplicateTypeCode() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	CreateHandicapModel createHandicapModelDuplicate = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	listCreateHandicapModel.add(createHandicapModelDuplicate);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_DUPLICATE_TYPE_CODE.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    
    @Test
    void testCreateHandicapWithEmptyType() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, "", defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
			assertEquals(BusinessErrorList.API_TYPE_IS_MISSING.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    
    @Test
    void testCreateHandicapWithTooLongType() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, "TEST_TYPE_TOO_LONG", defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_TYPE.getError().getName(), e.getRestError().getName());
			
		}
        
    }

    @Test
    void testCreateHandicapWithEmptyCode() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave("", defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
			assertEquals(BusinessErrorList.API_CODE_IS_MISSING.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    
    @Test
    void testCreateHandicapWithTooLongCode() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave("TEST_CODE_TOO_LONG", defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_CODE.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    
    @Test
    void testCreateHandicapCombinationTypeCodeDoesntExist() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("0"));
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_TYPE_OR_CODE_NOT_ALLOWED.getError().getName(), e.getRestError().getName());
			
		}
        
    }

    
    @Test
    void testCreateHandicapWithEmptyKey() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();

    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");

        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey("");
        handicapDataModel.setValue("test");
    	createHandicapModel.getData().add(handicapDataModel);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		List<RefHandicapTypeCodeData> listRefHandicapTypeCodeDataForTest = listRefHandicapTypeCodeData;
		
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("test");
    	
    	refHandicapTypeCodeData.setCondition("O");
    	refHandicapTypeCodeData.setDataType("String");
    	refHandicapTypeCodeData.setMaxLength(2);
    	refHandicapTypeCodeData.setMinLength(2);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeDataForTest.add(refHandicapTypeCodeData);
    	
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeDataForTest);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_HANDICAP.getError().getName(), e.getRestError().getName());
			
		}
        
    }

    
    @Test
    void testCreateHandicapWithEmptyValue() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "");
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeData);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_HANDICAP.getError().getName(), e.getRestError().getName());
			
		}
        
    }


    
    @Test
    void testCreateHandicapWithDuplicateKey() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");

        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey(defaultKey);
        handicapDataModel.setValue("test2");
    	createHandicapModel.getData().add(handicapDataModel);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeData);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_PRESENT_MORE_THAN_ONCE.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    

    @Test
    void testCreateHandicapWithWrongKey() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");

        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey("wrong");
        handicapDataModel.setValue("test2");
    	createHandicapModel.getData().add(handicapDataModel);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeData);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_KEY.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    

    @Test
    void testCreateHandicapWithMissingMandatoryKey() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, "test", "test");

    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		List<RefHandicapTypeCodeData> listRefHandicapTypeCodeDataForTest = listRefHandicapTypeCodeData;
		
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("test");
    	
    	refHandicapTypeCodeData.setCondition("O");
    	refHandicapTypeCodeData.setDataType("String");
    	refHandicapTypeCodeData.setMaxLength(2);
    	refHandicapTypeCodeData.setMinLength(2);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeDataForTest.add(refHandicapTypeCodeData);
    	
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeDataForTest);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_KEY_IS_MISSING.getError().getName(), e.getRestError().getName());
			
		}
        
    }

    @Test
    void testCreateHandicapWithValidDate() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");


        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey("test");
        handicapDataModel.setValue("17/11/1993");
    	createHandicapModel.getData().add(handicapDataModel);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		List<RefHandicapTypeCodeData> listRefHandicapTypeCodeDataForTest = listRefHandicapTypeCodeData;
		
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("test");
    	
    	refHandicapTypeCodeData.setCondition("O");
    	refHandicapTypeCodeData.setDataType("Date");
    	refHandicapTypeCodeData.setMaxLength(null);
    	refHandicapTypeCodeData.setMinLength(null);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeDataForTest.add(refHandicapTypeCodeData);
    	
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeDataForTest);
		
		try {
			
			ResponseEntity<WrapperHandicapCreateResponse> response = handicapCreateService.createHandicap(handicapCriteria);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(defaultGin, response.getBody().gin);
			
		} catch (ServiceException e) {

			fail();
			
		}
        
    }
    

    @Test
    void testCreateHandicapWithInvalidDate() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");


        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey("test");
        handicapDataModel.setValue("sdfsdfsdfsdf");
    	createHandicapModel.getData().add(handicapDataModel);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		List<RefHandicapTypeCodeData> listRefHandicapTypeCodeDataForTest = listRefHandicapTypeCodeData;
		
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("test");
    	
    	refHandicapTypeCodeData.setCondition("O");
    	refHandicapTypeCodeData.setDataType("Date");
    	refHandicapTypeCodeData.setMaxLength(null);
    	refHandicapTypeCodeData.setMinLength(null);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeDataForTest.add(refHandicapTypeCodeData);
    	
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeDataForTest);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_TYPE_DATE.getError().getName(), e.getRestError().getName());
			
		}
        
    }


    @Test
    void testCreateHandicapWithValidBoolean() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");


        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey("test");
        handicapDataModel.setValue("true");
    	createHandicapModel.getData().add(handicapDataModel);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		List<RefHandicapTypeCodeData> listRefHandicapTypeCodeDataForTest = listRefHandicapTypeCodeData;
		
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("test");
    	
    	refHandicapTypeCodeData.setCondition("O");
    	refHandicapTypeCodeData.setDataType("Boolean");
    	refHandicapTypeCodeData.setMaxLength(null);
    	refHandicapTypeCodeData.setMinLength(null);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeDataForTest.add(refHandicapTypeCodeData);
    	
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeDataForTest);
		
		try {
			
			ResponseEntity<WrapperHandicapCreateResponse> response = handicapCreateService.createHandicap(handicapCriteria);
			assertEquals(HttpStatus.OK, response.getStatusCode());
			assertEquals(defaultGin, response.getBody().gin);
			
		} catch (ServiceException e) {

			fail();
			
		}
        
    }
    
    @Test
    void testCreateHandicapWithInvalidBoolean() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");


        HandicapDataCreateModel handicapDataModel = new HandicapDataCreateModel();
        handicapDataModel.setKey("test");
        handicapDataModel.setValue("dfgdg");
    	createHandicapModel.getData().add(handicapDataModel);
    	
    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		List<RefHandicapTypeCodeData> listRefHandicapTypeCodeDataForTest = listRefHandicapTypeCodeData;
		
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("test");
    	
    	refHandicapTypeCodeData.setCondition("O");
    	refHandicapTypeCodeData.setDataType("Boolean");
    	refHandicapTypeCodeData.setMaxLength(null);
    	refHandicapTypeCodeData.setMinLength(null);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeDataForTest.add(refHandicapTypeCodeData);
    	
		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeDataForTest);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_TYPE_BOOLEAN.getError().getName(), e.getRestError().getName());
			
		}
        
    }

    @Test
    void testCreateHandicapWithStringTooLong() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "VALUE_TOO_LONG");

    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeData);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_VALUE_TOO_LONG.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    

    @Test
    void testCreateHandicapWithStringTooShort() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "T");

    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		when(refHandicapTypeCodeRepository.countByTypeAndCode(defaultType, defaultCode)).thenReturn(Long.parseLong("1"));
		when(refHandicapTypeCodeDataRepository.findByTypeAndCode(defaultType, defaultCode)).thenReturn(listRefHandicapTypeCodeData);
		
		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_VALUE_TOO_SHORT.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    

    @Test
    void testCreateHandicapWithInvalidType() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, "otherType", defaultKey, "test");

    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_TYPE.getError().getName(), e.getRestError().getName());
			
		}
        
    }


    @Test
    void testCreateHandicapWithTooMuchHandicapFromDB() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	Handicap hand = new Handicap();
    	hand.setType(defaultType);

    	Handicap hand2 = new Handicap();
    	hand2.setType(defaultType);
    	
    	handicapList.add(hand);
    	handicapList.add(hand2);
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");

    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_TOO_MUCH_TYPE.getError().getName(), e.getRestError().getName());
			
		}
        
    }
    

    @Test
    void testCreateHandicapWithTooMuchHandicapFromRequest() throws Exception {
        
    	List<Handicap> handicapList = new ArrayList<Handicap>();
    	
    	Handicap hand = new Handicap();
    	hand.setType(defaultType);
    	
    	handicapList.add(hand);
    	
    	CreateHandicapModel createHandicapModel = createHandicapDataToSave(defaultCode, defaultType, defaultKey, "test");
    	CreateHandicapModel createHandicapModel2 = createHandicapDataToSave("otherCode", defaultType, defaultKey, "test");

    	List<CreateHandicapModel> listCreateHandicapModel = new ArrayList<>();
    	listCreateHandicapModel.add(createHandicapModel);
    	listCreateHandicapModel.add(createHandicapModel2);

		HandicapCriteria handicapCriteria = new HandicapCriteria();
		handicapCriteria.setGin(defaultGin);
		handicapCriteria.setApplication(defaultApplication);
		handicapCriteria.setHandicap(listCreateHandicapModel);

		
		when(individualRepository.findIndividualNotDeleted(defaultGin)).thenReturn(Optional.of(new Individual()));
		
		when(handicapRepository.findByGin(defaultGin)).thenReturn(handicapList);

		when(refHandicapTypeRepository.findAll()).thenReturn(listRefHandicapType);

		try {
			
			handicapCreateService.createHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {

			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_TOO_MUCH_TYPE.getError().getName(), e.getRestError().getName());
			
		}
        
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

    void initData(String type, Integer nbMax, String key, String condition, String dataType, Integer maxLength, Integer minLength) {
    	
    	RefHandicapType refHandicapType = new RefHandicapType();
    	refHandicapType.setCode(type);
    	refHandicapType.setNbMax(nbMax);
    	listRefHandicapType.add(refHandicapType);
    	
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey(key);
    	
    	refHandicapTypeCodeData.setCondition(condition);
    	refHandicapTypeCodeData.setDataType(dataType);
    	refHandicapTypeCodeData.setMaxLength(maxLength);
    	refHandicapTypeCodeData.setMinLength(minLength);
    	
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	listRefHandicapTypeCodeData.add(refHandicapTypeCodeData);
    }
    
}
