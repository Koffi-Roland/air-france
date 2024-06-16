package com.afklm.repind.msv.graphql.bff.example.exception;

import com.afklm.repind.msv.graphql.bff.example.model.error.RestError;
import com.afklm.repind.msv.graphql.bff.example.model.error.ServerRestError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Component
@Slf4j
public  class ExceptionMapper {

    /**
     * Map receive object to RestError
     *
     * @param e HttpClientErrorException
     * @return RestError
     * @throws IOException the general class of exceptions produced by failed or interrupted I/O operations
     */
    public RestError mapReceivedObject(HttpClientErrorException e) throws IOException
    {
        RestError restError = null;

        ServerRestError serverRestError = new ObjectMapper().readValue(e.getResponseBodyAsString(), ServerRestError.class);
        if (serverRestError != null && serverRestError.getRestError() != null)
        {
            restError = serverRestError.getRestError();
        }
        return restError;
    }
}
