package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefComPrefMediaDTO;
import com.afklm.cati.common.entity.RefComPrefMedia;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefMediaService.java
 * </p>
 * Service interface to manage RefComPrefMedia
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefComPrefMediaService {

    /**
     * Retrieve all refComPrefMedia
     *
     * @return list of refComPrefMedia
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefMedia> getAllRefComPrefMedia() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refComPrefMedia
     *
     * @return refComPrefMedia
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefComPrefMedia> getRefComPrefMedia(String code) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refComPrefMedia
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefComPrefMedia(RefComPrefMediaDTO refComPrefMediaDTO, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refComPrefMedia
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefComPrefMedia(RefComPrefMedia refComPrefMedia, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refComPrefMedia
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefComPrefMedia(String code, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refComPref by media
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    Long countRefComPrefByMedia(RefComPrefMedia media) throws CatiCommonsException, JrafDaoException;
}
