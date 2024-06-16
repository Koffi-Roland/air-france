package com.airfrance.paymentpreferenceswsv2;

import com.afklm.repindpp.paymentpreference.dao.PaymentDetailsRepository;
import com.afklm.repindpp.paymentpreference.encoding.AES;
import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000469.v1.BusinessException;
import com.afklm.soa.stubs.w000469.v1.providedecryptedpaymentpreferencesschema.ProvideFields;
import com.afklm.soa.stubs.w000469.v1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000469.v1.providedecryptedpaymentpreferencesschema.ProvidePaymentPreferencesResponse;
import com.afklm.soa.stubs.w000470.v2_0_1.createorreplacepaymentpreferencesschema.CreateFields;
import com.afklm.soa.stubs.w000470.v2_0_1.createorreplacepaymentpreferencesschema.CreatePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.DeletePaymentPreferencesRequest;
import com.afklm.soa.stubs.w000471.v1.deletepaymentpreferencesschema.DeletePaymentPreferencesResponse;
import com.airfrance.config.WebTestConfig;
import com.airfrance.paymentpreferenceswsv2.v2.CreateOrReplacePaymentPreferencesV2Impl;
import com.airfrance.paymentpreferenceswsv2.v2.ProvidePaymentPreferencesV2Impl;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
//@Ignore     // build	29-Apr-2016 15:46:54	java.io.FileNotFoundException: /app/SIC/prod/bin/java/config/repind_pp.txt (No such file or directory)
public class ProvidePaymentPreferencesImplTest {

	private static final Log logger = LogFactory.getLog(ProvidePaymentPreferencesImplTest.class);

	@Autowired
	private PaymentDetailsRepository paymentDetailsDao;

	@Autowired
	@Qualifier("passenger_ProvidePaymentPreferences-v1Bean")
	private ProvidePaymentPreferencesImpl providePaymentPreferencesImpl;

	@Autowired
	@Qualifier("passenger_ProvidePaymentPreferences-v2Bean")
	private ProvidePaymentPreferencesV2Impl providePaymentPreferencesImplv2;

	@Autowired
	@Qualifier("passenger_DeletePaymentPreferences-v1Bean")
	private DeletePaymentPreferencesImpl deletePaymentPreferencesImpl;

	@Autowired
	@Qualifier("passenger_CreateOrReplacePaymentPreferences-v2Bean")
	private CreateOrReplacePaymentPreferencesV2Impl createOrReplacePaymentPreferencesImpl;

	private static int statsNotConform = 0;
	private static int statsConform = 0;
	private static int statsNbErreur = 0;

	// Test provide with scandinavian character and accent
	@Test
	@Ignore
	// @Category(com.afklm.repindpp.paymentpreference.util.TestCategory.Slow.class)	// TU qui ne passe pas en local chez T412211
	public void providePaymentPreferencesImplTestDecryptWithSpecialCharScandinavian()
			throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException, SystemException, BusinessException,
			com.afklm.soa.stubs.w000471.v1.BusinessException {

		ProvidePaymentPreferencesImplTest.logger
		.info("providePaymentPreferencesImplTestDecryptWithSpecialCharScandinavian...");

		String gin = "411415094784";

		deletePP(gin);

		String address = "Kjøpmannsgata àäåëæ 28";
		String city = "Åléèsund";

		CreatePaymentPreferencesRequest requestCreate = createRequestPaymentPreferences(gin, address, city);
		createOrReplacePaymentPreferencesImpl.createOrReplacePaymentPreferences(requestCreate);

		// Provide of the payment preferences tested
		ProvidePaymentPreferencesRequest requestProvide = new ProvidePaymentPreferencesRequest();
		requestProvide.setGin(gin);

		ProvidePaymentPreferencesResponse responseProvide = providePaymentPreferencesImpl
				.providePaymentPreferences(requestProvide);

		// Check how many payment fields are returned
		Assert.assertEquals(responseProvide.getProvidefields().size(), 7);

		// Check if characters are returned
		String billAddressReturned = "";
		String billCityReturned = "";

		for (int i = 0; i < responseProvide.getProvidefields().size(); i++) {

			if (responseProvide.getProvidefields().get(i).getPaymentFieldCode().equals("BILL_ADDRESS")) {
				billAddressReturned = responseProvide.getProvidefields().get(i).getPaymentFieldPreferences();
			} else if (responseProvide.getProvidefields().get(i).getPaymentFieldCode().equals("BILL_CITY")) {
				billCityReturned = responseProvide.getProvidefields().get(i).getPaymentFieldPreferences();
			}
		}

		Assert.assertEquals(billAddressReturned, address);
		Assert.assertEquals(billCityReturned, city);

	}

	// See JIRA for case2 error def
	@Ignore
	@Test(expected = Test.None.class /* no exception expected */)
	public void generatePlainTextValueCase2Error() throws NoSuchAlgorithmException, NoSuchPaddingException,
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		while (true) {
			String plainText = generateRandomString();
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec key = new SecretKeySpec(DatatypeConverter.parseHexBinary("DC70DE3562C03A11432630F6A1F6195D"),
					"AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
			String encrypted = new String(encryptedBytes, "UTF-8");
			if (decryptVerification(encrypted)) {
				// In this case, we found a plain text value, that when crypted doesn't not
				// contains any
				// non common UTF8 char, and will trigger error 2 case.
				byte[] doubleEncrypt = AESencrypt(encryptedBytes);
				ProvidePaymentPreferencesImplTest.logger
				.error("Plain text value: " + plainText + " - First encryption bytes: "
						+ DatatypeConverter.printHexBinary(encryptedBytes) + " - Second encryption bytes: "
						+ DatatypeConverter.printHexBinary(doubleEncrypt));
				break;
			}
		}
	}

	// reproduce error case2
	@Ignore
	@Test
	public void decryptErrorCase2() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
	UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {

		// generated using the previous method "generatePlainTextValueCase2Error"
		String plainTextValueErrorCase2 = "ob";

		String encrypted = AES.encrypt(plainTextValueErrorCase2);
		String doubleEncrypted = AES.encrypt(new String(DatatypeConverter.parseHexBinary(encrypted), "UTF-8"));
		ProvidePaymentPreferencesImplTest.logger.error(encrypted + " - " + doubleEncrypted);
		String decrypted = AES.decrypt(doubleEncrypted);

		// We see that the decrypted value doesn't match the plain text value
		Assert.assertNotEquals(plainTextValueErrorCase2, decrypted);

		ProvidePaymentPreferencesImplTest.logger.error(decrypted);

		String decrypted2 = AES.decrypt(DatatypeConverter.printHexBinary(decrypted.getBytes()));
		ProvidePaymentPreferencesImplTest.logger.error(decrypted2);

		// We see now that the decrypted value match the plain text value
		Assert.assertEquals(plainTextValueErrorCase2, decrypted2);


	}


	public byte[] AESencrypt(byte[] plain) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
	NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec key = new SecretKeySpec(DatatypeConverter.parseHexBinary("DC70DE3562C03A11432630F6A1F6195D"),
				"AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(plain);
	}

	public String generateRandomString() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	private boolean decryptVerification(String decrypted) {
		for (char current_char : decrypted.toCharArray()) {
			if (!Charset.forName(CharEncoding.UTF_8).newEncoder().canEncode(current_char)
					|| Character.UnicodeBlock.of(current_char) == Character.UnicodeBlock.SPECIALS) {
				return false;
			}
		}
		return true;
	}

	public String AESStandardDecrypt(String encrypted) throws IllegalBlockSizeException, BadPaddingException,
	UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec key = new SecretKeySpec(DatatypeConverter.parseHexBinary("DC70DE3562C03A11432630F6A1F6195D"),
				"AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plaintextBytes = cipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));
		return new String(plaintextBytes, "UTF-8");
	}

	public String AESStandardDecryptAllKeys(String encrypted) throws IllegalBlockSizeException, BadPaddingException,
	UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

		List<String> keys = new ArrayList<String>() {
			{
				add("DC70DE3562C03A11432630F6A1F6195D");
				add("9E3BCA0EA51E37394C99DB4E35D27561");
				add("D005E6356071FF700EABA75B56508149");
				add("ED9B61DF3A099140A022DE28AA952D30");
				add("A814D1895883984D162F1D3874B44547");
			}
		};
		for (int i = 0; i <= 4; i++) {
			try {
				Cipher cipher = Cipher.getInstance("AES");
				SecretKeySpec key = new SecretKeySpec(DatatypeConverter.parseHexBinary(keys.get(i)), "AES");
				cipher.init(Cipher.DECRYPT_MODE, key);
				byte[] plaintextBytes = cipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));
				return new String(plaintextBytes, "UTF-8");
			} catch (BadPaddingException e) {
				if (i == 4) {
					throw e;
				}
			}
		}
		throw new BadPaddingException("err");
	}

	// Test provide with russian character
	@Test
	@Ignore		// 
	// @Category(com.afklm.repindpp.paymentpreference.util.TestCategory.Slow.class)	// TU qui ne passe pas en local chez T412211
	public void providePaymentPreferencesImplTestDecryptWithSpecialCharRussian()
			throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException, SystemException, BusinessException,
			com.afklm.soa.stubs.w000471.v1.BusinessException {

		ProvidePaymentPreferencesImplTest.logger
		.info("providePaymentPreferencesImplTestDecryptWithSpecialCharRussian...");

		String gin = "411415094784";

		deletePP(gin);

		String address = "Санкт-Петербургконецщекаряд";
		String city = "мечрекамой";

		CreatePaymentPreferencesRequest requestCreate = createRequestPaymentPreferences(gin, address, city);

		createOrReplacePaymentPreferencesImpl.createOrReplacePaymentPreferences(requestCreate);

		// Provide of the payment preferences tested
		ProvidePaymentPreferencesRequest requestProvide = new ProvidePaymentPreferencesRequest();
		requestProvide.setGin(gin);

		ProvidePaymentPreferencesResponse responseProvide = providePaymentPreferencesImpl.providePaymentPreferences(requestProvide);

		// Check how many payment fields are returned
		Assert.assertEquals(responseProvide.getProvidefields().size(), 7);

		// Check if characters are returned
		String billAddressReturned = "";
		String billCityReturned = "";

		for (int i = 0; i < responseProvide.getProvidefields().size(); i++) {

			if (responseProvide.getProvidefields().get(i).getPaymentFieldCode().equals("BILL_ADDRESS")) {
				billAddressReturned = responseProvide.getProvidefields().get(i).getPaymentFieldPreferences();
			} else if (responseProvide.getProvidefields().get(i).getPaymentFieldCode().equals("BILL_CITY")) {
				billCityReturned = responseProvide.getProvidefields().get(i).getPaymentFieldPreferences();
			}
		}

		Assert.assertEquals(billAddressReturned, address);
		Assert.assertEquals(billCityReturned, city);

	}

	// Test provide with chinese character
	@Test
	@Ignore
	// @Category(com.afklm.repindpp.paymentpreference.util.TestCategory.Slow.class)	// TU qui ne passe pas en local chez T412211
	public void providePaymentPreferencesImplTestDecryptWithSpecialCharChinese()
			throws com.afklm.soa.stubs.w000470.v2_0_1.BusinessException, SystemException, BusinessException,
			com.afklm.soa.stubs.w000471.v1.BusinessException {

		ProvidePaymentPreferencesImplTest.logger
		.info("providePaymentPreferencesImplTestDecryptWithSpecialCharChinese...");

		String gin = "411415094784";

		deletePP(gin);

		String address = "杭大路";
		String city = "号嘉华厦门市湖里区金浙";

		CreatePaymentPreferencesRequest requestCreate = createRequestPaymentPreferences(gin, address, city);

		createOrReplacePaymentPreferencesImpl.createOrReplacePaymentPreferences(requestCreate);

		// Provide of the payment preferences tested
		ProvidePaymentPreferencesRequest requestProvide = new ProvidePaymentPreferencesRequest();
		requestProvide.setGin(gin);

		ProvidePaymentPreferencesResponse responseProvide = providePaymentPreferencesImpl.providePaymentPreferences(requestProvide);

		// Check how many payment fields are returned
		Assert.assertEquals(responseProvide.getProvidefields().size(), 7);

		// Check if characters are returned
		String billAddressReturned = "";
		String billCityReturned = "";

		for (int i = 0; i < responseProvide.getProvidefields().size(); i++) {

			if (responseProvide.getProvidefields().get(i).getPaymentFieldCode().equals("BILL_ADDRESS")) {
				billAddressReturned = responseProvide.getProvidefields().get(i).getPaymentFieldPreferences();
			} else if (responseProvide.getProvidefields().get(i).getPaymentFieldCode().equals("BILL_CITY")) {
				billCityReturned = responseProvide.getProvidefields().get(i).getPaymentFieldPreferences();
			}
		}

		Assert.assertEquals(billAddressReturned, address);
		Assert.assertEquals(billCityReturned, city);

	}

	@Test
	public void providePaymentPreferencesImplTest() throws JrafDomainException, BusinessException, SystemException {

		ProvidePaymentPreferencesImplTest.logger.info("providePaymentPreferencesImplTest...");

		ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
		request.setGin("400331206250");

		ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl.providePaymentPreferences(request);

		Assert.assertNotEquals(null, response);

		Assert.assertEquals("KL", response.getAirlinePaymentPref());
		Assert.assertEquals("CC", response.getPaymentGroupType());
		Assert.assertEquals("VISA", response.getPaymentMethod());
		Assert.assertEquals("NL", response.getPointOfSale());

		List<ProvideFields> pfl = response.getProvidefields();

		for (ProvideFields pf : pfl) {

			Assert.assertNotEquals(null, pf);

			switch (pf.getPaymentFieldCode()) {
			case "PCI_TOKEN":
				Assert.assertEquals("493831ABWN2X7672", pf.getPaymentFieldPreferences());
				break;
			case "BILL_COUNTRY":
				Assert.assertEquals("NL", pf.getPaymentFieldPreferences());
				break;
			case "BILL_NAME":
				Assert.assertEquals("BLAAUBOER", pf.getPaymentFieldPreferences());
				break;
			case "BILL_FIRSTNAME":
				Assert.assertEquals("JACQUELINE", pf.getPaymentFieldPreferences());
				break;
			}
		}
		// logger.error("Probleme car pas de test ");
		// Assert.fail("Probleme car pas de test ");
	}

	@Test	// ProvidePaymentPreferencesImplTest providePaymentPreferencesImplOnManyGinsTest History	2 mins 	
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void providePaymentPreferencesImplOnManyGinsTest()
			throws JrafDomainException, BusinessException, SystemException {

		ProvidePaymentPreferencesImplTest.logger.info("providePaymentPreferencesImplOnManyGinsTest...");

		List<String> tabCasX_Autre = new ArrayList<>();
		List<String> tabCas5_ERROR = new ArrayList<>();

		Long statTOTAL = 0L;
		Long statCas0_PCITOKEN_Seul_Non_Vide = 0L;
		Long statCas1_CCNR_Plus_PCITOKEN = 0L;
		Long statCas2_CCNR_Seul_Vide = 0L;
		Long statCas3_CCNR_Seul_Non_Vide = 0L;
		Long statCas4_Aucun_Field = 0L;
		Long statCas5_ERROR = 0L;
		Long statCas6_CCNR_PCITOKEN_Aucun = 0L;
		Long statCasX_Autre = 0L;

		Long statCas_CCNR_Numeric = 0L;

		// String ginDepart = "400360000000";
		// String ginArrivee = "400380000000";

		// String ginDepart = "400460000000";
		// String ginArrivee = "400480000000";

		// String ginDepart = "400480000000";
		// String ginArrivee = "400490000000";

		// String ginDepart = "900010000000";
		// String ginArrivee = "900020000000";

		// String ginDepart = "110000000000";
		// String ginArrivee = "110050000000";

		// String ginDepart = "400480100000";
		// String ginArrivee = "400480200000";

		String ginDepart = "400004543487"; // 400441255884 CCNR_NUmeric
		String ginArrivee = "400004643487";

		// Long nombreTOTAL = 500000L;
		Long nombreTOTAL = 50L;

		try {

			// On cherche des GIN
			// List<PaymentDetails> lpd = paymentDetailsDao.findWhere("where
			// paymentdetails.gin between '002001200000' and '002001700000'");
			/*List<Paymentdetails> lpd = paymentDetailsDao.findWhere("where paymentdetails.gin between '" + ginDepart
					+ "' and '" + ginArrivee + "'and paymentdetails.paymentGroup in ('CC', 'DC')");*/
			
			List<String> listPaymentGroup = Arrays.asList("CC", "DC");
			
			List<PaymentDetails> lpd = paymentDetailsDao.findByGinBetweenAndPaymentGroupIn(ginDepart, ginArrivee, listPaymentGroup);

			for (PaymentDetails pd : lpd) {

				String gin = pd.getGin();

				ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
				request.setGin(gin);

				try {
					ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl
							.providePaymentPreferences(request);

					if (response != null) {

						List<ProvideFields> pfl = response.getProvidefields();

						if (pfl != null && pfl.size() == 0) {
							statCas4_Aucun_Field++;
						} else {
							if (Cas0_PCITOKEN_Seul_Non_Vide(pfl)) {
								statCas0_PCITOKEN_Seul_Non_Vide++;
							} else {
								if (Cas2_CCNR_Seul_Vide(pfl)) {
									statCas2_CCNR_Seul_Vide++;
								} else if (Cas1_CCNR_Plus_PCITOKEN(pfl)) {

									if (Cas_CCNR_Numeric(pfl)) {
										ProvidePaymentPreferencesImplTest.logger.info("CC_NR= " + gin);
										statCas_CCNR_Numeric++;
									}

									statCas1_CCNR_Plus_PCITOKEN++;
								} else if (Cas3_CCNR_Seul_Non_Vide(pfl)) {

									if (Cas_CCNR_Numeric(pfl)) {
										ProvidePaymentPreferencesImplTest.logger.info("CC_NR= " + gin);
										statCas_CCNR_Numeric++;
									}

									statCas3_CCNR_Seul_Non_Vide++;
								} else if (Cas6_CCNR_PCITOKEN_Aucun(pfl)) {
									statCas6_CCNR_PCITOKEN_Aucun++;
								} else {
									statCasX_Autre++;
									tabCasX_Autre.add(gin);
								}
							}
						}
					}

				} catch (Exception ex) {
					statCas5_ERROR++;
					tabCas5_ERROR.add(gin);
				}

				statTOTAL++;

				// On stoppe des que l on a parcourru et traite X GIN
				if (statTOTAL >= nombreTOTAL) {
					break;
				}
			}

			ProvidePaymentPreferencesImplTest.logger.info("PCITOKEN_Seul_Non_Vide= " + statCas0_PCITOKEN_Seul_Non_Vide
					+ "\t" + pourcentage(statCas0_PCITOKEN_Seul_Non_Vide, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_Seul_Vide=         " + statCas2_CCNR_Seul_Vide + "\t"
					+ pourcentage(statCas2_CCNR_Seul_Vide, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_Seul_Non_Vide=     " + statCas3_CCNR_Seul_Non_Vide
					+ "\t" + pourcentage(statCas3_CCNR_Seul_Non_Vide, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_Plus_PCITOKEN=     " + statCas1_CCNR_Plus_PCITOKEN
					+ "\t" + pourcentage(statCas1_CCNR_Plus_PCITOKEN, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_PCITOKEN_Aucun=    " + statCas6_CCNR_PCITOKEN_Aucun
					+ "\t" + pourcentage(statCas6_CCNR_PCITOKEN_Aucun, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("AUCUN_Field=            " + statCas4_Aucun_Field + "\t"
					+ pourcentage(statCas4_Aucun_Field, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger
			.info("ERROR=                  " + statCas5_ERROR + "\t" + pourcentage(statCas5_ERROR, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_Numeric=           " + statCas_CCNR_Numeric + "\t"
					+ pourcentage(statCas_CCNR_Numeric, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("========================================");
			ProvidePaymentPreferencesImplTest.logger.info("AUTRE=                  " + statCasX_Autre);
			ProvidePaymentPreferencesImplTest.logger.info("========================================");
			ProvidePaymentPreferencesImplTest.logger.info("TOTAL=                  " + statTOTAL);

			if (statCasX_Autre != 0) {
				for (String s : tabCasX_Autre) {
					ProvidePaymentPreferencesImplTest.logger.info("AUTRE=                  " + s);
				}
			}

			/*
			 * if (statCas5_ERROR != 0) { int i = 0; for (String s: tabCas5_ERROR) { i++;
			 * logger.info("ERROR=                  " + s); // On affiche que les 20
			 * premiers if (i > 20) { break; } } }
			 */
			Assert.assertEquals(Long.valueOf(0), Long.valueOf(statCasX_Autre));

			Assert.assertEquals(statTOTAL,
					Long.valueOf(statCas0_PCITOKEN_Seul_Non_Vide + statCas2_CCNR_Seul_Vide + statCas3_CCNR_Seul_Non_Vide
							+ statCas1_CCNR_Plus_PCITOKEN + statCas4_Aucun_Field + statCas5_ERROR
							+ statCas6_CCNR_PCITOKEN_Aucun));

		} catch (Exception ex) {

			String error = "Probleme dans le test " + ex.getMessage();
			ProvidePaymentPreferencesImplTest.logger.error(error);
			Assert.fail(error);
		}
	}

	@Test 	
	public void providePaymentPreferencesImplOnManyRandomGinsTest()
			throws JrafDomainException, BusinessException, SystemException {

		ProvidePaymentPreferencesImplTest.logger.info("providePaymentPreferencesImplOnManyRandomGinsTest...");

		List<String> tabCasX_Autre = new ArrayList<>();
		List<String> tabCas5_ERROR = new ArrayList<>();

		Long statTOTAL = 0L;
		Long statCas0_PCITOKEN_Seul_Non_Vide = 0L;
		Long statCas1_CCNR_Plus_PCITOKEN_NonNumeric = 0L;
		Long statCas2_CCNR_Seul_Vide = 0L;
		Long statCas3_CCNR_Seul_Non_Vide_NonNumeric = 0L;
		Long statCas4_Aucun_Field = 0L;
		Long statCas5_ERROR = 0L;
		Long statCas6_CCNR_PCITOKEN_Aucun = 0L;
		Long statCasX_Autre = 0L;

		Long statCas_CCNR_Numeric = 0L;

		String ginDepart = null;
		String ginArrivee = null;

		// Long nombreTOTAL = 3000000L;
		Long nombreTOTAL = 20L;

		try {

			for (long i = 0L; i < nombreTOTAL; i++) {
			
				// On genere 4001 2345 6789 01
				String ginDepartDebut = "400";
				String ginSousGin = RandomStringUtils.randomNumeric(10 - 3);
				ginDepart = ginDepartDebut + ginSousGin + "00";
				ginArrivee = ginDepartDebut + ginSousGin + "99";
				
				// On cherche des GIN
				/*List<Paymentdetails> lpd = paymentDetailsDao.findWhere("where paymentdetails.gin between '" + ginDepart
						+ "' and '" + ginArrivee + "'and paymentdetails.paymentGroup in ('CC', 'DC')");*/
				
				List<String> listPaymentGroup = Arrays.asList("CC", "DC");
				
				List<PaymentDetails> lpd = paymentDetailsDao.findByGinBetweenAndPaymentGroupIn(ginDepart, ginArrivee, listPaymentGroup);
	
				for (PaymentDetails pd : lpd) {
	
					String gin = pd.getGin();
	
					ProvidePaymentPreferencesImplTest.logger.info(i + " - " + gin);					
					
					ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
					request.setGin(gin);
	
					try {
						ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl
								.providePaymentPreferences(request);
	
						if (response != null) {
	
							List<ProvideFields> pfl = response.getProvidefields();
	
							if (pfl != null && pfl.size() == 0) {
								statCas4_Aucun_Field++;
							} else {
								if (Cas0_PCITOKEN_Seul_Non_Vide(pfl)) {
									statCas0_PCITOKEN_Seul_Non_Vide++;
								} else {
									if (Cas2_CCNR_Seul_Vide(pfl)) {
										statCas2_CCNR_Seul_Vide++;
									} else if (Cas1_CCNR_Plus_PCITOKEN(pfl)) {
	
										if (Cas_CCNR_Numeric(pfl)) {
											ProvidePaymentPreferencesImplTest.logger.info("CC_NR= " + gin);
											statCas_CCNR_Numeric++;
										} else {	
											statCas1_CCNR_Plus_PCITOKEN_NonNumeric++;
										}
									} else if (Cas3_CCNR_Seul_Non_Vide(pfl)) {
	
										if (Cas_CCNR_Numeric(pfl)) {
											ProvidePaymentPreferencesImplTest.logger.info("CC_NR= " + gin);
											statCas_CCNR_Numeric++;
										} else {	
											statCas3_CCNR_Seul_Non_Vide_NonNumeric++;
										}
									} else if (Cas6_CCNR_PCITOKEN_Aucun(pfl)) {
										statCas6_CCNR_PCITOKEN_Aucun++;
									} else {
										statCasX_Autre++;
										tabCasX_Autre.add(gin);
									}
								}
							}
						}
	
					} catch (Exception ex) {
						statCas5_ERROR++;
						tabCas5_ERROR.add(gin);
					}
	
					statTOTAL++;
	
					// On stoppe des que l on a parcourru et traite X GIN
					if (statTOTAL >= nombreTOTAL) {
						break;
					}
				}
			
			}

			ProvidePaymentPreferencesImplTest.logger.info("PCITOKEN_Seul_Non_Vide= " + statCas0_PCITOKEN_Seul_Non_Vide
					+ "\t" + pourcentage(statCas0_PCITOKEN_Seul_Non_Vide, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_Seul_Vide=         " + statCas2_CCNR_Seul_Vide + "\t"
					+ pourcentage(statCas2_CCNR_Seul_Vide, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_Seul_Non_Numeric=  " + statCas3_CCNR_Seul_Non_Vide_NonNumeric
					+ "\t" + pourcentage(statCas3_CCNR_Seul_Non_Vide_NonNumeric, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_PCITOKEN_NonNum=   " + statCas1_CCNR_Plus_PCITOKEN_NonNumeric
					+ "\t" + pourcentage(statCas1_CCNR_Plus_PCITOKEN_NonNumeric, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_PCITOKEN_Aucun=    " + statCas6_CCNR_PCITOKEN_Aucun
					+ "\t" + pourcentage(statCas6_CCNR_PCITOKEN_Aucun, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("AUCUN_Field=            " + statCas4_Aucun_Field + "\t"
					+ pourcentage(statCas4_Aucun_Field, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger
			.info("ERROR=                  " + statCas5_ERROR + "\t" + pourcentage(statCas5_ERROR, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("CCNR_Numeric=           " + statCas_CCNR_Numeric + "\t"
					+ pourcentage(statCas_CCNR_Numeric, statTOTAL));
			ProvidePaymentPreferencesImplTest.logger.info("========================================");
			ProvidePaymentPreferencesImplTest.logger.info("AUTRE=                  " + statCasX_Autre);
			ProvidePaymentPreferencesImplTest.logger.info("========================================");
			ProvidePaymentPreferencesImplTest.logger.info("TOTAL=                  " + statTOTAL);

			if (statCasX_Autre != 0) {
				for (String s : tabCasX_Autre) {
					ProvidePaymentPreferencesImplTest.logger.info("AUTRE=                  " + s);
				}
			}

			/*
			 * if (statCas5_ERROR != 0) { int i = 0; for (String s: tabCas5_ERROR) { i++;
			 * logger.info("ERROR=                  " + s); // On affiche que les 20
			 * premiers if (i > 20) { break; } } }
			 */
			Assert.assertEquals(Long.valueOf(0), Long.valueOf(statCasX_Autre));

			Assert.assertEquals(statTOTAL,
					Long.valueOf(statCas0_PCITOKEN_Seul_Non_Vide + statCas2_CCNR_Seul_Vide + statCas3_CCNR_Seul_Non_Vide_NonNumeric
							+ statCas1_CCNR_Plus_PCITOKEN_NonNumeric + statCas4_Aucun_Field + statCas5_ERROR
							+ statCas6_CCNR_PCITOKEN_Aucun));

		} catch (Exception ex) {

			String error = "Probleme dans le test " + ex.getMessage();
			ProvidePaymentPreferencesImplTest.logger.error(error);
			Assert.fail(error);
		}
	}

	@Test
	public void providePaymentPreferencesImplOnManyCCNRGinsTest()
			throws JrafDomainException, BusinessException, SystemException {

		ProvidePaymentPreferencesImplTest.logger.info("providePaymentPreferencesImplOnManyCCNRGinsTest...");

		/*String ginCCNR = "'400269300280','400462403083','400022398030','400357475043','400315858735','400345403355','400463272870','400450524374','400021881500','400441930485','400423747300','400050512535','400151394332','400428171860'," + 
				"'400402859112','400091420093','400110217230','400428171860','400423747300','400461337576','400042626265','400041138021','400464016992','400455405625','400210072462','400091420093','400455405625','400374013954','400210072462','400236348292','400462095960','400132800125','400398933324','400042626265'";*/

		try {
			
			// On cherche des GIN
			// List<PaymentDetails> lpd = paymentDetailsDao.findWhere("where
			// paymentdetails.gin between '002001200000' and '002001700000'");
			//List<Paymentdetails> lpd = paymentDetailsDao.findWhere("where paymentdetails.gin in (" + ginCCNR + ") and paymentdetails.paymentGroup in ('CC', 'DC')");
			
			List<String> listGins = Arrays.asList("400269300280","400462403083","400022398030","400357475043","400315858735","400345403355","400463272870","400450524374","400021881500","400441930485","400423747300","400050512535","400151394332","400428171860","400402859112","400091420093","400110217230","400428171860","400423747300","400461337576","400042626265","400041138021","400464016992","400455405625","400210072462","400091420093","400455405625","400374013954","400210072462","400236348292","400462095960","400132800125","400398933324","400042626265");
			
			List<String> listPaymentGroup = Arrays.asList("CC", "DC");
			
			List<PaymentDetails> lpd = paymentDetailsDao.findByGinInAndPaymentGroupIn(listGins, listPaymentGroup);
			
			
			for (PaymentDetails pd : lpd) {

				String gin = pd.getGin();

				ProvidePaymentPreferencesImplTest.logger.info(gin);					
				
				ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
				request.setGin(gin);

				try {
					ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl
							.providePaymentPreferences(request);

					if (response != null) {

						List<ProvideFields> pfl = response.getProvidefields();

						for (ProvideFields pf : pfl) {
							ProvidePaymentPreferencesImplTest.logger.info(pf.getPaymentFieldCode() + " - " + pf.getPaymentFieldPreferences());	
						}
					}

				} catch (Exception ex) {
					ProvidePaymentPreferencesImplTest.logger.info("ERROR " + ex);
				}
			}


		} catch (Exception ex) {

			String error = "Probleme dans le test " + ex.getMessage();
			ProvidePaymentPreferencesImplTest.logger.error(error);
			Assert.fail(error);
		}
	}
	
	private String pourcentage(Long nombre, Long nombreTotal) {

		if (nombre != 0) {
			return (100 * Float.valueOf(Float.valueOf(nombre) / Float.valueOf(nombreTotal))) + "%";
		} else {
			return "0%";
		}
	}
	
	private boolean Cas0_PCITOKEN_Seul_Non_Vide(List<ProvideFields> pfl) {
		boolean pci_token = false;
		boolean cc_nr = false;

		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					if ("CC_NR".equals(pf.getPaymentFieldCode())) {
						cc_nr = true;
					}
					if ("PCI_TOKEN".equals(pf.getPaymentFieldCode()) && !"".equals(pf.getPaymentFieldPreferences())) {
						pci_token = true;
					}
				}
			}
		}
		return pci_token && !cc_nr;
	}

	private boolean Cas6_CCNR_PCITOKEN_Aucun(List<ProvideFields> pfl) {
		boolean pci_token = false;
		boolean cc_nr = false;

		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					if ("CC_NR".equals(pf.getPaymentFieldCode())) {
						cc_nr = true;
					}
					if ("PCI_TOKEN".equals(pf.getPaymentFieldCode()) && !"".equals(pf.getPaymentFieldPreferences())) {
						pci_token = true;
					}
				}
			}
		}
		return !pci_token && !cc_nr;
	}

	private boolean Cas2_CCNR_Seul_Vide(List<ProvideFields> pfl) {
		boolean retour = false;

		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					// TODO : Tester si il n est pas vide
					if ("PCI_TOKEN".equals(pf.getPaymentFieldCode())) {
						return false;
					}
					if ("CC_NR".equals(pf.getPaymentFieldCode()) && "".equals(pf.getPaymentFieldPreferences())) {
						retour = true;
					}
				}
			}
		}
		return retour;
	}

	private boolean Cas3_CCNR_Seul_Non_Vide(List<ProvideFields> pfl) {
		boolean retour = false;

		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					// TODO : Tester si il n est pas vide
					if ("PCI_TOKEN".equals(pf.getPaymentFieldCode())) {
						return false;
					}
					if ("CC_NR".equals(pf.getPaymentFieldCode()) && !"".equals(pf.getPaymentFieldPreferences())) {
						retour = true;
					}
				}
			}
		}
		return retour;
	}

	private boolean Cas_CCNR_Numeric(List<ProvideFields> pfl) {

		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					// TODO : Tester si il n est pas vide
					if ("CC_NR".equals(pf.getPaymentFieldCode()) && isNumeric(pf.getPaymentFieldPreferences())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean Cas1_CCNR_Plus_PCITOKEN(List<ProvideFields> pfl) {

		boolean cc_nr = false;
		boolean pci_token = false;

		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					// TODO : Tester si il n est pas vide
					if ("PCI_TOKEN".equals(pf.getPaymentFieldCode())) {
						pci_token = true;
					}
					if ("CC_NR".equals(pf.getPaymentFieldCode())) {
						cc_nr = true;
					}
				}
			}
		}
		return pci_token && cc_nr;
	}

	@Test
	public void providePaymentPreferencesImplOnSomeGinTest()
			throws JrafDomainException, BusinessException, SystemException {

		ProvidePaymentPreferencesImplTest.logger.info("providePaymentPreferencesImplOnSomeGinTest...");

		ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
		request.setGin("400365157613"); // Aucun CC_NR et Ni PCI_TOKEN
		// request.setGin("400364775354"); // Aucun CC_NR et Ni PCI_TOKEN
		// request.setGin("400364344316"); // Aucun CC_NR et Ni PCI_TOKEN

		ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl.providePaymentPreferences(request);

		Assert.assertNotEquals(null, response);

		ProvidePaymentPreferencesImplTest.logger.info(response.getAirlinePaymentPref());
		ProvidePaymentPreferencesImplTest.logger.info(response.getPaymentGroupType());
		ProvidePaymentPreferencesImplTest.logger.info(response.getPaymentMethod());
		ProvidePaymentPreferencesImplTest.logger.info(response.getPointOfSale());
		ProvidePaymentPreferencesImplTest.logger.info(response.getAirlinePaymentPref());

		List<ProvideFields> pfl = response.getProvidefields();

		for (ProvideFields pf : pfl) {
			ProvidePaymentPreferencesImplTest.logger
			.info(pf.getPaymentFieldCode() + " " + pf.getPaymentFieldPreferences());
		}
	}

	@Test
	public void providePaymentPreferencesImplOnOtherIsTokenizedTest()
			throws JrafDomainException, BusinessException, SystemException {

		ProvidePaymentPreferencesImplTest.logger.info("providePaymentPreferencesImplOnOtherIsTokenizedTest...");

		Long statNbErreur = 0L;
		Long statTOTAL = 0L;

		try {

			// On cherche des GIN Non Tokenized mais qui ne sont pas des CC ou DC
			// KO:18/727 - pour E, N sur non CC et DC
			// KO:2931/206361 - pour Y sur non CC et DC
			/*List<Paymentdetails> lpd = paymentDetailsDao.findWhere(
					"where paymentdetails.isTokenized in ('N', 'E') and paymentdetails.paymentGroup not in ('CC', 'DC') ");*/
			// List<PaymentDetails> lpd = paymentDetailsDao.findWhere("where
			// paymentdetails.isTokenized = 'Y' and paymentdetails.paymentGroup not in
			// ('CC', 'DC') ");

			List<PaymentDetails> lpd = paymentDetailsDao.findTokenInAndPaymentgroupNotIn();
			
			for (PaymentDetails pd : lpd) {

				String gin = pd.getGin();

				ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
				request.setGin(gin);

				try {

					ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl
							.providePaymentPreferences(request);

					if (response != null) {

						// List<ProvideFields> pfl = response.getProvidefields();
						ProvidePaymentPreferencesImplTest.logger.info("GIN: " + gin + " OK");
					}
				} catch (Exception ex) {
					ProvidePaymentPreferencesImplTest.logger
					.error("Probleme sur ce GIN " + gin + " car " + ex.getMessage());
					statNbErreur++;
				}
				statTOTAL++;
			}


			ProvidePaymentPreferencesImplTest.logger.info("KO:" + statNbErreur + "/" + statTOTAL);

		} catch (Exception ex) {

			String error = "Probleme dans le test " + ex.getMessage();
			ProvidePaymentPreferencesImplTest.logger.error(error);
			Assert.fail(error);
		}
	}

	public void deletePP(String gin) throws com.afklm.soa.stubs.w000471.v1.BusinessException, SystemException {

		// Remove of the payment preferences created above
		// ProvidePaymentPreferencesImplTest.logger.info("Delete all PP for gin :
		// "+gin);
		DeletePaymentPreferencesRequest requestDelete = new DeletePaymentPreferencesRequest();
		requestDelete.setGin(gin);
		requestDelete.setSignature("UnitTest");
		requestDelete.setSignatureSite("UnitTest");
		requestDelete.setIpAdress("127.0.0.1");

		try {
			DeletePaymentPreferencesResponse responseDelete = null;
			responseDelete = deletePaymentPreferencesImpl.deletePaymentPreferences(requestDelete);
		} catch (Exception e) {
			ProvidePaymentPreferencesImplTest.logger.info(e);
		}

	}

	public CreatePaymentPreferencesRequest createRequestPaymentPreferences(String gin, String address, String city) {

		// Creation of the payment preferences for test
		CreatePaymentPreferencesRequest requestCreate = new CreatePaymentPreferencesRequest();

		requestCreate.setGin(gin);
		requestCreate.setCardName("testImpl");
		requestCreate.setPaymentGroupType("CC");
		requestCreate.setPreferred("Y");
		requestCreate.setCorporate("Y");
		requestCreate.setIpAdress("127.0.0.1");
		requestCreate.setSignature("UnitTest");
		requestCreate.setSignatureSite("UnitTest");
		requestCreate.setPointOfSale("GB");
		requestCreate.setPaymentMethod("VISA");
		requestCreate.setAirlinePaymentPref("KL");

		CreateFields billCountry = new CreateFields();
		billCountry.setPaymentFieldCode("BILL_COUNTRY");
		billCountry.setPaymentFieldPreferences("NO");
		requestCreate.getCreateListfields().add(billCountry);

		// Add a field with special character
		CreateFields billAddress = new CreateFields();
		billAddress.setPaymentFieldCode("BILL_ADDRESS");
		billAddress.setPaymentFieldPreferences(address);
		requestCreate.getCreateListfields().add(billAddress);

		// Add a field with special character
		CreateFields billCity = new CreateFields();
		billCity.setPaymentFieldCode("BILL_CITY");
		billCity.setPaymentFieldPreferences(city);
		requestCreate.getCreateListfields().add(billCity);

		CreateFields ccExpDate = new CreateFields();
		ccExpDate.setPaymentFieldCode("CC_EXP_DATE");
		ccExpDate.setPaymentFieldPreferences("0918");
		requestCreate.getCreateListfields().add(ccExpDate);

		CreateFields billZip = new CreateFields();
		billZip.setPaymentFieldCode("BILL_ZIP");
		billZip.setPaymentFieldPreferences("6005");
		requestCreate.getCreateListfields().add(billZip);

		CreateFields token = new CreateFields();
		token.setPaymentFieldCode("PCI_TOKEN");
		token.setPaymentFieldPreferences("541063B27LZ39697");
		requestCreate.getCreateListfields().add(token);

		CreateFields lname = new CreateFields();
		lname.setPaymentFieldCode("BILL_NAME");
		lname.setPaymentFieldPreferences(address);
		requestCreate.getCreateListfields().add(lname);

		return requestCreate;
	}

	public boolean isNumeric(String str) {
		boolean result = false;
		result = str.matches("^\\d+$");
		return result;
	}
	
	/* 
	 * Extract
	 * 
	 */
	@Test(expected = Test.None.class /* no exception expected */)
	public void providePaymentPreferencesImpl_analyseDBForCreditCardNotTokenized() throws FileNotFoundException, UnsupportedEncodingException {
		logger.info("START providePaymentPreferencesImpl_analyseDBForCreditCardNotTokenized...");

		int statsNumberOfLines = 0;
		int startId = 1;
		int endId = 1000;
		
		String path = "/tmp/PCIDSS/Analysis/";
//		String file = "reportPCIDSS-index-" + startId + "to" + endId + ".txt";
		String file = "reportPCIDSS-last365days_create.txt";
		
		PrintWriter writer = new PrintWriter(path + file, "UTF-8");
		
//		writer.println("------ Start analysis from id " + startId + " to " + endId + " ------");
		writer.println("------ Start analysis ------");

		// List<PaymentDetails> lpd = paymentDetailsDao.getByLastModificationDate("365");
		List<PaymentDetails> lpd = paymentDetailsDao.getByModificationDate("184", "365");
		
		statsNumberOfLines = lpd.size();

		logger.info(statsNumberOfLines + " lines found");
		writer.println("Number of lines to analyze: " + statsNumberOfLines);

		lpd.stream().parallel().forEach(pd -> this.analyzeData(pd, writer));
		
		writer.println("Number of conform Credit card found: " + statsConform);
		writer.println("Number of NOT conform Credit card found: " + statsNotConform);
		writer.println("Number of lines analyzed: " + statsNumberOfLines);
		//writer.println("Payment id index range analyzed: " + startId + " to " + endId);
		writer.println("Number of error: " + statsNbErreur);
		
		logger.info("END providePaymentPreferencesImpl_analyseDBForCreditCardNotTokenized...");
		
		writer.println("------ End analysis in LIVE ------");
		writer.close();
	}
	
	private boolean Cas_CCNR_Numeric(List<ProvideFields> pfl, String ccnc) {
		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					// TODO : Tester si il n est pas vide
					if ("CC_NR".equals(pf.getPaymentFieldCode()) && isNumeric(pf.getPaymentFieldPreferences())) {
						
						ccnc = pf.getPaymentFieldPreferences();
						return true;
					}
				}
			}
		}
		return false;
	}

	private void analyzeData(PaymentDetails pd, PrintWriter writer) {
		String gin = pd.getGin();

		ProvidePaymentPreferencesRequest request = new ProvidePaymentPreferencesRequest();
		request.setGin(gin);

		try {

			ProvidePaymentPreferencesResponse response = providePaymentPreferencesImpl.providePaymentPreferences(request);

			if (response != null) {

				List<ProvideFields> pfl = response.getProvidefields();
				String ccnc = null;
				String dateModif = "null";
				if (pd.getChangingDate() != null) {
					dateModif = pd.getChangingDate().toString();
				}
				else if (pd.getDateCreation() != null) {
					dateModif = pd.getDateCreation().toString();
				}

				if (Cas_CCNR_Numeric(pfl, ccnc) || Cas_PCI_TOKEN_Numeric(pfl, ccnc)) {
					writer.println("GIN: " + gin + " Not conform ");
					statsNotConform++;
					String finalDateModif = dateModif;
					pfl.stream().forEach(pf -> {
						if ("PCI_TOKEN".equalsIgnoreCase(pf.getPaymentFieldCode())) {
							writer.println("** CC Not conform found : " + pf.getPaymentFieldPreferences() + " - update : " + finalDateModif);
							logger.info(statsNotConform + " lines not conform");
						}
					});
				}
				else {
					writer.println("GIN: " + gin + " OK" + " - update : " + dateModif);
					statsConform++;

					if (statsConform % 1000 == 0) {
						logger.info(statsConform + " lines conform");
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Probleme sur ce GIN " + gin + " car " + ex.getLocalizedMessage());
			writer.println("Probleme sur ce GIN " + gin + " car " + ex.getLocalizedMessage());
			statsNbErreur++;
		}
	}
	
	private boolean Cas_PCI_TOKEN_Numeric(List<ProvideFields> pfl, String ccnc) {
		if (pfl != null) {
			for (ProvideFields pf : pfl) {
				if (pf != null) {
					// TODO : Tester si il n est pas vide
					if ("PCI_TOKEN".equals(pf.getPaymentFieldCode()) && isNumeric(pf.getPaymentFieldPreferences())) {
						
						ccnc = pf.getPaymentFieldPreferences();
						return true;
					}
				}
			}
		}
		return false;
	}
}
