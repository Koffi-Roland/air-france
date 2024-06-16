package com.airfrance.repind.util;

import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ContractIndividualDTO;

import java.util.Comparator;
import java.util.Date;

public class CompareContractDate implements Comparator<Object>{

	public CompareContractDate(){}
	
    public int compare(Object a, Object b) {
    	
    	ContractIndividualDTO contractDatea = (ContractIndividualDTO)a;
    	ContractIndividualDTO contractDateb = (ContractIndividualDTO)b;
    	
    	Date datea = new Date();
    	Date dateb = datea;
    	
    	if(contractDatea.getValidityEndDate() != null) {
    		datea = contractDatea.getValidityEndDate();
    	}
    	
    	if(contractDateb.getValidityEndDate() != null) {
    		dateb = contractDateb.getValidityEndDate();
    	}
    	
        if(datea.equals(dateb)){
        	if(contractDatea.getValidityStartDate() != null) {
        		datea = contractDatea.getValidityStartDate();
        	}
        	if(contractDateb.getValidityStartDate() != null) {
        		dateb = contractDateb.getValidityStartDate();
        	}
        	if(datea.equals(dateb)) {
        		return 0;
        	}
        	if( datea.after(dateb)) {
                return -1;
            } else{
                return 1;
            }
        }
        if( datea.after(dateb)) {
            return -1;
        } else {
            return 1;
        }
    }
}
