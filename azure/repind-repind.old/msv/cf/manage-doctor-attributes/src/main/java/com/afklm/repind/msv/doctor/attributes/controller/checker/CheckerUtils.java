package com.afklm.repind.msv.doctor.attributes.controller.checker;

import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import com.afklm.repind.msv.doctor.attributes.model.error.BusinessError;
import com.afklm.repind.msv.doctor.attributes.utils.*;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CheckerUtils {
    public static final String DATETIME_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final Integer GIN_MAX_LENGTH = 12;
    public static final Integer DOCTOR_ID_MAX_LENGTH = 20;

    public static String roleIdChecker(String iRoleId) throws BusinessException {
        if(StringUtils.isNotEmpty(iRoleId)){
            return iRoleId;
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

    public static Date signatureDateChecker(String iDate) throws BusinessException {
        try {
            return new SimpleDateFormat(DATETIME_FORMATTER).parse(iDate);
        } catch (ParseException e) {
            //Nothing to do
        }
        throw new BusinessException(BusinessError.API_SIGNATURE_DATE_MISMATCH);
    }

    public static Date diplomaDateChecker(String iDate) throws BusinessException {
        if(StringUtils.isEmpty(iDate)){
            return null;
        }
        try {
            return new SimpleDateFormat(DATETIME_FORMATTER).parse(iDate);
        } catch (ParseException e) {
            //Nothing to do
        }
        throw new BusinessException(BusinessError.API_END_DATE_ROLE_MISMATCH);
    }


    public static String typeChecker(String iType) throws BusinessException {
        if(!StringUtils.isNotEmpty(iType)){
            throw new BusinessException(BusinessError.API_TYPE_IS_MISSING);
        }
        if(!IConstants.DOCTOR_TYPE.equals(iType.toUpperCase())){
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

    public static String siteCreationChecker(String iSiteCreation) throws BusinessException {
        if(!StringUtils.isNotEmpty(iSiteCreation)){
            throw new BusinessException(BusinessError.API_SITE_CREATION_IS_MISSING);
        }
        return iSiteCreation;
    }

    public static String signatureCreationChecker(String iSignatureCreation) throws BusinessException {
        if(!StringUtils.isNotEmpty(iSignatureCreation)){
            throw new BusinessException(BusinessError.API_SIGNATURE_CREATION_IS_MISSING);
        }
        if(!ESource.CBS.getValue().equalsIgnoreCase(iSignatureCreation) && !ESource.CAPI.getValue().equalsIgnoreCase(iSignatureCreation)) {
            throw new BusinessException(BusinessError.API_SIGNATURE_CREATION_VALUE_MISMATCH);
        }
        return iSignatureCreation;
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

    public static Set<RelationModel> relationModelsChecker(Collection<RelationModel> iRelationModels) throws BusinessException {
        Set<RelationModel> relationModels = new HashSet<>();
        if(iRelationModels == null){
            throw new BusinessException(BusinessError.API_RELATION_LIST_IS_MISSING);
        }
        List<String> types = iRelationModels.stream().map(RelationModel::getType).collect(Collectors.toList());
        if(types.size() != ERelationType.values().length){
            throw new BusinessException(BusinessError.API_INCORRECT_NUMBER_OF_TYPE_VALUES_SENT_MISMATCH);
        }
        if(!ERelationType.containAll(types)){
            throw new BusinessException(BusinessError.API_RELATION_TYPE_MISMATCH);
        }
        for (RelationModel iRelationModel : iRelationModels) {
            relationModels.add(relationModelChecker(iRelationModel));
        }
        return relationModels;
    }

    public static RelationModel relationModelChecker(RelationModel iRelationModel) throws BusinessException{
        String type = iRelationModel.getType();
        if(StringUtils.isEmpty(type)){
            throw new BusinessException(BusinessError.API_RELATION_TYPE_IS_MISSING);
        }
        if(iRelationModel.getValues() == null){
            throw new BusinessException(BusinessError.API_RELATION_VALUE_IS_MISSING);
        }
        if(!ERelationType.checkSizeOfRelationType(iRelationModel)){
            throw new BusinessException(BusinessError.API_INCORRECT_NUMBER_OF_TYPE_VALUES_SENT_MISMATCH);
        }
        return checkRelationModel(iRelationModel);
    }


    private static RelationModel checkRelationModel(RelationModel iRelationModel) throws BusinessException {
        ERelationType eRelationType = ERelationType.contains(iRelationModel.getType());
        if(eRelationType == null){
            throw new BusinessException(BusinessError.API_RELATION_TYPE_MISMATCH);
        }
        Set<String> iValues = new HashSet<>();
        for (String value : iRelationModel.getValues()) {
            IAttributesEnum attributesEnum = eRelationType.getFunction().apply(value);
            if(attributesEnum == null){
                throw new BusinessException(BusinessError.API_RELATION_VALUES_MISMATCH);
            }else{
                iValues.add(attributesEnum.getValue());
            }
        }
        return new RelationModel().withType(eRelationType.getValue()).withValues(iValues.stream().collect(Collectors.toList()));
    }

    public static String specialityChecker(String speciality) throws BusinessException {
        if(!StringUtils.isNotEmpty(speciality)){
            throw new BusinessException(BusinessError.API_SPECIALITY_IS_MISSING);
        }
        if(speciality.contains(",")){
            throw new BusinessException(BusinessError.API_INCORRECT_NUMBER_SPECIALITY_VALUES_SENT_MISMATCH);
        }
        ESpeciality eSpeciality = ESpeciality.contains(speciality);
        if(eSpeciality == null){
            throw new BusinessException(BusinessError.API_SPECIALITY_VALUES_MISMATCH);
        }
        return eSpeciality.getValue();
    }
}
