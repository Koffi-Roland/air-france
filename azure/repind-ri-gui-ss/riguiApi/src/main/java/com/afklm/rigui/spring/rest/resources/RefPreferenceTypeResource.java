package com.afklm.rigui.spring.rest.resources;

import java.io.Serializable;

public class RefPreferenceTypeResource implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String libelleFR;
	
	private String libelleEN;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelleFR() {
		return libelleFR;
	}

	public void setLibelleFR(String libelleFR) {
		this.libelleFR = libelleFR;
	}

	public String getLibelleEN() {
		return libelleEN;
	}

	public void setLibelleEN(String libelleEN) {
		this.libelleEN = libelleEN;
	}

}
