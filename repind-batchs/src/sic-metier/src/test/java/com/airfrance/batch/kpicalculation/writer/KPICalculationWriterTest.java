package com.airfrance.batch.kpicalculation.writer;

import com.airfrance.batch.kpicalculation.config.KPICalculationConfig;
import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import com.airfrance.batch.common.repository.kpi.HistorizationKpiRepository;
import com.airfrance.batch.kpicalculation.model.KPIStatModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = KPICalculationConfig.class)
//This last annotation allow us to bypass the automatic datasource configuration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class KPICalculationWriterTest {
    @Autowired
    private KPICalculationWriter kpiCalculationWriter;

    @Autowired
    private HistorizationKpiRepository historizationKpiRepository;

    @Test
    @Transactional
    //Here the Transactional annotation is to revert the transaction to prevent putting test data in the db
    void testWriter() {
        int numberOfDataBeforeTest = historizationKpiRepository.findAll().size();
        assertEquals(numberOfDataBeforeTest,historizationKpiRepository.findAll().size());

        List<KPIStatModel> list = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> kpiCalculationWriter.write(list));
        assertEquals(numberOfDataBeforeTest,historizationKpiRepository.findAll().size());

        KPIStatModel kpi = new KPIStatModel();
        kpi.setKpi("test");
        kpi.setLabel("label test");
        kpi.setValue(0);

        kpiCalculationWriter.write(List.of(kpi));
        assertEquals(numberOfDataBeforeTest+1,historizationKpiRepository.findAll().size());
    }
}
