package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.entity.RefProduct;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefProductRepository;
import com.afklm.cati.common.service.RefProductService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefGroupImpl.java
 * </p>
 * Service Implementation to manage RefComPrefGroup
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefProductImpl implements RefProductService {

    @Autowired
    private RefProductRepository refProductRepo;


    @Override
    public List<RefProduct> getAllRefProduct() throws CatiCommonsException, JrafDaoException {
        return refProductRepo.findAll();
    }


    @Override
    public Optional<RefProduct> getRefProduct(Integer id) throws CatiCommonsException, JrafDaoException {
        return refProductRepo.findById(id);
    }


    @Override
    public List<Integer> getAllCustomerRefProduct() throws CatiCommonsException, JrafDaoException {
        return refProductRepo.getAllCustomerRefProduct();
    }
}
