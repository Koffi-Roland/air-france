package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.VariablesDTO;
import com.afklm.cati.common.entity.Variables;
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
public interface VariablesService {

    /**
     * Retrieve all Variables
     *
     * @return list of Variables
     * @throws CatiCommonsException, JrafDaoException
     */
    List<Variables> getAllVariables() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one Variables
     *
     * @return Variables
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<Variables> getVariables(String key) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one Variables
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addVariables(VariablesDTO variablesDTO) throws CatiCommonsException, JrafDaoException;

    /**
     * Update Variables
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateVariables(Variables variables) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove Variables
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeVariables(String key, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Retrieve all Variables that can be modified
     *
     * @return list of Variables
     * @throws CatiCommonsException, JrafDaoException
     */
    List<Variables> getAllVariablesAlterable() throws CatiCommonsException, JrafDaoException;
}
