package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.builders;

import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.AgencyDTO;
import com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto.ResponseDTO;

import java.util.ArrayList;
import java.util.List;



/**
 * Builder for ResponseDTO
 * @author t950700
 *
 */
public class ResponseDTOBuilder {
	public static ResponseDTO build()
	{
		ResponseDTO responseDTO = new ResponseDTO();
		List<AgencyDTO> agencies = new ArrayList<AgencyDTO>();
		responseDTO.setAgencies(agencies);
		return responseDTO;
	}
}
