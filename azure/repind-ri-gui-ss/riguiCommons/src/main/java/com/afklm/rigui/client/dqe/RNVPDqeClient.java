package com.afklm.rigui.client.dqe;

import com.afklm.rigui.exception.AddressNormalizationException;
import com.afklm.rigui.client.core.RestCall;
import com.afklm.rigui.client.dqe.encoder.RNVPDqeEncoder;
import com.afklm.rigui.client.dqe.model.RNVPDqeRequestModel;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.client.dqe.RNVP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RNVPDqeClient extends RestCall<RNVPDqeRequestModel, RNVP> {

    @Autowired
    private RNVPDqeEncoder rnvpDqeEncoder;

    public RNVPDqeClient(String url, HttpMethod httpMethod, Class<RNVP> returnObject, RestTemplate iRestTemplate) {
        super(url, httpMethod, returnObject,iRestTemplate);
    }

    public RNVP execute(PostalAddress iAddress) throws AddressNormalizationException {
        return super.execute(rnvpDqeEncoder.encode(iAddress));
    }
}
