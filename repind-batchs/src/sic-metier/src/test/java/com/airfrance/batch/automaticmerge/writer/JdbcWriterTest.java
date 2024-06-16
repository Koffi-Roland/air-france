package com.airfrance.batch.automaticmerge.writer;

import com.airfrance.batch.automaticmerge.logger.MergeDuplicateScoreLogger;
import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.batch.automaticmerge.service.AutomaticMergeSummaryService;
import com.airfrance.batch.automaticmerge.service.IndividusDS;
import com.airfrance.batch.common.metric.StatusEnum;
import com.airfrance.ref.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JdbcWriterTest {
    @Mock
    private IndividusDS individusDS;
    @Mock
    private AutomaticMergeSummaryService summaryService;
    @Mock
    private MergeDuplicateScoreLogger logger;
    @InjectMocks
    private JdbcWriter jdbcWriter;

    @Test
    void testWrite_pairGins() throws NotFoundException {
        // Given
        List<OutputRecord> outputRecords = List.of(
                new OutputRecord("123", "456", new Date(), new Date().toString()),
                new OutputRecord("789", "100", new Date(), new Date().toString())
        );

        // When
        jdbcWriter.write(outputRecords);

        // Then
        verify(individusDS, times(1)).updateIndividuSource("456", outputRecords.get(0));
        verify(individusDS, times(1)).updateIndividuSource("100", outputRecords.get(1));
        verify(summaryService, times(2)).incrementNbMerged();
        verify(logger, times(2)).logComo(anyString(), anyString(), any(StatusEnum.class));
        verify(summaryService, never()).incrementFailedMergeInDB();
        verify(summaryService, never()).addErrorMessage(anyString());
    }

    @Test
    void testWrite_tripleGins() throws NotFoundException {
        // Given
        List<OutputRecord> outputRecords = List.of(
                new OutputRecord("123", "456", new Date(), new Date().toString()),
                new OutputRecord("100", "789,546", new Date(), new Date().toString())
        );

        // When
        jdbcWriter.write(outputRecords);

        // Then
        verify(individusDS, times(1)).updateIndividuSource("456", outputRecords.get(0));
        verify(individusDS, times(1)).updateIndividuSource("789", outputRecords.get(1));
        verify(individusDS, times(1)).updateIndividuSource("546", outputRecords.get(1));

        verify(summaryService, times(3)).incrementNbMerged();
        verify(logger, times(3)).logComo(anyString(), anyString(), any(StatusEnum.class));
        verify(summaryService, never()).incrementFailedMergeInDB();
        verify(summaryService, never()).addErrorMessage(anyString());
    }

}
