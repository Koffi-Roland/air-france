package com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildConditionDS;
import org.springframework.stereotype.Component;


@Component
public class RequestSearchAgencyDTOBuilder {

	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	
	/**
	 * Creates a requestDTO from the client's request
	 */
	public RequestDTO build(com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO request)
	{
		RequestDTO requestDTO = new RequestDTO();
		
		if(request != null)
		{
			IdentityDTO identityDTO = new IdentityDTO();
			identityDTO.setFirmType("A");			
			requestDTO.setIdentity(identityDTO);
			
			//Index
			if(BuildConditionDS.isIndexConditionSet(request))
			{
				requestDTO.setQueryIndex(request.getIndex());
			}
			
			//Process type
			if(BuildConditionDS.isProcessTypeConditionSet(request))
			{
				requestDTO.setProcessType(request.getProcessType());
			}
			
			//Requestor
			if(BuildConditionDS.isRequestorConditionSet(request))
			{
				RequestorDTO requestorDTO = buildRequestor(request.getContext().getRequestor());
				requestDTO.setRequestor(requestorDTO);
			}
			
			//Address, phone, email, ...
			if(BuildConditionDS.isSearchIdentifierConditionSet(request))
			{
				ContactsDTO contactsDTO = buildContacts(request.getSearchIdentifier());
				requestDTO.setContacts(contactsDTO);
			}
			
			//Identification (GIN, SIRET, SIREN, ...)
			if(BuildConditionDS.isProvideIdentifierConditionSet(request))
			{
				IdentificationDTO identificationDTO = buildIdentification(request.getProvideIdentifier());
				requestDTO.setIdentification(identificationDTO);
			}
			
		}

		return requestDTO;
	}
	

	/*==========================================*/
	/*                                          */
	/*             PRIVATE METHODS              */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Build a requestorDTO from a requestor
	 * @param requestor
	 * @return
	 */
	private RequestorDTO buildRequestor(com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestorDTO requestor)
	{
		RequestorDTO builtRequestor = new RequestorDTO();
		
		if(requestor.getApplicationCode() != null)
		{
			builtRequestor.setApplicationCode(requestor.getApplicationCode());
		}
		if(requestor.getChannel() != null)
		{
			builtRequestor.setChannel(requestor.getChannel());
		}
		if(requestor.getIpAddress() != null)
		{
			builtRequestor.setIpAddress(requestor.getIpAddress());
		}
		
		return builtRequestor;
	}

	
	/**
	 * Build a ContactsDTO from a Contacts' object
	 * @param requestor
	 * @return
	 */
	private ContactsDTO buildContacts(com.airfrance.repind.transversal.identifycustomercrossreferential.dto.SearchIdentifierDTO searchIdentifier)
	{
		ContactsDTO builtContacts = new ContactsDTO();
		
		if(searchIdentifier.getTelecom() != null)
		{
			PhoneBlocDTO phoneBlocDTO = new PhoneBlocDTO();
			
			if(searchIdentifier.getTelecom().getCountryCode() != null)
			{
				phoneBlocDTO.setCountry(searchIdentifier.getTelecom().getCountryCode());
			}
			if(searchIdentifier.getTelecom().getPhoneNumber() != null)
			{
				phoneBlocDTO.setPhoneNumber(searchIdentifier.getTelecom().getPhoneNumber());
			}
			builtContacts.setPhoneBloc(phoneBlocDTO);
		}
		
		if(searchIdentifier.getEmail() != null)
		{
			EmailBlocDTO emailBlocDTO = new EmailBlocDTO();
			if(searchIdentifier.getEmail().getEmail() != null)
			{
				emailBlocDTO.setEmail(searchIdentifier.getEmail().getEmail());
			}
			builtContacts.setEmailBloc(emailBlocDTO);
		}
		
		return builtContacts;
	}
	
	
	/**
	 * Build a identificationDTO from an identification object
	 * @param requestor
	 * @return
	 */
	private IdentificationDTO buildIdentification(com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ProvideIdentifierDTO identification)
	{
		IdentificationDTO builtIdentification = new IdentificationDTO();
		
		if(identification.getIdentifierType() != null)
		{
			builtIdentification.setIdentificationType(identification.getIdentifierType());
		}
		if(identification.getIdentifierValue() != null)
		{
			builtIdentification.setIdentificationValue(identification.getIdentifierValue());
		}
		
		return builtIdentification;
	}
}
