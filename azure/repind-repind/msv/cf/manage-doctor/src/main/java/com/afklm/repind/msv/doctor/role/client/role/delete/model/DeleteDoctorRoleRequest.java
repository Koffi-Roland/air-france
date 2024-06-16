package com.afklm.repind.msv.doctor.role.client.role.delete.model;

import com.afklm.repind.msv.doctor.role.client.core.HttpRequestModel;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDoctorRoleRequest extends HttpRequestModel {
    String roleId;
}
