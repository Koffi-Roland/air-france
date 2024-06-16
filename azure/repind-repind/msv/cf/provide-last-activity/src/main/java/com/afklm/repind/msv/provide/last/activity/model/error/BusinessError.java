package com.afklm.repind.msv.provide.last.activity.model.error;
import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Enum to handle business error
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BusinessError implements IError {

    API_GIN_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_001 , HttpStatus.BAD_REQUEST)),

    GIN_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404_001 , HttpStatus.NOT_FOUND)),

    API_GIN_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001 , HttpStatus.PRECONDITION_FAILED));

    /**
     * rest response format error
     */
    private RestError restError;

}
