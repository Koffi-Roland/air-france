package com.afklm.spring.security.habile.authentication.validator;

import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.authentication.AuthenticationValidator;

import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class ValidateCoherenceTest {
    @Mock
    private Authentication mockedContextAuth;

    @BeforeEach
    public void setup() {
        HabileUserDetails contextUserDetails = new HabileUserDetails("m_user1", "lastname", "name", Collections.emptyList(), null);

        when(mockedContextAuth.getPrincipal()).thenReturn(contextUserDetails);
    }

    @Test
    public void coherentDataTest() {
        Authentication providedAuth = new PreAuthenticatedAuthenticationToken("m_user1", null, null);
        StepVerifier.create(
                AuthenticationValidator.validateCoherence(mockedContextAuth, providedAuth))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void differentUsernameTest() {
        Authentication providedAuth = new PreAuthenticatedAuthenticationToken("m_user_different", null, null);
        StepVerifier.create(
                AuthenticationValidator.validateCoherence(mockedContextAuth, providedAuth))
                .verifyComplete();
    }

    @Test
    public void missingUsernameTest() {
        Authentication providedAuth = new PreAuthenticatedAuthenticationToken("", null, null);
        StepVerifier.create(
                AuthenticationValidator.validateCoherence(mockedContextAuth, providedAuth))
                .verifyComplete();
    }

    @Test
    public void somethingRandomAsPrincipalTest() {
        Authentication providedAuth = new PreAuthenticatedAuthenticationToken(42, null, null);
        StepVerifier.create(
                AuthenticationValidator.validateCoherence(mockedContextAuth, providedAuth))
                .verifyComplete();
    }
}