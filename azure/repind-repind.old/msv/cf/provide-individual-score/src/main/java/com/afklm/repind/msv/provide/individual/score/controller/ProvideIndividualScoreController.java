package com.afklm.repind.msv.provide.individual.score.controller;

import com.afklm.repind.msv.provide.individual.score.service.ProvideIndividualScoreService;
import com.afklm.repind.msv.provide.individual.score.wrapper.WrapperProvideIndividualScoreResponse;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequestMapping(value = "/pcs-score")
@AllArgsConstructor
public class ProvideIndividualScoreController {

    private ProvideIndividualScoreService provideIndividualScoreService;

    /**
     * Provide individual gin and score endpoint
     *
     * @Param Gins list of gins input
     * @return the list of gins with corresponding score
     **/
    @ApiOperation(value = "Provide Individual Score", notes = "Provide Individual Score", response = List.class)
    @GetMapping(value = "/{gins}")
    public ResponseEntity<List<WrapperProvideIndividualScoreResponse>> provideIndividualScore(
            @PathVariable("gins") final List<String> gins) {
        List<WrapperProvideIndividualScoreResponse> response = provideIndividualScoreService.calculatePcsScore(gins);

        HttpStatus status = HttpStatus.OK;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, status);
    }

}
