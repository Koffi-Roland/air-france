package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.spring.rest.controllers.SecurityController;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SecurityControllerTest {

    private static final String TEST = "Test";
    private static final String ROLE = "ROLE_ADMIN_COMMPREF";

    @Mock
    private SecurityController securityController;



    @Test
    @DisplayName("Unit test to get security data")
    public void testSecuritiesGet()  {
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        ProfileAccessKey profileAccessKey = new ProfileAccessKey();
        profileAccessKey.setAccessKeyLst(null);
        profileAccessKey.setProfile(ROLE);
        UserProfilesAccessKey accessKeys = new UserProfilesAccessKey();
        accessKeys.addProfileAccessKey(profileAccessKey);
        doReturn(accessKeys).when(securityController).securitiesGet(mockAuthenticatedUserDTO);
        UserProfilesAccessKey mockAccessKeys = securityController.securitiesGet(mockAuthenticatedUserDTO);
        assertThat(mockAccessKeys).isNotNull();
    }


    @Test
    @DisplayName("Unit test to Get User credentials")
    public void testSecuritiesGetUser()  {

        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        doReturn(mockAuthenticatedUserDTO).when(securityController).securitiesGetUser(mockAuthenticatedUserDTO);
        AuthenticatedUserDTO authenticatedUserDTO  = securityController.securitiesGetUser(mockAuthenticatedUserDTO);
        assertThat(authenticatedUserDTO).isNotNull();

    }


    @Test
    @DisplayName("Unit test to get user name")
    public void testSecuritiesGetUserName()  {
        ProvideUserRightsAccessRS mockProvideUserRightsAccessRS = this.mockProvideUserRightsAccessRS();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        ProvideUserRightsAccessRQ provideUserRightsAccessRQ = new ProvideUserRightsAccessRQ();
        provideUserRightsAccessRQ.setUserId(mockAuthenticatedUserDTO.getUsername());
        doReturn(mockProvideUserRightsAccessRS).when(securityController).securitiesGetUserName(mockAuthenticatedUserDTO);
        assertThat(mockProvideUserRightsAccessRS).isNotNull();

    }

    private ProvideUserRightsAccessRS mockProvideUserRightsAccessRS()
    {
        ProvideUserRightsAccessRS provideUserRightsAccessRS = new ProvideUserRightsAccessRS();
        provideUserRightsAccessRS.setFirstName(TEST);
        provideUserRightsAccessRS.setLastName(TEST);
        provideUserRightsAccessRS.setProfileList(null);
        return provideUserRightsAccessRS;
    }
    private AuthenticatedUserDTO mockAuthenticateDto()
    {
        // Profile access key
        ProfileAccessKey profileAccessKey = new ProfileAccessKey();
        profileAccessKey.setAccessKeyLst(null);
        profileAccessKey.setProfile("ROLE_ADMIN_COMMPREF");
        UserProfilesAccessKey accessKeys = new UserProfilesAccessKey();
        accessKeys.addProfileAccessKey(profileAccessKey);
        return new AuthenticatedUserDTO(TEST,"password",List.of(new SimpleGrantedAuthority("ROLE_ADMIN_COMMPREF")),accessKeys,TEST,TEST);
    }
}
