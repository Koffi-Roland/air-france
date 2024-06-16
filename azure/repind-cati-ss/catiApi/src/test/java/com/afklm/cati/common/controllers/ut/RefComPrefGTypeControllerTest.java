package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefComPrefGTypeDTO;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefGTypeController;
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
public class RefComPrefGTypeControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefComPrefGTypeController refComPrefGTypeController;
    @Mock
    private RefComPrefGTypeService refComPrefGTypeService;


    @Test
    @DisplayName("Unit test for Ref communication preferences  DGT collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefGType mockRefComPrefGType = this.mockRefComPrefGType();
        // when -  action or the behaviour that we are going test
        when(refComPrefGTypeService.getAllRefComPrefGType()).thenReturn(List.of(mockRefComPrefGType));
        List<RefComPrefGType> refComPrefGTypes = refComPrefGTypeController.collectionList(request);
        assertThat(refComPrefGTypes.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Unit test to add collection of Ref communication preferences G type")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefComPrefGTypeDTO mockRefComPrefGTypeDTO = this.mockRefComPrefGTypeDTO();
        refComPrefGTypeController.collectionAdd(mockRefComPrefGTypeDTO,mockAuthenticatedUserDTO,request,response);
        verify(refComPrefGTypeService, times(1)).addRefComPrefGType(mockRefComPrefGTypeDTO,mockAuthenticatedUserDTO.getUsername());

    }


    @Test
    @DisplayName("Unit test to get Ref communication preferences G type")
    public void testRefComPrefDgtGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefGType mockRefComPrefGType = this.mockRefComPrefGType();
        when(refComPrefGTypeService.getRefComPrefGType(mockRefComPrefGType.getCodeGType())).thenReturn(Optional.of(mockRefComPrefGType));
        RefComPrefGType refComPrefGType = refComPrefGTypeController.refComPrefTypeGet(mockRefComPrefGType.getCodeGType(),request);
        assertThat(refComPrefGType).isNotNull();
    }


    @Test
    @DisplayName("Unit test to delete Ref communication preferences G type")
    public void testRefComPrefDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefGType mockRefComPrefGType = this.mockRefComPrefGType();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        when(refComPrefGTypeService.getRefComPrefGType(mockRefComPrefGType.getCodeGType())).thenReturn(Optional.of(mockRefComPrefGType));
        refComPrefGTypeController.refComPrefGTypeDelete(mockAuthenticatedUserDTO,mockRefComPrefGType.getCodeGType());
        verify(refComPrefGTypeService, times(1)).removeRefComPrefGType(mockRefComPrefGType.getCodeGType(),mockAuthenticatedUserDTO.getUsername());

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
    RefComPrefGTypeDTO mockRefComPrefGTypeDTO()
    {
        String code = "T";
        RefComPrefGTypeDTO refComPrefGTypeDTO = new RefComPrefGTypeDTO();
        refComPrefGTypeDTO.setCodeGType(code);
        refComPrefGTypeDTO.setLibelleGType(TEST);
        refComPrefGTypeDTO.setLibelleGTypeEN(TEST);

        Date date = new Date();
        refComPrefGTypeDTO.setDateCreation(date);
        refComPrefGTypeDTO.setDateModification(date);
        refComPrefGTypeDTO.setSignatureCreation(TEST);
        refComPrefGTypeDTO.setSignatureModification(TEST);
        refComPrefGTypeDTO.setSiteCreation(TEST);
        refComPrefGTypeDTO.setSiteModification(TEST);
        return refComPrefGTypeDTO;
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
