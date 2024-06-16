package com.afklm.spring.security.habile;

import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;
import com.afklm.spring.security.habile.web.UserResource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HabileUserDetailsTest {

    private final static String USERNAME = "JDOE";
    private final static String LASTNAME = "DOE";
    private final static String FIRSTNAME = "JOHN";
    private final static String EMAIL = "JOHN.DOE@AIRFRANCE.FR";
    private final static String USER_PROFILE = "USER";
    private final static String ADMIN_ROLE = "ADMIN";
    private final static String[] PERMISSIONS = {USER_PROFILE, GlobalConfiguration.ROLE_PREFIX + ADMIN_ROLE};
    private final static List<SimpleGrantedAuthority> AUTHORITIES = Stream.of(PERMISSIONS)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    private HabileUserDetails user;

    @BeforeEach
    public void setHabileUserDetails() {
        user = new HabileUserDetails(USERNAME,
            LASTNAME,
            FIRSTNAME,
            EMAIL,
            AUTHORITIES,
            null);
    }

    @Test
    public void testHabileUserDetails() {
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getLastname()).isEqualTo(LASTNAME);
        assertThat(user.getFirstname()).isEqualTo(FIRSTNAME);
        assertThat(user.getAuthorities()).isNotEmpty();
        assertThat(user.getAuthorities()).containsAll(AUTHORITIES);
    }

    @Test
    public void testHabileUserDetailsService() {
        MyCustomUserDetailsService myUserDetailsService = new MyCustomUserDetailsService();
        UserDetails user2 = myUserDetailsService.enrichUserDetails(user);
        assertThat(user2).isEqualTo(user);

        Object renderedUser = myUserDetailsService.render(user);
        assertThat(renderedUser).isInstanceOf(UserResource.class);
        UserResource userResource = (UserResource) renderedUser;
        assertThat(userResource.getUsername()).isEqualTo(USERNAME);
        assertThat(userResource.getLastname()).isEqualTo(LASTNAME);
        assertThat(userResource.getFirstname()).isEqualTo(FIRSTNAME);
        assertThat(userResource.getEmail()).isEqualTo(EMAIL);
        assertThat(userResource.getRoles().size()).isEqualTo(1);
        assertThat(userResource.getRoles()).contains(ADMIN_ROLE);
        assertThat(userResource.getPermissions().size()).isEqualTo(1);
        assertThat(userResource.getPermissions()).containsAll(Arrays.asList(USER_PROFILE));
    }
}

class MyCustomUserDetailsService implements CustomUserDetailsService {

}
