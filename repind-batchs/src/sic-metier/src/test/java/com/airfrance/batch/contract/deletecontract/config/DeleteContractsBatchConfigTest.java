package com.airfrance.batch.contract.deletecontract.config;

import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.entity.role.RoleUCCR;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(MockitoJUnitRunner.class)
public class DeleteContractsBatchConfigTest {
    @Autowired
    @Qualifier("dataSourceRepind")
    private DataSource dataSource;

    @Mock
    private VariablesDS variableDS;

    @InjectMocks
    private DeleteContractsBatchConfig deleteContractsBatchConfig;

    @Before
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testDeleteContractsBatchReader() throws Exception {

        // Set the DataSource field in DeleteContractsBatchConfig using ReflectionTestUtils
        ReflectionTestUtils.setField(deleteContractsBatchConfig, "dataSource", dataSource);

        // Call the deleteContractsBatchReader() method
        ItemReader<RoleContrats> reader = deleteContractsBatchConfig.deleteContractsBatchReader();

        // Assert that the returned reader is of type JdbcPagingItemReader
        // You can add more assertions based on your specific requirements
        assertNotNull(reader);
        assertTrue(reader instanceof JdbcPagingItemReader);
    }

    @Test
    public void testDeleteBusinessRoleUCCRReader() throws Exception {

        // Set the DataSource field in DeleteContractsBatchConfig using ReflectionTestUtils
        ReflectionTestUtils.setField(deleteContractsBatchConfig, "dataSource", dataSource);

        // Call the deleteBusinessRoleUCCRReader() method
        ItemReader<RoleUCCR> reader = deleteContractsBatchConfig.deleteBusinessRoleUCCRReader();

        // Assert that the returned reader is of type JdbcPagingItemReader
        // You can add more assertions based on your specific requirements
        assertNotNull(reader);
        assertTrue(reader instanceof JdbcPagingItemReader);
    }
}


