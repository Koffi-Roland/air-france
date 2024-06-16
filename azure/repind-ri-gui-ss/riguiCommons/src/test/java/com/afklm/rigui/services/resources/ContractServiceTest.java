package com.afklm.rigui.services.resources;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.model.individual.ModelContract;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.spring.TestConfiguration;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class ContractServiceTest {

	private static final String TESTED_GIN = "400007352160";
	private static final String ROLE_CONTRACT_TYPE = "C";

	@Autowired
	private ContractService contractService;

	@Test
	void test_getAll() {

		// Arrange
		int expectedContractsCount = 2;

		// Act
		List<ModelContract> contracts = null;
		try {
			// Get all the contracts of the individual (only role contracts)
			contracts = contractService.getAll(TESTED_GIN);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Assertions
		assert contracts != null;
		Assertions.assertEquals(expectedContractsCount, contracts.size());
		Assertions.assertTrue(hasOnlyContractWithType(contracts));

	}

	private boolean hasOnlyContractWithType(List<ModelContract> contracts) {

		boolean result = true;

		for (ModelContract modelContract : contracts) {
			if (!ContractServiceTest.ROLE_CONTRACT_TYPE.equals(modelContract.getContractType())) {
				result = false;
				break;
			}
		}

		return result;

	}

}
