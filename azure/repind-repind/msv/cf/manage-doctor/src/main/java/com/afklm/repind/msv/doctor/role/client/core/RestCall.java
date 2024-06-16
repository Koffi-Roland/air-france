package com.afklm.repind.msv.doctor.role.client.core;

import com.afklm.repind.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestCall<T extends HttpRequestModel,V extends HttpResponseModel> {

    private final String url;
    private final HttpMethod httpMethod;
    private final Class<V> returnObject;

    @Autowired
    private ClientResponseErrorHandler responseErrorHandler;

    @Autowired
    private RestTemplate restTemplate;

    public RestCall(String url, HttpMethod httpMethod , Class<V> returnObject) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.returnObject = returnObject;
    }


    public V execute(T data) throws BusinessException {
        restTemplate.setErrorHandler(responseErrorHandler);
        HttpEntity<?> entity;
        if(data instanceof  HttpRequestBodyModel){
            entity = new HttpEntity<>( ((HttpRequestBodyModel) data).getBody() , data.getHttpHeaders());
        }
        else {
            entity = new HttpEntity<>(data.getHttpHeaders());
        }

        ResponseEntity<V> response = restTemplate.exchange(url, httpMethod, entity, returnObject , data.returnUriVariables());
        V body = response.getBody();
        if(!response.getStatusCode().is2xxSuccessful() && body != null ){
                throw new BusinessException(body);
        }
        return response.getBody();
    }
}
