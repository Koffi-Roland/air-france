package com.afklm.rigui.model.individual;

import java.util.Date;

public class ModelOfficeID {
	
	private Long cle;
    private Date dateLastResa;
    private Date dateMaj;
    private String codeGDS;
    private String lettreComptoir;
    private String majManuelle;
    private String officeID;
    private String signatureMaj;
    private String siteMaj;
    private ModelAgency agence;
    
	public Long getCle() {
		return cle;
	}
	public void setCle(Long cle) {
		this.cle = cle;
	}
	public Date getDateLastResa() {
		return dateLastResa;
	}
	public void setDateLastResa(Date dateLastResa) {
		this.dateLastResa = dateLastResa;
	}
	public Date getDateMaj() {
		return dateMaj;
	}
	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}
	public String getCodeGDS() {
		return codeGDS;
	}
	public void setCodeGDS(String codeGDS) {
		this.codeGDS = codeGDS;
	}
	public String getLettreComptoir() {
		return lettreComptoir;
	}
	public void setLettreComptoir(String lettreComptoir) {
		this.lettreComptoir = lettreComptoir;
	}
	public String getMajManuelle() {
		return majManuelle;
	}
	public void setMajManuelle(String majManuelle) {
		this.majManuelle = majManuelle;
	}
	public String getOfficeID() {
		return officeID;
	}
	public void setOfficeID(String officeID) {
		this.officeID = officeID;
	}
	public String getSignatureMaj() {
		return signatureMaj;
	}
	public void setSignatureMaj(String signatureMaj) {
		this.signatureMaj = signatureMaj;
	}
	public String getSiteMaj() {
		return siteMaj;
	}
	public void setSiteMaj(String siteMaj) {
		this.siteMaj = siteMaj;
	}
	public ModelAgency getAgence() {
		return agence;
	}
	public void setAgence(ModelAgency agence) {
		this.agence = agence;
	}

}
