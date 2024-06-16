package com.afklm.spring.security.habile.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.afklm.spring.security.habile.GenericHeaders.SM_USER_ANONYMOUS;

/**
 * AuthenticationInterceptor
 * 
 * provides a way to add authentication before RestTemplate call
 * 
 * Support for Basic and Habile authentication is provided
 * 
 * @author m405991
 *
 */
@Component
public class AuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private String username;
    private String password;
    private AuthenticationMode authenticationMode;
    private boolean anonymous;

    /**
     * Set Authentication mode
     * 
     * @param authenticationMode may be null
     * @param username may be null
     * @param password may be null
     */
    public void setAuthentication(AuthenticationMode authenticationMode, String username, String password) {
        this.authenticationMode = authenticationMode;
        this.username = username;
        this.password = password;
    }

    /**
     * Set Anonymous authentication
     * 
     * @param authenticationMode
     * @param user
     */
    public void setAnonymousAuthentication(AuthenticationMode authenticationMode) {
        this.authenticationMode = authenticationMode;
        this.username = SM_USER_ANONYMOUS;
        this.anonymous = true;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if (AuthenticationMode.BASIC.equals(authenticationMode)) {
            String token = Base64Utils.encodeToString((this.username + ":" + this.password).getBytes(StandardCharsets.UTF_8));
            request.getHeaders().add("Authorization", "Basic " + token);
            LOGGER.info("Adding header Authorization for user '{}': Basic '{}'", this.username, token);
        } else if (AuthenticationMode.HABILE.equals(authenticationMode)) {
            if (anonymous) {
                request.getHeaders().add("x-forwarded-user", "");
                request.getHeaders().add("x-access-token", "");
            } else {
                request.getHeaders().add("SM_USER", this.username);
            }
            request.getHeaders().add("SM_LOGOUT", "fakeLogout");
            LOGGER.info("Adding header SM_USER: '{}'", this.username);
        } else {
            LOGGER.info("Adding no security header");
        }

        return execution.execute(request, body);
    }
}