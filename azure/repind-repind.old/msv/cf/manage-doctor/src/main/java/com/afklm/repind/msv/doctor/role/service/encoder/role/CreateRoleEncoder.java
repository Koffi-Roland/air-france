package com.afklm.repind.msv.doctor.role.service.encoder.role;

import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleBody;
import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.entity.BusinessRole;
import com.afklm.repind.msv.doctor.role.utils.IConstants;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperCreateDoctorRoleResponse;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CreateRoleEncoder {


    public BusinessRole encode(CreateDoctorRoleCriteria createDoctorRoleCriteria) throws NoSuchAlgorithmException {
        BusinessRole businessRole = new BusinessRole();
        //Generate a random number based on gin for contract number using salt

        String contractNumber = EncoderUtils.generateRandomWithGinEncoder(createDoctorRoleCriteria.getGin());

        businessRole.setNumeroContrat(contractNumber.subSequence(0 , 20).toString());
        businessRole.setGinInd(createDoctorRoleCriteria.getGin());
        businessRole.setSignatureCreation(createDoctorRoleCriteria.getSignatureSourceCreation());
        businessRole.setSiteCreation(createDoctorRoleCriteria.getSignatureSiteCreation());
        businessRole.setType(createDoctorRoleCriteria.getType());
        businessRole.setDateCreation(new Date());
        return businessRole;
    }

    public CreateDoctorRoleRequest encodeCreateDoctorRequest(String contractNumber , CreateDoctorRoleCriteria createDoctorRoleCriteria){

        CreateDoctorRoleRequest createDoctorRoleRequest = new CreateDoctorRoleRequest()
                .withGin(createDoctorRoleCriteria.getGin())
                .withRoleId(contractNumber)
                .withDoctorId(createDoctorRoleCriteria.getDoctorId())
                .withDoctorStatus(createDoctorRoleCriteria.getDoctorStatus())
                .withEndDateRole(createDoctorRoleCriteria.getEndDateRole())
                .withAirLineCode(createDoctorRoleCriteria.getAirLineCode())
                .withSignatureSource(createDoctorRoleCriteria.getSignatureSourceCreation())
                .withSiteCreation(createDoctorRoleCriteria.getSignatureSiteCreation())
                .withSpeciality(createDoctorRoleCriteria.getSpeciality());
        createDoctorRoleRequest.setBody(new CreateDoctorRoleBody().withRelationsList(new ArrayList<>(createDoctorRoleCriteria.getRelationsList())));
        return createDoctorRoleRequest;
    }

    public WrapperCreateDoctorRoleResponse decode(DoctorRoleResponseModel createDoctorRoleResponse) {
        WrapperCreateDoctorRoleResponse response = new WrapperCreateDoctorRoleResponse();
        response.setContractNumber(createDoctorRoleResponse.getRoleId());
        response.setType(IConstants.DOCTOR_TYPE);
        response.setDoctorId(createDoctorRoleResponse.getDoctorId());
        response.setDoctorStatus(createDoctorRoleResponse.getStatus());
        response.setEndDateRole(createDoctorRoleResponse.getEndDateRole());
        response.setSiteCreation(createDoctorRoleResponse.getSiteCreation());
        response.setSignatureSourceCreation(createDoctorRoleResponse.getSignatureSourceCreation());
        response.setSignatureDateCreation(createDoctorRoleResponse.getSignatureDateCreation());
        response.setDoctorStatus(createDoctorRoleResponse.getStatus());
        response.setAirLineCode(createDoctorRoleResponse.getAirLineCode());
        response.setSpeciality(createDoctorRoleResponse.getSpeciality());
        response.setRelationsList(createDoctorRoleResponse.getRelationsList());
        response.setLanguages(createDoctorRoleResponse.getLanguages());
        return response;
    }
}
