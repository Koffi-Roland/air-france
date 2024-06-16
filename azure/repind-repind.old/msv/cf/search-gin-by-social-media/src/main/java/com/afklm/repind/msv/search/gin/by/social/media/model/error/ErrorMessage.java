package com.afklm.repind.msv.search.gin.by.social.media.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {
    /**
     * list of Business Errors Messages
     */

    ERROR_MESSAGE_412_001("business.412_001" ,"Invalid value for the 'External Identifier' parameter, external identifier must be valid"),

    ERROR_MESSAGE_404_001("business.404_001" ,"External Identifier Type not found"),

    ERROR_MESSAGE_404("business.404" ,"Not Found");

    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}