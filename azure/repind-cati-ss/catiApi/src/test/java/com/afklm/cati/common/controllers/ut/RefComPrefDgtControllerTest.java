package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefDgtController;
import com.afklm.cati.common.spring.rest.resources.RefComPrefDgtResource;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RefComPrefDgtControllerTest {

    private static final Integer REF_COM_PREF_DGT_ID  = 1;
    private static final String COM_GROUPE_TYPE = "N";
    private static final String COM_TYPE = "AF";
    private static final String DOMAIN = "AF";
    private static final String DESCRIPTION = "Description";
    private static final String TEST = "Test";
    @InjectMocks
    private RefComPrefDgtController refComPrefDgtController;

    @Mock
    private RefComPrefDgtService refComPrefDgtService;

    @Mock
    private Mapper dozerBeanMapper;

    @Mock
    private RefComPrefTypeService refComPrefTypeService;

    @Mock
    private RefComPrefDomainService refComPrefDomainService;

    @Mock
    private RefComPrefGTypeService refComPrefGTypeService;



    @Test
    @DisplayName("Unit test for Ref communication preferences  DGT collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefDgtResource mockRefComPrefDgtResource = this.mockRefComPrefDgtResource();
        RefComPrefDgt  mockRefComPrefDgt = this.mockRefComPrefDgt();
        // when -  action or the behaviour that we are going test
        when(refComPrefDgtService.getAllRefComPrefDgt()).thenReturn(List.of(mockRefComPrefDgt));
        when(dozerBeanMapper.map(mockRefComPrefDgt,RefComPrefDgtResource.class)).thenReturn(mockRefComPrefDgtResource);
        List<RefComPrefDgtResource> refComPrefDgtResources = refComPrefDgtController.collectionList(request);
        assertThat(refComPrefDgtResources.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences DGT")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefComPrefDgtResource mockRefComPrefDgtResource = this.mockRefComPrefDgtResource();
        RefComPrefGType mockRefComPrefGType = this.mockRefComPrefGType();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefComPrefType mockRefComPrefType = this.mockRefComPrefType();
        RefComPrefDomain mockRefComPrefDomain =  this.mockRefComPrefDomain();
        RefComPrefDgt mockRefComPrefDgt = this.mockRefComPrefDgt();
        // when -  action or the behaviour that we are going test
        when(refComPrefTypeService.getRefComPrefType(mockRefComPrefDgtResource.getType())).thenReturn(Optional.of(mockRefComPrefType));
        when(refComPrefGTypeService.getRefComPrefGType(mockRefComPrefDgtResource.getGroupType())).thenReturn(Optional.of(mockRefComPrefGType));
        when(refComPrefDomainService.getRefComPrefDomain(mockRefComPrefDgtResource.getDomain())).thenReturn(Optional.of(mockRefComPrefDomain));
        when(dozerBeanMapper.map(mockRefComPrefDgtResource,RefComPrefDgt.class)).thenReturn(mockRefComPrefDgt);

        refComPrefDgtController.collectionAdd(mockRefComPrefDgtResource,mockAuthenticatedUserDTO,request,response);
        verify(refComPrefTypeService, times(1)).getRefComPrefType(any());
        verify(refComPrefGTypeService, times(1)).getRefComPrefGType(any());
        verify(refComPrefDomainService, times(1)).getRefComPrefDomain(any());

    }


    @Test
    @DisplayName("Unit test to get Ref communication preferences")
    public void testRefComPrefDgtGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefDgtResource mockRefComPrefDgtResource = this.mockRefComPrefDgtResource();
        RefComPrefDgt  refComPrefDgt = this.mockRefComPrefDgt();

        when(refComPrefDgtService.getRefComPrefDgt(mockRefComPrefDgtResource.getRefComPrefDgtId())).thenReturn(Optional.of(refComPrefDgt));
        when(dozerBeanMapper.map(refComPrefDgt,RefComPrefDgtResource.class)).thenReturn(mockRefComPrefDgtResource);

        RefComPrefDgtResource refComPrefDgtResource = refComPrefDgtController.refComPrefDgtGet(refComPrefDgt.getRefComPrefDgtId(),request);
        assertThat(refComPrefDgtResource).isNotNull();
    }


    @Test
    @DisplayName("Unit test to delete Ref communication preferences DGT")
    public void testRefComPrefDelete() throws JrafDaoException, CatiCommonsException {
        RefComPrefDgt  refComPrefDgt = this.mockRefComPrefDgt();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        refComPrefDgtController.refComPrefDgtDelete(mockAuthenticatedUserDTO,refComPrefDgt.getRefComPrefDgtId());
        verify(refComPrefDgtService, times(1)).removeRefComPrefDgt(refComPrefDgt.getRefComPrefDgtId(),mockAuthenticatedUserDTO.getUsername());

    }


    private RefComPrefDgt mockRefComPrefDgt() {

        RefComPrefDgt refComPrefDgt = new RefComPrefDgt();
        refComPrefDgt.setRefComPrefDgtId(1);
        refComPrefDgt.setDescription(TEST);
        refComPrefDgt.setGroupType(null);
        refComPrefDgt.setType(null);
        refComPrefDgt.setDomain(null);
        Date date = new Date();
        refComPrefDgt.setDateCreation(date);
        refComPrefDgt.setDateModification(date);
        refComPrefDgt.setSignatureCreation(TEST);
        refComPrefDgt.setSignatureModification(TEST);
        refComPrefDgt.setSiteCreation(TEST);
        refComPrefDgt.setSiteModification(TEST);

        return refComPrefDgt;
    }

    private RefComPrefType mockRefComPrefType()
    {
        String code = "T";
        RefComPrefType refComPrefType = new RefComPrefType();
        refComPrefType.setCodeType(code);
        refComPrefType.setLibelleType(TEST);
        refComPrefType.setLibelleTypeEN(TEST);

        Date date = new Date();
        refComPrefType.setDateCreation(date);
        refComPrefType.setDateModification(date);
        refComPrefType.setSignatureCreation(TEST);
        refComPrefType.setSignatureModification(TEST);
        refComPrefType.setSiteCreation(TEST);
        refComPrefType.setSiteModification(TEST);
        return refComPrefType;
    }

    private RefComPrefGType mockRefComPrefGType ()
    {
        String code = "T";
        RefComPrefGType refComPrefGType = new RefComPrefGType();
        refComPrefGType.setCodeGType(code);
        refComPrefGType.setLibelleGType(TEST);
        refComPrefGType.setLibelleGTypeEN(TEST);

        Date date = new Date();
        refComPrefGType.setDateCreation(date);
        refComPrefGType.setDateModification(date);
        refComPrefGType.setSignatureCreation(TEST);
        refComPrefGType.setSignatureModification(TEST);
        refComPrefGType.setSiteCreation(TEST);
        refComPrefGType.setSiteModification(TEST);
        return refComPrefGType;
    }

    private RefComPrefDomain mockRefComPrefDomain()
    {
        String code = "T";
        RefComPrefDomain refComPrefDomain = new RefComPrefDomain();
        refComPrefDomain.setCodeDomain(code);
        refComPrefDomain.setLibelleDomain(TEST);
        refComPrefDomain.setLibelleDomainEN(TEST);

        Date date = new Date();
        refComPrefDomain.setDateCreation(date);
        refComPrefDomain.setDateModification(date);
        refComPrefDomain.setSignatureCreation(TEST);
        refComPrefDomain.setSignatureModification(TEST);
        refComPrefDomain.setSiteCreation(TEST);
        refComPrefDomain.setSiteModification(TEST);
        return refComPrefDomain;
    }
    private RefComPrefDgtResource mockRefComPrefDgtResource() {

        RefComPrefDgtResource refComPrefDgtResource = new RefComPrefDgtResource();
        refComPrefDgtResource.setRefComPrefDgtId(REF_COM_PREF_DGT_ID);
        refComPrefDgtResource.setDescription(DESCRIPTION);
        refComPrefDgtResource.setGroupType(COM_GROUPE_TYPE);
        refComPrefDgtResource.setType(COM_TYPE);
        refComPrefDgtResource.setDomain(DOMAIN);
        Date date = new Date();
        refComPrefDgtResource.setDateCreation(date);
        refComPrefDgtResource.setDateModification(date);
        refComPrefDgtResource.setSignatureCreation(TEST);
        refComPrefDgtResource.setSignatureModification(TEST);
        refComPrefDgtResource.setSiteCreation(TEST);
        refComPrefDgtResource.setSiteModification(TEST);

        return refComPrefDgtResource;
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
