package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefComPrefTypeDTO;
import com.afklm.cati.common.entity.RefComPrefType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefTypeController;
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
public class RefComPrefTypeControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefComPrefTypeController refComPrefTypeController;
    @Mock
    private RefComPrefTypeService refComPrefTypeService;

    @Test
    @DisplayName("Unit test for Ref communication preferences  type collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefType mockRefComPrefType = this.mockRefComPrefType();
        // when -  action or the behaviour that we are going test
        when(refComPrefTypeService.getAllRefComPrefType()).thenReturn(List.of(mockRefComPrefType));
        List<RefComPrefType> refComPrefTypes = refComPrefTypeController.collectionList(request);
        assertThat(refComPrefTypes.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences type")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefComPrefType mockRefComPrefType = this.mockRefComPrefType();
        RefComPrefTypeDTO mockRefComPrefTypeDTO = this.mockRefComPrefTypeDTO();
        // when -  action or the behaviour that we are going test
        when(refComPrefTypeService.getRefComPrefType(mockRefComPrefType.getCodeType())).thenReturn(Optional.of(mockRefComPrefType));
        refComPrefTypeController.collectionAdd(mockRefComPrefTypeDTO,mockAuthenticatedUserDTO,request,response);
        verify(refComPrefTypeService, times(1)).addRefComPrefType(mockRefComPrefTypeDTO,mockAuthenticatedUserDTO.getUsername());

    }


    @Test
    @DisplayName("Unit test to get Ref communication preferences type")
    public void testRefComPrefTypeGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefType mockRefComPrefType = this.mockRefComPrefType();
        when(refComPrefTypeService.getRefComPrefType(mockRefComPrefType.getCodeType())).thenReturn(Optional.of(mockRefComPrefType));
        RefComPrefType refComPrefType = refComPrefTypeController.refComPrefTypeGet(mockRefComPrefType.getCodeType(),request);
        assertThat(refComPrefType).isNotNull();
    }


    @Test
    @DisplayName("Unit test to delete Ref communication preferences type")
    public void testRefComPrefDelete() throws JrafDaoException, CatiCommonsException {
        RefComPrefType mockRefComPrefType = this.mockRefComPrefType();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        when(refComPrefTypeService.getRefComPrefType(mockRefComPrefType.getCodeType())).thenReturn(Optional.of(mockRefComPrefType));
        refComPrefTypeController.refComPrefTypeDelete(mockAuthenticatedUserDTO,mockRefComPrefType.getCodeType());
        verify(refComPrefTypeService, times(1)).removeRefComPrefType(mockRefComPrefType.getCodeType(),TEST);
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

    private RefComPrefTypeDTO mockRefComPrefTypeDTO()
    {
        String code = "T";
        RefComPrefTypeDTO refComPrefTypeDTO = new RefComPrefTypeDTO();
        refComPrefTypeDTO.setCodeType(code);
        refComPrefTypeDTO.setLibelleType(TEST);
        refComPrefTypeDTO.setLibelleTypeEN(TEST);

        Date date = new Date();
        refComPrefTypeDTO.setDateCreation(date);
        refComPrefTypeDTO.setDateModification(date);
        refComPrefTypeDTO.setSignatureCreation(TEST);
        refComPrefTypeDTO.setSignatureModification(TEST);
        refComPrefTypeDTO.setSiteCreation(TEST);
        refComPrefTypeDTO.setSiteModification(TEST);
        return refComPrefTypeDTO;
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
