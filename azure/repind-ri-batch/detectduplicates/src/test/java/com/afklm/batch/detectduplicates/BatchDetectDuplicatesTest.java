package com.afklm.batch.detectduplicates;

import com.afklm.batch.detectduplicates.service.DetectDuplicatesService;
import com.airfrance.batch.common.exception.ParametersException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BatchDetectDuplicatesTest {


    @Mock
    private DetectDuplicatesService detectDuplicatesService;

    @InjectMocks
    private BatchDetectDuplicates batchDetectDuplicates;

    @Test
    public void checkInit() {
        assertNotNull(batchDetectDuplicates);
    }

    @Test
    public void execute() throws Exception {
        batchDetectDuplicates.execute();
        verify(detectDuplicatesService, times(1)).selectDuplicatedCandidatesByNomPrenom();
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/drop.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_gin.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_email_sgin_no_dup.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_email.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/update_table_same_nomprenom_candidates.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_and_email_or_telecom.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/copy_no_duplicate_element.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/copy_no_duplicate_element_email_and_telecom.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_and_telecom_and_email.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_telecom_sgin_no_dup.sql");
        verify(detectDuplicatesService, times(1)).launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_telecom.sql");
        verify(detectDuplicatesService, times(1)).removeDuplicatesRow();
        verify(detectDuplicatesService, times(1)).removeDuplicatesRowForEmailOrTelecom();
        verify(detectDuplicatesService, times(1)).writeResultFile(any(), any());
    }

    @Test
    public void parseArgs() throws ParametersException {
        assertDoesNotThrow(() -> {
            BatchDetectDuplicates.parseArgs(new String[]{"-O", "myFile"});
        });
        assertThrows(Exception.class, () -> {
            BatchDetectDuplicates.parseArgs(new String[]{"-U", "myFile"});
        });
        assertThrows(Exception.class, () -> {
            BatchDetectDuplicates.parseArgs(new String[]{"+O", "myFile"});
        });
    }
}
