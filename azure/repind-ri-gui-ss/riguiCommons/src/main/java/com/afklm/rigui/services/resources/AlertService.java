package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.rigui.model.individual.ModelAlert;
import com.afklm.rigui.model.individual.requests.ModelAlertRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.AlertRequestBuilder;
import com.afklm.rigui.services.helper.resources.AlertsHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.individu.AlertRepository;
import com.afklm.rigui.entity.individu.Alert;

@Service
public class AlertService {
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private AlertsHelper alertsHelper;

	@Autowired
	private CreateOrUpdateIndividualService createOrUpdateIndividualService;

	@Autowired
	private AlertRequestBuilder requestBuilder;
	
	/**
	 * This method is used to GET all the alerts of an individual according to the GIN in parameter.
	 * @param gin - the individual GIN
	 * @return a list of ModelAlert
	 * @throws ServiceException
	 */
	@Transactional
	public List<ModelAlert> getAll(String gin) throws ServiceException {
		
		List<ModelAlert> modelAlerts = new ArrayList<>();
		
		// Fetch all the alerts according to the individual GIN
		List<Alert> alerts = alertRepository.findBySgin(gin);
		
		for (Alert alert : alerts) {
			modelAlerts.add(alertsHelper.buildAlertModel(alert));
		}
		
		return modelAlerts;

	}

	/**
	 * UPDATE an alert.
	 *
	 * @param alert ModelAlertRequest
	 * @param gin String
	 * @return boolean
	 * @throws ServiceException
	 * @throws SystemException
	 * @throws com.afklm.soa.stubs.common.systemfault.v1.SystemException
	 */
	public boolean update(ModelAlertRequest alert, String gin)
					throws ServiceException, SystemException {
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildUpdateRequest(gin, alert));
	}


}
