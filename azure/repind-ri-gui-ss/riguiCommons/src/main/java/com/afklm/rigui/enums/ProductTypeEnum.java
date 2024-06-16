package com.afklm.rigui.enums;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum ProductTypeEnum {

	FLYING_BLUE("FP"),
	MY_ACCOUNT("MA"),
	SAPHIR("S");
	
	private String type;
	
	ProductTypeEnum(String type) {
		this.type = type;
	}
	
	public static ProductTypeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing product type");
		}
		
		for (ProductTypeEnum e : values()) {
	        if (e.type.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid product type: "+name);
	}
	
	   
	
	public String toString() {
		return type;
	}
	
}
