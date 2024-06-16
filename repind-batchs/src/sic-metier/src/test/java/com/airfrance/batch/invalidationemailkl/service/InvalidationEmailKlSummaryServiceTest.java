package com.airfrance.batch.invalidationemailkl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvalidationEmailKlSummaryServiceTest {
    private InvalidationEmailKLSummaryService summaryService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        summaryService = new InvalidationEmailKLSummaryService();
    }

    @Test
    void generateMetricFile_CreatesFileCorrectly() throws IOException {
        // Set up test data
        summaryService.incrementNbProcessedLines();
        summaryService.incrementNbSuccessEmailInvalid();
        summaryService.incrementNbEmailNotFound();
        summaryService.incrementNbEmptyMandatoryFields();
        summaryService.incrementNbTechnicalError();
        String outputPath = tempDir.toString();

        // Call the method to generate the metric file
        summaryService.generateMetricFile(outputPath);

        // Verify that the metric file is created
        Path metricFilePath = Paths.get(outputPath, "metric_results.txt");
        assertTrue(Files.exists(metricFilePath));

        // Read the contents of the generated metric file
        List<String> lines = Files.readAllLines(metricFilePath);

        System.out.println("############");
        lines.forEach(System.out::println);
        System.out.println("############");


        // Verify the content of the metric file
        assertTrue(lines.size() >= 6); // Expecting at least 6 lines of content
        assertTrue(lines.get(0).contains("Emails INVALIDATION")); // Check for header
    }

    @Test
    void writeListToFile_WritesToFileCorrectly() throws IOException {
        // Set up test data
        String outputPath = tempDir.toString();
        summaryService.addErrorMessage("Error 1\n");
        summaryService.addErrorMessage("Error 2\n");

        // Call the method to write the list to file
        summaryService.writeListToFile(outputPath);

        // Verify that the rejected file is created
        Path rejectedFilePath = Paths.get(outputPath, "reject_inputs.txt");
        assertTrue(Files.exists(rejectedFilePath));

        // Read the contents of the generated rejected file
        List<String> lines = Files.readAllLines(rejectedFilePath);

        // Verify the content of the rejected file
        assertEquals(2, lines.size()); // Expecting 2 lines of content
        assertEquals("Error 1", lines.get(0).trim());
        assertEquals("Error 2", lines.get(1).trim());
    }

    @Test
    void testAddErrorMessage() {
        String errorMsg = "Error1";
        summaryService.addErrorMessage(errorMsg);
        assertFalse(summaryService.getMessage().isEmpty());
        assertTrue(summaryService.getMessage().contains(errorMsg));
    }
}