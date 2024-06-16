package com.afklm.cati.common.service;

import com.afklm.cati.common.entity.RefProductComPrefGroup;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;

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
public interface RefGroupProductService {

    /**
     * Retrieve all RefProductComPrefGroup
     *
     * @return list of RefProductComPrefGroup
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefProductComPrefGroup> getAllRefGroupProduct() throws CatiCommonsException, JrafDaoException;

    List<RefProductComPrefGroup> getAllRefGroupProductByProductId(int productId) throws CatiCommonsException, JrafDaoException;

    RefProductComPrefGroup getRefGroupProduct(int id) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one RefProductComPrefGroup
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefGroupProduct(RefProductComPrefGroup refProductComPrefGroup, String matricule) throws CatiCommonsException, JrafDaoException;


    /**
     * Remove RefProductComPrefGroup
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefGroupProduct(RefProductComPrefGroup refProductComPrefGroup, String matricule) throws CatiCommonsException, JrafDaoException;

    public Long countRefProductComPrefGroupByGroupInfoId(Integer refComPrefGroupInfoId) throws CatiCommonsException, JrafDaoException;
}
