package com.afklm.repind.v7.createorupdateindividualws.type;

public enum UltimateCustomerTypeCode {
	ULTIMATE_PLATINIUM("UP"), ULTIMATE_PLATINIUM_FOR_LIFE("UL"), ULTIMATE_CLUB_2000("U2"), ULTIMATE_SKIPPER("US");
	
	private String _code;
	UltimateCustomerTypeCode(String code){
		_code = code;
	}
	
	public String getUltimateCustomerCode(){
		return _code;
	}
	
	public static boolean IsUltimateCustomerType(final String delegationType) {
		for (final UltimateCustomerTypeCode t : UltimateCustomerTypeCode.values()) {
			if (t.getUltimateCustomerCode().equals(delegationType)) {
				return true;
			}
		}
		return false;
	}
}
