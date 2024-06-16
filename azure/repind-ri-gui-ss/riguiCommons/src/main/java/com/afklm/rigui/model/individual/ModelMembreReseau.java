package com.afklm.rigui.model.individual;

import java.util.Date;


public class ModelMembreReseau {
	
	private Integer cle;
    private Date dateDebut;
    private Date dateFin;
    private ModelAgency agence;
    private ModelReseau reseau;
    
	public Integer getCle() {
		return cle;
	}
	public void setCle(Integer cle) {
		this.cle = cle;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public ModelAgency getAgence() {
		return agence;
	}
	public void setAgence(ModelAgency agence) {
		this.agence = agence;
	}
	public ModelReseau getReseau() {
		return reseau;
	}
	public void setReseau(ModelReseau reseau) {
		this.reseau = reseau;
	}

}
