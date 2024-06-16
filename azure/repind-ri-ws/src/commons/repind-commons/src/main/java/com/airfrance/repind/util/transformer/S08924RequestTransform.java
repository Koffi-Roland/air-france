package com.airfrance.repind.util.transformer;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.GenderEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.SignatureTypeEnum;
import com.airfrance.repind.dto.individu.createmodifyindividual.EmailDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.SignatureDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.*;
import com.airfrance.repind.dto.ws.PostalAddressContentDTO;
import com.airfrance.repind.dto.ws.PostalAddressPropertiesDTO;
import com.airfrance.repind.dto.ws.UsageAddressDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.util.SicDateUtils;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repindutf8.util.SicUtf8StringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class S08924RequestTransform {
	
	private static final String FORCAGE_FAUX = "N";
	private static final String FORCAGE_VRAI = "O";

	public static CreateModifyIndividualResquestDTO transformToCreateModifyIndividualRequestDTO(CreateUpdateIndividualRequestDTO ws) throws InvalidParameterException {
	
		CreateModifyIndividualResquestDTO dto = new CreateModifyIndividualResquestDTO();
	
		dto.setIndividu(transformToInfosIndividuDTO(ws.getIndividualRequestDTO()));
		dto.setHabilitation(null); 
		dto.setRequete(transformToRequeteDTO(ws.getRequestorDTO()));
		dto.setSignature(transformToSignatureDTO(ws.getRequestorDTO(), ws.getIndividualRequestDTO()));
		dto.setTitrecivil(transformToTitreCivilDTO(ws.getIndividualRequestDTO()));
		// REPIND-1023 : remove email CRUD from S08924
//		dto.setEmail(transformToEmailDTO(ws.getEmailRequestDTO()));
		dto.setProfil(transformToProfilAvecCodeFonctionValideDTO(ws.getIndividualRequestDTO()));
		dto.setAdressepostale(transformToAdressePostaleDTO(ws.getPostalAddressRequestDTO()));
		
		dto.setProcess(ws.getProcess());
		
		return dto;
		
	}

	public static SignatureDTO transformToSignatureDTO(RequestorDTO requestor, IndividualRequestDTO individualRequest) {
		
		SignatureDTO dto = new SignatureDTO();

		Date today = new Date();
		
		dto.setDate(SicDateUtils.computeFrenchDate(today));
		dto.setHeure(SicDateUtils.computeHour(today));
		dto.setSignature(requestor.getSignature());
		dto.setSite(requestor.getSite());
		dto.setTypeSignature(transformToTypeSignature(individualRequest));
		
		return dto;
	}
	
	public static String transformToTypeSignature(IndividualRequestDTO ws) {
		
		if(ws==null || ws.getIndividualInformationsDTO()==null || ws.getIndividualInformationsDTO().getIdentifier()==null) {
			return SignatureTypeEnum.CREATION.toString();
		}
		
		return SignatureTypeEnum.MODIFICATION.toString();
	}
	
	public static RequeteDTO transformToRequeteDTO(RequestorDTO ws) {
	
		if (ws==null) {
			return null;
		}
	
		RequeteDTO dto = new RequeteDTO();
		dto.setCodeAppliMetier(ws.getApplicationCode());
		dto.setClefVisa(ws.getToken());
		dto.setContext(ws.getContext());
		
		return dto;
	}

	public static InfosIndividuDTO transformToInfosIndividuDTO(IndividualRequestDTO ws) throws InvalidParameterException {
		
		if(ws==null) {
			return null;
		}
		
		IndividualInformationsDTO infos = ws.getIndividualInformationsDTO();
		
		if(infos==null) {
			return null;
		}
		
		InfosIndividuDTO dto = new InfosIndividuDTO();
		dto.setAliasNom(infos.getLastNamePseudonym());
		dto.setAliasPrenom(infos.getFirstNamePseudonym());
		dto.setAutreNationalite(infos.getSecondNationality());
		dto.setCivilite(infos.getCivility());
		dto.setIdentPerso(infos.getPersonalIdentifier());
		dto.setIndicNonFusion(SicStringUtils.getStringFrenchBoolean(infos.getFlagNoFusion()));
		dto.setIndicNonFusion(SicStringUtils.getStringFrenchBoolean(infos.getFlagThirdTrap()));
		dto.setNationalite(infos.getNationality());
		dto.setNom(infos.getLastNameSC());
		dto.setNomSC(infos.getLastNameSC());
		dto.setNumeroClient(infos.getIdentifier());
		dto.setPrenom(infos.getFirstNameSC());
		dto.setPrenomSC(infos.getFirstNameSC());
		dto.setSecondPrenom(infos.getSecondFirstName());
		dto.setSexe(GenderEnum.getEnum(infos.getGender()).toString());
		dto.setDateNaissance(SicDateUtils.computeFrenchDate(infos.getBirthDate()));
		
		// REPIND-993 : BadDateFormat send to S08924 on 8 char DDMMYYYY
		if(dto.getDateNaissance() != null && dto.getDateNaissance().length() < 8) {
			throw new InvalidParameterException("Invalid date");
		}
		
		dto.setVersion(infos.getVersion());
		dto.setStatut(infos.getStatus());
		
		return dto;
	}
	
	public static TitreCivilDTO transformToTitreCivilDTO(IndividualRequestDTO ws) {
		
		if(ws==null) {
			return null;
		}
		
		String civilian = ws.getTitleCode();
		
		if(civilian==null) {
			return null;
		}
		
		TitreCivilDTO dto = new TitreCivilDTO();
		dto.setCodeTitre(civilian);
		return dto;
	}
	
	public static Set<EmailDTO> transformToEmailDTO(List<EmailRequestDTO> wsList) {
		
		if(wsList==null) {
			return null;
		}
		
		Set<EmailDTO> dtoList = new HashSet<EmailDTO>();
		
		for(EmailRequestDTO ws : wsList) {
			dtoList.add(transformToEmailDTO(ws.getEmailDTO()));
		}
		
		return dtoList;
	}
	
	public static EmailDTO transformToEmailDTO(com.airfrance.repind.dto.ws.EmailDTO ws) {
		
		if(ws==null) {
			return null;
		}
		
		EmailDTO dto = new EmailDTO();
		dto.setAutoriseMailing(ws.getEmailOptin());
		dto.setCodeMedium(ws.getMediumCode());
		dto.setEmail(ws.getEmail());
		
		if(StringUtils.isNotEmpty(ws.getMediumStatus())) {
			dto.setStatutMedium(ws.getMediumStatus());
		} else {
			dto.setStatutMedium(MediumStatusEnum.VALID.toString());
		}
		
		if(StringUtils.isNotEmpty(ws.getVersion())) {
			dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));
		} else {
			dto.setVersion(1);
		}
	
		return dto;
	}

	public static ProfilAvecCodeFonctionValideDTO transformToProfilAvecCodeFonctionValideDTO(IndividualRequestDTO ws) {
		
		if(ws==null) {
			return null;
		}
		
		IndividualProfilDTO profil = ws.getIndividualProfilDTO();
	
		if(profil==null) {
			return null;
		}
		
		ProfilAvecCodeFonctionValideDTO dto = new ProfilAvecCodeFonctionValideDTO();
		dto.setCodeDomainePro(profil.getProAreaCode());
		dto.setCodeEtudiant(profil.getStudentCode());
		dto.setCodeFonctionPro(profil.getProFunctionCode());
		dto.setCodeLangue(profil.getLanguageCode());
		dto.setCodeMarital(profil.getCivilianCode());
		dto.setIndicMailing(profil.getEmailOptin());
		dto.setLibelDomainePro(profil.getProAreaWording());
		dto.setLibelFonctionPro(profil.getProFunctionWording());
		dto.setNbEnfants(SicStringUtils.getIntegerFromString(profil.getChildrenNumber()));
		dto.setSegmentClient(profil.getCustomerSegment());
		
		return dto;
	}
	
	public static Set<AdressePostaleDTO> transformToAdressePostaleDTO(List<PostalAddressRequestDTO> wsList) {
		
		if(wsList==null) {
			return null;
		}
		
		Set<AdressePostaleDTO> dtoList = new HashSet<AdressePostaleDTO>();
		
		for(PostalAddressRequestDTO ws : wsList) {
			dtoList.add(transformToAdressePostaleDTO(ws));
		}
		
		return dtoList;
	}
	
	public static AdressePostaleDTO transformToAdressePostaleDTO(PostalAddressRequestDTO ws) {
		
		if(ws==null) {
			return null;
		}
		
		AdressePostaleDTO dto = new AdressePostaleDTO();
		transformToAdressePostaleDTO(ws.getPostalAddressContentDTO(), dto);
		transformToAdressePostaleDTO(ws.getPostalAddressPropertiesDTO(), dto);
		transformToAdressePostaleDTO(ws.getUsageAddressDTO(), dto);
		dto.setRoleadresse(transformToRoleAdresseDTO(ws.getUsageAddressDTO()));
		
		return dto;
	}
	
	public static void transformToAdressePostaleDTO(PostalAddressContentDTO ws, AdressePostaleDTO dto) {
		
		if(ws==null) {
			return;
		}
		
		// REPIND-1767 : Do not store in SIC database if NonAscii is found
		if (!SicUtf8StringUtils.isNonASCII(ws.getCountryCode())) {
			dto.setCodePays(ws.getCountryCode());
		}
		if (!SicUtf8StringUtils.isNonASCII(ws.getZipCode())) {
			dto.setCodePostal(ws.getZipCode());
		}
		if (!SicUtf8StringUtils.isNonASCII(ws.getStateCode())) {
			dto.setCodeProvince(ws.getStateCode());
		}
		if (!SicUtf8StringUtils.isNonASCII(ws.getAdditionalInformation())) {
			dto.setComplAdr(ws.getAdditionalInformation());
		}
		if (!SicUtf8StringUtils.isNonASCII(ws.getDistrict())) {
			dto.setLieuDit(ws.getDistrict());
		}
		if (!SicUtf8StringUtils.isNonASCII(ws.getNumberAndStreet())) {
			dto.setNumeroRue(ws.getNumberAndStreet());
		}
		if (!SicUtf8StringUtils.isNonASCII(ws.getCorporateName())) {
			dto.setRaisonSociale(ws.getCorporateName());
		}
		if (!SicUtf8StringUtils.isNonASCII(ws.getCity())) {
			dto.setVille(ws.getCity());
		}
	}
	
	public static void transformToAdressePostaleDTO(PostalAddressPropertiesDTO ws, AdressePostaleDTO dto) {
		
		if(ws==null) {
			return;
		}
		
		dto.setCodeMedium(ws.getMediumCode());
		dto.setStatutMedium(ws.getMediumStatus());
		dto.setVersion(SicStringUtils.getIntegerFromString(ws.getVersion()));
		
		if (ws.getIndicAdrNorm() != null
				&& ws.getIndicAdrNorm())
			dto.setIndicAdrNorm(FORCAGE_VRAI);
		else
			dto.setIndicAdrNorm(FORCAGE_FAUX);
				
	}
	
	public static void transformToAdressePostaleDTO(UsageAddressDTO ws, AdressePostaleDTO dto) {
		
		if(ws==null) {
			return;
		}
		
		if(ws.getUsageNumber()!=null) {
			dto.setNumeroUsage(ws.getUsageNumber());
		}
		
	}
	
	public static Set<RoleAdresseDTO> transformToRoleAdresseDTO(UsageAddressDTO ws) {
		
		if(ws==null) {
			return null;
		}
		
		RoleAdresseDTO dto = new RoleAdresseDTO();
		dto.setCodeRoleAdresse(ws.getAddressRoleCode());

		Set<RoleAdresseDTO> dtoSet = new HashSet<RoleAdresseDTO>();
		dtoSet.add(dto);
		
		return dtoSet;
	}
	
}
