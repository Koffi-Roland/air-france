package com.afklm.rigui.model.individual;

import java.util.Date;

public class ModelMember {

	private Integer key;
    private Integer version;
    private String fonction;
    private Date dateCreation;
    private String signatureCreation;
    private Date dateModification;
    private String signatureModification;
    private Date dateDebutValidite;
    private Date dateFinValidite;
    private String client;
    private String contact;
    private String contactAf;
    private String emissionHs;
    private String serviceAf;
    private ModelPersonneMorale personneMorale;
    // private ModelIndividual individu;
    // private Set<ModelFonction> fonctions;
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
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
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactAf() {
		return contactAf;
	}
	public void setContactAf(String contactAf) {
		this.contactAf = contactAf;
	}
	public String getEmissionHs() {
		return emissionHs;
	}
	public void setEmissionHs(String emissionHs) {
		this.emissionHs = emissionHs;
	}
	public String getServiceAf() {
		return serviceAf;
	}
	public void setServiceAf(String serviceAf) {
		this.serviceAf = serviceAf;
	}
	public ModelPersonneMorale getPersonneMorale() {
		return personneMorale;
	}
	public void setPersonneMorale(ModelPersonneMorale personneMorale) {
		this.personneMorale = personneMorale;
	}
}
