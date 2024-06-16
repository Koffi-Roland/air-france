package com.airfrance.repind.firme.searchfirmonmulticriteria.ds;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.RequestDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.ResponseDTO;



public interface IObtainTokenDS {

	public void readToken(RequestDTO requestDTO, ResponseDTO responseDTO) throws InvalidParameterException;

}
