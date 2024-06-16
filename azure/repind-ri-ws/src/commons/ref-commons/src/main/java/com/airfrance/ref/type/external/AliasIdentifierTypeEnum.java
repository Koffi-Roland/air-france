
package com.airfrance.ref.type.external;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum AliasIdentifierTypeEnum {

	CA("Corporate Alias"),
    TA("TravelAgent Alias"),
    LA("Leasure Alias");

    private String type;
    
    AliasIdentifierTypeEnum(String type) {
		this.type = type;
	}
    
    public static AliasIdentifierTypeEnum getEnum(String name) throws InvalidParameterException {
		
    	AliasIdentifierTypeEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static AliasIdentifierTypeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing alias option");
		}
		
		for (AliasIdentifierTypeEnum e : values()) {
	        if (e.type.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid alias option: "+name);
	}
	
	public static List<String> getAllAlias() {
		List<String> listOfAllias = new ArrayList<String>();
		
		for (AliasIdentifierTypeEnum e : values()) {
			listOfAllias.add(e.name());
	    }
		
		return listOfAllias;
	}
	
	public String toString() {
		return type;
	}

}
