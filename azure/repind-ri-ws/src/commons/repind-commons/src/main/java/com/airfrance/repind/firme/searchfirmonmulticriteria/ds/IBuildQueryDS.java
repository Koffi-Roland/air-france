package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;


public interface IBuildQueryDS 
{
	public void buildQuery(RequestDTO requestDTO) throws BusinessException;

}
