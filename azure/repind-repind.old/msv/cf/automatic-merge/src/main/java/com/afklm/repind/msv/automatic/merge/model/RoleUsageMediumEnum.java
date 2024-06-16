package com.afklm.repind.msv.automatic.merge.model;

/**
 * Enum with the role usage used in the project
 */
public enum RoleUsageMediumEnum {
    M("M"), C("C");

    private final String name;

	RoleUsageMediumEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

}