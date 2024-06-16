package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefComPrefDomainDTO;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefDomainController;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RefComPrefDomainControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefComPrefDomainController refComPrefDomainController;
    @Mock
    private RefComPrefDomainService refComPrefDomainService;


    @Test
    @DisplayName("Unit test for Ref communication preferences  DGT collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefDomain mockRefComPrefDomain = this.mockRefComPrefDomain();
        // when -  action or the behaviour that we are going test
        when(refComPrefDomainService.getAllRefComPrefDomain()).thenReturn(List.of(mockRefComPrefDomain));
        List<RefComPrefDomain> refComPrefDomains = refComPrefDomainController.collectionList(request);
        assertThat(refComPrefDomains.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences domain")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefComPrefDomainDTO refComPrefDomainDTO = this.mockRefComPrefDomainDTO();
        // when -  action or the behaviour that we are going test
        refComPrefDomainController.collectionAdd(mockAuthenticatedUserDTO,refComPrefDomainDTO,request,response);
        verify(refComPrefDomainService, times(1)).addRefComPrefDomain(refComPrefDomainDTO,mockAuthenticatedUserDTO.getUsername());

    }

    @Test
    @DisplayName("Unit test to get Ref communication preferences domain")
    public void testRefComPrefDomainGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefDomainDTO refComPrefDomainDTO = this.mockRefComPrefDomainDTO();
        RefComPrefDomain mockRefComPrefDomain = this.mockRefComPrefDomain();
        when(refComPrefDomainService.getRefComPrefDomain(refComPrefDomainDTO.getCodeDomain())).thenReturn(Optional.of(mockRefComPrefDomain));
        RefComPrefDomain refComPrefDomain = refComPrefDomainController.refComPrefDomainGet(refComPrefDomainDTO.getCodeDomain(),request);
        assertThat(refComPrefDomain).isNotNull();
    }

    @Test
    @DisplayName("Unit test to delete Ref communication preferences domain")
    public void testRefComPrefDomainDelete() throws JrafDaoException, CatiCommonsException {
        RefComPrefDomain mockRefComPrefDomain = this.mockRefComPrefDomain();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        when(refComPrefDomainService.getRefComPrefDomain(mockRefComPrefDomain.getCodeDomain())).thenReturn(Optional.of(mockRefComPrefDomain));
        refComPrefDomainController.refComPrefDelete(mockAuthenticatedUserDTO,mockRefComPrefDomain.getCodeDomain());
        verify(refComPrefDomainService, times(1)).removeRefComPrefDomain(mockRefComPrefDomain.getCodeDomain(),TEST);

    }

    private RefComPrefDomainDTO mockRefComPrefDomainDTO()
    {
        String code = "T";
        RefComPrefDomainDTO refComPrefDomainDTO = new RefComPrefDomainDTO();
        refComPrefDomainDTO.setCodeDomain(code);
        refComPrefDomainDTO.setLibelleDomain(TEST);
        refComPrefDomainDTO.setLibelleDomainEN(TEST);

        Date date = new Date();
        refComPrefDomainDTO.setDateCreation(date);
        refComPrefDomainDTO.setDateModification(date);
        refComPrefDomainDTO.setSignatureCreation(TEST);
        refComPrefDomainDTO.setSignatureModification(TEST);
        refComPrefDomainDTO.setSiteCreation(TEST);
        refComPrefDomainDTO.setSiteModification(TEST);
        return refComPrefDomainDTO;
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
