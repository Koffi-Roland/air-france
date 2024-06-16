package com.afklm.rigui.services.resources;

import com.afklm.rigui.model.individual.ModelRoleUCCR;
import com.afklm.rigui.services.resources.UccrRoleService;
import com.afklm.rigui.spring.TestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class UccrRoleServiceTest {

	private static final String TESTED_GIN = "400214063836";

	@Autowired
	private UccrRoleService uccrRoleService;

	@Test
	void test_getAll() {

		// Arrange
		int expectedUccrRolesCount = 2;

		// Act
		List<ModelRoleUCCR> roles = uccrRoleService.getAll(TESTED_GIN);

		// Asset
		Assertions.assertEquals(expectedUccrRolesCount, roles.size());

	}

}
