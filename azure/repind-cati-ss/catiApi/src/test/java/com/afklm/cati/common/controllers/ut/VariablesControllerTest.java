package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.VariablesDTO;
import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.VariablesController;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class VariablesControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private VariablesController variablesController;

    @Mock
    private VariablesService variablesService;



    @Test
    @DisplayName("Unit test to get list variable")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Variables mockVariables = this.mockVariable();
        // when -  action or the behaviour that we are going test
        when(variablesService.getAllVariablesAlterable()).thenReturn(List.of(mockVariables));
        List<Variables> variables = variablesController.variablesList(request);
        assertThat(variables.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Unit test to add variable")
    public void testVariablesCreate() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        VariablesDTO mockVariableDTO = this.mockVariableDTO();
        variablesController.variablesCreate(mockVariableDTO,request,response);
        verify(variablesService, times(1)).addVariables(mockVariableDTO);

    }

    @Test
    @DisplayName("Unit test to get variable")
    public void testVariablesGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Variables mockVariables = this.mockVariable();
        when(variablesService.getVariables(mockVariables.getEnvKey())).thenReturn(Optional.of(mockVariables));
        Variables variables = variablesController.variablesGet(mockVariables.getEnvKey(),request);
        assertThat(variables).isNotNull();
    }


    @Test
    @DisplayName("Unit test to delete variable")
    public void testRefComPrefDelete() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Variables mockVariables = this.mockVariable();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        variablesController.variablesDelete(mockAuthenticatedUserDTO,mockVariables.getEnvKey());
        verify(variablesService, times(1)).removeVariables(mockVariables.getEnvKey(),mockAuthenticatedUserDTO.getUsername());

    }

    private Variables mockVariable()
    {
        Variables variables = new Variables();
        variables.setEnvKey(TEST);
        variables.setEnvValue(TEST);
        return variables;
    }
    private VariablesDTO mockVariableDTO()
    {
        VariablesDTO variablesDTO = new VariablesDTO();
        variablesDTO.setEnvKey(TEST);
        variablesDTO.setEnvValue(TEST);
        return variablesDTO;
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
