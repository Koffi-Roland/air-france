package com.afklm.rigui.services.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.model.individual.ModelAlert;
import com.afklm.rigui.model.individual.requests.ModelAlertRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.AlertService;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class AlertServiceTest {

	private static final String GIN_WITH_ALERTS = "400622216384";
	private static final String GIN_WITH_ALERTS_TO_UPDATE = "400424668522";

	private static final String GIN_WITHOUT_ALERTS = "400368644313";

	@Autowired
	private AlertService alertService;

	@Test
	void test_getAll_withAlerts() {

		List<ModelAlert> modelAlerts = null;

		try {
			modelAlerts = alertService.getAll(GIN_WITH_ALERTS);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Verify that the getAll function returns all the alerts of an individual according to the GIN given
		assert modelAlerts != null;
		Assertions.assertTrue(modelAlerts.size()>1);
	}

	@Test
	void test_update_withAlerts() throws ServiceException, SystemException {
		List<ModelAlert> modelAlerts;

		modelAlerts = alertService.getAll(GIN_WITH_ALERTS_TO_UPDATE);

		List<ModelAlertRequest> modelAlertRequests =
						modelAlerts.stream().map(this::mapAllWithoutRequest).collect(Collectors.toList());

			// check that all promo alert are optout
			for(ModelAlertRequest model : modelAlertRequests) {
				if(!model.getOptIn().equals("N")) {
					model.setOptIn("N");
					alertService.update(model, GIN_WITH_ALERTS_TO_UPDATE);
				}
			}

			// prepare optin request
			for(ModelAlertRequest model : modelAlertRequests) {
				model.setOptIn("Y");
				alertService.update(model, GIN_WITH_ALERTS_TO_UPDATE);
			}

			// check that all promo alert are optin
			modelAlerts = alertService.getAll(GIN_WITH_ALERTS_TO_UPDATE);
			for(ModelAlert model: modelAlerts) {
				Assertions.assertEquals("Y", model.getOptIn());
			}

	}

	private ModelAlertRequest mapAllWithoutRequest(ModelAlert alert) {
		ModelAlertRequest modelRequest = new ModelAlertRequest();
		modelRequest.setAlertId(alert.getAlertId());
		modelRequest.setType(alert.getType());
		modelRequest.setSgin(alert.getSgin());
		modelRequest.setOptIn(alert.getOptIn());
		return modelRequest;
	}

	@Test
	void test_getAll_withoutAlerts() {

		List<ModelAlert> modelAlerts = null;

		try {
			modelAlerts = alertService.getAll(GIN_WITHOUT_ALERTS);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Verify that the getAll function returns 0 alerts
		assert modelAlerts != null;
		Assertions.assertEquals(0, modelAlerts.size());

	}

}
