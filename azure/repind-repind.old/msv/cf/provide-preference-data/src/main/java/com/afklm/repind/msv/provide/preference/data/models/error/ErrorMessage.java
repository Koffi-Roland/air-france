package com.afklm.repind.msv.provide.preference.data.models.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    COMM_PREFS_ERROR_MESSAGE_404("business.error.001", "Communication preferences not found for this gin"),

    PREFS_ERROR_MESSAGE_404("business.error.002", "Preferences not found for this gin"),

    GENERAL_ERROR_MESSAGE_404("business.error.003",
            "No Communication preferences or Preferences found for this gin"),

    GIN_FORMAT_WRONG_404("business.error.004", "GIN format not correct - should have 12 digits or less");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}
