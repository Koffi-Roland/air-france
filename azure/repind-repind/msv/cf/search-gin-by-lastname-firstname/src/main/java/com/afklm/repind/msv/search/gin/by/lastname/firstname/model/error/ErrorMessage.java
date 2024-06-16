package com.afklm.repind.msv.search.gin.by.lastname.firstname.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_412_001("business.412_001" ,"Invalid value for the 'lastname' parameter, lastname must be valid"),
    ERROR_MESSAGE_412_002("business.412_002" ,"Invalid value for the 'firstname' parameter, firstname must be valid");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}