package com.afklm.spring.security.habile;

import static java.util.function.Predicate.not;
import static com.afklm.spring.security.habile.properties.HttpVerbsAuthorities.getRolesAndOrPermissionsListing;
import static com.afklm.spring.security.habile.properties.HttpVerbsAuthorities.mergeRolesAndPermissions;

import com.afklm.spring.security.habile.authentication.HabileAuthenticationSuccessHandler;
import com.afklm.spring.security.habile.authentication.HabileAuthenticationConverter;
import com.afklm.spring.security.habile.authentication.HabileAuthenticationFailureHandler;
import com.afklm.spring.security.habile.authentication.HabileAuthenticationManager;
import com.afklm.spring.security.habile.properties.Ss4hProperties;
import com.afklm.spring.security.habile.properties.UrlsHttpVerbsAuthorities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class holds the configuration needed by Spring Security to use our Habile adaptation
 *
 * @author TECC SE
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private HabileAuthenticationManager habileAuthManager;

    private Ss4hProperties afklSecurityProperties;

    public SecurityConfig(HabileAuthenticationManager habileAuthManager, Ss4hProperties afklSecurityProperties) {
        this.habileAuthManager = habileAuthManager;
        this.afklSecurityProperties = afklSecurityProperties;
    }

    /**
     * Provides a bean used to configure security for public resources.
     *
     * @param http the {@link ServerHttpSecurity} used as base for the configuration
     * @return a configured {@link SecurityWebFilterChain}
     */
    @Bean
    @Order(99) // Arbitrary value ensuring that this filter chain is evaluated before the overall one as it is done in non-reactive part
    public SecurityWebFilterChain publicWebFilterChain(ServerHttpSecurity http) {
        LOGGER.info("Starting reactive web filter configuration for public paths");

        final Optional<String[]> publicEndpointsOpt = Optional
                .ofNullable(afklSecurityProperties.getPublicEndpoints())
                .filter(not(List::isEmpty))
                .map(publicEndpointsList -> publicEndpointsList.toArray(new String[0]));

        if (publicEndpointsOpt.isEmpty()) {
            // Match none if no public endpoint is defined so that next SecurityWebFilterChain is evaluated
            http.securityMatcher(new NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.anyExchange()));
        } else {
            final String[] publicEndpoints = publicEndpointsOpt.get();

            // Grant access to any public endpoint as defined in configuration for any user
            http.securityMatcher(ServerWebExchangeMatchers.pathMatchers(publicEndpoints))
                .authorizeExchange().anyExchange().permitAll().and()
                .csrf().disable()
                .requestCache().disable()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .formLogin().disable()
                .httpBasic().disable();

            Stream.of(publicEndpoints).forEach(endpoint -> LOGGER.info("Authorizing access to public '{}' urls", endpoint));

            if (afklSecurityProperties.isAllowIframe()) {
                http.headers().frameOptions().mode(Mode.SAMEORIGIN);
            }

            // Set Content-Security-Policy header value from yaml
            setContentSecurityPolicy(http);
        }

        LOGGER.info("Reactive web filter configuration done for public paths");
        return http.build();
    }

    /**
     * This method provides a bean used in the application to configure the whole Spring Security system
     *
     * @param http the {@link ServerHttpSecurity} used as base for the configuration
     * @return a configured {@link SecurityWebFilterChain}
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        LOGGER.info("Starting reactive web filter configuration for protected paths");

        if (afklSecurityProperties.isAllowIframe()) {
            http.headers().frameOptions().mode(Mode.SAMEORIGIN);
        }

        // Set Content-Security-Policy header value from yaml
        setContentSecurityPolicy(http);

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeSpec = http.authorizeExchange();

        // Grant access to any actuator endpoint for any user owning "actuators role" if such a role is defined in configuration
        Optional.ofNullable(afklSecurityProperties.getActuatorsRole())
            .filter(not(String::isEmpty))
            .ifPresent(actuatorsRole -> authorizeSpec.matchers(EndpointRequest.toAnyEndpoint()).hasRole(actuatorsRole));

        // Grant access based on urls-http-verbs-authorities
        Optional.ofNullable(afklSecurityProperties.getUrlsHttpVerbsAuthorities())
            .filter(not(List::isEmpty))
            .ifPresent(list -> list.forEach(config -> processUrlsHttpVerbsAuthorities(config, http)));

        authorizeSpec
            .anyExchange().authenticated()
            .and()
            .addFilterAt(habileAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .formLogin().disable()
            .httpBasic().disable();

        // We want to save the csrf token in a cookie so that angular can read it
        ServerCsrfTokenRepository csrfTokenRepository = getCookieServerCsrfTokenRepository();
        http.csrf()
            .csrfTokenRepository(csrfTokenRepository)
            .csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler())
            .accessDeniedHandler(new CsrfServerAccessDeniedHandler(csrfTokenRepository));

        // Provide a custom redirect handler for Habile logout
        http.logout().logoutSuccessHandler(new HabileLogoutHandler());

        LOGGER.info("Reactive web filter configuration done for protected paths");
        return http.build();
    }

    /**
     * Apply grant policy to a group of profiles on a set of urls and their http method. We'll grant access only to the explicitly stated HTTP verbs.
     * For all the other HTTP methods the policy is deny ALL!
     *
     * @param urlsHttpVerbsAuthorities {@link UrlsHttpVerbsAuthorities}
     * @param http {@link HttpSecurity}
     */
    private void processUrlsHttpVerbsAuthorities(UrlsHttpVerbsAuthorities urlsHttpVerbsAuthorities, ServerHttpSecurity http) {
        try {
            urlsHttpVerbsAuthorities.getHttpVerbsAuthorities()
                .forEach(httpVerbsAuthorities -> httpVerbsAuthorities.getVerbs()
                    .forEach(httpVerb -> authorizeRequestsByRolesAndOrPermissions(
                            urlsHttpVerbsAuthorities.getUrls(),
                            httpVerb,
                            httpVerbsAuthorities.getRoles(),
                            httpVerbsAuthorities.getPermissions(),
                            http)));

            // for the urls patterns deny the access for all the http methods not defined.
            http.authorizeExchange()
                .pathMatchers(urlsHttpVerbsAuthorities.getUrls().stream().toArray(String[]::new))
                .denyAll();
        } catch (Exception e) {
            LOGGER.error("Error applying deny all policy for '{}'", urlsHttpVerbsAuthorities.getUrls(), e);
        }
    }

    private void authorizeRequestsByRolesAndOrPermissions(List<String> urls, HttpMethod httpMethod, List<String> roles, List<String> permissions, ServerHttpSecurity http) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Authorizing access to '{}' urls with http method '{}' for {}", urls, httpMethod, getRolesAndOrPermissionsListing(roles, permissions));
        }

        try {
            http.authorizeExchange()
                .pathMatchers(httpMethod, urls.stream().toArray(String[]::new))
                .hasAnyAuthority(mergeRolesAndPermissions(roles, permissions));

        } catch (Exception e) {
            LOGGER.error("Error applying custom security configuration on '{}' for '{}'", urls, getRolesAndOrPermissionsListing(roles, permissions), e);
        }
    }

    /**
     * Configure CSRF Token Repository as cookie
     * <p>
     * Set HttpOnly flag to false and Path to /
     *
     * @return HabileProxySimul
     */
    private ServerCsrfTokenRepository getCookieServerCsrfTokenRepository() {
        // We want the cookie to be read by javascript
        CookieServerCsrfTokenRepository tokenRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();

        // We set the cookie path to / because front end and backend are on different
        // context paths
        tokenRepository.setCookiePath("/");

        return tokenRepository;
    }

    private AuthenticationWebFilter habileAuthenticationWebFilter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(habileAuthManager);
        filter.setServerAuthenticationConverter(new HabileAuthenticationConverter());
        filter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        filter.setAuthenticationSuccessHandler(new HabileAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new HabileAuthenticationFailureHandler());
        return filter;
    }

    /**
     * Sets the content security policy of the specified {@link ServerHttpSecurity}
     * instance according to the internal security configuration (properties).
     *
     * @param http
     *          The {@link ServerHttpSecurity} instance for which to set the
     *          {@code Content-Security-Policy} HTTP header.
     */
    private void setContentSecurityPolicy(ServerHttpSecurity http) {
        // Set Content-Security-Policy header value from yaml
        Optional.ofNullable(afklSecurityProperties.getContentSecurityPolicy())
                .filter(not(String::isBlank))
                .ifPresent(policy -> http.headers().contentSecurityPolicy(policy));
    }
}
