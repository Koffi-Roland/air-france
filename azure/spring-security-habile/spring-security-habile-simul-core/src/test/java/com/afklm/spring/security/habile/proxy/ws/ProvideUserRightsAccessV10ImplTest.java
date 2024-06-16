package com.afklm.spring.security.habile.proxy.ws;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.afklm.spring.security.habile.proxy.model.UserInformationFixture;
import com.afklm.spring.security.habile.proxy.service.ConfigurationService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for ProvideUserRightsAccessV10Impl
 * 
 * @author M405991
 *
 */

@ExtendWith(MockitoExtension.class)
public class ProvideUserRightsAccessV10ImplTest {

    @InjectMocks
    private ProvideUserRightsAccessV10Impl provideUserRightsAccessV10Impl;

    @Mock
    private ConfigurationService authoritiesConfigurationService;

    @Test
    public void testUnknownUser() throws HblWsBusinessException, SystemException {
        ProvideUserRightsAccessRQ provideUserRightsAccessRQ = new ProvideUserRightsAccessRQ();
        provideUserRightsAccessRQ.setUserId("junit-unknown");

        Assertions.assertThrows(HblWsBusinessException.class, () -> {
            provideUserRightsAccessV10Impl.provideUserRightsAccess(provideUserRightsAccessRQ);
        });
    }

    @Test
    public void testKnownUser() throws HblWsBusinessException, SystemException {
        UserInformation value = UserInformationFixture.user();
        Mockito.when(authoritiesConfigurationService.getUserInformation(value.getUserId())).thenReturn(value);

        ProvideUserRightsAccessRQ provideUserRightsAccessRQ = new ProvideUserRightsAccessRQ();
        provideUserRightsAccessRQ.setUserId(value.getUserId());

        ProvideUserRightsAccessRS response = provideUserRightsAccessV10Impl.provideUserRightsAccess(provideUserRightsAccessRQ);

        assertThat(response.getFirstName()).isEqualTo(value.getFirstName());
        assertThat(response.getLastName()).isEqualTo(value.getLastName());
        assertThat(response.getProfileList().getProfileName()).containsAll(value.getProfiles());
    }
}
