package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.model.individual.ModelAdhoc;
import com.afklm.rigui.model.individual.requests.ModelAdhocRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.adhoc.AdhocService;
import com.afklm.rigui.wrapper.adhoc.WrapperAdhoc;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdhocControllerTest {

    @Mock
    AdhocService adhocService;

    @InjectMocks
    AdhocController adhocController;

    @Test
    void validate() throws ServiceException, SystemException {
        ModelAdhocRequest request = new ModelAdhocRequest();
        ModelAdhoc adhoc = new ModelAdhoc();
        // invalid item
        adhoc.setId(0);
        adhoc.setEmailAddress("totogmail.com");
        adhoc.setGin("1234ab6789012");
        adhoc.setCin("67890");
        adhoc.setFirstname("toto");
        adhoc.setSurname("titi");
        adhoc.setCivility("MR");
        adhoc.setBirthdate("22-02-1990");
        adhoc.setCountryCode("fr");
        adhoc.setLanguageCode("fr");
        adhoc.setSubscriptionType("AF");
        adhoc.setDomain("S");
        adhoc.setGroupType("N");
        adhoc.setStatus("Y");
        adhoc.setSource("source");
        adhoc.setDateOfConsent("22-02-1990");
        adhoc.setPreferredDepartureAirport("cdg");
        request.setList(List.of(adhoc));

        when(adhocService.validate(any(), any())).thenReturn(new WrapperAdhoc());
        ResponseEntity<WrapperAdhoc> response = adhocController.validate("AF", request);
        assertEquals(0, response.getBody().getResult().size());
    }
}
