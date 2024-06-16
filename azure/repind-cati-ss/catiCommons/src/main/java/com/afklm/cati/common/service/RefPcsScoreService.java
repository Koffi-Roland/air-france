package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefPcsScoreDTO;
import com.afklm.cati.common.entity.RefPcsScore;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsScore;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefPcsScoreService.java
 * </p>
 * Service interface to manage RefPcsScore
 * <p>
 * Copyright : Copyright (c) 2022
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefPcsScoreService {

    /**
     * Retrieve PCS Scores by Type of factor
     *
     * @return list of PCS SCOREs
     * @throws CatiCommonsException, JrafDaoException
     */
    List<ModelRefPcsScore> getPcsScoreByFactorCode(String codeFactor) throws CatiCommonsException, JrafDaoException;

    /**
     * Post new RefPcsScore
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefPcsScore(RefPcsScoreDTO refPcsScoreDTO) throws CatiCommonsException, JrafDaoException;

    /**
     * Update RefPcsScore
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefPcsScore(RefPcsScore refPcsScore) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove RefPcsScore
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefPcsScore(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Get one RefPcsScore
     *
     * @return RefPcsScore
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefPcsScore> getRefPcsScore(String code) throws CatiCommonsException, JrafDaoException;

}
