package com.airfrance.repind.service.marketing;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.config.WebTestConfig;
import com.airfrance.repind.util.SicStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class HandleCommunicationTest extends HandleCommunication {
    
	private static String KEY_REQUESTOR = "requestor";
	private static String UNKNOW = "unknown";
	private static String REINIT = "reinit";
	private static String KEY_ACTION_CODE = "action_code";
	private static String KEY_CHANNEL = "channel";
	private static String EMAIL = "email";
	private static String SMS = "sms";
	private static String KEY_GIN = "gin";
	private static String KEY_PASSWORD = "password";
	private static String TEST = "test";
	
	@Test
	public void fillPropertiesForRecoveryProcessWithBusinessKeyNull() throws JrafDomainException {
		String businessKey = null;
		String gin = "000000000000";
		String password = "password";
		Properties myProperties = SicStringUtils.decodeBusinessKey(
				businessKey, ";", "=");
		
		Properties properties = this.fillPropertiesForRecoveryProcess(
				gin, password, businessKey, myProperties);
		
		Assert.assertTrue(properties.get(KEY_REQUESTOR).toString().equals(UNKNOW));
		Assert.assertTrue(properties.get(KEY_ACTION_CODE).toString().equals(REINIT));
		Assert.assertTrue(properties.get(KEY_CHANNEL).toString().equals(EMAIL));
		Assert.assertTrue(properties.get(KEY_GIN).toString().equals(gin));
		Assert.assertTrue(properties.get(KEY_PASSWORD).toString().equals(password));
	}

	@Test
	public void fillPropertiesForRecoveryProcessWithKeyRequestorNoEmpty() throws JrafDomainException {
		String businessKey = "requestor=test";
		String gin = "000000000000";
		String password = "password";
		Properties myProperties = SicStringUtils.decodeBusinessKey(
				businessKey, ";", "=");
		
		Properties properties = this.fillPropertiesForRecoveryProcess(
				gin, password, businessKey, myProperties);
		
		Assert.assertTrue(properties.get(KEY_REQUESTOR).toString().equals(TEST));
		Assert.assertTrue(properties.get(KEY_ACTION_CODE).toString().equals(REINIT));
		Assert.assertTrue(properties.get(KEY_CHANNEL).toString().equals(EMAIL));
		Assert.assertTrue(properties.get(KEY_GIN).toString().equals(gin));
		Assert.assertTrue(properties.get(KEY_PASSWORD).toString().equals(password));
	}
	
	@Test
	public void fillPropertiesForRecoveryProcessWithActionCodeNoEmpty() throws JrafDomainException {
		String businessKey = "action_code=test";
		String gin = "000000000000";
		String password = "password";
		Properties myProperties = SicStringUtils.decodeBusinessKey(
				businessKey, ";", "=");
		
		Properties properties = this.fillPropertiesForRecoveryProcess(
				gin, password, businessKey, myProperties);
		
		Assert.assertTrue(properties.get(KEY_REQUESTOR).toString().equals(UNKNOW));
		Assert.assertTrue(properties.get(KEY_ACTION_CODE).toString().equals(TEST));
		Assert.assertTrue(properties.get(KEY_CHANNEL).toString().equals(EMAIL));
		Assert.assertTrue(properties.get(KEY_GIN).toString().equals(gin));
		Assert.assertTrue(properties.get(KEY_PASSWORD).toString().equals(password));
	}
	
	@Test
	public void fillPropertiesForRecoveryProcessWithChannelNoEmpty() throws JrafDomainException {
		String businessKey = "channel=test";
		String gin = "000000000000";
		String password = "password";
		Properties myProperties = SicStringUtils.decodeBusinessKey(
				businessKey, ";", "=");
		
		Properties properties = this.fillPropertiesForRecoveryProcess(
				gin, password, businessKey, myProperties);
		
		Assert.assertTrue(properties.get(KEY_REQUESTOR).toString().equals(UNKNOW));
		Assert.assertTrue(properties.get(KEY_ACTION_CODE).toString().equals(REINIT));
		Assert.assertTrue(properties.get(KEY_CHANNEL).toString().equals(TEST));
		Assert.assertTrue(properties.get(KEY_GIN).toString().equals(gin));
		Assert.assertTrue(properties.get(KEY_PASSWORD).toString().equals(password));
	}
	
	@Test
	public void fillPropertiesForRecoveryProcessWithBusinessKeyNoEmpty() throws JrafDomainException {
		String businessKey = "channel=test;action_code=test;requestor=test";
		String gin = "000000000000";
		String password = "password";
		Properties myProperties = SicStringUtils.decodeBusinessKey(
				businessKey, ";", "=");
		
		Properties properties = this.fillPropertiesForRecoveryProcess(
				gin, password, businessKey, myProperties);
		
		Assert.assertTrue(properties.get(KEY_REQUESTOR).toString().equals(TEST));
		Assert.assertTrue(properties.get(KEY_ACTION_CODE).toString().equals(TEST));
		Assert.assertTrue(properties.get(KEY_CHANNEL).toString().equals(TEST));
		Assert.assertTrue(properties.get(KEY_GIN).toString().equals(gin));
		Assert.assertTrue(properties.get(KEY_PASSWORD).toString().equals(password));
	}
	
	@Test
	public void fillPropertiesForRecoveryProcessWithChannelSMS() throws JrafDomainException {
		String businessKey = "channel=sms";;
		String gin = "000000000000";
		String password = "password";
		Properties myProperties = SicStringUtils.decodeBusinessKey(
				businessKey, ";", "=");
		
		Properties properties = this.fillPropertiesForRecoveryProcess(
				gin, password, businessKey, myProperties);
		
		Assert.assertTrue(properties.get(KEY_REQUESTOR).toString().equals(UNKNOW));
		Assert.assertTrue(properties.get(KEY_ACTION_CODE).toString().equals(REINIT));
		Assert.assertTrue(properties.get(KEY_CHANNEL).toString().equals(SMS));
		Assert.assertTrue(properties.get(KEY_GIN).toString().equals(gin));
		Assert.assertTrue(properties.get(KEY_PASSWORD).toString().equals(password));
	}
	
}
