package com.afklm.repind.msv.search.gin.by.phone.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.gin.by.phone.model.error.BusinessError;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CheckerUtils {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static String checkPhone(String iPhone) throws BusinessException {
        if(!StringUtils.isNotEmpty(iPhone)){
            throw new BusinessException(BusinessError.API_PHONE_IS_MISSING);
        }
        return iPhone;
    }
}
