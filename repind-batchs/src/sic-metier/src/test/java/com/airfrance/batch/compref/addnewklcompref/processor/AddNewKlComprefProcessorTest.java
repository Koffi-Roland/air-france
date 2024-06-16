package com.airfrance.batch.compref.addnewklcompref.processor;

import com.airfrance.batch.compref.addnewklcompref.utils.GenerateTestData;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AddNewKlComprefProcessorTest {
    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @Mock
    private MarketLanguageRepository marketLanguagesRepository;
    @Mock
    private CommunicationPreferences communicationPreferences;

    @InjectMocks
    @Spy
    private NewKlComPrefProcessor newKlComPrefProcessor;


    @Test
    @DisplayName("Test : test process CommunicationPreferences")
    void testProcess() throws Exception {
        // Create test data
        CommunicationPreferences inputComPref = GenerateTestData.createKLComprefForTest();
        CommunicationPreferences expectedNewComPref = GenerateTestData.createKLComprefForTest();
        Set<MarketLanguage> expectedMarketLanguages = GenerateTestData.createNewMarketLanguagesForTest();

        inputComPref.setMarketLanguage(expectedMarketLanguages);
        expectedNewComPref.setMarketLanguage(expectedMarketLanguages);

        doReturn(expectedNewComPref).when(newKlComPrefProcessor).createNewKLComPref(any(CommunicationPreferences.class));
        doReturn(expectedMarketLanguages).when(newKlComPrefProcessor).createNewMarketLanguage(any(CommunicationPreferences.class), any(Integer.class));


        CommunicationPreferences result = newKlComPrefProcessor.process(inputComPref);

        // Verify the result
        assertNotNull(result);
        assertEquals(expectedNewComPref.getComPrefId(), result.getComPrefId());
        assertEquals(expectedNewComPref.getGin(), result.getGin());
        assertEquals(expectedNewComPref.getDomain(), result.getDomain());
        assertEquals(expectedNewComPref.getComType(), result.getComType());
        assertEquals(expectedNewComPref.getComGroupType(), result.getComGroupType());
        assertEquals(expectedNewComPref.getSubscribe(), result.getSubscribe());
        verify(communicationPreferencesRepository).save(expectedNewComPref);
    }

    @Test
    @DisplayName("Test : test create New KL compref")
    void testCreateNewKLCompref(){
        CommunicationPreferences compref  = GenerateTestData.createKLComprefForTest();

        when(communicationPreferencesRepository.save(Mockito.any(CommunicationPreferences.class))).thenReturn(compref);

        CommunicationPreferences createdCompref  = newKlComPrefProcessor.createNewKLComPref(compref);

        assertEquals(compref.getComPrefId(), createdCompref.getComPrefId());
        assertEquals(compref.getAccountIdentifier(), createdCompref.getAccountIdentifier());
        assertEquals(compref.getGin(), createdCompref.getGin());
        assertEquals(compref.getDomain(), createdCompref.getDomain());
        assertEquals(compref.getComType(), createdCompref.getComType());
        assertEquals(compref.getComGroupType(), createdCompref.getComGroupType());
        assertEquals(compref.getMedia1(), createdCompref.getMedia1());
        assertEquals(compref.getSubscribe(), createdCompref.getSubscribe());
        assertEquals(compref.getCreationDate(), createdCompref.getCreationDate());
        assertEquals(compref.getDateOptinPartners(), createdCompref.getDateOptinPartners());
        assertEquals(compref.getDateOfEntry(), createdCompref.getDateOfEntry());
        assertEquals(compref.getModificationDate(), createdCompref.getModificationDate());
        assertEquals(compref.getModificationSignature(), createdCompref.getModificationSignature());
        assertEquals(compref.getModificationSite(), createdCompref.getModificationSite());
        assertEquals(compref.getOptinPartners(), createdCompref.getOptinPartners());
        assertEquals(compref.getCreationSignature(), createdCompref.getCreationSignature());
        assertEquals(compref.getCreationSite(), createdCompref.getCreationSite());
        assertEquals(compref.getChannel(), createdCompref.getChannel());
    }

    @Test
    @DisplayName("Test : test create New KL market languages")
    void testCreateNewMarketLanguage(){
        Set<MarketLanguage> existingMarketLanguages = GenerateTestData.createNewMarketLanguagesForTest();

        when(communicationPreferences.getMarketLanguage()).thenReturn(existingMarketLanguages);
        when(marketLanguagesRepository.save(Mockito.any(MarketLanguage.class))).thenAnswer(i -> i.getArguments()[0]);

        Set<MarketLanguage> result = newKlComPrefProcessor.createNewMarketLanguage(communicationPreferences, 1);

        assertNotNull(result);
        assertEquals(existingMarketLanguages.size(), result.size());
        MarketLanguage expectedMarketLanguage = existingMarketLanguages.iterator().next();
        for (MarketLanguage ml : result) {
            assertNotNull(ml.getComPrefId());
            assertEquals(expectedMarketLanguage.getMarket(), ml.getMarket());
            assertEquals(expectedMarketLanguage.getLanguage(), ml.getLanguage());
            assertEquals(expectedMarketLanguage.getOptIn(), ml.getOptIn());
            assertEquals(expectedMarketLanguage.getDateOfConsent(), ml.getDateOfConsent());
            assertEquals(expectedMarketLanguage.getCommunicationMedia1(), ml.getCommunicationMedia1());
            assertEquals(expectedMarketLanguage.getCreationDate(), ml.getCreationDate());
            assertEquals(expectedMarketLanguage.getCreationSignature(), ml.getCreationSignature());
            assertEquals(expectedMarketLanguage.getModificationSignature(), ml.getModificationSignature());
            assertEquals(expectedMarketLanguage.getModificationSite(), ml.getModificationSite());
        }

        verify(marketLanguagesRepository, times(existingMarketLanguages.size())).save(any(MarketLanguage.class));

    }
}


