package com.afklm.batch.adrInvalidBarecode.enums;

public enum IOFieldEnum {
	NUMERO_CONTRAT("numeroContrat"),
	SAIN("sain"),
	DATE_MODIFICATION("dateModification"),
    MESSAGE("message");

	private final String value;

	IOFieldEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
