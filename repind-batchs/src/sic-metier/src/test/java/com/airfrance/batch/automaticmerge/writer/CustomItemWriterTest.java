package com.airfrance.batch.automaticmerge.writer;

import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.batch.automaticmerge.service.AutomaticMergeSummaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.file.FlatFileItemWriter;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomItemWriterTest {

    @Mock
    private FlatFileItemWriter<OutputRecord> flatFileItemWriter;

    @Mock
    private AutomaticMergeSummaryService summaryService;

    @InjectMocks
    private CustomItemWriter customItemWriter;

    @Test
     void write_SuccessfullyWritesRecords() throws Exception {
        List<OutputRecord> outputRecords = List.of(
                new OutputRecord("123", "456", new Date(), new Date().toString()),
                new OutputRecord("678", "910", new Date(), new Date().toString())
        );

        customItemWriter.write(outputRecords);

        verify(flatFileItemWriter, times(2)).write(anyList());
        verify(summaryService, never()).incrementFailedMergeInDB();
        verify(summaryService, never()).addErrorMessage(anyString());
    }

    @Test
     void write_HandlesException() throws Exception {
        List<OutputRecord> outputRecords = List.of(
                new OutputRecord("123", "456", new Date(), new Date().toString())
        );

        doThrow(new RuntimeException("Write error")).when(flatFileItemWriter).write(anyList());

        customItemWriter.write(outputRecords);

        verify(flatFileItemWriter, times(1)).write(anyList());
        verify(summaryService, times(1)).incrementFailedMergeInDB();
        verify(summaryService, times(1)).addErrorMessage(anyString());
    }

}
