package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.config.PurgeMyAccountConfig;
import com.airfrance.batch.purgemyaccount.model.AcPhysicalDelete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = PurgeMyAccountConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AcPhysicalDeleteWriterTest {


    @Autowired
    private AcPhysicalDeleteWriter acPhysicalDeleteWriter;
    private AcPhysicalDelete acPhysicalDeleteModel;
    private static final Integer ID = 999999914;
    private static final String ACCOUNT_IDENTIFIER = "348650BG";

    @BeforeEach
    public void setup()
    {
        this.acPhysicalDeleteModel = new AcPhysicalDelete(ID, ACCOUNT_IDENTIFIER);
    }

    @Test
    @Rollback
    @DisplayName("TEST : AcPhysicalDelete write - empty input ")
    void testAcPhysicalDeleteWriter_emptyInput()  {
        List<AcPhysicalDelete> acPhysicalDeleteModels = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> this.acPhysicalDeleteWriter.write(acPhysicalDeleteModels));
    }

    @Test
    @Rollback
    @DisplayName("TEST : AcPhysicalDelete write - with input")
    void testAcPhysicalDeleteWriter_inputOk() {
        //GIVEN
        List<AcPhysicalDelete> acPhysicalDeleteModels = List.of(acPhysicalDeleteModel);

        assertNotNull(acPhysicalDeleteModels);
        assertThat(acPhysicalDeleteModels, hasSize(1));
        assertDoesNotThrow( () -> this.acPhysicalDeleteWriter.write(acPhysicalDeleteModels));
    }
}