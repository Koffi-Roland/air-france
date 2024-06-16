package com.afklm.repind.msv.doctor.attributes.service.encoder.role;

import com.afklm.repind.msv.doctor.attributes.criteria.role.UpdateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.model.Language;
import com.afklm.repind.msv.doctor.attributes.entity.Role;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperUpdateDoctorRoleResponse;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UpdateRoleEncoder {


    public Role encode(Role ioRole, UpdateDoctorRoleCriteria iUpdateDoctorRoleCriteria) {
        ioRole.setEndDateRole(iUpdateDoctorRoleCriteria.getEndDateRole());
        ioRole.setDoctorId(iUpdateDoctorRoleCriteria.getDoctorId());
        ioRole.setDoctorStatus(iUpdateDoctorRoleCriteria.getDoctorStatus());
        ioRole.setSignatureSourceModification(iUpdateDoctorRoleCriteria.getSignatureSourceModification());
        ioRole.setSiteModification(iUpdateDoctorRoleCriteria.getSiteModification());
        ioRole.setSignatureDateModification(new Date());
        ioRole.setLanguages(iUpdateDoctorRoleCriteria.getLanguages());
        ioRole.setAirLineCode(iUpdateDoctorRoleCriteria.getAirLineCode());
        ioRole.setSpeciality(iUpdateDoctorRoleCriteria.getSpeciality());
        ioRole.setLastUpdate(new Date());
        return ioRole;
    }

    public WrapperUpdateDoctorRoleResponse decode(Role iRole) {
        String airlineCode = null;
        String speciality = null;
        if (iRole.getAirLineCode() != null) {
            airlineCode = iRole.getAirLineCode().getValue();
        }

        if (iRole.getSpeciality() != null) {
            speciality = iRole.getSpeciality().getValue();
        }

        final WrapperUpdateDoctorRoleResponse wrapperUpdateDoctorRoleResponse = new WrapperUpdateDoctorRoleResponse();
        wrapperUpdateDoctorRoleResponse.setRoleId(iRole.getRoleId());
        wrapperUpdateDoctorRoleResponse.setEndDateRole(iRole.getEndDateRole());
        wrapperUpdateDoctorRoleResponse.setDoctorId(iRole.getDoctorId());
        wrapperUpdateDoctorRoleResponse.setStatus(iRole.getDoctorStatus());
        wrapperUpdateDoctorRoleResponse.setSignatureSourceCreation(iRole.getSignatureSourceCreation());
        wrapperUpdateDoctorRoleResponse.setSiteCreation(iRole.getSiteCreation());
        wrapperUpdateDoctorRoleResponse.setSignatureDateCreation(iRole.getSignatureDateCreation());
        wrapperUpdateDoctorRoleResponse.setSignatureSourceModification(iRole.getSignatureSourceModification());
        wrapperUpdateDoctorRoleResponse.setSiteModification(iRole.getSiteModification());
        wrapperUpdateDoctorRoleResponse.setSignatureDateModification(iRole.getSignatureDateModification());
        wrapperUpdateDoctorRoleResponse.setOptOut(iRole.getOptOut());
        wrapperUpdateDoctorRoleResponse.setLastUpdate(iRole.getLastUpdate());
        wrapperUpdateDoctorRoleResponse.setAirLineCode(airlineCode);
        wrapperUpdateDoctorRoleResponse.setSpeciality(speciality);
        wrapperUpdateDoctorRoleResponse.setLanguages(displayLanguages(iRole.getLanguages()));

        return wrapperUpdateDoctorRoleResponse;
    }

    private Set<String> displayLanguages(Collection<Language> iLanguages) {
        return iLanguages.stream().map(Language::getValue).collect(Collectors.toSet());
    }

}
