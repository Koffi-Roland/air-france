package com.airfrance.batch.automaticmerge.service;

import com.airfrance.batch.automaticmerge.exception.ServiceException;
import com.airfrance.batch.common.service.ProvideIndividualScoreService;
import com.airfrance.batch.common.wrapper.WrapperProvideIndividualScoreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {
    @Mock
    private ProvideIndividualScoreService provideIndividualScoreService;

    @InjectMocks
    private ScoreService scoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchScoreByGin_ThrowsException_IfGinsIsEmpty() {
        List<String> emptyGins = Collections.emptyList();

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> scoreService.fetchScoreByGin(emptyGins),
                "Expected fetchScoreByGin to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("empty input parameters"));
    }

    @Test
    void fetchScoreByGin_ThrowsException_IfServiceReturnsNull() {
        when(provideIndividualScoreService.calculatePcsScore(anyList())).thenReturn(null);

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> scoreService.fetchScoreByGin(List.of("123")),
                "Expected fetchScoreByGin to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("response is null"));
    }

    @Test
    void fetchScoreByGin_ReturnsWrapperPcs_IfServiceReturnsNonEmptyList() throws ServiceException {
        List<WrapperProvideIndividualScoreResponse> mockResponse = List.of(
                new WrapperProvideIndividualScoreResponse("123", 14),
                new WrapperProvideIndividualScoreResponse("456", 13),
                new WrapperProvideIndividualScoreResponse("789", 12)
                );
        when(provideIndividualScoreService.calculatePcsScore(anyList())).thenReturn(mockResponse);

        List<WrapperProvideIndividualScoreResponse> result = scoreService.fetchScoreByGin(List.of("123", "456", "789"));

        assertNotNull(result);
        assertEquals(mockResponse.size(), result.size());
        assertIterableEquals(mockResponse, result);
    }
}
