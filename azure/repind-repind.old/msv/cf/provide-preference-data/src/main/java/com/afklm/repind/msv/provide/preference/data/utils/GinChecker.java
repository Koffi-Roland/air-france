package com.afklm.repind.msv.provide.preference.data.utils;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.preference.data.models.error.BusinessError;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

public final class GinChecker {

    private GinChecker(){}
    public static String checkGin(String gin) throws BusinessException{
        if(gin.length() > 12){
            throw new BusinessException(BusinessError.GIN_FORMAT_WRONG);
        }
        return StringUtils.leftPad(gin,12,"0");
    }
}
