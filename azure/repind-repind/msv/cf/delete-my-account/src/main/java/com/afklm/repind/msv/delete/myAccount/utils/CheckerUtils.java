package com.afklm.repind.msv.delete.myAccount.utils;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.delete.myAccount.model.error.BusinessError;
import org.apache.commons.lang3.StringUtils;

public class CheckerUtils {

    public static final Integer GIN_MAX_LENGTH = 12;

    public static String ginChecker(String iGin) throws BusinessException {
        if(!StringUtils.isNotEmpty(iGin)){
            throw new BusinessException(BusinessError.API_GIN_IS_MISSING);
        }
        if(iGin.length() < GIN_MAX_LENGTH || iGin.length() > GIN_MAX_LENGTH){
            throw new BusinessException(BusinessError.API_GIN_MISMATCH);
        }
        return iGin;
    }
}
