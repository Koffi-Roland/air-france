package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefComPrefMediaDTO;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefMediaController;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RefComPrefMediaControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefComPrefMediaController refComPrefMediaController;

    @Mock
    private RefComPrefMediaService refComPrefMediaService;


    @Test
    @DisplayName("Unit test for Ref communication preferences  media collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefMedia mockRefComPrefMedia = this.mockRefComPrefMedia();
        // when -  action or the behaviour that we are going test
        when(refComPrefMediaService.getAllRefComPrefMedia()).thenReturn(List.of(mockRefComPrefMedia));
        List<RefComPrefMedia> refComPrefMedias = refComPrefMediaController.collectionList(request);
        assertThat(refComPrefMedias.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences media")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefComPrefMediaDTO mockRefComPrefMediaDTO = this.mockRefComPrefMediaDTO();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        refComPrefMediaController.collectionAdd(mockRefComPrefMediaDTO,mockAuthenticatedUserDTO,request,response);
        verify(refComPrefMediaService, times(1)).addRefComPrefMedia(mockRefComPrefMediaDTO,mockAuthenticatedUserDTO.getUsername());
    }

    @Test
    @DisplayName("Unit test to get Ref communication preferences media")
    public void testRefComPrefMediaGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefMedia mockRefComPrefMedia = this.mockRefComPrefMedia();
        when(refComPrefMediaService.getRefComPrefMedia(mockRefComPrefMedia.getCodeMedia())).thenReturn(Optional.of(mockRefComPrefMedia));
        RefComPrefMedia refComPrefMedia = refComPrefMediaController.refComPrefMediaGet(mockRefComPrefMedia.getCodeMedia(),request);
        assertThat(refComPrefMedia).isNotNull();
    }


    @Test
    @DisplayName("Unit test to delete Ref communication preferences media")
    public void testRefComPrefDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefMedia mockRefComPrefMedia = this.mockRefComPrefMedia();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        when(refComPrefMediaService.getRefComPrefMedia(mockRefComPrefMedia.getCodeMedia())).thenReturn(Optional.of(mockRefComPrefMedia));
        refComPrefMediaController.refComPrefDelete(mockAuthenticatedUserDTO,mockRefComPrefMedia.getCodeMedia());
        verify(refComPrefMediaService, times(1)).removeRefComPrefMedia(mockRefComPrefMedia.getCodeMedia(),mockAuthenticatedUserDTO.getUsername());

    }

    private RefComPrefMedia mockRefComPrefMedia() {

        RefComPrefMedia refComPrefMedia = new RefComPrefMedia();
        refComPrefMedia.setCodeMedia(TEST);
        refComPrefMedia.setLibelleMedia(TEST);
        refComPrefMedia.setLibelleMediaEN(TEST);
        refComPrefMedia.setLibelleMedia(TEST);
        return refComPrefMedia;
    }

    private RefComPrefMediaDTO mockRefComPrefMediaDTO() {

        RefComPrefMediaDTO refComPrefMediaDTO = new RefComPrefMediaDTO();
        refComPrefMediaDTO.setCodeMedia(TEST);
        refComPrefMediaDTO.setLibelleMedia(TEST);
        refComPrefMediaDTO.setLibelleMediaEN(TEST);
        refComPrefMediaDTO.setLibelleMedia(TEST);
        return refComPrefMediaDTO;
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
