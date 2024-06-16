package com.afklm.repind.msv.search.gin.by.contract.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.contract.repository.IBusinessRoleRepository;
import com.afklm.repind.msv.search.gin.by.contract.service.encoder.SearchGinByContractEncoder;
import com.afklm.repind.msv.search.gin.by.contract.utils.TestUtils;
import com.afklm.repind.msv.search.gin.by.contract.wrapper.WrapperSearchGinByContractResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchGinByContractServiceTest {

    @InjectMocks
    private SearchGinByContractService searchGinByContractService;

    @Mock
    private IBusinessRoleRepository businessRoleRepository;

    @Mock
    private SearchGinByContractEncoder searchGinByContractEncoder;

    @Test
    public void searchByCin() throws BusinessException {

        when(businessRoleRepository.findByContractNumber(any())).thenReturn(TestUtils.createBusinessRoleCollection(2));

        ResponseEntity<WrapperSearchGinByContractResponse> response = searchGinByContractService.search("12345678");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
