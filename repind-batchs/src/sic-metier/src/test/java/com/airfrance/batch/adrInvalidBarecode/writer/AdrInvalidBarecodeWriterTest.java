package com.airfrance.batch.adrInvalidBarecode.writer;

import com.airfrance.batch.adrInvalidBarecode.config.AdrInvalidBarecodeConfig;
import com.airfrance.batch.adrInvalidBarecode.initData.InitDataAdrInvalidBarecode;
import com.airfrance.batch.adrInvalidBarecode.model.OutputRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = AdrInvalidBarecodeConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdrInvalidBarecodeWriterTest {
    @Autowired
    private JdbcWriter jdbcWriter;

    private OutputRecord outputRecord;

    @Autowired
    private InitDataAdrInvalidBarecode initDataAdrInvalidBarecode;

    @BeforeEach
    public void setup()
    {
        this.outputRecord = this.initDataAdrInvalidBarecode.initDataAdrInvalidBarecodeOutputRecordUnknownAin();
    }
    @Test
    @DisplayName("JUnit test for testing batch writer AdrInvalid Barecode Writer")
    public void testAdrInvalidBarecodeWriter() throws Exception {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());
        jdbcWriter.write(List.of(this.outputRecord));
        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        assertEquals(this.initDataAdrInvalidBarecode.DATE_MODIFICATION, this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE1, this.outputRecord.getMessage());
    }
}
