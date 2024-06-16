package com.afklm.rigui.services.resources;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.model.individual.ModelExternalIdentifier;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.ExternalIdService;
import com.afklm.rigui.spring.TestConfiguration;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class ExternalIdServiceTest {

	private static final String TESTED_GIN = "400351565013";

	@Autowired
	private ExternalIdService externalIdService;

	@Test
	void test_getAll() {

		// Arrange
		int expectedExternalIdCount = 25;

		// Act
		List<ModelExternalIdentifier> modelExternalIdentifiers = null;
		try {
			modelExternalIdentifiers = externalIdService.getAll(TESTED_GIN);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Assert
		assert modelExternalIdentifiers != null;
		Assertions.assertEquals(expectedExternalIdCount, modelExternalIdentifiers.size());

	}

}
