package com.airfrance.batch.detectduplicates.service;

import com.google.common.io.Resources;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
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

    /**
     * This method write result of detect duplicates into output file
     * @param path path to output file
     * @param filename output filename
     * @param content content of output file
     * @throws IOException exception
     */
    public synchronized void writeInFile(String path, String filename, String content) throws IOException {
        File output = new File(path, filename);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(output, true))) {
            boolean alreadyExist = output.createNewFile();
            if (!alreadyExist) {
                log.info(String.format("File '%s' (path: %s) already exists", filename, path));
            } else {
                log.info(String.format("Created new file '%s' (path: %s)", filename, path));
            }
            bufferedWriter.write(content);
        }
    }

}