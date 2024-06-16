package com.afklm.sicwscustomeridentification.requestsbuilders;

import com.afklm.soa.stubs.w001345.v1.BusinessException;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.request.IdentifyCustomerCrossReferentialRequest;
import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.BusinessError;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


/**
 * Builds a RequestDTO instance from IdentifyCustomerCrossReferentialRequest
 * @author t950700
 *
 */
@Service("requestDTOBuilderIdentify")
public class RequestDTOBuilder {

	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	public RequestDTO build(IdentifyCustomerCrossReferentialRequest request) throws BusinessException
	{
		RequestDTO requestDTO = new RequestDTO();
		
		if(request != null)
		{
			/*
			 * Index
			 */
			if(request.getIndex() != null)
			{
				requestDTO.setIndex(request.getIndex());
			}	
			
			/*
			 * Process type
			 */
			if(request.getProcessType() != null)
			{
				requestDTO.setProcessType(request.getProcessType());
			}

			/*
			 * Context
			 */
			if(request.getContext() != null)
			{
				ContextDTO context = new ContextDTO();
				if(request.getContext().getTypeOfSearch() != null)
				{
					context.setTypeOfSearch(request.getContext().getTypeOfSearch());
				}
				if(request.getContext().getResponseType() != null)
				{
					context.setResponseType(request.getContext().getResponseType());
				}
				/*
				 * Firm type
				 */
				if(request.getContext().getTypeOfFirm() != null)
				{
					context.setTypeOfFirm(request.getContext().getTypeOfFirm());
				}
				if(request.getContext().getRequestor() != null)
				{
					RequestorDTO requestor = new RequestorDTO();
					if(request.getContext().getRequestor().getApplicationCode() != null)
					{
						requestor.setApplicationCode(request.getContext().getRequestor().getApplicationCode());
					}
					if(request.getContext().getRequestor().getChannel() != null)
					{
						requestor.setChannel(request.getContext().getRequestor().getChannel());
					}
					if(request.getContext().getRequestor().getContext() != null)
					{
						requestor.setContext(request.getContext().getRequestor().getContext());
					}
					if(request.getContext().getRequestor().getIpAddress() != null)
					{
						requestor.setIpAddress(request.getContext().getRequestor().getIpAddress());
					}
					if(request.getContext().getRequestor().getManagingCompany() != null)
					{
						requestor.setManagingCompany(request.getContext().getRequestor().getManagingCompany());
					}
					if(request.getContext().getRequestor().getMatricule() != null)
					{
						requestor.setMatricule(request.getContext().getRequestor().getMatricule());
					}
					if(request.getContext().getRequestor().getOfficeId() != null)
					{
						requestor.setOfficeId(request.getContext().getRequestor().getOfficeId());
					}
					if(request.getContext().getRequestor().getScope() != null)
					{
						requestor.setScope(request.getContext().getRequestor().getScope());
					}
					if(request.getContext().getRequestor().getSignature() != null)
					{
						requestor.setSignature(request.getContext().getRequestor().getSignature());
					}
					if(request.getContext().getRequestor().getSite() != null)
					{
						requestor.setSite(request.getContext().getRequestor().getSite());
					}
					if(request.getContext().getRequestor().getToken() != null)
					{
						requestor.setToken(request.getContext().getRequestor().getToken());
					}
					context.setRequestor(requestor);
				}
				requestDTO.setContext(context);
			}
			
			/*
			 * Provide Identifier
			 */
			if(request.getProvideIdentifier() != null)
			{
				ProvideIdentifierDTO provideIdentifier = new ProvideIdentifierDTO();
				
				if(request.getProvideIdentifier().getCustomerGin() != null)
				{
					provideIdentifier.setCustomerGin(request.getProvideIdentifier().getCustomerGin());
				}
				
				if (StringUtils.isNotBlank(request.getProvideIdentifier().getIdentificationType())) {
				    
                                    String type = request.getProvideIdentifier().getIdentificationType().trim();
				    if (IdentificationTypeEnum.fromLiteral(type) == null) {
                
                                        BusinessError error = new BusinessError();              
                                        error.setErrorCode("932");
                                        error.setFaultDescription("INVALID PARAMETER|IDENTIFICATION TYPE");
                                        String faultstring = String.format("%s|%s", error.getErrorCode(), error.getFaultDescription());
                                        throw new BusinessException(faultstring, error);
                                    } else {
                
                                        provideIdentifier.setIdentifierType(type);
                                    }				    
				}
				
				if(request.getProvideIdentifier().getIdentificationValue() != null)
				{
					provideIdentifier.setIdentifierValue(request.getProvideIdentifier().getIdentificationValue());
				}
				
				requestDTO.setProvideIdentifier(provideIdentifier);
			}
			
			/*
			 * Search Identifier
			 */
			if(request.getSearchIdentifier() != null)
			{
				SearchIdentifierDTO searchIdentifier = new SearchIdentifierDTO();
				
				if(request.getSearchIdentifier().getEmail() != null)
				{
					EmailDTO emailDTO = new EmailDTO();
					if(request.getSearchIdentifier().getEmail().getEmail() != null)
					{
						// REPIND-1288 : Put Email in lower case because in database it is stored in lowercase
						emailDTO.setEmail(SicStringUtils.normalizeEmail(request.getSearchIdentifier().getEmail().getEmail()));
					}
					searchIdentifier.setEmail(emailDTO);
				}
				
				if(request.getSearchIdentifier().getTelecom() != null)
				{
					TelecomDTO telecomDTO = new TelecomDTO();
					if(request.getSearchIdentifier().getTelecom().getCountryCode() != null)
					{
						telecomDTO.setCountryCode(request.getSearchIdentifier().getTelecom().getCountryCode());
					}
					if(request.getSearchIdentifier().getTelecom().getPhoneNumber() != null)
					{
						telecomDTO.setPhoneNumber(request.getSearchIdentifier().getTelecom().getPhoneNumber());
					}
					
					searchIdentifier.setTelecom(telecomDTO);
				}
				
				if(request.getSearchIdentifier().getPersonsIdentity() != null)
				{
					IndividualIdentityDTO individualIdentityDTO = new IndividualIdentityDTO();
					
					if(request.getSearchIdentifier().getPersonsIdentity().getTitle() != null)
					{
						individualIdentityDTO.setTitle(request.getSearchIdentifier().getPersonsIdentity().getTitle());
					}
					if(request.getSearchIdentifier().getPersonsIdentity().getFirstName() != null)
					{
						individualIdentityDTO.setFirstName(request.getSearchIdentifier().getPersonsIdentity().getFirstName());
					}
					if(request.getSearchIdentifier().getPersonsIdentity().getFirstNameSearchType() != null)
					{
						individualIdentityDTO.setFirstNameSearchType(request.getSearchIdentifier().getPersonsIdentity().getFirstNameSearchType());
					}
					if(request.getSearchIdentifier().getPersonsIdentity().getLastName() != null)
					{
						individualIdentityDTO.setLastName(request.getSearchIdentifier().getPersonsIdentity().getLastName());
					}
					if(request.getSearchIdentifier().getPersonsIdentity().getLastNameSearchType() != null)
					{
						individualIdentityDTO.setLastNameSearchType(request.getSearchIdentifier().getPersonsIdentity().getLastNameSearchType());
					}
					if(request.getSearchIdentifier().getPersonsIdentity().getBirthDate() != null)
					{
						individualIdentityDTO.setBirthDate(request.getSearchIdentifier().getPersonsIdentity().getBirthDate());
					}
					
					searchIdentifier.setIndividualIdentity(individualIdentityDTO);
				}
				
				if(request.getSearchIdentifier().getPostalAddress() != null)
				{
					PostalAddressDTO postalAddressDTO = new PostalAddressDTO();
					
					if(request.getSearchIdentifier().getPostalAddress().getAdditionalInformations() != null)
					{
						postalAddressDTO.setAdditionalInformations(request.getSearchIdentifier().getPostalAddress().getAdditionalInformations());
					}
					
					if(request.getSearchIdentifier().getPostalAddress().getCity() != null)
					{
						postalAddressDTO.setCity(request.getSearchIdentifier().getPostalAddress().getCity());
					}
					
					if(request.getSearchIdentifier().getPostalAddress().getCitySearchType() != null)
					{
						postalAddressDTO.setCitySearchType(request.getSearchIdentifier().getPostalAddress().getCitySearchType());
					}
					
					if(request.getSearchIdentifier().getPostalAddress().getCountryCode() != null)
					{
						postalAddressDTO.setCountryCode(request.getSearchIdentifier().getPostalAddress().getCountryCode());
					}
					
					if(request.getSearchIdentifier().getPostalAddress().getNumberAndStreet() != null)
					{
						postalAddressDTO.setNumberAndStreet(request.getSearchIdentifier().getPostalAddress().getNumberAndStreet());
					}
					
					if(request.getSearchIdentifier().getPostalAddress().getZipCode() != null)
					{
						postalAddressDTO.setZipCode(request.getSearchIdentifier().getPostalAddress().getZipCode());
					}
					
					if(request.getSearchIdentifier().getPostalAddress().getZipCodeSearchType() != null)
					{
						postalAddressDTO.setZipCodeSearchType(request.getSearchIdentifier().getPostalAddress().getZipCodeSearchType());
					}
					
					searchIdentifier.setPostalAddress(postalAddressDTO);
				}

				
				requestDTO.setSearchIdentifier(searchIdentifier);
			}
		}
		
		return requestDTO;
	}
}
