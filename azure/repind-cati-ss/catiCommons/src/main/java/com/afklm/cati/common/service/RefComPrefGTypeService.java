package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefComPrefGTypeDTO;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefGTypeService.java
 * </p>
 * Service interface to manage RefComPrefGType
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefComPrefGTypeService {

    /**
     * Retrieve all refComPrefGType
     *
     * @return list of refComPrefGType
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefGType> getAllRefComPrefGType() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refComPrefGType
     *
     * @return refComPrefGType
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefComPrefGType> getRefComPrefGType(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refComPrefGType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefComPrefGType(RefComPrefGTypeDTO refComPrefGTypeDTO, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refComPrefGType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefComPrefGType(RefComPrefGType refComPrefGType, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refComPrefGType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefComPrefGType(String code, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refComPref by groupType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    Long countRefComPrefByGType(RefComPrefGType groupType) throws CatiCommonsException, JrafDaoException;
}
