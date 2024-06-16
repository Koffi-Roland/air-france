package com.afklm.repind.msv.doctor.attributes.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DoctorAttributesModel {

    @Getter
    @Setter
    @ApiModelProperty(required = true)
    public List<RelationModel> relationsList;
}
