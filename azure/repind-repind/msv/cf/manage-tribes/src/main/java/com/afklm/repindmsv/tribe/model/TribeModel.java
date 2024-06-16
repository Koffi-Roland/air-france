package com.afklm.repindmsv.tribe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class TribeModel {

	private String id;
	private String name;
	private String type;
	private String status;
	private Date deletionDate;
	private Set<MemberModel> members;
	private SignatureModel signature;
	
}