package com.airfrance.batch.purgemyaccount.service;

import com.airfrance.batch.purgemyaccount.model.MyaLogicalToDelete;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.role.RoleContrats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit testing service layer - Deduplication communication preferences service
 */
@ExtendWith(MockitoExtension.class)
class RoleContratServiceTest {

    @Mock
    private RoleContratsRepository roleContratsRepository;
    @Mock
    private BusinessRoleRepository businessRoleRepository;
    @InjectMocks
    private RoleContratService roleContratService;
    private MyaLogicalToDelete myaLogicalToDeleteModel;

    private final static Integer ID = 18;
    private final static Integer VERSION = 1;
    private static final String GIN = "12345678988";
    private static final String STATUS = "V";
    private static final String SRIN = "12345";
    private static final String TYPE_CONTRAT = "MA";
    private static final String NUMERO_CONTRAT = "M17ZH2";
    public static final String SIGNATURE_MODIFICATION_PURGE_MYA = "PURGE_MYA";
    public static final String SITE_MODIFICATION_QVI = "QVI";

    @BeforeEach
    public void setup()
    {
        final Date date = new Date();
        //given - precondition or setup
        this.myaLogicalToDeleteModel = MyaLogicalToDelete.builder()
                .id(ID)
                .iversion(VERSION)
                .sgin(GIN)
                .status(STATUS)
                .srin(SRIN)
                .stypeContrat(TYPE_CONTRAT)
                .snumeroContrat(NUMERO_CONTRAT)
                .ddateCreation(date)
                .ddateModification(date)
                .build();
    }

    @Test
    @Transactional
    void testLogicalDeleteAccountDataNotFound() {
        // Mock the When method
        when(roleContratsRepository.findBySrin(this.myaLogicalToDeleteModel.getSrin())).thenReturn(null);

        // Call the logicalDeleteRoleContrat method
        boolean result = roleContratService.logicalDeleteRoleContrat(this.myaLogicalToDeleteModel);

        // Assert
        assertFalse(result);
        verify(roleContratsRepository, never()).logicalDeleteRoleContract(any(), any(), any(), any(), any(), any());
    }

    @Test
    void testLogicalDeleteRoleContrat_Found() {
        // Mock the When method
        when(roleContratsRepository.findBySrin(this.myaLogicalToDeleteModel.getSrin())).thenReturn(new RoleContrats());

        // Call the logicalDeleteRoleContrat method
        boolean result = roleContratService.logicalDeleteRoleContrat(this.myaLogicalToDeleteModel);

        // Verify that the repository method was called with the correct arguments
        assertTrue(result);
        verify(roleContratsRepository).findBySrin(
                myaLogicalToDeleteModel.getSrin()
        );
        verify(roleContratsRepository).logicalDeleteRoleContract(
                anyString(), anyString(), anyString(), any(Date.class), anyString(), anyString()
        );
    }
    @Test
    void testPhysicalDeleteAccountData() {
        // Given
        Integer icleRole = 1;

        // When
        roleContratService.physicalDeleteRoleContrat(icleRole);

        // Then
        verify(roleContratsRepository).deleteByCleRole(icleRole);
        verify(businessRoleRepository).deleteByCleRole(icleRole);
    }
}