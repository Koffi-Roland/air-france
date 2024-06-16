package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefPcsScoreDTO;
import com.afklm.cati.common.entity.RefPcsScore;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsScore;
import com.afklm.cati.common.repository.RefPcsScoreRepository;
import com.afklm.cati.common.service.RefPcsScoreService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RefPcsScoreImp implements RefPcsScoreService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefPcsScoreImp.class);

    @Autowired
    private RefPcsScoreRepository refPcsScoreRepository;


    @Override
    public List<ModelRefPcsScore> getPcsScoreByFactorCode(String codeFactor) throws CatiCommonsException, JrafDaoException {
        List<RefPcsScore> refPcsScoreList = refPcsScoreRepository.findByCodeFactor(codeFactor);
        return refPcsScoreList.
                stream().map(entity -> mapRefPcsScoreEntityToResource(entity)).collect(Collectors.toList());
    }

    @Override
    public void addRefPcsScore(RefPcsScoreDTO refPcsScoreDTO) throws CatiCommonsException, JrafDaoException {
        RefPcsScore newRefPcsScore = new RefPcsScore();
        newRefPcsScore.setCode(refPcsScoreDTO.getCode());
        newRefPcsScore.setCodeFactor(refPcsScoreDTO.getCodeFactor());
        newRefPcsScore.setLibelle(refPcsScoreDTO.getLibelle());
        newRefPcsScore.setScore(refPcsScoreDTO.getScore());

        refPcsScoreRepository.saveAndFlush(newRefPcsScore);
        LOGGER.info("RefPcsScore added : " + newRefPcsScore.toString());
    }

    @Override
    public void updateRefPcsScore(RefPcsScore refPcsScore) throws CatiCommonsException, JrafDaoException {
        RefPcsScore updatedRefPcsScore = refPcsScoreRepository.saveAndFlush(refPcsScore);
        LOGGER.info("RefPcsScore updated : " + updatedRefPcsScore.toString());
    }

    @Override
    public void removeRefPcsScore(String code) throws CatiCommonsException, JrafDaoException {
        Optional<RefPcsScore> refPcsScore = refPcsScoreRepository.findById(code);
        if (refPcsScore.isPresent()) {
            refPcsScoreRepository.deleteById(code);
        } else {
            String msg = String.format("Unable to find ref Pcs Score (%s)", code);
            LOGGER.info(msg);
            throw new CatiCommonsException(msg);
        }
    }

    @Override
    public Optional<RefPcsScore> getRefPcsScore(String code) throws CatiCommonsException, JrafDaoException {
        return refPcsScoreRepository.findById(code);
    }

    /**
     * transform RefPcsScore entity to RefPcsScoreRessource
     *
     * @param entity
     * @return
     */
    private ModelRefPcsScore mapRefPcsScoreEntityToResource(RefPcsScore entity) {
        return new ModelRefPcsScore(
                entity.getCode(),
                entity.getCodeFactor(),
                entity.getLibelle(),
                entity.getScore());
    }
}
