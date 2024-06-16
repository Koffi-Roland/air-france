package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.model.individual.ModelContract;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.helper.resources.ContractHelper;
import com.afklm.rigui.dao.role.BusinessRoleRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleContrats;

@Service
public class ContractService {
	
	@Autowired
	private RoleContratsRepository roleContratsRepository;
	
	@Autowired
	private BusinessRoleRepository businessRoleRepository;
	
	@Autowired
	private ContractHelper contractHelper;
	
	private static final String ROLE_CONTRACT = "C";

	private static final String ROLE_CONTRACT_STATUS_VALID = "C";
	private static final String ROLE_DOCTOR = "D";
	
	/**
	 * This method GET all the contracts according to the GIN in parameter
	 * @param gin
	 * @return a list of ModelContract
	 * @throws ServiceException
	 */
    public List<ModelContract> getAll(String gin) throws ServiceException {
    	
    	List<BusinessRole> businessRoles = businessRoleRepository.findByGinInd(gin);
    	
    	return getRoleContracts(businessRoles);
		
	}
    
    /**
     * This method takes in parameter a list of BusinessRole and return a list of ModelContract with only the contracts
     * that has the type C (for RoleContract).
     * @param businessRoles
     * @return a list of ModelContract
     */
    private List<ModelContract> getRoleContracts(List<BusinessRole> businessRoles) {
    	
    	List<ModelContract> modelContracts = new ArrayList<>();
    	
    	for (BusinessRole businessRole : businessRoles) {
    		if (ROLE_CONTRACT.equals(businessRole.getType())) {
    			RoleContrats contract = roleContratsRepository.findByNumeroContrat(businessRole.getNumeroContrat());
				if (contract != null) {
					ModelContract modelContract = contractHelper.buildContractModel(contract);
					modelContract.setContractType(businessRole.getType());
					modelContracts.add(modelContract);
				}
    		} /**
			 Add dedicated section to alson include Doctor. In the future when we will add neo4J data maybe we should change that.
    		*/
			else if (ROLE_DOCTOR.equals(businessRole.getType())) {
				ModelContract modelContract = contractHelper.buildBusinessRoleModel(businessRole);
				modelContract.setStatus(ROLE_CONTRACT_STATUS_VALID);
				modelContracts.add(modelContract);
			}
    	}
    	
    	return modelContracts;
    	
    }

}
