package com.airfrance.batch.automaticmerge.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OutputRecordTest {

    @Test
     void testGetterAndSetterForGinTarget() {
        OutputRecord record = new OutputRecord();
        record.setGinTarget("123");
        assertEquals("123", record.getGinTarget());
    }

    @Test
     void testGetterAndSetterForGinSource() {
        OutputRecord record = new OutputRecord();
        record.setGinSource("1234");
        assertEquals("1234", record.getGinSource());
    }

    @Test
     void testGetterAndSetterForMergeDate() {
        OutputRecord record = new OutputRecord();
        Date now = new Date();
        record.setMergeDate(now);
        assertEquals(now, record.getMergeDate());
    }

    @Test
     void testGetterAndSetterForMergeDateAsString() {
        OutputRecord record = new OutputRecord();
        record.setMergeDateAsString("2024-01-01");
        assertEquals("2024-01-01", record.getMergeDateAsString());
    }

    @Test
     void testNoArgsConstructor() {
        OutputRecord record = new OutputRecord();
        assertNotNull(record);
    }

    @Test
     void testAllArgsConstructor() {
        Date now = new Date();
        OutputRecord record = new OutputRecord("123", "1234", now, now.toString());
        assertEquals("123", record.getGinTarget());
        assertEquals("1234", record.getGinSource());
        assertEquals(now, record.getMergeDate());
        assertEquals( now.toString(), record.getMergeDateAsString());
    }

    @Test
     void testToString() {
        Date now = new Date();
        OutputRecord record = new OutputRecord("123", "1234", now, now.toString());
        String expectedString = "OutputRecord(ginTarget=123, ginSource=1234, mergeDate=" + now + ", mergeDateAsString="+ now + ")";
        assertEquals(expectedString, record.toString());
    }

}
