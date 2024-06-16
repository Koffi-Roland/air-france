package com.airfrance.batch.kpicalculation.writer;

import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import com.airfrance.batch.common.repository.kpi.HistorizationKpiRepository;
import com.airfrance.batch.kpicalculation.config.KPICalculationConfig;
import com.airfrance.batch.kpicalculation.model.KPIStatModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = KPICalculationConfig.class)
//This last annotation allow us to bypass the automatic datasource configuration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OldDataWriterTest {
    @Autowired
    private OldDataWriter oldDataWriter;

    @Autowired
    private HistorizationKpiRepository historizationKpiRepository;

    @Test
    @Transactional
    //Here the Transactional annotation is to revert the transaction to prevent putting test data in the db
    void testWriter() {
        int numberOfDataBeforeTest = historizationKpiRepository.findAll().size();
        assertEquals(numberOfDataBeforeTest,historizationKpiRepository.findAll().size());

        HistorizationKPIEntity tmp = new HistorizationKPIEntity();
        tmp.setValue(0);
        tmp.setKpi("test");
        tmp.setLabel("test label");
        tmp.setDate(Date.from(LocalDate.of(1999, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        tmp = historizationKpiRepository.save(tmp);

        assertEquals(numberOfDataBeforeTest+1,historizationKpiRepository.findAll().size());

        oldDataWriter.write(List.of(tmp));

        int id = tmp.getId();
        assertThrows(JpaObjectRetrievalFailureException.class,() -> historizationKpiRepository.getReferenceById(id));
        assertEquals(numberOfDataBeforeTest,historizationKpiRepository.findAll().size());

        oldDataWriter.write(List.of());
        assertEquals(numberOfDataBeforeTest,historizationKpiRepository.findAll().size());
    }
}
