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


@ExtendWith(SpringExtension.class)
@SpringBootTest
class HandicapHelperTest {

    @MockBean
    private HandicapRepository handicapRepository;
    
    @Autowired
    private HandicapProvideService handicapProvideService;
    
    @Test
    void testProvideHandicapByGin_HPC_NOT_FOUND() {
    	
    	Mockito.when(handicapRepository.findByGin(Mockito.anyString())).thenReturn(new ArrayList<Handicap>());
    	
    	ServiceException eRaised = null;
    	
    	try {
			handicapProvideService.provideHandicapByGin(new HandicapCriteria());
		} catch (ServiceException e) {
			eRaised = e;
		}
    	    	
    	Assert.assertEquals(HttpStatus.NOT_FOUND, eRaised.getStatus());
    	Assert.assertEquals(eRaised.getRestError().getCode(), BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode());
    }
    
    @Test
    void testProvideHandicapByGinAndType_HPC_NOT_FOUND() {
    	
    	Mockito.when(handicapRepository.findByGinAndType(Mockito.anyString(), Mockito.anyString())).thenReturn(new ArrayList<Handicap>());
    	
    	ServiceException eRaised = null;
    	
    	try {
			handicapProvideService.provideHandicapByGin(new HandicapCriteria());
		} catch (ServiceException e) {
			eRaised = e;
		}
    	
    	Assert.assertEquals(HttpStatus.NOT_FOUND, eRaised.getStatus());
    	Assert.assertEquals(eRaised.getRestError().getCode(), BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode());
    }
    
    @Test
    void testProvideHandicapByGinAndTypeAndCode_HPC_NOT_FOUND() {
    	
    	Mockito.when(handicapRepository.findByGinAndTypeAndCode(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(new ArrayList<Handicap>());
    	
    	ServiceException eRaised = null;
    	
    	try {
			handicapProvideService.provideHandicapByGin(new HandicapCriteria());
		} catch (ServiceException e) {
			eRaised = e;
		}
    	
    	Assert.assertEquals(HttpStatus.NOT_FOUND, eRaised.getStatus());
    	Assert.assertEquals(eRaised.getRestError().getCode(), BusinessErrorList.API_HANDICAP_NOT_FOUND.getError().getCode());
    }
}
