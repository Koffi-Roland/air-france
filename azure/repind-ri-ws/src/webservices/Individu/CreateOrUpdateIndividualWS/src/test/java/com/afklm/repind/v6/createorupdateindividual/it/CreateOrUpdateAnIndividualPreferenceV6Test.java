package com.afklm.repind.v6.createorupdateindividual.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.v6.createorupdateindividualws.CreateOrUpdateIndividualImplV6;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000442.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.request.IndividualRequest;
import com.afklm.soa.stubs.w000442.v6.request.MarketingDataRequest;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.IndividualInformations;
import com.afklm.soa.stubs.w000442.v6.sicmarketingtype.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
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
public class CreateOrUpdateAnIndividualPreferenceV6Test {
	
	private static final Log logger = LogFactory.getLog(CreateOrUpdateAnIndividualPreferenceV6Test.class);
	
	@Autowired
	@Qualifier("passenger_CreateUpdateIndividualService-v6Bean")
	private CreateOrUpdateIndividualImplV6 createOrUpdateIndividualImplV6;
	
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
	private Requestor initRequestor() {
		Requestor requestor = new Requestor();
		requestor.setApplicationCode("ISI");
		requestor.setChannel("B2C");
		requestor.setSignature("TestU");
		requestor.setSite("VLB");
		
		return requestor;
	}
	
	// Init webservice individual fields
	private IndividualRequest initIndividualInfo() {
		IndividualRequest indReq = new IndividualRequest();
		IndividualInformations indInfos = new IndividualInformations();
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
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 23 secs
	public void createOrUpdateIndividualV6_createExistingTravelDocTest() throws ParseException {
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
			CreateUpdateIndividualResponse response = createOrUpdateIndividualImplV6.createIndividual(request);
			
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
		} catch (SystemException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV6_createPICTest() throws ParseException, BusinessErrorBlocBusinessException, SystemException {		
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

		createOrUpdateIndividualImplV6.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "PIC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(3, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV6_createTCCTest() throws ParseException, BusinessErrorBlocBusinessException, SystemException {		
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

		createOrUpdateIndividualImplV6.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "TCC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(8, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV6_createHDCTest() throws ParseException, BusinessErrorBlocBusinessException, SystemException {		
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

		createOrUpdateIndividualImplV6.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "HDC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(40, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV6_createTPCTest() throws ParseException, BusinessErrorBlocBusinessException, SystemException {		
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

		createOrUpdateIndividualImplV6.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "TPC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(9, prefList.get(0).getPreferenceData().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void createOrUpdateIndividualV6_createECCTest() throws ParseException, BusinessErrorBlocBusinessException, SystemException {		
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

		createOrUpdateIndividualImplV6.createIndividual(request);
		
		List<Preference> prefList = preferenceDS.findByGinAndType(ginTest, "ECC");
		
		Assert.assertEquals(1, prefList.size());
		Assert.assertEquals(4, prefList.get(0).getPreferenceData().size());
	}
}
