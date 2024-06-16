package com.afklm.repind.msv.search.individual.client.search.lastnameandfirstname;

import com.afklm.repind.msv.search.individual.client.core.RestCall;
import com.afklm.repind.msv.search.individual.client.search.lastnameandfirstname.model.SearchGinByLastnameAndFirstnameRequest;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import org.springframework.http.HttpMethod;

public class RiSearchGinByLastnameAndFirstnameClient extends RestCall<SearchGinByLastnameAndFirstnameRequest, SearchGinsResponse> {

    public RiSearchGinByLastnameAndFirstnameClient(String url, HttpMethod httpMethod, Class<SearchGinsResponse> iClass){
        super(url, httpMethod, iClass);
    }
}
