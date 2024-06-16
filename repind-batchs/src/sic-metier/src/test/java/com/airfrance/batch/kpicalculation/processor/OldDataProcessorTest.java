package com.airfrance.batch.kpicalculation.processor;

import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import com.airfrance.batch.kpicalculation.config.KPICalculationConfig;
import com.airfrance.batch.kpicalculation.model.KPIStatModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@ContextConfiguration(classes = KPICalculationConfig.class)
//This last annotation allow us to bypass the automatic datasource configuration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OldDataProcessorTest {
    @Autowired
    private OldDataProcessor oldDataProcessor;

    @Test
    void testProcessor() throws Exception {
        HistorizationKPIEntity test = new HistorizationKPIEntity(0,"kpi","label",1,"optional",new Date());
        HistorizationKPIEntity res = oldDataProcessor.process(test);

        assertNotNull(res);
        assertEquals(test.getId(),res.getId());
        assertEquals(test.getKpi(),res.getKpi());
        assertEquals(test.getLabel(),res.getLabel());
        assertEquals(test.getValue(),res.getValue());
        assertEquals(test.getOptionalData(),res.getOptionalData());
        assertEquals(test.getDate(),res.getDate());
    }
}
