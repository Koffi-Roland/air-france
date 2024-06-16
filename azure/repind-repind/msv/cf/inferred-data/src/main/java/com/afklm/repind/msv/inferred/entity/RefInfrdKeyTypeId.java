package com.afklm.repind.msv.inferred.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class RefInfrdKeyTypeId implements Serializable {

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
