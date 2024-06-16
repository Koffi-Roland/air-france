package com.afklm.repindmsv.tribe.model;


public enum StatusEnum {
    VALIDATED("V"),
    PENDING("P"),
    REFUSED("R"),
    DELETED("X");

    private String name;
    
	StatusEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

}