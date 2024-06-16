package com.airfrance.repind.firme.searchfirmonmulticriteria.dao;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.SearchResultDTO;

import java.util.List;



/**
 * DAO Layer interface
 * @author t950700
 *
 */
public interface ISearchFirmDAO {
	
	public List<SearchResultDTO> searchGinsAndTypes(RequestDTO requestDTO) throws BusinessException;
	
	public FirmDTO readFirmByGin(RequestDTO requestDTO, String gin) throws BusinessException; 
}
