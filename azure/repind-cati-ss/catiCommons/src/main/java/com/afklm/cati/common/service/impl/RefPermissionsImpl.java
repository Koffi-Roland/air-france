package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefPermissionsRepository;
import com.afklm.cati.common.repository.RefTrackingRepository;
import com.afklm.cati.common.service.RefComPrefDgtService;
import com.afklm.cati.common.service.RefPermissionsQuestionService;
import com.afklm.cati.common.service.RefPermissionsService;
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
 * Title : RefPermissionsImpl.java
 * </p>
 * Service Implementation to manage RefPermissions
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefPermissionsImpl implements RefPermissionsService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefPermissionsImpl.class);

    @Autowired
    private RefPermissionsRepository refPermissonsRepo;

    @Autowired
    private RefComPrefDgtService refComPrefDgtService;

    @Autowired
    private RefPermissionsQuestionService refPermissionsQuestionService;

    @Autowired
    private RefTrackingRepository refTrackingRepo;

    @Override
    public List<Integer> getRefComPrefDgtByRefPermissionsQuestion(RefPermissionsQuestion refPermissionsQuestion) throws CatiCommonsException, JrafDaoException {
        return refPermissonsRepo.getRefComPrefDgtByRefPermissionsQuestion(refPermissionsQuestion);
    }

    @Override
    public void addRefPermissions(RefPermissions refPermissions, Integer permissionsQuestionId, List<Integer> listComPrefDgt, String matricule) throws CatiCommonsException, JrafDaoException {

        Optional<RefPermissionsQuestion> permissionsQuestion = refPermissionsQuestionService.getRefPermissionsQuestion(permissionsQuestionId);
        List<Integer> comPrefDgtForThisPermissionQuestion = getRefComPrefDgtByRefPermissionsQuestion(permissionsQuestion.get());

        for (Integer idToAdd : listComPrefDgt) {

            if (!comPrefDgtForThisPermissionQuestion.contains(idToAdd)) {

                Optional<RefComPrefDgt> refComPrefDgt = refComPrefDgtService.getRefComPrefDgt(idToAdd);
                RefPermissionsId refPermissionsId = new RefPermissionsId();
                refPermissionsId.setQuestionId(permissionsQuestion.get());
                refPermissionsId.setRefComPrefDgt(refComPrefDgt.get());

                refPermissions.setDateCreation(new Date());
                refPermissions.setDateModification(new Date());
                refPermissions.setSignatureCreation(matricule);
                refPermissions.setSignatureModification(matricule);
                refPermissions.setSiteCreation("CATI");
                refPermissions.setSiteModification("CATI");

                refPermissions.setRefPermissionsId(refPermissionsId);

                refPermissonsRepo.saveAndFlush(refPermissions);

                String msg = "RefPermissions added " + matricule + ". Permission question with id " + permissionsQuestion.get().getId() + " and RefComPrefDgt with id " + refComPrefDgt.get().getRefComPrefDgtId();
                LOGGER.info(msg);

            }

        }

        for (Integer idToRemove : comPrefDgtForThisPermissionQuestion) {

            if (!listComPrefDgt.contains(idToRemove)) {

                Optional<RefComPrefDgt> refComPrefDgt = refComPrefDgtService.getRefComPrefDgt(idToRemove);
                RefPermissionsId refPermissionsId = new RefPermissionsId();
                refPermissionsId.setQuestionId(permissionsQuestion.get());
                refPermissionsId.setRefComPrefDgt(refComPrefDgt.get());

                refPermissonsRepo.deleteById(refPermissionsId);

                String msg = "RefPermissions removed by " + matricule + ". Permission question with id " + permissionsQuestion.get().getId() + " and RefComPrefDgt with id " + refComPrefDgt.get().getRefComPrefDgtId();
                LOGGER.info(msg);

            }

        }

        //REPIND-1238: Tracking the update
        //Get Compref before
        String beforeValue = "";
        for (Integer comprefId : comPrefDgtForThisPermissionQuestion) {
            beforeValue += comprefId.toString() + ";";
        }
        if (beforeValue.length() != 0) beforeValue = beforeValue.substring(0, beforeValue.length() - 1);

        //Get Compref after
        String afterValue = "";
        for (Integer comprefId : listComPrefDgt) {
            afterValue += comprefId.toString() + ";";
        }
        if (afterValue.length() != 0) afterValue = afterValue.substring(0, afterValue.length() - 1);

        TrackingRefPermissions trackingRefPermissions = new TrackingRefPermissions();
        trackingRefPermissions.setActionType("UPDATE");
        trackingRefPermissions.setPermissionId(permissionsQuestion.get());
        trackingRefPermissions.setTableName("REF_PERMISSIONS");
        trackingRefPermissions.setBeforeValue(beforeValue);
        trackingRefPermissions.setAfterValue(afterValue);
        trackingRefPermissions.setDateModification(new Date());
        trackingRefPermissions.setSiteModification("CATI");
        trackingRefPermissions.setSignatureModification(matricule);

        refTrackingRepo.saveAndFlush(trackingRefPermissions);
    }

}
