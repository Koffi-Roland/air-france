package com.afklm.repind.msv.doctor.role.client.role.retrieve;

import com.afklm.repind.msv.doctor.role.client.core.RestCall;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.retrieve.model.RetrieveDoctorRoleRequest;
import org.springframework.http.HttpMethod;

public class RetrieveDoctorRoleClient extends RestCall<RetrieveDoctorRoleRequest, DoctorRoleResponseModel> {

    public RetrieveDoctorRoleClient(String url, HttpMethod httpMethod, Class<DoctorRoleResponseModel> iClass) {
        super(url, httpMethod , iClass);
    }
}
