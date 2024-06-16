package com.afklm.repind.msv.handicap.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetInferredDataModel {
	
    private Long inferredId;
    private String gin;
    private String type;
    private String status;
    private SignatureModel signature;
    private List<HandicapDataModel> data;
    
	public Long getInferredId() {
		return inferredId;
	}
	public void setInferredId(Long inferredId) {
		this.inferredId = inferredId;
	}
	public String getGin() {
		return gin;
	}
	public void setGin(String gin) {
		this.gin = gin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public SignatureModel getSignature() {
		return signature;
	}
	public void setSignature(SignatureModel signature) {
		this.signature = signature;
	}
	public List<HandicapDataModel> getData() {
		return data;
	}
	public void setData(List<HandicapDataModel> data) {
		this.data = data;
	}
    
    

}