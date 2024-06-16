package com.afklm.repind.ws.provideginforuserid;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000422.v1.BusinessException;
import com.afklm.soa.stubs.w000422.v1.data.MyActIdentifierTypeEnum;
import com.afklm.soa.stubs.w000422.v1.request.ProvideGinForUserIdRequest;
import com.afklm.soa.stubs.w000422.v1.request.ProvideGinForUserIdResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ProvideGinForUserIdImplTest {


    private static final Log logger = LogFactory.getLog(ProvideGinForUserIdImplTest.class);

    @Autowired
	@Qualifier("passenger_ProvideGINForUserID-v01Bean")
	private ProvideGinForUserIdImpl provideGinForUserIdImpl;
        
    @Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
    public void provideGinForUserIdImplByACTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByACTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.AC);
    	request.setCustomerIdentifier("001256609431");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("400000010265", response.getGIN());
    }
    
    @Test
    public void provideGinForUserIdImplByFPTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByFPTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.FP);
    	request.setCustomerIdentifier("001028502343");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("000000025070", response.getGIN());
    }

    @Test
    public void provideGinForUserIdImplByEMTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByEMTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.EM);
    	request.setCustomerIdentifier("elise.durupt@free.fr");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("000000026735", response.getGIN());
    }

    @Test
    public void provideGinForUserIdImplByMATest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByMATest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.MA);
    	request.setCustomerIdentifier("350362AA");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("400000010814", response.getGIN());
    }

    @Test
    public void provideGinForUserIdImplByEmShareEmailTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByEmShareEmailTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.EM);
    	request.setCustomerIdentifier("nicolasmerigot@yahoo.fr");
    	
    	try {
    		ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    		
    		Assert.fail("ERREUR : On devrait avoir une Exception 382, ce n est pas normal de ne pas l'avoir eut !");
    	
    	} catch (Exception ex) {
    		
    		Assert.assertEquals("EMAIL ALREADY USED BY FLYING BLUE MEMBERS", ex.getMessage());
    	}
    }
    
    @Test
    public void provideGinForUserIdImplBySTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplBySTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.S);					// SAFIR
    	request.setCustomerIdentifier("000222943334");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("400359070623", response.getGIN());
    }


    @Test
    public void provideGinForUserIdImplByRPTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByRNTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.RP);					// RP
    	request.setCustomerIdentifier("000218903811");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("110018015924", response.getGIN());
    }

    @Test
    public void provideGinForUserIdImplByAXTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByAXTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.AX);					// AMEX
    	request.setCustomerIdentifier("070011373105");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("400317206320", response.getGIN());
    }

    @Test
    public void provideGinForUserIdImplByGIGYA_MATest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByGIGYA_MATest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.MA);					// MA - GIGYA
    	request.setCustomerIdentifier("AFKL_201412121614__guid_0nPb-ygIDRfOYy8rjBbpE7GxJiSGudn3uifx");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("400447533462", response.getGIN());
    	Assert.assertEquals(MyActIdentifierTypeEnum.FP, response.getFoundIdentifier());
    }
    
/*    
    @Test    
    public void provideGinForUserIdImplByGIGYA_SNTest() throws JrafDomainException, BusinessException, SystemException  {

    	logger.info("provideGinForUserIdImplByGIGYA_SNTest...");

    	ProvideGinForUserIdRequest request = new ProvideGinForUserIdRequest();
    	request.setIdentifierType(MyActIdentifierTypeEnum.SN);					// SN - GIGYA AF
    	request.setCustomerIdentifier("AFKL_201412121614__guid_0nPb-ygIDRfOYy8rjBbpE7GxJiSGudn3uifx");
    				
    	ProvideGinForUserIdResponse response = provideGinForUserIdImpl.provideGinForUserId(request);
    	
    	Assert.assertNotEquals(null, response);
    	Assert.assertNotEquals(null, response.getGIN());
    	
    	Assert.assertEquals("400447533462", response.getGIN());
    	Assert.assertEquals("FP", response.getFoundIdentifier());
    }
*/    
}	
