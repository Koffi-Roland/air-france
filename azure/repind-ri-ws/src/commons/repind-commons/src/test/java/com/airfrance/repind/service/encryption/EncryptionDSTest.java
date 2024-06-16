package com.airfrance.repind.service.encryption;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.service.encryption.internal.EncryptionDS;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.util.EncryptionUtils;
import com.airfrance.repind.util.PwdContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EncryptionDSTest {

	/** logger */
	private static final Log logger = LogFactory.getLog(EncryptionDSTest.class);

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	private VariablesRepository variablesRepository;

	public EncryptionDS getCustomEncryptionDS(boolean robustPasswordActivated, boolean errorMode, int pinLenght,
			String gin)
			throws JrafDomainException, JrafApplicativeException {
		EncryptionDS encryptionDS = Mockito.mock(EncryptionDS.class);
		VariablesDS variablesDS = Mockito.mock(VariablesDS.class);

		Mockito.when(encryptionDS.isRobustPasswordErrorModeActivated()).thenReturn(errorMode); // Mock
		Mockito.when(encryptionDS.isRobustPasswordAuthenticateActivated()).thenReturn(robustPasswordActivated); // Mock
		Mockito.when(encryptionDS.isRobustPasswordGenerateActivated()).thenReturn(robustPasswordActivated); // Mock
		VariablesDTO variablesDto = new VariablesDTO();
		variablesDto.setEnvKey("pincodelength");
		variablesDto.setEnvValue("" + pinLenght);
		Mockito.when(variablesDS.getByEnvKey("pincodelength")).thenReturn(variablesDto);
		// implementation
		Mockito.when(variablesDS.getByEnvKey("generatepasswordpattern")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("generatepasswordpatternsdelimiter")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("generatepasswordpatternstomatch")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("passwordpatternstomatch")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("minlengthrobustpassword")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("maxlengthrobustpassword")).thenCallRealMethod();
		Mockito.when(encryptionDS.getCryptedGeneratePassword(gin)).thenCallRealMethod(); // Real implementation
		Mockito.when(encryptionDS.isPasswordCompliant(Mockito.anyString())).thenCallRealMethod(); // Real implementation
		ReflectionTestUtils.setField(encryptionDS, "variablesDS", variablesDS);
		ReflectionTestUtils.setField(variablesDS, "variablesRepository", variablesRepository);
		ReflectionTestUtils.setField(variablesDS, "entityManager", entityManager);

		return encryptionDS;
	}

	public EncryptionDS getCustomEncryptionDS(boolean robustPasswordActivated, boolean errorMode, int pinLenght,
			String gin, int minLength) throws JrafDomainException, JrafApplicativeException {

		EncryptionDS encryptionDS = getCustomEncryptionDS(robustPasswordActivated, errorMode, pinLenght, gin);
		VariablesDS variablesDS = Mockito.mock(VariablesDS.class);

		VariablesDTO variablesDto = new VariablesDTO();
		variablesDto.setEnvKey("pincodelength");
		variablesDto.setEnvValue("" + pinLenght);

		Mockito.when(variablesDS.getByEnvKey("pincodelength")).thenReturn(variablesDto);
		// implementation
		Mockito.when(variablesDS.getByEnvKey("generatepasswordpattern")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("generatepasswordpatternsdelimiter")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("generatepasswordpatternstomatch")).thenCallRealMethod();
		Mockito.when(variablesDS.getByEnvKey("passwordpatternstomatch")).thenCallRealMethod();
		variablesDto = new VariablesDTO();
		variablesDto.setEnvKey("minlengthrobustpassword");
		variablesDto.setEnvValue("" + minLength);
		Mockito.when(variablesDS.getByEnvKey("minlengthrobustpassword")).thenReturn(variablesDto);
		Mockito.when(variablesDS.getByEnvKey("maxlengthrobustpassword")).thenCallRealMethod();
		ReflectionTestUtils.setField(encryptionDS, "variablesDS", variablesDS);
		ReflectionTestUtils.setField(variablesDS, "variablesRepository", variablesRepository);
		ReflectionTestUtils.setField(variablesDS, "entityManager", entityManager);

		return encryptionDS;
	}

	/**
	 * Test getCryptedGeneratePasswordNumTest Generate a random password hashed
	 * with PwdPBKDF2WithHmacSHA1 and verify if is the same as encodated one
	 * with classic generation (only num, before robust password)
	 *
	 * @throws Exception
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 3 mins
	public void getCryptedGeneratePasswordTest() throws Exception {
		PwdContainer pwdContainer = getCustomEncryptionDS(false, false, 4, "000000018254")
				.getCryptedGeneratePassword("000000018254");
		EncryptionDSTest.logger.info(pwdContainer.getCryptedPwd());
		Assert.assertEquals(pwdContainer.getCryptedPwd(),
				EncryptionUtils.hashPBKDF2WithHmacSHA1("000000018254", pwdContainer.getNotCryptedPwd()));
	}

	/**
	 * Test isPasswordCompliant method when robust is activated but not in error
	 * mode
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 37 secs
	public void isPasswordCompliantErrorModeDisableTest()
			throws JrafDomainException, JrafApplicativeException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		EncryptionDS encryptionDS = getCustomEncryptionDS(true, false, 4, "1");
		// Good pattern, minimal size
		Assert.assertTrue(encryptionDS.isPasswordCompliant("Toto2234"));

		// We try some generated password against the checking system
		for (int i = 0; i < 100; i++) {
			PwdContainer container = getCustomEncryptionDS(true, false, 8, "123").getCryptedGeneratePassword("123");
			Assert.assertTrue(encryptionDS.isPasswordCompliant(container.getNotCryptedPwd()));
		}
	}

	/**
	 * Test to generate 100 password to check is compliant with Robust Password
	 * Pattern and maxlength for generated password. Expected: SUCCESS In case
	 * of errors: The length isn't good or the password don't implemented
	 * anymore Robust Rules.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class) // 75 secs
	public void isGenerateOnlyLengthMinPasswordLengthCompliantErrorModeDisableTest()
			throws JrafDomainException, JrafApplicativeException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		EncryptionDS encryptionDS = getCustomEncryptionDS(true, false, 4, "1");

		Method method = EncryptionDS.class.getDeclaredMethod("getMinLengthRobustPasswordFromDB");
		method.setAccessible(true);

		// We try some generated password against the checking system
		for (int i = 0; i < 100; i++) {
			PwdContainer container = getCustomEncryptionDS(true, false, 8, "123").getCryptedGeneratePassword("123");
			logger.info(container.getNotCryptedPwd() + " length:" + container.getNotCryptedPwd().length());
			//Verify that the password has the minimal size (and only the minimal size)
			Assert.assertTrue(container.getNotCryptedPwd().length() == (int) method.invoke(encryptionDS));
			Assert.assertTrue(encryptionDS.isPasswordCompliant(container.getNotCryptedPwd()));
		}
	}

	/**
	 * Test to generate 100 password to check if the exception in case of bad
	 * length is called Expected: SUCCESS
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class) // 10 secs
	public void isGenerateOnlyLengthMinPasswordLengthCompliantWithLengthNegativeErrorModeDisableTest()
			throws JrafDomainException, JrafApplicativeException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		Method method = EncryptionDS.class.getDeclaredMethod("getMinLengthRobustPasswordFromDB");
		method.setAccessible(true);

		// We try some generated password against the checking system
		for (int i = 0; i < 100; i++) {
			try {
				PwdContainer container = getCustomEncryptionDS(true, false, 8, "123", -1)
						.getCryptedGeneratePassword("123");
				logger.info(container.getNotCryptedPwd() + " length:" + container.getNotCryptedPwd().length());
				//Verify that the password has the minimal size (and only the minimal size)
				Assert.fail();
			} catch (JrafDomainException e) {
				Assert.assertSame(e.getMessage(), "minLength or maxLength isn't a valid value!");
			}

		}
	}

	/**
	 * Test isPasswordCompliant method when robust is activated but not in error
	 * mode
	 */
	@Test
	public void errorIsPasswordCompliantErrorModeDisableTest() throws JrafDomainException, JrafApplicativeException {
		EncryptionDS encryptionDS = getCustomEncryptionDS(true, false, 4, "1");
		Assert.assertFalse(encryptionDS.isPasswordCompliant("toto"));
		// We don't allow special chars yet
		Assert.assertFalse(encryptionDS.isPasswordCompliant("test*/0731:!1"));
		// Good pattern but too small
		Assert.assertFalse(encryptionDS.isPasswordCompliant("tt01"));
		// Good pattern but too big
		Assert.assertFalse(encryptionDS.isPasswordCompliant("mizapatoes391"));

		Assert.assertFalse(encryptionDS.isPasswordCompliant("totototo2345"));
		Assert.assertFalse(encryptionDS.isPasswordCompliant("123456789AZEZ"));
	}

	/**
	 * Error test Excepected : Error 932
	 * 
	 * Test isPasswordCompliant method when robust and error mode activated
	 */
	@Test
	public void errorIsPasswordCompliantErrorModeEnableTest() throws JrafDomainException, JrafApplicativeException {
		EncryptionDS encryptionDS = getCustomEncryptionDS(true, true, 4, "1");

		Assert.assertFalse(encryptionDS.isPasswordCompliant("toto"));
		// We don't allow special chars yet
		Assert.assertFalse(encryptionDS.isPasswordCompliant("test*/0731:!1"));
		// Good pattern but too small
		Assert.assertFalse(encryptionDS.isPasswordCompliant("tt01"));
		// Good pattern but too big
		Assert.assertFalse(encryptionDS.isPasswordCompliant("mizapatoes391"));

		Assert.assertFalse(encryptionDS.isPasswordCompliant("totototo2345"));
		Assert.assertFalse(encryptionDS.isPasswordCompliant("123456789AZEZ"));

	}

	/**
	 * Test isPasswordCompliant method when robust and error mode activated
	 */
	@Test
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)	// 37 secs
	public void isPasswordCompliantErrorModeEnableTest() throws JrafDomainException, JrafApplicativeException {
		EncryptionDS encryptionDS = getCustomEncryptionDS(true, true, 4, "1");
		// Good pattern, minimal size
		Assert.assertTrue(encryptionDS.isPasswordCompliant("Toto2234"));
		// Good pattern, maximal size
		Assert.assertTrue(encryptionDS.isPasswordCompliant("Totototo2345"));

		// We try some generated password against the checking system
		for (int i = 0; i < 100; i++) {
			PwdContainer container = getCustomEncryptionDS(true, true, 8, "123").getCryptedGeneratePassword("123");
			Assert.assertTrue(encryptionDS.isPasswordCompliant(container.getNotCryptedPwd()));
		}
	}

}
