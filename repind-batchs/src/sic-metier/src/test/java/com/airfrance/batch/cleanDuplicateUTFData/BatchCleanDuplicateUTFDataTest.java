package com.airfrance.batch.cleanDuplicateUTFData;

import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import({JpaRepindConfig.class, JpaRepindUtf8Config.class})
class BatchCleanDuplicateUTFDataTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection mockConnection;
    @Mock
    private Logger mockLogger;
    @Mock
    private PreparedStatement mockPreparedStatement;
    private BatchCleanDuplicateUTFData batchCleanDuplicateUTFData;

    @BeforeEach
    @DisplayName("Test Constructor BatchCleanContactData")
    void setup() throws SQLException {

        when(dataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        batchCleanDuplicateUTFData = new BatchCleanDuplicateUTFData(dataSource);

    }

    @Test
    void testDeleteDuplicateIndividualFromUtfDataTable_success() throws SQLException {
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(1,0); // First call deletes, second call finds no duplicates

        batchCleanDuplicateUTFData.deleteDuplicateIndividualFromUtfDataTable("mjadar");

        verify(mockPreparedStatement, times(2)).executeUpdate(any());
        verify(mockConnection, times(1)).commit();
    }

    @Test
    void testDeleteDuplicateIndividualFromUtfDataTable_noDuplicateFound() throws SQLException {
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(0);

        batchCleanDuplicateUTFData.deleteDuplicateIndividualFromUtfDataTable("mjadar");

        // Verify that executeUpdate was called once and no commit
        verify(mockPreparedStatement, times(1)).executeUpdate(any());
    }


}