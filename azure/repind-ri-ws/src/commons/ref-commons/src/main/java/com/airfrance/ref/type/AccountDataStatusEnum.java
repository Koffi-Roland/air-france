package com.airfrance.ref.type;

public enum AccountDataStatusEnum {
	ACCOUNT_DELETED("D"),
	ACCOUNT_UPGRADE("U"),
	ACCOUNT_EXPIRED("E"),
	ACCOUNT_VALID("V"),
	ACCOUNT_CLOSED("C"),
	ACCOUNT_NEW("N"),
	ACCOUNT_INCOMPLETE("I");

	private final String code;

	public String code() {
		return code;
	}

	private AccountDataStatusEnum(String code) {
		this.code = code;
	}

}
