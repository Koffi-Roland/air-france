package com.afklm.repind.msv.doctor.role.client.role.create.model;

import com.afklm.repind.msv.doctor.role.client.core.HttpRequestBodyModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorRoleRequest extends HttpRequestBodyModel {

    private String gin;
    private String roleId;
    private String doctorId;
    private String doctorStatus;
    private String endDateRole;
    private String airLineCode;
    private String signatureSource;
    private String siteCreation;
    private String speciality;
}
