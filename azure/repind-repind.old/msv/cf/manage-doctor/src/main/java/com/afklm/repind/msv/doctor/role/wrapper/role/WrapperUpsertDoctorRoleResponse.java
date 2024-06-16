package com.afklm.repind.msv.doctor.role.wrapper.role;

import com.afklm.repind.msv.doctor.role.model.RelationModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@With
public class WrapperUpsertDoctorRoleResponse {

    private String contractNumber;
    private String type;
    private String doctorId;
    private String endDateRole;
    private String siteCreation;
    private String signatureCreation;
    private String signatureDateCreation;
    private String siteModification;
    private String signatureModification;
    private String signatureDateModification;
    private String status;
    private String speciality;
    private String airLineCode;
    private List<RelationModel> relationsList;
    private List<String> languages ;
}
