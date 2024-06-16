package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REF_SS_DOMAINE")
public class RefSousDomaine {
	
	@Id
	@Column(name="ICLE", length=10, nullable=false)
	private int icle;
	
	@Column(name="SCODE", length=2, nullable=false)
	private String code;
	
	@Column(name="SLIBELLE", length=25, nullable=false)
	private String label;
	
	@Column(name="SLIBELLE_EN", length=25)
	private String labelEng;

	@Column(name="SCODE_DOMAINE", length=2, nullable=false)
	private String codeDomaine;

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

	public String getCodeDomaine() {
		return codeDomaine;
	}

	public void setCodeDomaine(String codeDomaine) {
		this.codeDomaine = codeDomaine;
	}

	public int getIcle() {
		return icle;
	}

	public void setIcle(int icle) {
		this.icle = icle;
	}
	
}