package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.entity.adresse.Email;
import com.afklm.rigui.model.individual.ModelEmail;
import com.afklm.rigui.model.individual.ModelSignature;
import com.afklm.rigui.dao.adresse.EmailRepository;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.helper.resources.EmailHelper;
import com.afklm.rigui.services.resources.EmailService;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class EmailServiceTest {

	private static final String TESTED_GIN = "400000771353";

	private static final String MOCK_EMAIL_CREATE = "testcreate@gmail.com";
	private static final String MOCK_EMAIL_AFTER_UPDATE = "hsefia@gmail.com";

	private static final String MOCK_EMAIL_UPDATE_AIN = "30190398";

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private EmailHelper emailHelper;

	@BeforeEach
	public void init() {

		List<Email> emailEntities = emailRepository.findBySgin(TESTED_GIN);
		List<ModelEmail> modelEmails = new ArrayList<>();
		for (Email emailTemp : emailEntities) {
			modelEmails.add(emailHelper.buildEmailModel(emailTemp));
		}

		// If there is the created email
		if (hasEmail(modelEmails, MOCK_EMAIL_CREATE)) {
			// Delete it
			emailRepository.deleteByEmail(TESTED_GIN, MOCK_EMAIL_CREATE);
		}

		// If there is the update email
		if (hasEmail(modelEmails, MOCK_EMAIL_AFTER_UPDATE)) {
			// Delete it
			emailRepository.deleteByEmail(TESTED_GIN, MOCK_EMAIL_AFTER_UPDATE);
		}

	}

	@Disabled("ENV-WS-W000442-V08 in dev plateform not available")
	@Test
	void test_create() {

		// Arrange
		int expectedEmailsCountBeforeCreation = 6, expectedEmailsCountAfterCreation = 7;
		List<Email> emailEntities = emailRepository.findBySgin(TESTED_GIN);

		Assertions.assertEquals(expectedEmailsCountBeforeCreation, emailEntities.size());

		// Mock a new model email
		ModelEmail modelEmail = new ModelEmail();
		modelEmail.setEmail(MOCK_EMAIL_CREATE);
		modelEmail.setAuthorizationMailing("A");
		modelEmail.setStatus("V");
		modelEmail.setType("D");

		// Act
		try {

			emailService.create(modelEmail, TESTED_GIN);
		} catch (ServiceException | SystemException e) {
			e.printStackTrace();
		}

		// Gather the new list of email with the new one
		emailEntities = emailRepository.findBySgin(TESTED_GIN);
		List<ModelEmail> modelEmails = new ArrayList<>();
		for (Email email : emailEntities) {
			modelEmails.add(emailHelper.buildEmailModel(email));
		}

		// Assert
		Assertions.assertEquals(expectedEmailsCountAfterCreation, emailEntities.size());
		Assertions.assertTrue(hasEmail(modelEmails, MOCK_EMAIL_CREATE));

	}

	@Test
	void test_getAll() {

		// Arrange
		int expectedEmailsCount = 6;

		List<ModelEmail> emails = null;

		try {
			// Call the getAll method with the GIN tested
			emails = emailService.getAll(TESTED_GIN);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Assert
		assert emails != null;
		Assertions.assertEquals(expectedEmailsCount, emails.size());

	}

	@Disabled("ENV-WS-W000442-V08 in dev plateform not available")
	@Test
	void test_update() {

		// Get the email we want to update and modify just the email address
		Email emailTemp = emailRepository.findBySain(MOCK_EMAIL_UPDATE_AIN);
		ModelEmail modelEmail = emailHelper.buildEmailModel(emailTemp);
		modelEmail.setEmail(MOCK_EMAIL_AFTER_UPDATE);

		// Call the update method
		try {
			emailService.update(modelEmail, TESTED_GIN);
		} catch (SystemException | ServiceException e) {
			e.printStackTrace();
		}

		List<Email> emailTemps = emailRepository.findBySgin(TESTED_GIN);
		List<ModelEmail> modelEmails = new ArrayList<>();
		for (Email email : emailTemps) {
			modelEmails.add(emailHelper.buildEmailModel(email));
		}

		// Assertion
		boolean res = hasEmail(modelEmails, MOCK_EMAIL_AFTER_UPDATE);
		Assertions.assertTrue(res);

	}

	@Disabled("ENV-WS-W000442-V08 in dev plateform not available")
	@Test
	void test_delete() {

		// First, create a test email with the create method (tested before)
		ModelEmail modelEmail = new ModelEmail();
		modelEmail.setEmail(MOCK_EMAIL_CREATE);
		modelEmail.setAuthorizationMailing("A");
		modelEmail.setStatus("V");
		modelEmail.setType("D");
		modelEmail.setIdentifiant("01239999990");
		modelEmail.setVersion(42);
		ModelSignature signature = new ModelSignature();
		signature.setCreationDate(new Date());
		signature.setCreationSignature("CSigTest");
		signature.setCreationSite("CSiteTest");
		signature.setModificationDate(new Date());
		signature.setModificationSignature("MSigTest");
		signature.setModificationSite("MSiteTest");
		modelEmail.setSignature(signature);

		try {
			emailService.create(modelEmail, TESTED_GIN);
		} catch (ServiceException | SystemException e) {
			e.printStackTrace();
		}

		List<ModelEmail> modelsBeforeDeletion = new ArrayList<>();
		for (Email e : emailRepository.findBySgin(TESTED_GIN)) {
			modelsBeforeDeletion.add(emailHelper.buildEmailModel(e));
		}
		int emailsDeletedCountBeforeDeletion = countEmailsByStatus(modelsBeforeDeletion);

		// Fetch the created email
		if (!emailRepository.findByEmail(MOCK_EMAIL_CREATE).stream().findFirst().isEmpty() && !emailRepository.findByEmail(MOCK_EMAIL_CREATE).stream().findFirst().isEmpty()){
			Email createdEmail = emailRepository.findByEmail(MOCK_EMAIL_CREATE).stream().findFirst().get();
			try {
				// Delete the created email
				emailService.delete(TESTED_GIN, createdEmail.getId());
			} catch (ServiceException | SystemException e) {
				e.printStackTrace();
			}
	    }
		List<ModelEmail> modelsAfterDeletion = new ArrayList<>();
		for (Email e : emailRepository.findBySgin(TESTED_GIN)) {
			modelsAfterDeletion.add(emailHelper.buildEmailModel(e));
		}
		int emailsDeletedCountAfterDeletion = countEmailsByStatus(modelsAfterDeletion);

		// Assertion - there must be one email with status X more than before applying the delete
		Assertions.assertEquals(emailsDeletedCountAfterDeletion, emailsDeletedCountBeforeDeletion + 1);

	}

	private boolean hasEmail(List<ModelEmail> modelEmails, String email) {

		boolean hasEmail = false;

		for (ModelEmail model : modelEmails) {
			if (email.equals(model.getEmail())) {
				hasEmail = true;
				break;
			}
		}

		return hasEmail;

	}

	private int countEmailsByStatus(List<ModelEmail> modelEmails) {

		int count = 0;

		for (ModelEmail modelEmail : modelEmails) {
			if ("X".equals(modelEmail.getStatus())) count++;
		}

		return count;

	}

}
