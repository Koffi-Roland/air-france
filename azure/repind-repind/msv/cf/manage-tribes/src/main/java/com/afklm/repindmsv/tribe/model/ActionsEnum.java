package com.afklm.repindmsv.tribe.model;


public enum ActionsEnum {
    ADD("ADD"),
    DELETE("DELETE");

    private String name;
    
	ActionsEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

}