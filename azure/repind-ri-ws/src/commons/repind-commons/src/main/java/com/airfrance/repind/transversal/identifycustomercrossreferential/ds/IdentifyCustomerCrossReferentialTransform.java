package com.airfrance.repind.transversal.identifycustomercrossreferential.ds;

import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.firme.*;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.zone.PmZoneDTO;
import com.airfrance.repind.dto.zone.ZoneCommDTO;
import com.airfrance.repind.entity.adresse.enums.MediumCodeEnum;
import com.airfrance.repind.entity.adresse.enums.MediumStatusEnum;
import com.airfrance.repind.transversal.identifycustomercrossreferential.dto.*;
import com.airfrance.repind.util.CompareContractDate;
import com.airfrance.repind.util.ConstantValues;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class IdentifyCustomerCrossReferentialTransform {

	/**
	 * LOGGER
	 */
	private static Log LOGGER  = LogFactory.getLog(IdentifyCustomerCrossReferentialTransform.class);

	/**
	 * Private constructor
	 */
	private IdentifyCustomerCrossReferentialTransform(){
		
	}
	
	public static ResponseDTO individuDtoToResponseDto(RequestDTO requestDTO, IndividuDTO individuDTO, List<PostalAddressIndividualDTO> postalAddressIndividualList, List<TelecomsDTO> telecoms, List<EmailDTO> emaildto, List<RoleContratsDTO> roleContrats) throws BusinessExceptionDTO{
		if(!BuildConditionDS.isConditionStatusSatisfied(requestDTO, individuDTO.getStatutIndividu(), "T")) {
			AbstractDS.throwBusinessException(AbstractDS.getNoIndividualsReturnCode(), AbstractDS.getNoIndividualsReturnMessage());
		}
		ResponseDTO responseDTO = new ResponseDTO();
		//INDIVIDUAL INFORMATIONS
		CustomerDTO customerDTO = new CustomerDTO();
		List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
		
		//=================================================
		//CustomerDTO contient IndividualDTO
		//=================================================
		IndividualDTO individualDto = new IndividualDTO();
		
		IndividualInformationsDTO iiDto = new IndividualInformationsDTO();
		iiDto.setIndividualKey(individuDTO.getSgin());
		iiDto.setCivility(individuDTO.getCivilite());
		iiDto.setLastNameExternal(individuDTO.getNomSC());
		iiDto.setFirstNameExternal(individuDTO.getPrenomSC());
		iiDto.setFirstNameInternal(individuDTO.getPrenom());
		iiDto.setLastNameInternal(individuDTO.getNom());
		iiDto.setBirthDate(individuDTO.getDateNaissance());
		iiDto.setCustomerStatus(individuDTO.getStatutIndividu());
		iiDto.setSpokenLanguage(individuDTO.getCodeLangue());
		iiDto.setGinFusion(individuDTO.getGinFusion());
		if(individuDTO.getProfilsdto() != null) {
			iiDto.setOptinIndividual(individuDTO.getProfilsdto().getSmailing_autorise());
		}
		individualDto.setIndividualInformations(iiDto);
		individualDto.setRelevance(100);
		
		//POSTAL ADDRESS : passées en paramètre car récupérées via appel à postalAddressDS.findPostalAddress(gin);	
		individualDto.setPostalAddressIndividual(postalAddressIndividualList);
		
		//TELECOM
		List<TelecomIndividualDTO> telecomIndList = new ArrayList<TelecomIndividualDTO>();
		TelecomIndividualDTO telIndDto = null;
		int indexTel = 0;
		for ( TelecomsDTO telecom : telecoms) {
			if ( telecom.isInvalidFixedLinePhone()==false && telecom.isInvalidMobilePhone()==false && indexTel < ConstantValues.INDEX_TELECOM_IND && MediumStatusEnum.VALID.toLiteral().equals(telecom.getSstatut_medium()) ) {
				telIndDto = new TelecomIndividualDTO();
				if(telecom.getSnorm_inter_country_code() != null) {
					telIndDto.setCountryCode(telecom.getSnorm_inter_country_code());
				}
				else if(telecom.getSindicatif() != null) {
					telIndDto.setCountryCode(telecom.getSindicatif());
				}
				else {
					telIndDto.setCountryCode("");
				}
				telIndDto.setMediumCode(telecom.getScode_medium());
				telIndDto.setInternationalNormalizedPhoneNumber(telecom.getSnorm_inter_phone_number());
				telIndDto.setPhoneNumber(telecom.getSnumero());
				telIndDto.setTerminalType(telecom.getSterminal());
				telecomIndList.add(telIndDto);
				indexTel++;
			}
		}
		individualDto.setTelecomIndividual(telecomIndList);
		
		//EMAIL ADDRESS : !!!!only Valid emails are returned 0..2 !!!
		List<EmailIndividualDTO> emailIndList = new ArrayList<EmailIndividualDTO>();
		EmailIndividualDTO emailIndividual = null;
		int indexML = 0;
		for (EmailDTO email : emaildto) {
			if ( indexML < ConstantValues.INDEX_EMAIL_IND && (MediumStatusEnum.VALID.toLiteral().equals(email.getStatutMedium())
					|| MediumStatusEnum.TEMPORARY.toLiteral().equals(email.getStatutMedium()))){
				emailIndividual = new EmailIndividualDTO();
				
				emailIndividual.setEmail(email.getEmail());
				emailIndividual.setMediumCode(email.getCodeMedium());
				emailIndividual.setEmailOptin(email.getAutorisationMailing());
				
				emailIndList.add(emailIndividual);
				indexML++;
			}
		}
		individualDto.setEmailIndividual(emailIndList);
		
		//CONTRACTS
		List<ContractIndividualDTO> contractIndList = new ArrayList<ContractIndividualDTO>();
		ContractIndividualDTO constractIndDto = null;
		int indexContract = 0;
		for (RoleContratsDTO role : roleContrats) {
			
			if (indexContract < ConstantValues.INDEX_CONTRACT_IND && (role.getEtat().equals("C") || role.getEtat().equals("P")))	{
				constractIndDto = new ContractIndividualDTO();
				constractIndDto.setContractNumber(role.getNumeroContrat());
				constractIndDto.setContractFamily(role.getFamilleProduit());
				constractIndDto.setProductType(role.getTypeContrat());
				constractIndDto.setProductSubType(role.getSousType());
				constractIndDto.setContractStatus(role.getEtat());
				constractIndDto.setValidityStartDate(role.getDateDebutValidite());
				constractIndDto.setValidityEndDate(role.getDateFinValidite());
				constractIndDto.setTierLevel(role.getTier());
				//MemberType renseigné dans IdentifyCustomerCrossReferentialFacade via Osiris
				//car les données Osiris sont plus récentes de 24H que les données en BD.
				constractIndDto.setMemberType(role.getMemberType());
				
				contractIndList.add(constractIndDto);
				indexContract++;
			}
		}
		
		//LA LISTE DOIT ETRE TRIEE "BY END DATE"
		Collections.sort(contractIndList, new CompareContractDate());
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Collection SORT :");
			for (ContractIndividualDTO dto : contractIndList){
				LOGGER.debug("Contract nber :"+dto.getContractNumber());
				LOGGER.debug("Contract status :"+dto.getContractStatus());
				LOGGER.debug("Contract productType :"+dto.getProductType());
				LOGGER.debug("Contract validity End date :"+dto.getValidityEndDate());
				LOGGER.debug("==================================================");
			}
		}
	
		individualDto.setContractIndividual(contractIndList);
				
		customerDTO.setIndividual(individualDto);

		customerDTOList.add(customerDTO);
		responseDTO.setCustomers(customerDTOList);
		
		return responseDTO;
	}

	public static List<PostalAddressIndividualDTO> postalAddressDTOToPostalAddressIndividualDTO(List<PostalAddressDTO> postalAddressDTOList){
		
		List<PostalAddressIndividualDTO> postalAddressIndividualList = new ArrayList<PostalAddressIndividualDTO>();
		
		PostalAddressIndividualDTO pai = null;
		int index = 0;
		if(postalAddressDTOList != null) {
			for (PostalAddressDTO dto : postalAddressDTOList) {

				if ( index < ConstantValues.INDEX_POSTAL_ADDRESS_IND && !MediumStatusEnum.HISTORIC.equals(MediumStatusEnum.fromLiteral(dto.getSstatut_medium()))
						&& !MediumStatusEnum.SUPPRESSED.equals(MediumStatusEnum.fromLiteral(dto.getSstatut_medium()))) {
					pai = new PostalAddressIndividualDTO();

					pai.setMediumCode(dto.getScode_medium());
					pai.setMediumStatus(dto.getSstatut_medium());
					pai.setCorporateName(dto.getSraison_sociale());
					pai.setCity(dto.getSville());
					pai.setAdditionalInformations(dto.getScomplement_adresse());
					pai.setNumberAndStreet(dto.getSno_et_rue());
					pai.setDistrict(dto.getSlocalite());
					pai.setZipCode(dto.getScode_postal());
					pai.setCountryCode(dto.getScode_pays());
					pai.setStateCode(dto.getScode_province());

					List<AddressRoleIndividualDTO> ariList = new ArrayList<AddressRoleIndividualDTO>();
					AddressRoleIndividualDTO ari = null;
					int indexRoles = 0;
					for (Usage_mediumDTO um : dto.getUsage_mediumdto() ) {

						if ( indexRoles < ConstantValues.INDEX_ROLES_IND) {

							ari = new AddressRoleIndividualDTO();
							ari.setRole(um.getSrole1());				
							ari.setApplicationCode(um.getScode_application());
							ari.setUsageNumber(SicStringUtils.getStringFromObject(um.getInum()));

							ariList.add(ari);
						}
						indexRoles++;
					}
					pai.setAddressRoleIndividual(ariList);			
					postalAddressIndividualList.add(pai);
				}
				index++;
			}
		}
		
		return postalAddressIndividualList;
	}

	

    /**
     * @return valid localisation postal address
     */
	private static PostalAddressDTO fetchValidLocalisationPostalAddress(List<PostalAddressDTO> postalAddrList) {

		PostalAddressDTO validLocalisationPostalAddress = null;

		if(postalAddrList != null && !postalAddrList.isEmpty()) {
			for (PostalAddressDTO postalAddress : postalAddrList) {

				if (MediumCodeEnum.LOCALISATION.equals(MediumCodeEnum.fromLiteral(postalAddress.getScode_medium()))
						&& MediumStatusEnum.VALID.equals(MediumStatusEnum.fromLiteral(postalAddress.getSstatut_medium()))) {

					validLocalisationPostalAddress = postalAddress;
					break;
				}
			}
		}

		return validLocalisationPostalAddress;
	}

	public static ResponseDTO personneMoraleDtoToResponseDto(RequestDTO requestDTO, PersonneMoraleDTO dto, String siret) throws BusinessExceptionDTO{
		if(!BuildConditionDS.isConditionStatusSatisfied(requestDTO, dto.getStatut(), "FU")
				|| !BuildConditionDS.isTypeOfFirmConditionSatisfied(requestDTO, dto.getClass())) {
			AbstractDS.throwBusinessException(AbstractDS.getNoResultsReturnCode(), AbstractDS.getNoResultsReturnMessage());
		}

		ResponseDTO responseDto = new ResponseDTO();

		if ( dto != null) {
			String gin = dto.getGin();

			CustomerDTO customerDTO = new CustomerDTO();
			List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();

			//=================================================
			//CustomerDTO contient CorporateDTO
			//=================================================
			CorporateDTO corporateDto = new CorporateDTO();
			corporateDto.setRelevance(100);

			//CorporateInformations
			CorporateInformationsDTO corpInfoDto = new CorporateInformationsDTO();
			corpInfoDto.setCorporateKey(gin);
			corpInfoDto.setSiretNumber(siret);
			corpInfoDto.setLegalName(dto.getNom());
			corpInfoDto.setStatus(dto.getStatut());
			String firmType = null;
			if(dto.getClass().equals(EtablissementDTO.class)) {
				firmType = "T";
			} else if(dto.getClass().equals(GroupeDTO.class)) {
				firmType = "G";
			} else if(dto.getClass().equals(EntrepriseDTO.class)) {
				firmType = "E";
			} else if(dto.getClass().equals(ServiceDTO.class)) {
				firmType = "S";
			}
			corpInfoDto.setType(firmType);
			Set<SynonymeDTO> synomymList= dto.getSynonymes();
			if ( synomymList != null  &&  !synomymList.isEmpty()) {
				for (SynonymeDTO synomym : synomymList) {
					if (synomym.getType().equals("U") ) {
						corpInfoDto.setUsualName(synomym.getNom());
						break;
					}
				}
			}
			corporateDto.setCorporateInformations(corpInfoDto);


			//PostalAddress
			PostalAddressDTO postalAdd = fetchValidLocalisationPostalAddress(dto.getPostalAddresses());
			if(postalAdd != null) {
				PostalAddressCorporateDTO postalAddCorp = new PostalAddressCorporateDTO();
				postalAddCorp.setCity(postalAdd.getSville());
				postalAddCorp.setAdditionalInformations(postalAdd.getScomplement_adresse());
				postalAddCorp.setNumberAndStreet(postalAdd.getSno_et_rue());
				postalAddCorp.setDistrict(postalAdd.getSlocalite());
				postalAddCorp.setZipCode(postalAdd.getScode_postal());
				postalAddCorp.setCountryCode(postalAdd.getScode_pays());
				postalAddCorp.setStateCode(postalAdd.getScode_province());
				corporateDto.setPostalAddressCorporate(postalAddCorp);
			}


			//Telecom
			int indexTel = 0;
			Set<TelecomsDTO>  telecomList = dto.getTelecoms();
			List<TelecomCorporateDTO> telecomCorpDtoList = new ArrayList<TelecomCorporateDTO>();
			TelecomCorporateDTO telCorpDto = null;
			for (TelecomsDTO telDto : telecomList) {
				if ( telDto.isInvalidFixedLinePhone()==false && telDto.isInvalidMobilePhone()==false && indexTel < ConstantValues.INDEX_TELECOM && MediumStatusEnum.VALID.toLiteral().equals(telDto.getSstatut_medium())) {
					telCorpDto = new TelecomCorporateDTO();
					if(telDto.getSnorm_inter_country_code() != null) {
						telCorpDto.setCountryCode(telDto.getSnorm_inter_country_code());
					}
					else if(telDto.getSindicatif() != null) {
						telCorpDto.setCountryCode(telDto.getSindicatif());
					}
					else {
						telCorpDto.setCountryCode("");
					}
					telCorpDto.setMediumCode(telDto.getScode_medium());
					telCorpDto.setInternationalNormalizedPhoneNumber(telDto.getSnorm_inter_phone_number());
					telCorpDto.setPhoneNumber(telDto.getSnumero());
					telCorpDto.setTerminalType(telDto.getSterminal());

					telecomCorpDtoList.add(telCorpDto);
					indexTel++;
				}
			}
			corporateDto.setTelecomCorporate(telecomCorpDtoList);


			//EmailAddress
			int indexML = 0;
			List<EmailCorporateDTO> emailCorpDtoList = new ArrayList<EmailCorporateDTO>();
			EmailCorporateDTO emailCorpDto = null;
			Set<EmailDTO> emailsList = dto.getEmails();
			for ( EmailDTO emailDto : emailsList){
				if ( indexML < ConstantValues.INDEX_EMAIL && MediumStatusEnum.VALID.toLiteral().equals(emailDto.getStatutMedium())){
					emailCorpDto = new EmailCorporateDTO();

					emailCorpDto.setEmail(emailDto.getEmail());
					emailCorpDto.setMediumCode(emailDto.getCodeMedium());
					emailCorpDto.setOptin(emailDto.getAutorisationMailing());

					emailCorpDtoList.add(emailCorpDto);
					indexML++;
				}
			}
			corporateDto.setEmailCorporate(emailCorpDtoList);


			//CommercialZone
			if(dto.getPmZones() != null && !dto.getPmZones().isEmpty()) {
				for(PmZoneDTO pmZoneDto : dto.getPmZones()) {
					if(pmZoneDto.getLienPrivilegie().equals("O")) {
						ZoneCommDTO zc = (ZoneCommDTO) pmZoneDto.getZoneDecoup();
						CommercialZonesCorporateDTO zcCorpDto = new CommercialZonesCorporateDTO();
						zcCorpDto.setZoneSubtype(zc.getSousType());
						zcCorpDto.setNatureZone(zc.getNature());
						zcCorpDto.setZc1(zc.getZc1());
						zcCorpDto.setZc2(zc.getZc2());
						zcCorpDto.setZc3(zc.getZc3());
						zcCorpDto.setZc4(zc.getZc4());
						zcCorpDto.setZc5(zc.getZc5());
						corporateDto.setCommercialZonesCorporate(zcCorpDto);
					}
				}
			}

			//Customer
			customerDTO.setCorporate(corporateDto);
			customerDTOList.add(customerDTO);
			responseDto.setCustomers(customerDTOList);
		}
		return responseDto;
	}

	public static ResponseDTO agenceDtoToResponseDto(RequestDTO requestDTO, AgenceDTO dto) throws BusinessExceptionDTO{
		if(!BuildConditionDS.isConditionStatusSatisfied(requestDTO, dto.getStatut(), "R")) {
			AbstractDS.throwBusinessException(AbstractDS.getNoResultsReturnCode(), AbstractDS.getNoResultsReturnMessage());
		}

		ResponseDTO responseDto = new ResponseDTO();

		if ( dto != null){
			String gin = dto.getGin();

			List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
			CustomerDTO customerDto = new CustomerDTO();

			AgencyDTO agenceDto = new AgencyDTO();
			/*
			int relevance;
			private AgencyInformationsDTO agencyInformations;
			private List<TelecomAgencyDTO> telecomAgency;
			private PostalAddressAgencyDTO postalAddressAgency;
			private List<EmailAgencyDTO> emailAgency;
			private List<MemberAgencyDTO> memberAgency;
			*/
			agenceDto.setRelevance(100);

			//AgencyInformations
			AgencyInformationsDTO agenceInfo = new AgencyInformationsDTO();
			agenceInfo.setAgencyKey(dto.getGin());
			/*
			 * Get Agency Number and Number Type
			 */
			if(dto.getNumerosIdent() != null && !dto.getNumerosIdent().isEmpty()) {
				for (NumeroIdentDTO n : dto.getNumerosIdent()) {
					if(("AT".equalsIgnoreCase(n.getType()) || "IA".equalsIgnoreCase(n.getType()) || "AG".equalsIgnoreCase(n.getType()))
							&& (n.getDateFermeture() == null || n.getDateFermeture().after(new Date()))){
						agenceInfo.setTypeAgreementNumber(n.getType());
						agenceInfo.setAgreementNumber(n.getNumero());
					}
				}
			}
			agenceInfo.setLegalName(dto.getNom());
			agenceInfo.setStatus(dto.getStatut());
			Set<SynonymeDTO> synomymList= dto.getSynonymes();
			if ( synomymList != null  &&  !synomymList.isEmpty()) {
				for (SynonymeDTO synomym : synomymList) {
					if ( synomym.getType().equals("U") ){
						agenceInfo.setUsualName(synomym.getNom());
						break;
					}
				}
			}
			agenceDto.setAgencyInformations(agenceInfo);

			//PostalAddresses
			PostalAddressDTO postalAddDto = fetchValidLocalisationPostalAddress(dto.getPostalAddresses());
			if(postalAddDto != null) {
				PostalAddressAgencyDTO postalAddressAgency = new PostalAddressAgencyDTO();
				postalAddressAgency.setCity(postalAddDto.getSville());
				postalAddressAgency.setAdditionalInformations(postalAddDto.getScomplement_adresse());
				postalAddressAgency.setNumberAndStreet(postalAddDto.getSno_et_rue());
				postalAddressAgency.setDistrict(postalAddDto.getSlocalite());
				postalAddressAgency.setZipCode(postalAddDto.getScode_postal());
				postalAddressAgency.setCountryCode(postalAddDto.getScode_pays());
				postalAddressAgency.setStateCode(postalAddDto.getScode_province());
				agenceDto.setPostalAddressAgency(postalAddressAgency);
			}

			//Telecoms
			Set<TelecomsDTO> telList = dto.getTelecoms();
			List<TelecomAgencyDTO> telecomAgencyList = new ArrayList<TelecomAgencyDTO>();
			TelecomAgencyDTO telAgencyDto = null;
			int index = 0;
			for ( TelecomsDTO tel : telList){
				//Only Valid telecoms are returned
				if (index < ConstantValues.INDEX_TELECOM && MediumStatusEnum.VALID.toLiteral().equals(tel.getSstatut_medium())) {
					telAgencyDto = new TelecomAgencyDTO();
					if(tel.getSnorm_inter_country_code() != null) {
						telAgencyDto.setCountryCode(tel.getSnorm_inter_country_code());
					}
					else if(tel.getSindicatif() != null) {
						telAgencyDto.setCountryCode(tel.getSindicatif());
					}
					else {
						telAgencyDto.setCountryCode("");
					}
					telAgencyDto.setMediumCode(tel.getScode_medium());
					telAgencyDto.setInternationalNormalizedPhoneNumber(tel.getSnorm_inter_phone_number());
					telAgencyDto.setPhoneNumber(tel.getSnumero());
					telAgencyDto.setTerminalType(tel.getSterminal());

					telecomAgencyList.add(telAgencyDto);
					index++;
				}
			}
			agenceDto.setTelecomAgency(telecomAgencyList);

			//Emails
			Set<EmailDTO> emailList = dto.getEmails();
			List<EmailAgencyDTO> emailAgencyList = new ArrayList<EmailAgencyDTO>();
			EmailAgencyDTO emailAgency = null;
			int indexMail = 0;
			for (EmailDTO email : emailList) {
				//Only Valid emails are returned
				if (indexMail < ConstantValues.INDEX_EMAIL && MediumStatusEnum.VALID.toLiteral().equals(email.getStatutMedium())) {
					emailAgency = new EmailAgencyDTO();
					emailAgency.setEmail(email.getEmail());
					emailAgency.setMediumCode(email.getCodeMedium());
					emailAgency.setOptin(email.getAutorisationMailing());
					emailAgencyList.add(emailAgency);
					indexMail++;
				}
			}
			agenceDto.setEmailAgency(emailAgencyList);

			//Commercial Zone
			List<PmZoneDTO> activeZcDtoList = returnActiveZc(gin, dto.getPmZones());
			//Transform ZoneCommDTO en CommercialZonesAgencyDTO
			if (activeZcDtoList != null && !activeZcDtoList.isEmpty()) {
				List<CommercialZonesAgencyDTO> zcAgencyList = new ArrayList<CommercialZonesAgencyDTO>();
				for(PmZoneDTO  pmZoneDTOLoop : activeZcDtoList) {
					ZoneCommDTO  zcDTOLoop = (ZoneCommDTO)pmZoneDTOLoop.getZoneDecoup();
					CommercialZonesAgencyDTO zcAgency = new CommercialZonesAgencyDTO();
					if ( zcDTOLoop.getSousType() != null)
						zcAgency.setZoneSubtype(zcDTOLoop.getSousType());
					if ( zcDTOLoop.getNature() != null)
						zcAgency.setNatureZone(zcDTOLoop.getNature());
					if ( pmZoneDTOLoop.getLienPrivilegie() != null)
						zcAgency.setPrivilegedLink(pmZoneDTOLoop.getLienPrivilegie());
					if ( pmZoneDTOLoop.getDateOuverture() != null)
						zcAgency.setLinkStartDate(pmZoneDTOLoop.getDateOuverture());
					if ( pmZoneDTOLoop.getDateFermeture() != null)
						zcAgency.setLinkEndDate(pmZoneDTOLoop.getDateFermeture());
					if ( zcDTOLoop.getDateOuverture() != null)
						zcAgency.setZcStartDate(zcDTOLoop.getDateOuverture());
					if ( zcDTOLoop.getDateFermeture() != null)
						zcAgency.setZcEndDate(zcDTOLoop.getDateFermeture());
					if ( zcDTOLoop.getZc1() != null)
						zcAgency.setZc1(zcDTOLoop.getZc1());
					if ( zcDTOLoop.getZc2() != null)
						zcAgency.setZc2(zcDTOLoop.getZc2());
					if ( zcDTOLoop.getZc3() != null)
						zcAgency.setZc3(zcDTOLoop.getZc3());
					if ( zcDTOLoop.getZc4() != null)
						zcAgency.setZc4(zcDTOLoop.getZc4());
					if ( zcDTOLoop.getZc5() != null)
						zcAgency.setZc5(zcDTOLoop.getZc5());
					zcAgencyList.add(zcAgency);
				}
				agenceDto.setCommercialZonesAgency(zcAgencyList);
			}

			//Members : ONLY for typeOfSearch = 'IA'

			//Customer
			customerDto.setAgency(agenceDto);
			customerDTOList.add(customerDto);
			responseDto.setCustomers(customerDTOList);
		}

		return responseDto;

	}

	private static List<PmZoneDTO> returnActiveZc(String gin, Set<PmZoneDTO> pmZoneDtoList){
		/*
		 * Returned value
		 */
		List<PmZoneDTO> activeZcList = null;
		/*
		 * Current date (priorityZC.getDateFermeture() has to be after the currentDate
		 */
		Date currentDate = new Date();

		if(pmZoneDtoList != null && !pmZoneDtoList.isEmpty())
		{
			for(PmZoneDTO pmZone :pmZoneDtoList)
			{
				if((pmZone.getZoneDecoup() != null)
						&&	(pmZone.getZoneDecoup().getClass().equals(ZoneCommDTO.class)))
				{
					/*
					 * Treating ACTIVE ZC
					 */
					ZoneCommDTO zc = (ZoneCommDTO)pmZone.getZoneDecoup();

					if(zc.getDateFermeture() == null || (zc.getDateFermeture() != null && zc.getDateFermeture().after(currentDate)))
					{
						if(activeZcList == null) {
							activeZcList = new ArrayList<PmZoneDTO>();
						}
						activeZcList.add(pmZone);
					}
				}
			}
		}
		if(activeZcList == null)
		{
			LOGGER.info("No active ZC found for gin " + gin);
		}
		return activeZcList;
	}

}
