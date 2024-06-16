package com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders;


import com.airfrance.repind.entity.adresse.enums.MediumStatusEnum;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.EmailDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.TelecomDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildConditionDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.util.ConstantValues;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ResponseSearchFirmDTOBuilder {

	
	/*==========================================*/
	/*                                          */
	/*           PUBLIC METHODS                 */
	/*                                          */
	/*==========================================*/
	
	
	/**
	 * Builds IdentifyCustomerCrossReferential ResponseDTO from SearchFirmOnMultiCriteria ResponseDTO
	 * @param requestDTO 
	 * @param responseDTOSearchFirm
	 * @return
	 */
	public ResponseDTO build(
			RequestDTO requestDTO, com.airfrance.repind.firme.searchfirmonmulticriteria.dto.ResponseDTO responseDTOSearchFirm)
	{
		ResponseDTO response = new ResponseDTO();
		handleContinuity(response, responseDTOSearchFirm);
		handleCustomers(requestDTO, response, responseDTOSearchFirm);
		return response;
	}
	

	/*==========================================*/
	/*                                          */
	/*             PRIVATE METHODS              */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Set responseDTOSearchFirm continuity
	 */
	private void handleContinuity(
			ResponseDTO response,
			com.airfrance.repind.firme.searchfirmonmulticriteria.dto.ResponseDTO responseDTOSearchFirm)
	{
		if(responseDTOSearchFirm != null)
		{
			ContinuityDTO continuity = new ContinuityDTO();
			continuity.setFirstIndex(responseDTOSearchFirm.getFirstIndex());
			continuity.setLastIndex(responseDTOSearchFirm.getMaxResult());
			continuity.setTotalNumber(responseDTOSearchFirm.getTotalNumber());
			response.setContinuity(continuity);
		}

		
	}
	
	
	/**
	 * Creates CustomerDTO from SearchFirmOnMultiCriteria response 
	 * 	and add the result to IdentifyCustomerCrossReferential response
	 * @param requestDTO 
	 */
	private void handleCustomers(
			RequestDTO requestDTO, ResponseDTO response,
			com.airfrance.repind.firme.searchfirmonmulticriteria.dto.ResponseDTO responseDTOSearchFirm)
	{
		if((responseDTOSearchFirm != null)
				&&	(responseDTOSearchFirm.getFirms() != null)
				&&	(!responseDTOSearchFirm.getFirms().isEmpty()))
		{
			List<CustomerDTO> customers = new LinkedList<CustomerDTO>();
			for(com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmDTO firm : responseDTOSearchFirm.getFirms())
			{
				if(firm.getFirmInformations() != null
						&& BuildConditionDS.isConditionStatusSatisfied(requestDTO, firm.getFirmInformations().getFirmStatus(), "FU"))
				{
					CustomerDTO customer = new CustomerDTO();
					handleCorporate(customer, firm);
					customers.add(customer);
				}
			}
			response.setCustomers(customers);
		}
		
	}

	
	/**
	 * Crates CorporateDTO instance and associate it to the CustomerDTO instance
	 * @param customer
	 * @param firmDTO
	 */
	private void handleCorporate(CustomerDTO customer, FirmDTO firm) 
	{
		CorporateDTO corporate = new CorporateDTO();
		handleRelevance(corporate, firm);
		handleCorporateInformations(corporate, firm);
		handlePostalAddress(corporate, firm);
		handleEmail(corporate, firm);
		handleTelecom(corporate, firm);
		handleCommercialZones(corporate, firm);
		customer.setCorporate(corporate);
	}


	/**
	 * Set corporate relevance
	 * @param corporate
	 * @param firm
	 */
	private void handleRelevance(CorporateDTO corporate, FirmDTO firm) {
		corporate.setRelevance(firm.getRelevance());
	}

	
	/**
	 * Set corporateInformations
	 * @param corporate
	 * @param firm
	 */
	private void handleCorporateInformations(CorporateDTO corporate, FirmDTO firm) 
	{
		if(firm.getFirmInformations() != null)
		{
			CorporateInformationsDTO corporateInformations = new CorporateInformationsDTO();
			
			/*
			 * Corporate key
			 */
			if(firm.getFirmInformations().getFirmKey() != null)
			{
				corporateInformations.setCorporateKey(firm.getFirmInformations().getFirmKey());
			}
			
			/*
			 * Legal name
			 */
			if((firm.getFirmInformations().getFirmName() != null)
					&&	(firm.getFirmInformations().getFirmName().getName() != null))
			{
				corporateInformations.setLegalName(firm.getFirmInformations().getFirmName().getName());
			}
			
			/*
			 * Siret number
			 */
			if(firm.getFirmInformations().getSiretNumber() != null)
			{
				corporateInformations.setSiretNumber(firm.getFirmInformations().getSiretNumber());
			}
			
			/*
			 * Usual name
			 */
			if(firm.getFirmInformations().getUsualName() != null)
			{
				corporateInformations.setUsualName(firm.getFirmInformations().getUsualName());
			}
			
			/*
			 * Status
			 */
			if(firm.getFirmInformations().getFirmStatus() != null)
			{
				corporateInformations.setStatus(firm.getFirmInformations().getFirmStatus());
			}
			
			/*
			 * Type
			 */
			if(firm.getFirmInformations().getFirmType() != null)
			{
				corporateInformations.setType(firm.getFirmInformations().getFirmType());
			}
			
			corporate.setCorporateInformations(corporateInformations);	
		}
	}


	/**
	 * Set corporate postal address
	 * @param corporate
	 * @param firm
	 */
	private void handlePostalAddress(CorporateDTO corporate, FirmDTO firm) {
		if((firm.getFirmInformations() != null)
				&& (firm.getFirmInformations().getPostalAddressBloc() != null))
		{
			PostalAddressCorporateDTO postalAddressCorporate = new PostalAddressCorporateDTO();
			if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent() != null)
			{
				/*
				 * Additional informations
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getComplementSends() != null)
				{
					postalAddressCorporate.setAdditionalInformations(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getComplementSends());
				}
				
				/*
				 * City
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getCity() != null)
				{
					postalAddressCorporate.setCity(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getCity());
				}
				
				/*
				 * Corporate name
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getBusinessName() != null)
				{
					postalAddressCorporate.setCorporateName(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getBusinessName());
				}
				
				/*
				 * Country code
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getCountryCode() != null)
				{
					postalAddressCorporate.setCountryCode(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getCountryCode());
				}
				
				/*
				 * District
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getSaidPlace() != null)
				{
					postalAddressCorporate.setDistrict(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getSaidPlace());
				}
				
				/*
				 * Number and street
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getStreetNumber() != null)
				{
					postalAddressCorporate.setNumberAndStreet(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getStreetNumber());
				}
				
				/*
				 * State code
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getProvinceCode() != null)
				{
					postalAddressCorporate.setStateCode(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getProvinceCode());
				}
				
				/*
				 * Zip code
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getZipCode() != null)
				{
					postalAddressCorporate.setZipCode(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressContent().getZipCode());
				}
				
			}
			
			if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressProperties() != null)
			{
				/*
				 * Medium code
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressProperties().getMediumCode() != null)
				{
					postalAddressCorporate.setMediumCode(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressProperties().getMediumCode());
				}
				
				/*
				 * Medium status
				 */
				if(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressProperties().getMediumStatus() != null)
				{
					postalAddressCorporate.setMediumStatus(firm.getFirmInformations().getPostalAddressBloc().getPostalAddressProperties().getMediumStatus());
				}
			}
				
			corporate.setPostalAddressCorporate(postalAddressCorporate);
		}
		
	}
	
	
	/**
	 * Set Corporate email
	 * @param corporate
	 * @param firm
	 */
	private void handleEmail(CorporateDTO corporate, FirmDTO firm) {
		if((firm.getEmail() != null)
				&& (! firm.getEmail().isEmpty()))
		{
			List<EmailCorporateDTO> listEmailCorporate = new LinkedList<EmailCorporateDTO>();
			int index = 0;
			for(EmailDTO emailFirm : firm.getEmail())
			{
				if(index < ConstantValues.INDEX_EMAIL && MediumStatusEnum.VALID.toLiteral().equals(emailFirm.getMediumStatus())) {
					EmailCorporateDTO emailCorporate = new EmailCorporateDTO();

					/*
					 * Email address
					 */
					if(emailFirm.getEmail() != null)
					{
						emailCorporate.setEmail(emailFirm.getEmail());
					}

					/*
					 * Email medium code
					 */
					if(emailFirm.getMediumCode() != null)
					{
						emailCorporate.setMediumCode(emailFirm.getMediumCode());
					}
					
					/*
					 * Email optin
					 */
					if(emailFirm.getMailingAuthorized() != null)
					{
						emailCorporate.setOptin(emailFirm.getMailingAuthorized());
					}
					listEmailCorporate.add(emailCorporate);
					index++;
				}
			}
			corporate.setEmailCorporate(listEmailCorporate);
		}
		
	}


	/**
	 * Set Corporate telecom
	 * @param corporate
	 * @param firm
	 */
	private void handleTelecom(CorporateDTO corporate, FirmDTO firm) {
		
		if((firm.getTelecoms() != null)
				&& (! firm.getTelecoms().isEmpty()))
		{
			List<TelecomCorporateDTO> listTelecomCorporate = new LinkedList<TelecomCorporateDTO>();
			int index = 0;
			for(TelecomDTO telecomFirm : firm.getTelecoms())
			{
				if(index < ConstantValues.INDEX_TELECOM) {
					TelecomCorporateDTO telecomCorporate = new TelecomCorporateDTO();
					/*
					 * Country code
					 */
					if(telecomFirm.getCountryCode() != null)
					{
						telecomCorporate.setCountryCode(telecomFirm.getCountryCode());
					}

					/*
					 * Phone number
					 */
					if(telecomFirm.getUnchangedPhoneNumber() != null)
					{
						telecomCorporate.setPhoneNumber(telecomFirm.getUnchangedPhoneNumber());
					}

					/*
					 * Terminal type
					 */
					if(telecomFirm.getTerminalType() != null)
					{
						telecomCorporate.setTerminalType(telecomFirm.getTerminalType());
					}

					/*
					 * Normalized phone number
					 */
					if(telecomFirm.getInterNormPhoneNumber() != null)
					{
						telecomCorporate.setInternationalNormalizedPhoneNumber(telecomFirm.getInterNormPhoneNumber());
					}

					/*
					 * Medium code
					 */
					if(telecomFirm.getMediumCode() != null)
					{
						telecomCorporate.setMediumCode(telecomFirm.getMediumCode());
					}

					listTelecomCorporate.add(telecomCorporate);
					index++;
				}
			}
			corporate.setTelecomCorporate(listTelecomCorporate);
		}
		
	}
	
	
	/**
	 * Set Corporate commercial zones
	 * @param corporate
	 * @param firm
	 */
	private void handleCommercialZones(CorporateDTO corporate, FirmDTO firm) {
		
		if(firm.getCommercialZonesCorporate() != null)
		{
			CommercialZonesCorporateDTO commercialZonesCorporateDTO = new CommercialZonesCorporateDTO();
			
			CommercialZonesCorporateDTO zc = firm.getCommercialZonesCorporate();
			if(zc.getZoneSubtype() != null && !zc.getZoneSubtype().isEmpty()) {
				commercialZonesCorporateDTO.setZoneSubtype(zc.getZoneSubtype());
			}
			if(zc.getNatureZone() != null && !zc.getNatureZone().isEmpty()) {
				commercialZonesCorporateDTO.setNatureZone(zc.getNatureZone());
			}
			if(zc.getZc1() != null && !zc.getZc1().isEmpty()) {
				commercialZonesCorporateDTO.setZc1(zc.getZc1());
			}
			if(zc.getZc2() != null && !zc.getZc2().isEmpty()) {
				commercialZonesCorporateDTO.setZc2(zc.getZc2());
			}
			if(zc.getZc3() != null && !zc.getZc3().isEmpty()) {
				commercialZonesCorporateDTO.setZc3(zc.getZc3());
			}
			if(zc.getZc4() != null && !zc.getZc4().isEmpty()) {
				commercialZonesCorporateDTO.setZc4(zc.getZc4());
			}
			if(zc.getZc5() != null && !zc.getZc5().isEmpty()) {
				commercialZonesCorporateDTO.setZc5(zc.getZc5());
			}
			
			corporate.setCommercialZonesCorporate(commercialZonesCorporateDTO);
		}
			
	}
}
