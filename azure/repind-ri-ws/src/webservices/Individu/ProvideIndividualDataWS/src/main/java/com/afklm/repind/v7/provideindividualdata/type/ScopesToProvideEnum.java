package com.afklm.repind.v7.provideindividualdata.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum ScopesToProvideEnum {
	
	ALL("ALL"),
	INDIVIDUAL("INDIVIDUAL"),
	IDENTIFICATION("IDENTIFICATION"),
	POSTAL_ADDRESS("POSTAL ADDRESS"),
	LOCALIZATION("LOCALIZATION"),
	EMAIL("EMAIL"),
	TELECOM("TELECOM"),
	CONTRACT("CONTRACT"),
	EXTERNAL_IDENTIFIER("EXTERNAL IDENTIFIER"),
	ACCOUNT("ACCOUNT"),
	PREFILLED_NUMBER("PREFILLED NUMBER"),
	PREFERENCE("PREFERENCE"),
	MARKETING("MARKETING"),
	FLYING_BLUE_CONTRACT("FLYING BLUE CONTRACT"),
	SOCIAL_NETWORK("SOCIAL NETWORK"),
	COMMUNICATIION_PREFERENCE("COMMUNICATION PREFERENCE"),
	DELEGATION("DELEGATION"),
	TRAVELER("TRAVELER"), 
	UCCR_CONTRACT("UCCR CONTRACT"),
	ALERT("ALERT");
	
	private String scope;
	
	ScopesToProvideEnum(String scope) {
		this.scope = scope;
	}
	
	public static Set<ScopesToProvideEnum> getEnumSet(List<String> nameList) throws InvalidParameterException {
		Set<ScopesToProvideEnum> enumTypeSet = null;
		if(nameList != null && !nameList.isEmpty()) {
			enumTypeSet = new HashSet<ScopesToProvideEnum>();
			for(String name : nameList) {
				try {
					ScopesToProvideEnum enumType = getEnumMandatory(name);
					enumTypeSet.add(enumType);
				} catch(MissingParameterException e) {
				}
			}
		}
		return enumTypeSet;
	}
	
	public static ScopesToProvideEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing scope");
		}
		
		for (ScopesToProvideEnum e : values()) {
	        if (e.scope.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid scope: "+name);
	}
	
	public String toString() {
		return scope;
	}
	
}
