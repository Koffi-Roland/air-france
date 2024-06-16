package com.airfrance.ref.type;

import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum TableReferenceEnum {

	/**
	 * If defaultMaxResults = 0, we take the max result set in PaginationUtils
	 * Max output for this V2 is 200
	 */
	
	// REPIND-1018 : For WIDGET/ADHESION PHASE OUT
	PAYS("PAYS", 0),

	REF_COMPREF("REF_COMPREF", 0),
	REF_COMPREF_DOMAIN("REF_COMPREF_DOMAIN", 0),
	REF_COMPREF_TYPE("REF_COMPREF_TYPE", 0),
	REF_COMPREF_GTYPE("REF_COMPREF_GTYPE", 0),
	REF_COMPREF_MEDIA("REF_COMPREF_MEDIA", 0),
	REF_COMPREF_COUNTRY_MARKET("REF_COMPREF_COUNTRY_MARKET", 0),
	REF_PERMISSIONS_QUESTION("REF_PERMISSIONS_QUESTION", 20),
	REF_PERMISSIONS("REF_PERMISSIONS", 10),
	
	// REPIND-1776 : Preference for CBS/CAPI
	REF_PREFERENCE_KEY_TYPE("REF_PREFERENCE_KEY_TYPE", 0),

	//REPIND-1768 : HDC Migration
	REF_HANDICAP_TYPE("REF_HANDICAP_TYPE", 0),
	REF_HANDICAP_CODE("REF_HANDICAP_CODE", 0),
	REF_HANDICAP_DATA_KEY("REF_HANDICAP_DATA_KEY", 0),

	// REPIND-2926 : Add LA-PREMIERE ref tables for CAST
	REF_LP_DRINK_BEVERAGES("REF_LP_DRINK_BEVERAGES", 0),
	REF_LP_MEALS("REF_LP_MEALS", 0);
	
	private String tableName;
	private int defaultMaxResults;
	
	TableReferenceEnum(String tableName, int defaultMaxResults) {
		this.tableName = tableName;
		this.defaultMaxResults = defaultMaxResults;
	}
	
	public String toString() {
		return tableName;
	}
		
	public static boolean isExist(String name) throws MissingParameterException {
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Table Name is missing");
		}
		
		for (TableReferenceEnum e : values()) {
	        if (e.tableName.equalsIgnoreCase(name)) {
	            return true;
	        }
	    }
		
		return false;
	}
		
	public int getDefaultMaxResults() throws MissingParameterException {
		return defaultMaxResults;
	}
	
}
