package com.afklm.spring.security.habile.authentication.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.spring.security.habile.authentication.AuthenticationValidator;

import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class ValidateProvidedTest {
    @Test
    public void validAuthDataTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken("m_user", "some creds", null);

        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void emptyUsernameTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken("", "some creds", null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void nullUsernameTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken(null, "some creds", null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);

    }

    @Test
    public void notAStringUsernameTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken(42, "some creds", null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void emptyCredentialTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken("m_user", "", null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void nullCredentialTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken("m_user", null, null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void notAStringCredentialTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken("m_user", 42, null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void emptyEverythingTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken("", "", null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void nullEverythingTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken(null, null, null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void notAStringEverythingTest() {
        Authentication auth = new PreAuthenticatedAuthenticationToken(42, 42, null);
        StepVerifier.create(AuthenticationValidator.validateProvided(auth))
                .verifyError(AuthenticationCredentialsNotFoundException.class);
    }
}