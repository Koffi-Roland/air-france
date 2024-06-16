package com.afklm.spring.security.habile.oidc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

import java.util.*;

/**
 * Class that centralized utility method around JWT.
 * 
 * @author m408461
 *
 */
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    public static final String JWT_PREFIX = "jwt:";
    public static final String USERINFO_ENDPOINT = "/idp/userinfo.openid";
    public static final String ISSUER_KEY = "iss";
    public static final String SCOPE_KEY = "scope";

    /**
     * List of hosts whose JWT are trusted.
     */
    public static final List<String> TRUSTED_ISSUERS = Arrays.asList(
            "fedhub-ee.airfranceklm.com",
            "fedhub-cae.airfranceklm.com",
            "fedhub-acp.airfranceklm.com",
            "fedhub.airfranceklm.com",
            "localhost",
            "host.docker.internal");

    /**
     * Extracts the element as an object contained in the body of a token
     * 
     * @param token
     * @param key - the element to look for
     * @return an Optional of the element
     */
    public static Optional<Object> getElementFromBody(String token, String key) {
        int firstDotIndex = token.indexOf('.') + 1;
        if (firstDotIndex == 0) {
            return Optional.empty();
        }
        int secondDotIndex = token.indexOf('.', firstDotIndex + 1);
        if (secondDotIndex == -1) {
            return Optional.empty();
        }
        String body = token.substring(firstDotIndex, secondDotIndex);
        try {
            String decodedBody = new String(Base64.getDecoder().decode(body));
            JsonParser parser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = parser.parseMap(decodedBody);
            return Optional.ofNullable(map.get(key));
        } catch (JsonParseException e) {
            LOGGER.error("Paring error with '{}'", token, e);
            return Optional.empty();
        }
    }

    /**
     * Extracts the element contained in the body of a token if it exists
     * and is a String
     *
     * @param token
     * @param key - the element to look for
     * @return tan Optional of the element
     */
    public static Optional<String> getStringElementFromBody(String token, String key) {
        return getElementFromBody(token, key)
                .filter(String.class::isInstance)
                .map(String.class::cast);
    }

    /**
     * Checks if a String is formatted like a JWT.<br/>
     * We only check that the token contains 3 parts (header, body and signature) and
     * that the header and body parts are Base64 encoded.<br/>
     * We can't use any library because they usually check signatures and so far those are
     * not available. Moreover we also address this token to the Ping userinfo endpoint
     * so it is automatically validated.
     * 
     * @param token
     * @return
     */
    public static boolean isToken(String token) {
        // we can't use a library because they usually check the signature and we don't have the key
        // see https://auth0.com/docs/tokens/json-web-tokens/validate-json-web-tokens
        // section manually implement checks (but the signature)
        String[] sections = token.split("\\.");

        if (sections.length != 3)
            return false;

        try {
            /* String header = */ new String(Base64.getDecoder().decode(sections[0]));
            /* String body = */ new String(Base64.getDecoder().decode(sections[1]));
            // we could also check the content of body and header
        } catch (IllegalArgumentException e) {
            LOGGER.debug("'{}' is not a token", token);
            return false;
        }
        return true;
    }
}
