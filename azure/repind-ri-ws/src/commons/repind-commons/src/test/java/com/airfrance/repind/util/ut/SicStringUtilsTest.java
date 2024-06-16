package com.airfrance.repind.util.ut;

import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author E6378882
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
public class SicStringUtilsTest {

	/** logger */
	private static final Log logger = LogFactory.getLog(SicStringUtilsTest.class);

	@Test
	public void formatDateToAdhTest() throws Exception {
		String date = "2011-05-09 00:00:00.0";
		String formattedDate = SicStringUtils.formatDateToAdh(date);
		Assert.assertEquals("09052011", formattedDate);
	}

	@Test
	public void formatDateToWsdl() throws Exception {
		String date = "09052011";
		String formattedDate = SicStringUtils.formatDateToWsdl(date);
		Assert.assertEquals("2011-05-09", formattedDate);
	}

	@Test
	public void isDDMMYYYFormatDateTest() throws Exception {

		Date test = SicDateUtils.stringToDate("1980-50-50");
		SicStringUtilsTest.logger.info(test);
		String date = "09052011";
		boolean valid = SicStringUtils.isDDMMYYYFormatDate(date);
		Assert.assertTrue(valid);
	}

	@Test
	public void toUpperCaseWithoutAccents() throws Exception {

		String accentedCharacters = "çáéíóúýàèìòùâêîôûäëïöüÿñ";
		String result = SicStringUtils.toUpperCaseWithoutAccents(accentedCharacters);
		System.err.println("result=" + result);
		Assert.assertEquals(result, "CAEIOUYAEIOUAEIOUAEIOUYN");
	}

	@Test
	public void toUpperCaseWithoutAccentsForFirms() throws Exception {

		String accentedCharacters = "çáéíóúýàèìòùâêîôûäëïöüÿãõñß";
		String result = SicStringUtils.toUpperCaseWithoutAccentsForFirms(accentedCharacters);
		System.err.println("result=" + result);
		Assert.assertEquals(result, "ÇAEIOUYAEIOUAEIOÛÄËÏÖÜYÃÕÑß");
	}

	@Test
	public void testEmailValidity() throws Exception {

		// IM02204577
		String email = "john.watson@fcm.uk.travel";
		boolean isValid = SicStringUtils.isValidEmail(email);
		Assert.assertTrue(isValid);
	}

	@Test
	public void longContractNumberToString12Test() {
		Long cin = 123456789012L;
		String result = SicStringUtils.longIdentifierNumberToString(cin);
		Assert.assertEquals(12, result.length());
		Assert.assertEquals(cin.toString(), result);
	}

	@Test
	public void longContractNumberToString8Test() {
		Long cin = 12345678L;
		String result = SicStringUtils.longIdentifierNumberToString(cin);
		Assert.assertEquals(12, result.length());
		Assert.assertEquals("0000" + cin.toString(), result);
	}

	@Test
	public void testIsMatriculeShouldWork() {
		Assert.assertTrue(SicStringUtils.isMatricule("t838155"));
	}

	@Test
	public void testIsMatriculeShouldFail() {
		Assert.assertFalse(SicStringUtils.isMatricule("unknown"));
	}

	@Test
	public void normalizeStringToASCIIEmpty() {
		Assert.assertEquals("", SicStringUtils.replaceAccentAndRemoveSpecialChars(""));
		Assert.assertEquals(null, SicStringUtils.replaceAccentAndRemoveSpecialChars(null));
	}

	@Test
	public void normalizeStringToASCIInormal() {
		Assert.assertEquals("aeeu", SicStringUtils.replaceAccentAndRemoveSpecialChars("âéè$*ù"));
		Assert.assertEquals("This is a funky String",
				SicStringUtils.replaceAccentAndRemoveSpecialChars("Tĥïŝ ĩš â fůňķŷ Šťŕĭńġ"));
		Assert.assertEquals("Pierre-Etienne L'Homme",
				SicStringUtils.replaceAccentAndRemoveSpecialChars("Pîérrê-Etïennë L'Hômme!!$^"));
		Assert.assertEquals("1234567890", SicStringUtils.replaceAccentAndRemoveSpecialChars("1234567890"));

	}

	@Test
	public void testCorrectDateFormat_resultOK() {
		String sDate1 = "01/01/2020";
		String sDate2 = "29/02/2000";
		String sDate3 = "31/12/2018";
		Assert.assertTrue(SicStringUtils.isValidPreferenceData(sDate1));
		Assert.assertTrue(SicStringUtils.isValidPreferenceData(sDate2));
		Assert.assertTrue(SicStringUtils.isValidPreferenceData(sDate3));
	}

	@Test
	public void testCorrectDateFormat_resultKO() {
		String sDate1 = "00/01/2020";
		String sDate2 = "29/02/2001";
		String sDate3 = "32/12/2018";
		Assert.assertFalse(SicStringUtils.isValidPreferenceData(sDate1));
		Assert.assertFalse(SicStringUtils.isValidPreferenceData(sDate2));
		Assert.assertFalse(SicStringUtils.isValidPreferenceData(sDate3));
	}

	// --- Test for IsMatchingStringWithPatterns --- //
	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result OK -> Simple Pattern to check (1 only)
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultOK_simplePattern() {
		String stringToCheckMatch = "PwDfg85Spdf";
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add(".*");
		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result OK -> Complexe Pattern to check : -
	 * [0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]* String has at least 1 letter (upper or
	 * lower) - [0-9a-zA-Z]*[0-9][0-9a-zA-Z]* String has at least 1 num
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultOK_complexePattern() {
		String stringToCheckMatch = "PwDfg85Spdf";
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("[0-9a-zA-Z]*[a-zA-Z][0-9a-zA-Z]*");
		patternsToMatch.add("[0-9a-zA-Z]*[0-9][0-9a-zA-Z]*");
		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result OK -> Complexe Pattern to check : - the string
	 * PwDfg85Spdf
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultOK_perfectPatternMatch() {
		String stringToCheckMatch = "PwDfg85Spdf";
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("PwDfg85Spdf");
		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result KO -> Simple Pattern to check : - $ String is
	 * contains '$' char
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultKO() {
		String stringToCheckMatch = "PwDfg85Spdf";
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("$");
		Assert.assertFalse(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result OK -> Pattern = null
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultOK_PatternsNull() {
		String stringToCheckMatch = "PwDfg85Spdf";
		List<String> patternsToMatch = null;
		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result OK -> Pattern is empty
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultOK_PatternsEmpty() {
		String stringToCheckMatch = "PwDfg85Spdf";
		List<String> patternsToMatch = new ArrayList<String>();
		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result OK -> Email regex, \w -> Un caractère de mot : *
	 * [a-zA-Z_0-9]
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultOK_emailPattern() {
		String stringToCheckMatch = "test.email@airfrance.fr";
		List<String> patternsToMatch = new ArrayList<String>();
		patternsToMatch.add("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	/**
	 * Test for IsMatchingStringWithPatterns, check if the string match with
	 * patterns : -> Result OK -> Regex uninterpretable by regex system
	 */
	@Test
	public void testIsMatchingStringWithPatterns_resultOK_uninterpretablePattern() {
		String stringToCheckMatch = "test.email@airfrance.fr";
		List<String> patternsToMatch = new ArrayList<String>();
		// Will not block the process, a warning would be log
		patternsToMatch.add("[");
		Assert.assertTrue(SicStringUtils.isMatchingStringWithPatterns(stringToCheckMatch, patternsToMatch));
	}

	@Test
	public void deleteZeroToTheLeft_1() {
		Assert.assertEquals("1", SicStringUtils.deleteZeroToTheLeft("1"));
	}

	@Test
	public void deleteZeroToTheLeft_100() {
		Assert.assertEquals("100", SicStringUtils.deleteZeroToTheLeft("100"));
	}

	@Test
	public void deleteZeroToTheLeft_0100() {
		Assert.assertEquals("100", SicStringUtils.deleteZeroToTheLeft("0100"));
	}

	@Test
	public void deleteZeroToTheLeft_00010100() {
		Assert.assertEquals("10100", SicStringUtils.deleteZeroToTheLeft("00010100"));
	}

	@Test
	public void deleteZeroToTheLeft_01010100() {
		Assert.assertEquals("1010100", SicStringUtils.deleteZeroToTheLeft("01010100"));
	}

	@Test
	public void deleteZeroToTheLeft_0() {
		Assert.assertEquals("0", SicStringUtils.deleteZeroToTheLeft("0"));
	}

	@Test
	public void deleteZeroToTheLeft_00() {
		Assert.assertEquals("0", SicStringUtils.deleteZeroToTheLeft("00"));
	}

	@Test
	public void deleteZeroToTheLeft_000() {
		Assert.assertEquals("0", SicStringUtils.deleteZeroToTheLeft("000"));
	}

	@Test
	public void truncToTwoRightChar_99() {
		Assert.assertEquals("99", SicStringUtils.truncToTwoRightChar("99"));
	}

	@Test
	public void truncToTwoRightChar_100() {
		Assert.assertEquals("0", SicStringUtils.truncToTwoRightChar("100"));
	}

	@Test
	public void truncToTwoRightChar_101() {
		Assert.assertEquals("1", SicStringUtils.truncToTwoRightChar("101"));
	}

	@Test
	public void truncToTwoRightChar_999() {
		Assert.assertEquals("99", SicStringUtils.truncToTwoRightChar("999"));
	}

	@Test
	public void truncToTwoRightChar_10000() {
		Assert.assertEquals("0", SicStringUtils.truncToTwoRightChar("10000"));
	}

	@Test
	public void truncToTwoRightChar_10100() {
		Assert.assertEquals("0", SicStringUtils.truncToTwoRightChar("10100"));
	}

	@Test
	public void truncToTwoRightChar_10101() {
		Assert.assertEquals("1", SicStringUtils.truncToTwoRightChar("10101"));
	}
	// --- END Test for IsMatchingStringWithPatterns --- //

	// REPIND-1288 : Normalized Email
	@Test
	public void normalizeEmail_Null() {
		Assert.assertEquals(null, SicStringUtils.normalizeEmail(null));
	}

	@Test
	public void normalizeEmail_Empty() {
		Assert.assertEquals("", SicStringUtils.normalizeEmail(""));
	}

	@Test
	public void normalizeEmail_Blank() {
		Assert.assertEquals("", SicStringUtils.normalizeEmail(" "));
	}

	@Test
	public void normalizeEmail_BlankTwice() {
		Assert.assertEquals("", SicStringUtils.normalizeEmail("  "));
	}

	@Test
	public void normalizeEmail_LeftTrim() {
		Assert.assertEquals("adresse@email.com", SicStringUtils.normalizeEmail(" adresse@email.com"));
	}

	@Test
	public void normalizeEmail_LeftTrimTwice() {
		Assert.assertEquals("adresse@email.com", SicStringUtils.normalizeEmail("  adresse@email.com"));
	}

	@Test
	public void normalizeEmail_LeftTrimTwiceWithWhite() {
		Assert.assertEquals("adresse@email.com", SicStringUtils.normalizeEmail("  r adresse@email.com"));
	}

	@Test
	public void normalizeEmail_RightTrim() {
		Assert.assertEquals("adresse@email.com", SicStringUtils.normalizeEmail("adresse@email.com "));
	}

	@Test
	public void normalizeEmail_RightTrimTwice() {
		Assert.assertEquals("adresse@email.com", SicStringUtils.normalizeEmail("adresse@email.com  "));
	}

	@Test
	public void normalizeEmail_RightTrimTwiceWithWhite() {
		Assert.assertEquals("adresse@email.com", SicStringUtils.normalizeEmail("adresse@email.com m  "));
	}

	@Test
	public void normalizeEmail_toLower() {
		Assert.assertEquals("adresse@email.com", SicStringUtils.normalizeEmail("Adresse@Email.Com"));
	}

	@Test
	public void normalizeEmail_RemoveUnprintableCharactersAtEnd() {
		Assert.assertEquals("charles.augello@odigeo.com",
				SicStringUtils.normalizeEmail("CHARLES.augello@odigeo.com p	�"));
	}

	@Test
	public void normalizeEmail_RemoveUnprintableCharactersAtStart() {
		Assert.assertEquals("charles.augello@odigeo.com", SicStringUtils.normalizeEmail("�CHARLES.augello@odigeo.com"));
	}

	@Test
	public void normalizeEmail_RemoveUnprintableCharacters() {
		Assert.assertEquals("charles�.augello@odigeo.com",
				SicStringUtils.normalizeEmail("� d  d CHARLES�.augello@odigeo.com  p	�"));
	}
	
	
	@Test
	public void hardTrim_SpecialChar_eacute() {
		Assert.assertEquals("philippe.clément@poma.net", SicStringUtils.normalizeEmail("philippe.clément@poma.net"));
	}
	
	@Test
	public void hardTrim_SpecialChar_agrave() {
		Assert.assertEquals("francois.leloup@à.fr", SicStringUtils.normalizeEmail("francois.leloup@à.fr"));
	}
	
	@Test
	public void hardTrim_SpecialChar_N() {
		Assert.assertEquals("hcarre�o@security.cl", SicStringUtils.normalizeEmail("hcarre�o@security.cl"));
	}


	@Test
	public void hardTrim_SpecialChar_Start_eacute() {
		Assert.assertEquals("lectricité@poma.net", SicStringUtils.normalizeEmail("électricité@poma.net"));
	}
	
	@Test
	public void hardTrim_SpecialChar_Start_agrave() {
		Assert.assertEquals("lamer@voila.fr", SicStringUtils.normalizeEmail("àlamer@voila.fr"));
	}
	
	@Test
	public void hardTrim_SpecialChar_Start_N() {
		Assert.assertEquals("ossenunez@repind.com", SicStringUtils.normalizeEmail("Ñossenunez@repind.com"));
	}
	
	@Test
	public void replaceNonPrintableChars_ASCII_Email() {
		Assert.assertEquals("ossenunez@repind.com", SicStringUtils.replaceNonPrintableChars("ossenunez@repind.com"));
	}

	@Test
	public void replaceNonPrintableChars_ASCII_Date() {
		Assert.assertEquals("15/01/2018", SicStringUtils.replaceNonPrintableChars("15/01/2018"));
	}
	
	@Test
	public void replaceNonPrintableChars_ASCII_Accent() {
		Assert.assertEquals("lectricite@poma.net", SicStringUtils.replaceNonPrintableChars("lectricité@poma.net"));
	}

	@Test
	public void replaceNonPrintableChars_ASCII_NonPrintable() {
		Assert.assertEquals(" ", SicStringUtils.replaceNonPrintableChars("�"));
	}
	
	@Test
	public void replaceNonPrintableChars_AccentBizarre() {
		Assert.assertEquals("aAuUS", SicStringUtils.replaceNonPrintableChars("ăAüUŜ"));
	}

	// Test ASCII //
	@Test
	public void isNonASCII_Empty() {
		Assert.assertTrue(!SicStringUtils.isNonASCII(""));
	}

	@Test
	public void isNonASCII_Null() {
		Assert.assertTrue(!SicStringUtils.isNonASCII(null));
	}

	@Test
	public void isNonASCII_Accent() {
		Assert.assertTrue(SicStringUtils.isNonASCII("ăAüUŜ"));
	}
	
	@Test
	public void isNonASCII_Normal() {
		Assert.assertTrue(!SicStringUtils.isNonASCII("abcde"));
	}

	@Test
	public void isNonASCII_UTF8_Chinese() {
		Assert.assertTrue(SicStringUtils.isNonASCII("開催"));
	}

	@Test
	public void isNonASCII_UTF8_Moroco() {
		Assert.assertTrue(SicStringUtils.isNonASCII("لسابق"));
	}

	@Test
	public void isNonASCII_UTF8_Russian() {
		Assert.assertTrue(SicStringUtils.isNonASCII("Российской"));
	}

	// REPIND-1767 : Check and Find ASCI in UTF8 for

	//////////////
	// getASCII //
	//////////////
	
	@Test
	public void getASCII_Russian_Empty() {
		Assert.assertEquals("", SicStringUtils.getASCII("Российской"));
	}

	@Test
	public void getASCII_ASCII_Only() {
		Assert.assertEquals("ABDGH", SicStringUtils.getASCII("ABDGH"));
	}

	@Test
	public void getASCII_ASCII_Empty() {
		Assert.assertEquals("", SicStringUtils.getASCII(""));
	}

	@Test
	public void getASCII_ASCII_Null() {
		Assert.assertEquals(null, SicStringUtils.getASCII(null));
	}
	
	@Test
	public void getASCII_UTF8_And_ASCII_Russian_Begin() {
		Assert.assertEquals("A", SicStringUtils.getASCII("AРоссийской"));
	}

	@Test
	public void getASCII_UTF8_And_ASCII_Russian_BeginEnd() {
		Assert.assertEquals("AB", SicStringUtils.getASCII("AРоссийскойB"));
	}

	@Test
	public void getASCII_UTF8_And_ASCII_Russian_BeginMiddleEnd() {
		Assert.assertEquals("ARB", SicStringUtils.getASCII("AРоссиRйскойB"));
	}

	@Test
	public void getASCII_UTF8_And_ASCII_Russian_BeginMiddleEndSpace() {
		Assert.assertEquals(" A R B ", SicStringUtils.getASCII(" AРосси R йскойB "));
	}

	////////////////////////////////////////
	// toUpperCaseWithoutAccentsOnlyASCII //
	////////////////////////////////////////
	
	@Test
	public void toUpperCaseWithoutAccentsOnlyASCII_UTF8_And_ASCII_Russian_Begin() {
		Assert.assertEquals("A", SicStringUtils.toUpperCaseWithoutAccentsOnlyASCII("AРоссийской"));
	}

	@Test
	public void toUpperCaseWithoutAccentsOnlyASCII_UTF8_And_ASCII_Russian_BeginEnd() {
		Assert.assertEquals("AB", SicStringUtils.toUpperCaseWithoutAccentsOnlyASCII("AРоссийскойB"));
	}

	@Test
	public void toUpperCaseWithoutAccentsOnlyASCII_UTF8_And_ASCII_Russian_BeginMiddleEnd() {
		Assert.assertEquals("ARB", SicStringUtils.toUpperCaseWithoutAccentsOnlyASCII("AРоссиRйскойB"));
	}

	@Test
	public void toUpperCaseWithoutAccentsOnlyASCII_UTF8_And_ASCII_Russian_BeginMiddleEndSpace() {
		Assert.assertEquals(" A R B ", SicStringUtils.toUpperCaseWithoutAccentsOnlyASCII(" AРосси R йскойB "));
	}
	
	@Test
	public void normalizelWithQuotes() {
		Assert.assertEquals("\"validemail\"@example.com", SicStringUtils.normalizeEmail("\"validemail\"@example.com"));
		Assert.assertEquals("\"email\"invalid@example.com", SicStringUtils.normalizeEmail("\"email\"invalid@example.com"));
		Assert.assertEquals("special_char_in_quotes\"@example.com", SicStringUtils.normalizeEmail("\"Ñspecial_char_in_quotes\"@example.com"));
	}

	@Test
	public void normalizelWithSquareBrackets() {
		Assert.assertEquals("email@[123.123.123.123]", SicStringUtils.normalizeEmail("email@[123.123.123.123]"));
		Assert.assertEquals("email@[123.123.123.123]", SicStringUtils.normalizeEmail("email@[123.123.123.123]]]"));
	}
	
}
