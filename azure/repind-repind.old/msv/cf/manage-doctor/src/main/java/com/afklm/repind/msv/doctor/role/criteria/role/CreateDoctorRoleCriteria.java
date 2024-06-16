package com.afklm.repind.msv.doctor.role.criteria.role;

import com.afklm.repind.msv.doctor.role.model.RelationModel;
import lombok.*;

import java.util.List;

@Getter @Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRoleCriteria {

    private String doctorStatus;
    private String speciality;
    private String gin;
    private String type;
    private String endDateRole;
    private String signatureSiteCreation;
    private String signatureSourceCreation;
    private String doctorId;
    private String airLineCode;
    private List<RelationModel> relationsList;

}
