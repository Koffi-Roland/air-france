package com.afklm.rigui.dto.ws;

import com.afklm.rigui.dto.individu.IndividuDTO;
import com.afklm.rigui.dto.individu.createmodifyindividual.InfosIndividuDTO;
import com.afklm.rigui.dto.individu.createmodifyindividual.ProfilAvecCodeFonctionValideDTO;
import com.afklm.rigui.dto.profil.ProfilsDTO;
import com.afklm.rigui.util.SicDateUtils;

public class IndividualResponseTransform {
	
	public static InfosIndividuDTO transformIndividuDtoToInfosIndividuDTO(IndividuDTO individual) {
		if (individual == null) {
			return null;
		}
		
		InfosIndividuDTO info = new InfosIndividuDTO();
		
		info.setAliasNom(individual.getAlias());
		info.setAliasPrenom(individual.getAliasPrenom());
		info.setAutreNationalite(individual.getAutreNationalite());
		info.setCieGestionnaire(individual.getCieGest());
		info.setCivilite(individual.getCivilite());
		if (individual.getDateFusion() != null) {
			info.setDateFusion(SicDateUtils.dateToString(individual.getDateFusion()));
		}
		if (individual.getDateNaissance() != null) {
			info.setDateNaissance(SicDateUtils.dateToString(individual.getDateNaissance()));
		}
		info.setGinFusion(individual.getGinFusion());
		info.setIdentPerso(individual.getIdentifiantPersonnel());
		info.setIndicFraudBanq(individual.getFraudeurCarteBancaire());
		info.setIndicNonFusion(individual.getNonFusionnable());
		info.setIndicTiersPiege(individual.getTierUtiliseCommePiege());
		info.setMotDePasse(individual.getMotDePasse());
		info.setNationalite(individual.getNationalite());
		info.setNom(individual.getNom());
		info.setNomSC(individual.getNomSC());
		info.setNumeroClient(individual.getSgin());
		info.setPrenom(individual.getPrenom());
		info.setPrenomSC(individual.getPrenomSC());
		info.setSecondPrenom(individual.getSecondPrenom());
		info.setSexe(individual.getSexe());
		info.setStatut(individual.getStatutIndividu());
		if (individual.getVersion() != null) {
			info.setVersion(Integer.toString(individual.getVersion()));
		}
		
		return info;
	}

	public static ProfilAvecCodeFonctionValideDTO transformProfilDtoToProfilAvecCodeFonctionValideDTO(ProfilsDTO profil) {
		if (profil == null) {
			return null;
		}
		
		ProfilAvecCodeFonctionValideDTO response = new ProfilAvecCodeFonctionValideDTO();
		
		response.setCleProfil(Integer.valueOf(profil.getSrin()));
		response.setCodeDomainePro(profil.getScode_professionnel());
		response.setCodeEtudiant(profil.getSetudiant());
		response.setCodeFonctionPro(profil.getScode_fonction());
		response.setCodeLangue(profil.getScode_langue());
		response.setCodeMarital(profil.getScode_maritale());
		response.setIndicMailing(profil.getSmailing_autorise());
		response.setIndicSolvabilite(profil.getSsolvabilite());
		response.setNbEnfants(profil.getInb_enfants());
		response.setSegmentClient(profil.getSsegment());
		
		return response;
	}
}
