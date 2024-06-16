package com.afklm.repind.common.model.error;

import lombok.Getter;

@Getter
public enum FormatErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_400_001("business.400.001" , "Email is mandatory"),
    ERROR_MESSAGE_400_002("business.400.002" , "CIN is mandatory"),
    ERROR_MESSAGE_412_001("business.412_001" ,"Invalid value for the 'email' parameter, email must be valid"),
    ERROR_MESSAGE_412_002("business.412_002" ,"Invalid value for the 'cin' parameter, CIN should be between 10 and 12 characters and only contain numbers");

    private String description;
    private String code;

    FormatErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}