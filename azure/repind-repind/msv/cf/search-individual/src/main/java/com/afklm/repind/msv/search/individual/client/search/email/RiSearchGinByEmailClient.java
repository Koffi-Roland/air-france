package com.afklm.repind.msv.search.individual.client.search.email;

import com.afklm.repind.msv.search.individual.client.core.RestCall;
import com.afklm.repind.msv.search.individual.client.search.email.model.SearchGinByEmailRequest;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import org.springframework.http.HttpMethod;

public class RiSearchGinByEmailClient extends RestCall<SearchGinByEmailRequest, SearchGinsResponse> {

    public RiSearchGinByEmailClient(String url, HttpMethod httpMethod, Class<SearchGinsResponse> iClass){
        super(url, httpMethod, iClass);
    }
}
