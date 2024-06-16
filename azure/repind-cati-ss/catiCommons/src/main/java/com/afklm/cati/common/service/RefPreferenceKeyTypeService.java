package com.afklm.cati.common.service;

import com.afklm.cati.common.criteria.RefPreferenceKeyTypeCriteria;
import com.afklm.cati.common.entity.RefPreferenceKeyType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.NotFoundException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * RefPreferenceKeyTypeService.java
 *
 * @author m430152
 */
public interface RefPreferenceKeyTypeService {

    /**
     * Retrieve all refPreferenceKeyType
     *
     * @return list of refPreferenceKeyType
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefPreferenceKeyType> getAllRefPreferenceKeyType() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refPreferenceKeyType
     *
     * @return refPreferenceType
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefPreferenceKeyType> getRefPreferenceKeyType(Integer refId) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refPreferenceKeyType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefPreferenceKeyType(RefPreferenceKeyTypeCriteria refPreferenceKeyTypeCriteria) throws CatiCommonsException,
            JrafDaoException;

    /**
     * Update refPreferenceKeyType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefPreferenceKeyType(RefPreferenceKeyTypeCriteria refPreferenceKeyTypeCriteria) throws CatiCommonsException, JrafDaoException, NotFoundException;

    /**
     * Remove refPreferenceKeyType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefPreferenceKeyType(Integer refId) throws CatiCommonsException, JrafDaoException;
}
