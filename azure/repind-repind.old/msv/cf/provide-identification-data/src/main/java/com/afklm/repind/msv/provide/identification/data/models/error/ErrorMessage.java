package com.afklm.repind.msv.provide.identification.data.models.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
/*
 * A class containing some data about the different buisness Error we can throw
 */
public enum ErrorMessage implements IErrorMessage {

    ACCOUNT_ERROR_MESSAGE_404("business.error.001", "Account data not found for this gin"),

    DELEGATION_ERROR_MESSAGE_404("business.error.002", "Delegation data not found for this gin"),

    IDENTIFICATION_ERROR_MESSAGE_404("business.error.003", "Identification data not found for this gin"),

    GENERAL_ERROR_MESSAGE_404("business.error.004", "ProvideIdentificationData data not found for this gin"),

    PARAMETER_GIN_INVALID_ERROR_MESSAGE_404("business.error.005", "Gin parameter should have 12 digit or less");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}