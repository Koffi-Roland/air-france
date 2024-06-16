package com.afklm.rigui.services.helper.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelEmail;
import com.afklm.rigui.entity.adresse.Email;

@Component
public class EmailHelper {
	
	@Autowired
	public DozerBeanMapper dozerBeanMapper;
	
	private static final String DELETED = "X";
	private static final String VALID = "V";
	private static final String INVALID = "I";
	
	private static final int MAX_DELETED_EMAILS = 5;
	
	/**
	 * Convert an EmailTemp object into a ModelEmail object
	 * @param emailTmp
	 * @return a ModelEmail
	 */
	public ModelEmail buildEmailModel(Email email) {
		return dozerBeanMapper.map(email, ModelEmail.class);
	}
	
	/**
	 * This method sorts the ModelEmail in the list provided in parameter.
	 * @param modelEmails
	 * @return
	 */
	public List<ModelEmail> sortEmails(List<ModelEmail> modelEmails) {
		
		// The sorted list
		List<ModelEmail> sortedEmails = new ArrayList<>();
		
		// The list of deleted emails
		List<ModelEmail> deletedEmails = new ArrayList<>();
		
		for (ModelEmail modelEmail : modelEmails) {
			if (DELETED.equals(modelEmail.getStatus())) { deletedEmails.add(modelEmail); }
			if (VALID.equals(modelEmail.getStatus()) || INVALID.equals(modelEmail.getStatus())) {
				sortedEmails.add(modelEmail);
			}
		}
		
		// Remove the oldest deleted emails and store them in the sorted list
		List<ModelEmail> filteredDeletedEmails = removeOldest(deletedEmails);
		sortedEmails.addAll(filteredDeletedEmails);
		
		// Sort the emails by their status to have the V status first
		Collections.sort(sortedEmails, getStatusComparator());
		
		return sortedEmails;
		
	}
	
	/**
	 * This method filter the list in parameter to remove the oldest emails that are deleted.
	 * @param modelEmails
	 * @return a list of ModelEmail
	 */
	private List<ModelEmail> removeOldest(List<ModelEmail> modelEmails) {
		
		List<ModelEmail> filteredList = new ArrayList<>();
		
		// Use the modification date comparator to order the emails by date
		Collections.sort(modelEmails, getModificationDateComparator());
		
		// Add in the filtered list only 5 emails if there are more 
		filteredList.addAll((modelEmails.size() > MAX_DELETED_EMAILS) ? modelEmails.subList(0, MAX_DELETED_EMAILS) : modelEmails);
		
		return filteredList;
		
	}
	
	/**
	 * Get the comparator to compare two ModelEmail with their modification dates.
	 * @return
	 */
	private Comparator<ModelEmail> getModificationDateComparator() {
		
		return new Comparator<ModelEmail>() {
			
			@Override
			public int compare(ModelEmail arg0, ModelEmail arg1) {
				return arg1.getSignature().getModificationDate().compareTo(arg0.getSignature().getModificationDate());
			}
			
		};
		
	}
	
	/**
	 * Get the comparator to compare two ModelEmail with their status (valid first)
	 * @return
	 */
	private Comparator<ModelEmail> getStatusComparator() {
		
		return new Comparator<ModelEmail>() {

			@Override
			public int compare(ModelEmail arg0, ModelEmail arg1) {
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
