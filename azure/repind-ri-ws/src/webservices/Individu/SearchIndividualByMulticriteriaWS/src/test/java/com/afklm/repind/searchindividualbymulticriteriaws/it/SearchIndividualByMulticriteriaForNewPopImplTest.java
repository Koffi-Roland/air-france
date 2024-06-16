package com.afklm.repind.searchindividualbymulticriteriaws.it;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.searchindividualbymulticriteriaws.SearchIndividualByMulticriteriaImpl;
import com.afklm.soa.stubs.w001271.v2.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaRequest;
import com.afklm.soa.stubs.w001271.v2.data.SearchIndividualByMulticriteriaResponse;
import com.afklm.soa.stubs.w001271.v2.request.Contact;
import com.afklm.soa.stubs.w001271.v2.request.Identity;
import com.afklm.soa.stubs.w001271.v2.siccommontype.Requestor;
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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class SearchIndividualByMulticriteriaForNewPopImplTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T730890";
	private static final String SITE = "QVI";
	private static final String COMPANY = "AF";
	private static final String APP_CODE = "GRC";
	
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
		identity.setFirstName("CATHERINE");
		identity.setLastName("HERRERA");
		sibmRequest.setIdentity(identity);
		Contact contact = new Contact();
		contact.setEmail("herrera.catherine@yahoo.com");
		sibmRequest.setContact(contact);

		SearchIndividualByMulticriteriaResponse response = searchIndividualByMulticriteriaImpl.searchIndividual(sibmRequest);		
		
		assertNotNull(response);
		assertNotNull(response.getIndividual());
		assertEquals(1, response.getIndividual().size());
		assertEquals("400295784743", response.getIndividual().get(0).getIndividualInformations().getIdentifier());
	}

}
