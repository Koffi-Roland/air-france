package com.airfrance.repind.service.adresse.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.dto.adresse.EmailDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class EmailDSTest {
	
	@Autowired
	private EmailDS emailDS;
	
	/* 
	 * If the parameter are blank -> throw a InvalidParameterException 
	 */
	@Test
	public void isEmailSharedInvalidParameter() throws JrafDomainException {
		try {
			emailDS.isEmailShared(null, null);
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
		try {
			emailDS.isEmailShared(null, "tata");
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
		try {
			emailDS.isEmailShared("tata", null);
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
		try {
			emailDS.isEmailShared("", "tata");
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
		try {
			emailDS.isEmailShared("tata", "           ");
			Assert.fail();
		}catch(InvalidParameterException e) {}
		
	}
	
	/*
	 * No emails found -> return false
	 */
	@Test
	public void isEmailSharedNotFoundNull() throws JrafDomainException {
		List<EmailDTO> emails = null;
		boolean isShared = getEmailDSMock(emails).isEmailShared("toto", "9");
		Assert.assertFalse(isShared);
	}
	
	/*
	 * No emails found -> return false
	 */
	@Test
	public void isEmailSharedNotFoundEmpty() throws JrafDomainException {
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
		boolean isShared = getEmailDSMock(emails).isEmailShared("toto", "9");
		Assert.assertFalse(isShared);
	}
	
	/*
	 * Emails found but not shared -> return false
	 */
	@Test
	public void isEmailSharedNotShared() throws JrafDomainException {
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
		EmailDTO email1 = new EmailDTO();
		email1.setEmail("toto");
		email1.setSgin("9");
		emails.add(email1);
		boolean isShared = getEmailDSMock(emails).isEmailShared("toto", "9");
		Assert.assertFalse(isShared);
	}
	
	
	/*
	 * Emails found but not shared -> return false
	 */
	@Test
	public void isEmailSharedShared() throws JrafDomainException {
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
		EmailDTO email1 = new EmailDTO();
		EmailDTO email2 = new EmailDTO();
		EmailDTO email3 = new EmailDTO();
		EmailDTO email4 = new EmailDTO();
		
		email1.setEmail("toto");
		email2.setEmail("toto");
		email3.setEmail("toto");
		email4.setEmail("toto");
		
		email1.setSgin("1");
		email2.setSgin("2");
		email3.setSgin("3");
		email4.setSgin("9");

		emails.add(email1);
		emails.add(email2);
		emails.add(email3);
		emails.add(email4);

		boolean isShared = getEmailDSMock(emails).isEmailShared("toto", "9");
		Assert.assertTrue(isShared);
	}
	
	@Test
	public void emailValidAndExistForGinMissingParameter() throws JrafDomainException {
		try {
			getEmailDSMock(null).isValidAndExistForGin("", "");
			Assert.fail();
		}catch(MissingParameterException e){}
	}
	
	@Test
	public void emailValidAndExistForGinEmailNotValid() throws JrafDomainException {
		boolean result = getEmailDSMock(null).isValidAndExistForGin("sdfsdds", "toto");
		Assert.assertFalse(result);
	}
	
	@Test
	public void emailValidAndExistForGinEmailNotFound() throws JrafDomainException {
		boolean result = getEmailDSMock(null).isValidAndExistForGin("test@test.com", "toto");
		Assert.assertFalse(result);
	}
	
	@Test
	public void emailValidAndExistForGinEmailFound() throws JrafDomainException {
		List<EmailDTO> emails = new ArrayList<EmailDTO>();
		EmailDTO email1 = new EmailDTO();
		email1.setEmail("test@test.com");
		emails.add(email1);
		boolean result = getEmailDSMock(emails).isValidAndExistForGin("test@test.com", "toto");
		Assert.assertTrue(result);
	}

	
	public EmailDS getEmailDSMock(List<EmailDTO> emails) throws JrafDomainException {
		EmailDS emailDS = Mockito.mock(EmailDS.class);
		Mockito.when(emailDS.isEmailShared(Mockito.anyString(), Mockito.anyString())).thenCallRealMethod();
		Mockito.when(emailDS.isValidAndExistForGin(Mockito.anyString(), Mockito.anyString())).thenCallRealMethod();
		Mockito.when(emailDS.findByExample(Mockito.any(EmailDTO.class))).thenReturn(emails);
		return emailDS;
	}

}
