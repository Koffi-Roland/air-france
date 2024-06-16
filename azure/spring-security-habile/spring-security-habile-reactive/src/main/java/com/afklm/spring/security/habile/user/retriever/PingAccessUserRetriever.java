package com.afklm.spring.security.habile.user.retriever;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

import com.afklm.spring.security.habile.oidc.JwtUtils;
import com.afklm.spring.security.habile.oidc.UserInfo;
import com.afklm.spring.security.habile.user.AbstractUserRetriever;

import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link AbstractUserRetriever} based on Ping Access provided by Habile.
 *
 * @author TECC
 */
@Service("ProvideUserRightsAccessPing")
public class PingAccessUserRetriever extends AbstractUserRetriever{
    private static final Logger LOGGER = LoggerFactory.getLogger(PingAccessUserRetriever.class);

    private WebClient client;

    public PingAccessUserRetriever() {
        client = WebClient.builder()
              .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
              .build();
    }

    @Override
    protected Mono<UserInfo> callHabile(String username, String token) {
        LOGGER.trace("Calling userinfo endpoint");

        Optional<String> userInfoUri = JwtUtils.getStringElementFromBody(token, JwtUtils.ISSUER_KEY);
        if (userInfoUri.isEmpty()) {
            throw new RestClientException("No issuer field extracted from JWT");
        }
        try {
            URL userInfoURL = new URL(userInfoUri.get().toLowerCase());
            if (!JwtUtils.TRUSTED_ISSUERS.contains(userInfoURL.getHost())) {
                if(LOGGER.isDebugEnabled()) {
                    JwtUtils.TRUSTED_ISSUERS.forEach(trustedIssuer -> LOGGER.debug("Comparing '{}' and '{}' seems to have failed", userInfoURL.getHost(), trustedIssuer));
                }
                throw new RestClientException("Untrusted issuer extracted from JWT: " + userInfoURL.getHost());
            }
        } catch (MalformedURLException e) {
            throw new RestClientException(String.format("Invalid userinfo endpoint URL: '%s'", userInfoUri.get()), e);
        }

        String uri = userInfoUri.get() + JwtUtils.USERINFO_ENDPOINT;

        return client.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(UserInfo.class);
    }

    public WebClient getClient() {
        return client;
    }

    public void setClient(WebClient client) {
        this.client = client;
    }
}
