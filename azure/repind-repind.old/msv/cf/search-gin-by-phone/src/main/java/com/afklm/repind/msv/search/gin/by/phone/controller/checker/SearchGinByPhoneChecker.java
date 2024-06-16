package com.afklm.repind.msv.search.gin.by.phone.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class SearchGinByPhoneChecker {

    public String checkSearchGinByPhone(String iPhone) throws BusinessException {
        return CheckerUtils.checkPhone(iPhone);
    }
}
