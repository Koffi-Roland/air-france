package com.afklm.repind.msv.search.individual.client.search.contract;

import com.afklm.repind.msv.search.individual.client.core.RestCall;
import com.afklm.repind.msv.search.individual.client.search.contract.model.SearchGinByContractRequest;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import org.springframework.http.HttpMethod;

public class RiSearchGinByContractClient extends RestCall<SearchGinByContractRequest, SearchGinsResponse> {

    public RiSearchGinByContractClient(String url, HttpMethod httpMethod, Class<SearchGinsResponse> iClass){
        super(url, httpMethod, iClass);
    }
}
