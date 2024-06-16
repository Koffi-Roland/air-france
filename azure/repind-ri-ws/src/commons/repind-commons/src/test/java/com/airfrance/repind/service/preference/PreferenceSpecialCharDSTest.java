package com.airfrance.repind.service.preference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.entity.preference.PreferenceData;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
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
public class PreferenceSpecialCharDSTest {
	
	/** logger */
    private static final Log logger = LogFactory.getLog(PreferenceSpecialCharDSTest.class);
	
	@Autowired
	private PreferenceDS preferenceDS;
	
	@Autowired
	private PreferenceRepository preferenceRepository;
	
	@Autowired
	private IndividuDS individuDS;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createTCCWithSpecialCharacters() throws JrafDomainException {
		
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
		listPrefData.add(new PreferenceDataDTO("firstName", "João Pedro Garcia"));
		

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TCC");
		
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Set<PreferenceData> preferencesData = preferenceDTOs.get(0).getPreferenceData(); 
		Assert.assertTrue(preferencesData != null);
		
		Iterator<PreferenceData> iterator = preferencesData.iterator();
		while (iterator.hasNext()) {
			PreferenceData e = iterator.next();
			Assert.assertTrue("Joao Pedro Garcia".equals(e.getValue()));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Rollback(true)
	public void testcreateIndividualPreference_createTCCWithNonPrintableCharacters() throws JrafDomainException {
		
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
		listPrefData.add(new PreferenceDataDTO("firstName", "NON (�) PRINTABLE"));
		

		prefDto.setPreferenceDataDTO(listPrefData);
		listPref.add(prefDto);

		individuDto.setSgin(ginTest);
		individuDto.setPreferenceDTO(listPref);
		
		IndividuDTO fakeIndivFromDB = new IndividuDTO();
		
		preferenceDS.updateIndividualPreferences(individuDto, fakeIndivFromDB, new SignatureDTO());
		
		List<Preference> preferenceDTOs = preferenceDS.findByGinAndType(ginTest, "TCC");
		
		Assert.assertTrue(preferenceDTOs.size() == 1);
		Set<PreferenceData> preferencesData = preferenceDTOs.get(0).getPreferenceData(); 
		Assert.assertTrue(preferencesData != null);
		
		Iterator<PreferenceData> iterator = preferencesData.iterator();
		while (iterator.hasNext()) {
			PreferenceData e = iterator.next();
			Assert.assertEquals("NON ( ) PRINTABLE", e.getValue());
		}
	}
	
}
