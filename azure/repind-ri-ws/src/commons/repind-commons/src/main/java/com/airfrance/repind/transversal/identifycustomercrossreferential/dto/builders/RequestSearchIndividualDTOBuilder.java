package com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders;

import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.Utils;
import org.springframework.stereotype.Component;
@Component
public class RequestSearchIndividualDTOBuilder {

	/*==========================================*/
	/*                                          */
	/*            PUBLIC METHODS                */
	/*                                          */
	/*==========================================*/
	

	/**
	 * Builds SearchIndividualByMulticriteriaRequest instance
	 *   from a IdentifyCustomerCrossReferential requestDTO
	 * @param request
	 * @return
	 */
	public SearchIndividualByMulticriteriaRequestDTO  buildSearchIndividualByMultiCriteriaRequest(
			com.airfrance.repind.transversal.identifycustomercrossreferential.dto.RequestDTO request)
	{
		SearchIndividualByMulticriteriaRequestDTO requestIndividual = null;
		
		if(request != null)
		{
			/*
			 * Request initialization
			 */
			requestIndividual = new SearchIndividualByMulticriteriaRequestDTO();
			requestIndividual.setPopulationTargeted("A");
			
			/*
			 * Requester
			 */
			RequestorDTO requestor = new RequestorDTO();
			if((request.getContext() != null)
					&&	(request.getContext().getRequestor() != null))
			{
				if((request.getContext().getRequestor().getApplicationCode() != null)
						&&	(request.getContext().getRequestor().getApplicationCode().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getApplicationCode())))
				{
					requestor.setApplicationCode(request.getContext().getRequestor().getApplicationCode());
				}
				if((request.getContext().getRequestor().getChannel() != null)
						&&	(request.getContext().getRequestor().getChannel().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getChannel())))
				{
					requestor.setChannel(request.getContext().getRequestor().getChannel());
				}
				if((request.getContext().getRequestor().getContext() != null)
						&&	(request.getContext().getRequestor().getContext().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getContext())))
				{
					requestor.setContext(request.getContext().getRequestor().getContext());
				}
				if((request.getContext().getRequestor().getIpAddress() != null)
						&&	(request.getContext().getRequestor().getIpAddress().replace(" ", "").length() > 0))
				{
					requestor.setIpAddress(request.getContext().getRequestor().getIpAddress());
				}
				if((request.getContext().getRequestor().getManagingCompany() != null)
						&&	(request.getContext().getRequestor().getManagingCompany().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getManagingCompany())))
				{
					requestor.setManagingCompany(request.getContext().getRequestor().getManagingCompany());
				}
				if((request.getContext().getRequestor().getMatricule() != null)
						&&	(request.getContext().getRequestor().getMatricule().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getMatricule())))
				{
					requestor.setMatricule(request.getContext().getRequestor().getMatricule());
				}
				if((request.getContext().getRequestor().getOfficeId() != null)
						&&	(request.getContext().getRequestor().getOfficeId().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getOfficeId())))
				{
					requestor.setOfficeId(request.getContext().getRequestor().getOfficeId());
				}
				if((request.getContext().getRequestor().getScope() != null)
						&&	(request.getContext().getRequestor().getScope().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getScope())))
				{
					requestor.setScope(request.getContext().getRequestor().getScope());
				}
				if((request.getContext().getRequestor().getSignature() != null)
						&&	(request.getContext().getRequestor().getSignature().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getSignature())))
				{
					requestor.setSignature(request.getContext().getRequestor().getSignature());
				}
				if((request.getContext().getRequestor().getSite() != null)
						&&	(request.getContext().getRequestor().getSite().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getSite())))
				{
					requestor.setSite(request.getContext().getRequestor().getSite());
				}
				if((request.getContext().getRequestor().getToken() != null)
						&&	(request.getContext().getRequestor().getToken().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getToken())))
				{
					requestor.setToken(request.getContext().getRequestor().getToken());
				}
				if((request.getContext().getRequestor().getConsumerId() != null)
						&&	(request.getContext().getRequestor().getConsumerId().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getContext().getRequestor().getConsumerId())))
				{
					requestor.setConsumerId(request.getContext().getRequestor().getConsumerId());
				}
			}
			requestIndividual.setRequestor(requestor);
			
			/*
			 * Process type
			 */
			if((request.getProcessType() != null)
					&&	(request.getProcessType().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getProcessType())))
			{
				requestIndividual.setProcessType(request.getProcessType());
			}
			
			/*
			 * Contact
			 */
			ContactDTO contact = new ContactDTO();
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getTelecom() != null)
					&&	(request.getSearchIdentifier().getTelecom().getCountryCode() != null)
					&&	(request.getSearchIdentifier().getTelecom().getCountryCode().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getTelecom().getCountryCode())))
			{
				contact.setCountryCode(request.getSearchIdentifier().getTelecom().getCountryCode());
			}
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getTelecom() != null)
					&&	(request.getSearchIdentifier().getTelecom().getPhoneNumber() != null)
					&&	(request.getSearchIdentifier().getTelecom().getPhoneNumber().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getTelecom().getPhoneNumber())))
			{
				contact.setPhoneNumber(request.getSearchIdentifier().getTelecom().getPhoneNumber());
			}
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getEmail() != null)
					&&	(request.getSearchIdentifier().getEmail().getEmail() != null)
					&&	(request.getSearchIdentifier().getEmail().getEmail().replace(" ", "").length() > 0)
					&&	(Utils.isEmailCompliant(request.getSearchIdentifier().getEmail().getEmail())))
			{
				contact.setEmail(request.getSearchIdentifier().getEmail().getEmail());
			}
			
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getPostalAddress() != null))
			{
				PostalAddressBlocDTO postalAddressBloc = new PostalAddressBlocDTO();
				
				if((request.getSearchIdentifier().getPostalAddress().getAdditionalInformations() != null)
						&&	(request.getSearchIdentifier().getPostalAddress().getAdditionalInformations().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getPostalAddress().getAdditionalInformations())))
				{
					postalAddressBloc.setAdditionalInformation(request.getSearchIdentifier().getPostalAddress().getAdditionalInformations());
				}
				
				if((request.getSearchIdentifier().getPostalAddress().getCity() != null)
						&&	(request.getSearchIdentifier().getPostalAddress().getCity().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getPostalAddress().getCity())))
				{
					postalAddressBloc.setCity(request.getSearchIdentifier().getPostalAddress().getCity());
				}
				
				if((request.getSearchIdentifier().getPostalAddress().getCitySearchType() != null)
						&&	(request.getSearchIdentifier().getPostalAddress().getCitySearchType().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getPostalAddress().getCitySearchType())))
				{
					postalAddressBloc.setCitySearchType(request.getSearchIdentifier().getPostalAddress().getCitySearchType());
				}
				
				if((request.getSearchIdentifier().getPostalAddress().getCountryCode() != null)
						&&	(request.getSearchIdentifier().getPostalAddress().getCountryCode().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getPostalAddress().getCountryCode())))
				{
					postalAddressBloc.setCountryCode(request.getSearchIdentifier().getPostalAddress().getCountryCode());
				}
				
				if((request.getSearchIdentifier().getPostalAddress().getNumberAndStreet() != null)
						&&	(request.getSearchIdentifier().getPostalAddress().getNumberAndStreet().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getPostalAddress().getNumberAndStreet())))
				{
					postalAddressBloc.setNumberAndStreet(request.getSearchIdentifier().getPostalAddress().getNumberAndStreet());
				}
				
				if((request.getSearchIdentifier().getPostalAddress().getZipCode() != null)
						&&	(request.getSearchIdentifier().getPostalAddress().getZipCode().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getPostalAddress().getZipCode())))
				{
					postalAddressBloc.setZipCode(request.getSearchIdentifier().getPostalAddress().getZipCode());
				}
				
				if((request.getSearchIdentifier().getPostalAddress().getZipCodeSearchType() != null)
						&&	(request.getSearchIdentifier().getPostalAddress().getZipCodeSearchType().replace(" ", "").length() > 0)
						&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getPostalAddress().getZipCodeSearchType())))
				{
					postalAddressBloc.setZipCodeSearchType(request.getSearchIdentifier().getPostalAddress().getZipCodeSearchType());
				}
				
				contact.setPostalAddressBloc(postalAddressBloc);
			}
			requestIndividual.setContact(contact);
			
			/*
			 * Indetity
			 */
			IdentityDTO identity = new IdentityDTO();
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getFirstName() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getFirstName().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getIndividualIdentity().getFirstName())))
			{
				identity.setFirstName(request.getSearchIdentifier().getIndividualIdentity().getFirstName());
			}
			
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getFirstNameSearchType() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getFirstNameSearchType().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getIndividualIdentity().getFirstNameSearchType())))
			{
				identity.setFirstNameSearchType(request.getSearchIdentifier().getIndividualIdentity().getFirstNameSearchType());
			}
			
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getLastName() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getLastName().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getIndividualIdentity().getLastName())))
			{
				identity.setLastName(request.getSearchIdentifier().getIndividualIdentity().getLastName());
			}
			
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getLastNameSearchType() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getLastNameSearchType().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getIndividualIdentity().getLastNameSearchType())))
			{
				identity.setLastNameSearchType(request.getSearchIdentifier().getIndividualIdentity().getLastNameSearchType());
			}
			
			
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getTitle() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getTitle().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getSearchIdentifier().getIndividualIdentity().getTitle())))
			{
				identity.setCivility(request.getSearchIdentifier().getIndividualIdentity().getTitle());
			}
			if((request.getSearchIdentifier() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity() != null)
					&&	(request.getSearchIdentifier().getIndividualIdentity().getBirthDate() != null))
			{
				identity.setBirthday(request.getSearchIdentifier().getIndividualIdentity().getBirthDate());
			}
			requestIndividual.setIdentity(identity);
			
			
			/*
			 * Indetification
			 */
			IdentificationDTO identification = new IdentificationDTO();
			if((request.getProvideIdentifier() != null)
					&&	(request.getProvideIdentifier().getIdentifierType() != null)
					&&	(request.getProvideIdentifier().getIdentifierValue() != null)
					&&	(request.getProvideIdentifier().getIdentifierType().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getProvideIdentifier().getIdentifierType()))
					&&	(request.getProvideIdentifier().getIdentifierValue().replace(" ", "").length() > 0)
					&&	(Utils.isAlphaNumeric(request.getProvideIdentifier().getIdentifierValue())))
			{
				identification.setIdentificationType(request.getProvideIdentifier().getIdentifierType());
				identification.setIdentificationValue(request.getProvideIdentifier().getIdentifierValue());
			}
			requestIndividual.setIdentification(identification);	
	
		}
		
		return requestIndividual;
		
	}
}
