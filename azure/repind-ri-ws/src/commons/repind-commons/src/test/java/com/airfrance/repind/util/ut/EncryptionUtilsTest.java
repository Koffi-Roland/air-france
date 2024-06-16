package com.airfrance.repind.util.ut;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.util.EncryptionUtils;
import com.airfrance.repind.util.PwdContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EncryptionUtilsTest {

	/** logger */
	private static final Log logger = LogFactory.getLog(EncryptionUtilsTest.class);

	@Test
	public void cryptPwdTest() throws Exception {
		String encryptedPwd = EncryptionUtils.cryptPwd("000000018254", "123456");
		EncryptionUtilsTest.logger.info(encryptedPwd);
		Assert.assertEquals(encryptedPwd, "FCEF167F223EEA2D4720937D4463F26D114E3D00");
	}

	/**
	 * Test hashPBKDF2WithHmacSHA1Test Hash the password with
	 * PwdPBKDF2WithHmacSHA1 and verify if is the same as encodated one.
	 *
	 * @throws Exception
	 */
	@Test
	public void hashPBKDF2WithHmacSHA1Test() throws Exception {
		String encryptedPwd = EncryptionUtils.hashPBKDF2WithHmacSHA1("000000018254", "123456");
		EncryptionUtilsTest.logger.info(encryptedPwd);
		Assert.assertEquals(encryptedPwd, "");
	}

	/**
	 * Test hashPBKDF2WithHmacSHA1UtfTest Hash the password with
	 * PwdPBKDF2WithHmacSHA1 and verify if is the same as encodated one. Use UTF
	 * Chars
	 *
	 * @throws Exception
	 */
	@Test
	public void hashPBKDF2WithHmacSHA1UtfTest() throws Exception {
		String encryptedPwd = EncryptionUtils.hashPBKDF2WithHmacSHA1("000000018254", "é@êᶊᶋ◕ᶌΧ♞");
		EncryptionUtilsTest.logger.info(encryptedPwd);
		Assert.assertEquals(encryptedPwd, "");
	}

	/**
	 * Test getCryptedGeneratePasswordNumTest Generate a random password hashed
	 * with PwdPBKDF2WithHmacSHA1 and verify if is the same as encodated one
	 * with classic generation (only num, before robust password)
	 *
	 * @throws Exception
	 */
	@Test
	public void getCryptedGeneratePasswordNotRobustTest() throws Exception {
		PwdContainer pwdContainer = EncryptionUtils.getCryptedGeneratePassword("000000018254", 4);
		EncryptionUtilsTest.logger.info(pwdContainer.getCryptedPwd());
		Assert.assertEquals(pwdContainer.getCryptedPwd(),
				EncryptionUtils.hashPBKDF2WithHmacSHA1("000000018254", pwdContainer.getNotCryptedPwd()));
	}

	/**
	 * Test getCryptedGeneratePasswordTest Generate a random password hashed
	 * with PwdPBKDF2WithHmacSHA1 and verify if is the same as encodated one
	 * with classic generation (alphanum, after robust password)
	 *
	 * @throws Exception
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 2 mins
	public void getCryptedGeneratePasswordRobustTest() throws Exception {
		PwdContainer pwdContainer = EncryptionUtils.getCryptedGeneratePassword("000000018254",
				"[0-9a-zA-Z@$&+\\-></#_\\?!]{8,12}",
				"[0-9a-zA-Z@$&+\\-></#_\\?!]*[A-Z][0-9a-zA-Z@$&+\\-></#_\\?!]*</next/>[0-9a-zA-Z@$&+\\-></#_\\?!]*[a-z][0-9a-zA-Z@$&+\\-></#_\\?!]*</next/>[0-9a-zA-Z@$&+\\-></#_\\?!]*[0-9][0-9a-zA-Z@$&+\\-></#_\\?!]*",
				"</next/>");
		EncryptionUtilsTest.logger.info(pwdContainer.getCryptedPwd());
		Assert.assertEquals(pwdContainer.getCryptedPwd(),
				EncryptionUtils.hashPBKDF2WithHmacSHA1("000000018254", pwdContainer.getNotCryptedPwd()));
	}

	@Test
	public void isPasswordCompliantRobustPasswordTest() throws JrafDomainException, JrafApplicativeException {
		String grammar = "[0-9a-zA-Z@$&+\\-></#_\\?!]{8,12}";
		String patternToMatch = "[0-9a-zA-Z@$&+\\-></#_\\?!]*[A-Z][0-9a-zA-Z@$&+\\-></#_\\?!]*</next/>[0-9a-zA-Z@$&+\\-></#_\\?!]*[a-z][0-9a-zA-Z@$&+\\-></#_\\?!]*</next/>[0-9a-zA-Z@$&+\\-></#_\\?!]*[0-9][0-9a-zA-Z@$&+\\-></#_\\?!]*";
		String delimiter = "</next/>";
		Assert.assertFalse(
				EncryptionUtils.isPasswordCompliantRobustPassword("toto", grammar, patternToMatch, delimiter));
		// We don't allow special chars yet
		Assert.assertFalse(
				EncryptionUtils.isPasswordCompliantRobustPassword("test*/0731:!1", grammar, patternToMatch, delimiter));
		// Good pattern but too small
		Assert.assertFalse(
				EncryptionUtils.isPasswordCompliantRobustPassword("tt01", grammar, patternToMatch, delimiter));
		// Good pattern but too big
		Assert.assertFalse(
				EncryptionUtils.isPasswordCompliantRobustPassword("mizapatoes391", grammar, patternToMatch, delimiter));
		// Good pattern, minimal size
		Assert.assertTrue(
				EncryptionUtils.isPasswordCompliantRobustPassword("Toto2234", grammar, patternToMatch, delimiter));
		// Good pattern, maximal size
		Assert.assertTrue(
				EncryptionUtils.isPasswordCompliantRobustPassword("Totototo2345", grammar, patternToMatch, delimiter));

		Assert.assertFalse(
				EncryptionUtils.isPasswordCompliantRobustPassword("totototo2345", grammar, patternToMatch, delimiter));
		Assert.assertFalse(
				EncryptionUtils.isPasswordCompliantRobustPassword("123456789AZEZ", grammar, patternToMatch, delimiter));
	}

	/**
	 * Test hashPBKDF2WithHmacSHA1Test Hash the password with
	 * PwdPBKDF2WithHmacSHA1 and verify if is the same as encodated one
	 * (RobustPassword more than 8 characters).
	 *
	 * @throws Exception
	 */
	@Test
	public void hashPBKDF2WithHmacSHA1RobustPasswordTest() throws Exception {
		String encryptedPwd = EncryptionUtils.hashPBKDF2WithHmacSHA1("000000018254", "123abc456@7");
		EncryptionUtilsTest.logger.info(encryptedPwd);
		Assert.assertEquals(encryptedPwd, "");
	}

	/**
	 * Test Error - hashPBKDF2WithHmacSHA1NPETest With NPE For Gin + null,
	 *
	 * @throws Exception
	 */
	@Test
	public void hashPBKDF2WithHmacSHA1PasswordNPETest() throws Exception {
		try {
			String encryptedPwd = EncryptionUtils.hashPBKDF2WithHmacSHA1("000000018254", null);
			EncryptionUtilsTest.logger.info(encryptedPwd);
		}
		 catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test Error - hashPBKDF2WithHmacSHA1GinNPETest With NPE for null +
	 * password
	 *
	 * @throws Exception
	 */
	@Test
	public void hashPBKDF2WithHmacSHA1GinNPETest() throws Exception {
		try {
			String encryptedPwd = EncryptionUtils.hashPBKDF2WithHmacSHA1(null, "123456");
			EncryptionUtilsTest.logger.info(encryptedPwd);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}

		try {
			String encryptedPwd = EncryptionUtils.hashPBKDF2WithHmacSHA1(null, null);
			EncryptionUtilsTest.logger.info(encryptedPwd);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test Error - hashPBKDF2WithHmacSHA1GinPasswordNPETest With NPE for null +
	 * null
	 *
	 * @throws Exception
	 */
	@Test
	public void hashPBKDF2WithHmacSHA1GinPasswordNPETest() throws Exception {
		try {
			String encryptedPwd = EncryptionUtils.hashPBKDF2WithHmacSHA1(null, null);
			EncryptionUtilsTest.logger.info(encryptedPwd);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test cryptInitalPwdTest Hash the password with PwdPBKDF2WithHmacSHA1 and
	 * verify if is the same as encodated one (RobustPassword more than 8
	 * characters).
	 *
	 * @throws Exception
	 */
	@Test
	public void cryptInitalPwdTest() throws Exception {
		// The generated pin is 2217
		String encryptedPwd = EncryptionUtils.cryptInitalPwd("000000018254");
		EncryptionUtilsTest.logger.info(encryptedPwd);
		Assert.assertEquals(encryptedPwd, "");
	}

	/**
	 * Test Error - cryptInitalPwdGinNPETest NPE for Gin null
	 *
	 * @throws Exception
	 */
	@Test
	public void cryptInitalPwdGinNPETest() throws Exception {
		try {
			// The generated pin is 2217
			String encryptedPwd = EncryptionUtils.cryptInitalPwd(null);
			EncryptionUtilsTest.logger.info(encryptedPwd);
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void calculCheckDigitTest() throws Exception {
		String seq = EncryptionUtils.processCheckDigit("11111111111");
		Assert.assertEquals(seq, "111111111111");
	}

	@Test
	public void calculCheckDigitForASeqOfElevenDigitsTest() throws Exception {
		String seq = EncryptionUtils.processCheckDigit("40060105574");
		Assert.assertEquals(seq, "400601055745");
	}

	@Test
	public void calculCheckDigitForASeqOfTenDigitsTest() throws Exception {
		String seq = EncryptionUtils.processCheckDigit("1111111111");
		Assert.assertEquals(seq, "11111111114");
	}

	@Test
	public void calculCheckDigitForASeqWhitAlphaTest() throws Exception {
		String seq = EncryptionUtils.processCheckDigit("1111111111B");
		Assert.assertEquals(seq, null);
	}

	@Test
	public void claculPinCodeWithZero() {
		String generatedPWD = EncryptionUtils.calculPinCodeV2("000000006811");
		Assert.assertEquals("3604", generatedPWD);
	}

	@Test
	public void claculPinCodeWithoutZero() {
		String generatedPWD = EncryptionUtils.calculPinCodeV2("000000006811");
		Assert.assertEquals("3604", generatedPWD);
	}

	@Test
	public void calculCheckDigitForARandomSeqTest() throws Exception {
		Random rand = new Random();
		Integer nombreAleatoire = rand.nextInt(999999999 - 100000000 + 1) + 100000000;

		String seq = EncryptionUtils.processCheckDigit(nombreAleatoire.toString());

		List<String> numbers = new ArrayList<>();
		numbers.add("0");
		numbers.add("1");
		numbers.add("2");
		numbers.add("3");
		numbers.add("4");
		numbers.add("5");
		numbers.add("6");
		Assert.assertTrue(numbers.contains(seq.substring(9)));
	}
}
