package com.afklm.repind.msv.search.gin.by.lastname.firstname.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class SearchGinByLastnameAndFirstnameChecker {

    public String checkSearchGinByLastname(String lastname) throws BusinessException {
        return CheckerUtils.checkLastname(lastname);
    }

    public String checkSearchGinByFirstname(String firstname) throws BusinessException {
        return CheckerUtils.checkFirstname(firstname);
    }
}
