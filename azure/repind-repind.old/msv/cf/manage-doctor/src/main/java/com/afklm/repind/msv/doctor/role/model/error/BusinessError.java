package com.afklm.repind.msv.doctor.role.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessError implements IError {

    API_MISSING_REQUEST_PARAMETER(new RestError(ErrorMessage.ERROR_MESSAGE_400 , HttpStatus.BAD_REQUEST)),

    API_GIN_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_001 , HttpStatus.BAD_REQUEST)),

    API_TYPE_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_002 , HttpStatus.BAD_REQUEST)),

    API_SITE_CREATION_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_004 , HttpStatus.BAD_REQUEST)),

    API_SIGNATURE_CREATION_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_005 , HttpStatus.BAD_REQUEST)),

    API_ROLE_ID_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_006 , HttpStatus.BAD_REQUEST)),

    API_DOCTOR_ID_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_007 , HttpStatus.BAD_REQUEST)),

    API_AIR_LINE_CODE_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_008 , HttpStatus.BAD_REQUEST)),

    API_DOCTOR_STATUS_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_009 , HttpStatus.BAD_REQUEST)),

    API_RELATION_TYPE_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_010 , HttpStatus.BAD_REQUEST)),

    API_RELATION_VALUE_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_011 , HttpStatus.BAD_REQUEST)),

    API_RELATION_LIST_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_012 , HttpStatus.BAD_REQUEST)),

    API_SIGNATURE_DATE_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_013 , HttpStatus.BAD_REQUEST)),

    API_SPECIALITY_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_014 , HttpStatus.BAD_REQUEST)),

    FB_CONTRACT_MUST_EXIST(new RestError(ErrorMessage.ERROR_MESSAGE_403_001 , HttpStatus.FORBIDDEN)),

    GIN_DOES_NOT_MATCH_WITH_GIN_DOCTOR_ROLE(new RestError(ErrorMessage.ERROR_MESSAGE_403_002 , HttpStatus.FORBIDDEN)),

    CANNOT_UPDATE_RETRIEVE_SUPPRESSED_ROLE(new RestError(ErrorMessage.ERROR_MESSAGE_403_003 , HttpStatus.FORBIDDEN)),

    CANNOT_UPDATE_RETRIEVE_ATTRIBUTES_BECAUSE_SUPPRESSED_ROLE(new RestError(ErrorMessage.ERROR_MESSAGE_403_004 , HttpStatus.FORBIDDEN)),

    GIN_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404_001 , HttpStatus.NOT_FOUND)),

    ROLE_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404_003 , HttpStatus.NOT_FOUND)),

    ROLE_ALREADY_EXISTS(new RestError(ErrorMessage.ERROR_MESSAGE_409_001 , HttpStatus.CONFLICT)),

    DOCTOR_ID_ALREADY_EXISTS(new RestError(ErrorMessage.ERROR_MESSAGE_409_002 , HttpStatus.CONFLICT)),

    API_CONSTRAINT_VIOLATION(new RestError( ErrorMessage.ERROR_MESSAGE_412 , HttpStatus.PRECONDITION_FAILED)),

    API_GIN_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001 , HttpStatus.PRECONDITION_FAILED)),

    API_TYPE_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_002 , HttpStatus.PRECONDITION_FAILED)),

    API_END_DATE_ROLE_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_003 , HttpStatus.PRECONDITION_FAILED)),

    API_DOCTOR_ID_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_004 , HttpStatus.PRECONDITION_FAILED)),

    API_AIR_LINE_CODE_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_005 , HttpStatus.PRECONDITION_FAILED)),

    API_DOCTOR_STATUS_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_006 , HttpStatus.PRECONDITION_FAILED)),

    API_RELATION_TYPE_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_007 , HttpStatus.PRECONDITION_FAILED)),

    API_RELATION_VALUES_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_008 , HttpStatus.PRECONDITION_FAILED)),

    API_INCORRECT_NUMBER_OF_TYPE_VALUES_SENT_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_009 , HttpStatus.PRECONDITION_FAILED)),

    API_SIGNATURE_DATE_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_010 , HttpStatus.PRECONDITION_FAILED)),

    API_SPECIALITY_VALUES_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_011 , HttpStatus.PRECONDITION_FAILED)),

    API_INCORRECT_NUMBER_SPECIALITY_VALUES_SENT_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_012 , HttpStatus.PRECONDITION_FAILED));


    @Getter  private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}
