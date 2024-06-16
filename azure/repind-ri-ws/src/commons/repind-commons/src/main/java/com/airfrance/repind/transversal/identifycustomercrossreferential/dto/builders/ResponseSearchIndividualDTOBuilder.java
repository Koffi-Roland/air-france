package com.airfrance.repind.transversal.identifycustomercrossreferential.dto.builders;

import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.IndividualMulticriteriaDTO;
import com.airfrance.repind.dto.individu.adh.searchindividualbymulticriteria.SearchIndividualByMulticriteriaResponseDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.adresse.enums.MediumStatusEnum;
import com.airfrance.repind.transversal.identifycustomercrossreferential.ds.BuildConditionDS;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.util.CompareContractDate;
import com.airfrance.repind.util.ConstantValues;
import com.airfrance.repind.util.SicStringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class ResponseSearchIndividualDTOBuilder {
	
	/*==========================================*/
	/*                                          */
	/*           PUBLIC METHODS                 */
	/*                                          */
	/*==========================================*/
	
	/**
	 * Builds IdentifyCustomerCrossReferential responseDTO from SearchIndividualByMultiCriteria response
	 */
	/*public ResponseDTO build(SearchIndividualByMulticriteriaResponse responseDTOSearchIndividual) {
		ResponseDTO response = new ResponseDTO();
		handleCustomers(response, responseDTOSearchIndividual);
		return response;
	}*/
	
	/**
	 * Builds IdentifyCustomerCrossReferential responseDTO from SearchIndividualByMultiCriteria response
	 */
	public ResponseDTO build(RequestDTO requestDTO, SearchIndividualByMulticriteriaResponseDTO dto) {
		ResponseDTO response = new ResponseDTO();
		handleCustomers(requestDTO, response, dto);
		return response;
	}

	/*==========================================*/
	/*                                          */
	/*            PRIVATE METHODS               */
	/*                                          */
	/*==========================================*/
	/**
	 * Handle responseDTO customers
	 * @param requestDTO
	 * @param response
	 * @param SearchIndividualByMulticriteriaResponseDTO
	 */
	public ResponseDTO handleCustomers(RequestDTO requestDTO, ResponseDTO response, SearchIndividualByMulticriteriaResponseDTO dto) {
		if (dto != null) {
			List<CustomerDTO> customersList = new LinkedList<CustomerDTO>();
			for (IndividualMulticriteriaDTO individuDTOLoop : dto.getIndividuals()) {
				Boolean isSetStatus = individuDTOLoop.getIndividu() != null
						&& individuDTOLoop.getIndividu().getStatutIndividu() != null;
				// Si le type de recherche est F et que le statut de l'individu est égale à X ou T on ne remonte pas l'individu.
				// (voir BuildConditionDS.isConditionStatusSatisfied)
				if(isSetStatus && BuildConditionDS.isConditionStatusSatisfied(requestDTO, individuDTOLoop.getIndividu().getStatutIndividu(), "T")) {
					CustomerDTO customerDTO = new CustomerDTO();
					com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO
					individualDTO = new com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO();
					handleRelevance(individualDTO, individuDTOLoop);
					handleIndividualInformations(individualDTO, individuDTOLoop);
					handleEmail(individualDTO, individuDTOLoop);
					handlePostalAddress(individualDTO, individuDTOLoop);
					handleTelecom(individualDTO, individuDTOLoop);
					handleContracts(individualDTO, individuDTOLoop);
					customerDTO.setIndividual(individualDTO);
					customersList.add(customerDTO);
				}
			}
			response.setCustomers(customersList);
		}
		return response;
	}

	/**
	 * Set responseDTO relevance
	 * @param individualDTO
	 * @param individual
	 */
	private void handleRelevance(
			com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO individualDTO,
			IndividualMulticriteriaDTO individual)
	{
		if(individual != null)
		{
			individualDTO.setRelevance(Integer.parseInt(individual.getRelevance()));
		}
		
	}

	/**
	 * Set responseDTO individualInformations
	 * @param individualDTO
	 * @param individual
	 */
	private void handleIndividualInformations(
			com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO individualDTO,
			IndividualMulticriteriaDTO individual) 
	{
		if(individual != null && individual.getIndividu() != null) {
			IndividualInformationsDTO individualInformations = new IndividualInformationsDTO();
			/*
			 * Key
			 */
			if(individual.getIndividu().getSgin() != null) 
			{
				individualInformations.setIndividualKey(individual.getIndividu().getSgin());
			}

			/*
			 * Civility
			 */
			if(individual.getIndividu().getCivilite() != null)
			{
				individualInformations.setCivility(individual.getIndividu().getCivilite());
			}

			/*
			 * Last name
			 */
			if(individual.getIndividu().getNom() != null)
			{
				individualInformations.setLastNameInternal(individual.getIndividu().getNom());
			}

			if(individual.getIndividu().getNomSC() != null)
			{
				individualInformations.setLastNameExternal(individual.getIndividu().getNomSC());
			}

			/*
			 * First name
			 */
			if(individual.getIndividu().getPrenom() != null)
			{
				individualInformations.setFirstNameInternal(individual.getIndividu().getPrenom());
			}

			if(individual.getIndividu().getPrenomSC() != null)
			{
				individualInformations.setFirstNameExternal(individual.getIndividu().getPrenomSC());
			}

			/*
			 * Birth date
			 */
			if(individual.getIndividu().getDateNaissance() != null)
			{
				individualInformations.setBirthDate(individual.getIndividu().getDateNaissance());
			}

			/*
			 * Status
			 */
			if(individual.getIndividu().getStatutIndividu() != null)
			{
				individualInformations.setCustomerStatus(individual.getIndividu().getStatutIndividu() );
			}


			/*
			 * Spoken language
			 */
			if(individual.getIndividu().getCodeLangue() != null)
			{
				individualInformations.setSpokenLanguage(individual.getIndividu().getCodeLangue());
			}
			
			/*
			 * Optin Individual
			 */
			if(individual.getIndividu().getProfilsdto() != null) {
				String optinIndividual = individual.getIndividu().getProfilsdto().getSmailing_autorise();
				individualInformations.setOptinIndividual(optinIndividual);
			}
			
			/*
			 * GIN Fusion
			 */
			if (individual.getIndividu().getGinFusion() != null && individual.getIndividu().getStatutIndividu().equals("T")) {
				individualInformations.setGinFusion(individual.getIndividu().getGinFusion());
			}

			individualDTO.setIndividualInformations(individualInformations);
		}

	}

	/**
	 * Set responseDTO Email
	 * @param individualDTO
	 * @param individual
	 */
	private void handleEmail(
			com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO individualDTO,
			IndividualMulticriteriaDTO individual) 
	{
		if(individual != null && 
				individual.getIndividu() != null
				&&	individual.getIndividu().getEmaildto() != null
				&&	!individual.getIndividu().getEmaildto().isEmpty())
		{
			List<EmailIndividualDTO> emailIndividualList = new LinkedList<EmailIndividualDTO>();

			int indexML = 0;
			for(EmailDTO email : individual.getIndividu().getEmaildto())
			{
				if(indexML < ConstantValues.INDEX_EMAIL_IND && (MediumStatusEnum.VALID.toLiteral().equals(email.getStatutMedium()) || MediumStatusEnum.TEMPORARY.toLiteral().equals(email.getStatutMedium()))){
					EmailIndividualDTO emailIndividual = new EmailIndividualDTO();

					if(email.getCodeMedium() != null)
					{
						emailIndividual.setMediumCode(email.getCodeMedium());
					}

					if(email.getAutorisationMailing() != null)
					{
						emailIndividual.setEmailOptin(email.getAutorisationMailing());
					}

					if(email.getEmail() != null)
					{
						emailIndividual.setEmail(email.getEmail());
					}

					emailIndividualList.add(emailIndividual);
					indexML++;
				}

				individualDTO.setEmailIndividual(emailIndividualList);
			}
		}
	}
	
	/**
	 * Handles responseDTO postal address
	 * @param individualDTO
	 * @param individual
	 */
	private void handlePostalAddress(
			com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO individualDTO,
			IndividualMulticriteriaDTO individual) 
	{
		if(individual != null
				&&	individual.getIndividu() != null
				&&	individual.getIndividu().getPostaladdressdto() != null
				&&	!individual.getIndividu().getPostaladdressdto().isEmpty())
		{
			List<PostalAddressIndividualDTO> postalAddressIndividualList = new LinkedList<PostalAddressIndividualDTO>();
			int index = 0;
			for(PostalAddressDTO address : individual.getIndividu().getPostaladdressdto())
			{
				if(index < ConstantValues.INDEX_POSTAL_ADDRESS_IND && MediumStatusEnum.VALID.equals(MediumStatusEnum.fromLiteral(address.getSstatut_medium()))) {
					PostalAddressIndividualDTO postalAddressIndividual = new PostalAddressIndividualDTO();

					if(address.getScode_medium() != null)
					{
						postalAddressIndividual.setMediumCode(address.getScode_medium());
					}

					if(address.getSstatut_medium() != null)
					{
						postalAddressIndividual.setMediumStatus(address.getSstatut_medium());
					}

					if(address.getSraison_sociale() != null)
					{
						postalAddressIndividual.setCorporateName(address.getSraison_sociale());
					}

					if(address.getSville() != null)
					{
						postalAddressIndividual.setCity(address.getSville());
					}

					if(address.getScomplement_adresse() != null)
					{
						postalAddressIndividual.setAdditionalInformations(address.getScomplement_adresse());
					}

					if(address.getSno_et_rue() != null)
					{
						postalAddressIndividual.setNumberAndStreet(address.getSno_et_rue());
					}

					if(address.getScode_province() != null)
					{
						postalAddressIndividual.setDistrict(address.getScode_province());
					}

					if(address.getScode_postal() != null)
					{
						postalAddressIndividual.setZipCode(address.getScode_postal());
					}

					if(address.getScode_pays() != null)
					{
						postalAddressIndividual.setCountryCode(address.getScode_pays());
					}

					if(address.getScode_province() != null)
					{
						postalAddressIndividual.setStateCode(address.getScode_province());
					}


					if(address.getUsage_mediumdto() != null && !address.getUsage_mediumdto().isEmpty()) {
						List<AddressRoleIndividualDTO> addressRoleIndividualDTOList = new ArrayList<AddressRoleIndividualDTO>();
						int indexRoles = 0;
						for(Usage_mediumDTO usageAddress : address.getUsage_mediumdto()) {
							if (indexRoles++ < ConstantValues.INDEX_ROLES_IND) {
								AddressRoleIndividualDTO addressRoleIndividualDTO = new AddressRoleIndividualDTO();
								addressRoleIndividualDTO.setRole(usageAddress.getSrole1());
								addressRoleIndividualDTO.setApplicationCode(usageAddress.getScode_application());
								addressRoleIndividualDTO.setUsageNumber(SicStringUtils.getStringFromObject(usageAddress.getInum()));
								addressRoleIndividualDTOList.add(addressRoleIndividualDTO);
							}
						}
						postalAddressIndividual.setAddressRoleIndividual(addressRoleIndividualDTOList);
					}
					postalAddressIndividualList.add(postalAddressIndividual);
					index++;
				}
			}
			individualDTO.setPostalAddressIndividual(postalAddressIndividualList);
		}
		
	}
	
	/**
	 * Set responseDTO telecom
	 * @param individualDTO
	 * @param individual
	 */
	private void handleTelecom(
			com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO individualDTO,
			IndividualMulticriteriaDTO individual)
	{
		if((individual != null)
				&&	(individual.getIndividu() != null)
				&&	(individual.getIndividu().getTelecoms() != null)
				&&	(individual.getIndividu().getTelecoms().isEmpty() == false))
		{
			List<TelecomIndividualDTO> telecomIndividualList = new LinkedList<TelecomIndividualDTO>();
			int indexTel = 0;
			for(TelecomsDTO telecom : individual.getIndividu().getTelecoms())
			{
				if(indexTel < ConstantValues.INDEX_TELECOM_IND && MediumStatusEnum.VALID.toLiteral().equals(telecom.getSstatut_medium())) {
					TelecomIndividualDTO telecomIndividual = new TelecomIndividualDTO();

					if(telecom.getScode_medium() != null)
					{
						telecomIndividual.setMediumCode(telecom.getScode_medium());
					}

					if(telecom.getSindicatif() != null)
					{
						telecomIndividual.setCountryCode(telecom.getSindicatif());
					}

					if(telecom.getSnorm_inter_phone_number() != null)
					{
						telecomIndividual.setInternationalNormalizedPhoneNumber(telecom.getSnorm_inter_phone_number());
					}

					if(telecom.getSnumero() != null)
					{
						telecomIndividual.setPhoneNumber(telecom.getSnumero());
					}

					if(telecom.getSterminal() != null)
					{
						telecomIndividual.setTerminalType(telecom.getSterminal());
					}

					telecomIndividualList.add(telecomIndividual);
					indexTel++;
				}
			}
			
			individualDTO.setTelecomIndividual(telecomIndividualList);
		}
		
	}

	private void handleContracts(
			com.airfrance.repind.transversal.identifycustomercrossreferential.dto.IndividualDTO individualDTO,
			IndividualMulticriteriaDTO individual) 
	{
		if(individual != null
				&&	individual.getIndividu() != null
				&&	individual.getIndividu().getRolecontratsdto() != null
				&&	!individual.getIndividu().getRolecontratsdto().isEmpty())
		{
			List<com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ContractIndividualDTO> contractIndividualList
				= new LinkedList<com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ContractIndividualDTO>();
			int index = 0;
			for(RoleContratsDTO contractIndividual : individual.getIndividu().getRolecontratsdto())
			{
				if(index < ConstantValues.INDEX_CONTRACT_IND && (contractIndividual.getEtat().equals("C") || contractIndividual.getEtat().equals("P"))) {
					com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ContractIndividualDTO contractIndividualDTO =
							new com.airfrance.repind.transversal.identifycustomercrossreferential.dto.ContractIndividualDTO();

					if(contractIndividual.getFamilleProduit() != null)
					{
						contractIndividualDTO.setContractFamily(contractIndividual.getFamilleProduit());
					}

					if(contractIndividual.getNumeroContrat() != null)
					{
						contractIndividualDTO.setContractNumber(contractIndividual.getNumeroContrat());
					}

					if(contractIndividual.getEtat() != null)
					{
						contractIndividualDTO.setContractStatus(contractIndividual.getEtat());
					}

					if(contractIndividual.getMemberType() != null)
					{
						contractIndividualDTO.setMemberType(contractIndividual.getMemberType());
					}

					if(contractIndividual.getSousType() != null)
					{
						contractIndividualDTO.setProductSubType(contractIndividual.getSousType());
					}

					if(contractIndividual.getTypeContrat() != null)
					{
						contractIndividualDTO.setProductType(contractIndividual.getTypeContrat());
					}

					if(contractIndividual.getTier() != null)
					{
						contractIndividualDTO.setTierLevel(contractIndividual.getTier());
					}

					if(contractIndividual.getDateDebutValidite() != null)
					{
						contractIndividualDTO.setValidityStartDate(contractIndividual.getDateDebutValidite());
					}

					if(contractIndividual.getDateFinValidite() != null)
					{
						contractIndividualDTO.setValidityEndDate(contractIndividual.getDateFinValidite());
					}

					contractIndividualList.add(contractIndividualDTO);
					index++;
				}
			}
			Collections.sort(contractIndividualList, new CompareContractDate());
			individualDTO.setContractIndividual(contractIndividualList);
		}
		
	}

}
