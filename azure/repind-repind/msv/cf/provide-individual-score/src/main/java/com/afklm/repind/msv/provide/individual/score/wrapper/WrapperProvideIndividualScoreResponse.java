package com.afklm.repind.msv.provide.individual.score.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapperProvideIndividualScoreResponse {

    private String gin;
    private double score;

}

