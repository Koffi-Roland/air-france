package com.airfrance.repind.client.core;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import lombok.SneakyThrows;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;

@Service
public class ClientResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response){
        return false;
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse response){
        throw new JrafDomainException("Error client api call");
    }
}
