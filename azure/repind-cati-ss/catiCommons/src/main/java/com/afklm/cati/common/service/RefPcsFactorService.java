package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefPcsFactorDTO;
import com.afklm.cati.common.entity.RefPcsFactor;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsFactor;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefPcsFactorService.java
 * </p>
 * Service interface to manage RefPcsFactor
 * <p>
 * Copyright : Copyright (c) 2022
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefPcsFactorService {

    /**
     * Retrieve all PCS FACTORs
     *
     * @return list of PCS FACTORs
     * @throws CatiCommonsException, JrafDaoException
     */
    List<ModelRefPcsFactor> getAllPcsFactor() throws CatiCommonsException, JrafDaoException;

    /**
     * Post one RefPcsFactorType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefPcsFactor(RefPcsFactorDTO refPcsFactorDTO) throws CatiCommonsException, JrafDaoException;

    /**
     * Update RefPcsFactorType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefPcsFactor(RefPcsFactor refPcsFactor) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove RefPcsFactorType
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefPcsFactor(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Get one RefPcsFactor
     *
     * @return RefPcsFactor
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefPcsFactor> getRefPcsFactor(String code) throws CatiCommonsException, JrafDaoException;

}
