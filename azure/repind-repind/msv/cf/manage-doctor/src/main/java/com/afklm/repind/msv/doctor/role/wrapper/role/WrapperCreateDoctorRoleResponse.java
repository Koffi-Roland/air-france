package com.afklm.repind.msv.doctor.role.wrapper.role;

import com.afklm.repind.msv.doctor.role.model.RelationModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class WrapperCreateDoctorRoleResponse {

    private String contractNumber;
    private String type;
    private String doctorId;
    private String endDateRole;
    private String siteCreation;
    private String signatureSourceCreation;
    private String signatureDateCreation;
    private String doctorStatus;
    private String speciality;
    private String airLineCode;
    private List<RelationModel> relationsList;
    private List<String> languages ;
}
