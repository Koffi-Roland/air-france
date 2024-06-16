package com.afklm.spring.security.habile;

import com.afklm.spring.security.habile.userdetails.dao.UserRetrieverDaoImplPing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.Cookie;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles({"customSecurityConfig", "customUserServiceDetailsServiceConfig"})
public class CustomSecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    final private String userId = "toto";
    final String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJNT0NLX0lEIiwiaWF0IjoxNTk5NzUzMzYxLCJzdWIiOiJkdW1tbXkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDEvIiwiZXhwIjoxNTk5NzUzMzcxfQ.OQdap9Oj5-PtupspF_4ZXcUCB_1wrG9efVO5XO8drms";

    final private String smSession = "junitSmsession";
    final private String contentSecurityPolicyHeader = "Content-Security-Policy";
    final private String contentSecurityPolicyExampleValue = "upgrade-insecure-requests; frame-ancestors 'self'; " +
            "font-src 'self' https://fonts.googleapis.com; script-src 'self'; object-src 'self';";


    @MockBean
    private UserRetrieverDaoImplPing userRetrieverDao;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    private void setupMockOk(String id, String... profilesToBeInjected) {
        HabilePrincipal habilePrincipal = HabilePrincipalFixture.habilePrincipal(id, profilesToBeInjected);
        Mockito.when(userRetrieverDao.getUser(jwt, null)).thenReturn(habilePrincipal);
    }

    /**
     * Check that specific configuration is required to define custom public page patterns.
     * <p>
     * This test should fail with 404 since (1) the URL is unprotected thanks to a custom
     * security configuration but (2) the URL is not mapped to any controller.
     */
    @Test
    public void testGetExtraUnprotectedPage() throws Exception {
        checkNotFound("/foo/bar");
        checkNotFound("/faa/bar");
    }

    /**
     * Check that we did not lose the URL that are unprotected by default.
     */
    @Test
    public void testGetDefaultUnprotectedPage() throws Exception {
        checkNotFound("/ws/fake");
    }

    @Test
    public void testMeEndpointAnonymousWithRealUser() throws Exception {
        setupMockOk(userId, "P_TEST", "P_PROFILE_NOT_COVERED_IN_YAML_THEN_TO_BE_SKIPPED");

        String junitUserId = "JUNIT-" + userId;
        mockMvc.perform(get("/me")
            .header(GenericHeaders.X_FORWARDED_USER, junitUserId)
            .header(GenericHeaders.X_ACCESS_TOKEN, jwt)
            .cookie(new Cookie(GenericHeaders.SM_SESSION, smSession)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype())))
            .andExpect(authenticated().withUsername(junitUserId)
                .withAuthorities(Stream.of("AK_TEST", "AK_MODIF", "ROLE_P_TEST").map(SimpleGrantedAuthority::new).collect(Collectors.toList())))
            .andExpect(content().json("{\"username\":\"JUNIT-toto\",\"customField\":\"TECCSE JUNIT CUSTOM FIELD\"}"))
            .andExpect(header().stringValues("X-Frame-Options", "SAMEORIGIN"));
    }

    @Test
    public void testPublicEndpointWithoutHeader() throws Exception {
        mockMvc.perform(post("/ws/tryme"))
                .andExpect(status().isOk())
                .andExpect(header().string(contentSecurityPolicyHeader, contentSecurityPolicyExampleValue));
    }

    @Test
    public void testPublicEndpointWithSMUserHeader() throws Exception {
        mockMvc.perform(post("/ws/tryme")
                .header(GenericHeaders.SM_USER,""))
                .andExpect(status().isOk());
    }

    private void checkNotFound(String path) throws Exception {
        mockMvc.perform(get(path))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andReturn();
    }
}
