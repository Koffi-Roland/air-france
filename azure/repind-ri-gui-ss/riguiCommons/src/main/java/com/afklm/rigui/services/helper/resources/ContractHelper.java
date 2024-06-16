package com.afklm.rigui.services.helper.resources;

import com.afklm.rigui.entity.role.BusinessRole;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelContract;
import com.afklm.rigui.entity.role.RoleContrats;

@Component
public class ContractHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	/**
	 * Convert a RoleContrats object into a ModelContract object
	 * @param contract
	 * @return a ModelContract
	 */
	public ModelContract buildContractModel(RoleContrats contract) {
		return dozerBeanMapper.map(contract, ModelContract.class);
	}
	/**
	 * Convert a BusinessRole object into a ModelContract object
	 * @param br
	 * @return a ModelContract
	 */
	public ModelContract buildBusinessRoleModel(BusinessRole br) {
		return dozerBeanMapper.map(br, ModelContract.class);
	}

}
