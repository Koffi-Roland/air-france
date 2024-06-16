package com.airfrance.jraf.batch.common.type;

public enum BatchSicUpdateNotificationChangedBlockNameEnum {

	ADR_POST("ADR_POST"),
	CONTRACTS("CONTRACTS"),
	COMMUNICATION_PREFERENCES("COMMUNICATION_PREFERENCES"),
	INDIVIDUS("INDIVIDUS"),
	TELECOMS("TELECOMS"),
	ACCOUNT_DATA("ACCOUNT_DATA"),
	ROLE_CONTRATS("ROLE_CONTRATS"),
	EMAILS("EMAILS"),
	PREFERENCES("PREFERENCES");
	
	private String type;

	BatchSicUpdateNotificationChangedBlockNameEnum(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
}
