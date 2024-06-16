package com.afklm.repind.msv.customer.adaptor.service.encoder.individus;

import com.afklm.repind.msv.customer.adaptor.client.core.HttpResponseModel;
import com.afklm.repind.msv.customer.adaptor.client.sfmc.individus.upsert.model.UpsertIndividusRequest;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.wrapper.individus.WrapperUpsertIndividusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpsertIndividusEncoder<V extends HttpResponseModel>{

    public UpsertIndividusRequest encodeUpdateIndividusRequest(UpsertIndividusRequestCriteria upsertIndividusCriteria){
        UpsertIndividusRequest upsertIndividusRequest = new UpsertIndividusRequest();

        upsertIndividusRequest.setBody(upsertIndividusCriteria.getIndividusList());

        return upsertIndividusRequest;

    }


    public WrapperUpsertIndividusResponse decode(ResponseEntity<V> upsertIndividusResponse){
        WrapperUpsertIndividusResponse response = new WrapperUpsertIndividusResponse();
        response.setHttpStatusCode(upsertIndividusResponse.getStatusCode());
        return response;
    }
}
