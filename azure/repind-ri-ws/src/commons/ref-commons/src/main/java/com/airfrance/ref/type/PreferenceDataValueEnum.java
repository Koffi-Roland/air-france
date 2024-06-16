package com.airfrance.ref.type;

import java.util.ArrayList;
import java.util.Arrays;

public enum PreferenceDataValueEnum {
	
	    COD_HCP1 ("codeHCP1", ValuesForKeys.COD_HCP),
	    COD_HCP2 ("codeHCP2", ValuesForKeys.COD_HCP),
	    COD_HCP3 ("codeHCP3", ValuesForKeys.COD_HCP),
	    
	    COD_MAT1 ("codeMat1", ValuesForKeys.COD_MAT),
	    COD_MAT2 ("codeMat2", ValuesForKeys.COD_MAT);

		private String name;
		private ValuesForKeys values;

	    PreferenceDataValueEnum(String name, ValuesForKeys values) {
	    	this.name = name;
	        this.values = values;
	    }
	    
	    public String getName() {
	    	return this.name;
	    }
	    
	    public static ArrayList<String> getAuthorizedValues(String key) {
	    	for (PreferenceDataValueEnum pref : PreferenceDataValueEnum.values()) {
	    		if (pref.getName().equalsIgnoreCase(key)) {
	    			return pref.values.getListOfValues();
	    		}
	    	}
	    	return null;
	    }
	    
	    public static boolean isKeyExists(String key) {
	    	for (PreferenceDataValueEnum pref : PreferenceDataValueEnum.values()) {
	    		if (pref.getName().equalsIgnoreCase(key)) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    	    
	    public enum ValuesForKeys {
	    	
	    	COD_HCP ("WCHR", "WCHS", "WCHC", "BLND", "DEAF", "DUMB", "EXST", "DM"),
	    	COD_MAT ("WCMP", "WCBD", "WCBW");
	    	
	    	String[] values;
	    	
	    	ValuesForKeys (String... values) {
	    		this.values = values;
	    	}
	    	
	    	public ArrayList<String> getListOfValues() {
	    		if (values == null) return null;
	    		
	    		return new ArrayList<>(Arrays.asList(values));
	    	}
	    	
	    }
}