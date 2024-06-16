package com.afklm.rigui.model.individual;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelExternalIdentifier {
	
	private int identifierId;
	private String gin;
	private String identifier;
	private String type;
	private Date lastSeenDate;
	private ModelSignature signature;
	private Set<ModelExternalIdentifierData> externalIdentifierData;
	
	public int getIdentifierId() {
		return identifierId;
	}
	public void setIdentifierId(int identifierId) {
		this.identifierId = identifierId;
	}
	public String getGin() {
		return gin;
	}
	public void setGin(String gin) {
		this.gin = gin;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getLastSeenDate() {
		return lastSeenDate;
	}
	public void setLastSeenDate(Date lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
	}
	public Set<ModelExternalIdentifierData> getExternalIdentifierDataList() {
		return externalIdentifierData;
	}
	public void setExternalIdentifierData(Set<ModelExternalIdentifierData> externalIdentifierDataList) {
		this.externalIdentifierData = externalIdentifierDataList;
	}
	public ModelSignature getSignature() {
		return signature;
	}
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	
	

}
