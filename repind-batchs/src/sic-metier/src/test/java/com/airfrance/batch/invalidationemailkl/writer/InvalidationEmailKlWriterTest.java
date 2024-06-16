package com.airfrance.batch.invalidationemailkl.writer;

import com.airfrance.batch.invalidationemailkl.model.InputInvalid;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKLSummaryService;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvalidationEmailKlWriterTest {

    public static final String CAUSE_INDEX = "006";
    public static final String EMAIL1 = "mjadar1@test.com";
    public static final String EMAIL2 = "mjadar2@test.com";
    @Mock
    private InvalidationEmailKLSummaryService summaryService;

    @Mock
    private EmailDS emailDS;

    @InjectMocks
    private InvalidationEmailKlWriter writer;

    @Test
    void write_WithEmptyList_ShouldThrowIllegalArgumentException() {
        List<InputInvalid> invalidInputs = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> writer.write(invalidInputs));
        assertEquals("The list of data is empty...", exception.getMessage());
        verify(summaryService, never()).incrementNbTechnicalError();
        verify(summaryService, never()).addErrorMessage(anyString());
    }

    @Test
    void write_WithInvalidInputs_ShouldInvalidateInputsAndHandleExceptions() throws JrafDomainException {
        InputInvalid invalidInput1 = new InputInvalid();
        invalidInput1.setContactIndex(EMAIL1);
        invalidInput1.setCauseIndex(CAUSE_INDEX);

        InputInvalid invalidInput2 = new InputInvalid();
        invalidInput2.setContactIndex(EMAIL2);
        invalidInput2.setCauseIndex(CAUSE_INDEX);

        List<InputInvalid> invalidInputs = List.of(invalidInput1, invalidInput2);

        doThrow(JrafDomainException.class).when(emailDS).search(anyString());

        writer.write(invalidInputs);

        verify(summaryService, times(2)).incrementNbTechnicalError();
        verify(summaryService, times(2)).addErrorMessage(anyString());
    }

    @Test
    void write_WithValidInputs_ShouldInvalidateInputs() throws JrafDomainException {
        InputInvalid invalidInput1 = new InputInvalid();
        invalidInput1.setContactIndex(EMAIL1);
        invalidInput1.setCauseIndex(CAUSE_INDEX);

        InputInvalid invalidInput2 = new InputInvalid();
        invalidInput2.setContactIndex(EMAIL2);
        invalidInput2.setCauseIndex(CAUSE_INDEX);

        List<InputInvalid> invalidInputs = List.of(invalidInput1, invalidInput2);

        EmailDTO emailDTO1 = new EmailDTO();
        emailDTO1.setEmail(EMAIL1);

        EmailDTO emailDTO2 = new EmailDTO();
        emailDTO2.setEmail(EMAIL2);

        when(emailDS.search(EMAIL1)).thenReturn(List.of(emailDTO1));
        when(emailDS.search(EMAIL2)).thenReturn(List.of(emailDTO2));

        writer.write(invalidInputs);

        verify(summaryService, times(2)).incrementNbSuccessEmailInvalid();
        verify(summaryService, never()).incrementNbTechnicalError();
        verify(summaryService, never()).addErrorMessage(anyString());
        verify(emailDS, times(2)).invalidOnEmail(any(), anyString());
    }

}
