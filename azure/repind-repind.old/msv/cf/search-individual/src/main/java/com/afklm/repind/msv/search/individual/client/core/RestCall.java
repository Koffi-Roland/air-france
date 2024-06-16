package com.afklm.repind.msv.search.individual.client.core;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.model.error.SystemError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestCall<T extends HttpRequestModel,V extends HttpResponseModel> {

    private final String url;
    private final HttpMethod httpMethod;
    private final Class<V> returnObject;

    public RestCall(String url, HttpMethod httpMethod , Class<V> returnObject) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.returnObject = returnObject;
    }

    @Autowired
    private ClientResponseErrorHandler responseErrorHandler;

    @Autowired
    private RestTemplate restTemplate;

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
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new BusinessException(SystemError.API_SYSTEM_UNKNOW_ERROR);
        }
        return response.getBody();
    }
}
