package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.spring.security.habile.proxy.ws.ProvideUserRightsAccessV10Impl;
import com.afklm.spring.security.habile.proxy.ws.ProvideUserRightsAccessV20Impl;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

/**
 * Test for WebServiceController
 * 
 * @author m405991
 *
 */
@WebFluxTest(WebServiceController.class)
public class WebServiceControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ProvideUserRightsAccessV10Impl provideUserRightsAccessV10Impl;

    @MockBean
    private ProvideUserRightsAccessV20Impl provideUserRightsAccessV20Impl;

    @Test
    @WithMockUser
    public void testEndpointV1Nominal() throws Exception {
        String content = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("w000479v01.xml"));
        ProvideUserRightsAccessRS response = ProvideUserRightsAccessV10Fixture.response();
        Mockito.when(provideUserRightsAccessV10Impl.provideUserRightsAccess(Mockito.any(ProvideUserRightsAccessRQ.class))).thenReturn(response);

        webClient.mutateWith(csrf())
            .post()
            .uri("/mock/ws/w000479v01")
            .contentType(MediaType.TEXT_XML)
            .body(BodyInserters.fromValue(content))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .value(containsString(response.getLastName()));
    }

    @Test
    @WithMockUser
    public void testEndpointV1BusinessFault() throws Exception {
        String content = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("w000479v01.xml"));
        HblWsBusinessException businessException = ProvideUserRightsAccessV10Fixture.businessException();
        Mockito.when(provideUserRightsAccessV10Impl.provideUserRightsAccess(Mockito.any(ProvideUserRightsAccessRQ.class))).thenThrow(businessException);

        webClient.mutateWith(csrf())
            .post()
            .uri("/mock/ws/w000479v01")
            .contentType(MediaType.TEXT_XML)
            .body(BodyInserters.fromValue(content))
            .exchange()
            .expectStatus()
            .isEqualTo(INTERNAL_SERVER_ERROR)
            .expectBody(String.class)
            .value(containsString(businessException.getMessage()));
    }

    @Test
    @WithMockUser
    public void testEndpointV1SystemFault() throws Exception {
        String content = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("w000479v01.xml"));
        SystemException systemException = ProvideUserRightsAccessV10Fixture.systemException();
        Mockito.when(provideUserRightsAccessV10Impl.provideUserRightsAccess(Mockito.any(ProvideUserRightsAccessRQ.class))).thenThrow(systemException);

        webClient.mutateWith(csrf())
            .post()
            .uri("/mock/ws/w000479v01")
            .contentType(MediaType.TEXT_XML)
            .body(BodyInserters.fromValue(content))
            .exchange()
            .expectStatus()
            .isEqualTo(INTERNAL_SERVER_ERROR)
            .expectBody(String.class)
            .value(containsString(systemException.getMessage()));
    }

    @Test
    @WithMockUser
    public void testEndpointV2Nominal() throws Exception {
        String content = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("w000479v02.xml"));
        com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRS response = ProvideUserRightsAccessV20Fixture.response();
        Mockito.when(provideUserRightsAccessV20Impl.provideUserRightsAccess(Mockito.any(com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRQ.class))).thenReturn(response);

        webClient.mutateWith(csrf())
            .post()
            .uri("/mock/ws/w000479v02")
            .contentType(MediaType.TEXT_XML)
            .body(BodyInserters.fromValue(content))
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .value(containsString(response.getLastName()));
    }

    @Test
    @WithMockUser
    public void testEndpointV2BusinessFault() throws Exception {
        String content = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("w000479v02.xml"));
        com.afklm.soa.stubs.w000479.v2.HblWsBusinessException businessException = ProvideUserRightsAccessV20Fixture.businessException();
        Mockito.when(provideUserRightsAccessV20Impl.provideUserRightsAccess(Mockito.any(com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRQ.class))).thenThrow(businessException);

        webClient.mutateWith(csrf())
            .post()
            .uri("/mock/ws/w000479v02")
            .contentType(MediaType.TEXT_XML)
            .body(BodyInserters.fromValue(content))
            .exchange()
            .expectStatus()
            .isEqualTo(INTERNAL_SERVER_ERROR)
            .expectBody(String.class)
            .value(containsString(businessException.getMessage()));
    }

    @Test
    @WithMockUser
    public void testEndpointV2SystemFault() throws Exception {
        String content = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("w000479v02.xml"));
        SystemException systemException = ProvideUserRightsAccessV10Fixture.systemException();
        Mockito.when(provideUserRightsAccessV20Impl.provideUserRightsAccess(Mockito.any(com.afklm.soa.stubs.w000479.v2.provideuserrightsaccessv2.ProvideUserRightsAccessRQ.class))).thenThrow(systemException);

        webClient.mutateWith(csrf())
            .post()
            .uri("/mock/ws/w000479v02")
            .contentType(MediaType.TEXT_XML)
            .body(BodyInserters.fromValue(content))
            .exchange()
            .expectStatus()
            .isEqualTo(INTERNAL_SERVER_ERROR)
            .expectBody(String.class)
            .value(containsString(systemException.getMessage()));
    }
}
