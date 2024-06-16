package com.afklm.repind.v7.provideindividualdata.ut.helpers;

import com.afklm.config.WebTestConfig;
import com.afklm.repind.v7.provideindividualdata.helpers.ForgetStatusHelper;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.ForgottenIndividualDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestConfig.class)
@Transactional(value = "transactionManagerRepind")
public class ForgetStatusHelperTest {
	
	@Autowired
	private ForgetStatusHelper forgetStatusHelper;
	
	@Test
	public void testForgetStatus_checkConformityContextCforIndividualPurged() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualPurged = null;
		
		fIdentifierFound.setContext("C");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualPurged);
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e.getMessage().contains("don't match"));
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextAforIndividualPurged() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualPurged = null;
		
		fIdentifierFound.setContext("A");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualPurged);
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e.getMessage().contains("don't match"));
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextPforIndividualPurged() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualPurged = null;
		
		fIdentifierFound.setContext("P");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualPurged);
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextPforIndividualX() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("P");
		individualDeleted.setStatutIndividu("X");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e.getMessage().contains("don't match"));
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextCforIndividualX() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("C");
		individualDeleted.setStatutIndividu("X");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e.getMessage().contains("don't match"));
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextAforIndividualX() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("A");
		individualDeleted.setStatutIndividu("X");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextCforIndividualF() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("C");
		individualDeleted.setStatutIndividu("F");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextCforIndividualFF() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("F");
		individualDeleted.setStatutIndividu("F");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
		} catch (JrafDomainException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextCforIndividualError() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("X");
		individualDeleted.setStatutIndividu("F");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextAforIndividualF() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("A");
		individualDeleted.setStatutIndividu("F");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e.getMessage().contains("don't match"));
		}
	}
	
	@Test
	public void testForgetStatus_checkConformityContextPforIndividualF() {
		// Init test data
		ForgottenIndividualDTO fIdentifierFound = new ForgottenIndividualDTO();
		IndividuDTO individualDeleted = new IndividuDTO();
		
		fIdentifierFound.setContext("P");
		individualDeleted.setStatutIndividu("F");
		
		// Execute test
		try {
			forgetStatusHelper.checkForgetStatusConformity(fIdentifierFound, individualDeleted);
			Assert.fail();
		} catch (JrafDomainException e) {
			Assert.assertTrue(e.getMessage().contains("don't match"));
		}
	}
}
