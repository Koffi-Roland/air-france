package com.afklm.repind.msv.doctor.attributes.wrapper.attributes;

import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperUpsertDoctorAttributesResponse {

    private String roleId;
    private String speciality;
    private String airLineCode;
    private List<RelationModel> relationsList = new ArrayList<>();

    public void addRelation(RelationModel iRelation) {
        if (iRelation != null) {
            relationsList.add(iRelation);
        }
    }

    public void addRelations(Collection<RelationModel> iRelations) {
        if (iRelations != null) {
            relationsList.addAll(iRelations);
        }
    }

}
