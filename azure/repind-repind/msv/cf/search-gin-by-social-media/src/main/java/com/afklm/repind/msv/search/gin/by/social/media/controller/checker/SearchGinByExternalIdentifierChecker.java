package com.afklm.repind.msv.search.gin.by.social.media.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class SearchGinByExternalIdentifierChecker {

    public String checkSearchGinByExternalIdentifierId(String identifier) throws BusinessException {
        return CheckerUtils.checkExternalIdentifierId(identifier);
    }

    public String checkSearchGinByExternalIdentifierType(String type) throws BusinessException {
        return CheckerUtils.checkExternalIdentifierType(type);
    }
}
