package com.airfrance.ref.type;

public enum ContractTypeEnum {
	BLUEBIZ_CONTRACT("BB"),
	BDC_CONTRACT("BD"),
	BACK_OFFICE_CONTRACT("CB"),
	FINANCIAL_CONTRACT("CC"),
	FIRM_CONTRACT("CF"),
	INCENTIVE("IC"),
	MASTER_CONTROL("MC"),
	FIDELIO_CONTRACT("RC"),
	SEQUOIA_CONTRACT("SQ"),
	DISTRIBUTION_CONTRACT("VD");
	
	private final String code;

	public String code() {
		return code;
	}

	private ContractTypeEnum(String code) {
		this.code = code;
	}

}
