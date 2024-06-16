package com.afklm.repind.msv.preferences.services.it;

import com.afklm.repind.msv.preferences.criteria.RequestorCriteria;
import com.afklm.repind.msv.preferences.criteria.UltimatePreferencesCriteria;
import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.model.UltimatePreferencesModel;
import com.afklm.repind.msv.preferences.repository.PreferenceRepository;
import com.afklm.repind.msv.preferences.services.UltimatePreferencesCreateService;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import com.afklm.repind.msv.preferences.wrapper.WrapperCreateUltimatePreferencesResponse;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.CreateUpdateIndividualServiceV8;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd7.CreateUpdateIndividualResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class UltimatePreferencesCreateServiceTest {
	
    /** logger */
    private static final Log log = LogFactory.getLog(UltimatePreferencesCreateServiceTest.class);

    @InjectMocks
    private UltimatePreferencesCreateService ultimatePreferencesCreateService;

	@Mock
	@Qualifier("consumerW000442v8")
	private CreateUpdateIndividualServiceV8 createUpdateIndividualServiceV8;

	@Mock
	private PreferenceRepository preferenceRepository;

    @Test    
	void UltimatePreferencesCreateService_CREATE_ULO_Test() throws ServiceException, BusinessErrorBlocBusinessException, SystemException {
    	String gin = "000000318053";
		String type = "LOUNGES";
		String key = "ACTIVITY_PATTERN";
		String value = "Please provide De Telegraaf or Wallstreet Journal in case of trips ex USA - This is not a Test for T412211";
    	
		UltimatePreferencesCriteria cupm = new UltimatePreferencesCriteria();
    	    	
    	cupm.setRequestor(fillRequetorTest());
    	cupm.setGin(gin);
    	cupm.setType(type);
    	cupm.setActionCode("CREATE");
    	
    	UltimatePreferencesModel upm = new UltimatePreferencesModel();
    	List<UltimatePreferencesModel> lupm = new ArrayList<UltimatePreferencesModel>();
    	upm.setKey(key);
    	upm.setValue(value);
    	lupm.add(upm);
    	cupm.setData(lupm);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response.setGin(gin);
		response.setSuccess(true);
		when(createUpdateIndividualServiceV8.createIndividual(any())).thenReturn(response);
    	
    	try {
    		ResponseEntity<WrapperCreateUltimatePreferencesResponse> retour = ultimatePreferencesCreateService.manageUltimatePreferences(cupm);
    		Assertions.assertNotNull(retour);
    		Assertions.assertNotNull(retour.getBody());
    		Assertions.assertNotNull((WrapperCreateUltimatePreferencesResponse) retour.getBody());
    		Assertions.assertTrue(((WrapperCreateUltimatePreferencesResponse) retour.getBody()).retour);
    	} catch (Exception ex) {
    		log.error(ex);
			Assertions.fail(ex.getMessage());
    	}
	}

    @Test    
	void UltimatePreferencesCreateService_CREATE_UFD_Test() throws ServiceException, BusinessErrorBlocBusinessException, SystemException {
    	String gin = "000000318053";
		String type = "FAVORITE_DESTINATION";
		String key = "COUNTRIES";
		String value = "TLN;NCE;CDG";
    	
		UltimatePreferencesCriteria cupm = new UltimatePreferencesCriteria();
    	    	
    	cupm.setRequestor(fillRequetorTest());
    	cupm.setGin(gin);
    	cupm.setType(type);
    	cupm.setActionCode("CREATE");
    	
    	UltimatePreferencesModel upm = new UltimatePreferencesModel();
    	List<UltimatePreferencesModel> lupm = new ArrayList<UltimatePreferencesModel>();
    	upm.setKey(key);
    	upm.setValue(value);
    	lupm.add(upm);
    	cupm.setData(lupm);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response.setGin(gin);
		response.setSuccess(true);
		when(createUpdateIndividualServiceV8.createIndividual(any())).thenReturn(response);

    	try {
    		ResponseEntity<WrapperCreateUltimatePreferencesResponse> retour = ultimatePreferencesCreateService.manageUltimatePreferences(cupm);
    		Assertions.assertNotNull(retour);
    		Assertions.assertNotNull(retour.getBody());
    		Assertions.assertNotNull((WrapperCreateUltimatePreferencesResponse) retour.getBody());
    		Assertions.assertTrue(((WrapperCreateUltimatePreferencesResponse) retour.getBody()).retour);
    	} catch (Exception ex) {
    		log.error(ex);
			Assertions.fail(ex.getMessage());
    	}
	}

    @Test    
	void UltimatePreferencesCreateService_UPDATE_UFD_Test() throws ServiceException, BusinessErrorBlocBusinessException, SystemException {
    	String gin = "110000503906";
		String type = "FAVORITE_DESTINATION";
		String key = "COUNTRIES";
		String value = "TLN;NCE;CDG";
    	
		UltimatePreferencesCriteria cupm = new UltimatePreferencesCriteria();
    	    	
    	cupm.setRequestor(fillRequetorTest());
    	cupm.setGin(gin);
    	cupm.setType(type);
    	cupm.setActionCode("UPDATE");
    	
    	UltimatePreferencesModel upm = new UltimatePreferencesModel();
    	List<UltimatePreferencesModel> lupm = new ArrayList<UltimatePreferencesModel>();
    	upm.setKey(key);
    	upm.setValue(value);
    	lupm.add(upm);
    	cupm.setData(lupm);


		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response.setGin(gin);
		response.setSuccess(true);
		Preference pref = new Preference();
		pref.setGin(gin);
		pref.setType("UFD");
		pref.setPreferenceId(123L);
		when(createUpdateIndividualServiceV8.createIndividual(any())).thenReturn(response);
		when(preferenceRepository.findUltimatePreferencesByGin(gin)).thenReturn(Collections.singletonList(pref));
    	
    	try {
    		ResponseEntity<WrapperCreateUltimatePreferencesResponse> retour = ultimatePreferencesCreateService.manageUltimatePreferences(cupm);
    		Assertions.assertNotNull(retour);
    		Assertions.assertNotNull(retour.getBody());
    		Assertions.assertNotNull((WrapperCreateUltimatePreferencesResponse) retour.getBody());
    		Assertions.assertTrue(((WrapperCreateUltimatePreferencesResponse) retour.getBody()).retour);
    	} catch (Exception ex) {
    		log.error(ex);
			Assertions.fail(ex.getMessage());
    	}
	}

	@ParameterizedTest
	@CsvSource({
			"UFD, FAVORITE_DESTINATION, COUNTRIES",
			"UTS, TA_SERVICING, CUSTOMER_DETAILS",
			"UFB, FOOD_BEVERAGE, WELCOME_DRINK",
			"UOB, ON_BOARD, EXTRA_COMFORT_AMENITIES",
			"UST, SEATING, SEAT_NUMBER_BY_AIRCRAFT_CABIN_ALLERGY_SEAT",
			"ULO, LOUNGES, SPECIFIC_ATTENTION",
			"UCO, COMMUNICATION, PREFERRED_SPOKEN_LANGUAGE",
			"UPM, PRESS_MEDIA, NEWSPAPER",
			"UTF, TO_FROM_AIRPORT, OTHERS",
			"UMU, MILES_USAGE, UPGRADES_SERVICES_IN_MILES",
			"ULS, LEISURE, SPORT_ACTIVITY",
			"UFD, FAVORITE_DESTINATION, COUNTRIES",
	})
	void UltimatePreferencesCreateService_UPDATE(String prefType, String type, String key) throws ServiceException, BusinessErrorBlocBusinessException, SystemException {
		String gin = "110000503906";
		String value = "TLN;NCE;CDG";

		UltimatePreferencesCriteria cupm = new UltimatePreferencesCriteria();

		cupm.setRequestor(fillRequetorTest());
		cupm.setGin(gin);
		cupm.setType(type);
		cupm.setActionCode("UPDATE");

		UltimatePreferencesModel upm = new UltimatePreferencesModel();
		List<UltimatePreferencesModel> lupm = new ArrayList<UltimatePreferencesModel>();
		upm.setKey(key);
		upm.setValue(value);
		lupm.add(upm);
		cupm.setData(lupm);


		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response.setGin(gin);
		response.setSuccess(true);
		Preference pref = new Preference();
		pref.setGin(gin);
		pref.setType(prefType);
		pref.setPreferenceId(123L);
		when(createUpdateIndividualServiceV8.createIndividual(any())).thenReturn(response);
		when(preferenceRepository.findUltimatePreferencesByGin(gin)).thenReturn(Collections.singletonList(pref));

		try {
			ResponseEntity<WrapperCreateUltimatePreferencesResponse> retour = ultimatePreferencesCreateService.manageUltimatePreferences(cupm);
			Assertions.assertNotNull(retour);
			Assertions.assertNotNull(retour.getBody());
			Assertions.assertNotNull((WrapperCreateUltimatePreferencesResponse) retour.getBody());
			Assertions.assertTrue(((WrapperCreateUltimatePreferencesResponse) retour.getBody()).retour);
		} catch (Exception ex) {
			log.error(ex);
			Assertions.fail(ex.getMessage());
		}
	}
    
    @Test    
	void UltimatePreferencesCreatetService_DELETE_UFD_Test() throws ServiceException, BusinessErrorBlocBusinessException, SystemException {
    	String gin = "110000503906";
		String type = "FAVORITE_DESTINATION";
		String key = "COUNTRIES";
		// String value = "AMS";
    	
		UltimatePreferencesCriteria cupm = new UltimatePreferencesCriteria();
    	    	
    	cupm.setRequestor(fillRequetorTest());
    	cupm.setGin(gin);
    	cupm.setType(type);
    	cupm.setActionCode("DELETE");
    	
    	UltimatePreferencesModel upm = new UltimatePreferencesModel();
    	List<UltimatePreferencesModel> lupm = new ArrayList<UltimatePreferencesModel>();
    	upm.setKey(key);
    	// upm.setValue(value);
    	lupm.add(upm);
    	cupm.setData(lupm);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response.setGin(gin);
		response.setSuccess(true);
		Preference pref = new Preference();
		pref.setGin(gin);
		pref.setType("UFD");
		when(createUpdateIndividualServiceV8.createIndividual(any())).thenReturn(response);
		when(preferenceRepository.findUltimatePreferencesByGin(gin)).thenReturn(Collections.singletonList(pref));

    	try {
    		ResponseEntity<WrapperCreateUltimatePreferencesResponse> retour = ultimatePreferencesCreateService.manageUltimatePreferences(cupm);
    		Assertions.assertNotNull(retour);
    		Assertions.assertNotNull(retour.getBody());
    		Assertions.assertNotNull((WrapperCreateUltimatePreferencesResponse) retour.getBody());
    		Assertions.assertTrue(((WrapperCreateUltimatePreferencesResponse) retour.getBody()).retour);
    	} catch (Exception ex) {
    		log.error(ex);
			Assertions.fail(ex.getMessage());
    	}
	}

 
    
    @Test    
	void UltimatePreferencesCreateService_CREATE_UFD_NotUltimate_Test() throws ServiceException, BusinessErrorBlocBusinessException, SystemException {
    	String gin = "400309010322";
		String type = "FAVORITE_DESTINATION";
		String key = "COUNTRIES";
		String value = "TLN;NCE;CDG";
    	
		UltimatePreferencesCriteria cupm = new UltimatePreferencesCriteria();
    	    	
    	cupm.setRequestor(fillRequetorTest());
    	cupm.setGin(gin);
    	cupm.setType(type);
    	cupm.setActionCode("CREATE");
    	
    	UltimatePreferencesModel upm = new UltimatePreferencesModel();
    	List<UltimatePreferencesModel> lupm = new ArrayList<UltimatePreferencesModel>();
    	upm.setKey(key);
    	upm.setValue(value);
    	lupm.add(upm);
    	cupm.setData(lupm);

		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response.setGin(gin);
		response.setSuccess(true);
		when(createUpdateIndividualServiceV8.createIndividual(any())).thenReturn(response);

    	try {
    		ResponseEntity<WrapperCreateUltimatePreferencesResponse> retour = ultimatePreferencesCreateService.manageUltimatePreferences(cupm);
    		Assertions.assertNotNull(retour);
    		Assertions.assertNotNull(retour.getBody());
    		Assertions.assertNotNull((WrapperCreateUltimatePreferencesResponse) retour.getBody());
    		Assertions.assertTrue(((WrapperCreateUltimatePreferencesResponse) retour.getBody()).retour);
    	} catch (Exception ex) {
    		log.error(ex);
			Assertions.fail(ex.getMessage());
    	}
	}
    
    
    // Fill default value for REQSTOR
    private RequestorCriteria fillRequetorTest() {
    	RequestorCriteria requestor = new RequestorCriteria();
    	
    	requestor.setApplication("SIC");
    	requestor.setChannel("CHNL");
    	requestor.setCompany("AF");
    	requestor.setIpAddress("10.60.200.105");
    	requestor.setMatricule("T412211");
    	requestor.setOfficeId("OffID");    	
    	requestor.setSignature("UT_T412211");
    	requestor.setSite("QVI");
    	
    	return requestor;
    }
}
