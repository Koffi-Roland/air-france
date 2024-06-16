package com.airfrance.repind.util;

public enum PrefilledContract {

	SAPHIR_NUMBER_TYPE("Saphir", "S"),
	CORPORATE_CONTRACT_REFERENCE_NUMBER_TYPE("Corporate Contract Reference", "CC"),
	CORPORATE_ACCOUNT_CODE_NUMBER_TYPE("Corporate Account code", "AC"),
	ARIES_REFERENCE_NUMBER_TYPE("ARIES Reference", "AR"),
	BLUE_BIZ_REFERENCE_NUMBER_TYPE("BlueBiz Reference", "BB"),
	VENTE_PRIVEE_REFERENCE_NUMBER_TYPE("Vente Privee", "VP");

	public String nameField;
	public String type;

	PrefilledContract(String name, String type) {
		this.nameField = name;
		this.type = type;
	}

	public static String errorMessage() {
		StringBuilder sb = new StringBuilder(
				"Only the following contract are allowed :");
		for (PrefilledContract contract : PrefilledContract.values()) {
			sb.append("\n");
			sb.append(contract.nameField);
			sb.append(" - type = ");
			sb.append(contract.type);
		}
		return sb.toString();
	}

	public static PrefilledContract getContract(String value) {
		for (PrefilledContract contract : PrefilledContract.values()) {
			if (contract.type.equals(value)) {
				return contract;
			}
		}
		return null;
	}
	
	public static Boolean isValidPrefilledContract(String type) {
		if(PrefilledContract.getContract(type) != null) {
			return true;
		}
		return false;
	}
}
