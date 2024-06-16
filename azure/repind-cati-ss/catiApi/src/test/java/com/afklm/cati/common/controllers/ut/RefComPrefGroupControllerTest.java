package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefGroupController;
import com.afklm.cati.common.spring.rest.resources.*;
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
public class RefComPrefGroupControllerTest {

    private static final String TEST = "Test";
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    private static final String MODIFICATION_SITE = "KLM";
    @InjectMocks
    private RefComPrefGroupController refComPrefGroupController;

    @Mock
    private Mapper dozerBeanMapper;
    @Mock
    private RefComPrefGroupService refComPrefGroupService;
    @Mock
    private RefComPrefGroupInfoService refComPrefGroupInfoService;


    @Test
    @DisplayName("Unit test for Ref communication preferences  group collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefGroup mockRefComPrefGroup = this.mockRefComPrefGroup();
        RefComPrefGroupResource mockRefComPrefGroupResource = this.mockRefComPrefGroupResource();
        // when -  action or the behaviour that we are going test
        when(refComPrefGroupService.getAllRefComPrefGroup()).thenReturn(List.of(mockRefComPrefGroup));
        when(dozerBeanMapper.map(mockRefComPrefGroup,RefComPrefGroupResource.class)).thenReturn(mockRefComPrefGroupResource);
        List<RefComPrefGroupResource> refComPrefGroupResource = refComPrefGroupController.collectionList(request);
        assertThat(refComPrefGroupResource.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences group")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefComPrefGroup mockRefComPrefGroup = this.mockRefComPrefGroup();
        RefGroupsSaveOrUpdateResource  mockRefGroupSaveOrUpdateResource = this.mockRefGroupSaveOrUpdateResource();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        // when -  action or the behaviour that we are going test
        when(dozerBeanMapper.map(mockRefGroupSaveOrUpdateResource,RefComPrefGroup.class)).thenReturn(mockRefComPrefGroup);

        refComPrefGroupController.collectionAdd(mockRefGroupSaveOrUpdateResource,mockAuthenticatedUserDTO,request,response);
        verify(refComPrefGroupService, times(1)).addRefComPrefGroup(mockRefComPrefGroup, mockRefGroupSaveOrUpdateResource.getRefGroupsInfoId(), mockRefGroupSaveOrUpdateResource.getListComPrefDgt(), mockAuthenticatedUserDTO.getUsername());

    }

    @Test
    @DisplayName("Unit test to get Ref communication preferences group")
    public void testRefComPrefGroupGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefGroupInfo mockRefComPrefGroupInfo = this.mockRefComPrefGroupInfo();

        when(refComPrefGroupInfoService.getRefComPrefGroupInfo(mockRefComPrefGroupInfo.getId())).thenReturn(Optional.of(mockRefComPrefGroupInfo));
        when(refComPrefGroupService.getRefComPrefDgtByRefComPrefGroupInfo(mockRefComPrefGroupInfo)).thenReturn(List.of(1));

        List<Integer> requestBo = refComPrefGroupController.refComPrefGroupGetRefComPrefDgt(mockRefComPrefGroupInfo.getId(),request);
        assertThat(requestBo.size()).isEqualTo(1);
    }


private RefComPrefGroup mockRefComPrefGroup()
{
    RefComPrefGroup refComPrefGroup = new RefComPrefGroup();
    Date date = new Date();
    refComPrefGroup.setDateCreation(date);
    refComPrefGroup.setDateModification(date);
    refComPrefGroup.setSiteCreation(TEST);
    refComPrefGroup.setSignatureCreation(TEST);
    refComPrefGroup.setSiteModification(TEST);
    refComPrefGroup.setRefComPrefGroupId(null);
    return  refComPrefGroup;
}

    private RefComPrefGroupResource mockRefComPrefGroupResource()
    {
        RefComPrefGroupResource refComPrefGroupResource = new RefComPrefGroupResource();
        RefComPrefGroupIdResource refComPrefGroupIdResource= mockRefComPrefGroupIdResource();
        refComPrefGroupResource.setRefComPrefGroupId(refComPrefGroupIdResource);
        refComPrefGroupResource.setTechId(1L);
        return  refComPrefGroupResource;
    }
    private RefGroupsSaveOrUpdateResource mockRefGroupSaveOrUpdateResource() {

        RefGroupsSaveOrUpdateResource refGroupSaveOrUpdateResource = new RefGroupsSaveOrUpdateResource();

        Date date = new Date();
        refGroupSaveOrUpdateResource.setDateCreation(date);
        refGroupSaveOrUpdateResource.setDateModification(date);
        refGroupSaveOrUpdateResource.setSignatureModification(SIGNATURE_MODIFICATION);
        refGroupSaveOrUpdateResource.setSignatureCreation(SIGNATURE_MODIFICATION);
        refGroupSaveOrUpdateResource.setSiteCreation(MODIFICATION_SITE);
        refGroupSaveOrUpdateResource.setSiteModification(MODIFICATION_SITE);
        refGroupSaveOrUpdateResource.setRefGroupsInfoId(1);
        refGroupSaveOrUpdateResource.setTechId(1L);
        refGroupSaveOrUpdateResource.setListComPrefDgt(List.of(1));
        return refGroupSaveOrUpdateResource;

    }

    RefComPrefGroupInfo mockRefComPrefGroupInfo()
    {
        RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
        Date date = new Date();
        refComPrefGroupInfo.setId(1);
        refComPrefGroupInfo.setCode(TEST);
        refComPrefGroupInfo.setDateCreation(date);
        refComPrefGroupInfo.setDateModification(date);
        refComPrefGroupInfo.setLibelleEN(TEST);
        refComPrefGroupInfo.setDefaultOption(TEST);
        refComPrefGroupInfo.setLibelleFR(TEST);
        refComPrefGroupInfo.setMandatoryOption(TEST);
        refComPrefGroupInfo.setSignatureCreation(TEST);
       return refComPrefGroupInfo;

    }

    private RefComPrefGroupIdResource mockRefComPrefGroupIdResource()
    {
        RefComPrefGroupInfo refComPrefGroupInfo = mockRefComPrefGroupInfo();
        RefComPrefGroupIdResource refComPrefGroupIdResource = new RefComPrefGroupIdResource();
        refComPrefGroupIdResource.setRefComPrefGroupInfo(refComPrefGroupInfo);
        refComPrefGroupIdResource.setTechId(1L);
        return refComPrefGroupIdResource;
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
