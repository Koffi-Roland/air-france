package com.airfrance.batch.automaticmerge.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutomaticMergeSummaryServiceTest {
    private AutomaticMergeSummaryService service;

    @BeforeEach
     void setUp() {
        service = new AutomaticMergeSummaryService();
    }

    @Test
     void testIncrementProcessedLinesCounter() {
        service.incrementProcessedLinesCounter();
        assertEquals(1, service.getProcessedLines().get());
    }

    @Test
     void testIncrementNbMerged() {
        service.incrementNbMerged();
        assertEquals(1, service.getNbMerged().get());
    }

    @Test
     void testIncrementFailedMergeInDB() {
        service.incrementFailedMergeInDB();
        assertEquals(1, service.getFailedMergeInDB().get());
    }

    @Test
     void testIncrementSkippedGinsCounter() {
        service.incrementSkippedGinsCounter();
        assertEquals(1, service.getSkippedGins().get());
    }

    @Test
     void testIncrementSkipLineError() {
        service.incrementSkipLineError();
        assertEquals(1, service.getSkipLineError().get());
    }

    @Test
     void testAddErrorMessage() {
        String errorMsg = "Error message";
        service.addErrorMessage(errorMsg);
        assertFalse(service.getMessage().isEmpty());
        assertTrue(service.getMessage().contains(errorMsg));
    }
}