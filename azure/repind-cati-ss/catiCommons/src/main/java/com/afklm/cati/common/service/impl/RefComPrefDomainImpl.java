package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefComPrefDomainDTO;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefDgtRepository;
import com.afklm.cati.common.repository.RefComPrefDomainRepository;
import com.afklm.cati.common.service.RefComPrefDomainService;
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
 * Title : RefComPrefDomainImpl.java
 * </p>
 * Service Implementation to manage RefComPrefDomain
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefComPrefDomainImpl implements RefComPrefDomainService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefDomainImpl.class);

    @Autowired
    private RefComPrefDomainRepository refComPrefDomainRepo;

    @Autowired
    private RefComPrefDgtRepository refComPrefDgtRepo;

    @Override
    public List<RefComPrefDomain> getAllRefComPrefDomain() throws CatiCommonsException, JrafDaoException {
        return refComPrefDomainRepo.findAllByOrderByCodeDomain();
    }

    @Override
    public Optional<RefComPrefDomain> getRefComPrefDomain(String code) throws CatiCommonsException, JrafDaoException {
        return refComPrefDomainRepo.findById(code);
    }

    @Override
    public void addRefComPrefDomain(RefComPrefDomainDTO refComPrefDomainDTO, String matricule) throws CatiCommonsException, JrafDaoException {
        RefComPrefDomain refComPrefDomainAdded = new RefComPrefDomain();
        refComPrefDomainAdded.setCodeDomain(refComPrefDomainDTO.getCodeDomain().toUpperCase());
        refComPrefDomainAdded.setLibelleDomain(refComPrefDomainDTO.getLibelleDomain());
        refComPrefDomainAdded.setLibelleDomainEN(refComPrefDomainDTO.getLibelleDomainEN());
        if (StringUtils.isBlank(refComPrefDomainDTO.getLibelleDomain())) {
            refComPrefDomainAdded.setLibelleDomain(refComPrefDomainDTO.getLibelleDomainEN());
        }

        refComPrefDomainAdded.setDateCreation(new Date());
        refComPrefDomainAdded.setDateModification(new Date());
        refComPrefDomainAdded.setSignatureCreation(matricule);
        refComPrefDomainAdded.setSignatureModification(matricule);
        refComPrefDomainAdded.setSiteCreation("CATI");
        refComPrefDomainAdded.setSiteModification("CATI");

        refComPrefDomainRepo.saveAndFlush(refComPrefDomainAdded);
        String msg = "RefComPrefDomain added : " + refComPrefDomainAdded;
        LOGGER.info(msg);
    }

    @Override
    public void updateRefComPrefDomain(RefComPrefDomain refComPrefDomain, String matricule) throws CatiCommonsException, JrafDaoException {

        if (StringUtils.isBlank(refComPrefDomain.getLibelleDomain())) {
            refComPrefDomain.setLibelleDomain(refComPrefDomain.getLibelleDomainEN());
        }

        refComPrefDomain.setDateModification(new Date());
        refComPrefDomain.setSignatureModification(matricule);
        refComPrefDomain.setSiteModification("CATI");

        RefComPrefDomain refComPrefDomainUpdated = refComPrefDomainRepo.saveAndFlush(refComPrefDomain);
        String msg = "RefComPrefDomain updated : " + refComPrefDomainUpdated.toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeRefComPrefDomain(String code, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<RefComPrefDomain> refComPrefDomain = getRefComPrefDomain(code);
        if (refComPrefDomain.isPresent()) {
            LOGGER.info("RefComPrefDomain with code " + code + " deleted by " + matricule + " : " + refComPrefDomain.toString());
        }
        refComPrefDomainRepo.deleteById(code);
    }

    @Override
    public Long countRefComPrefByDomain(RefComPrefDomain domain) throws CatiCommonsException, JrafDaoException {
        return refComPrefDgtRepo.countByDomain(domain);
    }

}
