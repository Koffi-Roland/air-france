package com.airfrance.repind.entity.individu;

public enum IndividuStatut {
	ACTIVE("V"),
	CANCELLED("X"),
	DEAD("D"),
	FUSIONNE("T"),
	TEMPORARY("P"),
	GDPR("F");

	private String _status;

	@Override
	public String toString() {
		return _status;
	}
	IndividuStatut(String status) {
		_status = status;
	}

}
