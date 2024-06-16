package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.criteria.administrator.AdministratorToolsCriteria;
import com.afklm.rigui.services.AdministratorToolsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class AdministratorToolsControllerTest {

    @Mock
    private AdministratorToolsService administratorToolsService;
    @InjectMocks
    private AdministratorToolsController administratorToolsController;

    private static final String GIN = "123456789";

    @Test
    void unmergeGin() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        administratorToolsController.unmergeGin(GIN, request);
        verify(administratorToolsService, times(1)).unmergeIndividual(any());
    }

    @Test
    void reactivateAccount() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        administratorToolsController.reactivateAccountGin(GIN, request);
        verify(administratorToolsService, times(1)).reactivateAccountGin(any());
    }

}
