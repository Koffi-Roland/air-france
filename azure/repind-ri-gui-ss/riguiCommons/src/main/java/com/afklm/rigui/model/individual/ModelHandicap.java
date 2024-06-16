package com.afklm.rigui.model.individual;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelHandicap {
	
	public Long handicapId;
    public String gin;
    public String type;
	public String code;
	private ModelSignature signature;
	public Set<ModelHandicapData> data;
	
	/**
	 * @return the handicapId
	 */
	public Long getHandicapId() {
		return handicapId;
	}
	
	/**
	 * @param handicapId
	 *            the handicapId to set
	 */
	public void setHandicapId(Long handicapId) {
		this.handicapId = handicapId;
	}
	/**
	 * @return the gin
	 */
	public String getGin() {
		return gin;
	}
	
	/**
	 * @param gin
	 *            the gin to set
	 */
	public void setGin(String gin) {
		this.gin = gin;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the signature
	 */
	public ModelSignature getSignature() {
		return signature;
	}

	/**
	 * @param signature
	 *            the signature to set
	 */
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	
	/**
	 * @return the handicapData
	 */
	public Set<ModelHandicapData> getHandicapData() {
		return data;
	}
	
	/**
	 * @param handicapData
	 *            the handicapData to set
	 */
	public void setHandicapData(Set<ModelHandicapData> handicapData) {
		this.data = handicapData;
	}
}