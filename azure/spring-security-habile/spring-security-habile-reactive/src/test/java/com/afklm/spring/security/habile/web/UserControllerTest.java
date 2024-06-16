package com.afklm.spring.security.habile.web;

import static com.afklm.spring.security.habile.GlobalConfiguration.ROLE_PREFIX;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;

import com.afklm.spring.security.habile.user.CompleteHabileUserDetails;
import com.afklm.spring.security.habile.userdetails.NoOpCustomUserDetailsService;
import com.afklm.spring.security.habile.utils.StreamHelper;

import reactor.core.publisher.Mono;

public class UserControllerTest {
    private WebTestClient client;

    private static final String testUsername = "mTEST";
    private static final String testFirstName = "test first name";
    private static final String testLastNme = "test last name";
    private static final String testEmail = "mail@test.com";
    private static final String testRole1 = "SOME_ROLE";
    private static final String testRole2 = "SOME_OTHER_ROLE";

    @BeforeEach
    public void setUp() throws Exception {
        client = WebTestClient
                .bindToController(new UserController(new NoOpCustomUserDetailsService()))
                .argumentResolvers(argumentResolverConfigurer -> argumentResolverConfigurer.addCustomResolver(putAuthenticationPrincipal))
                .configureClient()
                .baseUrl("/me")
                .build();
    }

    private HandlerMethodArgumentResolver putAuthenticationPrincipal = new HandlerMethodArgumentResolver() {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().isAssignableFrom(CompleteHabileUserDetails.class);
        }

        @Override
        public Mono<Object> resolveArgument(MethodParameter methodParameter, BindingContext bindingContext, ServerWebExchange serverWebExchange) {
            return Mono.just(new CompleteHabileUserDetails(testUsername, testLastNme, testFirstName, testEmail, Collections.emptyList(), null)
                    .withAllAuthorities(StreamHelper.transform(Arrays.asList(ROLE_PREFIX + testRole1, ROLE_PREFIX + testRole2), SimpleGrantedAuthority::new)));
        }
    };

    @Test
    public void retrieveUsernameTest() {
        client.get()
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.username").isEqualTo(testUsername);
    }

    @Test
    public void retrieveLastNameTest() {
        client.get()
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.lastname").isEqualTo(testLastNme);
    }

    @Test
    public void retrieveAuthoritiesTest() {
        client.get()
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.roles[0]").isEqualTo(testRole1)
                .jsonPath("$.roles[1]").isEqualTo(testRole2);
    }
}