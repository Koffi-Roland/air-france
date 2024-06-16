package com.afklm.spring.security.habile.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.afklm.spring.security.habile.GlobalConfiguration;
import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.utils.StreamHelper;

public class CompleteHabileUserDetailsTest {
    //TODO: fix the tests
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "first name";
    private static final String LASTNAME = "last name";
    private static final String[] baseRoles = {
            "P_TEST_DEV",
            "P_TEST_ADMIN",
            "P_TEST_USELESS"
    };
    private static final String[] appRoles = {
            "P_TEST_DEV",
            "P_TEST_ADMIN"
    };
    private static final String[] permissions = {
            "ADMIN",
            "DEV",
            "BLA"
    };
    private static final String[] fullAuthorities = {
            GlobalConfiguration.ROLE_PREFIX + "P_TEST_DEV",
            GlobalConfiguration.ROLE_PREFIX + "P_TEST_ADMIN",
            "ADMIN",
            "DEV",
            "BLA"
    };

    private List<? extends GrantedAuthority> authorities(String... auths) {
        return StreamHelper.transform(Arrays.asList(auths), SimpleGrantedAuthority::new);
    }

    @Test
    public void initializeWithAuthoritiesTest() {
        HabileUserDetails testUser = new HabileUserDetails(USERNAME, LASTNAME, FIRSTNAME, authorities(baseRoles), null);
        CompleteHabileUserDetails testHabile = CompleteHabileUserDetails
                .from(testUser)
                .withAllAuthorities(authorities(fullAuthorities));

//        assertThat(testHabile.getBaseRoles()).containsExactlyElementsOf(it2); //.containsInAnyOrder(authorities(baseRoles));
        assertThat(testHabile.getRoles()).containsExactlyElementsOf(authorities(appRoles));
        assertThat(testHabile.getPermissions()).containsExactlyElementsOf(authorities(permissions));
        assertThat(testHabile.getAuthorities()).containsAll(authorities(fullAuthorities));
    }

    @Test
    public void initializeWithEmptyAuthoritiesTest() {
        HabileUserDetails testUser = new HabileUserDetails(USERNAME, LASTNAME, FIRSTNAME, Collections.emptyList(), null);
        CompleteHabileUserDetails testHabile = CompleteHabileUserDetails.from(testUser);

        assertThat(testHabile.getBaseRoles()).isEmpty();
        assertThat(testHabile.getRoles()).isEmpty();

        testHabile = CompleteHabileUserDetails
                .from(testUser)
                .withAllAuthorities(Collections.emptyList());

        assertThat(testHabile.getBaseRoles()).isEmpty();
        assertThat(testHabile.getRoles()).isEmpty();
    }

    @Test
    public void initializeWithoutMatchingAuthoritiesTest() {
        HabileUserDetails testUser = new HabileUserDetails(USERNAME, LASTNAME, FIRSTNAME, authorities(baseRoles), null);
        CompleteHabileUserDetails testHabile = CompleteHabileUserDetails.from(testUser);
//        assertThat(testHabile.getBaseRoles(), IsIterableContainingInAnyOrder.containsInAnyOrder(authorities(baseRoles).toArray()));
        assertThat(testHabile.getRoles()).isEmpty();
    }


    @Test
    public void initializeWithoutAuthoritiesTest() {
        Assertions.assertThrows(IllegalArgumentException.class, 
                () -> CompleteHabileUserDetails.from(new HabileUserDetails(USERNAME, LASTNAME, FIRSTNAME, null, null))
                );
    }
}