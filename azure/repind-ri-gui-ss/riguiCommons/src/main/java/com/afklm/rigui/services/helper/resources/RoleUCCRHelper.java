package com.afklm.rigui.services.helper.resources;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelRoleUCCR;
import com.afklm.rigui.entity.role.RoleUCCR;

@Component
public class RoleUCCRHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * Convert a role UCCR entity into a model.
	 * @param roleUCCR
	 * @return a ModelRoleUCCR
	 */
	public ModelRoleUCCR buildRoleUCCRModel(RoleUCCR roleUCCR) {
		return dozerBeanMapper.map(roleUCCR, ModelRoleUCCR.class);
	}

}
