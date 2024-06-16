package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.config.PurgeMyAccountConfig;
import com.airfrance.batch.purgemyaccount.model.PrPhysicalDelete;
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
public class PrPhysicalDeleteWriterTest {

    @Autowired
    private PrPhysicalDeleteWriter prPhysicalDeleteWriter;
    private PrPhysicalDelete prPhysicalDeleteModel;
    private static final Long preferenceId = 1L;

    @BeforeEach
    public void setup()
    {
        this.prPhysicalDeleteModel = new PrPhysicalDelete(preferenceId);
    }

    @Test
    @Rollback
    @DisplayName("TEST : PrPhysicalDelete write - empty input ")
    void testPrPhysicalDeleteWriter_emptyInput()  {
        List<PrPhysicalDelete> prPhysicalDeleteModels = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> this.prPhysicalDeleteWriter.write(prPhysicalDeleteModels));
    }

    @Test
    @Rollback
    @DisplayName("TEST : PrPhysicalDelete write - with input")
    void testPrPhysicalDeleteWriter_inputOk() {
        //GIVEN
        List<PrPhysicalDelete> prPhysicalDeleteModels = List.of(prPhysicalDeleteModel);

        assertNotNull(prPhysicalDeleteModels);
        assertThat(prPhysicalDeleteModels, hasSize(1));
        assertDoesNotThrow( () -> this.prPhysicalDeleteWriter.write(prPhysicalDeleteModels));
    }
}
