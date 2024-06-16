package com.airfrance.ref.type;

public enum BussinessRoleTypeEnum {
	ROLE_AFFAIRE("A"), 
	ROLE_FIRME("F"), 
	ROLE_CONTRAT("C"), 
	ROLE_GP("G"), 
	ROLE_FINANCIER("N"), 
	ROLE_INCENTIVE("I"),
	ROLE_BACKOFFICE("B"), 
	ROLE_SEQUOIA("S"),
	ROLE_UCCR("U");

	private final String code;

	public String code() {
		return code;
	}

	private BussinessRoleTypeEnum(String code) {
		this.code = code;
	}

}
