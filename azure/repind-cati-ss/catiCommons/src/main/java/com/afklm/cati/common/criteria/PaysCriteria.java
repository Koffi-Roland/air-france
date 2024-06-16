package com.afklm.cati.common.criteria;

import com.sun.istack.NotNull;
import org.apache.commons.lang.StringUtils;
/**
 * format codePays and normalisable fields of Table Pays before passing it to service
 * @author M436128
 *
 */
public class PaysCriteria {

	@NotNull
    String codePays;
    
    @NotNull
    String normalisable;
    
    public PaysCriteria(String codePays, String normalisable) {
    	setCodePays(codePays);
    	setNormalisable(normalisable);
    }

    public String getCodePays() {
        return codePays;
    }

    /**
     * set code pays and remove leading and trailing whitespace
     * @param codePays
     */
    public void setCodePays(final String codePays) {
    	if(StringUtils.isAlpha(codePays) && codePays.length() == 2) {
    		this.codePays = codePays;
    	}else {
    		throw new IllegalArgumentException("codePays: " + codePays + " is not a valid argument");
    	}
    }

    public String getNormalisable() {
        return normalisable;
    }

    /**
     * use "N" by default if normalisable is invalid
     * @param normalisable
     */
    public void setNormalisable(final String normalisable) {
    	if(StringUtils.isAlpha(normalisable) && (normalisable.equals("N") || normalisable.equals("O"))) {
    		this.normalisable = normalisable;
    	}else {
    		throw new IllegalArgumentException("normalisable: " + normalisable + " is not a valid argument");
    	}
    	
    }

}
