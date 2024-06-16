package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefPcsFactorDTO;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.model.ModelRefPcsFactor;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefPcsFactorController;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RefPcsFactorControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefPcsFactorController refPcsFactorController;
    @Mock
    private RefPcsFactorService refPcsFactorService;

    @Test
    @DisplayName("Unit test for Ref PcsFactor collection")
    public void testRefPcsFactorList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ModelRefPcsFactor mockModelRefPcsFactor = this.mockModelRefPcsFactor();
        // when -  action or the behaviour that we are going test
        when(refPcsFactorService.getAllPcsFactor()).thenReturn(List.of(mockModelRefPcsFactor));
        ResponseEntity<?> response  = refPcsFactorController.refPcsFactorList(request);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    @Test
    @DisplayName("Unit test to add collection of Ref PcsFactor")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        RefPcsFactorDTO refPcsFactorDTO = mockRefPcsFactorDTO();
        refPcsFactorController.collectionAdd(refPcsFactorDTO,request,response);
        verify(refPcsFactorService, times(1)).addRefPcsFactor(refPcsFactorDTO);

    }


    @Test
    @DisplayName("Unit test to delete Ref Ref PcsFactor")
    public void testRefPcsFactorCodeDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefPcsFactor refPcsFactor  = this.mockRefPcsFactor();
        when(refPcsFactorService.getRefPcsFactor(refPcsFactor.getCode())).thenReturn(Optional.of(refPcsFactor));
        refPcsFactorController.refPcsFactorCodeDelete(mockAuthenticatedUserDTO,TEST);
        verify(refPcsFactorService, times(1)).removeRefPcsFactor(refPcsFactor.getCode());

    }

    private ModelRefPcsFactor mockModelRefPcsFactor()
    {
        ModelRefPcsFactor modelRefPcsFactor = new ModelRefPcsFactor();
        modelRefPcsFactor.setFactor(1);
        modelRefPcsFactor.setCode(TEST);
        modelRefPcsFactor.setLibelle(TEST);
        modelRefPcsFactor.setMaxPoints(1);
        return modelRefPcsFactor;
    }

    private RefPcsFactorDTO mockRefPcsFactorDTO()
    {
        RefPcsFactorDTO refPcsFactorDTO = new RefPcsFactorDTO();
        refPcsFactorDTO.setFactor(1);
        refPcsFactorDTO.setCode(TEST);
        refPcsFactorDTO.setLibelle(TEST);
        refPcsFactorDTO.setMaxPoints(1);
        return refPcsFactorDTO;
    }

    private RefPcsFactor mockRefPcsFactor()
    {
        RefPcsFactor refPcsFactor = new RefPcsFactor();
        refPcsFactor.setFactor(1);
        refPcsFactor.setCode(TEST);
        refPcsFactor.setLibelle(TEST);
        refPcsFactor.setMaxPoints(1);
        return refPcsFactor;
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
