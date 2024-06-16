package com.afklm.rigui.services.helper.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelAddress;
import com.afklm.rigui.model.individual.ModelUsageMedium;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.entity.adresse.Usage_medium;

@Component("postalAddressHelperRIGUI")
public class PostalAddressHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	private static final String VALID_STATUS = "V";
	
	/**
	 * Remove the addresses that doesnt have the status in parameter.
	 * @param modelAddresses
	 * @param status
	 * @return a list of ModelAddress
	 */
	public List<ModelAddress> removeAddressesByStatus(List<ModelAddress> modelAddresses, String status) {
		
		List<ModelAddress> filteredList = new ArrayList<>();
		
		// Loop through all the model address
		for (ModelAddress modelAddress : modelAddresses) {
			// If the status is different than the one in parameter
			if (!status.equals(modelAddress.getStatus())) {
				// Add the model to the filtered list
				filteredList.add(modelAddress);
			}
		}
		return filteredList;
		
	}
	
	/**
	 * Sort a list of ModelAddress by their status.
	 * @param modelAddresses
	 */
	public void sortByStatus(List<ModelAddress> modelAddresses) {
		Collections.sort(modelAddresses, getStatusComparator());
	}
	
	/**
	 * Convert a PostalAddressTemp object to a ModelAddress object.
	 * @param addrTmp
	 * @param usagesTmp
	 * @return a ModelAddress object
	 */
	public ModelAddress buildAddressModel(PostalAddress addrTmp, List<Usage_medium> usagesTmp) {
		
		// Convert the PostalAddressTemp object into a ModelAddress object
		ModelAddress modelAddress = dozerBeanMapper.map(addrTmp, ModelAddress.class);
		
		Set<ModelUsageMedium> usages = new HashSet<>();
		for (Usage_medium usage : usagesTmp) {
			// Convert the Usage_mediumTemp object to ModelUsageMedium
			usages.add(dozerBeanMapper.map(usage, ModelUsageMedium.class));
		}
		modelAddress.setUsages(usages);
		
		return modelAddress;
		
	}
	
	/**
	 * Get the comparator for the addresses status (valid status first).
	 * @return a Comparator for ModelAddress
	 */
	private Comparator<ModelAddress> getStatusComparator() {
		
		Comparator<ModelAddress> comparator = new Comparator<ModelAddress>() {

			@Override
			public int compare(ModelAddress addr1, ModelAddress addr2) {
				if (addr1.getStatus().equals(VALID_STATUS) && !addr2.getStatus().equals(VALID_STATUS)) {
					return -1;
				} else if (addr1.getStatus().equals(VALID_STATUS) && addr2.getStatus().equals(VALID_STATUS)) {
					return 0;
				} else {
					return 1;
				}
			}
		};
		
		return comparator;
		
	}

}
