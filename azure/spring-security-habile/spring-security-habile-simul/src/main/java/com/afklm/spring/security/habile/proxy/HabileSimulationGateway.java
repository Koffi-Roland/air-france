package com.afklm.spring.security.habile.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.afklm.spring.security.habile.proxy.controller.FourOFourController.BACKEND_FALLBACK_URI;
import static com.afklm.spring.security.habile.proxy.controller.FourOFourController.FRONTEND_FALLBACK_URI;

/**
 * Habile simulation gateway
 * 
 * @author m405991
 * @author m408461
 */
@Configuration
public class HabileSimulationGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabileSimulationGateway.class);

    /**
     * Fallback path when 404 on backend
     */
    private static final String BACKEND_FALLBACK_PATH = "404Backend";
    /**
     * Fallback path when 404 on frontend
     */
    private static final String FRONTEND_FALLBACK_PATH = "404Frontend";

    /**
     * Angular URL if needed
     */
    @Value("${habile.proxy.angular.url:http://localhost:4200}")
    private String angularUrl;

    /**
     * Backend URL if needed
     */
    @Value("${habile.proxy.backend.url:http://localhost:8080}")
    private String backendUrl;

    /**
     * List of endpoints that must be routed to the backend.
     * You can use wildcard characters but they must be explicit (ie. /api/**)
     */
    @Value("#{'${habile.proxy.backend.endpoints:}'.split(',')}")
    private List<String> backendEndpoints;

    /**
     * Custom Route locator</br>
     * If backend endpoints are provided, then create a route to the backend application and then route all the others the
     * angular application.</br>
     * If no backend endpoint is provided, then route everything to the backend application.</br>
     * Note that /mock/** calls are never routed to any other application
     * 
     * @param builder
     * @param habileGatewayFilter
     * @return RouteLocator
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, HabileGatewayFilter habileGatewayFilter) {
        backendEndpoints = backendEndpoints.stream()
                .filter(StringUtils::hasText)
                .map(String::trim)
                .collect(Collectors.toList());

        String defaultRoute = (backendEndpoints.isEmpty()) ? backendUrl : angularUrl;
        Builder routeBuilder = builder.routes();

        backendEndpoints.forEach(path -> {
                LOGGER.info("Routing '{}' to '{}'", path, backendUrl);
                routeBuilder
                        .route(r -> r.path("/"+ BACKEND_FALLBACK_PATH).uri("forward://"+ BACKEND_FALLBACK_URI))
                        .route(r -> r.path(path)
                        .filters(f -> f
                                .circuitBreaker(cbc -> cbc.setFallbackUri("forward:/"+ BACKEND_FALLBACK_PATH))
                                .filter(habileGatewayFilter)
                        )
                        .uri(backendUrl));
            });
        return defaultRoute(routeBuilder, habileGatewayFilter, defaultRoute).build();
    }

    private Builder defaultRoute(Builder routeBuilder, GatewayFilter habileGatewayFilter, String endpoint) {
        LOGGER.info("Default routing to '{}'", endpoint);
        String fallbackPath = backendEndpoints.isEmpty() ? BACKEND_FALLBACK_PATH : FRONTEND_FALLBACK_PATH;
        String fallbackUri = backendEndpoints.isEmpty() ? BACKEND_FALLBACK_URI : FRONTEND_FALLBACK_URI;
        return routeBuilder
                .route(r -> r.path("/"+fallbackPath).uri("forward://"+fallbackUri))
                .route(r -> r.path("/mock/**")
                .negate()
                .filters(f -> f.circuitBreaker(cbc -> cbc.setFallbackUri("forward:/"+fallbackPath)).filter(habileGatewayFilter))
                .uri(endpoint));
    }
}
