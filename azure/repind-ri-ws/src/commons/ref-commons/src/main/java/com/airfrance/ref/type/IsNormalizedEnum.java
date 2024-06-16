package com.airfrance.ref.type;

/**
 * Type Enumeratif pour le champs ISNormalized de la table {@link Telecoms}.
 * @author t251684
 *
 */
public enum IsNormalizedEnum {
	E("E"),
	Y("Y"),
	N("N");
	
	private String value;
	
	IsNormalizedEnum(String valeur) {
		this.setValue(valeur);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
