package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REF_STA_JURI")
public class RefStaJuri {
	
	@Id
	@Column(name = "SCODE", length = 4)
	private String code;
	
	@Column(name="SLIBELLE")
	private String label;
	
	@Column(name="SLIBELLE_EN")
	private String labelEng;

	@Column(name = "ICODE_DUNS")
	private int codeDuns;

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

	/**
	 * @return the codeDuns
	 */
	public int getCodeDuns() {
		return codeDuns;
	}

	/**
	 * @param codeDuns
	 *            the codeDuns to set
	 */
	public void setCodeDuns(int codeDuns) {
		this.codeDuns = codeDuns;
	}

}
