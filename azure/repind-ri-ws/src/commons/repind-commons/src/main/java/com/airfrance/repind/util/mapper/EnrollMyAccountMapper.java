package com.airfrance.repind.util.mapper;

import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EnrollMyAccountMapper {
	
	@Mappings ({
		@Mapping(source = "civility", target = "individualRequestDTO.individualInformationsDTO.civility"),
		@Mapping(source = "gin", target = "individualRequestDTO.individualInformationsDTO.identifier"),
		@Mapping(source = "firstName", target = "individualRequestDTO.individualInformationsDTO.firstNameSC"),
		@Mapping(source = "lastName", target = "individualRequestDTO.individualInformationsDTO.lastNameSC"),
		@Mapping(source = "language", target = "individualRequestDTO.individualInformationsDTO.languageCode"),
		@Mapping(source = "channel", target = "requestorDTO.channel"),
		@Mapping(source = "signature.site", target = "requestorDTO.site"),
		@Mapping(source = "signature.signature", target = "requestorDTO.signature"),
		@Mapping(source = "signature.ipAddress", target = "requestorDTO.ipAddress"),
		@Mapping(source = "signature.applicationCode", target = "requestorDTO.applicationCode")
	})
	CreateUpdateIndividualRequestDTO wsRequestToIndividualCommon(MyAccountCustomerRequestDTO ws);
	
}
