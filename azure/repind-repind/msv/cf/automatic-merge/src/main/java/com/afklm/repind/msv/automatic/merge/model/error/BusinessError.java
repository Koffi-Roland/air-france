package com.afklm.repind.msv.automatic.merge.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Business errors list
 *
 * @author m312812
 */
@Getter
public enum BusinessError implements IError {


    API_CUSTOMER_NOT_FOUND(
            new RestError("business.404.001", BusinessErorMessageList.ERROR_MESSAGE_404_001, HttpStatus.NOT_FOUND)),

    API_CUSTOMER_NOT_FOUND_MULTI_CRIT(
            new RestError("business.404.002", BusinessErorMessageList.ERROR_MESSAGE_404_002, HttpStatus.NOT_FOUND)),

    API_BUSINESS_NO_DATA_FOUND(
            new RestError("business.404.003", BusinessErorMessageList.ERROR_MESSAGE_404_003, HttpStatus.NOT_FOUND)),

    API_CONSTRAINT_VIOLATION(new RestError("business.412", BusinessErorMessageList.ERROR_MESSAGE_412, HttpStatus.PRECONDITION_FAILED)),

    API_PARAMETER_TYPE_MISMATCH(new RestError("business.412.001", BusinessErorMessageList.ERROR_MESSAGE_412_001, HttpStatus.PRECONDITION_FAILED)),

    API_INVALID_COMPLAINT_STATUS(
            new RestError("business.412.002", BusinessErorMessageList.ERROR_MESSAGE_412_002, HttpStatus.PRECONDITION_FAILED)),

    API_CUSTOMER_EMPTY(
            new RestError("business.412.003", BusinessErorMessageList.ERROR_MESSAGE_412_003, HttpStatus.PRECONDITION_FAILED)),

    API_INVALID_GIN(
            new RestError("business.412.004", BusinessErorMessageList.ERROR_MESSAGE_412_004, HttpStatus.PRECONDITION_FAILED)),

    MERGE_GIN_EQUALS(
            new RestError("business.412.005", BusinessErorMessageList.ERROR_MESSAGE_412_005, HttpStatus.PRECONDITION_FAILED)),

    MERGE_INVALID_TYPE(
            new RestError("business.412.006", BusinessErorMessageList.ERROR_MESSAGE_412_006, HttpStatus.PRECONDITION_FAILED)),

    MERGE_INVALID_STATUS(
            new RestError("business.412.007", BusinessErorMessageList.ERROR_MESSAGE_412_007, HttpStatus.NOT_ACCEPTABLE)),

    API_INVALID_BUSINESS_RULE(new RestError("business.412.008", BusinessErorMessageList.ERROR_MESSAGE_412_008, HttpStatus.PRECONDITION_FAILED)),

    API_INVALID_VALUE(
            new RestError("business.412.009", BusinessErorMessageList.ERROR_MESSAGE_412_009, HttpStatus.PRECONDITION_FAILED)),

    API_UNKNOWN_BUSINESS_ERROR(
            new RestError("business.412.010", BusinessErorMessageList.ERROR_MESSAGE_412_010, HttpStatus.PRECONDITION_FAILED)),

    MERGE_TOO_MANY_ADDRESSES(
            new RestError("business.412.011", BusinessErorMessageList.ERROR_MESSAGE_412_011, HttpStatus.PRECONDITION_FAILED)),

    MERGE_ERROR_S04600(
            new RestError("business.412.012", BusinessErorMessageList.ERROR_MESSAGE_412_012, HttpStatus.PRECONDITION_FAILED)),

    MERGE_INVALID_TYPE_GP(
            new RestError("business.412.013", BusinessErorMessageList.ERROR_MESSAGE_412_013, HttpStatus.PRECONDITION_FAILED)),

    MERGE_INVALID_RIGHTS(
            new RestError("business.412.015", BusinessErorMessageList.ERROR_MESSAGE_412_015, HttpStatus.PRECONDITION_FAILED)),

    API_FB_EXPERT_VALIDATION_REQUESTED(
            new RestError("business.412.017", BusinessErorMessageList.ERROR_MESSAGE_412_017, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_HACHIKO_FIRST_GIN_FORBIDDEN_MERGE(
            new RestError("business.412.018", BusinessErorMessageList.ERROR_MESSAGE_412_018, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_HACHIKO_SECOND_GIN_FORBIDDEN_MERGE(
            new RestError("business.412.019", BusinessErorMessageList.ERROR_MESSAGE_412_019, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_HACHIKO_GINS_FORBIDDEN_MERGE(
            new RestError("business.412.020", BusinessErorMessageList.ERROR_MESSAGE_412_020, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_HACHIKO_GINS_WITHOUT_FB(
            new RestError("business.412.021", BusinessErorMessageList.ERROR_MESSAGE_412_021, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_HACHIKO_MISSING_PARAMETER(
            new RestError("business.412.022", BusinessErorMessageList.ERROR_MESSAGE_412_022, HttpStatus.PRECONDITION_FAILED)),

    UPDATE_INVALID_EMAIL_ERROR(new RestError("business.412.023", BusinessErorMessageList.ERROR_MESSAGE_412_023, HttpStatus.PRECONDITION_FAILED)),

    MERGE_TOO_MANY_GP_ROLES(
            new RestError("business.412.084", BusinessErorMessageList.ERROR_MESSAGE_412_084, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_GP_GIN_CIBLE_INVALID(
            new RestError("business.412.085", BusinessErorMessageList.ERROR_MESSAGE_412_085, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_GP_GIN_SOURCE_INVALID(
            new RestError("business.412.087", BusinessErorMessageList.ERROR_MESSAGE_412_087, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_GP_PROCESSING_ERROR(
            new RestError("business.412.088", BusinessErorMessageList.ERROR_MESSAGE_412_088, HttpStatus.PRECONDITION_FAILED)),

    API_BUSINESS_GP_TECHNICAL_ERROR(
            new RestError("business.412.089", BusinessErorMessageList.ERROR_MESSAGE_412_089, HttpStatus.PRECONDITION_FAILED)),

    ERROR_UNMERGE_NOT_GOOD_ORDER(
            new RestError("business.412.090", BusinessErorMessageList.ERROR_MESSAGE_412_090, HttpStatus.NOT_ACCEPTABLE)),

    API_CANT_ACCESS_DB(
            new RestError("business.500.001", BusinessErorMessageList.ERROR_MESSAGE_500_001, HttpStatus.INTERNAL_SERVER_ERROR)),

    API_UNKNOWN_TECHNICAL_ERROR(
            new RestError("business.500.003", BusinessErorMessageList.ERROR_MESSAGE_500_003, HttpStatus.INTERNAL_SERVER_ERROR));


    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}
