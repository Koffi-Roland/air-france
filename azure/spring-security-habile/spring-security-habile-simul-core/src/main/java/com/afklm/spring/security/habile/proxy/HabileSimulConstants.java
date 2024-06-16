package com.afklm.spring.security.habile.proxy;

import java.util.Arrays;
import java.util.List;

/**
 * Constants class for Habile simul
 * 
 * @author TECC
 *
 */
public final class HabileSimulConstants {
    /**
     * Habile session cookie
     */
    public static final String HABILE_SESSION_COOKIE = "MOCKSMSESSION";

    /**
     * Ping token cookie
     */
    public static final String PING_JWT_COOKIE = "MOCKTOKEN";

    /**
     * Habile logged cookie
     */
    public static final String HABILE_LOGGED_COOKIE = "MOCKSMLOGGED";

    /**
     *  default JWT scopes
     */
    protected static final List<String> PING_JWT_SCOPES = Arrays.asList("openid", "address", "email", "phone", "profile");

    private HabileSimulConstants() {
        throw new IllegalStateException("Utility class");
    }
}
