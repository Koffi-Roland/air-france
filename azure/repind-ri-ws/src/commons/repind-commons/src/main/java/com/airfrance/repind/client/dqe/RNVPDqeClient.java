package com.airfrance.repind.client.dqe;

import com.airfrance.ref.exception.AddressNormalizationException;
import com.airfrance.repind.client.core.RestCall;
import com.airfrance.repind.client.dqe.encoder.RNVPDqeEncoder;
import com.airfrance.repind.client.dqe.model.RNVPDqeRequestModel;
import com.airfrance.repind.entity.adresse.PostalAddress;
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
