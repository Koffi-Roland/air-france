package com.afklm.repind.msv.search.gin.by.email.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.email.model.error.BusinessError;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CheckerUtils {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$", Pattern.CASE_INSENSITIVE);

    public static String checkEmail(String iEmail) throws BusinessException {
        if(!StringUtils.isNotEmpty(iEmail)){
            throw new BusinessException(BusinessError.API_EMAIL_IS_MISSING);
        }
        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(iEmail).find()){
            throw new BusinessException(BusinessError.API_EMAIL_MISMATCH);
        }
        return iEmail;
    }
}
