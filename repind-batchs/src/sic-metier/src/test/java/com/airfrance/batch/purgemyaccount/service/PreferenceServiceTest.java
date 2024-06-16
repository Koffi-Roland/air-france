package com.airfrance.batch.purgemyaccount.service;

import com.airfrance.repind.dao.preference.PreferenceDataRepository;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PreferenceServiceTest {

    @Mock
    private PreferenceRepository preferenceRepository;

    @Mock
    private PreferenceDataRepository preferenceDataRepository;

    @InjectMocks
    private PreferenceService preferenceService;

    @Test
    void testPhysicalDeletePreference() {
        // Given
        List<Long> preferenceLoneList = new ArrayList<>();
        preferenceLoneList.add(1L);

        // When
        preferenceService.physicalDeletePreference(1L);

        // Then
        verify(preferenceRepository).deleteByPreferenceIdIn(preferenceLoneList);
        verify(preferenceDataRepository).deleteByPreferenceIdIn(preferenceLoneList);
    }
}
