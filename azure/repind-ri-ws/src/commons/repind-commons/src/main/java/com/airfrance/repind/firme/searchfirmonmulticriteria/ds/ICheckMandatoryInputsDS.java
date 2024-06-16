package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.BusinessException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;


public interface ICheckMandatoryInputsDS {
	public void checkMandatoryInputs(RequestDTO requestDTO) throws BusinessException;
}
