package com.afklm.repind.v7.provideindividualdata.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum PopulationTargetedEnum {
	
	A("A"),	// All type of individual 
	I("I"),	// Individual with contact as (FB, MyAccount, Saphir, AMEX etc.)
	T("T"),	// Traveler with ROLE_TRAVELERS
	W("W"),	// WhiteWinger is KLM prospect	
	C("C"),	// Claimers coming from I-CARE
	G("G"),	// AF employee with ROLE_GP
	E("E"),	// External Identifier or Social with EXTERNAL_IDENTIFIER
	S("S");	// Social network data
	
	private String populationTargeted;
	
	PopulationTargetedEnum(String populationTargeted) {
		this.populationTargeted = populationTargeted;
	}

	public static PopulationTargetedEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing populationTargeted");
		}
		
		for (PopulationTargetedEnum e : values()) {
	        if (e.populationTargeted.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid populationTargeted: "+name);
	}
	
	public String toString() {
		return populationTargeted;
	}
	
}
