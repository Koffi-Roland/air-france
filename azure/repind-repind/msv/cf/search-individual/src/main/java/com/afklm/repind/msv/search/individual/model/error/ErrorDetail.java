package com.afklm.repind.msv.search.individual.model.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {

    private String code;
    private String field;
    private String defaultMessage;
    private Object rejectedValue;
}
