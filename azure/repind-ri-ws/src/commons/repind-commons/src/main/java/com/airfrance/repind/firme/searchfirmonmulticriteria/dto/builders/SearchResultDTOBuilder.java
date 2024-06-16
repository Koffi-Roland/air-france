package com.airfrance.repind.firme.searchfirmonmulticriteria.dto.builders;

import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmTypeEnum;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.SearchResultDTO;
import org.springframework.stereotype.Component;

@Component
public class SearchResultDTOBuilder {


	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	/**
	 * Builds SearchResultDTO from PersonneMorale entity
	 */
	public SearchResultDTO build(PersonneMorale personneMorale)
	{
		SearchResultDTO searchResultDTO = null;
		
		if(personneMorale != null)
		{
			if(personneMorale.getGin() != null)
			{
				searchResultDTO = new SearchResultDTO();
				searchResultDTO.setGin(personneMorale.getGin());
				
				if(personneMorale.getClass().equals(Groupe.class))
				{
					searchResultDTO.setFirmType(FirmTypeEnum.GROUP);
				}
				else if(personneMorale.getClass().equals(Entreprise.class))
				{
					searchResultDTO.setFirmType(FirmTypeEnum.COMPANY);
				}
				else if(personneMorale.getClass().equals(Etablissement.class))
				{
					searchResultDTO.setFirmType(FirmTypeEnum.FIRM);
				}
				else if(personneMorale.getClass().equals(Service.class))
				{
					searchResultDTO.setFirmType(FirmTypeEnum.SERVICE);
				}
				else
				{
					searchResultDTO.setFirmType(FirmTypeEnum.FIRM);
				}
			}
			
		}
		return searchResultDTO;
	}
}
