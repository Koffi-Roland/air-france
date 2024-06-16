package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.criteria.RefPreferenceKeyTypeCriteria;
import com.afklm.cati.common.entity.RefPreferenceKeyType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefPreferenceKeyTypeRepository;
import com.afklm.cati.common.service.RefPreferenceKeyTypeService;
import com.afklm.cati.common.exception.jraf.NotFoundException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * RefPreferenceKeyTypeImpl.java
 *
 * @author m430152
 */
@Service
public class RefPreferenceKeyTypeImpl implements RefPreferenceKeyTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefPreferenceKeyTypeImpl.class);

    @Autowired
    private RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepo;


    @Override
    public List<RefPreferenceKeyType> getAllRefPreferenceKeyType() throws CatiCommonsException, JrafDaoException {
        return refPreferenceKeyTypeRepo.findAllByOrderByRefId();
    }

    @Override
    public Optional<RefPreferenceKeyType> getRefPreferenceKeyType(Integer refId) throws CatiCommonsException, JrafDaoException {
        return refPreferenceKeyTypeRepo.findById(refId);
    }

    @Override
    public void addRefPreferenceKeyType(RefPreferenceKeyTypeCriteria criteria)
            throws CatiCommonsException, JrafDaoException {

        List<String> prefTypes = criteria.getAllPreferenceType();

        try {
            for (int i = 0; i < prefTypes.size(); i++) {

                RefPreferenceKeyType refPreferenceKeyType = new RefPreferenceKeyType(
                        criteria.getKey(),
                        prefTypes.get(i),
                        criteria.getMinLength(),
                        criteria.getMaxLength(),
                        criteria.getDataType(),
                        criteria.getCondition());

                RefPreferenceKeyType saved = refPreferenceKeyTypeRepo.saveAndFlush(refPreferenceKeyType);
                LOGGER.info((i + 1) + "/" + prefTypes.size() + " RefPreferenceKeyType added : " + saved);
            }
        } catch (Exception e) {
            String errorMsg = "Cannot create RefPreferenceKeyTypes";
            LOGGER.error(errorMsg);
            throw new CatiCommonsException(errorMsg, e);
        }

    }

    @Override
    public void updateRefPreferenceKeyType(RefPreferenceKeyTypeCriteria criteria)
            throws CatiCommonsException, JrafDaoException, NotFoundException {

        Optional<RefPreferenceKeyType> toUpdate = refPreferenceKeyTypeRepo.findById(criteria.getRefId());

        if (toUpdate.isPresent()) {

            RefPreferenceKeyType refPreferenceKeyType = new RefPreferenceKeyType(
                    criteria.getRefId(),
                    criteria.getKey(),
                    criteria.getUniquePreferenceType(),
                    criteria.getMinLength(),
                    criteria.getMaxLength(),
                    criteria.getDataType(),
                    criteria.getCondition()
            );

            RefPreferenceKeyType updatedRefPreferenceKeyType = refPreferenceKeyTypeRepo.saveAndFlush(refPreferenceKeyType);
            LOGGER.info("RefPreferenceKeyType added : " + updatedRefPreferenceKeyType.toString());
        } else {
            String msg = "RefPreferenceKeyType with refId " + criteria.getRefId() + " doesn't exist";
            throw new NotFoundException(msg);
        }
    }

    @Override
    public void removeRefPreferenceKeyType(Integer refId) throws CatiCommonsException, JrafDaoException {
        Optional<RefPreferenceKeyType> refPreferenceKeyType = getRefPreferenceKeyType(refId);
        if (refPreferenceKeyType.isPresent()) {
            refPreferenceKeyTypeRepo.deleteById(refId);
        } else {
            String msg = String.format("Unable to find refPreferenceKeyType(%s)", refId);
            LOGGER.info(msg);
            throw new CatiCommonsException(msg);
        }
    }
}
