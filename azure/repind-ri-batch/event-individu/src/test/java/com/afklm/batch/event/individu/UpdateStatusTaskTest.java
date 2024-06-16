package com.afklm.batch.event.individu;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.tracking.internal.TriggerChangeIndividusDS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateStatusTaskTest {

    @Mock
    private TriggerChangeIndividusDS triggerChangeIndividusDS;

    @Mock
    private BufferedWriter bfwOutput;

    @InjectMocks
    private UpdateStatusTask updateStatusTask;

    @Test
    void checkInit() {
        assertNotNull(updateStatusTask);
    }

    @Test
    void setIdListMaxSize() {
        assertDoesNotThrow(() -> this.updateStatusTask.setIdListMaxSize(20));
        assertThrows(Exception.class, () -> this.updateStatusTask.setIdListMaxSize(2000));
    }

    @Test
    void run() {
        // Used to mock LogBatchOutput
        try (MockedStatic<BatchIndividualUpdateNotification> utilities = Mockito.mockStatic(BatchIndividualUpdateNotification.class)) {
            LinkedBlockingQueue<List<Long>> idBlockToUpdate = new LinkedBlockingQueue<>();
            idBlockToUpdate.add(List.of(1L, 2L));
            idBlockToUpdate.add(List.of(3L, 4L));
            idBlockToUpdate.add(List.of()); // Add an empty List so we know when to stop
            this.updateStatusTask.setTaskName("TASK_TOTO");
            this.updateStatusTask.setEntriesQueue(idBlockToUpdate);
            this.updateStatusTask.run();
            verify(triggerChangeIndividusDS, times(2)).updateStatus(any());
        } catch (JrafDomainException e) {
            throw new RuntimeException(e);
        }
    }
}
