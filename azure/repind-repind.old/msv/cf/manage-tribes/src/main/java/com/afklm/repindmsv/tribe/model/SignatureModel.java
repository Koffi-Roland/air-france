package com.afklm.repindmsv.tribe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SignatureModel {

	private String modificationSignature;
	private String modificationSite;
	private Date modificationDate;
	private String creationSignature;
	private String creationSite;
	private Date creationDate;
	
}