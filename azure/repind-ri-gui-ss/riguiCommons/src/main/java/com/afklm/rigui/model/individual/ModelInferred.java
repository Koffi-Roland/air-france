package com.afklm.rigui.model.individual;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelInferred {
	
    public Long inferredId;
    public String gin;
    public String type;
    public String status;
	private ModelSignature signature;
	public Set<ModelInferredData> inferredData;

	/**
	 * @return the inferredId
	 */
	public Long getInferredId() {
		return inferredId;
	}

	/**
	 * @param inferredId
	 *            the inferredId to set
	 */
	public void setInferredId(Long inferredId) {
		this.inferredId = inferredId;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public ModelSignature getSignature() {
		return signature;
	}

	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}

	/**
	 * @return the inferredData
	 */
	public Set<ModelInferredData> getInferredData() {
		return inferredData;
	}

	/**
	 * @param inferredData
	 *            the inferredData to set
	 */
	public void setInferredData(Set<ModelInferredData> inferredData) {
		this.inferredData = inferredData;
	}

}