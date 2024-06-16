package com.afklm.rigui.model.individual;

import java.util.Set;

public class ModelDelegationData {
	
	private Integer delegationId;
    private String status;
    private String type;
    private ModelSignature signature;
    private String sender;
    private ModelIndividual delegate;
    private ModelIndividual delegator;
    private Set<ModelDelegationDataInfo> delegationDataInfo;
    
	public Integer getDelegationId() {
		return delegationId;
	}
	public void setDelegationId(Integer delegationId) {
		this.delegationId = delegationId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ModelSignature getSignature() {
		return signature;
	}
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public ModelIndividual getDelegate() {
		return delegate;
	}
	public void setDelegate(ModelIndividual delegate) {
		this.delegate = delegate;
	}
	public ModelIndividual getDelegator() {
		return delegator;
	}
	public void setDelegator(ModelIndividual delegator) {
		this.delegator = delegator;
	}
	public Set<ModelDelegationDataInfo> getDelegationDataInfo() {
		return delegationDataInfo;
	}
	public void setDelegationDataInfo(Set<ModelDelegationDataInfo> delegationDataInfo) {
		this.delegationDataInfo = delegationDataInfo;
	}
}
