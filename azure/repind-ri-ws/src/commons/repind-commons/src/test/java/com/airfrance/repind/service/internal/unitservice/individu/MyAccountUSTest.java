package com.airfrance.repind.service.internal.unitservice.individu;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.entity.individu.AccountData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Title : EtablissementDSTest.java
 * </p>
 * <p>
 * Description :
 * </p>
 * <p>
 * Copyright : Copyright (c) 2014
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class MyAccountUSTest {

    /**
     * Individu Unit Service
     */
    @Autowired
    private MyAccountUS myAccountUS;
    
    private AccountDataRepository accountDataRepository;
    
    @Autowired
    private MyAccountUS myAccountUSMock;

    /**
     * Test de la methode provideGinForUserId
     */
    @Test
    @Transactional
    public void testProvideGinForUserId1() throws Exception {

        ProvideGinForUserIdRequestDTO requestMyAccountDatas = new ProvideGinForUserIdRequestDTO();
        requestMyAccountDatas.setIdentifier("2083541643");
        requestMyAccountDatas.setIdentifierType("MA");
        ProvideGinForUserIdResponseDTO responseForMyAccountDatas = myAccountUS.provideGinForUserId(requestMyAccountDatas);
        Assert.assertEquals(responseForMyAccountDatas.getGin(), "400368703835");
        Assert.assertEquals(responseForMyAccountDatas.getFoundIdentifier(), "FP");
    }

    /**
     * Test de la methode provideGinForUserId
     */
    @Test
    @Transactional
    public void testProvideGinForUserId2() throws Exception {

        ProvideGinForUserIdRequestDTO requestMyAccountDatas = new ProvideGinForUserIdRequestDTO();
        requestMyAccountDatas.setIdentifier("2083541643");
        requestMyAccountDatas.setIdentifierType("FP");
        ProvideGinForUserIdResponseDTO responseForMyAccountDatas = myAccountUS.provideGinForUserId(requestMyAccountDatas);
        Assert.assertEquals(responseForMyAccountDatas.getGin(), "400368703835");
        Assert.assertEquals(responseForMyAccountDatas.getFoundIdentifier(), "FP");
    }

    /**
     * Test de la methode provideGinForUserId
     */
    @Test
    @Transactional
    public void testProvideGinForUserId3() throws Exception {

        ProvideGinForUserIdRequestDTO requestMyAccountDatas = new ProvideGinForUserIdRequestDTO();
        requestMyAccountDatas.setIdentifier("370668AD");
        requestMyAccountDatas.setIdentifierType("MA");
        ProvideGinForUserIdResponseDTO responseForMyAccountDatas = myAccountUS.provideGinForUserId(requestMyAccountDatas);
        Assert.assertEquals(responseForMyAccountDatas.getGin(), "400368703835");
        Assert.assertEquals(responseForMyAccountDatas.getFoundIdentifier(), "FP");
    }
    
    /**
     * Test de la methode provideGinForUserId
     */
    @Test
    @Transactional
    public void testProvideGinForUserId4() throws Exception {

        ProvideGinForUserIdRequestDTO requestMyAccountDatas = new ProvideGinForUserIdRequestDTO();
        requestMyAccountDatas.setIdentifier("513406AA");
        requestMyAccountDatas.setIdentifierType("MA");
        ProvideGinForUserIdResponseDTO responseForMyAccountDatas = myAccountUS.provideGinForUserId(requestMyAccountDatas);
        Assert.assertEquals(responseForMyAccountDatas.getGin(), "400352332773");
        Assert.assertEquals(responseForMyAccountDatas.getFoundIdentifier(), "MA");
    }
    
    @Test
    @DirtiesContext
    public void provideGinByUserIdNoCheckSharedEmail() throws JrafDomainException {
    	
    	ProvideGinForUserIdRequestDTO request = new ProvideGinForUserIdRequestDTO();
    	request.setIdentifier("test@gmail.com");
    	
    	AccountData accountDataExpected = new AccountData();
    	accountDataExpected.setSgin("000000000264");
    	accountDataExpected.setEmailIdentifier("test@gmail.com");
    	accountDataExpected.setAccountIdentifier("348650AA");
    	accountDataExpected.setFbIdentifier("001024800135");
    	
    	accountDataRepository = Mockito.mock(AccountDataRepository.class);
    	Mockito.when(accountDataRepository.findByEmailIdentifier(Mockito.any(String.class))).thenReturn(accountDataExpected);
    	
    	myAccountUSMock.setAccountDataRepository(accountDataRepository);
    	
    	ProvideGinForUserIdResponseDTO response = myAccountUSMock.provideGinByUserIdNoCheckSharedEmail(request);
    	
        Assert.assertEquals("000000000264", response.getGin());
        Assert.assertEquals("FP", response.getFoundIdentifier());
        Assert.assertEquals(null, response.getReturnCode());
    }
}
