package com.afklm.repind.msv.doctor.role.client.role.create;

import com.afklm.repind.msv.doctor.role.client.core.RestCall;
import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import org.springframework.http.HttpMethod;

public class CreateDoctorRoleClient extends RestCall<CreateDoctorRoleRequest, DoctorRoleResponseModel> {

    public CreateDoctorRoleClient(String url, HttpMethod httpMethod, Class<DoctorRoleResponseModel> iClass) {
        super(url, httpMethod , iClass);
    }
}
