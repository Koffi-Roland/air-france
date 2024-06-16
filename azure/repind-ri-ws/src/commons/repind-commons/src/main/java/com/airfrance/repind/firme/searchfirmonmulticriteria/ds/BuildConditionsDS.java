package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;

/**
 * Centralizes SearchFirmOnMultiCriteria conditions (for flow control)
 * Used by:
 *    - CheckMandatoryDS
 *    - SearchFirmDS
 *    - BuildQueryDS
 *    
 * @author t950700
 *
 */
public class BuildConditionsDS
{

	/*===============================================*/
	/*               IDENTITY CONDITIONS             */
	/*===============================================*/
	
	/**
	 * Shared name condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getName() != null) 
				&& (requestDTO.getIdentity().getName().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getName())))
				
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared name condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameSearchTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getNameSearchType() != null) 
				&& (requestDTO.getIdentity().getNameSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getNameSearchType())))
				
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared strict name condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameStrictConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getName() != null) 
				&& (requestDTO.getIdentity().getName().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getName()))
				&& (requestDTO.getIdentity().getNameSearchType() != null)
				&& (requestDTO.getIdentity().getNameSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getNameSearchType()))
				&& (requestDTO.getIdentity().getNameSearchType().equalsIgnoreCase("S")))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared name like condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameLikeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getName() != null) 
				&& (requestDTO.getIdentity().getName().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getName()))
				&& (requestDTO.getIdentity().getNameSearchType() != null)
				&& (requestDTO.getIdentity().getNameSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getNameSearchType()))
				&& (requestDTO.getIdentity().getNameSearchType().equalsIgnoreCase("L")))
		{
			result = true;
		}

		return result;
	}

	
	/**
	 * Shared name type condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getNameType() != null) 
				&& (requestDTO.getIdentity().getNameType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getNameType()))
				&& (
						(requestDTO.getIdentity().getNameType().equalsIgnoreCase("LEGAL"))
						||
						(requestDTO.getIdentity().getNameType().equalsIgnoreCase("TRADE"))
						||
						(requestDTO.getIdentity().getNameType().equalsIgnoreCase("USUAL"))
						||
						(requestDTO.getIdentity().getNameType().equalsIgnoreCase("ALL"))
					)
		)
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared LEGAL_NAME (name type) condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameTypeLegalConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&&	(requestDTO.getIdentity() != null) 
				&& 	(requestDTO.getIdentity().getNameType() != null) 
				&& 	(requestDTO.getIdentity().getNameType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getIdentity().getNameType()))
				&&	(requestDTO.getIdentity().getNameType().equalsIgnoreCase("LEGAL")))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared TRADE_NAME (name type) condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameTypeTradeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& 	(requestDTO.getIdentity() != null) 
				&&	(requestDTO.getIdentity().getNameType() != null) 
				&& 	(requestDTO.getIdentity().getNameType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getIdentity().getNameType()))
				&&	(requestDTO.getIdentity().getNameType().equalsIgnoreCase("TRADE")))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared USUAL_NAME (name type) condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameTypeUsualConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& 	(requestDTO.getIdentity() != null) 
				&&	(requestDTO.getIdentity().getNameType() != null) 
				&&	(requestDTO.getIdentity().getNameType().replace(" ", "").length() > 0)
				&&	(Utils.isAlphaNumeric(requestDTO.getIdentity().getNameType()))
				&&	(requestDTO.getIdentity().getNameType().equalsIgnoreCase("USUAL")))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared ALL (name type) condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNameTypeAllConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& 	(requestDTO.getIdentity() != null) 
				&&	(requestDTO.getIdentity().getNameType() != null) 
				&&	(requestDTO.getIdentity().getNameType().replace(" ", "").length() > 0)
				&&	(Utils.isAlphaNumeric(requestDTO.getIdentity().getNameType()))
				&&	(requestDTO.getIdentity().getNameType().equalsIgnoreCase("ALL")))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared firm type condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isFirmTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getFirmType() != null) 
				&& (requestDTO.getIdentity().getFirmType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getFirmType()))
				&& ((requestDTO.getIdentity().getFirmType().equalsIgnoreCase("G"))
						|| (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("E"))
						|| (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("T"))
						|| (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("S"))
						|| (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("A"))))
		{
			result = true;
		}
		
		return result;
	}
	

	/**
	 * Shared firm type condition set to GROUP
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isFirmTypeGroupsConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getFirmType() != null) 
				&& (requestDTO.getIdentity().getFirmType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getFirmType()))
				&& (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("G")))
		{
			result = true;
		}
		
		return result;
	}
	

	/**
	 * Shared firm type condition set to COMPANY
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isFirmTypeCompaniesConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getFirmType() != null) 
				&& (requestDTO.getIdentity().getFirmType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getFirmType()))
				&& (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("E")))
		{
			result = true;
		}
		
		return result;
	}
	

	/**
	 * Shared firm type condition set to FIRM
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isFirmTypeFirmsConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getFirmType() != null) 
				&& (requestDTO.getIdentity().getFirmType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getFirmType()))
				&& (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("T")))
		{
			result = true;
		}
		
		return result;
	}
	

	/**
	 * Shared firm type condition set to FIRM
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isFirmTypeServicesConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getFirmType() != null) 
				&& (requestDTO.getIdentity().getFirmType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getFirmType()))
				&& (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("S")))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared firm type condition set to ALL (FIRM/COMPANY/SERVICE/GROUP)
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isFirmTypeAllConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getIdentity() != null) 
				&& (requestDTO.getIdentity().getFirmType() != null) 
				&& (requestDTO.getIdentity().getFirmType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getFirmType()))
				&& (requestDTO.getIdentity().getFirmType().equalsIgnoreCase("A")))
		{
			result = true;
		}
		
		return result;
	}

	/*===============================================*/
	/*               CONTACTS CONDITIONS             */
	/*===============================================*/
	
	/**
	 * Shared Phone condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isPhoneConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPhoneBloc() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber() != null) 
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getPhoneNumber())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared Phone country condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isPhoneCountryConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPhoneBloc() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getCountry() != null) 
				&& (requestDTO.getContacts().getPhoneBloc().getCountry().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getCountry())))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared normalized Phone condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNormalizedPhoneConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPhoneBloc() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber() != null) 
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getPhoneNumber()))
				&& (requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber() != null) 
				&& (requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber())))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared normalized Phone country condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNormalizedPhoneCountryConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPhoneBloc() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getCountry() != null) 
				&& (requestDTO.getContacts().getPhoneBloc().getCountry().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getCountry()))
				&& (requestDTO.getContacts().getPhoneBloc().getNormalizedCountry() != null) 
				&& (requestDTO.getContacts().getPhoneBloc().getNormalizedCountry().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getNormalizedCountry())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared email condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isEmailConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getEmailBloc() != null) 
				&& (requestDTO.getContacts().getEmailBloc().getEmail() != null)
				&& (requestDTO.getContacts().getEmailBloc().getEmail().replace(" ", "").length() > 0)
				&& (Utils.isEmailCompliant(requestDTO.getContacts().getEmailBloc().getEmail())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared nbr and street condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNumberAndStreetConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared City (strict) condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isCityStrictConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCity()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType()))
				&& (!requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().equalsIgnoreCase("L")))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared City (like) condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isCityLikeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCity()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().equalsIgnoreCase("L")))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared Zip (strict) condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isZipStrictConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipCode()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType()))
				&& (!requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().equalsIgnoreCase("L")))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared Zip (like) condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isZipLikeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipCode()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().equalsIgnoreCase("L")))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared state condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isStateConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getStateCode() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getStateCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getStateCode())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared Country (address) condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isCountryConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null) 
				&& (requestDTO.getContacts() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getCountryCode() != null) 
				&& (requestDTO.getContacts().getPostalAddressBloc().getCountryCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCountryCode())))
		{
			result = true;
		}
		return result;
	}

	

	/*===============================================*/
	/*               ZC CONDITIONS                   */
	/*===============================================*/
	
	
	/**
	 * Shared ZC1 condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isZC1ConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc1() != null) 
				&& (requestDTO.getCommercialZones().getZc1().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc1())))
		{
			result = true;
		}
			
		return result;
	}

	
	/**
	 * Shared ZC2 condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isZC2ConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc2() != null) 
				&& (requestDTO.getCommercialZones().getZc2().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc2())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared ZC3 condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isZC3ConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc3() != null) 
				&& (requestDTO.getCommercialZones().getZc3().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc3())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared ZC4 condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isZC4ConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc4() != null) 
				&& (requestDTO.getCommercialZones().getZc4().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc4())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared ZC5 condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isZC5ConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc5() != null) 
				&& (requestDTO.getCommercialZones().getZc5().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc5())))
		{
			result = true;
		}
		
		return result;
	}

	
	/*===============================================*/
	/*               REQUESTOR CONDITIONS            */
	/*===============================================*/
	
	
	/**
	 * Shared Channel set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isChannelConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getRequestor() != null)
				&& (requestDTO.getRequestor().getChannel() != null) 
				&& (requestDTO.getRequestor().getChannel().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getRequestor().getChannel())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared Application code set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isApplicationCodeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getRequestor() != null)
				&& (requestDTO.getRequestor().getApplicationCode() != null) 
				&& (requestDTO.getRequestor().getApplicationCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getRequestor().getApplicationCode())))
		{
			result = true;
		}
		
		return result;
	}

	
	/**
	 * Shared Process type set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isProcessTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getProcessType() != null) 
				&& 	(requestDTO.getProcessType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getProcessType()))
				&& 	((requestDTO.getProcessType().equalsIgnoreCase("M")) || (requestDTO.getProcessType().equalsIgnoreCase("A"))))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared process type set to AUTO (A)
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isProcessTypeAutoConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getProcessType() != null) 
				&& 	(requestDTO.getProcessType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getProcessType()))
				&& 	(requestDTO.getProcessType().equalsIgnoreCase("A")))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared process type set to MANUAL (M)
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isProcessTypeManualConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getProcessType() != null) 
				&& 	(requestDTO.getProcessType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getProcessType()))
				&& 	(requestDTO.getProcessType().equalsIgnoreCase("M")))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared index set 
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isIndexConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getQueryIndex() > 0))
		{
			result = true;
		}
		
		return result;
	}
	
	/*===============================================*/
	/*               IDENT CONDITIONS                */
	/*===============================================*/
	
	
	/**
	 * Shared ident type set condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isIdentTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getIdentification() != null)
				&& (requestDTO.getIdentification().getIdentificationType() != null)
				&& (requestDTO.getIdentification().getIdentificationType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentification().getIdentificationType())))
		{
			result = true;
		}
				
		return result;
	}

	
	/**
	 * Shared ident value set condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isIdentValueConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&& (requestDTO.getIdentification() != null)
				&& (requestDTO.getIdentification().getIdentificationValue() != null)
				&& (requestDTO.getIdentification().getIdentificationValue().length() != 0)
				&& (requestDTO.getIdentification().getIdentificationValue().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentification().getIdentificationValue())))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared ident set to SIRET ("SI") condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSiretConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("SI"))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared ident set to SIREN ("SR") condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSirenConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("SR"))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared ident set to NCSC ("NC") condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNcscConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("NC"))
		{
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Shared ident set to Key Number ("KN") condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isKeyNumberConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("KN"))
		{
			result = true;
		}
		
		return result;
	}
	
	
	
	
	
	/**
	 * Shared ident set to GIN ("GI") condition
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isGinConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("GI"))
		{
			result = true;
		}
		
		return result;
	}
}
