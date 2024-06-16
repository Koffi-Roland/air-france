package com.afklm.spring.security.habile.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Ss4hProperties.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@EnableAutoConfiguration
public class Ss4hPropertiesTest {

    @Autowired
    private Ss4hProperties props;

    @Test
    public void testProperties() {
        assertThat(props).isNotNull();

        assertThat(props.getRoles().size()).isEqualTo(4);
        assertThat(props.getPermissions("P_TEST")).containsExactlyElementsOf(List.of("AK_TEST", "AK_MODIF"));

        assertThat(props.getActuatorsRole()).isEqualTo("P_MOCK_ACTUATOR");
        assertThat(props.getAnonymousRoles()).containsExactly("P_MOCK_ANONYMOUS_1", "P_MOCK_ANONYMOUS_2");

        // test urls http verbs permissions
        assertThat(props.getUrlsHttpVerbsAuthorities().size()).isEqualTo(2);

        assertThat(props.getUrlsHttpVerbsAuthorities().get(0).getUrls()).containsExactlyElementsOf(List.of("/settings/**", "/configuration/**"));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(0).getHttpVerbsAuthorities().size()).isEqualTo(2);
        assertThat(props.getUrlsHttpVerbsAuthorities().get(0).getHttpVerbsAuthorities().get(0).getVerbs()).containsExactlyElementsOf(List.of(HttpMethod.GET));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(0).getHttpVerbsAuthorities().get(0).getRoles()).containsExactlyElementsOf(List.of("P_ADMIN", "P_USER"));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(0).getHttpVerbsAuthorities().get(1).getVerbs()).containsExactlyElementsOf(List.of(HttpMethod.POST, HttpMethod.PUT));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(0).getHttpVerbsAuthorities().get(1).getRoles()).containsExactlyElementsOf(List.of("P_ADMIN"));

        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getUrls()).containsExactlyElementsOf(List.of("/api/**"));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().size()).isEqualTo(3);
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().get(0).getVerbs()).containsExactlyElementsOf(List.of(HttpMethod.GET, HttpMethod.HEAD));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().get(0).getPermissions()).containsExactlyElementsOf(List.of("AK_TEST"));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().get(1).getVerbs()).containsExactlyElementsOf(List.of(HttpMethod.POST));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().get(1).getRoles()).containsExactlyElementsOf(List.of("P_ADMIN"));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().get(1).getPermissions()).containsExactlyElementsOf(List.of("AK_MODIF"));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().get(2).getVerbs()).containsExactlyElementsOf(List.of(HttpMethod.DELETE));
        assertThat(props.getUrlsHttpVerbsAuthorities().get(1).getHttpVerbsAuthorities().get(2).getPermissions()).containsExactlyElementsOf(List.of("AK_MODIF"));
    }

    @Test
    public void testRolesAndAnonymousPresent() {
        assertThat(props.getRoles()).isNotEmpty();
        assertThat(props.getAnonymousRoles()).isNotEmpty();
    }

    @Test
    public void testRolesPermissions() {
        Assertions.assertNull(props.getPermissions("P_TEST_NOT_PRESENT"));
        assertEquals(0, props.getPermissions("P_TEST_EMPTY").size());
        assertEquals(List.of("AK_TEST", "AK_MODIF"), props.getPermissions("P_TEST"));
        assertEquals(List.of("AK_TEST"), props.getPermissions("P_TEST2"));
    }

    @Test
    public void testAnonymousRoles() {
        assertEquals(List.of("P_MOCK_ANONYMOUS_1", "P_MOCK_ANONYMOUS_2"), props.getAnonymousRoles());
    }

    @Test
    public void testContentSecurityPolicy() {
        assertEquals("upgrade-insecure-requests; frame-ancestors 'self'; " +
                "font-src 'self' https://fonts.googleapis.com; script-src 'self'; object-src 'self';", props.getContentSecurityPolicy());

    }

}
