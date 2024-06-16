package com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders;

import com.airfrance.repind.entity.adresse.enums.MediumStatusEnum;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.CommercialZonesDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.EmailDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.TelecomBlocDTO;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildConditionDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.util.ConstantValues;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Component
public class ResponseSearchAgencyDTOBuilder {

	/*==========================================*/
	/*                                          */
	/*           PUBLIC METHODS                 */
	/*                                          */
	/*==========================================*/
	
	
	/**
	 * Builds IdentifyCustomerCrossReferential ResponseDTO from SearchAgencyOnMultiCriteria ResponseDTO
	 * @param requestDTO 
	 * @param responseDTOSearchAgency
	 * @return
	 */
	public ResponseDTO build(
			RequestDTO requestDTO, com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.ResponseDTO responseDTOSearchAgency)
	{
		ResponseDTO response = new ResponseDTO();
		handleContinuity(response, responseDTOSearchAgency);
		handleCustomers(requestDTO, response, responseDTOSearchAgency);
		return response;
	}
	

	/*==========================================*/
	/*                                          */
	/*             PRIVATE METHODS              */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Set responseDTOSearchAgency continuity
	 */
	private void handleContinuity(
			ResponseDTO response,
			com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.ResponseDTO responseDTOSearchAgency)
	{
		if(responseDTOSearchAgency != null)
		{
			ContinuityDTO continuity = new ContinuityDTO();
			continuity.setFirstIndex(responseDTOSearchAgency.getFirstIndex());
			continuity.setLastIndex(responseDTOSearchAgency.getMaxResult());
			continuity.setTotalNumber(responseDTOSearchAgency.getTotalNumber());
			response.setContinuity(continuity);
		}

		
	}
	
	
	/**
	 * Creates CustomerDTO from SearchAgencyOnMultiCriteria response 
	 * 	and add the result to IdentifyCustomerCrossReferential response
	 * @param requestDTO 
	 */
	private void handleCustomers(
			RequestDTO requestDTO, ResponseDTO response,
			com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.ResponseDTO responseDTOSearchAgency)
	{
		if((responseDTOSearchAgency != null)
				&&	(responseDTOSearchAgency.getAgencies() != null)
				&&	(!responseDTOSearchAgency.getAgencies().isEmpty()))
		{
			List<CustomerDTO> customers = new LinkedList<CustomerDTO>();
			for(com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agency : responseDTOSearchAgency.getAgencies())
			{
				if(agency.getAgencyInformationsDTO() != null
						&& BuildConditionDS.isConditionStatusSatisfied(requestDTO, agency.getAgencyInformationsDTO().getAgencyStatus(), "R"))
				{
					CustomerDTO customer = new CustomerDTO();
					handleAgency(customer, agency);
					customers.add(customer);	
				}
			}
			response.setCustomers(customers);
		}
		
	}

	
	/**
	 * Crates AgencyDTO instance and associate it to the CustomerDTO instance
	 * @param customer
	 * @param firmDTO
	 */
	private void handleAgency(CustomerDTO customer, com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agencyDTO)
	{
		AgencyDTO agency = new AgencyDTO();
		handleRelevance(agencyDTO, agency);
		handleAgencyInformations(agencyDTO, agency);
		handlePostalAddress(agencyDTO, agency);
		handleEmail(agencyDTO, agency);
		handleTelecom(agencyDTO, agency);
		handleCommercialZones(agencyDTO, agency);
		customer.setAgency(agency);	
	}


	/**
	 * Set agency relevance
	 * @param corporate
	 * @param firm
	 */
	private void handleRelevance(com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agencyDTO, AgencyDTO agency)
	{
		agency.setRelevance(agencyDTO.getRelevance());
	}

	
	/**
	 * Set agencyInformations
	 * @param corporate
	 * @param firm
	 */
	private void handleAgencyInformations(com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agencyDTO, AgencyDTO agency)
	{
		if(agencyDTO.getAgencyInformationsDTO() != null)
		{
			AgencyInformationsDTO agencyInformations = new AgencyInformationsDTO();
			
			/*
			 * Agency key
			 */
			if(agencyDTO.getAgencyInformationsDTO().getAgencyKey() != null)
			{
				agencyInformations.setAgencyKey(agencyDTO.getAgencyInformationsDTO().getAgencyKey());
			}
			
			/*
			 * Legal name
			 */
			if(agencyDTO.getAgencyInformationsDTO().getAgencyName() != null)
			{
				agencyInformations.setLegalName(agencyDTO.getAgencyInformationsDTO().getAgencyName());
			}

			/*
			 * Usual name
			 */
			if(agencyDTO.getAgencyInformationsDTO().getAgencyUsualName() != null)
			{
				agencyInformations.setUsualName(agencyDTO.getAgencyInformationsDTO().getAgencyUsualName());
			}
			
			/*
			 * Agency status
			 */
			if(agencyDTO.getAgencyInformationsDTO().getAgencyStatus() != null)
			{
				agencyInformations.setStatus(agencyDTO.getAgencyInformationsDTO().getAgencyStatus());
			}
			
			/*
			 * Type agreement
			 */
			if(agencyDTO.getAgencyInformationsDTO().getTypeAgreement() != null)
			{
				agencyInformations.setTypeAgreementNumber(agencyDTO.getAgencyInformationsDTO().getTypeAgreement());
			}
			
			/*
			 * Agreement Number
			 */
			if(agencyDTO.getAgencyInformationsDTO().getAgreementNumber() != null)
			{
				agencyInformations.setAgreementNumber(agencyDTO.getAgencyInformationsDTO().getAgreementNumber());
			}
			agency.setAgencyInformations(agencyInformations);
		}
	}


	/**
	 * Set agency postal address
	 * @param corporate
	 * @param firm
	 */
	private void handlePostalAddress(com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agencyDTO, AgencyDTO agency)
	{
		if((agencyDTO.getAgencyInformationsDTO() != null)
				&& (agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc() != null))
		{
			PostalAddressAgencyDTO postalAddressAgency = new PostalAddressAgencyDTO();
			
			if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO() != null)
			{
				/*
				 * Additional informations
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getComplementSends() != null)
				{
					postalAddressAgency.setAdditionalInformations(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getComplementSends());
				}
				
				/*
				 * City
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getCity() != null)
				{
					postalAddressAgency.setCity(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getCity());
				}
				
				/*
				 * Corporate name
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getBusinessName() != null)
				{
					postalAddressAgency.setCorporateName(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getBusinessName());
				}
				
				/*
				 * Country code
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getCountryCode() != null)
				{
					postalAddressAgency.setCountryCode(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getCountryCode());
				}
				
				/*
				 * District
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getSaidPlace() != null)
				{
					postalAddressAgency.setDistrict(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getSaidPlace());
				}
				
				/*
				 * Number and street
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getStreetNumber() != null)
				{
					postalAddressAgency.setNumberAndStreet(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getStreetNumber());
				}
				
				/*
				 * State code
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getProvinceCode() != null)
				{
					postalAddressAgency.setStateCode(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getProvinceCode());
				}
				
				/*
				 * Zip code
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getZipCode() != null)
				{
					postalAddressAgency.setZipCode(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressContentDTO().getZipCode());
				}
				
			}
			
			if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressPropertiesDTO() != null)
			{
				/*
				 * Medium code
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressPropertiesDTO().getMediumCode() != null)
				{
					postalAddressAgency.setMediumCode(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressPropertiesDTO().getMediumCode());
				}
				
				/*
				 * Medium status
				 */
				if(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressPropertiesDTO().getMediumStatus() != null)
				{
					postalAddressAgency.setMediumStatus(agencyDTO.getAgencyInformationsDTO().getPostalAddressBloc().getPostalAddressPropertiesDTO().getMediumStatus());
				}
			}
				
			agency.setPostalAddressAgency(postalAddressAgency);
		}
		
	}
	
	
	/**
	 * Set Agency email
	 * @param corporate
	 * @param firm
	 */
	private void handleEmail(com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agencyDTO, AgencyDTO agency)
	{
		if((agencyDTO.getEmailDTO() != null)
				&& (! agencyDTO.getEmailDTO().isEmpty()))
		{
			List<EmailAgencyDTO> listEmailAgency = new LinkedList<EmailAgencyDTO>();
			int index = 0;
			for(EmailDTO emailAgencyDTO : agencyDTO.getEmailDTO())
			{
				if(index < ConstantValues.INDEX_EMAIL && MediumStatusEnum.VALID.toLiteral().equals(emailAgencyDTO.getMediumStatus())) {
					EmailAgencyDTO emailAgency = new EmailAgencyDTO();

					/*
					 * Email address
					 */
					if(emailAgencyDTO.getEmail() != null)
					{
						emailAgency.setEmail(emailAgencyDTO.getEmail());
					}

					/*
					 * Email medium code
					 */
					if(emailAgencyDTO.getMediumCode() != null)
					{
						emailAgency.setMediumCode(emailAgencyDTO.getMediumCode());
					}
					
					/*
					 * Email optin
					 */
					if(emailAgencyDTO.getMailingAuthorized() != null)
					{
						emailAgency.setOptin(emailAgencyDTO.getMailingAuthorized());
					}
					listEmailAgency.add(emailAgency);
					index++;
				}
			}
			agency.setEmailAgency(listEmailAgency);
		}
		
	}


	/**
	 * Set Corporate telecom
	 * @param corporate
	 * @param firm
	 */
	private void handleTelecom(com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agencyDTO, AgencyDTO agency)
	{
		
		if((agencyDTO.getTelecomBlocDTO() != null)
				&& (! agencyDTO.getTelecomBlocDTO().isEmpty()))
		{
			List<TelecomAgencyDTO> listTelecomAgency = new LinkedList<TelecomAgencyDTO>();
			int index = 0;
			for(TelecomBlocDTO telecomAgencyDTO : agencyDTO.getTelecomBlocDTO())
			{			
				if(index < ConstantValues.INDEX_TELECOM && telecomAgencyDTO.getTelecomDTO() != null && telecomAgencyDTO.getTelecomDTO().getMediumStatus() != null
						&& telecomAgencyDTO.getTelecomDTO().getMediumStatus().equals(MediumStatusEnum.VALID.toLiteral())) {
					TelecomAgencyDTO telecomAgency = new TelecomAgencyDTO();
					/*
					 * Country code
					 */
					if(telecomAgencyDTO.getTelecomDTO().getCountryCodeNum() != null)
					{
						telecomAgency.setCountryCode(telecomAgencyDTO.getTelecomDTO().getCountryCodeNum());

					}
					/*
					 * Terminal type
					 */
					if(telecomAgencyDTO.getTelecomDTO().getTerminalType() != null)
					{
						telecomAgency.setTerminalType(telecomAgencyDTO.getTelecomDTO().getTerminalType());
					}

					/*
					 * Medium code
					 */
					if(telecomAgencyDTO.getTelecomDTO().getMediumCode() != null) {
						telecomAgency.setMediumCode(telecomAgencyDTO.getTelecomDTO().getMediumCode());
					}

					if(telecomAgencyDTO.getTelecomStandardizationDTO() != null) {
						/*
						 * Phone number
						 */
						if(telecomAgencyDTO.getTelecomStandardizationDTO().getPhoneNumber() != null)
						{
							telecomAgency.setPhoneNumber(telecomAgencyDTO.getTelecomStandardizationDTO().getPhoneNumber());
						}

						/*
						 * Normalized phone number
						 */
						if(telecomAgencyDTO.getTelecomStandardizationDTO().getNormPhoneNumber() != null)
						{
							telecomAgency.setInternationalNormalizedPhoneNumber(telecomAgencyDTO.getTelecomStandardizationDTO().getNormPhoneNumber());
						}
					}
					listTelecomAgency.add(telecomAgency);
					index++;
				}
			}
			agency.setTelecomAgency(listTelecomAgency);
		}
		
	}
	
	
	/**
	 * Set agency commercial zones
	 * @param corporate
	 * @param firm
	 */
	private void handleCommercialZones(com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO agencyDTO, AgencyDTO agency)
	{
		if(agencyDTO.getCommercialZonesAgencyDTO() != null)
		{
			List<CommercialZonesAgencyDTO> commercialZonesAgencyDTOList = new ArrayList<CommercialZonesAgencyDTO>();
			int index = 0;
			for(CommercialZonesDTO zc : agencyDTO.getCommercialZonesAgencyDTO()) {
				if(index < ConstantValues.INDEX_COMMERCIAL_ZONE_AGENCY) {
					CommercialZonesAgencyDTO commercialZonesAgencyDTO = new CommercialZonesAgencyDTO();
					if(zc.getZoneSubtype() != null && !zc.getZoneSubtype().isEmpty()) {
						commercialZonesAgencyDTO.setZoneSubtype(zc.getZoneSubtype());
					}
					if(zc.getNatureZone() != null && !zc.getNatureZone().isEmpty()) {
						commercialZonesAgencyDTO.setNatureZone(zc.getNatureZone());
					}
					if(zc.getPrivilegedLink() != null && !zc.getPrivilegedLink().isEmpty()) {
						commercialZonesAgencyDTO.setPrivilegedLink(zc.getPrivilegedLink());
					}
					if(zc.getLinkStartDate() != null) {
						commercialZonesAgencyDTO.setLinkStartDate(zc.getLinkStartDate());
					}
					if(zc.getLinkEndDate() != null) {
						commercialZonesAgencyDTO.setLinkEndDate(zc.getLinkEndDate());
					}
					if(zc.getZcStartDate() != null) {
						commercialZonesAgencyDTO.setZcStartDate(zc.getZcStartDate());
					}
					if(zc.getZcEndDate() != null) {
						commercialZonesAgencyDTO.setZcEndDate(zc.getZcEndDate());
					}
					if(zc.getZc1() != null && !zc.getZc1().isEmpty()) {
						commercialZonesAgencyDTO.setZc1(zc.getZc1());
					}
					if(zc.getZc2() != null && !zc.getZc2().isEmpty()) {
						commercialZonesAgencyDTO.setZc2(zc.getZc2());
					}
					if(zc.getZc3() != null && !zc.getZc3().isEmpty()) {
						commercialZonesAgencyDTO.setZc3(zc.getZc3());
					}
					if(zc.getZc4() != null && !zc.getZc4().isEmpty()) {
						commercialZonesAgencyDTO.setZc4(zc.getZc4());
					}
					if(zc.getZc5() != null && !zc.getZc5().isEmpty()) {
						commercialZonesAgencyDTO.setZc5(zc.getZc5());
					}
					commercialZonesAgencyDTOList.add(commercialZonesAgencyDTO);
					index++;
				}
			}
			agency.setCommercialZonesAgency(commercialZonesAgencyDTOList);
		}
		
	}
}
