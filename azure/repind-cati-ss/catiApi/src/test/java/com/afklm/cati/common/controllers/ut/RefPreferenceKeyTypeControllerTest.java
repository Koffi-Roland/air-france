package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefPreferenceKeyTypeController;
import com.afklm.cati.common.spring.rest.resources.RefPreferenceKeyTypeResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RefPreferenceKeyTypeControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefPreferenceKeyTypeController refPreferenceKeyTypeController;

    @Mock
    private RefPreferenceKeyTypeController mockRefPreferenceKeyTypeController;

    @Mock
    private RefPreferenceKeyTypeService refPreferenceKeyTypeService;

    @Test
    @DisplayName("Unit test for Ref preferences key")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefPreferenceKeyType mockRefPreferenceKeyType = this.mockRefPreferenceKeyType();
        // when -  action or the behaviour that we are going test
        when(refPreferenceKeyTypeService.getAllRefPreferenceKeyType()).thenReturn(List.of(mockRefPreferenceKeyType));
        List<RefPreferenceKeyType> refPreferenceKeyTypes = refPreferenceKeyTypeController.collectionList(request);
        assertThat(refPreferenceKeyTypes.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Unit test to add collection of Ref preferences key")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefPreferenceKeyTypeResource mockRefPreferenceKeyTypeResource = this.mockRefPreferenceKeyTypeResource();
        doReturn(new ResponseEntity<>(HttpStatus.OK)).when(mockRefPreferenceKeyTypeController).collectionAdd(mockRefPreferenceKeyTypeResource,request,response);
        ResponseEntity<?> mockResponse = mockRefPreferenceKeyTypeController.collectionAdd(mockRefPreferenceKeyTypeResource,request,response);
        assertEquals(mockResponse.getStatusCode(),HttpStatus.OK);

    }

    @Test
    @DisplayName("Unit test to get preferences key type")
    public void testRefPreferenceKeyTypeGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefPreferenceKeyType mockRefPreferenceKeyType = this.mockRefPreferenceKeyType();
        when(refPreferenceKeyTypeService.getRefPreferenceKeyType(mockRefPreferenceKeyType.getRefId())).thenReturn(Optional.of(mockRefPreferenceKeyType));
        RefPreferenceKeyType refPreferenceKeyType = refPreferenceKeyTypeController.refPreferenceKeyTypeGet(mockRefPreferenceKeyType.getRefId(),request);
        assertThat(refPreferenceKeyType).isNotNull();
    }


    @Test
    @DisplayName("Unit test to get Ref preferences key")
    public void testRefPermissionsQuestionsDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefPreferenceKeyType mockRefPreferenceKeyType = this.mockRefPreferenceKeyType();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        refPreferenceKeyTypeController.refPreferenceTypeDelete(mockAuthenticatedUserDTO,mockRefPreferenceKeyType.getRefId());
        verify(refPreferenceKeyTypeService, times(1)).removeRefPreferenceKeyType(1);

    }

   private RefPreferenceKeyType mockRefPreferenceKeyType()
   {
       RefPreferenceKeyType refPreferenceKeyType = new RefPreferenceKeyType();
       refPreferenceKeyType.setKey(TEST);
       refPreferenceKeyType.setDataType(TEST);
       refPreferenceKeyType.setCondition(TEST);
       refPreferenceKeyType.setMaxLength(1);
       refPreferenceKeyType.setMinLength(1);
       refPreferenceKeyType.setRefId(1);
       refPreferenceKeyType.setType(TEST);
       return refPreferenceKeyType;
   }
    private RefPreferenceKeyTypeResource mockRefPreferenceKeyTypeResource()
    {
        RefPreferenceKeyTypeResource refPreferenceKeyTypeResource = new RefPreferenceKeyTypeResource();
        refPreferenceKeyTypeResource.setKey(TEST);
        refPreferenceKeyTypeResource.setDataType(TEST);
        refPreferenceKeyTypeResource.setCondition(TEST);
        refPreferenceKeyTypeResource.setMaxLength(1);
        refPreferenceKeyTypeResource.setMinLength(1);
        refPreferenceKeyTypeResource.setListPreferenceType(List.of(TEST));
        refPreferenceKeyTypeResource.setRefId(1);
        refPreferenceKeyTypeResource.setType(TEST);

        return refPreferenceKeyTypeResource;
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
