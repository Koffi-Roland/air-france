package com.afklm.rigui.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelHandicapData {

	public Long handicapDataId;
	public String key;
	public String value;
	private ModelSignature signature;

	/**
	 * @return the handicapDataId
	 */
	public Long getHandicapDataId() {
		return handicapDataId;
	}

	/**
	 * @param handicapDataId
	 *            the handicapDataId to set
	 */
	public void setHandicapDataId(Long handicapDataId) {
		this.handicapDataId = handicapDataId;
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