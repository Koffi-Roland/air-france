package com.afklm.repind.utils;

import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.*;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Alert;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.CommunicationPreferences;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.MarketLanguage;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Preference;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateIndividualMapperV7 {
	
	@Mappings ({
		@Mapping(source = "requestor", target  = "requestorDTO"),
		@Mapping(source = "emailRequest", target = "emailRequestDTO"),
		@Mapping(source = "telecomRequest", target = "telecomRequestDTO"),
		@Mapping(source = "postalAddressRequest", target = "postalAddressRequestDTO"),
		@Mapping(source = "individualRequest", target = "individualRequestDTO"),
		@Mapping(source = "externalIdentifierRequest", target = "externalIdentifierRequestDTO"),
		@Mapping(source = "comunicationPreferencesRequest", target = "communicationPreferencesRequestDTO"),
		@Mapping(source = "prefilledNumbersRequest", target = "prefilledNumbersRequestDTO"),
		@Mapping(source = "accountDelegationDataRequest", target = "accountDelegationDataRequestDTO.accountDelegationDataDTO"),
		@Mapping(source = "alertRequest", target = "alertRequestDTO"),
		@Mapping(source = "preferenceDataRequest", target = "preferenceRequestDTO")
	})
	CreateUpdateIndividualRequestDTO wsRequestV7ToCommon(CreateUpdateIndividualRequest ws);
	
	RequestorDTO requestorV7ToCommon(RequestorV2 ws);
	
	@Mapping(source = "email", target = "emailDTO")
	EmailRequestDTO emailRequestV7ToCommon(EmailRequest ws);
	
	@Mapping(source = "telecom", target = "telecomDTO")
	TelecomRequestDTO telRequestV7ToCommon(TelecomRequest ws);
	
	@Mappings ({
		@Mapping(source = "postalAddressProperties", target = "postalAddressPropertiesDTO"),
		@Mapping(source = "postalAddressContent", target = "postalAddressContentDTO"),
		@Mapping(source = "usageAddress", target = "usageAddressDTO")
	})
	PostalAddressRequestDTO adrPostV7ToCommon(PostalAddressRequest ws);
	
	@Mappings ({
		@Mapping(source = "individualInformations", target = "individualInformationsDTO"),
		@Mapping(source = "individualProfil", target = "individualProfilDTO"),
		@Mapping(source = "civilian.titleCode", target = "titleCode")
	})
	IndividualRequestDTO indV7ToCommon(IndividualRequest ws);
	
	@Mappings ({
		@Mapping(source = "externalIdentifier", target = "externalIdentifierDTO"),
		@Mapping(source = "externalIdentifierData", target = "externalIdentifierDataDTO")
	})
	ExternalIdentifierRequestDTO extIdentV7ToCommon(ExternalIdentifierRequest ws);
	
	@Mapping(source = "communicationPreferences", target = "communicationPreferencesDTO")
	CommunicationPreferencesRequestDTO comPrefReqV7ToCommon(ComunicationPreferencesRequest ws);
	
	@Mapping(source = "marketLanguage", target = "marketLanguageDTO")
	CommunicationPreferencesDTO comPrefV7ToCommon(CommunicationPreferences ws);
	
	@Mapping(source = "media", target = "mediaDTO")
	MarketLanguageDTO marketLanguageV7ToCommon(MarketLanguage ws);
	
	@Mapping(source = "prefilledNumbers", target = "prefilledNumbersDTO")
	PrefilledNumbersRequestDTO prefilledNumV7ToCommon(PrefilledNumbersRequest ws);
	
	@Mappings ({
		@Mapping(source = "delegate", target = "delegateDTO"),
		@Mapping(source = "delegator", target = "delegatorDTO")
	})
	AccountDelegationDataDTO delegationDataV7ToCommon(AccountDelegationDataRequest ws);
	
	@Mapping(source = "delegationData", target = "delegateDataDTO")
	DelegateDTO delegateV7ToCommon(Delegate ws);
	
	@Mapping(source = "delegationData", target = "delegatorDataDTO")
	DelegatorDTO delegatorV7ToCommon(Delegator ws);
	
	@Mapping(source = "alert", target = "alertDTO")
	AlertRequestDTO alertReqV7ToCommon(AlertRequest ws);
	
	@Mapping(source = "alertData", target = "alertDataDTO")
	AlertDTO alertV7ToCommon(Alert ws);
	
	@Mapping(source = "preference", target = "preferenceDTO")
	PreferenceRequestDTO prefReqV7ToCommon(PreferenceDataRequest ws);
	
	@Mappings ({
		@Mapping(source = "typePreference", target = "type"),
		@Mapping(source = "preferenceData", target = "preferencesDatasDTO.preferenceDataDTO")
	})
	PreferenceDTO prefV7ToCommon(Preference ws);
	
	CreateModifyIndividualResponseDTO wsResponseV7ToCommon(CreateUpdateIndividualResponse response);
}
