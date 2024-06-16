package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.airfrance.repind.dto.firme.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.CustomerDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Centralizes IdentifyCustomerCrossReferential conditions (for flow control)
 * Used by:
 *    - IdentifyCustomerCrossReferentialDS
 *    - CheckMandatoryInputsDS
 *    
 * @author t950700
 *
 */
@Service
public class BuildConditionDS 
{

	/*==========================================*/
	/*                                          */
	/*                REQUESTOR                 */
	/*      INDEX & PROCESS TYPE CONDITIONS     */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Shared Requestor set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isRequestorConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null)
				&&	(requestDTO.getContext().getRequestor() != null))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared Channel set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isChannelConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null)
				&&	(requestDTO.getContext().getRequestor() != null)
				&&	(requestDTO.getContext().getRequestor().getChannel() != null)
				&&	(requestDTO.getContext().getRequestor().getChannel().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getRequestor().getChannel())))
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
				&&	(requestDTO.getContext() != null)
				&&	(requestDTO.getContext().getRequestor() != null)
				&&	(requestDTO.getContext().getRequestor().getApplicationCode() != null)
				&&	(requestDTO.getContext().getRequestor().getApplicationCode().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getRequestor().getApplicationCode())))
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
	 * Shared index set 
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isIndexConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getIndex() > 0))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared typeOfFirm condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isTypeOfFirmConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getTypeOfFirm() != null) 
				&& 	(requestDTO.getContext().getTypeOfFirm().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfFirm()))
				&& 	(
						(requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("G")) 
						||	(requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("E"))
						||	(requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("T"))
						||	(requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("S"))
					)
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared SearchType condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getTypeOfSearch() != null) 
				&& 	(requestDTO.getContext().getTypeOfSearch().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfSearch()))
				&& 	(
						(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("I")) 
						||	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("F"))
						||	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("A"))
						||	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("IF"))
						||	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("IA"))
					)
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared SearchType individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchTypeIndividualConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getTypeOfSearch() != null) 
				&& 	(requestDTO.getContext().getTypeOfSearch().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfSearch()))
				&& 	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("I")) 
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared LastName individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchLastNameIndividualConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity().getLastName() != null) 
				&& 	(requestDTO.getSearchIdentifier().getIndividualIdentity().getLastName().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getSearchIdentifier().getIndividualIdentity().getLastName())))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared LastNameSearchType individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isLastNameSearchTypeIndividualConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity().getLastNameSearchType() != null) 
				&& 	(requestDTO.getSearchIdentifier().getIndividualIdentity().getLastNameSearchType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getSearchIdentifier().getIndividualIdentity().getLastNameSearchType())))
		{
			result = true;
		}
		
		return result;
	}

	/**
	 * Shared FirstName individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchFirstNameIndividualConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity().getFirstName() != null) 
				&& 	(requestDTO.getSearchIdentifier().getIndividualIdentity().getFirstName().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getSearchIdentifier().getIndividualIdentity().getFirstName())))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared CountryCode individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchCountryCodeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getPostalAddress() != null) 
				&&	(requestDTO.getSearchIdentifier().getPostalAddress().getCountryCode() != null) 
				&& 	(requestDTO.getSearchIdentifier().getPostalAddress().getCountryCode().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getSearchIdentifier().getPostalAddress().getCountryCode())))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared Email individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isEmailConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getEmail() != null) 
				&&	(requestDTO.getSearchIdentifier().getEmail().getEmail() != null) 
				&& 	(requestDTO.getSearchIdentifier().getEmail().getEmail().replace(" ", "").length() > 0)
				&& 	(Utils.isEmailCompliant(requestDTO.getSearchIdentifier().getEmail().getEmail())))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared Phone individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isPhoneConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getTelecom() != null) 
				&&	(requestDTO.getSearchIdentifier().getTelecom().getPhoneNumber() != null) 
				&& 	(requestDTO.getSearchIdentifier().getTelecom().getPhoneNumber().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getSearchIdentifier().getTelecom().getPhoneNumber())))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared Birth date individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isBirthdateConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity() != null) 
				&&	(requestDTO.getSearchIdentifier().getIndividualIdentity().getBirthDate() != null))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared Phone individual condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isPhoneCountryCodeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null) 
				&&	(requestDTO.getSearchIdentifier().getTelecom() != null) 
				&&	(requestDTO.getSearchIdentifier().getTelecom().getCountryCode() != null) 
				&& 	(requestDTO.getSearchIdentifier().getTelecom().getCountryCode().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getSearchIdentifier().getTelecom().getCountryCode())))
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared SearchType Firm condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchTypeFirmConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getTypeOfSearch() != null) 
				&& 	(requestDTO.getContext().getTypeOfSearch().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfSearch()))
				&& 	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("F")) 
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared SearchType Agency condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchTypeAgencyConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getTypeOfSearch() != null) 
				&& 	(requestDTO.getContext().getTypeOfSearch().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfSearch()))
				&& 	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("A")) 
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared SearchType Individual/Agency condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchTypeIndividualAgencyConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getTypeOfSearch() != null) 
				&& 	(requestDTO.getContext().getTypeOfSearch().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfSearch()))
				&& 	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("IA")) 
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared SearchType Individual/Firm condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchTypeIndividualFirmConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getTypeOfSearch() != null) 
				&& 	(requestDTO.getContext().getTypeOfSearch().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfSearch()))
				&& 	(requestDTO.getContext().getTypeOfSearch().equalsIgnoreCase("IF")) 
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared ResponseType condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isResponseTypeConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getResponseType() != null) 
				&& 	(requestDTO.getContext().getResponseType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getResponseType()))
				&& 	(
						(requestDTO.getContext().getResponseType().equalsIgnoreCase("U")) 
						||	(requestDTO.getContext().getResponseType().equalsIgnoreCase("F"))
					)
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Shared ResponseType condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isResponseTypeUniqueConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getContext() != null) 
				&&	(requestDTO.getContext().getResponseType() != null) 
				&& 	(requestDTO.getContext().getResponseType().replace(" ", "").length() > 0)
				&& 	(Utils.isAlphaNumeric(requestDTO.getContext().getResponseType()))
				&& 	(requestDTO.getContext().getResponseType().equalsIgnoreCase("U"))
			)
		{
			result = true;
		}
		
		return result;
	}
	
	/*==========================================*/
	/*                                          */
	/*      SearchIdentifier CONDITIONS         */
	/*     PHONE - EMAIL - COUNTRY CODE         */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Shared SearchIdentifier condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isSearchIdentifierConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getSearchIdentifier() != null))
		{
			result = true;
		}
		
		return result;
	}
	
	/*==========================================*/
	/*                                          */
	/*     ProvideIdentifier CONDITIONS         */
	/*     PHONE - EMAIL - COUNTRY CODE         */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Shared ProvideIdentifier condition set
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isProvideIdentifierConditionSet(RequestDTO requestDTO) {
		boolean result = false;
		
		if((requestDTO != null)
				&&	(requestDTO.getProvideIdentifier() != null))
		{
			result = true;
		}
		
		return result;
	}
	
	/*==========================================*/
	/*                                          */
	/*          RESPONSE CONDITIONS             */
	/*                                          */
	/*                                          */
	/*==========================================*/
	
	
	/**
	 * Check if multiple corporates found
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean areMultipleCorporatesFound(ResponseDTO responseDTO) 
	{
		boolean result = false;
		
		int count = 0;
		
		if((responseDTO != null)
				&&	(responseDTO.getCustomers() != null)
				&&	(responseDTO.getCustomers().isEmpty() == false))
		{
			for(CustomerDTO customerDTO : responseDTO.getCustomers())
			{
				if(customerDTO.getCorporate() != null)
				{
					count += 1;
				}
				if(count > 1)
				{
					result = true;
					break;
				}
			}
			
		}
		
		return result;
	}
	
	/**
	 * Check if multiple agencies found
	 * @param requestDTO
	 * @return boolean
	 */

	
	/**
	 * Check if multiple individuals found
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean areMultipleIndividualsFound(ResponseDTO responseDTO) 
	{
		boolean result = false;
		
		int count = 0;
		
		if((responseDTO != null)
				&&	(responseDTO.getCustomers() != null)
				&&	(responseDTO.getCustomers().isEmpty() == false))
		{
			for(CustomerDTO customerDTO : responseDTO.getCustomers())
			{
				if(customerDTO.getIndividual() != null)
				{
					count += 1;
				}
				if(count > 1)
				{
					result = true;
					break;
				}
			}
			
		}
		
		return result;
	}
	
	/**
	 * Check if one (and only one) individual found
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isOnlyOneIndividualFound(ResponseDTO responseDTO) 
	{
		boolean result = false;
		
		if((responseDTO != null)
				&&	(responseDTO.getCustomers() != null)
				&&	(responseDTO.getCustomers().isEmpty() == false)
				&&	(responseDTO.getCustomers().size() == 1))
		{
			for(CustomerDTO customerDTO : responseDTO.getCustomers())
			{
				if(customerDTO.getIndividual() != null)
				{
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Check if one (and only one) corporate found
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isOnlyOneCorporateFound(ResponseDTO responseDTO) 
	{
		boolean result = false;
		
		if((responseDTO != null)
				&&	(responseDTO.getCustomers() != null)
				&&	(responseDTO.getCustomers().isEmpty() == false)
				&&	(responseDTO.getCustomers().size() == 1))
		{
			for(CustomerDTO customerDTO : responseDTO.getCustomers())
			{
				if(customerDTO.getCorporate() != null)
				{
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Check if one (and only one) agency found
	 * @param requestDTO
	 * @return boolean
	 */


	public static boolean isConditionStatusSatisfied(RequestDTO requestDTO, String status, String statusTypeCondition) {
		if(isSearchTypeIndividualFirmConditionSet(requestDTO) || isSearchTypeIndividualAgencyConditionSet(requestDTO)) {
				if(!status.equals("X") && !status.equals(statusTypeCondition)) {
					return true;
				}
				return false;
		}
		if(isResponseTypeUniqueConditionSet(requestDTO)
				|| (!isResponseTypeUniqueConditionSet(requestDTO)
					&& !status.equals("X") && !status.equals(statusTypeCondition)))
		{
			return true;
		}
		return false;
	}

	/**
	 * Check if multiple agencies found
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean areMultipleAgenciesFound(ResponseDTO responseDTO)
	{
		boolean result = false;

		int count = 0;

		if((responseDTO != null)
				&&	(responseDTO.getCustomers() != null)
				&&	(responseDTO.getCustomers().isEmpty() == false))
		{
			for(CustomerDTO customerDTO : responseDTO.getCustomers())
			{
				if(customerDTO.getAgency() != null)
				{
					count += 1;
				}
				if(count > 1)
				{
					result = true;
					break;
				}
			}

		}

		return result;
	}

	/**
	 * Check if one (and only one) agency found
	 * @param requestDTO
	 * @return boolean
	 */
	public static boolean isOnlyOneAgencyFound(ResponseDTO responseDTO)
	{
		boolean result = false;

		if((responseDTO != null)
				&&	(responseDTO.getCustomers() != null)
				&&	(responseDTO.getCustomers().isEmpty() == false)
				&&	(responseDTO.getCustomers().size() == 1))
		{
			for(CustomerDTO customerDTO : responseDTO.getCustomers())
			{
				if(customerDTO.getAgency() != null)
				{
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public static boolean isTypeOfFirmConditionSatisfied(RequestDTO requestDTO,
														 Class<? extends PersonneMoraleDTO> classType) {
		boolean result = true;
		if((requestDTO != null)
				&& (requestDTO.getContext() != null)
				&& (requestDTO.getContext().getTypeOfFirm() != null)
				&& (requestDTO.getContext().getTypeOfFirm().replace(" ", "").length() > 0
				&& (Utils.isAlphaNumeric(requestDTO.getContext().getTypeOfFirm())))) {

			if(requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("G") && !classType.equals(GroupeDTO.class)) {
				result = false;
			} else if (requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("E") && !classType.equals(EntrepriseDTO.class)) {
				result = false;
			} else if (requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("T") && !classType.equals(EtablissementDTO.class)) {
				result = false;
			} else if (requestDTO.getContext().getTypeOfFirm().equalsIgnoreCase("S") && !classType.equals(ServiceDTO.class)) {
				result = false;
			}
		}
		return result;
	}
}
