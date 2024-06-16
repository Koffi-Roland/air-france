package com.airfrance.repind.firme.searchfirmonmulticriteria.dto.builders;

import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.FirmDTO;
import com.airfrance.repind.firme.searchfirmonmulticriteria.dto.ResponseDTO;

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
		List<FirmDTO> firms = new ArrayList<FirmDTO>();
		responseDTO.setFirms(firms);
		return responseDTO;
	}
}
