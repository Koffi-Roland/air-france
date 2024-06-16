package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REF_NAT_RESO")
public class RefNatReso {
	
	@Id
	@Column(name="SCODE", length=1, nullable=false)
	private String code;
	
	@Column(name="SLIBELLE", length=15)
	private String label;
	
	@Column(name="SLIBELLE_EN", length=15)
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
