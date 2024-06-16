package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefComPrefCountryMarketDTO;
import com.afklm.cati.common.entity.RefComPrefCountryMarket;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefCountryMarketRepository;
import com.afklm.cati.common.service.RefComPrefCountryMarketService;
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
 * Title : RefComPrefCountryMarketImpl.java
 * </p>
 * Service Implementation to manage RefComPrefCountryMarket
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefComPrefCountryMarketImpl implements RefComPrefCountryMarketService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefCountryMarketImpl.class);

    @Autowired
    private RefComPrefCountryMarketRepository refComPrefCountryMarketRepo;


    @Override
    public List<RefComPrefCountryMarket> getAllRefComPrefCountryMarket() throws CatiCommonsException, JrafDaoException {
        return refComPrefCountryMarketRepo.findAll();
    }

    @Override
    public Optional<RefComPrefCountryMarket> getRefComPrefCountryMarket(String code) throws CatiCommonsException, JrafDaoException {
        return refComPrefCountryMarketRepo.findById(code);
    }

    @Override
    public void addRefComPrefCountryMarket(RefComPrefCountryMarketDTO refComPrefCountryMarketDTO, String matricule) throws CatiCommonsException, JrafDaoException {

        RefComPrefCountryMarket refComPrefCountryMarketAdded = new RefComPrefCountryMarket();

        refComPrefCountryMarketAdded.setCodePays(refComPrefCountryMarketDTO.getCodePays().toUpperCase());
        refComPrefCountryMarketAdded.setMarket(refComPrefCountryMarketDTO.getMarket().toUpperCase());
        refComPrefCountryMarketAdded.setDateCreation(new Date());
        refComPrefCountryMarketAdded.setDateModification(new Date());
        refComPrefCountryMarketAdded.setSignatureCreation(matricule);
        refComPrefCountryMarketAdded.setSignatureModification(matricule);
        refComPrefCountryMarketAdded.setSiteCreation("CATI");
        refComPrefCountryMarketAdded.setSiteModification("CATI");

        refComPrefCountryMarketRepo.saveAndFlush(refComPrefCountryMarketAdded);
        String msg = "RefComPrefCountryMarket added : " + refComPrefCountryMarketAdded.toString();
        LOGGER.info(msg);
    }

    @Override
    public void updateRefComPrefCountryMarket(RefComPrefCountryMarket refComPrefCountryMarket, String matricule) throws CatiCommonsException, JrafDaoException {
        refComPrefCountryMarket.setMarket(refComPrefCountryMarket.getMarket().toUpperCase());
        refComPrefCountryMarket.setDateModification(new Date());
        refComPrefCountryMarket.setSignatureModification(matricule);
        refComPrefCountryMarket.setSiteModification("CATI");

        RefComPrefCountryMarket refComPrefCountryMarketUpdated = refComPrefCountryMarketRepo.saveAndFlush(refComPrefCountryMarket);
        String msg = "RefComPrefCountryMarket updated : " + refComPrefCountryMarketUpdated.toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeRefComPrefCountryMarket(String code, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<RefComPrefCountryMarket> refComPrefCountryMarket = getRefComPrefCountryMarket(code);
        if (refComPrefCountryMarket.isPresent()) {
            LOGGER.info("RefComPrefCountryMarket with code " + code + " deleted by " + matricule + " : " + refComPrefCountryMarket.toString());
        }
        refComPrefCountryMarketRepo.deleteById(code);
    }


}
