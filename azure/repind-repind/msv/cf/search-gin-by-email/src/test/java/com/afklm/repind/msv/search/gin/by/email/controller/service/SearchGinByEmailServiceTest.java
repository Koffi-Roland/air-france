package com.afklm.repind.msv.search.gin.by.email.controller.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.enums.MediumStatusEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.email.repository.IEmailRepository;
import com.afklm.repind.msv.search.gin.by.email.service.SearchGinByEmailService;
import com.afklm.repind.msv.search.gin.by.email.service.encoder.SearchGinByEmailEncoder;
import com.afklm.repind.msv.search.gin.by.email.wrapper.WrapperSearchGinByEmailResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SearchGinByEmailServiceTest {

    @InjectMocks
    private SearchGinByEmailService searchGinByEmailService;

    @Mock
    private IEmailRepository emailRepository;

    @Mock
    private SearchGinByEmailEncoder searchGinByEmailEncoder;

    private final String gin = "400401474125";
    private final String ginMerged = "400401474126";
    private final HttpStatus status = HttpStatus.OK;
    private final String email = "jane.marple@repind.com";

    @Test
    void searchTest() throws BusinessException {
        when(emailRepository.findByEmailAndStatutMediumInAndIndividuIsNotNull(email,
                Arrays.asList(MediumStatusEnum.VALID.toString(), MediumStatusEnum.INVALID.toString())))
                .thenReturn(buildMockedCollection());

        when(searchGinByEmailEncoder.decode(any()))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByEmailResponse> response =
                searchGinByEmailService.search(email, false);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertEquals(gin, response.getBody().getGins().get(0));
    }

    @Test
    void searchTestMerged() throws BusinessException {
        when(emailRepository.findByEmailAndStatutMediumInAndIndividuIsNotNull(email,
                Arrays.asList(MediumStatusEnum.VALID.toString(), MediumStatusEnum.INVALID.toString())))
                .thenReturn(buildMockedCollection());

        when(searchGinByEmailEncoder.decode(any()))
                .thenReturn(buildMockedResponseMerged());

        ResponseEntity<WrapperSearchGinByEmailResponse> response =
                searchGinByEmailService.search(email, true);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getGins().contains(gin));
        Assertions.assertTrue(response.getBody().getGins().contains(ginMerged));
    }

    private Collection<EmailEntity> buildMockedCollection() {
        List<EmailEntity> list = new ArrayList<>();
        EmailEntity emailE = new EmailEntity();

        Individu individu = new Individu();
        individu.setGin(gin);
        individu.setStatutIndividu(IndividuStatusEnum.VALID.toString());

        emailE.setIndividu(individu);

        EmailEntity emailMerged = new EmailEntity();

        Individu individuMerged = new Individu();
        individuMerged.setGin(ginMerged);
        individuMerged.setStatutIndividu(IndividuStatusEnum.MERGED.toString());

        emailMerged.setIndividu(individuMerged);

        list.add(emailE);
        list.add(emailMerged);
        return list;
    }

    private WrapperSearchGinByEmailResponse buildMockedResponse() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByEmailResponse response = new WrapperSearchGinByEmailResponse();
        response.addGins(gins);

        return response;
    }

    private WrapperSearchGinByEmailResponse buildMockedResponseMerged() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);
        gins.add(ginMerged);

        WrapperSearchGinByEmailResponse response = new WrapperSearchGinByEmailResponse();
        response.addGins(gins);

        return response;
    }
}
