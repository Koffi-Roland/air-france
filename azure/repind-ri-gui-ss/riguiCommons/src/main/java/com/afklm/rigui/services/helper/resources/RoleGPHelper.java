package com.afklm.rigui.services.helper.resources;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelRoleGP;
import com.afklm.rigui.entity.role.RoleGP;

@Component
public class RoleGPHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * Convert a role GP entity into a role GP model.
	 * @param roleGP
	 * @return
	 */
	public ModelRoleGP buildRoleGPModel(RoleGP roleGP) {
		return dozerBeanMapper.map(roleGP, ModelRoleGP.class);
	}

}
