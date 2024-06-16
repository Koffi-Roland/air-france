package com.airfrance.batch.common;


import com.airfrance.batch.common.enums.PopulationTypeEnum;
import com.airfrance.batch.common.enums.ScopesToProvideEnum;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.ref.type.ProductTypeEnum;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.dto.profil.Profil_afDTO;
import com.airfrance.repind.dto.profil.Profil_mereDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Set;

import static com.airfrance.batch.common.NotifyIndividualTransform.*;


public class NotifyAdhocIndividualTransform {
	
	// REPIND-795
	final static String TPC = "TPC";
	final static String preferredAirport = "preferredAirport";
	final static String AirFranceProfilId = "0";

	public final static int EMAIL_LIMIT = 2;
	public final static int POSTAL_ADDRESS_LIMIT = 5;
	public final static int CONTRACT_LIMIT = 25;

	public static com.afklm.soa.stubs.w001992.v1_0_1.data.IndividualInformationData transformToIndividualInformationData(IndividuDTO dto, Set<ScopesToProvideEnum> scopesToProvideSet, Boolean isIndividu) throws InvalidParameterException {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.data.IndividualInformationData ws = new com.afklm.soa.stubs.w001992.v1_0_1.data.IndividualInformationData();
		ws.setIndividualBlock(transformToIndividualBlock(dto, scopesToProvideSet, isIndividu));

		return ws;

	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.IndividualBlock transformToIndividualBlock(IndividuDTO dto, Set<ScopesToProvideEnum> scopesToProvideSet, Boolean isIndividu) throws InvalidParameterException {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.IndividualBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.IndividualBlock();
		ws.setIndividualInformations(transformToIndividualInformation(dto, isIndividu));
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

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.IndividualInformationsV3 transformToIndividualInformation(IndividuDTO dto, Boolean isIndividu) throws InvalidParameterException {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.IndividualInformationsV3 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.IndividualInformationsV3();
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
		if(dto.getProfilsdto()!=null) {
			ws.setLanguageCode(dto.getProfilsdto().getScode_langue());
		}
		if(StringUtils.isNotBlank(dto.getGinFusion())) {
			ws.setGinFusion(dto.getGinFusion());
		}
		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.Civilian transformToCivilian(IndividuDTO dto) {

		if (dto==null || StringUtils.isEmpty(dto.getCodeTitre())) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.Civilian ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.Civilian();
		ws.setTitleCode(dto.getCodeTitre());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.IndividualProfilV3 transformToIndividualProfil(ProfilsDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.IndividualProfilV3 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.IndividualProfilV3();
		ws.setCivilianCode(dto.getScode_maritale());
		ws.setEmailOptin(dto.getSmailing_autorise());
		ws.setLanguageCode(dto.getScode_langue());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.NormalizedName transformToNormalizedName(IndividuDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.NormalizedName ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.NormalizedName();
		ws.setFirstName(dto.getPrenom());
		ws.setLastName(dto.getNom());

		return ws;
	}

	public static void transformToAirFranceProfil(Set<Profil_mereDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AirFranceProfilV3> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(Profil_mereDTO dto : dtoList) {
			wsList.add(transformToAirFranceProfil(dto));
		}

	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AirFranceProfilV3 transformToAirFranceProfil(Profil_mereDTO dto) {

		if(dto==null) {
			return null;
		}

		return transformToAirFranceProfil(dto.getProfil_afdto());
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AirFranceProfilV3 transformToAirFranceProfil(Profil_afDTO dto) {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AirFranceProfilV3 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AirFranceProfilV3();

		ws.setId(AirFranceProfilId);
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

	public static void transformToSignature(IndividuDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(AccountDataDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(TelecomsDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getDdate_creation());
		creation.setSignature(dto.getSsignature_creation());
		creation.setSignatureSite(dto.getSsite_creation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getDdate_modification());
		modification.setSignature(dto.getSsignature_modification());
		modification.setSignatureSite(dto.getSsite_modification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(EmailDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(PostalAddressDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getDdate_creation());
		creation.setSignature(dto.getSignature_creation());
		creation.setSignatureSite(dto.getSsite_creation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getDdate_modification());
		modification.setSignature(dto.getSsignature_modification());
		modification.setSignatureSite(dto.getSsite_modification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(RoleContratsDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getDateCreation());
		creation.setSignature(dto.getSignatureCreation());
		creation.setSignatureSite(dto.getSiteCreation());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getDateModification());
		modification.setSignature(dto.getSignatureModification());
		modification.setSignatureSite(dto.getSiteModification());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(ExternalIdentifierDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(ExternalIdentifierDataDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(CommunicationPreferencesDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToSignature(MarketLanguageDTO dto, List<com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature> wsList) {

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature creation = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		creation.setDate(dto.getCreationDate());
		creation.setSignature(dto.getCreationSignature());
		creation.setSignatureSite(dto.getCreationSite());
		creation.setSignatureType(SignatureTypeEnum.CREATION.toString());

		com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature modification = new com.afklm.soa.stubs.w001992.v1_0_1.siccommontype.Signature();
		modification.setDate(dto.getModificationDate());
		modification.setSignature(dto.getModificationSignature());
		modification.setSignatureSite(dto.getModificationSite());
		modification.setSignatureType(SignatureTypeEnum.MODIFICATION.toString());

		wsList.add(creation);
		wsList.add(modification);
	}

	public static void transformToUsageClient(Set<UsageClientsDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageClient> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(UsageClientsDTO dto : dtoList) {
			wsList.add(transformToUsageClient(dto));
		}

	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageClient transformToUsageClient(UsageClientsDTO dto) {

		if (dto == null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageClient ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageClient();
		ws.setApplicationCode(dto.getScode());
		ws.setAuthorizedModification(dto.getSconst());
		ws.setLastModificationDate(dto.getDate_modification());
		ws.setSrin(SicStringUtils.getStringFromObject(dto.getSrin()));

		return ws;
	}

	public static void transformToUsageAddress(Set<Usage_mediumDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageAddressV2> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(Usage_mediumDTO dto : dtoList) {
			wsList.add(transformToUsageAddress(dto));
		}

	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageAddressV2 transformToUsageAddress(Usage_mediumDTO dto) {

		if (dto == null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageAddressV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.UsageAddressV2();
		ws.setAddressRoleCode(dto.getSrole1());
		ws.setApplicationCode(dto.getScode_application());
		ws.setUsageNumber(SicStringUtils.getStringFromObject(dto.getInum()));

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.AccountDataBlock transformToAccountDataBlock(AccountDataDTO dto) {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.AccountDataBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.AccountDataBlock();
		ws.setAccountData(transformToAccountData(dto));
		transformToSignature(dto, ws.getSignature());
		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AccountDataV2 transformToAccountData(AccountDataDTO dto) {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AccountDataV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.AccountDataV2();
		ws.setId(dto.getId().toString());
		ws.setAccountIdentifier(dto.getAccountIdentifier());
		ws.setCustomerType(transformToCustomerType(dto));
		ws.setEmailIdentifier(dto.getEmailIdentifier());
		ws.setFbIdentifier(dto.getFbIdentifier());
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

	public static void transformToTelecomBlock(List<TelecomsDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.individual.TelecomBlock> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(TelecomsDTO dto : dtoList) {
			wsList.add(transformToTelecomBlock(dto));
		}

	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.TelecomBlock transformToTelecomBlock(TelecomsDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.TelecomBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.TelecomBlock();
		ws.setTelecom(transformToTelecom(dto));
		ws.setTelecomFlags(transformToTelecomFlags(dto));
		ws.setTelecomNormalization(transformToTelecomNormalization(dto));
		transformToSignature(dto, ws.getSignature());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomV2 transformToTelecom(TelecomsDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomV2();
		ws.setId(dto.getSain());
		ws.setCountryCode(transformCountryCode(dto));
		ws.setMediumStatus(dto.getSstatut_medium());
		ws.setMediumCode(dto.getScode_medium());
		ws.setPhoneNumber(transformPhoneNumber(dto));
		ws.setTerminalType(dto.getSterminal());
		ws.setVersion(SicStringUtils.getStringFromObject(dto.getVersion()));

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomFlags transformToTelecomFlags(TelecomsDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomFlags ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomFlags();
		ws.setFlagInvalidFixTelecom(dto.isInvalidFixedLinePhone());
		ws.setFlagInvalidMobileTelecom(dto.isInvalidMobilePhone());
		ws.setFlagNoValidNormalizedTelecom(dto.isNoValidNormalizedTelecom());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomNormalization transformToTelecomNormalization(TelecomsDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomNormalization ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.TelecomNormalization();
		ws.setInternationalPhoneNumber(dto.getSnorm_inter_phone_number());
		ws.setIsoCountryCode(dto.getIsoCountryCode());

		return ws;
	}

	public static void transformToEmailBlock(List<EmailDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.individual.EmailBlock> wsList) {

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

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.EmailBlock transformToEmailBlock(EmailDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.EmailBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.EmailBlock();
		ws.setEmail(transformToEmail(dto));
		transformToSignature(dto,ws.getSignature());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.EmailV2 transformToEmail(EmailDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.EmailV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.EmailV2();
		ws.setId(dto.getSain());
		ws.setEmail(dto.getEmail());
		ws.setEmailOptin(dto.getAutorisationMailing());
		ws.setMediumCode(dto.getCodeMedium());
		ws.setMediumStatus(dto.getStatutMedium());
		ws.setVersion(SicStringUtils.getStringFromObject(dto.getVersion()));

		return ws;
	}

	public static void transformToPostalAddressBlock(List<PostalAddressDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.individual.PostalAddressBlock> wsList) throws InvalidParameterException {

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

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.PostalAddressBlock transformToPostalAddressBlock(PostalAddressDTO dto) throws InvalidParameterException {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.PostalAddressBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.PostalAddressBlock();
		ws.setPostalAddressContent(transformToPostalAddressContent(dto));
		ws.setPostalAddressProperties(transformToPostalAddressProperties(dto));
		transformToSignature(dto,ws.getSignature());
		//Verify if usuable or not
		transformToUsageAddress(dto.getUsage_mediumdto(), ws.getUsageAddress());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PostalAddressContent transformToPostalAddressContent(PostalAddressDTO dto) {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PostalAddressContent ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PostalAddressContent();

		ws.setAdditionalInformation(replaceCharacterNoPrintable(dto.getScomplement_adresse()));
		ws.setCity(replaceCharacterNoPrintable(dto.getSville()));
		ws.setCorporateName(replaceCharacterNoPrintable(dto.getSraison_sociale()));
		ws.setCountryCode(dto.getScode_pays());
		ws.setDistrict(replaceCharacterNoPrintable(dto.getSlocalite()));
		ws.setNumberAndStreet(replaceCharacterNoPrintable(dto.getSno_et_rue()));
		ws.setStateCode(replaceCharacterNoPrintable(dto.getScode_province()));
		ws.setZipCode(replaceCharacterNoPrintable(dto.getScode_postal()));

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PostalAddressPropertiesV2 transformToPostalAddressProperties(PostalAddressDTO dto) throws InvalidParameterException {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PostalAddressPropertiesV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PostalAddressPropertiesV2();

		if(StringUtils.isNotEmpty(dto.getSforcage())) {
			ws.setIndicAdrNorm(OuiNonFlagEnum.getEnum(dto.getSforcage()).toBoolean());
		}
		ws.setId(dto.getSain());
		ws.setMediumCode(dto.getScode_medium());
		ws.setMediumStatus(dto.getSstatut_medium());
		ws.setVersion(SicStringUtils.getStringFromObject(dto.getVersion()));

		return ws;
	}

	public static void transformToContractBlock(List<RoleContratsDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.individual.ContractBlock> wsList) {

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

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.ContractBlock transformToContractBlock(RoleContratsDTO dto) {

		if (dto == null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.ContractBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.ContractBlock();
		ws.setContract(transformToContract(dto));
		transformToSignature(dto, ws.getSignature());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ContractV3 transformToContract(RoleContratsDTO dto) {

		if (dto == null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ContractV3 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ContractV3();
		ws.setId(dto.getSrin());
		ws.setContractNumber(dto.getNumeroContrat());
		ws.setContractStatus(dto.getEtat());

		ws.setProductSubType(dto.getSousType());
		ws.setProductType(dto.getTypeContrat());

		// REPIND-1263 : We truncate 2 on left because 3 is too huge for WSDL (ROLE_CONTRACT / VERSION)
		ws.setVersion(SicStringUtils.leftTruncate(SicStringUtils.getStringFromObject(dto.getVersion()), 2));

		return ws;
	}

	public static void transformToExternalIdentifierBlock(List<ExternalIdentifierDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierBlock> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(ExternalIdentifierDTO dto : dtoList) {
			wsList.add(transformToExternalIdentifierBlock(dto));
		}

	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierBlock transformToExternalIdentifierBlock(ExternalIdentifierDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierBlock();
		ws.setExternalIdentifier(transformToExternalIdentifier(dto));
		transformToExternalIdentifierDataBlock(dto.getExternalIdentifierDataList(), ws.getExternalIdentifierDataBlock());
		transformToSignature(dto, ws.getSignature());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ExternalIdentifierV2 transformToExternalIdentifier(ExternalIdentifierDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ExternalIdentifierV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ExternalIdentifierV2();
		ws.setId(dto.getIdentifierId().toString());
		ws.setIdentifier(dto.getIdentifier());
		ws.setType(dto.getType());

		return ws;
	}

	public static void transformToExternalIdentifierDataBlock(List<ExternalIdentifierDataDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierDataBlock> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(ExternalIdentifierDataDTO dto : dtoList) {
			wsList.add(transformToExternalIdentifierDataBlock(dto));
		}

	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierDataBlock transformToExternalIdentifierDataBlock(ExternalIdentifierDataDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierDataBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.ExternalIdentifierDataBlock();
		ws.setExternalIdentifierData(transformToExternalIdentifierData(dto));
		transformToSignature(dto, ws.getSignature());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ExternalIdentifierData transformToExternalIdentifierData(ExternalIdentifierDataDTO dto) {

		if (dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ExternalIdentifierData ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.ExternalIdentifierData();
		ws.setKey(dto.getKey());
		ws.setValue(dto.getValue());

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.PreferencesBlock transformToPreferencesBlock(List<PreferenceDTO> listPreferenceDTO) {
		com.afklm.soa.stubs.w001992.v1_0_1.individual.PreferencesBlock pb = null;

		if(listPreferenceDTO != null) {

			for (PreferenceDTO preferenceDTO : listPreferenceDTO) {

				if(preferenceDTO.getType().equals("TPC")) {
					if(preferenceDTO.getPreferenceDataDTO() != null && !preferenceDTO.getPreferenceDataDTO().isEmpty()) {
						for (PreferenceDataDTO preferencedataDTO : preferenceDTO.getPreferenceDataDTO()) {
							if(preferencedataDTO.getKey().equalsIgnoreCase(preferredAirport)) {
								pb = new com.afklm.soa.stubs.w001992.v1_0_1.individual.PreferencesBlock();
								com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PreferenceV2 pref = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PreferenceV2();
								com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PreferenceDatasV2 pds = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PreferenceDatasV2();
								com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PreferenceDataV2 pd = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.PreferenceDataV2();

								pd.setKey(preferredAirport);
								pd.setValue(preferencedataDTO.getValue());

								pds.getPreferenceData().add(pd);

								pref.setPreferenceDatas(pds);
								pref.setId(preferenceDTO.getPreferenceId().toString());
								pref.setType(TPC);

								pb.setPreference(pref);
							}
						}
					}
				}
			}
		}
		return pb;
	}

	public static void transformToCommunicationPreferencesBlock(List<CommunicationPreferencesDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.individual.CommunicationPreferencesBlock> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(CommunicationPreferencesDTO dto : dtoList) {
			com.afklm.soa.stubs.w001992.v1_0_1.individual.CommunicationPreferencesBlock ws = transformToCommunicationPreferencesBlock(dto);
			wsList.add(ws);
		}
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.individual.CommunicationPreferencesBlock transformToCommunicationPreferencesBlock(CommunicationPreferencesDTO dto) {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.individual.CommunicationPreferencesBlock ws = new com.afklm.soa.stubs.w001992.v1_0_1.individual.CommunicationPreferencesBlock();
		ws.setCommunicationPreferences(transformToCommunicationPreferences(dto));

		return ws;
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.CommunicationPreferencesV2 transformToCommunicationPreferences(CommunicationPreferencesDTO dto) {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.CommunicationPreferencesV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.CommunicationPreferencesV2();
		ws.setId(dto.getComPrefId().toString());
		ws.setDomain(dto.getDomain());
		ws.setCommunicationGroupeType(dto.getComGroupType());
		ws.setCommunicationType(dto.getComType());
		ws.setOptIn(dto.getSubscribe());
		ws.setOptInPartner(dto.getOptinPartners());
		ws.setDateOfConsent(dto.getDateOptin());
		ws.setDateOfConsentPartner(dto.getDateOptinPartners());
		ws.setDateOfEntry(dto.getDateOfEntry());
		ws.setSubscriptionChannel(dto.getChannel());

		transformToMarketLanguage(dto.getMarketLanguageDTO(), ws.getMarketLanguage());
		transformToSignature(dto, ws.getSignature());

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.Media media = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.Media();
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

	public static void transformToMarketLanguage(Set<MarketLanguageDTO> dtoList, List<com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.MarketLanguageV2> wsList) {

		if(dtoList==null || dtoList.isEmpty()) {
			return;
		}

		for(MarketLanguageDTO dto : dtoList) {
			com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.MarketLanguageV2 ws = transformToMarketLanguage(dto);
			wsList.add(ws);
		}
	}

	public static com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.MarketLanguageV2 transformToMarketLanguage(MarketLanguageDTO dto) {

		if(dto==null) {
			return null;
		}

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.MarketLanguageV2 ws = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.MarketLanguageV2();
		ws.setId(dto.getMarketLanguageId().toString());
		ws.setDateOfConsent(dto.getDateOfConsent());
		ws.setLanguage(com.afklm.soa.stubs.w001992.v1_0_1.siccommonenum.LanguageCodesEnum.valueOf(dto.getLanguage()));
		ws.setMarket(dto.getMarket());
		ws.setOptIn(dto.getOptIn());

		transformToSignature(dto, ws.getSignature());

		com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.Media media = new com.afklm.soa.stubs.w001992.v1_0_1.sicindividutype.Media();
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
}
