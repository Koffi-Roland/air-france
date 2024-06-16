package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

/*
 * Could be deprecated once we will be able to get properly delegation by type from DB
 *  
@Deprecated*/
/**
 * Do not use it anymore, a ref_tab is existing to check that :
 * REF_DELEGATION_TYPE. Use methode boolean isDelegationTypeValid(String type)
 * from DelegationDAO instead. This method is also usable from DelegationDS
 * boolean isDelegationTypeValid(DelegationDataDTO delegationDataDTO) which call
 * the DAO.
 * 
 * @author m421262
 *
 */
public enum DelegationTypeEnum {

	TRAVEL_MANAGER("TM"), ULTIMATE_FAMILLY("UF"), UNACOMPAGNED_MINOR("UM"), UNACOMPAGNED_MINOR_ATTENDANT("UA");

	private String type;

	DelegationTypeEnum(String type) {
		this.type = type;
	}

	public static DelegationTypeEnum fromString(String value) {
	    for (DelegationTypeEnum b : DelegationTypeEnum.values()) {
	      if (b.type.equalsIgnoreCase(value)) {
	        return b;
	      }
	    }
	    return null;
	  }
	
	public static DelegationTypeEnum getEnumMandatory(String name)
			throws InvalidParameterException, MissingParameterException {

		if (StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing delegation type");
		}

		for (DelegationTypeEnum e : values()) {
			if (e.type.equals(name)) {
				return e;
			}
		}

		throw new InvalidParameterException("Invalid delegation type: " + name);
	}

	public String toString() {
		return type;
	}

}
