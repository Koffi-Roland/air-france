package com.airfrance.repind.service.ws.internal.it;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import com.airfrance.repind.service.ws.internal.helpers.UltimateException;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualDSForPreferenceTest {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualDSForPreferenceTest.class);
	
	@Autowired
	private CreateOrUpdateIndividualDS createOrUpdateDS;
	
	// Add a travel doc to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddTravelDoc() throws JrafDomainException, JrafApplicativeException, UltimateException, UtfException, ParseException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequestDTO request = new CreateUpdateIndividualRequestDTO();
		
		// Individual
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		
		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformationsDTO(indInfo);
		request.setIndividualRequestDTO(indReq);
		
		// Requestor
		RequestorDTO req = new RequestorDTO();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestorDTO(req);
		
		// Preference = TravelDoc
		PreferenceRequestDTO preferenceRequest = new PreferenceRequestDTO();
		request.setPreferenceRequestDTO(preferenceRequest);
		
		PreferenceDTO preference = new PreferenceDTO();
		preferenceRequest.setPreferenceDTO(new ArrayList<PreferenceDTO>());
		preferenceRequest.getPreferenceDTO().add(preference);
		preference.setType("TDC");
		
		PreferenceDatasDTO preferenceDatas = new PreferenceDatasDTO();
		preference.setPreferencesDatasDTO(preferenceDatas);
		
		// key = type
		PreferenceDataDTO typeTdc = new PreferenceDataDTO();
		preferenceDatas.setPreferenceDataDTO(new ArrayList<PreferenceDataDTO>());
		preferenceDatas.getPreferenceDataDTO().add(typeTdc);
		typeTdc.setKey("type");
		typeTdc.setValue("PA");
		
		// key = number
		PreferenceDataDTO number = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(number);
		number.setKey("number");
		number.setValue("01AA00011122");
		
		// key = expirationDate
		PreferenceDataDTO expDate = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(expDate);
		expDate.setKey("expiryDate");
		expDate.setValue("31/12/2039");
		
		// key = countryIssued
		PreferenceDataDTO country = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(country);
		country.setKey("countryOfIssue");
		country.setValue("PR");
		
		// key = touchPoint
		PreferenceDataDTO touch = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(touch);
		touch.setKey("touchPoint");
		touch.setValue("ISI");
		
		// key = authorizationDate
		PreferenceDataDTO authorizationDate = new PreferenceDataDTO();
		preferenceDatas.getPreferenceDataDTO().add(authorizationDate);
		authorizationDate.setKey("authorizationDate");
		authorizationDate.setValue("31/07/2017");

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		// Execute test
		response = createOrUpdateDS.createOrUpdateV8(request, response);
		
		Assert.assertNotNull(response);
	}
}
