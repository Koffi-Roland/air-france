package com.afklm.repind.msv.doctor.role.client.role.delete;

import com.afklm.repind.msv.doctor.role.client.core.RestCall;
import com.afklm.repind.msv.doctor.role.client.role.delete.model.DeleteDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import org.springframework.http.HttpMethod;

public class DeleteDoctorRoleClient extends RestCall<DeleteDoctorRoleRequest, DoctorRoleResponseModel> {

    public DeleteDoctorRoleClient(String url, HttpMethod httpMethod, Class<DoctorRoleResponseModel> iClass) {
        super(url, httpMethod , iClass);
    }
}
