package com.airfrance.batch.purgemyaccount.processor;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.purgemyaccount.model.MyaLogicalToDelete;
import com.airfrance.batch.purgemyaccount.service.AccountDataService;
import com.airfrance.batch.purgemyaccount.service.RoleContratService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit testing batch - PurgeMyAccount processor
 */
@ExtendWith(MockitoExtension.class)
public class MyaLogicalDeleteProcessorTest {


    public final static Integer ID = 18;
    public final static Integer VERSION = 1;
    private static final String GIN = "12345678988";
    private static final String STATUS = "V";
    private static final String SRIN = "12345";
    private static final String TYPE_CONTRAT = "MA";
    private static final String NUMERO_CONTRAT = "M17ZH2";


    @Mock
    private AccountDataService accountDataService;

    @Mock
    private RoleContratService roleContratService;

    @Mock
    private SummaryService summaryService;

    @InjectMocks
    private MyaLogicalDeleteProcessor myaLogicalDeleteProcessor;

    @Test
    @DisplayName("Test : test process method in MyaLogicalDeleteProcessor")
    void testProcess() throws IOException {
        // Given
        MyaLogicalToDelete myaLogicalToDeleteModel = MyaLogicalToDelete.builder()
                .id(ID)
                .iversion(VERSION)
                .sgin(GIN)
                .status(STATUS)
                .srin(SRIN)
                .stypeContrat(TYPE_CONTRAT)
                .snumeroContrat(NUMERO_CONTRAT)
                .build();

        // When
        when(summaryService.getProcessed()).thenReturn(new AtomicInteger(0));
        when(accountDataService.logicalDeleteAccountData(myaLogicalToDeleteModel)).thenReturn(true);
        when(roleContratService.logicalDeleteRoleContrat(myaLogicalToDeleteModel)).thenReturn(true);


        MyaLogicalToDelete result = myaLogicalDeleteProcessor.process(myaLogicalToDeleteModel);

        // Verify that the logical delete methods were called with the correct arguments
        verify(accountDataService).logicalDeleteAccountData(myaLogicalToDeleteModel);
        verify(roleContratService).logicalDeleteRoleContrat(myaLogicalToDeleteModel);

        // Verify that the result is the same as the input object since both logical deletions were successful
        assertEquals(myaLogicalToDeleteModel, result);
    }


}


