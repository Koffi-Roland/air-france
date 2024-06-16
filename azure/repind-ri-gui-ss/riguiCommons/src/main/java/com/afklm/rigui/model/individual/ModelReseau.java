package com.afklm.rigui.model.individual;

import java.util.Date;
import java.util.Set;

public class ModelReseau {
	
	private String code;
    private Date dateCreation;
    private Date dateFermeture;
    private String nature;
    private String nom;
    private String pays;
    private String type;
    private Set<ModelMembreReseau> agences;
    private Set<ModelReseau> enfants;
    private ModelReseau parent;
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDateFermeture() {
		return dateFermeture;
	}
	public void setDateFermeture(Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<ModelMembreReseau> getAgences() {
		return agences;
	}
	public void setAgences(Set<ModelMembreReseau> agences) {
		this.agences = agences;
	}
	public Set<ModelReseau> getEnfants() {
		return enfants;
	}
	public void setEnfants(Set<ModelReseau> enfants) {
		this.enfants = enfants;
	}
	public ModelReseau getParent() {
		return parent;
	}
	public void setParent(ModelReseau parent) {
		this.parent = parent;
	}
    

}
