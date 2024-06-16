package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefPcsScoreDTO;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.model.ModelRefPcsScore;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefPcsNonContractScoreController;
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
public class RefPcsNonContractScoreControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefPcsNonContractScoreController refPcsNonContractScoreController;
    @Mock
    private RefPcsScoreService refPcsScoreService;


    @Test
    @DisplayName("Unit test for Ref non contract score collection")
    public void testRefPcsNonContractScoreList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ModelRefPcsScore mockModelRefPcsScore = this.mockModelRefPcsScore();
        // when -  action or the behaviour that we are going test
        when(refPcsScoreService.getPcsScoreByFactorCode(TEST)).thenReturn(List.of(mockModelRefPcsScore));
        ResponseEntity<?> response  = refPcsNonContractScoreController.refPcsNonContractScoreList(request);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    @Test
    @DisplayName("Unit test to add collection of Ref non contract score")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefPcsScoreDTO refPcsScoreDTO = this.mockRefPcsScoreDTO();
        refPcsNonContractScoreController.collectionAdd(refPcsScoreDTO,request,response);
        verify(refPcsScoreService, times(1)).addRefPcsScore(refPcsScoreDTO);

    }


    @Test
    @DisplayName("Unit test to delete Ref non contract score")
    public void testRefPcsScoreDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefPcsScore refPcsScore = this.mockRefPcsScore();
        when(refPcsScoreService.getRefPcsScore(refPcsScore.getCode())).thenReturn(Optional.of(refPcsScore));
        refPcsNonContractScoreController.refPcsScoreCodeDelete(mockAuthenticatedUserDTO,TEST);
        verify(refPcsScoreService, times(1)).removeRefPcsScore(refPcsScore.getCode());

    }

    private ModelRefPcsScore mockModelRefPcsScore()
    {
        ModelRefPcsScore modelRefPcsScore = new ModelRefPcsScore();
        modelRefPcsScore.setCode(TEST);
        modelRefPcsScore.setLibelle(TEST);
        modelRefPcsScore.setCodeFactor(TEST);
        modelRefPcsScore.setScore(1);
        return modelRefPcsScore;
    }

    private RefPcsScore mockRefPcsScore()
    {
        RefPcsScore refPcsScore = new RefPcsScore();
        refPcsScore.setCode(TEST);
        refPcsScore.setLibelle(TEST);
        refPcsScore.setCodeFactor(TEST);
        refPcsScore.setScore(1);
        return refPcsScore;
    }

    private RefPcsScoreDTO mockRefPcsScoreDTO()
    {
        RefPcsScoreDTO refPcsScoreDTO = new RefPcsScoreDTO();
        refPcsScoreDTO.setCode(TEST);
        refPcsScoreDTO.setScore(1);
        refPcsScoreDTO.setCodeFactor(TEST);
        refPcsScoreDTO.setLibelle(TEST);
        return refPcsScoreDTO;
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
