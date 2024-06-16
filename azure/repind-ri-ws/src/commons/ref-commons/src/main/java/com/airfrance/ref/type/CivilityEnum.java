package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum CivilityEnum {
	M_("M."),
	MISTER("MR"),
	MS("MS"),
	MISS("MISS"),
	MRS("MRS"),
	MX("MX");

	private String code;
	
	CivilityEnum(String code) {
		this.code = code;
	}
	
	
	public static CivilityEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing civility");
		}
		
		for (CivilityEnum e : values()) {
	        if (e.code.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid civility code: " + name);
	}
	
	public String toString() {
		return code;
	}
	
}
