package com.afklm.repind.msv.customer.adaptor.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataModel {

    @ApiModelProperty(required = true)
    Map<String,String> keys;
    @ApiModelProperty(required = true)
    Map<String,String> values;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel that = (DataModel) o;
        return keys.equals(
                that.keys
        );
    }


    public int hashCode() { return Objects.hash(keys);
    }
}
