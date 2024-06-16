package com.airfrance.repind.service.ws.internal.type;

public enum UltimateCustomerTypeCode {
	ULTIMATE_PLATINIUM("UP"), ULTIMATE_PLATINIUM_FOR_LIFE("UL"), ULTIMATE_CLUB_2000("U2"), ULTIMATE_SKIPPER("US");
	
	private String _code;
	UltimateCustomerTypeCode(String code){
		_code = code;
	}
	
	@Deprecated
	public String getUltimateCustomerCode(){
		return _code;
	}
	
	@Deprecated	// REPIND-1804 : MemberType should not be used anymore - It is store on Com Pref ULTIMATE "U / S / UL_PS"
	public static boolean IsUltimateCustomerType(final String delegationType) {
		for (final UltimateCustomerTypeCode t : UltimateCustomerTypeCode.values()) {
			if (t.getUltimateCustomerCode().equals(delegationType)) {
				return true;
			}
		}
		return false;
	}
}
