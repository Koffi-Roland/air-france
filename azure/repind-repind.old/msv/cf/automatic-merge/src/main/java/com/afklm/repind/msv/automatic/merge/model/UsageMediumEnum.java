package com.afklm.repind.msv.automatic.merge.model;

/**
 * Enum with the usage used in the project
 */
public enum UsageMediumEnum {
    ISI("ISI");

    private final String name;

	UsageMediumEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

}