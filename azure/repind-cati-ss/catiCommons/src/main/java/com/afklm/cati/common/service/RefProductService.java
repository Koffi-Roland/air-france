package com.afklm.cati.common.service;

import com.afklm.cati.common.entity.RefProduct;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefGroupService.java
 * </p>
 * Service interface to manage RefComPrefGroup
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefProductService {

    /**
     * Retrieve all refProduct
     *
     * @return list of refProduct
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefProduct> getAllRefProduct() throws CatiCommonsException, JrafDaoException;

    /**
     * @return list of refProduct
     * @throws CatiCommonsException, JrafDaoException
     */
    List<Integer> getAllCustomerRefProduct() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refProduct
     *
     * @return refProduct
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefProduct> getRefProduct(Integer id) throws CatiCommonsException, JrafDaoException;

}
