package com.afklm.repind.msv.preferences.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity


@Table(name="REF_PREFERENCE_DATA_KEY")
public class RefPreferenceDataKey implements Serializable {

    private static final long serialVersionUID = 9L;

    @Id
    @Column(name="SCODE")
    private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
