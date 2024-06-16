package com.afklm.repind.msv.provide.contract.data.models.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
/*
 * A class containing some data about the different buisness Error we can throw
 */
public enum ErrorMessage implements IErrorMessage {

    CONTRACT_ERROR_MESSAGE_404("business.error.001", "Contract data not found for this gin"),
    MISSING_PARAMETER_403("business.error.002","Type or Identifier of individual is missing/incorrect"),
    PARAMETER_TYPE_INVALID_403("business.error.003","Type must be CIN or GIN only"),
    PARAMETER_CIN_INVALID_403("business.error.004", "Cin parameter is incorrect"),
    PARAMETER_GIN_INVALID_403("business.error.005", "Gin parameter should have 12 digit or less");

    private final String description;
    private final String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}