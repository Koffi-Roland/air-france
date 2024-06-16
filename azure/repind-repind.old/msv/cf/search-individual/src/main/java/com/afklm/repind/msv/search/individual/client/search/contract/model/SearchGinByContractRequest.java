package com.afklm.repind.msv.search.individual.client.search.contract.model;

import com.afklm.repind.msv.search.individual.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class SearchGinByContractRequest extends HttpRequestModel {
    private String contract;
}
