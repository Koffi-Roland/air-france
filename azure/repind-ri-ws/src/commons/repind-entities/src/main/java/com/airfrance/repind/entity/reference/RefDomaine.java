package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REF_DOMAINE")
public class RefDomaine {
	
	@Id
	@Column(name="SCODE", length=2, nullable=false)
	private String code;
	
	@Column(name="SLIBELLE", length=25, nullable=false)
	private String label;
	
	@Column(name="SLIBELLE_EN", length=25)
	private String labelEng;

	@Column(name="SCODE_VENTE", length=2, nullable=false)
	private String codeVente;

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

	public String getCodeVente() {
		return codeVente;
	}

	public void setCodeVente(String codeVente) {
		this.codeVente = codeVente;
	}
	
}
