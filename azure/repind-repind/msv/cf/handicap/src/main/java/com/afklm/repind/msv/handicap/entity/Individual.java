package com.afklm.repind.msv.handicap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;


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
