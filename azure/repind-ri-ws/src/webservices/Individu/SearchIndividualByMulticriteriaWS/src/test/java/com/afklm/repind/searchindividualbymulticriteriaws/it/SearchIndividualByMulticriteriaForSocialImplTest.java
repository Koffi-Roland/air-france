package com.afklm.repind.searchindividualbymulticriteriaws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.request.Identification;
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
public class SearchIndividualByMulticriteriaForSocialImplTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String COMPANY = "AF";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_SearchIndividualByMulticriteriaService-v2Bean")
	private SearchIndividualByMulticriteriaImpl searchIndividualByMulticriteriaImpl;
	
	@Test
	// REPIND-1286 : Bloc identification must be not null or not empty
	public void testSearchIndividualBy_TypeValueNotFound() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
// 		i.setIdentificationType("PD");
//		i.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(i);		

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("KO, on devrait avoir une Exception");
			
		} catch (BusinessErrorBlocBusinessException bbex) {
			
			Assert.assertNotNull(bbex);
			Assert.assertNotNull(bbex.getFaultInfo());
			Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
			Assert.assertEquals("ERR_133", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("MISSING PARAMETERS", bbex.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Missing parameter exception: LastName/FirstName or Email or Phone or identification is missing", bbex.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	// REPIND-1286 : Bloc identification must be not null or not empty
	public void testSearchIndividualBy_TypeValueEmpty() throws BusinessErrorBlocBusinessException {

		// Preparing the request		
		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();		
		sibmRequest.setRequestor(initRequestor());
		
		Identification id = new Identification();
		
// 		i.setIdentificationType("PD");
//		i.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(id);		
		
		Identity i = new Identity();
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		sibmRequest.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		sibmRequest.setContact(c);
		
		sibmRequest.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400390235581", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}
	
	@Test
	// REPIND-1286 : Bloc identification must be not null or not empty
	public void testSearchIndividualBy_TypeValueNotEmpty() throws BusinessErrorBlocBusinessException {

		// Preparing the request		
		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();		
		sibmRequest.setRequestor(initRequestor());
		
		Identification id = new Identification();
		
 		id.setIdentificationType("FB");
		id.setIdentificationValue("1220653541358405");		
		
		sibmRequest.setIdentification(id);		
		
		Identity i = new Identity();
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		sibmRequest.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		sibmRequest.setContact(c);
		
		sibmRequest.setSearchDriving("S");

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400390235581", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}
	
	@Test
	// REPIND-1286 : Bloc identification must be not null or not empty
	public void testSearchIndividualBy_TypeEmpty() throws BusinessErrorBlocBusinessException {

		// Preparing the request		
		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();		
		sibmRequest.setRequestor(initRequestor());
		
		Identification id = new Identification();
		
// 		i.setIdentificationType("PD");
		id.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(id);		
		
		Identity i = new Identity();
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		sibmRequest.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		sibmRequest.setContact(c);
		
		sibmRequest.setSearchDriving("S");
try {
		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		
		Assert.assertNotNull(response);		
		Assert.assertNotNull(response.getVisaKey());
		Assert.assertNotNull(response.getIndividual());
		Assert.assertTrue(response.getIndividual().size() > 0);
		Assert.assertNotNull(response.getIndividual().get(0));
		Assert.assertNotNull(response.getIndividual().get(0).getIndividualInformations());
		Assert.assertEquals("100", response.getIndividual().get(0).getRelevance());
		Assert.assertEquals("400390235581", response.getIndividual().get(0).getIndividualInformations().getIdentifier());

	
	} catch (BusinessErrorBlocBusinessException bbex) {
		
		Assert.assertNotNull(bbex);
		Assert.assertNotNull(bbex.getFaultInfo());
		Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
		Assert.assertEquals("ERR_133", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
		Assert.assertEquals("MISSING PARAMETERS", bbex.getFaultInfo().getBusinessError().getErrorLabel());
		Assert.assertEquals("type or identification could not be empty", bbex.getFaultInfo().getBusinessError().getErrorDetail());
	}
	
	}
	
	@Test
	// REPIND-1286 : Bloc identification must be not null or not empty
	public void testSearchIndividualBy_ValueEmpty() throws BusinessErrorBlocBusinessException {

		// Preparing the request		
		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();		
		sibmRequest.setRequestor(initRequestor());
		
		Identification id = new Identification();
		
// 		i.setIdentificationType("PD");
		id.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(id);		
		
		Identity i = new Identity();
		i.setFirstName("ADRIEN");
		i.setFirstNameSearchType("L");
		i.setLastName("BOUCHIKH");
		i.setLastNameSearchType("L");
		sibmRequest.setIdentity(i);

		Contact c = new Contact();
		c.setEmail("adrien.bouchikh@gmail.com");
		sibmRequest.setContact(c);
		
		sibmRequest.setSearchDriving("S");
try {
		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
		Assert.fail("KO, on devrait avoir une Exception");
		
	
	} catch (BusinessErrorBlocBusinessException bbex) {
		
		Assert.assertNotNull(bbex);
		Assert.assertNotNull(bbex.getFaultInfo());
		Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
		Assert.assertEquals("ERR_133", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
		Assert.assertEquals("MISSING PARAMETERS", bbex.getFaultInfo().getBusinessError().getErrorLabel());
		Assert.assertEquals("type or identification could not be empty", bbex.getFaultInfo().getBusinessError().getErrorDetail());
	}
	
	}

	@Test
	// REPIND-1264 : Add Search for Social by Type and Identifier
	public void testSearchIndividualBy_TypeNotFound() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("PD");
		i.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(i);		

		try {
			SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);
			Assert.fail("KO, on devrait avoir une Exception");
			
		} catch (BusinessErrorBlocBusinessException bbex) {
			
			Assert.assertNotNull(bbex);
			Assert.assertNotNull(bbex.getFaultInfo());
			Assert.assertNotNull(bbex.getFaultInfo().getBusinessError() );
			Assert.assertEquals("ERR_905", bbex.getFaultInfo().getBusinessError().getErrorCode().toString());
			Assert.assertEquals("TECHNICAL ERROR", bbex.getFaultInfo().getBusinessError().getErrorLabel());
			Assert.assertEquals("Invalid external identifier data key: PD", bbex.getFaultInfo().getBusinessError().getErrorDetail());
		}
	}

	@Test
	public void testSearchIndividualByPNM_IndividuNotFound() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("PNM");
		i.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(i);		

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
	public void testSearchIndividualByPNM_Individu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("PNM");
		i.setIdentificationValue("697C5C42-F998-43D5-ABAC-40D8516BA7E7");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());		
		assertEquals("800007773271", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}

	@Test
	public void testSearchIndividualByPNM_Individu_EspaceAvant() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("PNM");
		i.setIdentificationValue(" 697C5C42-F998-43D5-ABAC-40D8516BA7E7");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());		
		assertEquals("800007773271", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}
	
	@Test
	public void testSearchIndividualByPNM_MultiIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("PNM");
		i.setIdentificationValue("DBA4BE8D-B19B-459C-9B2C-20555A970C59");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(5, response.getIndividual().size());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}

	public void testSearchIndividualByGIGYA_IndividuNotFound() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
		
		Identification i = new Identification();
		
		i.setIdentificationType("GID");
		i.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(i);		

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
	public void testSearchIndividualByGIGYA_SocialIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
		
		Identification i = new Identification();
		
		i.setIdentificationType("GID");
		i.setIdentificationValue("AFKL_201412110804__guid_mVpKxEm7MlyZMSgIEd-6nAvD09SlOZ7SrOQl");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());		
		assertEquals("400396381846", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}

	@Test
	public void testSearchIndividualByGIGYA_SocialIndividu_EspaceApres() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
		
		Identification i = new Identification();
		
		i.setIdentificationType("GID");
		i.setIdentificationValue("AFKL_201412110804__guid_mVpKxEm7MlyZMSgIEd-6nAvD09SlOZ7SrOQl ");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());		
		assertEquals("400396381846", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}
	
	@Test
	public void testSearchIndividualByGIGYA_MultiExternalIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("GID");
		i.setIdentificationValue("AFKL_2018659142__guid_oz6rAt22WEzdwPIGDFR63Sjgkk8zD");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(3, response.getIndividual().size());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}
	

	@Test
	public void testSearchIndividualByGIGYA_MultiExternalSocialIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("GID");
		i.setIdentificationValue("AFKL_201503130948__guid_E3Rj2rPFS8p7SsON3X_8hhz5jFtQcp3i6lEY");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(2, response.getIndividual().size());
		
		// One is coming from SOCIAL NETWORK DATA
		// One is coming from EXTERNAL IDENTIFIER DATA				
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}
	
	public void testSearchIndividualByTWITTER_IndividuNotFound() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
		
		Identification i = new Identification();
		
		i.setIdentificationType("TWT");
		i.setIdentificationValue("111-111");		
		
		sibmRequest.setIdentification(i);		

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
	public void testSearchIndividualByTWITTER_MultiIndividu() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		
		i.setIdentificationType("TWT");
		i.setIdentificationValue("TWITTER_ID_6465646684sfsdf64sdf64sd6f4q4gsdfg6qs4f8s4ffg");		
		
		sibmRequest.setIdentification(i);		

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(5, response.getIndividual().size());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}

	// REPIND-1495 : Add a 100% For Social with NAME
	@Test
	public void testSearchIndividualByNameAndTWITTER() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		i.setIdentificationType("TWT");
		i.setIdentificationValue("39708107@RectifiedSpirit");		
		sibmRequest.setIdentification(i);
		
		Identity id = new Identity();
		id.setFirstName("HILDA ELISABETH");
		id.setFirstNameSearchType("S");
		id.setLastName("AMERICA");
		id.setLastNameSearchType("S");
		sibmRequest.setIdentity(id);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertNotNull(response.getIndividual().get(0));
		assertEquals("400419157691", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("100", response.getIndividual().get(0).getRelevance());
	}
	

	@Test
	public void testSearchIndividualByTWITTER() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		i.setIdentificationType("TWT");
		i.setIdentificationValue("39708107@RectifiedSpirit");		
		sibmRequest.setIdentification(i);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertNotNull(response.getIndividual().get(0));
		assertEquals("400419157691", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}

	@Test
	public void testSearchIndividualByNameAndFACEBOOK() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		i.setIdentificationType("FB");
		i.setIdentificationValue("cd5d4443-7329-4e52-9809-3819960c7041");		
		sibmRequest.setIdentification(i);
		
		Identity id = new Identity();
		id.setFirstName("JEAN CLAUDE");
		id.setFirstNameSearchType("S");
		id.setLastName("LEDET");
		id.setLastNameSearchType("S");
		sibmRequest.setIdentity(id);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertNotNull(response.getIndividual().get(0));
		assertEquals("400281577823", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("100", response.getIndividual().get(0).getRelevance());
	}

	@Test
	public void testSearchIndividualByNameAndFACEBOOK_NLike() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		i.setIdentificationType("FB");
		i.setIdentificationValue("cd5d4443-7329-4e52-9809-3819960c7041");		
		sibmRequest.setIdentification(i);
		
		Identity id = new Identity();
		id.setFirstName("JEAN");
		id.setFirstNameSearchType("L");
		id.setLastName("LEDET");
		id.setLastNameSearchType("S");
		sibmRequest.setIdentity(id);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertNotNull(response.getIndividual().get(0));
		assertEquals("400281577823", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}
	
	@Test
	public void testSearchIndividualByNameAndFACEBOOK_FLike() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		i.setIdentificationType("FB");
		i.setIdentificationValue("cd5d4443-7329-4e52-9809-3819960c7041");		
		sibmRequest.setIdentification(i);
		
		Identity id = new Identity();
		id.setFirstName("JEAN CLAUDE");
		id.setFirstNameSearchType("S");
		id.setLastName("LED");
		id.setLastNameSearchType("L");
		sibmRequest.setIdentity(id);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertNotNull(response.getIndividual().get(0));
		assertEquals("400281577823", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}

	@Test
	public void testSearchIndividualByFACEBOOK() throws BusinessErrorBlocBusinessException {

		SearchIndividualByMulticriteriaRequest sibmRequest = new SearchIndividualByMulticriteriaRequest();
		sibmRequest.setRequestor(initRequestor());
				
		Identification i = new Identification();
		i.setIdentificationType("FB");
		i.setIdentificationValue("cd5d4443-7329-4e52-9809-3819960c7041");		
		sibmRequest.setIdentification(i);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertNotNull(response.getIndividual().get(0));
		assertEquals("400281577823", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
		assertEquals("90", response.getIndividual().get(0).getRelevance());
	}
	
	// Nominal initialisation of the Requestor 
	private Requestor initRequestor() {
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);
		return requestor;
	}
}
