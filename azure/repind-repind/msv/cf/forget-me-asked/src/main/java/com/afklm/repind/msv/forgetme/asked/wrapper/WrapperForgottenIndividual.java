package com.afklm.repind.msv.forgetme.asked.wrapper;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class WrapperForgottenIndividual {
    @ApiModelProperty(required = true) private String gin;
    @ApiModelProperty(required = true , dataType = "string" ,notes = "Creation date as dd/MM/yy", example = "01/02/21") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy", timezone="Europe/Paris") private Date creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperForgottenIndividual that = (WrapperForgottenIndividual) o;
        return Objects.equals(gin, that.gin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gin);
    }
}