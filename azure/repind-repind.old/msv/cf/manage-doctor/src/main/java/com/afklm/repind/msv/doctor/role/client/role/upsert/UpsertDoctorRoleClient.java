package com.afklm.repind.msv.doctor.role.client.role.upsert;

import com.afklm.repind.msv.doctor.role.client.core.RestCall;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.upsert.model.UpsertDoctorRoleRequest;
import org.springframework.http.HttpMethod;

public class UpsertDoctorRoleClient extends RestCall<UpsertDoctorRoleRequest, DoctorRoleResponseModel> {

    public UpsertDoctorRoleClient(String url, HttpMethod httpMethod, Class<DoctorRoleResponseModel> iClass) {
        super(url, httpMethod , iClass);
    }
}
