package com.afklm.batch.event.individu;

import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PrepareNotifyEventIndividualTaskTest {

    @InjectMocks
    private PrepareNotifyEventIndividualTask prepareNotifyEventIndividualTask;

    @Test
    public void checkInit() {
        assertNotNull(prepareNotifyEventIndividualTask);
    }

    @Test
    public void run() {
        // Needed to mock BatchIndividualUpdateNotification static call
        try (MockedStatic<BatchIndividualUpdateNotification> utilities = Mockito.mockStatic(BatchIndividualUpdateNotification.class)) {
            LinkedBlockingQueue<TriggerChangeIndividusDTO> changes = new LinkedBlockingQueue<>();
            TriggerChangeIndividusDTO dto = new TriggerChangeIndividusDTO();
            dto.setGin("GIN");
            changes.add(dto);
            prepareNotifyEventIndividualTask.setTaskName("TOTO");
            prepareNotifyEventIndividualTask.setQueue(changes);
            prepareNotifyEventIndividualTask.run();
            // can't verify that addBlocksToSend is called because it is static
        }
    }
}
