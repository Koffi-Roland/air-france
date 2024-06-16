package com.afklm.repind.msv.doctor.role.service.encoder.role;

import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleBody;
import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.dto.DoctorAttributesDto;
import com.afklm.repind.msv.doctor.role.entity.BusinessRole;
import com.afklm.repind.msv.doctor.role.model.AirLineCode;
import com.afklm.repind.msv.doctor.role.model.Language;
import com.afklm.repind.msv.doctor.role.model.Speciality;
import com.afklm.repind.msv.doctor.role.utils.IConstants;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperCreateDoctorRoleResponse;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class CreateRoleEncoder {

    private static final String SPEAK = "SPEAK";

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

DoctorAttributesDto doctorAttributesDto = this.mapToDoctorAttributesDto(createDoctorRoleCriteria,createDoctorRoleRequest);

        createDoctorRoleRequest.setBody(doctorAttributesDto);
        return createDoctorRoleRequest;
    }

    public WrapperCreateDoctorRoleResponse decode(DoctorRoleResponseModel createDoctorRoleResponse) {
        String airlineCode = null;
        String speciality = null;

        if (createDoctorRoleResponse.getAirLineCode() != null) {
            airlineCode = createDoctorRoleResponse.getAirLineCode().getValue();
        }

        if (createDoctorRoleResponse.getSpeciality() != null) {
            speciality = createDoctorRoleResponse.getSpeciality().getValue();
        }
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
        response.setAirLineCode(airlineCode);
        response.setSpeciality(speciality);
        response.setLanguages(createDoctorRoleResponse.getLanguages());
        return response;
    }
    private DoctorAttributesDto mapToDoctorAttributesDto(CreateDoctorRoleCriteria createDoctorRoleCriteria, CreateDoctorRoleRequest createDoctorRoleRequest)
    {
        //Get language from relation list
        Set<Language> languages = new HashSet<>();
        if(createDoctorRoleCriteria.getRelationsList() != null) {
            for (String language : createDoctorRoleCriteria.getRelationsList().get(0).getValues()) {
                if (SPEAK.equals(createDoctorRoleCriteria.getRelationsList().get(0).getType())) {
                    languages.add(Language.builder().acronyme(language).value(language).build());
                }
            }
        }
        DoctorAttributesDto doctorAttributesDto = new DoctorAttributesDto();
        doctorAttributesDto.setDoctorStatus(createDoctorRoleRequest.getDoctorStatus());
        doctorAttributesDto.setGin(createDoctorRoleRequest.getGin());
        doctorAttributesDto.setSiteCreation(createDoctorRoleRequest.getSiteCreation());
        doctorAttributesDto.setSignatureSourceCreation(createDoctorRoleRequest.getSignatureSource());
        doctorAttributesDto.setRoleId(createDoctorRoleRequest.getRoleId());
        doctorAttributesDto.setDoctorId(createDoctorRoleRequest.getDoctorId());
        doctorAttributesDto.setSpeciality(Speciality.builder().value(createDoctorRoleRequest.getSpeciality()).build());
        doctorAttributesDto.setAirLineCode(AirLineCode.builder().value(createDoctorRoleRequest.getAirLineCode()).build());
        doctorAttributesDto.setEndDateRole(createDoctorRoleRequest.getEndDateRole());
        doctorAttributesDto.setLanguages(languages);

        return doctorAttributesDto;
    }
}
