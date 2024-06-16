package com.afklm.rigui.fonetik.fonc;

import com.afklm.rigui.fonetik.PhoVoy;
import com.afklm.rigui.fonetik.TabSubstStr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PhonEDT implements IPhonetikFonction {
    /** logger */
    private static final Log logger = LogFactory.getLog(PhonEDT.class);
    
	private List<TabSubstStr> tabEdt = new ArrayList<TabSubstStr>();
	public PhonEDT() {
		tabEdt.add(new TabSubstStr("T"," "));
		tabEdt.add(new TabSubstStr("D"," "));
		tabEdt.add(new TabSubstStr("B"," "));
		tabEdt.add(new TabSubstStr("G"," "));
		tabEdt.add(new TabSubstStr("P"," "));
	}
	
	public String process(String tampon) {
		StringBuffer result = new StringBuffer(tampon);
		
		int tampLen = result.length()-1;
	    /* uniquement si au moins 2 car */
	    if(tampLen>0)
	    {
	        if((result.charAt(tampLen) != '.') &&
	           (result.charAt(tampLen-1) != '.') &&
	           (PhoVoy.isConsonne(result.charAt(tampLen-1))))
	        {
	            /* On scanne tous les elements de TabEdt */
	    		int iElem = 0;
	    		for (TabSubstStr edt : tabEdt) {
	                if((result.charAt(tampLen) == edt.getTexte().charAt(0)) &&
	 	                   ((iElem != 0) || ((iElem == 0) && (result.charAt(tampLen-1) != 'K')))) {
	                	result.setCharAt(tampLen, edt.getPhone().charAt(0));
	                }	                	
	    			iElem++;				
	    		}		
	        }
	    }
		
		return result.toString();
	}

}
