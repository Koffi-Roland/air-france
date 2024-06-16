package com.airfrance.batch.compref.addnewklcompref.reader;

import com.airfrance.batch.compref.addnewklcompref.utils.GenerateTestData;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewKlComPrefReaderTest {

    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;
    @InjectMocks
    private NewKlComPrefReader newKlComPrefReader;

    @Test
    void whenListIsNotEmpty_readShouldReturnCommPreferences() throws Exception {
        // Mocking repository response
        List<CommunicationPreferences> mockList = List.of(GenerateTestData.createKLComprefForTest());
        CommunicationPreferences expectedCompref = mockList.iterator().next();

        when(communicationPreferencesRepository.findByDomainAndComTypeAndComGroupType(any(String.class), any(String.class),any(String.class),any(String.class))).thenReturn(mockList);

        // Test read() method
        CommunicationPreferences result = newKlComPrefReader.read();
        assertNotNull(result);
        assertEquals(expectedCompref.getComPrefId(), result.getComPrefId());
        assertEquals(expectedCompref.getAccountIdentifier(), result.getAccountIdentifier());
        assertEquals(expectedCompref.getGin(), result.getGin());
        assertEquals(expectedCompref.getDomain(), result.getDomain());
        assertEquals(expectedCompref.getComType(), result.getComType());
        assertEquals(expectedCompref.getComGroupType(), result.getComGroupType());
        assertEquals(expectedCompref.getMedia1(), result.getMedia1());
        assertEquals(expectedCompref.getSubscribe(), result.getSubscribe());
        assertEquals(expectedCompref.getCreationDate(), result.getCreationDate());
        assertEquals(expectedCompref.getDateOptinPartners(), result.getDateOptinPartners());
        assertEquals(expectedCompref.getDateOfEntry(), result.getDateOfEntry());
        assertEquals(expectedCompref.getModificationDate(), result.getModificationDate());
        assertEquals(expectedCompref.getModificationSignature(), result.getModificationSignature());
    }

    @Test
    @DisplayName("Test : getCommunicationPreferencesList")
    void testGetCommunicationPreferencesList(){
        // Mocking repository response
        List<CommunicationPreferences> mockList = List.of(GenerateTestData.createKLComprefForTest());

        when(communicationPreferencesRepository.findByDomainAndComTypeAndComGroupType(any(String.class), any(String.class),any(String.class),any(String.class))).thenReturn(mockList);

        List<CommunicationPreferences> result = newKlComPrefReader.getCommunicationPreferencesList();

        assertEquals(mockList.size(), result.size(), "The sizes of the input and output lists should be equal.");
    }

}
