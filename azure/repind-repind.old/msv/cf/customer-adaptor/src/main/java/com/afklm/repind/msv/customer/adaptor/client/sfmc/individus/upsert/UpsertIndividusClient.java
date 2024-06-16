package com.afklm.repind.msv.customer.adaptor.client.sfmc.individus.upsert;

import com.afklm.repind.msv.customer.adaptor.client.core.RestCall;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.individus.global.IndividusResponseModel;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.individus.upsert.model.UpsertIndividusRequest;
import org.springframework.http.HttpMethod;

public class UpsertIndividusClient extends RestCall<UpsertIndividusRequest, IndividusResponseModel> {

    public UpsertIndividusClient(String url, HttpMethod httpMethod, Class<IndividusResponseModel> returnObject) {
        super(url, httpMethod, returnObject);
    }
}
