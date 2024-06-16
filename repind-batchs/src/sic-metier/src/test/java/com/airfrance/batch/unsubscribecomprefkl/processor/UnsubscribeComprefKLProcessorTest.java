package com.airfrance.batch.unsubscribecomprefkl.processor;

import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLSummaryService;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

/**
 * Unit testing batch - InvalidationEmailKl processor
 */
@ExtendWith(MockitoExtension.class)
class UnsubscribeComprefKLProcessorTest {

    @Mock
    private UnsubscribeComprefKLSummaryService summaryService;

    @InjectMocks
    private UnsubscribeComprefKLProcessor processor;

    @Test
    void process_WithMissingMandatoryFields_ShouldReturnNullAndIncrementNbEmptyMandatoryFields() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();
        input.setGinIndex(null);

        UnsubscribeComprefInput result = processor.process(input);

        assertNull(result);
        verify(summaryService).incrementNbEmptyMandatoryFields();
        verify(summaryService).addErrorMessage("#" + input + "#;BLANK_MANDATORY_FIELD\n");
    }

    @Test
    void process_WithNonUActionIndex_ShouldReturnNullAndIncrementNbIncorrectData() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();
        input.setActionIndex("A");

        UnsubscribeComprefInput result = processor.process(input);

        assertNull(result);
        verify(summaryService).incrementNbIncorrectData();
        verify(summaryService).addErrorMessage("#" + input + "#;ACTION_NOT_U\n");
    }



    @Test
    void process_WithCauseExceedLimit_ShouldReturnNullAndIncrementNbIncorrectData() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();
        input.setCauseIndex("007-testestestestest"); // Cause index exceeds limit

        UnsubscribeComprefInput result = processor.process(input);

        assertNull(result);
        verify(summaryService).incrementNbIncorrectData();
        verify(summaryService).addErrorMessage("#" + input + "#;CAUSE_EXCEED_LIMIT_7_CHARS\n");
    }

    @Test
    void process_WithNonKlOrKlPartComType_ShouldReturnNullAndIncrementNbIncorrectData() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();
        input.setComTypeComprefIndex("FB"); // Non-KL or KL_PART comType

        UnsubscribeComprefInput result = processor.process(input);

        assertNull(result);
        verify(summaryService).incrementNbIncorrectData();
        verify(summaryService).addErrorMessage("#" + input + "#;COM_TYPE_NOT_KL_OR_KLPART\n");
    }

    @Test
    void process_WithValidInput_ShouldReturnCleanedInput() throws JrafDomainException {
        UnsubscribeComprefInput input = getMockInput();

        UnsubscribeComprefInput result = processor.process(input);

        assertEquals(input, result); // Ensure input is returned unchanged
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


