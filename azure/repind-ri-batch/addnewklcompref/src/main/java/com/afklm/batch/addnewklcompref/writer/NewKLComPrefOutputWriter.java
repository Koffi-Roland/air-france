package com.afklm.batch.addnewklcompref.writer;

import com.airfrance.repind.entity.individu.CommunicationPreferences;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class NewKLComPrefOutputWriter implements ItemWriter<CommunicationPreferences> {

       private String outputPath;

       private String fileName;

       private String fileExt;

    public NewKLComPrefOutputWriter(String outputPath, String fileName, String fileExt) {
        this.outputPath = outputPath;
        this.fileName = fileName;
        this.fileExt = fileExt;
    }

    @Override
    public void write(List<? extends CommunicationPreferences> items) throws Exception {
        // Formatthe date to add to the title of the output File
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(date);

        FileWriter writer = new FileWriter(outputPath + fileName + today + fileExt, true);
        writer.append("Total number of KL Communications Preferences updated : " + items.size());
        writer.append(System.lineSeparator());
        writer.append("List of New KL Communications Preferences created : ");
        writer.append(System.lineSeparator());
        for (CommunicationPreferences item : items) {
            writer.append(item.getComPrefId().toString());
            writer.append(System.lineSeparator());
        }
        writer.flush();
        writer.close();
    }


}
