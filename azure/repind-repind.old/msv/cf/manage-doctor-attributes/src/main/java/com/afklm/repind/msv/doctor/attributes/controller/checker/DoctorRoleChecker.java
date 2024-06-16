package com.afklm.repind.msv.doctor.attributes.controller.checker;

import com.afklm.repind.msv.doctor.attributes.criteria.role.*;
import com.afklm.repind.msv.doctor.attributes.model.DoctorAttributesModel;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class DoctorRoleChecker {

    public CreateDoctorRoleCriteria checkCreateDoctorRole(IndividuCriteria individuCriteria, String endDateRole, String airLineCode, String roleId ,String signatureSource,String siteCreation,  DoctorAttributesModel iBody) throws BusinessException {
        return new CreateDoctorRoleCriteria()
                .withGin(CheckerUtils.ginChecker(individuCriteria.getGin()))
                .withDoctorStatus(CheckerUtils.doctorStatusChecker(individuCriteria.getDoctorStatus()))
                .withEndDateRole(CheckerUtils.diplomaDateChecker(endDateRole))
                .withDoctorId(CheckerUtils.doctorIdChecker(individuCriteria.getDoctorId()))
                .withAirLineCode(CheckerUtils.airLineCodeChecker(airLineCode))
                .withRoleId(CheckerUtils.roleIdChecker(roleId))
                .withSignatureCreation(CheckerUtils.signatureCreationChecker(signatureSource))
                .withSiteCreation(CheckerUtils.siteCreationChecker(siteCreation))
                .withSpeciality(CheckerUtils.specialityChecker(individuCriteria.getSpeciality()))
                .withRelationsList(CheckerUtils.relationModelsChecker(iBody.getRelationsList()));
    }

    public UpdateDoctorRoleCriteria checkUpdateDoctorRole(String roleId, String doctorStatus , String endDateRole , String doctorId, String speciality, String airLineCode,  String signatureSource, String siteModification , DoctorAttributesModel iDoctorAttributesModel) throws BusinessException {
        return new UpdateDoctorRoleCriteria()
                .withRoleId(CheckerUtils.roleIdChecker(roleId))
                .withDoctorStatus(CheckerUtils.doctorStatusChecker(doctorStatus))
                .withEndDateRole(CheckerUtils.diplomaDateChecker(endDateRole))
                .withDoctorId(CheckerUtils.doctorIdChecker(doctorId))
                .withSpeciality(CheckerUtils.specialityChecker(speciality))
                .withAirLineCode(CheckerUtils.airLineCodeChecker(airLineCode))
                .withSignatureSourceModification(CheckerUtils.signatureCreationChecker(signatureSource))
                .withSiteModification(CheckerUtils.siteCreationChecker(siteModification))
                .withRelationsList(CheckerUtils.relationModelsChecker(iDoctorAttributesModel.getRelationsList()));
    }

    public DeleteDoctorRoleCriteria checkDeleteDoctorRole(String iRoleId) throws BusinessException {
        return new DeleteDoctorRoleCriteria()
                .withRoleId(CheckerUtils.roleIdChecker(iRoleId));
    }

    public RetrieveDoctorRoleCriteria checkRetrieveDoctorRole(String iRoleId) throws BusinessException {
        return new RetrieveDoctorRoleCriteria()
                .withRoleId(CheckerUtils.roleIdChecker(iRoleId));
    }
}
