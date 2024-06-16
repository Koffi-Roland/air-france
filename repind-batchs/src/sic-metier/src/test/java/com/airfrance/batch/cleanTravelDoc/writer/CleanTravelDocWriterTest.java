package com.airfrance.batch.cleanTravelDoc.writer;


import com.airfrance.batch.cleanTravelDoc.config.CleanTravelDocConfig;
import com.airfrance.batch.cleanTravelDoc.model.CleanTravelDocModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.List;

@DataJpaTest
@ContextConfiguration(classes = CleanTravelDocConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //This annotation allow us to bypass the automatic datasource configuration
@EnableTransactionManagement(proxyTargetClass = true) // This annotation is for using proxy introduced by @Transactional
public class CleanTravelDocWriterTest {

    @Autowired
    private CleanTravelDocWriter cleanTravelDocWriter;

    private List<CleanTravelDocModel> listToDelete;

    @BeforeEach
    public void setup()
    {
        listToDelete = List.of(
                CleanTravelDocModel.builder().preferenceId(9999997L).build(),
                CleanTravelDocModel.builder().preferenceId(9999998L).build(),
                CleanTravelDocModel.builder().preferenceId(9999999L).build());
    }
    @Test
    @Transactional // In test Transactional rollback the delete
    @DisplayName("JUnit test for testing batch writer CleanTravelDocWriter")
    public void testCleanTravelDocWriter() throws Exception {
        cleanTravelDocWriter.write(listToDelete);
    }
}
