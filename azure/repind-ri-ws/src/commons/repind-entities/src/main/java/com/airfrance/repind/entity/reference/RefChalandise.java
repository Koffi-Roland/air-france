package com.airfrance.repind.entity.reference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REF_CHALANDISE")
public class RefChalandise {
	
	@Id
	@Column(name="ICLE", length=10, nullable=false)
	private int icle;
	
	@Column(name="SCODE", length=3, nullable=false)
	private String code;
	
	@Column(name="SCODE_POST")
	private String codePost;
	
	@Column(name="SVILLE_DEPT")
	private String villeDept;
	
	@Column(name="SPAYS")
	private String pays;
	
	@Column(name="SLIB_AEROPORT")
	private String libAeroport;
	
	@Column(name="SCODE_DEPT")
	private String codeDept;

	public int getIcle() {
		return icle;
	}

	public void setIcle(int icle) {
		this.icle = icle;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodePost() {
		return codePost;
	}

	public void setCodePost(String codePost) {
		this.codePost = codePost;
	}

	public String getVilleDept() {
		return villeDept;
	}

	public void setVilleDept(String villeDept) {
		this.villeDept = villeDept;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getLibAeroport() {
		return libAeroport;
	}

	public void setLibAeroport(String libAeroport) {
		this.libAeroport = libAeroport;
	}

	public String getCodeDept() {
		return codeDept;
	}

	public void setCodeDept(String codeDept) {
		this.codeDept = codeDept;
	}
	
}
