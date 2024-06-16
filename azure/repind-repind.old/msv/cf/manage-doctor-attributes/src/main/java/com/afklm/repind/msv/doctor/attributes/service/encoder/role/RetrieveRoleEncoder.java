package com.afklm.repind.msv.doctor.attributes.service.encoder.role;

import com.afklm.repind.msv.doctor.attributes.entity.node.Language;
import com.afklm.repind.msv.doctor.attributes.entity.node.Role;
import com.afklm.repind.msv.doctor.attributes.wrapper.role.WrapperRetrieveDoctorRoleResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RetrieveRoleEncoder {

    public WrapperRetrieveDoctorRoleResponse decode(Role iRole)  {
        final WrapperRetrieveDoctorRoleResponse wrapperRetrieveDoctorRoleResponse = new WrapperRetrieveDoctorRoleResponse();
        wrapperRetrieveDoctorRoleResponse.setRoleId(iRole.getRoleId());
        if (iRole.getEndDateRole() != null) {
            wrapperRetrieveDoctorRoleResponse.setEndDateRole(iRole.getEndDateRole());
        }
        wrapperRetrieveDoctorRoleResponse.setDoctorId(iRole.getDoctorId());
        wrapperRetrieveDoctorRoleResponse.setStatus(iRole.getDoctorStatus());
        wrapperRetrieveDoctorRoleResponse.setOptOut(iRole.getOptOut());
        wrapperRetrieveDoctorRoleResponse.setLastUpdate(iRole.getLastUpdate());
        wrapperRetrieveDoctorRoleResponse.setSignatureSourceCreation(iRole.getSignatureSourceCreation());
        wrapperRetrieveDoctorRoleResponse.setSiteCreation(iRole.getSiteCreation());
        wrapperRetrieveDoctorRoleResponse.setSignatureDateCreation(iRole.getSignatureDateCreation());
        wrapperRetrieveDoctorRoleResponse.setSignatureSourceModification(iRole.getSignatureSourceModification());
        wrapperRetrieveDoctorRoleResponse.setSiteModification(iRole.getSiteModification());
        wrapperRetrieveDoctorRoleResponse.setSignatureDateModification(iRole.getSignatureDateModification());

        wrapperRetrieveDoctorRoleResponse.setAirLineCode(iRole.getAirLineCode().getValue());
        wrapperRetrieveDoctorRoleResponse.setSpeciality(iRole.getSpeciality().getValue());
        wrapperRetrieveDoctorRoleResponse.setLanguages(iRole.getLanguages().stream().map(Language::getValue).collect(Collectors.toList()));

       return wrapperRetrieveDoctorRoleResponse;
    }
}
