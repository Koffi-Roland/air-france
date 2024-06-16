package com.afklm.spring.security.habile.properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.http.HttpMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ss4hPropertiesValidationTest {

    private static final List<String> DUMMY_URLS = List.of("/some/url/path/**", "/another/url/path/**");
    private static final List<String> DUMMY_ROLES = List.of("roleA", "roleB");
    private static final List<String> DUMMY_PERMISSIONS = List.of("authorityA", "authorityB");

    @ParameterizedTest
    @NullAndEmptySource
    public void testNullOrEmptyHttpVerbsAuthorities(List<HttpVerbsAuthorities> httpVerbsAuthorities) {
        var urlsHttpVerbsAuthoritiesList = List.of(buildUrlsHttpVerbsAuthorities(DUMMY_URLS, httpVerbsAuthorities));
        Ss4hProperties props = buildSs4hProperties(urlsHttpVerbsAuthoritiesList);

        Errors errors = new MapBindingResult(new HashMap<>(), "incorrectUrlObject");
        props.validate(props, errors);
        assertEquals(1, errors.getAllErrors().size());
        assertEquals("HTTP verbs authorities cannot be null nor empty.", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @ParameterizedTest
    @MethodSource("getNullOrEmptyListArguments")
    public void testNullOrEmptyUrlsAndHttpVerbs(List<String> urls, List<HttpMethod> verbs) {
        var urlsHttpVerbsAuthoritiesList = List.of(buildUrlsHttpVerbsAuthorities(urls, List.of(buildHttpVerbsAuthorities(verbs, DUMMY_ROLES, DUMMY_PERMISSIONS))));
        Ss4hProperties props = buildSs4hProperties(urlsHttpVerbsAuthoritiesList);

        Errors errors = new MapBindingResult(new HashMap<>(), "incorrectUrlObject");
        props.validate(props, errors);
        assertEquals(2, errors.getAllErrors().size());
        assertEquals("URLs cannot be null nor empty.", errors.getAllErrors().get(0).getDefaultMessage());
        assertEquals("HTTP verbs cannot be null nor empty.", errors.getAllErrors().get(1).getDefaultMessage());
    }

    @Test
    public void testValidUrlsAndEmptyHttpVerbs() {
        var urlsHttpVerbsAuthoritiesList = List.of(buildUrlsHttpVerbsAuthorities(List.of("/b/**", "/a/**"), List.of(buildHttpVerbsAuthorities(List.of(), DUMMY_ROLES, DUMMY_PERMISSIONS))));
        Ss4hProperties props = buildSs4hProperties(urlsHttpVerbsAuthoritiesList);

        Errors errors = new MapBindingResult(new HashMap<>(), "incorrectUrlObject");
        props.validate(props, errors);
        assertEquals(1, errors.getAllErrors().size());
        assertEquals("HTTP verbs cannot be null nor empty.", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    public void testIncorrectUrls() {
        var urlsHttpVerbsAuthoritiesList = List.of(buildUrlsHttpVerbsAuthorities(List.of("/b", "/**", ""), List.of(buildHttpVerbsAuthorities(List.of(), DUMMY_ROLES, DUMMY_PERMISSIONS))));
        Ss4hProperties props = buildSs4hProperties(urlsHttpVerbsAuthoritiesList);

        Errors errors = new MapBindingResult(new HashMap<>(), "incorrectUrlObject");
        props.validate(props, errors);
        assertEquals(4, errors.getAllErrors().size());
        assertEquals("URL '/b' does not match regexp.", errors.getAllErrors().get(0).getDefaultMessage());
        assertEquals("URL '/**' does not match regexp.", errors.getAllErrors().get(1).getDefaultMessage());
        assertEquals("URL '' does not match regexp.", errors.getAllErrors().get(2).getDefaultMessage());
        assertEquals("HTTP verbs cannot be null nor empty.", errors.getAllErrors().get(3).getDefaultMessage());
    }

    @Test
    public void testIncorrectRoles() {
        var urlsHttpVerbsAuthoritiesList = List.of(buildUrlsHttpVerbsAuthorities(DUMMY_URLS, List.of(buildHttpVerbsAuthorities(List.of(HttpMethod.GET), List.of("NO_P_KO", "")))));
        Ss4hProperties props = buildSs4hProperties(urlsHttpVerbsAuthoritiesList);

        Errors errors = new MapBindingResult(new HashMap<>(), "incorrectUrlObject");
        props.validate(props, errors);
        assertEquals(2, errors.getAllErrors().size());
        assertEquals("Role 'NO_P_KO' does not start with P_.", errors.getAllErrors().get(0).getDefaultMessage());
        assertEquals("Role '' does not start with P_.", errors.getAllErrors().get(1).getDefaultMessage());
    }

    @ParameterizedTest
    @MethodSource("getNullOrEmptyListArguments")
    public void testNullOrEmptyRolesAndAuthorities(List<String> roles, List<String> authorities) {
        var urlsHttpVerbsAuthoritiesList = List.of(buildUrlsHttpVerbsAuthorities(DUMMY_URLS, List.of(buildHttpVerbsAuthorities(List.of(HttpMethod.GET), roles, authorities))));
        Ss4hProperties props = buildSs4hProperties(urlsHttpVerbsAuthoritiesList);

        Errors errors = new MapBindingResult(new HashMap<>(), "incorrectUrlObject");
        props.validate(props, errors);
        assertEquals(1, errors.getAllErrors().size());
        assertEquals("Roles and permissions cannot be both null or empty.", errors.getAllErrors().get(0).getDefaultMessage());
    }

    @Test
    public void testValidProperties() {
        var urlsHttpVerbsAuthoritiesList = List.of(
            buildUrlsHttpVerbsAuthorities(List.of("/b/**", "/a/**"),
            List.of(buildHttpVerbsAuthorities(List.of(HttpMethod.GET), List.of("P_ADMIN", "P_USER")))));
        Ss4hProperties props = buildSs4hProperties(urlsHttpVerbsAuthoritiesList);

        Errors errors = new MapBindingResult(new HashMap<>(), "incorrectUrlObject");
        props.validate(props, errors);
        assertTrue(errors.getAllErrors().isEmpty());
    }

    private static Stream<Arguments> getNullOrEmptyListArguments() {
        return Stream.of(
                Arguments.of(null     , List.of()),
                Arguments.of(List.of(), null),
                Arguments.of(List.of(), List.of()),
                Arguments.of(null     , null));
    }

    private static Ss4hProperties buildSs4hProperties(List<UrlsHttpVerbsAuthorities> urlsHttpVerbsAuthoritiesList) {
        Ss4hProperties props = new Ss4hProperties();
        props.setUrlsHttpVerbsAuthorities(urlsHttpVerbsAuthoritiesList);
        return props;
    }

    private static UrlsHttpVerbsAuthorities buildUrlsHttpVerbsAuthorities(List<String> urls, List<HttpVerbsAuthorities> httpVerbAuthorities) {
        UrlsHttpVerbsAuthorities urlsHttpVerbsAuthorities = new UrlsHttpVerbsAuthorities();
        urlsHttpVerbsAuthorities.setUrls(urls);
        urlsHttpVerbsAuthorities.setHttpVerbsAuthorities(httpVerbAuthorities);
        return urlsHttpVerbsAuthorities;
    }

    private static HttpVerbsAuthorities buildHttpVerbsAuthorities(List<HttpMethod> verbs, List<String> roles) {
        return buildHttpVerbsAuthorities(verbs, roles, null);
    }

    private static HttpVerbsAuthorities buildHttpVerbsAuthorities(List<HttpMethod> verbs, List<String> roles, List<String> permissions) {
        HttpVerbsAuthorities httpVerbsAuthorities = new HttpVerbsAuthorities();
        httpVerbsAuthorities.setVerbs(verbs);
        httpVerbsAuthorities.setRoles(roles);
        httpVerbsAuthorities.setPermissions(permissions);
        return httpVerbsAuthorities;
    }
}
