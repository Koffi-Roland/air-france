package com.airfrance.batch.purgemyaccount.service;

import com.airfrance.batch.purgemyaccount.model.MyaLogicalToDelete;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit testing service layer - Deduplication communication preferences service
 */
@ExtendWith(MockitoExtension.class)
class AccountDataServiceTest {

    @Mock
    private AccountDataRepository accountDataRepository;
    @InjectMocks
    private AccountDataService accountDataService;

    private final static Integer ID = 18;
    private final static Integer VERSION = 1;
    private static final String GIN = "12345678988";
    private static final String STATUS = "V";
    private static final String SRIN = "12345";
    private static final String TYPE_CONTRAT = "MA";
    private static final String NUMERO_CONTRAT = "M17ZH2";


    @Test
    @Transactional
    void testLogicalDeleteAccountDataNotFound() {
        // Arrange
        MyaLogicalToDelete myaLogicalToDeleteModel = MyaLogicalToDelete.builder()
                .id(ID)
                .iversion(VERSION)
                .sgin(GIN)
                .status(STATUS)
                .srin(SRIN)
                .stypeContrat(TYPE_CONTRAT)
                .snumeroContrat(NUMERO_CONTRAT)
                .build();

        Integer id = myaLogicalToDeleteModel.getId();
        when(accountDataRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        boolean result = accountDataService.logicalDeleteAccountData(myaLogicalToDeleteModel);

        // Assert
        assertFalse(result);
        verify(accountDataRepository, never()).logicalDeleteAccountData(any(), any(), any(), any(), any());
    }

    @Test
    void testPhysicalDeleteAccountData() {
        // Given
        Integer id = 1;

        // When
        accountDataService.physicalDeleteAccountData(id);

        // Then
        verify(accountDataRepository).deleteById(id);
    }

}