package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefDgtRepository;
import com.afklm.cati.common.service.RefComPrefDgtService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.apache.commons.lang3.StringUtils;
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
public class RefComPrefDgtImpl implements RefComPrefDgtService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefDgtImpl.class);

    @Autowired
    private RefComPrefDgtRepository refComPrefDgtRepo;

    @Override
    public List<RefComPrefDgt> getAllRefComPrefDgt() throws CatiCommonsException, JrafDaoException {
        return refComPrefDgtRepo.findAllByOrderByRefComPrefDgtId();
    }

    @Override
    public Optional<RefComPrefDgt> getRefComPrefDgt(Integer id) throws CatiCommonsException, JrafDaoException {
        return refComPrefDgtRepo.findById(id);
    }

    @Override
    public void addRefComPrefDgt(RefComPrefDgt refComPref, String matricule) throws CatiCommonsException, JrafDaoException {

        if (StringUtils.isBlank(refComPref.getDescription())) {
            refComPref.setDescription(refComPref.getType().getLibelleTypeEN());
        }
        refComPref.setDateCreation(new Date());
        refComPref.setDateModification(new Date());
        refComPref.setSignatureCreation(matricule);
        refComPref.setSignatureModification(matricule);
        refComPref.setSiteCreation("CATI");
        refComPref.setSiteModification("CATI");

        RefComPrefDgt refComPrefAdded = refComPrefDgtRepo.saveAndFlush(refComPref);
        String msg = "RefComPrefDgt added : " + refComPrefAdded.toString();
        LOGGER.info(msg);
    }

    @Override
    public void updateRefComPrefDgt(RefComPrefDgt refComPref, String matricule) throws CatiCommonsException, JrafDaoException {

        if (StringUtils.isBlank(refComPref.getDescription())) {
            refComPref.setDescription(refComPref.getType().getLibelleTypeEN());
        }
        refComPref.setDateModification(new Date());
        refComPref.setSignatureModification(matricule);
        refComPref.setSiteModification("CATI");
        RefComPrefDgt refComPrefUpdated = refComPrefDgtRepo.saveAndFlush(refComPref);
        if (refComPrefUpdated != null) {
            RefComPrefDgt refComPrefDgtLight = getRefComPrefDgtLight(refComPrefUpdated);
            String msg = "RefComPrefDgt updated : " + refComPrefDgtLight.toString();
            LOGGER.info(msg);
        }
    }

    @Override
    public void removeRefComPrefDgt(Integer id, String matricule) throws CatiCommonsException, JrafDaoException {

        Optional<RefComPrefDgt> refComPrefDgt = getRefComPrefDgt(id);
        if (refComPrefDgt.isPresent()) {
            RefComPrefDgt refComPrefDgtLight = getRefComPrefDgtLight(refComPrefDgt.get());
            LOGGER.info("RefComPrefDgt with id " + id + " deleted by " + matricule + " : " + refComPrefDgtLight.toString());
        }
        refComPrefDgtRepo.deleteById(id);
    }

    public RefComPrefDgt getRefComPrefDgtLight(RefComPrefDgt refComPrefDgt) {

        RefComPrefDgt refComPrefDgtLight = new RefComPrefDgt();
        refComPrefDgtLight.setDateCreation(refComPrefDgt.getDateCreation());
        refComPrefDgtLight.setDateModification(refComPrefDgt.getDateModification());
        refComPrefDgtLight.setSignatureCreation(refComPrefDgt.getSignatureCreation());
        refComPrefDgtLight.setSignatureModification(refComPrefDgt.getSignatureModification());
        refComPrefDgtLight.setSiteCreation(refComPrefDgt.getSiteCreation());
        refComPrefDgtLight.setSiteModification(refComPrefDgt.getSiteModification());
        refComPrefDgtLight.setDescription(refComPrefDgt.getDescription());
        refComPrefDgtLight.setDomain(refComPrefDgt.getDomain());
        refComPrefDgtLight.setGroupType(refComPrefDgt.getGroupType());
        refComPrefDgtLight.setType(refComPrefDgt.getType());

        return refComPrefDgtLight;
    }

}
