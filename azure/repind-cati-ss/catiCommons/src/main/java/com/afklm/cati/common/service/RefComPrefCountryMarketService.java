package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefComPrefCountryMarketDTO;
import com.afklm.cati.common.entity.RefComPrefCountryMarket;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefCountryMarketService.java
 * </p>
 * Service interface to manage RefComPrefCountryMarket
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefComPrefCountryMarketService {

    /**
     * Retrieve all refComPrefCountryMarket
     *
     * @return list of refComPrefCountryMarket
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefCountryMarket> getAllRefComPrefCountryMarket() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refComPrefCountryMarket
     *
     * @return refComPrefCountryMarket
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefComPrefCountryMarket> getRefComPrefCountryMarket(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refComPrefCountryMarket
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefComPrefCountryMarket(RefComPrefCountryMarketDTO refComPrefCountryMarketDTO, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refComPrefCountryMarket
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefComPrefCountryMarket(RefComPrefCountryMarket refComPrefCountryMarket, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refComPrefCountryMarket
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefComPrefCountryMarket(String code, String matricule) throws CatiCommonsException, JrafDaoException;
}
