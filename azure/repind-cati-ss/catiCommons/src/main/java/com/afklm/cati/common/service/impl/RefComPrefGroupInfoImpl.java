package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefGroupInfoRepository;
import com.afklm.cati.common.service.RefComPrefGroupInfoService;
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
public class RefComPrefGroupInfoImpl implements RefComPrefGroupInfoService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefGroupImpl.class);

    @Autowired
    private RefComPrefGroupInfoRepository refComPrefGroupInfoRepo;

    @Override
    public List<RefComPrefGroupInfo> getAllRefComPrefGroupInfo() throws CatiCommonsException, JrafDaoException {
        List<RefComPrefGroupInfo> groups = refComPrefGroupInfoRepo.findAll();
        LOGGER.debug("Size List<RefComPrefGroupInfo> : " + groups.size());
        return groups;
    }

    @Override
    public Optional<RefComPrefGroupInfo> getRefComPrefGroupInfoCode(int code) throws CatiCommonsException, JrafDaoException {
        return refComPrefGroupInfoRepo.findById(code);
    }

    @Override
    public void addRefComPrefGroupInfo(RefComPrefGroupInfo refComPrefGroupInfo, String matricule) {
        Date now = new Date();
        refComPrefGroupInfo.setDateModification(now);
        refComPrefGroupInfo.setDateCreation(now);
        refComPrefGroupInfo.setSignatureCreation(matricule);
        refComPrefGroupInfo.setSignatureModification(matricule);
        refComPrefGroupInfo.setSiteCreation("CATI");
        refComPrefGroupInfo.setSiteModification("CATI");

        if (StringUtils.isEmpty(refComPrefGroupInfo.getLibelleFR())) {
            refComPrefGroupInfo.setLibelleFR(refComPrefGroupInfo.getLibelleEN());
        }

        refComPrefGroupInfoRepo.saveAndFlush(refComPrefGroupInfo);
        String msg = "RefComPrefGroupInfo added : " + refComPrefGroupInfo.toString();
        LOGGER.info(msg);

    }

    @Override
    public void updateRefComPref(RefComPrefGroupInfo refComPrefGroupInfoUpdate, String matricule) {
        Date now = new Date();
        refComPrefGroupInfoUpdate.setDateModification(now);
        refComPrefGroupInfoUpdate.setSignatureModification(matricule);
        refComPrefGroupInfoUpdate.setSiteModification("CATI");

        if (StringUtils.isEmpty(refComPrefGroupInfoUpdate.getLibelleFR())) {
            refComPrefGroupInfoUpdate.setLibelleFR(refComPrefGroupInfoUpdate.getLibelleEN());
        }

        refComPrefGroupInfoRepo.saveAndFlush(refComPrefGroupInfoUpdate);
    }

    @Override
    public Optional<RefComPrefGroupInfo> getRefComPrefGroupInfo(Integer refComPrefGroupInfoId) {
        return refComPrefGroupInfoRepo.findById(refComPrefGroupInfoId);
    }

    @Override
    public void removeRefComPrefGroupInfo(Integer id, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<RefComPrefGroupInfo> refComPrefGroupInfo = getRefComPrefGroupInfoCode(id);
        if (refComPrefGroupInfo.isPresent()) {
            LOGGER.info("refComPrefGroupInfo with id " + id + " deleted by " + matricule + " : " + refComPrefGroupInfo.toString());
        }
        refComPrefGroupInfoRepo.deleteById(id);
    }

    @Override
    public Long countRefComPrefGroupByRefComPrefGroupInfo(Integer refPermissionsQuestionId) throws CatiCommonsException, JrafDaoException {

        Optional<RefComPrefGroupInfo> refComPrefGroupInfo = getRefComPrefGroupInfoCode(refPermissionsQuestionId);

        return refComPrefGroupInfoRepo.countRefComPrefGroupById(refComPrefGroupInfo.get());
    }
}
