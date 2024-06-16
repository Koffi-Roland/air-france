package com.afklm.sicwscustomeridentification.impl;

import com.afklm.sicwscustomeridentification.config.WebTestConfig;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.siccommontype.Requestor;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.*;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.IdentifyCustomerCrossReferentialResponse;
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
public class IdentifyCustomerCrossReferentialServiceImplForExternalTest {
	private static final String CHANNEL = "B2C";
	private static final String SIGNATURE = "T412211";
	private static final String SITE = "QVI";
	private static final String COMPANY = "AF";
	private static final String APP_CODE = "B2C";
	
	@Autowired
	@Qualifier("passenger_IdentifyCustomerCrossReferentialService-v1Bean")
	private IdentifyCustomerCrossReferentialServiceV1Impl identifyCustomerCrossReferentialServiceV1Impl;
	
	@Test
	public void testIdentifyCustomerCrossReferentialServiceV1Impl_SocialOnly() throws BusinessException, SystemException {

		IdentifyCustomerCrossReferentialRequest request = new IdentifyCustomerCrossReferentialRequest();
		
		request.setProcessType("M"); 	// MANUEL
		request.setIndex(1);			// Continuous index
		
		SearchIdentifier searchIdentifier = new SearchIdentifier();
		
		// Postal address add in order to test matching with SOCIAL/EXTERNAL
		// INSERT INTO "SIC2"."ADR_POST" (SAIN, SGIN, IVERSION, SCODE_PAYS, SCODE_MEDIUM, SSTATUT_MEDIUM, SSITE_MODIFICATION, SSIGNATURE_MODIFICATION, DDATE_MODIFICATION, SSITE_CREATION, SSIGNATURE_CREATION, DDATE_CREATION, SFORCAGE, ICOD_ERR) VALUES ('0', '920000116371', '1', 'FR', 'P', 'V', 'JPC', 'T412211', TO_DATE('2018-06-12 07:17:15', 'YYYY-MM-DD HH24:MI:SS'), 'JPC', 'T412211', TO_DATE('2018-06-12 07:17:02', 'YYYY-MM-DD HH24:MI:SS'), 'N', '0')
		PostalAddress postalAddress = new PostalAddress();
		postalAddress.setCountryCode("FR");
		// postalAddress.setZipCode("200041");
		searchIdentifier.setPostalAddress(postalAddress);
		
		PersonsIdentity personsIdentity = new PersonsIdentity();
		personsIdentity.setFirstName("SOCIALJOVRDHBMNT");
		personsIdentity.setLastName("SOCIALQKFJZLWYUI");
		searchIdentifier.setPersonsIdentity(personsIdentity);
				
		request.setSearchIdentifier(searchIdentifier);
		
		Context context = new Context();
		context.setTypeOfSearch("I");	// Individual
		context.setResponseType("F");	// NON Unique result
		
		Requestor requestor = new Requestor();
		requestor.setChannel(CHANNEL);
		requestor.setSignature(SIGNATURE);
		requestor.setSite(SITE);
		requestor.setManagingCompany(COMPANY);
		requestor.setApplicationCode(APP_CODE);

		context.setRequestor(requestor);
		request.setContext(context);

		IdentifyCustomerCrossReferentialResponse response = identifyCustomerCrossReferentialServiceV1Impl.search(request);		
		
		assertNotNull(response);
		assertNotNull(response.getCustomer());
		assertEquals(1, response.getCustomer().size());
		assertNotNull(response.getCustomer().get(0).getIndividual());
		assertEquals("920000116371", response.getCustomer().get(0).getIndividual().getIndividualInformations().getIndividualKey());
	}
}
