package com.airfrance.repind.util.ut;

import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.util.EmailUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
public class EmailUtilsTest {

	/** logger */
	private static final Log logger = LogFactory.getLog(EmailUtilsTest.class);

	@Test
	public void isValidEmailTest() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto@hotmail.com");
		logger.info("isValidEmail = " + isValidEmail);
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest2() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto.com@hotmail.com");
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest3() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto.jo.?com@hotmail.com");
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest4() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto.jo-toto.com@hotmail.com");
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest5() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto.jo-toto.comhotmail.com");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest6() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto.jo-toto.comhotmail.com@");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest7() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto.jo-toto.com@hotmail.com.theokdealfreihofffff");
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest8() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("toto.jo.com@hotma\\il.com");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest9() throws Exception {
		boolean isValidEmail = EmailUtils
				.isValidEmail("toto.jo-toto.com@hotmail.com.theokdealfreihofffffeezgzeg.zetez.s");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest10() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("1toto.jo.comqfkhqohsfoqhfoqhfoqhfoqshfoj@hotmaffil.com.fjrks");
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest11() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("1toto.jo.comqfkhqohsfoqhfoqhfoqhfoqshfoj@hotmaffil.com.fjrksk");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest12() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("1toto.jo.comqfkhqj@hotmaffil.com.fjrks.");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest13() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("1toto.jo.comqfkhqj@hotmaffil.com.f3gdg");
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest14() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("Abc.@example.com");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest15() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("Ab c@example.com");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest16() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("_usermailg@ex.com");
		assertTrue(isValidEmail);
	}

	@Test
	public void isValidEmailTest17() throws Exception {
		boolean isValidEmail = EmailUtils.isValidEmail("user..mailg@ex.com");
		assertFalse(isValidEmail);
	}

	@Test
	public void isValidEmailTest18() throws Exception {
		// Valid email id
		assertTrue(EmailUtils.isValidEmail("email@example.com"));
		assertTrue(EmailUtils.isValidEmail("email@example.org.gov"));
		assertTrue(EmailUtils.isValidEmail("firstname.lastname@example.com"));
		assertTrue(EmailUtils.isValidEmail("email@subdomain.example.com"));
		assertTrue(EmailUtils.isValidEmail("firstname+lastname@example.com"));
		assertTrue(EmailUtils.isValidEmail("email@example-one.com"));
		assertTrue(EmailUtils.isValidEmail("_______@example.com"));
		assertTrue(EmailUtils.isValidEmail("email@example.name"));
		assertTrue(EmailUtils.isValidEmail("email@example.museum"));
		assertTrue(EmailUtils.isValidEmail("email@example.co.jp"));
		assertTrue(EmailUtils.isValidEmail("firstname-lastname@example.com"));
		assertTrue(EmailUtils.isValidEmail("email@123.123.123.123"));
		assertTrue(EmailUtils.isValidEmail("\"email\"@example.com"));
		assertTrue(EmailUtils.isValidEmail("email@[123.123.123.123]"));

		// Invalid email id
		assertFalse(EmailUtils.isValidEmail("email@[123.123.123.123"));
		assertFalse(EmailUtils.isValidEmail("a\"b(c)d,e:f;g<h>i[j\\k]l@example.com"));
		assertFalse(EmailUtils.isValidEmail("i_like_underscore@but_its_not_allowed_in_this_part.example.com"));
		assertFalse(EmailUtils.isValidEmail("\"email\"outsidequotes@example.com"));
	}
}
