package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefPreferenceDataKeyDTO;
import com.afklm.cati.common.entity.RefPreferenceDataKey;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefPreferenceDataKeyRepository;
import com.afklm.cati.common.repository.RefPreferenceKeyTypeRepository;
import com.afklm.cati.common.service.RefPreferenceDataKeyService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * RefPreferenceDataKeyImpl.java
 *
 * @author m430152
 */
@Service
public class RefPreferenceDataKeyImpl implements RefPreferenceDataKeyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefPreferenceDataKeyImpl.class);

    @Autowired
    private RefPreferenceDataKeyRepository refPreferenceDataKeyRepo;

    @Autowired
    private RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepo;

    @Override
    public List<RefPreferenceDataKey> getAllRefPreferenceDataKey() throws CatiCommonsException, JrafDaoException {
        return refPreferenceDataKeyRepo.findAllByOrderByCode();
    }

    @Override
    public Optional<RefPreferenceDataKey> getRefPreferenceDataKey(String code) throws CatiCommonsException, JrafDaoException {
        return refPreferenceDataKeyRepo.findById(code);
    }

    @Override
    public void addRefPreferenceDataKey(RefPreferenceDataKeyDTO refPreferenceDataKeyDTO)
            throws CatiCommonsException, JrafDaoException {
        RefPreferenceDataKey newRefPreferenceDataKey = new RefPreferenceDataKey();
        newRefPreferenceDataKey.setCode(refPreferenceDataKeyDTO.getCode());
        newRefPreferenceDataKey.setNormalizedKey(refPreferenceDataKeyDTO.getNormalizedKey());
        newRefPreferenceDataKey.setLibelleEn(refPreferenceDataKeyDTO.getLibelleEn());
        newRefPreferenceDataKey.setLibelleFr(refPreferenceDataKeyDTO.getLibelleFr());
        refPreferenceDataKeyRepo.saveAndFlush(newRefPreferenceDataKey);
        LOGGER.info("RefPreferenceDataKey added : " + newRefPreferenceDataKey.toString());
    }

    @Override
    public void updateRefPreferenceDataKey(RefPreferenceDataKey refPreferenceDataKey)
            throws CatiCommonsException, JrafDaoException {
        RefPreferenceDataKey updatedRefPreferenceDataKey = refPreferenceDataKeyRepo.saveAndFlush(refPreferenceDataKey);
        LOGGER.info("RefPreferenceDataKey added : " + updatedRefPreferenceDataKey.toString());
    }

    @Override
    public void removeRefPreferenceDataKey(String code) throws CatiCommonsException, JrafDaoException {
        Optional<RefPreferenceDataKey> refPreferenceDataKey = getRefPreferenceDataKey(code);

        if (refPreferenceDataKey.isPresent()) {
            refPreferenceDataKeyRepo.deleteById(code);
        } else {
            String msg = String.format("Unable to find refPreferenceDataKey(%s)", code);
            LOGGER.info(msg);
            throw new CatiCommonsException(msg);
        }
    }

    @Override
    public Long countRefPreferenceKeyTypeByRefPreferenceDataKey(RefPreferenceDataKey refPreferenceDataKey)
            throws CatiCommonsException, JrafDaoException {
        return refPreferenceKeyTypeRepo.countByKey(refPreferenceDataKey.getCode());
    }
}
