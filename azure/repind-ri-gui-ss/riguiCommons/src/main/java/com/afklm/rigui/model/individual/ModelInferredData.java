package com.afklm.rigui.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelInferredData {

	public Long inferredDataId;
	public String key;
	public String value;
	private ModelSignature signature;

	/**
	 * @return the inferredId
	 */
	public Long getInferredDataId() {
		return inferredDataId;
	}

	/**
	 * @param inferredId
	 *            the inferredId to set
	 */
	public void setInferredDataId(Long inferredId) {
		this.inferredDataId = inferredId;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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

}