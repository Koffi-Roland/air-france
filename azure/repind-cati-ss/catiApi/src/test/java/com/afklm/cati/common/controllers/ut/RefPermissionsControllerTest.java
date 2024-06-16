package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefPermissionsController;
import com.afklm.cati.common.spring.rest.resources.RefPermissionsSaveOrUpdateResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import org.dozer.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RefPermissionsControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefPermissionsController refPermissionsController;

    @Mock
    private RefPermissionsQuestionService refPermissionsQuestionService;

    @Mock
    private Mapper dozerBeanMapper;

    @Mock
    private RefPermissionsService refPermissionsService;


    @Test
    @DisplayName("Unit test to add collection of Ref add permissions")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefPermissionsSaveOrUpdateResource mockRefPermissionsSaveOrUpdateResource = this.mockRefPermissionsSaveOrUpdateResource();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefPermissions mockRefPermissions =  this.mockRefPermissions();
        when(dozerBeanMapper.map(mockRefPermissionsSaveOrUpdateResource,RefPermissions.class)).thenReturn(mockRefPermissions);

        refPermissionsController.collectionAdd(mockRefPermissionsSaveOrUpdateResource,mockAuthenticatedUserDTO,request,response);
        verify(refPermissionsService, times(1)).addRefPermissions(mockRefPermissions,1,List.of(1),mockAuthenticatedUserDTO.getUsername());


    }

    @Test
    @DisplayName("Unit test to get Ref permissions")
    public void testRefPermissionsGetRefComPrefDgt() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefPermissionsQuestion mockRefPermissionsQuestion = this.mockRefPermissionsQuestion();
        when(refPermissionsQuestionService.getRefPermissionsQuestion(mockRefPermissionsQuestion.getId())).thenReturn(Optional.of(mockRefPermissionsQuestion));
        when(refPermissionsService.getRefComPrefDgtByRefPermissionsQuestion(mockRefPermissionsQuestion)).thenReturn(List.of(1));
        List<Integer> refPermissionsQuestionId = refPermissionsController.refPermissionsGetRefComPrefDgt(mockRefPermissionsQuestion.getId(),request);
        assertThat(refPermissionsQuestionId.size()).isEqualTo(1);
    }


    private RefPermissionsQuestion mockRefPermissionsQuestion()
    {
        RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
        refPermissionsQuestion.setQuestion(TEST);
        refPermissionsQuestion.setQuestionEN(TEST);
        refPermissionsQuestion.setDateCreation(new Date());
        refPermissionsQuestion.setName(TEST);
        refPermissionsQuestion.setId(1);
        refPermissionsQuestion.setDateCreation(new Date());
        refPermissionsQuestion.setDateModification(new Date());
        refPermissionsQuestion.setSiteCreation(TEST);
        refPermissionsQuestion.setSignatureCreation(TEST);
        refPermissionsQuestion.setSiteModification(TEST);
        return refPermissionsQuestion;
    }

    private RefPermissionsSaveOrUpdateResource mockRefPermissionsSaveOrUpdateResource()
    {
        RefPermissionsSaveOrUpdateResource refPermissionsSaveOrUpdateResource = new RefPermissionsSaveOrUpdateResource();
        refPermissionsSaveOrUpdateResource.setDateCreation(new Date());
        refPermissionsSaveOrUpdateResource.setRefPermissionsQuestionId(1);
        refPermissionsSaveOrUpdateResource.setDateModification(new Date());
        refPermissionsSaveOrUpdateResource.setSignatureCreation(TEST);
        refPermissionsSaveOrUpdateResource.setSiteCreation(TEST);
        refPermissionsSaveOrUpdateResource.setListComPrefDgt(List.of(1));
        refPermissionsSaveOrUpdateResource.setTechId(1L);
        refPermissionsSaveOrUpdateResource.setSiteModification(TEST);
        return refPermissionsSaveOrUpdateResource;
    }
    private RefPermissions mockRefPermissions()
    {
        RefPermissions refPermissions = new RefPermissions();
        refPermissions.setDateCreation(new Date());
        refPermissions.setDateModification(new Date());
        refPermissions.setSiteCreation(TEST);
        refPermissions.setSignatureCreation(TEST);
        refPermissions.setSiteModification(TEST);
        refPermissions.setRefPermissionsId(null);

        return refPermissions;
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
