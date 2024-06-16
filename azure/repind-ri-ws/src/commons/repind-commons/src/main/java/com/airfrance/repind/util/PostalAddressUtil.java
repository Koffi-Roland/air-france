package com.airfrance.repind.util;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.ws.UsageAddressDTO;

public class PostalAddressUtil {
	
	public static boolean usageAlreadyExist(UsageAddressDTO usageFromWS, UsageAddressDTO usageFromList) {
		boolean sameAppCode = false;
		boolean sameUsageNumber = false;
		boolean sameRole = false;
		
		if (usageFromWS ==  null || usageFromList == null) {
			return false;
		}
		
		if (usageFromWS.getApplicationCode() != null && usageFromList.getApplicationCode() != null) {
			sameAppCode = usageFromWS.getApplicationCode().equalsIgnoreCase(usageFromList.getApplicationCode());
		}
		if (usageFromWS.getUsageNumber() != null && usageFromList.getUsageNumber() != null) {
			sameUsageNumber = usageFromWS.getUsageNumber().equalsIgnoreCase(usageFromList.getUsageNumber());
		}
		else if (usageFromWS.getUsageNumber() == null && usageFromList.getUsageNumber() == null) {
			sameUsageNumber = true;
		}
		if (usageFromWS.getAddressRoleCode() != null && usageFromList.getAddressRoleCode() != null) {
			sameRole = usageFromWS.getAddressRoleCode().equalsIgnoreCase(usageFromList.getAddressRoleCode());
		}
		else if (usageFromWS.getAddressRoleCode() == null && usageFromList.getAddressRoleCode() == null) {
			sameRole = true;
		}
		
		return (sameAppCode && sameUsageNumber && sameRole);
	}

	/**
	 * Control postal address status and check if it's not Deleted or Historized
	 * @param status
	 * @return Boolean
	 * @throws MissingParameterException
	 */
	public static boolean isNotHistorizedStatus(PostalAddressDTO addressToCheck) throws MissingParameterException {
		boolean response = false;
		if (addressToCheck == null) {
			throw new MissingParameterException("Postal Address is null");
		}
		
		if (addressToCheck.getSstatut_medium() != null) {
			String status = addressToCheck.getSstatut_medium();
			if (status.equalsIgnoreCase(MediumStatusEnum.VALID.toString()) ||
				status.equalsIgnoreCase(MediumStatusEnum.INVALID.toString()) ||
				status.equalsIgnoreCase(MediumStatusEnum.SUSPENDED.toString()) ||
				status.equalsIgnoreCase(MediumStatusEnum.TEMPORARY.toString())) {
				
				response = true;
			}
		}
		else {
			throw new MissingParameterException("Postal Address status is null");
		}
		
		return response;
	}
	
	public static boolean sameMediumCode(PostalAddressDTO leftDTO, PostalAddressDTO rightDTO) {
		boolean result = false;
		
		if (leftDTO != null && rightDTO != null) {
			if (leftDTO.getScode_medium() != null && rightDTO.getScode_medium() != null) {
				result = leftDTO.getScode_medium().equalsIgnoreCase(rightDTO.getScode_medium());
			}
		}
		return result;
	}

	public static boolean sameAddress(PostalAddressDTO leftDTO, PostalAddressDTO rightDTO) {
		boolean result = false;
		if (leftDTO != null && rightDTO != null) {
			if (leftDTO.getScode_pays() != null && rightDTO.getScode_pays() != null) {
				result = leftDTO.getScode_pays().equalsIgnoreCase(rightDTO.getScode_pays());
			}
			else if (leftDTO.getScode_pays() == null && rightDTO.getScode_pays() == null) {
				result = true;
			}
			else {
				return false;
			}
			if (result && leftDTO.getSville() != null && rightDTO.getSville() != null) {
				result = leftDTO.getSville().equalsIgnoreCase(rightDTO.getSville());
			}
			else if (result && leftDTO.getSville() == null && rightDTO.getSville() == null) {
				result = true;
			}
			else {
				return false;
			}
			if (result && leftDTO.getScode_postal() != null && rightDTO.getScode_postal() != null) {
				result = leftDTO.getScode_postal().equalsIgnoreCase(rightDTO.getScode_postal());
			}
			else if (result && leftDTO.getScode_postal() == null && rightDTO.getScode_postal() == null) {
				result = true;
			}
			else {
				return false;
			}
			if (result && leftDTO.getSno_et_rue() != null && rightDTO.getSno_et_rue() != null) {
				result = leftDTO.getSno_et_rue().equalsIgnoreCase(rightDTO.getSno_et_rue());
			}
			else if (result && leftDTO.getSno_et_rue() == null && rightDTO.getSno_et_rue() == null) {
				result = true;
			}
			else {
				return false;
			}
		}
		
		return result;
	}
}
