package com.afklm.repind.msv.provide.individual.score.controller;

import com.afklm.repind.msv.provide.individual.score.service.ProvideIndividualScoreService;
import com.afklm.repind.msv.provide.individual.score.utils.GenerateTestData;
import com.afklm.repind.msv.provide.individual.score.wrapper.WrapperProvideIndividualScoreResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProvideIndividualScoreControllerTest {

    @InjectMocks
    ProvideIndividualScoreController provideIndividualScoreController;

    @Mock
    ProvideIndividualScoreService provideIndividualScoreService;

    @Test
    void provideIndividualScoreTest() {
        when(provideIndividualScoreService.calculatePcsScore(any())).thenReturn(GenerateTestData.buildResponse());
        ResponseEntity<List<WrapperProvideIndividualScoreResponse>> response = provideIndividualScoreController.provideIndividualScore(Arrays.asList("0123012395000"));

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertThat(response.getBody()).hasSize(1)
                .extracting("gin")
                .containsExactlyInAnyOrder("0123012395000");
    }
}
