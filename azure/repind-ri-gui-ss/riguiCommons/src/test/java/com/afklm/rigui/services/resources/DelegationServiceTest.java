package com.afklm.rigui.services.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.model.individual.ModelDelegationData;
import com.afklm.rigui.model.individual.requests.ModelDelegationRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.DelegationService;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class DelegationServiceTest {

	private static final String TESTED_GIN = "400442947316";
	private static final String TESTED_GIN_CHIEF = "400442943772";

	@Autowired
	private DelegationService delegationService;

	@AfterEach
	@BeforeEach
	public void deleteUselessDelegation() throws ServiceException, SystemException {
		List<Integer> uselessDelegations =
						delegationService.getAll(TESTED_GIN).stream()
										.map(ModelDelegationData::getDelegationId)
										.filter(id -> id != 45 && id != 46)
										.collect(Collectors.toList());
		for(Integer delegation : uselessDelegations) {
			delegationService.delete(TESTED_GIN, delegation.toString());
		}

	}

	@Test
	void test_getAll() {

		// Arrange
		int expectedDelegationsCount = 1;

		List<ModelDelegationData> delegations = null;

		try {
			// Get all the delegations for the tested GIN
			delegations = delegationService.getAll(TESTED_GIN);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Assertions
		assert delegations != null;
		Assertions.assertEquals(expectedDelegationsCount, delegations.size());

	}

	@Disabled("ENV-WS-W000442-V08 in dev plateform not available")
	@Test
	void test_update() throws ServiceException, SystemException {
		// create request
		ModelDelegationRequest request = new ModelDelegationRequest();
		request.setGin(TESTED_GIN_CHIEF);
		request.setStatus("I");
		request.setType("TM");
		request.setDelegate(TESTED_GIN);
		request.setDelegator(TESTED_GIN_CHIEF);

		boolean success = delegationService.update(request, TESTED_GIN_CHIEF);
		Assertions.assertTrue(success);

		// update status "I" to "C"
		request.setStatus("C");
		success = delegationService.update(request, TESTED_GIN_CHIEF);
		Assertions.assertTrue(success);

		// check new value is updated
		List<ModelDelegationData> delegations = delegationService.getAll(TESTED_GIN);
		Assertions.assertEquals(3, delegations.size());

		Optional<ModelDelegationData> found = delegations.stream().filter(d -> d.getStatus().equals("C")).findAny();
		Assertions.assertTrue(found.isPresent());
	}

}
