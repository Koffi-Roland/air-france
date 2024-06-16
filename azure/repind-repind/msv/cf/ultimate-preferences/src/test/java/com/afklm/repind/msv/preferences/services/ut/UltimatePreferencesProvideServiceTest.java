package com.afklm.repind.msv.preferences.services.ut;

import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.entity.PreferenceData;
import com.afklm.repind.msv.preferences.model.GetUltimatePreferencesModel;
import com.afklm.repind.msv.preferences.services.UltimatePreferencesProvideService;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ExtendWith(SpringExtension.class)
class UltimatePreferencesProvideServiceTest {
	
    /** logger */
    private static final Log log = LogFactory.getLog(UltimatePreferencesProvideServiceTest.class);

    @InjectMocks
    private UltimatePreferencesProvideService ultimatePreferencesProvideService;

    @ParameterizedTest
	@CsvSource({
			"UTS, PersonnalAssistant, TA_SERVICING",
			"UFB, WELCOMEDRINK, FOOD_BEVERAGE",
			"UOB, EXTRACOMFORTAMENITIES, ON_BOARD",
			"UST, SEATNUMBERBYAIRCRAFTCABINALLERGYSEAT, SEATING",
			"ULO, SPECIFICATTENTION, LOUNGES",
			"UCO, PREFERREDSPOKENLANGUAGE, COMMUNICATION",
			"UTF, TAXI, TO_FROM_AIRPORT",
			"UPM, NEWSPAPER, PRESS_MEDIA",
			"UMU, AWARDTICKET, MILES_USAGE",
			"ULS, SPORTACTIVITY, LEISURE",
			"UFD, HOTELS, FAVORITE_DESTINATION"
	})
	void convertListToModel_Test(String type, String key, String prefId) throws ServiceException {
		Long id = 123456789L;
		Long link = 987654321L;
    	String gin = "000000318053";
		String value = "TA to arrange seat\nTA to arrange check-in";
    	
    	List<Preference> listPreference = new ArrayList<Preference>();
    	
    	listPreference.add(initTestData(id, link, gin, type, key, value));
    	
    	List<GetUltimatePreferencesModel> lgupm = ultimatePreferencesProvideService.convertListToModel(listPreference);
    	    	
    	Assert.assertNotNull(lgupm);
    	Assert.assertEquals(1, lgupm.size());
    	Assert.assertNotNull(lgupm.get(0));
    	Assert.assertEquals(prefId, lgupm.get(0).getPreferenceId());
    	Assert.assertEquals(value, lgupm.get(0).getValue());
	}

    private Preference initTestData(Long id, Long link, String gin, String type, String key, String value) {

    	Date now = new Date();
    	
    	Preference preference = new Preference();
    	preference.setPreferenceId(id);
    	preference.setLink(link);
    	preference.setGin(gin);
    	preference.setType(type);
    	preference.setSignatureCreation("TestC");
    	preference.setSignatureModification("TestM");
    	preference.setSiteCreation("STestC");
    	preference.setSiteModification("STestM");
    	preference.setDateCreation(now);
    	preference.setDateModification(now);
        
        List<PreferenceData> listInferredData = new ArrayList<PreferenceData>();
        
        PreferenceData preferenceData = new PreferenceData();
        preferenceData.setKey(key);
        preferenceData.setValue(value);
        
        listInferredData.add(preferenceData);
        
        preference.setPreferenceData(listInferredData);
        
        return preference;
    }
}