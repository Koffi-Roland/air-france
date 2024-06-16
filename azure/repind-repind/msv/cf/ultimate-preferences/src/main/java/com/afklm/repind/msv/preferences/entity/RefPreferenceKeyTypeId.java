package com.afklm.repind.msv.preferences.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Embeddable
public class RefPreferenceKeyTypeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="SKEY")
    private String key;

    @Column(name="STYPE")
    private String type;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
