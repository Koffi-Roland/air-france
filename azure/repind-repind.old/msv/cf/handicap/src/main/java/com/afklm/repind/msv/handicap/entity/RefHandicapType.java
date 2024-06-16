package com.afklm.repind.msv.handicap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;



@Entity


@Table(name="REF_HANDICAP_TYPE")
public class RefHandicapType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="SCODE")
    private String code;

    @Column(name="INB_MAX")
    private Integer nbMax;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getNbMax() {
		return nbMax;
	}

	public void setNbMax(Integer nbMax) {
		this.nbMax = nbMax;
	}
    
}
