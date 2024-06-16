package com.afklm.cati.common.service;

import com.afklm.cati.common.entity.RefComPrefDgt;
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
public interface RefComPrefDgtService {

    /**
     * Retrieve all refComPref
     *
     * @return list of refComPref
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefDgt> getAllRefComPrefDgt() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refComPref
     *
     * @return refComPref
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefComPrefDgt> getRefComPrefDgt(Integer id) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refComPref
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefComPrefDgt(RefComPrefDgt refComPref, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refComPref
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefComPrefDgt(RefComPrefDgt refComPref, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refComPref
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefComPrefDgt(Integer id, String matricule) throws CatiCommonsException, JrafDaoException;
}
