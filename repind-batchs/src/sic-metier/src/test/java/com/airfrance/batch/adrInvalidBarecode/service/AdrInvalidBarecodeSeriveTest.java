package com.airfrance.batch.adrInvalidBarecode.service;

import com.airfrance.batch.adrInvalidBarecode.config.AdrInvalidBarecodeConfig;
import com.airfrance.batch.adrInvalidBarecode.initData.InitDataAdrInvalidBarecode;
import com.airfrance.batch.adrInvalidBarecode.model.OutputRecord;
import com.airfrance.ref.type.MediumStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = AdrInvalidBarecodeConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdrInvalidBarecodeSeriveTest {

    @Autowired
    private PostalAddressInvalidBarecodeDS postalAddressDS;
    @Autowired
    private InitDataAdrInvalidBarecode initDataAdrInvalidBarecode;

    private OutputRecord outputRecord;
    @BeforeEach
    public void setup()
    {
        //given - precondition or setup
        this.outputRecord = this.initDataAdrInvalidBarecode.initDataAdrInvalidBarecodeOutputRecordUnknownAin();
    }

    @Test
    @DisplayName("JUnit test for testing batch service updatePoastalAddressStatus/UnknownAin")
    public void testUpdatePoastalAddressStatusUnknownAin() {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());
        postalAddressDS.updatePoastalAddressStatus(this.outputRecord);
        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        assertEquals(this.initDataAdrInvalidBarecode.DATE_MODIFICATION, this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE1, this.outputRecord.getMessage());
    }

    @Test
    @DisplayName("JUnit test for testing batch service updatePoastalAddressStatus/History")
    public void testUpdatePoastalAddressStatusHistory() {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());

        this.initDataAdrInvalidBarecode.initMockPostalAddress(MediumStatusEnum.HISTORIZED.toString());

        postalAddressDS.updatePoastalAddressStatus(this.outputRecord);

        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        Objects.requireNonNull(this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE2, this.outputRecord.getMessage());
    }

    @Test
    @DisplayName("JUnit test for testing batch service updatePoastalAddressStatus/removed")
    public void testUpdatePoastalAddressStatusRemoved() {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());

        this.initDataAdrInvalidBarecode.initMockPostalAddress(MediumStatusEnum.REMOVED.toString());

        postalAddressDS.updatePoastalAddressStatus(this.outputRecord);

        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        Objects.requireNonNull(this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE3, this.outputRecord.getMessage());
    }

    @Test
    @DisplayName("JUnit test for testing batch service updatePoastalAddressStatus/already invalid")
    public void testUpdatePoastalAddressStatusAlreadyInvalid() {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());

        this.initDataAdrInvalidBarecode.initMockPostalAddress(MediumStatusEnum.INVALID.toString());

        postalAddressDS.updatePoastalAddressStatus(this.outputRecord);

        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        Objects.requireNonNull(this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE4, this.outputRecord.getMessage());
    }

    @Test
    @DisplayName("JUnit test for testing batch service updatePoastalAddressStatus/contrat not found")
    public void testUpdatePoastalAddressStatusContratNotFound() {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());

        this.initDataAdrInvalidBarecode.initMockPostalAddress(MediumStatusEnum.VALID.toString());
        this.initDataAdrInvalidBarecode.initMockRoleContrats(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.initDataAdrInvalidBarecode.NUMERO_CONTRAT_NOT_FOUND_TRUE, null);

        postalAddressDS.updatePoastalAddressStatus(this.outputRecord);

        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        Objects.requireNonNull(this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE5, this.outputRecord.getMessage());
    }

    @Test
    @DisplayName("JUnit test for testing batch service updatePoastalAddressStatus/Address Updated")
    public void testUpdatePoastalAddressStatusAddressUpdated() {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());

        this.initDataAdrInvalidBarecode.initMockPostalAddress(MediumStatusEnum.VALID.toString());
        this.initDataAdrInvalidBarecode.initMockRoleContrats(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.initDataAdrInvalidBarecode.NUMERO_CONTRAT_NOT_FOUND_FALSE, null);

        postalAddressDS.updatePoastalAddressStatus(this.outputRecord);

        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        Objects.requireNonNull(this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE6, this.outputRecord.getMessage());
    }

    @Test
    @DisplayName("JUnit test for testing batch service updatePoastalAddressStatus/gin not match")
    public void testUpdatePoastalAddressStatusGinNotMatch() {
        Objects.requireNonNull(this.outputRecord);
        assertNull(this.outputRecord.getMessage());

        this.initDataAdrInvalidBarecode.initMockPostalAddress(MediumStatusEnum.VALID.toString());
        this.initDataAdrInvalidBarecode.initMockRoleContrats(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.initDataAdrInvalidBarecode.NUMERO_CONTRAT_NOT_FOUND_FALSE, this.initDataAdrInvalidBarecode.GIN_NOT_MATCH);

        postalAddressDS.updatePoastalAddressStatus(this.outputRecord);

        Objects.requireNonNull(this.outputRecord);
        assertNotNull(this.outputRecord.getMessage());
        assertEquals(this.initDataAdrInvalidBarecode.NUMERO_CONTRAT, this.outputRecord.getNumeroContrat());
        assertEquals(this.initDataAdrInvalidBarecode.SAIN, this.outputRecord.getSain());
        Objects.requireNonNull(this.outputRecord.getDateModification());
        assertEquals(this.initDataAdrInvalidBarecode.MESSAGE7, this.outputRecord.getMessage());
    }
}
