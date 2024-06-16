package com.afklm.repind.msv.search.individual.client.search.phone;

import com.afklm.repind.msv.search.individual.client.core.RestCall;
import com.afklm.repind.msv.search.individual.client.search.phone.model.SearchGinByPhoneNumberRequest;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import org.springframework.http.HttpMethod;

public class RiSearchGinByPhoneNumberClient extends RestCall<SearchGinByPhoneNumberRequest, SearchGinsResponse> {

    public RiSearchGinByPhoneNumberClient(String url, HttpMethod httpMethod, Class<SearchGinsResponse> iClass){
        super(url, httpMethod, iClass);
    }
}
