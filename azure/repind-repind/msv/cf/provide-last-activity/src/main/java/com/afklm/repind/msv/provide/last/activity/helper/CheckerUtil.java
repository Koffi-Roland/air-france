package com.afklm.repind.msv.provide.last.activity.helper;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.last.activity.model.error.BusinessError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Checker utility service used to checker business value
 */
@Service
public class CheckerUtil {


    public static final Integer GIN_MAX_LENGTH = 12;

    /**
     * Check gin value. if not empty and not match
     *
     * @param gin individual number
     * @throws BusinessException business exception
     */

    public String ginChecker(String gin) throws BusinessException
    {
        if (!StringUtils.isNotEmpty(gin.trim()))
        {
            throw new BusinessException(BusinessError.API_GIN_IS_MISSING);
        }
        if (gin.length() > GIN_MAX_LENGTH || gin.length() < GIN_MAX_LENGTH)
        {
            throw new BusinessException(BusinessError.API_GIN_MISMATCH);
        }
        return gin;
    }

}
