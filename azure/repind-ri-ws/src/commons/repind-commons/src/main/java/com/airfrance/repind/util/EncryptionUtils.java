package com.airfrance.repind.util;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.util.service.EnvVarUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;
import java.util.List;

public class EncryptionUtils {

	private final static int iterationPBKDF2WithHmacSHA1 = 12000;
	private final static int lengthPasswordBase = 40;

	private static Log log  = LogFactory.getLog(EncryptionUtils.class);

	/**
	 * Hash the password with SHA 1, DEPRECATED, use instead PBKDF2WithHmacSHA1
	 *
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@Deprecated
	public static String SHA1(String text) throws NoSuchAlgorithmException,	UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance("SHA-1");
		byte[] sha1hash = new byte[40];
		md.update(text.getBytes("iso-8859-1"), 0, text.length());
		sha1hash = md.digest();
		return DatatypeConverter.printHexBinary(sha1hash);
	}

	/**
	 * Created a password using password string passed and Salt gin passed
	 *
	 * @param toHashString,
	 *            Generaly the password
	 * @param salt,
	 *            Generaly the GIN
	 * @return hashedPassword
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String PBKDF2WithHmacSHA1(String toHashString, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		//TODO : Remove this method properly, SHA1 isn't used anymore because it was only used to encrypt password on ACCOUNT_DATA and now, they're managed at CID
		return "";
	}

	/**
	 * Hash the password with SHA 1, DEPRECATED, use instead PBKDF2WithHmacSHA1
	 *
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@Deprecated
	public static String cryptPwd(String gin, String pwd) throws JrafApplicativeException {

		try {

			return EncryptionUtils.SHA1(pwd.trim()+gin.trim());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new JrafApplicativeException(e);
		}
	}

	/**
	 * Return the HashedString on PBKDF2WithHmacSHA1
	 *
	 * @param toHashString,
	 *            Generaly the password or other thing to hash
	 * @param salt,
	 *            Generaly the gin
	 * @return HashedString
	 * @throws JrafApplicativeException
	 */
	public static String hashPBKDF2WithHmacSHA1(String salt, String toHashString) throws JrafApplicativeException {

		try {
			return EncryptionUtils.PBKDF2WithHmacSHA1(toHashString.trim(), salt.trim());
		} catch (Exception e) {
			throw new JrafApplicativeException(e);
		}
	}

	private static String CalculPinCode(String sGin) {
		String sPinCode = "";
		double dPinCode = 32222221;
		int iC1, iC2, iC3, iC4, iC5, iC6, iC7, iC8, iC9, iC10, iC11, iC12;

		// on vérifie que le code GIN respecte le format
		if (sGin.length() == 12) {
			iC1 = Integer.parseInt(sGin.substring(0, 1));
			iC2 = Integer.parseInt(sGin.substring(1, 2));
			iC3 = Integer.parseInt(sGin.substring(2, 3));
			iC4 = Integer.parseInt(sGin.substring(3, 4));
			iC5 = Integer.parseInt(sGin.substring(4, 5));
			iC6 = Integer.parseInt(sGin.substring(5, 6));
			iC7 = Integer.parseInt(sGin.substring(6, 7));
			iC8 = Integer.parseInt(sGin.substring(7, 8));
			iC9 = Integer.parseInt(sGin.substring(8, 9));
			iC10 = Integer.parseInt(sGin.substring(9, 10));
			iC11 = Integer.parseInt(sGin.substring(10, 11));
			iC12 = Integer.parseInt(sGin.substring(11, 12));

			dPinCode += 52874793 * iC1;
			dPinCode += 21256481 * iC2;
			dPinCode += 15642489 * iC3;
			dPinCode += 45626874 * iC4;
			dPinCode += 84621697 * iC5;
			dPinCode += 96314785 * iC6;
			dPinCode += 65495176 * iC7;
			dPinCode += 35789642 * iC8;
			dPinCode += 15935871 * iC9;
			dPinCode += 25832426 * iC10;
			dPinCode += 48931538 * iC11;
			dPinCode += 79275211 * iC12;
		}
		// retourne le code confidentiel en prenant les 4 premiers chiffres de
		// droite du
		// resultat de l'addition
		DecimalFormat df = new DecimalFormat("#");
		sPinCode = df.format(dPinCode);

		if (sPinCode != null) {
			sPinCode = sPinCode.substring(sPinCode.length() - 4);
		}

		return sPinCode;
	}

	/**
	 * Hash the initial password with PBKDF2WithHmacSHA1
	 *
	 * @param gin
	 * @return the hashedPassword
	 * @throws JrafApplicativeException
	 */
	public static String cryptInitalPwd(String gin) throws JrafApplicativeException {
		try {
			return EncryptionUtils.PBKDF2WithHmacSHA1(EncryptionUtils.CalculPinCode(gin.trim()), gin.trim());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new JrafApplicativeException(e);
		} catch (Exception e) {
			throw new JrafApplicativeException(e);
		}
	}

	/**
	 * Generate a crypted password following patterns given in parameters
	 *
	 * @param gin
	 * @param passwordGrammarDefinition
	 *            Alphabet to generate the password, Password grammar
	 *            definition: aka char allowed + size
	 * @param passwordPatterns
	 *            More precise business rules about password rules, list of
	 *            regex, stored as a single string
	 * @param delimiter
	 *            Delimiter used between each regex by
	 *            generatepasswordpatternstomatch
	 * @return A PwdContainer with password generated and crypted
	 * @throws JrafApplicativeException
	 * @throws JrafDomainException
	 */
	public static PwdContainer getCryptedGeneratePassword(String gin, String passwordGrammarDefinition,
			String passwordPatterns, String delimiter) throws JrafApplicativeException {

		String randomPwd = "";

		List<String> generatePasswordPatternsToMatch = EnvVarUtils.preparePatternsToMatchPassword(passwordPatterns,
					delimiter);
		GenerateStringFromRegex pwdGenerator;
		try {
			pwdGenerator = new GenerateStringFromRegex(passwordGrammarDefinition);
			randomPwd = pwdGenerator.generateWithPatterns(generatePasswordPatternsToMatch);
		} catch (Exception e) {
			throw new JrafApplicativeException(e);
		}

		EncryptionUtils.log.debug("EncryptionUtils:getCryptedGeneratePassword : pass generated : " + randomPwd);
		PwdContainer container = new PwdContainer(EncryptionUtils.hashPBKDF2WithHmacSHA1(gin, randomPwd), randomPwd);
		return container;
	}

	/**
	 * Generate a crypted password, this is to generate old version of password
	 *
	 * @param gin
	 * @param pincodelength
	 *            Length of password to generate, use for old version
	 * @return A PwdContainer with password generated and crypted
	 * @throws JrafApplicativeException
	 * @throws JrafDomainException
	 */
	@Deprecated
	public static PwdContainer getCryptedGeneratePassword(String gin, int pincodelength)
			throws JrafApplicativeException, JrafDomainException {

		String randomPwd = "";
		// Old method to generate password
		char[] passwordFormat = SecureRandomNumber.NUM;
		randomPwd = SecureRandomNumber.getNextSecureRandom(pincodelength, passwordFormat);

		EncryptionUtils.log.debug("EncryptionUtils:getCryptedGeneratePassword : pass generated : " + randomPwd);
		PwdContainer container = new PwdContainer(EncryptionUtils.hashPBKDF2WithHmacSHA1(gin, randomPwd), randomPwd);
		return container;
	}

	/**
	 * Return if the password is compliant with Robust Password Pattern
	 * (requirement)
	 *
	 * @param gin
	 * @param passwordGrammarDefinition
	 *            Alphabet to generate the password, Password grammar
	 *            definition: aka char allowed + size
	 * @param passwordPatterns
	 *            More precise business rules about password rules, list of
	 *            regex, stored as a single string
	 * @param delimiter
	 *            Delimiter used between each regex by
	 *            generatepasswordpatternstomatch
	 * @param pincodelength
	 *            Length of password to generate, use for old version
	 * @return if the password is robust or not
	 * @throws JrafApplicativeException
	 * @throws JrafDomainException
	 */
	public static boolean isPasswordCompliantRobustPassword(String password, String passwordGrammarDefinition,
			String passwordPatterns, String delimiter) throws JrafDomainException {

		// Concat the grammar definition to the list of regex to check, to have the size
		// + char allowed check
		passwordPatterns = passwordPatterns + delimiter + passwordGrammarDefinition;

		List<String> patterns = EnvVarUtils.preparePatternsToMatchPassword(passwordPatterns, delimiter);
		return SicStringUtils.isMatchingStringWithPatterns(password, patterns);
	}

	/**
	 * Calcul pin code with optimization
	 *
	 * @param csGin
	 * @return
	 */
	public static String calculPinCodeV2(String csGin) {

		String result = null;

		try {

			String csGinNew = SicStringUtils.addingZeroToTheLeft(csGin, 12);
			int[] iC = new int[12];

			int j =1 ;
			for (int i = 0; i < csGinNew.length(); i++) {
				iC[i] = Integer.parseInt(csGinNew.substring(i,j++));
			}

			double dPinCode = 32222221;


			double values[] = { 52874793, 21256481, 15642489, 45626874, 84621697, 96314785, 65495176, 35789642,
					15935871, 25832426, 48931538, 79275211 };
			for (int i = 0; i < values.length; i++) {
				dPinCode += values[i] * iC[i];
			}

			DecimalFormat df = new DecimalFormat("#");
			df.setMaximumFractionDigits(30);

			String dPinCodeStr = BigDecimal.valueOf(dPinCode).toPlainString();
			// retourne le code confidentiel en prenant les 4 premiers chiffres de droite du
			// resultat de l'addition
			result = dPinCodeStr.substring(dPinCodeStr.length() - 4);
		} catch (Exception e) {
			EncryptionUtils.log.error(e.getMessage());
		}
		return result;
	}

	public static String processCheckDigit (String numSeq) {
		//## begin EncryptionUtils.processCheckDigit
		if(SicStringUtils.isNumeric(numSeq)) {
			Double dNumSeqSrc = Double.parseDouble(numSeq);
			try {
				// calcul check digit
				Double dConstCheck = 6.0;
				Double dNumSeq = dNumSeqSrc + dConstCheck;
				Double dDiviseur = 7.0;
				Double dCheckDigit = dNumSeq % dDiviseur;
				if(dDiviseur.compareTo(dCheckDigit) == 0) {
					dCheckDigit=0.0;
				}
				// conversion en string XX caracteres + ajout du check digit
				String csNumeroWithDigit = numSeq + dCheckDigit.toString().substring(0,1);
				EncryptionUtils.log.debug( "csNumero = " + csNumeroWithDigit );
				return(csNumeroWithDigit);
			} catch(Exception e) {
				// Gestion des erreurs applicatives
				EncryptionUtils.log.error("EncryptionUtils : processCheckDigit : Rejet : " + e.getMessage() );
			}
		} else {
			EncryptionUtils.log.error("EncryptionUtils : processCheckDigit : Rejet : numSeq " + numSeq + " is not a digit." );
		}
		return null;
	}
	
	public static String encodeBase64(String value) throws UnsupportedEncodingException{
		byte[] message = value.getBytes("UTF-8");
		return DatatypeConverter.printBase64Binary(message);
	}
	
	

}
