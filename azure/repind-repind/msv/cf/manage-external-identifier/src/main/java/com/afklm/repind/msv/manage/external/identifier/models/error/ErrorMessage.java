package com.afklm.repind.msv.manage.external.identifier.models.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
/*
 * A class containing some data about the different business Error we can throw
 */
public enum ErrorMessage implements IErrorMessage {

    EXTERNAL_IDENTIFIER_ERROR_404("business.error.001", "External Identifier not found"),
    PARAMETER_GIN_INVALID_ERROR_MESSAGE_400("business.error.002", "Gin parameter should have 12 digit or less"),
    MISSING_IDENTIFIER_ERROR_MESSAGE_400("business.error.003","Identifier is missing"),
    MISSING_TYPE_ERROR_MESSAGE_400("business.error.004","Type is missing"),
    WRONG_TYPE_ERROR_MESSAGE_400("business.error.005","Type is wrong");
    private String description;
    private String code;

    ErrorMessage(String code, String description) {
        this.code = code;
        this.description = description;
    }
}