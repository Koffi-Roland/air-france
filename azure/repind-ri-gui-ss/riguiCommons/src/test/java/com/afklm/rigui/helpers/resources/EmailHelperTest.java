package com.afklm.rigui.helpers.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.model.individual.ModelEmail;
import com.afklm.rigui.model.individual.ModelSignature;
import com.afklm.rigui.services.helper.resources.EmailHelper;
import com.afklm.rigui.spring.TestConfiguration;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class EmailHelperTest {

	private static final String DELETED_STATUS = "X";
	private static final String VALID_STATUS = "V";
	private static final String INVALID_STATUS = "I";
	private static final String UNKNOWN_STATUS = "U";

	private static final int MAXIMUM_NUMBER_OF_DELETED_EMAILS = 5;

	private final List<ModelEmail> emails = new ArrayList<>();

	@Autowired
	private EmailHelper emailHelper;

	@BeforeEach
	public void init() {

		addEmails(emails, DELETED_STATUS, 6);
		addEmails(emails, VALID_STATUS, 2);
		addEmails(emails, INVALID_STATUS, 1);
		addEmails(emails, UNKNOWN_STATUS, 1);

	}

	@Test
	void test_sortEmails() {

		List<ModelEmail> sortedList = emailHelper.sortEmails(emails);

		// Verify that the rule to only keep at most 5 deleted email is correct
		Assertions.assertEquals(MAXIMUM_NUMBER_OF_DELETED_EMAILS, countEmailsByStatus(sortedList, DELETED_STATUS));

		// Verify that in the list there cannot be an email that has not V, X or I status
		Assertions.assertEquals(0, countEmailsByStatus(sortedList, UNKNOWN_STATUS));
		Assertions.assertEquals(8, sortedList.size());

		// The first email should be valid
		Assertions.assertEquals(VALID_STATUS, sortedList.get(0).getStatus());
		// The second email should be valid
		Assertions.assertEquals(VALID_STATUS, sortedList.get(1).getStatus());
		// The third email should NOT be valid
		Assertions.assertNotEquals(VALID_STATUS, sortedList.get(3).getStatus());

		// The first item is the list should be more recent than the second one (modification date)
		Date modificationDateEmail1 = sortedList.get(0).getSignature().getModificationDate();
		Date modificationDateEmail2 = sortedList.get(1).getSignature().getModificationDate();
		Assertions.assertTrue(modificationDateEmail1.before(modificationDateEmail2));

	}

	/**
	 * Fill a list with model email, the number of models is specified in parameter and also the status.
	 * @param emails
	 * @param status
	 * @param count
	 */
	private void addEmails(List<ModelEmail> emails, String status, int count) {

		do {
			ModelEmail modelEmail = new ModelEmail();
			modelEmail.setStatus(status);
			ModelSignature signature = new ModelSignature();
			// Set the modification date to the current date
			signature.setModificationDate(new Date());
			modelEmail.setSignature(signature );
			emails.add(modelEmail);
			try {
				// Sleep for 1 ms before creating another email
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count--;
		} while (count != 0);

	}

	/**
	 * Count the number of email in the list that have the status given in parameter
	 * @param emails
	 * @param status
	 * @return
	 */
	private int countEmailsByStatus(List<ModelEmail> emails, String status) {

		int count = 0;

		for (ModelEmail modelEmail : emails) {
			if (status.equals(modelEmail.getStatus())) {
				count++;
			}
		}

		return count;

	}

}
