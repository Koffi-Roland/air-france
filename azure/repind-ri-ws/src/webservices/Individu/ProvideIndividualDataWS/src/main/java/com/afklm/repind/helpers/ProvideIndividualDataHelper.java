package com.afklm.repind.helpers;

import com.airfrance.ref.type.DelegationTypeEnum;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;

import java.util.Iterator;
import java.util.List;

public class ProvideIndividualDataHelper {
	
	/**
	 * This method allows to clean a list of DelegationDataDTO from a String
	 * 
	 * @param delegationsData List of delegationDataDTO to clean 
	 * @param type DelegationTypeEnum we want to delete 
	 */
	public static void deleteDelegationFromReponseByType(List<DelegationDataDTO> delegationsData, String type) {
		Iterator<DelegationDataDTO> itr = delegationsData.iterator();
		
		while(itr.hasNext()) {
			DelegationDataDTO delegationData = itr.next();
			if (delegationData.getType().equalsIgnoreCase(type)) {
				itr.remove();
			}
		}
	}
	
	
	/**
	 * This method allows to clean a list of DelegationDataDTO from a DelegationTypeEnum
	 * 
	 * @param delegationsData List of delegationDataDTO to clean 
	 * @param type DelegationTypeEnum we want to delete
	 */
	public static void deleteDelegationFromReponseByType(List<DelegationDataDTO> delegationsData, DelegationTypeEnum type) {
		deleteDelegationFromReponseByType(delegationsData, type.toString());
	}
	
	
	/**
	 * This method allows to clean a list of DelegationDataDTO from a list of DelegationTypeEnum
	 * 
	 * @param delegationsData List of delegationDataDTO to clean 
	 * @param types List of DelegationTypeEnum we want to delete 
	 */
	public static void deleteDelegationFromReponseByTypesDelegation(List<DelegationDataDTO> delegationsData, List<DelegationTypeEnum> types) {
		if (types != null) {
			for (DelegationTypeEnum type : types) {
				deleteDelegationFromReponseByType(delegationsData, type);
			}
		}
	}
	
	
	/**
	 * This method allows to clean a list of DelegationDataDTO from a list of String
	 * 
	 * @param delegationsData List of delegationDataDTO to clean 
	 * @param types List of String we want to delete 
	 */
	public static void deleteDelegationFromReponseByTypesString(List<DelegationDataDTO> delegationsData, List<String> types) {
		if (types != null) {
			for (String type : types) {
				deleteDelegationFromReponseByType(delegationsData, type);
			}
		}
	}
	
}
