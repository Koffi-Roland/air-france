package com.afklm.repind.msv.graphql.bff.example.client;

import com.afklm.repind.msv.graphql.bff.example.exception.ExceptionMapper;
import com.afklm.repind.msv.graphql.bff.example.exception.ServiceException;
import com.afklm.repind.msv.graphql.bff.example.helper.RestTemplateExtended;
import com.afklm.repind.msv.graphql.bff.example.model.LastActivity;
import com.afklm.repind.msv.graphql.bff.example.model.error.RestError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

/**
 * Last activity client component used to request the microservice last activity
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LastActivityClient {

    /**
     * Spring core environment - inject by spring
     */
    private final Environment env;
    /**
     * Client rest template -inject by spring
     */
    @Qualifier("lastActivityRestTemplate")
    private final RestTemplateExtended lastActivityTemplate;

    /**
     * Exception mapper - inject by spring
     */
    private final ExceptionMapper exceptionMapper;

    /**
     * Get last activity according to the given
     *
     * @param gin individual number
     * @return last activity
     */
    public LastActivity getLastActivityByGin(String gin) throws IOException, ServiceException
    {

        ResponseEntity<LastActivity> responseLastActivity = null;

        try
        {
            //Create Headers for request
            HttpHeaders headers = lastActivityTemplate.createHeaders();

            // Construct the URL using UriComponentsBuilder
            StringBuilder path = new StringBuilder();
            path.append(env.getProperty("url.location.provide-last-activity"));
            path.append(gin);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(path.toString())
                    .queryParam("api_key", lastActivityTemplate.getApiKey())
                    .queryParam("sig", lastActivityTemplate.getSig());

            // Encode the URL to ensure it's safe and properly formatted
            URI uri = builder.build().encode().toUri();
            log.debug("uri: "+ uri);
            responseLastActivity = lastActivityTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(headers), LastActivity.class);

        }
        catch (HttpClientErrorException e)
        {
            RestError restError = this.exceptionMapper.mapReceivedObject(e);
            throw new ServiceException(restError,  restError.getHttpStatus());
        }
        log.info("MS respond with Last Activity");
        return responseLastActivity.getBody();
    }

}
