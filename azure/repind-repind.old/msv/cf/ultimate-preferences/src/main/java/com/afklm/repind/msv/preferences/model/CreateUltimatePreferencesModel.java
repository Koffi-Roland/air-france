package com.afklm.repind.msv.preferences.model;

import java.util.List;

public class CreateUltimatePreferencesModel {
	
    private String gin;
    private String actionCode;
    private String type;    
    private RequestorModel requestor;
    private List<UltimatePreferencesModel> data;
    

	public RequestorModel getRequestor() {
		return requestor;
	}
	public void setRequestor(RequestorModel requestor) {
		this.requestor = requestor;
	}
    
    public String getGin() {
		return gin;
	}
	public void setGin(String gin) {
		this.gin = gin;
	}
	public String getType() {
		if (type != null) {
			return type.toUpperCase();
		}
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<UltimatePreferencesModel> getData() {
		return data;
	}
	public void setData(List<UltimatePreferencesModel> data) {
		this.data = data;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
}