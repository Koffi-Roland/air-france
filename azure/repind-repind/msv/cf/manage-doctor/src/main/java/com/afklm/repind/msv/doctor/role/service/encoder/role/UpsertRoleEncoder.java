package com.afklm.repind.msv.doctor.role.service.encoder.role;

import com.afklm.repind.msv.doctor.role.client.role.create.model.CreateDoctorRoleBody;
import com.afklm.repind.msv.doctor.role.client.role.global.model.DoctorRoleResponseModel;
import com.afklm.repind.msv.doctor.role.client.role.upsert.model.UpsertDoctorRoleRequest;
import com.afklm.repind.msv.doctor.role.criteria.role.UpsertDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.dto.DoctorAttributesDto;
import com.afklm.repind.msv.doctor.role.dto.DoctorAttributesUpdateDto;
import com.afklm.repind.msv.doctor.role.model.AirLineCode;
import com.afklm.repind.msv.doctor.role.model.Language;
import com.afklm.repind.msv.doctor.role.model.Speciality;
import com.afklm.repind.msv.doctor.role.utils.IConstants;
import com.afklm.repind.msv.doctor.role.wrapper.role.WrapperUpsertDoctorRoleResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UpsertRoleEncoder {

    private static final String SPEAK = "SPEAK";

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
      DoctorAttributesUpdateDto doctorAttributesUpdateDto = this.mapToDoctorAttributesUpdateDto(upsertDoctorRoleCriteria,upsertDoctorRoleRequest);
        upsertDoctorRoleRequest.setBody(doctorAttributesUpdateDto);
        return upsertDoctorRoleRequest;
    }

    public WrapperUpsertDoctorRoleResponse decode(DoctorRoleResponseModel upsertDoctorRoleResponse){
        String airlineCode = null;
        String speciality = null;

        if (upsertDoctorRoleResponse.getAirLineCode() != null) {
            airlineCode = upsertDoctorRoleResponse.getAirLineCode().getValue();
        }

        if (upsertDoctorRoleResponse.getSpeciality() != null) {
            speciality = upsertDoctorRoleResponse.getSpeciality().getValue();
        }
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
        response.setAirLineCode(airlineCode);
        response.setSpeciality(speciality);
        response.setLanguages(upsertDoctorRoleResponse.getLanguages());
        return response;
    }

    private DoctorAttributesUpdateDto mapToDoctorAttributesUpdateDto(UpsertDoctorRoleCriteria upsertDoctorRoleCriteria,UpsertDoctorRoleRequest upsertDoctorRoleRequest)
    {
        //Get language from relation list
        Set<Language> languages = new HashSet<>();
        if(upsertDoctorRoleCriteria.getRelationsList() != null) {
            for (String language : upsertDoctorRoleCriteria.getRelationsList().get(0).getValues()) {
                if (SPEAK.equals(upsertDoctorRoleCriteria.getRelationsList().get(0).getType())) {
                    languages.add(Language.builder().acronyme(language).value(language).build());
                }
            }
        }

        DoctorAttributesUpdateDto doctorAttributesUpdateDto = new DoctorAttributesUpdateDto();
        doctorAttributesUpdateDto.setDoctorStatus(upsertDoctorRoleRequest.getDoctorStatus());
        doctorAttributesUpdateDto.setDoctorId(upsertDoctorRoleRequest.getDoctorId());
        doctorAttributesUpdateDto.setRoleId(upsertDoctorRoleRequest.getRoleId());
        doctorAttributesUpdateDto.setSpeciality(Speciality.builder().value(upsertDoctorRoleRequest.getSpeciality()).build());
        doctorAttributesUpdateDto.setAirLineCode(AirLineCode.builder().value(upsertDoctorRoleRequest.getAirLineCode()).build());
        doctorAttributesUpdateDto.setEndDateRole(upsertDoctorRoleRequest.getEndDateRole());
        doctorAttributesUpdateDto.setSignatureSourceModification(upsertDoctorRoleRequest.getSignatureSource());
        doctorAttributesUpdateDto.setSiteModification(upsertDoctorRoleRequest.getSiteModification());
        doctorAttributesUpdateDto.setLanguages(languages);

        return doctorAttributesUpdateDto;
    }
}
