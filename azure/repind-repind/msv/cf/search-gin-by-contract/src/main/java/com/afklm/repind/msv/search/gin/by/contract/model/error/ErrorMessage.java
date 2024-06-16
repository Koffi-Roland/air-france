package com.afklm.repind.msv.search.gin.by.contract.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_400("business.400" , "Missing Request Parameter"),
    ERROR_MESSAGE_400_001("business.400.001" , "Contract is mandatory"),
    ERROR_MESSAGE_412("business.412_004" ,"Mismatch type parameter");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}