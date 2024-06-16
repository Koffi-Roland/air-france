package com.afklm.repind.msv.search.individual.client.core;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.model.error.SystemError;
import lombok.SneakyThrows;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Service
public class ClientResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new BusinessException(SystemError.API_CLIENT_CALL_ERROR);
    }
}
