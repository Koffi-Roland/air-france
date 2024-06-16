package com.afklm.repind.msv.doctor.role.service.encoder.role;

import com.afklm.repind.msv.doctor.role.client.role.delete.model.DeleteDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.criteria.role.DeleteDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperDoctorRoleResponse;
import org.springframework.stereotype.Service;

@Service
public class DeleteRoleEncoder {

    public DeleteDoctorRoleRequest encode(DeleteDoctorRoleCriteria deleteDoctorRoleCriteria){
        return new DeleteDoctorRoleRequest()
                .withRoleId(deleteDoctorRoleCriteria.getRoleId());
    }

    public WrapperDoctorRoleResponse decode(DoctorRoleResponseModel deleteDoctorRoleResponse) {
        WrapperDoctorRoleResponse response = new WrapperDoctorRoleResponse();
        response.setContractNumber(deleteDoctorRoleResponse.getRoleId());

        return response;
    }
}
