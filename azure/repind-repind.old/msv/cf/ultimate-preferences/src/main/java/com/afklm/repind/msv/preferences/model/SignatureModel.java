package com.afklm.repind.msv.preferences.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignatureModel {

	private String modificationSignature;
	private String modificationSite;
	private Date modificationDate;
	private String creationSignature;
	private String creationSite;
	private Date creationDate;
    
	public String getModificationSignature() {
		return modificationSignature;
	}
	public void setModificationSignature(String modificationSignature) {
		this.modificationSignature = modificationSignature;
	}
	public String getModificationSite() {
		return modificationSite;
	}
	public void setModificationSite(String modificationSite) {
		this.modificationSite = modificationSite;
	}
	public Date getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	public String getCreationSignature() {
		return creationSignature;
	}
	public void setCreationSignature(String creationSignature) {
		this.creationSignature = creationSignature;
	}
	public String getCreationSite() {
		return creationSite;
	}
	public void setCreationSite(String creationSite) {
		this.creationSite = creationSite;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
