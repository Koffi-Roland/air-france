package com.afklm.rigui.services.helper.resources;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelDelegationData;
import com.afklm.rigui.entity.delegation.DelegationData;

@Component
public class DelegationHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * Convert a DelegationData object into a ModelDelegationData object
	 * @param delegatorData
	 * @return a ModelDelegationData
	 */
	public ModelDelegationData buildDelegationModel(DelegationData delegatorData) {
		return dozerBeanMapper.map(delegatorData, ModelDelegationData.class);
	}

}
