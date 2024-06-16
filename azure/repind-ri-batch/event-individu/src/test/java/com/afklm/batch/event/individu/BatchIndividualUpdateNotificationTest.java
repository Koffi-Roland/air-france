package com.afklm.batch.event.individu;

import com.airfrance.batch.common.enums.RequirementEnum;
import com.airfrance.batch.common.exception.BatchException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.tracking.internal.TriggerChangeDS;
import com.airfrance.repind.service.tracking.internal.TriggerChangeIndividusDS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchIndividualUpdateNotificationTest {

    @InjectMocks
    private BatchIndividualUpdateNotification batchIndividualUpdateNotification;

    @Mock
    protected TriggerChangeDS triggerChangeDS;

    @Mock
    protected TriggerChangeIndividusDS triggerChangeIndividusDS;

    @Mock
    protected VariablesDS variablesDS;

    @Mock
    private SendNotifyEventIndividualTask sendNotifyEventIndividualTask;

    @Mock
    private UpdateStatusTask updateStatusTask;

    @Test
    void checkInit() {
        assertNotNull(this.batchIndividualUpdateNotification);
    }

    @Test
    void parseArgs() throws BatchException {
        assertDoesNotThrow(() -> {
            batchIndividualUpdateNotification.parseArgs(new String[]{"-l", "pathLocal", "-i", "-f", "-s"});
        });
        assertThrows(Exception.class, () -> {
            batchIndividualUpdateNotification.parseArgs(new String[]{"-a"});
        });
    }

    @Test
    void execute() throws JrafDomainException {
        TriggerChangeIndividusDTO dto = new TriggerChangeIndividusDTO();
        dto.setGin("GIN");
        dto.setId(1L);
        when(triggerChangeIndividusDS.findChanges(any())).thenReturn(List.of(dto));
        when(variablesDS.getEnv("BATCH_EVENT_RI_DEBUG_LOG_ACTIVATED", "false")).thenReturn("false");
        when(variablesDS.getEnv("BATCH_EVENT_RI_MAX_BLOCK_SIZE", "120000")).thenReturn("120000");
        when(variablesDS.getEnv("BATCH_EVENT_RI_MAX_ID_IN_SQL_UPDATE", 10)).thenReturn(10);
        when(variablesDS.getEnv("BATCH_EVENT_RI_NB_GIN_PER_THREAD", "4000")).thenReturn("4000");
        // Please create the folder C:\tmp\Event if not created
        batchIndividualUpdateNotification.parseArgs(new String[]{"-l", "C:\\tmp\\Event\\"});
        // We can test some behavior but what about the CI that folder to write ? How to detect that we are in CI ?
        // We launch a bunch of Threads that are difficults to verify so I don't know if it's worth to test it
        // It's better to test the Task class individually, the batch is only here to orchestrate the TaskClass
        //batchIndividualUpdateNotification.execute();
    }
}
