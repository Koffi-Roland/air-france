package com.airfrance.repind.dto.ws;

import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.profil.ProfilsDTO;
import com.airfrance.repind.util.NormalizedStringUtilsV2;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;


public class IndividualRequestTransform {

	public static IndividuDTO transformIndividualRequestFromWsToIndividuDTO(IndividuDTO individuDTO, IndividualRequestDTO individualRequestDTO) {
		if(individualRequestDTO == null) {
			return individuDTO;
		}
		
		if (individuDTO == null) {
			individuDTO = new IndividuDTO();
		}
		
		IndividualInformationsDTO indInfoDTO = individualRequestDTO.getIndividualInformationsDTO();
		IndividualProfilDTO indProfilDTO = individualRequestDTO.getIndividualProfilDTO();
		
		if (indInfoDTO != null) {
			if (StringUtils.isNotEmpty(indInfoDTO.getIdentifier())) {
				individuDTO.setSgin(indInfoDTO.getIdentifier());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getVersion())) {
				individuDTO.setVersion(SicStringUtils.getIntegerFromString(indInfoDTO.getVersion()));
			}
			else {
				individuDTO.setVersion(0);
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getCivility())) {
				individuDTO.setCivilite(indInfoDTO.getCivility());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getLastNameSC())) {
				individuDTO.setNomSC(indInfoDTO.getLastNameSC());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getMiddleNameSC())) {
				individuDTO.setNomSC(individuDTO.getNomSC() + " " + indInfoDTO.getMiddleNameSC());
			}
			if (StringUtils.isNotEmpty(individuDTO.getNomSC())) {
				individuDTO.setNom(NormalizedStringUtilsV2.normalizeName(individuDTO.getNomSC()));
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getLastNamePseudonym())) {
				individuDTO.setAlias(indInfoDTO.getLastNamePseudonym());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getFirstNameSC())) {
				individuDTO.setPrenom(NormalizedStringUtilsV2.normalizeName(indInfoDTO.getFirstNameSC()));
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getFirstNameSC())) {
				individuDTO.setPrenomSC(indInfoDTO.getFirstNameSC());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getFirstNamePseudonym())) {
				individuDTO.setAliasPrenom(indInfoDTO.getFirstNamePseudonym());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getSecondFirstName())) {
				individuDTO.setSecondPrenom(indInfoDTO.getSecondFirstName());
			}	
			if (StringUtils.isNotEmpty(indInfoDTO.getGender())) {
				individuDTO.setSexe(indInfoDTO.getGender());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getPersonalIdentifier())) {
				individuDTO.setIdentifiantPersonnel(indInfoDTO.getPersonalIdentifier());
			}
			if (indInfoDTO.getBirthDate() != null) {
				individuDTO.setDateNaissance(indInfoDTO.getBirthDate());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getStatus())) {
				individuDTO.setStatutIndividu(indInfoDTO.getStatus());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getNationality())) {
				individuDTO.setNationalite(indInfoDTO.getNationality());
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getSecondNationality())) {
				individuDTO.setAutreNationalite(indInfoDTO.getSecondNationality());
			}
			if (indInfoDTO.getFlagNoFusion() != null && indInfoDTO.getFlagNoFusion()) {
				individuDTO.setNonFusionnable("O");
			}
			else {
				individuDTO.setNonFusionnable("N");
			}
			if (indInfoDTO.getFlagThirdTrap() != null && indInfoDTO.getFlagThirdTrap()) {
				individuDTO.setTierUtiliseCommePiege("Y");
			}
			else {
				individuDTO.setTierUtiliseCommePiege("N");
			}
			if (StringUtils.isNotEmpty(indInfoDTO.getLanguageCode())) {
				individuDTO.setCodeLangue(indInfoDTO.getLanguageCode());
			}
		}
		
		if (indProfilDTO != null) {
			if (StringUtils.isNotEmpty(indProfilDTO.getCarrierCode())) {
				individuDTO.setCieGest(indProfilDTO.getCarrierCode());
			}
			if (StringUtils.isNotEmpty(indProfilDTO.getLanguageCode())) {
				individuDTO.setCodeLangue(indProfilDTO.getLanguageCode());
			}
		}
		
		if (StringUtils.isNotEmpty(individualRequestDTO.getTitleCode())) {
			individuDTO.setCodeTitre(individualRequestDTO.getTitleCode());
		}
		
		return individuDTO;
	}

	public static void transformProfilRequestToProfilDTO(String sGin, ProfilsDTO profil, IndividualProfilDTO individualProfilDTO) {
		
		if (profil == null) {
			profil = new ProfilsDTO();
		}

		profil.setSgin(sGin);
		
		if (profil.getIversion() == null) {
			profil.setIversion(1);
		}
		else {
			profil.setIversion(profil.getIversion() + 1);
		}
		
		if (StringUtils.isEmpty(profil.getSrin())) {
			profil.setSrin("1");
		}
		
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getEmailOptin())) {
			profil.setSmailing_autorise(individualProfilDTO.getEmailOptin());
		}
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getProFunctionWording())) {
			profil.setScode_professionnel(individualProfilDTO.getProFunctionWording());
		}
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getCivilianCode())) {
			profil.setScode_maritale(individualProfilDTO.getCivilianCode());
		}
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getLanguageCode())) {
			profil.setScode_langue(individualProfilDTO.getLanguageCode());
		}
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getProFunctionCode())) {
			profil.setScode_fonction(individualProfilDTO.getProFunctionCode());
		}
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getChildrenNumber())) {
			profil.setInb_enfants(Integer.valueOf(individualProfilDTO.getChildrenNumber()));
		}
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getCustomerSegment())) {
			profil.setSsegment(individualProfilDTO.getCustomerSegment());
		}
		if (individualProfilDTO != null && StringUtils.isNotEmpty(individualProfilDTO.getStudentCode())) {
			profil.setSetudiant(individualProfilDTO.getStudentCode());
		}
	}
}
