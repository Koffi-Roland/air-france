package com.airfrance.repind.service.telecom.internal;

import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dto.telecom.NormalizePhoneNumberDTO;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repind.util.transformer.NormalizedPhoneNumberTransformer;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.ValidationResult;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class NormalizePhoneNumberDS {

    /** logger */
    private static final Log log = LogFactory.getLog(NormalizePhoneNumberDS.class);

    /*PROTECTED REGION ID(_w3G4AGYwEeOQF5R-qFxxUQ u var) ENABLED START*/

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
    
    @Autowired
	@Qualifier("variablesDS")
	protected VariablesDS variablesDS;
    
    private final String COUNTRY_CODE_REGEX = "(^\\+{0,1}[0-9]{1,3}$)|(^[A-Z]{2}$)";
    private final String PHONE_NUMBER_REGEX = "^[\\d\\s\\+\\.\\-\\(\\)]*$";
    
    private final String COUNTRY_CODE_NUM_REGEX = "^[0-9]{1,3}$";
    private final String COUNTRY_CODE_ALPHANUM_REGEX = "^\\+{1}[0-9]{1,3}$";
    private final String COUNTRY_CODE_ALPHA_REGEX = "^[A-Z]{2}$";
    
    private final String COUNTRY_CODE_ERROR = "ZZ";
    private final String COUNTRY_CODE_EMPTY = "empty";
    
    /*PROTECTED REGION END*/

    /**
     * empty Constructor
     */
    public NormalizePhoneNumberDS() {
    }


    /** 
     * normalizePhoneNumber
     * @param countryCode in String
     * @param phoneNumber in String
     * @return The normalizePhoneNumber as <code>NormalizePhoneNumberDTO</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public NormalizePhoneNumberDTO normalizePhoneNumber(String countryCode, String phoneNumber) throws JrafDomainException {
        /*PROTECTED REGION ID(_8DwWoGYwEeOQF5R-qFxxUQ) ENABLED START*/

    	if(!isValidCountryCode(countryCode)) {    		
    		throw new InvalidCountryCodeException(countryCode);    		
    	}
    	
    	if(!isValidPhoneNumber(phoneNumber)) {
    		throw new InvalidPhoneNumberException(phoneNumber);    		
    	}
    	
    	String regionCode = normalizeCountryCode(countryCode);
    	
    	PhoneNumber phoneNumberResult = parsePhoneNumber(regionCode, phoneNumber);
     	checkPhoneNumberPossibility(phoneNumberResult, countryCode, phoneNumber);    	
    	checkPhoneNumberValidity(phoneNumberResult, regionCode, phoneNumber);
		
		return NormalizedPhoneNumberTransformer.transform(phoneNumberResult);
    	
        /*PROTECTED REGION END*/
    }
    
    /** 
     * normalizePhoneNumber
     * @param countryCode in String
     * @param phoneNumber in String
     * @return The normalizePhoneNumber as <code>NormalizePhoneNumberDTO</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public NormalizePhoneNumberDTO normalizePhoneNumber(String countryCode, String phoneNumber, boolean bForce) throws JrafDomainException {
        /*PROTECTED REGION ID(_8DwWoGYwEeOQF5R-qFxxUQ) ENABLED START*/

    	if(!isValidCountryCode(countryCode)) {
    		if (!bForce)
    			throw new InvalidCountryCodeException(countryCode);
    		else 
    			throw new JrafDomainNoRollbackException(countryCode);
    	}
    	
    	if(!isValidPhoneNumber(phoneNumber)) {
    		if (!bForce)
    			throw new InvalidPhoneNumberException(phoneNumber);
    		else 
    			throw new JrafDomainNoRollbackException(countryCode);
    	}
    	
    	String regionCode = normalizeCountryCode(countryCode);
    	
    	PhoneNumber phoneNumberResult = parsePhoneNumber(regionCode, phoneNumber);
    	
    	if (!bForce)
    	{
    		checkPhoneNumberPossibility(phoneNumberResult, countryCode, phoneNumber);    	
    		checkPhoneNumberValidity(phoneNumberResult, regionCode, phoneNumber);
    	}
			
		return NormalizedPhoneNumberTransformer.transform(phoneNumberResult);
    	
        /*PROTECTED REGION END*/
    }
    

    /** 
     * isValidCountryCode
     * @param countryCode in String
     * @return The isValidCountryCode as <code>Boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    public Boolean isValidCountryCode(String countryCode) throws JrafDomainException {
        /*PROTECTED REGION ID(__puXwGYwEeOQF5R-qFxxUQ) ENABLED START*/
    	
    	if(StringUtils.isEmpty(countryCode)) {
    		return false;
    	}
    	
    	return countryCode.matches(COUNTRY_CODE_REGEX);
    	
        /*PROTECTED REGION END*/
    }
    

    /** 
     * isValidPhoneNumber
     * @param phoneNumber in String
     * @return The isValidPhoneNumber as <code>Boolean</code>
     * @throws JrafDomainException en cas d'exception
     */
    public Boolean isValidPhoneNumber(String phoneNumber) throws JrafDomainException {
        /*PROTECTED REGION ID(_HGz0YGYxEeOQF5R-qFxxUQ) ENABLED START*/
  
    	if(StringUtils.isEmpty(phoneNumber)) {
    		return false;
    	}
    	
    	return phoneNumber.matches(PHONE_NUMBER_REGEX);
    	
        /*PROTECTED REGION END*/
    }
    


    /*PROTECTED REGION ID(_w3G4AGYwEeOQF5R-qFxxUQ u m) ENABLED START*/

    public String computeIsoCountryCode(String countryCode, String phoneNumber) throws JrafDomainException {
    	
    	// nothing to do
    	if(StringUtils.isEmpty(countryCode) && StringUtils.isEmpty(phoneNumber)) {
    		return null;
    	}
    	
    	// get region code from country code
		String regionCode = normalizeCountryCodeNumeric(countryCode);
		
		// normalize phone number
		PhoneNumber phoneNumberResult = parsePhoneNumber(regionCode, phoneNumber);
		
		// get lib phonenumber
    	PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    	
    	// return region code from normalized phone
    	return phoneNumberUtil.getRegionCodeForNumber(phoneNumberResult);
    }
    
    protected String normalizeCountryCode(String countryCode) throws JrafDomainException {

    	if(StringUtils.isEmpty(countryCode)) {
    		throw new InvalidCountryCodeException(COUNTRY_CODE_EMPTY);
    	}
    	
    	String normalizedCountryCode = null;
    	
    	if(countryCode.matches(COUNTRY_CODE_ALPHA_REGEX)) { // Example : FR
    		normalizedCountryCode = countryCode;
    	}
    	
    	if(countryCode.matches(COUNTRY_CODE_ALPHANUM_REGEX)) { // Example : +33
    		normalizedCountryCode = normalizeCountryCodeNumeric(countryCode.substring(1));
    	}
    	
    	if(countryCode.matches(COUNTRY_CODE_NUM_REGEX)) { // Example : 33
    		normalizedCountryCode = normalizeCountryCodeNumeric(countryCode);
    	}
    	
    	if(normalizedCountryCode == null || COUNTRY_CODE_ERROR.equals(normalizedCountryCode)) {
    		throw new InvalidCountryCodeException(countryCode);
    	}
    	
    	return normalizedCountryCode;
    	
    }
    
    /**
     * This method is aimed to normalize a country code into a region code
     * 
     * <p>
     * 	Example: 33 -> FR
     * </p>
     * 
     * <p>
     * Warning: some country codes target several region code (example: 1 -> US/CA/BM/...)
     * <ul>
     * 	<li>a default region is set (example: 1 -> US)</li>
     *	<li>it does not affect normalization</li>
     * 	<li>it affects {@link PhoneNumberUtil#isValidNumberForRegion(PhoneNumber, String)}
     * </ul>
     * </p>
     */
    protected String normalizeCountryCodeNumeric(String countryCode) throws JrafDomainException {
    
    	int countryCodeNumber = Integer.valueOf(countryCode);

    	PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		String regionCode = phoneNumberUtil.getRegionCodeForCountryCode(countryCodeNumber);
		
		return regionCode;
    }
    
    protected PhoneNumber parsePhoneNumber(String regionCode, String phoneNumber) throws JrafDomainException {
    	
    	PhoneNumber phoneNumberResult = null; 
    	
    	try {
    		
    		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    		phoneNumberResult = phoneNumberUtil.parse(phoneNumber, regionCode);
    		
		} catch (NumberParseException e) {
			
			switch(e.getErrorType()) {
			case INVALID_COUNTRY_CODE:
				throw new InvalidCountryCodeException(regionCode);
			case NOT_A_NUMBER:
			case TOO_SHORT_AFTER_IDD:
				throw new InvalidPhoneNumberException(phoneNumber);
			case TOO_LONG:
				throw new TooLongPhoneNumberException(phoneNumber);
			case TOO_SHORT_NSN:
				throw new TooShortPhoneNumberException(phoneNumber);
			default:
				throw new NormalizedPhoneNumberException("Unexpected error type: "+e.getErrorType());
			}
			
		}
		
		return phoneNumberResult;
    	
    }
    
    protected void checkPhoneNumberPossibility(PhoneNumber phoneNumberResult, String countryCode, String phoneNumber) throws JrafDomainException {
    	
    	PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    	ValidationResult validationResult = phoneNumberUtil.isPossibleNumberWithReason(phoneNumberResult);
		
		switch(validationResult) {
		case IS_POSSIBLE:
			return;
		case INVALID_COUNTRY_CODE:
			throw new InvalidCountryCodeException(countryCode);
		case TOO_LONG:
			throw new TooLongPhoneNumberException(SicStringUtils.maskingPCIDSS(phoneNumber));
		case TOO_SHORT:
			throw new TooShortPhoneNumberException(SicStringUtils.maskingPCIDSS(phoneNumber));
		default:
			throw new NormalizedPhoneNumberException("Unexpected validation: "+validationResult);
		}
    	
    }
    
    /**
     * This method is aimed to check if a normalized phone number is consistent with provided region code
     * 
     * <p>
     *	If provided REGION CODE has the same country code than resulted REGION CODE -> no error <br/>
     *	Example : US and CA have the same country code 1 <br/>
     * </p>
     * 
     * @param phoneNumberResult
     * @param regionCode
     * @param phoneNumber
     * @throws JrafDomainException
     */
    protected void checkPhoneNumberValidity(PhoneNumber phoneNumberResult, String regionCode, String phoneNumber) throws JrafDomainException {
    	
    	PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    	boolean isNumberValid = false;
    	
    	if(1l == variablesDS.getEnv("NEW_RULES_PHONE_VALIDITY", 0l)) {
    		// DQCRM-6
    		// if there is in an entry called NEW_RULES_PHONE_VALIDITY set to 1 in the table 'ENV_VAR' 
        	// new rules : isValidNumber instead of isValidNumberForRegion
    		isNumberValid = phoneNumberUtil.isValidNumber(phoneNumberResult);
        	if(!isNumberValid) {
    			throw new InvalidPhoneNumberException(phoneNumber);
    		}

    	} else {
    		// else, old version with isValidNumberForRegion
    		isNumberValid = phoneNumberUtil.isValidNumberForRegion(phoneNumberResult, regionCode);
        	if(!isNumberValid && !isSameCountryCode(phoneNumberResult, regionCode)) {
    			throw new InvalidPhoneNumberException(phoneNumber);
    		}

    	}    	
    }
    
    /**
     * This method is aimed to check if provided region code has the same country code than the normalized phone
     * 
     * <p>
     * Example:
     * <ul>
     * 	<li>provided region code = US -> country code = 1</li>
     * 	<li>resulted region code = CA -> country code = 1</li>
     * </ul>
     * </p>
     */
    protected boolean isSameCountryCode(PhoneNumber phoneNumberResult, String regionCode) {
    
    	PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    	int countryCodeInput = phoneNumberUtil.getCountryCodeForRegion(regionCode);
		int countryCodeResult = phoneNumberResult.getCountryCode();
				
    	return (countryCodeInput==countryCodeResult);
    }
    
    /*PROTECTED REGION END*/
}
