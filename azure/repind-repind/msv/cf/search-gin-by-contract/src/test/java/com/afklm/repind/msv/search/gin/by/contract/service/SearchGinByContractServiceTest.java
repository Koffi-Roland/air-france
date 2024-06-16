package com.afklm.repind.msv.search.gin.by.contract.service;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.contract.repository.IBusinessRoleRepository;
import com.afklm.repind.msv.search.gin.by.contract.service.encoder.SearchGinByContractEncoder;
import com.afklm.repind.msv.search.gin.by.contract.wrapper.WrapperSearchGinByContractResponse;
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
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SearchGinByContractServiceTest {

    @InjectMocks
    private SearchGinByContractService searchGinByContractService;

    @Mock
    private IBusinessRoleRepository businessRoleRepository;

    @Mock
    private SearchGinByContractEncoder searchGinByContractEncoder;

    private final String gin = "400401474125";
    private final String ginMerged = "400401474126";
    private final HttpStatus status = HttpStatus.OK;
    private final String cin = "001259777499";

    @Test
    void searchTest() throws BusinessException {
        when(businessRoleRepository.findByContractNumber(cin))
                .thenReturn(buildMockedCollection());

        when(searchGinByContractEncoder.decode(any()))
                .thenReturn(buildMockedResponse());

        ResponseEntity<WrapperSearchGinByContractResponse> response =
                searchGinByContractService.search(cin, false);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertEquals(gin, response.getBody().getGins().get(0));
    }

    @Test
    void searchTestMerged() throws BusinessException {
        when(businessRoleRepository.findByContractNumber(cin))
                .thenReturn(buildMockedCollection());

        when(searchGinByContractEncoder.decode(any()))
                .thenReturn(buildMockedResponseMerged());

        ResponseEntity<WrapperSearchGinByContractResponse> response =
                searchGinByContractService.search(cin, true);

        Assertions.assertEquals(status, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getGins().contains(gin));
        Assertions.assertTrue(response.getBody().getGins().contains(ginMerged));
    }

    private Collection<BusinessRole> buildMockedCollection() {
        List<BusinessRole> list = new ArrayList<>();
        BusinessRole businessRole = new BusinessRole();

        Individu individu = new Individu();
        individu.setGin(gin);
        individu.setStatutIndividu(IndividuStatusEnum.VALID.toString());

        businessRole.setIndividu(individu);

        BusinessRole businessRoleMerged = new BusinessRole();

        Individu individuMerged = new Individu();
        individuMerged.setGin(ginMerged);
        individuMerged.setStatutIndividu(IndividuStatusEnum.MERGED.toString());

        businessRoleMerged.setIndividu(individuMerged);

        list.add(businessRole);
        list.add(businessRoleMerged);
        return list;
    }

    private WrapperSearchGinByContractResponse buildMockedResponse() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);

        WrapperSearchGinByContractResponse response = new WrapperSearchGinByContractResponse();
        response.addGins(gins);

        return response;
    }

    private WrapperSearchGinByContractResponse buildMockedResponseMerged() {
        List<String> gins = new ArrayList<>();
        gins.add(gin);
        gins.add(ginMerged);

        WrapperSearchGinByContractResponse response = new WrapperSearchGinByContractResponse();
        response.addGins(gins);

        return response;
    }
}
