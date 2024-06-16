package com.afklm.repind.msv.customer.adaptor.model.error;

import com.afklm.repind.common.model.error.IErrorMessage;
import lombok.Getter;

@Getter
public enum ErrorMessage implements IErrorMessage {

    KAFKA_PROCESSING_ERROR("business.error.001", "Kafka processing business error"),
    CONTRACT_NOT_SUPPORTED("business.error.002", "Contract is not supported"),
    TABLE_NOT_SUPPORTED("business.error.003", "Table is not supported"),
    INDIVIDU_NOT_ELIGIBLE("business.error.004", "Individu is not eligible to send to sfmc"),
    SFMC_API_BUSINESS_EXCEPTION("business.error.005", "Salesforce API Business exception"),
    NO_ELIGIBLE_DATA_FOUND("business.error.006", "No eligible data found to send"),
    JWT_BUSINESS_EXCEPTION("business.error.007", "JWT  Business exception"),
    HISTORIZED_DATA_ERROR("business.error.008", "Do not send historized data.");




    private String description;
    private String code;

    ErrorMessage(String code , String description) {
        this.code = code;
        this.description = description;
    }
}
