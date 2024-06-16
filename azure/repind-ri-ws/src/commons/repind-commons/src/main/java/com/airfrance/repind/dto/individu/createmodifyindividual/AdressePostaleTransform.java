package com.airfrance.repind.dto.individu.createmodifyindividual;

import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.util.SicStringUtils;

import java.util.HashSet;
import java.util.Set;

public class AdressePostaleTransform {
	
	
	
	public static AdressePostaleDTO transformToAdressePostaleDTO(PostalAddressDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		AdressePostaleDTO adh = new AdressePostaleDTO();
		adh.setCleAdresse(dto.getSain());
		adh.setStatutMedium(dto.getSstatut_medium());
		adh.setCodeMedium(dto.getScode_medium());
		adh.setVersion(dto.getVersion());
		adh.setIndicAdrNorm(dto.getSforcage());
		adh.setCodePays(dto.getScode_pays());
		adh.setCodePostal(dto.getScode_postal());
		adh.setCodeProvince(dto.getScode_province());
		adh.setComplAdr(dto.getScomplement_adresse());
		adh.setLieuDit(dto.getSlocalite());
		adh.setNumeroRue(dto.getSno_et_rue());
		adh.setRaisonSociale(dto.getSraison_sociale());
		adh.setVille(dto.getSville());
		adh.setNumeroUsage(transformToNumeroUsage(dto.getUsage_mediumdto()));
		adh.setRoleadresse(transformToUsageMediumDTO(dto.getUsage_mediumdto()));
		
		return adh;
	}
	
	public static Set<RoleAdresseDTO> transformToUsageMediumDTO(Set<Usage_mediumDTO> dtoSet) {
		
		if(dtoSet==null || dtoSet.isEmpty()) {
			return null;
		}
		
		Set<RoleAdresseDTO> adhSet = new HashSet<RoleAdresseDTO>();
		
		for(Usage_mediumDTO dto : dtoSet) {
			adhSet.add(transformToUsageMediumDTO(dto));
		}
		
		return adhSet;
		
	}
	
	public static RoleAdresseDTO transformToUsageMediumDTO(Usage_mediumDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		RoleAdresseDTO adh = new RoleAdresseDTO();
		adh.setCodeRoleAdresse(dto.getSrole1());
		
		return adh;
		
	}
	
	public static String transformToNumeroUsage(Set<Usage_mediumDTO> dtoSet) {
		
		if(dtoSet==null || dtoSet.isEmpty()) {
			return null;
		}
		
		Usage_mediumDTO dto = dtoSet.iterator().next();
		
		return SicStringUtils.getStringFromObject(dto.getInum());
	}
	
}
