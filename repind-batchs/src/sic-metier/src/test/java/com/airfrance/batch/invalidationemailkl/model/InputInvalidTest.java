package com.airfrance.batch.invalidationemailkl.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

 class InputInvalidTest {
    @Test
     void testActionIndex() {
        InputInvalid input = new InputInvalid();
        assertNull(input.getActionIndex());

        input.setActionIndex("action1");
        assertEquals("action1", input.getActionIndex());
    }

    @Test
     void testComReturnCodeIndex() {
        InputInvalid input = new InputInvalid();
        assertNull(input.getComReturnCodeIndex());

        input.setComReturnCodeIndex("code1");
        assertEquals("code1", input.getComReturnCodeIndex());
    }

    @Test
     void testContactTypeIndex() {
        InputInvalid input = new InputInvalid();
        assertNull(input.getContactTypeIndex());

        input.setContactTypeIndex("type1");
        assertEquals("type1", input.getContactTypeIndex());
    }

    @Test
     void testContactIndex() {
        InputInvalid input = new InputInvalid();
        assertNull(input.getContactIndex());

        input.setContactIndex("contact1");
        assertEquals("contact1", input.getContactIndex());
    }

    @Test
     void testCauseIndex() {
        InputInvalid input = new InputInvalid();
        assertNull(input.getCauseIndex());

        input.setCauseIndex("cause1");
        assertEquals("cause1", input.getCauseIndex());
    }
}
