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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.model.individual.ModelAddress;
import com.afklm.rigui.model.individual.ModelSignature;
import com.afklm.rigui.services.helper.resources.PostalAddressHelper;
import com.afklm.rigui.spring.TestConfiguration;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class PostalAddressHelperTest {

	private static final String DELETED_STATUS = "X";
	private static final String VALID_STATUS = "V";
	private static final String INVALID_STATUS = "I";

	private final List<ModelAddress> addresses = new ArrayList<>();

	@Autowired
	@Qualifier("postalAddressHelperRIGUI")
	private PostalAddressHelper postalAddressHelper;

	@BeforeEach
	public void init() {

		addAddresses(addresses, DELETED_STATUS, 3);
		addAddresses(addresses, VALID_STATUS, 2);
		addAddresses(addresses, INVALID_STATUS, 1);

	}

	@Test
	void test_filterByStatus() {

		List<ModelAddress> filteredList = postalAddressHelper.removeAddressesByStatus(addresses, DELETED_STATUS);

		// Verify that the number of deleted addresses is 0
		Assertions.assertEquals(0, countAddressesByStatus(filteredList, DELETED_STATUS));

		// Verify that for the rest of the addresses (those without the deleted status) they are in the filtered list
		Assertions.assertEquals(2, countAddressesByStatus(filteredList, VALID_STATUS));
		Assertions.assertEquals(1, countAddressesByStatus(filteredList, INVALID_STATUS));

	}

	@Test
	void test_sortByStatus() {

		postalAddressHelper.sortByStatus(addresses);

		// Verify that the first address has the most recent modification date (i.e. more recent than the second)
		Date modificationDateAddr1 = addresses.get(0).getSignature().getModificationDate();
		Date modificationDateAddr2 = addresses.get(1).getSignature().getModificationDate();
		Assertions.assertTrue(modificationDateAddr1.before(modificationDateAddr2));

	}

	/**
	 * Fill a list with model address, the number of models is specified in parameter and also the status.
	 * @param addresses
	 * @param status
	 * @param count
	 */
	private void addAddresses(List<ModelAddress> addresses, String status, int count) {

		do {
			ModelAddress modelEmail = new ModelAddress();
			modelEmail.setStatus(status);
			ModelSignature signature = new ModelSignature();
			// Set the modification date to the current date
			signature.setModificationDate(new Date());
			modelEmail.setSignature(signature );
			addresses.add(modelEmail);
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
	 * @param addresses
	 * @param status
	 * @return
	 */
	private int countAddressesByStatus(List<ModelAddress> addresses, String status) {

		int count = 0;

		for (ModelAddress modelEmail : addresses) {
			if (status.equals(modelEmail.getStatus())) {
				count++;
			}
		}

		return count;

	}

}
