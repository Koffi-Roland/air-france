package com.afklm.repind.msv.preferences.controller.ut;

import com.afklm.repind.msv.preferences.controller.UltimatePreferencesController;
import com.afklm.repind.msv.preferences.entity.Preference;
import com.afklm.repind.msv.preferences.entity.PreferenceData;
import com.afklm.repind.msv.preferences.repository.PreferenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
class ProvideUltimatePreferencesControllerMockTest {

    MockMvc mockMvc;
    
    @Autowired
    protected WebApplicationContext wac;
    
    @Autowired
    UltimatePreferencesController ultimatePreferenceController;
    
    @MockBean
    PreferenceRepository preferenceRepository;
    
    private List<Preference> listPreference;
    
    @BeforeEach
    void setup() throws Exception {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        
        listPreference = new ArrayList<>();
        listPreference.add(initTestData("400473353290", 0L, "UTS", "CUSTOMERDETAILS", "Aime Zidane"));
        listPreference.add(initTestData("400473353290", 0L, "UTS", "CUSTOMERDETAILS", "Adore le fromage"));
        listPreference.add(initTestData("400473353290", 0L, "UTS", "CUSTOMERDETAILS", "tdlinares-ext@airfrance.fr"));
    }
    
    @Test
    void testProvideUltimatePreferencesByGin() throws Exception {
        when(preferenceRepository.findUltimatePreferencesByGin(any(String.class))).thenReturn(listPreference);
        mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.extendedPreferences", hasSize(3)));
    }
    
    @Test
    void testProvideOneUltimatePreferencesByGin() throws Exception {

    	List<Preference> newListInferred = new ArrayList<>();
    	newListInferred.add(listPreference.get(0));

        when(preferenceRepository.findUltimatePreferencesByGin(any(String.class))).thenReturn(newListInferred);
        mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
			.andExpect(jsonPath("$.extendedPreferences", hasSize(1)));
    }
    
    @Test
    void testProvideUltimatePreferencesWithNotFound() throws Exception {

    	List<Preference> newListInferred = new ArrayList<>();
        when(preferenceRepository.findByGin(any(String.class))).thenReturn(newListInferred);        
        mockMvc.perform(get("/400473353290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    
    @Test
    void testProvideUltimatePreferencesWithWrongGin() throws Exception {
    	
        when(preferenceRepository.findByGin(any(String.class))).thenReturn(listPreference);        
        mockMvc.perform(get("/40047380290").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed());
    }

    Preference initTestData(String gin, Long ilink, String type, String key, String value) {

    	Date now = new Date();
    	
    	Preference preference = new Preference();
    	preference.setGin(gin);
    	preference.setLink(ilink);
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
        
        listInferredData.add(preferenceData);
        
        preference.setPreferenceData(listInferredData);
        
        return preference;
    }    
}
