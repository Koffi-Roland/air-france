package com.afklm.repind.msv.doctor.attributes.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter @Setter @With
@NoArgsConstructor
@AllArgsConstructor
public class RelationModel {

    @ApiModelProperty(required = true) String type;
    @ApiModelProperty(required = true) List<String> values;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationModel that = (RelationModel) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}