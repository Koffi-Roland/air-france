package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REF_TYP_REGR")
public class RefTypeRegr {
	
	@Id
	@Column(name="SCODE", length=1, nullable=false)
	private String code;
	
	@Column(name="SLIBELLE", length=25)
	private String label;
	
	@Column(name="SLIBELLE_EN", length=25)
	private String labelEng;

	@Column(name="SAPPARTENANCE")
	private String appartenance;

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

	public String getAppartenance() {
		return appartenance;
	}

	public void setAppartenance(String appartenance) {
		this.appartenance = appartenance;
	}
	
}
