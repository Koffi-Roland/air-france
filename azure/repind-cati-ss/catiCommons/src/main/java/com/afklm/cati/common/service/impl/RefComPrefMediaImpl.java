package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefComPrefMediaDTO;
import com.afklm.cati.common.entity.RefComPrefMedia;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefMediaRepository;
import com.afklm.cati.common.repository.RefComPrefRepository;
import com.afklm.cati.common.service.RefComPrefMediaService;
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
 * Title : RefComPrefMediaImpl.java
 * </p>
 * Service Implementation to manage RefComPrefMedia
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefComPrefMediaImpl implements RefComPrefMediaService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefMediaImpl.class);

    @Autowired
    private RefComPrefMediaRepository refComPrefMediaRepo;

    @Autowired
    private RefComPrefRepository refComPrefRepo;

    @Override
    public List<RefComPrefMedia> getAllRefComPrefMedia() throws CatiCommonsException, JrafDaoException {
        return refComPrefMediaRepo.findAllByOrderByCodeMedia();
    }

    @Override
    public Optional<RefComPrefMedia> getRefComPrefMedia(String code) throws CatiCommonsException, JrafDaoException {
        return refComPrefMediaRepo.findById(code);
    }

    @Override
    public void addRefComPrefMedia(RefComPrefMediaDTO refComPrefMediaDTO, String matricule) throws CatiCommonsException, JrafDaoException {
        RefComPrefMedia refComPrefMediaAdded = new RefComPrefMedia();
        refComPrefMediaAdded.setCodeMedia(refComPrefMediaDTO.getCodeMedia().toUpperCase());
        refComPrefMediaAdded.setLibelleMedia(refComPrefMediaDTO.getLibelleMedia());
        refComPrefMediaAdded.setLibelleMediaEN(refComPrefMediaDTO.getLibelleMediaEN());
        if (StringUtils.isBlank(refComPrefMediaDTO.getLibelleMedia())) {
            refComPrefMediaAdded.setLibelleMedia(refComPrefMediaDTO.getLibelleMediaEN());
        }

        refComPrefMediaAdded.setDateCreation(new Date());
        refComPrefMediaAdded.setDateModification(new Date());
        refComPrefMediaAdded.setSignatureCreation(matricule);
        refComPrefMediaAdded.setSignatureModification(matricule);
        refComPrefMediaAdded.setSiteCreation("CATI");
        refComPrefMediaAdded.setSiteModification("CATI");

        refComPrefMediaRepo.saveAndFlush(refComPrefMediaAdded);
        String msg = "RefComPrefMedia added : " + refComPrefMediaAdded.toString();
        LOGGER.info(msg);
    }

    @Override
    public void updateRefComPrefMedia(RefComPrefMedia refComPrefMedia, String matricule) throws CatiCommonsException, JrafDaoException {
        if (StringUtils.isBlank(refComPrefMedia.getLibelleMedia())) {
            refComPrefMedia.setLibelleMedia(refComPrefMedia.getLibelleMediaEN());
        }

        refComPrefMedia.setDateModification(new Date());
        refComPrefMedia.setSignatureModification(matricule);
        refComPrefMedia.setSiteModification("CATI");

        RefComPrefMedia refComPrefMediaUpdated = refComPrefMediaRepo.saveAndFlush(refComPrefMedia);
        String msg = "RefComPrefMedia updated : " + refComPrefMediaUpdated.toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeRefComPrefMedia(String code, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<RefComPrefMedia> refComPrefMedia = getRefComPrefMedia(code);
        if (refComPrefMedia.isPresent()) {
            LOGGER.info("RefComPrefMedia with code " + code + " deleted by " + matricule + " : " + refComPrefMedia.toString());
        }
        refComPrefMediaRepo.deleteById(code);
    }

    @Override
    public Long countRefComPrefByMedia(RefComPrefMedia media) throws CatiCommonsException, JrafDaoException {
        return refComPrefRepo.countByMedia(media);
    }


}
