package com.afklm.repind.msv.handicap.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity


@Table(name="REF_HANDICAP_TYPE_CODE")
@AllArgsConstructor
@NoArgsConstructor
public class RefHandicapTypeCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="REFID_TYPE_CODE")
    private Integer refHandicapTypeCodeId;

    @Column(name="SCODE")
    private String code;

    @Column(name="STYPE")
    private String type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
