package com.afklm.repindmsv.tribe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MemberModel {


	private String gin;
	private String role;
	private String status;
	private SignatureModel signature;
    
}