package com.afklm.cati.common.spring.rest.resources;

import java.io.Serializable;

public class EntryPointResource extends CatiCommonResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1517483939445778428L;
	
	private String applicationName;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
}
