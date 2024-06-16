package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SimulationAuthenticationProviderTest extends SimulationAuthenticationProvider {

    @InjectMocks
    private SimulationAuthenticationProvider simulationAuthenticationProvider;

    @Mock
    private ConfigurationService authoritiesConfigurationService;

    @Test
    public void testEmptyConfiguration() {
        Mockito.when(authoritiesConfigurationService.hasNoUser()).thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken("junit-user", "junit-password");
        Mono<Authentication> result = simulationAuthenticationProvider.authenticate(authentication);

        StepVerifier.create(result)
            .assertNext(v -> {
                assertThat(v.getName()).isEqualTo("junit-user");
                assertThat(v.getCredentials()).isEqualTo("junit-password");
                assertThat(v.getAuthorities()).isEmpty();
            })
            .verifyComplete();
    }

    @Test
    public void testConfigurationUnknownUser() {
        Mockito.when(authoritiesConfigurationService.hasNoUser()).thenReturn(false);

        Authentication authentication = new UsernamePasswordAuthenticationToken("junit-unknowUser", "junit-password");
        Mono<Authentication> result = simulationAuthenticationProvider.authenticate(authentication);

        StepVerifier.create(result)
            .verifyError(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    public void testConfigurationBadPassword() {
        final UserInformation user = UserInformationFixture.user();
        Mockito.when(authoritiesConfigurationService.hasNoUser()).thenReturn(false);
        Mockito.when(authoritiesConfigurationService.getUserInformation(user.getUserId())).thenReturn(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), "junit-badPassword");
        Mono<Authentication> result = simulationAuthenticationProvider.authenticate(authentication);

        StepVerifier.create(result)
            .verifyError(BadCredentialsException.class);
    }

    @Test
    public void testConfigurationNominal() {
        final UserInformation user = UserInformationFixture.user();
        Mockito.when(authoritiesConfigurationService.hasNoUser()).thenReturn(false);
        Mockito.when(authoritiesConfigurationService.getUserInformation(user.getUserId())).thenReturn(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());
        Mono<Authentication> result = simulationAuthenticationProvider.authenticate(authentication);

        StepVerifier.create(result)
            .assertNext(v -> {
                assertThat(v.getName()).isEqualTo(user.getUserId());
                assertThat(v.getCredentials()).isEqualTo(user.getPassword());
                assertThat(v.getAuthorities()).isEmpty();
            })
            .verifyComplete();
    }
}
