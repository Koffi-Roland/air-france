package com.afklm.repind.msv.graphql.bff.example.client;

import com.afklm.repind.msv.graphql.bff.example.exception.ExceptionMapper;
import com.afklm.repind.msv.graphql.bff.example.exception.ServiceException;
import com.afklm.repind.msv.graphql.bff.example.helper.RestTemplateExtended;
import com.afklm.repind.msv.graphql.bff.example.model.EmailResponse;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Email client component used to request the microservice email
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailClient {

    /**
     * Spring core environment - inject by spring
     */
    private final Environment env;

    /**
     * Client rest template - inject by spring
     */
    @Qualifier("emailRestTemplate")
    private final RestTemplateExtended emailRestTemplate;

    /**
     * Exception mapper - inject by spring
     */
    private final ExceptionMapper exceptionMapper;

    /**
     * Path email
     */
    private static final String PATH_EMAIL = "/emails";

    /**
     * Get emails according to the given gin
     *
     * @param gin individual number
     * @return list of email
     */
    public List<EmailResponse> getEmailsByGin(String gin) throws IOException, ServiceException
    {

        ResponseEntity<EmailResponse[]> responseEntityEmails = null;
        List<EmailResponse> emailResponses = null;
        try
        {
            //Create Headers for request
            HttpHeaders headers = emailRestTemplate.createHeaders();

            // Construct the URL using UriComponentsBuilder
            StringBuilder path = new StringBuilder();
            path.append(env.getProperty("url.location.provide-contact-data"));
            path.append(gin);
            path.append(PATH_EMAIL);
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(path.toString())
                    .queryParam("api_key", emailRestTemplate.getApiKey())
                    .queryParam("sig", emailRestTemplate.getSig());

            // Encode the URL to ensure it's safe and properly formatted
            URI uri = builder.build().encode().toUri();
            log.debug("path: " + uri);

            responseEntityEmails = emailRestTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(headers), EmailResponse[].class);

        }
        catch (HttpClientErrorException e)
        {
            RestError restError = this.exceptionMapper.mapReceivedObject(e);
            log.error(restError.getDescription());
            throw new ServiceException(restError, restError.getHttpStatus());
        }
        if (responseEntityEmails.hasBody())
        {
            emailResponses = Arrays.asList(Objects.requireNonNull(responseEntityEmails.getBody()));
        }
        log.info("MS respond with emails");
        return emailResponses;
    }


}
