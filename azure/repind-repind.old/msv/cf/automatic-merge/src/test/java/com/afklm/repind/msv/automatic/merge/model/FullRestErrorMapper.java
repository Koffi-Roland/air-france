package com.afklm.repind.msv.automatic.merge.model;

import com.afklm.repind.common.model.error.RestError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FullRestErrorMapper {
    private HttpStatus status;
    private RestError restError;
}
