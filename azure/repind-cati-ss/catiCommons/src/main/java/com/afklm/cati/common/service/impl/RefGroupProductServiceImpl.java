package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.entity.RefProduct;
import com.afklm.cati.common.entity.RefProductComPrefGroup;
import com.afklm.cati.common.entity.RefProductComPrefGroupId;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefGroupProductRepository;
import com.afklm.cati.common.service.RefComPrefGroupInfoService;
import com.afklm.cati.common.service.RefGroupProductService;
import com.afklm.cati.common.service.RefProductService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefDgtImpl.java
 * </p>
 * Service Implementation to manage RefComPrefDgt
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefGroupProductServiceImpl implements RefGroupProductService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefGroupProductServiceImpl.class);

    @Autowired
    private RefGroupProductRepository refGroupProductRepository;

    @Autowired
    private RefProductService refProductService;

    @Autowired
    private RefComPrefGroupInfoService refComPrefGroupInfoService;

    @Override
    public List<RefProductComPrefGroup> getAllRefGroupProduct() throws CatiCommonsException, JrafDaoException {
        return refGroupProductRepository.findAll();
    }

    @Override
    public void addRefGroupProduct(RefProductComPrefGroup refProductComPrefGroup, String username) throws CatiCommonsException, JrafDaoException {

        Optional<RefProduct> refProduct = refProductService.getRefProduct(refProductComPrefGroup.getRefProductComPrefGroupId().getRefProduct().getProductId());

        Optional<RefComPrefGroupInfo> refComPrefGroupInfo = refComPrefGroupInfoService.getRefComPrefGroupInfo(refProductComPrefGroup.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getId());

        RefProductComPrefGroupId refProductComPrefGroupId = new RefProductComPrefGroupId(refProduct.get(), refComPrefGroupInfo.get());

        refProductComPrefGroup.setRefProductComPrefGroupId(refProductComPrefGroupId);

        Date now = new Date();
        refProductComPrefGroup.setDateCreation(now);
        refProductComPrefGroup.setDateModification(now);
        refProductComPrefGroup.setSiteCreation("CATI");
        refProductComPrefGroup.setSiteModification("CATI");
        refProductComPrefGroup.setSignatureCreation(username);
        refProductComPrefGroup.setSignatureModification(username);

        RefProductComPrefGroup refProductComPrefGroupAdded = refGroupProductRepository.saveAndFlush(refProductComPrefGroup);
        String msg = "RefProductComPrefGroup added : " + refProductComPrefGroupAdded.toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeRefGroupProduct(RefProductComPrefGroup refProductComPrefGroup, String matricule) throws CatiCommonsException, JrafDaoException {

        RefProductComPrefGroup refProductComPrefGroupToDelete = refGroupProductRepository.getRefGroupProductById(refProductComPrefGroup.getRefProductComPrefGroupId().getRefProduct().getProductId(), refProductComPrefGroup.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getId());

        refGroupProductRepository.delete(refProductComPrefGroupToDelete);

        LOGGER.info("Link between Product " + refProductComPrefGroupToDelete.getRefProductComPrefGroupId().getRefProduct().getProductId() + " and Group " + refProductComPrefGroupToDelete.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getId() + " deleted by matricule " + matricule);
    }

    @Override
    public Long countRefProductComPrefGroupByGroupInfoId(Integer refComPrefGroupInfoId) throws CatiCommonsException, JrafDaoException {

        return refGroupProductRepository.countRefProductComPrefGroupByGroupInfoId(refComPrefGroupInfoId);
    }

    @Override
    public RefProductComPrefGroup getRefGroupProduct(int id) throws CatiCommonsException, JrafDaoException {
        return refGroupProductRepository.getOne(id);
    }

    @Override
    public List<RefProductComPrefGroup> getAllRefGroupProductByProductId(int productId)
            throws CatiCommonsException, JrafDaoException {
        return refGroupProductRepository.getRefGroupProductByProductId(productId);
    }


}
