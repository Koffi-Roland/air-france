package com.afklm.repind.common.sequence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class StringSequenceGeneratorTest {

    @Test
    void testGenerate() {
        StringSequenceGenerator sequence = new StringSequenceGenerator();
        IdentifiableObjectMock mock = new IdentifiableObjectMock();
        assertDoesNotThrow(() -> {
            String result = (String) sequence.generate(null, mock);
            assertEquals("id", result);
        });
        assertThrows(Exception.class, () -> {
            String result = (String) sequence.generate(null, new Date());
        });
    }

    private class IdentifiableObjectMock implements Identifiable {

        private String id = "id";
        @Override
        public String getId() {
            return id;
        }
    }
}
