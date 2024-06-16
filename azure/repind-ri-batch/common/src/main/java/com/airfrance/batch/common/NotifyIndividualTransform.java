package com.airfrance.batch.common;

import com.afklm.soa.stubs.w001539.v1.data.IndividualInformationData;
import com.afklm.soa.stubs.w001539.v1.individual.*;
import com.afklm.soa.stubs.w001539.v1.siccommonenum.LanguageCodesEnum;
import com.afklm.soa.stubs.w001539.v1.siccommontype.Signature;
import com.afklm.soa.stubs.w001539.v1.sicindividutype.*;
import com.airfrance.batch.common.enums.PopulationTypeEnum;
import com.airfrance.batch.common.enums.ScopesToProvideEnum;
import com.airfrance.ref.exception.InvalidParameterException;
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
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.tracking.TriggerChangeIndividus;
import com.airfrance.repind.util.SicStringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Set;

public class NotifyIndividualTransform {
	
	public final static int CONTRACT_LIMIT = 25;
	public final static int EMAIL_LIMIT = 2;
	public final static int POSTAL_ADDRESS_LIMIT = 5;
	public final static int TELECOM_LIMIT = 2;
		
public static IndividualInformationData transformToIndividualInformationData(IndividuDTO dto, Set<ScopesToProvideEnum> scopesToProvideSet, Boolean isIndividu) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}

		IndividualInformationData ws = new IndividualInformationData();
		ws.setIndividualBlock(transformToIndividualBlock(dto, scopesToProvideSet, isIndividu));
	
		return ws;
	
	}
	
	public static void transformToContractBlock(List<RoleContratsDTO> dtoList, List<ContractBlock> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(RoleContratsDTO dto : dtoList) {
			
			if(wsList.size()==CONTRACT_LIMIT) {
				break;
			}
			
			wsList.add(transformToContractBlock(dto));
		}
		
	}

	//REPIND-3034 : do the same when a contract has been deleted
	public static void transformToContractBlockDelete(List<TriggerChangeIndividus> tcList, List<ContractBlock> wsList)
			throws JsonProcessingException{

		if(tcList==null || tcList.isEmpty()) {
			return;
		}

		for(TriggerChangeIndividus triggerChangeIndividus : tcList) {

			if(wsList.size()==CONTRACT_LIMIT) {
				break;
			}

			wsList.add(transformToContractBlockDelete(triggerChangeIndividus));
		}

	}
	
	public static ContractBlock transformToContractBlock(RoleContratsDTO dto) {
		
		if (dto == null) {
			return null;
		}

		ContractBlock ws = new ContractBlock();
		ws.setContract(transformToContract(dto));
		transformToSignature(dto, ws.getSignature());
	
		return ws;
	}

	//REPIND-3034 : do the same when a contract has been deleted
	public static ContractBlock transformToContractBlockDelete(TriggerChangeIndividus triggerChangeIndividus)
			throws JsonProcessingException {

		if (triggerChangeIndividus == null || triggerChangeIndividus.getChangeAfter() == null) {
			return null;
		}
		else {

			ContractBlock ws = new ContractBlock();

			//Contract
			ContractV2 contractV2 = new ContractV2();

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(triggerChangeIndividus.getChangeAfter());
			String contractNumber = rootNode.get("contractNumber").asText();
			String contractType = rootNode.get("contractType").asText();

			contractV2.setContractNumber(contractNumber);
			contractV2.setProductType(contractType);

			ws.setContract(contractV2);

			//Signature
			Signature modification = new Signature();
			modification.setSignatureSite("DELETED");
			modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

			List<Signature> wsList = ws.getSignature();
			wsList.add(modification);

			return ws;
		}
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
		
		if (dto == null) {
			return null;
		}
	
		ContractV2 ws = new ContractV2();
		

		ws.setCompanyContractType(dto.getCodeCompagnie());
		ws.setContractNumber(dto.getNumeroContrat());
		ws.setContractStatus(dto.getEtat());
		
		ws.setProductSubType(dto.getSousType());
		ws.setProductType(dto.getTypeContrat());

		// REPIND-1263 : We truncate 2 on left because 3 is too huge for WSDL (ROLE_CONTRACT / VERSION)
		ws.setVersion(SicStringUtils.leftTruncate(SicStringUtils.getStringFromObject(dto.getVersion()), 2));
	
		return ws;
	}
	
	
	
	public static IndividualBlock transformToIndividualBlock(IndividuDTO dto, Set<ScopesToProvideEnum> scopesToProvideSet, Boolean isIndividu) throws InvalidParameterException {
		
		if(dto==null) {
			return null;
		}
		
		IndividualBlock ws = new IndividualBlock();
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
		ws.setPersonalIdentifier(dto.getIdentifiantPersonnel());
		ws.setSecondFirstName(dto.getSecondPrenom());
		ws.setStatus(dto.getStatutIndividu());
		ws.setVersion(String.valueOf(dto.getVersion()));
		String populationType = null;
		if(isIndividu) {
			populationType = PopulationTypeEnum.INDIVIDU.toString();
		} else {
			populationType = PopulationTypeEnum.WHITE_WINGERS.toString();
		}
		ws.setPopulationType(populationType);
//		ws.setManagingCompany(dto.getCieGestionnaire());
//		ws.setDateFusion(SicDateUtils.stringToDate(dto.getDateFusion()));
		ws.setGinFusion(dto.getGinFusion());
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
		ws.setFirstName(dto.getPrenom());
		ws.setLastName(dto.getNom());
	
		return ws;
	}

	public static IndividualProfilV2 transformToIndividualProfil(ProfilsDTO dto) {
			
			if (dto==null) {
				return null;
			}
		
			IndividualProfilV2 ws = new IndividualProfilV2();
			ws.setCivilianCode(dto.getScode_maritale());
			ws.setEmailOptin(dto.getSmailing_autorise());
			ws.setLanguageCode(dto.getScode_langue());
			//ws.setProfilKey(SicStringUtils.getStringFromObject(dto.getCleProfil()));
			//ws.setFlagSolvency(dto.getIndicSolvabilite());
		
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

	

	public static void transformToEmailBlock(List<EmailDTO> dtoList, List<EmailBlock> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(EmailDTO dto : dtoList) {
			
			if(wsList.size()==EMAIL_LIMIT) {
				break;
			}
			
			wsList.add(transformToEmailBlock(dto));
		}
		
	}
	
	public static EmailBlock transformToEmailBlock(EmailDTO dto) {
		
		if (dto==null) {
			return null;
		}
	
		EmailBlock ws = new EmailBlock();
		ws.setEmail(transformToEmail(dto));
		transformToSignature(dto,ws.getSignature());
		
		return ws;
	}

	public static Email transformToEmail(EmailDTO dto) {
		
		if (dto==null) {
			return null;
		}
	
		Email ws = new Email();
		ws.setEmail(dto.getEmail());
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

	public static void transformToPostalAddressBlock(List<PostalAddressDTO> dtoList, List<PostalAddressBlock> wsList) throws InvalidParameterException {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(PostalAddressDTO dto : dtoList) {
			
			if(wsList.size()==POSTAL_ADDRESS_LIMIT) {
				break;
			}
			wsList.add(transformToPostalAddressBlock(dto));
		}
		
	}
	
	public static PostalAddressBlock transformToPostalAddressBlock(PostalAddressDTO dto) throws InvalidParameterException {
		
		if (dto==null) {
			return null;
		}
	
		PostalAddressBlock ws = new PostalAddressBlock();
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
		
		// REPIND-657 - Replace character no printable
		ws.setAdditionalInformation(replaceCharacterNoPrintable(dto.getScomplement_adresse()));
		ws.setCity(replaceCharacterNoPrintable(dto.getSville()));
		ws.setCorporateName(replaceCharacterNoPrintable(dto.getSraison_sociale()));
		ws.setCountryCode(dto.getScode_pays());
		ws.setDistrict(replaceCharacterNoPrintable(dto.getSlocalite()));
		ws.setNumberAndStreet(replaceCharacterNoPrintable(dto.getSno_et_rue()));
		ws.setStateCode(replaceCharacterNoPrintable(dto.getScode_province()));
		ws.setZipCode(replaceCharacterNoPrintable(dto.getScode_postal()));
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
	
	public static void transformToTelecomBlock(List<TelecomsDTO> dtoList, List<TelecomBlock> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(TelecomsDTO dto : dtoList) {
			wsList.add(transformToTelecomBlock(dto));
		}
		
	}

	public static TelecomBlock transformToTelecomBlock(TelecomsDTO dto) {
		
		if (dto==null) {
			return null;
		}

		TelecomBlock ws = new TelecomBlock();
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
	
	public static void transformToExternalIdentifierBlock(List<ExternalIdentifierDTO> dtoList, List<ExternalIdentifierBlock> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(ExternalIdentifierDTO dto : dtoList) {
			wsList.add(transformToExternalIdentifierBlock(dto));
		}
		
	}
	
	public static ExternalIdentifierBlock transformToExternalIdentifierBlock(ExternalIdentifierDTO dto) {
		
		if (dto==null) {
			return null;
		}

		ExternalIdentifierBlock ws = new ExternalIdentifierBlock();
		ws.setExternalIdentifier(transformToExternalIdentifier(dto));
		transformToExternalIdentifierDataBlock(dto.getExternalIdentifierDataList(), ws.getExternalIdentifierData());
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
	
	public static void transformToExternalIdentifierDataBlock(List<ExternalIdentifierDataDTO> dtoList, List<ExternalIdentifierDataBlock> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(ExternalIdentifierDataDTO dto : dtoList) {
			wsList.add(transformToExternalIdentifierDataBlock(dto));
		}
		
	}
	
	public static ExternalIdentifierDataBlock transformToExternalIdentifierDataBlock(ExternalIdentifierDataDTO dto) {
		
		if (dto==null) {
			return null;
		}

		ExternalIdentifierDataBlock ws = new ExternalIdentifierDataBlock();
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
	
	public static AccountDataBlock transformToAccountDataBlock(AccountDataDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		AccountDataBlock ws = new AccountDataBlock();
		ws.setAccountData(transformToAccountData(dto));
		transformToSignature(dto, ws.getSignature());
		
		return ws;
	}
	
	public static AccountDataV2 transformToAccountData(AccountDataDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		AccountDataV2 ws = new AccountDataV2();
		ws.setAccountIdentifier(dto.getAccountIdentifier());
		ws.setCustomerType(transformToCustomerType(dto));
		ws.setEmailIdentifier(dto.getEmailIdentifier());
		ws.setFbIdentifier(dto.getFbIdentifier());
//		ws.setPercentageFullProfil(); filled later
		ws.setPersonnalizedIdentifier(dto.getPersonnalizedIdentifier());

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
	
	public static DelegationDataBlock dtoTOws(List<DelegationDataDTO> delegatorDataListDTO, List<DelegationDataDTO> delegateDataListDTO) {
		
		if(delegatorDataListDTO==null) {
			return null;
		}
	
		DelegationDataBlock delegationDataBlock = new DelegationDataBlock();

		for (DelegationDataDTO delegatorDTO : delegatorDataListDTO){

			// we must not provide the C, RJ and RF status
			if (DelegationActionEnum.CANCELLED.getStatus().equals(delegatorDTO.getStatus()) || 
					DelegationActionEnum.REJECTED.getStatus().equals(delegatorDTO.getStatus()) || 
					DelegationActionEnum.DELETED.getStatus().equals(delegatorDTO.getStatus())){
				continue;
			}

			Delegator delegator = new Delegator();
			
			// Process changing the status so we know who made the invitation by reading the WS Block
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
			
			delegationDataBlock.getDelegator().add(delegator);
		}
		
		for (DelegationDataDTO delegateDTO : delegateDataListDTO){
			
			Delegate delegate = new Delegate();
			
			// Process changing the status so we know who made the invitation by reading the WS Block
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
			
			delegationDataBlock.getDelegate().add(delegate);
		}

		return delegationDataBlock;
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
	
	public static DelegationIndividualData transformToDelegationIndividualData(IndividuDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		DelegationIndividualData ws = new DelegationIndividualData();
		
		ws.setFirstName(dto.getPrenom());
		ws.setFirstNameSC(dto.getPrenomSC());
		ws.setLastName(dto.getNom());
		ws.setLastNameSC(dto.getNomSC());
		
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
	public static void transformToTelecom(IndividuDTO dto, List<Telecom> wsList) {
		
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

	public static void transformToPrefilledNumbersBlock(List<PrefilledNumbersDTO> dtoList, List<PrefilledNumbersBlock> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(PrefilledNumbersDTO dto : dtoList) {
			PrefilledNumbersBlock prefilledNumbersBlock = transformToPrefilledNumbersBlock(dto);
			wsList.add(prefilledNumbersBlock);
		}

	}
	
	public static PrefilledNumbersBlock transformToPrefilledNumbersBlock(PrefilledNumbersDTO dto) {

		if(dto==null) {
			return null;
		}

		PrefilledNumbersBlock ws = new PrefilledNumbersBlock();
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
	
	public static void transformToCommunicationPreferencesBlock(List<CommunicationPreferencesDTO> dtoList, List<CommunicationPreferencesBlock> wsList) {
		
		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}
		
		for(CommunicationPreferencesDTO dto : dtoList) {
			CommunicationPreferencesBlock ws = transformToCommunicationPreferencesBlock(dto);
			wsList.add(ws);
		}
	}
	
	public static CommunicationPreferencesBlock transformToCommunicationPreferencesBlock(CommunicationPreferencesDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		CommunicationPreferencesBlock ws = new CommunicationPreferencesBlock();
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


	

	public static PreferencesBlock transformToPreferencesBlock(List<PreferenceDTO> listPreferenceDTO) {
		PreferencesBlock pr = null;
		
		if(listPreferenceDTO != null) {
			
			for (PreferenceDTO preferenceDTO : listPreferenceDTO) {
							
				if(preferenceDTO.getType().equals("TPC")) {
					if(preferenceDTO.getPreferenceDataDTO() != null && !preferenceDTO.getPreferenceDataDTO().isEmpty()) {
						for (PreferenceDataDTO preferencedataDTO : preferenceDTO.getPreferenceDataDTO()) {
							if(preferencedataDTO.getKey().equalsIgnoreCase("preferredAirport")) {
								pr = new PreferencesBlock();
								Preference pref = new Preference();
								pref.setPrefferedAirport(preferencedataDTO.getValue());
								pr.setPreference(pref);
							}
						}
					}
				}
				
			}
			
		}
		
		return pr;
	}
	
	
	public static String replaceCharacterNoPrintable(String stringToCheck) {
		if(stringToCheck != null) {
			return stringToCheck.replaceAll("\\p{C}", "?");
		} else {
			return stringToCheck;
		}
	}
}
