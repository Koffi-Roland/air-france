package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.RefPermissionsQuestionDTO;
import com.afklm.cati.common.entity.RefPermissionsQuestion;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefPermissionsQuestionRepository;
import com.afklm.cati.common.service.RefPermissionsQuestionService;
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
 * Title : RefPermissionsQuestionImpl.java
 * </p>
 * Service Implementation to manage RefPermissionsQuestion
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefPermissionsQuestionImpl implements RefPermissionsQuestionService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefPermissionsQuestionImpl.class);

    @Autowired
    private RefPermissionsQuestionRepository refPermissonsQuestionRepo;

    @Override
    public List<RefPermissionsQuestion> getAllRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException {
        return refPermissonsQuestionRepo.findAllByOrderById();
    }

    @Override
    public Optional<RefPermissionsQuestion> getRefPermissionsQuestion(Integer id) throws CatiCommonsException, JrafDaoException {
        return refPermissonsQuestionRepo.findById(id);
    }

    @Override
    public void addRefPermissionsQuestion(RefPermissionsQuestionDTO refPermissionsQuestionDTO, String matricule) throws CatiCommonsException, JrafDaoException {
        RefPermissionsQuestion refPermissionsQuestionAdded = new RefPermissionsQuestion();
        refPermissionsQuestionAdded.setName(refPermissionsQuestionDTO.getName());
        refPermissionsQuestionAdded.setQuestion(refPermissionsQuestionDTO.getQuestion());
        refPermissionsQuestionAdded.setQuestionEN(refPermissionsQuestionDTO.getQuestionEN());
        refPermissionsQuestionAdded.setDateCreation(new Date());
        refPermissionsQuestionAdded.setSignatureCreation(matricule);
        refPermissionsQuestionAdded.setSiteCreation("CATI");
        refPermissionsQuestionAdded.setDateModification(new Date());
        refPermissionsQuestionAdded.setSignatureModification(matricule);
        refPermissionsQuestionAdded.setSiteModification("CATI");

        refPermissonsQuestionRepo.saveAndFlush(refPermissionsQuestionAdded);
        String msg = "RefPermissionsQuestion added : " + refPermissionsQuestionAdded.toString();
        LOGGER.info(msg);

    }

    @Override
    public void updateRefPermissionsQuestion(RefPermissionsQuestion refPermissionsQuestion, String matricule) throws CatiCommonsException, JrafDaoException {

        refPermissionsQuestion.setDateModification(new Date());
        refPermissionsQuestion.setSignatureModification(matricule);
        refPermissionsQuestion.setSiteModification("CATI");

        RefPermissionsQuestion refPermissionsQuestionUpdated = refPermissonsQuestionRepo.saveAndFlush(refPermissionsQuestion);
        String msg = "RefPermissionsQuestion updated : " + refPermissionsQuestionUpdated.toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeRefPermissionsQuestion(Integer id, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<RefPermissionsQuestion> refPermissionsQuestion = getRefPermissionsQuestion(id);
        if (refPermissionsQuestion.isPresent()) {
            LOGGER.info("RefPermissionsQuestion with id " + id + " deleted by " + matricule + " : " + refPermissionsQuestion.toString());
        }
        refPermissonsQuestionRepo.deleteById(id);
    }

    @Override
    public Long countRefPermissionsByPermissionsQuestionId(Integer refPermissionsQuestionId) throws CatiCommonsException, JrafDaoException {

        Optional<RefPermissionsQuestion> refPermissionsQuestion = getRefPermissionsQuestion(refPermissionsQuestionId);

        if (refPermissionsQuestion.isPresent()) {
            return refPermissonsQuestionRepo.countRefPermissionsById(refPermissionsQuestion.get());
        } else {
            return 0L;
        }
    }

    @Override
    public Long countRefPermissionsByPermissionsQuestion(RefPermissionsQuestion refPermissionsQuestion) throws CatiCommonsException, JrafDaoException {

        return refPermissonsQuestionRepo.countRefPermissionsById(refPermissionsQuestion);
    }

}
