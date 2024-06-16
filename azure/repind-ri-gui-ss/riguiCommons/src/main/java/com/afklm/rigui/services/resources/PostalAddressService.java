package com.afklm.rigui.services.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afklm.rigui.model.individual.ModelAddress;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.builder.w000442.PostalAdrRequestBuilder;
import com.afklm.rigui.services.helper.resources.PostalAddressHelper;
import com.afklm.rigui.services.ws.CreateOrUpdateIndividualService;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.dao.adresse.Usage_mediumRepository;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.entity.adresse.Usage_medium;

@Service
public class PostalAddressService {
	
	@Autowired
	@Qualifier("postalAddressHelperRIGUI")
	private PostalAddressHelper postalAddressHelper;
	
	@Autowired
	private PostalAdrRequestBuilder postalAddressRequestBuilder;
	
	@Autowired
	private PostalAddressRepository addressRepository;
	
	@Autowired
	private Usage_mediumRepository usageMediumRepository;
	
	@Autowired
	private CreateOrUpdateIndividualService createOrUpdateIndividualService;
	
	/**
	 * This method is used to GET all the addresses of an individual.
	 * @param gin - the GIN of the individual
	 * @return a list of ModelAddress that correspond to the addresses of the individual
	 */
	@Transactional
	public List<ModelAddress> getAll(String gin) {
		
		// Fetch the list of addresses of the individual with GIN provided in parameter
		List<PostalAddress> addresses = addressRepository.findBySgin(gin);
		
		// Create the list of address models
		List<ModelAddress> modelAddresses = new ArrayList<>();
		
		// For each addresses returned by the repository...
		for (PostalAddress addr : addresses) {
			// Take the list of usages corresponding to the address
			List<Usage_medium> usages = usageMediumRepository.findBySainAdr(addr.getSain());
			// Add the address model in the list
			modelAddresses.add(postalAddressHelper.buildAddressModel(addr, usages));
		}
		
		// Remove all the addresses with status X (deleted)
		String deleteStatus = "X";
		List<ModelAddress> filteredAddresses = postalAddressHelper.removeAddressesByStatus(modelAddresses, deleteStatus);
		
		// Sort the list to have the addresses with status V (valid) first
		postalAddressHelper.sortByStatus(filteredAddresses);

		return filteredAddresses;
		
	}

	/**
	 * This method is used to update a postal address
	 */
	public boolean update(ModelAddress modelAddress, String gin) throws SystemException, ServiceException {
		
		return createOrUpdateIndividualService.callWebService(postalAddressRequestBuilder.buildUpdateRequest(gin, modelAddress));
		
	}
	
	/**
	 * This method is used to create a postal address
	 */
	public boolean create(ModelAddress address, String gin) throws SystemException, ServiceException {
		
		return createOrUpdateIndividualService.callWebService(postalAddressRequestBuilder.buildCreateRequest(gin, address));
		
	}
	
	/**
	 * This method is used to delete a postal address
	 */
	@Transactional
	public boolean delete(String gin, String resourceID) throws SystemException, ServiceException {
		
		// Fetch the address according to the resource id provided in parameter
		PostalAddress addressTmp = addressRepository.findBySain(resourceID);
		
		// Fetch the usages of the address according to the resource id provided in parameter
		List<Usage_medium> usagesTmp = usageMediumRepository.findBySainAdr(resourceID);
		
		// Build the address model
		ModelAddress modelAddress = postalAddressHelper.buildAddressModel(addressTmp, usagesTmp);
		
		return createOrUpdateIndividualService.callWebService(postalAddressRequestBuilder.buildDeleteRequest(gin, modelAddress));
		
	}

}
