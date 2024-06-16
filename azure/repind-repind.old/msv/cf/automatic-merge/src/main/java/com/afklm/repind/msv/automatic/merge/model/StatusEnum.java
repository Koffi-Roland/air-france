package com.afklm.repind.msv.automatic.merge.model;

/**
 * Enum with the status used in the project
 */
public enum StatusEnum {
    VALIDATED("V"),
    PENDING("P"),
    REFUSED("R"),
    HISTORIZED("H"),
    DELETED("D");

    private final String name;
    
	StatusEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

}