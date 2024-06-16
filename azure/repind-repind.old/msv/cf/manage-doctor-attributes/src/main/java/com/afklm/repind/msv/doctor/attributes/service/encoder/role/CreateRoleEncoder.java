package com.afklm.repind.msv.doctor.attributes.service.encoder.role;

import com.afklm.repind.msv.doctor.attributes.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.entity.node.Role;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateRoleEncoder {


    public Role encode(CreateDoctorRoleCriteria iCreateDoctorRoleCriteria){
        Role role = new Role();
        if(iCreateDoctorRoleCriteria.getEndDateRole() != null){
            role.setEndDateRole(iCreateDoctorRoleCriteria.getEndDateRole());
        }
        role.setGin(iCreateDoctorRoleCriteria.getGin());
        role.setDoctorId(iCreateDoctorRoleCriteria.getDoctorId());
        role.setDoctorStatus(iCreateDoctorRoleCriteria.getDoctorStatus());
        role.setLastUpdate(new Date());
        role.setRoleId(iCreateDoctorRoleCriteria.getRoleId());

        role.setSignatureSourceCreation(iCreateDoctorRoleCriteria.getSignatureCreation());
        role.setSiteCreation(iCreateDoctorRoleCriteria.getSiteCreation());
        role.setSignatureDateCreation(new Date());
        return role;
    }


}
