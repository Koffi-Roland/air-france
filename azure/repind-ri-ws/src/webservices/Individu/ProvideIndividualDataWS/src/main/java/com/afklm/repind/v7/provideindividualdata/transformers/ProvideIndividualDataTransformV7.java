package com.afklm.repind.v7.provideindividualdata.transformers;

import com.afklm.repind.v7.provideindividualdata.type.PopulationTypeEnum;
import com.afklm.repind.v7.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v7.response.*;
import com.afklm.soa.stubs.w000418.v7.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000418.v7.siccommontype.Signature;
import com.afklm.soa.stubs.w000418.v7.sicindividutype.*;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.delegation.DelegationDataInfoDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.profil.Profil_afDTO;
import com.airfrance.repind.dto.profil.Profil_mereDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.role.RoleTravelersDTO;
import com.airfrance.repind.dto.role.RoleUCCRDTO;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class ProvideIndividualDataTransformV7 {
	
	public final static int CONTRACT_LIMIT = 25;
	public final static int EMAIL_LIMIT = 2;
	public final static int POSTAL_ADDRESS_LIMIT = 5;
	public final static int TELECOM_LIMIT = 2;
	private final static String F_ASKED = ForgetEnum.ASKED.getCode();         // A
	private final static String F_CONFIRMED = ForgetEnum.CONFIRMED.getCode(); // C
	private final static String F_FORCED = ForgetEnum.FORCED.getCode(); // C
	private final static String F_PROCESSED = ForgetEnum.PROCESSED.getCode(); // P

	private static class DelegationDataGroup {
		
		private String type;
		private String key;
		private String value;
		
		public DelegationDataGroup(String type, String key, String value) {
			this.type = type;
			this.key = key;
			this.value = value;
		}
		
		public String getType() {
			return type;
		}
		public String getKey() {
			return key;
		}
		public String getValue() {
			return value;
		}		
	}
	
	public static ProvideIndividualInformationResponse transformToProvideIndividualInformationResponse(IndividuDTO dto, Set<ScopesToProvideEnum> scopesToProvideSet, Boolean isIndividu) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}
		
		ProvideIndividualInformationResponse ws = new ProvideIndividualInformationResponse();
		ws.setIndividualResponse(transformToIndividualResponse(dto, scopesToProvideSet, isIndividu));
	
		return ws;
	
	}
	public static void transformToContractResponse(List<RoleContratsDTO> dtoList, List<ContractResponse> wsList) {
		transformToContractResponse(dtoList, wsList, true);
	}	
	public static void transformToContractResponse(List<RoleContratsDTO> dtoList, List<ContractResponse> wsList, boolean isFBRecognitionActivate) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(RoleContratsDTO dto : dtoList) {
			
			if(wsList.size()==CONTRACT_LIMIT) {
				break;
			}
			
			wsList.add(transformToContractResponse(dto));
		}
		
	}
	
	public static ContractResponse transformToContractResponse(RoleContratsDTO dto) {
		
		if (dto == null) {
			return null;
		}

		ContractResponse ws = new ContractResponse();
		ws.getContractData().addAll(transformToContractDataList(dto));
		ws.setContract(transformToContract(dto));
		transformToSignature(dto, ws.getSignature());
	
		return ws;
	}

	private static Collection<? extends ContractData> transformToContractDataList(RoleContratsDTO dto) {
		List<ContractData> contractDataList = new ArrayList<ContractData>();
		if(dto.getPermissionPrime() != null && !dto.getPermissionPrime().isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.BONUSPERMISSION.getKey(), dto.getPermissionPrime()));
		}
		String soldeMiles = SicStringUtils.getStringFromObject(dto.getSoldeMiles());
		if(soldeMiles != null && !soldeMiles.isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.MILESBALANCE.getKey(), soldeMiles));
		}
		if(dto.getFamilleProduit() != null && !dto.getFamilleProduit().isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.PRODUCTFAMILY.getKey(), dto.getFamilleProduit()));
		}
		String segmentsQualif = SicStringUtils.getStringFromObject(dto.getSegmentsQualif());
		if(segmentsQualif != null && !segmentsQualif.isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.QUALIFYINGSEGMENTS.getKey(), segmentsQualif));
		}
		String segmentsQualifPrec = SicStringUtils.getStringFromObject(dto.getSegmentsQualifPrec());
		if(segmentsQualifPrec != null && !segmentsQualifPrec.isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.QUALIFYINGHISTSEGMENTS.getKey(), segmentsQualifPrec));
		}
		String milesQualif = SicStringUtils.getStringFromObject(dto.getMilesQualif());
		if(milesQualif != null && !milesQualif.isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.QUALIFYINGMILES.getKey(), milesQualif));
		}
		String milesQualifPrec = SicStringUtils.getStringFromObject(dto.getMilesQualifPrec());
		if(milesQualifPrec != null && !milesQualifPrec.isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.QUALIFYINGHISTMILES.getKey(), milesQualifPrec));
		}
		if(dto.getTier() != null && !dto.getTier().isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.TIERLEVEL.getKey(), dto.getTier()));
		}
		if(dto.getMemberType() != null && !dto.getMemberType().isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.MEMBERTYPE.getKey(), dto.getMemberType()));
		}
		String version = SicStringUtils.getStringFromObject(dto.getVersion());
		if(version != null && !version.isEmpty()) {
		contractDataList.add(transformToContractData(ContractDataKeyEnum.VERSION.getKey(), version));
		}
		if(dto.getSourceAdhesion() != null && !dto.getSourceAdhesion().isEmpty()) {
			contractDataList.add(transformToContractData(ContractDataKeyEnum.PRODUCTFAMILY.getKey(), dto.getSourceAdhesion()));
		}
		
		return contractDataList;
	}

	public static void transformToTravelerResponse(List<RoleTravelersDTO> dtoList, List<ContractResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		
		
		for(RoleTravelersDTO dto : dtoList) {
			
			if(wsList.size()==CONTRACT_LIMIT) {
				break;
			}
			
			wsList.add(transformToTravelerResponse(dto));
		}
		
	}
	
	public static ContractResponse transformToTravelerResponse(RoleTravelersDTO dto) {
		
		if (dto == null) {
			return null;
		}

		ContractResponse ws = new ContractResponse();
		
		
		// REPIND-663 : Dans le cas d'un Traveler, on ajoute le type du contrat //
		ContractV2 contract = new ContractV2();
		contract.setProductType("TR");				// Traveler
		contract.setContractStatus("C");			// Contrat Cree
		ws.setContract(contract);
		//////////////////////////////////////////////////////////////////////////
		
		ws.getContractData().addAll(transformToTraveler(dto));
		transformTravelerToSignature(dto, ws.getSignature());
	
		return ws;
	}
	
	public static List<ContractData> transformToTraveler(RoleTravelersDTO dto) {
		
		if (dto == null) {
			return null;
		}
		List<ContractData> contractDataList = new ArrayList<ContractData>();
		
		if(dto.getLastRecognitionDate() != null && !dto.getLastRecognitionDate().toString().isEmpty()) {
			ContractData contractData = new ContractData();

			contractData.setKey(ContractDataKeyEnum.LASTRECOGNITIONDATE.getKey());
			contractData.setValue(dto.getLastRecognitionDate().toString());

			contractDataList.add(contractData);
		}
		
		if(dto.getMatchingRecognitionCode() != null && !dto.getMatchingRecognitionCode().isEmpty()) {
			ContractData contractData2 = new ContractData();

			contractData2.setKey(ContractDataKeyEnum.MATCHINGRECOGNITION.getKey());
			contractData2.setValue(dto.getMatchingRecognitionCode());

			contractDataList.add(contractData2);
		}
		
		return contractDataList;
	}

	public static void transformTravelerToSignature(RoleTravelersDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(RoleContratsDTO dto, List<Signature> list) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		list.add(creation);
		list.add(modification);
	}
	
	public static ContractV2 transformToContract(RoleContratsDTO dto) {
		
		if (dto == null) {
			return null;
		}
	
		ContractV2 ws = new ContractV2();
		ws.setContractNumber(dto.getNumeroContrat());
		ws.setContractStatus(dto.getEtat());
		ws.setContractType(transformToContractType(dto.getBusinessroledto()));
		ws.setCompanyCode(dto.getCodeCompagnie());
		ws.setIataCode(dto.getAgenceIATA());
		ws.setProductSubType(dto.getSousType());
		ws.setProductType(dto.getTypeContrat());
		ws.setValidityEndDate(dto.getDateFinValidite());
		ws.setValidityStartDate(dto.getDateDebutValidite());
		return ws;
	}
	
	private static ContractData transformToContractData(String key, String value){
		ContractData cd = new ContractData();
		cd.setKey(key);
		cd.setValue(value);
		return cd;
	}
	
	public static String transformToContractType(BusinessRoleDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		return dto.getType();
	}
	
	public static IndividualResponse transformToIndividualResponse(IndividuDTO dto, Set<ScopesToProvideEnum> scopesToProvideSet, Boolean isIndividu) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}
		
		IndividualResponse ws = new IndividualResponse();
		ws.setIndividualInformations(transformToIndividualInformations(dto, isIndividu));
		if(scopesToProvideSet != null && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.IDENTIFICATION))) {
			ws.setCivilian(transformToCivilian(dto));
			ws.setIndividualProfil(transformToIndividualProfil(dto.getProfilsdto()));
			ws.setNormalizedName(transformToNormalizedName(dto));
			transformToAirFranceProfil(dto.getProfil_meredto(), ws.getAirFranceProfil());
			transformToSignature(dto,ws.getSignature());
			transformToUsageClient(null,ws.getUsageClient());
		}
	
		return ws;
	
	}
	
	public static void transformToAirFranceProfil(Set<Profil_mereDTO> dtoList, List<AirFranceProfilV2> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(Profil_mereDTO dto : dtoList) {
			wsList.add(transformToAirFranceProfil(dto));
		}
		
	}
	
	public static AirFranceProfilV2 transformToAirFranceProfil(Profil_mereDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		return transformToAirFranceProfil(dto.getProfil_afdto());
	}
	
	public static AirFranceProfilV2 transformToAirFranceProfil(Profil_afDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		AirFranceProfilV2 ws = new AirFranceProfilV2();
		
		ws.setCompagnyCode(dto.getScode_cie());
		ws.setFunction(dto.getSfonction());
		ws.setNotesAddress(dto.getSadr_notes());
		ws.setOriginCode(dto.getScode_origine());
// 		ws.setPassword(dto.getSpasswrd());
		ws.setRank(dto.getSrang());
		ws.setRegimental(dto.getSmatricule());
		ws.setRReference(dto.getSreference_r());
		ws.setStatusCode(dto.getScode_status());
		ws.setTypology(dto.getStypologie());
		
		return ws;
	}
	
	public static IndividualInformationsV2 transformToIndividualInformations(IndividuDTO dto, Boolean isIndividu) throws InvalidParameterException {
			
		if (dto==null) {
			return null;
		}
	
		IndividualInformationsV2 ws = new IndividualInformationsV2();
		ws.setBirthDate(dto.getDateNaissance());
		ws.setCivility(dto.getCivilite());
		ws.setFirstNamePseudonym(dto.getAliasPrenom());
		ws.setFirstNameSC(dto.getPrenomSC());
		if(StringUtils.isNotEmpty(dto.getNonFusionnable())){
			ws.setFlagNoFusion(OuiNonFlagEnum.getEnum(dto.getNonFusionnable()).toBoolean());
		}
		if(StringUtils.isNotEmpty(dto.getTierUtiliseCommePiege())){
			ws.setFlagThirdTrap(OuiNonFlagEnum.getEnum(dto.getTierUtiliseCommePiege()).toBoolean());
		}
		ws.setGender(dto.getSexe());
		ws.setIdentifier(dto.getSgin());
		ws.setLastNamePseudonym(dto.getAlias());
		ws.setLastNameSC(dto.getNomSC());
		// ws.setNationality(dto.getNationalite()); TODO : Deleted by Herve
		// Valadon 07/2016
		ws.setPersonalIdentifier(dto.getIdentifiantPersonnel());
		ws.setSecondFirstName(dto.getSecondPrenom());
// 		ws.setSecondNationality(dto.getAutreNationalite());
		ws.setStatus(dto.getStatutIndividu());
		ws.setVersion(String.valueOf(dto.getVersion()));
		String populationType = null;
		if(isIndividu) {
			// We are not sure of this rule
			// populationType = PopulationTypeEnum.INDIVIDU.toString();
			populationType = dto.getType();
		} else {
			populationType = PopulationTypeEnum.WHITE_WINGERS.toString();
		}
		ws.setPopulationType(populationType);
//		ws.setManagingCompany(dto.getCieGestionnaire());
//		ws.setDateFusion(SicDateUtils.stringToDate(dto.getDateFusion()));
//		ws.setGinFusion(dto.getGinFusion());
//		ws.setFlagBankFraud(dto.getIndicFraudBanq());
//		ws.setPassword(dto.getMotDePasse());
//		ws.setLastName(dto.getNom());
//		ws.setFirstName(dto.getPrenom());
	
		if(dto.getProfilsdto()!=null) {
			ws.setLanguageCode(dto.getProfilsdto().getScode_langue());
		}
		
		return ws;
	}

	public static Civilian transformToCivilian(IndividuDTO dto) {
		
		if (dto==null || StringUtils.isEmpty(dto.getCodeTitre())) {
			return null;
		}
	
		Civilian ws = new Civilian();
		ws.setTitleCode(dto.getCodeTitre());
	
		return ws;
	}
	
	public static NormalizedName transformToNormalizedName(IndividuDTO dto) {
		
		if (dto==null) {
			return null;
		}
	
		NormalizedName ws = new NormalizedName();
		ws.setFirstName(SicStringUtils.replaceNonPrintableChars(dto.getPrenom()));
		ws.setLastName(SicStringUtils.replaceNonPrintableChars(dto.getNom()));
	
		return ws;
	}

	public static IndividualProfilV2 transformToIndividualProfil(ProfilsDTO dto) {
			
			if (dto==null) {
				return null;
			}
		
			IndividualProfilV2 ws = new IndividualProfilV2();
//			ws.setChildrenNumber(SicStringUtils.getStringFromObject(dto.getInb_enfants()));
//			ws.setCivilianCode(dto.getScode_maritale());	TODO : Delete by Herve Valadon 07/2016
//			ws.setCustomerSegment(dto.getSsegment());
			ws.setEmailOptin(dto.getSmailing_autorise());
			ws.setLanguageCode(dto.getScode_langue());
//			ws.setProAreaCode(dto.getScode_professionnel());
	//		ws.setProAreaWording(dto.getLibelDomainePro()); TODO information à supprimer ?
//			ws.setProFunctionCode(dto.getScode_fonction());
	//		ws.setProFunctionWording(dto.getLibelFonctionPro()); TODO information à supprimer ?
//			ws.setStudentCode(dto.getSetudiant());
	//		ws.setProfilKey(SicStringUtils.getStringFromObject(dto.getCleProfil()));
	//		ws.setFlagSolvency(dto.getIndicSolvabilite());
		
			return ws;
	}

	public static void transformToUsageClient(Set<UsageClientsDTO> dtoList, List<UsageClient> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(UsageClientsDTO dto : dtoList) {
			wsList.add(transformToUsageClient(dto));
		}
		
	}
	
	public static UsageClient transformToUsageClient(UsageClientsDTO dto) {
		
		if (dto == null) {
			return null;
		}
	
		UsageClient ws = new UsageClient();
		ws.setApplicationCode(dto.getScode());
		ws.setAuthorizedModification(dto.getSconst());
		ws.setLastModificationDate(dto.getDate_modification());
		ws.setSrin(SicStringUtils.getStringFromObject(dto.getSrin()));
	
		return ws;
	}
	
	public static void transformToSignature(IndividuDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	// REPIND-555 : Prospect migration
//	public static void transformToLocalizationResponse(ProspectLocalizationDTO prospectLocalizationDTO, List<LocalizationResponse> list){
//		
//		if(prospectLocalizationDTO != null) {
//			LocalizationResponse ws = new LocalizationResponse();
//
//			ws.setLocalization(transformToLocalization(prospectLocalizationDTO));
//
//			ws.getSignatureV2().add(transformToSignatureV2(prospectLocalizationDTO));
//
//			list.add(ws);
//		}
//	}
	
	// REPIND-555 : Prospect migration
//	public static Localization transformToLocalization(ProspectLocalizationDTO prospectLocalizationDTO) {
//		Localization ws = new Localization();
//
//		ws.setCity(prospectLocalizationDTO.getCity());
//		ws.setCountryCode(prospectLocalizationDTO.getCountryCode());
//		ws.setMediumCode(prospectLocalizationDTO.getMediumCode());
//		ws.setMediumStatus(prospectLocalizationDTO.getMediumStatus());
//		ws.setPostalAddress(prospectLocalizationDTO.getPostalAddress());
//		ws.setStateCode(prospectLocalizationDTO.getStateCode());
//		ws.setZipCode(prospectLocalizationDTO.getZipCode());
//
//		return ws;
//	}
//	
//	public static SignatureV2 transformToSignatureV2(ProspectLocalizationDTO prospectLocalizationDTO) {
//		SignatureV2 signature = new SignatureV2();
//
//		signature.setCreationDate(prospectLocalizationDTO.getCreationDate());
//		signature.setCreationSignature(prospectLocalizationDTO.getCreationSignature());
//		signature.setCreationSite(prospectLocalizationDTO.getCreationSite());
//		signature.setModificationDate(prospectLocalizationDTO.getModificationDate());
//		signature.setModificationSignature(prospectLocalizationDTO.getModificationSignature());
//		signature.setModificationSite(prospectLocalizationDTO.getModificationSite());
//
//		return signature;
//	}

	public static void transformToEmailResponse(List<EmailDTO> dtoList, List<EmailResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(EmailDTO dto : dtoList) {
			
			if(wsList.size()==EMAIL_LIMIT) {
				break;
			}
			
			wsList.add(transformToEmailResponse(dto));
		}
		
	}
	
	public static EmailResponse transformToEmailResponse(EmailDTO dto) {
		
		if (dto==null) {
			return null;
		}
	
		EmailResponse ws = new EmailResponse();
		ws.setEmail(transformToEmail(dto));
		transformToSignature(dto,ws.getSignature());
		
		return ws;
	}

	public static Email transformToEmail(EmailDTO dto) {
		
		if (dto==null) {
			return null;
		}
	
		Email ws = new Email();
		ws.setEmail(SicStringUtils.replaceNonPrintableChars(dto.getEmail()));
		ws.setEmailOptin(dto.getAutorisationMailing());
		ws.setMediumCode(dto.getCodeMedium());
		ws.setMediumStatus(dto.getStatutMedium());
		ws.setVersion(SicStringUtils.getStringFromObject(dto.getVersion()));
	//	ws.setAdditionalInformations(dto.getInfosCompl());
	
		return ws;
	}
	
	public static void transformToSignature(EmailDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToPostalAddressResponse(List<PostalAddressDTO> dtoList, List<PostalAddressResponse> wsList) throws InvalidParameterException {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(PostalAddressDTO dto : dtoList) {
			
			if(wsList.size()==POSTAL_ADDRESS_LIMIT) {
				break;
			}
			
			wsList.add(transformToPostalAddressResponse(dto));
		}
		
	}
	
	public static PostalAddressResponse transformToPostalAddressResponse(PostalAddressDTO dto) throws InvalidParameterException {
		
		if (dto==null) {
			return null;
		}
	
		PostalAddressResponse ws = new PostalAddressResponse();
		ws.setPostalAddressContent(transformToPostalAddressContent(dto));
		ws.setPostalAddressProperties(transformToPostalAddressProperties(dto));
		transformToSignature(dto,ws.getSignature());
		transformToUsageAddress(dto.getUsage_mediumdto(), ws.getUsageAddress());
		
		return ws;
	}
	
	public static PostalAddressContent transformToPostalAddressContent(PostalAddressDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		PostalAddressContent ws = new PostalAddressContent();
		
		ws.setCity(SicStringUtils.replaceNonPrintableChars(dto.getSville()));
		ws.setZipCode(SicStringUtils.replaceNonPrintableChars(dto.getScode_postal()));
		ws.setCountryCode(SicStringUtils.replaceNonPrintableChars(dto.getScode_pays()));
		ws.setDistrict(SicStringUtils.replaceNonPrintableChars(dto.getSlocalite()));
		ws.setStateCode(SicStringUtils.replaceNonPrintableChars(dto.getScode_province()));
		ws.setAdditionalInformation(SicStringUtils.replaceNonPrintableChars(dto.getScomplement_adresse()));
		ws.setCorporateName(SicStringUtils.replaceNonPrintableChars(dto.getSraison_sociale()));
		ws.setNumberAndStreet(SicStringUtils.replaceNonPrintableChars(dto.getSno_et_rue()));
		
//		ws.setAdresseKey(dto.getCleAdresse());
//		ws.setUsageNumber(dto.getNumeroUsage());
		
		return ws;
	}
	
	public static PostalAddressProperties transformToPostalAddressProperties(PostalAddressDTO dto) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}
		
		PostalAddressProperties ws = new PostalAddressProperties();
		
		if(StringUtils.isNotEmpty(dto.getSforcage())) {
			ws.setIndicAdrNorm(OuiNonFlagEnum.getEnum(dto.getSforcage()).toBoolean());
		}
		
		ws.setMediumCode(dto.getScode_medium());
		ws.setMediumStatus(dto.getSstatut_medium());
		ws.setVersion(SicStringUtils.getStringFromObject(dto.getVersion()));
		
		return ws;
	}
	
	public static void transformToUsageAddress(Set<Usage_mediumDTO> dtoList, List<UsageAddress> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(Usage_mediumDTO dto : dtoList) {
			wsList.add(transformToUsageAddress(dto));
		}
		
	}
	
	public static UsageAddress transformToUsageAddress(Usage_mediumDTO dto) {
		
		if (dto == null) {
			return null;
		}
	
		UsageAddress ws = new UsageAddress();
		ws.setAddressRoleCode(dto.getSrole1());
		ws.setApplicationCode(dto.getScode_application());
		ws.setUsageNumber(SicStringUtils.getStringFromObject(dto.getInum()));
	
		return ws;
	}
	
	public static void transformToSignature(PostalAddressDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDdate_creation());
		creation.setSignature(dto.getSignature_creation());
		creation.setSignatureSite(dto.getSsite_creation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDdate_modification());
		modification.setSignature(dto.getSsignature_modification());
		modification.setSignatureSite(dto.getSsite_modification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static void transformToTelecomResponse(List<TelecomsDTO> dtoList, List<TelecomResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(TelecomsDTO dto : dtoList) {
			wsList.add(transformToTelecomResponse(dto));
		}
		
	}

	public static TelecomResponse transformToTelecomResponse(TelecomsDTO dto) {
		
		if (dto==null) {
			return null;
		}

		TelecomResponse ws = new TelecomResponse();
		ws.setTelecom(transformToTelecom(dto));
		ws.setTelecomFlags(transformToTelecomFlags(dto));
		ws.setTelecomNormalization(transformToTelecomNormalization(dto));
		transformToSignature(dto, ws.getSignature());
		
		return ws;
	}
	
	public static Telecom transformToTelecom(TelecomsDTO dto) {
			
		if (dto==null) {
			return null;
		}

		Telecom ws = new Telecom();
		ws.setCountryCode(transformCountryCode(dto));
		ws.setMediumStatus(dto.getSstatut_medium());
		ws.setMediumCode(dto.getScode_medium());
		ws.setPhoneNumber(transformPhoneNumber(dto));
		ws.setTerminalType(dto.getSterminal());
		ws.setVersion(SicStringUtils.getStringFromObject(dto.getVersion()));
		
		return ws;
	}

	public static String transformCountryCode(TelecomsDTO dto) {
		
		String countryCode = "";
		
		if(StringUtils.isNotEmpty(dto.getSnorm_inter_country_code())) {
			countryCode = dto.getSnorm_inter_country_code();
		} else {
			countryCode = dto.getSindicatif();
		}
		
		return countryCode;
		
	}
	
	public static String transformPhoneNumber(TelecomsDTO dto) {
		
		String phoneNumber = "";
		
		if(StringUtils.isNotEmpty(dto.getSnorm_nat_phone_number_clean())) {
			phoneNumber = dto.getSnorm_nat_phone_number_clean();
		} else {
			phoneNumber = dto.getSnumero();
			
			if (StringUtils.isNotEmpty(dto.getScode_region())) {
				phoneNumber = dto.getScode_region() + phoneNumber;
			}
		}
		
		return phoneNumber;
		
	}
	
	public static TelecomFlags transformToTelecomFlags(TelecomsDTO dto) {
		
		if (dto==null) {
			return null;
		}
	
		TelecomFlags ws = new TelecomFlags();
		ws.setFlagInvalidFixTelecom(dto.isInvalidFixedLinePhone());
		ws.setFlagInvalidMobileTelecom(dto.isInvalidMobilePhone());
		ws.setFlagNoValidNormalizedTelecom(dto.isNoValidNormalizedTelecom());
		
		return ws;
	}

	public static TelecomNormalization transformToTelecomNormalization(TelecomsDTO dto) {
		
		if (dto==null) {
			return null;
		}
	
		TelecomNormalization ws = new TelecomNormalization();
		ws.setInternationalPhoneNumber(dto.getSnorm_inter_phone_number());
		ws.setIsoCountryCode(dto.getIsoCountryCode());
		
		return ws;
	}

	public static void transformToSignature(TelecomsDTO dto, List<Signature> wsList) {
	
		Signature creation = new Signature();
		creation.setDate(dto.getDdate_creation());
		creation.setSignature(dto.getSsignature_creation());
		creation.setSignatureSite(dto.getSsite_creation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDdate_modification());
		modification.setSignature(dto.getSsignature_modification());
		modification.setSignatureSite(dto.getSsite_modification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static void transformToExternalIdentifierResponse(List<ExternalIdentifierDTO> dtoList, List<ExternalIdentifierResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(ExternalIdentifierDTO dto : dtoList) {
			wsList.add(transformToExternalIdentifierResponse(dto));
		}
		
	}
	
	public static ExternalIdentifierResponse transformToExternalIdentifierResponse(ExternalIdentifierDTO dto) {
		
		if (dto==null) {
			return null;
		}

		ExternalIdentifierResponse ws = new ExternalIdentifierResponse();
		ws.setExternalIdentifier(transformToExternalIdentifier(dto));
		transformToExternalIdentifierDataResponse(dto.getExternalIdentifierDataList(), ws.getExternalIdentifierDataResponse());
		transformToSignature(dto, ws.getSignature());
		
		return ws;
	}
	
	public static ExternalIdentifier transformToExternalIdentifier(ExternalIdentifierDTO dto) {
		
		if (dto==null) {
			return null;
		}

		ExternalIdentifier ws = new ExternalIdentifier();
		ws.setIdentifier(dto.getIdentifier());
		ws.setType(dto.getType());
		
		return ws;
	}
	
	public static void transformToExternalIdentifierDataResponse(List<ExternalIdentifierDataDTO> dtoList, List<ExternalIdentifierDataResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(ExternalIdentifierDataDTO dto : dtoList) {
			wsList.add(transformToExternalIdentifierDataResponse(dto));
		}
		
	}
	
	public static ExternalIdentifierDataResponse transformToExternalIdentifierDataResponse(ExternalIdentifierDataDTO dto) {
		
		if (dto==null) {
			return null;
		}

		ExternalIdentifierDataResponse ws = new ExternalIdentifierDataResponse();
		ws.setExternalIdentifierData(transformToExternalIdentifierData(dto));
		transformToSignature(dto, ws.getSignature());
		
		return ws;
	}
	
	public static ExternalIdentifierData transformToExternalIdentifierData(ExternalIdentifierDataDTO dto) {
		
		if (dto==null) {
			return null;
		}

		ExternalIdentifierData ws = new ExternalIdentifierData();
		ws.setKey(dto.getKey());
		ws.setValue(dto.getValue());
		
		return ws;
	}
	
	public static void transformToSignature(ExternalIdentifierDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static void transformToSignature(ExternalIdentifierDataDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static AccountDataResponse transformToAccountDataResponse(AccountDataDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		AccountDataResponse ws = new AccountDataResponse();
		ws.setAccountData(transformToAccountData(dto));
		transformToSignature(dto, ws.getSignature());
		
		return ws;
	}
	
	public static AccountData transformToAccountData(AccountDataDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		AccountData ws = new AccountData();
		ws.setAccountIdentifier(dto.getAccountIdentifier());
		ws.setCustomerType(transformToCustomerType(dto));
		ws.setEmailIdentifier(dto.getEmailIdentifier());
		ws.setFbIdentifier(dto.getFbIdentifier());
//		ws.setPercentageFullProfil(); filled later
		ws.setPersonnalizedIdentifier(dto.getPersonnalizedIdentifier());
		ws.setSecretQuestion(dto.getSecretQuestion());
		ws.setPos(dto.getEnrolmentPointOfSell());
		ws.setWebsiteCarrier(dto.getCarrier());
		ws.setStatus(dto.getStatus());
		
		return ws;
	}
	
	public static String transformToCustomerType(AccountDataDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		// individual has an account -> MA
		String customerType = ProductTypeEnum.MY_ACCOUNT.toString();
		
		// a FB identifier exists -> FB
		if(StringUtils.isNotEmpty(dto.getFbIdentifier())) {
			customerType = ProductTypeEnum.FLYING_BLUE.toString();
		}
		
		return customerType;
	}
	
	public static void transformToSignature(AccountDataDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static DelegationDataResponse dtoTOws(List<DelegationDataDTO> delegatorDataListDTO, List<DelegationDataDTO> delegateDataListDTO) {
		
		if(delegatorDataListDTO==null) {
			return null;
		}
	
		DelegationDataResponse delegationDataResponse = new DelegationDataResponse();

		for (DelegationDataDTO delegatorDTO : delegatorDataListDTO){

			// we must not provide the C, RJ and RF status
			if (DelegationActionEnum.CANCELLED.getStatus().equals(delegatorDTO.getStatus()) || 
					DelegationActionEnum.REJECTED.getStatus().equals(delegatorDTO.getStatus()) || 
					DelegationActionEnum.DELETED.getStatus().equals(delegatorDTO.getStatus())){
				continue;
			}
			
			Delegator delegator = new Delegator();
			
			// Process changing the status so we know who made the invitation by reading the WS response
			if (delegatorDTO.getStatus().equals(DelegationActionEnum.INVITED.toString())){
				if (delegatorDTO.getSender().equals(DelegationSenderEnum.DELEGATE.toString())){
					delegatorDTO.setStatus(DelegationOutputStatusEnum.SENT.toString());
				} else if (delegatorDTO.getSender().equals(DelegationSenderEnum.DELEGATOR.toString())){
					delegatorDTO.setStatus(DelegationOutputStatusEnum.RECEIVED.toString());
				}
			}
			
			delegator.setDelegationStatusData(transformToDelegatorData(delegatorDTO));
			delegator.setDelegationIndividualData(transformToDelegationIndividualData(delegatorDTO.getDelegatorDTO()));
			transformToSignature(delegatorDTO, delegator.getSignature());
			transformToTelecom(delegatorDTO.getDelegatorDTO(), delegator.getTelecom());
			
			delegationDataResponse.getDelegator().add(delegator);
		}
		
		for (DelegationDataDTO delegateDTO : delegateDataListDTO){
			
			Delegate delegate = new Delegate();
			
			// Process changing the status so we know who made the invitation by reading the WS response
			if (delegateDTO.getStatus().equals(DelegationActionEnum.INVITED.toString())){
				if (delegateDTO.getSender().equals(DelegationSenderEnum.DELEGATOR.toString())){
					delegateDTO.setStatus(DelegationOutputStatusEnum.SENT.toString());
				} else if (delegateDTO.getSender().equals(DelegationSenderEnum.DELEGATE.toString())){
					delegateDTO.setStatus(DelegationOutputStatusEnum.RECEIVED.toString());
				}
			}
			
			delegate.setDelegationStatusData(transformToDelegateData(delegateDTO));
			delegate.setDelegationIndividualData(transformToDelegationIndividualData(delegateDTO.getDelegateDTO()));
			transformToSignature(delegateDTO, delegate.getSignature());
			transformToTelecom(delegateDTO.getDelegateDTO(), delegate.getTelecom());
			transformToComplementaryInformation(delegateDTO, delegate);
			
			delegationDataResponse.getDelegate().add(delegate);
		}

		return delegationDataResponse;
	}
	
	public static DelegationStatusData transformToDelegatorData(DelegationDataDTO delegatorDto) {
		
		if(delegatorDto==null) {
			return null;
		}
		
		DelegationStatusData ws = new DelegationStatusData();
		ws.setDelegationStatus(delegatorDto.getStatus());
		ws.setDelegationType(delegatorDto.getType());
		ws.setGin(delegatorDto.getDelegatorDTO().getSgin());
		
		return ws;
		
	}
	
	public static DelegationStatusData transformToDelegateData(DelegationDataDTO delegateDto) {
		
		if(delegateDto==null) {
			return null;
		}
		
		DelegationStatusData ws = new DelegationStatusData();
		ws.setDelegationStatus(delegateDto.getStatus());
		ws.setDelegationType(delegateDto.getType());
		ws.setGin(delegateDto.getDelegateDTO().getSgin());
		
		return ws;
		
	}
	
	public static DelegationIndividualData transformToDelegationIndividualData(com.airfrance.repind.dto.individu.IndividuDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		DelegationIndividualData ws = new DelegationIndividualData();
		
		ws.setFirstName(dto.getPrenom());
		ws.setFirstNameSC(dto.getPrenomSC());
		ws.setLastName(dto.getNom());
		ws.setLastNameSC(dto.getNomSC());
		ws.setCivility(dto.getCivilite());
		
		transformToDelegationIndividualData(dto.getAccountdatadto(), ws);
		
		return ws;
		
	}
	
	public static void transformToDelegationIndividualData(AccountDataDTO dto, DelegationIndividualData ws) {
		
		if(dto==null) {
			return;
		}
		
		ws.setAccountIdentifier(dto.getAccountIdentifier());
		ws.setEmailIdentifier(dto.getEmailIdentifier());
		ws.setFBIdentifier(dto.getFbIdentifier());
		
	}
	
	public static void transformToSignature(DelegationDataDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	// for delegation
	public static void transformToTelecom(com.airfrance.repind.dto.individu.IndividuDTO dto, List<Telecom> wsList) {
		
		if(dto==null) {
			return;
		}
		
		transformToTelecom(dto.getTelecoms(), wsList);
	}
	
	// for delegation
	public static void transformToTelecom(Set<TelecomsDTO> dtoList, List<Telecom> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(TelecomsDTO dto : dtoList) {
			wsList.add(transformToTelecom(dto));
		}
		
	}
	
	//for delegation
	public static void transformToComplementaryInformation(DelegationDataDTO delegationDataDTO, Delegate delegate) {

		if (delegationDataDTO.getDelegationDataInfoDTO() == null || delegationDataDTO.getDelegationDataInfoDTO().isEmpty()) {
			return;
		}
		DelegationTypeEnum type = DelegationTypeEnum.fromString(delegationDataDTO.getType());
		if (type == DelegationTypeEnum.UNACOMPAGNED_MINOR || type == DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT) {
			
			//Group DelegationDataInfo by TypeGroupId
			Map<Integer, List<DelegationDataGroup>> groupedDelegationDataInfo = groupDelegetationDataInfo(delegationDataDTO);
			
			for (Integer typeGroupId : groupedDelegationDataInfo.keySet()) {
				transformToComplementaryInformation(groupedDelegationDataInfo.get(typeGroupId), delegate);
			}
		}
	}
	
	//for delegation
	public static void transformToComplementaryInformation(List<DelegationDataGroup> delegationDataInfoList, Delegate delegate) {
		
		ComplementaryInformation complementaryInformation = new ComplementaryInformation();
		
		ComplementaryInformationDatas complementaryInformationDatas = new ComplementaryInformationDatas();
		complementaryInformation.setComplementaryInformationDatas(complementaryInformationDatas);
		
		
		for (DelegationDataGroup delegationDataGroup : delegationDataInfoList) {
			complementaryInformation.setType(delegationDataGroup.getType());
			
			ComplementaryInformationData complementaryInformationData = new ComplementaryInformationData();
			complementaryInformationData.setKey(delegationDataGroup.getKey());
			complementaryInformationData.setValue(delegationDataGroup.getValue());
			
			complementaryInformation.getComplementaryInformationDatas().getComplementaryInformationData().add(complementaryInformationData);
		}
		
		delegate.getComplementaryInformation().add(complementaryInformation);
	}
	
	private static Map<Integer, List<DelegationDataGroup>> groupDelegetationDataInfo (DelegationDataDTO delegationDataDTO) {
		
		Map<Integer, List<DelegationDataGroup>> groupedDelegationDataInfo = new HashMap<Integer, List<DelegationDataGroup>>();
		
		for (DelegationDataInfoDTO delegationDataInfoDTO : delegationDataDTO.getDelegationDataInfoDTO()) {
			if (!groupedDelegationDataInfo.containsKey(delegationDataInfoDTO.getTypeGroupId())) {
				groupedDelegationDataInfo.put(delegationDataInfoDTO.getTypeGroupId(), new ArrayList<DelegationDataGroup>());
				groupedDelegationDataInfo.get(delegationDataInfoDTO.getTypeGroupId()).add(new DelegationDataGroup(delegationDataInfoDTO.getType(), delegationDataInfoDTO.getKey(), delegationDataInfoDTO.getValue()));
			} else {
				groupedDelegationDataInfo.get(delegationDataInfoDTO.getTypeGroupId()).add(new DelegationDataGroup(delegationDataInfoDTO.getType(), delegationDataInfoDTO.getKey(), delegationDataInfoDTO.getValue()));
			}
		}
		
		return groupedDelegationDataInfo;
	}

	public static void transformToPrefilledNumbers(List<PrefilledNumbersDTO> dtoList, List<PrefilledNumbersResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(PrefilledNumbersDTO dto : dtoList) {
			PrefilledNumbersResponse prefilledNumbersResponse = transformToPrefilledNumbersResponse(dto);
			wsList.add(prefilledNumbersResponse);
		}

	}
	
	public static PrefilledNumbersResponse transformToPrefilledNumbersResponse(PrefilledNumbersDTO dto) {

		if(dto==null) {
			return null;
		}

		PrefilledNumbersResponse ws = new PrefilledNumbersResponse();
		ws.setPrefilledNumbers(transformToPrefilledNumbers(dto));

		if(dto.getPrefilledNumbersDataDTO() != null && !dto.getPrefilledNumbersDataDTO().isEmpty()) {
			for(PrefilledNumbersDataDTO pndDTO : dto.getPrefilledNumbersDataDTO()) {
				PrefilledNumbersData pnd = new PrefilledNumbersData();
				pnd.setKey(pndDTO.getKey());
				pnd.setValue(pndDTO.getValue());
				ws.getPrefilledNumbersData().add(pnd);
			}
		}

		transformToSignature(dto, ws.getSignature());

		return ws;
	}

	public static PrefilledNumbers transformToPrefilledNumbers(PrefilledNumbersDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		PrefilledNumbers ws = new PrefilledNumbers();
		ws.setContractNumber(dto.getContractNumber());
		ws.setContractType(dto.getContractType());
		
		return ws;
	}
	
	public static void transformToSignature(PrefilledNumbersDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToCommunicationPreferencesResponse(List<CommunicationPreferencesDTO> dtoList, List<CommunicationPreferencesResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(CommunicationPreferencesDTO dto : dtoList) {
			CommunicationPreferencesResponse ws = transformToCommunicationPreferencesResponse(dto);
			wsList.add(ws);
		}
	}
	
	public static CommunicationPreferencesResponse transformToCommunicationPreferencesResponse(CommunicationPreferencesDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		CommunicationPreferencesResponse ws = new CommunicationPreferencesResponse();
		ws.setCommunicationPreferences(transformToCommunicationPreferences(dto));
		
		return ws;
	}
	
	public static CommunicationPreferences transformToCommunicationPreferences(CommunicationPreferencesDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		CommunicationPreferences ws = new CommunicationPreferences();
		ws.setDomain(dto.getDomain());
		ws.setCommunicationGroupeType(dto.getComGroupType());
		ws.setCommunicationType(dto.getComType());
		ws.setOptIn(dto.getSubscribe());
		ws.setDateOfConsent(dto.getDateOptin());
		ws.setDateOfConsentPartner(dto.getDateOptinPartners());
		ws.setDateOfEntry(dto.getDateOfEntry());
		ws.setSubscriptionChannel(dto.getChannel());
		ws.setOptInPartner(dto.getOptinPartners());
		
		transformToMarketLanguage(dto.getMarketLanguageDTO(), ws.getMarketLanguage());
		transformToSignature(dto, ws.getSignature());
		
		Media media = new Media();
		ws.setMedia(media);
		
		if(dto.getMedia1() != null) {
			ws.getMedia().setMedia1(dto.getMedia1());
		}
		if(dto.getMedia2() != null) {
			ws.getMedia().setMedia2(dto.getMedia2());
		}
		if(dto.getMedia3() != null) {
			ws.getMedia().setMedia3(dto.getMedia3());
		}
		if(dto.getMedia4() != null) {
			ws.getMedia().setMedia4(dto.getMedia4());
		}
		if(dto.getMedia5() != null) {
			ws.getMedia().setMedia5(dto.getMedia5());
		}
		
		return ws;
	}
	
	public static void transformToMarketLanguage(Set<MarketLanguageDTO> dtoList, List<MarketLanguage> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(MarketLanguageDTO dto : dtoList) {
			MarketLanguage ws = transformToMarketLanguage(dto);
			wsList.add(ws);
		}
	}
	
	public static MarketLanguage transformToMarketLanguage(MarketLanguageDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		MarketLanguage ws = new MarketLanguage();
		ws.setDateOfConsent(dto.getDateOfConsent());
		ws.setLanguage(LanguageCodesEnum.valueOf(dto.getLanguage()));
		ws.setMarket(dto.getMarket());		
		ws.setOptIn(dto.getOptIn());
		
		transformToSignature(dto, ws.getSignature());
		
		Media media = new Media();
		ws.setMedia(media);
		
		if(dto.getMedia1() != null) {
			ws.getMedia().setMedia1(dto.getMedia1());
		}
		if(dto.getMedia2() != null) {
			ws.getMedia().setMedia2(dto.getMedia2());
		}
		if(dto.getMedia3() != null) {
			ws.getMedia().setMedia3(dto.getMedia3());
		}
		if(dto.getMedia4() != null) {
			ws.getMedia().setMedia4(dto.getMedia4());
		}
		if(dto.getMedia5() != null) {
			ws.getMedia().setMedia5(dto.getMedia5());
		}
		
		return ws;
	}
	
	public static void transformToSignature(CommunicationPreferencesDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static void transformToSignature(MarketLanguageDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}
	
	public static void transformToWarningResponse(Set<WarningDTO> dtoList, List<WarningResponse> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		
		for(WarningDTO dto : dtoList) {
			WarningResponse ws = transformToWarningResponse(dto);
			wsList.add(ws);
		}
		
	}
	
	public static WarningResponse transformToWarningResponse(WarningDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		WarningResponse ws = new WarningResponse();
		ws.setWarning(transformToWarning(dto));
		
		return ws;
	}
	
	public static Warning transformToWarning(WarningDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		Warning ws = new Warning();
		ws.setWarningCode(dto.getWarningCode());
		ws.setWarningDetails(dto.getWarningDetails());
		
		return ws;
	}

	// REPIND-555 : Prospect migration
//	public static IndividuDTO transformToIndividuDTO(ProspectDTO prospectDTO) {
//		IndividuDTO individuDTO = null;
//		
//		if(prospectDTO != null) {
//			individuDTO = new IndividuDTO();
//			individuDTO.setCivilite(prospectDTO.getCivilityCode());
//			individuDTO.setDateCreation(prospectDTO.getCreationDate());
//			individuDTO.setDateModification(prospectDTO.getModificationDate());
//			individuDTO.setAutreNationalite(prospectDTO.getOtherNationality());
//			individuDTO.setNationalite(prospectDTO.getNationality());
//			individuDTO.setNom(prospectDTO.getLastName());
//			individuDTO.setNomSC(prospectDTO.getLastNameSC());
//			individuDTO.setPrenom(prospectDTO.getFirstName());
//			individuDTO.setPrenomSC(prospectDTO.getFirstNameSC());
//			individuDTO.setCodeLangue(prospectDTO.getLanguageCode());
//			individuDTO.setStatutIndividu(prospectDTO.getStatus());
//			individuDTO.setSignatureCreation(prospectDTO.getCreationSignature());
//			individuDTO.setSignatureModification(prospectDTO.getModificationSignature());
//			individuDTO.setSiteCreation(prospectDTO.getCreationSite());
//			individuDTO.setDateNaissance(prospectDTO.getDateOfBirth());
//			individuDTO.setSiteModification(prospectDTO.getModificationSite());
//			EmailDTO emailDTO = new EmailDTO();
//			emailDTO.setEmail(prospectDTO.getEmail());
//			Set<EmailDTO> emailDTOList = new HashSet<EmailDTO>();
//			emailDTOList.add(emailDTO);
//			individuDTO.setEmaildto(emailDTOList);
//			
//			if(prospectDTO.getGin() != null) {
//				individuDTO.setSgin(prospectDTO.getGin().toString());
//			}
//
////			ProspectLocalizationDTO prospectLocalizationDTO = prospectDTO.getProspectLocalizationDTO();
////			if(prospectLocalizationDTO != null) {
////			prospectLocalizationDTO.getCity();
////			prospectLocalizationDTO.getCreationDate();
////			prospectLocalizationDTO.
////			}
//			
//			ProspectTelecomsDTO prospectTelecomsDTO = prospectDTO.getProspectTelecomsDTO();
//			if(prospectTelecomsDTO != null) {
//				Set<TelecomsDTO> pTelecoms = new HashSet<TelecomsDTO>();
//				TelecomsDTO telecomDTO = new TelecomsDTO();
//				
//				telecomDTO.setSnorm_inter_country_code(prospectTelecomsDTO.getNormInterCountryCode());
//				telecomDTO.setSindicatif(prospectTelecomsDTO.getRegionCode());
//				telecomDTO.setSstatut_medium(prospectTelecomsDTO.getMediumStatus());
//				telecomDTO.setScode_medium(prospectTelecomsDTO.getMediumCode());
//				telecomDTO.setSterminal(prospectTelecomsDTO.getTerminal());
//				telecomDTO.setSnorm_nat_phone_number_clean(prospectTelecomsDTO.getNormNatPhoneNumberClean());
//				telecomDTO.setSnumero(prospectTelecomsDTO.getPhoneNumber());
//				telecomDTO.setSnorm_inter_phone_number(prospectTelecomsDTO.getNormInterPhoneNumber());
//				telecomDTO.setIsoCountryCode(prospectTelecomsDTO.getCountryCode());
//				telecomDTO.setDdate_creation(prospectTelecomsDTO.getCreationDate());
//				telecomDTO.setSsignature_creation(prospectTelecomsDTO.getCreationSignature());
//				telecomDTO.setSsite_creation(prospectTelecomsDTO.getCreationSite());
//				telecomDTO.setDdate_modification(prospectTelecomsDTO.getModificationDate());
//				telecomDTO.setSsignature_modification(prospectTelecomsDTO.getModificationSignature());
//				telecomDTO.setSsite_modification(prospectTelecomsDTO.getModificationSite());
//				
//				pTelecoms.add(telecomDTO);
//				individuDTO.setTelecoms(pTelecoms);
//			}
//			
//			Set<ProspectCommunicationPreferencesDTO> prospectCommunicationPreferencesDTOSet = prospectDTO.getCommunicationPreferencesDTO();
//			if(prospectCommunicationPreferencesDTOSet != null) {
//				List<CommunicationPreferencesDTO> pCommunicationpreferencesdto = new ArrayList<CommunicationPreferencesDTO>();
//				for(ProspectCommunicationPreferencesDTO prospectCommunicationPreferencesDTO : prospectCommunicationPreferencesDTOSet) {
//					CommunicationPreferencesDTO communicationPreferencesDTO = new CommunicationPreferencesDTO();
//					if(prospectCommunicationPreferencesDTO.getMarketLanguageDTO() != null &&
//							!prospectCommunicationPreferencesDTO.getMarketLanguageDTO().isEmpty()) {
//						Set<MarketLanguageDTO> pMarketLanguageDTO = new HashSet<MarketLanguageDTO>();
//						for(ProspectMarketLanguageDTO prospectMarketLanguageDTO : prospectCommunicationPreferencesDTO.getMarketLanguageDTO()) {
//							MarketLanguageDTO marketLanguageDTO = new MarketLanguageDTO();
//							marketLanguageDTO.setMarket(prospectMarketLanguageDTO.getMarket());
//							marketLanguageDTO.setLanguage(prospectMarketLanguageDTO.getLanguage());
//							marketLanguageDTO.setOptIn(prospectMarketLanguageDTO.getOptIn());
//							marketLanguageDTO.setDateOfConsent(prospectMarketLanguageDTO.getDateOfConsent());
//							marketLanguageDTO.setCreationDate(prospectMarketLanguageDTO.getCreationDate());
//							marketLanguageDTO.setCreationSignature(prospectMarketLanguageDTO.getCreationSignature());
//							marketLanguageDTO.setCreationSite(prospectMarketLanguageDTO.getCreationSite());
//							marketLanguageDTO.setModificationDate(prospectMarketLanguageDTO.getModificationDate());
//							marketLanguageDTO.setModificationSignature(prospectMarketLanguageDTO.getModificationSignature());
//							marketLanguageDTO.setModificationSite(prospectMarketLanguageDTO.getModificationSite());
//							pMarketLanguageDTO.add(marketLanguageDTO);
//						}
//						communicationPreferencesDTO.setMarketLanguageDTO(pMarketLanguageDTO);
//					}
//					communicationPreferencesDTO.setDomain(prospectCommunicationPreferencesDTO.getDomain());
//					communicationPreferencesDTO.setComGroupType(prospectCommunicationPreferencesDTO.getCommunicationGroupType());
//					communicationPreferencesDTO.setComType(prospectCommunicationPreferencesDTO.getCommunicationType());
//					communicationPreferencesDTO.setOptinPartners(prospectCommunicationPreferencesDTO.getOptIn());
//					communicationPreferencesDTO.setDateOfEntry(prospectCommunicationPreferencesDTO.getDateOfConsent());
//					communicationPreferencesDTO.setChannel(prospectCommunicationPreferencesDTO.getSubscriptionChannel());
//					communicationPreferencesDTO.setOptinPartners(prospectCommunicationPreferencesDTO.getOptinPartners());
//					communicationPreferencesDTO.setDateOptinPartners(prospectCommunicationPreferencesDTO.getDateOfConsentPartners());
//					communicationPreferencesDTO.setDateOfEntry(prospectCommunicationPreferencesDTO.getDateOfEntry());
//
//					communicationPreferencesDTO.setCreationDate(prospectCommunicationPreferencesDTO.getCreationDate());
//					communicationPreferencesDTO.setCreationSignature(prospectCommunicationPreferencesDTO.getCreationSignature());
//					communicationPreferencesDTO.setCreationSite(prospectCommunicationPreferencesDTO.getCreationSite());
//					communicationPreferencesDTO.setModificationDate(prospectCommunicationPreferencesDTO.getModificationDate());
//					communicationPreferencesDTO.setModificationSignature(prospectCommunicationPreferencesDTO.getModificationSignature());
//					communicationPreferencesDTO.setModificationSite(prospectCommunicationPreferencesDTO.getModificationSite());
//				}
//				individuDTO.setCommunicationpreferencesdto(pCommunicationpreferencesdto);
//			}
//		}
//		
//		return individuDTO;
//	}

	public static void transformRoleUCCRToContractResponse(List<RoleUCCRDTO> roleUCCRDTOList, List<ContractResponse> wsList) {
		if(roleUCCRDTOList==null || roleUCCRDTOList.isEmpty()) {
			return;
		}
		
		for(RoleUCCRDTO dto : roleUCCRDTOList) {
			
			if(wsList.size()==CONTRACT_LIMIT) {
				break;
			}
			
			wsList.add(transformToContractResponse(dto));
		}
	}
	
	public static ContractResponse transformToContractResponse(RoleUCCRDTO dto) {
		
		if (dto == null) {
			return null;
		}

		ContractResponse ws = new ContractResponse();
		ws.setContract(transformToContract(dto));
		ws.getContractData().add(transformRoleUCCRToContractData(dto));
		transformToSignature(dto, ws.getSignature());
	
		return ws;
	}
	
	private static ContractData transformRoleUCCRToContractData(RoleUCCRDTO dto){
		ContractData cd = new ContractData();
		cd.setKey(ContractDataKeyEnum.CEID.getKey());
		cd.setValue(dto.getCeID());
		return cd;
	}

	public static ContractV2 transformToContract(RoleUCCRDTO dto) {
		
		if (dto == null) {
			return null;
		}
	
		ContractV2 ws = new ContractV2();
		ws.setContractNumber(dto.getUccrID());
		ws.setContractStatus(dto.getEtat());
		ws.setContractType(transformToContractType(dto.getBusinessRole()));
		ws.setProductSubType("UCCR");
		ws.setProductType(dto.getType());
		ws.setValidityEndDate(dto.getFinValidite());
		ws.setValidityStartDate(dto.getDebutValidite());
	
		return ws;
	}
	
	public static void transformToSignature(RoleUCCRDTO dto, List<Signature> wsList) {
		
		Signature creation = new Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());
		
		Signature modification = new Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());
		
		wsList.add(creation);
		wsList.add(modification);
	}

	// REPIND-555 : Prospect migration
//	public static void transformProspectPrefToPreferencesResponse(ProspectLocalizationDTO prospectLocalizationDTO, ProvideIndividualInformationResponse response) {
//		String creation = "C";
//		String modif = "M";
//		
//		if(prospectLocalizationDTO != null && prospectLocalizationDTO.getPreferredAirport() != null) {
//			PreferenceResponse	prefResponse = new PreferenceResponse();
//			
//			// Preference Bloc
//			PreferenceV2 preference = new PreferenceV2();
//			if (prospectLocalizationDTO.getProspectLocalisationId() != null) {
//				preference.setId(Integer.toString(prospectLocalizationDTO.getProspectLocalisationId()));
//			} else {
//				preference.setId("0");
//			}
//			preference.setType("TPC");
//			prefResponse.getPreference().add(preference);
//			
//			// Bloc Signature Creation
//			Signature signatureCreation = new Signature();
//			if (prospectLocalizationDTO.getCreationSignature() != null) {
//				signatureCreation.setSignatureType(creation);
//				signatureCreation.setSignature(prospectLocalizationDTO.getCreationSignature());
//			}
//			if (prospectLocalizationDTO.getCreationSite() != null) {
//				signatureCreation.setSignatureType(creation);
//				signatureCreation.setSignatureSite(prospectLocalizationDTO.getCreationSite());
//			}
//			if (prospectLocalizationDTO.getCreationDate() != null) {
//				signatureCreation.setSignatureType(creation);
//				signatureCreation.setDate(prospectLocalizationDTO.getCreationDate());
//			}
//			preference.getSignature().add(signatureCreation);
//			
//			// Bloc Signature Modification
//			Signature signatureModif = new Signature();
//			if (prospectLocalizationDTO.getModificationSignature() != null) {
//				signatureModif.setSignatureType(modif);
//				signatureModif.setSignature(prospectLocalizationDTO.getModificationSignature());
//			}
//			if (prospectLocalizationDTO.getModificationSite() != null) {
//				signatureModif.setSignatureType(modif);
//				signatureModif.setSignatureSite(prospectLocalizationDTO.getModificationSite());
//			}
//			if (prospectLocalizationDTO.getModificationDate() != null) {
//				signatureModif.setSignatureType(modif);
//				signatureModif.setDate(prospectLocalizationDTO.getModificationDate());
//			}
//			preference.getSignature().add(signatureModif);
//			
//			// PreferenceData Bloc
//			PreferenceDatasV2 preferenceDatas = new PreferenceDatasV2();
//			preference.setPreferenceDatas(preferenceDatas);
//			
//			PreferenceDataV2 prefDataType = new PreferenceDataV2();
//			prefDataType.setKey("type");
//			prefDataType.setValue("WWP");
//			preferenceDatas.getPreferenceData().add(prefDataType);
//			
//			PreferenceDataV2 preferenceData = new PreferenceDataV2();
//			preferenceData.setKey(ContractDataKeyEnum.PREFERREDAIRPORT.getKey());
//			preferenceData.setValue(prospectLocalizationDTO.getPreferredAirport());
//			preferenceDatas.getPreferenceData().add(preferenceData);
//			
//			response.getPreferenceResponse().getPreference().add(preference);
//
//		}
//	}
	
	public static void transformPreferenceDTOListToPreferenceResponse(List<PreferenceDTO> preferenceDTOList, ProvideIndividualInformationResponse response) throws InvalidParameterException{
		
		if(response == null){
			throw new InvalidParameterException("response is null");
		}
		
		if (preferenceDTOList != null && !preferenceDTOList.isEmpty()) {
			if (response.getPreferenceResponse() == null) {
				response.setPreferenceResponse(new PreferenceResponse());
			}
			
			for (PreferenceDTO prefDTO : preferenceDTOList) {
				transformToPreferenceResponse(response.getPreferenceResponse(), prefDTO);
			}
		}
	}
	
	private static void transformToPreferenceResponse(PreferenceResponse preferenceResponse, PreferenceDTO prefDTO) {
		
		String creation = "C";
		String modif = "M";

		if (prefDTO == null) {
			return;
		}
		
		if (preferenceResponse == null) {
			preferenceResponse = new PreferenceResponse();
		}
		
		// Bloc Preference
		PreferenceV2 preference = new PreferenceV2();
		if (prefDTO.getPreferenceId() != null) {
			preference.setId(Long.toString(prefDTO.getPreferenceId()));
		} else {
			preference.setId("0");
		}
		if (prefDTO.getType() != null) {
			preference.setType(prefDTO.getType());
		}
		if (prefDTO.getLink() != null) {
			preference.setLink(Integer.toString(prefDTO.getLink()));
		}

		// Bloc PreferenceData		
		if (prefDTO.getPreferenceDataDTO() != null && !prefDTO.getPreferenceDataDTO().isEmpty()) {
			transformPreferenceData(prefDTO.getPreferenceDataDTO(), preference);
		}
		
		// Bloc Signature Creation
		Signature signatureCreation = new Signature();
		if (prefDTO.getSignatureCreation() != null) {
			signatureCreation.setSignatureType(creation);
			signatureCreation.setSignature(prefDTO.getSignatureCreation());
		}
		if (prefDTO.getSiteCreation() != null) {
			signatureCreation.setSignatureType(creation);
			signatureCreation.setSignatureSite(prefDTO.getSiteCreation());
		}
		if (prefDTO.getDateCreation() != null) {
			signatureCreation.setSignatureType(creation);
			signatureCreation.setDate(prefDTO.getDateCreation());
		}
		preference.getSignature().add(signatureCreation);
		
		// Bloc Signature Modification
		Signature signatureModif = new Signature();
		if (prefDTO.getSignatureModification() != null) {
			signatureModif.setSignatureType(modif);
			signatureModif.setSignature(prefDTO.getSignatureModification());
		}
		if (prefDTO.getSiteModification() != null) {
			signatureModif.setSignatureType(modif);
			signatureModif.setSignatureSite(prefDTO.getSiteModification());
		}
		if (prefDTO.getDateModification() != null) {
			signatureModif.setSignatureType(modif);
			signatureModif.setDate(prefDTO.getDateModification());
		}
		preference.getSignature().add(signatureModif);

		preferenceResponse.getPreference().add(preference);		
	}

	private static void transformPreferenceData(Set<PreferenceDataDTO> preferenceDataDTO, PreferenceV2 preference) {
		if (preferenceDataDTO != null && !preferenceDataDTO.isEmpty()) {
			if (preference.getPreferenceDatas() == null) {
				preference.setPreferenceDatas(new PreferenceDatasV2());
				
				for (PreferenceDataDTO prefDataDTO : preferenceDataDTO) {
					PreferenceDataV2 prefData = new PreferenceDataV2();
					
					if (prefDataDTO.getKey() != null) {
						
						prefData.setKey(prefDataDTO.getKey());
					}
					if (prefDataDTO.getValue() != null) {
						prefData.setValue(prefDataDTO.getValue());
					} else {
						prefData.setValue("");
					}
					preference.getPreferenceDatas().getPreferenceData().add(prefData);
				}
			}
		}
	}

	public static void transformToAlert(List<AlertDTO> alertDTOList, ProvideIndividualInformationResponse response) {
		AlertResponse alertResponse = new AlertResponse();
		if (alertDTOList != null && !alertDTOList.isEmpty()) {
			for (AlertDTO dto: alertDTOList) {
				alertResponse.getAlert().add(transformToAlert(dto));
			}
		}
		response.setAlertResponse(alertResponse);
	}

	private static Alert transformToAlert(AlertDTO dto) {
		Alert alert = null;
		
		if (dto != null) {
			alert = new Alert();
			alert.setAlertId(dto.getAlertId().toString());
			alert.setType(dto.getType());
			alert.setOptIn(dto.getOptIn());
			
			for (AlertDataDTO alertDataDTO: dto.getAlertDataDTO()) {
				alert.getAlertData().add(transformToAlertData(alertDataDTO));
			}
		}
		
		return alert;
	}

	private static AlertData transformToAlertData(AlertDataDTO dto) {
		AlertData alertData = null;
		
		if (dto != null) {
			alertData = new AlertData();
			
			alertData.setKey(dto.getKey());
			alertData.setValue(dto.getValue());
		}
		return alertData;
	}
	
	// REPIND-555 : Prospect migration
//	public static void transformToAlertProspect(List<AlertProspectDTO> alertDTOList, ProvideIndividualInformationResponse response) {
//		AlertResponse alertResponse = new AlertResponse();
//		if (alertDTOList != null && !alertDTOList.isEmpty()) {
//			for (AlertProspectDTO dto: alertDTOList) {
//				alertResponse.getAlert().add(transformToAlert(dto));
//			}
//		}
//		response.setAlertResponse(alertResponse);
//	}
//
//	private static Alert transformToAlert(AlertProspectDTO dto) {
//		Alert alert = null;
//		
//		if (dto != null) {
//			alert = new Alert();
//			alert.setType(dto.getType());
//			alert.setAlertId(dto.getAlertId().toString());
//			alert.setOptIn(dto.getOptIn());
//			
//			for (AlertDataProspectDTO alertDataDTO: dto.getAlertDataDTO()) {
//				alert.getAlertData().add(transformToAlertData(alertDataDTO));
//			}
//		}
//		
//		return alert;
//	}
//
//	private static AlertData transformToAlertData(AlertDataProspectDTO dto) {
//		AlertData alertData = null;
//		
//		if (dto != null) {
//			alertData = new AlertData();
//			
//			alertData.setKey(dto.getKey());
//			alertData.setValue(dto.getValue());
//		}
//		return alertData;
//	}

	public static void transformProspectPreferredAirportToPreferenceResponse(List<PreferenceDTO> preferenceDTOList, ProvideIndividualInformationResponse response) throws InvalidParameterException {
		
		if(response == null){
			throw new InvalidParameterException("response is null");
		}
		
		if (preferenceDTOList != null && !preferenceDTOList.isEmpty()) {
			
			if (response.getPreferenceResponse() == null) {
				response.setPreferenceResponse(new PreferenceResponse());
			}
			
			for (PreferenceDTO prefDTO : preferenceDTOList) {
				if (prefDTO.getType().equalsIgnoreCase("TPC") && prefDTO.getPreferenceDataDTO() != null && !prefDTO.getPreferenceDataDTO().isEmpty()) {
					
					// for (PreferenceDataDTO prefDataDTO : prefDTO.getPreferenceDataDTO()) {
						// REPIND-768 : We do not need to store type=WWP for PreferedAirPort
						// if (prefDataDTO.getKey().equalsIgnoreCase("type") && prefDataDTO.getValue().equalsIgnoreCase("WWP")) {
							transformToPreferenceResponse(response.getPreferenceResponse(), prefDTO);
							break;
						// }
					// }	
				}
			}
		}
	}

	public static IndividualResponse transformForgetInformationToIndividual(ForgottenIndividualDTO fIdentifierFound) {
		if (fIdentifierFound == null) {
			return null;
		}
		
		IndividualResponse individualResponse = new IndividualResponse();
		IndividualInformationsV2 individualInformation = new IndividualInformationsV2();
		Signature sign = new Signature();
		
		individualInformation.setIdentifier(fIdentifierFound.getIdentifier());
		individualResponse.setIndividualInformations(individualInformation);
		
		sign.setSignatureType("M");
		sign.setDate(fIdentifierFound.getModificationDate());
		sign.setSignature(fIdentifierFound.getSignature());
		sign.setSignatureSite(fIdentifierFound.getSite());
		individualResponse.getSignature().add(sign);
		
		return individualResponse;
	}

	public static WarningResponse transformForgetInformationToWarning(ForgottenIndividualDTO fIdentifierFound) {
		if (fIdentifierFound == null) {
			return null;
		}
		
		WarningResponse response = new WarningResponse();
		Warning warning = new Warning();
		warning.setWarningCode("UNKNOWN_STATUS");
		
		if (F_ASKED.equalsIgnoreCase(fIdentifierFound.getContext())) {
			warning.setWarningCode(ForgetEnum.ASKED.getLibelle());
		}
		else if (F_CONFIRMED.equalsIgnoreCase(fIdentifierFound.getContext())) {
			warning.setWarningCode(ForgetEnum.CONFIRMED.getLibelle());
		} else if(F_FORCED.equalsIgnoreCase(fIdentifierFound.getContext())){
			warning.setWarningCode(ForgetEnum.FORCED.getLibelle());
		} else if (F_PROCESSED.equalsIgnoreCase(fIdentifierFound.getContext())) {
			warning.setWarningCode(ForgetEnum.PROCESSED.getLibelle());
		}
		
		if (fIdentifierFound.getDeletionDate() != null) {
			warning.setWarningDetails(SicDateUtils.dateToString(fIdentifierFound.getDeletionDate()));
		}
		else {
			warning.setWarningDetails(SicDateUtils.dateToString(fIdentifierFound.getModificationDate()));
		}
		
		response.setWarning(warning);
		
		return response;
	}


	public static void transformToDoctorResponse(List<BusinessRoleDTO> dtoList, List<ContractResponse> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		for (BusinessRoleDTO dto: dtoList) {
			if(wsList.size()==CONTRACT_LIMIT) {
			break;
		}
			wsList.add(transformToDoctorResponse(dto));
		}

	}

	public static ContractResponse transformToDoctorResponse(BusinessRoleDTO dto) {

		if (dto == null) {
			return null;
		}

		ContractResponse ws = new ContractResponse();


		ContractV2 contract = new ContractV2();
		contract.setContractType(dto.getType());
		contract.setContractNumber(dto.getNumeroContrat());

		contract.setProductType(RoleDoctorEnum.DR.getCode());	// Dortor
		contract.setContractStatus(RoleDoctorEnum.C.getCode()); // default status of doctor

		ws.setContract(contract);
		//////////////////////////////////////////////////////////////////////////

		transformDoctorToSignature(dto, ws.getSignature());

		return ws;
	}


	public static void transformDoctorToSignature(BusinessRoleDTO dto, List<Signature> wsList) {

		Signature creation = new Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		wsList.add(creation);
	}
}
