package com.afklm.rigui.model.individual;

import java.util.Date;
import java.util.Set;

public class ModelFonction {
	
	private Integer cle;
    private String fonction;
    private Date dateDebutValidite;
    private Date dateFinValidite;
    private Date dateCreation;
    private String signatureCreation;
    private Date dateModification;
    private String signatureModification;
    private Integer version;
    private Set<ModelEmail> emails;
    private ModelMember membre;
    private Set<ModelAddress> postalAddresses;
    private Set<ModelTelecom> telecoms;
    
	public Integer getCle() {
		return cle;
	}
	public void setCle(Integer cle) {
		this.cle = cle;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public Date getDateDebutValidite() {
		return dateDebutValidite;
	}
	public void setDateDebutValidite(Date dateDebutValidite) {
		this.dateDebutValidite = dateDebutValidite;
	}
	public Date getDateFinValidite() {
		return dateFinValidite;
	}
	public void setDateFinValidite(Date dateFinValidite) {
		this.dateFinValidite = dateFinValidite;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getSignatureCreation() {
		return signatureCreation;
	}
	public void setSignatureCreation(String signatureCreation) {
		this.signatureCreation = signatureCreation;
	}
	public Date getDateModification() {
		return dateModification;
	}
	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}
	public String getSignatureModification() {
		return signatureModification;
	}
	public void setSignatureModification(String signatureModification) {
		this.signatureModification = signatureModification;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Set<ModelEmail> getEmails() {
		return emails;
	}
	public void setEmails(Set<ModelEmail> emails) {
		this.emails = emails;
	}
	public ModelMember getMembre() {
		return membre;
	}
	public void setMembre(ModelMember membre) {
		this.membre = membre;
	}
	public Set<ModelAddress> getPostalAddresses() {
		return postalAddresses;
	}
	public void setPostalAddresses(Set<ModelAddress> postalAddresses) {
		this.postalAddresses = postalAddresses;
	}
	public Set<ModelTelecom> getTelecoms() {
		return telecoms;
	}
	public void setTelecoms(Set<ModelTelecom> telecoms) {
		this.telecoms = telecoms;
	}

}
