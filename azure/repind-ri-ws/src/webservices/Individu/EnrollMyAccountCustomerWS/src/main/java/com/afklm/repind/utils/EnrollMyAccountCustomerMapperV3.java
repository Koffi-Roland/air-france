package com.afklm.repind.utils;

import com.afklm.soa.stubs.w000431.v3.data.EnrollMyAccountCustomerRequest;
import com.afklm.soa.stubs.w000431.v3.data.EnrollMyAccountCustomerResponse;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EnrollMyAccountCustomerMapperV3 {
	
	@Mappings ({
		@Mapping(source = "languageCode", target  = "language"),
		@Mapping(source = "pointOfSale", target  = "pointOfSell"),
		@Mapping(source = "requestor.channel", target  = "channel"),
		@Mapping(source = "requestor.signature", target  = "signature.signature"),
		@Mapping(source = "requestor.site", target  = "signature.site"),
		@Mapping(source = "requestor.applicationCode", target  = "signature.applicationCode"),
		@Mapping(source = "requestor.ipAddress", target  = "signature.ipAddress")
	})
	MyAccountCustomerRequestDTO wsRequestToMyAccountCommon(EnrollMyAccountCustomerRequest ws);

	EnrollMyAccountCustomerResponse myAccountCommonToWsResponse(MyAccountCustomerResponseDTO common);

}
