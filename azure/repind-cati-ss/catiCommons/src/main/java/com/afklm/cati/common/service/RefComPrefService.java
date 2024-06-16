package com.afklm.cati.common.service;

import com.afklm.cati.common.entity.RefComPref;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefService.java
 * </p>
 * Service interface to manage RefComPref
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefComPrefService {

    /**
     * Retrieve all refComPref
     *
     * @return list of refComPref
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPref> getAllRefComPref() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refComPref
     *
     * @return refComPref
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefComPref> getRefComPref(Integer id) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refComPref
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefComPref(RefComPref refComPref, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refComPref
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefComPref(RefComPref refComPref, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refComPref
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefComPref(Integer id, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refComPrefMl by dgt id
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    Long countRefComPrefMlByDgt(Integer id) throws CatiCommonsException, JrafDaoException;
}
