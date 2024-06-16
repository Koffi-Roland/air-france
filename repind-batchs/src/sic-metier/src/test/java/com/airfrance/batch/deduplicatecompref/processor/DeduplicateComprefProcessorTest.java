package com.airfrance.batch.deduplicatecompref.processor;

import com.airfrance.batch.deduplicatecompref.config.DeduplicateComprefConfig;
import com.airfrance.batch.deduplicatecompref.model.CommunicationPreferencesModel;
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
 * Unit testing batch - Deduplicate communication preferences processor
 */
@DataJpaTest
@ContextConfiguration(classes = DeduplicateComprefConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeduplicateComprefProcessorTest {

    /**
     * Deduplicate Communication preferences processor - inject by spring
     */
    @Autowired
    private DeduplicateComprefProcessor deduplicateComprefProcessor;

    /**
     * Communication preference model
     */
    private CommunicationPreferencesModel communicationPreferencesModel;

    /**
     * Communication preference identifier
     */
    public final static Integer COM_PREF_ID = 18200112;

    /**
     * Individual number identifier
     */
    private static final String GIN = "110000038701";

    /**
     * Communication type -AF
     */
    private static final String COM_TYPE_AF = "AF";

    /**
     * Communication type -KL
     */
    private static final String COM_TYPE_KL = "KL";


    @BeforeEach
    public void setup()
    {
        //given - precondition or setup
        this.communicationPreferencesModel = CommunicationPreferencesModel.builder()
                .comPrefId(18200112)
                .gin("110000038701")
                .comType("AF")
                .build();
    }

    @Test
    @DisplayName("JUnit test for testing batch processor communication preferences SNAF")
    public void testProcessorWithSNAF() throws Exception {

        CommunicationPreferencesModel communicationPreferencesModelTest = deduplicateComprefProcessor.process(this.communicationPreferencesModel);
        Objects.requireNonNull(communicationPreferencesModelTest);
        assertAll(
                () -> assertNotNull(communicationPreferencesModelTest.getComPrefId()),
                () -> assertNotNull(communicationPreferencesModelTest.getGin()),
                () -> assertNotNull(communicationPreferencesModelTest.getComType()),
                () -> assertEquals(GIN, communicationPreferencesModelTest.getGin()),
                () -> assertEquals(COM_PREF_ID, communicationPreferencesModelTest.getComPrefId()),
                () -> assertEquals(COM_TYPE_AF, communicationPreferencesModelTest.getComType())
        );
    }

    @Test
    @DisplayName("JUnit test for testing batch processor SNKL")
    public void testProcessorWithSNKL() throws Exception {

        this.communicationPreferencesModel.setComType("KL");
        CommunicationPreferencesModel communicationPreferencesModelTest = deduplicateComprefProcessor.process(this.communicationPreferencesModel);
        Objects.requireNonNull(communicationPreferencesModelTest);
        assertAll(
                () -> assertNotNull(communicationPreferencesModelTest.getComPrefId()),
                () -> assertNotNull(communicationPreferencesModelTest.getGin()),
                () -> assertNotNull(communicationPreferencesModelTest.getComType()),
                () -> assertEquals(GIN, communicationPreferencesModelTest.getGin()),
                () -> assertEquals(COM_PREF_ID, communicationPreferencesModelTest.getComPrefId()),
                () -> assertEquals(COM_TYPE_KL, communicationPreferencesModelTest.getComType())
        );
    }


}
