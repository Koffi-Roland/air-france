package com.afklm.repind.msv.doctor.role.service.encoder.role;

import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.retrieve.model.RetrieveDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.utils.IConstants;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperRetrieveDoctorRoleResponse;
import org.springframework.stereotype.Service;

@Service
public class RetrieveRoleEncoder {

    public WrapperRetrieveDoctorRoleResponse decode(DoctorRoleResponseModel iRetrieveDoctorRoleResponse){
        String airlineCode = null;
        String speciality = null;
        if (iRetrieveDoctorRoleResponse.getAirLineCode() != null) {
            airlineCode = iRetrieveDoctorRoleResponse.getAirLineCode().getValue();
        }

        if (iRetrieveDoctorRoleResponse.getSpeciality() != null) {
            speciality = iRetrieveDoctorRoleResponse.getSpeciality().getValue();
        }
        WrapperRetrieveDoctorRoleResponse response = new WrapperRetrieveDoctorRoleResponse();
        response.setContractNumber(iRetrieveDoctorRoleResponse.getRoleId());
        response.setType(IConstants.DOCTOR_TYPE);
        response.setDoctorId(iRetrieveDoctorRoleResponse.getDoctorId());
        response.setEndDateRole(iRetrieveDoctorRoleResponse.getEndDateRole());
        response.setSiteCreation(iRetrieveDoctorRoleResponse.getSiteCreation());
        response.setSignatureSourceCreation(iRetrieveDoctorRoleResponse.getSignatureSourceCreation());
        response.setSignatureDateCreation(iRetrieveDoctorRoleResponse.getSignatureDateCreation());
        response.setSiteModification(iRetrieveDoctorRoleResponse.getSiteModification());
        response.setSignatureDateModification(iRetrieveDoctorRoleResponse.getSignatureDateModification());
        response.setSignatureSourceModification(iRetrieveDoctorRoleResponse.getSignatureSourceModification());
        response.setStatus(iRetrieveDoctorRoleResponse.getStatus());
        response.setAirLineCode(airlineCode);
        response.setSpeciality(speciality);
        response.setLanguages(iRetrieveDoctorRoleResponse.getLanguages());
        return response;
    }

    public RetrieveDoctorRoleRequest encode(String contractNumber){
        return new RetrieveDoctorRoleRequest().withRoleId(contractNumber);
    }
}
