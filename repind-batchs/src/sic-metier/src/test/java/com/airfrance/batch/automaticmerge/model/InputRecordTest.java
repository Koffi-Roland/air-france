package com.airfrance.batch.automaticmerge.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputRecordTest {
    @Test
     void testNoArgsConstructor() {
        InputRecord record = new InputRecord();
        assertNotNull(record);
    }

    @Test
     void testAllArgsConstructor() {
        InputRecord record = new InputRecord("test@gmail.com", 2, "123,456");
        assertEquals("test@gmail.com", record.getElementDuplicate());
        assertEquals(2, record.getNbGins());
        assertEquals("123,456", record.getGins());
    }

    @Test
     void testSettersAndGetters() {
        InputRecord record = new InputRecord();
        record.setElementDuplicate("test@gmail.com");
        record.setNbGins(3);
        record.setGins("123,456,789");

        assertEquals("test@gmail.com", record.getElementDuplicate());
        assertEquals(3, record.getNbGins());
        assertEquals("123,456,789", record.getGins());
    }

    @Test
     void testToString() {
        InputRecord record = new InputRecord("test@gmail.com", 1, "123");
        String expectedString = "InputRecord(elementDuplicate=test@gmail.com, nbGins=1, gins=123)";
        assertEquals(expectedString, record.toString());
    }

    @Test
     void testEqualsAndHashCode() {
        InputRecord record1 = new InputRecord("test1@gmail.com", 1, "123");
        InputRecord record2 = new InputRecord("test1@gmail.com", 1, "123");
        InputRecord record3 = new InputRecord("test2@gmail.com", 1, "123");

        assertEquals(record1, record2);
        assertNotEquals(record1, record3);

        assertEquals(record1.hashCode(), record2.hashCode());
        assertNotEquals(record1.hashCode(), record3.hashCode());
    }

}
