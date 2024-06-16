package com.afklm.repind.utils;

import com.afklm.soa.stubs.w000431.v1.enrollmyaccountcustomeryype.EnrollMyAccountCustomerRequest;
import com.afklm.soa.stubs.w000431.v1.enrollmyaccountcustomeryype.EnrollMyAccountCustomerResponse;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EnrollMyAccountCustomerMapperV1 {
	
	@Mappings ({
		@Mapping(source = "languageCode", target  = "language"),
		@Mapping(source = "pointOfSale", target  = "pointOfSell"),
		@Mapping(source = "signature.signature", target  = "signature.signature"),
		@Mapping(source = "signature.site", target  = "signature.site"),
		@Mapping(source = "signature.ipAddress", target  = "signature.ipAddress")
	})
	MyAccountCustomerRequestDTO wsRequestToMyAccountCommon(EnrollMyAccountCustomerRequest ws);

	EnrollMyAccountCustomerResponse myAccountCommonToWsResponse(MyAccountCustomerResponseDTO common);

}
