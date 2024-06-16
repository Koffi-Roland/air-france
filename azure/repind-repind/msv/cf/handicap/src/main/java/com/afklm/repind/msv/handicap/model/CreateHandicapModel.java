package com.afklm.repind.msv.handicap.model;

import java.util.List;

public class CreateHandicapModel {

    private String type;
    private String code;
	private List<HandicapDataCreateModel> data;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<HandicapDataCreateModel> getData() {
		return data;
	}

	public void setData(List<HandicapDataCreateModel> data) {
		this.data = data;
	}
	
}