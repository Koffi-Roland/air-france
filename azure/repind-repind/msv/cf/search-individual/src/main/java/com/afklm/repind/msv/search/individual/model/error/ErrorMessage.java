package com.afklm.repind.msv.search.individual.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_400("business.400", "Missing Request Parameter. At least a parameter must be provided : gin or gins or cin or email or internationalPhoneNumber or socialIdentifier with socialMedia or lastname with firstname"),
    ERROR_MESSAGE_412("business.412", "Mismatch type parameter"),
    ERROR_MESSAGE_412_001("business.412_001" ,"Invalid value for the 'gin' parameter, gin must be valid"),
    ERROR_MESSAGE_412_002("business.412_002" ,"Invalid value for the 'cin' parameter, cin must be valid"),
    ERROR_MESSAGE_412_003("business.412_003" ,"Invalid value for the 'email' parameter, email must be valid"),
    ERROR_MESSAGE_412_004("business.412_004", "Invalid value for the 'internationalPhoneNumber' parameter, internationalPhoneNumber must be valid"),
    ERROR_MESSAGE_412_005("business.412_005" ,"Invalid value for the 'External Identifier' parameter, external identifier must be valid"),
    ERROR_MESSAGE_412_006("business.412_006" ,"Invalid value for the 'External Type' parameter, external type must be valid"),
    ERROR_MESSAGE_412_007("business.412_007" ,"Invalid value for the 'Lastname' parameter, Lastname must be valid"),
    ERROR_MESSAGE_412_008("business.412_008" ,"Invalid value for the 'Firstname' parameter, Firstname must be valid"),

    ERROR_MESSAGE_SYSTEM_001("api.error.system.001" , "Internal server error"),
    ERROR_MESSAGE_SYSTEM_002("api.error.system.002" , "No exposed resource for this URI"),
    ERROR_MESSAGE_SYSTEM_003("api.error.system.003" , "Handle No Such Request Handling Method"),
    ERROR_MESSAGE_SYSTEM_004("api.error.system.004" , "Unsupported content type"),
    ERROR_MESSAGE_SYSTEM_005("api.error.system.005" , "Message not readable"),
    ERROR_MESSAGE_SYSTEM_006("api.error.system.006" , "System fault from webservice"),
    ERROR_MESSAGE_SYSTEM_007("api.error.system.007" , "System unknown error"),
    ERROR_MESSAGE_SYSTEM_008("api.error.system.008" , "Unexpected error");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}