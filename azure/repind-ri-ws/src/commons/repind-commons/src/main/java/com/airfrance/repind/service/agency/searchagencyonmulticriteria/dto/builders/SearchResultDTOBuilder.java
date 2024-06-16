package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.builders;

import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.SearchResultDTO;
import org.springframework.stereotype.Component;

@Component("AgencySearchResultDTOBuilder")
public class SearchResultDTOBuilder {

	
	/*===============================================*/
	/*               PUBLIC METHODS                  */
	/*===============================================*/
	
	public SearchResultDTO build(Agence agence)
	{
		SearchResultDTO searchResultDTO = null;
		
		if(agence != null)
		{
			if(agence.getGin() != null)
			{
				searchResultDTO = new SearchResultDTO();
				searchResultDTO.setGin(agence.getGin());
			}
			
		}
		return searchResultDTO;
	}
}
