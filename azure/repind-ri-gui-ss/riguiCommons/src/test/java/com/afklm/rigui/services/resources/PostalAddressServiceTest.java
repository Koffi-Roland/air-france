package com.afklm.rigui.services.resources;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.model.individual.ModelAddress;
import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.services.resources.PostalAddressService;
import com.afklm.rigui.spring.TestConfiguration;



@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class PostalAddressServiceTest {

	private static final String TESTED_GIN = "400132846244";

	@Autowired
	private PostalAddressRepository addrRepo;

	@Autowired
	private PostalAddressService service;

	@Test
	void test_getAll() {

		// Arrange
		List<PostalAddress> entities = addrRepo.findBySgin(TESTED_GIN);
		int expectedAddressesCount = entities.size();

		// Act
		List<ModelAddress> modelAddresses = service.getAll(TESTED_GIN);

		// Assert
		Assertions.assertEquals(expectedAddressesCount, modelAddresses.size());

	}

}
