package com.afklm.repind.msv.handicap.services.ut;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapDataRepository;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.repository.RefHandicapTypeCodeDataRepository;
import com.afklm.repind.msv.handicap.services.HandicapDeleteService;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.fail;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class DeleteHandicapServiceTest {

    @MockBean
    private HandicapRepository handicapRepository;
    
    @MockBean
    private HandicapDataRepository handicapDataRepository;
    
    @Autowired
    private HandicapDeleteService handicapDeleteService;
    
    @MockBean
    private RefHandicapTypeCodeDataRepository refHandicapTypeCodeDataRepository;
    
    @Test
    void testDeleteHandicapById_HPC_NOT_FOUND() throws ServiceException {
    	
    	Mockito.when(handicapRepository.deleteByHandicapId(Mockito.anyLong())).thenReturn(new Long(0));
    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setId(new Long(10));
    	
    	try {
    		handicapDeleteService.deleteHandicap(handicapCriteria);
    		fail();
		} catch (ServiceException e) {
	    	Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
	    	Assert.assertEquals(e.getRestError().getCode(), BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode());
		}
    }
    
    @Test
    void testDeleteHandicapByIdAndKey_HPC_NOT_FOUND() throws ServiceException {
    	
    	Mockito.when(handicapRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setId(new Long(10));
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("dogGuideFlag");
    	handicapCriteria.setHandicapData(new ArrayList<HandicapDataCreateModel>());
    	handicapCriteria.getHandicapData().add(handicapDataCreateModel);
    	
    	try {
    		handicapDeleteService.deleteHandicap(handicapCriteria);
    		fail();
		} catch (ServiceException e) {
	    	Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
	    	Assert.assertEquals(e.getRestError().getCode(), BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode());
		}
    }
    
    @Test
    void testDeleteHandicapByIdAndKey_KEY_NOT_EXISTING() throws ServiceException {
    	
    	Mockito.when(handicapRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(new Handicap()));
    	Mockito.when(refHandicapTypeCodeDataRepository.findByTypeAndCodeAndKey(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(null);
    	    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setId(new Long(10));
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("notExisting");
    	handicapCriteria.setHandicapData(new ArrayList<HandicapDataCreateModel>());
    	handicapCriteria.getHandicapData().add(handicapDataCreateModel);
    	
    	try {
    		handicapDeleteService.deleteHandicap(handicapCriteria);
    		fail();
		} catch (ServiceException e) {
	    	Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
	    	Assert.assertEquals(e.getRestError().getCode(), BusinessErrorList.API_KEY_NOT_EXISTING.getError().getCode());
		}
    }
    
    @Test
    void testDeleteHandicapByIdAndKey_KEY_IS_MANDATORY() throws ServiceException {
    	
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	refHandicapTypeCodeData.setCondition("M");
    	
    	Mockito.when(handicapRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(new Handicap()));
    	Mockito.when(refHandicapTypeCodeDataRepository.findByTypeAndCodeAndKey(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(refHandicapTypeCodeData);
    	   	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setId(new Long(10));
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("mandatoryKey");
    	handicapCriteria.setHandicapData(new ArrayList<HandicapDataCreateModel>());
    	handicapCriteria.getHandicapData().add(handicapDataCreateModel);
    	
    	try {
    		handicapDeleteService.deleteHandicap(handicapCriteria);
    		fail();
		} catch (ServiceException e) {
	    	Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, e.getStatus());
	    	Assert.assertEquals(e.getRestError().getCode(), BusinessErrorList.API_KEY_IS_MANDATORY.getError().getCode());
		}
    }
    
    @Test
    void testDeleteHandicapByIdAndKey_KEY_NOT_FOUND() throws ServiceException {
    	
    	RefHandicapTypeCodeData refHandicapTypeCodeData = new RefHandicapTypeCodeData();
    	refHandicapTypeCodeData.setCondition("O");
    	
    	Mockito.when(handicapRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(new Handicap()));
    	Mockito.when(refHandicapTypeCodeDataRepository.findByTypeAndCodeAndKey(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(refHandicapTypeCodeData);
    	Mockito.when(handicapDataRepository.deleteByHandicapHandicapIdAndKey(Mockito.anyLong(), Mockito.anyString())).thenReturn(new Long(0));
    	    	
    	HandicapCriteria handicapCriteria = new HandicapCriteria();
    	handicapCriteria.setId(new Long(10));
    	HandicapDataCreateModel handicapDataCreateModel = new HandicapDataCreateModel();
    	handicapDataCreateModel.setKey("optionalKey");
    	handicapCriteria.setHandicapData(new ArrayList<HandicapDataCreateModel>());
    	handicapCriteria.getHandicapData().add(handicapDataCreateModel);
    	
    	try {
    		handicapDeleteService.deleteHandicap(handicapCriteria);
    		fail();
		} catch (ServiceException e) {
	    	Assert.assertEquals( HttpStatus.NOT_FOUND, e.getStatus());
	    	Assert.assertEquals(e.getRestError().getCode(), BusinessErrorList.API_KEY_NOT_FOUND.getError().getCode());
		}
    }
}
