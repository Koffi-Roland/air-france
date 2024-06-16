package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.model.individual.ModelIndividual;
import com.afklm.rigui.model.individual.ModelIndividualResult;
import com.afklm.rigui.model.individual.requests.ModelIndividualIdentificationRequest;
import com.afklm.rigui.model.individual.requests.ModelIndividualProfileRequest;
import com.afklm.rigui.model.individual.requests.ModelIndividualRequest;
import com.afklm.rigui.services.IndividualService;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.wrapper.individual.WrapperIndividual;
import com.afklm.rigui.wrapper.individual.WrapperIndividualResult;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class IndividualControllerTest {

    @Mock
    private IndividualService individualService;

    @InjectMocks
    private IndividualController individualController;

    private static final String GIN = "123456789";

    @Test
    void getIndividualDetails() throws ServiceException, JrafDomainException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        WrapperIndividual result = new WrapperIndividual();
        ModelIndividual individualResult = new ModelIndividual();
        individualResult.setGin(GIN);
        individualResult.setFirstName("TOTO");
        result.individu = individualResult;
        when(individualService.getIndividualDetailsByIdentifiant(GIN)).thenReturn(result);
        WrapperIndividual response = individualController.getIndividualDetails(GIN, request).getBody();
        assertEquals(GIN, response.individu.getGin());
        assertEquals("TOTO", response.individu.getFirstName());
    }

    @Test
    void updateIndividualOK() throws ServiceException, SystemException {
        ModelIndividualRequest request = new ModelIndividualRequest();
        ModelIndividualIdentificationRequest identificationRequest = new ModelIndividualIdentificationRequest();
        ModelIndividualProfileRequest individualProfileRequest = new ModelIndividualProfileRequest();
        // identificationRequest setup
        identificationRequest.setAliasFirstname("TOTO");
        // individualProfileRequest setup
        individualProfileRequest.setBranch("BRANCH");
        // request setup
        request.setIdentification(identificationRequest);
        request.setProfile(individualProfileRequest);
        // call
        when(individualService.updateIndividual(request)).thenReturn(true);
        ResponseEntity<?> result = individualController.updateIndividual(request);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void updateIndividualKO() throws ServiceException, SystemException {
        ModelIndividualRequest request = new ModelIndividualRequest();
        ModelIndividualIdentificationRequest identificationRequest = new ModelIndividualIdentificationRequest();
        ModelIndividualProfileRequest individualProfileRequest = new ModelIndividualProfileRequest();
        // identificationRequest setup
        identificationRequest.setAliasFirstname("TOTO");
        // individualProfileRequest setup
        individualProfileRequest.setBranch("BRANCH");
        // request setup
        request.setIdentification(identificationRequest);
        request.setProfile(individualProfileRequest);
        // call
        when(individualService.updateIndividual(request)).thenReturn(false);
        ResponseEntity<?> result = individualController.updateIndividual(request);
        assertEquals(400, result.getStatusCodeValue());
    }

    @Test
    void individualsList() throws ServiceException, SystemException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        WrapperIndividualResult result = new WrapperIndividualResult();
        ModelIndividualResult individualResult = new ModelIndividualResult();
        individualResult.setGin(GIN);
        individualResult.setFirstName("TOTO");
        result.individuals = List.of(individualResult);
        when(individualService.getListIndividualResult(GIN)).thenReturn(result);
        WrapperIndividualResult response = individualController.individualsList(GIN, request).getBody();
        assertEquals(1, response.individuals.size());
        assertEquals("TOTO", response.individuals.get(0).getFirstName());
        assertEquals(GIN, response.individuals.get(0).getGin());
    }

    @Test
    void isContractAndIndividualHaveSameNumber() throws ServiceException, SystemException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        when(individualService.isContractAndIndividualHaveSameNumber(GIN)).thenReturn(1);
        ResponseEntity<Integer> result = individualController.isContractAndIndividualHaveSameNumber(GIN, request);
        assertEquals(1, result.getBody());
    }
}
