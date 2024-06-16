package com.afklm.spring.security.habile.authentication;

import static com.afklm.spring.security.habile.GlobalConfiguration.NO_CREDENTIAL;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.roles.RolesProvider;
import com.afklm.spring.security.habile.user.CompleteHabileUserDetails;
import com.afklm.spring.security.habile.user.HabileUserDetailsService;

import reactor.core.publisher.Mono;

/**
 * This class handles the authentication of a Habile user in the Spring Security Reactive request flow.
 * It assumes that the provided username has not been tampered with since it's provided by the Habile proxy.
 *
 * @author TECC-SE Frameworks
 */
@Service
public class HabileAuthenticationManager implements ReactiveAuthenticationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HabileAuthenticationManager.class);
    private HabileUserDetailsService habileUserDetailsService;
    private RolesProvider rolesProvider;

    @Autowired
    public HabileAuthenticationManager(HabileUserDetailsService habileUserDetailsService, RolesProvider rolesProvider) {
        this.habileUserDetailsService = habileUserDetailsService;
        this.rolesProvider = rolesProvider;
    }

    /**
     * Assumes the users is already authenticated against Habile and provides the full permissions the user has
     * for the application using the SS4H Reactive filter.
     *
     * @param providedAuthentication {@link PreAuthenticatedAuthenticationToken} containing the username and
     *                               the session token as strings
     * @return a Mono of {@link Authentication} containing a {@link CompleteHabileUserDetails}
     * as principal and effective authorities the user had. Returns an error signal if the user couldn't be authenticated.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication providedAuthentication) {

        final String username = String.class.cast(providedAuthentication.getPrincipal());
        final String token = String.class.cast(providedAuthentication.getCredentials());

        return Mono.just(providedAuthentication)
                .doOnNext(x -> LOGGER.debug("Starting authentication of user {}", x))
                .flatMap(AuthenticationValidator::validateProvided)
                .then(ReactiveSecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> AuthenticationValidator.validateCoherence(auth, providedAuthentication))
                .doOnNext(s -> LOGGER.debug("Using data from security context"))
                .switchIfEmpty(Mono.defer(() -> retrieveUser(username, token)))
                .doOnNext(logAccess);
    }

    private Mono<PreAuthenticatedAuthenticationToken> retrieveUser(String username, String token) {
        return habileUserDetailsService.findByUsername(username, token)
                .cast(HabileUserDetails.class)
                .flatMap(rolesProvider::provideFor)
                .map(user -> new PreAuthenticatedAuthenticationToken(user, NO_CREDENTIAL, user.getAuthorities()))
                .doOnNext(s -> LOGGER.debug("User data retrieved from UA2"));
    }

    private Consumer<Authentication> logAccess = auth ->
            LOGGER.debug("End of authentication management for user {} with authorities {}",
                    UserDetails.class.cast(auth.getPrincipal()).getUsername(),
                    auth.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(" - ")));
}
