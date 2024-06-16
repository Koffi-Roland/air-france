package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.rigui.model.individual.ModelRoleUCCR;
import com.afklm.rigui.services.helper.resources.RoleUCCRHelper;
import com.afklm.rigui.dao.role.RoleUCCRRepository;
import com.afklm.rigui.entity.role.RoleUCCR;

@Service
public class UccrRoleService {
	
	@Autowired
    private RoleUCCRRepository roleUCCRRepository;
	
	@Autowired
	private RoleUCCRHelper roleUCCRHelper;
	
	/**
	 * This method GET all the roles UCCR of an individual according to the GIN in parameter.
	 * @param gin
	 * @return a list of ModelRoleUCCR
	 */
	@Transactional
	public List<ModelRoleUCCR> getAll(String gin) {
		
		List<ModelRoleUCCR> modelRoleUCCRs = new ArrayList<>();
		
		// Fetch all the roles UCCR using the appropriate repository
		List<RoleUCCR> roleUCCRs = roleUCCRRepository.findByGin(gin);
		// Loop through all the entities found
		for (RoleUCCR roleUCCR : roleUCCRs) {
			// Convert the entity into a model and add it to the list of models
			modelRoleUCCRs.add(roleUCCRHelper.buildRoleUCCRModel(roleUCCR));
		}
		
		return modelRoleUCCRs;

	}

}
