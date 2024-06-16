package com.afklm.batch.mergeduplicatescore.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static com.afklm.batch.mergeduplicatescore.helper.Constant.PROCESSED;
import static com.afklm.batch.mergeduplicatescore.helper.Constant.PROCESSING;

@Service
@NoArgsConstructor
@Slf4j
public class FileService {

    private String baseDirectory;
    private static final String PROCESSING_DIR = PROCESSING;
    private static final String PROCESSED_DIR  = PROCESSED;

    /**
     * This method takes one param "sourceFileName" then it move it to PROCESSED_DIR.
     * @param sourceFileName
     * @return The path to the target file
     * @throws IOException
     */
    public Path moveFileToProcessed(Path sourceFileName) throws IOException {
        //create directory "Processed"
        Path processedDir = Paths.get(baseDirectory, PROCESSED_DIR);
        if (!Files.exists(processedDir)) {
            Files.createDirectories(processedDir);
        }
        return Files.move(sourceFileName, processedDir.resolve(sourceFileName.getFileName()), StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * This method takes one param "sourceFileName" then it move it to PROCESSING_DIR.
     * @param sourceFileName
     * @return The path to the target file
     * @throws IOException
     */
    private Path moveFileToProcessing(Path sourceFileName) throws IOException {
        //create directory "Processing"
        Path processingDir = Paths.get(baseDirectory, PROCESSING_DIR);
        if (!Files.exists(processingDir)) {
            Files.createDirectories(processingDir);
        }
        return Files.move(sourceFileName, processingDir.resolve(sourceFileName.getFileName()), StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * This method read baseDirectory and return the oldest file by LastDateModification.
     * @return The path to the new file to process
     * @throws IOException
     */
    private Path getNewFileToProcess() throws IOException {
        Path baseDir = Paths.get(baseDirectory);

        try (Stream<Path> filesStream = Files.list(baseDir)) {
            return filesStream
                    .filter(f -> !Files.isDirectory(f))
                    .min(Comparator.comparingLong(f -> f.toFile().lastModified()))
                    .orElseThrow(() -> new FileNotFoundException("No files found in base directory"));
        }

    }

    /**
     * This method reads the folder PROCESSING_DIR, if it doesn't exist it creates it.
     * Then it give back the file in this folder (either zero or one file will exist).
     * if no file exist, it will read from parent directory and then add the file to this PROCESSING_DIR
     *  else it will return the file that already exist in PROCESSING_DIR
     * @return the File
     * @throws IOException
     */
    public Path getFileInProcessing() throws IOException {
        Path processingDir = Paths.get(baseDirectory, PROCESSING_DIR);

        if (!Files.exists(processingDir)) {
                Files.createDirectories(processingDir);
        }

        Optional<Path> fileInProcess;
        try (Stream<Path> filesStream = Files.list(processingDir)) {
            fileInProcess = filesStream
                    .filter(f -> !Files.isDirectory(f))
                    .findAny();
        }

        // read folder "Processing" return the file it contains else retrieve a new file from base dir and add it to "Processing"
        if(fileInProcess.isPresent()){
            return fileInProcess.get();
        }else{
            Path newFileToProcess = getNewFileToProcess();
            return moveFileToProcessing(newFileToProcess);
        }
    }

    /**
     * This method takes in param a path to file, and then give the number of lines it has.
     * @param path
     * @return number of lines
     * @throws IOException
     */
    public static long countNumberOfLines(Path path) throws IOException {
        long count = 0;

        if (Files.exists(path)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                while (reader.readLine() != null) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * This method takes 2 param "nextMergeCount" which means the number of lines to skip in next run of batch,
     * and a second param "sourceFileName". Goal is to check if we arrived to End-Of-File or not yet.
     * @param nextMergeCount
     * @param sourceFileName
     * @return boolean
     * @throws IOException
     */
    public boolean checkEOF(long nextMergeCount, Path sourceFileName) throws IOException{
        return nextMergeCount >= countNumberOfLines(sourceFileName);
    }

    public void setBaseDirectory(String baseDirectory){
        this.baseDirectory = baseDirectory;
    }


}
