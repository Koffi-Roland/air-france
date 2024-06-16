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
@ContextConfiguration(classes = {RolesMatcher.class})
public class RolesMatcherTest extends RolesMockTest {
    @Autowired
    private RolesMatcher matcher;

    @Test
    public void matchExistingRolesTest() {
        StepVerifier.create(Mono.just(matcher
                .match(authoritiesOf("P_TEST_USER", "P_TEST_ADMIN"))))
                .expectNextMatches(auths ->
                        auths.containsAll(authoritiesOf("P_TEST_USER", "P_TEST_ADMIN")))
                .verifyComplete();
    }

    @Test
    public void matchExistingRolesAndIgnoreOthersTest() {
        StepVerifier.create(Mono.just(matcher
                .match(authoritiesOf("P_TEST_USER", "P_TEST_ADMIN", "P_OTHERAPP_USER", "P_OTHERAPP_ADMIN"))))
                .expectNextMatches(auths ->
                        auths.containsAll(authoritiesOf("P_TEST_USER", "P_TEST_ADMIN")))
                .verifyComplete();
    }

    @Test
    public void nothingMatchesTest() {
        StepVerifier.create(Mono.just(matcher
                .match(authoritiesOf("P_OTHERAPP_USER"))))
                .expectNextMatches(List::isEmpty)
                .verifyComplete();
    }

    @Test
    public void nothingToMatchTest() {
        StepVerifier.create(Mono.just(matcher
                .match(authoritiesOf())))
                .expectNextMatches(List::isEmpty)
                .verifyComplete();
    }

    @Test
    public void duplicatedRolesMatchTest() {
        StepVerifier.create(Mono.just(matcher
                .match(authoritiesOf("P_TEST_USER", "P_TEST_USER"))))
                .expectNextMatches(auths -> auths.size() == 2)
                .verifyComplete();
    }

    @Test
    public void nullToMatchTest() {
        StepVerifier.create(Mono.just(matcher
                .match(null)))
                .expectNextMatches(List::isEmpty)
                .verifyComplete();
    }
}
