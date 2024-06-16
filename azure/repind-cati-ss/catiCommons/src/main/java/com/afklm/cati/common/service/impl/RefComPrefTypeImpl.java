package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefComPrefTypeDTO;
import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefDgtRepository;
import com.afklm.cati.common.repository.RefComPrefTypeRepository;
import com.afklm.cati.common.service.RefComPrefTypeService;
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
 * Title : RefComPrefTypeImpl.java
 * </p>
 * Service Implementation to manage refComPrefType
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefComPrefTypeImpl implements RefComPrefTypeService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefTypeImpl.class);

    @Autowired
    private RefComPrefTypeRepository refComPrefTypeRepo;

    @Autowired
    private RefComPrefDgtRepository refComPrefDgtRepo;

    @Override
    public List<RefComPrefType> getAllRefComPrefType() throws CatiCommonsException, JrafDaoException {
        return refComPrefTypeRepo.findAllByOrderByCodeType();
    }

    @Override
    public Optional<RefComPrefType> getRefComPrefType(String code) throws CatiCommonsException, JrafDaoException {
        return refComPrefTypeRepo.findById(code);
    }

    @Override
    public void addRefComPrefType(RefComPrefTypeDTO refComPrefTypeDTO, String matricule) throws CatiCommonsException, JrafDaoException {
        RefComPrefType refComPrefTypeAdded = new RefComPrefType();
        refComPrefTypeAdded.setCodeType(refComPrefTypeDTO.getCodeType().toUpperCase());
        refComPrefTypeAdded.setLibelleType(refComPrefTypeDTO.getLibelleType());
        refComPrefTypeAdded.setLibelleTypeEN(refComPrefTypeDTO.getLibelleTypeEN());
        if (StringUtils.isBlank(refComPrefTypeDTO.getLibelleType())) {
            refComPrefTypeAdded.setLibelleType(refComPrefTypeDTO.getLibelleTypeEN());
        }

        refComPrefTypeAdded.setDateCreation(new Date());
        refComPrefTypeAdded.setDateModification(new Date());
        refComPrefTypeAdded.setSignatureCreation(matricule);
        refComPrefTypeAdded.setSignatureModification(matricule);
        refComPrefTypeAdded.setSiteCreation("CATI");
        refComPrefTypeAdded.setSiteModification("CATI");

        refComPrefTypeRepo.saveAndFlush(refComPrefTypeAdded);
        String msg = "RefComPrefType added : " + refComPrefTypeAdded.toString();
        LOGGER.info(msg);
    }

    @Override
    public void updateRefComPrefType(RefComPrefType refComPrefType, String matricule) throws CatiCommonsException, JrafDaoException {

        if (StringUtils.isBlank(refComPrefType.getLibelleType())) {
            refComPrefType.setLibelleType(refComPrefType.getLibelleTypeEN());
        }

        refComPrefType.setDateModification(new Date());
        refComPrefType.setSignatureModification(matricule);
        refComPrefType.setSiteModification("CATI");

        List<RefComPrefDgt> refs = refComPrefDgtRepo.findByType(refComPrefType);
        RefComPrefType refComPrefTypeUpdated = refComPrefTypeRepo.saveAndFlush(refComPrefType);
        String msg = "RefComPrefType updated : " + refComPrefTypeUpdated.toString();
        LOGGER.info(msg);
        for (int i = 0; i < refs.size(); i++) {
            refs.get(i).setDescription(refComPrefType.getLibelleTypeEN());
            refComPrefDgtRepo.saveAndFlush(refs.get(i));
        }
    }

    @Override
    public void removeRefComPrefType(String code, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<RefComPrefType> refComPrefType = getRefComPrefType(code);
        if (refComPrefType.isPresent()) {
            LOGGER.info("RefComPrefType with code " + code + " deleted by " + matricule + " : " + refComPrefType.toString());
        }
        refComPrefTypeRepo.deleteById(code);
    }

    @Override
    public Long countRefComPrefByType(RefComPrefType type) throws CatiCommonsException, JrafDaoException {
        return refComPrefDgtRepo.countByType(type);
    }


}
