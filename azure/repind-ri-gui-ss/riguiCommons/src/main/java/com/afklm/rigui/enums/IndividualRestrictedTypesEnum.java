package com.afklm.rigui.enums;

public enum IndividualRestrictedTypesEnum {
	
	TRAVELER("T"),
	WHITE_WINGER("W"),
	CALLER("C");
	
	private String key = "";

	IndividualRestrictedTypesEnum(String key){
		this.key = key;
	}
	
	
	public static Boolean isIndividualRestrictedType(String individualType) {
	    for (IndividualRestrictedTypesEnum type : IndividualRestrictedTypesEnum.values()) {
		      if (type.key.equalsIgnoreCase(individualType)) {
		        return true;
		      }
	    }
	    return false;
	}
}
