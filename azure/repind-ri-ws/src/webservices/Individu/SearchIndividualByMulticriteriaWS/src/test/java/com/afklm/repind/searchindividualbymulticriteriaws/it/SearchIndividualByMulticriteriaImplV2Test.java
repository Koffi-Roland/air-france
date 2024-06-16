package com.afklm.repind.searchindividualbymulticriteriaws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.*;
import com.afklm.soa.stubs.w001271.v2.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w001271.v2.siccommontype.Requestor;
import com.afklm.soa.stubs.w001271.v2.sicindividutype.Contract;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.GregorianCalendar;
import java.util.Random;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class SearchIndividualByMulticriteriaImplV2Test {

	private static final Log logger = LogFactory.getLog(SearchIndividualByMulticriteriaImplV2Test.class);

	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String CONTEXT = "DECEASED_FOR_AMEX";
	
	@Autowired
	@Qualifier("passenger_SearchIndividualByMulticriteriaService-v2Bean")
	private SearchIndividualByMulticriteriaImpl searchIndividualByMulticriteriaRequestImplV2;

	
	private String generateString() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		for (int i = 0; i < 10; ++i) {
			char c = chars[rd.nextInt(chars.length)];
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	private String generateEmail() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		
		for (int i = 0; i < 12; ++i) {
			char c = chars[rd.nextInt(chars.length)];
			sb.append(c);
			sb.append(i == 7 ? "." : "");
		}
		sb.append("@bidon.fr");
		
		return sb.toString();
	}
	
	private String generatePhone() {
		char[] chars = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		sb.append("0");
		for (int i = 0; i < 9; ++i) {
			char c = chars[rd.nextInt(chars.length)];
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	@Test	// Missing Contact Email or Telecom 
	// NOT UP TO DATE SINCE REPIND-1675
	@Ignore
	public void searchIndividualByMulticriteria_ByNomPrenom_KO_133_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		// i.setCivility("M");
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		request.setSearchDriving("S");
		try {
			// SearchIndividualByMulticriteriaResponse response = 
			searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.assertTrue(false);			// Ce n est pas normal de ne pas avoir l'exception 133	
			
		} catch (Exception ex)  {
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			// Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());
			Assert.assertEquals("Missing parameter exception: Contact or Birthday", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());
		}

		logger.info("Test stop.");
	}


	@Test
	public void searchIndividualByMulticriteria_ByNomPrenomEmail_NOT_FOUND_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		// i.setCivility("M");
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("ddsd@dsd.fr");
		request.setContact(c);
		
		request.setSearchDriving("S");
		try {
			// SearchIndividualByMulticriteriaResponse response = 
			searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.assertTrue(false);			// Ce n est pas normal de ne pas avoir l'exception 133	
			
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}

		logger.info("Test stop.");
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void searchIndividualByMulticriteria_ByNomPrenomEmail_OK_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400390235581", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}


	@Test
	public void searchIndividualByMulticriteria_ByCiviliteNomPrenomEmail_OK_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400390235581", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}

	@Test
	public void searchIndividualByMulticriteria_ByCiviliteNomPrenomEmail_OK_ByUpperCaseTest() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("Adrien.Bouchikh@gmail.com");
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400390235581", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}

	// REPIND-1288 : Test on Upper in DB
	@Test
	public void searchIndividualByMulticriteria_ByCiviliteNomPrenomEmail_OK_ByUpperCaseOnLowerDBTest() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("W");
		i.setFirstNameSearchType("L");
		i.setLastName("KONIJNENBELT");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("MAIL@KONIJNENBELTWoTGEVING.NL");
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400518980900", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}

	@Test
	public void searchIndividualByMulticriteria_ByCiviliteNomPrenomEmail_OK_ByUpperCaseOnUpperDBTest() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("W");
		i.setFirstNameSearchType("L");
		i.setLastName("KONIJNENBELT");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("MAIL@KONIJNENBELTWiTGEVING.NL");
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400518833432", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}

	@Test
	@Ignore
	public void searchIndividualByMulticriteria_ByEmail_OK_ByUpperCaseOnUpperDBTest() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
/*		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("W");
		i.setFirstNameSearchType("L");
		i.setLastName("KONIJNENBELT");
		i.setLastNameSearchType("L");
		request.setIdentity(i);
*/
		Contact c = new Contact();
		c.setEmail("MAIL@KONIJNENBELTWiTGEVING.NL");
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("70", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400518980900", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}
	
	@Test
	public void searchIndividualByMulticriteria_ByCivilite2NomPrenomEmail_OK_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("M.");
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400390235581", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}
	
	@Test
	public void searchIndividualByMulticriteria_ByCiviliteNomPrenomEmail_NOT_FOUND_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("M");
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		request.setContact(c);
		
		request.setSearchDriving("S");
		try {
			// SearchIndividualByMulticriteriaResponse response = 
			searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.assertTrue(false);			// Ce n est pas normal de ne pas avoir l'exception 133	
			
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
		logger.info("Test stop.");
	}


	@Test
	public void searchIndividualByMulticriteria_With_InvalidCountry_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		// i.setCivility("M");
		i.setFirstName("FHUR");
		i.setFirstNameSearchType("S");
		i.setLastName("FTRHHT");
		i.setLastNameSearchType("S");
		request.setIdentity(i);
	
		
		Contact c = new Contact();
		
		c.setPhoneNumber("12345678");
		c.setCountryCode("99");				// Code faux
		
/*		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("ABC");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
*/		request.setContact(c);
		
		request.setSearchDriving("S");
		try {
			// SearchIndividualByMulticriteriaResponse response = 
			searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.assertTrue(false);			// Ce n est pas normal de ne pas avoir l'exception 133	
			
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Invalid country code: 99", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
		logger.info("Test stop.");
	}
	
	@Test
	@Ignore	// Dur d avoir plus de 255 resultats car il faut plus de 40% pour les remonter
	public void searchIndividualByMulticriteria_With_TooManyResult_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		// i.setCivility("M");
		i.setFirstName("PIERRE");
		i.setFirstNameSearchType("L");
		i.setLastName("MARTIN");
		i.setLastNameSearchType("L");
		request.setIdentity(i);
	
		
		Contact c = new Contact();
				
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("FR");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);

		request.setSearchDriving("L");
		try {
			// SearchIndividualByMulticriteriaResponse response = 
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.assertTrue(false);			// Ce n est pas normal de ne pas avoir l'exception 133	
			
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Invalid country code: 99", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
		logger.info("Test stop.");
	}
	
	@Test
	@Ignore 	// Problem occured only in RCT
	public void searchIndividualByMulticriteria_KeyOnly_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		request.setPopulationTargeted("A");		// TOUS
		request.setProcessType("A");			// AUTO
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("BAF");
		requestor.setSite("TLS");
		requestor.setSignature("HAC");
		requestor.setApplicationCode("ICI");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("ABONNE");
		i.setFirstNameSearchType("L");
		i.setLastName("AFFAIRES");
		i.setLastNameSearchType("L");
		i.setBirthday(new GregorianCalendar(1990, 8, 5).getTime());
		request.setIdentity(i);
	
		try {
			// SearchIndividualByMulticriteriaResponse response = 
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.assertTrue(false);			// Ce n est pas normal de ne pas avoir l'exception 133	
			
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Invalid country code: 99", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
		logger.info("Test stop.");
	}


	@Test
	@Ignore	// TU qui n'est plus d actualite - A ete corrige ou supprimer par les equipes Sales Force
	public void searchIndividualByMulticriteria_With_EmptyResult_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();

		request.setPopulationTargeted("A");
		request.setProcessType("A");
		
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("IPA");
		requestor.setSite("SALESFORCE");
		requestor.setSignature("T412211");
		requestor.setApplicationCode("CCH");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("Mr");
		i.setFirstName("PRATHAMESH");
		i.setFirstNameSearchType("S");
		i.setLastName("PARAB");
		i.setLastNameSearchType("P");
		request.setIdentity(i);
	
		
		Contact c = new Contact();
				
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("IN");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);

		request.setSearchDriving("A");
		
		
		Identification identification = new Identification();
		
		identification.setIdentificationType("Z");
		request.setIdentification(identification);
		
		try {
			// SearchIndividualByMulticriteriaResponse response = 
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getVisaKey());
			
			Assert.assertTrue(true);			// Ce n est pas normal de ne pas avoir l'exception 133	
			
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Invalid country code: 99", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}
		logger.info("Test stop.");
	}
	
	public void searchIndividualByMulticriteria_ByNomPrenomCountryCodeINVALID_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("PRATHAMESH");
		i.setFirstNameSearchType("S");
		i.setLastName("PARAB");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("IN");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);
		
		request.setSearchDriving("S");

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.assertTrue(false);			// Ce n est pas normal de ne pas avoir l'exception NOT FOUND
		
		} catch (Exception ex)  {
			
			Assert.assertNotNull((BusinessErrorBlocBusinessException) ex);
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo());
			Assert.assertNotNull(((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError());
			Assert.assertEquals("Individual not found: No individual found", ((BusinessErrorBlocBusinessException) ex).getFaultInfo().getBusinessError().getErrorDetail());	
		}

		logger.info("Test stop.");
	}

	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 20 secondes
	public void searchIndividualByMulticriteria_ByNomLPrenomSCountryCode_ICARE_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		// i.setCivility("MR");
		i.setFirstName("PETR");
		i.setFirstNameSearchType("S");
		i.setLastName("BEN");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("CZ");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("40", response.getIndividual().get(0).getRelevance());	// Relevance MAX a 40%
		// Assert.assertEquals("400437113240", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}
	
	/**
	 * Create a search request with an individual forgotten
	 * @result this method will be return a BusinessErrorCode
	 * 			BusinessErrorCode <code>ERROR_001</code>
	 */
	@Test
	public void testSearchIndividualByMulticriteriaWithIndividualTypeF(){
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
				
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setCivility("MR");
		i.setFirstName("GILBERT");
		i.setFirstNameSearchType("L");
		i.setLastName("SCHINDLER");
		i.setLastNameSearchType("L");
		i.setBirthday(new GregorianCalendar(1932, 04, 19).getTime());
		
		request.setSearchDriving("S");
		request.setIdentity(i);
		request.setPopulationTargeted("I");
		try {
		    searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			Assert.fail();
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals(e.getFaultInfo().getBusinessError().getErrorCode(), BusinessErrorCodeEnum.ERR_001);
		}
	}

	@Test
	// REPIND-988 : Trunc on the right the 3 digit to 2 digit for IVersion of ROLE_CONTRACT
	public void testSearchIndividualByMulticriteria_With_HugeContractNumber() throws BusinessErrorBlocBusinessException{

			logger.info("Test start...");
			
			SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
			// Preparing the request		
			Requestor requestor = new Requestor();
			requestor.setChannel(CHANNEL);
			requestor.setSite(SITE);
			requestor.setSignature(SIGNATURE);
			requestor.setApplicationCode("OSC");
			request.setRequestor(requestor);
			
			Identity i = new Identity();
			i.setCivility("MR");
			i.setFirstName("THIERRY");
			i.setFirstNameSearchType("L");
			i.setLastName("BAUMANN");
			i.setLastNameSearchType("L");
			request.setIdentity(i);

			Contact c = new Contact();
			c.setEmail("tba@triax.com");
			request.setContact(c);
			
			request.setSearchDriving("S");

			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getVisaKey());
			Assert.assertNotNull(response.getIndividual());
			Assert.assertTrue(response.getIndividual().size() > 0);
			Assert.assertNotNull(response.getIndividual().get(0));
			Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
			Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
			Assert.assertEquals("000000086051", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
			
			
			Assert.assertNotNull(response.getIndividual().get(0).getContractResponse());
			
			for (Contract cr :  response.getIndividual().get(0).getContractResponse().getContract()) {
				Assert.assertNotNull(cr);
				Assert.assertNotNull(cr.getVersion());
				Assert.assertTrue(cr.getVersion().length() <= 2);
			}

			logger.info("Test stop.");
		}

	@Test
	public void searchIndividualByMulticriteria_ByNomLPrenomSCountryCode_BugCIRRUS_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("CBS");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		// i.setCivility("MR");
		i.setFirstName("MICHEL");
		i.setFirstNameSearchType("L");
		i.setLastName("FRANCOIS");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("FR");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);
		
		request.setSearchDriving("L");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertEquals(51, response.getIndividual().size());

		logger.info("Test stop.");
	}

	@Test
	public void searchIndividualByMulticriteria_ByNomLPrenomSCountryCode_BugCIRRUS2_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		request.setPopulationTargeted("A");
		request.setProcessType("A");
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("CBS");
		requestor.setSite("QVI");
		requestor.setSignature("CBS");
		requestor.setApplicationCode("CBS");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		// i.setCivility("MR");
		i.setFirstName("MICHEL");
		i.setFirstNameSearchType("L");
		i.setLastName("FRANCOIS");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("FR");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);
		
		request.setSearchDriving("A");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertEquals(51, response.getIndividual().size());

		logger.info("Test stop.");
	}
	
	@Test
	public void searchIndividualByMulticriteria_ByNomLPrenomLCountryCode_MoreThan100_Auto_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		request.setPopulationTargeted("A");
		request.setProcessType("A");
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("CBS");
		requestor.setSite("QVI");
		requestor.setSignature("CBS");
		requestor.setApplicationCode("CBS");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("FRANCOIS");
		i.setFirstNameSearchType("L");
		i.setLastName("MICHEL");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("FR");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);
		
		request.setSearchDriving("A");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertEquals(45, response.getIndividual().size());

		logger.info("Test stop.");
	}

	@Test
	// The max will be reach, return will be a 100 max size list
	public void searchIndividualByMulticriteria_ByNomLPrenomLCountryCode_MoreThan100_Manual_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		request.setPopulationTargeted("A");
		request.setProcessType("M");			
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("CBS");
		requestor.setSite("QVI");
		requestor.setSignature("CBS");
		requestor.setApplicationCode("CBS");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("FRANCOIS");
		i.setFirstNameSearchType("L");
		i.setLastName("MICHEL");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("FR");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		request.setContact(c);
		
		request.setSearchDriving("A");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertEquals(100, response.getIndividual().size());
		
		logger.info("Test stop.");
	}
	

	@Test
	public void searchIndividualByMulticriteria_ByNomSPrenomSDateOfBirth_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		request.setPopulationTargeted("A");
		request.setProcessType("M");			
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel("CBS");
		requestor.setSite("QVI");
		requestor.setSignature("CBS");
		requestor.setApplicationCode("CBS");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("LAURENCE");
		i.setFirstNameSearchType("S");
		i.setLastName("TISSIER");
		i.setLastNameSearchType("S");
		i.setBirthday(new GregorianCalendar(1961, 2, 29).getTime());
		
		request.setIdentity(i);

		
		
		request.setSearchDriving("A");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertEquals(1, response.getIndividual().size());
		
		logger.info("Test stop.");
	}


	// REPIND-1286 : ContractNumber Failed because 82 in RCT and limit is 25
	@Test
	public void searchIndividualByMulticriteria_ByNomPrenomAdrPost_Test() throws BusinessErrorBlocBusinessException, SystemException {

		logger.info("Test start...");
		
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
		
		// Preparing the request		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
//		i.setCivility("MR");
		i.setFirstName("ABONNE");
		i.setFirstNameSearchType("L");
		i.setLastName("AFFAIRES");
		i.setLastNameSearchType("L");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pab = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setCountryCode("FR");
		pac.setCitySearchType("S");
		pac.setZipCodeSearchType("S");
		pab.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pab);
		
		c.setCountryCode("FR");
		
		request.setContact(c);
		
		request.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);

		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("50", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400496470834", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

		logger.info("Test stop.");
	}
	

	@Test
	@Transactional
	public void searchIndividualByMulticriteria_ByNomPrenomEmail_Dead() {

		logger.info("Test start...");

		/* FIRST REQUEST WITHOUT CONTEXT */
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CAROLINE");
		i.setLastName("LE PEN");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("lepenc@yahoo.fr");
		request.setContact(c);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("ERR_001", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel().toString());
		}

		/* SECOND REQUEST WITH CONTEXT */
		requestor.setContext(CONTEXT);
		
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertFalse("An individual must be found",true);
		}
		
		Assert.assertNotNull(response.getIndividual());
		
		logger.info("Test stop.");
	}
	
	@Test
	@Transactional
	public void searchIndividualByMulticriteria_ByNomPrenomPhone_Dead() {

		logger.info("Test start...");

		/* FIRST REQUEST WITHOUT CONTEXT */
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CAROLINE");
		i.setLastName("LE PEN");
		request.setIdentity(i);

		Contact c = new Contact();
		c.setPhoneNumber("+33664904104");
		request.setContact(c);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("ERR_001", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel().toString());
		}

		/* SECOND REQUEST WITH CONTEXT */
		requestor.setContext(CONTEXT);
		
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertFalse("An individual must be found",true);
		}
		
		Assert.assertNotNull(response.getIndividual());
		
		logger.info("Test stop.");
	}
	
	@Test
	@Transactional
	public void searchIndividualByMulticriteria_ByNomPrenomBirthday_Dead() {

		logger.info("Test start...");

		/* FIRST REQUEST WITHOUT CONTEXT */
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CAROLINE");
		i.setLastName("LE PEN");
		i.setBirthday(new GregorianCalendar(1982, 05, 12).getTime());
		request.setIdentity(i);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("ERR_001", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel().toString());
		}

		/* SECOND REQUEST WITH CONTEXT */
		requestor.setContext(CONTEXT);
		
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertFalse("An individual must be found",true);
		}
		
		Assert.assertNotNull(response.getIndividual());
		
		logger.info("Test stop.");
	}
	
	@Test
	@Transactional
	public void searchIndividualByMulticriteria_ByNomPrenomAddress_Dead() {

		logger.info("Test start...");

		/* FIRST REQUEST WITHOUT CONTEXT */
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CAROLINE");
		i.setLastName("LE PEN");
		request.setIdentity(i);

		Contact c = new Contact();
		PostalAddressBloc pa = new PostalAddressBloc();
		PostalAddressContent pac = new PostalAddressContent();
		pac.setNumberAndStreet("99 RUE DU BOIS HARDY");
		pac.setCity("NANTES");
		pac.setZipCode("44100");
		pac.setCountryCode("FR");
		pa.setPostalAddressContent(pac);
		c.setPostalAddressBloc(pa);
		request.setContact(c);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("ERR_001", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel().toString());
		}

		/* SECOND REQUEST WITH CONTEXT */
		requestor.setContext(CONTEXT);
		
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertFalse("An individual must be found",true);
		}
		
		Assert.assertNotNull(response.getIndividual());
		
		logger.info("Test stop.");
	}
	
	@Test
	@Transactional
	public void searchIndividualByMulticriteria_By_Email_NOTFOUND() {

		logger.info("Test start...");

		/* FIRST REQUEST WITHOUT CONTEXT */
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);

		Contact c = new Contact();
		c.setEmail("uniqueemail@gmail.com");
		request.setContact(c);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			
			Assert.fail("We should have a NOT FOUND EXCEPTION");
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.assertEquals("ERR_001", e.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("NOT FOUND", e.getFaultInfo().getBusinessError().getErrorLabel().toString());
		}

		logger.info("Test stop.");
	}	
	
	@Test
	@Transactional
	public void searchIndividualByMulticriteria_By_Email_ONE() {

		logger.info("Test start...");

		/* FIRST REQUEST WITHOUT CONTEXT */
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);

		Contact c = new Contact();
		c.setEmail("luc.lanoue@aero.bombardier.com");
		request.setContact(c);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividual());
			Assert.assertEquals(1, response.getIndividual().size());
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("We should not have an EXCEPTION");
		}

		logger.info("Test stop.");
	}	
	
	
	
	@Test
	@Transactional
	public void searchIndividualByMulticriteria_By_Email_TWO() {

		logger.info("Test start...");

		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);

		Contact c = new Contact();
		c.setEmail("j-rodriguez@aldeasa.es");
		request.setContact(c);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividual());
			Assert.assertEquals(2, response.getIndividual().size());
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("We should not have an EXCEPTION");
		}
		
		logger.info("Test stop.");
	}	


	@Test
	@Transactional
	public void searchIndividualByMulticriteria_By_Email_THREE() {

		logger.info("Test start...");

		/* FIRST REQUEST WITHOUT CONTEXT */
		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);

		Contact c = new Contact();
		c.setEmail("arnoultcdo@hotmail.fr");
		request.setContact(c);

		SearchIndividualByMulticriteriaResponse response = null;
		try {
			response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
			
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getIndividual());
			Assert.assertEquals(3, response.getIndividual().size());
			
		} catch (BusinessErrorBlocBusinessException e) {
			Assert.fail("We should not have an EXCEPTION");
		}
		
		logger.info("Test stop.");
	}	

	// REPIND-1675 : Add only FirstName / LastName search with lower relevancy 

	@Test
	public void searchIndividualByMulticriteria_ByNomS_PrenomS_40() throws BusinessErrorBlocBusinessException {
		logger.info("Test start...");

		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CAROLINE");
		i.setFirstNameSearchType("S");
		i.setLastName("LE PEN");
		i.setLastNameSearchType("S");
		request.setIdentity(i);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		
		Assert.assertNotNull(response.getIndividual());
		Assert.assertEquals(1, response.getIndividual().size());
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertEquals("40", response.getIndividual().get(0).getRelevance());
		
		logger.info("Test stop.");
	}

	@Test
	public void searchIndividualByMulticriteria_ByNomL_PrenomL_25() throws BusinessErrorBlocBusinessException {
		logger.info("Test start...");

		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CARO");
		i.setFirstNameSearchType("L");
		i.setLastName("LE PE");
		i.setLastNameSearchType("L");
		request.setIdentity(i);
		request.setProcessType("M");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		
		Assert.assertNotNull(response.getIndividual());
		Assert.assertEquals(11, response.getIndividual().size());
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertEquals("25", response.getIndividual().get(0).getRelevance());
		
		logger.info("Test stop.");
	}

	@Test
	public void searchIndividualByMulticriteria_ByNomS_PrenomL_35() throws BusinessErrorBlocBusinessException {
		logger.info("Test start...");

		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CARO");
		i.setFirstNameSearchType("L");
		i.setLastName("LE PEN");
		i.setLastNameSearchType("S");
		request.setIdentity(i);
		request.setProcessType("M");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		
		Assert.assertNotNull(response.getIndividual());
		Assert.assertEquals(1, response.getIndividual().size());
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertEquals("35", response.getIndividual().get(0).getRelevance());
		
		logger.info("Test stop.");
	}

	@Test
	public void searchIndividualByMulticriteria_ByNomL_PrenomS_30() throws BusinessErrorBlocBusinessException {
		logger.info("Test start...");

		SearchIndividualByMulticriteriaRequest request = new SearchIndividualByMulticriteriaRequest();
			
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSite(SITE);
		requestor.setSignature(SIGNATURE);
		requestor.setApplicationCode("OSC");
		request.setRequestor(requestor);
		
		Identity i = new Identity();
		i.setFirstName("CAROLINE");
		i.setFirstNameSearchType("S");
		i.setLastName("LE PE");
		i.setLastNameSearchType("L");
		request.setIdentity(i);
		request.setProcessType("M");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaRequestImplV2.searchIndividual(request);
		
		Assert.assertNotNull(response.getIndividual());
		Assert.assertEquals(8, response.getIndividual().size());
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertEquals("30", response.getIndividual().get(0).getRelevance());
		
		logger.info("Test stop.");
	}
}

