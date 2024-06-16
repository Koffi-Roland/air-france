package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.model.individual.ModelBasicIndividualData;
import com.afklm.rigui.model.individual.requests.*;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.search.IndividualSearchService;
import com.afklm.rigui.wrapper.searches.WrapperIndividualSearch;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SearchControllerTest {

    @Mock
    private IndividualSearchService individualSearchService;
    @InjectMocks
    private SearchController searchController;
    @Test
    public void searchByEmail() throws ServiceException, SystemException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        ModelSearchByEmailRequest search = new ModelSearchByEmailRequest();
        search.setEmail("email");
        WrapperIndividualSearch wrapper = new WrapperIndividualSearch();
        ModelBasicIndividualData individu = new ModelBasicIndividualData();
        individu.setFirstname("TOTO");
        wrapper.data = List.of(individu);
        wrapper.count = 1;
        when(individualSearchService.searchIndividualByEmail(search,false)).thenReturn(wrapper);
        WrapperIndividualSearch result = searchController.searchByEmail(search,false, request).getBody();
        assertEquals(1, result.count);
        assertEquals("TOTO", result.data.get(0).getFirstname());
    }

    @Test
    public void searchByTelecom() throws ServiceException, SystemException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        ModelSearchByTelecomRequest search = new ModelSearchByTelecomRequest();
        search.setCountryCode("countryCode");
        search.setPhoneNumber("phoneNumber");
        WrapperIndividualSearch wrapper = new WrapperIndividualSearch();
        ModelBasicIndividualData individu = new ModelBasicIndividualData();
        individu.setFirstname("TOTO");
        wrapper.data = List.of(individu);
        wrapper.count = 1;
        when(individualSearchService.searchIndividualByTelecom(search,false)).thenReturn(wrapper);
        WrapperIndividualSearch result = searchController.searchByTelecom(search,false, request).getBody();
        assertEquals(1, result.count);
        assertEquals("TOTO", result.data.get(0).getFirstname());
    }
    @Test
    public void searchByMulticriteria() throws ServiceException, SystemException, ParseException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        ModelSearchByMulticriteriaRequest search = new ModelSearchByMulticriteriaRequest();
        search.setLastname("Lastname");
        search.setFirstname("firstname");
        WrapperIndividualSearch wrapper = new WrapperIndividualSearch();
        ModelBasicIndividualData individu = new ModelBasicIndividualData();
        individu.setFirstname("TOTO");
        wrapper.data = List.of(individu);
        wrapper.count = 1;
        when(individualSearchService.searchIndividualByMulticriteria(search,false)).thenReturn(wrapper);
        WrapperIndividualSearch result = searchController.searchByMulticriteria(search, false,request).getBody();
        assertEquals(1, result.count);
        assertEquals("TOTO", result.data.get(0).getFirstname());
    }

    @Test
    public void searchByAccount() throws ServiceException, SystemException, ParseException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("cin", "123456789105");
        ModelSearchByAccountRequest search = new ModelSearchByAccountRequest();
        WrapperIndividualSearch wrapper = new WrapperIndividualSearch();
        ModelBasicIndividualData individu = new ModelBasicIndividualData();
        individu.setFirstname("TOTO");
        wrapper.data = List.of(individu);
        wrapper.count = 1;
        when(individualSearchService.searchIndividualByAccountIdentifier(search)).thenReturn(wrapper);
        WrapperIndividualSearch result = searchController.searchByAccount(search, request).getBody();
        assertEquals(1, result.count);
        assertEquals("TOTO", result.data.get(0).getFirstname());
    }

    @Test
    @DisplayName("JUnit test for testing search by social identifier included merge (merge status true)")
    public void searchBySocialIDWithMergeStatusTrue() throws ServiceException, SystemException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ModelSearchBySocialIDRequest search = new ModelSearchBySocialIDRequest();
        search.setSocialID("af6589po98989");
        search.setSocialType("GID");
        WrapperIndividualSearch wrapper = new WrapperIndividualSearch();
        ModelBasicIndividualData individu = new ModelBasicIndividualData();
        individu.setFirstname("TOTO");
        wrapper.data = List.of(individu);
        wrapper.count = 1;
        when(individualSearchService.searchIndividualBySocialID(search,true)).thenReturn(wrapper);
        WrapperIndividualSearch result = searchController.searchBySocialID(search,true, request).getBody();
        assertEquals(1, result.count);
        assertEquals("TOTO", result.data.get(0).getFirstname());
    }

    @Test
    @DisplayName("JUnit test for testing search by social identifier")
    public void searchBySocialIDWithMergeStatusFalse() throws ServiceException, SystemException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        ModelSearchBySocialIDRequest search = new ModelSearchBySocialIDRequest();
        search.setSocialID("affd6589po98989");
        search.setSocialType("GID");
        WrapperIndividualSearch wrapper = new WrapperIndividualSearch();
        ModelBasicIndividualData individu = new ModelBasicIndividualData();
        individu.setFirstname("TOTO");
        wrapper.data = List.of(individu);
        wrapper.count = 1;
        when(individualSearchService.searchIndividualBySocialID(search,false)).thenReturn(wrapper);
        WrapperIndividualSearch result = searchController.searchBySocialID(search,false, request).getBody();
        assertEquals(1, result.count);
        assertEquals("TOTO", result.data.get(0).getFirstname());
    }
}
