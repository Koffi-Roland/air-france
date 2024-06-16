package com.afklm.repind.msv.search.gin.by.social.media.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.model.error.ApiError;
import com.afklm.repind.msv.search.gin.by.social.media.model.error.BusinessError;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CheckerUtils {

    private static final Pattern VALID_SOCIAL_IDENTIFIER_REGEX =
            Pattern.compile("^.{1,500}$", Pattern.CASE_INSENSITIVE);

    public static String checkExternalIdentifierId(String identifier) throws BusinessException {
        if(!StringUtils.isNotEmpty(identifier)){
            throw new BusinessException(ApiError.API_MISSING_REQUEST_PARAMETER);
        }
        if(!VALID_SOCIAL_IDENTIFIER_REGEX.matcher(identifier).find()){
            //API_SOCIAL_IDENTIFIER_MISMATCH
            throw new BusinessException(BusinessError.API_SOCIAL_IDENTIFIER_MISMATCH);
        }
        return identifier;
    }

    public static String checkExternalIdentifierType(String type) throws BusinessException {
        if(!StringUtils.isNotEmpty(type)){
            throw new BusinessException(ApiError.API_MISSING_REQUEST_PARAMETER);
        }
        return type;
    }
}