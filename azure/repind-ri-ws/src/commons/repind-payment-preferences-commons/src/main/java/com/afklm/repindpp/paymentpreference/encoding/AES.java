package com.afklm.repindpp.paymentpreference.encoding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

// REPIND-735, simplify the old AES encryption process
public class AES {
	private static final Log LOGGER = LogFactory.getLog(AES.class);

	private static final String ALGORITHM = "AES";
	private static final List<SecretKeySpec> KEYS = new ArrayList<SecretKeySpec>() {
		private static final long serialVersionUID = 5416003204543086099L;
		{
			// It s the key that should be used for everything
			add(new SecretKeySpec(DatatypeConverter.parseHexBinary("DC70DE3562C03A11432630F6A1F6195D"), AES.ALGORITHM));

			// The keys keeped for historical retro compatibility (some values in the
			// database have been ciphered
			// with thoses key in the past)
			add(new SecretKeySpec(DatatypeConverter.parseHexBinary("9E3BCA0EA51E37394C99DB4E35D27561"), AES.ALGORITHM));
			add(new SecretKeySpec(DatatypeConverter.parseHexBinary("D005E6356071FF700EABA75B56508149"), AES.ALGORITHM));
			add(new SecretKeySpec(DatatypeConverter.parseHexBinary("ED9B61DF3A099140A022DE28AA952D30"), AES.ALGORITHM));
			add(new SecretKeySpec(DatatypeConverter.parseHexBinary("A814D1895883984D162F1D3874B44547"), AES.ALGORITHM));
		}
	};

	public static final String DEFAULT_ERROR_STRING = "error";

	public static String decrypt(final String encrypted) throws IllegalBlockSizeException, BadPaddingException,
	UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

		// We try every key. This behavior should be removed once we are sure all the
		// database value are
		// correctly encrypted with the prod key only. This is not the case at the
		// moment.
		for (int i = 0; i < AES.KEYS.size(); i++) {
			try {
				final Cipher cipher = Cipher.getInstance(AES.ALGORITHM);
				cipher.init(Cipher.DECRYPT_MODE, AES.KEYS.get(i));
				final byte[] plaintextBytes = cipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));
				return new String(plaintextBytes, "UTF-8");
			} catch (final BadPaddingException e) {
				// We tested every keys now, so the BadPaddingException is normal
				if (i == AES.KEYS.size() - 1) {
					AES.LOGGER.error("Cannot decrypt: " + encrypted + " - " + e);
					throw e;
				}
			}
		}

		// Nothing can reach this code
		throw new BadPaddingException("Impossible case");
	}

	/*
	 * This method is only keeped for compatibility with the Batch PCIDSS. The day
	 * the Batch PCIDSS is reworked, please remove this method. Returning a default
	 * value if a exception is thrown in the decryption process it NOT a good idea
	 */
	@Deprecated
	public static String decryptDefaultValueIfError(final String encrypted) {
		try {
			return AES.decrypt(encrypted);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException
				| NoSuchAlgorithmException | NoSuchPaddingException | IllegalArgumentException e) {
			AES.LOGGER.error("Batch PCIDSS decryption error: " + encrypted + " - " + e);
			return AES.DEFAULT_ERROR_STRING;
		}
	}

	public static String encrypt(final String plain) throws NoSuchAlgorithmException, NoSuchPaddingException,
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		final Cipher cipher = Cipher.getInstance(AES.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, AES.KEYS.get(0));
		final byte[] encryptedBytes = cipher.doFinal(plain.getBytes());
		return DatatypeConverter.printHexBinary(encryptedBytes);
	}

	/*
	 * This method is only keeped for compatibility with the Batch PCIDSS. The day
	 * the Batch PCIDSS is reworked, please remove this method. Returning a null
	 * string if a exception is thrown in the encryption process is NOT a good idea
	 */
	@Deprecated
	public static String encryptOrNull(final String plain) {
		try {
			return AES.encrypt(plain);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | IllegalArgumentException e) {
			AES.LOGGER.error("Batch PCIDSS encryption error: " + e);
			return null;
		}
	}

}
