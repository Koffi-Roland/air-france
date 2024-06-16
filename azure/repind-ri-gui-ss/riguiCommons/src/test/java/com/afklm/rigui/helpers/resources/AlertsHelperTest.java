package com.afklm.rigui.helpers.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.afklm.rigui.model.individual.ModelAlert;
import com.afklm.rigui.services.helper.resources.AlertsHelper;
import com.afklm.rigui.spring.TestConfiguration;
import com.afklm.rigui.entity.individu.Alert;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
class AlertsHelperTest {
	
	private static final int ALERT_ENTITY_ID = 17459;
	private static final String ALERT_ENTITY_GIN = "900027775790";
	private static final String ALERT_ENTITY_TYPE = "P";
	private static final String ALERT_ENTITY_OPTIN = "N";
	
	private Alert alertEntity = null;

	@Autowired
	private AlertsHelper alertsHelper;
	
	@BeforeEach
	public void init() {
		
		alertEntity = new Alert();
		alertEntity.setAlertId(ALERT_ENTITY_ID);
		alertEntity.setSgin(ALERT_ENTITY_GIN);
		alertEntity.setType(ALERT_ENTITY_TYPE);
		alertEntity.setOptIn(ALERT_ENTITY_OPTIN);
		
	}
	
	@Test
	void test_buildAlertModel() {

		ModelAlert alertModel = alertsHelper.buildAlertModel(alertEntity);

		Assertions.assertEquals(alertEntity.getAlertId(), alertModel.getAlertId());
		Assertions.assertEquals(alertEntity.getSgin(), alertModel.getSgin());
		Assertions.assertEquals(alertEntity.getType(), alertModel.getType());
		Assertions.assertEquals(alertEntity.getOptIn(), alertModel.getOptIn());
		
	}

}
