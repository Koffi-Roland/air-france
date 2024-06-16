package com.afklm.repind.msv.doctor.role.service.encoder.role;

import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleBody;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.upsert.model.UpsertDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.criteria.role.UpsertDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.utils.IConstants;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperUpsertDoctorRoleResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UpsertRoleEncoder {

    public UpsertDoctorRoleRequest encodeUpdateDoctorRequest(String contractNumber , UpsertDoctorRoleCriteria upsertDoctorRoleCriteria) {

        UpsertDoctorRoleRequest upsertDoctorRoleRequest = new UpsertDoctorRoleRequest()
                .withRoleId(contractNumber)
                .withDoctorId(upsertDoctorRoleCriteria.getDoctorId())
                .withDoctorStatus(upsertDoctorRoleCriteria.getDoctorStatus())
                .withEndDateRole(upsertDoctorRoleCriteria.getEndDateRole())
                .withAirLineCode(upsertDoctorRoleCriteria.getAirLineCode())
                .withSignatureSource(upsertDoctorRoleCriteria.getSignatureSource())
                .withSiteModification(upsertDoctorRoleCriteria.getSiteModification())
                .withSpeciality(upsertDoctorRoleCriteria.getSpeciality());
        upsertDoctorRoleRequest.setBody(new CreateDoctorRoleBody().withRelationsList(new ArrayList<>(upsertDoctorRoleCriteria.getRelationsList())));
        return upsertDoctorRoleRequest;
    }

    public WrapperUpsertDoctorRoleResponse decode(DoctorRoleResponseModel upsertDoctorRoleResponse){
        WrapperUpsertDoctorRoleResponse response = new WrapperUpsertDoctorRoleResponse();
        response.setContractNumber(upsertDoctorRoleResponse.getRoleId());
        response.setType(IConstants.DOCTOR_TYPE);
        response.setDoctorId(upsertDoctorRoleResponse.getDoctorId());
        response.setStatus(upsertDoctorRoleResponse.getStatus());
        response.setEndDateRole(upsertDoctorRoleResponse.getEndDateRole());
        response.setSiteCreation(upsertDoctorRoleResponse.getSiteCreation());
        response.setSignatureCreation(upsertDoctorRoleResponse.getSignatureSourceCreation());
        response.setSignatureDateCreation(upsertDoctorRoleResponse.getSignatureDateCreation());
        response.setSiteModification(upsertDoctorRoleResponse.getSiteModification());
        response.setSignatureDateModification(upsertDoctorRoleResponse.getSignatureDateModification());
        response.setSignatureModification(upsertDoctorRoleResponse.getSignatureSourceModification());
        response.setAirLineCode(upsertDoctorRoleResponse.getAirLineCode());
        response.setSpeciality(upsertDoctorRoleResponse.getSpeciality());
        response.setRelationsList(upsertDoctorRoleResponse.getRelationsList());
        response.setLanguages(upsertDoctorRoleResponse.getLanguages());
        return response;
    }
}
