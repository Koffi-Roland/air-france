package com.airfrance.batch.unsubscribecomprefkl.enums;

import lombok.Getter;

@Getter
public enum InputFieldEnum {
	ACTION_INDEX("actionIndex"),
	DOMAIN_COMPREF_INDEX("domainComprefIndex"),
	COMGROUPTYPE_COMPREF_INDEX("comGroupTypeComprefIndex"),
	COMTYPE_COMPREF_INDEX("comTypeComprefIndex"),
	GIN_INDEX("ginIndex"),
	MARKET_COMPREF_INDEX("marketComprefIndex"),
	LANGUAGE_COMPREF_INDEX("languageComprefIndex"),
	CAUSE_INDEX("causeIndex");
	
	private final String value;

	InputFieldEnum(String value) {
        this.value = value;
    }

}
