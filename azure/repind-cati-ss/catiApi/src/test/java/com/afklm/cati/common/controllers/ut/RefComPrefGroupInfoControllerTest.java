package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefGroupInfoController;
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
public class RefComPrefGroupInfoControllerTest {

    private static final String TEST = "Test";
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    private static final String MODIFICATION_SITE = "KLM";
    @InjectMocks
    private RefComPrefGroupInfoController refComPrefGroupInfoController;

    @Mock
    private Mapper dozerBeanMapper;
    @Mock
    private RefGroupProductService refGroupProductService;
    @Mock
    private RefComPrefGroupInfoService refComPrefGroupInfoService;


    @Test
    @DisplayName("Unit test for Ref communication preferences  group collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefGroupInfo mockRefComPrefGroupInfo = this.mockRefComPrefGroupInfo();
        RefComPrefGroupInfoResource mockRefComPrefGroupInfoResource = this.mockRefComPrefGroupInfoResource();
        // when -  action or the behaviour that we are going test
        when(refComPrefGroupInfoService.getAllRefComPrefGroupInfo()).thenReturn(List.of(mockRefComPrefGroupInfo));
        when(dozerBeanMapper.map(mockRefComPrefGroupInfo,RefComPrefGroupInfoResource.class)).thenReturn(mockRefComPrefGroupInfoResource);
        List<RefComPrefGroupInfoResource> refComPrefGroupInfoResource = refComPrefGroupInfoController.collectionList(request);
        assertThat(refComPrefGroupInfoResource.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences group info")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefComPrefGroupInfo mockRefComPrefGroupInfo = this.mockMinRefComPrefGroup();
        RefComPrefGroupInfoResource mockRefComPrefGroupInfoResource = this.mockRefComPrefGroupInfoResource();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        // when -  action or the behaviour that we are going test
        refComPrefGroupInfoController.collectionAdd(mockRefComPrefGroupInfoResource,mockAuthenticatedUserDTO,request,response);
        verify(refComPrefGroupInfoService, times(1)).addRefComPrefGroupInfo(mockRefComPrefGroupInfo, mockAuthenticatedUserDTO.getUsername());

    }

    @Test
    @DisplayName("Unit test to get Ref communication preferences group")
    public void testRefComPrefGroupInfoGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefGroupInfo mockRefComPrefGroupInfo = this.mockRefComPrefGroupInfo();
        when( refComPrefGroupInfoService.getRefComPrefGroupInfoCode(mockRefComPrefGroupInfo.getId())).thenReturn(Optional.of(mockRefComPrefGroupInfo));
        RefComPrefGroupInfo requestBo = refComPrefGroupInfoController.RefComPrefGroupInfoGet(mockRefComPrefGroupInfo.getId(),request);
        assertThat(requestBo).isNotNull();
    }

    @Test
    @DisplayName("Unit test to delete Ref communication preferences DGT")
    public void testRefComPrefDelete() throws JrafDaoException, CatiCommonsException {
        RefComPrefGroupInfo mockRefComPrefGroupInfo = this.mockRefComPrefGroupInfo();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        refComPrefGroupInfoController.refComPrefDelete(mockAuthenticatedUserDTO,mockRefComPrefGroupInfo.getId());
        verify(refComPrefGroupInfoService, times(1)).countRefComPrefGroupByRefComPrefGroupInfo(mockRefComPrefGroupInfo.getId());
        verify(refGroupProductService, times(1)).countRefProductComPrefGroupByGroupInfoId(mockRefComPrefGroupInfo.getId());
        verify(refComPrefGroupInfoService, times(1)).removeRefComPrefGroupInfo(mockRefComPrefGroupInfo.getId(),mockAuthenticatedUserDTO.getUsername());

    }


    private RefComPrefGroupInfo mockMinRefComPrefGroup()
    {
        RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
        refComPrefGroupInfo.setCode(TEST);
        refComPrefGroupInfo.setLibelleEN(TEST);
        refComPrefGroupInfo.setLibelleFR(TEST);
        refComPrefGroupInfo.setMandatoryOption(TEST);
        refComPrefGroupInfo.setDefaultOption(TEST);

        return  refComPrefGroupInfo;
    }


    RefComPrefGroupInfo mockRefComPrefGroupInfo()
    {
        RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
        Date date = new Date();
        refComPrefGroupInfo.setId(1);
        refComPrefGroupInfo.setCode(TEST);
        refComPrefGroupInfo.setDateCreation(date);
        refComPrefGroupInfo.setLibelleEN(TEST);
        refComPrefGroupInfo.setDefaultOption(TEST);
        refComPrefGroupInfo.setDateModification(date);
        refComPrefGroupInfo.setLibelleFR(TEST);
        refComPrefGroupInfo.setMandatoryOption(TEST);
        refComPrefGroupInfo.setDefaultOption(TEST);
        refComPrefGroupInfo.setSiteCreation(MODIFICATION_SITE);
        refComPrefGroupInfo.setSiteModification(MODIFICATION_SITE);
        refComPrefGroupInfo.setSignatureModification(SIGNATURE_MODIFICATION);
        refComPrefGroupInfo.setSignatureCreation(SIGNATURE_MODIFICATION);

        return refComPrefGroupInfo;

    }

    private RefComPrefGroupInfoResource mockRefComPrefGroupInfoResource() {

        RefComPrefGroupInfoResource refComPrefGroupInfoResource = new RefComPrefGroupInfoResource();
        Date date = new Date();
        refComPrefGroupInfoResource.setId(1);
        refComPrefGroupInfoResource.setCode(TEST);
        refComPrefGroupInfoResource.setDateCreation(date);
        refComPrefGroupInfoResource.setLibelleEN(TEST);
        refComPrefGroupInfoResource.setDefaultOption(TEST);
        refComPrefGroupInfoResource.setDateModification(date);
        refComPrefGroupInfoResource.setLibelleFR(TEST);
        refComPrefGroupInfoResource.setMandatoryOption(TEST);
        refComPrefGroupInfoResource.setSiteCreation(MODIFICATION_SITE);
        refComPrefGroupInfoResource.setSiteModification(MODIFICATION_SITE);
        refComPrefGroupInfoResource.setSignatureModification(SIGNATURE_MODIFICATION);
        refComPrefGroupInfoResource.setSignatureCreation(SIGNATURE_MODIFICATION);
        refComPrefGroupInfoResource.setNbCompref(1L);
        refComPrefGroupInfoResource.setNbProduct(1L);
        return refComPrefGroupInfoResource;

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
