package com.airfrance.ref.type;

import java.util.ArrayList;
import java.util.List;

public enum ContractDataKeyEnum {
	
	CEID(ContractConstantValues.CONTRACT_TYPE_UCCR, "corporateEnvironmentID"),
	TIERLEVEL(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "tierLevel"),
	MEMBERTYPE(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "memberType"),
	MILESBALANCE(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "milesBalance"),
	QUALIFYINGMILES(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "qualiFyingMiles"),
	QUALIFYINGSEGMENTS(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "qualifyingSegments"),
	LASTRECOGNITIONDATE(ContractConstantValues.CONTRACT_TYPE_TRAVELER, "lastRecognitionDate"),
	MATCHINGRECOGNITION(ContractConstantValues.CONTRACT_TYPE_TRAVELER, "matchingRecognition"),
	ORIGINCOMPANY(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "originCompany"),
	PRODUCTFAMILY(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "productFamily"),
	PREFERREDAIRPORT(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "preferredAirport"),
	QUALIFYINGHISTMILES(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "qualifyingHistMiles"),
	QUALIFYINGHISTSEGMENTS(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "qualifyingHistSegments"),
	BONUSPERMISSION(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "bonusPermission"),
	ADHESIONSOURCE(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "adhesionSource"),
	VERSION(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "version"),
	TREATMENTFAMILY(ContractConstantValues.CONTRACT_TYPE_ROLE_CONTRACT, "treatmentFamily");

	private String type = "";
	private String key = "";
	
	ContractDataKeyEnum(String type, String key) {
		this.type = type;
		this.key = key;
	}
	
	public static List<String> getKeysByType(String type) {
		ArrayList<String> keyList = new ArrayList<String>();
		for(ContractDataKeyEnum contractDataKeyLoop : ContractDataKeyEnum.values()) {
			if(contractDataKeyLoop.getType().equals(type)) {
				keyList.add(contractDataKeyLoop.getKey());
			}
		}
		return keyList;
	}

	public static ContractDataKeyEnum getEnumByKey(String key) {
		for (ContractDataKeyEnum contractDataKeyLoop : ContractDataKeyEnum.values()) {
			if (contractDataKeyLoop.getKey().equalsIgnoreCase(key)) {
				return contractDataKeyLoop;
			}
		}
		return null;
	}
	
	public static boolean existKeyForType(String type, String key) {
		List<String> keyList = getKeysByType(type);
		for(String keyLoop : keyList) {
			if(keyLoop.equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}
	
	public final String getKey() {
		return this.key;
	}
	
	public String getType() {
		return this.type;
	}
}