package com.afklm.repind.msv.doctor.role.client.role.upsert.model;

import com.afklm.repind.msv.doctor.role.client.core.HttpRequestBodyModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class UpsertDoctorRoleRequest extends HttpRequestBodyModel {

    private String roleId;
    private String doctorId;
    private String doctorStatus;
    private String endDateRole;
    private String airLineCode;
    private String signatureSource;
    private String siteModification;
    private String speciality;
}
