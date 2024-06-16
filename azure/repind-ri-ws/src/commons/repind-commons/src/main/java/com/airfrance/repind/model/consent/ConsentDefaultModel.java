package com.airfrance.repind.model.consent;

public class ConsentDefaultModel {
	private String application;
	private String gin;

	public ConsentDefaultModel() {
		super();
	}

	public ConsentDefaultModel(String gin, String application) {
		super();
		this.application = application;
		this.gin = gin;
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
}
