package com.afklm.rigui.spring.rest.resources;

import java.io.Serializable;
import java.util.Date;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TrackingResource extends RiguiCommonResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * dateModification
     */
    @Mapping("sdateModification")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date dateModification;
    
    /**
     * action
     */
    private String action;

    /**
     * type
     */
    private String type;

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
