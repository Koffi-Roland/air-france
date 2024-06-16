package com.afklm.batch.detectduplicates.service;

import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailAndTelecomEntity;
import com.afklm.batch.detectduplicates.entity.SameLastnameNameEmailOrTelecomEntity;
import com.afklm.batch.detectduplicates.repository.SameLastnameNameEmailAndTelecomNoDuplicateRepository;
import com.afklm.batch.detectduplicates.repository.SameLastnameNameEmailAndTelecomRepository;
import com.afklm.batch.detectduplicates.repository.SameLastnameNameEmailOrTelecomRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DetectDuplicatesServiceTest {

    private static final int CHUNK_SIZE = 10000;

    @Mock
    private JdbcTemplate jdbcTemplateSicOds;

    @Mock
    private FileService fileService;

    @Mock
    private SameLastnameNameEmailAndTelecomNoDuplicateRepository sameLastnameNameEmailAndTelecomNoDuplicateRepository;
    @Mock
    private SameLastnameNameEmailAndTelecomRepository sameLastnameNameEmailAndTelecomRepository;
    @Mock
    private SameLastnameNameEmailOrTelecomRepository sameLastnameNameEmailOrTelecomRepository;

    @InjectMocks
    private DetectDuplicatesService detectDuplicatesService;

    @Test
    void checkInit() {
        assertNotNull(detectDuplicatesService);
    }

    @Test
    void launchPlSqlScript() throws IOException {
        when(fileService.readFile("myScript.sql")).thenReturn("myScript");
        detectDuplicatesService.launchPlSqlScript("myScript.sql");
        verify(jdbcTemplateSicOds, times(1)).execute("myScript");
    }

    @Test
    void removeDuplicatesRow() {
        SameLastnameNameEmailAndTelecomEntity entity = new SameLastnameNameEmailAndTelecomEntity();
        entity.setLastnameName("TOTO");
        entity.setTelecomNbGINs(1);
        entity.setEmailNbGINs(2);
        PageImpl<SameLastnameNameEmailAndTelecomEntity> page = new PageImpl<>(List.of(entity));
        page.isLast();
        when(sameLastnameNameEmailAndTelecomRepository.findAllByDuplicateIs(true, Pageable.ofSize(CHUNK_SIZE))).thenReturn(page);
        when(sameLastnameNameEmailAndTelecomRepository.findAllByLastnameNameAndAndTelecomNbGINsIsGreaterThanAndEmailNbGINsIsGreaterThan("TOTO", 1, 2)).thenReturn(List.of());
        detectDuplicatesService.removeDuplicatesRow();
        verify(sameLastnameNameEmailAndTelecomNoDuplicateRepository, times(1)).saveAll(any());
    }

    @Test
    void removeDuplicatesRowForEmailOrTelecom() {
        SameLastnameNameEmailOrTelecomEntity entity = new SameLastnameNameEmailOrTelecomEntity();
        entity.setLastnameName("TOTO");
        entity.setNbGINs(1);
        PageImpl<SameLastnameNameEmailOrTelecomEntity> page = new PageImpl<>(List.of(entity));
        page.isLast();
        when(sameLastnameNameEmailOrTelecomRepository.findAllByDuplicateIs(true, Pageable.ofSize(CHUNK_SIZE))).thenReturn(page);
        when(sameLastnameNameEmailOrTelecomRepository.findAllByLastnameNameAndNbGINsGreaterThan("TOTO", 1)).thenReturn(List.of());
        detectDuplicatesService.removeDuplicatesRowForEmailOrTelecom();
        verify(sameLastnameNameEmailAndTelecomNoDuplicateRepository, times(1)).saveAll(any());
    }

    @Test
    void selectDuplicatedCandidatesByNomPrenom() throws IOException {
        when(jdbcTemplateSicOds.queryForObject(any(), eq(Integer.class))).thenReturn(0);
        when(fileService.readFile(any())).thenReturn("script");
        detectDuplicatesService.selectDuplicatedCandidatesByNomPrenom();
        verify(jdbcTemplateSicOds, times(1)).execute("script");
    }

    @Test
    void selectDuplicatedCandidatesByNomPrenomNoLaunch() throws IOException {
        when(jdbcTemplateSicOds.queryForObject(any(), eq(Integer.class))).thenReturn(1);
        detectDuplicatesService.selectDuplicatedCandidatesByNomPrenom();
        verify(jdbcTemplateSicOds, times(0)).execute(anyString());
    }

}
