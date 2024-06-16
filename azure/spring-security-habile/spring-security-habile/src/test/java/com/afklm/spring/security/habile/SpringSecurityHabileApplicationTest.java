package com.afklm.spring.security.habile;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.afklm.spring.security.habile.oidc.UserInfo;
import com.afklm.spring.security.habile.userdetails.dao.UserInfoFixture;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDao;
import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDaoImplPing;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@Import({PingConfiguration.class})
public class SpringSecurityHabileApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private UserRetrieverDao userRetrieverDao;

    @Autowired
    private UserRetrieverDaoImplPing userRetrieverDaoImpl;

    @MockBean
    private RestTemplate mockRestTemplate;

    final private String userId = "toto";
    final String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJNT0NLX0lEIiwiaWF0IjoxNTk5NzUzMzYxLCJzdWIiOiJkdW1tbXkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDEvIiwiZXhwIjoxNTk5NzUzMzcxfQ.OQdap9Oj5-PtupspF_4ZXcUCB_1wrG9efVO5XO8drms";
    // another JWT with same subject 'dummy'
    final String updatedJwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkdW1teSIsInNjb3BlIjpbIm9wZW5pZCIsImFkZHJlc3MiLCJlbWFpbCIsInBob25lIiwicHJvZmlsZSJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDEiLCJleHAiOjE2MjMwNjA0NzIsImlhdCI6MTYyMzA1Njg3MiwianRpIjoic3M0aC1wcm94eSJ9.ci7ZucWNNZaQbyY7NrqJ8wKWNHhaBMLjyOf3MnU0hBo";
    final String jwtWithoutIssuer = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJzczRoLXByb3h5IiwiaWF0IjoxNjAzMTEzOTUwLCJzdWIiOiJ1c2VyIiwiZXhwIjoxNjAzMTE3NTUwfQ.pPn-a9FtDHKCs9VTf4C-i-Bonbu7ufQvGJzcpsBycOo";
    final private String smSession = "junitSmsession";
    final private String contentSecurityPolicyHeader = "Content-Security-Policy";
    final private String contentSecurityPolicyExampleValue = "upgrade-insecure-requests; frame-ancestors 'self'; " +
            "font-src 'self' https://fonts.googleapis.com; script-src 'self'; object-src 'self';";

    private String returnedRuntimeErrorStart;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        String transientMsg = SS4H_MSG_RT_ERROR.format("");
        returnedRuntimeErrorStart = transientMsg.substring(0, transientMsg.length() - 1);
        this.userRetrieverDaoImpl.setRestTemplate(mockRestTemplate);
    }

    private void setupMockOk(String id, String... profilesToBeInjected) {
        HabilePrincipal habilePrincipal = HabilePrincipalFixture.habilePrincipal(id, profilesToBeInjected);

        Mockito.when(userRetrieverDao.getUser(id, smSession)).thenReturn(habilePrincipal);
        Mockito.when(mockRestTemplate.exchange(
            ArgumentMatchers.any(URI.class),
            ArgumentMatchers.any(HttpMethod.class),
            ArgumentMatchers.any(),
            ArgumentMatchers.<Class<UserInfo>> any()))
            .thenReturn(new ResponseEntity<UserInfo>(UserInfoFixture.response(userId, profilesToBeInjected), HttpStatus.OK));
    }

    private void setupMockKo() {
        Mockito.when(userRetrieverDao.getUser(Mockito.anyString(), Mockito.anyString())).thenThrow(HabilePrincipalFixture.exception());
        Mockito.when(mockRestTemplate.exchange(
            ArgumentMatchers.any(URI.class),
            ArgumentMatchers.any(HttpMethod.class),
            ArgumentMatchers.any(),
            ArgumentMatchers.<Class<UserInfo>> any()))
            .thenThrow(RestClientException.class);
    }

    @Test
    public void testMeEndpointNotConnected() throws Exception {
        MvcResult result = mockMvc.perform(get("/me"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue))
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)))
            .andReturn();

        Assertions.assertNull(result.getResponse().getErrorMessage());
    }

    @Test
    public void testMeEndpointAnonymous() throws Exception {
        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_ACCESS_TOKEN, "")
            .header(GenericHeaders.X_FORWARDED_USER, "")
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(GlobalConfiguration.ANONYMOUS)
                .withAuthorities(Stream.of("AK_TEST", "ROLE_P_TEST2", "ROLE_P_TEST_EMPTY").map(SimpleGrantedAuthority::new).collect(Collectors.toList())))
            .andExpect(content().json("{\"username\":\"anonymous\",\"lastname\":\"anonymous\",\"firstname\":\"anonymous\",\"permissions\":[\"AK_TEST\"],\"roles\":[\"P_TEST2\", \"P_TEST_EMPTY\"]}"))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));

    }

    @Test
    public void testMeEndpointAnonymousSiteMinder() throws Exception {
        mockMvc.perform(get("/me")
            .header(GenericHeaders.SM_USER, GenericHeaders.SM_USER_ANONYMOUS)
            .header(GenericHeaders.SM_USERDN, GenericHeaders.SM_USER_ANONYMOUS)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(GlobalConfiguration.ANONYMOUS)
                .withAuthorities(Stream.of("AK_TEST", "ROLE_P_TEST2", "ROLE_P_TEST_EMPTY").map(SimpleGrantedAuthority::new).collect(Collectors.toList())))
            .andExpect(content().json("{\"username\":\"anonymous\",\"lastname\":\"anonymous\",\"firstname\":\"anonymous\",\"permissions\":[\"AK_TEST\"],\"roles\":[\"P_TEST2\", \"P_TEST_EMPTY\"]}"));
    }

    @Test
    public void testMeEndpointAnonymousWithRealUser() throws Exception {
        setupMockOk(userId, "P_TEST", "P_PROFILE_NOT_COVERED_IN_YAML_THEN_TO_BE_SKIPPED");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.SM_USER, userId)
            .header(GenericHeaders.SM_AUTHTYPE, GenericHeaders.SM_AUTHTYPE_ANONYMOUS)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(userId)
                .withAuthorities(Stream.of("AK_TEST", "AK_MODIF", "ROLE_P_TEST").map(SimpleGrantedAuthority::new).collect(Collectors.toList())))
            .andExpect(content().json("{\"username\":\"toto\",\"lastname\":\"lastName_toto\",\"firstname\":\"firstName_toto\",\"permissions\":[\"AK_TEST\",\"AK_MODIF\"],\"roles\":[\"P_TEST\"]}"));
    }

    @Test
    public void testMeEndpointWithRealUserInToken() throws Exception {
        setupMockOk(userId, "P_TEST", "P_PROFILE_NOT_COVERED_IN_YAML_THEN_TO_BE_SKIPPED");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(userId))
            .andExpect(content().json("{\"username\":\"toto\",\"lastname\":\"lastName\",\"firstname\":\"firstName\",\"permissions\":[\"AK_TEST\",\"AK_MODIF\"],\"roles\":[\"P_TEST\"]}"))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
    }

    @Test
    public void testMeAndCHeckTokenIsTakenInsteadOfSMUser() throws Exception {
        setupMockOk(userId, "P_TEST");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.SM_USER, userId)
            .header(GenericHeaders.SM_USERDN, userId)
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(userId))
            .andExpect(content().json("{\"username\":\"toto\",\"lastname\":\"lastName\",\"firstname\":\"firstName\",\"permissions\":[\"AK_TEST\",\"AK_MODIF\"],\"roles\":[\"P_TEST\"]}"));
    }

    @Test
    public void testMeEndpointWithoutIssuerInToken() throws Exception {
        setupMockOk(userId, "P_TEST", "P_PROFILE_NOT_COVERED_IN_YAML_THEN_TO_BE_SKIPPED");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwtWithoutIssuer)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testMeEndpointAnonymousWithRealUserInTokenAndWrongForwardedUserHeader() throws Exception {
        setupMockOk(userId, "P_TEST", "P_PROFILE_NOT_COVERED_IN_YAML_THEN_TO_BE_SKIPPED");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_FORWARDED_USER, "dummy2")
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())

            .andExpect(status().isUnauthorized())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
    }

    @Test
    public void testMeEndpointUserWithCorrectAccessKeySMHeader() throws Exception {
        setupMockOk(userId, "P_TEST", "P_PROFILE_NOT_COVERED_IN_YAML_THEN_TO_BE_SKIPPED");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.SM_USER, userId)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(userId)
                .withAuthorities(Stream.of("AK_TEST", "AK_MODIF", "ROLE_P_TEST").map(SimpleGrantedAuthority::new).collect(Collectors.toList())))
            .andExpect(content().json(
                "{\"username\":\"toto\",\"lastname\":\"lastName_toto\",\"firstname\":\"firstName_toto\",\"email\":\"lastName_toto@fxture.org\",\"permissions\":[\"AK_TEST\",\"AK_MODIF\"],\"roles\":[\"P_TEST\"]}"));
    }

    public void testMeEndpointUserWithCorrectAccessKey() throws Exception {
        setupMockOk(userId, "P_TEST", "P_PROFILE_NOT_COVERED_IN_YAML_THEN_TO_BE_SKIPPED");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(
                new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(userId)
                .withAuthorities(Stream.of("AK_TEST", "AK_MODIF", "ROLE_P_TEST").map(SimpleGrantedAuthority::new).collect(Collectors.toList())))
            .andExpect(content().json(
                "{\"username\":\"toto\",\"lastname\":\"lastName_toto\",\"firstname\":\"firstName_toto\",\"email\":\"lastName_toto@fxture.org\",\"permissions\":[\"AK_TEST\",\"AK_MODIF\"],\"roles\":[\"P_TEST\"]}"));
    }

    @Test
    public void testGetMethodToGetCsrfCookie() throws Exception {
        setupMockOk(userId, "P_TEST");

        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(cookie().exists("XSRF-TOKEN"))
            .andExpect(cookie().httpOnly("XSRF-TOKEN", false))
            .andExpect(cookie().path("XSRF-TOKEN", "/"))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
    }

    @Test
    public void testPostMethodWithoutCsrfTokenIsForbiddenSMHeader() throws Exception {
        setupMockOk(userId, "P_TEST");

        final String fakeLogoutUrl = "http://fakeurl";
        mockMvc.perform(post("/logout")
            .header(GenericHeaders.SM_USER, userId)
            .header(GenericHeaders.SM_LOGOUT, fakeLogoutUrl))
            .andDo(print())
            .andExpect(status().isForbidden())
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
    }

    public void testPostMethodWithoutCsrfTokenIsForbidden() throws Exception {
        setupMockOk(userId, "P_TEST");

        final String fakeLogoutUrl = "http://fakeurl";
        mockMvc.perform(post("/logout")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .header(GenericHeaders.X_LOGOUT_URL, fakeLogoutUrl))
            .andDo(print())
            .andExpect(status().isForbidden())
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)));
    }

    @Test
    public void testMeEndpointUnknownUser() throws Exception {
        setupMockKo();

        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE))
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
    }

    @Test
    public void testMeEndpointPingAndSMHeader() throws Exception {
        mockMvc.perform(get("/me")
            .header(GenericHeaders.SM_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE))
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)));
    }

    @Test
    public void testMeEndpointSecMobileAndPingHeader() throws Exception {
        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .header(GenericHeaders.SECGW_USER, userId))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE))
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)));
    }

    @Test
    public void testMeEndpointSecMobileAndPingAndSMHeader() throws Exception {
        mockMvc.perform(get("/me")
            .header(GenericHeaders.SM_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .header(GenericHeaders.SECGW_USER, userId))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE))
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)));
    }

    /**
     * The with(csrf()) invocation replaces our current configuration. To annotate is as
     * dirty asks Spring to reload our configuration. This is to avoid side effect on further
     * tests.
     * 
     * @throws Exception
     */
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testLogoutWithSMHeader() throws Exception {
        setupMockOk(userId, "P_TEST");

        final String fakeLogoutUrl = "http://fakeurl";
        mockMvc.perform(post("/logout").with(csrf())
            .header(GenericHeaders.SM_LOGOUT, fakeLogoutUrl))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(GenericHeaders.SM_LOGOUT, URLEncoder.encode(fakeLogoutUrl, "UTF-8")))
            .andExpect(header().string(GenericHeaders.X_LOGOUT_URL, URLEncoder.encode(fakeLogoutUrl, "UTF-8")))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));

    }

    /**
     * The with(csrf()) invocation replaces our current configuration. To annotate is as
     * dirty asks Spring to reload our configuration. This is to avoid side effect on further
     * tests.
     * 
     * @throws Exception
     */
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testLogoutWith() throws Exception {
        setupMockOk(userId, "P_TEST");

        final String fakeLogoutUrl = "http://fakeurl";
        mockMvc.perform(post("/logout").with(csrf())
            .header(GenericHeaders.X_LOGOUT_URL, fakeLogoutUrl))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(GenericHeaders.SM_LOGOUT, URLEncoder.encode(fakeLogoutUrl, "UTF-8")))
            .andExpect(header().string(GenericHeaders.X_LOGOUT_URL, URLEncoder.encode(fakeLogoutUrl, "UTF-8")));
    }

    /**
     * Check that specific configuration is required to define custom public page
     * patterns. See also {@link CustomSecurityConfigTest}.
     *
     * This test should fail with 401 since the URL is not unprotected.
     */
    @Test
    public void testGetProtectedPage() throws Exception {
        mockMvc.perform(get("/foo/bar"))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE))
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue))
            .andExpect(content().string(containsString(returnedRuntimeErrorStart)));
    }

    /**
     * Admin users are not allowed to use /test/**.
     */
    @Test
    public void testCustomUrlAsNotGrantedUserSMHeader() throws Exception {
        setupMockOk(userId, "P_ADMIN");

        mockMvc.perform(get("/test/info")
            .header(GenericHeaders.SM_USER, userId)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isForbidden())
            .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
    }

    @Test
    public void testCustomUrlAsNotGrantedUser() throws Exception {
        setupMockOk(userId, "P_ADMIN");

        mockMvc.perform(get("/test/info")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    /**
     * Regular users are not allowed to use /test/**.
     */
    @Test
    public void testCustomUrlAsGrantedUserSMHeader() throws Exception {
        setupMockOk(userId, "P_TEST");

        mockMvc.perform(get("/test/info")
            .header(GenericHeaders.SM_USER, userId)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    public void testCustomUrlAsGrantedUser() throws Exception {
        setupMockOk(userId, "P_TEST");

        mockMvc.perform(get("/test/info")
            .header(GenericHeaders.X_FORWARDED_USER, userId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isNotFound());
    }
    
    @DisplayName("testing the actuator endpoint")
    @Nested
    class TestingTheActuatorEndpoint {

        final private static String ACTUATOR_ENDPOINT = "/actuator/health";

        /**
         * Unauthenticated users are not allowed to use Actuators.
         */
        @Test
        @DisplayName("check Anonymous is not granted for actuators")
        public void testActuatorAsAnonymous() throws Exception {
            mockMvc.perform(get(ACTUATOR_ENDPOINT))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
        }

        /**
         * Regular users are not allowed to use Actuators.
         */
        @Test
        @DisplayName("check Admin with SM headers is granted for actuators")
        public void testActuatorAsUserSmHeader() throws Exception {
            setupMockOk(userId, "P_TEST");

            mockMvc.perform(get(ACTUATOR_ENDPOINT)
                .header(GenericHeaders.SM_USER, userId)
                .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
                .andDo(print())
                .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("check User with Ping headers is not granted for actuators")
        public void testActuatorAsUser() throws Exception {
            setupMockOk(userId, "P_TEST");

            mockMvc.perform(get(ACTUATOR_ENDPOINT)
                .header(GenericHeaders.X_FORWARDED_USER, userId)
                .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
                .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
                .andDo(print())
                .andExpect(status().isForbidden());
        }

        /**
         * Admins are allowed to use Actuators.
         */
        @Test
        @DisplayName("check Admin with SM headers is granted for actuators")
        public void testActuatorAsAdminSMHeader() throws Exception {
            setupMockOk(userId, "P_ADMIN");

            mockMvc.perform(get(ACTUATOR_ENDPOINT)
                .header(GenericHeaders.SM_USER, userId)
                .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
        }

        @Test
        @DisplayName("check Admin with Ping headers is granted for actuators")
        public void testActuatorAsAdmin() throws Exception {
            setupMockOk(userId, "P_ADMIN");

            mockMvc.perform(get(ACTUATOR_ENDPOINT)
                .header(GenericHeaders.X_FORWARDED_USER, userId)
                .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
                .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
                .andDo(print())
                .andExpect(status().isOk());
        }
    }

    // wanted to test the @Nested approach
    @DisplayName("testing the check-session endpoint")
    @Nested
    class TestingTheRefreshEndpoint {
        @Autowired
        @Qualifier("messageResourceSS4H")
        private MessageSource msg;

        @DisplayName("requesting language")
        @ParameterizedTest
        @ValueSource(strings = {"fr", "fr-FR,fr;q=0.9", "en", "en-GB", "en-US,en;q=0.9,fr;q=0.8", "nl", "xyz"})
        void requestingLanguage(String loc) throws Exception {
            setupMockOk(userId, "");
            Locale locale = Locale.filter(Locale.LanguageRange.parse(loc), Arrays.asList(Locale.FRENCH, Locale.ENGLISH))
                    .stream()
                    .findFirst()
                    .orElse(Locale.ENGLISH);
            String expectedContent = msg.getMessage("refresh", null, locale);
            mockMvc.perform(get("/check-session")
                    .header(GenericHeaders.X_FORWARDED_USER, userId)
                    .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, loc)
                    .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(expectedContent)))
                    .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
        }

        @Test
        @DisplayName("requesting without authentication and getting unauthorized")
        void requestingWithoutAuthenticationAndGettingUnauthorized() throws Exception {
            setupMockKo();
            mockMvc.perform(get("/check-session")
                    .header(GenericHeaders.X_FORWARDED_USER, userId)
                    .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
                    .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("test that renewed JWT is available to application")
        void testThatRenewedJwtIsAvailableToApplication() throws Exception {
            setupMockOk(userId, "P_TEST");

            final MockHttpServletRequestBuilder defaultRequestBuilder = get("/me");
            // here we create a MockMvc object that maintains session :)
            MockMvc mockSessionMvc = MockMvcBuilders.webAppContextSetup(context)
                    .defaultRequest(defaultRequestBuilder)
                    .alwaysDo(result -> defaultRequestBuilder.session((MockHttpSession) result.getRequest().getSession()) )
                    .apply(springSecurity())
                    .build();

            mockSessionMvc.perform(get("/me")
                    .header(GenericHeaders.X_FORWARDED_USER, userId)
                    .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
                    .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(jwt)));

            Mockito.clearInvocations(mockRestTemplate);

            mockSessionMvc.perform(get("/me")
                    .header(GenericHeaders.X_FORWARDED_USER, userId)
                    .header(GenericHeaders.X_ACCESS_TOKEN, updatedJwt))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(updatedJwt)));

            // we make sure that the full authentication has not been processed
            assertThat(Mockito.mockingDetails(mockRestTemplate).getInvocations().size()).isEqualTo(0);
        }
    }
}
