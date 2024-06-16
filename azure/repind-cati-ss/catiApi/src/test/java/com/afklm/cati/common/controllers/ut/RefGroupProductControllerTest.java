package com.afklm.cati.common.controllers.ut;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.entity.RefProduct;
import com.afklm.cati.common.entity.RefProductComPrefGroup;
import com.afklm.cati.common.entity.RefProductComPrefGroupId;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import com.afklm.cati.common.service.RefGroupProductService;
import com.afklm.cati.common.spring.rest.controllers.RefGroupProductController;
import com.afklm.cati.common.spring.rest.resources.LinkGroupProduct;
import com.afklm.cati.common.spring.rest.resources.RefGroupProductResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RefGroupProductControllerTest {

    private static final String TEST = "Test";
    @InjectMocks
    private RefGroupProductController refGroupProductController;

    @Mock
    private RefGroupProductService refGroupProductService;


    @Test
    @DisplayName("Unit test for Ref product collection")
    public void testMethodCollectionList() throws Exception, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefProductComPrefGroup mockRefProductComPrefGroup = this.mockRefProductComPrefGroup();
        // when -  action or the behaviour that we are going test
        when(refGroupProductService.getAllRefGroupProduct()).thenReturn(List.of(mockRefProductComPrefGroup));
        List<RefGroupProductResource> refGroupProductResources = refGroupProductController.collectionList(request);
        assertThat(refGroupProductResources.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Unit test to add collection of Ref product")
    public void testMethodCollectionAdd() throws Exception, CatiCommonsException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        LinkGroupProduct linkGroupProduct = mockLinkGroupProduct();
        refGroupProductController.collectionAdd(linkGroupProduct, mockAuthenticatedUserDTO, request, response);
        verify(refGroupProductService, times(1)).addRefGroupProduct(any(RefProductComPrefGroup.class), any());

    }


    @Test
    @DisplayName("Unit test to get Ref product")
    public void testRefProductGetComPrefGroupByProductId() throws JrafDaoException, CatiCommonsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RefProductComPrefGroup mockRefProductComPrefGroup = this.mockRefProductComPrefGroup();
        when(refGroupProductService.getAllRefGroupProductByProductId(1)).thenReturn(List.of(mockRefProductComPrefGroup));
        List<RefGroupProductResource> refGroupProductResources = refGroupProductController.refProductGetComPrefGroupByProductId(1, request);
        assertThat(refGroupProductResources.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Unit test to delete Ref product")
    public void testRefGroupProductDelete() throws JrafDaoException, CatiCommonsException {
        AuthenticatedUserDTO mockAuthenticatedUserDTO = this.mockAuthenticateDto();
        refGroupProductController.RefProductComPrefGroupDelete(mockAuthenticatedUserDTO, 1, 1);
        verify(refGroupProductService, times(1)).removeRefGroupProduct(any(RefProductComPrefGroup.class), any());

    }

    private RefProductComPrefGroupId mockRefProductComPrefGroupId() {
        RefProductComPrefGroupId productComPrefGroupId = new RefProductComPrefGroupId();
        RefProduct refProduct = this.mockRefProduct();
        RefComPrefGroupInfo refComPrefGroupInfo = this.mockRefComPrefGroupInfo();
        productComPrefGroupId.setRefProduct(refProduct);
        productComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
        return productComPrefGroupId;
    }

    RefComPrefGroupInfo mockRefComPrefGroupInfo() {
        RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
        Date date = new Date();
        refComPrefGroupInfo.setId(1);
        refComPrefGroupInfo.setCode(TEST);
        refComPrefGroupInfo.setDateCreation(date);
        refComPrefGroupInfo.setLibelleEN(TEST);
        refComPrefGroupInfo.setDefaultOption(TEST);
        refComPrefGroupInfo.setDateModification(date);
        refComPrefGroupInfo.setLibelleFR(TEST);
        refComPrefGroupInfo.setMandatoryOption(TEST);
        refComPrefGroupInfo.setDefaultOption(TEST);
        refComPrefGroupInfo.setSiteCreation(TEST);
        refComPrefGroupInfo.setSiteModification(TEST);
        refComPrefGroupInfo.setSignatureModification(TEST);
        refComPrefGroupInfo.setSignatureCreation(TEST);
        return refComPrefGroupInfo;
    }

    private RefProduct mockRefProduct() {

        RefProduct refProduct = new RefProduct();
        Date date = new Date();
        refProduct.setProductId(1);
        refProduct.setProductName(TEST);
        refProduct.setProductType(TEST);
        refProduct.setDateCreation(date);
        refProduct.setDateModification(date);
        refProduct.setAssociatedComPref(TEST);
        refProduct.setContractId(TEST);
        refProduct.setSiteModification(TEST);
        refProduct.setSignatureModification(TEST);
        refProduct.setSignatureCreation(TEST);
        refProduct.setSubProductType(TEST);
        refProduct.setSiteCreation(TEST);
        refProduct.setGenerateComPref(TEST);
        refProduct.setContractType(TEST);
        return refProduct;
    }

    private LinkGroupProduct mockLinkGroupProduct() {
        LinkGroupProduct linkGroupProduct = new LinkGroupProduct();
        linkGroupProduct.setProductId(1);
        linkGroupProduct.setGroupsId(List.of(1));
        return linkGroupProduct;
    }

    private RefProductComPrefGroup mockRefProductComPrefGroup() {
        RefProductComPrefGroup refProductComPrefGroup = new RefProductComPrefGroup();
        RefProductComPrefGroupId refProductComPrefGroupId = mockRefProductComPrefGroupId();
        refProductComPrefGroup.setRefProductComPrefGroupId(refProductComPrefGroupId);
        Date date = new Date();
        refProductComPrefGroup.setDateCreation(date);
        refProductComPrefGroup.setDateModification(date);
        refProductComPrefGroup.setSignatureCreation(TEST);
        refProductComPrefGroup.setSignatureModification(TEST);
        refProductComPrefGroup.setSiteCreation(TEST);
        refProductComPrefGroup.setSiteModification(TEST);
        return refProductComPrefGroup;
    }

    private AuthenticatedUserDTO mockAuthenticateDto() {
        // Profile access key
        ProfileAccessKey profileAccessKey = new ProfileAccessKey();
        profileAccessKey.setAccessKeyLst(null);
        profileAccessKey.setProfile("ROLE_ADMIN_COMMPREF");
        UserProfilesAccessKey accessKeys = new UserProfilesAccessKey();
        accessKeys.addProfileAccessKey(profileAccessKey);
        return new AuthenticatedUserDTO(TEST, "password", List.of(new SimpleGrantedAuthority("ROLE_ADMIN_COMMPREF")), accessKeys, TEST, TEST);
    }
}
