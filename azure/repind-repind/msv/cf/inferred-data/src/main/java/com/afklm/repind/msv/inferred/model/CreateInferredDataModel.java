package com.afklm.repind.msv.inferred.model;

import java.util.List;

public class CreateInferredDataModel {
	
    private String gin;
    private String type;
    private String application;
    private List<InferredDataModel> data;
    
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
	public List<InferredDataModel> getData() {
		return data;
	}
	public void setData(List<InferredDataModel> data) {
		this.data = data;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
    
    

}