package com.afklm.repind.common.model.error;

import lombok.Getter;

@Getter
public enum SystemErrorMessage implements IErrorMessage {
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

    SystemErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}