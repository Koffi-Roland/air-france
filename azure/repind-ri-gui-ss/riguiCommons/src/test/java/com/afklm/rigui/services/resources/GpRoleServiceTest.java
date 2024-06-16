package com.afklm.rigui.services.resources;

import java.util.Calendar;
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
import org.springframework.transaction.annotation.Transactional;

import com.afklm.rigui.model.individual.ModelRoleGP;
import com.afklm.rigui.dao.role.RoleGPRepository;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.helper.resources.RoleGPHelper;
import com.afklm.rigui.services.resources.RoleGPService;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.entity.role.RoleGP;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class GpRoleServiceTest {

	private static final String TESTED_GIN = "400518738582";
	private static final int TESTED_CLE_ROLE = 79916592;
	private static final String BEFORE_UPDATE_COUNTRY_CODE = "FR";
	private static final String AFTER_UPDATE_COUNTRY_CODE = "BE";

	@Autowired
	private RoleGPService roleGpService;

	@Autowired
	private RoleGPRepository roleGpRepository;

	@Autowired
	private RoleGPHelper helper;

	@BeforeEach
	public void init() {

		RoleGP role = roleGpRepository.findByCleRole(TESTED_CLE_ROLE);

		// Be sure to have the correct country code (for the update test case)
		if (AFTER_UPDATE_COUNTRY_CODE.equals(role.getCodePays())) {
			roleGpRepository.updateCountryCode(TESTED_CLE_ROLE, BEFORE_UPDATE_COUNTRY_CODE);
		}

	}

	@Test
	void test_getAll() {

		// Arrange
		int expectedGpRolesCount = 1;

		// Act
		List<ModelRoleGP> roles = roleGpService.getAll(TESTED_GIN);

		// Asset
		Assertions.assertEquals(expectedGpRolesCount, roles.size());

	}

	@Disabled("ENV-WS-W000442-V08 in dev plateform not available")
	@Test
	void test_update() {

		RoleGP roleBeforeUpdate = roleGpRepository.findByCleRole(TESTED_CLE_ROLE);
		Assertions.assertEquals(BEFORE_UPDATE_COUNTRY_CODE, roleBeforeUpdate.getCodePays());

		// Act
		ModelRoleGP modelRoleGP = helper.buildRoleGPModel(roleBeforeUpdate);
		modelRoleGP.setCodePays(AFTER_UPDATE_COUNTRY_CODE);
		try {
			roleGpService.update(modelRoleGP, TESTED_GIN);
		} catch (ServiceException | SystemException e) {
			e.printStackTrace();
		}

		// Asset
		RoleGP roleAfterUpdate = roleGpRepository.findByCleRole(TESTED_CLE_ROLE);
		Assertions.assertEquals(AFTER_UPDATE_COUNTRY_CODE, roleAfterUpdate.getCodePays());

	}

	@Disabled("Fix WS timeout")
	@Transactional
	@Test
	void test_delete() {

		RoleGP entity = roleGpRepository.findByCleRole(TESTED_CLE_ROLE);
		ModelRoleGP model = helper.buildRoleGPModel(entity);

		try {
			roleGpService.delete(TESTED_GIN, model.getCleRole().toString());
		} catch (ServiceException | SystemException e) {
			e.printStackTrace();
		}

		RoleGP entityAfterDelete = roleGpRepository.findByCleRole(TESTED_CLE_ROLE);
		ModelRoleGP modelAfterDelete = helper.buildRoleGPModel(entityAfterDelete);

		// The ending date of the role GP should be the same day as today
		Assertions.assertEquals(getDayOfMonth(new Date()), getDayOfMonth(modelAfterDelete.getDateFinValidite()));

	}

	/**
	 * Given a date, return the date of the day in month
	 * @param date we want to get the day
	 * @return day of month
	 */
	private static int getDayOfMonth(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.get(Calendar.DAY_OF_MONTH);
	}

}
