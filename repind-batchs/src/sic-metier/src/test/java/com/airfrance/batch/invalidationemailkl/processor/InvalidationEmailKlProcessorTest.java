package com.airfrance.batch.invalidationemailkl.processor;

import com.airfrance.batch.invalidationemailkl.model.InputInvalid;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKLSummaryService;
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
class InvalidationEmailKlProcessorTest {

    @Mock
    private InvalidationEmailKLSummaryService summaryService;

    @InjectMocks
    private InvalidationEmailKlProcessor invalidationEmailKlProcessor;

    @Test
    void process_WithValidInput_ShouldReturnInputInvalid() throws JrafDomainException {
        InputInvalid inputInvalid = InputInvalid
                .builder()
                .actionIndex("I")
                .comReturnCodeIndex("6")
                .contactTypeIndex("E")
                .contactIndex("email1@gmail.com")
                .causeIndex("006")
                .build();

        InputInvalid result = invalidationEmailKlProcessor.process(inputInvalid);

        assertEquals(inputInvalid, result);
    }

    @Test
    void process_WithMissingMandatoryFields_empty_ShouldReturnNull() throws JrafDomainException {
        InputInvalid inputInvalid = InputInvalid
                .builder()
                .actionIndex("I")
                .comReturnCodeIndex("6")
                .contactTypeIndex(null)
                .contactIndex("email1@gmail.com")
                .causeIndex("006")
                .build();

        InputInvalid result = invalidationEmailKlProcessor.process(inputInvalid);

        assertNull(result);
        verify(summaryService).incrementNbEmptyMandatoryFields();
        verify(summaryService).addErrorMessage("#"+inputInvalid+"#;BLANK_MANDATORY_FIELD\n");
    }

    @Test
    void process_WithMissingMandatoryFields_null_ShouldReturnNull() throws JrafDomainException {
        InputInvalid inputInvalid = InputInvalid
                .builder()
                .actionIndex("I")
                .comReturnCodeIndex("")
                .contactTypeIndex("E")
                .contactIndex("email1@gmail.com")
                .causeIndex("006")
                .build();

        InputInvalid result = invalidationEmailKlProcessor.process(inputInvalid);

        assertNull(result);
        verify(summaryService).incrementNbEmptyMandatoryFields();
        verify(summaryService).addErrorMessage("#"+inputInvalid+"#;BLANK_MANDATORY_FIELD\n");
    }

}


