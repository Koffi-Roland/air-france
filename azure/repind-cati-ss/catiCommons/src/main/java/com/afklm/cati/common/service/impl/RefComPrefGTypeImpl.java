package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefComPrefGTypeDTO;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefDgtRepository;
import com.afklm.cati.common.repository.RefComPrefGTypeRepository;
import com.afklm.cati.common.service.RefComPrefGTypeService;
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
 * Title : RefComPrefGTypeImpl.java
 * </p>
 * Service Implementation to manage RefComPrefGType
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefComPrefGTypeImpl implements RefComPrefGTypeService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefGTypeImpl.class);

    @Autowired
    private RefComPrefGTypeRepository refComPrefGTypeRepo;

    @Autowired
    private RefComPrefDgtRepository refComPrefDgtRepo;

    @Override
    public List<RefComPrefGType> getAllRefComPrefGType() throws CatiCommonsException, JrafDaoException {
        return refComPrefGTypeRepo.findAllByOrderByCodeGType();
    }

    @Override
    public Optional<RefComPrefGType> getRefComPrefGType(String code) throws CatiCommonsException, JrafDaoException {
        return refComPrefGTypeRepo.findById(code);
    }

    @Override
    public void addRefComPrefGType(RefComPrefGTypeDTO refComPrefGTypeDTO, String matricule) throws CatiCommonsException, JrafDaoException {
        RefComPrefGType refComPrefGTypeAdded = new RefComPrefGType();
        refComPrefGTypeAdded.setCodeGType(refComPrefGTypeDTO.getCodeGType().toUpperCase());
        refComPrefGTypeAdded.setLibelleGType(refComPrefGTypeDTO.getLibelleGType());
        refComPrefGTypeAdded.setLibelleGTypeEN(refComPrefGTypeDTO.getLibelleGTypeEN());
        if (StringUtils.isBlank(refComPrefGTypeDTO.getLibelleGType())) {
            refComPrefGTypeAdded.setLibelleGType(refComPrefGTypeDTO.getLibelleGTypeEN());
        }

        refComPrefGTypeAdded.setDateCreation(new Date());
        refComPrefGTypeAdded.setDateModification(new Date());
        refComPrefGTypeAdded.setSignatureCreation(matricule);
        refComPrefGTypeAdded.setSignatureModification(matricule);
        refComPrefGTypeAdded.setSiteCreation("CATI");
        refComPrefGTypeAdded.setSiteModification("CATI");

        refComPrefGTypeRepo.saveAndFlush(refComPrefGTypeAdded);
        String msg = "RefComPrefGType added : " + refComPrefGTypeAdded.toString();
        LOGGER.info(msg);
    }

    @Override
    public void updateRefComPrefGType(RefComPrefGType refComPrefGType, String matricule) throws CatiCommonsException, JrafDaoException {
        if (StringUtils.isBlank(refComPrefGType.getLibelleGType())) {
            refComPrefGType.setLibelleGType(refComPrefGType.getLibelleGTypeEN());
        }

        refComPrefGType.setDateModification(new Date());
        refComPrefGType.setSignatureModification(matricule);
        refComPrefGType.setSiteModification("CATI");

        RefComPrefGType refComPrefGTypeUpdated = refComPrefGTypeRepo.saveAndFlush(refComPrefGType);
        String msg = "RefComPrefGType updated : " + refComPrefGTypeUpdated.toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeRefComPrefGType(String code, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<RefComPrefGType> refComPrefGType = getRefComPrefGType(code);
        if (refComPrefGType.isPresent()) {
            LOGGER.info("RefComPrefGType with code " + code + " deleted by " + matricule + " : " + refComPrefGType.toString());
        }
        refComPrefGTypeRepo.deleteById(code);
    }

    @Override
    public Long countRefComPrefByGType(RefComPrefGType groupType) throws CatiCommonsException, JrafDaoException {
        return refComPrefDgtRepo.countByGroupType(groupType);
    }


}
