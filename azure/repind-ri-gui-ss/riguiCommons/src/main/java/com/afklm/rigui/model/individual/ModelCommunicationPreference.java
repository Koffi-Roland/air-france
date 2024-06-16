package com.afklm.rigui.model.individual;

import java.util.Date;
import java.util.Set;

public class ModelCommunicationPreference {

	private Integer comPrefId;
	private String accountIdentifier;
	private String domain;
	private String gin;
	private Date dateOptin;
	private Date dateOptinPartners;
	private Date dateOfEntry;
	private ModelSignature signature;
	private String optinPartners;
	private String channel;
	private String comGroupType;
	private String comType;
	private String subscribe;
	private String media1;
	private String media2;
	private String media3;
	private String media4;
	private String media5;
	private Set<ModelMarketLanguage> marketLanguage;

	public Integer getComPrefId() {
		return comPrefId;
	}

	public void setComPrefId(Integer comPrefId) {
		this.comPrefId = comPrefId;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getGin() {
		return gin;
	}

	public void setGin(String gin) {
		this.gin = gin;
	}

	public Date getDateOptin() {
		return dateOptin;
	}

	public void setDateOptin(Date dateOptin) {
		this.dateOptin = dateOptin;
	}

	public Date getDateOptinPartners() {
		return dateOptinPartners;
	}

	public void setDateOptinPartners(Date dateOptinPartners) {
		this.dateOptinPartners = dateOptinPartners;
	}

	public Date getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public String getOptinPartners() {
		return optinPartners;
	}

	public void setOptinPartners(String optinPartners) {
		this.optinPartners = optinPartners;
	}
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getComGroupType() {
		return comGroupType;
	}

	public void setComGroupType(String comGroupType) {
		this.comGroupType = comGroupType;
	}

	public String getComType() {
		return comType;
	}

	public void setComType(String comType) {
		this.comType = comType;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
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

	public ModelSignature getSignature() {
		return signature;
	}

	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	public Set<ModelMarketLanguage> getMarketLanguage() {
		return marketLanguage;
	}
	public void setMarketLanguage(Set<ModelMarketLanguage> marketLanguage) {
		this.marketLanguage = marketLanguage;
	}
}
