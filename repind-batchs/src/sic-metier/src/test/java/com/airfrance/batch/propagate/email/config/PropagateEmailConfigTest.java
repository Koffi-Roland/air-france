package com.airfrance.batch.propagate.email.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.airfrance.batch.propagate.email.processor.AccountEmailProcessor;
import com.airfrance.batch.propagate.email.utils.GenerateData;
import com.airfrance.repind.entity.individu.AccountData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

@RunWith(MockitoJUnitRunner.class)
public class PropagateEmailConfigTest {

    @Autowired
    @Qualifier("dataSourceRepind")
    private DataSource dataSource;

    @Mock
    private AccountEmailProcessor accountEmailProcessor;

    @InjectMocks
    private PropagateEmailConfig propagateEmailConfig;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testAccountReader() throws Exception {

        // Set the DataSource field in PropagateEmailConfig using ReflectionTestUtils
        ReflectionTestUtils.setField(propagateEmailConfig, "dataSource", dataSource);

        // Call the accountReader() method
        ItemReader<AccountData> reader = propagateEmailConfig.accountReader("000000000001");

        // Assert that the returned reader is of type JdbcPagingItemReader
        // You can add more assertions based on your specific requirements
        assertNotNull(reader);
        assertTrue(reader instanceof JdbcPagingItemReader);
    }

    @Test
    public void testProcess() throws Exception {
        AccountData AccountData = GenerateData.buildAccount();
        AccountData.setEmailIdentifier("test@example.com");
        when(accountEmailProcessor.process(any())).thenReturn(AccountData);

        // Call the processor's process method
        AccountData processedAccountData = accountEmailProcessor.process(GenerateData.buildAccount());

        assertNotNull(processedAccountData);
        assertEquals(AccountData.getEmailIdentifier(), processedAccountData.getEmailIdentifier());
    }
}

