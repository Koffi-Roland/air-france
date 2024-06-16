package com.afklm.rigui.services.helper.resources;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelPreference;
import com.afklm.rigui.entity.preference.Preference;

@Component
public class PreferenceHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * Convert a Preference entity into a ModelPreference object
	 * @param preference
	 * @return a ModelPreference
	 */
	public ModelPreference buildPreferenceModel(Preference preference) {
		return dozerBeanMapper.map(preference, ModelPreference.class);
	}

}
