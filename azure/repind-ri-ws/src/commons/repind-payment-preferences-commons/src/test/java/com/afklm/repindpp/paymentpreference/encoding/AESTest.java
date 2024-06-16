package com.afklm.repindpp.paymentpreference.encoding;

import com.afklm.repindpp.paymentpreference.config.WebConfigTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestConfig.class)
public class AESTest {

	public static int StringSize = 20;
	public static int loopSize = 100;

	@Test
	public void encryptDecrypt() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "toto";
		String encrypted = AES.encrypt(value);
		String decrypted = AES.decrypt(encrypted);
		Assert.assertEquals(value, decrypted);
	}

	@Test
	public void DecryptCodeBILL_NAME() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "92A60A02779D7477A57E60F6B8B8AB6B";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("BILL_NAME", decrypted);
	}

	@Test
	public void DecryptCodeBILL_ZIP() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "3CA0D4F491792BD87AB0FA7190F0467E";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("BILL_ZIP", decrypted);
	}

	@Test
	public void DecryptCodeBILL_FIRST_NAME() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "05604C67E9D1C7370B95B6CF4316EEC2";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("BILL_FIRST_NAME", decrypted);
	}

	@Test
	public void DecryptCodeCC_NR() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "4CAF978F22389B18CD4FBF59894D4079";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("CC_NR", decrypted);
	}
	
	@Test
	public void DecryptCodeBILL_COUNTY() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "8FC2414C582A280C21CF4D7419E13F1A";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("BILL_COUNTY", decrypted);
	}

	@Test
	public void DecryptCodeBILL_COUNTRY() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "06B10DF034CBD8C02C34F81D4956ED59";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("BILL_COUNTRY", decrypted);
	}

	@Test
	public void DecryptCodeCC_EXP_DATE() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "5A4E70D34DC47E70B48F32874BFF528A";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("CC_EXP_DATE", decrypted);
	}

	@Test
	public void DecryptCodeBILL_ADDRESS() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "39831BDEBD677A5CE037F2FE591F997B";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("BILL_ADDRESS", decrypted);
	}

	@Test
	public void DecryptCodeBILL_CITY() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String value = "CDA2EDE6039DB775C03B6DBF8E3CA40A";
		String decrypted = AES.decrypt(value);
		Assert.assertEquals("BILL_CITY", decrypted);
	}

	@Test
	public void decryptPCIDSSWrongValue() {
		String result = AES.decryptDefaultValueIfError("toto");
		Assert.assertEquals(AES.DEFAULT_ERROR_STRING, result);
	}

	@Test
	public void decryptPCIDSSWrongValueRightHexa() {
		String result = AES.decryptDefaultValueIfError("26AB");
		Assert.assertEquals(AES.DEFAULT_ERROR_STRING, result);
	}

	@Test
	public void decryptBadPaddingException() throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String plain = "toto";
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE,
				new SecretKeySpec(DatatypeConverter.parseHexBinary("AE3BCA0EA51E37394C99DB4E35D26561"), "AES"));
		final byte[] encryptedBytes = cipher.doFinal(plain.getBytes());
		String encrypted = DatatypeConverter.printHexBinary(encryptedBytes);

		try {
			AES.decrypt(encrypted);
			Assert.fail();
		} catch (BadPaddingException e) {

		}

	}

	@Test
	public void decryptWrongValue() {
		try {
			AES.decrypt("toto");
			Assert.fail();
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException
				| NoSuchAlgorithmException | NoSuchPaddingException | IllegalArgumentException e) {
			Assert.assertEquals(IllegalArgumentException.class, e.getClass());
		}
	}

	@Test
	public void decryptWrongValueRightHexa() {
		try {
			AES.decrypt("26AB");
			Assert.fail();
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException
				| NoSuchAlgorithmException | NoSuchPaddingException | IllegalArgumentException e) {
			Assert.assertEquals(IllegalBlockSizeException.class, e.getClass());
		}
	}

}
