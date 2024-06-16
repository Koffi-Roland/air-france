package com.afklm.batch.detectduplicates.service;

import com.google.common.io.Resources;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@AllArgsConstructor
public class FileService {

    public String readFile(final String relFilePath) throws IOException {
        final URL url = Resources.getResource(relFilePath);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }

    public synchronized void writeInFile(String path, String filename, String content) throws IOException {
        File output = new File(path, filename);
        try (FileWriter fileWriter = new FileWriter(output, true)) {
            boolean alreadyExist = output.createNewFile();
            if(alreadyExist){
                log.info(String.format("Create file '%s' (path: %s)", filename, path));
            }
            fileWriter.write(content);
        }
    }
}
