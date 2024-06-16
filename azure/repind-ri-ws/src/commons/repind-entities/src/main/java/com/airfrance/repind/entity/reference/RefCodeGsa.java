package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REF_CODE_GSA")
public class RefCodeGsa {
	
	@Id
	@Column(name="SCODE", length=2)
	private String code;
	
	@Column(name="SLIBELLE", length=25, nullable=false)
	private String label;
	
	@Column(name="SLIBELLE_EN", length=25)
	private String labelEng;

	@Column(name="SCODE_IATA", length=2, nullable=false)
	private String codeIata;

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

	public String getCodeIata() {
		return codeIata;
	}

	public void setCodeIata(String codeIata) {
		this.codeIata = codeIata;
	}
	
}
