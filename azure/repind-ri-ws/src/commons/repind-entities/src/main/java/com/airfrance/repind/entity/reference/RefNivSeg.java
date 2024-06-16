package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REF_NIV_SEG")
public class RefNivSeg {
	
	@Id
	@Column(name = "SCODE", length = 3)
	private String code;
	
	@Column(name="SLIBELLE")
	private String label;
	
	@Column(name="SLIBELLE_EN")
	private String labelEng;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabelEng() {
		return labelEng;
	}

	public void setLabelEng(String labelEng) {
		this.labelEng = labelEng;
	}

}
