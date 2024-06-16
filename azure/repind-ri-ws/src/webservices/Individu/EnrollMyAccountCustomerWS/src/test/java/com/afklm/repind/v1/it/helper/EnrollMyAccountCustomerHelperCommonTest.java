package com.afklm.repind.v1.it.helper;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.enrollmyaccountcustomerws.helpers.EnrollMyAccountCustomerHelperCommon;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.encryption.internal.EncryptionDS;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Common helper for all version of EnrollMyAccountCustomerWS
 * 
 * @author m421262
 *
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EnrollMyAccountCustomerHelperCommonTest {

	@Autowired
	private EncryptionDS encryptionDS;

	/**
	 * Test IsPasswordPassedCorrect when :
	 *  - Robust password Activated on DB
	 *  - password compliant OK with DB
	 *  - password OK ("Tert1234$")
	 */
	@Test
	public void testIsPasswordPassedCorrectIntegration() throws JrafDomainException {
		String passwordPassed = "Tert1234$";
		boolean robustActivated = encryptionDS.isRobustPasswordAuthenticateActivated();
		boolean passwordCompliant = encryptionDS.isPasswordCompliant(passwordPassed);
		boolean isPasswordPassedOK = EnrollMyAccountCustomerHelperCommon.isPasswordPassedCorrect(passwordPassed,
				robustActivated, passwordCompliant);

		Assert.assertTrue(isPasswordPassedOK);
	}
	
}
