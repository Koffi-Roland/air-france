package com.afklm.repind.msv.doctor.attributes.criteria.attributes;

import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UpsertDoctorAttributesCriteria {
    private String roleId;
    private String speciality;
    private String airLineCode;
    private String signatureSource;
    private Date signatureDate;
    private Set<RelationModel> relationsList;
}
