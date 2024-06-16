package com.afklm.repindmsv.tribe.model;


public enum RolesEnum {
    MANAGER("MANAGER"),
	DELEGATOR("DELEGATOR");

    private String name;
    
	RolesEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

}