package com.afklm.repind.msv.provide.last.activity.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Error message base on business error
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorMessage implements IErrorMessage {

    ERROR_MESSAGE_400_001("Gin is mandatory", "business.400.001"),
    ERROR_MESSAGE_404_001("Last activity not found for this Gin", "business.404.001"),
    ERROR_MESSAGE_412_001("Invalid value for the 'gin' parameter, length must be equal to 12", "business.412_001");

    /**
     * Error description
     */
    private String description;

    /**
     * Error code
     */
    private String code;


}
