package com.afklm.repind.searchindividualbymulticriteriaws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.siccommontype.Requestor;
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
public class SearchIndividualByMulticriteriaForCallerImplTest {
	
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
	private SearchIndividualByMulticriteriaImpl searchIndividualByMulticriteriaImpl;
	
	@Before
	public void setUp() throws JrafDomainException, SOAPException {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSearchCallerByTelecom_CAPI() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		Mockito.doReturn(CAPI_CONSUMER).when(searchIndividualByMulticriteriaImpl).getConsumerId(Mockito.any());

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("+33446399053");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("960000000163", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}
	
	@Test
	public void testSearchCallerByTelecom_REPIND() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		Mockito.doReturn(REPIND_CONSUMER).when(searchIndividualByMulticriteriaImpl).getConsumerId(Mockito.any());

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("+33446399053");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("960000000163", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}
	
	@Test
	public void testSearchCallerByTelecom_SIC() throws BusinessErrorBlocBusinessException, JrafDomainException {
		
		Mockito.doReturn(SIC_CONSUMER).when(searchIndividualByMulticriteriaImpl).getConsumerId(Mockito.any());

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("+33446399053");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("960000000163", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}
	
	@Test
	public void testSearchCallerByTelecom_BAD_CONSUMER() throws JrafDomainException {
		
		Mockito.doReturn("w999999").when(searchIndividualByMulticriteriaImpl).getConsumerId(Mockito.any());

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("+33446399053");
		sibmRequest.setContact(contact);

		try {
			searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("Web service must return NOT FOUND");
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
	}
	
	@Test
	public void testSearchCallerByTelecom_BAD_CONTEXT() throws JrafDomainException {
		
		Mockito.doReturn(CAPI_CONSUMER).when(searchIndividualByMulticriteriaImpl).getConsumerId(Mockito.any());

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setContext("TEST");
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("+33446399053");
		sibmRequest.setContact(contact);

		try {
			searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("Web service must return NOT FOUND");
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
	}
	
	@Test
	public void testSearchCallerByTelecom_Population_C() throws JrafDomainException {
		
		Mockito.doReturn("w999999").when(searchIndividualByMulticriteriaImpl).getConsumerId(Mockito.any());

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		sibmRequest.setPopulationTargeted("C");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setContext(CONTEXT);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("+33446399053");
		sibmRequest.setContact(contact);

		try {
			searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("Web service must return NOT FOUND");
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel());
		}
	}
}
