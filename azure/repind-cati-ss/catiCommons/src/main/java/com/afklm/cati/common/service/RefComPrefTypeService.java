package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefComPrefTypeDTO;
import com.afklm.cati.common.entity.RefComPrefType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefTypeService.java
 * </p>
 * Service interface to manage RefComPrefType
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefComPrefTypeService {

    /**
     * Retrieve all refComPrefType
     *
     * @return list of refComPrefType
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefType> getAllRefComPrefType() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refComPrefType
     *
     * @return refComPrefType
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefComPrefType> getRefComPrefType(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refComPrefType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefComPrefType(RefComPrefTypeDTO refComPrefTypeDTO, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refComPrefType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefComPrefType(RefComPrefType refComPreftype, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refComPrefType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefComPrefType(String code, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refComPref by type
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    Long countRefComPrefByType(RefComPrefType type) throws CatiCommonsException, JrafDaoException;
}
