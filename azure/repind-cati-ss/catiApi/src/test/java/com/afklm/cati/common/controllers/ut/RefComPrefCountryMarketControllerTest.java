package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.dto.RefComPrefCountryMarketDTO;
import com.afklm.cati.common.entity.RefComPrefCountryMarket;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.RefComPrefCountryMarketService;
import com.afklm.cati.common.spring.rest.controllers.RefComPrefCountryMarketController;
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


@ExtendWith(SpringExtension.class)
public class RefComPrefCountryMarketControllerTest {

    private static final String TEST = "Test";

    @Mock
    private Mapper dozerBeanMapper;
    @InjectMocks
    private RefComPrefCountryMarketController refComPrefCountryMarketController;
    @Mock
    private RefComPrefCountryMarketService refComPrefCountryMarketService;


    @Test
    @DisplayName("Unit test for Ref communication preferences country market collection")
    public void testCollectionList() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefCountryMarket refComPrefCountryMarket = this.mockRefComprefCountryMarket();
        // when -  action or the behaviour that we are going test
        when(refComPrefCountryMarketService.getAllRefComPrefCountryMarket()).thenReturn(List.of(refComPrefCountryMarket));
        List<RefComPrefCountryMarket> refComPrefCountryMarkets = refComPrefCountryMarketController.collectionList(request);
        assertThat(refComPrefCountryMarkets.size()).isEqualTo(1);

    }

    @Test
    @DisplayName("Unit test to add Ref communication preferences country market")
    public void testCollectionAdd() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticatedUserDTO authenticatedUserDTO = mockAuthenticateDto();
        RefComPrefCountryMarketDTO refComPrefCountryMarketDTO =  mockRefComPrefCountryMarketDTO();
        refComPrefCountryMarketController.collectionAdd(refComPrefCountryMarketDTO,authenticatedUserDTO,request,response);
        verify(refComPrefCountryMarketService, times(1)).addRefComPrefCountryMarket(any(),any());
    }

    @Test
    @DisplayName("Unit test to get Ref communication preferences country market")
    public void testRefComPrefCountryMarketGet() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefComPrefCountryMarket mockRefComPrefCountryMarket = this.mockRefComprefCountryMarket();
        when(refComPrefCountryMarketService.getRefComPrefCountryMarket(mockRefComPrefCountryMarket.getCodePays())).thenReturn(Optional.of(mockRefComPrefCountryMarket));
        RefComPrefCountryMarket refComPrefCountryMarket =  refComPrefCountryMarketController.refComPrefCountryMarketGet(mockRefComPrefCountryMarket.getCodePays(),request);
        assertThat(refComPrefCountryMarket).isNotNull();
    }

    @Test
    @DisplayName("Unit test to get Ref communication preferences country market")
    public void testRefComPrefCountryMarketUpdate() throws JrafDaoException, CatiCommonsException {
        RefComPrefCountryMarket mockRefComPrefCountryMarket = this.mockRefComprefCountryMarket();
        AuthenticatedUserDTO authenticatedUserDTO = mockAuthenticateDto();
        RefComPrefCountryMarketDTO refComPrefCountryMarketDTO = this.mockRefComPrefCountryMarketDTO();
        when(refComPrefCountryMarketService.getRefComPrefCountryMarket(mockRefComPrefCountryMarket.getCodePays())).thenReturn(Optional.of(mockRefComPrefCountryMarket));
        RefComPrefCountryMarket refComPrefCountryMarket =  refComPrefCountryMarketController.refComPrefCountryMarketUpdate(refComPrefCountryMarketDTO,authenticatedUserDTO,mockRefComPrefCountryMarket.getCodePays());
        assertThat(refComPrefCountryMarket).isNotNull();
    }

    @Test
    @DisplayName("Unit test to get Ref communication preferences country market")
    public void testRefComPrefCountryMarketDelete() throws JrafDaoException, CatiCommonsException {
        RefComPrefCountryMarket mockRefComPrefCountryMarket = this.mockRefComprefCountryMarket();
        AuthenticatedUserDTO authenticatedUserDTO = mockAuthenticateDto();
        refComPrefCountryMarketController.refComPrefCountryMarketDelete(authenticatedUserDTO,mockRefComPrefCountryMarket.getCodePays());
        verify(refComPrefCountryMarketService, times(1)).removeRefComPrefCountryMarket(any(),any());
    }

    private RefComPrefCountryMarket mockRefComprefCountryMarket()
    {
        RefComPrefCountryMarket refComPrefCountryMarket = new RefComPrefCountryMarket();
        refComPrefCountryMarket.setMarket(TEST);
        Date date =  new Date();
        refComPrefCountryMarket.setDateCreation(date);
        refComPrefCountryMarket.setDateModification(date);
        refComPrefCountryMarket.setCodePays(TEST);
        refComPrefCountryMarket.setSignatureModification(TEST);
        refComPrefCountryMarket.setSiteCreation(TEST);
        refComPrefCountryMarket.setSiteModification(TEST);
        refComPrefCountryMarket.setSignatureCreation(TEST);
        return refComPrefCountryMarket;
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

    private RefComPrefCountryMarketDTO mockRefComPrefCountryMarketDTO()
    {
        RefComPrefCountryMarketDTO  refComPrefCountryMarketDTO = new RefComPrefCountryMarketDTO();
        Date date =  new Date();
        refComPrefCountryMarketDTO.setDateCreation(date);
        refComPrefCountryMarketDTO.setDateModification(date);
        refComPrefCountryMarketDTO.setCodePays(TEST);
        refComPrefCountryMarketDTO.setSignatureModification(TEST);
        refComPrefCountryMarketDTO.setSiteCreation(TEST);
        refComPrefCountryMarketDTO.setSiteModification(TEST);
        refComPrefCountryMarketDTO.setSignatureCreation(TEST);
       return refComPrefCountryMarketDTO;
    }
}
