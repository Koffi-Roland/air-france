package com.afklm.spring.security.habile.authentication;

import static com.afklm.spring.security.habile.GlobalConfiguration.ANONYMOUS;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.GlobalConfiguration.NO_CREDENTIAL;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;

import com.afklm.spring.security.habile.GenericHeaders;

import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

/**
 * The class handles the extraction of the authentication data
 * provided by the Habile Proxy in any given request's header.
 * Once the data has been extracted, it can be processed by the {@link HabileAuthenticationManager}.
 *
 * @author TECC-SE Frameworks
 */
public class HabileAuthenticationConverter implements ServerAuthenticationConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(HabileAuthenticationConverter.class);

    /**
     * Converts the incoming request's header into an Authentication object containing the username and the token.
     * This detects the following request mode : normal mode, anonymous mode and websocket mode.
     *
     * @param exchange containing the inbound request
     * @return A {@link PreAuthenticatedAuthenticationToken} object containing the following value as principal :
     * <dl>
     * <dt>normal mode</dt>
     * <dd>the user id if it was provided or and empty string</dd>
     *
     * <dt>anonymous mode</dt>
     * <dd>{@link GenericHeaders#SM_USER_ANONYMOUS}</dd>
     *
     * <dt>websocket</dt>
     * <dd>the user id (the user should already be authenticated to allow web sockets</dd>
     * </dl>
     */
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        LOGGER.info("Starting Habile headers conversion into Authentication data");

        final ServerHttpRequest request = exchange.getRequest();
        final Optional<String> pingAccessUserOpt = retrievePingAccessUser(request);
        final Optional<String> pingAccessTokenOpt = retrievePingAccessToken(request);

        final Mono<Authentication> authenticationMono;
        if (isAnonymousUser(pingAccessUserOpt, pingAccessTokenOpt)) {
            authenticationMono = Mono.just(new PreAuthenticatedAuthenticationToken(ANONYMOUS, ANONYMOUS));
        } else if (isWebSocketUser(request, pingAccessTokenOpt)) {
            authenticationMono = ReactiveSecurityContextHolder
                    .getContext()
                    .map(SecurityContext::getAuthentication)
                    .switchIfEmpty(Mono.just(new PreAuthenticatedAuthenticationToken(EMPTY_STRING, NO_CREDENTIAL)));
        } else {
            final Mono<String> pingAccessUserMono = Mono.justOrEmpty(pingAccessUserOpt);
            final Mono<String> pingAccessTokenMono = Mono.justOrEmpty(pingAccessTokenOpt);

            authenticationMono = pingAccessUserMono.zipWith(pingAccessTokenMono)
                    .filter(authData -> authData.getT2() != null)
                    .switchIfEmpty(Mono.error(() -> new PreAuthenticatedCredentialsNotFoundException("Missing PingAccess token")))
                    .map(authData -> new PreAuthenticatedAuthenticationToken(authData.getT1(), authData.getT2()));
        }

        return authenticationMono.doOnNext(HabileAuthenticationConverter::logPreAuthenticationData);
    }

    private static boolean isAnonymousUser(final Optional<String> pingAccessUserOpt, final Optional<String> pingAccessTokenOpt) {
        return isEmptyString(pingAccessUserOpt) && isEmptyString(pingAccessTokenOpt);
    }

    private static boolean isWebSocketUser(@NotNull ServerHttpRequest request, final Optional<String> pingAccessTokenOpt) {
        return retrievePingAccessToken(request).isEmpty() && // Access token not defined at all (i.e. is null)
                areStringEquals(retrieveHeader(request, GenericHeaders.WEBSOCKET), GenericHeaders.WEBSOCKET_UPGRADE) &&
                HttpMethod.GET.equals(request.getMethod());
    }

    private static Optional<String> retrievePingAccessUser(@NotNull ServerHttpRequest request) {
        return retrieveHeader(request, GenericHeaders.X_FORWARDED_USER);
    }

    private static Optional<String> retrievePingAccessToken(@NotNull ServerHttpRequest request) {
        return retrieveHeader(request, GenericHeaders.X_ACCESS_TOKEN);
    }

    private static Optional<String> retrieveHeader(@NotNull ServerHttpRequest request, @NotNull String headerName) {
        return Optional.of(request)
                .map(ServerHttpRequest::getHeaders)
                .map(headers -> headers.getFirst(headerName));
    }

    private static boolean isEmptyString(@NotNull final Optional<String> stringOpt) {
        return areStringEquals(stringOpt, EMPTY_STRING);
    }

    private static boolean areStringEquals(@NotNull final Optional<String> stringOpt, @NotNull final String compared) {
        return stringOpt.filter(compared::equals).isPresent();
    }

    private static void logPreAuthenticationData(Authentication authentication) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Username: '{}' | credentials: '{}'", authentication.getName(), authentication.getCredentials());
        }
    }
}
