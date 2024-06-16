package com.afklm.repind.msv.provide.last.activity.service.impl;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;
import com.afklm.repind.msv.provide.last.activity.helper.CheckerUtil;
import com.afklm.repind.msv.provide.last.activity.model.error.BusinessError;
import com.afklm.repind.msv.provide.last.activity.repository.LastActivityRepository;
import com.afklm.repind.msv.provide.last.activity.service.LastActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Last activity service implementation
 */
@Service
@RequiredArgsConstructor
public class LastActivityServiceImpl implements LastActivityService {

    /**
     * Last activity repository - inject by spring
     */
    private final LastActivityRepository lastActivityRepository;

    /**
     * Check util service - inject by spring
     */
    private final CheckerUtil checkerUtil;

    /**
     * @param gin Individual GIN number
     * @return LastActivityModel contains information details about last activity
     */
    @Override
    @Transactional(readOnly = true)
    public LastActivity getLastActivityByGin(String gin) throws BusinessException
    {
        //Checking gin if value is not empty and match gin regex
        this.checkerUtil.ginChecker(gin);
        Optional<LastActivity> optionalLastActivity = this.lastActivityRepository.findByGin(gin);
        if(optionalLastActivity.isPresent())
        {
            return optionalLastActivity.get();
        }
        else
        {
            // If gin has not last activity available throw 404 business error
            throw new BusinessException(BusinessError.GIN_NOT_FOUND);
        }
    }

}
