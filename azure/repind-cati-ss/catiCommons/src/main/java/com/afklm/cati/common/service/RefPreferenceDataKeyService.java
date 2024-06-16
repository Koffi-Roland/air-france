package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefPreferenceDataKeyDTO;
import com.afklm.cati.common.entity.RefPreferenceDataKey;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * RefPreferenceDataKeyService.java
 *
 * @author m430152
 */
public interface RefPreferenceDataKeyService {

    /**
     * Retrieve all refPreferenceDataKey
     *
     * @return list of refPreferenceDataKey
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefPreferenceDataKey> getAllRefPreferenceDataKey() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refPreferenceDataKey
     *
     * @return refPreferenceType
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefPreferenceDataKey> getRefPreferenceDataKey(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refPreferenceDataKey
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefPreferenceDataKey(RefPreferenceDataKeyDTO refPreferenceDataKeyDTO) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refPreferenceDataKey
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefPreferenceDataKey(RefPreferenceDataKey refPreferenceDataKey) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refPreferenceDataKey
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefPreferenceDataKey(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refPreferenceKeyType by refPreferenceDataKey
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    Long countRefPreferenceKeyTypeByRefPreferenceDataKey(RefPreferenceDataKey refPreferenceDataKey)
            throws CatiCommonsException, JrafDaoException;
}
