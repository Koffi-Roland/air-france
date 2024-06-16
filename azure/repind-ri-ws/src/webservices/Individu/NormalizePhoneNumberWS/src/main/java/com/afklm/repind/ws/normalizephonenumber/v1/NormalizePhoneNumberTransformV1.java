package com.afklm.repind.ws.normalizephonenumber.v1;

import com.afklm.soa.stubs.w001070.v1.data.NormalizePhoneNumberResponse;
import com.airfrance.repind.dto.telecom.NormalizePhoneNumberDTO;

public class NormalizePhoneNumberTransformV1 {

	public static NormalizePhoneNumberResponse dtoToWs(NormalizePhoneNumberDTO dto) {
		
		if(dto==null) {
			return null;
		}
		
		NormalizePhoneNumberResponse ws = new NormalizePhoneNumberResponse();
		
		ws.setNormalizedInternationalCountryCode(dto.getNormalizedInternationalCountryCode());
		ws.setNormalizedInternationalPhoneNumber(dto.getNormalizedInternationalPhoneNumber());
		ws.setNormalizedNationalPhoneNumber(dto.getNormalizedNationalPhoneNumber());
		ws.setNormalizedTerminalType(dto.getNormalizedTerminalType());
		ws.setNormalizedTerminalTypeDetail(dto.getNormalizedTerminalTypeDetail());
		
		return ws;
		
	}
	
}
