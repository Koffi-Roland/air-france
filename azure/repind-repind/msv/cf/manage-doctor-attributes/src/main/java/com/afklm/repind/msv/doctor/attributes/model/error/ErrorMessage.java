package com.afklm.repind.msv.doctor.attributes.model.error;

import lombok.Getter;

public enum ErrorMessage {

	ERROR_MESSAGE_400("business.400" , "Missing Request Parameter"),
	ERROR_MESSAGE_400_001("business.400.001" , "Gin is mandatory"),
	ERROR_MESSAGE_400_002("business.400.002" , "Type is mandatory"),
	ERROR_MESSAGE_400_004("business.400.004" , "Signature Site is mandatory"),
	ERROR_MESSAGE_400_005("business.400.005" , "Signature Source is mandatory"),
	ERROR_MESSAGE_400_006("business.400.006" , "Role id is mandatory"),
	ERROR_MESSAGE_400_007("business.400.007" , "Doctor id is mandatory"),
	ERROR_MESSAGE_400_008("business.400.008" , "Air line code is mandatory"),
	ERROR_MESSAGE_400_009("business.400.009" , "Doctor status is mandatory"),
	ERROR_MESSAGE_400_010("business.400.010" , "Relation type is mandatory"),
	ERROR_MESSAGE_400_011("business.400.011" , "Relation value is mandatory"),
	ERROR_MESSAGE_400_012("business.400.012" , "Relation list is mandatory"),
	ERROR_MESSAGE_400_014("business.400.014" , "Speciality is mandatory"),
	ERROR_MESSAGE_403_001("business.403.001" , "Individu must have Flying blue contract to has doctor role"),
	ERROR_MESSAGE_403_002("business.403.002" , "Gin provided does not match with the gin's doctor role"),
	ERROR_MESSAGE_403_003("business.403.003" , "Cannot update/retrieve/delete role with Suppressed Status"),
	ERROR_MESSAGE_403_004("business.403.004" , "Cannot update/retrieve attributes with suppressed role"),
	ERROR_MESSAGE_404_001("business.404.001" , "Gin not found"),
	ERROR_MESSAGE_404_003("business.404.003" ,"Role not found"),
	ERROR_MESSAGE_409_001("business.409_001" ,"Role already exists"),
	ERROR_MESSAGE_409_002("business.409_002" ,"Doctor id already exists"),
	ERROR_MESSAGE_412_001("business.412_001" ,"Invalid value for the 'gin' parameter, length must be equal to 12"),
	ERROR_MESSAGE_412_002("business.412_002" ,"Invalid value for the 'type' parameter, length must be equal to 1"),
	ERROR_MESSAGE_412_003("business.412_003" ,"Invalid value for the 'endDateRole' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"),
	ERROR_MESSAGE_412_004("business.412_004" ,"Invalid value for the 'doctorId' parameter, length must be equal to 20"),
	ERROR_MESSAGE_412_005("business.412_005" ,"Invalid value for the 'airLineCode' parameter, value must be 'AF' or 'KL'"),
	ERROR_MESSAGE_412_006("business.412_006" ,"Invalid value for the 'doctorStatus' parameter, value must be 'V' (Valid)"),
	ERROR_MESSAGE_412_007("business.412_007" ,"Invalid value for the 'type' of relation model"),
	ERROR_MESSAGE_412_008("business.412_008" ,"Invalid value for the 'values' of relation model"),
	ERROR_MESSAGE_412_009("business.412_009" ,"Incorrect number of type values has been sent"),
	ERROR_MESSAGE_412_010("business.412_010" ,"Invalid value for the 'signature Date' parameter, format must be equal to yyyy-MM-dd'T'HH:mm:ss'Z'"),
	ERROR_MESSAGE_412_011("business.412_011" ,"Incorrect value of speciality has been sent"),
	ERROR_MESSAGE_412_012("business.412_012" ,"Invalid number of specialities values has been sent, must be 1"),
	ERROR_MESSAGE_412_013("business.412_013" ,"Incorrect value of 'signatureSource' parameter, value must be 'CAPI' or 'CBS'"),
	ERROR_MESSAGE_412("business.412_004" ,"Mismatch type parameter"),
	ERROR_MESSAGE_SYSTEM_001("api.error.system.001" , "Internal server error"),
	ERROR_MESSAGE_SYSTEM_002("api.error.system.002" , "No exposed resource for this URI"),
	ERROR_MESSAGE_SYSTEM_003("api.error.system.003" , "Handle No Such Request Handling Method"),
	ERROR_MESSAGE_SYSTEM_004("api.error.system.004" , "Unsupported content type"),
	ERROR_MESSAGE_SYSTEM_005("api.error.system.005" , "Message not readable"),
	ERROR_MESSAGE_SYSTEM_006("api.error.system.006" , "System fault from webservice"),
	ERROR_MESSAGE_SYSTEM_007("api.error.system.007" , "System unknown error"),
	ERROR_MESSAGE_SYSTEM_008("api.error.system.008" , "Unexpected error");

	@Getter private String description;
	@Getter private String code;

	ErrorMessage(String code , String description) {
		this.code = code;
		this.description = description;
	}
}
