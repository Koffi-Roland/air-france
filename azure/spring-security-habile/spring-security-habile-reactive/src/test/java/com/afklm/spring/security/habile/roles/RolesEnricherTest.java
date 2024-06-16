package com.afklm.spring.security.habile.roles;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RolesEnricher.class})
public class RolesEnricherTest extends RolesMockTest {
    @Autowired
    private RolesEnricher enricher;

    @Test
    public void loadRoleAndAuthoritiesTest() {
        StepVerifier.create(Mono.just(enricher
                .enrich(authoritiesOf("P_TEST_USER"))))
                .expectNextMatches(auths -> auths.size() == 2 &&
                        auths.containsAll(authoritiesOf("ROLE_P_TEST_USER", "USER")))
                .verifyComplete();

        StepVerifier.create(Mono.just(enricher
                .enrich(authoritiesOf("P_TEST_ADMIN"))))
                .expectNextMatches(auths -> auths.size() == 3 &&
                        auths.containsAll(authoritiesOf("ROLE_P_TEST_ADMIN", "USER", "ADMIN")))
                .verifyComplete();
    }

    @Test
    public void loadMultipleRolesAndGetDistinctAuthoritiesTest() {
        StepVerifier.create(Mono.just(enricher
                .enrich(authoritiesOf("P_TEST_USER", "P_TEST_ADMIN"))))
                .expectNextMatches(auths -> auths.size() == 4 &&
                        auths.containsAll(authoritiesOf("ROLE_P_TEST_USER", "ROLE_P_TEST_ADMIN", "USER", "ADMIN")))
                .verifyComplete();
    }

    @Test
    public void loadEmptyRoleTest() {
        StepVerifier.create(Mono.just(enricher
                .enrich(authoritiesOf("P_TEST_EMPTY"))))
                .expectNextMatches(auths -> auths.size() == 1 &&
                        auths.containsAll(authoritiesOf("ROLE_P_TEST_EMPTY")))
                .verifyComplete();
    }

    @Test
    public void loadNonExistingRoleTest() {
        StepVerifier.create(Mono.just(enricher
                .enrich(authoritiesOf("P_TEST_FAKE_USER"))))
                .expectNextMatches(List::isEmpty)
                .verifyComplete();
    }

    /*@Test
    public void loadRoleWithDuplicatedAuthoritiesTest() {
        StepVerifier.create(matcher
                .match(Mono.just(
                        authoritiesOf("P_TEST_DUPLICATE"))))
                .expectNextMatches(auths -> auths.size() == 3)
                .verifyComplete();
    }*/
}
