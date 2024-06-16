package com.afklm.repind.common.model.error;

import lombok.Getter;

@Getter
public enum ApiErrorMessage implements IErrorMessage {
    ERROR_MESSAGE_400("business.400" , "Missing Request Parameter"),
    ERROR_MESSAGE_412("business.412_004" ,"Mismatch type parameter");

    private String description;
    private String code;

    ApiErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}