package com.afklm.repind.msv.preferences.controller.it;

import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.repository.PreferenceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class CreateUltimatePreferencesControllerDBTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    PreferenceRepository preferenceRepository;
    
    private List<Preference> listPreference;

    @Test
    void testProvideUltimatePreferences_BasicCase() {
    	String gin = "000000119894"; 
    	List<Preference> lp = preferenceRepository.findByGin(gin);
    	
    	Assert.assertNotNull(lp);
    	Assert.assertEquals(10, lp.size());					// ALL PREFERENCES !!!
    }

    @ParameterizedTest
	@CsvSource({"000000119894, 7", "110014431935,5"})
    void testProvideUltimatePreferences_BasicUltimate(String gin, String nbPref) {
    	List<Preference> lp = preferenceRepository.findUltimatePreferencesByGin(gin);
    	
    	Assert.assertNotNull(lp);
    	Assert.assertEquals(Integer.parseInt(nbPref), lp.size());					// ONLY ULTIMATE PREFERENCES
    }
}
