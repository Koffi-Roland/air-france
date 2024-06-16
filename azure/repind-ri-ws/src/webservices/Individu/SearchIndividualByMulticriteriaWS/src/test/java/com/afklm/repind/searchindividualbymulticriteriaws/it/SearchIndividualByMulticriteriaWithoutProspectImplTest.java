package com.afklm.repind.searchindividualbymulticriteriaws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.request.Identity;
import com.afklm.soa.stubs.w001271.v2.request.PostalAddressBloc;
import com.afklm.soa.stubs.w001271.v2.request.PostalAddressContent;
import com.afklm.soa.stubs.w001271.v2.response.Individual;
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

import java.util.GregorianCalendar;

import static org.junit.Assert.assertTrue;

//@Ignore
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class SearchIndividualByMulticriteriaWithoutProspectImplTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String COMPANY = "AF";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_SearchIndividualByMulticriteriaService-v2Bean")
	private SearchIndividualByMulticriteriaImpl searchIndividualByMulticriteriaImpl;
	
	@Test
	public void testSearchIndividualByNameFirstNameAndEmail_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();		
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("ALEXANDER");
		identity.setLastName("SHARP");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		contact.setEmail("alex.sharp@youniche.com");
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
			Assert.fail("On ne doit pas trouver le prospect car on cherche dans Individu I");
		
		} catch (Exception ex)  {
		
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
	}


	@Test
	public void testSearchIndividualByNameFirstNameAndPhone_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("JEAN BON");
		identity.setLastName("TESTTEST");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("0978541253");
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
			Assert.fail("On ne doit pas trouver le prospect car on cherche dans Individu I");
		
		} catch (Exception ex)  {
	
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
	}
	
	@Test
	public void testSearchIndividualByNameFirstNameAndAdrPost_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("FLUTEAUX");
		identity.setLastName("EMILIE");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		
		
		PostalAddressBloc pab = new PostalAddressBloc();
		
		PostalAddressContent pac = new PostalAddressContent();

		pac.setNumberAndStreet("14 RUE JEAN DE LA FONTAINE");
		pac.setCitySearchType("S");
		pac.setCity("CRETEIL");
		pac.setZipCodeSearchType("S");
		pac.setZipCode("940");
		pac.setCountryCode("FR");
		
		pab.setPostalAddressContent(pac);
		contact.setPostalAddressBloc(pab);
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
			Assert.fail("On ne doit pas trouver le prospect car on cherche dans Individu I");
			
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndAdrPostCodePost_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("YINGXIONG");
		identity.setLastName("FAN");
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		
		
		PostalAddressBloc pab = new PostalAddressBloc();
		
		PostalAddressContent pac = new PostalAddressContent();

		pac.setZipCodeSearchType("S");
		pac.setZipCode("300387");
		pac.setCountryCode("CN");
		
		pab.setPostalAddressContent(pac);
		contact.setPostalAddressBloc(pab);
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
			Assert.fail("On ne doit pas trouver le prospect car on cherche dans Individu I");
		
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
	}

	@Test
	public void testSearchIndividualByNameFirstNameAndAdrPostCodeCountry_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("SIMONMA");
		identity.setLastName("SUN");
		
		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		
		
		PostalAddressBloc pab = new PostalAddressBloc();
		
		PostalAddressContent pac = new PostalAddressContent();

		pac.setCountryCode("CN");
		
		pab.setPostalAddressContent(pac);
		contact.setPostalAddressBloc(pab);
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
			Assert.fail("On ne doit pas trouver le prospect car on cherche dans Individu I");
		
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
	}

	@Test
	public void testSearchIndividualByEmail_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();		
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
/*		Identity identity= new Identity();
		identity.setFirstName("ALEXANDER");
		identity.setLastName("SHARP");
		sibmRequest.setIdentity(identity);
*/		
		Contact contact = new Contact();
		contact.setEmail("alex.sharp@youniche.com");
		sibmRequest.setContact(contact);

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
			Assert.fail("On ne doit pas trouver le prospect car on cherche dans Individu I");
		
		} catch (Exception ex)  {
		
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
	}
	
	@Test
	public void testSearchIndividualByPhone_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
//		Identity identity= new Identity();
//		identity.setFirstName("JEAN BON");
//		identity.setLastName("TESTTEST");
//		sibmRequest.setIdentity(identity);
		
		Contact contact = new Contact();
		contact.setCountryCode("33");
		contact.setPhoneNumber("0978541253");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		

		boolean retour = true;
		for (Individual i : response.getIndividual()) {
			// If we find a Prospect Only
			if ("900027011902".equals(i.getIndividualInformations().getIdentifier())) {
				retour = false;
			}
		}
		assertTrue(retour);
	}

	@Test
	public void testSearchIndividualByNomPrenomDateNaissance_ProspectInIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setPopulationTargeted("I");		// On ne cherche que dans les Individus
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		sibmRequest.setRequestor(requestor);
		
		Identity identity= new Identity();
		identity.setFirstName("PATRICIA ALEJANDRA");
		identity.setLastName("DIAZ");
		identity.setBirthday(new GregorianCalendar(1973, 6, 7).getTime());	// 07/07/1973 00:00:00
		sibmRequest.setIdentity(identity);
		
		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		} catch (Exception ex)  {
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
	}
}
