package com.afklm.rigui.model.individual;

public class ModelDelegationDataInfo {
	
	private Integer delegationDataInfoId;
    private String type;
    private Integer typeGroupId;
    private String key;
    private String value;
    private ModelSignature signature;
    private ModelDelegationData delegationData;
    
	public Integer getDelegationDataInfoId() {
		return delegationDataInfoId;
	}
	public void setDelegationDataInfoId(Integer delegationDataInfoId) {
		this.delegationDataInfoId = delegationDataInfoId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getTypeGroupId() {
		return typeGroupId;
	}
	public void setTypeGroupId(Integer typeGroupId) {
		this.typeGroupId = typeGroupId;
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
	public ModelDelegationData getDelegationData() {
		return delegationData;
	}
	public void setDelegationData(ModelDelegationData delegationData) {
		this.delegationData = delegationData;
	}

}
