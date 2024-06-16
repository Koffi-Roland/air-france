package com.airfrance.batch.unsubscribecomprefkl.service;

import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.ref.exception.compref.CommunicationPreferencesNotFoundException;
import com.airfrance.ref.exception.compref.MarketLanguageNotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnsubscribeComprefKLServiceTest {

    @Mock
    private CommunicationPreferencesDS comprefDS;

    @Mock
    private MarketLanguageRepository marketLanguageRepository;

    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @InjectMocks
    private UnsubscribeComprefKLService service;


    @Test
    void unsubscribeMarketLanguage_WithComPrefIdNotFound_ShouldThrowCommunicationPreferencesNotFoundException() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();

        when(comprefDS.findComPrefId(input.getGinIndex(), input.getDomainComprefIndex(), input.getComGroupTypeComprefIndex(), input.getComTypeComprefIndex())).thenReturn(null);

        assertThrows(CommunicationPreferencesNotFoundException.class, () -> service.unsubscribeMarketLanguage(input));
    }

    @Test
    void unsubscribeMarketLanguage_WithMarketIdNotFound_ShouldThrowMarketLanguageNotFoundException() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();

        CommunicationPreferencesDTO comPrefDTO = new CommunicationPreferencesDTO();
        comPrefDTO.setComPrefId(123);

        when(comprefDS.findComPrefId(input.getGinIndex(), input.getDomainComprefIndex(), input.getComGroupTypeComprefIndex(), input.getComTypeComprefIndex())).thenReturn(comPrefDTO);

        when(comprefDS.findMarketId(comPrefDTO.getComPrefId(), input.getMarketComprefIndex(), input.getLanguageComprefIndex())).thenReturn(null);

        assertThrows(MarketLanguageNotFoundException.class, () -> service.unsubscribeMarketLanguage(input));
    }

    @Test
    void unsubscribeMarketLanguage_WithOptInN_DoNothing() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();

        CommunicationPreferencesDTO comPrefDTO = new CommunicationPreferencesDTO();
        comPrefDTO.setComPrefId(123);
        when(comprefDS.findComPrefId(input.getGinIndex(), input.getDomainComprefIndex(), input.getComGroupTypeComprefIndex(), input.getComTypeComprefIndex())).thenReturn(comPrefDTO);

        MarketLanguageDTO mlDTO = new MarketLanguageDTO();
        mlDTO.setOptIn("N");
        when(comprefDS.findMarketId(comPrefDTO.getComPrefId(), input.getMarketComprefIndex(), input.getLanguageComprefIndex())).thenReturn(mlDTO);

        service.unsubscribeMarketLanguage(input);

        verify(marketLanguageRepository, never()).unsubscribeMarketLanguage(any());
        verify(communicationPreferencesRepository, never()).unsubscribeCommPref(any());
    }

    @Test
    void unsubscribeMarketLanguage_WithOptInY_Unsubscribe() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();

        CommunicationPreferencesDTO comPrefDTO = new CommunicationPreferencesDTO();
        comPrefDTO.setComPrefId(123);
        when(comprefDS.findComPrefId(input.getGinIndex(), input.getDomainComprefIndex(), input.getComGroupTypeComprefIndex(), input.getComTypeComprefIndex())).thenReturn(comPrefDTO);

        MarketLanguageDTO mlDTO = new MarketLanguageDTO();
        mlDTO.setOptIn("Y");
        when(comprefDS.findMarketId(comPrefDTO.getComPrefId(), input.getMarketComprefIndex(), input.getLanguageComprefIndex())).thenReturn(mlDTO);

        service.unsubscribeMarketLanguage(input);

        verify(marketLanguageRepository).unsubscribeMarketLanguage(any());
        verify(communicationPreferencesRepository).unsubscribeCommPref(any());
    }

    private static UnsubscribeComprefInput getMockInput() {
        return UnsubscribeComprefInput.builder()
                .ginIndex("400392865772")
                .actionIndex("U")
                .comTypeComprefIndex("KL")
                .comGroupTypeComprefIndex("N")
                .domainComprefIndex("S")
                .languageComprefIndex("ES")
                .marketComprefIndex("CO")
                .causeIndex("U_006")
                .build();
    }
}

