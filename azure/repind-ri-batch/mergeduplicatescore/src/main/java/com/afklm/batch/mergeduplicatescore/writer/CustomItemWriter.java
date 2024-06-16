package com.afklm.batch.mergeduplicatescore.writer;

import com.afklm.batch.mergeduplicatescore.model.OutputRecord;
import com.afklm.batch.mergeduplicatescore.service.MergeDuplicateScoreSummaryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.afklm.batch.mergeduplicatescore.helper.Constant.*;

@Service
@Slf4j
public class CustomItemWriter implements ItemWriter<OutputRecord> {

    private final FlatFileItemWriter<OutputRecord> flatFileItemWriter;

    @Autowired
    private MergeDuplicateScoreSummaryService summaryService;

    public CustomItemWriter(FlatFileItemWriter<OutputRecord> flatFileItemWriter) {
        this.flatFileItemWriter = flatFileItemWriter;
    }

    /**
     * a;b;1,2,3
     * --> a;b;1  \n a;b;2  \n a;b;3
     * @param outputRecords
     * @throws Exception
     */
    @Override
    public void write(List<? extends OutputRecord> outputRecords) throws Exception {
        log.info("Custom Item Writer");
        if(!outputRecords.isEmpty()){
            manageWriteToCsv(outputRecords);
        }
    }

    private void manageWriteToCsv(List<? extends OutputRecord> outputRecords){
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
                summaryService.incrementFailedLinesCounter();
                StringBuilder message = new StringBuilder("Write CSV error")
                        .append(COMMA).append(listCustomRecord)
                        .append(COMMA).append(e.getMessage())
                        .append(COMMA).append(WRITE_CSV).append(END_OF_LINE);
                summaryService.addErrorMessage(message.toString());
            }
        }
    }
}
