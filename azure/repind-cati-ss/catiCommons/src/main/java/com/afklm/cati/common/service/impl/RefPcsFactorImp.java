package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefPcsFactorDTO;
import com.afklm.cati.common.entity.RefPcsFactor;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsFactor;
import com.afklm.cati.common.repository.RefPcsFactorRepository;
import com.afklm.cati.common.service.RefPcsFactorService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RefPcsFactorImp implements RefPcsFactorService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefPcsFactorImp.class);

    @Autowired
    private RefPcsFactorRepository refPcsFactorRepository;

    @Override
    public List<ModelRefPcsFactor> getAllPcsFactor() throws CatiCommonsException, JrafDaoException {
        return refPcsFactorRepository.findAll().stream().map(entity -> mapRefPcsFactorEntityToResource(entity)).collect(Collectors.toList());
    }

    @Override
    public Optional<RefPcsFactor> getRefPcsFactor(String code) throws CatiCommonsException, JrafDaoException {
        return refPcsFactorRepository.findById(code);
    }

    @Override
    public void addRefPcsFactor(RefPcsFactorDTO refPcsFactorDTO)
            throws CatiCommonsException, JrafDaoException {
        RefPcsFactor newRefPcsFactor = new RefPcsFactor();
        newRefPcsFactor.setCode(refPcsFactorDTO.getCode());
        newRefPcsFactor.setLibelle(refPcsFactorDTO.getLibelle());
        newRefPcsFactor.setFactor(refPcsFactorDTO.getFactor());
        newRefPcsFactor.setMaxPoints(refPcsFactorDTO.getMaxPoints());

        refPcsFactorRepository.saveAndFlush(newRefPcsFactor);
        LOGGER.info("RefPcsFactor added : " + newRefPcsFactor.toString());
    }

    @Override
    public void updateRefPcsFactor(RefPcsFactor refPcsFactor)
            throws CatiCommonsException, JrafDaoException {
        RefPcsFactor updatedRefPcsFactor = refPcsFactorRepository.saveAndFlush(refPcsFactor);
        LOGGER.info("RefPcsFactor updated : " + updatedRefPcsFactor.toString());
    }

    @Override
    public void removeRefPcsFactor(String code) throws CatiCommonsException, JrafDaoException {
        Optional<RefPcsFactor> refPcsFactor = refPcsFactorRepository.findById(code);
        if (refPcsFactor.isPresent()) {
            refPcsFactorRepository.deleteById(code);
        } else {
            String msg = String.format("Unable to find ref Pcs Factor(%s)", code);
            LOGGER.info(msg);
            throw new CatiCommonsException(msg);
        }
    }

    /**
     * transform RefPcsFactor entity to RefPcsFactorRessource
     *
     * @param entity
     * @return
     */
    private ModelRefPcsFactor mapRefPcsFactorEntityToResource(RefPcsFactor entity) {
        return new ModelRefPcsFactor(
                entity.getCode(),
                entity.getLibelle(),
                entity.getFactor(),
                entity.getMaxPoints());
    }
}
