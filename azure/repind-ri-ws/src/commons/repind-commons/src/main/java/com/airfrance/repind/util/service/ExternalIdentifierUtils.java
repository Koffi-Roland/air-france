package com.airfrance.repind.util.service;

import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.util.comparators.ExternalIdentifierDataDTOComparator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ExternalIdentifierUtils {

	public static void prepareExternalIdentifierForCreation(ExternalIdentifierDTO externalIdentifierFromWS, SignatureDTO signatureFromAPP) {
		
		// set mandatory values for identifier creation
		externalIdentifierFromWS.prepareForCreation(signatureFromAPP);
		
		// get external identifier data from input
		List<ExternalIdentifierDataDTO> externalIdentifierDataListFromWS = externalIdentifierFromWS.getExternalIdentifierDataList();
		
		// nothing to do
		if(externalIdentifierDataListFromWS==null || externalIdentifierDataListFromWS.isEmpty()) {
			return;
		}
		
		// set mandatory values for external identifier data creation
		for (ExternalIdentifierDataDTO externalIdentifierDataFromWS : externalIdentifierDataListFromWS) {	
			externalIdentifierDataFromWS.prepareForCreation(signatureFromAPP);
		}
		
	}
	
	public static void prepareExternalIdentifierForUpdate(ExternalIdentifierDTO externalIdentifierFromWS, ExternalIdentifierDTO externalIdentifierFromDB, SignatureDTO signatureFromAPP) {
		
		// set mandatory values for update
		externalIdentifierFromDB.prepareForUpdate(signatureFromAPP);
		
		// get external identifier data from input
		List<ExternalIdentifierDataDTO> externalIdentifierDataListFromWS = externalIdentifierFromWS.getExternalIdentifierDataList();
		
		// set external identifier data to external identifier from DB
		externalIdentifierFromDB.setExternalIdentifierDataList(externalIdentifierDataListFromWS);
		
		// nothing to do
		if(externalIdentifierDataListFromWS==null || externalIdentifierDataListFromWS.isEmpty()) {
			return;
		}
		
		// set mandatory values for external identifier data creation
		for (ExternalIdentifierDataDTO externalIdentifierDataFromWS : externalIdentifierDataListFromWS) {	
			externalIdentifierDataFromWS.prepareForCreation(signatureFromAPP);
		}
		
	}
	
	public static boolean hasDuplicates(List<ExternalIdentifierDataDTO> list) {
	
		boolean duplicates = false;
		
		ExternalIdentifierDataDTOComparator comparator = new ExternalIdentifierDataDTOComparator();
		Set<ExternalIdentifierDataDTO> set = new TreeSet<ExternalIdentifierDataDTO>(comparator);
		set.addAll(list);
		
		if(set.size()<list.size()) {
			duplicates = true; 
		}
		
		return duplicates;
		
	}
	
}
