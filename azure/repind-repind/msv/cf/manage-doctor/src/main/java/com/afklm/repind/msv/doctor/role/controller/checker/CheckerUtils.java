package com.afklm.repind.msv.doctor.role.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.doctor.role.model.RelationModel;
import com.afklm.repind.msv.doctor.role.model.error.BusinessError;
import com.afklm.repind.msv.doctor.role.utils.DoctorStatus;
import com.afklm.repind.msv.doctor.role.utils.IConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class CheckerUtils {
    public static final Integer GIN_MAX_LENGTH = 12;
    public static final Integer DOCTOR_ID_MAX_LENGTH = 20;

    public static String roleIdChecker(String roleId) throws BusinessException {
        if(StringUtils.isNotEmpty(roleId)){
            return roleId;
        }
        throw new BusinessException(BusinessError.API_ROLE_ID_IS_MISSING);
    }

    public static String ginChecker(String iGin) throws BusinessException {
        if(!StringUtils.isNotEmpty(iGin)){
            throw new BusinessException(BusinessError.API_GIN_IS_MISSING);
        }
        if(iGin.length() > GIN_MAX_LENGTH){
            throw new BusinessException(BusinessError.API_GIN_MISMATCH);
        }
        return iGin;
    }

    public static Date dateChecker(String iDate) throws BusinessException {
        try {
            return new SimpleDateFormat(IConstants.DATETIME_FORMATTER).parse(iDate);
        } catch (ParseException e) {
            //Nothing to do
        }
        throw new BusinessException(BusinessError.API_END_DATE_ROLE_MISMATCH);
    }

    public static String typeChecker(String iType) throws BusinessException {
        if(!StringUtils.isNotEmpty(iType)){
            throw new BusinessException(BusinessError.API_TYPE_IS_MISSING);
        }
        if(!IConstants.DOCTOR_TYPE.equalsIgnoreCase(iType)){
            throw new BusinessException(BusinessError.API_TYPE_MISMATCH);
        }
        return iType.toUpperCase();
    }

    public static String doctorIdChecker(String iDoctorId) throws BusinessException {
        if(!StringUtils.isNotEmpty(iDoctorId)){
            throw new BusinessException(BusinessError.API_DOCTOR_ID_IS_MISSING);
        }
        if(iDoctorId.length() > DOCTOR_ID_MAX_LENGTH){
            throw new BusinessException(BusinessError.API_DOCTOR_ID_MISMATCH);
        }
        return iDoctorId;
    }

    public static String siteChecker(String iSite) throws BusinessException {
        if(!StringUtils.isNotEmpty(iSite)){
            throw new BusinessException(BusinessError.API_SITE_CREATION_IS_MISSING);
        }
        return iSite;
    }

    public static String signatureChecker(String iSignature) throws BusinessException {
        if(!StringUtils.isNotEmpty(iSignature)){
            throw new BusinessException(BusinessError.API_SIGNATURE_CREATION_IS_MISSING);
        }
        return iSignature;
    }

    public static String airLineCodeChecker(String iAirLineCode) throws BusinessException {
        if(!StringUtils.isNotEmpty(iAirLineCode)){
            throw new BusinessException(BusinessError.API_AIR_LINE_CODE_IS_MISSING);
        }
        if(!IConstants.COMPANY_CODE_AF.equalsIgnoreCase(iAirLineCode) && !IConstants.COMPANY_CODE_KL.equalsIgnoreCase(iAirLineCode) ){
            throw new BusinessException(BusinessError.API_AIR_LINE_CODE_MISMATCH);
        }
        return iAirLineCode.toUpperCase();
    }

    public static String doctorStatusChecker(String iDoctorStatus) throws BusinessException {
        if(!StringUtils.isNotEmpty(iDoctorStatus)){
            throw new BusinessException(BusinessError.API_DOCTOR_STATUS_IS_MISSING);
        }
        if(!DoctorStatus.V.getAcronyme().equals(iDoctorStatus.toUpperCase())){
            throw new BusinessException(BusinessError.API_DOCTOR_STATUS_MISMATCH);
        }
        return iDoctorStatus.toUpperCase();
    }

    public static String specialityChecker(String iSpeciality) throws BusinessException {
        if(!StringUtils.isNotEmpty(iSpeciality)){
            throw new BusinessException(BusinessError.API_SPECIALITY_IS_MISSING);
        }
        return iSpeciality.toUpperCase();
    }

    public static void relationModelsChecker(Collection<RelationModel> iRelationModels) throws BusinessException {
        if(iRelationModels == null){
            throw new BusinessException(BusinessError.API_RELATION_LIST_IS_MISSING);
        }

    }


}
