package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_SECMOBILE;
import static com.afklm.spring.security.habile.properties.HttpVerbsAuthorities.getRolesAndOrPermissionsListing;
import static com.afklm.spring.security.habile.properties.HttpVerbsAuthorities.mergeRolesAndPermissions;
import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.csrf.CsrfFilter;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.afklm.spring.security.habile.helper.PublicResourcesHelper;
import com.afklm.spring.security.habile.properties.Ss4hProperties;
import com.afklm.spring.security.habile.properties.UrlsHttpVerbsAuthorities;
import com.afklm.spring.security.habile.secmobil.SecMobilFilter;

/**
 * Default security configuration.
 * <p>
 * Please refer to ReadMe.md if custom configuration is needed.
 *
 * @author m405991
 */
@Configuration("habileSecurityConfig")
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private static final String CACHE_REQUEST_PARAM = "continue";

    @Autowired
    private Ss4hProperties afklSecurityProperties;

    @Autowired(required = false)
    private SecMobilFilter secMobilFilter;

    @Autowired
    private HabileAccessUnauthorizedHandler habileAccessUnauthorizedHandler;

    @Autowired
    private HabileAccessDeniedHandler habileAccessDeniedHandler;

    @Autowired
    private HabileAuthenticationFailureHandler habileAuthenticationFailureHandler;

    @Autowired
    private ResponseExceptionLogger responseExceptionLogger;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private PublicResourcesHelper publicResourcesHelper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Registering a  {@link SecurityFilterChain} is the recommended way of configuring the {@link HttpSecurity}
     * @param http the {@link HttpSecurity}
     * @return the {@link SecurityFilterChain}
     * @throws Exception in case something goes wrong
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LOGGER.debug("Defining the security configuration");

        // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/session-management.html#_change_httpsessionsecuritycontextrepository_to_delegatingsecuritycontextrepository
        http.securityContext(securityContext -> securityContext
            .securityContextRepository(new DelegatingSecurityContextRepository(
                    new RequestAttributeSecurityContextRepository(),
                    new HttpSessionSecurityContextRepository()))
        // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/session-management.html#_require_explicit_saving_of_securitycontextrepository
            .requireExplicitSave(true));

        // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/session-management.html#requestcache-query-optimization
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(CACHE_REQUEST_PARAM);
        http.requestCache(cache -> cache.requestCache(requestCache));

        // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/session-management.html#_require_explicit_invocation_of_sessionauthenticationstrategy
        http.sessionManagement(sessions -> sessions.requireExplicitAuthenticationStrategy(true));

        if (afklSecurityProperties.isAllowIframe()) {
            http.headers().frameOptions().sameOrigin();
        }

        // We do not allow anonymous access to the application, thus AnonymousFilter
        // will not be set
        http.anonymous().disable();

        // By default #403 is returned so use a custom entryPoint
        // We only return the status code and no data since the semantics of the status code is clear enough
        http.exceptionHandling()
            .authenticationEntryPoint(habileAccessUnauthorizedHandler)
            .accessDeniedHandler(habileAccessDeniedHandler);

        if (secMobilFilter != null) {
            LOGGER.info(SS4H_MSG_SECMOBILE.format());
            http.addFilterAfter(secMobilFilter, ExceptionTranslationFilter.class);
        }

        // we now have two way to get user id, so none of them can make the decision to return 401 when its
        // matching header is missing, we then need to create a third Filter inserted before the two others
        // whose responsibility is only to check we have at least one header
        // Put filter after ExceptionTranslationFilter so that ExceptionTranslationFilter will catch the exception raised
        AFKLMAuthenticationFilter afklHeaderFilter = new AFKLMAuthenticationFilter();
        afklHeaderFilter.setAccessDeniedHandler((request, response, exception) -> responseExceptionLogger.logException(response, HttpStatus.UNAUTHORIZED.value(), exception));
        http.addFilterAfter(afklHeaderFilter, ExceptionTranslationFilter.class);

        SiteMinderAuthenticationFilter siteMinderAuthenticationFilter = new SiteMinderAuthenticationFilter();
        // Allow detection for CrossSession
        siteMinderAuthenticationFilter.setCheckForPrincipalChanges(true);
        siteMinderAuthenticationFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        siteMinderAuthenticationFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        siteMinderAuthenticationFilter.setAuthenticationFailureHandler(habileAuthenticationFailureHandler);
        http.addFilterAfter(siteMinderAuthenticationFilter, AFKLMAuthenticationFilter.class);

        PingAuthenticationFilter pingRequestHeaderAuthenticationFilter = new PingAuthenticationFilter();
        // Allow detection for CrossSession
        pingRequestHeaderAuthenticationFilter.setCheckForPrincipalChanges(true);
        pingRequestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        pingRequestHeaderAuthenticationFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        pingRequestHeaderAuthenticationFilter.setAuthenticationFailureHandler(habileAuthenticationFailureHandler);
        http.addFilterAfter(pingRequestHeaderAuthenticationFilter, SiteMinderAuthenticationFilter.class);

        // Provide a custom redirect handler for Habile logout
        http.logout().logoutSuccessHandler(new HabileLogoutHandler());

        // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/exploits.html#_defer_loading_csrftoken
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        // set the name, with which the CsrfToken is meant to be saved as an additional request attribute,
        // to be null so that we keep the same behavior as with Spring Security 5.8. If we explicitly
        // set this name to "_csrf", the value mentioned in the migration guide, or any other non-null value
        // actually, I do not know if it is a side effect or not, but the deferred loading mechanism bypasses
        // the creation and saving of the CSRF token in the CsrfTokenRepository which, at the end, leads to
        // the CSRF cookie (XSRF-TOKEN) not to be sent.
        // If we wanted to follow the default Spring Security 6 (having this additional attribute name set
        // to "_csrf"), this would require, at some time in the filter chain processing, to retrieve and
        // read the deferred CSRF token from the request attribute. This simple action would simply
        // force a new token to be generated and saved in the CsrfTokenRepository, meaning setting the
        // appropriate cookie in the response in our specific case (CookieCsrfTokenRepository).
        requestHandler.setCsrfRequestAttributeName(null);
// The following code may replace the trick above (setting the CSRF request attribute to null)
// to force reading the deferred CSRF token and it is kept only for reference for now
//        http.addFilterAfter((request, response, chain) -> {
//            Optional.ofNullable(request.getAttribute(CsrfToken.class.getName()))
//                    .filter(CsrfToken.class::isInstance)
//                    .map(CsrfToken.class::cast)
//                    .map(CsrfToken::getToken);
//            chain.doFilter(request, response);
//        }, CsrfFilter.class);
        http.csrf(csrf -> csrf
                .csrfTokenRequestHandler(requestHandler)
                // We want to save the csrf token in a cookie so that angular can read it
                .csrfTokenRepository(getCookieCsrfTokenRepository()));

        String actuatorsRole = afklSecurityProperties.getActuatorsRole();
        if (actuatorsRole != null) {
            http.authorizeHttpRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .hasRole(actuatorsRole);
        }

        // grant access based on urls-http-verbs-authorities
        Optional.ofNullable(afklSecurityProperties.getUrlsHttpVerbsAuthorities())
                .filter(not(List::isEmpty))
                .ifPresent(list -> list.forEach(config -> processUrlsHttpVerbsAuthorities(config, http)));

        // Set Content-Security-Policy header value from yaml
        setContentSecurityPolicy(http);

        // In Spring Security 5.8 and earlier, requests with no authorization rule are
        // permitted by default but in Spring Security 6, if a SecurityFilterChain is
        // configured programmatically, any request that is missing an authorization
        // rule is denied by default (see RequestMatcherDelegatingAuthorizationManager).
        // This new behavior prevents any non configured endpoint ("/me", "/check-session")
        // to be served, ending in an HTTP 403 error to be returned. Indeed, it also
        // forbids any endpoint, protected with a security annotation (@PreAuthorize),
        // from even been called as such a security check is performed after first
        // authorization filter check. To be completely backward compatible, we should
        // have defined "anyRequest().permitAll()" but in order to be less "open" and
        // thus more compliant with Spring Security 6 recommendations, we have chosen
        // to define "anyRequest().authenticated()" for any request that has not any
        // explicit authorization rule. This is also compliant with default Spring
        // Security configuration when no user-defined security filter chain is
        // defined programmatically (see SpringBootWebSecurityConfiguration).
        // https://docs.spring.io/spring-security/reference/5.8/migration/servlet/authorization.html#_ensure_that_all_requests_have_defined_authorization_rules
        http.authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated());

        return http.build();
    }

    /**
     * {@link SecurityFilterChain} needed in order to define the policy to be applied for public resources.
     * Following the instructions: https://github.com/spring-projects/spring-security/issues/10938
     * Once the https://github.com/spring-projects/spring-security/issues/10913 will be available (5.8) we might have to adjust a bit the code
     * The Order it's extremely important: the policy for public resources needs to be defined before the general configurations
     * @param http the {@link HttpSecurity}
     * @return the {@link SecurityFilterChain}
     * @throws Exception when something goes wrong
     */
    @Bean
    @Order(99)
    SecurityFilterChain configurePublicEndpoints(HttpSecurity http) throws Exception {
        // grant access to public resources
        authorizeAccessForPublicResources(publicResourcesHelper.getPublicResources(), http);
        return http.build();
    }

    /**
     * Apply the needed policy to all public resources
     *  Following the instructions: https://github.com/spring-projects/spring-security/issues/10938
     * @param urls the list of public resources
     * @param http the {@link HttpSecurity} to be configured
     */
    private void authorizeAccessForPublicResources(List<String> urls, HttpSecurity http) {
        try {
            if (afklSecurityProperties.isAllowIframe()) {
                http.headers().frameOptions().sameOrigin();
            }

            http.securityMatchers(customizer -> customizer
                    .requestMatchers(urls
                            .stream()
                            .map(AntPathRequestMatcher::antMatcher)
                            .toArray(AntPathRequestMatcher[]::new)))
                .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
                .csrf().disable()
                .requestCache().disable()
                .securityContext().disable()
                .sessionManagement().disable();

            // Set Content-Security-Policy header value from yaml
            setContentSecurityPolicy(http);
        }
        catch (Exception e) {
            LOGGER.error("Error applying custom security configuration on '{}' ", urls, e);
        }
    }

    /**
     * Sets the content security policy of the specified {@link HttpSecurity}
     * instance according to the internal security configuration (properties).
     *
     * @param http
     *          The {@link HttpSecurity} instance for which to set the
     *          {@code Content-Security-Policy} HTTP header.
     * @throws Exception
     *          If the response headers configurer could not be retrieved.
     */
    private void setContentSecurityPolicy(HttpSecurity http) throws Exception {
        Optional<String> policyOpt = Optional.ofNullable(afklSecurityProperties.getContentSecurityPolicy()).filter(not(String::isBlank));
        if (policyOpt.isPresent()) { // Cannot use .ifPresent(...) with lambda due to exception thrown by http.headers()
            http.headers().contentSecurityPolicy(policyOpt.get());
        }
    }

    /**
     * Apply grant policy to a group of profiles on a set of urls and their http method. We'll grant access only to the explicitly stated HTTP verbs.
     * For all the other HTTP methods the policy is deny ALL!
     *
     * @param urlsHttpVerbsAuthorities {@link UrlsHttpVerbsAuthorities}
     * @param http {@link HttpSecurity}
     */
    private void processUrlsHttpVerbsAuthorities(UrlsHttpVerbsAuthorities urlsHttpVerbsAuthorities, HttpSecurity http) {
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
            http.authorizeHttpRequests()
                .requestMatchers(urlsHttpVerbsAuthorities
                        .getUrls()
                        .stream()
                        .map(AntPathRequestMatcher::new)
                        .toArray(AntPathRequestMatcher[]::new))
                .denyAll();
        }
        catch (Exception e) {
            LOGGER.error("Error applying deny all policy for '{}'", urlsHttpVerbsAuthorities.getUrls(), e);
        }
    }

    /**
     * Authorizes requests targeted to the given list of URLs with the specified HTTP method
     * based on the given roles and/or authorities.
     * @param urls the list of URLs to be configured
     * @param httpMethod the {@link HttpMethod} to be authorized
     * @param roles the list of roles for which URLs are authorized
     * @param permissions the list of permissions for which URLs are authorized
     * @param http the {@link HttpSecurity} to be configured
     */
    private void authorizeRequestsByRolesAndOrPermissions(List<String> urls, HttpMethod httpMethod, List<String> roles, List<String> permissions, HttpSecurity http) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Authorizing access to '{}' urls with http method '{}' for {}", urls, httpMethod, getRolesAndOrPermissionsListing(roles, permissions));
        }

        try {
            http.authorizeHttpRequests()
                .requestMatchers(urls
                    .stream()
                    .map(url -> AntPathRequestMatcher.antMatcher(httpMethod, url))
                    .toArray(AntPathRequestMatcher[]::new))
                .hasAnyAuthority(mergeRolesAndPermissions(roles, permissions));
        }
        catch (Exception e) {
            LOGGER.error("Error applying custom security configuration on '{}' for {}", urls, getRolesAndOrPermissionsListing(roles, permissions), e);
        }
    }

    /**
     * Configure CSRF Token Repository as cookie
     * <p>
     * Set HttpOnly flag to false and Path to /
     *
     * @return HabileProxySimul
     */
    private static CsrfTokenRepository getCookieCsrfTokenRepository() {
        // We want the cookie to be read by javascript
        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

        // We set the cookie path to / because front end and backend are on different context paths
        tokenRepository.setCookiePath("/");

        return tokenRepository;
    }

    /**
     * Configure AuthenticationProvider
     * <p>
     * Adds the PreAuthenticatedAuthenticationProvider
     *
     * @param auth AuthenticationManagerBuilder
     * @param preauthAuthProvider the PreAuthenticatedAuthenticationProvider bean
     */
    @Autowired
    public void configureAuthenticationProvider(AuthenticationManagerBuilder auth, PreAuthenticatedAuthenticationProvider preauthAuthProvider) {
        auth.authenticationProvider(preauthAuthProvider);
    }

}
