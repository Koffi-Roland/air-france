package com.afklm.spring.security.habile.proxy;

import static com.afklm.spring.security.habile.GenericHeaders.AF_USER;
import static com.afklm.spring.security.habile.GenericHeaders.SM_AUTHTYPE;
import static com.afklm.spring.security.habile.GenericHeaders.SM_LOGOUT;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USER;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USERDN;
import static com.afklm.spring.security.habile.GenericHeaders.SM_USER_ANONYMOUS;
import static com.afklm.spring.security.habile.GenericHeaders.X_ACCESS_TOKEN;
import static com.afklm.spring.security.habile.GenericHeaders.X_DISPLAY_NAME;
import static com.afklm.spring.security.habile.GenericHeaders.X_FORWARDED_AUTHLEVEL;
import static com.afklm.spring.security.habile.GenericHeaders.X_FORWARDED_USER;
import static com.afklm.spring.security.habile.GenericHeaders.X_LOGOUT_URL;
import static com.afklm.spring.security.habile.GlobalConfiguration.EMPTY_STRING;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.HABILE_LOGOUT_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.SM_AUTHTYPE_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.X_DISPLAY_NAME_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.X_FORWARDED_AUTHLEVEL_VALUE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.PING_JWT_COOKIE;
import static com.afklm.spring.security.habile.proxy.HabileSimulConstants.PING_JWT_SCOPES;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest.Builder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.afklm.spring.security.habile.proxy.security.PublicAuthenticationToken;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

/**
 * Habile Gateway Filter
 * 
 * This filter is responsible for adding HTTP headers like Habile proxy would do
 * 
 * @author m405991
 *
 */
@Component
public class HabileGatewayFilter implements GatewayFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileGatewayFilter.class);

    private static final String PRINCIPAL_USER = "principal";

    /**
     * Defines if we are in JWT Ping mode or HTTP header SiteMinder
     */

    @Value("${server.port:8001}")
    private String port;

    /**
     * Hostname to be used for the JWT issuer
     */
    @Value("${habile.proxy.hostname:http://localhost}")
    private String host;

    /**
     * The TTL of the token is in seconds !!!
     */
    @Value("${afklm.security.jwt.ttl:3600}")
    private int jwtTtl;

    @Value("${afklm.security.sm:false}")
    private boolean smCompatibilityEnabled;

    @Autowired
    private ConfigurationService configurationService;

    private ServerWebExchangeMatcher publicPathMatcher = null;

    /**
     * PostContruct method that creates the publicPathMatcher according to the configuration
     */
    @PostConstruct
    public void postConstruct() {
        if (this.configurationService.getPublicPaths().length > 0) {
            publicPathMatcher = ServerWebExchangeMatchers.pathMatchers(null, this.configurationService.getPublicPaths());
        } else {
            publicPathMatcher = exchange -> ServerWebExchangeMatcher.MatchResult.notMatch();
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.debug("Adding extra headers to simulate habile");
        if (isUpgradeConnectionPresent(exchange)) {
            return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .doOnNext(principal -> injectHeadersForWebSocketUpgrade(principal, exchange))
                .then(chain.filter(exchange));
        }
        return this.publicPathMatcher.matches(exchange)
            .filter(matchResult -> !matchResult.isMatch())
            .flatMap(matchResult -> ReactiveSecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .switchIfEmpty(Mono.just(new PublicAuthenticationToken()))
            .doOnNext(principal -> injectHabileHeaders(principal, exchange))
            .then(chain.filter(exchange));
    }

    private boolean isUpgradeConnectionPresent(ServerWebExchange exchange) {
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
        List<String> connectionHeaders = httpHeaders.get(HttpHeaders.CONNECTION);
        return connectionHeaders != null && connectionHeaders
            .stream()
            .anyMatch(c -> c.equalsIgnoreCase(HttpHeaders.UPGRADE));
    }

    private void injectHeadersForWebSocketUpgrade(Authentication principal, ServerWebExchange exchange) {
        // https://confluence.devnet.klm.com/x/XjxdEg
        LOGGER.info("Injecting empty HABILE USER");
        String habileUser = EMPTY_STRING;

        Builder builder = exchange.getRequest().mutate();
        ServerHttpRequest request = exchange.getRequest();
        String port = request.getURI().getPort() == -1 ? "80" : String.valueOf(request.getURI().getPort());
        String logoutUrl = request.getURI().getScheme() + "://" + request.getURI().getHost() + ":" + port + HABILE_LOGOUT_VALUE;

        builder.header(X_FORWARDED_USER, habileUser);
        builder.header(X_LOGOUT_URL, logoutUrl);

        // on a Websocket upgrade our F5 element will so far inject an empty SM_USER
        builder.header(SM_LOGOUT, logoutUrl);
        builder.header(SM_USER, habileUser);
    }

    private void injectHabileHeaders(Authentication principal, ServerWebExchange exchange) {
        // https://confluence.devnet.klm.com/x/XjxdEg
        Builder builder = exchange.getRequest().mutate();
        ServerHttpRequest request = exchange.getRequest();
        LOGGER.debug("injecting authentication headers for request {}", exchange.getRequest().getPath());
        String logoutUrl =  host + ":" + request.getURI().getPort() + HABILE_LOGOUT_VALUE;
        String habileUser = principal.getPrincipal().toString();

        if (principal instanceof PublicAuthenticationToken) {
            LOGGER.debug("public path detected - skipping header injection");
            return;
        } else if (principal instanceof AnonymousAuthenticationToken) {
            if (this.smCompatibilityEnabled) {
                if (PRINCIPAL_USER.equals(habileUser)) {
                    builder.header(X_FORWARDED_USER, SM_USER_ANONYMOUS);
                    builder.header(X_LOGOUT_URL, EMPTY_STRING);

                    builder.header(SM_USER, SM_USER_ANONYMOUS);
                    builder.header(SM_USERDN, SM_USER_ANONYMOUS);
                    builder.header(SM_AUTHTYPE, SM_USER_ANONYMOUS);
                } else {
                    builder.header(X_FORWARDED_USER, habileUser);
                    builder.header(X_LOGOUT_URL, logoutUrl);

                    builder.header(SM_LOGOUT, logoutUrl);
                    builder.header(SM_USER, habileUser);
                    builder.header(AF_USER, habileUser);
                }
            } else {
                if (PRINCIPAL_USER.equals(habileUser)) {
                    builder.header(X_FORWARDED_USER, EMPTY_STRING);
                    builder.header(X_DISPLAY_NAME, EMPTY_STRING);
                    builder.header(X_ACCESS_TOKEN, EMPTY_STRING);
                    builder.header(X_LOGOUT_URL, EMPTY_STRING);
                    builder.header(X_FORWARDED_AUTHLEVEL, EMPTY_STRING);
                } else {
                    builder.header(X_FORWARDED_USER, habileUser);
                    builder.header(X_DISPLAY_NAME, X_DISPLAY_NAME_VALUE);
                    builder.header(X_LOGOUT_URL, logoutUrl);
                    builder.header(X_FORWARDED_AUTHLEVEL, X_FORWARDED_AUTHLEVEL_VALUE);

                    processToken(exchange, builder, habileUser);
                }
            }
        } else {
            builder.header(X_FORWARDED_USER, habileUser);
            builder.header(X_LOGOUT_URL, logoutUrl);
            builder.header(X_FORWARDED_AUTHLEVEL, X_FORWARDED_AUTHLEVEL_VALUE);

            if (this.smCompatibilityEnabled) {
                builder.header(SM_LOGOUT, logoutUrl);
                builder.header(SM_USER, habileUser);
                builder.header(AF_USER, habileUser);
                builder.header(SM_AUTHTYPE, SM_AUTHTYPE_VALUE);
            } else {
                builder.header(X_DISPLAY_NAME, X_DISPLAY_NAME_VALUE);
                processToken(exchange, builder, habileUser);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            builder.headers(headers -> headers.keySet().forEach(key -> Objects.requireNonNull(headers.get(key)).forEach(value -> LOGGER.debug(" > Header '{}' with value '{}'", key, value))));
        }
    }

    private void processToken(ServerWebExchange exchange, Builder builder, String habileUser) {
        HttpCookie pingCookie = exchange.getRequest().getCookies().getFirst(PING_JWT_COOKIE);
        String token;
        if (pingCookie == null) {
            // we create the cookie
            token = createJWT(habileUser);
        } else {
            // we check the value
            token = getOrRefreshToken(pingCookie.getValue(), habileUser);
            // we remove the cookie
            exchange.getResponse().getCookies().remove(PING_JWT_COOKIE);
        }
        // we add the cookie
        ResponseCookie cookie = ResponseCookie.from(PING_JWT_COOKIE, token)
            .path("/")
            .build();
        exchange.getResponse().addCookie(cookie);
        builder.header(X_ACCESS_TOKEN, token);
    }

    public String getOrRefreshToken(String currentJwt, String subject) {
        if (currentJwt == null) {
            return createJWT(subject);
        }
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(HabileProxyConstants.getSigningKey()).parseClaimsJws(currentJwt);
            Claims claimBody = jwt.getBody();
            String jwtSubject = claimBody.getSubject();
            // if the subject of JWT is not the same as the Principal one, we might have an old JWT -> regenerate it
            if (!jwtSubject.equals(subject)) {
                return createJWT(subject);
            }
        } catch (ExpiredJwtException e) {
            LOGGER.debug("token expired -> {}", currentJwt);
            return createJWT(subject);
        }
        return currentJwt;

    }

    public String createJWT(String subject) {
        LOGGER.debug("creating JWT");
        String issuer = host + ":" + port;
        LOGGER.trace("computed issuer: {}", issuer);
        String token =  JwtBuilder.createJWT(subject, issuer, jwtTtl);
        LOGGER.debug("new token is: {}", token);
        return token;
    }

    @PostConstruct
    public void displayMode() {
        if (this.smCompatibilityEnabled) {
            LOGGER.warn("\n!!!\n\nSiteMinder Compatibility Mode Enabled\n\n!!!");
        } else {
            if (!host.startsWith("http://") && !host.startsWith("https://")) {
                throw new RuntimeException("Proxy misconfigured, habile.proxy.hostname property must contain scheme");
            }
            LOGGER.info("Ping mode detected");
            LOGGER.info("JWT validity duration is {} seconds", jwtTtl);
            LOGGER.debug("JWT algorithm is {}", HabileProxyConstants.JWT_ALGORITHM);
            LOGGER.debug("JWT scopes are {}", PING_JWT_SCOPES);
            if (configurationService.hasNoUser()) {
                LOGGER.error("In ping mode you need to create a configuration file that describes "
                        + "at least one user and inject it into the program with argument " + ConfigurationService.CONFIG_SIMUL_OPTION);
                throw new RuntimeException("Proxy misconfigured, please check previous error message");
            }
        }
    }
}
