package com.afklm.spring.security.habile.userdetails.dao;

import com.afklm.spring.security.habile.HabilePrincipal;
import com.afklm.spring.security.habile.oidc.JwtUtils;
import com.afklm.spring.security.habile.oidc.UserInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

/**
 * Implementation of the {@link UserRetrieverDao} based on the OIDC.<br/>
 * It requires:
 * <ul>
 * <li>the <b>iss</b> field to be set into the JWT</li>
 * <li>the issuer to be from a trusted host</li>
 * </ul>
 * 
 * @author TECC
 *
 */
@Component("habileUserRetrieverDaoImplPing")
public class UserRetrieverDaoImplPing implements UserRetrieverDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRetrieverDaoImplPing.class);

    private RestTemplate restTemplate;

    public UserRetrieverDaoImplPing() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public HabilePrincipal getUser(String id, String smSession) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.setBearerAuth(id);
        try {
            Optional<String> userInfoUri = JwtUtils.getStringElementFromBody(id, JwtUtils.ISSUER_KEY);
            if (!userInfoUri.isPresent()) {
                throw new RestClientException("No issuer field extracted from JWT");
            }
            URL userInfoURL = new URL(userInfoUri.get().toLowerCase());
            if (!JwtUtils.TRUSTED_ISSUERS.contains(userInfoURL.getHost())) {
                if(LOGGER.isDebugEnabled()) {
                    JwtUtils.TRUSTED_ISSUERS.forEach(trustedIssuer -> LOGGER.debug("Comparing '{}' and '{}' seems to have failed", userInfoURL.getHost(), trustedIssuer));
                }
                throw new RestClientException("Untrusted issuer extracted from JWT: '" + userInfoURL.getHost() + "'");
            }
            String uri = userInfoUri.get() + JwtUtils.USERINFO_ENDPOINT;
            LOGGER.trace("Computed userinfo endpoint: '{}'", uri);
            LOGGER.trace("Injecting bearer '{}'", id);
            ResponseEntity<UserInfo> response = restTemplate.exchange(new URI(uri),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserInfo.class);
            UserInfo userInfo = response.getBody();
            return new HabilePrincipal(userInfo.getSub(),
                userInfo.getGiven_name(),
                userInfo.getFamily_name(),
                userInfo.getEmail(),
                userInfo.getProfile());
        } catch (RestClientException | URISyntaxException | MalformedURLException e) {
            throw new UsernameNotFoundException(String.format(PING_ERROR_MESSAGE, id), e);
        }
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
