package com.afklm.rigui.spring.rest.resources;

import java.io.Serializable;

public class RefPreferenceKeyTypeResource implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private String type;
	
	private Integer min_length;
	
	private Integer max_length;
	
	private String data_type;
	
	private String condition;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMin_length() {
		return min_length;
	}

	public void setMin_length(Integer min_length) {
		this.min_length = min_length;
	}

	public Integer getMax_length() {
		return max_length;
	}

	public void setMax_length(Integer max_length) {
		this.max_length = max_length;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
