package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefComPrefDomainDTO;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefDomainService.java
 * </p>
 * Service interface to manage RefComPrefDomain
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefComPrefDomainService {

    /**
     * Retrieve all refComPrefDomain
     *
     * @return list of refComPrefDomain
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefDomain> getAllRefComPrefDomain() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refComPrefDomain
     *
     * @return refComPrefDomain
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefComPrefDomain> getRefComPrefDomain(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refComPrefDomain
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefComPrefDomain(RefComPrefDomainDTO refComPrefDomainDTO, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refComPrefDomain
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefComPrefDomain(RefComPrefDomain refComPrefDomain, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refComPrefDomain
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefComPrefDomain(String code, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refComPref by domain
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    Long countRefComPrefByDomain(RefComPrefDomain domain) throws CatiCommonsException, JrafDaoException;
}
