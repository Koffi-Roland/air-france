package com.afklm.sicwscustomeridentification.requestsbuilders;


import com.afklm.soa.stubs.w001345.v1.com.afklm.soa.stubs.w001345.v1.com.afklm.sicwscustomeridentificationuml.v1.response.*;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Builds IdentifyCustomerCrossReferentialResponse instance from ResponseDTO
 * @author t950700
 *
 */
@Service
public class ResponseBuilder {

	/*==========================================*/
	/*                                          */
	/*             PUBLIC METHODS               */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Builds IdentifyCustomerCrossReferentialResponse instance from ResponseDTO
	 * @param requestDTO 
	 */
	public IdentifyCustomerCrossReferentialResponse build(RequestDTO requestDTO, ResponseDTO responseDTO)
	{
		IdentifyCustomerCrossReferentialResponse response = new IdentifyCustomerCrossReferentialResponse();
		handleContinuity(responseDTO, response);
		handleCustomers(responseDTO, response, requestDTO);
		return response;
	}

	
	/*==========================================*/
	/*                                          */
	/*             PRIVATE METHODS              */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Set IdentifyCustomerCrossReferentialResponse continuity
	 * @param responseDTO
	 * @param response
	 */
	private void handleContinuity(ResponseDTO responseDTO,
			IdentifyCustomerCrossReferentialResponse response) {
		if(responseDTO != null)
		{
			if(responseDTO.getContinuity() != null)
			{
				Continuity continuity = new Continuity();
				continuity.setFirstIndex(responseDTO.getContinuity().getFirstIndex());
				continuity.setLastIndex(responseDTO.getContinuity().getLastIndex());
				continuity.setTotalNumber(responseDTO.getContinuity().getTotalNumber());
				response.setContinuity(continuity);
			}
		}
	}
	
	
	/**
	 * Set IdentifyCustomerCrossReferentialResponse customers
	 * @param responseDTO
	 * @param response
	 * @param requestDTO 
	 */
	private void handleCustomers(ResponseDTO responseDTO, IdentifyCustomerCrossReferentialResponse response, RequestDTO requestDTO)
	{
		if(responseDTO != null && (responseDTO.getCustomers() != null)
				&&	(! responseDTO.getCustomers().isEmpty()))
		{
			for(CustomerDTO customerDTO : responseDTO.getCustomers())
			{
				Customer customer = new Customer();
				handleCorporate(customerDTO, customer, requestDTO);
				handleAgency(customerDTO, customer, requestDTO);
				handleIndividual(customerDTO, customer, requestDTO);
				response.getCustomer().add(customer);
			}
		}
	}


	


	/**
	 * Set Customer corporate data
	 * @param customerDTO
	 * @param customer
	 * @param requestDTO 
	 */
	private void handleCorporate(CustomerDTO customerDTO, Customer customer, RequestDTO requestDTO) 
	{
		if(customerDTO.getCorporate() != null)
		{
			Corporate corporate = new Corporate();
			
			/*
			 * Relevance
			 */
			corporate.setRelevance(customerDTO.getCorporate().getRelevance());
			
			/*
			 * Corporate informations
			 */
			if(customerDTO.getCorporate().getCorporateInformations() != null)
			{
				CorporateInformations corporateInformations = new CorporateInformations();
				
				if(customerDTO.getCorporate().getCorporateInformations().getCorporateKey() != null)
				{
					corporateInformations.setCorporateKey(customerDTO.getCorporate().getCorporateInformations().getCorporateKey());
				}
				if(customerDTO.getCorporate().getCorporateInformations().getLegalName() != null)
				{
					corporateInformations.setLegalName(customerDTO.getCorporate().getCorporateInformations().getLegalName());
				}
				if(customerDTO.getCorporate().getCorporateInformations().getSiretNumber() != null)
				{
					corporateInformations.setSiretNumber(customerDTO.getCorporate().getCorporateInformations().getSiretNumber());
				}
				if(customerDTO.getCorporate().getCorporateInformations().getUsualName() != null)
				{
					corporateInformations.setUsualName(customerDTO.getCorporate().getCorporateInformations().getUsualName());
				}
				if(customerDTO.getCorporate().getCorporateInformations().getStatus() != null)
				{
					corporateInformations.setStatus(customerDTO.getCorporate().getCorporateInformations().getStatus());
				}
				if(customerDTO.getCorporate().getCorporateInformations().getType() != null)
				{
					corporateInformations.setType(customerDTO.getCorporate().getCorporateInformations().getType());
				}
				
				corporate.setCorporateInformations(corporateInformations);
			}
			
			/*
			 * Postal address corporate
			 */
			if(customerDTO.getCorporate().getPostalAddressCorporate() != null)
			{
				PostalAddressCorporate postalAddressCorporate = new PostalAddressCorporate();
				
				if(customerDTO.getCorporate().getPostalAddressCorporate().getAdditionalInformations() != null)
				{
					postalAddressCorporate.setAdditionalInformations(customerDTO.getCorporate().getPostalAddressCorporate().getAdditionalInformations());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getCity() != null)
				{
					postalAddressCorporate.setCity(customerDTO.getCorporate().getPostalAddressCorporate().getCity());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getCorporateName() != null)
				{
					postalAddressCorporate.setCorporateName(customerDTO.getCorporate().getPostalAddressCorporate().getCorporateName());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getCountryCode() != null)
				{
					postalAddressCorporate.setCountryCode(customerDTO.getCorporate().getPostalAddressCorporate().getCountryCode());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getDistrict() != null)
				{
					postalAddressCorporate.setDistrict(customerDTO.getCorporate().getPostalAddressCorporate().getDistrict());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getMediumCode() != null)
				{
					postalAddressCorporate.setMediumCode(customerDTO.getCorporate().getPostalAddressCorporate().getMediumCode());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getMediumStatus() != null)
				{
					postalAddressCorporate.setMediumStatus(customerDTO.getCorporate().getPostalAddressCorporate().getMediumStatus());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getNumberAndStreet() != null)
				{
					postalAddressCorporate.setNumberAndStreet(customerDTO.getCorporate().getPostalAddressCorporate().getNumberAndStreet());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getStateCode() != null)
				{
					postalAddressCorporate.setStateCode(customerDTO.getCorporate().getPostalAddressCorporate().getStateCode());
				}
				if(customerDTO.getCorporate().getPostalAddressCorporate().getZipCode() != null)
				{
					postalAddressCorporate.setZipCode(customerDTO.getCorporate().getPostalAddressCorporate().getZipCode());
				}
				
				corporate.getPostalAddressCorporate().add(postalAddressCorporate);
			}
			
			/*
			 * Email address corporate
			 */
			if((customerDTO.getCorporate().getEmailCorporate() != null)
					&&	(!customerDTO.getCorporate().getEmailCorporate().isEmpty()))
			{
				for(EmailCorporateDTO emailCorporateDTO : customerDTO.getCorporate().getEmailCorporate())
				{
					EmailCorporate emailCorporate = new EmailCorporate();
					if(emailCorporateDTO.getEmail() != null)
					{
						emailCorporate.setEmail(emailCorporateDTO.getEmail());
					}
					if(emailCorporateDTO.getMediumCode() != null)
					{
						emailCorporate.setMediumCode(emailCorporateDTO.getMediumCode());
					}
					if(emailCorporateDTO.getOptin() != null)
					{
						emailCorporate.setOptin(emailCorporateDTO.getOptin());
					}
					corporate.getEmailCorporate().add(emailCorporate);
				}
			}
			
			/*
			 * Telecom corporate
			 */
			if((customerDTO.getCorporate().getTelecomCorporate() != null)
					&&	(!customerDTO.getCorporate().getTelecomCorporate().isEmpty()))
			{
				for(TelecomCorporateDTO telecomCorporateDTO : customerDTO.getCorporate().getTelecomCorporate())
				{
					TelecomCorporate telecomCorporate = new TelecomCorporate();
					
					if(telecomCorporateDTO.getCountryCode() != null)
					{
						telecomCorporate.setCountryCode(telecomCorporateDTO.getCountryCode());
					}
					if(telecomCorporateDTO.getMediumCode() != null)
					{
						telecomCorporate.setMediumCode(telecomCorporateDTO.getMediumCode());
					}
					if(telecomCorporateDTO.getInternationalNormalizedPhoneNumber() != null)
					{
						telecomCorporate.setInternationalNormalizedPhoneNumber(telecomCorporateDTO.getInternationalNormalizedPhoneNumber());
					}
					if(telecomCorporateDTO.getPhoneNumber() != null)
					{
						telecomCorporate.setPhoneNumber(telecomCorporateDTO.getPhoneNumber());
					}
					if(telecomCorporateDTO.getTerminalType() != null)
					{
						telecomCorporate.setTerminalType(telecomCorporateDTO.getTerminalType());
					}
					
					corporate.getTelecomCorporate().add(telecomCorporate);
				}
			}
			
			/*
			 * Commercial Zones corporate
			 */
			if(customerDTO.getCorporate().getCommercialZonesCorporate() != null) {
				CommercialZonesCorporate commercialZonesCorporate = new CommercialZonesCorporate();
				if(customerDTO.getCorporate().getCommercialZonesCorporate().getZoneSubtype() != null)
				{
					commercialZonesCorporate.setZoneSubtype(customerDTO.getCorporate().getCommercialZonesCorporate().getZoneSubtype());
				}
				if(customerDTO.getCorporate().getCommercialZonesCorporate().getNatureZone() != null)
				{
					commercialZonesCorporate.setNatureZone(customerDTO.getCorporate().getCommercialZonesCorporate().getNatureZone());
				}
				if(customerDTO.getCorporate().getCommercialZonesCorporate().getZc1() != null)
				{
					commercialZonesCorporate.setZc1(customerDTO.getCorporate().getCommercialZonesCorporate().getZc1());
				}
				if(customerDTO.getCorporate().getCommercialZonesCorporate().getZc2() != null)
				{
					commercialZonesCorporate.setZc2(customerDTO.getCorporate().getCommercialZonesCorporate().getZc2());
				}
				if(customerDTO.getCorporate().getCommercialZonesCorporate().getZc3() != null)
				{
					commercialZonesCorporate.setZc3(customerDTO.getCorporate().getCommercialZonesCorporate().getZc3());
				}
				if(customerDTO.getCorporate().getCommercialZonesCorporate().getZc4() != null)
				{
					commercialZonesCorporate.setZc4(customerDTO.getCorporate().getCommercialZonesCorporate().getZc4());
				}
				if(customerDTO.getCorporate().getCommercialZonesCorporate().getZc5() != null)
				{
					commercialZonesCorporate.setZc5(customerDTO.getCorporate().getCommercialZonesCorporate().getZc5());
				}
				corporate.setCommercialZonesCorporate(commercialZonesCorporate);
			}
			
			/*
			 * Member corporate
			 */
			if((customerDTO.getCorporate().getMemberCorporate() != null) 
					&&	(!customerDTO.getCorporate().getMemberCorporate().isEmpty())){
				
				for(MemberCorporateDTO memberCorporateDTO : customerDTO.getCorporate().getMemberCorporate())
				{
					MemberCorporate memberCorporate = new MemberCorporate();
					
					if(memberCorporateDTO.getJobTitle() != null)
					{
						memberCorporate.setJobTitle(memberCorporateDTO.getJobTitle());
					}
					
					if(memberCorporateDTO.getEmailCorporateMember() != null && !memberCorporateDTO.getEmailCorporateMember().isEmpty())
					{
						for(EmailCorporateMemberDTO emailCorporateMemberDTO : memberCorporateDTO.getEmailCorporateMember() ){
							EmailCorporateMember emailCorporateMember = new EmailCorporateMember();
							emailCorporateMember.setEmail(emailCorporateMemberDTO.getEmail()); 
							emailCorporateMember.setMediumCode(emailCorporateMemberDTO.getMediumCode());
							memberCorporate.getEmailCorporateMember().add(emailCorporateMember);
						}
					}
					
					if(memberCorporateDTO.getTelecomCorporateMember() != null && !memberCorporateDTO.getTelecomCorporateMember().isEmpty())
					{
						for(TelecomCorporateMemberDTO telecomCorporateMemberDTO : memberCorporateDTO.getTelecomCorporateMember() ){
							TelecomCorporateMember telecomCorporateMember= new TelecomCorporateMember();
							telecomCorporateMember.setCountryCode(telecomCorporateMemberDTO.getCountryCode()); 
							telecomCorporateMember.setMediumCode(telecomCorporateMemberDTO.getMediumCode());
							telecomCorporateMember.setInternationalNormalizedPhoneNumber(telecomCorporateMemberDTO.getInternationalNormalizedPhoneNumber());
							telecomCorporateMember.setPhoneNumber(telecomCorporateMemberDTO.getPhoneNumber());
							telecomCorporateMember.setTerminalType(telecomCorporateMemberDTO.getTerminalType());
							memberCorporate.getTelecomCorporateMember().add(telecomCorporateMember);
						}
					}
					corporate.getMemberCorporate().add(memberCorporate);
				}
			}
			
			customer.setCorporate(corporate);
		}
		
	}
	
	
	/**
	 * Set Customer agency data
	 * @param customerDTO
	 * @param customer
	 * @param requestDTO
	 */
	private void handleAgency(CustomerDTO customerDTO, Customer customer, RequestDTO requestDTO)
	{
		if(customerDTO.getAgency() != null)
		{

			Agency agency = new Agency();
			/*
			 * Relevance
			 */
			agency.setRelevance(customerDTO.getAgency().getRelevance());

			List<MemberAgencyDTO> memberAgencyDTOList = customerDTO.getAgency().getMemberAgency();
			if(memberAgencyDTOList != null && !memberAgencyDTOList.isEmpty())
			{
				for(MemberAgencyDTO memberAgencyDTO : memberAgencyDTOList) {
					MemberAgency menberAgency = new MemberAgency();
					if(memberAgencyDTO.getJobTitle() != null){
						menberAgency.setJobTitle(memberAgencyDTO.getJobTitle());
					}
					List<EmailAgencyMemberDTO> emailAgencyMemberDTOList = memberAgencyDTO.getEmailAgencyMember();
					if(emailAgencyMemberDTOList != null && !emailAgencyMemberDTOList.isEmpty()) {
						for(EmailAgencyMemberDTO emailAgencyMemberDTO : emailAgencyMemberDTOList) {
							EmailAgencyMember emailAgencyMember = new EmailAgencyMember();
							if(emailAgencyMemberDTO.getEmail() != null) {
								emailAgencyMember.setEmail(emailAgencyMemberDTO.getEmail());
							}
							if(emailAgencyMemberDTO.getMediumCode() != null) {
								emailAgencyMember.setMediumCode(emailAgencyMemberDTO.getMediumCode());
							}
							menberAgency.getEmailAgencyMember().add(emailAgencyMember);
						}
					}
					List<TelecomAgencyMemberDTO> telecomAgencyMemberDTOList = memberAgencyDTO.getTelecomAgencyMember();
					if(telecomAgencyMemberDTOList != null && !telecomAgencyMemberDTOList.isEmpty()) {
						for(TelecomAgencyMemberDTO telecomAgencyMemberDTO : telecomAgencyMemberDTOList) {
							TelecomAgencyMember telecomAgencyMember = new TelecomAgencyMember();
							if(telecomAgencyMemberDTO.getCountryCode() != null) {
								telecomAgencyMember.setCountryCode(telecomAgencyMemberDTO.getCountryCode());
							}
							if(telecomAgencyMemberDTO.getMediumCode() != null) {
								telecomAgencyMember.setMediumCode(telecomAgencyMemberDTO.getMediumCode());
							}
							if(telecomAgencyMemberDTO.getInternationalNormalizedPhoneNumber() != null) {
								telecomAgencyMember.setInternationalNormalizedPhoneNumber(telecomAgencyMemberDTO.getInternationalNormalizedPhoneNumber());
							}
							if(telecomAgencyMemberDTO.getPhoneNumber() != null) {
								telecomAgencyMember.setPhoneNumber(telecomAgencyMemberDTO.getPhoneNumber());
							}
							if(telecomAgencyMemberDTO.getTerminalType() != null) {
								telecomAgencyMember.setTerminalType(telecomAgencyMemberDTO.getTerminalType());
							}
							menberAgency.getTelecomAgencyMember().add(telecomAgencyMember);
						}
					}
					agency.getMemberAgency().add(menberAgency);
				}
			}

			/*
			 * Agency informations
			 */
			if(customerDTO.getAgency().getAgencyInformations() != null)
			{
				AgencyInformations agencyInformations = new AgencyInformations();

				if(customerDTO.getAgency().getAgencyInformations().getAgencyKey() != null)
				{
					agencyInformations.setAgencyKey(customerDTO.getAgency().getAgencyInformations().getAgencyKey());
				}
				if(customerDTO.getAgency().getAgencyInformations().getLegalName() != null)
				{
					agencyInformations.setLegalName(customerDTO.getAgency().getAgencyInformations().getLegalName());
				}
				if(customerDTO.getAgency().getAgencyInformations().getUsualName() != null)
				{
					agencyInformations.setUsualName(customerDTO.getAgency().getAgencyInformations().getUsualName());
				}
				if(customerDTO.getAgency().getAgencyInformations().getTypeAgreementNumber() != null)
				{
					agencyInformations.setTypeAgreementNumber(customerDTO.getAgency().getAgencyInformations().getTypeAgreementNumber());
				}
				if(customerDTO.getAgency().getAgencyInformations().getAgreementNumber() != null)
				{
					agencyInformations.setAgreementNumber(customerDTO.getAgency().getAgencyInformations().getAgreementNumber());
				}
				if(customerDTO.getAgency().getAgencyInformations().getStatus() != null) {
					agencyInformations.setStatus(customerDTO.getAgency().getAgencyInformations().getStatus());
				}

				agency.setAgencyInformations(agencyInformations);
			}

			/*
			 * Postal address agency
			 */
			if(customerDTO.getAgency().getPostalAddressAgency() != null)
			{
				PostalAddressAgency postalAddressAgency = new PostalAddressAgency();

				if(customerDTO.getAgency().getPostalAddressAgency().getAdditionalInformations() != null)
				{
					postalAddressAgency.setAdditionalInformations(customerDTO.getAgency().getPostalAddressAgency().getAdditionalInformations());
				}
				if(customerDTO.getAgency().getPostalAddressAgency().getCity() != null)
				{
					postalAddressAgency.setCity(customerDTO.getAgency().getPostalAddressAgency().getCity());
				}
				if(customerDTO.getAgency().getPostalAddressAgency().getCountryCode() != null)
				{
					postalAddressAgency.setCountryCode(customerDTO.getAgency().getPostalAddressAgency().getCountryCode());
				}
				if(customerDTO.getAgency().getPostalAddressAgency().getDistrict() != null)
				{
					postalAddressAgency.setDistrict(customerDTO.getAgency().getPostalAddressAgency().getDistrict());
				}
				if(customerDTO.getAgency().getPostalAddressAgency().getNumberAndStreet() != null)
				{
					postalAddressAgency.setNumberAndStreet(customerDTO.getAgency().getPostalAddressAgency().getNumberAndStreet());
				}
				if(customerDTO.getAgency().getPostalAddressAgency().getStateCode() != null)
				{
					postalAddressAgency.setStateCode(customerDTO.getAgency().getPostalAddressAgency().getStateCode());
				}
				if(customerDTO.getAgency().getPostalAddressAgency().getZipCode() != null)
				{
					postalAddressAgency.setZipCode(customerDTO.getAgency().getPostalAddressAgency().getZipCode());
				}

				agency.getPostalAddressAgency().add(postalAddressAgency);
			}

			/*
			 * Email address agency
			 */
			if((customerDTO.getAgency().getEmailAgency() != null)
					&&	(!customerDTO.getAgency().getEmailAgency().isEmpty()))
			{
				for(com.airfrance.repind.transversal.identifycustomercrossreferential.dto.EmailAgencyDTO emailAgencyDTO : customerDTO.getAgency().getEmailAgency())
				{
					EmailAgency emailAgency = new EmailAgency();
					if(emailAgencyDTO.getEmail() != null)
					{
						emailAgency.setEmail(emailAgencyDTO.getEmail());
					}
					if(emailAgencyDTO.getMediumCode() != null)
					{
						emailAgency.setMediumCode(emailAgencyDTO.getMediumCode());
					}
					if(emailAgencyDTO.getOptin() != null)
					{
						emailAgency.setOptin(emailAgencyDTO.getOptin());
					}
					agency.getEmailAgency().add(emailAgency);
				}
			}

			/*
			 * Telecom agency
			 */
			if((customerDTO.getAgency().getTelecomAgency() != null)
					&&	(!customerDTO.getAgency().getTelecomAgency().isEmpty()))
			{
				for(com.airfrance.repind.transversal.identifycustomercrossreferential.dto.TelecomAgencyDTO telecomAgencyDTO : customerDTO.getAgency().getTelecomAgency())
				{
					TelecomAgency telecomAgency = new TelecomAgency();

					if(telecomAgencyDTO.getCountryCode() != null)
					{
						telecomAgency.setCountryCode(telecomAgencyDTO.getCountryCode());
					}
					if(telecomAgencyDTO.getMediumCode() != null)
					{
						telecomAgency.setMediumCode(telecomAgencyDTO.getMediumCode());
					}
					if(telecomAgencyDTO.getInternationalNormalizedPhoneNumber() != null)
					{
						telecomAgency.setInternationalNormalizedPhoneNumber(telecomAgencyDTO.getInternationalNormalizedPhoneNumber());
					}
					if(telecomAgencyDTO.getPhoneNumber() != null)
					{
						telecomAgency.setPhoneNumber(telecomAgencyDTO.getPhoneNumber());
					}
					if(telecomAgencyDTO.getTerminalType() != null)
					{
						telecomAgency.setTerminalType(telecomAgencyDTO.getTerminalType());
					}

					agency.getTelecomAgency().add(telecomAgency);
				}
			}

			/*
			 * Commercial Zones agency
			 */
			if(customerDTO.getAgency().getCommercialZonesAgency() != null && !customerDTO.getAgency().getCommercialZonesAgency().isEmpty()) {
				for(CommercialZonesAgencyDTO zc : customerDTO.getAgency().getCommercialZonesAgency()) {
					CommercialZonesAgency commercialZonesAgency = new CommercialZonesAgency();
					if(!zc.getZoneSubtype().isEmpty()) {
						commercialZonesAgency.setZoneSubtype(zc.getZoneSubtype());
					}
					if(!zc.getNatureZone().isEmpty()) {
						commercialZonesAgency.setNatureZone(zc.getNatureZone());
					}
					if(!zc.getPrivilegedLink().isEmpty()) {
						commercialZonesAgency.setPrivilegedLink(zc.getPrivilegedLink());
					}
					if(zc.getLinkStartDate() != null) {
						commercialZonesAgency.setLinkStartDate(zc.getLinkStartDate());
					}
					if(zc.getLinkEndDate() != null) {
						commercialZonesAgency.setLinkEndDate(zc.getLinkEndDate());
					}
					if(zc.getZcStartDate() != null) {
						commercialZonesAgency.setZcStartDate(zc.getZcStartDate());
					}
					if(zc.getZcEndDate() != null) {
						commercialZonesAgency.setZcEndDate(zc.getZcEndDate());
					}
					if(!zc.getZc1().isEmpty()) {
						commercialZonesAgency.setZc1(zc.getZc1());
					}
					if(!zc.getZc2().isEmpty()) {
						commercialZonesAgency.setZc2(zc.getZc2());
					}
					if(!zc.getZc3().isEmpty()) {
						commercialZonesAgency.setZc3(zc.getZc3());
					}
					if(!zc.getZc4().isEmpty()) {
						commercialZonesAgency.setZc4(zc.getZc4());
					}
					if(!zc.getZc5().isEmpty()) {
						commercialZonesAgency.setZc5(zc.getZc5());
					}
					agency.getCommercialZonesAgency().add(commercialZonesAgency);
				}
			}
			customer.setAgency(agency);
		}

	}

	
	/**
	 * Set Customer individual data
	 * @param customerDTO
	 * @param customer
	 * @param requestDTO 
	 */
	private void handleIndividual(CustomerDTO customerDTO, Customer customer, RequestDTO requestDTO) {
		if(customerDTO.getIndividual() != null)
		{
			Individual individual = new Individual();
			
			/*
			 * Relevance
			 */
			individual.setRelevance(customerDTO.getIndividual().getRelevance());
			
			/*
			 * Individual informations
			 */
			if(customerDTO.getIndividual().getIndividualInformations() != null)
			{
				IndividualInformations individualInformations = new IndividualInformations();
				
				/*
				 * Key
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getIndividualKey() != null)
				{
					individualInformations.setIndividualKey(customerDTO.getIndividual().getIndividualInformations().getIndividualKey());
				}
				
				/*
				 * Civility
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getCivility() != null)
				{
					individualInformations.setCivility(customerDTO.getIndividual().getIndividualInformations().getCivility());
				}
				
				/*
				 * Last name
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getLastNameInternal() != null)
				{
					individualInformations.setLastNameInternal(customerDTO.getIndividual().getIndividualInformations().getLastNameInternal());
				}
				
				if(customerDTO.getIndividual().getIndividualInformations().getLastNameExternal() != null)
				{
					individualInformations.setLastNameExternal(customerDTO.getIndividual().getIndividualInformations().getLastNameExternal());
				}
				
				/*
				 * First name
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getFirstNameInternal() != null)
				{
					individualInformations.setFirstNameInternal(customerDTO.getIndividual().getIndividualInformations().getFirstNameInternal());
				}
				
				if(customerDTO.getIndividual().getIndividualInformations().getFirstNameExternal() != null)
				{
					individualInformations.setFirstNameExternal(customerDTO.getIndividual().getIndividualInformations().getFirstNameExternal());
				}
				
				/*
				 * Birth date
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getBirthDate() != null)
				{
					individualInformations.setBirthDate(customerDTO.getIndividual().getIndividualInformations().getBirthDate());
				}
				
				/*
				 * Status
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getCustomerStatus() != null)
				{
					individualInformations.setCustomerStatus(customerDTO.getIndividual().getIndividualInformations().getCustomerStatus());
				}

				/*
				 * Spoken language
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getSpokenLanguage() != null)
				{
					individualInformations.setSpokenLanguage(customerDTO.getIndividual().getIndividualInformations().getSpokenLanguage());
				}
				
				/*
				 * GIN Fusion
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getGinFusion() != null)
				{
					individualInformations.setGinTarget(customerDTO.getIndividual().getIndividualInformations().getGinFusion());
				}
				
				/*
				 * Optin Individual
				 */
				if(customerDTO.getIndividual().getIndividualInformations().getOptinIndividual() != null)
				{
					individualInformations.setOptinIndividual(customerDTO.getIndividual().getIndividualInformations().getOptinIndividual());
				}
				individual.setIndividualInformations(individualInformations);
			}
			
			/*
			 * Email
			 */
			if((customerDTO.getIndividual().getEmailIndividual() != null)
					&&	(customerDTO.getIndividual().getEmailIndividual().isEmpty() == false))
			{
				for(EmailIndividualDTO emailIndividualDTO : customerDTO.getIndividual().getEmailIndividual())
				{
					EmailIndividual emailIndividual = new EmailIndividual();
					
					if(emailIndividualDTO.getMediumCode() != null)
					{
						emailIndividual.setMediumCode(emailIndividualDTO.getMediumCode());
					}
					
					if(emailIndividualDTO.getEmailOptin() != null)
					{
						emailIndividual.setEmailOptin(emailIndividualDTO.getEmailOptin());
					}
					
					if(emailIndividualDTO.getEmail() != null)
					{
						emailIndividual.setEmail(emailIndividualDTO.getEmail());
					}
					
					
					individual.getEmailIndividual().add(emailIndividual);
				}
			}
			
			/*
			 * Postal address
			 */
			if((customerDTO.getIndividual().getPostalAddressIndividual() != null)
					&&	(customerDTO.getIndividual().getPostalAddressIndividual().isEmpty() == false))
			{
				for(PostalAddressIndividualDTO postalAddressIndividualDTO : customerDTO.getIndividual().getPostalAddressIndividual())
				{
					

					PostalAddressIndividual postalAddressIndividual = new PostalAddressIndividual();
					
					if(postalAddressIndividualDTO.getMediumCode() != null)
					{
						postalAddressIndividual.setMediumCode(postalAddressIndividualDTO.getMediumCode());
					}
					
					if(postalAddressIndividualDTO.getMediumStatus() != null)
					{
						postalAddressIndividual.setMediumStatus(postalAddressIndividualDTO.getMediumStatus());
					}
					
					if(postalAddressIndividualDTO.getCorporateName() != null)
					{
						postalAddressIndividual.setCorporateName(postalAddressIndividualDTO.getCorporateName());
					}
					
					if(postalAddressIndividualDTO.getCity() != null)
					{
						postalAddressIndividual.setCity(postalAddressIndividualDTO.getCity());
					}
					
					if(postalAddressIndividualDTO.getAdditionalInformations() != null)
					{
						postalAddressIndividual.setAdditionalInformations(postalAddressIndividualDTO.getAdditionalInformations());
					}
					
					if(postalAddressIndividualDTO.getNumberAndStreet() != null)
					{
						postalAddressIndividual.setNumberAndStreet(postalAddressIndividualDTO.getNumberAndStreet());
					}
					
					if(postalAddressIndividualDTO.getDistrict() != null)
					{
						postalAddressIndividual.setDistrict(postalAddressIndividualDTO.getDistrict());
					}
					
					if(postalAddressIndividualDTO.getZipCode() != null)
					{
						postalAddressIndividual.setZipCode(postalAddressIndividualDTO.getZipCode());
					}
					
					if(postalAddressIndividualDTO.getCountryCode() != null)
					{
						postalAddressIndividual.setCountryCode(postalAddressIndividualDTO.getCountryCode());
					}
					
					if(postalAddressIndividualDTO.getStateCode() != null)
					{
						postalAddressIndividual.setStateCode(postalAddressIndividualDTO.getStateCode());
					}
					
					//TODO:enlever les commentaires lors de l'import du nouveau jar!
					if (postalAddressIndividualDTO.getAddressRoleIndividual() != null)
					{
						AddressRoleIndividual ari = null;
						for( AddressRoleIndividualDTO dto : postalAddressIndividualDTO.getAddressRoleIndividual()){
						
							ari = new AddressRoleIndividual();
							
							ari.setRole(dto.getRole());
							ari.setApplicationCode(dto.getApplicationCode());
							ari.setUsageNumber(dto.getUsageNumber());
							
							postalAddressIndividual.getAddressRoleIndividual().add(ari);
						}
					}
					
					individual.getPostalAddressIndividual().add(postalAddressIndividual);
				}
			}
			
			/*
			 * Telecom
			 */
			if((customerDTO.getIndividual().getTelecomIndividual() != null)
					&&	(customerDTO.getIndividual().getTelecomIndividual().isEmpty() == false))
			{
				for( TelecomIndividualDTO telecomIndividualDTO : customerDTO.getIndividual().getTelecomIndividual())
				{
					TelecomIndividual telecomIndividual = new TelecomIndividual();
					
					if(telecomIndividualDTO.getMediumCode() != null)
					{
						telecomIndividual.setMediumCode(telecomIndividualDTO.getMediumCode());
					}
					
					if(telecomIndividualDTO.getCountryCode() != null)
					{
						telecomIndividual.setCountryCode(telecomIndividualDTO.getCountryCode());
					}
					
					if(telecomIndividualDTO.getInternationalNormalizedPhoneNumber() != null)
					{
						telecomIndividual.setInternationalNormalizedPhoneNumber(telecomIndividualDTO.getInternationalNormalizedPhoneNumber());
					}
					
					if(telecomIndividualDTO.getPhoneNumber() != null)
					{
						telecomIndividual.setPhoneNumber(telecomIndividualDTO.getPhoneNumber());
					}
					
					if(telecomIndividualDTO.getTerminalType() != null)
					{
						telecomIndividual.setTerminalType(telecomIndividualDTO.getTerminalType());
					}
					
					individual.getTelecomIndividual().add(telecomIndividual);
				}
			}
			
			/*
			 * Contracts
			 */
			
			if((customerDTO.getIndividual().getContractIndividual() != null)
					&&	(customerDTO.getIndividual().getContractIndividual().isEmpty() == false))
			{
				for( ContractIndividualDTO contractIndividualDTO : customerDTO.getIndividual().getContractIndividual())
				{
					ContractIndividual contractIndividual = new ContractIndividual();
					
					if(contractIndividualDTO.getContractFamily() != null)
					{
						contractIndividual.setContractFamily(contractIndividualDTO.getContractFamily());
					}
					
					if(contractIndividualDTO.getContractNumber() != null)
					{
						contractIndividual.setContractNumber(contractIndividualDTO.getContractNumber());
					}
					
					if(contractIndividualDTO.getContractStatus() != null)
					{
						contractIndividual.setContractStatus(contractIndividualDTO.getContractStatus());
					}
					
					if(contractIndividualDTO.getProductSubType() != null)
					{
						contractIndividual.setProductSubType(contractIndividualDTO.getProductSubType());
					}
					
					if(contractIndividualDTO.getProductType() != null)
					{
						contractIndividual.setProductType(contractIndividualDTO.getProductType());
					}
					
					if(contractIndividualDTO.getTierLevel() != null)
					{
						contractIndividual.setTierLevel(contractIndividualDTO.getTierLevel());
					}
					
					if(contractIndividualDTO.getValidityStartDate() != null)
					{
						contractIndividual.setValidityStartDate(contractIndividualDTO.getValidityStartDate());
					}
					
					if(contractIndividualDTO.getValidityEndDate() != null)
					{
						contractIndividual.setValidityEndDate(contractIndividualDTO.getValidityEndDate());
					}
					
					if(contractIndividualDTO.getMemberType() != null)
					{
						contractIndividual.setMemberType(contractIndividualDTO.getMemberType());
					}
					individual.getContractIndividual().add(contractIndividual);
				}
			}
			
			
			customer.setIndividual(individual);
		}
	}
}
