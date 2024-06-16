package com.afklm.spring.security.habile.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class linked to the application.yml file holding AF/KL security configuration
 * properties.
 * 
 * @author m408461
 *
 */
@Configuration
@ConfigurationProperties(prefix = "afklm.security")
public class Ss4hProperties implements Validator {

    private static final String HABILE_PROFILE_PREFIX = "P_";

    private static final String URLS_HTTP_VERBS_AUTHORITIES_KEY = "urls-http-verbs-authorities";

    /**
     * List of specific {@link UrlsHttpVerbsAuthorities} to grant access to certain roles on a set of urls and their http verbs.
     * The purpose is to allow a resource /abc to be accessible for a specific list of roles/permissions for GET,
     * and to a different list of roles/permissions for PUT, POST, DELETE...
     */
    private List<UrlsHttpVerbsAuthorities> urlsHttpVerbsAuthorities = new ArrayList<>();

    /**
     * List of Habile Profiles (considered as Spring roles) and associated
     * permissions (considered as Spring authorities).
     */
    private Map<String, List<String>> roles = new HashMap<>();

    /**
     * Role to apply on anonymous users
     */
    private List<String> anonymousRoles = new ArrayList<>();

    /**
     * List of public endpoints
     */
    private List<String> publicEndpoints = new ArrayList<>();

    /**
     * Role granting access to Actuator's URLs.
     */
    private String actuatorsRole;

    /**
     * Allows to set the X-Frame-Options header. true means SAMEORIGIN otherwise DENY.
     */
    private boolean allowIframe;

    /**
     * Allows to set the Content-Security-Policy header with a configurable value
     */
    private String contentSecurityPolicy;

    public Map<String, List<String>> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, List<String>> roles) {
        this.roles = roles;
    }

    public List<String> getAnonymousRoles() {
        return anonymousRoles;
    }

    public void setAnonymousRoles(List<String> anonymousRoles) {
        this.anonymousRoles = anonymousRoles;
    }

    public String getActuatorsRole() {
        return actuatorsRole;
    }

    public void setActuatorsRole(String actuatorsRole) {
        this.actuatorsRole = actuatorsRole;
    }

    public boolean isAllowIframe() {
        return allowIframe;
    }

    public void setAllowIframe(boolean allowIframe) {
        this.allowIframe = allowIframe;
    }

    /**
     * Return the list of permissions associated to a habile profile.
     * 
     * @param role role
     * @return list of permissions or <CODE>null</CODE> when the profile is not
     *         present in the YAML file
     */
    public List<String> getPermissions(String role) {
        return getRoles().get(role);
    }

    public List<UrlsHttpVerbsAuthorities> getUrlsHttpVerbsAuthorities() {
        return urlsHttpVerbsAuthorities;
    }

    public void setUrlsHttpVerbsAuthorities(List<UrlsHttpVerbsAuthorities> urlsHttpVerbsAuthorities) {
        this.urlsHttpVerbsAuthorities = urlsHttpVerbsAuthorities;
    }

    public List<String> getPublicEndpoints() {
        return publicEndpoints;
    }

    public void setPublicEndpoints(List<String> publicEndpoints) {
        this.publicEndpoints = publicEndpoints;
    }

    public String getContentSecurityPolicy() {
        return contentSecurityPolicy;
    }

    public void setContentSecurityPolicy(String contentSecurityPolicy) {
        this.contentSecurityPolicy = contentSecurityPolicy;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Ss4hProperties.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ss4hProperties props = Ss4hProperties.class.cast(target);
        AtomicInteger urlsHttpVerbsAuthoritiesIndex = new AtomicInteger();
        props
            .getUrlsHttpVerbsAuthorities()
            .forEach(action -> validateUrlsHttpVerbsAuthorities(action, errors, urlsHttpVerbsAuthoritiesIndex.getAndIncrement()));
    }

    private void validateUrlsHttpVerbsAuthorities(UrlsHttpVerbsAuthorities urlsHttpVerbsAuthorities, Errors errors, int index) {
        if (CollectionUtils.isEmpty(urlsHttpVerbsAuthorities.getHttpVerbsAuthorities())) {
            errors.rejectValue("http verbs", "Error code", "HTTP verbs authorities cannot be null nor empty.");
        } else {
            validateUrls(urlsHttpVerbsAuthorities.getUrls(), errors, index);
            urlsHttpVerbsAuthorities
                .getHttpVerbsAuthorities()
                .forEach(conf -> validateHttpVerbsAuthorities(conf.getVerbs(), conf.getRoles(), conf.getPermissions(), errors, index));
        }

    }

    private void validateHttpVerbsAuthorities(List<HttpMethod> httpVerbs, List<String> roles, List<String> permissions, Errors errors, int index) {
        if (CollectionUtils.isEmpty(httpVerbs)) {
            errors.rejectValue("http verbs", "Error code", "HTTP verbs cannot be null nor empty.");
        } else {
            validateAuthorities(roles, permissions, errors, index);
        }
    }

    private void validateAuthorities(List<String> roles, List<String> permissions, Errors errors, int index) {
        final boolean noRoles = CollectionUtils.isEmpty(roles);
        if (noRoles && CollectionUtils.isEmpty(permissions)) {
            errors.rejectValue("authorities", "Error code", "Roles and permissions cannot be both null or empty.");
        } else if (!noRoles) {
            roles
                .stream()
                .filter(s -> !s.startsWith(HABILE_PROFILE_PREFIX))
                .forEach(s -> errors.rejectValue(
                                        String.format("%s[%d].roles", URLS_HTTP_VERBS_AUTHORITIES_KEY, index),
                                        "error.role.code",
                                        String.format("Role '%s' does not start with %s.", s, HABILE_PROFILE_PREFIX)));
        }
    }

    private void validateUrls(List<String> urls, Errors errors, int index) {
        if (CollectionUtils.isEmpty(urls)) {
            errors.rejectValue("urls", "Error code", "URLs cannot be null nor empty.");
        } else {
            urls
                .stream()
                .map(String::trim)
                .filter(s -> !(s.startsWith("/") && s.endsWith("/**") && s.length() != 3))
                .forEach(s -> errors.rejectValue(
                                        String.format("%s[%d].urls", URLS_HTTP_VERBS_AUTHORITIES_KEY, index),
                                        "error.url.code",
                                        String.format("URL '%s' does not match regexp.", s)));
        }
    }
}
