package com.afklm.repind.common.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter @Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class RestError implements Serializable {

    private String code;
    private String description;
    private HttpStatus httpStatus;

    /**
     * Default constructor from
     *
     * @param errorMessage
     */
    public RestError(final IErrorMessage errorMessage , HttpStatus iHttpStatus) {
        this.code = errorMessage.getCode();
        this.description = errorMessage.getDescription();
        this.httpStatus = iHttpStatus;
    }
}
