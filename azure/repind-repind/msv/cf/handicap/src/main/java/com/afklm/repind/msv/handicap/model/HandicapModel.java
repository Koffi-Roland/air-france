package com.afklm.repind.msv.handicap.model;

import java.util.List;

public class HandicapModel {
	
	private Long handicapId;
    private String type;
    private String code;
    private List<HandicapDataModel> data;
    private SignatureModel signature;
    
    public Long getHandicapId() {
		return handicapId;
	}
	public void setHandicapId(Long handicapId) {
		this.handicapId = handicapId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<HandicapDataModel> getData() {
		return data;
	}
	public void setData(List<HandicapDataModel> data) {
		this.data = data;
	}
	public SignatureModel getSignature() {
		return signature;
	}
	public void setSignature(SignatureModel signature) {
		this.signature = signature;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}