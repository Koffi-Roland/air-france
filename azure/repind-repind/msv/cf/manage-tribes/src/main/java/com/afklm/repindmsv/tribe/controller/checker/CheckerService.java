package com.afklm.repindmsv.tribe.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.error.BusinessError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CheckerService {

    public  String nameChecker(String name) throws BusinessException {
        if(StringUtils.isNotEmpty(name)){
            return name;
        }
        throw new BusinessException(BusinessError.NAME_IS_MISSING);
    }

    public  String typeChecker(String type) throws BusinessException {
        if(StringUtils.isNotEmpty(type)){
            return type;
        }
        throw new BusinessException(BusinessError.TYPE_IS_MISSING);
    }

    public  String ginChecker(String gin) throws BusinessException {
        if(!StringUtils.isNotEmpty(gin)){
            throw new BusinessException(BusinessError.GIN_IS_MISSING);
        }
        if (gin.length() != 12 || gin.contains(" ")) {
            throw new BusinessException(BusinessError.GIN_MISMATCH);

        }
        return gin;
    }

    public  String applicationChecker(String application) throws BusinessException {
        if(StringUtils.isNotEmpty(application)){
            return application;
        }
        throw new BusinessException(BusinessError.APPLICATION_IS_MISSING);
    }

    public  String tribeIdChecker(String tribeId) throws BusinessException {
        if(StringUtils.isNotEmpty(tribeId)){
            return tribeId;
        }
        throw new BusinessException(BusinessError.TRIBE_ID_IS_MISSING);
    }


    public  String statusChecker(String status) throws BusinessException {
        if(!StringUtils.isNotEmpty(status)){
            throw new BusinessException(BusinessError.STATUS_IS_MISSING);
        }
        if (!StatusEnum.PENDING.getName().equals(status) && !StatusEnum.VALIDATED.getName().equals(status)
                && !StatusEnum.REFUSED.getName().equals(status)
                && !StatusEnum.DELETED.getName().equals(status)) {
            throw new BusinessException(BusinessError.STATUS_MISMATCH);

        }
        return status;
    }

}
