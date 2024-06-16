package com.afklm.repind.msv.doctor.attributes.service.encoder.role;

import com.afklm.repind.msv.doctor.attributes.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.entity.Role;
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
        role.setAirLineCode(iCreateDoctorRoleCriteria.getAirLineCode());
        role.setRoleId(iCreateDoctorRoleCriteria.getRoleId());
        role.setSpeciality(iCreateDoctorRoleCriteria.getSpeciality());
        role.setLanguages(iCreateDoctorRoleCriteria.getLanguages());
        role.setSignatureSourceCreation(iCreateDoctorRoleCriteria.getSignatureSourceCreation());
        role.setSiteCreation(iCreateDoctorRoleCriteria.getSiteCreation());
        role.setSignatureDateCreation(new Date());
        return role;
    }


}
