package com.airfrance.repind.model.consent;

import java.util.ArrayList;
import java.util.List;

public class ConsentModel {
	private String application;
	private String gin;
	private String type;
	private List<ConsentDataModel> data;

	public ConsentModel() {
		super();
		this.data = new ArrayList<>();
	}

	public List<ConsentDataModel> getData() {
		return data;
	}

	public void setData(List<ConsentDataModel> data) {
		this.data = data;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
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
}
