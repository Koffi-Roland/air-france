package com.airfrance.repind.service.agency.searchagencyonmulticriteria.ds;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.RequestDTO;
import org.springframework.stereotype.Service;

/**
 * Centralizes SearchAgencyOnMultiCriteria conditions (for flow control) Used
 * by: - CheckMandatoryDS - SearchFirmDS - BuildQueryDS
 * 
 * @author t950700
 *
 */

@Service
public class BuildConditionsDS {

	/* =============================================== */
	/* IDENTITY CONDITIONS */
	/* =============================================== */


	public static boolean isNameStrictConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (isNameConditionSet(requestDTO))
				&& (requestDTO.getIdentity().getNameSearchType() != null)
				&& (requestDTO.getIdentity().getNameSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getNameSearchType()))
				&& (requestDTO.getIdentity().getNameSearchType().equalsIgnoreCase("S"))) {
			result = true;
		}

		return result;
	}

	public static boolean isNameLikeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (isNameConditionSet(requestDTO))
				&& (requestDTO.getIdentity().getNameSearchType() != null)
				&& (requestDTO.getIdentity().getNameSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getNameSearchType()))
				&& (requestDTO.getIdentity().getNameSearchType().equalsIgnoreCase("L"))) {
			result = true;
		}

		return result;
	}

	public static boolean isNameConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && ((requestDTO.getIdentity() != null) && (requestDTO.getIdentity().getName() != null)
				&& (requestDTO.getIdentity().getName().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentity().getName())))) {
			result = true;
		}

		return result;
	}

	public static boolean isNameTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getIdentity() != null)
				&& (requestDTO.getIdentity().getNameType() != null)
				&& (requestDTO.getIdentity().getNameType().replace(" ", "").length() > 0)) {
			result = true;
		}

		return result;
	}
	
	public static boolean isNameTypeConditionIsUsual(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && isNameTypeConditionSet(requestDTO)
				&& (requestDTO.getIdentity().getNameType().equals("U"))) {
			result = true;
		}

		return result;
	}

	/* =============================================== */
	/* CONTACTS CONDITIONS */
	/* =============================================== */

	public static boolean isPhoneConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPhoneBloc() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getPhoneNumber()))) {
			result = true;
		}

		return result;
	}

	/**
	 * Shared normalized Phone condition set
	 * 
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isNormalizedPhoneConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPhoneBloc() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getPhoneNumber().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getPhoneNumber()))
				&& (requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getNormalizedPhoneNumber()))) {
			result = true;
		}

		return result;
	}

	public static boolean isPhoneCountryConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPhoneBloc() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getCountry() != null)
				&& (requestDTO.getContacts().getPhoneBloc().getCountry().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPhoneBloc().getCountry()))) {
			result = true;
		}

		return result;
	}

	public static boolean isEmailConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getEmailBloc() != null)
				&& (requestDTO.getContacts().getEmailBloc().getEmail() != null)
				&& (requestDTO.getContacts().getEmailBloc().getEmail().replace(" ", "").length() > 0)
				&& (Utils.isEmailCompliant(requestDTO.getContacts().getEmailBloc().getEmail()))) {
			result = true;
		}

		return result;
	}

	public static boolean isNumberAndStreetConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getNumberAndStreet()))) {
			result = true;
		}

		return result;
	}

	public static boolean isCityStrictConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCity()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().equalsIgnoreCase("S"))) {
			result = true;
		}

		return result;
	}

	public static boolean isCityLikeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCity()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCitySearchType()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getCitySearchType().equalsIgnoreCase("L"))) {
			result = true;
		}

		return result;
	}
	
	public static boolean isCityConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCity().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCity()))) {
			result = true;
		}

		return result;
	}
	

	public static boolean isZipStrictConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipCode()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().equalsIgnoreCase("S"))) {
			result = true;
		}

		return result;
	}

	public static boolean isZipLikeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipCode()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipSearchType()))
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipSearchType().equalsIgnoreCase("L"))) {
			result = true;
		}

		return result;
	}
	
	public static boolean isZipConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getZipCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getZipCode()))) {
			result = true;
		}

		return result;
	}
	

	public static boolean isStateConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getStateCode() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getStateCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getStateCode()))) {
			result = true;
		}

		return result;
	}

	public static boolean isCountryConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getContacts() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCountryCode() != null)
				&& (requestDTO.getContacts().getPostalAddressBloc().getCountryCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getContacts().getPostalAddressBloc().getCountryCode()))) {
			result = true;
		}
		return result;
	}

	/* =============================================== */
	/* ZC CONDITIONS */
	/* =============================================== */

	public static boolean isZC1ConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc1() != null)
				&& (requestDTO.getCommercialZones().getZc1().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc1()))) {
			result = true;
		}

		return result;
	}

	public static boolean isZC2ConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc2() != null)
				&& (requestDTO.getCommercialZones().getZc2().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc2()))) {
			result = true;
		}

		return result;
	}

	public static boolean isZC3ConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc3() != null)
				&& (requestDTO.getCommercialZones().getZc3().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc3()))) {
			result = true;
		}

		return result;
	}

	public static boolean isZC4ConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc4() != null)
				&& (requestDTO.getCommercialZones().getZc4().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc4()))) {
			result = true;
		}

		return result;
	}

	public static boolean isZC5ConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getCommercialZones() != null)
				&& (requestDTO.getCommercialZones().getZc5() != null)
				&& (requestDTO.getCommercialZones().getZc5().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getCommercialZones().getZc5()))) {
			result = true;
		}

		return result;
	}

	/* =============================================== */
	/* REQUESTOR CONDITIONS */
	/* =============================================== */

	public static boolean isChannelConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getRequestor() != null)
				&& (requestDTO.getRequestor().getChannel() != null)
				&& (requestDTO.getRequestor().getChannel().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getRequestor().getChannel()))) {
			result = true;
		}

		return result;
	}

	public static boolean isApplicationCodeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getRequestor() != null)
				&& (requestDTO.getRequestor().getApplicationCode() != null)
				&& (requestDTO.getRequestor().getApplicationCode().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getRequestor().getApplicationCode()))) {
			result = true;
		}

		return result;
	}

	public static boolean isProcessTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getProcessType() != null)
				&& (requestDTO.getProcessType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getProcessType()))
				&& ((requestDTO.getProcessType().equalsIgnoreCase("M"))
						|| (requestDTO.getProcessType().equalsIgnoreCase("A"))
						|| (requestDTO.getProcessType().equalsIgnoreCase("O")))) {
			result = true;
		}

		return result;
	}

	public static boolean isProcessTypeAutoConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getProcessType() != null)
				&& (requestDTO.getProcessType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getProcessType()))
				&& (requestDTO.getProcessType().equalsIgnoreCase("A"))) {
			result = true;
		}

		return result;
	}

	public static boolean isProcessTypeManualConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getProcessType() != null)
				&& (requestDTO.getProcessType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getProcessType()))
				&& (requestDTO.getProcessType().equalsIgnoreCase("M"))) {
			result = true;
		}

		return result;
	}

	/**
	 * Process type added for having s search similar to ADH service
	 * 
	 * @param requestDTO
	 * @return
	 */
	public static boolean isProcessTypeOldConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getProcessType() != null)
				&& (requestDTO.getProcessType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getProcessType()))
				&& (requestDTO.getProcessType().equalsIgnoreCase("O"))) {
			result = true;
		}

		return result;
	}

	public static boolean isIndexConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getQueryIndex() > 0)) {
			result = true;
		}

		return result;
	}

	/* =============================================== */
	/* IDENT CONDITIONS */
	/* =============================================== */

	public static boolean isIdentTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getIdentification() != null)
				&& (requestDTO.getIdentification().getIdentificationType() != null)
				&& (requestDTO.getIdentification().getIdentificationType().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentification().getIdentificationType()))) {
			result = true;
		}

		return result;
	}

	public static boolean isIdentValueConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO != null) && (requestDTO.getIdentification() != null)
				&& (requestDTO.getIdentification().getIdentificationValue() != null)
				&& (requestDTO.getIdentification().getIdentificationValue().length() != 0)
				&& (requestDTO.getIdentification().getIdentificationValue().replace(" ", "").length() > 0)
				&& (Utils.isAlphaNumeric(requestDTO.getIdentification().getIdentificationValue()))) {
			result = true;
		}

		return result;
	}

	public static boolean isSiretConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("SI")) {
			result = true;
		}

		return result;
	}

	public static boolean isSirenConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("SR")) {
			result = true;
		}

		return result;
	}

	public static boolean isNcscConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("NC")) {
			result = true;
		}

		return result;
	}

	public static boolean isKeyNumberConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("KN")) {
			result = true;
		}

		return result;
	}

	public static boolean isGinConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((BuildConditionsDS.isIdentTypeConditionSet(requestDTO))
				&& (BuildConditionsDS.isIdentValueConditionSet(requestDTO))
				&& requestDTO.getIdentification().getIdentificationType().equalsIgnoreCase("GI")) {
			result = true;
		}

		return result;
	}

	public static boolean isAgenceTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;

		if ((requestDTO.getIdentity() != null) && (requestDTO.getIdentity().getAgenceType() != null)
				&& (Utils.isDigit(requestDTO.getIdentity().getAgenceType()))) {
			result = true;
		}

		return result;
	}
}
