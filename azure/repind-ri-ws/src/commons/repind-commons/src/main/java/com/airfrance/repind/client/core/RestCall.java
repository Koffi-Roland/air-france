package com.airfrance.repind.client.core;

import com.airfrance.ref.exception.AddressNormalizationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestCall<T extends HttpRequestModel,V> {

    private final String url;
    private final HttpMethod httpMethod;
    private final Class<V> returnObject;
    private RestTemplate restTemplate;

    public RestCall(String url, HttpMethod httpMethod , Class<V> returnObject, RestTemplate iRestTemplate) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.returnObject = returnObject;
        this.restTemplate = iRestTemplate;
    }

    @Autowired
    private ClientResponseErrorHandler responseErrorHandler;

    protected V execute(T data) throws AddressNormalizationException {
        restTemplate.setErrorHandler(responseErrorHandler);
        HttpEntity<?> entity;
        if(data instanceof  HttpRequestBodyModel){
            entity = new HttpEntity<>( ((HttpRequestBodyModel) data).getBody() , data.getHttpHeaders());
        }
        else {
            entity = new HttpEntity<>(data.getHttpHeaders());
        }

        ResponseEntity<V> response = restTemplate.exchange(url, httpMethod, entity, returnObject , data.returnUriVariables());
        if(!response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
                throw new AddressNormalizationException("Error on client api rest response");
        }
        log.info("Response entity {}",response.getBody());
        return response.getBody();
    }
}
