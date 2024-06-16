package com.afklm.repind.v8.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v8.createorupdateindividualws.CreateOrUpdateIndividualImplV8;
import com.afklm.repind.v8.createorupdateindividualws.transformers.PreferenceTransform;
import com.afklm.soa.stubs.w000413.v2.common.MarketingDataV2;
import com.afklm.soa.stubs.w000442.v8.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v8.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.PreferenceRequest;
import com.afklm.soa.stubs.w000442.v8.response.BusinessError;
import com.afklm.soa.stubs.w000442.v8.response.BusinessErrorBloc;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceDataV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceDatasV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.PreferenceV2;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.preference.PreferenceData;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualImplV8ForPreferenceTest {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualImplV8ForPreferenceTest.class);
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v8Bean")
	private CreateOrUpdateIndividualImplV8 createOrUpdateIndividualImplV8;

	@Autowired
	private PreferenceDS preferenceDS;
	
	// Add a travel doc to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddTravelDoc() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = TravelDoc
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("TDC");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = type
		PreferenceDataV2 typeTdc = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(typeTdc);
		typeTdc.setKey("type");
		typeTdc.setValue("PA");
		
		// key = number
		PreferenceDataV2 number = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(number);
		number.setKey("number");
		number.setValue("01AA00011122");
		
		// key = expirationDate
		PreferenceDataV2 expDate = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(expDate);
		expDate.setKey("expiryDate");
		expDate.setValue("31/12/2039");
		
		// key = countryIssued
		PreferenceDataV2 country = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(country);
		country.setKey("countryOfIssue");
		country.setValue("PR");
		
		// key = touchPoint
		PreferenceDataV2 touch = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(touch);
		touch.setKey("touchPoint");
		touch.setValue("ISI");
		
		// key = authorizationDate
		PreferenceDataV2 authorizationDate = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(authorizationDate);
		authorizationDate.setKey("authorizationDate");
		authorizationDate.setValue("31/07/2017");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testCreateOrUpdateIndividual_transformTravelPrefToBDM() throws JrafDomainException {
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		PreferenceV2 preference = new PreferenceV2();
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preferenceRequest.getPreference().add(preference);
		
		preference.setType("TPC");
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = departureCity
		PreferenceDataV2 depCity = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(depCity);
		depCity.setKey("departureCity");
		depCity.setValue("TST");
		
		// key = departureAirport
		PreferenceDataV2 depApt = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(depApt);
		depApt.setKey("departureAirport");
		depApt.setValue("TST");
		
		// key = arrivalCity
		PreferenceDataV2 arrivalCity = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalCity);
		arrivalCity.setKey("arrivalCity");
		arrivalCity.setValue("TST");
		
		// key = arrivalAirport
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("arrivalAirport");
		arrivalAirport.setValue("TST");
		
		// key = meal
		PreferenceDataV2 meal = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(meal);
		meal.setKey("meal");
		meal.setValue("T");
		
		// key = seat
		PreferenceDataV2 seat = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(seat);
		seat.setKey("seat");
		seat.setValue("T");
		
		// key = travelClass
		PreferenceDataV2 travelClass = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(travelClass);
		travelClass.setKey("travelClass");
		travelClass.setValue("T");
		
		// key = boardingPass
		PreferenceDataV2 boardingPass = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(boardingPass);
		boardingPass.setKey("boardingPass");
		boardingPass.setValue("Y");
		
		// key = customerLeisure
		PreferenceDataV2 customerLeisure = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(customerLeisure);
		customerLeisure.setKey("customerLeisure");
		customerLeisure.setValue("TST");
		
		MarketingDataV2 result = PreferenceTransform.transformToBDM(preferenceRequest);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getTravelPreferences());
		Assert.assertEquals("TST", result.getTravelPreferences().getArrivalCity());
		Assert.assertEquals("TST", result.getTravelPreferences().getArrivalAirport());
		Assert.assertEquals("TST", result.getTravelPreferences().getDepartureAirport());
		Assert.assertEquals("TST", result.getTravelPreferences().getDepartureCity());
		Assert.assertEquals("T", result.getTravelPreferences().getMeal());
		Assert.assertEquals("T", result.getTravelPreferences().getSeat());
		Assert.assertEquals("T", result.getTravelPreferences().getTravelClass());
		Assert.assertEquals("Y", result.getTravelPreferences().getBoardingPass());
		Assert.assertEquals("TST", result.getTravelPreferences().getCustomerLeisure());
	}
	
	@Test
	public void testCreateOrUpdateIndividual_transformEmergencyContactToBDM() throws JrafDomainException {
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		PreferenceV2 preference = new PreferenceV2();
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preferenceRequest.getPreference().add(preference);
		
		preference.setType("ECC");
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = email
		PreferenceDataV2 email = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(email);
		email.setKey("email");
		email.setValue("tst@mail.test");
		
		// key = phoneNumber
		PreferenceDataV2 phoneNumber = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(phoneNumber);
		phoneNumber.setKey("phoneNumber");
		phoneNumber.setValue("+33111222333");
		
		// key = firstName
		PreferenceDataV2 firstName = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(firstName);
		firstName.setKey("firstName");
		firstName.setValue("testU RI");
		
		// key = lastName
		PreferenceDataV2 lastName = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(lastName);
		lastName.setKey("lastName");
		lastName.setValue("testU RI");
		
		MarketingDataV2 result = PreferenceTransform.transformToBDM(preferenceRequest);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getEmergencyContactList());
		Assert.assertEquals(1, result.getEmergencyContactList().size());
		Assert.assertEquals("tst@mail.test", result.getEmergencyContactList().get(0).getEmail());
		Assert.assertEquals("testU RI", result.getEmergencyContactList().get(0).getFirstName());
		Assert.assertEquals("testU RI", result.getEmergencyContactList().get(0).getLastName());
		Assert.assertEquals("+33111222333", result.getEmergencyContactList().get(0).getPhoneNumber());
	}
	
	
	@Test
	public void testCreateOrUpdateIndividual_transformHandicapToBDM() throws ParseException, JrafDomainException {
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		PreferenceV2 preference = new PreferenceV2();
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preferenceRequest.getPreference().add(preference);
		
		preference.setType("HDC");
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CodeHCP1
		PreferenceDataV2 CodeHCP1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(CodeHCP1);
		CodeHCP1.setKey("CodeHCP1");
		CodeHCP1.setValue("BLND");
		
		// key = CodeHCP2
		PreferenceDataV2 CodeHCP2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(CodeHCP2);
		CodeHCP2.setKey("CodeHCP2");
		CodeHCP2.setValue("MEDA");
		
		// key = CodeHCP3
		PreferenceDataV2 CodeHCP3 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(CodeHCP3);
		CodeHCP3.setKey("CodeHCP3");
		CodeHCP3.setValue("WCHR");
		
		// key = MedaCCFlag
		PreferenceDataV2 MedaCCFlag = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaCCFlag);
		MedaCCFlag.setKey("MedaCCFlag");
		MedaCCFlag.setValue("Y");
		
		// key = MedaCCDateStart
		PreferenceDataV2 MedaCCDateStart = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaCCDateStart);
		MedaCCDateStart.setKey("MedaCCDateStart");
		MedaCCDateStart.setValue("12/12/2017");
		
		// key = MedaCCDateEnd
		PreferenceDataV2 MedaCCDateEnd = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaCCDateEnd);
		MedaCCDateEnd.setKey("MedaCCDateEnd");
		MedaCCDateEnd.setValue("31/12/2020");
		
		// key = MedaCCAccomp
		PreferenceDataV2 MedaCCAccomp = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaCCAccomp);
		MedaCCAccomp.setKey("MedaCCAccomp");
		MedaCCAccomp.setValue("N");
		
		// key = MedaCCTxt
		PreferenceDataV2 MedaCCTxt = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaCCTxt);
		MedaCCTxt.setKey("MedaCCTxt");
		MedaCCTxt.setValue(" ");
		
		// key = MedaMCFlag
		PreferenceDataV2 MedaMCFlag = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaMCFlag);
		MedaMCFlag.setKey("MedaMCFlag");
		MedaMCFlag.setValue("Y");
		
		// key = MedaMCDateStart
		PreferenceDataV2 MedaMCDateStart = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaMCDateStart);
		MedaMCDateStart.setKey("MedaMCDateStart");
		MedaMCDateStart.setValue("01/01/2015");
		
		// key = MedaMCDateEnd
		PreferenceDataV2 MedaMCDateEnd = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaMCDateEnd);
		MedaMCDateEnd.setKey("MedaMCDateEnd");
		MedaMCDateEnd.setValue("01/12/2025");
		
		// key = MedaMCAccomp
		PreferenceDataV2 MedaMCAccomp = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaMCAccomp);
		MedaMCAccomp.setKey("MedaMCAccomp");
		MedaMCAccomp.setValue("O");
		
		// key = MedaMCTxt
		PreferenceDataV2 MedaMCTxt = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(MedaMCTxt);
		MedaMCTxt.setKey("MedaMCTxt");
		MedaMCTxt.setValue("DATA");
				
		MarketingDataV2 result = PreferenceTransform.transformToBDM(preferenceRequest);
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getHandicap());
		Assert.assertEquals("BLND", result.getHandicap().getCodeHCP1());
		Assert.assertEquals("MEDA", result.getHandicap().getCodeHCP2());
		Assert.assertEquals("WCHR", result.getHandicap().getCodeHCP3());
		Assert.assertEquals("Y", result.getHandicap().getMedaCCFlag());
		Assert.assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2017"), result.getHandicap().getMedaCCDateStart());
		Assert.assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2020"), result.getHandicap().getMedaCCDateEnd());
		Assert.assertEquals("N", result.getHandicap().getMedaCCAccomp());
		Assert.assertEquals(" ", result.getHandicap().getMedaCCTxt());
		Assert.assertEquals("Y", result.getHandicap().getMedaMCFlag());
		Assert.assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2015"), result.getHandicap().getMedaMCDateStart());
		Assert.assertEquals(new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2025"), result.getHandicap().getMedaMCDateEnd());
		Assert.assertEquals("O", result.getHandicap().getMedaMCAccomp());
		Assert.assertEquals("DATA", result.getHandicap().getMedaMCTxt());
	}
	
	
	// Add a PIC to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddPIC() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = TravelDoc
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("PIC");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = bluebizNumber
		PreferenceDataV2 bluebizNumber = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(bluebizNumber);
		bluebizNumber.setKey("bluebizNumber");
		bluebizNumber.setValue("10");
		
		// key = FFPNumber
		PreferenceDataV2 FFPNumber = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(FFPNumber);
		FFPNumber.setKey("FFPNumber");
		FFPNumber.setValue("20");
		
		// key = FFPProgram
		PreferenceDataV2 FFPProgram = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(FFPProgram);
		FFPProgram.setKey("FFPProgram");
		FFPProgram.setValue("30");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}
	
	// Add a TCC to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddTCC() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = TravelDoc
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("TCC");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = civility
		PreferenceDataV2 civility = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(civility);
		civility.setKey("civility");
		civility.setValue("MR");
		
		// key = dateOfBirth
		PreferenceDataV2 dateOfBirth = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(dateOfBirth);
		dateOfBirth.setKey("dateOfBirth");
		dateOfBirth.setValue("20/01/1980");
		
		// key = email
		PreferenceDataV2 email = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(email);
		email.setKey("email");
		email.setValue("test@af.com");
		
		// key = firstName
		PreferenceDataV2 firstName = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(firstName);
		firstName.setKey("firstName");
		firstName.setValue("Test");
		
		// key = lastName
		PreferenceDataV2 lastName = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(lastName);
		lastName.setKey("lastName");
		lastName.setValue("Test");
		
		// key = bluebizNumber
		PreferenceDataV2 bluebizNumber = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(bluebizNumber);
		bluebizNumber.setKey("bluebizNumber");
		bluebizNumber.setValue("12345678");							// IC-545 : Number must be on 8
		
		// key = FFPNumber
		PreferenceDataV2 FFPNumber = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(FFPNumber);
		FFPNumber.setKey("FFPNumber");
		FFPNumber.setValue("20");
		
		// key = FFPProgram
		PreferenceDataV2 FFPProgram = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(FFPProgram);
		FFPProgram.setKey("FFPProgram");
		FFPProgram.setValue("30");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}
	
	// Add a HDC to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddHDC() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = TravelDoc
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("HDC");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = codeHCP1
		PreferenceDataV2 codeHCP1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(codeHCP1);
		codeHCP1.setKey("codeHCP1");
		codeHCP1.setValue("WCHR");
		
		// key = codeHCP2
		PreferenceDataV2 codeHCP2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(codeHCP2);
		codeHCP2.setKey("codeHCP2");
		codeHCP2.setValue("WCHR");
		
		// key = codeHCP3
		PreferenceDataV2 codeHCP3 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(codeHCP3);
		codeHCP3.setKey("codeHCP3");
		codeHCP3.setValue("WCHR");
		
		// key = codeMat1
		PreferenceDataV2 codeMat1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(codeMat1);
		codeMat1.setKey("codeMat1");
		codeMat1.setValue("WCMP");
		
		// key = codeMat2
		PreferenceDataV2 codeMat2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(codeMat2);
		codeMat2.setKey("codeMat2");
		codeMat2.setValue("WCMP");
		
		// key = dogGuideBreed
		PreferenceDataV2 dogGuideBreed = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(dogGuideBreed);
		dogGuideBreed.setKey("dogGuideBreed");
		dogGuideBreed.setValue("Labrador");
		
		// key = dogGuideFlag
		PreferenceDataV2 dogGuideFlag = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(dogGuideFlag);
		dogGuideFlag.setKey("dogGuideFlag");
		dogGuideFlag.setValue("Y");
		
		// key = dogGuideWeight
		PreferenceDataV2 dogGuideWeight = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(dogGuideWeight);
		dogGuideWeight.setKey("dogGuideWeight");
		dogGuideWeight.setValue("15");
		
		// key = height1
		PreferenceDataV2 height1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(height1);
		height1.setKey("height1");
		height1.setValue("100");
		
		// key = height2
		PreferenceDataV2 height2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(dogGuideWeight);
		height2.setKey("height2");
		height2.setValue("100");
		
		// key = heightPlie1
		PreferenceDataV2 heightPlie1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(heightPlie1);
		heightPlie1.setKey("heightPlie1");
		heightPlie1.setValue("100");
		
		// key = heightPlie2
		PreferenceDataV2 heightPlie2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(heightPlie2);
		heightPlie2.setKey("heightPlie2");
		heightPlie2.setValue("100");
		
		// key = length1
		PreferenceDataV2 length1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(length1);
		length1.setKey("length1");
		length1.setValue("100");
		
		// key = length2
		PreferenceDataV2 length2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(length2);
		length2.setKey("length2");
		length2.setValue("100");
		
		// key = lengthPlie1
		PreferenceDataV2 lengthPlie1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(lengthPlie1);
		lengthPlie1.setKey("lengthPlie1");
		lengthPlie1.setValue("100");
		
		// key = lengthPlie2
		PreferenceDataV2 lengthPlie2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(lengthPlie2);
		lengthPlie2.setKey("lengthPlie2");
		lengthPlie2.setValue("100");
		
		// key = medaCCAccomp
		PreferenceDataV2 medaCCAccomp = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaCCAccomp);
		medaCCAccomp.setKey("medaCCAccomp");
		medaCCAccomp.setValue("A");
		
		// key = medaCCDateEnd
		PreferenceDataV2 medaCCDateEnd = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaCCDateEnd);
		medaCCDateEnd.setKey("medaCCDateEnd");
		medaCCDateEnd.setValue("31/12/2018");
		
		// key = medaCCDateStart
		PreferenceDataV2 medaCCDateStart = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaCCDateStart);
		medaCCDateStart.setKey("medaCCDateStart");
		medaCCDateStart.setValue("01/01/2018");
		
		// key = medaCCFlag
		PreferenceDataV2 medaCCFlag = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaCCFlag);
		medaCCFlag.setKey("medaCCFlag");
		medaCCFlag.setValue("Y");
		
		// key = medaCCTxt
		PreferenceDataV2 medaCCTxt = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaCCTxt);
		medaCCTxt.setKey("medaCCTxt");
		medaCCTxt.setValue("Text");
		
		// key = medaLCAccomp
		PreferenceDataV2 medaLCAccomp = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaLCAccomp);
		medaLCAccomp.setKey("medaLCAccomp");
		medaLCAccomp.setValue("A");
		
		// key = medaLCDateEnd
		PreferenceDataV2 medaLCDateEnd = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaLCDateEnd);
		medaLCDateEnd.setKey("medaLCDateEnd");
		medaLCDateEnd.setValue("31/12/2018");
		
		// key = medaLCDateStart
		PreferenceDataV2 medaLCDateStart = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaLCDateStart);
		medaLCDateStart.setKey("medaLCDateStart");
		medaLCDateStart.setValue("01/01/2018");
		
		// key = medaLCFlag
		PreferenceDataV2 medaLCFlag = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaLCFlag);
		medaLCFlag.setKey("medaLCFlag");
		medaLCFlag.setValue("Y");
		
		// key = medaLCTxt
		PreferenceDataV2 medaLCTxt = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaLCTxt);
		medaLCTxt.setKey("medaLCTxt");
		medaLCTxt.setValue("Text");
		
		// key = medaMCAccomp
		PreferenceDataV2 medaMCAccomp = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaMCAccomp);
		medaMCAccomp.setKey("medaMCAccomp");
		medaMCAccomp.setValue("A");
		
		// key = medaMCDateEnd
		PreferenceDataV2 medaMCDateEnd = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaMCDateEnd);
		medaMCDateEnd.setKey("medaMCDateEnd");
		medaMCDateEnd.setValue("31/12/2018");
		
		// key = medaMCDateStart
		PreferenceDataV2 medaMCDateStart = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaMCDateStart);
		medaMCDateStart.setKey("medaMCDateStart");
		medaMCDateStart.setValue("01/01/2018");
		
		// key = medaMCFlag
		PreferenceDataV2 medaMCFlag = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaMCFlag);
		medaMCFlag.setKey("medaMCFlag");
		medaMCFlag.setValue("F");
		
		// key = medaMCTxt
		PreferenceDataV2 medaMCTxt = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(medaMCTxt);
		medaMCTxt.setKey("medaMCTxt");
		medaMCTxt.setValue("Text");
		
		// key = otherMat
		PreferenceDataV2 otherMat = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(otherMat);
		otherMat.setKey("otherMat");
		otherMat.setValue("other");
		
		// key = oxygFlag
		PreferenceDataV2 oxygFlag = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(oxygFlag);
		oxygFlag.setKey("oxygFlag");
		oxygFlag.setValue("Y");
		
		// key = oxygOutput
		PreferenceDataV2 oxygOutput = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(oxygOutput);
		oxygOutput.setKey("oxygOutput");
		oxygOutput.setValue("Y");
		
		// key = weight1
		PreferenceDataV2 weight1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(weight1);
		weight1.setKey("weight1");
		weight1.setValue("100");
		
		// key = weight2
		PreferenceDataV2 weight2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(weight2);
		weight2.setKey("weight2");
		weight2.setValue("200");
		
		// key = width1
		PreferenceDataV2 width1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(width1);
		width1.setKey("width1");
		width1.setValue("100");
		
		// key = width2
		PreferenceDataV2 width2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(width2);
		width2.setKey("width2");
		width2.setValue("200");
		
		// key = widthPlie1
		PreferenceDataV2 widthPlie1 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(widthPlie1);
		widthPlie1.setKey("widthPlie1");
		widthPlie1.setValue("100");
		
		// key = widthPlie2
		PreferenceDataV2 widthPlie2 = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(widthPlie2);
		widthPlie2.setKey("widthPlie2");
		widthPlie2.setValue("200");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}
	
	// Add a TPC to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddTPC() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = TravelDoc
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("TPC");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = arrivalAirport
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("arrivalAirport");
		arrivalAirport.setValue("NCE");
		
		// key = arrivalCity
		PreferenceDataV2 arrivalCity = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalCity);
		arrivalCity.setKey("arrivalCity");
		arrivalCity.setValue("NCE");
		
		// key = boardingPass
		PreferenceDataV2 boardingPass = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(boardingPass);
		boardingPass.setKey("boardingPass");
		boardingPass.setValue("10");
		
		// key = customerLeisure
		PreferenceDataV2 customerLeisure = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(customerLeisure);
		customerLeisure.setKey("customerLeisure");
		customerLeisure.setValue("Test");
		
		// key = departureAirport
		PreferenceDataV2 departureAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(departureAirport);
		departureAirport.setKey("departureAirport");
		departureAirport.setValue("NCE");
		
		// key = departureCity
		PreferenceDataV2 departureCity = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(departureCity);
		departureCity.setKey("departureCity");
		departureCity.setValue("NCE");
		
		// key = meal
		PreferenceDataV2 meal = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(meal);
		meal.setKey("meal");
		meal.setValue("Salt");
		
		// key = seat
		PreferenceDataV2 seat = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(seat);
		seat.setKey("seat");
		seat.setValue("W");
		
		// key = travelClass
		PreferenceDataV2 travelClass = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(travelClass);
		travelClass.setKey("travelClass");
		travelClass.setValue("E");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}
	
	// Add a ECC to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddECC() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400424668522");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = TravelDoc
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("ECC");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = arrivalAirport
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("email");
		arrivalAirport.setValue("test@af.com");
		
		// key = arrivalCity
		PreferenceDataV2 arrivalCity = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalCity);
		arrivalCity.setKey("firstName");
		arrivalCity.setValue("Test");
		
		// key = boardingPass
		PreferenceDataV2 boardingPass = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(boardingPass);
		boardingPass.setKey("lastName");
		boardingPass.setValue("Test");
		
		// key = customerLeisure
		PreferenceDataV2 customerLeisure = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(customerLeisure);
		customerLeisure.setKey("phoneNumber");
		customerLeisure.setValue("0123456");
				
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}



	// Add a UCO to a customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUCO() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400255047252");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UCO");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("checkInChannel");
		arrivalAirport.setValue("TheCheckInChannel");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}
	
	// Add a UCO to a customer
	@Test
	@Transactional
 	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUCOBis() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("110000971134");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UCO");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("checkInChannel");
		arrivalAirport.setValue("TheCheckInChannel");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}

	// Add a UCO to a Non ULTIMATE customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUCO_For_NonUltimateCusto() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221750");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UCO");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("checkInChannel");
		arrivalAirport.setValue("TheCheckInChannel");

		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);

		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
		

	}

	// Add a UCO to a ULTIMATE customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUCO_For_UltimateCusto() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UCO");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("checkInChannel");
		arrivalAirport.setValue("TheCheckInChannel");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}


	// Add a UCO to a ULTIMATE customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFB_With_REFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFB");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("SPECIALMEALPAIDMEALS");
		arrivalAirport.setValue("MOML");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}


	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFB_With_KOREFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFB");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("SPECIALMEALPAIDMEALS");
		arrivalAirport.setValue("NotValid");
		
		try {
			createOrUpdateIndividualImplV8.createIndividual(request);
					
			Assert.fail("On devrait avoir une PARAMETER exception");
			
		} catch (BusinessErrorBlocBusinessException ex) {
			
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Unknown value for the key SPECIALMEALPAIDMEALS", be.getErrorDetail());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFB_With_IncorrectREFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFB");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("SPECIALUNITTEST");
		arrivalAirport.setValue("NotValid");
		
		try {
			createOrUpdateIndividualImplV8.createIndividual(request);
					
			Assert.fail("On devrait avoir une PARAMETER exception");
			
		} catch (BusinessErrorBlocBusinessException ex) {
			
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Unknown preference data key: SPECIALUNITTEST", be.getErrorDetail());
		}
	}

	// Add a UST to a ULTIMATE customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUST_With_REFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UST
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UST");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("ROWAISLEWINDOW");
		arrivalAirport.setValue("W");				// WINDOW
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}

	// Add a UCO to a ULTIMATE customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUCO_With_REFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UCO");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("PREFERREDSPOKENLANGUAGE");
		arrivalAirport.setValue("NL");						// NL
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}

	// Add a UFD to a ULTIMATE customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFD_With_REFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UFD
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFD");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("COUNTRIES");
		arrivalAirport.setValue("IT");						// IT
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}

	// Add a UFD to a ULTIMATE customer
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFD_With_MultipleREFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UFD
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFD");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("COUNTRIES");
		arrivalAirport.setValue("FR;IT");						// FR & IT
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}


	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFD_With_REFTable_MINUS() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UFD
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFD");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("COUNTRIES");
		arrivalAirport.setValue("fr");				
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFD_With_LastKOREFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCD
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFD");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("COUNTRIES");
		arrivalAirport.setValue("FR;AA");							// Second failed
		
		try {
			createOrUpdateIndividualImplV8.createIndividual(request);
					
			Assert.fail("On devrait avoir une PARAMETER exception");
			
		} catch (BusinessErrorBlocBusinessException ex) {
			
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Unknown value for the key COUNTRIES", be.getErrorDetail());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFD_With_FirstKOREFTable() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCD
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFD");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("COUNTRIES");
		arrivalAirport.setValue("AA;FR");							// First failed
		
		try {
			createOrUpdateIndividualImplV8.createIndividual(request);
					
			Assert.fail("On devrait avoir une PARAMETER exception");
			
		} catch (BusinessErrorBlocBusinessException ex) {
			
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Unknown value for the key COUNTRIES", be.getErrorDetail());
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFD_With_REFTable_EMPTY() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UFD
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UFD");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("COUNTRIES");
		arrivalAirport.setValue(";;;");				
	
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
			Assert.fail("On devrait avoir une PARAMETER exception");
		
		} catch (BusinessErrorBlocBusinessException ex) {
			
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Unknown value for the key COUNTRIES", be.getErrorDetail());
		}
	}


	@Test
 	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUST_With_REFTable_SINGLE() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UST
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UST");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = cabinSeatingDay
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("cabinSeatingDay");
		arrivalAirport.setValue("A;W");
		
		try {
			
			createOrUpdateIndividualImplV8.createIndividual(request);
			
			Assert.fail("On devrait avoir une PARAMETER exception");
		
		} catch (BusinessErrorBlocBusinessException ex) {
			
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Invalid occurence 2 for the key cabinSeatingDay", be.getErrorDetail());
		}
	}
	

	@Test
 	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUST_With_REFTable_SINGLE_Night() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UST
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UST");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = cabinSeatingDay
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("cabinSeatingNight");
		arrivalAirport.setValue("U");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull( response);
		Assert.assertTrue(response.isSuccess());	
	}

	@Test
 	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_UpdateUST_With_REFTable_SINGLE_Night() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UST
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UST");
		preference.setId("34789791");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = cabinSeatingDay
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("cabinSeatingNight");
		arrivalAirport.setValue("T");
		
		try {
			
			createOrUpdateIndividualImplV8.createIndividual(request);
			
			Assert.fail("On devrait avoir une PARAMETER exception");
		
		} catch (BusinessErrorBlocBusinessException ex) {
			
			Assert.assertNotNull(ex);
			BusinessErrorBloc beb = (BusinessErrorBloc) ex.getFaultInfo() ;
			Assert.assertNotNull(beb);
			BusinessError be = (BusinessError) beb.getBusinessError();
			Assert.assertEquals("ERROR_932", be.getErrorCode().toString());
			Assert.assertEquals("INVALID PARAMETER", be.getErrorLabel());
			Assert.assertEquals("Invalid parameter: Unknown value for the key cabinSeatingNight", be.getErrorDetail());
		}
	}
	
	@Test
  	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUST_With_String() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UST
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UST");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = cabinSeatingDay
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("avoidOnMediumHaul");
		arrivalAirport.setValue("Rien de tout cela");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());		
	}

	@Test
 	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_UpdateUST_With_String() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UST
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UST");
		preference.setId("34789787");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = cabinSeatingDay
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
				
		arrivalAirport.setKey("avoidOnMediumHaul");
		arrivalAirport.setValue("Rien de tout cela, on change");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}

	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividual_AddUFD_With_MULTIPLE_Random() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
			
		indInfo.setIdentifier("400000221794");				// 400000221794		ULTI
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UFD
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UCO");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		
		// key = CheckInChannel
		PreferenceDataV2 arrivalAirport = new PreferenceDataV2();
		preferenceDatas.getPreferenceData().add(arrivalAirport);
		arrivalAirport.setKey("preferredSpokenLanguage");
		arrivalAirport.setValue("  ;; ; NL;;");				
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response.isSuccess());
	}

	// Delete UTS of a customer
	@Test
	@Transactional
 	@Rollback(true)
	public void testCreateOrUpdateIndividual_Delete_UTS() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("110000230906");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType("UTS");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		preference.setPreferenceDatas(preferenceDatas);
		preference.setId("34726882");
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}

	// Delete a UCO of a customer as a Last Key existing... we must delete the entire UCO
	@Test
	@Transactional
 	@Rollback(true)
	public void testCreateOrUpdateIndividual_Delete_UCO_Last_TA() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		logger.info("Test start...");

		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		indInfo.setIdentifier("400255047252");
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);
		
		// Requestor
		RequestorV2 req = new RequestorV2();
		
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test U w000442v8");
		request.setRequestor(req);
		
		// Preference = UCO
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setId("34732478");
		preference.setType("UCO");
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		PreferenceDataV2 preferenceData = new PreferenceDataV2();
		preferenceData.setKey("checkInChannel");
		preferenceDatas.getPreferenceData().add(preferenceData);
		preference.setPreferenceDatas(preferenceDatas);
		
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		
		Assert.assertNotNull(response);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateOrUpdateIndividualAddNewPref()
			throws BusinessErrorBlocBusinessException, JrafDomainException { // REPIND-2170
		logger.info("Test start...");
		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		
		// Individual
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		final String gin = "400424668522";
		final String desType = "DES";
		final String preferenceDataKey = "ICE";
		final String preferenceDataValue = "Glace a la vanille";

		indInfo.setIdentifier(gin);
		indReq.setIndividualInformations(indInfo);
		request.setIndividualRequest(indReq);

		// Requestor
		RequestorV2 req = new RequestorV2();
		req.setChannel("B2C");
		req.setApplicationCode("ISI");
		req.setSite("QVI");
		req.setSignature("Test");
		request.setRequestor(req);
		
		// Preference = DES
		PreferenceRequest preferenceRequest = new PreferenceRequest();
		request.setPreferenceRequest(preferenceRequest);
		
		PreferenceV2 preference = new PreferenceV2();
		preferenceRequest.getPreference().add(preference);
		preference.setType(desType);
		
		PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
		PreferenceDataV2 preferenceData = new PreferenceDataV2();
		preferenceData.setKey(preferenceDataKey);
		preferenceData.setValue(preferenceDataValue);
		preferenceDatas.getPreferenceData().add(preferenceData);
		preference.setPreferenceDatas(preferenceDatas);
		
		CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV8.createIndividual(request);
		Assert.assertTrue(response.isSuccess());

		List<Preference> preferenceList = preferenceDS.findByGinAndType(gin, desType);
		Preference preferenceFromDB = preferenceList.get(0);
		Set<PreferenceData> preferenceDataSetFromDB = preferenceFromDB.getPreferenceData();
		Hibernate.initialize(preferenceDataSetFromDB);
		PreferenceData preferenceDataFromDB = preferenceDataSetFromDB.iterator().next();
		Assert.assertEquals(preferenceDataFromDB.getKey(), preferenceDataKey);
		Assert.assertEquals(preferenceDataFromDB.getValue(), preferenceDataValue);
	}
}
