package com.afklm.repind.msv.doctor.attributes.controller.checker;

import com.afklm.repind.msv.doctor.attributes.criteria.role.*;
import com.afklm.repind.msv.doctor.attributes.dto.DoctorAttributesDto;
import com.afklm.repind.msv.doctor.attributes.dto.DoctorAttributesUpdateDto;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class DoctorRoleChecker {

    public CreateDoctorRoleCriteria checkCreateDoctorRole(DoctorAttributesDto doctorAttributesDto) throws BusinessException {
        String airlineCode = null;
        String speciality = null;
        if (doctorAttributesDto.getAirLineCode() != null) {
            airlineCode = doctorAttributesDto.getAirLineCode().getValue();
        }

        if (doctorAttributesDto.getSpeciality() != null) {
            speciality = doctorAttributesDto.getSpeciality().getValue();
        }
        return new CreateDoctorRoleCriteria()
                .withGin(CheckerUtils.ginChecker(doctorAttributesDto.getGin()))
                .withDoctorStatus(CheckerUtils.doctorStatusChecker(doctorAttributesDto.getDoctorStatus()))
                .withEndDateRole(CheckerUtils.diplomaDateChecker(doctorAttributesDto.getEndDateRole()))
                .withDoctorId(CheckerUtils.doctorIdChecker(doctorAttributesDto.getDoctorId()))
                .withAirLineCode(CheckerUtils.airLineCodeChecker(airlineCode))
                .withRoleId(CheckerUtils.roleIdChecker(doctorAttributesDto.getRoleId()))
                .withSignatureSourceCreation(CheckerUtils.signatureCreationChecker(doctorAttributesDto.getSignatureSourceCreation()))
                .withSiteCreation(CheckerUtils.siteCreationChecker(doctorAttributesDto.getSiteCreation()))
                .withSpeciality(CheckerUtils.specialityChecker(speciality))
                .withLanguages(doctorAttributesDto.getLanguages());
    }

    public RetrieveDoctorRoleCriteria checkRetrieveDoctorRole(String iRoleId) throws BusinessException {
        return new RetrieveDoctorRoleCriteria()
                .withRoleId(CheckerUtils.roleIdChecker(iRoleId));
    }

    public UpdateDoctorRoleCriteria checkUpdateDoctorRole(String roleId, DoctorAttributesUpdateDto doctorAttributesUpdateDto) throws BusinessException {
        String airlineCode = null;
        String speciality = null;
        if (doctorAttributesUpdateDto.getAirLineCode() != null) {
            airlineCode = doctorAttributesUpdateDto.getAirLineCode().getValue();
        }

        if (doctorAttributesUpdateDto.getSpeciality() != null) {
            speciality = doctorAttributesUpdateDto.getSpeciality().getValue();
        }
        return new UpdateDoctorRoleCriteria()
                .withRoleId(CheckerUtils.roleIdChecker(roleId))
                .withDoctorStatus(CheckerUtils.doctorStatusChecker(doctorAttributesUpdateDto.getDoctorStatus()))
                .withEndDateRole(CheckerUtils.diplomaDateChecker(doctorAttributesUpdateDto.getEndDateRole()))
                .withDoctorId(CheckerUtils.doctorIdChecker(doctorAttributesUpdateDto.getDoctorId()))
                .withSpeciality(CheckerUtils.specialityChecker(speciality))
                .withAirLineCode(CheckerUtils.airLineCodeChecker(airlineCode))
                .withSignatureSourceModification(CheckerUtils.signatureCreationChecker(doctorAttributesUpdateDto.getSignatureSourceModification()))
                .withSiteModification(CheckerUtils.siteCreationChecker(doctorAttributesUpdateDto.getSiteModification()))
                .withLanguages(doctorAttributesUpdateDto.getLanguages());
    }

    public DeleteDoctorRoleCriteria checkDeleteDoctorRole(String iRoleId) throws BusinessException {
        return new DeleteDoctorRoleCriteria()
                .withRoleId(CheckerUtils.roleIdChecker(iRoleId));
    }

}
