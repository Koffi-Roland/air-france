package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefPcsScoreDTO;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.model.ModelRefPcsScore;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefPcsContractScoreController;
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
public class RefPcsContractScoreControllerTest {

    private static final String CODE_FACTOR = "C";

    private static final String TEST = "Test";
    @InjectMocks
    private RefPcsContractScoreController refPcsContractScoreController;
    @Mock
    private RefPcsScoreService refPcsScoreService;


    @Test
    @DisplayName("Unit test for Ref pcs contract score collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ModelRefPcsScore modelRefPcsScore = mockModelRefPcsScore();
        // when -  action or the behaviour that we are going test
        when(refPcsScoreService.getPcsScoreByFactorCode(CODE_FACTOR)).thenReturn(List.of(modelRefPcsScore));
        ResponseEntity<?> response  = refPcsContractScoreController.refPcsContractScoreList(request);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Unit test to add collection of Ref pcs contract score")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        RefPcsScoreDTO refPcsScoreDTO = mockRefPcsScoreDTO();
        refPcsContractScoreController.collectionAdd(refPcsScoreDTO,request,response);
        verify(refPcsScoreService, times(1)).addRefPcsScore(refPcsScoreDTO);

    }

    @Test
    @DisplayName("Unit test to delete Ref pcs contract score")
    public void testRefPcsScoreCodeDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        RefPcsScore refPcsScore  = this.mockRefPcsScore();

        when(refPcsScoreService.getRefPcsScore(refPcsScore.getCode())).thenReturn(Optional.of(refPcsScore));
        refPcsContractScoreController.refPcsScoreCodeDelete(mockAuthenticatedUserDTO,TEST);
        verify(refPcsScoreService, times(1)).removeRefPcsScore(TEST);

    }

    private RefPcsScore mockRefPcsScore()
    {
       RefPcsScore refPcsScore = new RefPcsScore();
       refPcsScore.setCode(TEST);
       refPcsScore.setScore(1);
       refPcsScore.setLibelle(TEST);
       refPcsScore.setCodeFactor(TEST);
       return refPcsScore;
    }
    private ModelRefPcsScore mockModelRefPcsScore()
    {
        ModelRefPcsScore modelRefPcsScore = new ModelRefPcsScore();
        modelRefPcsScore.setScore(1);
        modelRefPcsScore.setCode(TEST);
        modelRefPcsScore.setLibelle(TEST);
        modelRefPcsScore.setCodeFactor(TEST);
        return modelRefPcsScore;
    }

    private RefPcsScoreDTO mockRefPcsScoreDTO()
    {
        RefPcsScoreDTO refPcsScoreDTO = new RefPcsScoreDTO();
        refPcsScoreDTO.setScore(1);
        refPcsScoreDTO.setCode(TEST);
        refPcsScoreDTO.setLibelle(TEST);
        refPcsScoreDTO.setCodeFactor(TEST);
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
