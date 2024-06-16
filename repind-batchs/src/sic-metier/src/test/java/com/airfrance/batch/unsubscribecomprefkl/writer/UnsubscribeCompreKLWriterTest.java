package com.airfrance.batch.unsubscribecomprefkl.writer;

import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLService;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLSummaryService;
import com.airfrance.ref.exception.compref.CommunicationPreferencesNotFoundException;
import com.airfrance.ref.exception.compref.MarketLanguageNotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnsubscribeCompreKLWriterTest {

    @Mock
    private UnsubscribeComprefKLService unsubscribeComprefKLService;

    @Mock
    private UnsubscribeComprefKLSummaryService summaryService;

    @InjectMocks
    private UnsubscribeComprefKLWriter writer;

    @Test
    void write_WithInvalidInputs_ShouldHandleExceptionsAndIncrementCounters() throws JrafDomainException {
        UnsubscribeComprefInput input1 = getMockInput("GIN1", "FR", "FR");
        UnsubscribeComprefInput input2 = getMockInput("GIN2", "EN", "EN");
        List<UnsubscribeComprefInput> invalidInputs = List.of(input1, input2);

        doThrow(CommunicationPreferencesNotFoundException.class).when(unsubscribeComprefKLService).unsubscribeMarketLanguage(input1);
        doThrow(MarketLanguageNotFoundException.class).when(unsubscribeComprefKLService).unsubscribeMarketLanguage(input2);

        writer.write(invalidInputs);

        verify(summaryService, times(2)).incrementNbComprefNotFound();
        verify(summaryService, times(2)).addErrorMessage(anyString());
    }

    @Test
    void write_WithValidInputs_ShouldCallServiceAndIncrementCounters() throws JrafDomainException {
        UnsubscribeComprefInput input1 = getMockInput("GIN1", "FR", "FR");
        UnsubscribeComprefInput input2 = getMockInput("GIN2", "EN", "EN");
        List<UnsubscribeComprefInput> inputs = List.of(input1, input2);

        writer.write(inputs);

        verify(unsubscribeComprefKLService, times(2)).unsubscribeMarketLanguage(any());
        verify(summaryService, times(2)).incrementNbSuccessComprefUnsub();
    }

    @Test
    void write_WithEmptyList_ShouldThrowIllegalArgumentException() {
        List<UnsubscribeComprefInput> emptyList = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> writer.write(emptyList));
        assertEquals("The list of data is empty...", exception.getMessage());
    }

    private static UnsubscribeComprefInput getMockInput(String gin, String language, String market) {
        return UnsubscribeComprefInput.builder()
                .ginIndex(gin)
                .actionIndex("U")
                .comTypeComprefIndex("KL")
                .comGroupTypeComprefIndex("N")
                .domainComprefIndex("S")
                .languageComprefIndex(language)
                .marketComprefIndex(market)
                .causeIndex("U_006")
                .build();
    }

}
