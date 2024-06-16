package com.afklm.repind.msv.provide.last.activity.service;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.last.activity.entity.LastActivity;

import java.util.Optional;

/**
 * Last activity service
 *  SOLID principle - Dependency Inversion Principle
 * A class must depend on its abstraction, not its implementation
 */
public interface LastActivityService {

    /**
     * Get last activity according to the GIN given
     *
     * @param gin Individual GIN number
     * @return LastActivityModel contains information details about last activity
     */
   LastActivity  getLastActivityByGin(String gin) throws BusinessException;

}
