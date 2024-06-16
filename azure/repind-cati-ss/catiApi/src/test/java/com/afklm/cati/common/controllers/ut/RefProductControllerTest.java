package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.controllers.RefProductController;
import com.afklm.cati.common.spring.rest.resources.RefProductResource;
import org.dozer.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RefProductControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefProductController refProductController;
    @Mock
    private RefProductService refProductService;
    @Mock
    private Mapper dozerBeanMapper;


    @Test
    @DisplayName("Unit test for Ref product collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefProductResource refProductResource = mockRefProductResource();
        RefProduct refProduct = mockRefProduct();
        // when -  action or the behaviour that we are going test
        when(refProductService.getAllCustomerRefProduct()).thenReturn(List.of(1));
        when(refProductService.getRefProduct(1)).thenReturn(Optional.of(refProduct));
        when(dozerBeanMapper.map(refProduct,RefProductResource.class)).thenReturn(refProductResource);
        List<RefProductResource> refProductResources = refProductController.collectionList(request);
        assertThat(refProductResources.size()).isEqualTo(1);
    }


    private RefProduct mockRefProduct()
    {
        RefProduct refProduct = new RefProduct();
        Date date = new Date();
        refProduct.setContractType(TEST);
        refProduct.setDateCreation(date);
        refProduct.setDateModification(date);
        refProduct.setSignatureCreation(TEST);
        refProduct.setSignatureModification(TEST);
        refProduct.setSiteCreation(TEST);
        refProduct.setSiteModification(TEST);
        refProduct.setSubProductType(TEST);
        refProduct.setProductType(TEST);
        refProduct.setGenerateComPref(TEST);
        refProduct.setContractId(TEST);
        refProduct.setAssociatedComPref(TEST);
        refProduct.setProductName(TEST);
        return refProduct;

    }

    private RefProductResource mockRefProductResource()
    {
        RefProductResource refProductResource = new RefProductResource();
        refProductResource.setTechId(1L);
        refProductResource.setProductId(1);
        refProductResource.setProductName(TEST);
        refProductResource.setProductType(TEST);
        refProductResource.setSubProductType(TEST);
        refProductResource.setContractType(TEST);
        return refProductResource;
    }

}
