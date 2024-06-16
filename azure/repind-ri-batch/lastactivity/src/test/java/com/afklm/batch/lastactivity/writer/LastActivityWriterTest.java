package com.afklm.batch.lastactivity.writer;

import com.airfrance.batch.common.repository.lastactivity.LastActivityRepository;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing batch- Last activity writer
 */
@DataJpaTest
@ContextConfiguration(classes = LastActivityConfig.class)
//This last annotation allow us to bypass the automatic datasource configuration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LastActivityWriterTest {

    @Autowired
    private LastActivityWriter lastActivityWriter;

    @Autowired
    private LastActivityRepository lastActivityRepository;

    @Autowired
    private InitLastActivityData initLastActivityDataService;
    private LastActivityModel lastActivityModel;

    @BeforeEach
    public void setup()
    {
        //given - precondition or setup
        this.lastActivityModel = this.initLastActivityDataService.initLastActivityData();
    }

    @Test
    @Transactional
    @DisplayName("JUnit test for testing batch writer")
        //Here the Transactional annotation is to revert the transaction to prevent putting test data in the db
   public void testWriter()
    {
        int numberOfDataBeforeTest = lastActivityRepository.findAll().size();
        assertEquals(numberOfDataBeforeTest, lastActivityRepository.findAll().size());

        List<LastActivityModel> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> lastActivityWriter.write(list));
        assertEquals(numberOfDataBeforeTest, lastActivityRepository.findAll().size());
        assertAll(
                () -> assertNotNull(this.lastActivityModel.getGin()),
                () -> assertNotNull(this.lastActivityModel.getSignatureModification()),
                () -> assertNotNull(this.lastActivityModel.getSiteModification()),
                () -> assertNotNull(this.lastActivityModel.getSourceModification()),
                () -> assertNotNull(this.lastActivityModel.getDateModification())
        );
        lastActivityWriter.write(List.of(this.lastActivityModel));
        assertEquals(numberOfDataBeforeTest, lastActivityRepository.findAll().size());
    }
}
