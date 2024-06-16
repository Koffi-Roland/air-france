package com.airfrance.repind.util.transformer;

import com.airfrance.ref.exception.BadDateFormatException;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.delegation.DelegationDataInfoDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.InfosIndividuDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.dto.ws.TelecomDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class IndividuTransform {

	// Transform Request to IndividuDTO
	public static IndividuDTO transformRequestToIndividuDTO(CreateUpdateIndividualRequestDTO request ,  IndividuDS individuDS) throws InvalidParameterException, MissingParameterException {
		
		if(request == null){
			throw new InvalidParameterException("Request is null");
		}
		
		// Init individu
		IndividuDTO dto = transformToIndividuDTO(request);
		if (dto == null && (request.getProcess() != null && ProcessEnum.E.getCode().equals(request.getProcess()))) { 
			return createDefaultEmptyIndividu(request);
		} else if (dto == null) {
			return null;
		// Dans le cas d'un Traveler
		} else if (request != null && request.getProcess() != null && ProcessEnum.T.getCode().equals(request.getProcess())) {
/*			if (request.getIndividualRequest() != null && 
					request.getIndividualRequest().getIndividualInformations() != null && 
							request.getIndividualRequest().getIndividualInformations().getVersion() == null) {
				// On initialise la Version pour le Traveler a 1
				request.getIndividualRequest().getIndividualInformations().setVersion("1");
			}
*/			if (dto != null && dto.getVersion() == null) {
				// On initialise la Version pour le Traveler a 1
				dto.setVersion(1);
			}
		} else if (request != null && request.getProcess() != null && ProcessEnum.C.getCode().equals(request.getProcess())) {
			dto.setStatutIndividu(IndividualStatusEnum.VALID.toString()); //By default put status valid REPIND-2024
			for (TelecomRequestDTO telecomRequest : request.getTelecomRequestDTO()) {
				String terminalType = telecomRequest.getTelecomDTO().getTerminalType();
				if(terminalType == null){
					TelecomDTO telecom = telecomRequest.getTelecomDTO();
					if(telecom !=null ){
						//if not provide put Mobile (M) by default
						if(telecom.getTerminalType() == null){
							telecom.setTerminalType(TerminalTypeEnum.MOBILE.toString());
						}
						//if not provide put Home (D) by default
						if(telecom.getMediumCode() == null){
							telecom.setMediumCode(MediumCodeEnum.HOME.toString());
						}
					}
				}
			}
			individuDS.prepareIndividualFields(dto); //put all mandatory fields by default
		}
		// Add context Type
		if (!"W".equalsIgnoreCase(request.getProcess()) && !"A".equalsIgnoreCase(request.getProcess())) {
			dto.setType(request.getProcess());
		}
		
		// Adding signature
		addSignatureToIndividuDTO(request.getRequestorDTO(), dto);
		
		// Set Contact(s)
		// Don't process the emails at this level, @REPIND-1143 transformListToSetEmailDTO(request.getEmailRequestDTO(), dto);
		transformListToSetTelecomsDTO(request.getTelecomRequestDTO(), dto);
		
		return dto;
	}

	public static IndividuDTO transformToIndividuDTO(CreateUpdateIndividualRequestDTO ws) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		IndividuDTO dto = ProcessEnum.C.getCode().equals(ws.getProcess()) ? transformToIndividuDTOForCaller(ws.getIndividualRequestDTO()) : transformToIndividuDTO(ws.getIndividualRequestDTO());
		
		return dto;
		
	}

	
	private static IndividuDTO createDefaultEmptyIndividu(CreateUpdateIndividualRequestDTO request) {
		IndividuDTO dto = new IndividuDTO();

		dto.setVersion(1);
		dto.setStatutIndividu("V");
		dto.setCivilite(CivilityEnum.M_.toString());
		dto.setSexe(GenderEnum.UNKNOWN.toString());
		dto.setNonFusionnable("N");
		dto.setType(request.getProcess());
		// Adding signature
		addSignatureToIndividuDTO(request.getRequestorDTO(), dto);

		return dto;
	}

	// Dans le cas d un update , on veut pouvoir juste modifier les infos de Modification
	public static void addSignatureToIndividuDTO(RequestorDTO requestor, IndividuDTO dto) {
		
		Date today = new Date();
		
		switch (dto.getSgin() == null || dto.getSgin().isEmpty() ? SignatureTypeEnum.CREATION : SignatureTypeEnum.MODIFICATION) {
		case CREATION:
			dto.setDateCreation(today);
			dto.setSignatureCreation(requestor.getSignature());
			dto.setSiteCreation(requestor.getSite());
			
			// Init also modification signature
			dto.setDateModification(today);
			dto.setSignatureModification(requestor.getSignature());
			dto.setSiteModification(requestor.getSite());
			break;
		case MODIFICATION:
			dto.setDateModification(today);
			dto.setSignatureModification(requestor.getSignature());
			dto.setSiteModification(requestor.getSite());
			break;
		}
	}

	private static void addSignatureToEmailDTO(EmailDTO email, IndividuDTO dto) {
		switch (dto.getSgin() == null || dto.getSgin().isEmpty() ? SignatureTypeEnum.CREATION : SignatureTypeEnum.MODIFICATION) {
		case CREATION:
			email.setDateCreation(dto.getDateCreation());
			email.setSignatureCreation(dto.getSignatureCreation());
			email.setSiteCreation(dto.getSiteCreation());
			
			// Init also modification signature
			email.setDateModification(dto.getDateModification());
			email.setSignatureModification(dto.getSignatureModification());
			email.setSiteModification(dto.getSiteModification());
			break;
		case MODIFICATION:
			email.setDateModification(dto.getDateModification());
			email.setSignatureModification(dto.getSignatureModification());
			email.setSiteModification(dto.getSiteModification());
			break;
		}
	}

	private static void addSignatureToTelecomDTO(TelecomsDTO tel, IndividuDTO dto) {
		switch (dto.getSgin() == null || dto.getSgin().isEmpty() ? SignatureTypeEnum.CREATION : SignatureTypeEnum.MODIFICATION) {
		case CREATION:
			tel.setDdate_creation(dto.getDateCreation());
			tel.setSsignature_creation(dto.getSignatureCreation());
			tel.setSsite_creation(dto.getSiteCreation());
			
			// Init also modification signature
			tel.setDdate_modification(dto.getDateModification());
			tel.setSsignature_modification(dto.getSignatureModification());
			tel.setSsite_modification(dto.getSiteModification());
			break;
		case MODIFICATION:
			tel.setDdate_modification(dto.getDateModification());
			tel.setSsignature_modification(dto.getSignatureModification());
			tel.setSsite_modification(dto.getSiteModification());
			break;
		}
	}
	
	public static SignatureDTO transformToSignatureAPP(RequestorDTO requestor) {
		
		Date today = new Date();
		
		SignatureDTO dto = new SignatureDTO();
		dto.setDate(today);
		dto.setHeure(SicDateUtils.computeHour(today));
		dto.setIpAddress(requestor.getIpAddress());
		dto.setSignature(requestor.getSignature());
		dto.setSite(requestor.getSite());
		dto.setTypeSignature(null);
		dto.setApplicationCode(requestor.getApplicationCode());
		
		return dto;
	}
	
	public static SignatureDTO transformToSignatureWS(String webserviceIdentifier, String site) {
		
		Date today = new Date();
		
		SignatureDTO dto = new SignatureDTO();
		dto.setDate(today);
		dto.setHeure(SicDateUtils.computeHour(today));
		dto.setSignature(webserviceIdentifier);
		dto.setSite(site);
		
		return dto;
	}
	
	
	public static IndividuDTO transformToIndividuDTO(IndividualRequestDTO ws) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		IndividuDTO dto = transformToIndividuDTO(ws.getIndividualInformationsDTO());

		// REPIND-1003 : Repair blocker
		if(dto != null) {
			dto.setCodeTitre(ws.getTitleCode());
			dto.setProfilsdto(transformToProfilsDTO(ws.getIndividualProfilDTO()));
		}
		
		return dto;
		
	}

	public static IndividuDTO transformToIndividuDTOForCaller(IndividualRequestDTO ws) throws InvalidParameterException {

		if(ws==null) {
			return new IndividuDTO();
		}

		IndividuDTO dto = transformToIndividuDTO(ws.getIndividualInformationsDTO());

		// REPIND-1003 : Repair blocker
		if(dto != null) {
			dto.setCodeTitre(ws.getTitleCode());
			dto.setProfilsdto(transformToProfilsDTO(ws.getIndividualProfilDTO()));
		}

		return dto;

	}
	
	// ===== POSTAL ADDRESSES ==============================================================
	
	public static List<PostalAddressDTO> transformToPostalAddressDTO(List<PostalAddressRequestDTO> wsList) throws InvalidParameterException {
		
		// REPIND-260 : SONAR - On test Null avant de tester Empty
		if(wsList == null || wsList.isEmpty()) {
			return null;
		}
		
		List<PostalAddressDTO> dtoList = new ArrayList<PostalAddressDTO>();
		
		for(PostalAddressRequestDTO ws : wsList) {
			dtoList.add(transformToPostalAddressDTO(ws));
		}
		
		return dtoList;
	}
	
	public static PostalAddressDTO transformToPostalAddressDTO(PostalAddressRequestDTO ws) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		PostalAddressDTO dto = new PostalAddressDTO();
		transformToPostalAddressDTO(ws.getPostalAddressContentDTO(), dto);
		transformToPostalAddressDTO(ws.getPostalAddressPropertiesDTO(), dto);
		dto.setPreferee(transformToPreferedAddress(ws.getUsageAddressDTO())); // attention ce champs est primordial pour le calcul des usages
		dto.setUsage_mediumdto(transformToUsageMediumDTO(ws.getUsageAddressDTO()));
		
		return dto;
	}
	
	public static void transformToPostalAddressDTO(PostalAddressContentDTO ws, PostalAddressDTO dto) {
		
		if(ws==null) {
			return;
		}
		
		dto.setScomplement_adresse(ws.getAdditionalInformation());
		dto.setSville(ws.getCity());
		dto.setSraison_sociale(ws.getCorporateName());
		dto.setScode_pays(ws.getCountryCode());
		dto.setSlocalite(ws.getDistrict());
		dto.setSno_et_rue(ws.getNumberAndStreet());
		dto.setScode_province(ws.getStateCode());
		dto.setScode_postal(ws.getZipCode());
	}
	
	public static void transformToPostalAddressDTO(PostalAddressPropertiesDTO ws, PostalAddressDTO dto) throws InvalidParameterException {
		
		if(ws==null) {
			return;
		}
		
		if(ws.getIndicAdrNorm()!=null) {
		dto.setSforcage(OuiNonFlagEnum.getEnum(ws.getIndicAdrNorm()).toString());
		}
		
		dto.setScode_medium(ws.getMediumCode());
		dto.setSstatut_medium(ws.getMediumStatus());
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));
		
	}
	
	public static Set<Usage_mediumDTO> transformToUsageMediumDTO(UsageAddressDTO ws) {
		
		if(ws==null) {
			return null;
		}

		Usage_mediumDTO dto = new Usage_mediumDTO();
		dto.setSrole1(ws.getAddressRoleCode());
		dto.setScode_application(ws.getApplicationCode());
		dto.setInum(SicStringUtils.getIntegerFromString(ws.getUsageNumber()));
		
		Set<Usage_mediumDTO> dtoSet = new HashSet<Usage_mediumDTO>();
		dtoSet.add(dto);
		
		return dtoSet;
		
	}
	
	public static boolean transformToPreferedAddress(UsageAddressDTO ws) {
		
		if(ws==null || ws.getAddressRoleCode()==null) {
			return false;
		}
		
		// si c'est l'adresse principale alors c'est la pr�f�r�e
		return AddressRoleCodeEnum.PRINCIPAL.toString().equals(ws.getAddressRoleCode());
	}
	

	
	public static ProfilsDTO transformToProfilsDTO(IndividualProfilDTO ws) {
		
		if(ws==null) {
			return null;
		}
		
		ProfilsDTO dto = new ProfilsDTO();
		
		dto.setInb_enfants(SicStringUtils.getIntegerFromString(ws.getChildrenNumber()));
		dto.setScode_maritale(ws.getCivilianCode());
		dto.setSsegment(ws.getCustomerSegment());
		dto.setSmailing_autorise(ws.getEmailOptin());
		dto.setScode_langue(ws.getLanguageCode());
		dto.setScode_professionnel(ws.getProAreaCode());
		dto.setScode_fonction(ws.getProFunctionCode());
		dto.setSetudiant(ws.getStudentCode());
		
		return dto;
		
	}
	
	

	// ===== INDIVIDUAL INFORMATION ==============================================================
	
	public static IndividuDTO transformToIndividuDTO(IndividualInformationsDTO ws) throws InvalidParameterException {
		
		if(ws == null) {
			return null;
		}
		
		IndividuDTO dto = new IndividuDTO();
		
		dto.setDateNaissance(ws.getBirthDate());
		dto.setCivilite(ws.getCivility());
		dto.setAliasPrenom(ws.getFirstNamePseudonym());
		dto.setPrenom(ws.getFirstNameSC());
		dto.setPrenomSC(ws.getFirstNameSC());
		//dto.setSexe(GenderEnum.getEnum(ws.getGender()).toString());
		dto.setSexe(ws.getGender());
		dto.setSgin(ws.getIdentifier());
		dto.setCodeLangue(ws.getLanguageCode());
		dto.setAlias(ws.getLastNamePseudonym());
		dto.setNom(ws.getLastNameSC());
		dto.setNomSC(ws.getLastNameSC());
		dto.setNationalite(ws.getNationality());
		dto.setIdentifiantPersonnel(ws.getPersonalIdentifier());
		dto.setSecondPrenom(ws.getSecondFirstName());
		dto.setAutreNationalite(ws.getSecondNationality());
		dto.setStatutIndividu(ws.getStatus());
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));
		dto.setNonFusionnable(SicStringUtils.getStringFrenchBoolean(ws.getFlagNoFusion()));
		dto.setTierUtiliseCommePiege(SicStringUtils.getStringFrenchBoolean(ws.getFlagThirdTrap()));
		
		return dto;
		
	}

	
	public static IndividuDTO transformToIndividuDTO(IndividuDTO ret, InfosIndividuDTO infoIndivDTO) throws BadDateFormatException, JrafApplicativeException {
		
		if(infoIndivDTO==null) {
			return ret;
		}

		if(StringUtils.isNotEmpty(infoIndivDTO.getAliasNom())) {
			ret.setAlias(infoIndivDTO.getAliasNom());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getAliasPrenom())) {
			ret.setAliasPrenom(infoIndivDTO.getAliasPrenom());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getAutreNationalite())) {
			ret.setNationalite(infoIndivDTO.getAutreNationalite());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getCieGestionnaire())) {
			ret.setCieGest(infoIndivDTO.getCieGestionnaire());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getCivilite())) {
			ret.setCivilite(infoIndivDTO.getCivilite());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getDateFusion())) {
			ret.setDateFusion(SicDateUtils.stringToDate(infoIndivDTO.getDateFusion()));
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getDateNaissance())) {
			ret.setDateNaissance(SicDateUtils.stringToDate(infoIndivDTO.getDateNaissance()));
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getGinFusion())) {
			ret.setGinFusion(infoIndivDTO.getGinFusion());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getIdentPerso())) {
			ret.setIdentifiantPersonnel(infoIndivDTO.getIdentPerso());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getIndicFraudBanq())) {
			ret.setFraudeurCarteBancaire(infoIndivDTO.getIndicFraudBanq());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getGinFusion())) {
			ret.setGinFusion(infoIndivDTO.getGinFusion());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getIndicNonFusion())) {
			ret.setNonFusionnable(infoIndivDTO.getIndicNonFusion());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getIndicTiersPiege())) {
			ret.setTierUtiliseCommePiege(infoIndivDTO.getIndicTiersPiege());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getMotDePasse())) {
			ret.setMotDePasse(infoIndivDTO.getMotDePasse());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getNationalite())) {
			ret.setNationalite(infoIndivDTO.getNationalite());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getNom())) {
			ret.setNom(infoIndivDTO.getNom());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getNomSC())) {
			ret.setNomSC(infoIndivDTO.getNomSC());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getNumeroClient())) {
			ret.setSgin(infoIndivDTO.getNumeroClient());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getPrenom())) {
			ret.setPrenom(infoIndivDTO.getPrenom());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getPrenomSC())) {
			ret.setPrenomSC(infoIndivDTO.getPrenomSC());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getSecondPrenom())) {
			ret.setSecondPrenom(infoIndivDTO.getSecondPrenom());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getSexe())) {
			ret.setSexe(infoIndivDTO.getSexe());
		}
		if(StringUtils.isNotEmpty(infoIndivDTO.getStatut())) {
			ret.setStatutIndividu(infoIndivDTO.getStatut());
		}
		
		if(StringUtils.isNotEmpty(infoIndivDTO.getVersion())) {
			ret.setVersion(SicStringUtils.getIntegerFromString(infoIndivDTO.getVersion()));
		}

		return ret;
	}
	
	// ===== EMAIL ==============================================================
	public static void transformListToSetEmailDTO(List<EmailRequestDTO> wsList, IndividuDTO indDTO) throws InvalidParameterException, MissingParameterException {
		if (wsList == null || wsList.isEmpty()) {
			return;
		}

		Set<EmailDTO> dtoSet = Collections.synchronizedSet(new HashSet<EmailDTO>()); 
		
		for (EmailRequestDTO email : wsList) {
			EmailDTO dto = transformToEmailDTO(email.getEmailDTO());
			// REPIND-1003 : Repair blocker
			if(dto != null) {
				if (StringUtils.isBlank(dto.getStatutMedium())) {
					dto.setStatutMedium("V");
				}
				if (StringUtils.isBlank(dto.getAutorisationMailing())) {
					dto.setAutorisationMailing("N");
				}
				addSignatureToEmailDTO(dto, indDTO);
				dtoSet.add(dto);
			}
		}

		indDTO.setEmaildto(dtoSet);
	}


	public static List<EmailDTO> transformToEmailDTO(List<EmailRequestDTO> wsList) throws InvalidParameterException, MissingParameterException {
		
		// REPIND-260 : SONAR - On test Null avant de tester Empty
		if(wsList == null || wsList.isEmpty()) {
			return null;
		}
		
		List<EmailDTO> dtoList = new ArrayList<EmailDTO>();
		
		for(EmailRequestDTO ws : wsList) {
			dtoList.add(transformToEmailDTO(ws.getEmailDTO()));
		}
		
		return dtoList;
	}
	
	
	
	public static EmailDTO transformToEmailDTO(com.airfrance.repind.dto.ws.EmailDTO ws) throws InvalidParameterException, MissingParameterException {
		
		if(ws==null) {
			return null;
		}
		
		EmailDTO dto = new EmailDTO();
		
		// REPIND-1288 : Store Email Address on Lower Case
		dto.setEmail(SicStringUtils.normalizeEmail(ws.getEmail()));
		dto.setAutorisationMailing(ws.getEmailOptin());
		dto.setCodeMedium(MediumCodeEnum.getEnumMandatory(ws.getMediumCode()).toString());		
		if (ws.getMediumStatus() != null) {
			dto.setStatutMedium(ws.getMediumStatus());
		}
		else {
			dto.setStatutMedium(StatutEmailEnum.V.getStatus());
		}
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));

		return dto;
		
	}
	

	// ===== TELECOM DATA ==============================================================
	
	public static void transformListToSetTelecomsDTO(List<TelecomRequestDTO> wsList, IndividuDTO indDTO) throws InvalidParameterException, MissingParameterException {
		if (wsList == null || wsList.isEmpty()) {
			return;
		}
		
		Set<TelecomsDTO> dtoSet = Collections.synchronizedSet(new HashSet<TelecomsDTO>());

		for (TelecomRequestDTO tel : wsList) {
			TelecomsDTO dto = transformToTelecomsDTO(tel.getTelecomDTO());
			// REPIND-1003 : Repair blocker
			if(dto != null) {
				if (StringUtils.isBlank(dto.getSstatut_medium())) {
					dto.setSstatut_medium("V");
				}
				addSignatureToTelecomDTO(dto, indDTO);
				dtoSet.add(dto);
			}
		}

		indDTO.setTelecoms(dtoSet);
	}
	
	public static List<TelecomsDTO> transformToTelecomsDTO(List<TelecomRequestDTO> wsList) throws InvalidParameterException, MissingParameterException {
		
		// REPIND-260 : SONAR - On test Null avant de tester Empty
		if(wsList == null || wsList.isEmpty()) {		
			return null;
		}
			
		List<TelecomsDTO> dtoList = new ArrayList<TelecomsDTO>();
		
		for(TelecomRequestDTO ws : wsList) {
			if (ws.getTelecomDTO() != null) {
				dtoList.add(transformToTelecomsDTO(ws.getTelecomDTO()));
			}
		}
		
		return dtoList;
	}

	public static TelecomsDTO transformToTelecomsDTO(TelecomDTO ws) throws InvalidParameterException, MissingParameterException {
		
		if(ws==null) {
			return null;
		}
		
		TelecomsDTO dto = new TelecomsDTO();
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));
		dto.setScode_medium(MediumCodeEnum.getEnumMandatory(ws.getMediumCode()).toString());
		dto.setSstatut_medium(ws.getMediumStatus());
		dto.setSterminal(TerminalTypeEnum.getEnumMandatory(ws.getTerminalType()).toString());
		dto.setCountryCode(ws.getCountryCode());
		dto.setSnumero(ws.getPhoneNumber());				
		
		return dto;
	}
	
	
	public static IndividuDTO transformToProspectDTO(CreateUpdateIndividualRequestDTO request) throws InvalidParameterException{
		String gin ="";
		IndividuDTO individuDto = new IndividuDTO();
		if(request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO() != null){
			IndividualInformationsDTO info = request.getIndividualRequestDTO().getIndividualInformationsDTO();

			if (info.getIdentifier() != null) {
				gin = info.getIdentifier();
				individuDto.setSgin(gin);
			}

			if (info.getCivility() != null) {
				individuDto.setCivilite(info.getCivility());
			}
			if (info.getGender() != null) {
				individuDto.setSexe(info.getGender());
			}
			if (info.getLastNameSC() != null) {
				individuDto.setNom(info.getLastNameSC());
			}
			if (info.getFirstNameSC() != null) {
				individuDto.setPrenom(info.getFirstNameSC());
			}
			if (info.getMiddleNameSC() != null) {
				individuDto.setSecondPrenom(info.getMiddleNameSC());
			}
			if (info.getBirthDate() != null) {
				individuDto.setDateNaissance(info.getBirthDate());
			}
			if (info.getNationality() != null) {
				individuDto.setNationalite(info.getNationality());
			}
			if (info.getSecondNationality() != null) {
				individuDto.setAutreNationalite(info.getSecondNationality());
			}
		}
		
		// Prospect data
		if ((request.getEmailRequestDTO() != null && !request.getEmailRequestDTO().isEmpty()) && request.getEmailRequestDTO().get(0).getEmailDTO() != null) {
			
			EmailDTO emailDTO = new EmailDTO();
			//REPIND-1288: Put in lower case email
			emailDTO.setEmail(SicStringUtils.normalizeEmail(request.getEmailRequestDTO().get(0).getEmailDTO().getEmail()));
			emailDTO.setSgin(gin);

			// Set value by default
			emailDTO.setAutorisationMailing("A");
			emailDTO.setCodeMedium("D");
			emailDTO.setStatutMedium("V");
			
			Set<EmailDTO> setEmailDTO = new HashSet<EmailDTO>();
			setEmailDTO.add(emailDTO);

			individuDto.setEmaildto(setEmailDTO);
			
		}

		if (request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualProfilDTO() != null) {
			if(request.getIndividualRequestDTO().getIndividualProfilDTO().getLanguageCode() != null) {
				individuDto.setCodeLangue(request.getIndividualRequestDTO().getIndividualProfilDTO().getLanguageCode());
			}
		}
		

		//Preferred airport
		if (request.getPreferenceRequestDTO() != null) {
			if(request.getPreferenceRequestDTO().getPreferenceDTO() != null && !request.getPreferenceRequestDTO().getPreferenceDTO().isEmpty()) {
				List<PreferenceDTO> listPreferenceDTO = new ArrayList<PreferenceDTO>();
				
					
				// REPIND-555 : Change preferences for prospect
				
				// Preference for preferredAirport
				PreferenceDTO preferenceDTO = new PreferenceDTO();
				preferenceDTO.setType("TPC");
				
				if(request.getPreferenceRequestDTO().getPreferenceDTO().get(0).getId() != null) {
					preferenceDTO.setPreferenceId(request.getPreferenceRequestDTO().getPreferenceDTO().get(0).getId());
				}
				
				Set<PreferenceDataDTO> setPreferenceDataDTO = new HashSet<PreferenceDataDTO>();

				if(request.getPreferenceRequestDTO().getPreferenceDTO().get(0).getPreferencesDatasDTO() != null && !request.getPreferenceRequestDTO().getPreferenceDTO().get(0).getPreferencesDatasDTO().getPreferenceDataDTO().isEmpty()) {

					// REPIND-768: Remove restrictions
					for (com.airfrance.repind.dto.ws.PreferenceDataDTO preferenceData : request.getPreferenceRequestDTO().getPreferenceDTO().get(0).getPreferencesDatasDTO().getPreferenceDataDTO()) {
						PreferenceDataDTO preferenceDataDTO = new PreferenceDataDTO();
						preferenceDataDTO.setKey(preferenceData.getKey());
						preferenceDataDTO.setValue(preferenceData.getValue());
						setPreferenceDataDTO.add(preferenceDataDTO);
					}
					
					preferenceDTO.setPreferenceDataDTO(setPreferenceDataDTO);

					
				}
				
				if (!"".equals(gin)){
					preferenceDTO.setGin(gin);
				}
				
				listPreferenceDTO.add(preferenceDTO);
				
				individuDto.setPreferenceDTO(listPreferenceDTO);
			}
		}
		
		PostalAddressDTO postalAddressDTO = null;
		
		if (request.getPostalAddressRequestDTO() != null && !request.getPostalAddressRequestDTO().isEmpty()) {
			
			postalAddressDTO = new PostalAddressDTO();
			
			if (!"".equals(gin)){
				postalAddressDTO.setSgin(gin);
			}
			if (request.getPostalAddressRequestDTO().get(0) != null) {
				PostalAddressContentDTO addressContent = request.getPostalAddressRequestDTO().get(0).getPostalAddressContentDTO();
				PostalAddressPropertiesDTO addressProp = request.getPostalAddressRequestDTO().get(0).getPostalAddressPropertiesDTO();
				boolean postalAddressEmpty = true;

				if (addressContent != null) {
					if (addressContent.getNumberAndStreet() != null && !addressContent.getNumberAndStreet().equalsIgnoreCase("")) {
						postalAddressDTO.setSno_et_rue(addressContent.getNumberAndStreet());	
						postalAddressEmpty = false;
					}
					if (addressContent.getZipCode() != null && !addressContent.getZipCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_postal(addressContent.getZipCode());	
						postalAddressEmpty = false;
					}
					if (addressContent.getCity() != null && !addressContent.getCity().equalsIgnoreCase("")) {
						postalAddressDTO.setSville(addressContent.getCity());	
						postalAddressEmpty = false;
					}
					if(addressContent.getStateCode() != null && !addressContent.getStateCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_province(addressContent.getStateCode());	
						postalAddressEmpty = false;
					}	
					if (addressContent.getCountryCode() != null && !addressContent.getCountryCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_pays(addressContent.getCountryCode());	
						postalAddressEmpty = false;
					}
				}
				if (addressProp != null) {
					if (addressProp.getMediumStatus() != null && !addressProp.getMediumStatus().equalsIgnoreCase("")) {
						postalAddressDTO.setSstatut_medium(addressProp.getMediumStatus());
					}
					else {
						postalAddressDTO.setSstatut_medium("V");
					}
					if (addressProp.getMediumCode() != null && !addressProp.getMediumCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_medium(addressProp.getMediumCode());
					}
					else {
						postalAddressDTO.setScode_medium("D");
					}
				} else {
					postalAddressDTO.setSstatut_medium("V");
					postalAddressDTO.setScode_medium("D");
				}
				postalAddressDTO.setIcod_err(0);
				postalAddressDTO.setSforcage("O");
				
				if(postalAddressEmpty == true) {
					postalAddressDTO = null;
				}
			}
		}
		
		List<PostalAddressDTO> listAdrPostaleDTO = new ArrayList<PostalAddressDTO>();
		if(postalAddressDTO != null) {
			listAdrPostaleDTO.add(postalAddressDTO);
		}
		
		if(listAdrPostaleDTO.size() != 0) {
			individuDto.setPostaladdressdto(listAdrPostaleDTO);
		}
			
		//CommunicationPréférences
		if(request.getCommunicationPreferencesRequestDTO() != null && !request.getCommunicationPreferencesRequestDTO().isEmpty()){
			List<CommunicationPreferencesDTO> comPrefsDto = new ArrayList<CommunicationPreferencesDTO>();
			
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = request.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO();
			
			CommunicationPreferencesDTO comPrefDto = new CommunicationPreferencesDTO();

			if (!"".equals(gin)) {
					comPrefDto.setGin(gin);
			}
			if (comPref.getDomain() != null) {
				comPrefDto.setDomain(comPref.getDomain());
			}
			if (comPref.getCommunicationGroupeType() != null) {
					comPrefDto.setComGroupType(comPref.getCommunicationGroupeType());
			}
			if (comPref.getCommunicationType() != null) {
					comPrefDto.setComType(comPref.getCommunicationType());
			}
			if (comPref.getOptIn() != null) {
					comPrefDto.setSubscribe(comPref.getOptIn());
			}
			if (comPref.getDateOfConsent() != null) {
					comPrefDto.setDateOptin(comPref.getDateOfConsent());
			}
			if (comPref.getSubscriptionChannel() != null) {
					comPrefDto.setChannel(comPref.getSubscriptionChannel());
			}
			if (comPref.getOptInPartner() != null) {
				comPrefDto.setOptinPartners(comPref.getOptInPartner());
			}
			if (comPref.getDateOfConsentPartner() != null) {
					comPrefDto.setDateOptinPartners(comPref.getDateOfConsentPartner());
			}
			if (comPref.getDateOfEntry() != null) {
				comPrefDto.setDateOfEntry(comPref.getDateOfEntry());
			}
					
			if(comPref.getMediaDTO()!=null){
				if (comPref.getMediaDTO().getMedia1() != null) {
						comPrefDto.setMedia1(comPref.getMediaDTO().getMedia1());
				}
				if (comPref.getMediaDTO().getMedia2() != null) {
						comPrefDto.setMedia2(comPref.getMediaDTO().getMedia2());
				}
				if (comPref.getMediaDTO().getMedia3() != null) {
						comPrefDto.setMedia3(comPref.getMediaDTO().getMedia3());
				}
				if (comPref.getMediaDTO().getMedia4() != null) {
						comPrefDto.setMedia4(comPref.getMediaDTO().getMedia4());
				}
				if (comPref.getMediaDTO().getMedia5() != null) {
						comPrefDto.setMedia5(comPref.getMediaDTO().getMedia5());
				}
			}
				
			//market language data:
			if (comPref.getMarketLanguageDTO() != null && !comPref.getMarketLanguageDTO().isEmpty()) {
				
				Set<MarketLanguageDTO> mlsDto = new HashSet<MarketLanguageDTO>();
				
				for (com.airfrance.repind.dto.ws.MarketLanguageDTO ml : comPref.getMarketLanguageDTO()) {
				
					MarketLanguageDTO mlDto = new MarketLanguageDTO();
					
					if (ml.getMarket() != null) {
						mlDto.setMarket(ml.getMarket());
					}
					if (ml.getLanguage() != null) {
						mlDto.setLanguage(ml.getLanguage());
					}
					if (ml.getOptIn() != null) {
						mlDto.setOptIn(ml.getOptIn());
					}
					if (ml.getDateOfConsent() != null) {
						mlDto.setDateOfConsent(ml.getDateOfConsent());
					}
					else if (comPref.getDateOfConsent() != null) {
						mlDto.setDateOfConsent(comPref.getDateOfConsent());
					}
						
					if(ml.getMediaDTO()!=null){
						if (ml.getMediaDTO().getMedia1() != null) {
								mlDto.setMedia1(ml.getMediaDTO().getMedia1());
						}
						if (ml.getMediaDTO().getMedia2() != null) {
								mlDto.setMedia2(ml.getMediaDTO().getMedia2());
						}
						if (ml.getMediaDTO().getMedia3() != null) {
								mlDto.setMedia3(ml.getMediaDTO().getMedia3());
						}
						if (ml.getMediaDTO().getMedia4() != null) {
								mlDto.setMedia4(ml.getMediaDTO().getMedia4());
						}
						if (ml.getMediaDTO().getMedia5() != null) {
								mlDto.setMedia5(ml.getMediaDTO().getMedia5());
						}
					}
					mlsDto.add(mlDto);
				}
				if (mlsDto != null && !mlsDto.isEmpty()) {
					comPrefDto.setMarketLanguageDTO(mlsDto);
				}
			}
            comPrefsDto.add(comPrefDto);
			individuDto.setCommunicationpreferencesdto(comPrefsDto);
		}
		
		return individuDto;
	}
	

	public static RequestorDTO getRequestorProspectToIndiv(CreateUpdateIndividualRequestDTO request) {
		RequestorDTO requestor = new RequestorDTO();
		requestor.setChannel("B2C");
		
		if (request != null) {
			if (request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualProfilDTO() !=  null) {
				if (request.getIndividualRequestDTO().getIndividualProfilDTO().getCarrierCode() !=  null) {
					requestor.setManagingCompany(request.getIndividualRequestDTO().getIndividualProfilDTO().getCarrierCode());
				}
			}
			if (request.getRequestorDTO() != null) {
				if (request.getRequestorDTO().getApplicationCode() != null) {
					requestor.setApplicationCode(request.getRequestorDTO().getApplicationCode());
				} else {
					requestor.setApplicationCode("B2C");
				}
				if (request.getRequestorDTO().getContext() != null) {
					requestor.setContext(request.getRequestorDTO().getContext());			
				}
				if (request.getRequestorDTO().getIpAddress() != null) {
					requestor.setIpAddress(request.getRequestorDTO().getIpAddress());	
				}	
				if (request.getRequestorDTO().getSignature() !=  null) {
					requestor.setSignature(request.getRequestorDTO().getSignature());	
				}
				if (request.getRequestorDTO().getSite() !=  null) {
					requestor.setSite(request.getRequestorDTO().getSite());	
				}
			}
		}
		return requestor;
	}
	
	public static IndividualRequestDTO getIndRequestProspectToIndiv(CreateUpdateIndividualRequestDTO request, String gin) {
		//IndividualRequest
		IndividualRequestDTO indReq = new IndividualRequestDTO();
		//IndividualInformations
		IndividualInformationsDTO indInfo = new IndividualInformationsDTO();
		
		if (request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO() != null) {
			indInfo.setNationality(request.getIndividualRequestDTO().getIndividualInformationsDTO().getNationality());
			indInfo.setSecondNationality(request.getIndividualRequestDTO().getIndividualInformationsDTO().getSecondNationality());
			indInfo.setSecondFirstName(request.getIndividualRequestDTO().getIndividualInformationsDTO().getMiddleNameSC());
		}
		indInfo.setIdentifier(gin);
		
		// First name, second name and birth date are skipped, otherwise SIMULTANEOUS UPDATE error is raised
		if (request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualProfilDTO() != null && request.getIndividualRequestDTO().getIndividualProfilDTO().getLanguageCode() != null) {
			indInfo.setLanguageCode(request.getIndividualRequestDTO().getIndividualProfilDTO().getLanguageCode());			
		}
		
		indReq.setIndividualInformationsDTO(indInfo);
		
		return indReq;
	}
	
	public static List<CommunicationPreferencesRequestDTO> getCommPrefRequestProspectToIndiv(CreateUpdateIndividualRequestDTO request){
		List<CommunicationPreferencesRequestDTO> listCpReq = new ArrayList<CommunicationPreferencesRequestDTO>();
		CommunicationPreferencesRequestDTO cpReq = new CommunicationPreferencesRequestDTO();
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO cpOut = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();

		if (request.getCommunicationPreferencesRequestDTO() != null && request.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO() != null) {
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO cp = request.getCommunicationPreferencesRequestDTO().get(0).getCommunicationPreferencesDTO();

			if (cp.getCommunicationGroupeType() != null) {
				cpOut.setCommunicationGroupeType(cp.getCommunicationGroupeType());
			}
			else {
				// in the previous version it was .setComType(null)... fixed
				cpOut.setCommunicationGroupeType(null);
			}
			if (cp.getCommunicationType() != null) {
				cpOut.setCommunicationType(cp.getCommunicationType());
			}
			else {
				cpOut.setCommunicationType(null);
			}
			if (cp.getDateOfConsent() != null) {
				cpOut.setDateOfConsent(cp.getDateOfConsent());
			}
			else {
				cpOut.setDateOfConsent(null);
			}
			if (cp.getDateOfConsentPartner() != null) {
				cpOut.setDateOfConsentPartner(cp.getDateOfConsentPartner());
			}
			else {
				cpOut.setDateOfConsentPartner(null);
			}
			if (cp.getDateOfEntry() != null) {
				cpOut.setDateOfEntry(cp.getDateOfEntry());
			}
			else {
				cpOut.setDateOfEntry(null);
			}
			if (cp.getDomain() != null) {
				cpOut.setDomain(cp.getDomain());
			}
			else {
				cpOut.setDomain(null);
			}
			if (cp.getOptIn() != null) {
				cpOut.setOptIn(cp.getOptIn());
			}
			else {
				cpOut.setOptIn(null);
			}
			if (cp.getOptInPartner() != null) {
				cpOut.setOptInPartner(cp.getOptInPartner());
			}
			else{
				cpOut.setOptInPartner(null);
			}
			if (cp.getSubscriptionChannel() != null) {
				cpOut.setSubscriptionChannel(cp.getSubscriptionChannel());
			}
			else {
				cpOut.setSubscriptionChannel(null);
			}
			if (cp.getMediaDTO() != null) {
				MediaDTO media = new MediaDTO();

				if (cp.getMediaDTO().getMedia1() != null) {
					media.setMedia1(cp.getMediaDTO().getMedia1());
				}
				else {
					media.setMedia1(null);
				}
				if (cp.getMediaDTO().getMedia2() != null) {
					media.setMedia2(cp.getMediaDTO().getMedia2());
				}
				else {
					media.setMedia2(null);
				}
				if (cp.getMediaDTO().getMedia3() != null) {
					media.setMedia3(cp.getMediaDTO().getMedia3());
				}
				else {
					media.setMedia3(null);
				}
				if (cp.getMediaDTO().getMedia4() != null) {
					media.setMedia4(cp.getMediaDTO().getMedia4());
				}
				else {
					media.setMedia4(null);
				}
				if (cp.getMediaDTO().getMedia5() != null) {
					media.setMedia5(cp.getMediaDTO().getMedia5());
				}
				else {
					media.setMedia5(null);
				}

				cpOut.setMediaDTO(media);
			}
			if (cp.getMarketLanguageDTO() != null) {
				com.airfrance.repind.dto.ws.MarketLanguageDTO ml = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
				if (cp.getMarketLanguageDTO().get(0).getMarket() != null) {
					ml.setMarket(cp.getMarketLanguageDTO().get(0).getMarket());
				}
				if (cp.getMarketLanguageDTO().get(0).getLanguage() != null) {
					ml.setLanguage(cp.getMarketLanguageDTO().get(0).getLanguage());
				}
				if (cp.getMarketLanguageDTO().get(0).getOptIn() != null) {
					ml.setOptIn(cp.getMarketLanguageDTO().get(0).getOptIn());
				}
				if (cp.getMarketLanguageDTO().get(0).getDateOfConsent() != null) {
					ml.setDateOfConsent(cp.getMarketLanguageDTO().get(0).getDateOfConsent());
				}
				if (cp.getMarketLanguageDTO().get(0).getMediaDTO() != null) {
					MediaDTO med = new MediaDTO();
					if (cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia1() != null) {
						med.setMedia1(cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia1());
					}
					if (cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia2() != null) {
						med.setMedia2(cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia2());
					}
					if (cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia3() != null) {
						med.setMedia3(cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia3());
					}
					if (cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia4() != null) {
						med.setMedia4(cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia4());
					}
					if (cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia5() != null) {
						med.setMedia5(cp.getMarketLanguageDTO().get(0).getMediaDTO().getMedia5());
					}
					ml.setMediaDTO(med);
				}
				
				if(cpOut.getMarketLanguageDTO() == null) {
					cpOut.setMarketLanguageDTO(new ArrayList<com.airfrance.repind.dto.ws.MarketLanguageDTO>());
				}
				cpOut.getMarketLanguageDTO().add(ml);
			}
			cpReq.setCommunicationPreferencesDTO(cpOut);
			
			listCpReq.add(cpReq);
		}
		else {
			return null;
		}
		
		return listCpReq;
	}

	public static List<TelecomRequestDTO> getTelecomRequestProspectToIndiv(CreateUpdateIndividualRequestDTO request)
			throws InvalidParameterException, MissingParameterException {
		
		List<TelecomRequestDTO> listTelReq = new ArrayList<TelecomRequestDTO>();
		TelecomRequestDTO telReq = null;

		if ((request.getTelecomRequestDTO() != null || !request.getTelecomRequestDTO().isEmpty()) && request.getTelecomRequestDTO().get(0).getTelecomDTO() != null ) {
			telReq = new TelecomRequestDTO();
			TelecomDTO tel = new TelecomDTO();

			if (request.getTelecomRequestDTO().get(0).getTelecomDTO().getCountryCode() != null) {
				tel.setCountryCode(request.getTelecomRequestDTO().get(0).getTelecomDTO().getCountryCode());
			}
			else {
				tel.setCountryCode(null);
			}
			if(request.getTelecomRequestDTO().get(0).getTelecomDTO().getMediumStatus() != null) {
				tel.setMediumStatus(request.getTelecomRequestDTO().get(0).getTelecomDTO().getMediumStatus());
			}
			else {
				tel.setMediumStatus(null);
			}
			if(request.getTelecomRequestDTO().get(0).getTelecomDTO().getPhoneNumber() != null) {
				tel.setPhoneNumber(request.getTelecomRequestDTO().get(0).getTelecomDTO().getPhoneNumber());
			}
			else {
				tel.setPhoneNumber(null);
			}
			if (request.getTelecomRequestDTO().get(0).getTelecomDTO().getTerminalType() != null) {
				tel.setTerminalType(TerminalTypeEnum
						.getEnumMandatory(request.getTelecomRequestDTO().get(0).getTelecomDTO().getTerminalType())
						.toString());
			}
			else {
				tel.setTerminalType(null);
			}
			// in the requestV2 MediumCode is not present, but it is mandatory for the WS, so it's set to D
			if (request.getTelecomRequestDTO().get(0).getTelecomDTO().getMediumCode() != null) {
				tel.setMediumCode(request.getTelecomRequestDTO().get(0).getTelecomDTO().getMediumCode());
			}
			else {
				tel.setMediumCode("D");
			}

			telReq.setTelecomDTO(tel);
			
			listTelReq.add(telReq);
		} else {
			return null;
		}
		
		return listTelReq;
	}
	
	public static PreferenceRequestDTO getPreferenceRequestProspectToIndiv(CreateUpdateIndividualRequestDTO request) throws InvalidParameterException {
		
		if (request.getPreferenceRequestDTO() != null && request.getPreferenceRequestDTO().getPreferenceDTO() != null 
				&& request.getPreferenceRequestDTO().getPreferenceDTO().size() > 0 && request.getPreferenceRequestDTO().getPreferenceDTO().get(0).getPreferencesDatasDTO() != null) {

			for (com.airfrance.repind.dto.ws.PreferenceDataDTO prefData : request.getPreferenceRequestDTO().getPreferenceDTO().get(0).getPreferencesDatasDTO().getPreferenceDataDTO()) {
				if ("TYPE".equalsIgnoreCase(prefData.getKey())) {
					prefData.setValue("BDM");
				}
			}
			
		}
		return request.getPreferenceRequestDTO();
	}
	
	/**
	 * Cette fonction permet d'affecter les signatures à toutes les entity d'un prospect dans le cadre d'une création de prospect
	 * La signature comprends:
	 * -creationSignature
	 * -creationSite
	 * -creationDate
	 * dans chacune des tables :
	 * -prospect
	 * -prospectlocalization
	 * -prospecttelecoms
	 * -communicationpreferences
	 * -marketlanguage
	 * @param prospect
	 * @param requestor
	 * @return
	 */
	// REPIND-555: Prospect migration
	public static IndividuDTO transformSignatureToDtoProspectCreation(IndividuDTO prospect, RequestorDTO requestor){
		//affectation de la signature de création
		if(requestor.getSignature()!=null && !"".equals(requestor.getSignature())){
			prospect.setSignatureCreation(requestor.getSignature());
			if(prospect.getPostaladdressdto()!=null) {
				if(prospect.getPostaladdressdto().get(0)!=null) {
					prospect.getPostaladdressdto().get(0).setSignature_creation(requestor.getSignature());
				}
			}
			if(prospect.getTelecoms()!=null) {
				if(prospect.getTelecoms().size() > 0) {
					prospect.getTelecoms().iterator().next().setSsignature_creation(requestor.getSignature());
				}
			}
			if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
				for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
					comPrefDto.setCreationSignature(requestor.getSignature());
					if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
						for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
							mlDto.setCreationSignature(requestor.getSignature());
						}
					}
				}
			}		
			if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
				prospect.getEmaildto().iterator().next().setSignatureCreation(requestor.getSignature());
			}
			if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
				for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
					prefDto.setSignatureCreation(requestor.getSignature());
				}
			}
		}
		//affectation du site de création
		if(requestor.getSite()!=null && !"".equals(requestor.getSite())){
			prospect.setSiteCreation(requestor.getSite());
			if(prospect.getPostaladdressdto()!=null) {
				if(prospect.getPostaladdressdto().get(0)!=null) {
					prospect.getPostaladdressdto().get(0).setSsite_creation(requestor.getSite());
				}
			}
			if(prospect.getTelecoms()!=null) {
				if(prospect.getTelecoms().iterator().next()!=null) {
					prospect.getTelecoms().iterator().next().setSsite_creation(requestor.getSite());
				}
			}
			if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
				for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
					comPrefDto.setCreationSite(requestor.getSite());
					if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
						for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
							mlDto.setCreationSite(requestor.getSite());
						}
					}
				}
			}	
			if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
				prospect.getEmaildto().iterator().next().setSiteCreation(requestor.getSite());
			}
			if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
				for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
					prefDto.setSiteCreation(requestor.getSite());
				}
			}
		}
		//affectation de la date de creation
		prospect.setDateCreation(new Date());
		if(prospect.getPostaladdressdto()!=null) {
			if(prospect.getPostaladdressdto().get(0)!=null) {
				prospect.getPostaladdressdto().get(0).setDdate_creation(new Date());
			}
		}
		if(prospect.getTelecoms()!=null) {
			if(prospect.getTelecoms().iterator().next()!=null) {
				prospect.getTelecoms().iterator().next().setDdate_creation(new Date());
			}
		}
		if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
			for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
				comPrefDto.setCreationDate(new Date());
				if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
					for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
						mlDto.setCreationDate(new Date());
					}
				}
			}
		}
		if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
			prospect.getEmaildto().iterator().next().setDateCreation(new Date());
		}
		if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
			for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
				prefDto.setDateCreation(new Date());
			}
		}

		return prospect;
	}

	/**
	 * Cette fonction permet d'affecter les signatures à toutes les entity d'un prospect dans le cadre d'une modification de prospect
	 * La signature comprends:
	 * -modificationSignature
	 * -modificationSite
	 * -modificationDate
	 * dans chacune des tables :
	 * -prospect
	 * -prospectlocalization
	 * -prospecttelecoms
	 * -communicationpreferences
	 * -marketlanguage
	 * @param prospect
	 * @param requestor
	 * @return
	 */
	// REPIND-555: Prospect migration
	public static IndividuDTO transformSignatureToDtoProspectUpdate(IndividuDTO prospect, RequestorDTO requestor){
		//affectation de la signature de modification
		if(requestor.getSignature()!=null && !"".equals(requestor.getSignature())){
			prospect.setSignatureModification(requestor.getSignature());
			if(prospect.getPostaladdressdto()!=null) {
				if(prospect.getPostaladdressdto().get(0)!=null) {
					prospect.getPostaladdressdto().get(0).setSsignature_modification(requestor.getSignature());
				}
			}
			if(prospect.getTelecoms()!=null) {
				if(prospect.getTelecoms().iterator().next()!=null){
					prospect.getTelecoms().iterator().next().setSsignature_modification(requestor.getSignature());
				}
			}
			if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
				for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
					comPrefDto.setModificationSignature(requestor.getSignature());
					if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
						for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {

							mlDto.setModificationSignature(requestor.getSignature());
						}
					}
				}
			}	
			if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
				prospect.getEmaildto().iterator().next().setSignatureModification(requestor.getSignature());
			}
			if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
				for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
					prefDto.setSignatureModification(requestor.getSignature());
				}
			}
		}
		//affectation du site de modification
		if(requestor.getSite()!=null && !"".equals(requestor.getSite())){
			prospect.setSiteModification(requestor.getSite());
			if(prospect.getPostaladdressdto()!=null) {
				if(prospect.getPostaladdressdto().get(0)!=null) {
					prospect.getPostaladdressdto().get(0).setSsite_modification(requestor.getSite());
				}
			}
			if(prospect.getTelecoms()!=null) {
				if(prospect.getTelecoms().iterator().next()!=null) {
					prospect.getTelecoms().iterator().next().setSsite_modification(requestor.getSite());
				}
			}
			if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
				for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
					comPrefDto.setModificationSite(requestor.getSite());
					if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
						for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
							mlDto.setModificationSite(requestor.getSite());
						}
					}
				}
			}	
			if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
				prospect.getEmaildto().iterator().next().setSiteModification(requestor.getSite());
			}
			if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
				for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
					prefDto.setSiteModification(requestor.getSite());
				}
			}
		}
		//affectation de la date de creation
		prospect.setDateModification(new Date());
		if(prospect.getPostaladdressdto()!=null) {
			if(prospect.getPostaladdressdto().get(0)!=null) {
				prospect.getPostaladdressdto().get(0).setDdate_modification(new Date());
			}
		}
		if(prospect.getTelecoms()!=null) {
			if(prospect.getTelecoms().iterator().next()!=null) {
				prospect.getTelecoms().iterator().next().setDdate_modification(new Date());
			}
		}
		if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
			for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
				comPrefDto.setModificationDate(new Date());
				if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
					for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
						mlDto.setModificationDate(new Date());
					}
				}
			}
		}
		if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
			prospect.getEmaildto().iterator().next().setDateModification(new Date());
		}
		if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
			for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
				prefDto.setDateModification(new Date());
			}
		}

	return prospect;
	}
	
	// REPIND-555: Prospect migration
	public static IndividuDTO transformStatusToDtoProspectCreation(IndividuDTO prospect){
		if((prospect.getStatutIndividu())==null){
			prospect.setStatutIndividu("V");
		}
		if(prospect.getPostaladdressdto()!=null){
			if(prospect.getPostaladdressdto().get(0)!=null) {
				if(prospect.getPostaladdressdto().get(0).getScode_medium()==null)
					prospect.getPostaladdressdto().get(0).setScode_medium("D");
				if(prospect.getPostaladdressdto().get(0).getSstatut_medium()==null)
					prospect.getPostaladdressdto().get(0).setSstatut_medium("V");
			}
		}
		if(prospect.getTelecoms()!=null){
			if(prospect.getTelecoms().iterator().next().getScode_medium()==null)
				prospect.getTelecoms().iterator().next().setScode_medium("D");
			if(prospect.getTelecoms().iterator().next().getSstatut_medium()==null)
				prospect.getTelecoms().iterator().next().setSstatut_medium("V");
			if(prospect.getTelecoms().iterator().next().getSterminal()==null)
				prospect.getTelecoms().iterator().next().setSterminal("T");
		}
		return prospect;
	}

	public static List<TelecomsDTO> transformToProspectTelecomsDTO(List<TelecomRequestDTO> telecomRequest) throws InvalidParameterException, MissingParameterException {
		List<TelecomsDTO> dtos = new ArrayList<TelecomsDTO>();
		if (telecomRequest == null) {
			return null;
		}

		if (telecomRequest.size() > 1) {
			throw new InvalidParameterException("Only 1 telcom allowed for prospect");
		}

		TelecomDTO tel = telecomRequest.get(0).getTelecomDTO();

		if (tel == null) {
			return null;
		}
		else {
			TelecomsDTO dto = new TelecomsDTO();
			dto.setCountryCode(tel.getCountryCode());
			dto.setScode_region(tel.getCountryCode());
			dto.setSindicatif(tel.getCountryCode());
			dto.setSnumero(tel.getPhoneNumber());
			dto.setSterminal(TerminalTypeEnum.getEnumMandatory(tel.getTerminalType()).toString());
			dto.setSstatut_medium(tel.getMediumStatus());
			dtos.add(dto);
		}
		return dtos;
	}
	
	public static SignatureDTO transformToProspectSignatureAPP(RequestorDTO requestor) {
		
		Date today = new Date();
		
		SignatureDTO dto = new SignatureDTO();
		dto.setDate(today);
		dto.setSignature(requestor.getSignature());
		dto.setSite(requestor.getSite());
		dto.setTypeSignature(null);
		
		return dto;
	}
	
	public static SignatureDTO transformToProspectSignatureWS(String webserviceIdentifier, String site) {
		
		Date today = new Date();
		
		SignatureDTO dto = new SignatureDTO();
		dto.setDate(today);
		dto.setSignature(webserviceIdentifier);
		dto.setSite(site);
		
		return dto;
	}
	
	// ===== COMMUNICATION PREFERENCES ==============================================================
	
	public static List<CommunicationPreferencesDTO> transformToComPrefsDTO(List<CommunicationPreferencesRequestDTO> adh,RequestorDTO requestor) {

		if (adh == null) {
			return null;
		}
		
		List<CommunicationPreferencesDTO> dto = null;
		if (!adh.isEmpty()) {
			dto = new ArrayList<CommunicationPreferencesDTO>();
			for (CommunicationPreferencesRequestDTO comPrefRequest : adh) {
				dto.add(transformToComPrefDTO(comPrefRequest, requestor));
			}
		}
		return dto;
	}
	

	public static CommunicationPreferencesDTO transformToComPrefDTO(CommunicationPreferencesRequestDTO comPrefRequest, RequestorDTO requestor) {
		com.airfrance.repind.dto.ws.CommunicationPreferencesDTO comPref = comPrefRequest
				.getCommunicationPreferencesDTO();

		CommunicationPreferencesDTO comPrefDto = new CommunicationPreferencesDTO();
		if (comPref.getDomain() != null)
			comPrefDto.setDomain(comPref.getDomain());
		if (comPref.getCommunicationGroupeType() != null)
			comPrefDto.setComGroupType(comPref
					.getCommunicationGroupeType());
		if (comPref.getCommunicationType() != null)
			comPrefDto.setComType(comPref.getCommunicationType());
		if (comPref.getOptIn() != null)
			comPrefDto.setSubscribe(comPref.getOptIn());

		if (comPref.getMediaDTO() != null
				&& comPref.getMediaDTO().getMedia1() != null)
			comPrefDto.setMedia1(comPref.getMediaDTO().getMedia1());
		if (comPref.getMediaDTO() != null
				&& comPref.getMediaDTO().getMedia2() != null)
			comPrefDto.setMedia2(comPref.getMediaDTO().getMedia1());
		if (comPref.getMediaDTO() != null
				&& comPref.getMediaDTO().getMedia3() != null)
			comPrefDto.setMedia3(comPref.getMediaDTO().getMedia1());
		if (comPref.getMediaDTO() != null
				&& comPref.getMediaDTO().getMedia4() != null)
			comPrefDto.setMedia4(comPref.getMediaDTO().getMedia1());
		if (comPref.getMediaDTO() != null
				&& comPref.getMediaDTO().getMedia5() != null)
			comPrefDto.setMedia5(comPref.getMediaDTO().getMedia1());
		if (comPref.getSubscriptionChannel() != null)
			comPrefDto.setChannel(comPref.getSubscriptionChannel());
		if (comPref.getDateOfConsent() != null)
			comPrefDto.setDateOptin(comPref.getDateOfConsent());
		if (comPref.getDateOfConsentPartner() != null)
			comPrefDto.setDateOptinPartners(comPref
					.getDateOfConsentPartner());
		if (comPref.getOptInPartner() != null)
			comPrefDto.setOptinPartners(comPref.getOptInPartner());

		if (comPref.getMarketLanguageDTO() != null) {
			Set<MarketLanguageDTO> mlsDto = new HashSet<MarketLanguageDTO>();

			for (com.airfrance.repind.dto.ws.MarketLanguageDTO ml : comPref.getMarketLanguageDTO()) {
				MarketLanguageDTO mlDto = new MarketLanguageDTO();
				List<com.airfrance.repind.dto.ws.SignatureDTO> signatureDTOList = ml.getSignatureDTOList();
				if (ml.getOptIn() != null)
					mlDto.setOptIn(ml.getOptIn());
				if (ml.getLanguage() != null)
					mlDto.setLanguage(ml.getLanguage());
				if (ml.getMarket() != null)
					mlDto.setMarket(ml.getMarket());
				if (ml.getDateOfConsent() != null)
					mlDto.setDateOfConsent(ml.getDateOfConsent());
				if (ml.getMediaDTO() != null) {
					if (ml.getMediaDTO().getMedia1() != null)
						mlDto.setMedia1(ml.getMediaDTO().getMedia1());
					if (ml.getMediaDTO().getMedia2() != null)
						mlDto.setMedia2(ml.getMediaDTO().getMedia2());
					if (ml.getMediaDTO().getMedia3() != null)
						mlDto.setMedia3(ml.getMediaDTO().getMedia3());
					if (ml.getMediaDTO().getMedia4() != null)
						mlDto.setMedia4(ml.getMediaDTO().getMedia4());
					if (ml.getMediaDTO().getMedia5() != null)
						mlDto.setMedia5(ml.getMediaDTO().getMedia5());
				}
				if (signatureDTOList != null){
					for(com.airfrance.repind.dto.ws.SignatureDTO signatureDTO : signatureDTOList){
						if (SignatureTypeEnum.CREATION.toString().equals(signatureDTO.getSignatureType())) {
							mlDto.setModificationDate(signatureDTO.getDate());
							mlDto.setModificationSignature(signatureDTO.getSignature());
							mlDto.setModificationSite(signatureDTO.getSignatureSite());
						} else if (SignatureTypeEnum.MODIFICATION.toString().equals(signatureDTO.getSignatureType())) {
							mlDto.setCreationDate(signatureDTO.getDate());
							mlDto.setCreationSignature(signatureDTO.getSignature());
							mlDto.setCreationSite(signatureDTO.getSignatureSite());
						}
					}
				}else{
					mlDto.setCreationDate(new Date());
					mlDto.setCreationSignature(requestor.getSignature());
					mlDto.setCreationSite(requestor.getSite());

					mlDto.setModificationDate(new Date());
					mlDto.setModificationSignature(requestor.getSignature());
					mlDto.setModificationSite(requestor.getSite());
				}


				mlsDto.add(mlDto);
			}
			comPrefDto.setCreationDate(new Date());
			comPrefDto.setCreationSignature(requestor.getSignature());
			comPrefDto.setCreationSite(requestor.getSite());
			
			comPrefDto.setModificationDate(new Date());
			comPrefDto.setModificationSignature(requestor.getSignature());
			comPrefDto.setModificationSite(requestor.getSite());
			
			comPrefDto.setMarketLanguageDTO(mlsDto);
		}
		return comPrefDto;
	}
	
	// ===== ALERT============================================================================
	public static List<AlertDTO> transformToAlertDTO(AlertRequestDTO ws) {
		
		if (ws==null || ws.getAlertDTO() == null) {
			return null;
		}
		List<AlertDTO> dtoList = new ArrayList<AlertDTO>();
		
		for(com.airfrance.repind.dto.ws.AlertDTO al : ws.getAlertDTO()) {
			dtoList.add(transformToAlertDTO(al));
		}
		
		return dtoList;
	}
	

	public static AlertDTO transformToAlertDTO(com.airfrance.repind.dto.ws.AlertDTO ws) {
		
		if (ws==null) {
			return null;
		}

		AlertDTO dto = new AlertDTO();
		if(ws.getAlertId() != 0){
			dto.setAlertId(ws.getAlertId());
		}
		dto.setType(ws.getType());
		dto.setOptIn(ws.getOptin());
		dto.setAlertDataDTO(transformToAlertDataDTO(ws.getAlertDataDTO()));
		return dto;
	}
	
	public static Set<AlertDataDTO> transformToAlertDataDTO(List<com.airfrance.repind.dto.ws.AlertDataDTO> wsList) {
		
		if(wsList==null || wsList.isEmpty()) {
			return null;
		}
		
		Set<AlertDataDTO> dtoList = new HashSet<AlertDataDTO>();
		
		for(com.airfrance.repind.dto.ws.AlertDataDTO ws : wsList) {
			dtoList.add(transformToAlertDataDTO(ws));
		}
		
		return dtoList;
		
	}
	
	public static AlertDataDTO transformToAlertDataDTO(com.airfrance.repind.dto.ws.AlertDataDTO ws) {
		
		if (ws==null) {
			return null;
		}

		AlertDataDTO dto = new AlertDataDTO();
		dto.setKey(ws.getKey());
		dto.setValue(ws.getValue());
		
		return dto;
	}
	
	// ===== DELEGATION DATA ==============================================================
	// ===== DELEGATE =====================================================================
	public static List<DelegationDataDTO> transformToDelegate(AccountDelegationDataRequestDTO ws, String gin) throws InvalidParameterException {
		
		
		if(ws==null || ws.getAccountDelegationDataDTO().getDelegateDTO() == null) {
			return null;
		}
		
		return transformToDelegate(ws.getAccountDelegationDataDTO().getDelegateDTO(), gin);	
	}

	public static List<DelegationDataDTO> transformToDelegate(List<DelegateDTO> wsList, String gin) throws InvalidParameterException {
		
		if(wsList==null) {
			return null;
		}
		
		List<DelegationDataDTO> dtoList = new ArrayList<DelegationDataDTO>();

		for(DelegateDTO ws : wsList) {
			DelegationDataDTO delegationDTO = transformToDelegate(ws.getDelegateDataDTO(), gin);
			addComplementatyInformation(delegationDTO, ws.getComplementaryInformationDTO());
			dtoList.add(delegationDTO); 
		}
		
		return dtoList;
	}
	
	/**
	 * This method transform requested complementaryInformation from delegate to
	 * DTO to save it into DelegationDTO
	 * 
	 * @param delegationDTO
	 * @param complementaryInformationFromRequest
	 * @return
	 */
	public static void addComplementatyInformation(DelegationDataDTO delegationDTO,
			List<com.airfrance.repind.dto.ws.ComplementaryInformationDTO> complementaryInformationFromRequest) throws InvalidParameterException {

		// REPIND-1804 : Prevent NullPointer
		if (complementaryInformationFromRequest == null || complementaryInformationFromRequest.isEmpty()) {
			return;
		}
		
		Set<DelegationDataInfoDTO> delegationDataInfoDTOs = new HashSet<DelegationDataInfoDTO>();
		
		for (int i = 0; i < complementaryInformationFromRequest.size(); i++) {
			
			List<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO> complementaryInformationDatas = complementaryInformationFromRequest
					.get(i).getComplementaryInformationDatasDTO().getComplementaryInformationDataDTO();
			
			if(complementaryInformationDatas == null) {
				complementaryInformationDatas = new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO>();
			}
			
			for (int u = 0; u < complementaryInformationDatas.size(); u++) {
				
				DelegationDataInfoDTO delegationDataInfo = new DelegationDataInfoDTO();
				
				delegationDataInfo.setType(complementaryInformationFromRequest.get(i).getType());
				delegationDataInfo.setKey(complementaryInformationDatas.get(u).getKey());
				delegationDataInfo.setValue(complementaryInformationDatas.get(u).getValue());
				delegationDataInfo.setDateCreation(new Date());
				delegationDataInfo.setDateModification(new Date());
				delegationDataInfo.setSignatureCreation("TEST");
				delegationDataInfo.setSignatureModification("TEST");
				delegationDataInfo.setSiteCreation("QVI");
				delegationDataInfo.setTypeGroupId(i);
				
				delegationDataInfoDTOs.add(delegationDataInfo);
			}
		}		
		delegationDTO.setDelegationDataInfoDTO(delegationDataInfoDTOs);
	}

	
	public static DelegationDataDTO transformToDelegate(DelegateDataDTO ws, String gin) {
		
		if(ws==null) {
			return null;
		}
		
		DelegationDataDTO dto = new DelegationDataDTO();
		
		IndividuDTO delegate = new IndividuDTO();
		delegate.setSgin(ws.getGin());
		
		IndividuDTO delegator = new IndividuDTO();
		delegator.setSgin(gin); // individual is the manager
		
		dto.setType(ws.getDelegationType());
		dto.setStatus(ws.getDelegationAction());
		dto.setDelegateDTO(delegate);
		dto.setDelegatorDTO(delegator);
		
		return dto;		
	}
	
	// ===== DELEGATION DATA ==============================================================
	// ===== DELEGATOR =====================================================================
	public static List<DelegationDataDTO> transformToDelegator(AccountDelegationDataRequestDTO ws, String gin) {
		
		if(ws==null || ws.getAccountDelegationDataDTO().getDelegatorDTO() == null) {
			return null;
		}
		
		return transformToDelegator(ws.getAccountDelegationDataDTO().getDelegatorDTO(), gin);	
	}

	public static List<DelegationDataDTO> transformToDelegator(List<DelegatorDTO> wsList, String gin) {
		
		if(wsList==null) {
			return null;
		}
		
		List<DelegationDataDTO> dtoList = new ArrayList<DelegationDataDTO>();

		for(DelegatorDTO ws : wsList) {
			dtoList.add(transformToDelegator(ws.getDelegatorDataDTO(), gin)); 
		}
		
		return dtoList;
	}
	
	public static DelegationDataDTO transformToDelegator(com.airfrance.repind.dto.ws.DelegatorDataDTO ws, String gin) {
		
		if(ws==null) {
			return null;
		}
		
		DelegationDataDTO dto = new DelegationDataDTO();
		
		IndividuDTO delegate = new IndividuDTO();
		delegate.setSgin(gin);
		
		IndividuDTO delegator = new IndividuDTO();
		delegator.setSgin(ws.getGin());
		
		dto.setType(ws.getDelegationType());
		dto.setStatus(ws.getDelegationAction());
		dto.setDelegateDTO(delegate);
		dto.setDelegatorDTO(delegator);
		
		return dto;		
	}
	
	// PREFERENCES

	public static List<PreferenceDTO> transformToPreferenceDTO(PreferenceRequestDTO ws, RequestorDTO requestor) {
		if (ws == null) {
			return null;
		}
		
		List<PreferenceDTO> dtoList = new ArrayList<>();
		if (ws.getPreferenceDTO() != null) {
			for (com.airfrance.repind.dto.ws.PreferenceDTO pref : ws.getPreferenceDTO()) {
				PreferenceDTO prefDTO = new PreferenceDTO();
				if (pref.getId() != null) {
					prefDTO.setPreferenceId(Long.valueOf(pref.getId()));
				}
				if (pref.getType() != null) {
					prefDTO.setType(pref.getType());
				}
				if (pref.getLink() != null) {
					prefDTO.setLink(Integer.valueOf(String.valueOf(pref.getLink())));
				}
				
				if (pref.getPreferencesDatasDTO() != null && pref.getPreferencesDatasDTO().getPreferenceDataDTO() != null && !pref.getPreferencesDatasDTO().getPreferenceDataDTO().isEmpty()) {
					Set<PreferenceDataDTO> prefDataDTOList = new HashSet<>();
					for (com.airfrance.repind.dto.ws.PreferenceDataDTO prefData : pref.getPreferencesDatasDTO().getPreferenceDataDTO()) {
						PreferenceDataDTO prefDataDTO = new PreferenceDataDTO();
						if (prefData.getKey() != null) {
							prefDataDTO.setKey(prefData.getKey());
						}
						if (prefData.getValue() != null) {
							prefDataDTO.setValue(prefData.getValue());
						}
						
						prefDataDTOList.add(prefDataDTO);
					}
					prefDTO.setPreferenceDataDTO(prefDataDTOList);
				}
				dtoList.add(prefDTO);
			}
		}
		return dtoList;
	}

	// ===== PREFILLED NUMBERS ==============================================================
	
	public static List<PrefilledNumbersDTO> wsdlTOPrefilledNumbersdto(List<PrefilledNumbersRequestDTO> adh) {

		if(adh==null || adh.isEmpty()) {
			return null;
		}
		
		List<PrefilledNumbersDTO> dto = new ArrayList<PrefilledNumbersDTO>();			
		for (PrefilledNumbersRequestDTO prefilledNumbersRequest : adh) {
			
			com.airfrance.repind.dto.ws.PrefilledNumbersDTO prefilledNumbers = prefilledNumbersRequest.getPrefilledNumbersDTO();
			
			PrefilledNumbersDTO prefilledNumbersDTO = new PrefilledNumbersDTO();
			prefilledNumbersDTO.setContractNumber(prefilledNumbers.getContractNumber());
			prefilledNumbersDTO.setContractType(prefilledNumbers.getContractType());
			dto.add(prefilledNumbersDTO);
		}
		return dto;
	}
	
	// ===== EXTERNAL IDENTIFIERS ==============================================================
	
	public static List<ExternalIdentifierDTO> transformToExternalIdentifierDTO(List<ExternalIdentifierRequestDTO> wsList) {
		
		if(wsList==null || wsList.isEmpty()) {
			return null;
		}
		
		List<ExternalIdentifierDTO> dtoList = new ArrayList<ExternalIdentifierDTO>();
		
		for(ExternalIdentifierRequestDTO ws : wsList) {
			dtoList.add(transformToExternalIdentifierDTO(ws));
		}
		
		return dtoList;		
	}
	
	public static ExternalIdentifierDTO transformToExternalIdentifierDTO(ExternalIdentifierRequestDTO ws) {
		
		if (ws==null) {
			return null;
		}

		ExternalIdentifierDTO dto = transformToExternalIdentifierDTO(ws.getExternalIdentifierDTO());
		// REPIND-1003 : Repair blocker
		if(dto != null) {
			dto.setExternalIdentifierDataList(transformToExternalIdentifierDataDTO(ws.getExternalIdentifierDataDTO()));
		}
		
		return dto;
	}
	
	public static ExternalIdentifierDTO transformToExternalIdentifierDTO(com.airfrance.repind.dto.ws.ExternalIdentifierDTO ws) {
		
		if (ws==null) {
			return null;
		}

		ExternalIdentifierDTO dto = new ExternalIdentifierDTO();
		dto.setIdentifier(ws.getIdentifier());
		dto.setType(ws.getType());
		
		return dto;
	}
	
	public static List<ExternalIdentifierDataDTO> transformToExternalIdentifierDataDTO(List<com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO> wsList) {
		
		if(wsList==null || wsList.isEmpty()) {
			return null;
		}
		
		List<ExternalIdentifierDataDTO> dtoList = new ArrayList<ExternalIdentifierDataDTO>();
		
		for(com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO ws : wsList) {
			dtoList.add(transformToExternalIdentifierDataDTO(ws));
		}
		
		return dtoList;
		
	}
		
	public static ExternalIdentifierDataDTO transformToExternalIdentifierDataDTO(com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO ws) {
		
		if (ws==null) {
			return null;
		}

		ExternalIdentifierDataDTO dto = new ExternalIdentifierDataDTO();
		dto.setKey(ws.getKey());
		dto.setValue(ws.getValue());
		
		return dto;
	}
	
}
