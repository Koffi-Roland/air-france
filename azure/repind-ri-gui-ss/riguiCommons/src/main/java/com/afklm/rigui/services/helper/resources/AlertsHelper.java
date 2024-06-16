package com.afklm.rigui.services.helper.resources;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelAlert;
import com.afklm.rigui.entity.individu.Alert;

@Component
public class AlertsHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * This method convert an Alert object to a ModelAlert object.
	 * @param alert
	 * @return
	 */
	public ModelAlert buildAlertModel(Alert alert) {
		return this.dozerBeanMapper.map(alert, ModelAlert.class);
	}

}
