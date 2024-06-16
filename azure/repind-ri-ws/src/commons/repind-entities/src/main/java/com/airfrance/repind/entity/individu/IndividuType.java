package com.airfrance.repind.entity.individu;

public enum IndividuType {
	PROSPECT("W"), INDIVIDU("I"), TRAVELER("T");

	private String _type;

	IndividuType(String type) {
		_type = type;
	}

	@Override
	public String toString() {
		return _type;
	}
}
