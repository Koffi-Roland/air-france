package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.config.PurgeMyAccountConfig;
import com.airfrance.batch.purgemyaccount.model.PayPhysicalDelete;
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
public class PayPhysicalDeleteWriterTest {

    @Autowired
    private PayPhysicalDeleteWriter payPhysicalDeleteWriter;
    private PayPhysicalDelete payPhysicalDeleteModel;
    private static final String gin = "0000000000001";

    @BeforeEach
    public void setup()
    {
        this.payPhysicalDeleteModel = new PayPhysicalDelete(gin);
    }

    @Test
    @Rollback
    @DisplayName("TEST : PayPhysicalDelete write - empty input ")
    void testPayPhysicalDeleteWriter_emptyInput()  {
        List<PayPhysicalDelete> payPhysicalDeleteModels = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> this.payPhysicalDeleteWriter.write(payPhysicalDeleteModels));
    }

    @Test
    @Rollback
    @DisplayName("TEST : PayPhysicalDelete write - with input")
    void testPayPhysicalDeleteWriter_inputOk() {
        //GIVEN
        List<PayPhysicalDelete> payPhysicalDeleteModels = List.of(payPhysicalDeleteModel);

        assertNotNull(payPhysicalDeleteModels);
        assertThat(payPhysicalDeleteModels, hasSize(1));
        assertDoesNotThrow( () -> this.payPhysicalDeleteWriter.write(payPhysicalDeleteModels));
    }
}
