package com.afklm.rigui.services.helper.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelTelecom;
import com.afklm.rigui.entity.adresse.Telecoms;

@Component
public class TelecomHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	private static final String VALID = "V";
	private static final String INVALID = "I";
	private static final String DELETED = "X";
	
	private static final int MAX_DELETED_TELECOMS = 5;
	
	/**
	 * Convert a telecom entity into a telecom model.
	 * @param telecomTmp
	 * @return a ModelTelecom
	 */
	public ModelTelecom buildTelecomModel(Telecoms telecom) {
		return dozerBeanMapper.map(telecom, ModelTelecom.class);
	}
	
	/**
	 * This method is used to sort a list of ModelTelecom according to specific parameters.
	 * @param telecoms
	 * @return a list of ModelTelecom
	 */
	public List<ModelTelecom> sort(List<ModelTelecom> telecoms) {
		
		// The sorted list of models
		List<ModelTelecom> sortedTelecoms = new ArrayList<>();
		
		// The list of deleted telecoms
		List<ModelTelecom> deletedTelecoms = new ArrayList<>();
		
		for (ModelTelecom modelTelecom : telecoms) {
			// The status is X (deleted), then add the telecom in the deleted telecoms list
			if (DELETED.equals(modelTelecom.getStatus())) deletedTelecoms.add(modelTelecom);
			// The status is V or I then add it to the sorted list
			if (VALID.equals(modelTelecom.getStatus()) || INVALID.equals(modelTelecom.getStatus())) {
				sortedTelecoms.add(modelTelecom);
			}
		}
		
		// Remove the oldest telecoms of the deleted list of telecoms to only keep at most 5 telecoms
		List<ModelTelecom> filteredDeletedTelecoms = removeOldest(deletedTelecoms);
		// Add all the deleted telecoms in the sorted list
		sortedTelecoms.addAll(filteredDeletedTelecoms);
		
		// Sort the list to have the telecoms with status V first using the status comparator
		Collections.sort(sortedTelecoms, getStatusComparator());
		
		return sortedTelecoms;
		
	}
	
	/**
	 * This method takes a list of telecom models and remove the oldest telecoms (date of modification).
	 * @param modelTelecoms
	 * @return a list of ModelTelecom
	 */
	private List<ModelTelecom> removeOldest(List<ModelTelecom> modelTelecoms) {
		
		List<ModelTelecom> filteredList = new ArrayList<>();
		
		// Sort the telecoms according to their modification date using the modification date comparator
		Collections.sort(modelTelecoms, getModificationDateComparator());
		
		// Add at most 5 telecoms in the filtered list (the most recent ones)
		filteredList.addAll((modelTelecoms.size() > MAX_DELETED_TELECOMS) ? modelTelecoms.subList(0, MAX_DELETED_TELECOMS) : modelTelecoms);
		
		return filteredList;
		
	}
	
	/**
	 * Get the comparator that is using modification date to compare ModelTelecom objects.
	 * @return
	 */
	private Comparator<ModelTelecom> getModificationDateComparator() {
		
		return new Comparator<ModelTelecom>() {
			
			@Override
			public int compare(ModelTelecom arg0, ModelTelecom arg1) {
				return arg1.getSignature().getModificationDate().compareTo(arg0.getSignature().getModificationDate());
			}
			
		};
		
	}
	
	/**
	 * Get the comparator that is using the status to compare ModelTelecom objects.
	 * @return
	 */
	private Comparator<ModelTelecom> getStatusComparator() {
		
		return new Comparator<ModelTelecom>() {
			
			@Override
			public int compare(ModelTelecom arg0, ModelTelecom arg1) {
				if (arg0.getStatus().equals(VALID) && !arg1.getStatus().equals(VALID)) {
					return -1;
				} else if (arg0.getStatus().equals(VALID) && arg1.getStatus().equals(VALID)) {
					return 0;
				} else {
					return 1;
				}
			}
			
		};
		
	}
	
}
