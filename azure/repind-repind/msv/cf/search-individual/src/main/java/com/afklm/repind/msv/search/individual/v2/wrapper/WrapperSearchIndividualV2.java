package com.afklm.repind.msv.search.individual.v2.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
public class WrapperSearchIndividualV2 {

    private String gin;
}
