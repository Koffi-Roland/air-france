package com.afklm.spring.security.habile;

import java.util.Collections;
import java.util.List;

/**
 * Constant class holding semantic configuration of Habile
 *
 * @author TECC
 */
public final class GlobalConfiguration {
    /**
     * Role prefix to use roles as authorities
     */
    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * Value used to indicate that nothing was provided for {@link GenericHeaders#SM_SESSION}
     */
    public static final String NO_CREDENTIAL = "N/A";

    /**
     * Anonymous user default value
     */
    public static final String ANONYMOUS = "anonymous";

    /**
     * The empty String ("").
     */
    public static final String EMPTY_STRING = "";

    /**
     * Anonymous roles list
     */
    public static List<String> getAnonymousRoles() {
        return Collections.singletonList(ANONYMOUS + "_ROLE");
    }

    /**
     * Websocket user
     */
    public static final String WEBSOCKET_USER = "GID-WEBSOCKET";

    private GlobalConfiguration() {
        throw new IllegalStateException("Utility class");
    }
}
