package com.airfrance.batch.invalidationemailkl.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class FileService {

    private String inputPath;
    private String outputPath;
    private Path sourceInput;


    /**
     * This method takes one param "sourceFileName" then it move it to PROCESSED_DIR.
     * @param sourceFileName source file name
     * @return The path to the target file
     * @throws IOException exception
     */
    public Path copyFileToBackup(Path sourceFileName) throws IOException {
        //create directory "BACKUP"
        Path backupDir = Paths.get(outputPath).resolve("backup");

        // if backup folder not exists, create it.
        if (!Files.exists(backupDir)) {
            Files.createDirectories(backupDir);
        }

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // then we copy the file from processing_dir to backup
        return Files.copy(sourceFileName,
                backupDir.resolve(date+"_"+sourceFileName.getFileName()),
                StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * This method will delete file from input directory at the end when the batch is COMPLETED
     * @param sourceFileName filename
     * @throws IOException exception
     */
    public void deleteFileFromBaseDirectory(Path sourceFileName) throws IOException {
        // Resolve the absolute path of the file
        Path absolutePath = sourceFileName.toAbsolutePath();
        // Delete the file
        Files.delete(absolutePath);
    }

    /**
     * This method read baseDirectory and return the oldest file by LastDateModification.
     * @return The path to the new file to process
     * @throws IOException exception
     */
    public Path getNewFileToProcess() throws IOException {
        try (Stream<Path> filesStream = Files.list(Paths.get(inputPath))) {
            return filesStream
                    .filter(f -> !Files.isDirectory(f))
                    .min(Comparator.comparingLong(f -> f.toFile().lastModified()))
                    .orElseThrow(() -> new FileNotFoundException("No files found in base directory"));
        }
    }

}
