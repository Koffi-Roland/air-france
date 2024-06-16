package com.airfrance.batch.common.enums;

public enum PopulationTypeEnum {

	INDIVIDU("I"),
	WHITE_WINGERS("W"),
	TRAVELER("T"),
	SOCIAL_ONLY("S");
	
	private String type;

	PopulationTypeEnum(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
	
}
