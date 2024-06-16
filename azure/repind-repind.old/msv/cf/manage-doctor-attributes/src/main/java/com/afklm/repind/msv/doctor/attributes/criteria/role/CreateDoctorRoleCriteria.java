package com.afklm.repind.msv.doctor.attributes.criteria.role;

import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;
import java.util.Set;

@Getter @With
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRoleCriteria {

    private String gin;
    private String roleId;
    private Date endDateRole;
    private String doctorStatus;
    private String doctorId;
    private String airLineCode;
    private String signatureCreation;
    private String siteCreation;
    private String speciality;
    private Set<RelationModel> relationsList;

}
