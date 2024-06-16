package com.afklm.repind.msv.search.gin.by.lastname.firstname.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.model.error.ApiError;
import com.afklm.repind.msv.search.gin.by.lastname.firstname.model.error.BusinessError;
import org.apache.commons.lang3.ObjectUtils;

import java.util.regex.Pattern;

public class CheckerUtils {

    private static final Pattern VALID_LASTNAME_REGEX =
            Pattern.compile("^[\\D]+$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_FIRSTNAME_REGEX =
            Pattern.compile("^[\\D]+$", Pattern.CASE_INSENSITIVE);

    private CheckerUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String checkLastname(String lastname) throws BusinessException {
        if(!ObjectUtils.isNotEmpty(lastname)){
            throw new BusinessException(ApiError.API_MISSING_REQUEST_PARAMETER);
        }
        if(!VALID_LASTNAME_REGEX.matcher(lastname).find()){
            throw new BusinessException(BusinessError.API_LASTNAME_MISMATCH);
        }
        return lastname;
    }

    public static String checkFirstname(String firstname) throws BusinessException {
        if(!ObjectUtils.isNotEmpty(firstname)){
            throw new BusinessException(ApiError.API_MISSING_REQUEST_PARAMETER);
        }
        if(!VALID_FIRSTNAME_REGEX.matcher(firstname).find()){
            throw new BusinessException(BusinessError.API_FIRSTNAME_MISMATCH);
        }
        return firstname;
    }
}
