package com.afklm.cati.common.springsecurity.userdetailsservice;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.spring.rest.resources.HabileResource;
import com.afklm.spring.security.habile.HabileUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
public class MyAuthenticationUserDetailsServiceTest {

    @Mock
    private MyAuthenticationUserDetailsService myAuthenticationUserDetailsService;

    private HabileUserDetails habileUserDetails;
    private AuthenticatedUserDTO customUserDetails;
    private HabileResource habileResource;
    private static final String EMAIL = "cati@airfrance.com";
    private static final String USERNAME = "cati";
    private static final String LASTNAME = "cati";
    private static final String FIRSTNAME = "cati";
    private static final String ROLE = "ROLE_ADMIN_COMMPREF";
    private static final String TOKEN = "457895645133333333333333";
    private static final String PASSWORD = "password";


    @BeforeEach
    public void setup() {
        // Profile access key
        ProfileAccessKey profileAccessKey = new ProfileAccessKey();
        profileAccessKey.setAccessKeyLst(null);
        profileAccessKey.setProfile(ROLE);
        UserProfilesAccessKey accessKeys = new UserProfilesAccessKey();
        accessKeys.addProfileAccessKey(profileAccessKey);
        // User habile
        HabileResource user = new HabileResource();
        user.setUserName(USERNAME);
        user.setLastName(LASTNAME);
        user.setFirstName(FIRSTNAME);
        user.setPermissions(List.of(ROLE));
        user.setRoles(List.of(ROLE));
        this.habileResource = user;
        this.customUserDetails =  new AuthenticatedUserDTO(USERNAME, PASSWORD, List.of(new SimpleGrantedAuthority(ROLE)), accessKeys, FIRSTNAME, LASTNAME);
        this.habileUserDetails = new HabileUserDetails(USERNAME,LASTNAME,FIRSTNAME,EMAIL, List.of(new SimpleGrantedAuthority(ROLE)),TOKEN);
    }

    @Test
    @DisplayName("Unit test for authentication user details")
    public void testEnrichUserDetails()
    {
        // when -  action or the behaviour that we are going test
        doReturn(this.customUserDetails).when(myAuthenticationUserDetailsService).enrichUserDetails(this.habileUserDetails);
        UserDetails userDetails = myAuthenticationUserDetailsService.enrichUserDetails(this.habileUserDetails);
        assertThat(userDetails).isNotNull();
        assertAll(
                () -> assertThat(userDetails).isNotNull(),
                () -> assertEquals(userDetails.getUsername(), USERNAME),
                () -> assertEquals(userDetails.getPassword(), PASSWORD),
                () -> assertThat(userDetails.getAuthorities().size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("Unit test for authentication user details render")
    public void testRenderUserDetailsWithAuthenticatedUserDTO()
    {
        doReturn(this.habileResource).when(myAuthenticationUserDetailsService).render(this.customUserDetails);
        HabileResource habileResourceMock = (HabileResource) myAuthenticationUserDetailsService.render(this.customUserDetails);
        assertThat(habileResourceMock).isNotNull();
        assertAll(
                () -> assertEquals(habileResourceMock.getUserName(), USERNAME),
                () -> assertEquals(habileResourceMock.getFirstName(), FIRSTNAME),
                () -> assertEquals(habileResourceMock.getLastName(), LASTNAME),
                () -> assertThat(habileResourceMock.getPermissions().size()).isEqualTo(1),
                () -> assertThat(habileResourceMock.getRoles().size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("Unit test for authentication user details render")
    public void testRenderUserDetailsWithNoAuthenticatedUserDTO()
    {
        doReturn(this.habileUserDetails).when(myAuthenticationUserDetailsService).render(this.habileUserDetails);
        HabileUserDetails habileUserDetailsMock = (HabileUserDetails) myAuthenticationUserDetailsService.render(this.habileUserDetails);
        assertThat(habileUserDetailsMock).isNotNull();
        assertAll(
                () -> assertEquals(habileUserDetailsMock.getUsername(), USERNAME),
                () -> assertEquals(habileUserDetailsMock.getFirstname(), FIRSTNAME),
                () -> assertEquals(habileUserDetailsMock.getLastname(), LASTNAME),
                () -> assertThat(habileUserDetailsMock.getAuthorities().size()).isEqualTo(1)
        );
    }

}
