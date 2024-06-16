package com.airfrance.repind.service.internal.unitservice.firm;

/*PROTECTED REGION ID(_Bszf0LeJEeSM_NEE6QydtQ US i) ENABLED START*/

// add not generated imports here

import com.airfrance.repind.entity.adresse.PostalAddress;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
public class PersonneMoraleUS {

    /** logger */
    private static final Log log = LogFactory.getLog(PersonneMoraleUS.class);

    /*PROTECTED REGION ID(_Bszf0LeJEeSM_NEE6QydtQ u var) ENABLED START*/
    // add your custom variables here if necessary
    
    private static final String M_INTERNAL_KEY = "H46yGR0DU2qOYaEukX5MbWfTniQmKeF7ScZh8wdCAzBVIjlvNoLPr1J9gtps3x";
    
    /*PROTECTED REGION END*/
    
    /**
     * empty constructor
     */
    public PersonneMoraleUS() {
    }


    /*PROTECTED REGION ID(_Bszf0LeJEeSM_NEE6QydtQ u m) ENABLED START*/
    // add your custom methods here if necessary
	
    /**
	 * Generate token's key using characters' ASCII code and the hour of the current date
	 * @param raisonSociale
	 * @param codePays
	 * @param codeMetier
	 * @param szTimeStamp
	 * @return
	 */
	public String generateTokenKey(String raisonSociale, String typeFirme, String codePays, String codeMetier)
	{
		// Returned key
		String key = null;
		
		// Returned key buffer
		StringBuffer keyBuffer = new StringBuffer("");
		
		// Getting the current hour
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		
		// Inputs concatenation
		StringBuffer inputBuffer = new StringBuffer();
		inputBuffer.append(raisonSociale);
		inputBuffer.append(codePays);
		inputBuffer.append(codeMetier);
		inputBuffer.append(hours);
		inputBuffer.append(typeFirme);
		String input = inputBuffer.toString().toUpperCase();

		int data[] = {0, 0, 0, 0, 0, 0, 0, 0};

		//We add characters' ASCII code to each element of data[]
		int index = 0;
		while(index < input.length())
		{
			char indexedChar = input.charAt(index);
			int indexedCharCode = (int)indexedChar;
			data[index % data.length] += indexedCharCode;
			index += 1;
		}
		
		//We generate the key
		for(int i = 0; i < data.length; i += 1)
		{
			int keyFragmentInt = data[i] % M_INTERNAL_KEY.length();
			char keyFragment = charFromIndex(keyFragmentInt);	
			keyBuffer.append(keyFragment);
		}
		
		key = keyBuffer.toString();
		
		return key;
	}
	
	/**
	 * Generate token's key using characters' ASCII code and the hour of the current date
	 * @param raisonSociale
	 * @param codePays
	 * @param codeMetier
	 * @param szTimeStamp
	 * @return
	 */
	public String generateTokenKey(String raisonSociale, String codePays, String codeMetier)
	{
		// Returned key
		String key = null;
		
		// Returned key buffer
		StringBuffer keyBuffer = new StringBuffer("");
		
		// Getting the current hour
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		
		// Inputs concatenation
		StringBuffer inputBuffer = new StringBuffer();
		inputBuffer.append(raisonSociale);
		inputBuffer.append(codePays);
		inputBuffer.append(codeMetier);
		inputBuffer.append(hours);
		String input = inputBuffer.toString().toUpperCase();

		int data[] = {0, 0, 0, 0, 0, 0, 0, 0};

		//We add characters' ASCII code to each element of data[]
		int index = 0;
		while(index < input.length())
		{
			char indexedChar = input.charAt(index);
			int indexedCharCode = (int)indexedChar;
			data[index % data.length] += indexedCharCode;
			index += 1;
		}
		
		//We generate the key
		for(int i = 0; i < data.length; i += 1)
		{
			int keyFragmentInt = data[i] % M_INTERNAL_KEY.length();
			char keyFragment = charFromIndex(keyFragmentInt);	
			keyBuffer.append(keyFragment);
		}
		
		key = keyBuffer.toString();
		
		return key;
	}
	
	private char charFromIndex(int index)
	{
		char result = M_INTERNAL_KEY.charAt(index);
		return result;
	}
	
    /**
     * Return the code country of the valid localization postal address
     * @param pAdrs
     * @return
     */
    public String getCodeCountryValidLocalizationPostalAddress(Collection<PostalAddress> pAdrs) {
    	String sCodePays = null;
    	
    	for (PostalAddress adresse : pAdrs) {
    		if ("V".equals(adresse.getSstatut_medium()) 
    				&& "L".equals(adresse.getScode_medium())) {
    			sCodePays = adresse.getScode_pays();
    			break;
    		}
		}
    	
    	return sCodePays;
    }
	
    /*PROTECTED REGION END*/
}
