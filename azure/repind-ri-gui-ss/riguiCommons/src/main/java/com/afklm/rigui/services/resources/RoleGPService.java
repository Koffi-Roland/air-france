package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.rigui.model.individual.ModelRoleGP;
import com.afklm.rigui.repository.IndividuAllRepository;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w002008.W002008RequestBuilder;
import com.afklm.rigui.services.helper.resources.RoleGPHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualGPService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.role.RoleGPRepository;
import com.afklm.rigui.entity.individu.IndividuLight;
import com.afklm.rigui.entity.role.RoleGP;

@Service
public class RoleGPService {
	
	@Autowired
	private CreateOrUpdateIndividualGPService createOrUpdateIndividualGPService;
	
	@Autowired
	private W002008RequestBuilder requestBuilder;
	
	@Autowired
	private RoleGPHelper roleGPHelper;
	
	@Autowired
	private IndividuAllRepository individuAllRepository;
	
	@Autowired
	private RoleGPRepository roleGPRepository;
	
	/**
	 * This method GET all the roles GP according to the GIN in parameter.
	 * @param gin
	 * @return a list of ModelRoleGP
	 */
	@Transactional
	public List<ModelRoleGP> getAll(String gin) {
		
		List<ModelRoleGP> modelRoleGPs = new ArrayList<>();
		
		// Fetch all the roles GP according to the GIN in parameter using the appropriate repository
		List<RoleGP> roleGPs = roleGPRepository.getRoleGPByGin(gin);
		
		// Loop through all the roles GP
		for (RoleGP roleGP : roleGPs) {
			// Convert the role GP entity into a model and add it to the list of models
			modelRoleGPs.add(roleGPHelper.buildRoleGPModel(roleGP));
		}
		
		return modelRoleGPs;
		
	}
	
	/**
	 * This method UPDATE a role GP.
	 */
	public boolean update(ModelRoleGP modelRoleGP, String id) throws ServiceException, SystemException {
		
		// Fetch the individual entity by using the individual repository
		IndividuLight individu = individuAllRepository.findBySgin(id).get(0);
		
		return createOrUpdateIndividualGPService.callWebService(requestBuilder.buildUpdateRequest(modelRoleGP, individu));
		
	}
	
	/**
	 * This method DELETE a role GP.
	 */
	public boolean delete(String gin, String id) throws ServiceException, SystemException {
		
		// Fetch the role GP according to its ID using the role GP repository
		RoleGP roleGP = roleGPRepository.getReferenceById(Integer.parseInt(id));
		// Convert the entity into a model
		ModelRoleGP modelRoleGP = roleGPHelper.buildRoleGPModel(roleGP);
		
		// Fetch the individual entity by using the individual repository
		IndividuLight individu = individuAllRepository.findBySgin(gin).get(0);
		
		return createOrUpdateIndividualGPService.callWebService(requestBuilder.buildDeleteRequest(modelRoleGP, individu));
		
	}

}
