package com.airfrance.batch.adrInvalidBarecode.processor;

import com.airfrance.batch.adrInvalidBarecode.config.AdrInvalidBarecodeConfig;
import com.airfrance.batch.adrInvalidBarecode.initData.InitDataAdrInvalidBarecode;
import com.airfrance.batch.adrInvalidBarecode.model.InputRecord;
import com.airfrance.batch.adrInvalidBarecode.model.OutputRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ContextConfiguration(classes = AdrInvalidBarecodeConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdrInvalidBarecodeProcessorTest {
    @Autowired
    private AdrInvalidBarecodeProcessor adrInvalidBarecodeProcessor;

    private InputRecord inputRecord;

    @Autowired
    private InitDataAdrInvalidBarecode initDataAdrInvalidBarecode;

    @BeforeEach
    public void setup()
    {
        this.inputRecord = this.initDataAdrInvalidBarecode.initDataAdrInvalidBarecodeInputRecords();
    }
    @Test
    @DisplayName("JUnit test for testing batch writer AdrInvalid Barecode Writer")
    public void testAdrInvalidBarecodeWriter() throws Exception {
        Objects.requireNonNull(this.inputRecord);
        OutputRecord outputRecord = adrInvalidBarecodeProcessor.process(this.inputRecord);

        Objects.requireNonNull(this.inputRecord);
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.inputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.inputRecord.getSain());
        assertEquals(this.initDataAdrInvalidBarecode.DATE_MODIFICATION, this.inputRecord.getDateModification());

        assertNotNull(outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, outputRecord.getSain());
        assertEquals(this.initDataAdrInvalidBarecode.DATE_MODIFICATION, outputRecord.getDateModification());
    }
}
