package com.afklm.spring.security.habile;

/**
 * Constants class for HTTP headers
 *
 * @author TECC
 */
public final class GenericHeaders {
    /**
     * Header user SEC_MOBILE
     */
    public static final String SECGW_USER = "secgw_user";
    /**
     * Header SEC_MOBILE
     */
    public static final String SECGW_TS = "secgw_ts";
    /**
     * Header SEC_MOBILE
     */
    public static final String SECGW_CHECK = "secgw_check";

    /**
     * Ping id token header ( this is an id token !!!!! )
     */
    public static final String X_ACCESS_TOKEN = "x-access-token";

    /**
     * Ping logout url header
     */
    public static final String X_LOGOUT_URL = "x-logout-url";

    /**
     * Ping forwarded user header
     */
    public static final String X_FORWARDED_USER = "x-forwarded-user";
    /**
     * Ping display name header
     */
    public static final String X_DISPLAY_NAME = "x-display-name";
    /**
     * Ping auth level header
     */
    public static final String X_FORWARDED_AUTHLEVEL = "x-forwarded-authlevel";
    /**
     * SiteMinder user
     */
    public static final String SM_USER = "SM_USER";
    /**
     * Habile user ?
     */
    public static final String AF_USER = "AF_USER";
    /**
     * SiteMinder user DN
     */
    public static final String SM_USERDN = "SM_USERDN";
    /**
     * SiteMinder user anonymous
     */
    public static final String SM_USER_ANONYMOUS = "Anonymous";

    /**
     * SiteMinder logout header
     */
    public static final String SM_LOGOUT = "SM_LOGOUT";

    /**
     * SiteMinder SM_SESSION cookie
     */
    public static final String SM_SESSION = "SMSESSION";

    /**
     * SiteMinder SM_AUTHTYPE header
     */
    public static final String SM_AUTHTYPE = "SM_AUTHTYPE";

    /**
     * SiteMinder anonymous authtype
     */
    public static final String SM_AUTHTYPE_ANONYMOUS = "Anonymous";

    /**
     * WebSocket sec_webSocket_key header
     */
    public static final String SEC_WEBSOCKET_KEY = "sec-websocket-key";

    /**
     * Connection upgrade value in websocket header
     */
    public static final String WEBSOCKET_UPGRADE = "upgrade";

    /**
     * WebSocket header value
     */
    public static final String WEBSOCKET = "WebSocket";

    private GenericHeaders() {
        throw new IllegalStateException("Utility class");
    }
}
