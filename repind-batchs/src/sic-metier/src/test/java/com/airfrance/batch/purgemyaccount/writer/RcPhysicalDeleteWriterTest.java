package com.airfrance.batch.purgemyaccount.writer;

import com.airfrance.batch.purgemyaccount.config.PurgeMyAccountConfig;
import com.airfrance.batch.purgemyaccount.model.RcPhysicalDelete;
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
class RcPhysicalDeleteWriterTest {


    @Autowired
    private RcPhysicalDeleteWriter rcPhysicalDeleteWriter;
    private RcPhysicalDelete rcPhysicalDeleteModel;
    private static final Integer ICLE_ROLE = 123;

    @BeforeEach
    public void setup()
    {
        this.rcPhysicalDeleteModel = new RcPhysicalDelete(ICLE_ROLE);
    }

    @Test
    @Rollback
    @DisplayName("TEST : RcPhysicalDelete write - empty input ")
    void testRcPhysicalDeleteWriter_emptyInput()  {
        List<RcPhysicalDelete> rcPhysicalDeleteModels = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> this.rcPhysicalDeleteWriter.write(rcPhysicalDeleteModels));
    }

    @Test
    @Rollback
    @DisplayName("TEST : RcPhysicalDelete write - with input")
    void testRcPhysicalDeleteWriter_inputOk() {
        //GIVEN
        List<RcPhysicalDelete> rcPhysicalDeleteModels = List.of(rcPhysicalDeleteModel);

        assertNotNull(rcPhysicalDeleteModels);
        assertThat(rcPhysicalDeleteModels, hasSize(1));
        assertDoesNotThrow( () -> this.rcPhysicalDeleteWriter.write(rcPhysicalDeleteModels));
    }
}
