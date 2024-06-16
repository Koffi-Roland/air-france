package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.entity.RefComPref;
import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefMl;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefDgtRepository;
import com.afklm.cati.common.repository.RefComPrefMlRepository;
import com.afklm.cati.common.repository.RefComPrefRepository;
import com.afklm.cati.common.service.RefComPrefService;
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
 * Title : RefComPrefImpl.java
 * </p>
 * Service Implementation to manage RefComPref
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefComPrefImpl implements RefComPrefService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefImpl.class);

    @Autowired
    private RefComPrefRepository refComPrefRepo;

    @Autowired
    private RefComPrefDgtRepository refComPrefDgtRepo;

    @Autowired
    private RefComPrefMlRepository refComPrefMlRepo;

    @Override
    public List<RefComPref> getAllRefComPref() throws CatiCommonsException, JrafDaoException {
        return refComPrefRepo.findAllByOrderByRefComprefId();
    }

    @Override
    public Optional<RefComPref> getRefComPref(Integer id) throws CatiCommonsException, JrafDaoException {
        return refComPrefRepo.findById(id);
    }

    @Override
    public void addRefComPref(RefComPref refComPref, String matricule) throws CatiCommonsException, JrafDaoException {

        RefComPrefDgt refComPrefDgtExist = refComPrefDgtRepo.findByDomainAndGroupTypeAndType(refComPref.getDomain(), refComPref.getComGroupeType(), refComPref.getComType());


        Date now = new Date();

        if (refComPrefDgtExist == null) {

            RefComPrefDgt refComPrefDgt = new RefComPrefDgt();
            if (!StringUtils.isEmpty(refComPref.getDescription())) {
                refComPrefDgt.setDescription(refComPref.getDescription());
            } else {
                refComPrefDgt.setDescription(refComPref.getComType().getLibelleTypeEN());
            }
            refComPrefDgt.setDomain(refComPref.getDomain());
            refComPrefDgt.setType(refComPref.getComType());
            refComPrefDgt.setGroupType(refComPref.getComGroupeType());

            refComPrefDgt.setDateCreation(now);
            refComPrefDgt.setDateModification(now);
            refComPrefDgt.setSignatureCreation(matricule);
            refComPrefDgt.setSignatureModification(matricule);
            refComPrefDgt.setSiteCreation("CATI");
            refComPrefDgt.setSiteModification("CATI");

            refComPrefDgtExist = refComPrefDgtRepo.saveAndFlush(refComPrefDgt);
        }

        RefComPrefMl refComPrefMl = new RefComPrefMl();
        refComPrefMl.setRefComPrefDgt(refComPrefDgtExist);
        refComPrefMl.setDefaultLanguage1(refComPref.getDefaultLanguage1());
        refComPrefMl.setDefaultLanguage2(refComPref.getDefaultLanguage2());
        refComPrefMl.setDefaultLanguage3(refComPref.getDefaultLanguage3());
        refComPrefMl.setDefaultLanguage4(refComPref.getDefaultLanguage4());
        refComPrefMl.setDefaultLanguage5(refComPref.getDefaultLanguage5());
        refComPrefMl.setDefaultLanguage6(refComPref.getDefaultLanguage6());
        refComPrefMl.setDefaultLanguage7(refComPref.getDefaultLanguage7());
        refComPrefMl.setDefaultLanguage8(refComPref.getDefaultLanguage8());
        refComPrefMl.setDefaultLanguage9(refComPref.getDefaultLanguage9());
        refComPrefMl.setDefaultLanguage10(refComPref.getDefaultLanguage10());
        refComPrefMl.setFieldA(refComPref.getFieldA());
        refComPrefMl.setFieldN(refComPref.getFieldN());
        refComPrefMl.setFieldT(refComPref.getFieldT());
        refComPrefMl.setMedia(refComPref.getMedia().getCodeMedia());
        refComPrefMl.setMarket(refComPref.getMarket());
        refComPrefMl.setMandatoryOption(refComPref.getMandatoryOptin());
        refComPrefMl.setDateCreation(now);
        refComPrefMl.setDateModification(now);
        refComPrefMl.setSignatureCreation(matricule);
        refComPrefMl.setSignatureModification(matricule);
        refComPrefMl.setSiteCreation("CATI");
        refComPrefMl.setSiteModification("CATI");

        RefComPrefMl refComPrefAdded = refComPrefMlRepo.saveAndFlush(refComPrefMl);

//		refComPref.setRefComprefId(refComPrefRepo.getMaxId() + 1);
//		RefComPref refComPrefAdded = refComPrefRepo.saveAndFlush(refComPref);
        String msg = "RefComPref added : " + getRefComPrefMlLight(refComPrefAdded).toString();
        LOGGER.info(msg);
    }

    @Override
    public void updateRefComPref(RefComPref refComPref, String matricule) throws CatiCommonsException, JrafDaoException {


        RefComPrefDgt refComPrefDgtExist = refComPrefDgtRepo.findByDomainAndGroupTypeAndType(refComPref.getDomain(), refComPref.getComGroupeType(), refComPref.getComType());

        Date now = new Date();

        if (refComPrefDgtExist == null) {

            RefComPrefDgt refComPrefDgt = new RefComPrefDgt();
            refComPrefDgt.setDescription(refComPref.getDescription());
            refComPrefDgt.setDomain(refComPref.getDomain());
            refComPrefDgt.setType(refComPref.getComType());
            refComPrefDgt.setGroupType(refComPref.getComGroupeType());
            refComPrefDgt.setDateCreation(now);
            refComPrefDgt.setDateModification(now);
            refComPrefDgt.setSignatureCreation(matricule);
            refComPrefDgt.setSignatureModification(matricule);
            refComPrefDgt.setSiteCreation("CATI");
            refComPrefDgt.setSiteModification("CATI");

            refComPrefDgtExist = refComPrefDgtRepo.saveAndFlush(refComPrefDgt);
        }


        RefComPrefMl refComPrefMl = new RefComPrefMl();
        refComPrefMl.setRefComPrefDgt(refComPrefDgtExist);
        refComPrefMl.setRefComPrefMlId(refComPref.getRefComprefId());
        refComPrefMl.setDefaultLanguage1(refComPref.getDefaultLanguage1());
        refComPrefMl.setDefaultLanguage2(refComPref.getDefaultLanguage2());
        refComPrefMl.setDefaultLanguage3(refComPref.getDefaultLanguage3());
        refComPrefMl.setDefaultLanguage4(refComPref.getDefaultLanguage4());
        refComPrefMl.setDefaultLanguage5(refComPref.getDefaultLanguage5());
        refComPrefMl.setDefaultLanguage6(refComPref.getDefaultLanguage6());
        refComPrefMl.setDefaultLanguage7(refComPref.getDefaultLanguage7());
        refComPrefMl.setDefaultLanguage8(refComPref.getDefaultLanguage8());
        refComPrefMl.setDefaultLanguage9(refComPref.getDefaultLanguage9());
        refComPrefMl.setDefaultLanguage10(refComPref.getDefaultLanguage10());
        refComPrefMl.setFieldA(refComPref.getFieldA());
        refComPrefMl.setFieldN(refComPref.getFieldN());
        refComPrefMl.setFieldT(refComPref.getFieldT());
        if (refComPref.getMedia() != null) {
            refComPrefMl.setMedia(refComPref.getMedia().getCodeMedia());
        } else {
            refComPrefMl.setMedia(null);
        }
        refComPrefMl.setMarket(refComPref.getMarket());
        refComPrefMl.setMandatoryOption(refComPref.getMandatoryOptin());
        refComPrefMl.setDateCreation(refComPref.getDateCreation());
        refComPrefMl.setDateModification(now);
        refComPrefMl.setSiteCreation(refComPref.getSiteCreation());
        refComPrefMl.setSiteModification("CATI");
        refComPrefMl.setSignatureCreation(refComPref.getSignatureCreation());
        refComPrefMl.setSignatureModification(matricule);

        RefComPrefMl refComPrefUpdated = refComPrefMlRepo.saveAndFlush(refComPrefMl);
        String msg = "RefComPref updated : " + getRefComPrefMlLight(refComPrefUpdated).toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeRefComPref(Integer id, String matricule) throws CatiCommonsException, JrafDaoException {

        Optional<RefComPref> refComPref = getRefComPref(id);
        if (refComPref.isPresent()) {
            LOGGER.info("RefComPref with id " + id + " deleted by " + matricule + " : " + refComPref.toString());
        }
        refComPrefRepo.deleteById(id);
    }

    @Override
    public Long countRefComPrefMlByDgt(Integer id) throws CatiCommonsException, JrafDaoException {

        Optional<RefComPrefDgt> refComPrefDgt = refComPrefDgtRepo.findById(id);
        return refComPrefMlRepo.countByRefComPrefDgt(refComPrefDgt.get());
    }

    public RefComPrefMl getRefComPrefMlLight(RefComPrefMl refComPrefMl) {

        RefComPrefMl refComPrefMlLight = new RefComPrefMl();
        refComPrefMlLight.setRefComPrefMlId(refComPrefMl.getRefComPrefMlId());
        refComPrefMlLight.setDefaultLanguage1(refComPrefMl.getDefaultLanguage1());
        refComPrefMlLight.setDefaultLanguage2(refComPrefMl.getDefaultLanguage2());
        refComPrefMlLight.setDefaultLanguage3(refComPrefMl.getDefaultLanguage3());
        refComPrefMlLight.setDefaultLanguage4(refComPrefMl.getDefaultLanguage4());
        refComPrefMlLight.setDefaultLanguage5(refComPrefMl.getDefaultLanguage5());
        refComPrefMlLight.setDefaultLanguage6(refComPrefMl.getDefaultLanguage6());
        refComPrefMlLight.setDefaultLanguage7(refComPrefMl.getDefaultLanguage7());
        refComPrefMlLight.setDefaultLanguage8(refComPrefMl.getDefaultLanguage8());
        refComPrefMlLight.setDefaultLanguage9(refComPrefMl.getDefaultLanguage9());
        refComPrefMlLight.setDefaultLanguage10(refComPrefMl.getDefaultLanguage10());
        refComPrefMlLight.setFieldA(refComPrefMl.getFieldA());
        refComPrefMlLight.setFieldN(refComPrefMl.getFieldN());
        refComPrefMlLight.setFieldT(refComPrefMl.getFieldT());
        refComPrefMlLight.setMedia(refComPrefMl.getMedia());
        refComPrefMlLight.setMarket(refComPrefMl.getMarket());
        refComPrefMlLight.setMandatoryOption(refComPrefMl.getMandatoryOption());
        refComPrefMlLight.setDateCreation(refComPrefMl.getDateCreation());
        refComPrefMlLight.setDateModification(refComPrefMl.getDateModification());
        refComPrefMlLight.setSiteCreation(refComPrefMl.getSiteCreation());
        refComPrefMlLight.setSiteModification(refComPrefMl.getSiteModification());
        refComPrefMlLight.setSignatureCreation(refComPrefMl.getSignatureCreation());
        refComPrefMlLight.setSignatureModification(refComPrefMl.getSignatureModification());

        return refComPrefMlLight;
    }


}
