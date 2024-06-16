package com.afklm.repind.msv.handicap.services.ut;

import com.afklm.repind.msv.handicap.criteria.HandicapCriteria;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.repository.HandicapRepository;
import com.afklm.repind.msv.handicap.services.HandicapProvideService;
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

import static org.junit.Assert.fail;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProvideHandicapServiceTest {

    @MockBean
    private HandicapRepository handicapRepository;
    
    @Autowired
    private HandicapProvideService handicapProvideService;
    
    @Test
    void testProvideHandicapByGin_HPC_NOT_FOUND() {
    	
    	Mockito.when(handicapRepository.findByGin(Mockito.anyString())).thenReturn(new ArrayList<Handicap>());
    	
    	try {
			handicapProvideService.provideHandicapByGin(new HandicapCriteria());
			fail();
		} catch (ServiceException e) {
	    	Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
	    	Assert.assertEquals(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode(), e.getRestError().getCode());
		}
    }
    
    @Test
    void testProvideHandicapByGinAndType_HPC_NOT_FOUND() {
    	
    	Mockito.when(handicapRepository.findByGinAndType(Mockito.anyString(), Mockito.anyString())).thenReturn(new ArrayList<Handicap>());
    	
    	try {
			handicapProvideService.provideHandicapByGinAndType(new HandicapCriteria());
			fail();
		} catch (ServiceException e) {
	    	Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
	    	Assert.assertEquals(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode(), e.getRestError().getCode());
		}
    }
    
    @Test
    void testProvideHandicapByGinAndTypeAndCode_HPC_NOT_FOUND() {
    	
    	Mockito.when(handicapRepository.findByGinAndTypeAndCode(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(new ArrayList<Handicap>());
    	
    	try {
			handicapProvideService.provideHandicapByGinAndTypeAndCode(new HandicapCriteria());
		} catch (ServiceException e) {
	    	Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
	    	Assert.assertEquals(BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode(), e.getRestError().getCode());
		}
    }
}
