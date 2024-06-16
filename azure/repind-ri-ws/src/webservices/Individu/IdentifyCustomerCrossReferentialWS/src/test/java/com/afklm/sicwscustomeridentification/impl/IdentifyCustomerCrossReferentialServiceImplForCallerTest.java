package com.afklm.sicwscustomeridentification.impl;

import com.afklm.sicwscustomeridentification.config.WebTestConfig;
import com.afklm.sicwscustomeridentification.facades.IdentifyCustomerCrossReferentialFacade;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.*;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.IdentifyCustomerCrossReferentialResponse;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.SOAPException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class IdentifyCustomerCrossReferentialServiceImplForCallerTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String CONTEXT = "CCP";
	private static final String APP_CODE = "ISI";
	private static final String CAPI_CONSUMER = "w21375138";
	private static final String SIC_CONSUMER = "w85875644";
	private static final String REPIND_CONSUMER = "w04776476";
	
	@Autowired
	@Spy
	private IdentifyCustomerCrossReferentialFacade identifyCustomerCrossReferentialFacade;
	
	@Before
	public void setUp() throws JrafDomainException, SOAPException {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testIdentifyCustomerSearchCaller_CAPI() throws JrafDomainException, BusinessException, SystemException {
		
		Mockito.doReturn(CAPI_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("0446399053");

		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setContext(CONTEXT);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialFacade.search(request, null);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertEquals("960000000163", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerProvideCaller_CAPI() throws JrafDomainException, BusinessException, SystemException {
		
		Mockito.doReturn(CAPI_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		ProvideIdentifier provideIdentifier = new ProvideIdentifier();
		provideIdentifier.setCustomerGin("960000000163");
		
		request.setProvideIdentifier(provideIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setContext(CONTEXT);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialFacade.search(request, null);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertEquals("960000000163", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerSearchCaller_REPIND() throws JrafDomainException, BusinessException, SystemException {
		
		Mockito.doReturn(REPIND_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("0446399053");

		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialFacade.search(request, null);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertEquals("960000000163", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerProvideCaller_SIC() throws JrafDomainException, BusinessException, SystemException {
		
		Mockito.doReturn(SIC_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		ProvideIdentifier provideIdentifier = new ProvideIdentifier();
		provideIdentifier.setCustomerGin("960000000163");
		
		request.setProvideIdentifier(provideIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialFacade.search(request, null);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertEquals("960000000163", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerSearchCaller_SIC() throws JrafDomainException, BusinessException, SystemException {
		
		Mockito.doReturn(SIC_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("0446399053");

		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialFacade.search(request, null);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertEquals("960000000163", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerProvideCaller_REPIND() throws JrafDomainException, BusinessException, SystemException {
		
		Mockito.doReturn(REPIND_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		ProvideIdentifier provideIdentifier = new ProvideIdentifier();
		provideIdentifier.setCustomerGin("960000000163");
		
		request.setProvideIdentifier(provideIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialFacade.search(request, null);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertEquals("960000000163", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
	
	@Test
	public void testIdentifyCustomerSearchCaller_Bad_Consumer() throws SystemException, JrafDomainException {
		
		Mockito.doReturn("w999999").when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("0446399053");

		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setContext(CONTEXT);

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			identifyCustomerCrossReferentialFacade.search(request, null);
			Assert.fail("Web service must return NOT FOUND");
		} catch (BusinessException e) {
			Assert.assertEquals("001", e.getFaultInfo().getErrorCode());
		}		
	}
	
	@Test
	public void testIdentifyCustomerSearchProvide_Bad_Consumer() throws SystemException, JrafDomainException {
		
		Mockito.doReturn("w999999").when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		ProvideIdentifier provideIdentifier = new ProvideIdentifier();
		provideIdentifier.setCustomerGin("960000000163");
		
		request.setProvideIdentifier(provideIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setContext(CONTEXT);

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			identifyCustomerCrossReferentialFacade.search(request, null);
			Assert.fail("Web service must return NOT FOUND");
		} catch (BusinessException e) {
			Assert.assertEquals("001", e.getFaultInfo().getErrorCode());
		}		
	}
	
	@Test
	public void testIdentifyCustomerSearchCaller_Bad_Context() throws SystemException, JrafDomainException {
		
		Mockito.doReturn(CAPI_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		Telecom telecom = new Telecom();
		telecom.setCountryCode("33");
		telecom.setPhoneNumber("0446399053");

		searchIdentifier.setTelecom(telecom);
		
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setContext("TEST");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			identifyCustomerCrossReferentialFacade.search(request, null);
			Assert.fail("Web service must return NOT FOUND");
		} catch (BusinessException e) {
			Assert.assertEquals("001", e.getFaultInfo().getErrorCode());
		}		
	}
	
	@Test
	public void testIdentifyCustomerSearchProvide_Bad_Context() throws SystemException, JrafDomainException {
		
		Mockito.doReturn(CAPI_CONSUMER).when(identifyCustomerCrossReferentialFacade).getConsumerId(Mockito.any());

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		ProvideIdentifier provideIdentifier = new ProvideIdentifier();
		provideIdentifier.setCustomerGin("960000000163");
		
		request.setProvideIdentifier(provideIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");
		context.setResponseType("U");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		requestor.setContext("TEST");

		context.setRequestor(requestor);
		request.setContext(context);

		try {
			identifyCustomerCrossReferentialFacade.search(request, null);
			Assert.fail("Web service must return NOT FOUND");
		} catch (BusinessException e) {
			Assert.assertEquals("001", e.getFaultInfo().getErrorCode());
		}		
	}
}
