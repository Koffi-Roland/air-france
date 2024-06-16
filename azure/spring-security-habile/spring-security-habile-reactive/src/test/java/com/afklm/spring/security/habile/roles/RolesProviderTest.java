package com.afklm.spring.security.habile.roles;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.properties.Ss4hProperties;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * App roles and permissions configuration are located in {@link RolesMockTest}
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RolesProvider.class, RolesMatcher.class, RolesEnricher.class, Ss4hProperties.class})
public class RolesProviderTest extends RolesMockTest {
    @Autowired
    private RolesProvider provider;

    @Test
    public void convertUa2UserTest() {
        HabileUserDetails ua2User = new HabileUserDetails("m000999", "gabin", "jean",
                Stream.of("P_IRRELEVANT_TEST", "P_TEST_USER")
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()),
                        null);

        GrantedAuthority expectedRole = new SimpleGrantedAuthority("P_TEST_USER");
        GrantedAuthority expectedPermission = new SimpleGrantedAuthority("USER");

        StepVerifier.create(provider
                .provideFor(ua2User))
                .expectNextMatches(habile -> habile.getUsername().equals(ua2User.getUsername()) &&
                        habile.getFirstname().equals(ua2User.getFirstname()) &&
                        habile.getLastname().equals(ua2User.getLastname()) &&
                        habile.getRoles().size() == 1 &&
                        habile.getRoles().contains(expectedRole) &&
                        habile.getPermissions().size() == 1 &&
                        habile.getPermissions().contains(expectedPermission))
                .verifyComplete();
    }

    @Test
    public void conversionWithoutMatchingRolesTest() {
        HabileUserDetails ua2User = new HabileUserDetails("m000999", "gabin", "jean",
                Lists.list(new SimpleGrantedAuthority("P_IRRELEVANT_TEST")), null);

        StepVerifier.create(provider
                .provideFor(ua2User))
                .expectNextMatches(habile -> habile.getUsername().equals(ua2User.getUsername()) &&
                        habile.getFirstname().equals(ua2User.getFirstname()) &&
                        habile.getLastname().equals(ua2User.getLastname()) &&
                        habile.getRoles().size() == 0 &&
                        habile.getPermissions().size() == 0)
                .verifyComplete();
    }

    @Test
    public void conversionWithoutRolesTest() {
        HabileUserDetails ua2User = new HabileUserDetails("m000999", "gabin", "jean",
                Collections.emptyList(), null);

        StepVerifier.create(provider
                .provideFor(ua2User))
                .expectNextMatches(habile -> habile.getUsername().equals(ua2User.getUsername()) &&
                        habile.getFirstname().equals(ua2User.getFirstname()) &&
                        habile.getLastname().equals(ua2User.getLastname()) &&
                        habile.getRoles().size() == 0 &&
                        habile.getPermissions().size() == 0)
                .verifyComplete();
    }

//    @Test
    public void conversionWithoutRolesListTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new HabileUserDetails("m000999", "gabin", "jean", null, null);
        });
    }

    @Test
    public void conversionWithListOfNullTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new HabileUserDetails("m000999", "gabin", "jean",
                    Lists.list(null), null);
        });
    }

    @Test
    public void conversionWithListOfNullAuthorityTest() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new HabileUserDetails("m000999", "gabin", "jean",
                    Lists.list(new SimpleGrantedAuthority(null)), null);
        });
                
    }
}