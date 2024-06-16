package com.airfrance.repind.service.consent;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.consent.ConsentRepository;
import com.airfrance.repind.entity.consent.Consent;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ConsentDSTest {
	
	@Autowired
	private ConsentDS consentDS;
	
	@Autowired
	private ConsentRepository consentRepository;
		
	private static final String GIN_FOR_TEST = "400272037921";
	private static final String APP_FOR_TEST = "TESTRI";
	
	@After
	public void deleteConsentAfterTest() {
		
		List<Consent> consents = new ArrayList<Consent>();
		consents = consentRepository.findByGin(GIN_FOR_TEST);
		
		if (consents.size() > 0) {
			for (Consent consent : consents) {
				consentRepository.deleteById(consent.getConsentId());
			}
		}
	}	
	
	@Test
	@Rollback(false)
	public void testCreateDefaultConsents() throws JrafDomainException {
		
		consentDS.createDefaultConsents(GIN_FOR_TEST, APP_FOR_TEST);
		
		List<Consent> consents = new ArrayList<Consent>();
		consents = consentRepository.findByGin(GIN_FOR_TEST);
		
		Assert.assertEquals(7, consents.size());
		
		for (Consent consent : consents) {
			if ("AF_PERSONALIZED_SERVICING".equalsIgnoreCase(consent.getType())) {
				
				Assert.assertEquals("DEFAULT", consent.getConsentData().stream().findFirst().get().getType());
				Assert.assertEquals("Y", consent.getConsentData().stream().findFirst().get().getIsConsent());	
				
			} else if("KL_PERSONALIZED_SERVICING".equalsIgnoreCase(consent.getType())) {
				
				Assert.assertEquals("DEFAULT", consent.getConsentData().stream().findFirst().get().getType());
				Assert.assertEquals("Y", consent.getConsentData().stream().findFirst().get().getIsConsent());	
				
			} else if ("AF_PERSONALIZED_OFFERS".equalsIgnoreCase(consent.getType())) {
				
				Assert.assertEquals("DEFAULT", consent.getConsentData().stream().findFirst().get().getType());
				Assert.assertEquals("P", consent.getConsentData().stream().findFirst().get().getIsConsent());
				
			} else if ("KL_PERSONALIZED_OFFERS".equalsIgnoreCase(consent.getType())) {
				
				Assert.assertEquals("DEFAULT", consent.getConsentData().stream().findFirst().get().getType());
				Assert.assertEquals("P", consent.getConsentData().stream().findFirst().get().getIsConsent());
				
			} else if ("PARTNERS_PERSONALIZED_OFFERS".equalsIgnoreCase(consent.getType())) {
				
				Assert.assertEquals("DEFAULT", consent.getConsentData().stream().findFirst().get().getType());
				Assert.assertEquals("P", consent.getConsentData().stream().findFirst().get().getIsConsent());
				
			} else if ("BIOMETRICS_AF_COLLECTION_STORAGE".equalsIgnoreCase(consent.getType())) {
				
				Assert.assertEquals("DEFAULT", consent.getConsentData().stream().findFirst().get().getType());
				Assert.assertEquals("P", consent.getConsentData().stream().findFirst().get().getIsConsent());
			
			} else if ("BIOMETRICS_KL_COLLECTION_STORAGE".equalsIgnoreCase(consent.getType())) {
					
				Assert.assertEquals("DEFAULT", consent.getConsentData().stream().findFirst().get().getType());
				Assert.assertEquals("P", consent.getConsentData().stream().findFirst().get().getIsConsent());
				
			} else {
				
				Assert.fail("This consent is not a default consent: " + consent.getType());
			}
		}
	}
}
