package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefPermissionsQuestionDTO;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefPermissionsQuestionController;
import com.afklm.cati.common.spring.rest.resources.RefPermissionsQuestionResource;
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
public class RefPermissionsQuestionControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefPermissionsQuestionController refPermissionsQuestionController;
    @Mock
    private RefPermissionsQuestionService refPermissionsQuestionService;
    @Mock
    private Mapper dozerBeanMapper;



    @Test
    @DisplayName("Unit test for Ref permissions question")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefPermissionsQuestionResource mockPermissionsQuestionResource = this.mockPermissionsQuestionResource();
        RefPermissionsQuestion mockRefPermissionsQuestion = this.mockRefPermissionsQuestion();
        // when -  action or the behaviour that we are going test
        when(refPermissionsQuestionService.getAllRefPermissionsQuestion()).thenReturn(List.of(mockRefPermissionsQuestion));
        when(dozerBeanMapper.map(mockRefPermissionsQuestion, RefPermissionsQuestionResource.class)).thenReturn(mockPermissionsQuestionResource);
        when(refPermissionsQuestionService.countRefPermissionsByPermissionsQuestion(mockRefPermissionsQuestion)).thenReturn(1L);

        List<RefPermissionsQuestionResource> refPermissionsQuestionResources = refPermissionsQuestionController.collectionList(request);
        assertThat(refPermissionsQuestionResources.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Unit test to add collection of Ref add permissions question")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefPermissionsQuestionDTO mockRefPermissionsQuestionDTO  =  this.mockRefPermissionsQuestionDTO();

        refPermissionsQuestionController.collectionAdd(mockRefPermissionsQuestionDTO,mockAuthenticatedUserDTO,request,response);
        verify(refPermissionsQuestionService, times(1)).addRefPermissionsQuestion(mockRefPermissionsQuestionDTO,mockAuthenticatedUserDTO.getUsername());

    }


    @Test
    @DisplayName("Unit test to get Ref permissions questions")
    public void testRefPermissionsQuestionGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefPermissionsQuestion mockRefPermissionsQuestion = this.mockRefPermissionsQuestion();
        when(refPermissionsQuestionService.getRefPermissionsQuestion(mockRefPermissionsQuestion.getId())).thenReturn(Optional.of(mockRefPermissionsQuestion));
        RefPermissionsQuestion refPermissionsQuestion = refPermissionsQuestionController.refPermissionsQuestionGet(mockRefPermissionsQuestion.getId(),request);
        assertThat(refPermissionsQuestion).isNotNull();
    }

    @Test
    @DisplayName("Unit test to get Ref permissions questions deletion")
    public void testRefPermissionsQuestionsDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        when(refPermissionsQuestionService.countRefPermissionsByPermissionsQuestionId(1)).thenReturn(0L);
        refPermissionsQuestionController.refComPrefDelete(mockAuthenticatedUserDTO,1);
        verify(refPermissionsQuestionService, times(1)).removeRefPermissionsQuestion(1,mockAuthenticatedUserDTO.getUsername());

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
    private RefPermissionsQuestionDTO mockRefPermissionsQuestionDTO()
    {
        RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
        refPermissionsQuestionDTO.setQuestion(TEST);
        refPermissionsQuestionDTO.setQuestionEN(TEST);
        refPermissionsQuestionDTO.setDateCreation(new Date());
        refPermissionsQuestionDTO.setName(TEST);
        refPermissionsQuestionDTO.setId(1);
        refPermissionsQuestionDTO.setDateCreation(new Date());
        refPermissionsQuestionDTO.setDateModification(new Date());
        refPermissionsQuestionDTO.setSiteCreation(TEST);
        refPermissionsQuestionDTO.setSignatureCreation(TEST);
        refPermissionsQuestionDTO.setSiteModification(TEST);
        return refPermissionsQuestionDTO;
    }
    private RefPermissionsQuestionResource mockPermissionsQuestionResource()
    {
        RefPermissionsQuestionResource refPermissionsQuestionResource = new RefPermissionsQuestionResource();

        refPermissionsQuestionResource.setName(TEST);
        refPermissionsQuestionResource.setQuestion(TEST);
        refPermissionsQuestionResource.setQuestionEN(TEST);
        Date date = new Date();
        refPermissionsQuestionResource.setDateCreation(date);
        refPermissionsQuestionResource.setDateModification(date);
        refPermissionsQuestionResource.setSignatureCreation(TEST);
        refPermissionsQuestionResource.setSignatureModification(TEST);
        refPermissionsQuestionResource.setSiteCreation(TEST);
        refPermissionsQuestionResource.setSiteModification(TEST);

        return refPermissionsQuestionResource;
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
