package com.afklm.repind.msv.search.gin.by.email.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class SearchGinByEmailChecker {

    public String checkSearchGinByEmail(String iEmail) throws BusinessException {
        return CheckerUtils.checkEmail(iEmail);
    }
}
