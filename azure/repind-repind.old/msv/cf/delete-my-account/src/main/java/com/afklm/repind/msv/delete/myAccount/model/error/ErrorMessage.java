package com.afklm.repind.msv.delete.myAccount.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_400_001("business.400.001" , "Gin is mandatory"),
    ERROR_MESSAGE_403_001("business.403.001" , "Cannot delete as other contract exist"),
    ERROR_MESSAGE_404_001("business.404.001", "Contract not found"),
    ERROR_MESSAGE_412_001("business.412_001" ,"Invalid value for the 'gin' parameter, length must be equal to 12"),;

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}