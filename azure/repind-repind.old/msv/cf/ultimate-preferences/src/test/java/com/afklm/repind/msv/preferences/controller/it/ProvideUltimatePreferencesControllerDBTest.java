package com.afklm.repind.msv.preferences.controller.it;

import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.entity.PreferenceData;
import com.afklm.repind.msv.preferences.repository.PreferenceRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProvideUltimatePreferencesControllerDBTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    PreferenceRepository preferenceRepository;
    
    private List<Preference> listPreference;
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listPreference = new ArrayList<>();
        
        purgeTestData("400476875686");
        
        listPreference.add(initTestData("400476875686", null, "TXT", "TEXT", "Aime Zidane"));
    //    listPreference.add(initTestData("400476875686", 0L, "TXT", "TEXT", "Adore le fromage"));
        listPreference.add(initTestData("400476875686",null , "EML", "EMAIL", "tdlinares-ext@airfrance.fr"));
    }

    @Test
    void testProvideWithNotFound() throws Exception {
    	
        mockMvc.perform(get("/ultimate-preferences/510000040101").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void testWithWrongGin() throws Exception {
    	
        mockMvc.perform(get("/11000000101").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed());
    }

    private void purgeTestData(String gin) {
        
        List<Preference> listInferred = preferenceRepository.findByGin(gin);
        
        for (Preference inferred : listInferred) {
        	preferenceRepository.delete(inferred);
		}
    }

    private Preference initTestData(String gin, Long link, String type, String key, String value) {
    	
    	Date now = new Date();
    	
    	Preference preference = new Preference();
    	preference.setGin(gin);
    	preference.setLink(link);
    	preference.setType(type);
    	preference.setSignatureCreation("Test");
    	preference.setSignatureModification("Test");
    	preference.setSiteCreation("Test");
    	preference.setSiteModification("Test");
    	preference.setDateCreation(now);
    	preference.setDateModification(now);
        
        List<PreferenceData> listInferredData = new ArrayList<PreferenceData>();
        
        PreferenceData preferenceData = new PreferenceData();
        preferenceData.setKey(key);
        preferenceData.setValue(value);
        preferenceData.setPreference(preference);
        
        listInferredData.add(preferenceData);
        
        preference.setPreferenceData(listInferredData);
        
        preferenceRepository.saveAndFlush(preference);
        
        return preference;
    }

    @Test
    void testProvideUltimatePreferences_BasicCase() {
    	String gin = "000000119894"; 
    	List<Preference> lp = preferenceRepository.findByGin(gin);
    	
    	Assert.assertNotNull(lp);
    	Assert.assertEquals(10, lp.size());					// ALL PREFERENCES !!!
    }

    @Test
    void testProvideUltimatePreferences_BasicUltimate_NO_AirPort_Case() {
    	String gin = "000000119894"; 
    	List<Preference> lp = preferenceRepository.findUltimatePreferencesByGin(gin);
    	
    	Assert.assertNotNull(lp);
    	Assert.assertEquals(7, lp.size());					// ONLY ULTIMATE PREFERENCES 
    }

    @Test
    void testProvideUltimatePreferences_BasicUltimateCase() {
    	String gin = "800007640912";
    	List<Preference> lp = preferenceRepository.findUltimatePreferencesByGin(gin);
    	
           	Assert.assertNotNull(lp);
    	Assert.assertEquals(3, lp.size());					// ONLY ULTIMATE PREFERENCES
    }
}
