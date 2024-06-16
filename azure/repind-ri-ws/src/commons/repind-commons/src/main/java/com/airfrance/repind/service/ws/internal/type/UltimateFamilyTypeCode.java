package com.airfrance.repind.service.ws.internal.type;

public enum UltimateFamilyTypeCode {
	ULTIMATE_FAMILY("UF");
	
	private String _code;
	UltimateFamilyTypeCode(String code){
		_code = code;
	}
	
	public String getUltimateFamilyCode(){
		return _code;
	}
	
	public static boolean IsUltimateFamilyType(final String delegationType) {
		for (final UltimateFamilyTypeCode t : UltimateFamilyTypeCode.values()) {
			if (t.getUltimateFamilyCode().equals(delegationType)) {
				return true;
			}
		}
		return false;
	}
}
