package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefPreferenceTypeDTO;
import com.afklm.cati.common.entity.RefPreferenceType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefPreferenceKeyTypeRepository;
import com.afklm.cati.common.repository.RefPreferenceTypeRepository;
import com.afklm.cati.common.service.RefPreferenceTypeService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * RefPreferenceTypeImpl.java
 *
 * @author m430152
 */
@Service
public class RefPreferenceTypeImpl implements RefPreferenceTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefPreferenceTypeImpl.class);

    @Autowired
    private RefPreferenceTypeRepository refPreferenceTypeRepo;

    @Autowired
    private RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepo;

    @Override
    public List<RefPreferenceType> getAllRefPreferenceType() throws CatiCommonsException, JrafDaoException {
        return refPreferenceTypeRepo.findAllByOrderByCode();
    }

    @Override
    public Optional<RefPreferenceType> getRefPreferenceType(String code) throws CatiCommonsException, JrafDaoException {
        return refPreferenceTypeRepo.findById(code);
    }

    @Override
    public void addRefPreferenceType(RefPreferenceTypeDTO refPreferenceTypeDTO)
            throws CatiCommonsException, JrafDaoException {
        RefPreferenceType newRefPreferenceType = new RefPreferenceType();
        newRefPreferenceType.setCode(refPreferenceTypeDTO.getCode());
        newRefPreferenceType.setLibelleEN(refPreferenceTypeDTO.getLibelleEN());
        newRefPreferenceType.setLibelleFR(refPreferenceTypeDTO.getLibelleFR());

        refPreferenceTypeRepo.saveAndFlush(newRefPreferenceType);
        LOGGER.info("RefPreferenceType added : " + newRefPreferenceType.toString());
    }

    @Override
    public void updateRefPreferenceType(RefPreferenceType refPreferenceType)
            throws CatiCommonsException, JrafDaoException {
        RefPreferenceType updatedRefPreferenceType = refPreferenceTypeRepo.saveAndFlush(refPreferenceType);
        LOGGER.info("RefPreferenceType added : " + updatedRefPreferenceType.toString());
    }

    @Override
    public void removeRefPreferenceType(String code) throws CatiCommonsException, JrafDaoException {
        Optional<RefPreferenceType> refPreferenceType = getRefPreferenceType(code);
        if (refPreferenceType.isPresent()) {
            refPreferenceTypeRepo.deleteById(code);
        } else {
            String msg = String.format("Unable to find refPreferenceType(%s)", code);
            LOGGER.info(msg);
            throw new CatiCommonsException(msg);
        }
    }

    @Override
    public Long countRefPreferenceKeyTypeByRefPreferenceType(RefPreferenceType refPreferenceType)
            throws CatiCommonsException, JrafDaoException {
        return refPreferenceKeyTypeRepo.countByType(refPreferenceType.getCode());
    }
}
