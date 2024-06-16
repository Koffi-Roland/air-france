package com.afklm.repind.v6.provideindividualdata.transformers;

import com.afklm.repind.v6.provideindividualdata.type.PopulationTypeEnum;
import com.afklm.repind.v6.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.w000418.v6.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v6.response.*;
import com.afklm.soa.stubs.w000418.v6.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w000418.v6.siccommontype.Signature;
import com.afklm.soa.stubs.w000418.v6.sicindividutype.*;
import com.afklm.soa.stubs.w000418.v6.sicmarketingtype.*;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
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
import com.airfrance.repind.util.PreferenceUtils;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
//import com.airfrance.sicutf8.dto.individu.AlertDataProspectDTO;
//import com.airfrance.sicutf8.dto.individu.AlertProspectDTO;
//import com.airfrance.sicutf8.dto.prospect.ProspectCommunicationPreferencesDTO;
//import com.airfrance.sicutf8.dto.prospect.ProspectDTO;
//import com.airfrance.sicutf8.dto.prospect.ProspectLocalizationDTO;
//import com.airfrance.sicutf8.dto.prospect.ProspectMarketLanguageDTO;
//import com.airfrance.sicutf8.dto.prospect.ProspectTelecomsDTO;

public class ProvideIndividualDataTransformV6 {
	
	public final static int CONTRACT_LIMIT = 25;
	public final static int EMAIL_LIMIT = 2;
	public final static int POSTAL_ADDRESS_LIMIT = 5;
	public final static int TELECOM_LIMIT = 2;
	
	public final static int PREFERENCE_TDC_LIMIT = 5;	// <xsd:element maxOccurs="5" minOccurs="0" name="travelDocument" type="tns:MaccTravelDocument"/>
	public final static int PREFERENCE_ECC_LIMIT = 5;	// <xsd:element maxOccurs="5" minOccurs="0" name="emergencyContact" type="tns:MaccEmergencyContacts"/>
	// public final static int PREFERENCE_TUM_LIMIT = 5;	// <xsd:element maxOccurs="2" minOccurs="0" name="tutorUM" type="tns:MaccTutorUM"/>
	public final static int PREFERENCE_TCC_LIMIT = 8;	// <xsd:element maxOccurs="8" minOccurs="0" name="travelCompanion" type="tns:MaccTravelCompanion"/>
	
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
			
			wsList.add(transformToContractResponse(dto, isFBRecognitionActivate));
		}
		
	}
	
	public static ContractResponse transformToContractResponse(RoleContratsDTO dto) {
		return transformToContractResponse(dto, true);
	}
	
	public static ContractResponse transformToContractResponse(RoleContratsDTO dto, boolean isFBRecognitionActivate) {
		
		if (dto == null) {
			return null;
		}

		ContractResponse ws = new ContractResponse();
		if (isFBRecognitionActivate) {
			ws.getContractData().addAll(transformToContractDataList(dto));
		}
		ws.setContract(transformToContract(dto, isFBRecognitionActivate));
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

	public static void transformToSignature(RoleContratsDTO dto, List<Signature> wsList) {
		
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
	
	public static ContractV2 transformToContract(RoleContratsDTO dto) {
		return transformToContract(dto, true);
	}
	
	public static ContractV2 transformToContract(RoleContratsDTO dto, boolean isFBRecognitionActivate) {
		
		if (dto == null) {
			return null;
		}

		ContractV2 ws = new ContractV2();

		ws.setContractNumber(dto.getNumeroContrat());
		ws.setContractStatus(dto.getEtat());
		ws.setContractType(transformToContractType(dto.getBusinessroledto()));
		ws.setValidityEndDate(dto.getDateFinValidite());
		ws.setValidityStartDate(dto.getDateDebutValidite());
		
		// Flag in database should be linked to FP process for RoleContract
    	if (!"FP".equals(ws.getContractType()))  {    		
    		isFBRecognitionActivate = true;
    	}
		
		if (isFBRecognitionActivate) {
		
			ws.setCompanyCode(dto.getCodeCompagnie());
			ws.setIataCode(dto.getAgenceIATA());
			ws.setProductSubType(dto.getSousType());
			ws.setProductType(dto.getTypeContrat());
		}
		
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
//		ws.setNationality(dto.getNationalite());				TODO : Deleted by Herve Valadon 07/2016
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

	// REPIND-555: prospect migration
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

	// REPIND-555: prospect migration
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

	// REPIND-555: prospect migration
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
		ws.setCountryCode(SicStringUtils.replaceNonPrintableChars(dto.getScode_pays()));
		ws.setDistrict(SicStringUtils.replaceNonPrintableChars(dto.getSlocalite()));
		ws.setStateCode(SicStringUtils.replaceNonPrintableChars(dto.getScode_province()));
		ws.setZipCode(SicStringUtils.replaceNonPrintableChars(dto.getScode_postal()));
		ws.setCorporateName(SicStringUtils.replaceNonPrintableChars(dto.getSraison_sociale()));
		ws.setAdditionalInformation(SicStringUtils.replaceNonPrintableChars(dto.getScomplement_adresse()));
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
		
		if(delegatorDataListDTO==null || delegatorDataListDTO.isEmpty()) {
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

	// REPIND-555 : Prospect migration
	public static PreferencesResponse transformToPreferencesResponse(List <PreferenceDTO> prospectLocalizationDTO, ProvideIndividualInformationResponse response) {
		PreferencesResponse preferenceResponse = response.getPreferencesResponse();
		if(preferenceResponse == null) {   
			preferenceResponse = new PreferencesResponse();
			response.setPreferencesResponse(preferenceResponse);
		}
		if(prospectLocalizationDTO != null) {
			Preference pref = new Preference();
			List<PreferenceData> preferenceDataList = pref.getPreferenceData();
			preferenceDataList.add(transformToPreferenceData(ContractDataKeyEnum.PREFERREDAIRPORT.getKey(), getPreferredAirport(choseOnePreferredAirport(prospectLocalizationDTO))));
			preferenceResponse.getPreference().add(pref);
		}
		return preferenceResponse;
	}
	
	private static PreferenceData transformToPreferenceData(String key, String value){
		PreferenceData preferenceData = new PreferenceData();
		preferenceData.setKey(key);
		preferenceData.setValue(value);
		return preferenceData;
	}

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

	public static void transformPreferenceDTOListToResponse(List<PreferenceDTO> preferenceDTOList, ProvideIndividualInformationResponse response) throws JrafDomainException {
		
		if(response == null){
			throw new InvalidParameterException("response is null");
		}
		
		if (preferenceDTOList != null && !preferenceDTOList.isEmpty()) {
			/*if (response.getPreferencesResponse() == null) {
				PreferencesResponse preferencesResponse = new PreferencesResponse();
				response.setPreferencesResponse(preferencesResponse);
			}*/

			if (response.getMarketingDataResponse() == null) {
				MarketingDataResponse marketingDataResponse = new MarketingDataResponse();
				response.setMarketingDataResponse(marketingDataResponse);
				if (response.getMarketingDataResponse().getMarketingInformation() == null) {
					MarketingInformation marketingInformation = new MarketingInformation();
					response.getMarketingDataResponse().setMarketingInformation(marketingInformation);
				}
			}
		}
		
		// Keep the 2 most recents Documents by type 
		filterFoundDocument(preferenceDTOList, 2);
		
		if (preferenceDTOList != null && !preferenceDTOList.isEmpty()) {
			for (PreferenceDTO preferenceDTOLoop : preferenceDTOList) {
				// REPIND-768 : We do not need to store type=WWP with PreferedAirPort
				// String subType = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "type");
				//TPC (WW - Prospect) mapped to PreferenceResponse
				if (preferenceDTOLoop.getType().equalsIgnoreCase("TPC")) {
					// if (subType != null && subType.equalsIgnoreCase("WWP")) {
						Preference preference = new Preference();
						
						String preferredAirport = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "preferredAirport");
						if(preferredAirport != null) {
							preference.getPreferenceData().add(transformToPreferenceData("preferredAirport", preferredAirport));
						}
						
//						if (preferenceDTOLoop.getPreferenceDataDTO() != null) {
//							for (PreferenceDataDTO prefDataDTOLoop : preferenceDTOLoop.getPreferenceDataDTO()) {
//								preference.getPreferenceData().add(transformToPreferenceData(prefDataDTOLoop.getKey(), prefDataDTOLoop.getValue()));
//							}
//						}
//						
						if (preferenceDTOLoop.getLink() != null) preference.getPreferenceData().add(transformToPreferenceData("link", preferenceDTOLoop.getLink().toString()));
						/*if (preferenceDTOLoop.getDateCreation() != null) preference.getPreferenceData().add(transformToPreferenceData("creationDate", preferenceDTOLoop.getDateCreation().toString()));
						if (preferenceDTOLoop.getSignatureCreation() != null) preference.getPreferenceData().add(transformToPreferenceData("creationSignature", preferenceDTOLoop.getSignatureCreation()));
						if (preferenceDTOLoop.getSiteCreation() != null) preference.getPreferenceData().add(transformToPreferenceData("creationSite", preferenceDTOLoop.getSiteCreation()));
						if (preferenceDTOLoop.getDateModification() != null) preference.getPreferenceData().add(transformToPreferenceData("modificationDate", preferenceDTOLoop.getDateModification().toString()));
						if (preferenceDTOLoop.getSignatureModification() != null) preference.getPreferenceData().add(transformToPreferenceData("modificationSignature", preferenceDTOLoop.getSignatureModification()));
						if (preferenceDTOLoop.getSiteModification() != null) preference.getPreferenceData().add(transformToPreferenceData("modificationSite", preferenceDTOLoop.getSiteModification()));*/
						
						//response.getPreferencesResponse().getPreference().add(preference);
						
						MaccTravelPreferences maccTravelPreferences = new MaccTravelPreferences();
						boolean validMaccTravelPreferences = false;
						
						String departureCity = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "departureCity");
						if (departureCity != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setDepartureCity(departureCity);
						}
						
						String departureAirport = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "departureAirport");
						if (departureAirport != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setDepartureAirport(departureAirport);
						}
						
						String arrivalCity = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "arrivalCity");
						if (arrivalCity != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setArrivalCity(arrivalCity);
						}
						
						String arrivalAirport = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "arrivalAirport");
						if (arrivalAirport != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setArrivalAirport(arrivalAirport);
						}
						
						String meal = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "meal");
						if (meal != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setMeal(meal);
						}
						
						String seat = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "seat");
						if (seat != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setSeat(seat);
						}
						
						String travelClass = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "travelClass");
						if (travelClass != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setTravelClass(travelClass);
						}
						
						String boardingPass = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "boardingPass");
						if (boardingPass != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setBoardingPass(boardingPass);
						}
						
						String customerLeisure = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "customerLeisure");
						if (customerLeisure != null) {
							validMaccTravelPreferences = true;
							maccTravelPreferences.setCustomerLeisure(customerLeisure);
						}
						
						if (validMaccTravelPreferences) response.getMarketingDataResponse().getMarketingInformation().setTravelPreferences(maccTravelPreferences);
					// }
				}
				
				// APC mapped to MarketingResponse
				if ("APC".equalsIgnoreCase(preferenceDTOLoop.getType())) {
					MaccApisData maccApisData = new MaccApisData();
					boolean validMaccApisData = false;
					
					String familyName = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "familyName");
					if (familyName != null) {
						validMaccApisData = true;
						maccApisData.setLastName(familyName);
					}
					
					String givenNames = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "givenNames");
					if (givenNames != null) {
						validMaccApisData = true;
						maccApisData.setFirstName(givenNames);
					}
					
					String dateOfBirth = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "dateOfBirth");
					if (dateOfBirth != null) {
						validMaccApisData = true;
						maccApisData.setDateOfBirth(SicDateUtils.convertStringToDateDDMMYYYY(dateOfBirth));
					}
					
					String gender = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "gender");
					if (gender != null) {
						validMaccApisData = true;
						maccApisData.setGender(gender);
					}
					
					String greenCardNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "greenCardNumber");
					if (greenCardNumber != null) {
						validMaccApisData = true;
						maccApisData.setGreenCardNumber(greenCardNumber);
					}
					
					String greenCardExpiryDate = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "greenCardExpiryDate");
					if (greenCardExpiryDate != null) {
						validMaccApisData = true;
						maccApisData.setGreenCardExpiryDate(SicDateUtils.convertStringToDateDDMMYYYY(greenCardExpiryDate));
					}
					
					String countryOfResidence = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "countryOfResidence");
					if (countryOfResidence != null) {
						validMaccApisData = true;
						maccApisData.setCountryOfResidence(countryOfResidence);
					}
					
					String address = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "address");
					if (address != null) {
						validMaccApisData = true;
						maccApisData.setAddressUS(address);
					}
					
					String zipCode = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "zipCode");
					if (zipCode != null) {
						validMaccApisData = true;
						maccApisData.setPostCodeUS(zipCode);
					}
					
					String city = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "city");
					if (city != null) {
						validMaccApisData = true;
						maccApisData.setCityUS(city);
					}
					
					String state = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "state");
					if (state != null) {
						validMaccApisData = true;
						maccApisData.setStateUS(state);
					}
					
					String redressControlNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "redressControlNumber");
					if (redressControlNumber != null) {
						validMaccApisData = true;
						maccApisData.setRedressControlNumber(redressControlNumber);
					}
					
					if (validMaccApisData) response.getMarketingDataResponse().getMarketingInformation().setApisData(maccApisData);
				}
				
				//TDC mapped to MarketingResponse
				if ("TDC".equalsIgnoreCase(preferenceDTOLoop.getType())) {
/*
					//Type AP goes to ApisData
					if (subType != null && subType.equalsIgnoreCase("AP")) {
						if (maccApisData == null) {
							maccApisData = new MaccApisData();
						}
						
						String familyName = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "familyName");
						if (familyName != null) maccApisData.setLastName(familyName);
						
						String givenNames = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "givenNames");
						if (givenNames != null) maccApisData.setLastName(givenNames);
						
						String dateOfBirth = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "dateOfBirth");
						if (dateOfBirth != null) maccApisData.setLastName(dateOfBirth);
						
						String gender = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "gender");
						if (gender != null) maccApisData.setLastName(gender);
						
					//Type GC goes to ApisData
					} else if (subType != null && subType.equalsIgnoreCase("GC")) {
						if (maccApisData == null) {
							maccApisData = new MaccApisData();
						}
						
						String number = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "number");
						if (number != null) maccApisData.setGreenCardNumber(number);
						
						Date expiryDate = getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "expiryDate"));
						if (expiryDate != null) maccApisData.setGreenCardExpiryDate(expiryDate);
						
						String redressControlNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "redressControlNumber");
						if (redressControlNumber != null) maccApisData.setRedressControlNumber(redressControlNumber);
					//Other types go to TravelDoc
					} else {
 */		
						MaccTravelDocument maccTravelDocument = new MaccTravelDocument();
						boolean validMaccTravelDocument = false;
					// REPIND-2468 : Do not return TravelDocuement when number is greater than maxLength defined on WSDL for V6
					boolean shouldReturnTravelDocument = true;
						
						String countryOfIssue = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "countryOfIssue");
						if (countryOfIssue != null) {
							maccTravelDocument.setCountryIssued(countryOfIssue);
							validMaccTravelDocument = true;
						}
						
						Date expiryDate = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "expiryDate"));
						if (expiryDate != null) {
							maccTravelDocument.setExpirationDate(expiryDate);
							validMaccTravelDocument = true;
						}
						
						Date issueDate = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "issueDate"));
						if (issueDate != null) {
							maccTravelDocument.setIssueDate(issueDate);
							validMaccTravelDocument = true;
						}
						
						String nationality = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "nationality");
						if (nationality != null) {
							maccTravelDocument.setNationality(nationality);
							validMaccTravelDocument = true;
						}
						
						String number = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "number");
						if (number != null) {
							maccTravelDocument.setNumber(number);
							validMaccTravelDocument = true;
							shouldReturnTravelDocument = number.length() <= 20;
						}
						
						String type = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "type");
						if (type != null) {
							maccTravelDocument.setType(type);
							validMaccTravelDocument = true;
						}
						
						//In order to not return an empty travelDocument tag
					if (validMaccTravelDocument && shouldReturnTravelDocument) {
						// REPIND-1563 : Do not return TravelDocuement over 5 that is the maximum size defined on WSDL for V6
						if (response != null && response.getMarketingDataResponse() != null && response.getMarketingDataResponse().getMarketingInformation() != null &&
								response.getMarketingDataResponse().getMarketingInformation().getTravelDocument() != null && response.getMarketingDataResponse().getMarketingInformation().getTravelDocument().size() < PREFERENCE_TDC_LIMIT) {
							response.getMarketingDataResponse().getMarketingInformation().getTravelDocument().add(maccTravelDocument);	
						}
					}
				}
				
				//PIC mapped to MarketingResponse
				if (preferenceDTOLoop.getType().equalsIgnoreCase("PIC")) {
					MaccPersonalInformation maccPersonalInformation = new MaccPersonalInformation();
					boolean validMaccPersonalInformation = false;
					
					String blueBizNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "blueBizNumber");
					if (blueBizNumber != null) {
						maccPersonalInformation.setBlueBizNumber(blueBizNumber);
						validMaccPersonalInformation = true;
					}
					
					String FFPNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "FFPNumber");
					if (FFPNumber != null) {
						maccPersonalInformation.setFFPNumber(FFPNumber);
						validMaccPersonalInformation = true;
					}
					
					String FFPProgram = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "FFPProgram");
					if (FFPProgram != null) {
						maccPersonalInformation.setFFPProgram(FFPProgram);
						validMaccPersonalInformation = true;
					}
					
					if (validMaccPersonalInformation) response.getMarketingDataResponse().getMarketingInformation().setPersonalInformation(maccPersonalInformation);
				}
			
				//PAC Cannot be mapped to output
				
				//ADC mapped to MarketingResponse
				/*if (preferenceDTOLoop.getType().equalsIgnoreCase("PAC")) {
					MaccApisData maccApisData = new MaccApisData();
					boolean validMaccApisData = false;
					
					Date greenCardExpiryDate = getDateFromString(getDataValueFromPreference(preferenceDTOLoop, "greenCardExpiryDate"));
					if (greenCardExpiryDate != null) {
						maccApisData.setGreenCardExpiryDate(greenCardExpiryDate);
						validMaccApisData = true;
					}
					
					String greenCardNumber = getDataValueFromPreference(preferenceDTOLoop, "greenCardNumber");
					if (greenCardNumber != null) {
						maccApisData.setGreenCardNumber(greenCardNumber);
						validMaccApisData = true;
					}
					
					String redressControlNumber = getDataValueFromPreference(preferenceDTOLoop, "redressControlNumber");
					if (redressControlNumber != null) {
						maccApisData.setRedressControlNumber(redressControlNumber);
						validMaccApisData = true;
					}
					
					if (validMaccApisData) response.getMarketingDataResponse().getMarketingInformation().setApisData(maccApisData);
				}*/
				
				//ECC mapped to MarketingResponse
				if (preferenceDTOLoop.getType().equalsIgnoreCase("ECC")) {
					MaccEmergencyContacts maccEmergencyContacts = new MaccEmergencyContacts();
					boolean validMaccEmergencyContacts = false;
					
					String email = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "email");
					if (email != null) {
						maccEmergencyContacts.setEmail(email);
						validMaccEmergencyContacts = true;
					}
					
					String phoneNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "phoneNumber");
					if (phoneNumber != null) {
						maccEmergencyContacts.setPhoneNumber(phoneNumber);
						validMaccEmergencyContacts = true;
					}
					
					String firstNameECC = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "firstName");
					if (firstNameECC != null) {
						maccEmergencyContacts.setFirstName(firstNameECC);
						validMaccEmergencyContacts = true;
					}
					
					String lastNameECC = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "lastName");
					if (lastNameECC != null) {
						maccEmergencyContacts.setLastName(lastNameECC);
						validMaccEmergencyContacts = true;
					}
					
					if (validMaccEmergencyContacts) { 
						// REPIND-1563 : Do not return EmergencyContact over 5 that is the maximum size defined on WSDL for V6
						if (response != null && response.getMarketingDataResponse() != null && response.getMarketingDataResponse().getMarketingInformation() != null &&
								response.getMarketingDataResponse().getMarketingInformation().getEmergencyContact() != null && response.getMarketingDataResponse().getMarketingInformation().getEmergencyContact().size() < PREFERENCE_ECC_LIMIT) {
							response.getMarketingDataResponse().getMarketingInformation().getEmergencyContact().add(maccEmergencyContacts);	
						}
					}
				}
				
				//TUM mapped to MarketingResponse
				/*if (preferenceDTOLoop.getType().equalsIgnoreCase("TUM")) {
					MaccTutorUM maccTutorUM = new MaccTutorUM();
					boolean validMaccTutorUM = false;
					
					String civilityTutor = getDataValueFromPreference(preferenceDTOLoop, "civilityTutor");
					if (civilityTutor != null) {
						maccTutorUM.setCivilityTutor(civilityTutor);
						validMaccTutorUM = true;
					}
					
					String lastnameTutor = getDataValueFromPreference(preferenceDTOLoop, "lastnameTutor");
					if (lastnameTutor != null) {
						maccTutorUM.setLastnameTutor(lastnameTutor);
						validMaccTutorUM = true;
					}
					
					String firstnameTutor = getDataValueFromPreference(preferenceDTOLoop, "firstnameTutor");
					if (firstnameTutor != null) {
						maccTutorUM.setFirstnameTutor(firstnameTutor);
						validMaccTutorUM = true;
					}
					
					String adressTutor = getDataValueFromPreference(preferenceDTOLoop, "adressTutor");
					if (adressTutor != null) {
						maccTutorUM.setAddressTutor(adressTutor);
						validMaccTutorUM = true;
					}
					
					String postCodeTutor = getDataValueFromPreference(preferenceDTOLoop, "postCodeTutor");
					if (postCodeTutor != null) {
						maccTutorUM.setPostCodeTutor(postCodeTutor);
						validMaccTutorUM = true;
					}
					
					String cityTutor = getDataValueFromPreference(preferenceDTOLoop, "cityTutor");
					if (cityTutor != null) {
						maccTutorUM.setCityTutor(cityTutor);
						validMaccTutorUM = true;
					}
					
					String codeProvinceTutor = getDataValueFromPreference(preferenceDTOLoop, "codeProvinceTutor");
					if (codeProvinceTutor != null) {
						maccTutorUM.setCodeProvinceTutor(codeProvinceTutor);
						validMaccTutorUM = true;
					}
					
					String countryCodeTutor = getDataValueFromPreference(preferenceDTOLoop, "countryCodeTutor");
					if (countryCodeTutor != null) {
						maccTutorUM.setCountryCodeTutor(countryCodeTutor);
						validMaccTutorUM = true;
					}
					
					String dialingCodePhoneTutor = getDataValueFromPreference(preferenceDTOLoop, "dialingCodePhoneTutor");
					if (dialingCodePhoneTutor != null) {
						maccTutorUM.setDialingCodePhoneTutor(dialingCodePhoneTutor);
						validMaccTutorUM = true;
					}
					
					String phoneNumberTutor = getDataValueFromPreference(preferenceDTOLoop, "phoneNumberTutor");
					if (phoneNumberTutor != null) {
						maccTutorUM.setPhoneNumberTutor(phoneNumberTutor);
						validMaccTutorUM = true;
					}
					
					String dialingCodeMobileTutor = getDataValueFromPreference(preferenceDTOLoop, "dialingCodeMobileTutor");
					if (dialingCodeMobileTutor != null) {
						maccTutorUM.setDialingCodeMobileTutor(dialingCodeMobileTutor);
						validMaccTutorUM = true;
					}
					
					String mobilePhoneTutor = getDataValueFromPreference(preferenceDTOLoop, "mobilePhoneTutor");
					if (mobilePhoneTutor != null) {
						maccTutorUM.setMobilePhoneTutor(mobilePhoneTutor);
						validMaccTutorUM = true;
					}
					
					String orderTutor = getDataValueFromPreference(preferenceDTOLoop, "orderTutor");
					if (orderTutor != null) {
						maccTutorUM.setOrderTutor(Integer.parseInt(orderTutor));
						validMaccTutorUM = true;
					}
					
					if (validMaccTutorUM) response.getMarketingDataResponse().getMarketingInformation().getTutorUM().add(maccTutorUM);
				}*/
				
				//HDC mapped to MarketingResponse
				if (preferenceDTOLoop.getType().equalsIgnoreCase("HDC")) {
					MaccHandicap maccHandicap = new MaccHandicap();
					boolean validMaccHandicap = false;
					
					String codeHCP1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "codeHCP1");
					if (codeHCP1 != null) {
						maccHandicap.setCodeHCP1(codeHCP1);
						validMaccHandicap = true;
					}
					
					String codeHCP2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "codeHCP2");
					if (codeHCP2 != null) {
						maccHandicap.setCodeHCP2(codeHCP2);
						validMaccHandicap = true;
					}
					
					String codeHCP3 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "codeHCP3");
					if (codeHCP3 != null) {
						maccHandicap.setCodeHCP3(codeHCP3);
						validMaccHandicap = true;
					}
					
					String medaCCFlag = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaCCFlag");
					if (medaCCFlag != null) {
						maccHandicap.setMedaCCFlag(medaCCFlag);
						validMaccHandicap = true;
					}
										
					Date medaCCDateStart = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaCCDateStart"));
					if (medaCCDateStart != null) {
						maccHandicap.setMedaCCDateStart(medaCCDateStart);
						validMaccHandicap = true;
					}
					
					Date medaCCDateEnd = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaCCDateEnd"));
					if (medaCCDateEnd != null) {
						maccHandicap.setMedaCCDateEnd(medaCCDateEnd);
						validMaccHandicap = true;
					}
					
					String medaCCAccomp = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaCCAccomp");
					if (medaCCAccomp != null) {
						maccHandicap.setMedaCCAccomp(medaCCAccomp);
						validMaccHandicap = true;
					}
					
					String medaCCTxt = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaCCTxt");
					if (medaCCTxt != null) {
						maccHandicap.setMedaCCTxt(medaCCTxt);
						validMaccHandicap = true;
					}
					
					String medaMCFlag = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaMCFlag");
					if (medaMCFlag != null) {
						maccHandicap.setMedaMCFlag(medaMCFlag);
						validMaccHandicap = true;
					}
											
					Date medaMCDateStart = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaMCDateStart"));
					if (medaMCDateStart != null) {
						maccHandicap.setMedaMCDateStart(medaMCDateStart);
						validMaccHandicap = true;
					}
											
					Date medaMCDateEnd = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaMCDateEnd"));
					if (medaMCDateEnd != null) {
						maccHandicap.setMedaMCDateEnd(medaMCDateEnd);
						validMaccHandicap = true;
					}
					
					String medaMCAccomp = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaMCAccomp");
					if (medaMCAccomp != null) {
						maccHandicap.setMedaMCAccomp(medaMCAccomp);
						validMaccHandicap = true;
					}
					
					String medaMCTxt = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaMCTxt");
					if (medaMCTxt != null) {
						maccHandicap.setMedaMCTxt(medaMCTxt);
						validMaccHandicap = true;
					}
					
					String medaLCFlag = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaLCFlag");
					if (medaLCFlag != null) {
						maccHandicap.setMedaLCFlag(medaLCFlag);
						validMaccHandicap = true;
					}
											
					Date medaLCDateStart = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaLCDateStart"));
					if (medaLCDateStart != null) {
						maccHandicap.setMedaLCDateStart(medaLCDateStart);
						validMaccHandicap = true;
					}
					
					Date medaLCDateEnd = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaLCDateEnd"));
					if (medaLCDateEnd != null) {
						maccHandicap.setMedaLCDateEnd(medaLCDateEnd);
						validMaccHandicap = true;
					}
					
					String medaLCAccomp = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaLCAccomp");
					if (medaLCAccomp != null) {
						maccHandicap.setMedaLCAccomp(medaLCAccomp);
						validMaccHandicap = true;
					}
					
					String medaLCTxt = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "medaLCTxt");
					if (medaLCTxt != null) {
						maccHandicap.setMedaLCTxt(medaLCTxt);
						validMaccHandicap = true;
					}
					
					String codeMat1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "codeMat1");
					if (codeMat1 != null) {
						maccHandicap.setCodeMat1(codeMat1);
						validMaccHandicap = true;
					}
					
					String length1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "length1");
					if (length1 != null) {
						maccHandicap.setLength1(Integer.parseInt(length1));
						validMaccHandicap = true;
					}
					
					String width1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "width1");
					if (width1 != null) {
						maccHandicap.setWidth1(Integer.parseInt(width1));
						validMaccHandicap = true;
					}
					
					String height1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "height1");
					if (height1 != null) {
						maccHandicap.setHeight1(Integer.parseInt(height1));
						validMaccHandicap = true;
					}
					
					String lengthPlie1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "lengthPlie1");
					if (lengthPlie1 != null) {
						maccHandicap.setLengthPlie1(Integer.parseInt(lengthPlie1));
						validMaccHandicap = true;
					}
					
					String widthPlie1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "widthPlie1");
					if (widthPlie1 != null) {
						maccHandicap.setWidthPlie1(Integer.parseInt(widthPlie1));
						validMaccHandicap = true;
					}
					
					String heightPlie1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "heightPlie1");
					if (heightPlie1 != null) {
						maccHandicap.setHeightPlie1(Integer.parseInt(heightPlie1));
						validMaccHandicap = true;
					}
					
					String weight1 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "weight1");
					if (weight1 != null) {
						maccHandicap.setWeight1(Integer.parseInt(weight1));
						validMaccHandicap = true;
					}
					
					String codeMat2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "codeMat2");
					if (codeMat2 != null) {
						maccHandicap.setCodeMat2(codeMat2);
						validMaccHandicap = true;
					}
					
					String length2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "length2");
					if (length2 != null) {
						maccHandicap.setLength2(Integer.parseInt(length2));
						validMaccHandicap = true;
					}
					
					String width2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "width2");
					if (width2 != null) {
						maccHandicap.setWidth2(Integer.parseInt(width2));
						validMaccHandicap = true;
					}
					
					String height2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "height2");
					if (height2 != null) {
						maccHandicap.setHeight2(Integer.parseInt(height2));
						validMaccHandicap = true;
					}
					
					String lengthPlie2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "lengthPlie2");
					if (lengthPlie2 != null) {
						maccHandicap.setLengthPlie2(Integer.parseInt(lengthPlie2));
						validMaccHandicap = true;
					}
					
					String widthPlie2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "widthPlie2");
					if (widthPlie2 != null) {
						maccHandicap.setWidthPlie2(Integer.parseInt(widthPlie2));
						validMaccHandicap = true;
					}
					
					String heightPlie2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "heightPlie2");
					if (heightPlie2 != null) {
						maccHandicap.setHeightPlie2(Integer.parseInt(heightPlie2));
						validMaccHandicap = true;
					}
					
					String weight2 = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "weight2");
					if (weight2 != null) {
						maccHandicap.setWeight2(Integer.parseInt(weight2));
						validMaccHandicap = true;
					}
					
					String otherMat = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "otherMat");
					if (otherMat != null) {
						maccHandicap.setOtherMat(otherMat);
						validMaccHandicap = true;
					}
					
					String dogGuideFlag = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "dogGuideFlag");
					if (dogGuideFlag != null) {
						maccHandicap.setDogGuideFlag(dogGuideFlag);
						validMaccHandicap = true;
					}
					
					String dogGuideBreed = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "dogGuideBreed");
					if (dogGuideBreed != null) {
						maccHandicap.setDogGuideBreed(dogGuideBreed);
						validMaccHandicap = true;
					}
					
					String dogGuideWeight = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "dogGuideWeight");
					if (dogGuideWeight != null) {
						maccHandicap.setDogGuideWeight(Integer.parseInt(dogGuideWeight));
						validMaccHandicap = true;
					}
					
					String oxygFlag = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "oxygFlag");
					if (oxygFlag != null) {
						maccHandicap.setOxygFlag(oxygFlag);
						validMaccHandicap = true;
					}
					
					String oxygOutput = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "oxygOutput");
					if (oxygOutput != null) {
						maccHandicap.setOxygOutput(Integer.parseInt(oxygOutput));
						validMaccHandicap = true;
					}
					
					if (validMaccHandicap) response.getMarketingDataResponse().getMarketingInformation().setHandicap(maccHandicap);
				}
				
				//TCC mapped to MarketingResponse
				if (preferenceDTOLoop.getType().equalsIgnoreCase("TCC")) {
					MaccTravelCompanion maccTravelCompanion = new MaccTravelCompanion();
					boolean validMaccTravelCompanion = false;
					
					String civility = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "civility");
					if (civility != null) {
						maccTravelCompanion.setCivility(civility);
						validMaccTravelCompanion = true;
					}
					
					String firstNameTCC = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "firstName");
					if (firstNameTCC != null) {
						maccTravelCompanion.setFirstName(firstNameTCC);
						validMaccTravelCompanion = true;
					}
					
					String lastNameTCC = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "lastName");
					if (lastNameTCC != null) {
						maccTravelCompanion.setLastName(lastNameTCC);
						validMaccTravelCompanion = true;
					}
											
					Date dateOfBirth = PreferenceUtils.getDateFromString(PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "dateOfBirth"));
					if (dateOfBirth != null) {
						maccTravelCompanion.setDateOfBirth(dateOfBirth);
						validMaccTravelCompanion = true;
					}
					
					String emailTCC = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "email");
					if (emailTCC != null) {
						maccTravelCompanion.setEmail(emailTCC);
						validMaccTravelCompanion = true;
					}
					
					String blueBizNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "blueBizNumber");
					String FFPNumber = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "FFPNumber");
					String FFPProgram = PreferenceUtils.getDataValueFromPreference(preferenceDTOLoop, "FFPProgram");
					if (blueBizNumber != null || FFPNumber != null || FFPProgram != null) {
						validMaccTravelCompanion = true;
						MaccPersonalInformation maccPersonalInformation = new MaccPersonalInformation();
						
						if (blueBizNumber != null) {
							maccPersonalInformation.setBlueBizNumber(blueBizNumber);
						}
						
						
						if (FFPNumber != null) {
							maccPersonalInformation.setFFPNumber(FFPNumber);
						}
						
						
						if (FFPProgram != null) {
							maccPersonalInformation.setFFPProgram(FFPProgram);
						}
						
						maccTravelCompanion.setPersonalInformation(maccPersonalInformation);
					}

					if (validMaccTravelCompanion) {
						// REPIND-1563 : Do not return TravelCompanion over 8 that is the maximum size defined on WSDL for V6
						if (response != null && response.getMarketingDataResponse() != null && response.getMarketingDataResponse().getMarketingInformation() != null &&
								response.getMarketingDataResponse().getMarketingInformation().getTravelCompanion() != null && response.getMarketingDataResponse().getMarketingInformation().getTravelCompanion().size() < PREFERENCE_TCC_LIMIT) {
							response.getMarketingDataResponse().getMarketingInformation().getTravelCompanion().add(maccTravelCompanion);	
						}
					}
				}
			}
		}
	}
	
	// This method keep on the list only the most recents passport found
	private static void filterFoundDocument(List<PreferenceDTO> preferenceDTOList, int numberToKeep) {
		if (preferenceDTOList != null && !preferenceDTOList.isEmpty()) {
			List<PreferenceDTO> passportList = new ArrayList<PreferenceDTO>();
			List<PreferenceDTO> visaList = new ArrayList<PreferenceDTO>();
			
			for (PreferenceDTO prefDto : preferenceDTOList) {
				// Check if it's a passport and add to the list
				if ("TDC".equalsIgnoreCase(prefDto.getType()) && "PA".equalsIgnoreCase(PreferenceUtils.getDataValueFromPreference(prefDto, "type"))) {
					passportList.add(prefDto);				
				}
				if ("TDC".equalsIgnoreCase(prefDto.getType()) && "VI".equalsIgnoreCase(PreferenceUtils.getDataValueFromPreference(prefDto, "type"))) {
					visaList.add(prefDto);				
				}
			}
			
			// Remove passport from initial list and keep only the last passport fixed by the limitation in 'numberToKeep'
			if (passportList.size() > numberToKeep) {
				cleanDocumentList(preferenceDTOList, passportList, numberToKeep);
			}
			
			// Remove visa from initial list and keep only the last visa fixed by the limitation in 'numberToKeep'
			if (visaList.size() > numberToKeep) {
				cleanDocumentList(preferenceDTOList, visaList, numberToKeep);
			}
		}
	}

	private static void cleanDocumentList(List<PreferenceDTO> preferenceList, List<PreferenceDTO> docList, int numberToKeep) {
		preferenceList.removeAll(docList);
		
		// Sort list 
		Collections.sort(docList, new Comparator<PreferenceDTO>() {
			@Override
			public int compare(PreferenceDTO p1, PreferenceDTO p2) {
				int result = 0;
				if (p1.getDateModification() != null) {
					if (p2.getDateModification() != null) {
						result = p1.getDateModification().compareTo(p2.getDateModification());
					}
					else if (p2.getDateCreation() != null) {
						result = p1.getDateModification().compareTo(p2.getDateCreation());
		}
	}
				else if (p1.getDateCreation() != null) {
					if (p2.getDateModification() != null) {
						result = p1.getDateCreation().compareTo(p2.getDateModification());
					}
					else if (p2.getDateCreation() != null) {
						result = p1.getDateCreation().compareTo(p2.getDateCreation());
					}
				}
				else {
					result = 0;
				}
				return result;
			}
		});
		
		Collections.reverse(docList);
		// We keep the most recent
		if (docList.size() > numberToKeep) {
			for (int i = 0; i < numberToKeep; ++i) {
				preferenceList.add(docList.get(i));
			}
		}
	}
		
	public static void transformToAlert(List<AlertDTO> alertDTOList, ProvideIndividualInformationResponse response) {
		AlertResponse alertResponse = new AlertResponse();
		if (alertDTOList != null && !alertDTOList.isEmpty() ) {
			for (AlertDTO dto: alertDTOList) {
				alertResponse.getAlert().add(transformToAlert(dto));
			}
			response.setAlertResponse(alertResponse);
		}
	}

	private static Alert transformToAlert(AlertDTO dto) {
		Alert alert = null;
		
		if (dto != null) {
			alert = new Alert();
			alert.setAlertId(dto.getAlertId().toString());
			alert.setType(dto.getType());
			alert.setOptIn(dto.getOptIn());
			
			if (dto.getAlertDataDTO() != null) {
				for (AlertDataDTO alertDataDTO: dto.getAlertDataDTO()) {
					alert.getAlertData().add(transformToAlertData(alertDataDTO));
				}
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
	

	// REPIND-555 : We chose one postal address taken from ADR_POST to provide LOCALIZATION block
	private static PostalAddressDTO choseOnePostalAddress(List<PostalAddressDTO> postalAddressDTO) {
		
		for (PostalAddressDTO pa : postalAddressDTO) {
			// On renvoi la premiere adresse Valide trouvée
			if ("V".equals(pa.getSstatut_medium())) {
				return pa;
			}
		}
		
		for (PostalAddressDTO pa : postalAddressDTO) {
			// On renvoi la premiere adresse Direct trouvée
			if ("D".equals(pa.getScode_medium())) {
				return pa;
			}
		}

		for (PostalAddressDTO pa : postalAddressDTO) {
			// On renvoi la premiere adresse trouvée
			return pa;
		}
		
		return null;
	}
	
	public static void transformToLocalizationResponse(List<PostalAddressDTO> prospectLocalizationDTO, List<LocalizationResponse> list){

		if(prospectLocalizationDTO==null || prospectLocalizationDTO.isEmpty()) {
			return;
		}
		
		// REPIND-555 : We have to chose only one PostalAddress 
		PostalAddressDTO pad = choseOnePostalAddress(prospectLocalizationDTO);
		
		if(pad != null) {
			LocalizationResponse ws = new LocalizationResponse();

			ws.setLocalization(transformToLocalization(pad));

			ws.getSignatureV2().add(transformToSignatureV2(pad));

			list.add(ws);
		}
	}
	
	public static Localization transformToLocalization(PostalAddressDTO prospectLocalizationDTO) {
		Localization ws = new Localization();

		ws.setCity(prospectLocalizationDTO.getSville());
		ws.setCountryCode(prospectLocalizationDTO.getScode_pays());
		ws.setMediumCode(prospectLocalizationDTO.getScode_medium());
		ws.setMediumStatus(prospectLocalizationDTO.getSstatut_medium());
		String postalAdress = "";
		if(prospectLocalizationDTO.getSno_et_rue() != null) {
			postalAdress += prospectLocalizationDTO.getSno_et_rue();
		}
		if(prospectLocalizationDTO.getScomplement_adresse() != null) {
			postalAdress += prospectLocalizationDTO.getScomplement_adresse();
		}
		if(prospectLocalizationDTO.getSdescriptif_complementaire() != null) {
			postalAdress += prospectLocalizationDTO.getSdescriptif_complementaire();
		}
		if(!StringUtils.isEmpty(postalAdress)) {
			ws.setPostalAddress(postalAdress);
		}
		ws.setStateCode(prospectLocalizationDTO.getScode_province());
		ws.setZipCode(prospectLocalizationDTO.getScode_postal());
		
		return ws;
	}
	
	public static SignatureV2 transformToSignatureV2(PostalAddressDTO prospectLocalizationDTO) {
		SignatureV2 signature = new SignatureV2();

		signature.setCreationDate(prospectLocalizationDTO.getDdate_creation());
		signature.setCreationSignature(prospectLocalizationDTO.getSignature_creation());
		signature.setCreationSite(prospectLocalizationDTO.getSsite_creation());
		signature.setModificationDate(prospectLocalizationDTO.getDdate_modification());
		signature.setModificationSignature(prospectLocalizationDTO.getSsignature_modification());
		signature.setModificationSite(prospectLocalizationDTO.getSsite_modification());

		return signature;
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
	
	// REPIND-555 : On recupere la preference d'aeroport prefere dans la liste depreference de l'individu 
	private static PreferenceDTO choseOnePreferredAirport(List <PreferenceDTO> prospectLocalizationDTO) {
		// Search for Prefered Airport
		for (PreferenceDTO p : prospectLocalizationDTO) {
			// On trouve le Travel Preference Content TPC
			if ("TPC".equals(p.getType())) {
				Set <PreferenceDataDTO> pds = p.getPreferenceDataDTO();
				
				for (PreferenceDataDTO pd : pds) {
					// On cherche l'ID du PreferenceData qui est de Type WWp => Travel Preference pour Prospect
					// REPIND-768 : We do not need to store type=WWP for PreferedAirPort
					// if ("TYPE".equals(pd.getKey()) && "WWP".equals(pd.getValue())) {
					if ("preferredAirport".equals(pd.getKey())) {
						return p;
					}
				}
			}
		}
		return null;
	}

	// On a obligatoirement une preference de type WWP (Appel de choseOnePreferredAirport avant)
	private static String getPreferredAirport(PreferenceDTO preferenceDTO) {

		if (preferenceDTO != null) {
			
			Set <PreferenceDataDTO> pds = preferenceDTO.getPreferenceDataDTO();
			for (PreferenceDataDTO pd : pds) {
				// On cherche l'ID du PreferenceData qui est de Type WWp => Travel Preference pour Prospect
				if ("preferredAirport".equals(pd.getKey())) {
					return pd.getValue();
				}
			}
		}

		return "";
	}
	
	public static PreferencesResponse transformToPreferencesResponse(List <PreferenceDTO> prospectLocalizationDTO) {
		PreferencesResponse pr = null;
		if(prospectLocalizationDTO != null) {
			
			PreferenceDTO pd = choseOnePreferredAirport(prospectLocalizationDTO);
			
			pr = new PreferencesResponse();
			Preference pref = new Preference();
			
			pref.setTypePreference("TPC");
	
// REPIND-768 : We do not need to store type=WWP for PreferedAirPort 
//			PreferenceData pd1 = new PreferenceData();
//			pd1.setKey("TYPE");
//			pd1.setValue("WWP");
//			pref.getPreferenceData().add(pd1);
			
			PreferenceData pd2 = new PreferenceData();
			pd2.setKey("preferredAirport");			// "TYPE" => BUG ?
			pd2.setValue(getPreferredAirport(pd));
			pref.getPreferenceData().add(pd2);
			
			pr.getPreference().add(pref);
		}
		return pr;
	}
	
}
