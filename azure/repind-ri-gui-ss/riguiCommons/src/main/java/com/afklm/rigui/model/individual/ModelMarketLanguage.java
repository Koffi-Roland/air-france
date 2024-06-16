package com.afklm.rigui.model.individual;

import java.util.Date;

public class ModelMarketLanguage {
	
	private Integer marketLanguageId;
    private String market;
    private String language;
    private String optIn;
    private String media1;
    private String media2;
    private String media3;
    private String media4;
    private String media5;
    private Date dateOfConsent;
    private ModelSignature signature;
    /*private Date creationDate;
    private String creationSignature;
    private String creationSite;
    private Date modificationDate;
    private String modificationSignature;
    private String modificationSite;*/
    
	public Integer getMarketLanguageId() {
		return marketLanguageId;
	}
	public void setMarketLanguageId(Integer marketLanguageId) {
		this.marketLanguageId = marketLanguageId;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getOptIn() {
		return optIn;
	}
	public void setOptIn(String optIn) {
		this.optIn = optIn;
	}
	public String getMedia1() {
		return media1;
	}
	public void setMedia1(String media1) {
		this.media1 = media1;
	}
	public String getMedia2() {
		return media2;
	}
	public void setMedia2(String media2) {
		this.media2 = media2;
	}
	public String getMedia3() {
		return media3;
	}
	public void setMedia3(String media3) {
		this.media3 = media3;
	}
	public String getMedia4() {
		return media4;
	}
	public void setMedia4(String media4) {
		this.media4 = media4;
	}
	public String getMedia5() {
		return media5;
	}
	public void setMedia5(String media5) {
		this.media5 = media5;
	}
	public Date getDateOfConsent() {
		return dateOfConsent;
	}
	public void setDateOfConsent(Date dateOfConsent) {
		this.dateOfConsent = dateOfConsent;
	}
	/*public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	public Date getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
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
	}*/
	public ModelSignature getSignature() {
		return signature;
	}
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}

    
    
}
