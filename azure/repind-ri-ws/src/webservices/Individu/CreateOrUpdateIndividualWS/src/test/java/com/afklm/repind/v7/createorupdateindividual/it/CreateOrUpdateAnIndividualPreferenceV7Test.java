package com.afklm.repind.v7.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v7.createorupdateindividualws.CreateOrUpdateIndividualImplV7;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v7.request.MarketingDataRequest;
import com.afklm.soa.stubs.w000442.v7.request.PreferenceDataRequest;
import com.afklm.soa.stubs.w000442.v7.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v7.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Telecom;
import com.afklm.soa.stubs.w000442.v7.sicmarketingtype.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.preference.PreferenceTransform;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.preference.PreferenceData;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class CreateOrUpdateAnIndividualPreferenceV7Test {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualPreferenceV7Test.class);
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v7Bean")
	private CreateOrUpdateIndividualImplV7 createOrUpdateIndividualImplV7;
	
	@Autowired
	PreferenceRepository preferenceRepository;
	
	@Autowired
	PreferenceDS preferenceDS;

	String ginTest = "900025086459";
	String typeTest = "TDC";
	String subTypeTest = "PA";
	String docNumberTest = "0000AAAATestU";
	
	// Initialisation of Database
	@Transactional
	private void initDatabaseForTest() {
		PreferenceDTO prefDTO = new PreferenceDTO();
		Set<PreferenceDataDTO> prefDataDTO = new HashSet<PreferenceDataDTO>();

		prefDTO.setGin(ginTest);
		prefDTO.setType(typeTest);
		prefDTO.setPreferenceDataDTO(prefDataDTO);
		
		prefDataDTO.add(new PreferenceDataDTO("type", subTypeTest));
		prefDataDTO.add(new PreferenceDataDTO("number", docNumberTest));
		prefDataDTO.add(new PreferenceDataDTO("expiryDate", "31/12/2020"));
		prefDataDTO.add(new PreferenceDataDTO("nationality", "FR"));
		prefDataDTO.add(new PreferenceDataDTO("issueDate", "01/06/2016"));
		prefDataDTO.add(new PreferenceDataDTO("countryOfIssue", "FR"));
		prefDataDTO.add(new PreferenceDataDTO("authorizationDate", "01/06/2017"));
		prefDataDTO.add(new PreferenceDataDTO("touchPoint", "TEST U"));
		
		try {
			preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDTO));
		} catch (JrafDomainException e) {
			logger.error(e.getMessage());
		}
	}
	
	// Init webservice requestor fields
	private RequestorV2 initRequestor() {
		RequestorV2 requestor = new RequestorV2();
		requestor.setApplicationCode("ISI");
		requestor.setChannel("B2C");
		requestor.setSignature("TestU");
		requestor.setSite("VLB");
		
		return requestor;
	}
	
	// Init webservice individual fields
	private IndividualRequest initIndividualInfo() {
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformationsV3 indInfos = new IndividualInformationsV3();
		indReq.setIndividualInformations(indInfos);
		indInfos.setIdentifier(ginTest);
		
		return indReq;
	}
	
	// Init date
	private Date getTestDate() throws ParseException {
		String sDate = "01/01/2018";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.parse(sDate);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 15 secondes
	public void createOrUpdateIndividualV7_createExistingTravelDocTest() throws ParseException {
		initDatabaseForTest();
		
		// Init WS input 
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndividualInfo());
		
		MarketingDataRequest mdRequest = new MarketingDataRequest();
		MarketingInformation mdInfo = new MarketingInformation();
		mdRequest.setMarketingInformation(mdInfo);
		request.setMarketingDataRequest(mdRequest);
		
		MaccTravelDocument travelDoc = new MaccTravelDocument();
		mdInfo.getTravelDocument().add(travelDoc);
		travelDoc.setType(subTypeTest);
		travelDoc.setNumber(docNumberTest);
		travelDoc.setCountryIssued("UK");
		travelDoc.setExpirationDate(getTestDate());
		
		// Execute test
		try {
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV7.createIndividual(request);
			
			List<Preference> prefList = preferenceDS.findByGinAndTypeAndNumber(ginTest, typeTest, docNumberTest);
			String foundCountryOfIssue = "";
			
			for (Preference foundPref: prefList) {
				if (foundPref.getPreferenceData() != null) {
					for (PreferenceData foundPrefData : foundPref.getPreferenceData()) {
						if ("countryOfIssue".equalsIgnoreCase(foundPrefData.getKey())) {
							foundCountryOfIssue = foundPrefData.getValue();
							break;
						}
					}
				}
			}
			
			Assert.assertNotNull(prefList);
			Assert.assertEquals("UK", foundCountryOfIssue);
			
		} catch (BusinessErrorBlocBusinessException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV7_createPICTest() throws ParseException, BusinessErrorBlocBusinessException {		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndividualInfo());
		
		MaccPersonalInformation maccPersonalInformation = new MaccPersonalInformation();
		maccPersonalInformation.setBlueBizNumber("10");
		maccPersonalInformation.setFFPNumber("10");
		maccPersonalInformation.setFFPProgram("10");
		
		MarketingDataRequest mdRequest = new MarketingDataRequest();
		MarketingInformation mdInfo = new MarketingInformation();
		mdInfo.setPersonalInformation(maccPersonalInformation);
		mdRequest.setMarketingInformation(mdInfo);
		request.setMarketingDataRequest(mdRequest);

		createOrUpdateIndividualImplV7.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "PIC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(3, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV7_createTCCTest() throws ParseException, BusinessErrorBlocBusinessException {		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndividualInfo());
		
		MaccTravelCompanion maccTravelCompanion = new MaccTravelCompanion();
		maccTravelCompanion.setCivility("MR");
		maccTravelCompanion.setDateOfBirth(new Date());
		maccTravelCompanion.setEmail("test@af.com");
		maccTravelCompanion.setFirstName("Test");
		maccTravelCompanion.setLastName("Test");
		
		MaccPersonalInformation maccPersonalInformation = new MaccPersonalInformation();
		maccPersonalInformation.setBlueBizNumber("12345678");		// SIC-545 : Number must be on 8
		maccPersonalInformation.setFFPNumber("10");
		maccPersonalInformation.setFFPProgram("10"); 
		
		maccTravelCompanion.setPersonalInformation(maccPersonalInformation);
		
		MarketingDataRequest mdRequest = new MarketingDataRequest();
		MarketingInformation mdInfo = new MarketingInformation();
		mdInfo.getTravelCompanion().add(maccTravelCompanion);
		mdRequest.setMarketingInformation(mdInfo);
		request.setMarketingDataRequest(mdRequest);

		createOrUpdateIndividualImplV7.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "TCC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(8, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV7_createHDCTest() throws ParseException, BusinessErrorBlocBusinessException {		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndividualInfo());
		
		MaccHandicap maccHandicap = new MaccHandicap();
		maccHandicap.setCodeHCP1("WCHR");
		maccHandicap.setCodeHCP2("WCHR");
		maccHandicap.setCodeHCP3("WCHR");
		maccHandicap.setCodeMat1("WCMP");
		maccHandicap.setCodeMat2("WCMP");
		maccHandicap.setDogGuideBreed("Labrador");
		maccHandicap.setDogGuideFlag("Y");
		maccHandicap.setDogGuideWeight(15);
		maccHandicap.setHeight1(100);
		maccHandicap.setHeight2(100);
		maccHandicap.setHeightPlie1(100);
		maccHandicap.setHeightPlie2(100);
		maccHandicap.setLength1(100);
		maccHandicap.setLength2(100);
		maccHandicap.setLengthPlie1(100);
		maccHandicap.setLengthPlie2(100);
		maccHandicap.setMedaCCAccomp("A");
		maccHandicap.setMedaCCDateEnd(new Date());
		maccHandicap.setMedaCCDateStart(new Date());
		maccHandicap.setMedaCCFlag("Y");
		maccHandicap.setMedaCCTxt("Test");
		maccHandicap.setMedaLCAccomp("A");
		maccHandicap.setMedaLCDateEnd(new Date());
		maccHandicap.setMedaLCDateStart(new Date());
		maccHandicap.setMedaLCFlag("Y");
		maccHandicap.setMedaLCTxt("Test");
		maccHandicap.setMedaMCAccomp("A");
		maccHandicap.setMedaMCDateEnd(new Date());
		maccHandicap.setMedaMCDateStart(new Date());
		maccHandicap.setMedaMCFlag("Y");
		maccHandicap.setMedaMCTxt("Test");
		maccHandicap.setOtherMat("AA");
		maccHandicap.setOxygFlag("Y");
		maccHandicap.setOxygOutput(1);
		maccHandicap.setWeight1(100);
		maccHandicap.setWeight2(100);
		maccHandicap.setWidth1(100);
		maccHandicap.setWidth2(100);
		maccHandicap.setWidthPlie1(100);
		maccHandicap.setWidthPlie2(100);
		
		MarketingDataRequest mdRequest = new MarketingDataRequest();
		MarketingInformation mdInfo = new MarketingInformation();
		mdInfo.setHandicap(maccHandicap);
		mdRequest.setMarketingInformation(mdInfo);
		request.setMarketingDataRequest(mdRequest);

		createOrUpdateIndividualImplV7.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "HDC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(40, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV7_createTPCTest() throws ParseException, BusinessErrorBlocBusinessException {		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndividualInfo());
		
		MaccTravelPreferences maccTravelPreferences = new MaccTravelPreferences();
		maccTravelPreferences.setArrivalAirport("NCE");
		maccTravelPreferences.setArrivalCity("NCE");
		maccTravelPreferences.setBoardingPass("AA");
		maccTravelPreferences.setCustomerLeisure("AAAA");
		maccTravelPreferences.setDepartureAirport("NCE");
		maccTravelPreferences.setDepartureCity("NCE");
		maccTravelPreferences.setMeal("Salt");
		maccTravelPreferences.setSeat("W");
		maccTravelPreferences.setTravelClass("E");
		
		MarketingDataRequest mdRequest = new MarketingDataRequest();
		MarketingInformation mdInfo = new MarketingInformation();
		mdInfo.setTravelPreferences(maccTravelPreferences);
		mdRequest.setMarketingInformation(mdInfo);
		request.setMarketingDataRequest(mdRequest);

		createOrUpdateIndividualImplV7.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "TPC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(9, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV7_createTPC_MergedTest() throws ParseException, BusinessErrorBlocBusinessException {		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndividualInfo());
		
		MaccTravelPreferences maccTravelPreferences = new MaccTravelPreferences();
		maccTravelPreferences.setArrivalAirport("NCE");
		maccTravelPreferences.setArrivalCity("NCE");
		maccTravelPreferences.setBoardingPass("AA");
		maccTravelPreferences.setCustomerLeisure("AAAA");
		maccTravelPreferences.setDepartureAirport("NCE");
		maccTravelPreferences.setDepartureCity("NCE");
		maccTravelPreferences.setMeal("Salt");
		maccTravelPreferences.setSeat("W");
		maccTravelPreferences.setTravelClass("E");
		
		MarketingDataRequest mdRequest = new MarketingDataRequest();
		MarketingInformation mdInfo = new MarketingInformation();
		mdInfo.setTravelPreferences(maccTravelPreferences);
		mdRequest.setMarketingInformation(mdInfo);
		request.setMarketingDataRequest(mdRequest);
		
		PreferenceDataRequest preferenceDataRequest = new PreferenceDataRequest();
		com.afklm.soa.stubs.w000442.v7.sicindividutype.Preference preference = new com.afklm.soa.stubs.w000442.v7.sicindividutype.Preference();
		com.afklm.soa.stubs.w000442.v7.sicindividutype.PreferenceData preferenceData = new com.afklm.soa.stubs.w000442.v7.sicindividutype.PreferenceData();
		preferenceData.setKey("preferredAirport");
		preferenceData.setValue("AMS");
		preference.setTypePreference("TPC");
		preference.getPreferenceData().add(preferenceData);
		preferenceDataRequest.getPreference().add(preference);
		request.setPreferenceDataRequest(preferenceDataRequest);

		createOrUpdateIndividualImplV7.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "TPC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(10, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV7_createECCTest() throws ParseException, BusinessErrorBlocBusinessException {		
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());
		request.setIndividualRequest(initIndividualInfo());
		
		MaccEmergencyContacts maccEmergencyContacts = new MaccEmergencyContacts();
		maccEmergencyContacts.setEmail("test@af.com");
		maccEmergencyContacts.setFirstName("Test");
		maccEmergencyContacts.setLastName("Test");
		maccEmergencyContacts.setPhoneNumber("0123456");
		
		MarketingDataRequest mdRequest = new MarketingDataRequest();
		MarketingInformation mdInfo = new MarketingInformation();
		mdInfo.getEmergencyContact().add(maccEmergencyContacts);
		mdRequest.setMarketingInformation(mdInfo);
		request.setMarketingDataRequest(mdRequest);

		createOrUpdateIndividualImplV7.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "ECC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(4, prefList.get(0).getPreferenceData().size());
	}
	
	/**
	 * Test to validate that TELEX type telecom is not created and appropriate error
	 * is thrown
	 */
	@Test
	public void createOrUpdateIndividualV7_Telex() {
		CreateUpdateIndividualRequest request = new CreateUpdateIndividualRequest();
		request.setRequestor(initRequestor());

		IndividualRequest indRequest = new IndividualRequest();
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		indInfo.setBirthDate(new Date());
		indInfo.setCivility("MR");
		indInfo.setStatus(MediumStatusEnum.VALID.toString());
		indInfo.setLastNameSC("LNAME");
		indInfo.setFirstNameSC("FNAME");
		indRequest.setIndividualInformations(indInfo);
		request.setIndividualRequest(indRequest);

		Telecom telecom = new Telecom();
		telecom.setPhoneNumber("084845458452121");
		telecom.setCountryCode("FR");
		telecom.setMediumCode(MediumCodeEnum.HOME.toString());
		telecom.setMediumStatus(MediumStatusEnum.VALID.toString());
		telecom.setTerminalType("X");
		TelecomRequest telecomRequest = new TelecomRequest();
		telecomRequest.setTelecom(telecom);
		request.getTelecomRequest().add(telecomRequest);

		try {
			createOrUpdateIndividualImplV7.createIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(BusinessErrorCodeEnum.ERROR_932, e.getFaultInfo().getBusinessError().getErrorCode());
		}
	}
}
