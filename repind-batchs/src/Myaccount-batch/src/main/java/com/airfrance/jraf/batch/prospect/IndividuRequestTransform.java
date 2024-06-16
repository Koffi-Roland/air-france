package com.airfrance.jraf.batch.prospect;

import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.Delegate;
import com.afklm.soa.stubs.w000442.v8.request.Delegator;
import com.afklm.soa.stubs.w000442.v8.request.*;
import com.afklm.soa.stubs.w000442.v8.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.afklm.soa.stubs.w000442.v8.softcomputingtype.SoftComputingResponse;
import com.airfrance.ref.exception.BadDateFormatException;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftRequestDTO;
import com.airfrance.repind.dto.adresse.adh.NormalisationSoftResponseDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.AdressePostaleDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InfosIndividuDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.dto.ws.TelecomDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
// import com.airfrance.sicutf8.dto.prospect.ProspectCommunicationPreferencesDTO;
// import com.airfrance.sicutf8.dto.prospect.ProspectMarketLanguageDTO;

public class IndividuRequestTransform {

	private static final String SOFTCOMPUTING_3 = "3";
	private static final String FORCAGE_VRAI = "O";
	
	// Transform Request to IndividuDTO
	public static IndividuDTO transformRequestToIndividuDTO(CreateUpdateIndividualRequest request) throws InvalidParameterException, MissingParameterException {
		
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
		}
		// Add context Type
		if (!"W".equalsIgnoreCase(request.getProcess()) && !"A".equalsIgnoreCase(request.getProcess())) {
			dto.setType(request.getProcess());
		}
		
		// Adding signature
		addSignatureToIndividuDTO(request.getRequestor(), dto);
		
		// Set Contact(s)
		transformListToSetEmailDTO(request.getEmailRequest(), dto);
		transformListToSetTelecomsDTO(request.getTelecomRequest(), dto);
		
		// Set External Identifier : On est dans le cas ou on cree un Individu donc pas les External Identifier
		// dto.setExternalIdentifierList(IndividuRequestTransformV7.transformToExternalIdentifierDTO(request.getExternalIdentifierRequest()));
	
		return dto;
	}
	
	private static IndividuDTO createDefaultEmptyIndividu(CreateUpdateIndividualRequest request) {
		IndividuDTO dto = new IndividuDTO();

		dto.setVersion(1);
		dto.setStatutIndividu("V");
		dto.setCivilite("MR");
		dto.setSexe("U");
		dto.setNonFusionnable("N");
		dto.setType(request.getProcess());
		// Adding signature
		addSignatureToIndividuDTO(request.getRequestor(), dto);

		return dto;
	}
	
	// ===== SIGNATURE ==============================================================
	
	
	
	
	
	// Dans le cas d un update , on veut pouvoir juste modifier les infos de Modification
	public static void addSignatureToIndividuDTO(RequestorV2 requestor, IndividuDTO dto) {
		
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

	
	public static SignatureDTO transformToSignatureAPP(RequestorV2 requestor) {
		
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

	public static IndividuDTO transformToIndividuDTO(CreateUpdateIndividualRequest ws) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		IndividuDTO dto = transformToIndividuDTO(ws.getIndividualRequest());
		
		return dto;
		
	}
	
	// ===== POSTAL ADDRESSES ==============================================================
	
	public static List<PostalAddressDTO> transformToPostalAddressDTO(List<PostalAddressRequest> wsList) throws InvalidParameterException {
		
		// REPIND-260 : SONAR - On test Null avant de tester Empty
		if(wsList == null || wsList.isEmpty()) {
			return null;
		}
		
		List<PostalAddressDTO> dtoList = new ArrayList<PostalAddressDTO>();
		
		for(PostalAddressRequest ws : wsList) {
			dtoList.add(transformToPostalAddressDTO(ws));
		}
		
		return dtoList;
	}
	
	public static PostalAddressDTO transformToPostalAddressDTO(PostalAddressRequest ws) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		PostalAddressDTO dto = new PostalAddressDTO();
		transformToPostalAddressDTO(ws.getPostalAddressContent(), dto);
		transformToPostalAddressDTO(ws.getPostalAddressProperties(), dto);
		dto.setPreferee(transformToPreferedAddress(ws.getUsageAddress())); // attention ce champs est primordial pour le calcul des usages
		dto.setUsage_mediumdto(transformToUsageMediumDTO(ws.getUsageAddress()));
		
		return dto;
	}
	
	public static void transformToPostalAddressDTO(PostalAddressContent ws, PostalAddressDTO dto) {
		
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
	
	public static void transformToPostalAddressDTO(PostalAddressProperties ws, PostalAddressDTO dto) throws InvalidParameterException {
		
		if(ws==null) {
			return;
		}
		
		if(ws.isIndicAdrNorm()!=null) {
		dto.setSforcage(OuiNonFlagEnum.getEnum(ws.isIndicAdrNorm()).toString());
		}
		
		dto.setScode_medium(ws.getMediumCode());
		dto.setSstatut_medium(ws.getMediumStatus());
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));
		
	}
	
	public static Set<Usage_mediumDTO> transformToUsageMediumDTO(UsageAddress ws) {
		
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
	
	public static boolean transformToPreferedAddress(UsageAddress ws) {
		
		if(ws==null || ws.getAddressRoleCode()==null) {
			return false;
		}
		
		// si c'est l'adresse principale alors c'est la pr�f�r�e
		return AddressRoleCodeEnum.PRINCIPAL.toString().equals(ws.getAddressRoleCode());
	}
	


	// ===== SOFT ==============================================================
	
	public static NormalisationSoftRequestDTO dtoTOSoftdto(AdressePostaleDTO adr) {
		
		NormalisationSoftRequestDTO request = new NormalisationSoftRequestDTO();
		request.setCity(adr.getVille());
		request.setComp(adr.getComplAdr());
		request.setCountry(adr.getCodePays());
		request.setLocal(adr.getLieuDit());
		request.setState(adr.getCodeProvince());
		request.setStreet(adr.getNumeroRue());
		request.setZip(adr.getCodePostal());
		return request;
	}
	
	
	public static PostalAddressResponse dtoTOPostal(AdressePostaleDTO postale) {
		PostalAddressResponse adr = new PostalAddressResponse();
		PostalAddressContent postalAddressContent = new PostalAddressContent();
		PostalAddressProperties postalAddressProperties = new PostalAddressProperties();

		postalAddressContent.setCountryCode(postale.getCodePays());
		postalAddressContent.setZipCode(postale.getCodePostal());
		postalAddressContent.setStateCode(postale.getCodeProvince());
		postalAddressContent.setAdditionalInformation(postale.getComplAdr());
		postalAddressContent.setDistrict(postale.getLieuDit());
		postalAddressContent.setNumberAndStreet(postale.getNumeroRue());
		postalAddressContent.setCorporateName(postale.getRaisonSociale());
		postalAddressContent.setCity(postale.getVille());
		
		postalAddressProperties.setMediumCode(postale.getCodeMedium());

		postalAddressProperties
				.setVersion(String.valueOf(postale.getVersion()));

		postalAddressProperties.setMediumStatus(postale.getStatutMedium());

		if (FORCAGE_VRAI.equalsIgnoreCase(postale.getIndicAdrNorm()))
			postalAddressProperties.setIndicAdrNorm(true);
		else
			postalAddressProperties.setIndicAdrNorm(false);
		
		adr.setPostalAddressContent(postalAddressContent);
		adr.setPostalAddressProperties(postalAddressProperties);
		
		return adr;
	}
	
public static PostalAddressResponse softTOPostal(NormalisationSoftResponseDTO soft, PostalAddressResponse par) {
		
		PostalAddressResponse adr = par;

		PostalAddressProperties properties = adr.getPostalAddressProperties();
		PostalAddressContent content = adr.getPostalAddressContent();
		
		if(properties==null) {		
			properties = new PostalAddressProperties();
			adr.setPostalAddressProperties(properties);
		}
		
		if(content==null) {
			content = new PostalAddressContent();
			adr.setPostalAddressContent(content);
		}
		
		properties.setVersion(null);
		properties.setMediumStatus(null);

		if (soft.getAdrComplement() != null) {
			content.setAdditionalInformation(soft.getAdrComplement());
		}
		else {
			content.setAdditionalInformation(null);
		}
		
		if (soft.getCityR() != null) {
			content.setCity(soft.getCityR());
		}
		else {
			content.setCity(null);
		}
		
		if (soft.getCountry() != null) {
			content.setCountryCode(soft.getCountry());
		} 
		else {
			content.setCountryCode(null);
		}
		
		if (soft.getLocality() != null) {
			content.setDistrict(soft.getLocality());
		}
		else {
			content.setDistrict(null);
		}
		
		if (soft.getNumAndStreet() != null) {
			content.setNumberAndStreet(soft.getNumAndStreet());
		}
		else {
			content.setNumberAndStreet(null);
		}
			
		if (soft.getState() != null) {
			content.setStateCode(soft.getState());
		} 
		else {
			content.setStateCode(null);
		}
			
		if (soft.getZipCode() != null) {
			content.setZipCode(soft.getZipCode());
		} 
		else {
			content.setZipCode(null);
		}
		
		return adr;
	}

public static SoftComputingResponse softToError(NormalisationSoftResponseDTO out) {
	SoftComputingResponse scr = new SoftComputingResponse();
	if (out.getReturnCode1() != null)
		scr.setErrorNumberNormail(out.getReturnCode1().toString());
	else if (out.getWsErr() != null && out.getWsErr() == 3)
		scr.setErrorNumberNormail(out.getWsErr().toString());
	else
		scr.setErrorNumberNormail(SOFTCOMPUTING_3);

	if (out.getNumError() != null)
		scr.setErrorNumber(out.getNumError());
	if (out.getLibError() != null)
		scr.setErrorLabel(out.getLibError());

	if (out.getMailingAdrLine1() != null)
		scr.setAdrMailingL1(out.getMailingAdrLine1());
	if (out.getMailingAdrLine2() != null)
		scr.setAdrMailingL2(out.getMailingAdrLine2());
	if (out.getMailingAdrLine3() != null)
		scr.setAdrMailingL3(out.getMailingAdrLine3());
	if (out.getMailingAdrLine4() != null)
		scr.setAdrMailingL4(out.getMailingAdrLine4());
	if (out.getMailingAdrLine5() != null)
		scr.setAdrMailingL5(out.getMailingAdrLine5());
	if (out.getMailingAdrLine6() != null)
		scr.setAdrMailingL6(out.getMailingAdrLine6());
	if (out.getMailingAdrLine7() != null)
		scr.setAdrMailingL7(out.getMailingAdrLine7());
	if (out.getMailingAdrLine8() != null)
		scr.setAdrMailingL8(out.getMailingAdrLine8());
	if (out.getMailingAdrLine9() != null)
		scr.setAdrMailingL9(out.getMailingAdrLine9());

	return scr;
}
	// ===== EMAIL ==============================================================
	public static void transformListToSetEmailDTO(List<EmailRequest> wsList, IndividuDTO indDTO) throws InvalidParameterException, MissingParameterException {
		if (wsList == null || wsList.isEmpty()) {
			return;
		}

		Set<EmailDTO> dtoSet = Collections.synchronizedSet(new HashSet<EmailDTO>()); 
		
		for (EmailRequest email : wsList) {
			EmailDTO dto = transformToEmailDTO(email.getEmail());
			
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


	public static List<EmailDTO> transformToEmailDTO(List<EmailRequest> wsList) throws InvalidParameterException, MissingParameterException {
		
		// REPIND-260 : SONAR - On test Null avant de tester Empty
		if(wsList == null || wsList.isEmpty()) {
			return null;
		}
		
		List<EmailDTO> dtoList = new ArrayList<EmailDTO>();
		
		for(EmailRequest ws : wsList) {
			dtoList.add(transformToEmailDTO(ws.getEmail()));
		}
		
		return dtoList;
	}
	
	
	
	public static EmailDTO transformToEmailDTO(Email ws) throws InvalidParameterException, MissingParameterException {
		
		if(ws==null) {
			return null;
		}
		
		EmailDTO dto = new EmailDTO();
		
		dto.setEmail(ws.getEmail());
		dto.setAutorisationMailing(ws.getEmailOptin());
		dto.setCodeMedium(MediumCodeEnum.getEnumMandatory(ws.getMediumCode()).toString());		
		dto.setStatutMedium(ws.getMediumStatus());
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));

		return dto;
		
	}
	
	public static IndividuDTO transformToIndividuDTO(IndividualRequest ws) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		IndividuDTO dto = transformToIndividuDTO(ws.getIndividualInformations());

		// REPIND-1003 : Repair blocker
		if(dto != null) {
			dto.setCodeTitre(transformToCodeTitre(ws.getCivilian()));
			dto.setProfilsdto(transformToProfilsDTO(ws.getIndividualProfil()));
		}
		
		return dto;
		
	}
	
	public static String transformToCodeTitre(Civilian ws) {
			
		if(ws==null) {
			return null;
		}
		
		return ws.getTitleCode();
	}
	
	public static ProfilsDTO transformToProfilsDTO(IndividualProfilV3 ws) {
		
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
//		ws.getProAreaWording()
		dto.setScode_fonction(ws.getProFunctionCode());
//		ws.getProFunctionWording()
		dto.setSetudiant(ws.getStudentCode());
		
		return dto;
		
	}
	
	// ===== INDIVIDUAL INFORMATION ==============================================================
	
	public static IndividuDTO transformToIndividuDTO(IndividualInformationsV3 ws) throws InvalidParameterException {
		
		if(ws == null) {
			return null;
		}
		
		IndividuDTO dto = new IndividuDTO();
		
		dto.setDateNaissance(ws.getBirthDate());
		dto.setCivilite(ws.getCivility());
		dto.setAliasPrenom(ws.getFirstNamePseudonym());
		dto.setPrenom(ws.getFirstNameSC());
		dto.setPrenomSC(ws.getFirstNameSC());
		dto.setSexe(GenderEnum.getEnum(ws.getGender()).toString());
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
		dto.setNonFusionnable(SicStringUtils.getStringFrenchBoolean(ws.isFlagNoFusion()));
		dto.setTierUtiliseCommePiege(SicStringUtils.getStringFrenchBoolean(ws.isFlagThirdTrap()));
		
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
	
	// ===== TELECOM DATA ==============================================================
	
	public static void transformListToSetTelecomsDTO(List<TelecomRequest> wsList, IndividuDTO indDTO) throws InvalidParameterException, MissingParameterException {
		if (wsList == null || wsList.isEmpty()) {
			return;
		}
		
		Set<TelecomsDTO> dtoSet = Collections.synchronizedSet(new HashSet<TelecomsDTO>());

		for (TelecomRequest tel : wsList) {
			TelecomsDTO dto = transformToTelecomsDTO(tel.getTelecom());
			
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
	
	public static List<TelecomsDTO> transformToTelecomsDTO(List<TelecomRequest> wsList) throws InvalidParameterException, MissingParameterException {
		
		// REPIND-260 : SONAR - On test Null avant de tester Empty
		if(wsList == null || wsList.isEmpty()) {		
			return null;
		}
		
		List<TelecomsDTO> dtoList = new ArrayList<TelecomsDTO>();
		
		for(TelecomRequest ws : wsList) {
			dtoList.add(transformToTelecomsDTO(ws.getTelecom()));
		}
		
		return dtoList;
	}

	public static TelecomsDTO transformToTelecomsDTO(Telecom ws) throws InvalidParameterException, MissingParameterException {
		
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
	
	// ===== EXTERNAL IDENTIFIERS ==============================================================
	
	public static List<ExternalIdentifierDTO> transformToExternalIdentifierDTO(List<ExternalIdentifierRequest> wsList) {
		
		if(wsList==null || wsList.isEmpty()) {
			return null;
		}
		
		List<ExternalIdentifierDTO> dtoList = new ArrayList<ExternalIdentifierDTO>();
		
		for(ExternalIdentifierRequest ws : wsList) {
			dtoList.add(transformToExternalIdentifierDTO(ws));
		}
		
		return dtoList;		
	}
	
	public static ExternalIdentifierDTO transformToExternalIdentifierDTO(ExternalIdentifierRequest ws) {
		
		if (ws==null) {
			return null;
		}

		ExternalIdentifierDTO dto = transformToExternalIdentifierDTO(ws.getExternalIdentifier());
		
		// REPIND-1003 : Repair blocker
		if(dto != null) {
			dto.setExternalIdentifierDataList(transformToExternalIdentifierDataDTO(ws.getExternalIdentifierData()));
		}
		
		return dto;
	}
	
	public static ExternalIdentifierDTO transformToExternalIdentifierDTO(ExternalIdentifier ws) {
		
		if (ws==null) {
			return null;
		}

		ExternalIdentifierDTO dto = new ExternalIdentifierDTO();
		dto.setIdentifier(ws.getIdentifier());
		dto.setType(ws.getType());
		
		return dto;
	}
	
	public static List<ExternalIdentifierDataDTO> transformToExternalIdentifierDataDTO(List<ExternalIdentifierData> wsList) {
		
		if(wsList==null || wsList.isEmpty()) {
			return null;
		}
		
		List<ExternalIdentifierDataDTO> dtoList = new ArrayList<ExternalIdentifierDataDTO>();
		
		for(ExternalIdentifierData ws : wsList) {
			dtoList.add(transformToExternalIdentifierDataDTO(ws));
		}
		
		return dtoList;
		
	}
		
	public static ExternalIdentifierDataDTO transformToExternalIdentifierDataDTO(ExternalIdentifierData ws) {
		
		if (ws==null) {
			return null;
		}

		ExternalIdentifierDataDTO dto = new ExternalIdentifierDataDTO();
		dto.setKey(ws.getKey());
		dto.setValue(ws.getValue());
		
		return dto;
	}
	

	
	// ===== ALERT============================================================================
	public static List<AlertDTO> transformToAlertDTO(AlertRequest ws) {
		
		if (ws==null) {
			return null;
		}
		List<AlertDTO> dtoList = new ArrayList<AlertDTO>();
		
		for(Alert al : ws.getAlert()) {
			dtoList.add(transformToAlertDTO(al));
		}
		
		return dtoList;
	}
	

	public static AlertDTO transformToAlertDTO(Alert ws) {
		
		if (ws==null) {
			return null;
		}

		AlertDTO dto = new AlertDTO();
		if(ws.getAlertId() != null){
			dto.setAlertId(Integer.parseInt(ws.getAlertId()));
		}
		dto.setType(ws.getType());
		dto.setOptIn(ws.getOptIn());
		dto.setAlertDataDTO(transformToAlertDataDTO(ws.getAlertData()));
		return dto;
	}
	
	public static Set<AlertDataDTO> transformToAlertDataDTO(List<AlertData> wsList) {
		
		if(wsList==null || wsList.isEmpty()) {
			return null;
		}
		
		Set<AlertDataDTO> dtoList = new HashSet<AlertDataDTO>();
		
		for(AlertData ws : wsList) {
			dtoList.add(transformToAlertDataDTO(ws));
		}
		
		return dtoList;
		
	}
	
	public static AlertDataDTO transformToAlertDataDTO(AlertData ws) {
		
		if (ws==null) {
			return null;
		}

		AlertDataDTO dto = new AlertDataDTO();
		dto.setKey(ws.getKey());
		dto.setValue(ws.getValue());
		
		return dto;
	}
	

	// ===== PREFILLED NUMBERS ==============================================================
	
	public static List<PrefilledNumbersDTO> wsdlTOPrefilledNumbersdto(List<PrefilledNumbersRequest> adh) {
		
		List<PrefilledNumbersDTO> dto = null;		
		if (!adh.isEmpty()) {
			
			dto = new ArrayList<PrefilledNumbersDTO>();			
			for (PrefilledNumbersRequest prefilledNumbersRequest : adh) {
				
				PrefilledNumbers prefilledNumbers = prefilledNumbersRequest.getPrefilledNumbers();
				
				PrefilledNumbersDTO prefilledNumbersDTO = new PrefilledNumbersDTO();
				prefilledNumbersDTO.setContractNumber(prefilledNumbers.getContractNumber());
				prefilledNumbersDTO.setContractType(prefilledNumbers.getContractType());
				dto.add(prefilledNumbersDTO);
			}
		}
		return dto;
	}
	
	// ===== COMMUNICATION PREFERENCES ==============================================================
	
	public static List<CommunicationPreferencesDTO> transformToComPrefsDTO(List<ComunicationPreferencesRequest> adh,RequestorV2 requestor) {
		
		List<CommunicationPreferencesDTO> dto = null;
		if (!adh.isEmpty()) {
			dto = new ArrayList<CommunicationPreferencesDTO>();
			for (ComunicationPreferencesRequest comPrefRequest : adh) {
				dto.add(transformToComPrefDTO(comPrefRequest, requestor));
			}
		}
		return dto;
	}
	

	public static CommunicationPreferencesDTO transformToComPrefDTO(ComunicationPreferencesRequest comPrefRequest, RequestorV2 requestor) {
		CommunicationPreferences comPref = comPrefRequest
				.getCommunicationPreferences();

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

		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia1() != null)
			comPrefDto.setMedia1(comPref.getMedia().getMedia1());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia2() != null)
			comPrefDto.setMedia2(comPref.getMedia().getMedia1());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia3() != null)
			comPrefDto.setMedia3(comPref.getMedia().getMedia1());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia4() != null)
			comPrefDto.setMedia4(comPref.getMedia().getMedia1());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia5() != null)
			comPrefDto.setMedia5(comPref.getMedia().getMedia1());
		if (comPref.getSubscriptionChannel() != null)
			comPrefDto.setChannel(comPref.getSubscriptionChannel());
		if (comPref.getDateOfConsent() != null)
			comPrefDto.setDateOptin(comPref.getDateOfConsent());
		if (comPref.getDateOfConsentPartner() != null)
			comPrefDto.setDateOptinPartners(comPref
					.getDateOfConsentPartner());
		if (comPref.getOptInPartner() != null)
			comPrefDto.setOptinPartners(comPref.getOptInPartner());

		if (comPref.getMarketLanguage() != null) {
			Set<MarketLanguageDTO> mlsDto = new HashSet<MarketLanguageDTO>();

			for (MarketLanguage ml : comPref.getMarketLanguage()) {
				MarketLanguageDTO mlDto = new MarketLanguageDTO();
				if (ml.getOptIn() != null)
					mlDto.setOptIn(ml.getOptIn());
				if (ml.getLanguage().value() != null)
					mlDto.setLanguage(ml.getLanguage().value());
				if (ml.getMarket() != null)
					mlDto.setMarket(ml.getMarket());
				if (ml.getDateOfConsent() != null)
					mlDto.setDateOfConsent(ml.getDateOfConsent());
				if (ml.getMedia() != null) {
					if (ml.getMedia().getMedia1() != null)
						mlDto.setMedia1(ml.getMedia().getMedia1());
					if (ml.getMedia().getMedia2() != null)
						mlDto.setMedia2(ml.getMedia().getMedia2());
					if (ml.getMedia().getMedia3() != null)
						mlDto.setMedia3(ml.getMedia().getMedia3());
					if (ml.getMedia().getMedia4() != null)
						mlDto.setMedia4(ml.getMedia().getMedia4());
					if (ml.getMedia().getMedia5() != null)
						mlDto.setMedia5(ml.getMedia().getMedia5());
				}
				mlDto.setCreationDate(new Date());
				mlDto.setCreationSignature(requestor.getSignature());
				mlDto.setCreationSite(requestor.getSite());
				
				mlDto.setModificationDate(new Date());
				mlDto.setModificationSignature(requestor.getSignature());
				mlDto.setModificationSite(requestor.getSite());
				
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
	
/*	public static ProspectCommunicationPreferencesDTO transformToProspectComPrefDTO(ComunicationPreferencesRequest comPrefRequest, RequestorV2 requestor) {
		CommunicationPreferences comPref = comPrefRequest
				.getCommunicationPreferences();

		ProspectCommunicationPreferencesDTO proComPrefDto = new ProspectCommunicationPreferencesDTO();
		if (comPref.getDomain() != null)
			proComPrefDto.setDomain(comPref.getDomain());
		if (comPref.getCommunicationGroupeType() != null)
			proComPrefDto.setCommunicationGroupType(comPref
					.getCommunicationGroupeType());
		if (comPref.getCommunicationType() != null)
			proComPrefDto.setCommunicationType(comPref.getCommunicationType());
		if (comPref.getOptIn() != null)
			proComPrefDto.setOptIn(comPref.getOptIn());

		//TODO : savoir si on as des media  
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia1() != null)
			proComPrefDto.setCommunicationMedia1(comPref.getMedia().getMedia1());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia2() != null)
			proComPrefDto.setCommunicationMedia2(comPref.getMedia().getMedia2());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia3() != null)
			proComPrefDto.setCommunicationMedia3(comPref.getMedia().getMedia3());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia4() != null)
			proComPrefDto.setCommunicationMedia4(comPref.getMedia().getMedia4());
		if (comPref.getMedia() != null
				&& comPref.getMedia().getMedia5() != null)
			proComPrefDto.setCommunicationMedia5(comPref.getMedia().getMedia5());
		
		if (comPref.getSubscriptionChannel() != null)
			proComPrefDto.setSubscriptionChannel(comPref.getSubscriptionChannel());
		if (comPref.getDateOfConsent() != null)
			proComPrefDto.setDateOfConsent(comPref.getDateOfConsent());
		if (comPref.getDateOfConsentPartner() != null)
			proComPrefDto.setDateOfConsentPartners(comPref
					.getDateOfConsentPartner());
		if (comPref.getOptInPartner() != null)
			proComPrefDto.setOptinPartners(comPref.getOptInPartner());

		if (comPref.getMarketLanguage() != null
				&& !comPref.getMarketLanguage().isEmpty()) {
			Set<ProspectMarketLanguageDTO> mlsDto = new HashSet<ProspectMarketLanguageDTO>();

			for (MarketLanguage ml : comPref.getMarketLanguage()) {
				ProspectMarketLanguageDTO mlDto = new ProspectMarketLanguageDTO();
				if (ml.getOptIn() != null)
					mlDto.setOptIn(ml.getOptIn());
				if (ml.getLanguage().value() != null)
					mlDto.setLanguage(ml.getLanguage().value());
				if (ml.getMarket() != null)
					mlDto.setMarket(ml.getMarket());
				if (ml.getDateOfConsent() != null)
					mlDto.setDateOfConsent(ml.getDateOfConsent());
				if (ml.getMedia() != null) {
					if (ml.getMedia().getMedia1() != null)
						mlDto.setCommunicationMedia1(ml.getMedia().getMedia1());
					if (ml.getMedia().getMedia2() != null)
						mlDto.setCommunicationMedia2(ml.getMedia().getMedia2());
					if (ml.getMedia().getMedia3() != null)
						mlDto.setCommunicationMedia3(ml.getMedia().getMedia3());
					if (ml.getMedia().getMedia4() != null)
						mlDto.setCommunicationMedia4(ml.getMedia().getMedia4());
					if (ml.getMedia().getMedia5() != null)
						mlDto.setCommunicationMedia5(ml.getMedia().getMedia5());
				}
				mlDto.setCreationDate(new Date());
				mlDto.setCreationSignature(requestor.getSignature());
				mlDto.setCreationSite(requestor.getSite());
				
				mlDto.setModificationDate(new Date());
				mlDto.setModificationSignature(requestor.getSignature());
				mlDto.setModificationSite(requestor.getSite());
				
				mlsDto.add(mlDto);
			}
			proComPrefDto.setCreationDate(new Date());
			proComPrefDto.setCreationSignature(requestor.getSignature());
			proComPrefDto.setCreationSite(requestor.getSite());
			
			proComPrefDto.setModificationDate(new Date());
			proComPrefDto.setModificationSignature(requestor.getSignature());
			proComPrefDto.setModificationSite(requestor.getSite());
			proComPrefDto.setMarketLanguageDTO(mlsDto);
		}
		return proComPrefDto;
	}
*/	
	// ===== DELEGATION DATA ==============================================================
	// ===== DELEGATE =====================================================================
	public static List<DelegationDataDTO> transformToDelegate(AccountDelegationDataRequest ws, String gin) {
		
		if(ws==null) {
			return null;
		}
		
		return transformToDelegate(ws.getDelegate(), gin);	
	}

	public static List<DelegationDataDTO> transformToDelegate(List<Delegate> wsList, String gin) {
		
		if(wsList==null) {
			return null;
		}
		
		List<DelegationDataDTO> dtoList = new ArrayList<DelegationDataDTO>();

		for(Delegate ws : wsList) {
			DelegationDataDTO delegationDTO = transformToDelegate(ws.getDelegationData(), gin);
			//addComplementatyInformation(delegationDTO, ws.getComplementaryInformation());
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
	/*public static void addComplementatyInformation(DelegationDataDTO delegationDTO,
			List<ComplementaryInformation> complementaryInformationFromRequest) {

		//Prevent NullPointer
		if (complementaryInformationFromRequest == null) {
			return;
		}

		Set<ComplementaryInformationDTO> complementaryInformations = new HashSet<ComplementaryInformationDTO>();
		
		for (int i = 0; i < complementaryInformationFromRequest.size(); i++) {
			ComplementaryInformationDTO complementaryInformationDTO = new ComplementaryInformationDTO();
			complementaryInformationDTO.setType(complementaryInformationFromRequest.get(i).getType());
			Set<ComplementaryInformationDataDTO> complementaryInformationDatasDTO = new HashSet<ComplementaryInformationDataDTO>();
			
			List<ComplementaryInformationData> complementaryInformationDatas = complementaryInformationFromRequest.get(i).getComplementaryInformationDatas().getComplementaryInformationData();
			//Prevent NullPointer
			if(complementaryInformationDatas == null) {
				complementaryInformationDatas = new ArrayList<ComplementaryInformationData>();
			}

			for (int u = 0; u < complementaryInformationDatas.size(); u++) {
				ComplementaryInformationDataDTO complementaryInformationDataDTO = new ComplementaryInformationDataDTO();
				complementaryInformationDataDTO.setKey(complementaryInformationDatas.get(u).getKey());
				complementaryInformationDataDTO.setValue(complementaryInformationDatas.get(u).getValue());
				complementaryInformationDatasDTO.add(complementaryInformationDataDTO);
			}
					
			complementaryInformationDTO.setComplementaryInformationDatas(complementaryInformationDatasDTO);
			complementaryInformations.add(complementaryInformationDTO);
		}

		delegationDTO.setComplementaryInformations(complementaryInformations);
	}*/

	public static DelegationDataDTO transformToDelegate(DelegationData ws, String gin) {
		
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
public static List<DelegationDataDTO> transformToDelegator(AccountDelegationDataRequest ws, String gin) {
		
		if(ws==null) {
			return null;
		}
		
		return transformToDelegator(ws.getDelegator(), gin);	
	}

	public static List<DelegationDataDTO> transformToDelegator(List<Delegator> wsList, String gin) {
		
		if(wsList==null) {
			return null;
		}
		
		List<DelegationDataDTO> dtoList = new ArrayList<DelegationDataDTO>();

		for(Delegator ws : wsList) {
			dtoList.add(transformToDelegator(ws.getDelegationData(), gin)); 
		}
		
		return dtoList;
	}
	
	public static DelegationDataDTO transformToDelegator(DelegationData ws, String gin) {
		
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
	
	

	
	public static RequestorV2 getRequestor(CreateUpdateIndividualRequest request) {
		RequestorV2 requestor = new RequestorV2();
		requestor.setChannel("B2C");
		
		if (request != null) {
			if (request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualProfil() !=  null) {
				if (request.getIndividualRequest().getIndividualProfil().getCarrierCode() !=  null) {
					requestor.setManagingCompany(request.getIndividualRequest().getIndividualProfil().getCarrierCode());
				}
			}
			if (request.getRequestor() != null) {
				if (request.getRequestor().getApplicationCode() != null) {
					requestor.setApplicationCode(request.getRequestor().getApplicationCode());
				} else {
					requestor.setApplicationCode("B2C");
				}
				if (request.getRequestor().getContext() != null) {
					requestor.setContext(request.getRequestor().getContext());			
				}
				if (request.getRequestor().getIpAddress() != null) {
					requestor.setIpAddress(request.getRequestor().getIpAddress());	
				}	
				if (request.getRequestor().getSignature() !=  null) {
					requestor.setSignature(request.getRequestor().getSignature());	
				}
				if (request.getRequestor().getSite() !=  null) {
					requestor.setSite(request.getRequestor().getSite());	
				}
			}
		}
		return requestor;
	}
	
	public static IndividualRequest getIndRequest(CreateUpdateIndividualRequest request, String gin) {
		//IndividualRequest
		IndividualRequest indReq = new IndividualRequest();
		//IndividualInformations
		IndividualInformationsV3 indInfo = new IndividualInformationsV3();
		
		if (request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualInformations() != null) {
			indInfo.setNationality(request.getIndividualRequest().getIndividualInformations().getNationality());
			indInfo.setSecondNationality(request.getIndividualRequest().getIndividualInformations().getSecondNationality());
			indInfo.setSecondFirstName(request.getIndividualRequest().getIndividualInformations().getMiddleNameSC());
		}
		indInfo.setIdentifier(gin);
		
		// First name, second name and birth date are skipped, otherwise SIMULTANEOUS UPDATE error is raised
		if (request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualProfil() != null && request.getIndividualRequest().getIndividualProfil().getLanguageCode() != null) {
			indInfo.setLanguageCode(request.getIndividualRequest().getIndividualProfil().getLanguageCode());			
		}
		
		indReq.setIndividualInformations(indInfo);
		
		return indReq;
	}
	
	public static SignatureDTO transformToProspectSignatureAPP(String signature, String site) {
		
		Date today = new Date();
		
		SignatureDTO dto = new SignatureDTO();
		dto.setDate(today);
		dto.setSignature(signature);
		dto.setSite(site);
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

	public static CreateUpdateIndividualRequestDTO transformRequestWSToDTO(CreateUpdateIndividualRequest request) {

		if(request==null) {
			return null;
		}
		
		CreateUpdateIndividualRequestDTO dto = new CreateUpdateIndividualRequestDTO();
		dto.setAccountDelegationDataRequestDTO(transformAccountDelegDataRequestWSToDTO(request.getAccountDelegationDataRequest()));
		dto.setAlertRequestDTO(transformAlertRequestWSToDTO(request.getAlertRequest()));
		dto.setCommunicationPreferencesRequestDTO(transformCommPrefRequestWSToDTO(request.getComunicationPreferencesRequest()));
		dto.setEmailRequestDTO(transformEmailRequestWSToDTO(request.getEmailRequest()));
		dto.setExternalIdentifierRequestDTO(transformExternalIdentifierRequestWSToDTO(request.getExternalIdentifierRequest()));
		dto.setIndividualRequestDTO(transformIndividualRequestWSToDTO(request.getIndividualRequest()));
		dto.setNewsletterMediaSending(request.getNewsletterMediaSending());
		dto.setPostalAddressRequestDTO(transformPostalAddressRequestWSToDTO(request.getPostalAddressRequest()));
		dto.setPreferenceRequestDTO(transformPreferenceRequestWSToDTO(request.getPreferenceRequest()));
		dto.setPrefilledNumbersRequestDTO(transformPrefilledNumbersRequestWSToDTO(request.getPrefilledNumbersRequest()));
		dto.setProcess(request.getProcess());
		dto.setRequestorDTO(transformRequestorRequestWSToDTO(request.getRequestor()));
		dto.setStatus(request.getStatus());
		dto.setTelecomRequestDTO(transformTelecomRequestWSToDTO(request.getTelecomRequest()));
		dto.setUpdateCommunicationPrefMode(request.getUpdateCommunicationPrefMode());
		dto.setUpdatePrefilledNumbersMode(request.getUpdatePrefilledNumbersMode());
		dto.setUtfRequestDTO(transformUtfRequestWSToDTO(request.getUtfRequest()));
		
		return dto;
	}
	
	/*
	 * UTF DATA
	 * 
	 */
	public static UtfRequestDTO transformUtfRequestWSToDTO(UtfRequest request) {

		if(request==null) {
			return null;
		}
		
		UtfRequestDTO dto = new UtfRequestDTO();
		dto.setUtfDTO(transformUtfWSToDTO(request.getUtf()));
		
		return dto;
	}
	
	public static List<UtfDTO> transformUtfWSToDTO(List<Utf> request) {

		if(request==null) {
			return null;
		}
		
		List<UtfDTO> dto = new ArrayList<UtfDTO>();
		
		for (Utf utfRequest : request) {
			UtfDTO utfDTO = new UtfDTO();
			if(utfRequest.getId() != null) {
				utfDTO.setId(Long.parseLong(utfRequest.getId()));
			}
			utfDTO.setType(utfRequest.getType());
			utfDTO.setUtfDatasDTO(transformUtfDatasWSToDTO(utfRequest.getUtfDatas()));
			dto.add(utfDTO);
		}
		
		return dto;
	}
	
	public static UtfDatasDTO transformUtfDatasWSToDTO(UtfDatas request) {

		if(request==null) {
			return null;
		}
		
		UtfDatasDTO dto = new UtfDatasDTO();
		dto.setUtfDataDTO(transformUtfDataWSToDTO(request.getUtfData()));
		
		return dto;
	}
	
	public static List<utfDataDTO> transformUtfDataWSToDTO(List<UtfData> request) {

		if(request==null) {
			return null;
		}

		List<utfDataDTO> dto = new ArrayList<utfDataDTO>();
		
		for (UtfData utfData : request) {
			utfDataDTO utfDataDTO = new utfDataDTO();
			utfDataDTO.setKey(utfData.getKey());
			utfDataDTO.setValue(utfData.getValue());
			
			dto.add(utfDataDTO);
		}
		
		return dto;
	}
	
	/*
	 * TELECOM
	 * 
	 */
	public static List<TelecomRequestDTO> transformTelecomRequestWSToDTO(List<TelecomRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<TelecomRequestDTO> dto = new ArrayList<TelecomRequestDTO>();
		for (TelecomRequest telecomRequest : request) {
			TelecomRequestDTO telDTO = new TelecomRequestDTO();
			telDTO.setTelecomDTO(transformTelecomWSToDTO(telecomRequest.getTelecom()));
			dto.add(telDTO);
		}
		
		return dto;
	}
	
	public static TelecomDTO transformTelecomWSToDTO(Telecom request) {

		if(request==null) {
			return null;
		}
		TelecomDTO dto = new TelecomDTO();
		dto.setCountryCode(request.getCountryCode());
		dto.setMediumCode(request.getMediumCode());
		dto.setMediumStatus(request.getMediumStatus());
		dto.setPhoneNumber(request.getPhoneNumber());
		dto.setTerminalType(request.getTerminalType());
		dto.setVersion(request.getVersion());
		
		return dto;
	}

	/*
	 * REQUESTOR
	 * 
	 */
	public static RequestorDTO transformRequestorRequestWSToDTO(RequestorV2 request) {

		if(request==null) {
			return null;
		}
		
		RequestorDTO dto = new RequestorDTO();
		dto.setApplicationCode(request.getApplicationCode());
		dto.setChannel(request.getChannel());
		dto.setContext(request.getContext());
		dto.setIpAddress(request.getIpAddress());
		dto.setLoggedGin(request.getLoggedGin());
		dto.setManagingCompany(request.getManagingCompany());
		dto.setMatricule(request.getMatricule());
		dto.setOfficeId(request.getOfficeId());
		dto.setReconciliationDataCIN(request.getReconciliationDataCIN());
		dto.setSignature(request.getSignature());
		dto.setSite(request.getSite());
		dto.setToken(request.getToken());
		
		return dto;
	}

	/*
	 * PREFILLED NUMBERS
	 * 
	 */
	public static List<PrefilledNumbersRequestDTO> transformPrefilledNumbersRequestWSToDTO(List<PrefilledNumbersRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<PrefilledNumbersRequestDTO> dto = new ArrayList<PrefilledNumbersRequestDTO>();
		
		for (PrefilledNumbersRequest prefilledNumbersRequest : request) {
			PrefilledNumbersRequestDTO pnDTO = new PrefilledNumbersRequestDTO();
			pnDTO.setPrefilledNumbersDTO(transformPrefilledNumbersWSToDTO(prefilledNumbersRequest.getPrefilledNumbers()));
			dto.add(pnDTO);
		}
		
		return dto;
	}
	
	
	public static com.airfrance.repind.dto.ws.PrefilledNumbersDTO transformPrefilledNumbersWSToDTO(PrefilledNumbers request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.PrefilledNumbersDTO dto = new com.airfrance.repind.dto.ws.PrefilledNumbersDTO();
		dto.setContractNumber(request.getContractNumber());
		dto.setContractType(request.getContractType());
		
		return dto;
	}
	

	/*
	 * PREFERENCE
	 * 
	 */
	public static PreferenceRequestDTO transformPreferenceRequestWSToDTO(PreferenceRequest request) {

		if(request==null) {
			return null;
		}
		
		PreferenceRequestDTO dto = new PreferenceRequestDTO();
		dto.setPreferenceDTO(transformPreferenceWSToDTO(request.getPreference()));
		
		return dto;
	}
	
	
	public static List<PreferenceDTO> transformPreferenceWSToDTO(List<PreferenceV2> request) {

		if(request==null) {
			return null;
		}
		
		List<PreferenceDTO> dto = new ArrayList<PreferenceDTO>();
		
		for (PreferenceV2 pref : request) {
			PreferenceDTO prefDTO = new PreferenceDTO();
			if(pref.getId() != null) {
				prefDTO.setId(Long.parseLong(pref.getId()));
			}
			if(pref.getLink() != null) {
				prefDTO.setLink(Long.parseLong(pref.getLink()));
			}
			prefDTO.setPreferencesDatasDTO(transformPreferenceDatasWSToDTO(pref.getPreferenceDatas()));
			prefDTO.setType(pref.getType());
			
			dto.add(prefDTO);
		}
		
		return dto;
	}
	
	public static PreferenceDatasDTO transformPreferenceDatasWSToDTO(PreferenceDatasV2 request) {

		if(request==null) {
			return null;
		}
		
		PreferenceDatasDTO dto = new PreferenceDatasDTO();
		dto.setPreferenceDataDTO(transformPreferenceDataWSToDTO(request.getPreferenceData()));
		
		return dto;
	}
	
	public static List<PreferenceDataDTO> transformPreferenceDataWSToDTO(List<PreferenceDataV2> request) {

		if(request==null) {
			return null;
		}
		
		List<PreferenceDataDTO> dto = new ArrayList<PreferenceDataDTO>();
		
		for (PreferenceDataV2 prefData : request) {
			PreferenceDataDTO prefDTO = new PreferenceDataDTO();
			prefDTO.setKey(prefData.getKey());
			prefDTO.setValue(prefData.getValue());
			
			dto.add(prefDTO);
		}
		
		return dto;
	}
	

	/*
	 * POSTAL ADDRESS
	 * 
	 */
	public static List<PostalAddressRequestDTO> transformPostalAddressRequestWSToDTO(List<PostalAddressRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<PostalAddressRequestDTO> dto = new ArrayList<PostalAddressRequestDTO>();
		
		for (PostalAddressRequest postalAddressRequest : request) {
			PostalAddressRequestDTO paDTO = new PostalAddressRequestDTO();
			
			paDTO.setPostalAddressContentDTO(transformPostalAddressContentWSToDTO(postalAddressRequest.getPostalAddressContent()));
			paDTO.setPostalAddressPropertiesDTO(transformPostalAddressPropertiesWSToDTO(postalAddressRequest.getPostalAddressProperties()));
			paDTO.setUsageAddressDTO(transformPostalAddressUsageWSToDTO(postalAddressRequest.getUsageAddress()));
			
			dto.add(paDTO);
		}
		
		return dto;
	}

	public static PostalAddressContentDTO transformPostalAddressContentWSToDTO(PostalAddressContent request) {

		if(request==null) {
			return null;
		}
		
		PostalAddressContentDTO dto = new PostalAddressContentDTO();
		dto.setAdditionalInformation(request.getAdditionalInformation());
		dto.setCity(request.getCity());
		dto.setCorporateName(request.getCorporateName());
		dto.setCountryCode(request.getCountryCode());
		dto.setDistrict(request.getDistrict());
		dto.setNumberAndStreet(request.getNumberAndStreet());
		dto.setStateCode(request.getStateCode());
		dto.setZipCode(request.getZipCode());
		
		return dto;
	}

	public static PostalAddressPropertiesDTO transformPostalAddressPropertiesWSToDTO(PostalAddressProperties request) {

		if(request==null) {
			return null;
		}
		
		PostalAddressPropertiesDTO dto = new PostalAddressPropertiesDTO();
		dto.setIndicAdrNorm(request.isIndicAdrNorm());
		dto.setMediumCode(request.getMediumCode());
		dto.setMediumStatus(request.getMediumStatus());
		dto.setVersion(request.getVersion());
		
		return dto;
	}

	public static UsageAddressDTO transformPostalAddressUsageWSToDTO(UsageAddress request) {

		if(request==null) {
			return null;
		}
		
		UsageAddressDTO dto = new UsageAddressDTO();
		dto.setAddressRoleCode(request.getAddressRoleCode());
		dto.setApplicationCode(request.getApplicationCode());
		dto.setUsageNumber(request.getUsageNumber());
		
		return dto;
	}
	
	/*
	 * INDIVIDUAL
	 * 
	 */
	public static IndividualRequestDTO transformIndividualRequestWSToDTO(IndividualRequest request) {

		if(request==null) {
			return null;
		}
		
		IndividualRequestDTO dto = new IndividualRequestDTO();
		dto.setIndividualInformationsDTO(transformIndividualInformationsRequestWSToDTO(request.getIndividualInformations()));
		dto.setIndividualProfilDTO(transformIndividualProfilRequestWSToDTO(request.getIndividualProfil()));
		dto.setTitleCode(transformCivilianRequestWSToDTO(request.getCivilian()));
		
		return dto;
	}
	
	public static IndividualInformationsDTO transformIndividualInformationsRequestWSToDTO(IndividualInformationsV3 request) {

		if(request==null) {
			return null;
		}
		
		IndividualInformationsDTO dto = new IndividualInformationsDTO();
		dto.setBirthDate(request.getBirthDate());
		dto.setCivility(request.getCivility());
		dto.setFirstNamePseudonym(request.getFirstNamePseudonym());
		dto.setFirstNameSC(request.getFirstNameSC());
		dto.setFlagNoFusion(request.isFlagNoFusion());
		dto.setFlagThirdTrap(request.isFlagThirdTrap());
		dto.setGender(request.getGender());
		dto.setIdentifier(request.getIdentifier());
		dto.setLanguageCode(request.getLanguageCode());
		dto.setLastNamePseudonym(request.getLastNamePseudonym());
		dto.setLastNameSC(request.getLastNameSC());
		dto.setMiddleNameSC(request.getMiddleNameSC());
		dto.setNationality(request.getNationality());
		dto.setPersonalIdentifier(request.getPersonalIdentifier());
		dto.setPopulationType(request.getPopulationType());
		dto.setSecondFirstName(request.getSecondFirstName());
		dto.setSecondNationality(request.getSecondNationality());
		dto.setStatus(request.getStatus());
		dto.setVersion(request.getVersion());				
		
		return dto;
	}
	
	public static IndividualProfilDTO transformIndividualProfilRequestWSToDTO(IndividualProfilV3 request) {

		if(request==null) {
			return null;
		}
		
		IndividualProfilDTO dto = new IndividualProfilDTO();
		dto.setCarrierCode(request.getCarrierCode());
		dto.setChildrenNumber(request.getChildrenNumber());
		dto.setCivilianCode(request.getCivilianCode());
		dto.setCustomerSegment(request.getCustomerSegment());
		dto.setEmailOptin(request.getEmailOptin());
		dto.setLanguageCode(request.getLanguageCode());
		dto.setProAreaCode(request.getProAreaCode());
		dto.setProAreaWording(request.getProAreaWording());
		dto.setProFunctionCode(request.getProFunctionCode());
		dto.setProFunctionWording(request.getProFunctionWording());
		dto.setStudentCode(request.getStudentCode());
		
		return dto;
	}
	
	public static String transformCivilianRequestWSToDTO(Civilian request) {

		if(request==null) {
			return null;
		}
		
		String titleCode = request.getTitleCode();
		
		return titleCode;
	}
	
	/*
	 * EXTERNAL IDENTIFIER
	 * 
	 */
	public static List<ExternalIdentifierRequestDTO> transformExternalIdentifierRequestWSToDTO(List<ExternalIdentifierRequest> request) {
		

		if(request==null) {
			return null;
		}
		
		List<ExternalIdentifierRequestDTO> dto = new ArrayList<ExternalIdentifierRequestDTO>();
		
		for (ExternalIdentifierRequest extIdR : request) {
			ExternalIdentifierRequestDTO extIdDTO = new ExternalIdentifierRequestDTO();
			extIdDTO.setExternalIdentifierDataDTO(transformExternalIdentifierDataRequestWSToDTO(extIdR.getExternalIdentifierData()));
			extIdDTO.setExternalIdentifierDTO(transformExternalIdentifierWSToDTO(extIdR.getExternalIdentifier()));
			
			dto.add(extIdDTO);
		}
		
		
		return dto;
	}
	
	public static List<com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO> transformExternalIdentifierDataRequestWSToDTO(List<ExternalIdentifierData> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO>();
		
		for (ExternalIdentifierData extIdD : request) {
			com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO extIdDDTO = new com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO();
			extIdDDTO.setKey(extIdD.getKey());
			extIdDDTO.setValue(extIdD.getValue());
			
			dto.add(extIdDDTO);
		}
		
		
		return dto;
	}
	
	public static com.airfrance.repind.dto.ws.ExternalIdentifierDTO transformExternalIdentifierWSToDTO(ExternalIdentifier request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.ExternalIdentifierDTO dto = new com.airfrance.repind.dto.ws.ExternalIdentifierDTO();
		dto.setIdentifier(request.getIdentifier());
		dto.setType(request.getType());
		
		return dto;
	}
	
	/*
	 * EMAIL
	 * 
	 */
	public static List<EmailRequestDTO> transformEmailRequestWSToDTO(List<EmailRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<EmailRequestDTO> dto = new ArrayList<EmailRequestDTO>();
		
		for (EmailRequest emailRequest : request) {
			EmailRequestDTO emDTO = new EmailRequestDTO();
			emDTO.setEmailDTO(transformEmailWSToDTO(emailRequest.getEmail()));
			dto.add(emDTO);
		}
		
		
		return dto;
	}
	
	public static com.airfrance.repind.dto.ws.EmailDTO transformEmailWSToDTO(Email request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.EmailDTO dto = new com.airfrance.repind.dto.ws.EmailDTO();
		dto.setEmail(request.getEmail());
		dto.setEmailOptin(request.getEmailOptin());
		dto.setMediumCode(request.getMediumCode());
		dto.setMediumStatus(request.getMediumStatus());
		dto.setVersion(request.getVersion());
		
		return dto;
	}
	
	/*
	 * ALERT
	 * 
	 */
	public static AlertRequestDTO transformAlertRequestWSToDTO(AlertRequest request) {

		if(request==null) {
			return null;
		}
		
		AlertRequestDTO dto = new AlertRequestDTO();
		dto.setAlertDTO(transformAlertWSToDTO(request.getAlert()));
		
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.AlertDTO> transformAlertWSToDTO(List<Alert> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.AlertDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>();
		for (Alert al : request) {
			com.airfrance.repind.dto.ws.AlertDTO alDTO = new com.airfrance.repind.dto.ws.AlertDTO();
			alDTO.setAlertDataDTO(transformAlertDataWSToDTO(al.getAlertData()));
			if(StringUtils.isNotBlank(al.getAlertId())) {
				alDTO.setAlertId(SicStringUtils.getIntegerFromString(al.getAlertId()));
			}
			alDTO.setOptin(al.getOptIn());
			alDTO.setType(al.getType());
			
			dto.add(alDTO);
			
		}
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.AlertDataDTO> transformAlertDataWSToDTO(List<AlertData> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.AlertDataDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.AlertDataDTO>();
		for (AlertData ald : request) {
			com.airfrance.repind.dto.ws.AlertDataDTO aldDTO = new com.airfrance.repind.dto.ws.AlertDataDTO();
			aldDTO.setKey(ald.getKey());
			aldDTO.setValue(ald.getValue());
			dto.add(aldDTO);
		}
		
		return dto;
	}

	
	/*
	 * DELEGATION DATA
	 * 
	 */
	public static AccountDelegationDataRequestDTO transformAccountDelegDataRequestWSToDTO(AccountDelegationDataRequest request) {

		if(request==null) {
			return null;
		}
		
		AccountDelegationDataRequestDTO dto = new AccountDelegationDataRequestDTO();
		AccountDelegationDataDTO accDelDataDTO = new AccountDelegationDataDTO();
		accDelDataDTO.setDelegateDTO(transformDelegateRequestWSToDTO(request.getDelegate()));
		accDelDataDTO.setDelegatorDTO(transformDelegatorRequestWSToDTO(request.getDelegator()));
		dto.setAccountDelegationDataDTO(accDelDataDTO);
		
		
		return dto;
	}

	public static List<DelegatorDTO> transformDelegatorRequestWSToDTO(List<Delegator> request) {

		if(request==null) {
			return null;
		}
		
		List<DelegatorDTO> dto = new ArrayList<DelegatorDTO>();
		for (Delegator delegator : request) {
			DelegatorDTO delDTO = new DelegatorDTO();
			delDTO.setDelegatorDataDTO(transformDelegatorDataRequestWSToDTO(delegator.getDelegationData()));
			
			dto.add(delDTO);
			
		}
		
		return dto;
	}

	public static DelegatorDataDTO transformDelegatorDataRequestWSToDTO(DelegationData request) {

		if(request==null) {
			return null;
		}
		
		DelegatorDataDTO dto = new DelegatorDataDTO();
		dto.setDelegationAction(request.getDelegationAction());
		dto.setDelegationType(request.getDelegationType());
		dto.setGin(request.getGin());
		
		return dto;
	}
	
	public static List<DelegateDTO> transformDelegateRequestWSToDTO(List<Delegate> request) {

		if(request==null) {
			return null;
		}
		
		List<DelegateDTO> dto = new ArrayList<DelegateDTO>();
		for (Delegate delegate : request) {
			DelegateDTO delDTO = new DelegateDTO();
			delDTO.setDelegateDataDTO(transformDelegateDataRequestWSToDTO(delegate.getDelegationData()));
			delDTO.setComplementaryInformationDTO(transformComplemInfoRequestWSToDTO(delegate.getComplementaryInformation()));
			
			dto.add(delDTO);
			
		}
		
		return dto;
	}

	public static DelegateDataDTO transformDelegateDataRequestWSToDTO(DelegationData request) {

		if(request==null) {
			return null;
		}
		
		DelegateDataDTO dto = new DelegateDataDTO();
		dto.setDelegationAction(request.getDelegationAction());
		dto.setDelegationType(request.getDelegationType());
		dto.setGin(request.getGin());
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.ComplementaryInformationDTO> transformComplemInfoRequestWSToDTO(List<ComplementaryInformation> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.ComplementaryInformationDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDTO>();
		
		for (ComplementaryInformation complemInfo : request) {
			
			com.airfrance.repind.dto.ws.ComplementaryInformationDTO ciDTO = new com.airfrance.repind.dto.ws.ComplementaryInformationDTO();
			ciDTO.setComplementaryInformationDatasDTO(transformComplemInfoDatasRequestWSToDTO(complemInfo.getComplementaryInformationDatas()));
			ciDTO.setType(complemInfo.getType());
			
			dto.add(ciDTO);
			
		}
		
		return dto;
	}

	public static com.airfrance.repind.dto.ws.ComplementaryInformationDatasDTO transformComplemInfoDatasRequestWSToDTO(ComplementaryInformationDatas request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.ComplementaryInformationDatasDTO dto = new com.airfrance.repind.dto.ws.ComplementaryInformationDatasDTO();
		dto.setComplementaryInformationDataDTO(transformComplemInfoDataRequestWSToDTO(request.getComplementaryInformationData()));
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO> transformComplemInfoDataRequestWSToDTO(List<ComplementaryInformationData> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO>();
		
		for (ComplementaryInformationData complemInfo : request) {
			
			com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO cidDTO = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
			cidDTO.setKey(complemInfo.getKey());
			cidDTO.setValue(complemInfo.getValue());
			
			dto.add(cidDTO);
			
		}
		
		return dto;
	}
	
	/*
	 * COMMUNICATION PREFRENCES
	 * 
	 */
	public static List<CommunicationPreferencesRequestDTO> transformCommPrefRequestWSToDTO(List<ComunicationPreferencesRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<CommunicationPreferencesRequestDTO> dto = new ArrayList<CommunicationPreferencesRequestDTO>();
		
		for (ComunicationPreferencesRequest comunicationPreferencesRequest : request) {
			
			CommunicationPreferences cpr = comunicationPreferencesRequest.getCommunicationPreferences();
			
			com.airfrance.repind.dto.ws.CommunicationPreferencesDTO cpDTO = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
			cpDTO.setCommunicationGroupeType(cpr.getCommunicationGroupeType());
			cpDTO.setCommunicationType(cpr.getCommunicationType());
			cpDTO.setDateOfConsent(cpr.getDateOfConsent());
			cpDTO.setDateOfConsentPartner(cpr.getDateOfConsentPartner());
			cpDTO.setDateOfEntry(cpr.getDateOfEntry());
			cpDTO.setDomain(cpr.getDomain());
			cpDTO.setMarketLanguageDTO(transformMarketLanguageRequestWSToDTO(cpr.getMarketLanguage()));
			cpDTO.setMediaDTO(transformMediaRequestWSToDTO(cpr.getMedia()));
			cpDTO.setOptIn(cpr.getOptIn());
			cpDTO.setOptInPartner(cpr.getOptInPartner());
			cpDTO.setSubscriptionChannel(cpr.getSubscriptionChannel());
			
			CommunicationPreferencesRequestDTO cprDTO = new CommunicationPreferencesRequestDTO();
			cprDTO.setCommunicationPreferencesDTO(cpDTO);
			
			dto.add(cprDTO);
			
		}
		
		return dto;
	}
	

	public static List<com.airfrance.repind.dto.ws.MarketLanguageDTO> transformMarketLanguageRequestWSToDTO(List<MarketLanguage> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.MarketLanguageDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.MarketLanguageDTO>();
		
		for (MarketLanguage mlRequest : request) {
			
			com.airfrance.repind.dto.ws.MarketLanguageDTO mlDTO = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
			mlDTO.setDateOfConsent(mlRequest.getDateOfConsent());
			mlDTO.setLanguage(mlRequest.getLanguage().toString());
			mlDTO.setMarket(mlRequest.getMarket());
			mlDTO.setMediaDTO(transformMediaRequestWSToDTO(mlRequest.getMedia()));
			mlDTO.setOptIn(mlRequest.getOptIn());
			
			dto.add(mlDTO);
			
		}
		
		return dto;
	}

	public static com.airfrance.repind.dto.ws.MediaDTO transformMediaRequestWSToDTO(Media request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.MediaDTO dto = new com.airfrance.repind.dto.ws.MediaDTO();
		dto.setMedia1(request.getMedia1());
		dto.setMedia2(request.getMedia2());
		dto.setMedia3(request.getMedia3());
		dto.setMedia4(request.getMedia4());
		dto.setMedia5(request.getMedia5());
		
		
		return dto;
	}


}
