package com.afklm.repind.utils;

import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.request.*;
import com.afklm.soa.stubs.w000442.v6.siccommontype.Requestor;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.CommunicationPreferences;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.MarketLanguage;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateIndividualMapperV6 {
	
	@Mappings ({
		@Mapping(source = "requestor", target  = "requestorDTO"),
		@Mapping(source = "emailRequest", target = "emailRequestDTO"),
		@Mapping(source = "telecomRequest", target = "telecomRequestDTO"),
		@Mapping(source = "postalAddressRequest", target = "postalAddressRequestDTO"),
		@Mapping(source = "individualRequest", target = "individualRequestDTO"),
		@Mapping(source = "externalIdentifierRequest", target = "externalIdentifierRequestDTO"),
		@Mapping(source = "comunicationPreferencesRequest", target = "communicationPreferencesRequestDTO"),
		@Mapping(source = "prefilledNumbersRequest", target = "prefilledNumbersRequestDTO"),
		@Mapping(source = "accountDelegationDataRequest", target = "accountDelegationDataRequestDTO.accountDelegationDataDTO")
	})
	CreateUpdateIndividualRequestDTO wsRequestToCommon(CreateUpdateIndividualRequest ws);
	
	RequestorDTO requestorToCommon(Requestor ws);
	
	@Mapping(source = "email", target = "emailDTO")
	EmailRequestDTO emailRequestToCommon(EmailRequest ws);
	
	@Mapping(source = "telecom", target = "telecomDTO")
	TelecomRequestDTO telRequestToCommon(TelecomRequest ws);
	
	@Mappings ({
		@Mapping(source = "postalAddressProperties", target = "postalAddressPropertiesDTO"),
		@Mapping(source = "postalAddressContent", target = "postalAddressContentDTO"),
		@Mapping(source = "usageAddress", target = "usageAddressDTO")
	})
	PostalAddressRequestDTO adrPostToCommon(PostalAddressRequest ws);
	
	@Mappings ({
		@Mapping(source = "individualInformations", target = "individualInformationsDTO"),
		@Mapping(source = "individualProfil", target = "individualProfilDTO"),
		@Mapping(source = "civilian.titleCode", target = "titleCode")
	})
	IndividualRequestDTO indToCommon(IndividualRequest ws);
	
	@Mappings ({
		@Mapping(source = "externalIdentifier", target = "externalIdentifierDTO"),
		@Mapping(source = "externalIdentifierData", target = "externalIdentifierDataDTO")
	})
	ExternalIdentifierRequestDTO extIdentToCommon(ExternalIdentifierRequest ws);
	
	@Mapping(source = "communicationPreferences", target = "communicationPreferencesDTO")
	CommunicationPreferencesRequestDTO comPrefReqToCommon(ComunicationPreferencesRequest ws);
	
	@Mapping(source = "marketLanguage", target = "marketLanguageDTO")
	CommunicationPreferencesDTO comPrefV7ToCommon(CommunicationPreferences ws);
	
	@Mapping(source = "media", target = "mediaDTO")
	MarketLanguageDTO marketLanguageToCommon(MarketLanguage ws);
	
	@Mapping(source = "prefilledNumbers", target = "prefilledNumbersDTO")
	PrefilledNumbersRequestDTO prefilledNumToCommon(PrefilledNumbersRequest ws);
	
	@Mappings ({
		@Mapping(source = "delegate", target = "delegateDTO"),
		@Mapping(source = "delegator", target = "delegatorDTO")
	})
	AccountDelegationDataDTO delegationDataToCommon(AccountDelegationDataRequest ws);
	
	@Mapping(source = "delegationData", target = "delegateDataDTO")
	DelegateDTO delegateToCommon(Delegate ws);
	
	@Mapping(source = "delegationData", target = "delegatorDataDTO")
	DelegatorDTO delegatorToCommon(Delegator ws);
	
	CreateModifyIndividualResponseDTO wsResponseToCommon(CreateUpdateIndividualResponse response);
}
