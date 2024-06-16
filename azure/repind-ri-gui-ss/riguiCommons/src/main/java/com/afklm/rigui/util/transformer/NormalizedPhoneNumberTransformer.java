package com.afklm.rigui.util.transformer;

import com.afklm.rigui.enums.NormalizedPhoneTypeEnum;
import com.afklm.rigui.dto.telecom.NormalizePhoneNumberDTO;
import com.afklm.rigui.util.PhoneNumberUtils;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class NormalizedPhoneNumberTransformer {

	public static NormalizePhoneNumberDTO transform(PhoneNumber phoneNumber) {
    	
		if(phoneNumber==null) {
			return null;
		}
		
    	PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    	String internationalCountryCode = String.valueOf(phoneNumber.getCountryCode());
    	String nationalPhoneNumber = phoneNumberUtil.format(phoneNumber,PhoneNumberFormat.NATIONAL);
    	String nationalPhoneNumberClean = PhoneNumberUtils.cleanPhoneNumber(nationalPhoneNumber); 
    	String internationalPhoneNumber = phoneNumberUtil.format(phoneNumber,PhoneNumberFormat.E164);
    	
    	PhoneNumberType phoneNumberType = phoneNumberUtil.getNumberType(phoneNumber);
    	String terminalTypeDetail = phoneNumberType.toString();
    	String terminalType = transform(phoneNumberType);
    	
    	NormalizePhoneNumberDTO normalizePhoneNumberDTO = new NormalizePhoneNumberDTO();
    	normalizePhoneNumberDTO.setNormalizedInternationalCountryCode(internationalCountryCode);
    	normalizePhoneNumberDTO.setNormalizedInternationalPhoneNumber(internationalPhoneNumber);
    	normalizePhoneNumberDTO.setNormalizedNationalPhoneNumber(nationalPhoneNumber);
    	normalizePhoneNumberDTO.setNormalizedNationalPhoneNumberClean(nationalPhoneNumberClean);
    	normalizePhoneNumberDTO.setNormalizedTerminalType(terminalType);
    	normalizePhoneNumberDTO.setNormalizedTerminalTypeDetail(terminalTypeDetail);
    	
    	return normalizePhoneNumberDTO;
    }
    
    public static String transform(PhoneNumberType phoneNumberType) {
    	
    	if(phoneNumberType==null) {
    		return null;
    	}
    	
    	String normalizedPhoneNumberType = null;
    	
    	switch(phoneNumberType) {
    		case FIXED_LINE: 
    			normalizedPhoneNumberType = NormalizedPhoneTypeEnum.FIX.toString();
    			break;
    		case MOBILE:
    			normalizedPhoneNumberType = NormalizedPhoneTypeEnum.MOBILE.toString();
    			break;
    		default:
    			normalizedPhoneNumberType = NormalizedPhoneTypeEnum.UNKNOWN.toString();
    			break;
    	}
    	
    	return normalizedPhoneNumberType;
    }
	
}
