package com.afklm.spring.security.habile.userdetails.dao;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.spring.security.habile.HabilePrincipal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserRetrieverDaoImplV1Test {

    @InjectMocks
    private UserRetrieverDaoImplV1 userRetrieverDaoImpl;

    @Mock
    private ProvideUserRightsAccessV10 mockHabileWSConsumer;

    final String userId = "dummmy";

    @Test
    public void testNominal() throws HblWsBusinessException, SystemException {
        // Setup
        ProvideUserRightsAccessRS invocationResponse = ProvideUserRightsAccessV10Fixture.response();
        Mockito.when(mockHabileWSConsumer.provideUserRightsAccess(any(ProvideUserRightsAccessRQ.class)))
            .thenReturn(invocationResponse);

        // Invoke
        HabilePrincipal habilePrincipal = userRetrieverDaoImpl.getUser(userId, null);

        // Verify
        verifyStubInvocation();

        assertThat(habilePrincipal.getUserId()).isEqualTo(userId);
        assertThat(habilePrincipal.getFirstName()).isEqualTo(invocationResponse.getFirstName());
        assertThat(habilePrincipal.getLastName()).isEqualTo(invocationResponse.getLastName());
        assertThat(habilePrincipal.getEmail()).isNull();
        assertThat(habilePrincipal.getProfiles()).containsAll(invocationResponse.getProfileList().getProfileName());
    }

    @Test
    public void testExceptionWrapped() throws HblWsBusinessException, SystemException {
        // Setup
        Throwable exception = ProvideUserRightsAccessV10Fixture.webServiceException();
        Mockito.when(mockHabileWSConsumer.provideUserRightsAccess(any(ProvideUserRightsAccessRQ.class)))
            .thenThrow(exception);

        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> {
            userRetrieverDaoImpl.getUser(userId, null);
        })
            .withCause(exception)
            .withMessage(String.format(UserRetrieverDao.WS_ERROR_MESSAGE, userId))
            .withMessageContaining("'" + userId + "'");
        verifyStubInvocation();
    }

    @Test
    public void testBizExceptionWrapped() throws HblWsBusinessException, SystemException {
        // Setup
        Throwable exception = ProvideUserRightsAccessV10Fixture.webServiceBizException();
        Mockito.when(mockHabileWSConsumer.provideUserRightsAccess(any(ProvideUserRightsAccessRQ.class)))
            .thenThrow(exception);

        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> {
            userRetrieverDaoImpl.getUser(userId, null);
        })
            .withCause(exception)
            .withMessage(String.format(UserRetrieverDao.WS_BIZ_ERROR_MESSAGE, userId));
        verifyStubInvocation();
    }

    private void verifyStubInvocation() throws HblWsBusinessException, SystemException {
        ArgumentCaptor<ProvideUserRightsAccessRQ> provideUserRightsAccessRQ = ArgumentCaptor
            .forClass(ProvideUserRightsAccessRQ.class);
        Mockito.verify(mockHabileWSConsumer).provideUserRightsAccess(provideUserRightsAccessRQ.capture());

        assertThat(provideUserRightsAccessRQ.getValue().getUserId()).isEqualTo(userId);
    }
}
