package com.afklm.repind.msv.inferred.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity


@Table(name="REF_INFERRED_STATUS")
public class RefInfrdStatus implements Serializable {

    private static final long serialVersionUID = 1L;

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
