package com.afklm.batch.kpicalculation.processor;

import com.afklm.batch.kpicalculation.config.KPICalculationConfig;
import com.afklm.batch.kpicalculation.model.KPIStatModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@ContextConfiguration(classes = KPICalculationConfig.class)
//This last annotation allow us to bypass the automatic datasource configuration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KPICalculationProcessorTest {
    @Autowired
    private KPICalculationProcessor kpiCalculationProcessor;

    @Test
    void testProcessor() throws Exception {
        KPIStatModel test1 = new KPIStatModel();
        test1.setLabel("test");

        KPIStatModel res1 = kpiCalculationProcessor.process(test1);
        assertNotNull(res1.getLabel());
        assertEquals("test",res1.getLabel());

        KPIStatModel test2 = new KPIStatModel();

        KPIStatModel res2 = kpiCalculationProcessor.process(test2);
        assertNotNull(res2.getLabel());
        assertEquals("null",res2.getLabel());
    }
}
