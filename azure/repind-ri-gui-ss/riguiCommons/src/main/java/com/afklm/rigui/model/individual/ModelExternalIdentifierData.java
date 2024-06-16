package com.afklm.rigui.model.individual;

public class ModelExternalIdentifierData {
	
	private int identifierDataId;
	private int identifierId;
	private String key;
	private String value;
	private ModelSignature signature;
	
	public int getIdentifierDataId() {
		return identifierDataId;
	}
	public void setIdentifierDataId(int identifierDataId) {
		this.identifierDataId = identifierDataId;
	}
	public int getIdentifierId() {
		return identifierId;
	}
	public void setIdentifierId(int identifierId) {
		this.identifierId = identifierId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public ModelSignature getSignature() {
		return signature;
	}
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	

}
