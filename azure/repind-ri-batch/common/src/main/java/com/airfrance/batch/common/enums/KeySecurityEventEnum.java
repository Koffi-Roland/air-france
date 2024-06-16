package com.airfrance.batch.common.enums;

/*
 * A KeySecurityEventEnum is composed of a name and a value 
 * The value corresponds to the index in the array changeBefore or changeAfter
 */
public enum KeySecurityEventEnum {

	STATUS(0),
	IP_ADDRESS(1),
	NB_FAILURE_AUTHENTIFICATION(2),
	PASSWORD_TO_CHANGE(3),
	LAST_PWD_RESET_DATE(4),
	TEMPORARY_PWD_END_DATE(5),
	LAST_SOCIAL_NETWORK_LOGON_DATE(6),
	LAST_SOCIAL_NETWORK_USED(7),
	LAST_SOCIAL_NETWORK_ID(8),
	LAST_CONNECTION_DATE(9),
	ACCOUNT_UPGRADE_DATE(10),
	ACCOUNT_LOCKED_DATE(11),
	ACCOUNT_DELETION_DATE(12),
	LAST_SECRET_ANSW_MODIFICATION_DATE(13),
	NB_FAILURE_SECRET_QUESTION_ANS(14),
	PASSWORD(15),
	TEMPORARY_PASSWORD(16),
	SECRET_QUESTION_ANSWER(17),
	SECRET_QUESTION_OLD(18),
	SECRET_QUESTION(19),
	SECRET_QUESTION_ANSWER_UPPER(20),
	EMAIL_IDENTIFIER(21),
	DATE_CREATION(22),
	SITE_CREATION(23),
	SIGNATURE_CREATION(24),
	DATE_MODIFICATION(25),
	SITE_MODIFICATION(26),
	SIGNATURE_MODIFICATION(27);
	
	private Integer key;

	KeySecurityEventEnum(Integer key) {
		this.key = key;
	}
	
	public Integer getKey() {
		return key;
	}
	
}
