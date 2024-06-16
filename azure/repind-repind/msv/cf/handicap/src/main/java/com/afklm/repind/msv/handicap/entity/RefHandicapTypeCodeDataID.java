package com.afklm.repind.msv.handicap.entity;

import java.io.Serializable;


import jakarta.persistence.*;



@Embeddable
public class RefHandicapTypeCodeDataID implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="SKEY")
    private String key;

    @ManyToOne()
    @JoinColumn(name="REFID_TYPE_CODE", referencedColumnName = "REFID_TYPE_CODE", nullable=false, foreignKey = @ForeignKey(name = "FK_REFID_TYPE_CODE"))
    private RefHandicapTypeCode refHandicapTypeCode;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public RefHandicapTypeCode getRefHandicapTypeCode() {
		return refHandicapTypeCode;
	}

	public void setRefHandicapTypeCode(RefHandicapTypeCode refHandicapTypeCode) {
		this.refHandicapTypeCode = refHandicapTypeCode;
	}
    
}
