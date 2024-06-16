package com.afklm.spring.security.habile.authentication;

import com.afklm.spring.security.habile.GenericHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import reactor.core.publisher.Mono;

/**
 * This class provides ways to verify and validate the authentication data provided by the request
 *
 * @author TECC-SE
 */
public final class AuthenticationValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationValidator.class);

    private AuthenticationValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Function validating that the request provided {@link Authentication} has :
     * <ul>
     * <li>the {@link GenericHeaders#X_FORWARDED_USER} string acting as principal is not empty</li>
     * <li>the {@link GenericHeaders#X_ACCESS_TOKEN} string acting as credential is not empty</li>
     * </ul>
     * <p>
     * If all conditions are fulfilled, the {@link Authentication} is passed along the chain.
     * If one of these conditions isn't fulfilled, an error signal containing a {@link AuthenticationCredentialsNotFoundException} is thrown,
     * thus interrupting the flow.
     * <p>
     * Designed to be used in a Reactive call chain.
     *
     * @param provided authentication data provided by the request
     * @return a {@link Mono} containing validated authentication data
     */
    public static Mono<Authentication> validateProvided(Authentication provided) {
        return Mono.when(
                    validateValue(provided.getPrincipal(), GenericHeaders.X_FORWARDED_USER),
                    validateValue(provided.getCredentials(), GenericHeaders.X_ACCESS_TOKEN))
                .then(Mono.just(provided));
    }

    private static Mono<Void> validateValue(Object value, String type) {
        try {
            if (!StringUtils.hasLength((String) value)) {
                LOGGER.error("{} is missing", type);
                return Mono.error(new AuthenticationCredentialsNotFoundException("Authentication data provided"));
            }
        } catch (ClassCastException e) {
            LOGGER.error("{} is not a string : ", type, e);
            return Mono.error(new AuthenticationCredentialsNotFoundException("Incompatible authentication data provided"));
        }

        return Mono.empty();
    }

    /**
     * This function validates that the current request is about the user previously authenticated for this session.
     *
     * @param contextUser previously authenticated user, where the principal is a {@link UserDetails}
     * @param requestUser user this request is about, where the principal is the username as string
     * @return {@link Mono} of the current user if the request is coherent with the session, an empty {@link Mono} if
     * they are incoherent (the username are different)
     */
    public static Mono<Authentication> validateCoherence(Authentication contextUser, Authentication requestUser) {
        if (!requestUser.getPrincipal().equals(UserDetails.class.cast(contextUser.getPrincipal()).getUsername())) {
            LOGGER.warn("Provided authentication data is not coherent with current session, authentication will be reattempted ");
            return Mono.empty();
        }
        return Mono.just(contextUser);
    }
}
