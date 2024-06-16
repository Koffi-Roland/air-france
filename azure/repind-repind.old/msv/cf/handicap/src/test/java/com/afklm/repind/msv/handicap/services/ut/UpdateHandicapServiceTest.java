package com.afklm.repind.msv.handicap.services.ut;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.Individual;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeDataID;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.*;
import com.afklm.repind.msv.handicap.services.HandicapUpdateService;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
class UpdateHandicapServiceTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    HandicapUpdateService handicapUpdateService;
    
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
		    
    @Test
    void testUpdateHandicap_GIN_NOT_FOUND() {
    	
    	when(individualRepository.findIndividualNotDeleted(Mockito.any())).thenReturn(Optional.ofNullable(null));

    	try {
			handicapUpdateService.updateHandicap(new HandicapCriteria());
			fail();
			
		} catch (ServiceException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
			assertEquals(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError().getName(), e.getRestError().getName());
		}
    }
    
    @Test
    void testUpdateHandicap_GIN_NOT_SAME() throws ServiceException {
    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setGin("110000054731");
    	
    	Handicap handicap = new Handicap();
    	handicap.setGin("110000054732");
    
    	when(handicapRepository.findById(Mockito.any())).thenReturn(Optional.of(handicap));
    	when(individualRepository.findIndividualNotDeleted(Mockito.any())).thenReturn(Optional.of(new Individual()));

    	try {
			handicapUpdateService.updateHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_CUSTOMER.getError().getName(), e.getRestError().getName());
		}
    }
    
    @Test
    void testUpdateHandicap_HANDICAP_NOT_FOUND() throws ServiceException {
    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setGin("110000054731");
    
    	when(handicapRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
    	when(individualRepository.findIndividualNotDeleted(Mockito.any())).thenReturn(Optional.of(new Individual()));

    	try {
			handicapUpdateService.updateHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
			assertEquals(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getName(), e.getRestError().getName());
		}
    }
    
    @Test
    void testUpdateHandicap_TYPE_NOT_ALLOWED() throws ServiceException {
    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setGin("110000054731");
    	
    	Handicap handicap = new Handicap();
    	handicap.setGin("110000054731");
    
    	when(handicapRepository.findById(Mockito.any())).thenReturn(Optional.of(handicap));
    	when(individualRepository.findIndividualNotDeleted(Mockito.any())).thenReturn(Optional.of(new Individual()));
    	when(refHandicapTypeCodeRepository.countByTypeAndCode(Mockito.any(), Mockito.any())).thenReturn(new Long(0));

    	try {
			handicapUpdateService.updateHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {
			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_TYPE_OR_CODE_NOT_ALLOWED.getError().getName(), e.getRestError().getName());
		}
    }
    
    @Test
    void testUpdateHandicap_KEY_NOT_ALLOWED() throws ServiceException {
    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setGin("110000054731");
    	handicapCriteria.setHandicapData(new ArrayList<HandicapDataCreateModel>());
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("newKey");
    	handicapDataCreateModel.setValue("newValue");
    	handicapCriteria.getHandicapData().add(handicapDataCreateModel);
    	
    	Handicap handicap = new Handicap();
    	handicap.setGin("110000054731");
    	
    	List<RefHandicapTypeCodeData> keysAllowed = new ArrayList<RefHandicapTypeCodeData>();
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID  = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("keyAllowed");
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	keysAllowed.add(refHandicapTypeCodeData);
    
    	when(handicapRepository.findById(Mockito.any())).thenReturn(Optional.of(handicap));
    	when(individualRepository.findIndividualNotDeleted(Mockito.any())).thenReturn(Optional.of(new Individual()));
    	when(refHandicapTypeCodeRepository.countByTypeAndCode(Mockito.any(), Mockito.any())).thenReturn(new Long(1));
    	when(refHandicapTypeCodeDataRepository.findByTypeAndCode(Mockito.any(), Mockito.any())).thenReturn(keysAllowed);

    	try {
			handicapUpdateService.updateHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {
			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_INVALID_KEY.getError().getName(), e.getRestError().getName());
		}
    }
    
    @Test
    void testUpdateHandicap_KEY_ALREADY_SET() throws ServiceException {
    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setGin("110000054731");
    	handicapCriteria.setHandicapData(new ArrayList<HandicapDataCreateModel>());
    	HandicapDataCreateModel handicapDataCreateModelA = new HandicapDataCreateModel();
    	handicapDataCreateModelA.setKey("keyAllowed");
    	handicapDataCreateModelA.setValue("newValueA");
    	handicapCriteria.getHandicapData().add(handicapDataCreateModelA);
    	HandicapDataCreateModel handicapDataCreateModelB = new HandicapDataCreateModel();
    	handicapDataCreateModelB.setKey("keyAllowed");
    	handicapDataCreateModelB.setValue("newValueB");
    	handicapCriteria.getHandicapData().add(handicapDataCreateModelB);
    	
    	Handicap handicap = new Handicap();
    	handicap.setGin("110000054731");
    	
    	List<RefHandicapTypeCodeData> keysAllowed = new ArrayList<RefHandicapTypeCodeData>();
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	RefHandicapTypeCodeDataID refHandicapTypeCodeDataID  = new RefHandicapTypeCodeDataID();
    	refHandicapTypeCodeDataID.setKey("keyAllowed");
    	refHandicapTypeCodeData.setRefHandicapTypeCodeDataID(refHandicapTypeCodeDataID);
    	keysAllowed.add(refHandicapTypeCodeData);
    
    	when(handicapRepository.findById(Mockito.any())).thenReturn(Optional.of(handicap));
    	when(individualRepository.findIndividualNotDeleted(Mockito.any())).thenReturn(Optional.of(new Individual()));
    	when(refHandicapTypeCodeRepository.countByTypeAndCode(Mockito.any(), Mockito.any())).thenReturn(new Long(1));
    	when(refHandicapTypeCodeDataRepository.findByTypeAndCode(Mockito.any(), Mockito.any())).thenReturn(keysAllowed);

    	try {
			handicapUpdateService.updateHandicap(handicapCriteria);
			fail();
			
		} catch (ServiceException e) {
			assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
			assertEquals(BusinessErrorList.API_PRESENT_MORE_THAN_ONCE.getError().getName(), e.getRestError().getName());
		}
    }    
}
