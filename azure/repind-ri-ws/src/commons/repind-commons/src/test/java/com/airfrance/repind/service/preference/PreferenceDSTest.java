package com.airfrance.repind.service.preference;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MultiplePreferencesException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.preference.PreferenceTransform;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.util.PreferenceUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Following BDM Migration, 3 tests are crashing because there are new migrated data on a specific gin 400159282363
 * I deleted the TCC preference on this GIN in order to be able to run IT tests.
 * If we want to reinsert those data, use following requests.
 * 
 * Insert into PREFERENCE (PREFERENCE_ID,SGIN,STYPE,ILINK,DDATE_CREATION,SSITE_CREATION,SSIGNATURE_CREATION,DDATE_MODIFICATION,SSITE_MODIFICATION,SSIGNATURE_MODIFICATION) values (11693285,'400159282363','TCC',null,to_date('22-NOV-18','DD-MON-RR'),'BATCH_QVI','BDM_166801',to_date('26-JUL-11','DD-MON-RR'),null,null);
 * Insert into PREFERENCE_DATA (PREFERENCE_DATA_ID,PREFERENCE_ID,SKEY,SVALUE,DDATE_CREATION,SSITE_CREATION,SSIGNATURE_CREATION,DDATE_MODIFICATION,SSITE_MODIFICATION,SSIGNATURE_MODIFICATION) values (68989407,11693285,'CIVILITY','MRS',to_date('22-NOV-18','DD-MON-RR'),'BATCH_QVI','BDM_166801',null,null,null);
 * Insert into PREFERENCE_DATA (PREFERENCE_DATA_ID,PREFERENCE_ID,SKEY,SVALUE,DDATE_CREATION,SSITE_CREATION,SSIGNATURE_CREATION,DDATE_MODIFICATION,SSITE_MODIFICATION,SSIGNATURE_MODIFICATION) values (68989408,11693285,'FIRSTNAME','ISCKRA',to_date('22-NOV-18','DD-MON-RR'),'BATCH_QVI','BDM_166801',null,null,null);
 * Insert into PREFERENCE_DATA (PREFERENCE_DATA_ID,PREFERENCE_ID,SKEY,SVALUE,DDATE_CREATION,SSITE_CREATION,SSIGNATURE_CREATION,DDATE_MODIFICATION,SSITE_MODIFICATION,SSIGNATURE_MODIFICATION) values (68989409,11693285,'LASTNAME','IBARRA',to_date('22-NOV-18','DD-MON-RR'),'BATCH_QVI','BDM_166801',null,null,null);
 * Insert into PREFERENCE_DATA (PREFERENCE_DATA_ID,PREFERENCE_ID,SKEY,SVALUE,DDATE_CREATION,SSITE_CREATION,SSIGNATURE_CREATION,DDATE_MODIFICATION,SSITE_MODIFICATION,SSIGNATURE_MODIFICATION) values (68989410,11693285,'DATEOFBIRTH','01/01/1949',to_date('22-NOV-18','DD-MON-RR'),'BATCH_QVI','BDM_166801',null,null,null);
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class PreferenceDSTest {
	
	/** logger */
    private static final Log logger = LogFactory.getLog(PreferenceDSTest.class);
	
	@Autowired
	private PreferenceDS preferenceDS;
	
	@Autowired
	private PreferenceRepository preferenceRepository;
	
	@Autowired
	private IndividuDS individuDS;
	
	// Preference type is null
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_preferenceTypeNull() {
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, new IndividuDTO(), new SignatureDTO());
		} catch (JrafDomainException e) {
			Assert.assertTrue(e.toString().contains("Missing type"));
			logger.error(e);
		}
	}
	
	// Preference type = XXX which not exists in DB
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_unknownPreferenceType() throws JrafDomainException {
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		prefDto.setType("XXX");
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, new IndividuDTO(), new SignatureDTO());
		} catch (InvalidParameterException e) {
			logger.error(e);
			Assert.assertTrue(e.toString().contains("Unknown preference type"));
		}
	}
	
	// Preference key is unknown for the type TDC
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_unknownPreferenceKey() throws JrafDomainException {
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		PreferenceDataDTO prefDataDto = new PreferenceDataDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		
		prefDataDto.setKey("unknown");
		prefDto.setType("TDC");
		prefDto.setPreferenceDataDTO(listPrefData);
		
		listPrefData.add(prefDataDto);
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, new IndividuDTO(), new SignatureDTO());
		} catch (JrafDomainException e) {
			logger.error(e);
			Assert.assertTrue(e.toString().contains("Unknown preference data key"));
		}
	}
	
	// Individual have already 29 TDC and we add 2 more (max = 30)
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_maxNumberOfTravelDocReached() throws JrafDomainException {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		for (int i = 0; i < 29 ; i++) {
			PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
			PreferenceDataDTO prefDataDtoFakeIndiv = new PreferenceDataDTO();
			Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
			
			prefDtoFakeIndiv.setType("TDC");
			prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
			prefDataDtoFakeIndiv.setKey("type");
			prefDataDtoFakeIndiv.setValue("A" + i);
			listPrefDataFakeIndiv.add(prefDataDtoFakeIndiv);
			listPrefFakeIndiv.add(prefDtoFakeIndiv);
		}
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto1 = new PreferenceDTO();
		PreferenceDTO prefDto2 = new PreferenceDTO();
		PreferenceDataDTO countryIssued = new PreferenceDataDTO();
		PreferenceDataDTO touchPoint = new PreferenceDataDTO();
		PreferenceDataDTO authorizationDate = new PreferenceDataDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		prefDto1.setType("TDC");
		prefDto1.setPreferenceDataDTO(listPrefData);
		prefDto2.setType("TDC");
		prefDto2.setPreferenceDataDTO(listPrefData);
		
		//Mandatory fields
		countryIssued.setKey("countryOfIssue");
		countryIssued.setValue("ZZ");
		listPrefData.add(countryIssued);
		
		touchPoint.setKey("touchPoint");
		touchPoint.setValue("airport");
		listPrefData.add(touchPoint);
		
		authorizationDate.setKey("authorizationDate");
		authorizationDate.setValue("01/08/2017");
		listPrefData.add(authorizationDate);
		
		listPref.add(prefDto1);
		listPref.add(prefDto2);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (MultiplePreferencesException e) {
			logger.error(e);
			logger.error(fakeIndivFromDB.getPreferenceDTO().size());
			Assert.assertTrue(e.toString().contains("Maximum number of preferences"));
			Assert.assertEquals(29, fakeIndivFromDB.getPreferenceDTO().size());
		}
	}
	
	// Insert a preference which already exist ==> replace existing
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_travelDocAlreadyExists() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("TDC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("type", "PA"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("number", "testUGC00001"));
		// This value below will be updated to another date in this test
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("expiryDate", "31/12/2017"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setType("TDC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// Input fields
		listPrefDataFromWs.add(new PreferenceDataDTO("type", "PA"));
		listPrefDataFromWs.add(new PreferenceDataDTO("number", "testUGC00001"));
		listPrefDataFromWs.add(new PreferenceDataDTO("expiryDate", "01/01/2018"));
		listPrefDataFromWs.add(new PreferenceDataDTO("touchPoint", "TEST U Loha"));
		listPrefDataFromWs.add(new PreferenceDataDTO("authorizationDate", "01/01/2017"));

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		String expiryDateResult = PreferenceUtils.getDataValueFromPreference(result, "expiryDate");
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getGin());
		Assert.assertEquals(ginTest, result.getGin());
		Assert.assertEquals("01/01/2018", expiryDateResult);
		
	}
	
	// Update a preference field ==> leave existing preference data and update only keys set
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_updateTravelDocField() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("TDC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("type", "PA"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("number", "testUGC00001"));
		// This value below will be updated to another date in this test
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("expiryDate", "31/12/2017"));
		// This value will remain in DB even it's not updated
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("nationality", "FR"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("TDC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// Input fields
		listPrefDataFromWs.add(new PreferenceDataDTO("type", "PA"));
		listPrefDataFromWs.add(new PreferenceDataDTO("number", "testUGC00001"));
		listPrefDataFromWs.add(new PreferenceDataDTO("expiryDate", "01/01/2018"));
		listPrefDataFromWs.add(new PreferenceDataDTO("touchPoint", "TEST U Loha"));
		listPrefDataFromWs.add(new PreferenceDataDTO("authorizationDate", "01/01/2017"));

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		String expiryDateResult = PreferenceUtils.getDataValueFromPreference(result, "expiryDate");
		String nationalityResult = PreferenceUtils.getDataValueFromPreference(result, "nationality");
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getGin());
		Assert.assertEquals(ginTest, result.getGin());
		Assert.assertEquals("01/01/2018", expiryDateResult);
		Assert.assertEquals("FR", nationalityResult);
	}
	
	// Update a preference field and delete one ==> leave existing preference data, update keys set and delete preference data with value == null
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_updateAndDeleteTravelDocField() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("TDC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("type", "PA"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("number", "testUGC00001"));
		// This value below will not change
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("expiryDate", "31/12/2017"));
		// This value will be deleted from DB
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("nationality", "FR"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("TDC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// Input fields
		listPrefDataFromWs.add(new PreferenceDataDTO("type", "PA"));
		listPrefDataFromWs.add(new PreferenceDataDTO("number", "testUGC00001"));
		listPrefDataFromWs.add(new PreferenceDataDTO("nationality", null));
		listPrefDataFromWs.add(new PreferenceDataDTO("touchPoint", "TEST U Loha"));
		listPrefDataFromWs.add(new PreferenceDataDTO("authorizationDate", "01/01/2017"));

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		String expiryDateResult = PreferenceUtils.getDataValueFromPreference(result, "expiryDate");
		String nationalityResult = PreferenceUtils.getDataValueFromPreference(result, "nationality");
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getGin());
		Assert.assertEquals(ginTest, result.getGin());
		Assert.assertEquals("31/12/2017", expiryDateResult);
		Assert.assertNull(nationalityResult);
	}
	
	// Delete a preference
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_deleteTravelDoc() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("TDC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("type", "PA"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("number", "testUGC00001"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("expiryDate", "31/12/2017"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("nationality", "FR"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("TDC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// No input fields

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		Assert.assertNull(result);
	}
	
	// Delete duplicate TDC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_unDuplicateTDC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(ginTest);
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		individuDto.setPreferenceDTO(listPref);
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("TDC");
		prefDto1.setGin(ginTest);
		prefDto1.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "PA"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("number", "testUGC00001")); 
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("touchPoint", "TEST"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("authorizationDate", "01/01/2017"));
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("TDC");
		prefDto2.setGin(ginTest);
		prefDto2.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("TYPE", "PA"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("NUMBER", "testUGC00001"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("TOUCHPOINT", "TEST"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("AUTHORIZATIONDATE", "01/01/2017"));
		listPref.add(prefDto2);
		
		PreferenceDTO prefDto3 = new PreferenceDTO();
		prefDto3.setType("TDC");
		prefDto3.setGin(ginTest);
		prefDto3.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("TYPE", "PA"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("NUMBER", "testUGC00002"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("TOUCHPOINT", "TEST"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("AUTHORIZATIONDATE", "01/01/2017"));
		listPref.add(prefDto3);

		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TDC");
		Assert.assertNotNull(preferenceDTOs);
		// == 3 Because one is already existing into DB + 2 created by the test
		Assert.assertTrue(preferenceDTOs.size() == 3);
	}
	
	// Individual have already 0 PIC and we add 2 more (max = 1)
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_maxNumberOfPIC() throws JrafDomainException {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("PIC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("bluebizNumber");
		prefDataDto1.setValue("10");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("PIC");
		Set<PreferenceDataDTO> listPrefData2 = new HashSet<PreferenceDataDTO>();
		prefDto2.setPreferenceDataDTO(listPrefData2);
		
		PreferenceDataDTO prefDataDto2 = new PreferenceDataDTO();
		prefDataDto2.setKey("BLUEBIZNUMBER");
		prefDataDto2.setValue("10");
		listPrefData2.add(prefDataDto2);
		
		
		listPref.add(prefDto2);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (MultiplePreferencesException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), "Maximum number of preferences of type PIC reached for this individual");
		}
	}

	// Individual have already 0 APC and we add 2 more (max = 1)
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_maxNumberOfAPC() throws JrafDomainException {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();

		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("APC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);

		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("familyName");
		prefDataDto1.setValue("Pierre");
		listPrefData1.add(prefDataDto1);

		listPref.add(prefDto1);

		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("APC");
		Set<PreferenceDataDTO> listPrefData2 = new HashSet<PreferenceDataDTO>();
		prefDto2.setPreferenceDataDTO(listPrefData2);

		PreferenceDataDTO prefDataDto2 = new PreferenceDataDTO();
		prefDataDto2.setKey("familyName");
		prefDataDto2.setValue("Paul");
		listPrefData2.add(prefDataDto2);

		listPref.add(prefDto2);

		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (MultiplePreferencesException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(),
					"Maximum number of preferences of type APC reached for this individual");
		}
	}

	// Create and Replace a preference PIC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createReplacePIC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("PIC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("bluebizNumber", "1"));
		listPrefData.add(new PreferenceDataDTO("FFPNumber", "2"));
		listPrefData.add(new PreferenceDataDTO("FFPProgram", "3"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "PIC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "bluebizNumber").equals("1"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPNumber").equals("2"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPProgram").equals("3"));
		Long bluebizNumberIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "bluebizNumber");
		Long FFPNumberIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPNumber");
		Long FFPProgramIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPProgram");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("bluebizNumber", "10"));
		listPrefData.add(new PreferenceDataDTO("FFPNumber", "20"));
		listPrefData.add(new PreferenceDataDTO("FFPProgram", "30"));
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "PIC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "bluebizNumber").equals("10"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPNumber").equals("20"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPProgram").equals("30"));
		Long bluebizNumberIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "bluebizNumber");
		Long FFPNumberIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPNumber");
		Long FFPProgramIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPProgram");
		
		Assert.assertTrue(bluebizNumberIdBefore != bluebizNumberIdAfter);
		Assert.assertTrue(FFPNumberIdBefore != FFPNumberIdAfter);
		Assert.assertTrue(FFPProgramIdBefore != FFPProgramIdAfter);
	}
	
	// Create and Update a preference PIC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createUpdatePIC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("PIC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("bluebizNumber", "1"));
		listPrefData.add(new PreferenceDataDTO("FFPNumber", "2"));
		listPrefData.add(new PreferenceDataDTO("FFPProgram", "3"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "PIC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "bluebizNumber").equals("1"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPNumber").equals("2"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPProgram").equals("3"));
		Long bluebizNumberIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "bluebizNumber");
		Long FFPNumberIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPNumber");
		Long FFPProgramIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPProgram");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("bluebizNumber", "10"));
		listPrefData.add(new PreferenceDataDTO("FFPNumber", "20"));
		listPrefData.add(new PreferenceDataDTO("FFPProgram", "30"));
		prefDto.setPreferenceId(preferenceDTOs.get(0).getPreferenceId());
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "PIC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "bluebizNumber").equals("10"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPNumber").equals("20"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "FFPProgram").equals("30"));
		Long bluebizNumberIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "bluebizNumber");
		Long FFPNumberIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPNumber");
		Long FFPProgramIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "FFPProgram");
		
		Assert.assertTrue(bluebizNumberIdBefore == bluebizNumberIdAfter);
		Assert.assertTrue(FFPNumberIdBefore == FFPNumberIdAfter);
		Assert.assertTrue(FFPProgramIdBefore == FFPProgramIdAfter);
	}

	// Delete a preference PIC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_deletePIC() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("PIC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("bluebizNumber", "10"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("FFPNumber", "20"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("FFPProgram", "30"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("PIC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// No input fields

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		Assert.assertNull(result);
	}
	
	// Individual have already 0 TPC and we add 2 more (max = 1)
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_maxNumberOfTPC() throws JrafDomainException {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("TPC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("arrivalAirport");
		prefDataDto1.setValue("NCE");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("TPC");
		Set<PreferenceDataDTO> listPrefData2 = new HashSet<PreferenceDataDTO>();
		prefDto2.setPreferenceDataDTO(listPrefData2);
		
		PreferenceDataDTO prefDataDto2 = new PreferenceDataDTO();
		prefDataDto2.setKey("arrivalAirport");
		prefDataDto2.setValue("NCE");
		listPrefData2.add(prefDataDto2);
		
		
		listPref.add(prefDto2);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (MultiplePreferencesException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), "Maximum number of preferences of type TPC reached for this individual");
		}
	}
	
	// Create and Replace a preference TPC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createReplaceTPC() throws JrafDomainException {
		String ginTest = "110000018401";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("TPC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("arrivalAirport", "NCE"));
		listPrefData.add(new PreferenceDataDTO("arrivalCity", "NCE"));
		listPrefData.add(new PreferenceDataDTO("boardingPass", "10"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TPC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalAirport").equals("NCE"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalCity").equals("NCE"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "boardingPass").equals("10"));
		Long arrivalAirportIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalAirport");
		Long arrivalCityIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalCity");
		Long boardingPassIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "boardingPass");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("arrivalAirport", "PRS"));
		listPrefData.add(new PreferenceDataDTO("arrivalCity", "PRS"));
		listPrefData.add(new PreferenceDataDTO("boardingPass", "20"));
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TPC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalAirport").equals("PRS"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalCity").equals("PRS"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "boardingPass").equals("20"));
		Long arrivalAirportIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalAirport");
		Long arrivalCityIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalCity");
		Long boardingPassIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "boardingPass");
		
		Assert.assertTrue(arrivalAirportIdBefore != arrivalAirportIdAfter);
		Assert.assertTrue(arrivalCityIdBefore != arrivalCityIdAfter);
		Assert.assertTrue(boardingPassIdBefore != boardingPassIdAfter);
	}
	
	// Create and Update a preference TPC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createUpdateTPC() throws JrafDomainException {
		String ginTest = "110000018401";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("TPC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("arrivalAirport", "NCE"));
		listPrefData.add(new PreferenceDataDTO("arrivalCity", "NCE"));
		listPrefData.add(new PreferenceDataDTO("boardingPass", "10"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TPC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalAirport").equals("NCE"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalCity").equals("NCE"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "boardingPass").equals("10"));
		Long arrivalAirportIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalAirport");
		Long arrivalCityIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalCity");
		Long boardingPassIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "boardingPass");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("arrivalAirport", "PRS"));
		listPrefData.add(new PreferenceDataDTO("arrivalCity", "PRS"));
		listPrefData.add(new PreferenceDataDTO("boardingPass", "20"));
		prefDto.setPreferenceId(preferenceDTOs.get(0).getPreferenceId());
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TPC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalAirport").equals("PRS"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "arrivalCity").equals("PRS"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "boardingPass").equals("20"));
		Long arrivalAirportIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalAirport");
		Long arrivalCityIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "arrivalCity");
		Long boardingPassIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "boardingPass");
		
		Assert.assertTrue(arrivalAirportIdBefore == arrivalAirportIdAfter);
		Assert.assertTrue(arrivalCityIdBefore == arrivalCityIdAfter);
		Assert.assertTrue(boardingPassIdBefore == boardingPassIdAfter);
	}

	// Delete a preference PIC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_deleteTPC() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("TPC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("arrivalAirport", "NCE"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("arrivalCity", "NCE"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("boardingPass", "10"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("TPC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// No input fields

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		Assert.assertNull(result);
	}
	
	// Individual have already 0 HDC and we add 2 more (max = 1)
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_maxNumberOfHDC() throws JrafDomainException {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("HDC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("dogGuideBreed");
		prefDataDto1.setValue("Labrador");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("HDC");
		Set<PreferenceDataDTO> listPrefData2 = new HashSet<PreferenceDataDTO>();
		prefDto2.setPreferenceDataDTO(listPrefData2);
		
		PreferenceDataDTO prefDataDto2 = new PreferenceDataDTO();
		prefDataDto2.setKey("dogGuideBreed");
		prefDataDto2.setValue("Labrador");
		listPrefData2.add(prefDataDto2);
		
		
		listPref.add(prefDto2);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (MultiplePreferencesException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), "Maximum number of preferences of type HDC reached for this individual");
		}
	}
	
	// Create and Replace a preference HDC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createReplaceHDC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("HDC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("codeHCP1", "WCHR"));
		listPrefData.add(new PreferenceDataDTO("codeHCP2", "WCHR"));
		listPrefData.add(new PreferenceDataDTO("codeHCP3", "WCHR"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "HDC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP1").equals("WCHR"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP2").equals("WCHR"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP3").equals("WCHR"));
		Long codeHCP1IdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP1");
		Long codeHCP2IdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP2");
		Long codeHCP3IdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP3");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("codeHCP1", "DUMB"));
		listPrefData.add(new PreferenceDataDTO("codeHCP2", "DUMB"));
		listPrefData.add(new PreferenceDataDTO("codeHCP3", "DUMB"));
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "HDC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP1").equals("DUMB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP2").equals("DUMB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP3").equals("DUMB"));
		Long codeHCP1IdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP1");
		Long codeHCP2IdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP2");
		Long codeHCP3IdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP3");
		
		Assert.assertTrue(codeHCP1IdBefore != codeHCP1IdAfter);
		Assert.assertTrue(codeHCP2IdBefore != codeHCP2IdAfter);
		Assert.assertTrue(codeHCP3IdBefore != codeHCP3IdAfter);
	}
	
	// Create and Update a preference HDC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createUpdateHDC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("HDC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("codeHCP1", "WCHR"));
		listPrefData.add(new PreferenceDataDTO("codeHCP2", "WCHR"));
		listPrefData.add(new PreferenceDataDTO("codeHCP3", "WCHR"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "HDC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP1").equals("WCHR"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP2").equals("WCHR"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP3").equals("WCHR"));
		Long codeHCP1IdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP1");
		Long codeHCP2IdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP2");
		Long codeHCP3IdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP3");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("codeHCP1", "DUMB"));
		listPrefData.add(new PreferenceDataDTO("codeHCP2", "DUMB"));
		listPrefData.add(new PreferenceDataDTO("codeHCP3", "DUMB"));
		prefDto.setPreferenceId(preferenceDTOs.get(0).getPreferenceId());
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "HDC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP1").equals("DUMB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP2").equals("DUMB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "codeHCP3").equals("DUMB"));
		Long codeHCP1IdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP1");
		Long codeHCP2IdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP2");
		Long codeHCP3IdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "codeHCP3");
		
		Assert.assertTrue(codeHCP1IdBefore == codeHCP1IdAfter);
		Assert.assertTrue(codeHCP2IdBefore == codeHCP2IdAfter);
		Assert.assertTrue(codeHCP3IdBefore == codeHCP3IdAfter);
	}

	// Delete a preference HDC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_deleteHDC() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("HDC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("codeHCP1", "WCHR"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("codeHCP2", "WCHR"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("codeHCP3", "WCHR"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("HDC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// No input fields

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		Assert.assertNull(result);
	}
	
	// Individual have already 8 TCC and we add 1 more (max = 8)
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_maxNumberOfTCC() throws JrafDomainException {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		for (int i = 0; i < 8 ; i++) {
			PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
			prefDtoFakeIndiv.setType("TCC");
			Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
			prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
			
			PreferenceDataDTO prefDataDtoFakeFirstname = new PreferenceDataDTO();
			prefDataDtoFakeFirstname.setKey("firstName");
			prefDataDtoFakeFirstname.setValue("firstName" + i);
			listPrefDataFakeIndiv.add(prefDataDtoFakeFirstname);
			
			PreferenceDataDTO prefDataDtoFakeLastname = new PreferenceDataDTO();
			prefDataDtoFakeLastname.setKey("lastName");
			prefDataDtoFakeLastname.setValue("lastName" + i);
			listPrefDataFakeIndiv.add(prefDataDtoFakeLastname);
			
			listPrefFakeIndiv.add(prefDtoFakeIndiv);
		}
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto1 = new PreferenceDTO();
		
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		prefDto1.setType("TCC");
		prefDto1.setPreferenceDataDTO(listPrefData);
		
		//Mandatory fields
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("firstName");
		prefDataDto1.setValue("firstName");
		listPrefData.add(prefDataDto1);
		
		PreferenceDataDTO prefDataDto2 = new PreferenceDataDTO();
		prefDataDto2.setKey("lastName");
		prefDataDto2.setValue("lastName");
		listPrefData.add(prefDataDto2);
		listPref.add(prefDto1);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (MultiplePreferencesException e) {
			logger.error(e);
			logger.error(fakeIndivFromDB.getPreferenceDTO().size());
			Assert.assertTrue(e.toString().contains("Maximum number of preferences"));
			Assert.assertEquals(8, fakeIndivFromDB.getPreferenceDTO().size());
		}
	}
	
	// Create and Replace a preference TCC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createReplaceTCC() throws JrafDomainException {
		

		List<Preference> preferencesToDelete = preferenceRepository.findByGinAndType("400159282363", "TCC");
		for (Preference prefToDelete : preferencesToDelete) {
			preferenceRepository.delete(prefToDelete);
		}	
		
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("TCC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TCC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@GMAIL.COM"));
		Long firstNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@FREE.COM"));
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TCC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@FREE.COM"));
		Long firstNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		Assert.assertTrue(firstNameIdBefore != firstNameIdAfter);
		Assert.assertTrue(lastNameIdBefore != lastNameIdAfter);
		Assert.assertTrue(emailIdBefore != emailIdAfter);
	}
	
	// Create and Update a preference TCC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createUpdateTCC() throws JrafDomainException {
		
		List<Preference> preferencesToDelete = preferenceRepository.findByGinAndType("400159282363", "TCC");
		for (Preference prefToDelete : preferencesToDelete) {
			preferenceRepository.delete(prefToDelete);
		}
		
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("TCC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TCC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@GMAIL.COM"));
		Long firstNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@FREE.COM"));
		prefDto.setPreferenceId(preferenceDTOs.get(0).getPreferenceId());
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TCC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@FREE.COM"));
		Long firstNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		Assert.assertTrue(firstNameIdBefore == firstNameIdAfter);
		Assert.assertTrue(lastNameIdBefore == lastNameIdAfter);
		Assert.assertTrue(emailIdBefore == emailIdAfter);
	}
	
	


	// Delete a preference TCC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_deleteTCC() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("TCC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("TCC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// No input fields

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		Assert.assertNull(result);
	}
	
	// Delete duplicate TCC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_unDuplicateTCC() throws JrafDomainException {
		
		List<Preference> preferencesToDelete = preferenceRepository.findByGinAndType("400159282363", "TCC");
		for (Preference prefToDelete : preferencesToDelete) {
			preferenceRepository.delete(prefToDelete);
		}
		
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(ginTest);
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		individuDto.setPreferenceDTO(listPref);
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("TCC");
		prefDto1.setGin(ginTest);
		prefDto1.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "AAAAY"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "BBBB"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("TCC");
		prefDto2.setGin(ginTest);
		prefDto2.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "AAAAY"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "BBBB"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		listPref.add(prefDto2);
		
		PreferenceDTO prefDto3 = new PreferenceDTO();
		prefDto3.setType("TCC");
		prefDto3.setGin(ginTest);
		prefDto3.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "aaaa"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "bbbb"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		listPref.add(prefDto3);

		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TCC");
		Assert.assertNotNull(preferenceDTOs);
		Assert.assertTrue(preferenceDTOs.size() == 2);
	}
	
	// Individual have already 5 ECC and we add 1 more (max = 5)
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_maxNumberOfECC() throws JrafDomainException {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		for (int i = 0; i < 8 ; i++) {
			PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
			prefDtoFakeIndiv.setType("ECC");
			Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
			prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
			
			PreferenceDataDTO prefDataDtoFakeFirstname = new PreferenceDataDTO();
			prefDataDtoFakeFirstname.setKey("firstName");
			prefDataDtoFakeFirstname.setValue("firstName" + i);
			listPrefDataFakeIndiv.add(prefDataDtoFakeFirstname);
			
			PreferenceDataDTO prefDataDtoFakeLastname = new PreferenceDataDTO();
			prefDataDtoFakeLastname.setKey("lastName");
			prefDataDtoFakeLastname.setValue("lastName" + i);
			listPrefDataFakeIndiv.add(prefDataDtoFakeLastname);
			
			listPrefFakeIndiv.add(prefDtoFakeIndiv);
		}
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto1 = new PreferenceDTO();
		
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		prefDto1.setType("ECC");
		prefDto1.setPreferenceDataDTO(listPrefData);
		
		//Mandatory fields
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("firstName");
		prefDataDto1.setValue("firstName");
		listPrefData.add(prefDataDto1);
		
		PreferenceDataDTO prefDataDto2 = new PreferenceDataDTO();
		prefDataDto2.setKey("lastName");
		prefDataDto2.setValue("lastName");
		listPrefData.add(prefDataDto2);
		listPref.add(prefDto1);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (MultiplePreferencesException e) {
			logger.error(e);
			logger.error(fakeIndivFromDB.getPreferenceDTO().size());
			Assert.assertTrue(e.toString().contains("Maximum number of preferences"));
			Assert.assertEquals(8, fakeIndivFromDB.getPreferenceDTO().size());
		}
	}
	
	// Create and Replace a preference ECC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createReplaceECC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("ECC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "ECC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@GMAIL.COM"));
		Long firstNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@FREE.COM"));
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "ECC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@FREE.COM"));
		Long firstNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		Assert.assertTrue(firstNameIdBefore != firstNameIdAfter);
		Assert.assertTrue(lastNameIdBefore != lastNameIdAfter);
		Assert.assertTrue(emailIdBefore != emailIdAfter);
	}
	
	// Create and Update a preference ECC
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createUpdateECC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("ECC");
		prefDto.setGin(ginTest);
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "ECC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@GMAIL.COM"));
		Long firstNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdBefore = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		fakeIndivFromDB.setPreferenceDTO(PreferenceTransform.bo2Dto(new HashSet(preferenceDTOs)));
		
		/* REPLACE */
		listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefData.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefData.add(new PreferenceDataDTO("email", "AG@FREE.COM"));
		prefDto.setPreferenceId(preferenceDTOs.get(0).getPreferenceId());
		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.clear();
		listPref.add(prefDto);
		individuDto.setPreferenceDTO(listPref);
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "ECC");
		Assert.assertTrue(preferenceDTOs.get(0) != null);
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "firstName").equals("AAAA"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "lastName").equals("BBBB"));
		Assert.assertTrue(PreferenceUtils.getDataValueFromPreference(preferenceDTOs.get(0), "email").equals("AG@FREE.COM"));
		Long firstNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "firstName");
		Long lastNameIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "lastName");
		Long emailIdAfter = PreferenceUtils.getDataIdFromPreference(preferenceDTOs.get(0), "email");
		
		Assert.assertTrue(firstNameIdBefore == firstNameIdAfter);
		Assert.assertTrue(lastNameIdBefore == lastNameIdAfter);
		Assert.assertTrue(emailIdBefore == emailIdAfter);
	}

	// Delete a preference ECC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_deleteECC() throws JrafDomainException {
		String ginTest = "400159282363";
		Long preferenceId = 5380475L;
		
		// Init existing Individual with preference
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		PreferenceDTO prefDtoFakeIndiv = new PreferenceDTO();
		List<PreferenceDTO> listPrefFakeIndiv = new ArrayList<PreferenceDTO>();
		
		fakeIndivFromDB.setSgin(ginTest);
		fakeIndivFromDB.setPreferenceDTO(listPrefFakeIndiv);
		
		prefDtoFakeIndiv.setPreferenceId(preferenceId);
		prefDtoFakeIndiv.setGin(ginTest);
		prefDtoFakeIndiv.setType("ECC");
		
		Set<PreferenceDataDTO> listPrefDataFakeIndiv = new HashSet<PreferenceDataDTO>();
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("firstName", "AAAA"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("lastName", "BBBB"));
		listPrefDataFakeIndiv.add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		
		prefDtoFakeIndiv.setPreferenceDataDTO(listPrefDataFakeIndiv);
		listPrefFakeIndiv.add(prefDtoFakeIndiv);
		preferenceRepository.saveAndFlush(PreferenceTransform.dto2BoLight(prefDtoFakeIndiv));
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDtoFromWs = new PreferenceDTO();

		List<PreferenceDTO> listPrefFromWs = new ArrayList<PreferenceDTO>();
		Set<PreferenceDataDTO> listPrefDataFromWs = new HashSet<PreferenceDataDTO>();
		
		prefDtoFromWs.setPreferenceId(preferenceId);
		prefDtoFromWs.setType("ECC");
		prefDtoFromWs.setPreferenceDataDTO(listPrefDataFromWs);
		
		// No input fields

		listPrefFromWs.add(prefDtoFromWs);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPrefFromWs);
		
		// Execute test
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		// Get result
		PreferenceDTO result  = preferenceDS.get(preferenceId);
		Assert.assertNull(result);
	}
	
	// Delete duplicate ECC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_unDuplicateECC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(ginTest);
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		individuDto.setPreferenceDTO(listPref);
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("ECC");
		prefDto1.setGin(ginTest);
		prefDto1.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "AAAAZ"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "BBBB"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("ECC");
		prefDto2.setGin(ginTest);
		prefDto2.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "AAAAZ"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "BBBB"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		listPref.add(prefDto2);
		
		PreferenceDTO prefDto3 = new PreferenceDTO();
		prefDto3.setType("ECC");
		prefDto3.setGin(ginTest);
		prefDto3.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", "aaaa"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", "bbbb"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("email", "AG@GMAIL.COM"));
		listPref.add(prefDto3);

		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "ECC");
		Assert.assertNotNull(preferenceDTOs);
		Assert.assertTrue(preferenceDTOs.size() == 2);
	}
	
	// Delete duplicate TAC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_unDuplicateTAC_Error() {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(ginTest);
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		individuDto.setPreferenceDTO(listPref);
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("TAC");
		prefDto1.setGin(ginTest);
		prefDto1.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "R"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("city", "NICE"));
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto2 = new PreferenceDTO();
		prefDto2.setType("TAC");
		prefDto2.setGin(ginTest);
		prefDto2.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "R"));
		prefDto2.getPreferenceDataDTO().add(new PreferenceDataDTO("city", "PARIS"));
		listPref.add(prefDto2);
		
		PreferenceDTO prefDto3 = new PreferenceDTO();
		prefDto3.setType("TAC");
		prefDto3.setGin(ginTest);
		prefDto3.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "D"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("city", "MAURICE"));
		listPref.add(prefDto3);
		
		PreferenceDTO prefDto4 = new PreferenceDTO();
		prefDto4.setType("TAC");
		prefDto4.setGin(ginTest);
		prefDto4.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto4.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "D"));
		prefDto4.getPreferenceDataDTO().add(new PreferenceDataDTO("city", "DUBAI"));
		listPref.add(prefDto4);

		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (JrafDomainException e) {
			// TODO Auto-generated catch block
			Assert.assertEquals("Invalid parameter: Only one Address of Residence is allowed for TAC", e.getMessage());
		}
	}
	
	// Delete duplicate TAC
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_unDuplicateTAC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		individuDto.setSgin(ginTest);
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		individuDto.setPreferenceDTO(listPref);
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("TAC");
		prefDto1.setGin(ginTest);
		prefDto1.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "R"));
		prefDto1.getPreferenceDataDTO().add(new PreferenceDataDTO("city", "NICE"));
		listPref.add(prefDto1);
		
		PreferenceDTO prefDto3 = new PreferenceDTO();
		prefDto3.setType("TAC");
		prefDto3.setGin(ginTest);
		prefDto3.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "D"));
		prefDto3.getPreferenceDataDTO().add(new PreferenceDataDTO("city", "MAURICE"));
		listPref.add(prefDto3);
		
		PreferenceDTO prefDto4 = new PreferenceDTO();
		prefDto4.setType("TAC");
		prefDto4.setGin(ginTest);
		prefDto4.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefDto4.getPreferenceDataDTO().add(new PreferenceDataDTO("type", "D"));
		prefDto4.getPreferenceDataDTO().add(new PreferenceDataDTO("city", "DUBAI"));
		listPref.add(prefDto4);

		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TAC");
		Assert.assertNotNull(preferenceDTOs);
		Assert.assertTrue(preferenceDTOs.size() == 3);
	}
	
	// Error on date format, expected 01/01/2018
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_ErrorDateFormat() {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("HDC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("medaCCDateStart");
		prefDataDto1.setValue("01012018");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (JrafDomainException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), "Invalid parameter: Invalid date format for the key medaCCDateStart. Expected format is 'dd/MM/yyyy'");
		}
	}
	
	// Error on max length
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_MaxLengthFormat() {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("HDC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("DOGGUIDEFLAG");
		prefDataDto1.setValue("YY");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (JrafDomainException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), "Invalid parameter: Invalid value length of key 'DOGGUIDEFLAG'");
		}
	}
	
	// Error on CODEHCP1 field
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_CODEHCP1() {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("HDC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("CODEHCP1");
		prefDataDto1.setValue("Test");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (JrafDomainException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), "Invalid parameter: Invalid value for the key CODEHCP1.");
		}
	}
	
	// Error on CODEMAT1 field
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_CODEMAT1() {
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("HDC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("CODEMAT1");
		prefDataDto1.setValue("Test");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		} catch (JrafDomainException e) {
			logger.error(e);
			Assert.assertEquals(e.getMessage(), "Invalid parameter: Invalid value for the key CODEMAT1.");
		}
	}
	
	// Check if Signature are well updated
	@Test
	@Rollback(true)
	public void testUpdateIndividualPreference_UpdateSignature() throws ParseException, JrafDomainException {
		
		SignatureDTO signature = new SignatureDTO();
		signature.setSignature("UPDATE");
		signature.setSite("QVI");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		PreferenceDTO prefCreated = new PreferenceDTO();
		prefCreated.setGin("400424668522");
		prefCreated.setType("PIC");
		prefCreated.setSignatureCreation("CREATION");
		prefCreated.setSiteCreation("TLS");
		prefCreated.setDateCreation(sdf.parse("01-01-2018"));
		
		PreferenceDataDTO preferenceDataCreated = new PreferenceDataDTO();
		preferenceDataCreated.setKey("bluebizNumber");
		preferenceDataCreated.setValue("1000");
		preferenceDataCreated.setPreferenceDTO(prefCreated);
		
		prefCreated.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
		prefCreated.getPreferenceDataDTO().add(preferenceDataCreated);
		
		preferenceDS.create(prefCreated);
		
		List<Preference> preferences = preferenceDS.findByGinAndType("400424668522", "PIC");
		Assert.assertEquals(1, preferences.size());
		Assert.assertNull(preferences.get(0).getDateModification());
		Assert.assertNull(preferences.get(0).getSiteModification());
		Assert.assertNull(preferences.get(0).getSignatureModification());
		
		// Fake individual in DB
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		fakeIndivFromDB.setSgin("400424668522");
		List<PreferenceDTO> listPrefFake = new ArrayList<PreferenceDTO>();
		listPrefFake.add(prefCreated);
		fakeIndivFromDB.setPreferenceDTO(listPrefFake);
		
		// Indiv from WS
		IndividuDTO individuDto = new IndividuDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();
		
		PreferenceDTO prefDto1 = new PreferenceDTO();
		prefDto1.setType("PIC");
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		prefDto1.setPreferenceDataDTO(listPrefData1);
		
		PreferenceDataDTO prefDataDto1 = new PreferenceDataDTO();
		prefDataDto1.setKey("bluebizNumber");
		prefDataDto1.setValue("2000");
		listPrefData1.add(prefDataDto1);
		
		listPref.add(prefDto1);
		
		individuDto.setSgin("400424668522");
		individuDto.setPreferenceDTO(listPref);

		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, signature);
		
		preferences = preferenceDS.findByGinAndType("400424668522", "PIC");
		Assert.assertEquals(1, preferences.size());
		Assert.assertNotNull(preferences.get(0).getDateModification());
		Assert.assertEquals("QVI", preferences.get(0).getSiteModification());
		Assert.assertEquals("UPDATE", preferences.get(0).getSignatureModification());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteIndividualPreference_PreferenceTypeGPC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("GPC");
		prefDto.setGin(ginTest);

		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish1", "1"));
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish2", "2"));
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish3", "3"));

		IndividuDTO fakeIndivFromDB = new IndividuDTO();


		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);


		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());

			List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "GPC");
			Assert.assertTrue(preferenceDTOs.get(0) != null);

			Long preferenceId = preferenceDTOs.get(0).getPreferenceId();
			fakeIndivFromDB.setPreferenceDTO(listPref);

			PreferenceDTO pDTO = preferenceDS.findByPreferenceId(preferenceId);
			Assert.assertTrue(pDTO.getPreferenceDataDTO().size() == 3);

			/*update */
			listPref.get(0).setPreferenceId(pDTO.getPreferenceId());
			listPrefData = new HashSet<PreferenceDataDTO>();
			prefDto.setPreferenceDataDTO(listPrefData);
			listPref.clear();
			listPref.add(prefDto);
			fakeIndivFromDB.setPreferenceDTO(listPref);
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
			pDTO = preferenceDS.findByPreferenceId(preferenceId);
			

		} catch (Exception e) {
			logger.error(e);
			Assert.assertTrue(e.getMessage().equals("No entity found for query"));
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateIndividualPreference_PreferenceTypeGPC_MultipleBlock() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("GPC");
		prefDto.setGin(ginTest);
		prefDto.setSiteCreation("QVI");
		prefDto.setSiteModification("QVI");
		prefDto.setDateCreation(new Date());
		prefDto.setSignatureCreation("TEST");
		prefDto.setDateModification(new Date());
		prefDto.setSignatureModification("TEST");
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish1", "1"));
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish2", "2"));
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish3", "3"));

		IndividuDTO fakeIndivFromDB = new IndividuDTO();


		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		PreferenceDTO prefDto1 = new PreferenceDTO();
		

		prefDto1.setType("GPC");
		prefDto1.setGin(ginTest);
		prefDto1.setSiteCreation("QVI");
		prefDto1.setSiteModification("QVI");
		prefDto1.setDateCreation(new Date());
		prefDto1.setSignatureCreation("TEST");
		prefDto1.setDateModification(new Date());
		prefDto1.setSignatureModification("TEST");
		
		Set<PreferenceDataDTO> listPrefData1 = new HashSet<PreferenceDataDTO>();
		listPrefData1.add(new PreferenceDataDTO("KLMHouseWish1", "1"));
		listPrefData1.add(new PreferenceDataDTO("KLMHouseWish2", "2"));
		listPrefData1.add(new PreferenceDataDTO("KLMHouseWish3", "3"));
		
		prefDto1.setPreferenceDataDTO(listPrefData1);
		listPref.add(prefDto1);
		
		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);

		SignatureDTO signatureDTO = new SignatureDTO();
		signatureDTO.setSite("QVI");
		signatureDTO.setSignature("TEST");

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, signatureDTO);

			preferenceDS.findByGinAndType(ginTest, "GPC");
			Assert.fail();
		}catch(Exception e){
			Assert.assertEquals("Maximum number of preferences of type GPC reached for this individual", e.getMessage());
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateIndividualPreference_PreferenceTypeGPC() throws JrafDomainException {
		String ginTest = "400159282363";

		IndividuDTO individuDto = new IndividuDTO();
		PreferenceDTO prefDto = new PreferenceDTO();
		List<PreferenceDTO> listPref = new ArrayList<PreferenceDTO>();

		prefDto.setType("GPC");
		prefDto.setGin(ginTest);
		prefDto.setSiteCreation("QVI");
		prefDto.setSiteModification("QVI");
		prefDto.setDateCreation(new Date());
		prefDto.setSignatureCreation("TEST");
		prefDto.setDateModification(new Date());
		prefDto.setSignatureModification("TEST");
		
		individuDto.setSgin(ginTest);
		Set<PreferenceDataDTO> listPrefData = new HashSet<PreferenceDataDTO>();
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish1", "1"));
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish2", "2"));
		listPrefData.add(new PreferenceDataDTO("KLMHouseWish3", "3"));

		IndividuDTO fakeIndivFromDB = new IndividuDTO();


		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);

		SignatureDTO signatureDTO = new SignatureDTO();
		signatureDTO.setSite("QVI");
		signatureDTO.setSignature("TEST");

		try {
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, signatureDTO);

			List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "GPC");
			Assert.assertTrue(preferenceDTOs.get(0) != null);

			Long preferenceId = preferenceDTOs.get(0).getPreferenceId();
			fakeIndivFromDB.setPreferenceDTO(listPref);

			PreferenceDTO pDTO = preferenceDS.findByPreferenceId(preferenceId);
			Assert.assertTrue(pDTO.getPreferenceDataDTO().size() == 3);
			Assert.assertTrue(pDTO.getDateCreation() != null && pDTO.getDateModification() != null);
			Assert.assertTrue(pDTO.getSignatureCreation() != null && pDTO.getSignatureModification() != null);
			Assert.assertTrue(pDTO.getSiteCreation() != null && pDTO.getSiteModification() != null);
			
			/*update */
			listPref.get(0).setPreferenceId(pDTO.getPreferenceId());
			listPrefData = new HashSet<PreferenceDataDTO>();
			listPrefData.add(new PreferenceDataDTO("KLMHouseWish1", "11"));
			listPrefData.add(new PreferenceDataDTO("KLMHouseWish2", "22"));
			listPrefData.add(new PreferenceDataDTO("KLMHouseWish3", "33"));
			prefDto.setPreferenceDataDTO(listPrefData);
			listPref.clear();
			listPref.add(prefDto);
			fakeIndivFromDB.setPreferenceDTO(listPref);
			preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
			pDTO = preferenceDS.findByPreferenceId(preferenceId);
			Assert.assertTrue(pDTO.getPreferenceDataDTO().size() == 3);
			
			String value = PreferenceUtils.getDataValueFromPreference(pDTO, "KLMHouseWish1");
			Assert.assertTrue(value.equals("11"));
			value = PreferenceUtils.getDataValueFromPreference(pDTO, "KLMHouseWish2");
			Assert.assertTrue(value.equals("22"));
			value = PreferenceUtils.getDataValueFromPreference(pDTO, "KLMHouseWish3");
			Assert.assertTrue(value.equals("33"));

		} catch (JrafDomainException e) {
			logger.error(e);
			Assert.assertTrue(e.toString().contains("Unknown preference data key"));
		}
	}
}
