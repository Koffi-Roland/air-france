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
public class UpdateDoctorRoleCriteria {
    private String roleId;
    private Date endDateRole;
    private String doctorStatus;
    private String signatureSourceModification;
    private String siteModification;
    private Date signatureDateModification;
    private String doctorId;
    private String speciality;
    private String airLineCode;
    private Set<RelationModel> relationsList;
}
