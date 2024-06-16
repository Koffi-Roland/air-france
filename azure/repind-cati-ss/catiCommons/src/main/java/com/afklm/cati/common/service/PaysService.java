package com.afklm.cati.common.service;

import com.afklm.cati.common.criteria.PaysCriteria;
import com.afklm.cati.common.entity.Pays;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelPays;
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
public interface PaysService {

    /**
     * Retrieve all Variables
     *
     * @return list of Variables
     * @throws CatiCommonsException, JrafDaoException
     */
    List<ModelPays> getAllPays() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one Variables
     *
     * @return Variables
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<ModelPays> getPays(String key) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one Variables
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addPays(Pays pays) throws CatiCommonsException, JrafDaoException;

    /**
     * Update Variables
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updatePays(PaysCriteria paysCriteria) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove Variables
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removePays(String codePays) throws CatiCommonsException, JrafDaoException;
}
