package com.afklm.repind.v1.it.helper;

import com.afklm.repind.config.WebTestConfig;
import com.afklm.repind.enrollmyaccountcustomerws.helpers.EnrollMyAccountCustomerHelperCommon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class EnrollMyAccountCustomerHelperCommonIntegration {

	/**
	 * Test IsPasswordPassedCorrect when :
	 *  - Robust password disabled
	 *  - password compliant without importance
	 *  - password OK ("123456")
	 */
	@Test
	public void testIsPasswordPassedCorrectWithoutRobustActivated() {
		String passwordPassed = "123456";
		boolean robustActivated = false;
		boolean passwordCompliant = false;
		boolean isPasswordPassedOK = EnrollMyAccountCustomerHelperCommon.isPasswordPassedCorrect(passwordPassed,
				robustActivated, passwordCompliant);

		Assert.assertTrue(isPasswordPassedOK);
	}
	
	/**
	 * Test IsPasswordPassedCorrect when :
	 *  - Robust password disabled
	 *  - password compliant without importance
	 *  - password NOK ("1234")
	 */
	@Test
	public void testIsPasswordPassedNotCorrectMin() {
		String passwordPassed = "1234";
		boolean robustActivated = false;
		boolean passwordCompliant = false;
		boolean isPasswordPassedOK = EnrollMyAccountCustomerHelperCommon.isPasswordPassedCorrect(passwordPassed,
				robustActivated, passwordCompliant);

		Assert.assertFalse(isPasswordPassedOK);
	}
	
	/**
	 * Test IsPasswordPassedCorrect when :
	 *  - Robust password disabled
	 *  - password compliant without importance
	 *  - password NOK ("1234567890123")
	 */
	@Test
	public void testIsPasswordPassedNotCorrectMax() {
		String passwordPassed = "1234567890123";
		boolean robustActivated = false;
		boolean passwordCompliant = false;
		boolean isPasswordPassedOK = EnrollMyAccountCustomerHelperCommon.isPasswordPassedCorrect(passwordPassed,
				robustActivated, passwordCompliant);

		Assert.assertFalse(isPasswordPassedOK);
	}
	
	/**
	 * Test IsPasswordPassedCorrect when :
	 *  - Robust password Activated
	 *  - password compliant OK
	 *  - password OK ("Tert1234$")
	 */
	@Test
	public void testIsPasswordPassedCorrectWithRobustActivated() {
		String passwordPassed = "Tert1234$";
		boolean robustActivated = true;
		boolean passwordCompliant = true;
		boolean isPasswordPassedOK = EnrollMyAccountCustomerHelperCommon.isPasswordPassedCorrect(passwordPassed,
				robustActivated, passwordCompliant);

		Assert.assertTrue(isPasswordPassedOK);
	}
	
	/**
	 * Test IsPasswordPassedCorrect when :
	 *  - Robust password Activated
	 *  - password compliant NOK
	 *  - password NOK ("12345678")
	 */
	@Test
	public void testIsPasswordPassedNotCorrectWithRobustActivated() {
		String passwordPassed = "12345678";
		boolean robustActivated = true;
		boolean passwordCompliant = false;
		boolean isPasswordPassedOK = EnrollMyAccountCustomerHelperCommon.isPasswordPassedCorrect(passwordPassed,
				robustActivated, passwordCompliant);

		Assert.assertFalse(isPasswordPassedOK);
	}

	
}
