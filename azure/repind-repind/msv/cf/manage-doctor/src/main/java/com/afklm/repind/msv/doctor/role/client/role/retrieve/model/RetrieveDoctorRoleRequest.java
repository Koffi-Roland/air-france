package com.afklm.repind.msv.doctor.role.client.role.retrieve.model;

import com.afklm.repind.msv.doctor.role.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveDoctorRoleRequest extends HttpRequestModel {
    private String roleId;
}
