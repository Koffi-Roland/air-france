package com.afklm.repind.msv.automatic.merge.model.error;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    ERROR_MESSAGE_400("business.400", "Missing Request Parameter"),
    ERROR_MESSAGE_400_001("business.400.001", "Phone is mandatory"),
    ERROR_MESSAGE_412("business.412_004", "Mismatch type parameter"),
    ERROR_MESSAGE_412_001("business.412_001", "Invalid value for the 'phone' parameter, email must be valid"),
    ERROR_MESSAGE_412_002("business.412_002", "Gin or FrequentFlyerNumber must not be empty"),
    ERROR_MESSAGE_412_003("business.412_003", "An individual can not merge himself"),
    ERROR_MESSAGE_412_004("business.412_004", "Gin or frequentFlyerNumber not found"),
    ERROR_MESSAGE_412_005("business.412_005", "FB of first GIN can not be merged"),
    ERROR_MESSAGE_412_006("business.412_006", "FB of second GIN can not be merged"),
    ERROR_MESSAGE_412_007("business.412_007", "FB of first and second GIN can not be merged"),
    ERROR_MESSAGE_412_008("business.412_008", "None of the customers has FB contract"),
    ERROR_MESSAGE_412_009("business.412_009", "A mandatory parameter is missing in input"),
    ERROR_MESSAGE_412_010("business.412_010", "The direction of merge can not be defined automatically"),
    ERROR_MESSAGE_412_011("business.412_011", "Business unknown error"),
    ERROR_MESSAGE_412_012("business.412_012", "Invalid status for merge"),
    ERROR_MESSAGE_412_013("business.412_013", "Too many addresses after merging (Max: 5)"),
    ERROR_MESSAGE_412_014("business.412_014", "Too many GP Roles"),
    ERROR_MESSAGE_412_015("business.412_015", "Gin cible format is invalid"),
    ERROR_MESSAGE_412_016("business.412_016", "Gin source format is invalid"),
    ERROR_MESSAGE_412_017("business.412_016", "An error has occurred during the process"),
    ERROR_MESSAGE_412_018("business.412_016", "An error has occurred independent from the process"),
    ERROR_MESSAGE_SYSTEM_001("api.error.system.001", "Internal server error"),
    ERROR_MESSAGE_SYSTEM_002("api.error.system.002", "No exposed resource for this URI"),
    ERROR_MESSAGE_SYSTEM_003("api.error.system.003", "Handle No Such Request Handling Method"),
    ERROR_MESSAGE_SYSTEM_004("api.error.system.004", "Unsupported content type"),
    ERROR_MESSAGE_SYSTEM_005("api.error.system.005", "Message not readable"),
    ERROR_MESSAGE_SYSTEM_006("api.error.system.006", "System fault from webservice"),
    ERROR_MESSAGE_SYSTEM_007("api.error.system.007", "System unknown error"),
    ERROR_MESSAGE_SYSTEM_008("api.error.system.008", "Unexpected error"),
    ERROR_MESSAGE_SYSTEM_009("api.error.system.009", "Technical unknown error"),
    ERROR_MESSAGE_SYSTEM_010("api.error.system.010", "Can't access to DB");


    private String description;
    private String code;

    ErrorMessage(String code, String description) {
        this.code = code;
        this.description = description;
    }
}