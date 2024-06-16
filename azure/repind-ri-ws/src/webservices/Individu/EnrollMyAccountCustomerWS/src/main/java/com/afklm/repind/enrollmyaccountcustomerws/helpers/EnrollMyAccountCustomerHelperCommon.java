package com.afklm.repind.enrollmyaccountcustomerws.helpers;

/**
 * Common helper for all version of EnrollMyAccountCustomerWS
 * 
 * @author m421262
 *
 */
public class EnrollMyAccountCustomerHelperCommon {

	/**
	 * Old constants for checking password (only when robust isn't activated)
	 */
	private final static int MIN_PASSWORD_SIZE = 6;
	private final static int MAX_PASSWORD_SIZE = 12;

	/**
	 * Check if password passed is correct in case of : 
	 * - Robust password is activated (check pattern and size) 
	 * - Robust password disabled (check size between MIN_PASSWORD_SIZE and MAX_PASSWORD_SIZE)
	 * 
	 * @param passwordPassed
	 * @return password passed OK nor NOK
	 */
	public static boolean isPasswordPassedCorrect(String passwordPassed, boolean robustActivated,
			boolean passwordCompliant) {
		int passwordPassedSize = passwordPassed.length();
		// Check robust password
		if ((robustActivated && !passwordCompliant || !robustActivated
				&& (passwordPassedSize < MIN_PASSWORD_SIZE || passwordPassedSize > MAX_PASSWORD_SIZE))) {
			// If robust password is not activated, only check the size
			return false;
		}
		return true;
	}

}
