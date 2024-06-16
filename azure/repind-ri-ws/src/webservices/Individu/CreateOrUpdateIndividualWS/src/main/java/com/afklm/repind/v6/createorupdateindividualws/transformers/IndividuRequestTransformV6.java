package com.afklm.repind.v6.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.request.Delegate;
import com.afklm.soa.stubs.w000442.v6.request.Delegator;
import com.afklm.soa.stubs.w000442.v6.request.*;
import com.afklm.soa.stubs.w000442.v6.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.*;
import com.afklm.soa.stubs.w000442.v6.sicmarketingtype.*;
import com.afklm.soa.stubs.w000442.v6.softcomputingtype.SoftComputingResponse;
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
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.AdressePostaleDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.InfosIndividuDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class IndividuRequestTransformV6 {

	private static final String SOFTCOMPUTING_3 = "3";
	private static final String FORCAGE_VRAI = "O";
	
	// ===== SIGNATURE ==============================================================
	
	public static SignatureDTO transformToSignatureAPP(Requestor requestor) {
		
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
	
	public static List<PostalAddressDTO> transformToPostalAddressDTO(List<PostalAddressRequest> wsList, SignatureDTO signatureAPP) throws InvalidParameterException {
		
		// REPIND-260 : SONAR - On test Null avant de tester Empty
		if(wsList == null || wsList.isEmpty()) {
			return null;
		}
		
		List<PostalAddressDTO> dtoList = new ArrayList<PostalAddressDTO>();
		
		for(PostalAddressRequest ws : wsList) {
			dtoList.add(transformToPostalAddressDTO(ws, signatureAPP));
		}
		
		return dtoList;
	}
	
	public static PostalAddressDTO transformToPostalAddressDTO(PostalAddressRequest ws, SignatureDTO signatureAPP) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		PostalAddressDTO dto = new PostalAddressDTO();
		transformToPostalAddressDTO(ws.getPostalAddressContent(), dto);
		transformToPostalAddressDTO(ws.getPostalAddressProperties(), dto);
		dto.setPreferee(transformToPreferedAddress(ws.getUsageAddress())); // attention ce champs est primordial pour le calcul des usages
		dto.setUsage_mediumdto(transformToUsageMediumDTO(ws.getUsageAddress()));
		dto.setSsignature_fonctionnel(signatureAPP.getSignature());
		dto.setSsite_fonctionnel(signatureAPP.getSite());
		dto.setDdate_fonctionnel(signatureAPP.getDate());
		
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
	
	public static EmailDTO transformToEmailDTO(EmailRequest ws) throws InvalidParameterException, MissingParameterException {
		
		if(ws==null) {
			return null;
		}
		
		EmailDTO dto = transformToEmailDTO(ws.getEmail());
		
		return dto;
		
	}
	
	public static EmailDTO transformToEmailDTO(Email ws) throws InvalidParameterException, MissingParameterException {
		
		if(ws==null) {
			return null;
		}
		
		EmailDTO dto = new EmailDTO();
		
		// REPIND-1288 : Store Email Address on Lower Case
		dto.setEmail(SicStringUtils.normalizeEmail(ws.getEmail()));
		dto.setAutorisationMailing(ws.getEmailOptin());
		dto.setCodeMedium(MediumCodeEnum.getEnumMandatory(ws.getMediumCode()).toString());		
		dto.setStatutMedium(ws.getMediumStatus());
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));
		
		return dto;
		
	}
	
	public static IndividuDTO transformToIndividuDTO(IndividualRequest ws) throws InvalidParameterException {
		
		if(ws==null || ws.getIndividualInformations() == null) {
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
	
	public static ProfilsDTO transformToProfilsDTO(IndividualProfil ws) {
		
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
	
	public static IndividuDTO transformToIndividuDTO(IndividualInformations ws) throws InvalidParameterException {
		
		if(ws == null) {
			return null;
		}
		
		IndividuDTO dto = new IndividuDTO();
		
		dto.setDateNaissance(ws.getBirthDate());
		dto.setCivilite(StringUtils.upperCase(ws.getCivility()));
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
	
	public static List<CommunicationPreferencesDTO> transformToComPrefsDTO(List<ComunicationPreferencesRequest> adh,Requestor requestor) {
		
		List<CommunicationPreferencesDTO> dto = null;
		if (!adh.isEmpty()) {
			dto = new ArrayList<CommunicationPreferencesDTO>();
			for (ComunicationPreferencesRequest comPrefRequest : adh) {
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

				if (comPref.getMarketLanguage() != null
						&& !comPref.getMarketLanguage().isEmpty()) {
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
						mlsDto.add(mlDto);
					}
					comPrefDto.setCreationDate(new Date());
					comPrefDto.setCreationSignature(requestor.getSignature());
					comPrefDto.setCreationSite(requestor.getSite());
					comPrefDto.setMarketLanguageDTO(mlsDto);
				}
				dto.add(comPrefDto);
			}
		}
		return dto;
	}
	
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
			dtoList.add(transformToDelegate(ws.getDelegationData(), gin)); 
		}
		
		return dtoList;
	}
	
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

	public static List<PreferenceDTO> transformBDMToPreferenceDTO(MarketingDataRequest marketingDataRequest, SignatureDTO signatureAPP) {
		List<PreferenceDTO> prefDtoList = null;
		
		if (marketingDataRequest != null && marketingDataRequest.getMarketingInformation() != null) {
			prefDtoList = new ArrayList<PreferenceDTO>();
			
			// Travel Doc 
			if (marketingDataRequest.getMarketingInformation().getTravelDocument() != null) {
				transformTravelDocToPreferenceDto(prefDtoList, marketingDataRequest.getMarketingInformation().getTravelDocument(), signatureAPP);
			}
			
			// Apis data
			if (marketingDataRequest.getMarketingInformation().getApisData() != null) {
				transformApisDataToPreferenceDto(prefDtoList, marketingDataRequest.getMarketingInformation().getApisData(), signatureAPP);
			}

			// Emergency Contact
			if (marketingDataRequest.getMarketingInformation().getEmergencyContact() != null) {
				transformEmergencyCtcToPreferenceDto(prefDtoList, marketingDataRequest.getMarketingInformation().getEmergencyContact());
			}
			
			// Travel Preferences
			if (marketingDataRequest.getMarketingInformation().getTravelPreferences() != null) {
				transformTravelPrefToPreferenceDto(prefDtoList, marketingDataRequest.getMarketingInformation().getTravelPreferences());
			}
			
			// Handicap
			if (marketingDataRequest.getMarketingInformation().getHandicap() != null) {
				transformHandicapToPreferenceDto(prefDtoList, marketingDataRequest.getMarketingInformation().getHandicap());
			}
			
			// Travel Companion
			if (marketingDataRequest.getMarketingInformation().getTravelCompanion() != null) {
				transformTravelCompanionToPreferenceDto(prefDtoList, marketingDataRequest.getMarketingInformation().getTravelCompanion());
			}
			
			// Personal Information
			if (marketingDataRequest.getMarketingInformation().getPersonalInformation() != null) {
				transformPersonalInfoToPreferenceDto(prefDtoList, marketingDataRequest.getMarketingInformation().getPersonalInformation());
			}
			
		}
		
		return prefDtoList;
	}

	private static void transformTravelDocToPreferenceDto(List<PreferenceDTO> prefDtoList, List<MaccTravelDocument> travelDocumentList, SignatureDTO signatureFromWs) {
		if (travelDocumentList != null && !travelDocumentList.isEmpty()) {
			String touchpoint = "";
			String authorizationDate = "";
			
			// Fill touchpoint and authorizationDate which is mandatory in DB
			if (signatureFromWs != null) {
				if (signatureFromWs.getApplicationCode() != null) {
					touchpoint = signatureFromWs.getApplicationCode();
				} 
				if (signatureFromWs.getApplicationCode() != null && signatureFromWs.getSignature() != null) {
					touchpoint = touchpoint.concat(" / ");
				}
				if (signatureFromWs.getSignature() != null) {
					touchpoint = touchpoint + signatureFromWs.getSignature();
				}
				if (signatureFromWs.getDate() != null) {
					authorizationDate = SicDateUtils.dateToString(signatureFromWs.getDate());
				}
				else {
					authorizationDate = SicDateUtils.dateToString(new Date());
				}
			}
			
			// Transform each TravelDoc field into new preference structure and keys
			for (MaccTravelDocument travelDoc : travelDocumentList) {
				PreferenceDTO preferenceTravelDoc = new PreferenceDTO();
				Set<PreferenceDataDTO> preferenceDataList = new HashSet<PreferenceDataDTO>();
				
				preferenceTravelDoc.setType("TDC"); //TODO use constant for all values below
				preferenceTravelDoc.setPreferenceDataDTO(preferenceDataList);
				
				
				if (travelDoc.getType() != null) {
					preferenceDataList.add(new PreferenceDataDTO("type", travelDoc.getType()));
				}
				if (travelDoc.getNumber() != null) {
					preferenceDataList.add(new PreferenceDataDTO("number", travelDoc.getNumber()));
				}
				if (travelDoc.getExpirationDate() != null) {
					preferenceDataList.add(new PreferenceDataDTO("expiryDate", SicDateUtils.dateToString(travelDoc.getExpirationDate())));
				}
				if (travelDoc.getNationality() != null) {
					preferenceDataList.add(new PreferenceDataDTO("nationality", travelDoc.getNationality()));
				}
				if (travelDoc.getIssueDate() != null) {
					preferenceDataList.add(new PreferenceDataDTO("issueDate", SicDateUtils.dateToString(travelDoc.getIssueDate())));
				}
				if (travelDoc.getCountryIssued() != null) {
					preferenceDataList.add(new PreferenceDataDTO("countryOfIssue", travelDoc.getCountryIssued()));
				}
				preferenceDataList.add(new PreferenceDataDTO("touchpoint", touchpoint));
				preferenceDataList.add(new PreferenceDataDTO("authorizationDate", authorizationDate));
				
				prefDtoList.add(preferenceTravelDoc);
			}
		}
	}
	
	private static void transformApisDataToPreferenceDto(List<PreferenceDTO> preferenceDTOList, MaccApisData apisData, SignatureDTO signatureFromWs) {
		if (apisData != null) {
			// *****************  NEW PROCESS  **************************
			
			PreferenceDTO preferenceApis = new PreferenceDTO();
			Set<PreferenceDataDTO> apisPreferenceDataList = new HashSet<PreferenceDataDTO>();
			
			preferenceApis.setType("APC");
			preferenceApis.setPreferenceDataDTO(apisPreferenceDataList);
			
			if (apisData.getFirstName() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("givenNames", apisData.getFirstName()));
			}
			if (apisData.getLastName() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("familyName", apisData.getLastName()));
			}
			if (apisData.getDateOfBirth() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("dateOfBirth", SicDateUtils.dateToString(apisData.getDateOfBirth())));
			}
			if (apisData.getGender() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("gender", apisData.getGender()));
			}
			if (apisData.getGreenCardNumber() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("greenCardNumber", apisData.getGreenCardNumber()));
			}
			if (apisData.getGreenCardExpiryDate() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("greenCardExpiryDate", SicDateUtils.dateToString(apisData.getGreenCardExpiryDate())));
			}
			if (apisData.getCountryOfResidence() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("countryOfResidence", apisData.getCountryOfResidence()));
			}
			if (apisData.getAddressUS() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("address", apisData.getAddressUS()));
			}
			if (apisData.getPostCodeUS() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("zipCode", apisData.getPostCodeUS()));
			}
			if (apisData.getCityUS() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("city", apisData.getCityUS()));
			}
			if (apisData.getStateUS() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("state", apisData.getStateUS()));
			}
			if (apisData.getRedressControlNumber() != null) {
				apisPreferenceDataList.add(new PreferenceDataDTO("redressControlNumber", apisData.getRedressControlNumber()));
			}
			
			if (!apisPreferenceDataList.isEmpty()) {
				preferenceDTOList.add(preferenceApis);
			}
			
			// **************** END NEW PROCESS *************************
		}
	}

	private static void transformEmergencyCtcToPreferenceDto(List<PreferenceDTO> prefDtoList, List<MaccEmergencyContacts> emergencyContact) {
		for (MaccEmergencyContacts ecc : emergencyContact) {
			PreferenceDTO preferenceDTO = new PreferenceDTO();
			preferenceDTO.setType("ECC");

			preferenceDTO.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
			
			if (ecc.getEmail() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("email", ecc.getEmail()));
			}
			if (ecc.getFirstName() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", ecc.getFirstName()));
			}
			if (ecc.getLastName() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", ecc.getLastName()));
			}
			if (ecc.getPhoneNumber() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("phoneNumber", ecc.getPhoneNumber()));
			}
			
			if (!preferenceDTO.getPreferenceDataDTO().isEmpty()) {
				prefDtoList.add(preferenceDTO);
			}
		}
	}

	private static void transformTravelPrefToPreferenceDto(List<PreferenceDTO> prefDtoList, MaccTravelPreferences travelPreferences) {
		if (travelPreferences != null) {
			PreferenceDTO preferenceDTO = new PreferenceDTO();
			preferenceDTO.setType("TPC");

			preferenceDTO.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
			
			if (travelPreferences.getArrivalAirport() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("arrivalAirport", travelPreferences.getArrivalAirport()));
			}
			if (travelPreferences.getArrivalCity() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("arrivalCity", travelPreferences.getArrivalCity()));
			}
			if (travelPreferences.getBoardingPass() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("boardingPass", travelPreferences.getBoardingPass()));
			}
			if (travelPreferences.getCustomerLeisure() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("customerLeisure", travelPreferences.getCustomerLeisure()));
			}
			if (travelPreferences.getDepartureAirport() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("departureAirport", travelPreferences.getDepartureAirport()));
			}
			if (travelPreferences.getDepartureCity() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("departureCity", travelPreferences.getDepartureCity()));
			}
			if (travelPreferences.getMeal() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("meal", travelPreferences.getMeal()));
			}
			if (travelPreferences.getSeat() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("seat", travelPreferences.getSeat()));
			}
			if (travelPreferences.getTravelClass() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("travelClass", travelPreferences.getTravelClass()));
			}
			
			if (!preferenceDTO.getPreferenceDataDTO().isEmpty()) {
				prefDtoList.add(preferenceDTO);
			}
		}	
	}

	private static void transformHandicapToPreferenceDto(List<PreferenceDTO> prefDtoList, MaccHandicap handicap) {
		if (handicap != null) {
			PreferenceDTO preferenceDTO = new PreferenceDTO();
			preferenceDTO.setType("HDC");

			preferenceDTO.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
			
			if (handicap.getCodeHCP1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("codeHCP1", handicap.getCodeHCP1()));
			}
			if (handicap.getCodeHCP2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("codeHCP2", handicap.getCodeHCP2()));
			}
			if (handicap.getCodeHCP3() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("codeHCP3", handicap.getCodeHCP3()));
			}
			if (handicap.getCodeMat1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("codeMat1", handicap.getCodeMat1()));
			}
			if (handicap.getCodeMat2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("codeMat2", handicap.getCodeMat2()));
			}
			if (handicap.getDogGuideBreed() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("dogGuideBreed", handicap.getDogGuideBreed()));
			}
			if (handicap.getDogGuideFlag() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("dogGuideFlag", handicap.getDogGuideFlag()));
			}
			if (handicap.getDogGuideWeight() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("dogGuideWeight", handicap.getDogGuideWeight().toString()));
			}
			if (handicap.getHeight1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("height1", handicap.getHeight1().toString()));
			}
			if (handicap.getHeight2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("height2", handicap.getHeight2().toString()));
			}
			if (handicap.getHeightPlie1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("heightPlie1", handicap.getHeightPlie1().toString()));
			}
			if (handicap.getHeightPlie2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("heightPlie2", handicap.getHeightPlie2().toString()));
			}
			if (handicap.getLength1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("length1", handicap.getLength1().toString()));
			}
			if (handicap.getLength2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("length2", handicap.getLength2().toString()));
			}
			if (handicap.getLengthPlie1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("lengthPlie1", handicap.getLengthPlie1().toString()));
			}
			if (handicap.getLengthPlie2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("lengthPlie2", handicap.getLengthPlie2().toString()));
			}
			if (handicap.getMedaCCAccomp() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCAccomp", handicap.getMedaCCAccomp()));
			}
			if (handicap.getMedaCCDateEnd() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCDateEnd", SicDateUtils.dateToString(handicap.getMedaCCDateEnd())));
			}
			if (handicap.getMedaCCDateStart() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCDateStart", SicDateUtils.dateToString(handicap.getMedaCCDateStart())));
			}
			if (handicap.getMedaCCFlag() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCFlag", handicap.getMedaCCFlag()));
			}
			if (handicap.getMedaCCTxt() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaCCTxt", handicap.getMedaCCTxt()));
			}
			if (handicap.getMedaLCAccomp() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCAccomp", handicap.getMedaLCAccomp()));
			}
			if (handicap.getMedaLCDateEnd() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCDateEnd", SicDateUtils.dateToString(handicap.getMedaLCDateEnd())));
			}
			if (handicap.getMedaLCDateStart() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCDateStart", SicDateUtils.dateToString(handicap.getMedaLCDateStart())));
			}
			if (handicap.getMedaLCFlag() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCFlag", handicap.getMedaLCFlag()));
			}
			if (handicap.getMedaLCTxt() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaLCTxt", handicap.getMedaLCTxt()));
			}
			if (handicap.getMedaMCAccomp() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCAccomp", handicap.getMedaMCAccomp()));
			}
			if (handicap.getMedaMCDateEnd() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCDateEnd", SicDateUtils.dateToString(handicap.getMedaMCDateEnd())));
			}
			if (handicap.getMedaMCDateStart() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCDateStart", SicDateUtils.dateToString(handicap.getMedaMCDateStart())));
			}
			if (handicap.getMedaMCFlag() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCFlag", handicap.getMedaMCFlag()));
			}
			if (handicap.getMedaMCTxt() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("medaMCTxt", handicap.getMedaMCTxt()));
			}
			if (handicap.getOtherMat() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("otherMat", handicap.getOtherMat()));
			}
			if (handicap.getOxygFlag() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("oxygFlag", handicap.getOxygFlag()));
			}
			if (handicap.getOxygOutput() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("oxygOutput", handicap.getOxygOutput().toString()));
			}
			if (handicap.getWeight1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("weight1", handicap.getWeight1().toString()));
			}
			if (handicap.getWeight2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("weight2", handicap.getWeight2().toString()));
			}
			if (handicap.getWidth1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("width1", handicap.getWidth1().toString()));
			}
			if (handicap.getWidth2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("width2", handicap.getWidth2().toString()));
			}
			if (handicap.getWidthPlie1() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("widthPlie1", handicap.getWidthPlie1().toString()));
			}
			if (handicap.getWidthPlie2() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("widthPlie2", handicap.getWidthPlie2().toString()));
			}

			if (!preferenceDTO.getPreferenceDataDTO().isEmpty()) {
				prefDtoList.add(preferenceDTO);
			}
		}		
	}

	private static void transformTravelCompanionToPreferenceDto(List<PreferenceDTO> prefDtoList, List<MaccTravelCompanion> travelCompanion) {
		for (MaccTravelCompanion tcc : travelCompanion) {
			PreferenceDTO preferenceDTO = new PreferenceDTO();
			preferenceDTO.setType("TCC");

			preferenceDTO.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
			
			if (tcc.getCivility() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("civility", tcc.getCivility()));
			}
			if (tcc.getDateOfBirth() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("dateOfBirth", SicDateUtils.dateToString(tcc.getDateOfBirth())));
			}
			if (tcc.getEmail() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("email", tcc.getEmail()));
			}
			if (tcc.getFirstName() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("firstName", tcc.getFirstName()));
			}
			if (tcc.getLastName() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("lastName", tcc.getLastName()));
			}
			if (tcc.getPersonalInformation() != null) {
				if (tcc.getPersonalInformation().getBlueBizNumber() != null) {
					preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("bluebizNumber", tcc.getPersonalInformation().getBlueBizNumber()));
				}
			}
			if (tcc.getPersonalInformation() != null) {
				if (tcc.getPersonalInformation().getFFPNumber() != null) {
					preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("FFPNumber", tcc.getPersonalInformation().getFFPNumber()));
				}
			}
			if (tcc.getPersonalInformation() != null) {
				if (tcc.getPersonalInformation().getFFPProgram() != null) {
					preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("FFPProgram", tcc.getPersonalInformation().getFFPProgram()));
				}
			}
			
			if (!preferenceDTO.getPreferenceDataDTO().isEmpty()) {
				prefDtoList.add(preferenceDTO);
			}
		}	
	}

	private static void transformPersonalInfoToPreferenceDto(List<PreferenceDTO> prefDtoList, MaccPersonalInformation personalInformation) {
		if (personalInformation != null) {
			PreferenceDTO preferenceDTO = new PreferenceDTO();
			preferenceDTO.setType("PIC");

			preferenceDTO.setPreferenceDataDTO(new HashSet<PreferenceDataDTO>());
			
			if (personalInformation.getBlueBizNumber() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("bluebizNumber", personalInformation.getBlueBizNumber()));
			}
			if (personalInformation.getFFPNumber() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("FFPNumber", personalInformation.getFFPNumber()));
			}
			if (personalInformation.getFFPProgram() != null) {
				preferenceDTO.getPreferenceDataDTO().add(new PreferenceDataDTO("FFPProgram", personalInformation.getFFPProgram()));
			}
			
			if (!preferenceDTO.getPreferenceDataDTO().isEmpty()) {
				prefDtoList.add(preferenceDTO);
			}
		}	
	}
	
}
