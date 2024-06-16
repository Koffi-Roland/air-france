package com.afklm.spring.security.habile.proxy.security;

import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.util.StringUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.LOGOUT_DONE;
import static com.afklm.spring.security.habile.proxy.HabileProxyConstants.HABILE_LOGOUT_VALUE;

/**
 * Security configuration
 *
 * @author TECCSE
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private HabileSessionManager habileSessionManager;

    @Autowired
    private SimulationAuthenticationProvider simulationAuthenticationProvider;

    @Autowired
    private HabileBasicAuthenticationSuccessHandler afklAuthenticationSuccessHandler;
    @Autowired
    private AFKLFormAuthenticationSuccessHandler afklFormAuthenticationSuccessHandler;
    @Value("${server.port}")
    private String port;

    /**
     * Configure SecurityWebFilterChain
     * 
     * @param http http security
     * @return habile security filter chain
     * @throws URISyntaxException if error occurs
     */
    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) throws URISyntaxException {
        LOGGER.info("Configuring SecurityWebFilterChain for simulation");

        List<String> publicPatterns = new ArrayList<>();
        publicPatterns.addAll(Arrays.asList("/login", "/idp/userinfo.openid", "/favicon.ico", "/mock/ws/**", "/mock/actuator/**", "/mock/authorities", "/mock/error", LOGOUT_DONE));

        // @formatter:off
        Arrays.asList(configurationService.getPublicPaths()).stream()
            .filter(StringUtils::hasText)
            .map(String::trim)
            .forEach(publicPatterns::add);
        
        String[] arr = publicPatterns.toArray(new String[0]);
        AuthorizeExchangeSpec resultTmp = http.authorizeExchange().pathMatchers(arr).permitAll();
        
        if (configurationService.getAnonymousPaths().length > 0) {
            LOGGER.info("Configuring anonymous authorization strategy");
            resultTmp.pathMatchers(configurationService.getAnonymousPaths()).access((authentication, object) -> {
                return authentication
                        .filter(a -> a.isAuthenticated())
                        .map(a -> new AuthorizationDecision(a.isAuthenticated()))
                        .defaultIfEmpty(new AuthorizationDecision(false));
            });
        }
        
        ServerHttpSecurity result = resultTmp
                .anyExchange().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(new AFKLFormAuthenticationEntryPoint(habileSessionManager, port))
                .and()
                    .csrf().disable()
                    .logout().logoutUrl(HABILE_LOGOUT_VALUE)
                    .logoutSuccessHandler(new HabileLogoutSuccessHandler(LOGOUT_DONE, habileSessionManager))
                    .requiresLogout(ServerWebExchangeMatchers.pathMatchers(HABILE_LOGOUT_VALUE))
                .and()
                    .httpBasic().disable()
                    .formLogin().loginPage("/login").authenticationSuccessHandler(afklFormAuthenticationSuccessHandler)
                .and()
                    .addFilterBefore(new HabileSessionFilter(habileSessionManager, publicPatterns, port), SecurityWebFiltersOrder.ANONYMOUS_AUTHENTICATION)
                    .addFilterBefore(formAuthenticationFilter(), SecurityWebFiltersOrder.ANONYMOUS_AUTHENTICATION);
        // @formatter:on

        if (configurationService.getAnonymousPaths().length > 0) {
            LOGGER.info("Configuring Anonymous filter");
            result.addFilterBefore(anonymousAuthenticationFilter(), SecurityWebFiltersOrder.ANONYMOUS_AUTHENTICATION);
        }
        if (!publicPatterns.isEmpty()) {
            result.addFilterAfter(publicAuthenticationFilter(publicPatterns), SecurityWebFiltersOrder.ANONYMOUS_AUTHENTICATION);
        }
        return result.build();
    }

    private AuthenticationWebFilter formAuthenticationFilter() {
        // Create our own basicAuthenticationFilter because we want to override
        // AuthenticationSuccessHandler
        // and this is not possible yet through HttpBasicSpec
        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(simulationAuthenticationProvider);
        authenticationFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(new AFKLFormAuthenticationEntryPoint(habileSessionManager, port)));
        authenticationFilter.setAuthenticationSuccessHandler(afklAuthenticationSuccessHandler);
        authenticationFilter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        authenticationFilter.setServerAuthenticationConverter(new HabileServerHttpBasicAuthenticationConverter());
        return authenticationFilter;
    }

    private AuthenticationWebFilter anonymousAuthenticationFilter() {
        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(new AnonymousAuthenticationProvider());
        authenticationFilter.setServerAuthenticationConverter(new AnonymousAuthenticationConverter());
        authenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(configurationService.getAnonymousPaths()));
        authenticationFilter.setAuthenticationSuccessHandler(afklAuthenticationSuccessHandler);
        return authenticationFilter;
    }

    private AuthenticationWebFilter publicAuthenticationFilter(List<String> publicPatterns) {
        AuthenticationWebFilter authenticationFilter = new AuthenticationWebFilter(new PublicAuthenticationProvider());
        authenticationFilter.setServerAuthenticationConverter(new PublicAuthenticationConverter());
        authenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers(publicPatterns.toArray(new String[0])));
        authenticationFilter.setAuthenticationSuccessHandler(afklAuthenticationSuccessHandler);
        return authenticationFilter;
    }
}
