package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefPreferenceTypeDTO;
import com.afklm.cati.common.entity.RefPreferenceType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * RefPreferenceTypeService.java
 *
 * @author m430152
 */
public interface RefPreferenceTypeService {

    /**
     * Retrieve all refPreferenceType
     *
     * @return list of refPreferenceType
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefPreferenceType> getAllRefPreferenceType() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refPreferenceType
     *
     * @return refPreferenceType
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefPreferenceType> getRefPreferenceType(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refPreferenceType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefPreferenceType(RefPreferenceTypeDTO refPreferenceTypeDTO) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refPreferenceType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefPreferenceType(RefPreferenceType refPreferenceType) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refPreferenceType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefPreferenceType(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refPreferenceKeyType by refPreferenceType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    Long countRefPreferenceKeyTypeByRefPreferenceType(RefPreferenceType refPreferenceType)
            throws CatiCommonsException, JrafDaoException;
}
