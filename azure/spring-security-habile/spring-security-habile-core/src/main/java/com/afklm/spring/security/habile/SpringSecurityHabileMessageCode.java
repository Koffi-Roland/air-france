package com.afklm.spring.security.habile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.IllegalFormatException;

/**
 * Messages for important logs dumped by SS4H
 * 
 * @author TECC
 *
 */
public enum SpringSecurityHabileMessageCode {
    /**
     * Auto configuration inconsistency detected.
     * One field is missing configuring one class.
     */
    SS4H_MSG_VERIFICATION("Verification failed for field '%s' of class '%s': '%s'"),
    /**
     * List of URL patterns that are not protected by SS4H filter
     */
    SS4H_MSG_PUBLIC_URLS("Configuring public URL patterns: %s"),
    /**
     * SecMobile filter added to the application
     */
    SS4H_MSG_SECMOBILE("Adding SecMobile security filter"),
    /**
     * A runtime exception has been raised during process. A UUID is logged with root exception
     * and the UUID is sent back to the client.
     */
    SS4H_MSG_RT_ERROR("Processing exception occured with uuid '%s'"),
    /**
     * Mismatch in Principal retrieved from session when processing the WebSocket upgrade.
     */
    SS4H_MSG_PRINCIPAL_MISMATCH("Expecting Principal of type '%s' but got '%s' instead. So returning '%s'."),
    /**
     * No authentication header found in the request neither SM_USER (in case of SiteMinder) nor
     * x-access-token (in case of Ping).
     */
    SS4H_MSG_MISSING_AUTH_HEADER("Missing AF/KLM authentication header for request '%s'"),
    /**
     * Too many authentication header found in the request.
     */
    SS4H_MSG_INCONSISTANCY_AUTH_HEADER("Too many AF/KLM authentication header for request '%s'"),
    /**
     * Runtime inconsistency detected: SM_USER present but no w000479 bean.
     */
    SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479("Configuration inconsistency detected at runtime. SM_USER header present but no w000479 bean in scope."),
    /**
     * Runtime inconsistency detected: SM_USER present but no w000479 bean.
     */
    SS4H_MSG_RT_INCONSISTANCY_AUTH_USER_SEC_USER("Inconsistency detected between authenticated user and security header: '%s' / '%s'"),
    /**
     * Runtime inconsistency detected: previous Credentials is null
     */
    SS4H_MSG_RT_INCONSISTANCY_NULL_CREDENTIALS_ON_CHANGE("Detecting change in principal but previous credentials are null!!!"),
    /**
     * Runtime inconsistency detected: wrong Credentials type
     */
    SS4H_MSG_RT_INCONSISTANCY_WRONG_CREDENTIALS_ON_CHANGE("Detecting change in principal but expecting previous credentials to be a String instead of {}!!!"),
    /**
     * Runtime inconsistency detected: wrong Principal type
     */
    SS4H_MSG_RT_INCONSISTANCY_WRONG_PRINCIPAL_ON_CHANGE("Detecting change in principal but expecting Principal to be a HabileUserDetails instead of {}!!!");

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSecurityHabileMessageCode.class);
    /** Error mnemonic */
    private String mnemonic;
    /** Error label */
    private String label;

    /**
     * Constructor
     *
     * @param label label
     */
    private SpringSecurityHabileMessageCode(String label) {
        DecimalFormat decFormat = new DecimalFormat("000");
        this.mnemonic = "SS4H-MSG-" + decFormat.format(this.ordinal() + 1L);
        this.label = label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return mnemonic;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Format error message
     * 
     * @param args variable list of arguments
     * @return formatted message
     */

    public String format(Object... args) {
        StringBuilder sb = new StringBuilder(80);
        sb.append(mnemonic).append(": ");
        try (Formatter formatter = new Formatter(sb);) {
            formatter.format(label, args);
        } catch (IllegalFormatException ife) {
            LOGGER.error("Unexpected error while trying to format " + mnemonic + ":" + label, ife);
            sb.append(label);
        }
        return sb.toString();
    }
}
