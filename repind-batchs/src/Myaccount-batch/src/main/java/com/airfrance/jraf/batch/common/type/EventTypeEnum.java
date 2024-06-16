package com.airfrance.jraf.batch.common.type;

/*
 * A EventTypeEnum is composed of a name and a code 
 * The code is an abbreviation of the name
 */
public enum EventTypeEnum {

	PASSWORD_CHANGE("PC"),
	PASSWORD_RESET("PR"),
	SECRET_QUESTION_UPDATE("SQU"),
	SECRET_ANSWER_UPDATE("SAU"),
	SECRET_QUESTION_AND_ANSWER_UPDATE("SQAU"),
	DISABLED_ACCOUNT("DA"),
	ACCOUNT_LOCKED("AL");
	
	private String type;

	EventTypeEnum(String type) {
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
	
}
