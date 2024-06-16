package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.rigui.model.individual.ModelDelegationData;
import com.afklm.rigui.model.individual.requests.ModelDelegationRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.DelegationRequestBuilder;
import com.afklm.rigui.services.helper.resources.DelegationHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.delegation.DelegationDataRepository;
import com.afklm.rigui.entity.delegation.DelegationData;

@Service
public class DelegationService {
	
	@Autowired
	private DelegationDataRepository delegationsDataRepository;
	
	@Autowired
	private DelegationHelper delegationHelper;

	@Autowired
	private DelegationRequestBuilder requestBuilder;

	@Autowired
	private CreateOrUpdateIndividualService createOrUpdateIndividualService;
	
	/**
	 * This method GET all the delegation according to the GIN in parameter
	 * @param gin
	 * @return a list of ModelDelegationData
	 * @throws ServiceException
	 */
	@Transactional
	public List<ModelDelegationData> getAll(String gin) throws ServiceException {
		
		List<ModelDelegationData> delegations = new ArrayList<>();
		delegations.addAll(findDelegates(gin));
		delegations.addAll(findDelegators(gin));
		
		return delegations;
		
	}
	
	/**
	 * This method returns all the delegates according to the GIN in parameter
	 * @param gin
	 * @return a list of ModelDelegationData
	 */
	private List<ModelDelegationData> findDelegates(String gin) {
		
		List<ModelDelegationData> delegateModels = new ArrayList<>();
		
		List<DelegationData> delegates = delegationsDataRepository.findDelegatesByGin(gin);
		for (DelegationData delegationData : delegates) {
			delegateModels.add(delegationHelper.buildDelegationModel(delegationData));
		}
		
		return delegateModels;
		
	}
	
	/**
	 * This method returns all the delegators according to the GIN in parameter
	 * @param gin
	 * @return a list of ModelDelegationData
	 */
	private List<ModelDelegationData> findDelegators(String gin) {
		
		List<ModelDelegationData> delegatorModels = new ArrayList<>();
		
		List<DelegationData> delegators = delegationsDataRepository.findDelegatorsByGin(gin);
		for (DelegationData delegationData : delegators) {
			delegatorModels.add(delegationHelper.buildDelegationModel(delegationData));
		}
		
		return delegatorModels;
		
	}

	/**
	 * This method will update or create (status I) a new delegation
	 * @param delegationData
	 * @param gin
	 * @return
	 * @throws ServiceException
	 * @throws SystemException
	 */
	public boolean update(ModelDelegationRequest delegationData, String gin)
					throws ServiceException, SystemException {
		return createOrUpdateIndividualService.callWebService(requestBuilder.buildUpdateRequest(gin, delegationData));
	}

	/**
	 * delete the delegation and its delegation info according to gin and delegation id
	 *
	 * @param gin of the delegates or delegator
	 * @param id of the delegation
	 * @return
	 * @throws ServiceException
	 * @throws SystemException
	 */
	@Transactional
	public boolean delete(String gin, String id) throws ServiceException, SystemException {
		List<ModelDelegationData> modelDelegationData = getAll(gin);

		// find delegates
		Optional<ModelDelegationData> delegationToDelete =
						modelDelegationData.stream().filter(delegation -> id.equals(delegation.getDelegationId().toString())).findFirst();

		if(delegationToDelete.isPresent()) {
			// It will also delete delegation data info
			delegationsDataRepository.deleteById(delegationToDelete.get().getDelegationId());
			return true;
		}
		return false;
	}

}
