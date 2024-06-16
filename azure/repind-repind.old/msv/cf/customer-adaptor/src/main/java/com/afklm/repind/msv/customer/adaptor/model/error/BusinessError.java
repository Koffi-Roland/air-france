package com.afklm.repind.msv.customer.adaptor.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    KAFKA_PROCESSING_BUSINESS_ERROR(new RestError(ErrorMessage.KAFKA_PROCESSING_ERROR , HttpStatus.NOT_ACCEPTABLE)),
    CONTRACT_NOT_SUPPORTED_BUSINESS_ERROR(new RestError(ErrorMessage.KAFKA_PROCESSING_ERROR , HttpStatus.NOT_ACCEPTABLE)),
    TABLE_NOT_SUPPORTED_BUSINESS_ERROR(new RestError(ErrorMessage.TABLE_NOT_SUPPORTED , HttpStatus.NOT_ACCEPTABLE)),
    INDIVIDUS_NOT_ELIGIBLE_BUSINESS_ERROR(new RestError(ErrorMessage.TABLE_NOT_SUPPORTED , HttpStatus.NOT_ACCEPTABLE)),
    NO_ELIGIBLE_DATA_FOUND_ERROR(new RestError(ErrorMessage.NO_ELIGIBLE_DATA_FOUND , HttpStatus.NOT_ACCEPTABLE)),
    SFMC_API_BUSINESS_EXCEPTION(new RestError(ErrorMessage.SFMC_API_BUSINESS_EXCEPTION , HttpStatus.NOT_ACCEPTABLE));


    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}
