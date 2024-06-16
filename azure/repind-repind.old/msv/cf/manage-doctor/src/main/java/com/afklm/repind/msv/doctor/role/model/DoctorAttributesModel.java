package com.afklm.repind.msv.doctor.role.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DoctorAttributesModel {

    @Getter
    @ApiModelProperty(required = true)
    public List<RelationModel> relationsList = new ArrayList<>();

    public void addRelations(Collection<RelationModel> iRelations){
        if(iRelations != null){
            relationsList.addAll(iRelations);
        }
    }

}
