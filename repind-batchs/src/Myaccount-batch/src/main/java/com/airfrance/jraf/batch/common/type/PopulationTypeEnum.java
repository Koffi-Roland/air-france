package com.airfrance.jraf.batch.common.type;

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
