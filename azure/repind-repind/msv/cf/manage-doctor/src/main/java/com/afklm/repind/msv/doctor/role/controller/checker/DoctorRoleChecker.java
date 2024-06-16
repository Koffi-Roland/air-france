package com.afklm.repind.msv.doctor.role.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.doctor.role.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.DeleteDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.RetrieveDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.criteria.role.UpsertDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.role.model.DoctorAttributesModel;
import org.springframework.stereotype.Service;

@Service
public class DoctorRoleChecker {

    public CreateDoctorRoleCriteria checkCreateDoctorRole(String iGin, String iType, String iDoctorStatus, String iEndDateRole, String iDoctorId, String iAirLineCode, String iSignatureCreation, String iSiteCreation, String iSpeciality, DoctorAttributesModel iBody) throws BusinessException {
        return new CreateDoctorRoleCriteria()
                .withGin(CheckerUtils.ginChecker(iGin))
                .withDoctorStatus(CheckerUtils.doctorStatusChecker(iDoctorStatus))
                .withEndDateRole(iEndDateRole)
                .withSignatureSourceCreation(CheckerUtils.signatureChecker(iSignatureCreation))
                .withSignatureSiteCreation(CheckerUtils.siteChecker(iSiteCreation))
                .withType(CheckerUtils.typeChecker(iType))
                .withDoctorId(CheckerUtils.doctorIdChecker(iDoctorId))
                .withAirLineCode(CheckerUtils.airLineCodeChecker(iAirLineCode))
                .withSpeciality(CheckerUtils.specialityChecker(iSpeciality))
                .withRelationsList(iBody.getRelationsList());
    }

    public DeleteDoctorRoleCriteria checkDeleteDoctorRole(String contractNumber) {
        return new DeleteDoctorRoleCriteria()
                .withRoleId(contractNumber);
    }

    public RetrieveDoctorRoleCriteria checkRetrieveDoctorRole(String contractNumber) {
        return new RetrieveDoctorRoleCriteria().withRoleId(contractNumber);
    }

    public UpsertDoctorRoleCriteria checkUpsertDoctorRole(String roleId, String iEndDateRole, String iDoctorStatus, String iSignatureModification, String iSiteModification, String iDoctorId, String iAirLineCode, String iSpeciality, DoctorAttributesModel iDoctorAttributesModel) throws BusinessException {
        return new UpsertDoctorRoleCriteria()
                .withRoleId(CheckerUtils.roleIdChecker(roleId))
                .withEndDateRole(iEndDateRole)
                .withDoctorId(CheckerUtils.doctorIdChecker(iDoctorId))
                .withSignatureSource(CheckerUtils.signatureChecker(iSignatureModification))
                .withSiteModification(CheckerUtils.siteChecker(iSiteModification))
                .withDoctorStatus(CheckerUtils.doctorStatusChecker(iDoctorStatus))
                .withAirLineCode(CheckerUtils.airLineCodeChecker(iAirLineCode))
                .withSpeciality(CheckerUtils.specialityChecker(iSpeciality))
                .withRelationsList(iDoctorAttributesModel.getRelationsList());
    }


}
