package com.afklm.repind.msv.consent.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "INDIVIDUS_ALL")
public class Individual implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "SGIN", length = 12, nullable = false, unique = true, updatable = false)
	private String gin;
	
	@Column(name="SSTATUT_INDIVIDU", length=1)
	private String status;

	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}