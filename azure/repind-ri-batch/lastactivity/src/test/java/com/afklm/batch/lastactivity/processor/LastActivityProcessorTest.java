package com.afklm.batch.lastactivity.processor;


import com.afklm.batch.lastactivity.config.LastActivityConfig;
import com.afklm.batch.lastactivity.model.LastActivityModel;
import com.afklm.batch.lastactivity.seed.InitLastActivityData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit testing batch - Last activity processor
 */
@DataJpaTest
@ContextConfiguration(classes = LastActivityConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LastActivityProcessorTest {

    @Autowired
    private LastActivityProcessor lastActivityProcessor;

    @Autowired
    private InitLastActivityData initLastActivityDataService;
    private LastActivityModel lastActivityModel;

    /**
     * Individual number identity
     */
    private static final String GIN = "110000038701";

    @BeforeEach
    public void setup()
    {
        //given - precondition or setup
        this.lastActivityModel = this.initLastActivityDataService.initLastActivityData();
    }

    @Test
    @DisplayName("JUnit test for testing batch processor")
    public void testProcessor() throws Exception {

        LastActivityModel lastActivityModelTest = lastActivityProcessor.process(this.lastActivityModel);
        Objects.requireNonNull(lastActivityModelTest);
        assertNotNull(lastActivityModelTest.getGin());
        assertEquals(GIN, lastActivityModelTest.getGin());

    }

    @Test
    @DisplayName("JUnit test for testing batch processor with null value")
    public void testProcessorWithNullValue() throws Exception
    {

        LastActivityModel lastActivityModelTest = lastActivityProcessor.process(this.lastActivityModel);
        Objects.requireNonNull(lastActivityModelTest);
        lastActivityModelTest.setSignatureModification(null);
        lastActivityModelTest.setSiteModification(null);
        lastActivityModelTest.setSourceModification(null);
        assertNotNull(lastActivityModelTest.getGin());
        assertAll(
                () -> assertEquals(GIN, lastActivityModelTest.getGin()),
                () -> assertNull(lastActivityModelTest.getSignatureModification()),
                () -> assertNull(lastActivityModelTest.getSiteModification()),
                () -> assertNull(lastActivityModelTest.getSourceModification())
        );

    }

}
