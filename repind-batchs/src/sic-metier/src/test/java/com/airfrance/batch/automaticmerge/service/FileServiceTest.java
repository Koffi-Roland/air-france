package com.airfrance.batch.automaticmerge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    public static final String PROCESSED = "PROCESSED";
    public static final String PROCESSING = "PROCESSING";
    private FileService fileService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        fileService.setBaseDirectory(tempDir.toString());
    }


    @Test
    void moveFileToProcessing_MovesFileCorrectly() throws IOException {
        Path sourceFile = Files.createFile(tempDir.resolve("sourceFile.txt"));

        Path result = fileService.moveFileToProcessing(sourceFile);

        Path expectedProcessingPath = tempDir.resolve(PROCESSING).resolve("sourceFile.txt");

        assertEquals(expectedProcessingPath, result);
        assertFalse(Files.exists(sourceFile));
        assertTrue(Files.exists(result));
    }

    @Test
    void moveFileToProcessed_MovesFileCorrectly() throws IOException {
        // Create a source file in the temporary directory
        Path sourceFile = Files.createFile(tempDir.resolve("sourceFile.txt"));

        // Perform the move operation
        Path result = fileService.moveFileToProcessed(sourceFile);

        // Define the expected path of the moved file
        Path expectedProcessedPath = tempDir.resolve(PROCESSED).resolve("sourceFile.txt");

        // Assertions
        assertEquals(expectedProcessedPath, result);
        assertFalse(Files.exists(sourceFile));
        assertTrue(Files.exists(result));
    }

    @Test
    void getFileInProcessing_CreatesDirectoryAndRetrievesNewFile_IfNotExists() throws IOException {
        Path newFile = tempDir.resolve("newFile.txt");
        Files.createFile(newFile);
        fileService.moveFileToProcessing(newFile);

        Path result = fileService.getFileInProcessing();

        assertNotNull(result);
        assertTrue(Files.exists(result));
    }

    @Test
    void getFileInProcessing_ReturnsFile_IfExistsInDirectory() throws IOException {
        Path processingDir = Files.createDirectories(tempDir.resolve(PROCESSING));
        Path existingFile = Files.createFile(processingDir.resolve("existingFile.txt"));

        Path result = fileService.getFileInProcessing();

        assertEquals(existingFile, result);
    }

    @Test
    void getFileInProcessing_RetrievesNewFile_IfDirectoryIsEmpty() throws IOException {
        // Create an empty processing directory
        Files.createDirectories(tempDir.resolve(PROCESSED));

        Path newFile = tempDir.resolve("newFile.txt");
        Files.createFile(newFile);
        fileService.moveFileToProcessing(newFile);

        Path result = fileService.getFileInProcessing();

        assertNotNull(result);
        assertTrue(Files.exists(result));
    }


    @Test
    void getNewFileToProcess_ReturnsLeastRecentlyModifiedFile() throws IOException {
        // Create some files
        Path firstFile = Files.createFile(tempDir.resolve("firstFile.txt"));
        Path secondFile = Files.createFile(tempDir.resolve("secondFile.txt"));
        Path thirdFile = Files.createFile(tempDir.resolve("thirdFile.txt"));

        Files.setLastModifiedTime(firstFile, FileTime.fromMillis(System.currentTimeMillis() - 10000));
        Files.setLastModifiedTime(secondFile, FileTime.fromMillis(System.currentTimeMillis() - 5000));
        Files.setLastModifiedTime(thirdFile, FileTime.fromMillis(System.currentTimeMillis() - 15000));

        Path result = fileService.getNewFileToProcess();

        // Assertions
        assertEquals(thirdFile, result);
    }

    @Test
    void getNewFileToProcess_ThrowsFileNotFoundException_IfNoFiles() {
        assertThrows(FileNotFoundException.class, () -> fileService.getNewFileToProcess());
    }
}
