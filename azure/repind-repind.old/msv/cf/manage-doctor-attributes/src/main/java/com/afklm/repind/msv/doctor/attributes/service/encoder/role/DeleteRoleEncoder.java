package com.afklm.repind.msv.doctor.attributes.service.encoder.role;

import com.afklm.repind.msv.doctor.attributes.entity.node.Role;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperDeleteDoctorRoleResponse;
import org.springframework.stereotype.Service;

@Service
public class DeleteRoleEncoder {

    public WrapperDeleteDoctorRoleResponse decode(Role iRole)  {
        final WrapperDeleteDoctorRoleResponse wrapperDeleteDoctorRoleResponse = new WrapperDeleteDoctorRoleResponse();
        wrapperDeleteDoctorRoleResponse.setRoleId(iRole.getRoleId());

       return wrapperDeleteDoctorRoleResponse;
    }
}
