package com.afklm.repind.searchindividualbymulticriteriaws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.request.Identity;
import com.afklm.soa.stubs.w001271.v2.siccommontype.Requestor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//@Ignore
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class SearchIndividualByMulticriteriaForTravelerImplTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String COMPANY = "AF";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_SearchIndividualByMulticriteriaService-v2Bean")
	private SearchIndividualByMulticriteriaImpl searchIndividualByMulticriteriaImpl;
	
	@Test
	public void testSearchIndividualByNameFirstNameAndEmail_Individu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("PIETER");
		identity.setLastName("WIERSMA");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		contact.setEmail("pieter.wiersma@gmail.com");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("400476889480", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndEmail_Traveler() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("PIETER");
		identity.setLastName("WIERSMA");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		contact.setEmail("pieter.wiersma@gmail.com");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("400476889480", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndEmail_Traveler_LikeOK() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("PIETER");
		identity.setFirstNameSearchType("L");
		identity.setLastName("WIERSMA");
		identity.setLastNameSearchType("L");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		contact.setEmail("pieter.wiersma@gmail.com");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("400476889480", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}
	
	@Test
	public void testSearchIndividualByNameFirstNameAndEmail_Traveler_LikeKO() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("TRAVELERLJGSBXYRX");
		identity.setLastName("TRAVELERSODRHIWRH");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		contact.setEmail("fcstkqmz.mbxt@bidon.fr");
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("KO, on devrait avoir une NotFoundException");
			
		} catch (BusinessErrorBlocBusinessException bbex) {
			
			Assert.assertNotNull(bbex);
			Assert.assertNotNull(bbex.getFaultInfo());
			Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
			Assert.assertEquals("ERR_001", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", bbex.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Individual not found: No individual found", bbex.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndTelecom_Traveler() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("CLARK");
		identity.setLastName("KENT");
		sibmRequest.setIdentity(identity);

		Contact contact = new Contact();
		contact.setCountryCode("1");
		contact.setPhoneNumber("4507411581");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("400415094784", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndTelecom_Traveler_LikeNomOK() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		sibmRequest.setProcessType("M");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);		
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("CLARK");
		identity.setFirstNameSearchType("S");
		identity.setLastName("KENT");	// P
		identity.setLastNameSearchType("L");
		sibmRequest.setIdentity(identity);

		Contact contact = new Contact();
		contact.setCountryCode("1");
		contact.setPhoneNumber("4507411581");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("400415094784", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndTelecom_Traveler_LikePrenomOK() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		sibmRequest.setProcessType("M");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);		
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("CLARK");
		identity.setFirstNameSearchType("L");
		identity.setLastName("KENT");
		identity.setLastNameSearchType("S");
		sibmRequest.setIdentity(identity);

		Contact contact = new Contact();
		contact.setCountryCode("1");
		contact.setPhoneNumber("4507411581");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("400415094784", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndTelecom_Traveler_LikeKO() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("TRAVEHSHSGRXBA");
		identity.setLastName("TRAVEHMUQENVTY");
		sibmRequest.setIdentity(identity);

		Contact contact = new Contact();
		contact.setPhoneNumber("0410668491");
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("KO, on devrait avoir une NotFoundException");
			
		} catch (BusinessErrorBlocBusinessException bbex) {
			
			Assert.assertNotNull(bbex);
			Assert.assertNotNull(bbex.getFaultInfo());
			Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
			Assert.assertEquals("ERR_001", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", bbex.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Individual not found: No individual found", bbex.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}
	
	
	@Test
	public void testSearchIndividualByNameFirstNameAndEmail_Traveler_NOT_FOUND() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		sibmRequest.setProcessType("M");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);		
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("MIEP");
		identity.setFirstNameSearchType("S");
		identity.setLastName("MAANDAG");
		identity.setLastNameSearchType("S");
		sibmRequest.setIdentity(identity);

		Contact contact = new Contact();
		contact.setEmail("lieneke.bouwman@klm.com");
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("KO, on devrait avoir une NotFoundException");
			
		} catch (BusinessErrorBlocBusinessException bbex) {
			
			Assert.assertNotNull(bbex);
			Assert.assertNotNull(bbex.getFaultInfo());
			Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
			Assert.assertEquals("ERR_001", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", bbex.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Individual not found: No individual found", bbex.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}
	
	@Test
	public void testSearchIndividualByNameFirstNameAndPhone_Traveler_NOT_FOUND() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		sibmRequest.setProcessType("M");
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);		
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("JEAN BON");
		identity.setFirstNameSearchType("S");
		identity.setLastName("TRAVELERTESTEEEII");
		identity.setLastNameSearchType("S");
		sibmRequest.setIdentity(identity);

		Contact contact = new Contact();
		contact.setPhoneNumber("0999887766");
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("KO, on devrait avoir une NotFoundException");
			
		} catch (BusinessErrorBlocBusinessException bbex) {
			
			Assert.assertNotNull(bbex);
			Assert.assertNotNull(bbex.getFaultInfo());
			Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
			Assert.assertEquals("ERR_001", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", bbex.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Individual not found: No individual found", bbex.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}
}
