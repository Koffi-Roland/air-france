package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.entity.RefComPref;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.entity.RefComPrefType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefController;
import com.afklm.cati.common.spring.rest.resources.RefComPrefResource;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RefComPrefControllerTest {
    private static final String COM_GROUPE_TYPE = "N";
    private static final String COM_TYPE = "AF";
    private static final String DOMAIN = "AF";
    private static final String DESCRIPTION = "Description";
    private static final String TEST = "Test";
    private static final String MARKET = "GB";
    private static final String FIELD = "Y";
    private static final String LANGUAGE = "FR";
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    private static final String MODIFICATION_SITE = "KLM";
    @InjectMocks
    private RefComPrefController refComPrefController;

    @Mock
    private Mapper dozerBeanMapper;

    @Mock
    private RefComPrefService refComPrefService;

    @Mock
    private RefComPrefTypeService refComPrefTypeService;

    @Mock
    private RefComPrefDomainService refComPrefDomainService;

    @Mock
    private RefComPrefGTypeService refComPrefGTypeService;


    @Test
    @DisplayName("Unit test for Ref communication preferences collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPref mockRefCompref = this.mockRefComPref();
        RefComPrefResource mockRefComPrefResource = this.mockRefComPrefResource();
        // when -  action or the behaviour that we are going test
        when(refComPrefService.getAllRefComPref()).thenReturn(List.of(mockRefCompref));
        when(dozerBeanMapper.map(mockRefCompref,RefComPrefResource.class)).thenReturn(mockRefComPrefResource);
        List<RefComPrefResource> refComPrefResources = refComPrefController.collectionList(request);
        assertAll(
                () -> assertThat(refComPrefResources.size()).isEqualTo(1),
                () -> assertEquals(refComPrefResources.get(0).getMarket(), MARKET),
                () -> assertEquals(refComPrefResources.get(0).getComType(), COM_TYPE),
                () -> assertEquals(refComPrefResources.get(0).getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refComPrefResources.get(0).getSiteCreation(), MODIFICATION_SITE),
                () -> assertEquals(refComPrefResources.get(0).getComGroupeType(), COM_GROUPE_TYPE),
                () -> assertEquals(refComPrefResources.get(0).getFieldN(),FIELD),
                () -> assertEquals(refComPrefResources.get(0).getFieldA(),FIELD),
                () -> assertEquals(refComPrefResources.get(0).getFieldT(),FIELD),
                () -> assertEquals(refComPrefResources.get(0).getDomain(), DOMAIN),
                () -> assertEquals(refComPrefResources.get(0).getDefaultLanguage1(), LANGUAGE),
                () -> assertEquals(refComPrefResources.get(0).getMandatoryOptin(), FIELD),
                () -> assertEquals(refComPrefResources.get(0).getDescription(), DESCRIPTION)

        );
    }

    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefComPref mockRefCompref = this.mockRefComPref();
        RefComPrefType mockRefComPrefType = this.mockRefComPrefType();
        RefComPrefDomain mockRefComPrefDomain =  this.mockRefComPrefDomain();
        RefComPrefGType mockRefComPrefGType = this.mockRefComPrefGType();
        RefComPrefResource mockRefComPrefResource = this.mockRefComPrefResource();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        // when -  action or the behaviour that we are going test
        when(dozerBeanMapper.map(mockRefComPrefResource,RefComPref.class)).thenReturn(mockRefCompref);
        when(refComPrefTypeService.getRefComPrefType(mockRefComPrefResource.getComType())).thenReturn(Optional.of(mockRefComPrefType));
        when(refComPrefGTypeService.getRefComPrefGType(mockRefComPrefResource.getComGroupeType())).thenReturn(Optional.of(mockRefComPrefGType));
        when(refComPrefDomainService.getRefComPrefDomain(mockRefComPrefResource.getDomain())).thenReturn(Optional.of(mockRefComPrefDomain));
        refComPrefController.collectionAdd(mockRefComPrefResource,mockAuthenticatedUserDTO,request,response);
        verify(refComPrefTypeService, times(1)).getRefComPrefType(any());
        verify(refComPrefGTypeService, times(1)).getRefComPrefGType(any());
        verify(refComPrefDomainService, times(1)).getRefComPrefDomain(any());

    }


    @Test
    @DisplayName("Unit test to get Ref communication preferences")
    public void testRefComPrefGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPref mockRefCompref = this.mockRefComPref();
        RefComPrefResource mockRefComPrefResource = this.mockRefComPrefResource();

        when(refComPrefService.getRefComPref(mockRefCompref.getRefComprefId())).thenReturn(Optional.of(mockRefCompref));
        when(dozerBeanMapper.map(mockRefCompref,RefComPrefResource.class)).thenReturn(mockRefComPrefResource);
        RefComPrefResource refComPrefResource = refComPrefController.refComPrefGet(mockRefCompref.getRefComprefId(),request);
        assertThat(refComPrefResource).isNotNull();
        assertAll(
                () -> assertThat(refComPrefResource).isNotNull(),
                () -> assertEquals(refComPrefResource.getMarket(), MARKET),
                () -> assertEquals(refComPrefResource.getComType(), COM_TYPE),
                () -> assertEquals(refComPrefResource.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refComPrefResource.getSiteCreation(), MODIFICATION_SITE),
                () -> assertEquals(refComPrefResource.getComGroupeType(), COM_GROUPE_TYPE),
                () -> assertEquals(refComPrefResource.getFieldN(),FIELD),
                () -> assertEquals(refComPrefResource.getFieldA(),FIELD),
                () -> assertEquals(refComPrefResource.getFieldT(),FIELD),
                () -> assertEquals(refComPrefResource.getDomain(), DOMAIN),
                () -> assertEquals(refComPrefResource.getDefaultLanguage1(), LANGUAGE),
                () -> assertEquals(refComPrefResource.getMandatoryOptin(), FIELD),
                () -> assertEquals(refComPrefResource.getDescription(), DESCRIPTION)

        );

    }


    @Test
    @DisplayName("Unit test to delete Ref communication preferences")
    public void testRefComPrefDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPref mockRefCompref = this.mockRefComPref();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        refComPrefController.refComPrefDelete(mockAuthenticatedUserDTO,mockRefCompref.getRefComprefId());
        verify(refComPrefService, times(1)).removeRefComPref(mockRefCompref.getRefComprefId(),mockAuthenticatedUserDTO.getUsername());

    }

    private RefComPref mockRefComPref() {

        RefComPref refComPref = new RefComPref();
        Date date = new Date();
        refComPref.setRefComprefId(1);
        refComPref.setMarket(MARKET);
        refComPref.setDefaultLanguage1(LANGUAGE);
        refComPref.setDescription(DESCRIPTION);
        refComPref.setFieldA(FIELD);
        refComPref.setFieldN(FIELD);
        refComPref.setFieldT(FIELD);
        refComPref.setMandatoryOptin(FIELD);
        refComPref.setDateCreation(date);
        refComPref.setDateModification(date);
        refComPref.setSignatureCreation(SIGNATURE_MODIFICATION);
        refComPref.setSignatureModification(SIGNATURE_MODIFICATION);
        refComPref.setSiteCreation(MODIFICATION_SITE);
        refComPref.setSiteModification(MODIFICATION_SITE);
        return refComPref;
    }

    private RefComPrefResource mockRefComPrefResource() {

        RefComPrefResource refComPrefResource = new RefComPrefResource();
        Date date = new Date();
        refComPrefResource.setMarket(MARKET);
        refComPrefResource.setDefaultLanguage1(LANGUAGE);
        refComPrefResource.setDescription(DESCRIPTION);
        refComPrefResource.setFieldA(FIELD);
        refComPrefResource.setFieldN(FIELD);
        refComPrefResource.setFieldT(FIELD);
        refComPrefResource.setMandatoryOptin(FIELD);
        refComPrefResource.setComGroupeType(COM_GROUPE_TYPE);
        refComPrefResource.setComType(COM_TYPE);
        refComPrefResource.setDomain(DOMAIN);
        refComPrefResource.setDateCreation(date);
        refComPrefResource.setDateModification(date);
        refComPrefResource.setSignatureCreation(SIGNATURE_MODIFICATION);
        refComPrefResource.setSignatureModification(SIGNATURE_MODIFICATION);
        refComPrefResource.setSiteCreation(MODIFICATION_SITE);
        refComPrefResource.setSiteModification(MODIFICATION_SITE);
        return refComPrefResource;
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
