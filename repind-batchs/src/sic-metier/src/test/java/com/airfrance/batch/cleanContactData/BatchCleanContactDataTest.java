package com.airfrance.batch.cleanContactData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BatchCleanContactDataTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    private BatchCleanContactData batchCleanContactData;

    @BeforeEach
    @DisplayName("Test Constructor BatchCleanContactData")
    void setup() throws SQLException {

        when(dataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        batchCleanContactData = new BatchCleanContactData(dataSource);

    }

    @Test
    void testDeleteXstatusFromEmailTable_success() throws SQLException {
        String email = "test@example.com";
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(1);

        batchCleanContactData.deleteXstatusFromEmailTable(email);

        verify(mockPreparedStatement, times(2)).executeUpdate(any());
        verify(mockConnection, times(2)).commit();
    }

    @Test
    void testDeleteXstatusFromEmailTable_noEmailsToDelete() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(0);

        batchCleanContactData.deleteXstatusFromEmailTable("test@example.com");

        verify(mockConnection, never()).commit();
    }

    @Test
    void testDeleteXstatusFromEmailTable_sQLExceptionHandling() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate(any())).thenThrow(new SQLException());

        batchCleanContactData.deleteXstatusFromEmailTable("test@example.com");

        verify(mockConnection, times(2)).rollback();
    }

    @Test
    void testDeleteXstatusFromTelecomTable_success() throws SQLException {
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(1);

        batchCleanContactData.deleteXstatusFromTelecomTable("99999999");

        verify(mockPreparedStatement, times(2)).executeUpdate(any());
        verify(mockConnection, times(2)).commit();
    }

    @Test
    void testDeleteXstatusFromTelecomTable_noEmailsToDelete() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(0);

        batchCleanContactData.deleteXstatusFromTelecomTable("99999999");

        verify(mockConnection, never()).commit();
    }

    @Test
    void testDeleteXstatusFromTelecomTable_sQLExceptionHandling() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate(any())).thenThrow(new SQLException());

        batchCleanContactData.deleteXstatusFromTelecomTable("99999999");

        verify(mockConnection, times(2)).rollback();
    }

    @Test
    void testDeleteXstatusFromPostAdrTable_success() throws SQLException {
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(1);

        batchCleanContactData.deleteXstatusFromPostAdrTable("17 av FLRS", "123", "GP");

        verify(mockPreparedStatement, times(4)).executeUpdate(any());
        verify(mockConnection, times(4)).commit();
    }

    @Test
    void testDeleteXstatusFromPostAdrTable_noEmailsToDelete() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate(any())).thenReturn(0);

        batchCleanContactData.deleteXstatusFromPostAdrTable("17 av FLRS", "123", "GP");

        verify(mockConnection, never()).commit();
    }

    @Test
    void testDeleteXstatusFromPostAdrTable_sQLExceptionHandling() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate(any())).thenThrow(new SQLException());

        batchCleanContactData.deleteXstatusFromPostAdrTable("17 av FLRS", "123", "GP");

        verify(mockConnection, times(4)).rollback();
    }



}