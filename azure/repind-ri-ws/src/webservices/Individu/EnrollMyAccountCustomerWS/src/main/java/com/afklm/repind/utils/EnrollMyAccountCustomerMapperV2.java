package com.afklm.repind.utils;

import com.afklm.soa.stubs.w000431.v2.data.EnrollMyAccountCustomerRequest;
import com.afklm.soa.stubs.w000431.v2.data.EnrollMyAccountCustomerResponse;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EnrollMyAccountCustomerMapperV2 {
	
	@Mappings ({
		@Mapping(source = "languageCode", target  = "language"),
		@Mapping(source = "pointOfSale", target  = "pointOfSell"),
		@Mapping(source = "signatureLight.signature", target  = "signature.signature"),
		@Mapping(source = "signatureLight.site", target  = "signature.site"),
		@Mapping(source = "signatureLight.ipAddress", target  = "signature.ipAddress")
	})
	MyAccountCustomerRequestDTO wsRequestToMyAccountCommon(EnrollMyAccountCustomerRequest ws);

	EnrollMyAccountCustomerResponse myAccountCommonToWsResponse(MyAccountCustomerResponseDTO common);

}
