package com.afklm.spring.security.habile.proxy.controller;

import io.jsonwebtoken.Jwts;

import com.afklm.spring.security.habile.oidc.UserInfo;
import com.afklm.spring.security.habile.proxy.HabileProxyConstants;
import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

/**
 * OidcController
 * 
 * The purpose of this class is to expose OIDC endpoints used in our
 * PreAuthenticated security flow with Ping
 * 
 * @author m408461
 *
 */
@RestController
public class OidcController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OidcController.class);

    @Autowired
    private ConfigurationService authoritiesConfigurationService;

    // @Autowired
    // private JWKSet jwkSet;
    //
    // @GetMapping("/.well-known/jwks.json")
    // public Map<String, Object> keys() {
    // return this.jwkSet.toJSONObject();
    // }

    // @GetMapping(path="/.well-known/openid-configuration")
    // public String openIdConfiguration() {
    // return "{ \"issuer\": \"http://localhost:8001/\","
    // + "\"jwks_uri\":\"http://localhost:8001/.well-known/jwks.json\"}";
    // }

    /**
     * userinfo.openid endpoint
     * 
     * @param auth the token to return user data
     * @return the UserInfo in json format
     */
    @GetMapping(path = "/idp/userinfo.openid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfo> userinfo(@RequestHeader("Authorization") String auth) {
        LOGGER.debug("/idp/userinfo.openid invoked");

        String token = auth.substring("Bearer ".length());
        LOGGER.debug("Token is: {}", token);
        // https://stormpath.com/blog/jjwt-how-it-works-why
        // The JWT signature algorithm we will be using to sign the token

        String subject = Jwts.parser().setSigningKey(HabileProxyConstants.getSigningKey()).parseClaimsJws(token).getBody().getSubject();
        LOGGER.debug("/idp/userinfo.openid  invoked for user '{}'", subject);
        UserInformation userInfo = authoritiesConfigurationService.getUserInformation(subject);
        if (userInfo == null) {
            LOGGER.warn("user not found: '{}'", subject);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(constructUserInfoResponse(userInfo), OK);
    }

    private UserInfo constructUserInfoResponse(UserInformation userInfo) {
        return new UserInfo(userInfo.getUserId(),
            "TODO",
            userInfo.getProfiles(),
            userInfo.getLastName(),
            userInfo.getFirstName(),
            userInfo.getEmail());
    }
}
