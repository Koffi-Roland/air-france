package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FCT_PRO")
public class FctPro {
	
	@Id
	@Column(name="SCODE_FONCTION", length=12, nullable=false)
	private String codeFonction;
	
	@Column(name="SLIBELLE_FONCTION", length=30, nullable=false)
	private String label;
	
	@Column(name="SLIBELLE_FONCTION_EN", length=30)
	private String labelEng;

	public String getCodeFonction() {
		return codeFonction;
	}

	public void setCodeFonction(String codeFonction) {
		this.codeFonction = codeFonction;
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
