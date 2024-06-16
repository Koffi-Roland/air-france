package com.airfrance.repind.service.telecom.internal;

import com.airfrance.ref.exception.InvalidCountryCodeException;
import com.airfrance.ref.exception.InvalidPhoneNumberException;
import com.airfrance.ref.exception.TooLongPhoneNumberException;
import com.airfrance.ref.exception.TooShortPhoneNumberException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.telecom.NormalizePhoneNumberDTO;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.util.PhoneNumberUtils;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class NormalizePhoneNumberDSTest extends NormalizePhoneNumberDS {

	/* ######################################################################## */
	/* # normalizePhoneNumber												  # */
	/* ######################################################################## */

	@Before
	public void setUp() {
		variablesDS = EasyMock.createMock(VariablesDS.class);
	}

	@Test
	public void testNormalizePhoneNumber_Valid() throws JrafDomainException {
		NormalizePhoneNumberDTO result = normalizePhoneNumber("33","0141566118");
		Assert.assertNotNull(result);
	}

	@Test
	public void testNormalizePhoneNumber_AmbiguousCountryCode() throws JrafDomainException {
		NormalizePhoneNumberDTO result = normalizePhoneNumber("1","4415198789");
		Assert.assertNotNull(result);
	}

	@Test(expected=InvalidCountryCodeException.class)
	public void testNormalizePhoneNumber_InvalidCountry() throws JrafDomainException {
		normalizePhoneNumber("XX","0141566118");
	}

	@Test(expected=TooLongPhoneNumberException.class)
	public void testNormalizePhoneNumber_TooLongPhoneNumber() throws JrafDomainException {
		normalizePhoneNumber("FR","06326040891");
	}

	@Test(expected=TooShortPhoneNumberException.class)
	public void testNormalizePhoneNumber_TooShortPhoneNumber() throws JrafDomainException {
		normalizePhoneNumber("FR","06285943");
	}

	@Test(expected=InvalidPhoneNumberException.class)
	public void testNormalizePhoneNumber_InvalidPhoneNumber() throws JrafDomainException {
		normalizePhoneNumber("FR","");
	}

	@Test(expected=InvalidPhoneNumberException.class)
	public void testIsValidPhoneNumber_OldRule() throws JrafDomainException {
		EasyMock.expect(variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)).andReturn(0l);
		EasyMock.replay(variablesDS);
		NormalizePhoneNumberDTO result = normalizePhoneNumber("US","+41319175200");
		Assert.assertNotNull(result);
	}

	@Test
	public void testIsValidPhoneNumber_NewRule() throws JrafDomainException {
		EasyMock.expect(variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)).andReturn(1l);
		EasyMock.replay(variablesDS);
		NormalizePhoneNumberDTO result = normalizePhoneNumber("US","+41319175200");
		Assert.assertNotNull(result);
	}

	@Test(expected=InvalidPhoneNumberException.class)
	public void testIsValidPhoneNumber_NewRuleShouldFail_1() throws JrafDomainException {
		EasyMock.expect(variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)).andReturn(1l);
		EasyMock.replay(variablesDS);
		NormalizePhoneNumberDTO result = normalizePhoneNumber("33","000000000");
		Assert.assertNotNull(result);
	}

	@Test(expected=InvalidPhoneNumberException.class)
	public void testIsValidPhoneNumber_NewRuleShouldFail_2() throws JrafDomainException {
		EasyMock.expect(variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)).andReturn(1l);
		EasyMock.replay(variablesDS);
		NormalizePhoneNumberDTO result = normalizePhoneNumber("33","047301011");
		Assert.assertNotNull(result);
	}

	@Test(expected=InvalidPhoneNumberException.class)
	public void testIsValidPhoneNumber_NewRuleShouldFail_3() throws JrafDomainException {
		EasyMock.expect(variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)).andReturn(1l);
		EasyMock.replay(variablesDS);
		NormalizePhoneNumberDTO result = normalizePhoneNumber("33","871713759");
		Assert.assertNotNull(result);
	}

	@Test(expected=InvalidPhoneNumberException.class)
	public void testIsValidPhoneNumber_NewRuleShouldFail_4() throws JrafDomainException {
		EasyMock.expect(variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)).andReturn(1l);
		EasyMock.replay(variablesDS);
		NormalizePhoneNumberDTO result = normalizePhoneNumber("55","1997711558");
		Assert.assertNotNull(result);
	}

	@Test(expected=InvalidPhoneNumberException.class)
	public void testIsValidPhoneNumber_NewRuleShouldFail_5() throws JrafDomainException {
		EasyMock.expect(variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)).andReturn(1l);
		EasyMock.replay(variablesDS);
		NormalizePhoneNumberDTO result = normalizePhoneNumber("55","1997711558");
		Assert.assertNotNull(result);
	}

	/* ######################################################################## */
	/* # testIsValidCountryCode												  # */
	/* ######################################################################## */

	@Test
	public void testIsValidCountryCode_Numeric() throws JrafDomainException {
		boolean result = isValidCountryCode("33");
		Assert.assertTrue(result);
	}

	@Test
	public void testIsValidCountryCode_AlphaNumeric() throws JrafDomainException {
		boolean result = isValidCountryCode("+33");
		Assert.assertTrue(result);
	}

	@Test
	public void testIsValidCountryCode_Alpha() throws JrafDomainException {
		boolean result = isValidCountryCode("FR");
		Assert.assertTrue(result);
	}

	@Test
	public void testIsValidCountryCode_Invalid() throws JrafDomainException {
		boolean result = isValidCountryCode("FRA");
		Assert.assertFalse(result);
	}

	@Test
	public void testIsValidCountryCode_Empty() throws JrafDomainException {
		boolean result = isValidCountryCode("");
		Assert.assertFalse(result);
	}

	/* ######################################################################## */
	/* # testIsValidPhoneNumber												  # */
	/* ######################################################################## */

	@Test
	public void testIsValidPhoneNumber_Valid() throws JrafDomainException {
		boolean result = isValidPhoneNumber("+(33) 6.32-60-40-89");
		Assert.assertTrue(result);
	}

	@Test
	public void testIsValidPhoneNumber_Invalid() throws JrafDomainException {
		boolean result = isValidPhoneNumber("#<>,?;.:/!§ù%*µ^¨$£+)°@çè");
		Assert.assertFalse(result);
	}

	@Test
	public void testIsValidPhoneNumber_Empty() throws JrafDomainException {
		boolean result = isValidPhoneNumber("");
		Assert.assertFalse(result);
	}

	/* ######################################################################## */
	/* # testNormalizeCountryCode											  # */
	/* ######################################################################## */

	@Test
	public void testNormalizeCountryCode_Numeric() throws JrafDomainException {
		String result = normalizeCountryCode("33");
		Assert.assertSame("FR",result);
	}

	@Test
	public void testNormalizeCountryCode_AlphaNumeric() throws JrafDomainException {
		String result = normalizeCountryCode("+33");
		Assert.assertSame("FR",result);
	}

	@Test
	public void testNormalizeCountryCode_Alpha() throws JrafDomainException {
		String result = normalizeCountryCode("FR");
		Assert.assertSame("FR",result);
	}

	@Test(expected=InvalidCountryCodeException.class)
	public void testNormalizeCountryCode_Empty() throws JrafDomainException {
		normalizeCountryCode("");
	}

	@Test(expected=InvalidCountryCodeException.class)
	public void testNormalizeCountryCode_Invalid() throws JrafDomainException {
		normalizeCountryCode("FRA");
	}

	@Test(expected=InvalidCountryCodeException.class)
	public void testNormalizeCountryCode_Unknown() throws JrafDomainException {
		normalizeCountryCode("777");
	}

	/* ######################################################################## */
	/* # testCleanPhoneNumber											  	  # */
	/* ######################################################################## */

	@Test
	public void testCleanPhoneNumber() throws JrafDomainException {
		String result = PhoneNumberUtils.cleanPhoneNumber("(01) 2 (34).5.6/78/");
		Assert.assertEquals("012345678",result);
	}

	/* ######################################################################## */
	/* # testIsSameCountryCode											  	  # */
	/* ######################################################################## */

	@Test
	public void testIsSameCountryCode_True() throws NumberParseException {

		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberResult = phoneNumberUtil.parse("4415198789", "BM"); // BM -> 1

		boolean result = isSameCountryCode(phoneNumberResult, "CA"); // CA -> 1
		Assert.assertTrue(result);
	}

	@Test
	public void testIsSameCountryCode_False() throws NumberParseException {

		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberResult = phoneNumberUtil.parse("4415198789", "BM"); // BM -> 1

		boolean result = isSameCountryCode(phoneNumberResult, "FR"); // FR -> 33
		Assert.assertFalse(result);
	}

	// REPIND-20 : TooLong instead of TooShort
	@Test
	public void testTooLongInsteadOfTooShort() throws NumberParseException {

		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberResult = phoneNumberUtil.parse("11305861890", "US");

		Assert.assertEquals(305861890L, phoneNumberResult.getNationalNumber());
		Assert.assertEquals(1, phoneNumberResult.getCountryCode());

		// REPIND-836, deleting check on country code source, since used by nothing in
		// our system
	}

	
	@Test
	// REPIND-1693 : Probleme de normalisation
	public void testTelecomFromGabonOld() throws NumberParseException {

		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberResult = phoneNumberUtil.parse("07024102", "GA");

		Assert.assertEquals(7024102L, phoneNumberResult.getNationalNumber());
		Assert.assertEquals(241, phoneNumberResult.getCountryCode());
	}

	@Test
	// REPIND-1693 : Probleme de normalisation
	public void testTelecomFromGabonNow() throws NumberParseException {

		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberResult = phoneNumberUtil.parse("77024102", "GA");

		Assert.assertEquals(77024102L, phoneNumberResult.getNationalNumber());
		Assert.assertEquals(241, phoneNumberResult.getCountryCode());
	}
	
}
