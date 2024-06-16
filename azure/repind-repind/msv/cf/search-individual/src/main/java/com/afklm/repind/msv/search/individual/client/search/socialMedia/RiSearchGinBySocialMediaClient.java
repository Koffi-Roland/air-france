package com.afklm.repind.msv.search.individual.client.search.socialMedia;

import com.afklm.repind.msv.search.individual.client.core.RestCall;
import com.afklm.repind.msv.search.individual.client.search.socialMedia.model.SearchGinBySocialMediaRequest;
import com.afklm.repind.msv.search.individual.model.SearchGinsResponse;
import org.springframework.http.HttpMethod;

public class RiSearchGinBySocialMediaClient extends RestCall<SearchGinBySocialMediaRequest, SearchGinsResponse> {

    public RiSearchGinBySocialMediaClient(String url, HttpMethod httpMethod, Class<SearchGinsResponse> iClass){
        super(url, httpMethod, iClass);
    }
}
