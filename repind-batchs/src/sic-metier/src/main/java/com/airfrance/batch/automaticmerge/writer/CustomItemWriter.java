package com.airfrance.batch.automaticmerge.writer;

import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.batch.automaticmerge.service.AutomaticMergeSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.airfrance.batch.automaticmerge.helper.Constant.*;

@Service
@Slf4j
public class CustomItemWriter implements ItemWriter<OutputRecord> {

    private final FlatFileItemWriter<OutputRecord> flatFileItemWriter;

    private final AutomaticMergeSummaryService summaryService;

    public CustomItemWriter(FlatFileItemWriter<OutputRecord> flatFileItemWriter, AutomaticMergeSummaryService summaryService) {
        this.flatFileItemWriter = flatFileItemWriter;
        this.summaryService = summaryService;
    }

    /**
     * a;b;1,2,3
     * --> a;b;1  \n a;b;2  \n a;b;3
     * @param outputRecords output records
     * @throws Exception exception
     */
    @Override
    public void write(List<? extends OutputRecord> outputRecords) throws Exception {
        log.info("Custom Item Writer");
        if(!outputRecords.isEmpty()){
            manageWriteToCsv(outputRecords);
        }
    }

    void manageWriteToCsv(List<? extends OutputRecord> outputRecords){
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<OutputRecord> listCustomRecord = new ArrayList<>();

        for (OutputRecord outputRecord : outputRecords) {
            String[] ginsSource = outputRecord.getGinSource().split(COMMA);
            for(String ginSource: ginsSource){
                OutputRecord customRecord = new OutputRecord();
                customRecord.setGinTarget(outputRecord.getGinTarget());
                customRecord.setMergeDateAsString(outputFormat.format(outputRecord.getMergeDate()));
                customRecord.setGinSource(ginSource);
                listCustomRecord.add(customRecord);
            }
            try{
                flatFileItemWriter.write(listCustomRecord);
                listCustomRecord.clear();
            }catch (Exception e){
                summaryService.incrementFailedMergeInDB();
                String message = "Write CSV error" +
                        COMMA + listCustomRecord +
                        COMMA + e.getMessage() +
                        COMMA + WRITE_CSV + END_OF_LINE;
                summaryService.addErrorMessage(message);
            }
        }
    }
}
