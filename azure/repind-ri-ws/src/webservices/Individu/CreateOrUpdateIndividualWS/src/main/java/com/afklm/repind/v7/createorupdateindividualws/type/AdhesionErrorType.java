package com.afklm.repind.v7.createorupdateindividualws.type;

public enum AdhesionErrorType {

	ERROR_001,
	ERROR_003,
	ERROR_102,
	ERROR_103,
	ERROR_104,
	ERROR_105,
	ERROR_106,
	ERROR_120,
	ERROR_133,
	ERROR_140,
	ERROR_194,
	ERROR_209,
	ERROR_277,
	ERROR_278,
	ERROR_279,
	ERROR_280,
	ERROR_281,
	ERROR_902,
	ERROR_932,
	OTHER;
	
	public String value() {
		return name().replace("ERROR_","");
	}
	
	public static AdhesionErrorType getEnum(String error) {
		
		AdhesionErrorType enumType;
		
		try {
			enumType = valueOf("ERROR_"+error);
		} catch(IllegalArgumentException e) {
			enumType = OTHER;
		} catch(NullPointerException e) {
			enumType = OTHER;
		}
		
		return enumType;
	}
	
}
