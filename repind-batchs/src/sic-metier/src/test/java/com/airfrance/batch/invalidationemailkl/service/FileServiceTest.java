package com.airfrance.batch.invalidationemailkl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        fileService.setInputPath(tempDir.toString());
        fileService.setOutputPath(tempDir.toString());
    }

    @Test
    void copyFileToBackup_CopiesFileCorrectly() throws IOException {
        // Create a source input file in the temporary directory
        Path sourceFile = Files.createFile(tempDir.resolve("sourceFile.csv"));

        // Perform the copy operation of source file into backup repository
        Path result = fileService.copyFileToBackup(sourceFile);

        // Define the expected path of the copied file
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Path expectedBackupPath = Paths.get(fileService.getOutputPath())
                .resolve("backup")
                .resolve(date+"_"+sourceFile.getFileName());

        // Assertions
        assertEquals(expectedBackupPath, result);
        assertTrue(Files.exists(result));
    }

    @Test
    void deleteFileFromBaseDirectory_DeletesOK() throws IOException {
        // Create a source file in the temporary directory
        Path sourceFile = Files.createFile(tempDir.resolve("inputFile.csv"));

        // Perform the delete operation
        fileService.deleteFileFromBaseDirectory(sourceFile);

        // Assertions
        assertFalse(Files.exists(sourceFile));
    }

    @Test
    void getNewFileToProcess_ReturnsOldestFile() throws IOException {
        // Create two files with different last modified times
        Path file1 = Files.createFile(tempDir.resolve("inputFile1.csv"));
        Path file2 = Files.createFile(tempDir.resolve("inputFile2.csv"));

        // Set different last modified times for the files
        Files.setLastModifiedTime(file1, FileTime.fromMillis(System.currentTimeMillis()));
        Files.setLastModifiedTime(file2, FileTime.fromMillis(System.currentTimeMillis() - 1000)); // file2 modified 1 second earlier

        // Perform the getNewFileToProcess operation
        Path result = fileService.getNewFileToProcess();

        // Assertions
        assertEquals(file2, result);
    }

}
