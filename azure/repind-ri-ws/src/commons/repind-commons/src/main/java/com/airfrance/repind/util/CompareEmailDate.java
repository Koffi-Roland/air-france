package com.airfrance.repind.util;

import com.airfrance.repind.dto.adresse.EmailDTO;

import java.util.Comparator;
import java.util.Date;

public class CompareEmailDate implements Comparator{

	public CompareEmailDate(){}
	
    public int compare(Object a, Object b) {
    	
    	EmailDTO EmailDatea = (EmailDTO)a;
    	EmailDTO EmailDateb = (EmailDTO)b;
    	Date datea = new Date();
    	Date dateb = new Date();
    	
    	if(EmailDatea.getSignatureModification() != null) {
    		datea = EmailDatea.getDateModification();
    	}
    	
    	if(EmailDateb.getSignatureModification() != null) {
    		dateb = EmailDateb.getDateModification();
    	}
    	
        if(datea.equals(dateb)){
            return 0;
        }  
        if( datea.after(dateb)){
            return 1;
        }
        else{
            return 0;
        }
    }
}
