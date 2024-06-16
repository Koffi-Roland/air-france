package com.afklm.repind.msv.search.gin.by.phone.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_400_001("business.400.001" , "Phone is mandatory"),
    ERROR_MESSAGE_412_001("business.412_001" ,"Invalid value for the 'phone' parameter, email must be valid");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}