package com.afklm.repind.msv.delete.myAccount.controller.checher;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.delete.myAccount.criteria.DeleteMyAccountCriteria;
import com.afklm.repind.msv.delete.myAccount.utils.CheckerUtils;
import org.springframework.stereotype.Service;

@Service
public class DeleteMyAccountChecker {

    public DeleteMyAccountCriteria checkDeleteMyAccountData(String gin) throws BusinessException {
        return new DeleteMyAccountCriteria().withGin(CheckerUtils.ginChecker(gin));
    }
}
