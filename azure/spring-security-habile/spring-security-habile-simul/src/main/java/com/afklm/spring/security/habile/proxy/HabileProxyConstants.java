package com.afklm.spring.security.habile.proxy;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import jakarta.xml.bind.DatatypeConverter;

import java.security.Key;

/**
 * Constants for HabileProxy
 * 
 * @author m405991
 * @author m408461
 *
 */
public final class HabileProxyConstants {
    /** Value for SM_AUTHTYPE header */
    public static final String SM_AUTHTYPE_VALUE = "mock";

    /** Value for Habile logout header */
    public static final String HABILE_LOGOUT_VALUE = "/mock/logout";
    /** Redirect value once logout is done */
    public static final String LOGOUT_DONE = "/mock/logoutDone";
    /** default auth level to mimic ping */
    public static final String X_FORWARDED_AUTHLEVEL_VALUE = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
    /** mock user */
    public static final String X_DISPLAY_NAME_VALUE = "Mocked User";

    /** logout message */
    public static final String LOGOUT_BODY = "You are now logged out from simulation";

    private HabileProxyConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String API_KEY_SECRET = "SS4H-PROXY-EXTENDED-SIZE-SECRET-KEY-IS-EVEN-TOO-SHORT-TO-GET-AT-LEAST-256-BITS";
    public static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(API_KEY_SECRET);

    public static final String JWT_ID = "ss4h-proxy";
    public static final SignatureAlgorithm JWT_ALGORITHM = SignatureAlgorithm.HS256;

    private static Key signingKey = null;

    public static Key getSigningKey() {
        if (signingKey==null) {
            signingKey = new SecretKeySpec(API_KEY_SECRET_BYTES, JWT_ALGORITHM.getJcaName());
        }
        return signingKey;
    }
}
