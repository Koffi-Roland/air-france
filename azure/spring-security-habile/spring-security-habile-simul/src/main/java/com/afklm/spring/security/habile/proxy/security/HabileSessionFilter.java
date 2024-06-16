package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Management of Habile session in simulation
 * 
 * @author m405991
 *
 */
public class HabileSessionFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileSessionFilter.class);

    private HabileSessionManager habileSessionManager;

    private ServerAuthenticationEntryPoint habileBasicAuthenticationEntryPoint;

    private List<PathPattern> pathPatterns;

    /**
     * Constructor
     * 
     * @param habileSessionManager HabileSessionManager
     */
    public HabileSessionFilter(HabileSessionManager habileSessionManager, List<String> publicPatterns, String port) {
        super();
        this.habileSessionManager = habileSessionManager;
        this.pathPatterns = new ArrayList<>();
        publicPatterns.stream()
            .filter(StringUtils::hasText)
            .map(String::trim)
            .map(new PathPatternParser()::parse)
            .forEach(pathPatterns::add);
        habileBasicAuthenticationEntryPoint = new AFKLFormAuthenticationEntryPoint(habileSessionManager, port);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        LOGGER.debug("Checking for LOGOUT HabileSession");
        // If nothing is present for Habile then we start BasicAuthentication
        if (needAuthentication(exchange.getRequest())) {
            LOGGER.debug("Detected logout and thus requiring new autentication");
            exchange.getSession().subscribe(WebSession::invalidate);
            return habileBasicAuthenticationEntryPoint.commence(exchange, null);
        } else {
            return chain.filter(exchange);
        }
    }

    private boolean needAuthentication(ServerHttpRequest serverHttpRequest) {
        HabileState habileState = habileSessionManager.getHabileState(serverHttpRequest);
        boolean result = false;
        String header = serverHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // If no cookie is found then either it is first login or session expired
        // If logout cookie is found and credentials are provided then ask once again
        // for credentials to allow swap to another user
        PathContainer path = serverHttpRequest.getPath().pathWithinApplication();
        if (!pathPatterns.stream().anyMatch(p -> p.matches(path))
                &&
                (HabileState.EXPIRED.equals(habileState)
                        || (HabileState.LOGOUT.equals(habileState) && (header != null)))) {
            result = true;
        }
        return result;
    }
}
