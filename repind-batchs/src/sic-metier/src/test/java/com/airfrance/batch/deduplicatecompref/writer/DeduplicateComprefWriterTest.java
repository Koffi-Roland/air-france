package com.airfrance.batch.deduplicatecompref.writer;

import com.airfrance.batch.deduplicatecompref.config.DeduplicateComprefConfig;
import com.airfrance.batch.deduplicatecompref.model.CommunicationPreferencesModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit testing batch- Deduplicate communication preference writer
 */
@DataJpaTest
@ContextConfiguration(classes = DeduplicateComprefConfig.class)
//This last annotation allow us to bypass the automatic datasource configuration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeduplicateComprefWriterTest {

    /**
     * Deduplicate Communication preferences writer - inject by spring
     */
    @Autowired
    private DeduplicateComprefWriter deduplicateComprefWriter;
    /**
     * Communication preference model
     */
    private CommunicationPreferencesModel communicationPreferencesModel;

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
    @Transactional
    @DisplayName("JUnit test for testing batch  deduplicate communication preferences writer")
    //Here the Transactional annotation is to revert the transaction to prevent putting test data in the db
    public void testWriter()  {
        List<CommunicationPreferencesModel> communicationPreferencesModels = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> deduplicateComprefWriter.write(communicationPreferencesModels));
        communicationPreferencesModels.add(this.communicationPreferencesModel);
        assertNotNull(communicationPreferencesModels);
        assertThat(communicationPreferencesModels.size()).isEqualTo(1);
        assertDoesNotThrow(() -> this.deduplicateComprefWriter.write(communicationPreferencesModels));
    }
}
