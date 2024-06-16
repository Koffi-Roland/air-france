package com.afklm.repind.msv.doctor.role.criteria.role;

import com.afklm.repind.msv.doctor.role.model.RelationModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Getter @With
@AllArgsConstructor
@NoArgsConstructor
public class UpsertDoctorRoleCriteria {

    private String roleId;
    private String doctorStatus;
    private String endDateRole;
    private String doctorId;
    private String speciality;
    private String airLineCode;
    private String siteModification;
    private String signatureSource;
    private List<RelationModel> relationsList;


}
