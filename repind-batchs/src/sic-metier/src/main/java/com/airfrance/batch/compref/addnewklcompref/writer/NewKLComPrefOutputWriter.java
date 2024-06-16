package com.airfrance.batch.compref.addnewklcompref.writer;

import com.airfrance.repind.entity.individu.CommunicationPreferences;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class NewKLComPrefOutputWriter implements ItemWriter<CommunicationPreferences> {

    private String outputPath;

    private String fileName;

    private String fileExt;

    private static List<CommunicationPreferences> accumulatedItems = new ArrayList<>();


    public NewKLComPrefOutputWriter(String outputPath, String fileName, String fileExt) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(date);

        this.outputPath = outputPath + today;
        this.fileName = fileName;
        this.fileExt = fileExt;

    }

    @Override
    public void write(List<? extends CommunicationPreferences> items) throws Exception {
        // Accumulate comPref items during processing
        accumulatedItems.addAll(items);

    }

    // Call this method at the end of the batch processing to write all accumulated items
    public void writeAccumulatedItems() throws IOException {

        // Check if path doesn't exist, then create it
        checkOutputPath(outputPath);

        // Create file and write outputs
        FileWriter writer = new FileWriter(outputPath + "/" + fileName + fileExt, true);
        writer.append("Total number of KL Communications Preferences updated : " + accumulatedItems.size());
        writer.append(System.lineSeparator());
        writer.append("List of New KL Communications Preferences created : ");
        writer.append(System.lineSeparator());
        for (CommunicationPreferences item : accumulatedItems) {
            writer.append(item.getComPrefId().toString());
            writer.append(System.lineSeparator());
        }
        writer.flush();
        writer.close();

        accumulatedItems.clear(); // Clear accumulated items after writing
    }

    public void checkOutputPath(String outputPath) throws IOException {
        Path path = Paths.get(outputPath);
        if (!Files.exists(path))
            Files.createDirectories(path);
    }

    public List<CommunicationPreferences> getAccumulatedItems(){
        return accumulatedItems;
    }


}
