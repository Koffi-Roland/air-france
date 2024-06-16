package com.afklm.repind.msv.manage.individual.identifier.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_400_382("382", "Email already used by flying blue members"),
    ERROR_MESSAGE_404_001("001", "GIN not found");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}