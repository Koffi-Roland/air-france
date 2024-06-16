package com.afklm.repind.msv.provide.contact.data.models.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    EMAILS_ERROR_MESSAGE_404("business.error.001", "Emails data not found for this gin"),

    POSTAL_ADDRESS_ERROR_MESSAGE_404("business.error.002", "Postal Addresses data not found for this gin"),

    TELECOMS_ERROR_MESSAGE_404("business.error.003", "Telecoms data not found for this gin"),

    GENERAL_ERROR_MESSAGE_404("business.error.004", "No contact data found for this gin"),

    GIN_FORMAT_WRONG_404("business.error.005", "GIN format not correct - should have 12 digits or less");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}
