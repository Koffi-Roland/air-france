package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefGroup;
import com.afklm.cati.common.entity.RefComPrefGroupId;
import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.RefComPrefGroupRepository;
import com.afklm.cati.common.service.RefComPrefDgtService;
import com.afklm.cati.common.service.RefComPrefGroupInfoService;
import com.afklm.cati.common.service.RefComPrefGroupService;
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
 * Title : RefComPrefGroupImpl.java
 * </p>
 * Service Implementation to manage RefComPrefGroup
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class RefComPrefGroupImpl implements RefComPrefGroupService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RefComPrefGroupImpl.class);

    @Autowired
    private RefComPrefGroupRepository refComPrefGroupRepo;

    @Autowired
    private RefComPrefGroupInfoService refComPrefGroupInfoService;

    @Autowired
    private RefComPrefDgtService refComPrefDgtService;

    @Override
    public List<RefComPrefGroup> getAllRefComPrefGroup() throws CatiCommonsException, JrafDaoException {
        List<RefComPrefGroup> groups = refComPrefGroupRepo.findAll();
        LOGGER.debug("Size List<RefComPrefGroup> : " + groups.size());
        return groups;
    }

    @Override
    public List<Integer> getRefComPrefDgtByRefComPrefGroupInfo(RefComPrefGroupInfo refComPrefGroupInfo) {
        return refComPrefGroupRepo.getRefComPrefDgtByRefComPrefGroupInfo(refComPrefGroupInfo);
    }

    @Override
    public void addRefComPrefGroup(RefComPrefGroup refComPrefGroup, Integer comPrefGroupInfoId,
                                   List<Integer> listComPrefDgt, String matricule) throws CatiCommonsException, JrafDaoException {

        Optional<RefComPrefGroupInfo> refComPrefGroupInfo = refComPrefGroupInfoService.getRefComPrefGroupInfo(comPrefGroupInfoId);
        List<Integer> comPrefDgt = getRefComPrefDgtByRefComPrefGroupInfo(refComPrefGroupInfo.get());

        for (Integer idToAdd : listComPrefDgt) {

            if (!comPrefDgt.contains(idToAdd)) {

                Optional<RefComPrefDgt> refComPrefDgt = refComPrefDgtService.getRefComPrefDgt(idToAdd);
                RefComPrefGroupId refComPrefGroupId = new RefComPrefGroupId();
                refComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo.get());
                refComPrefGroupId.setRefComPrefDgt(refComPrefDgt.get());

                refComPrefGroup.setRefComPrefGroupId(refComPrefGroupId);
                refComPrefGroup.setDateCreation(new Date());
                refComPrefGroup.setDateModification(new Date());
                refComPrefGroup.setSiteCreation("CATI");
                refComPrefGroup.setSiteModification("CATI");
                refComPrefGroup.setSignatureCreation(matricule);
                refComPrefGroup.setSignatureModification(matricule);

                refComPrefGroupRepo.saveAndFlush(refComPrefGroup);

                String msg = "refComPrefGroup added. refComPrefGroup question with id " + refComPrefGroupInfo.get().getId() + " and RefComPrefDgt with id " + refComPrefDgt.get().getRefComPrefDgtId();
                LOGGER.info(msg);

            }

        }

        for (Integer idToRemove : comPrefDgt) {

            if (!listComPrefDgt.contains(idToRemove)) {

                Optional<RefComPrefDgt> refComPrefDgt = refComPrefDgtService.getRefComPrefDgt(idToRemove);
                RefComPrefGroupId refComPrefGroupId = new RefComPrefGroupId();
                refComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo.get());
                refComPrefGroupId.setRefComPrefDgt(refComPrefDgt.get());
                refComPrefGroup.setRefComPrefGroupId(refComPrefGroupId);

                refComPrefGroupRepo.delete(refComPrefGroup);

                String msg = "RefComPrefGroup removed. GroupInfo with id " + refComPrefGroupInfo.get().getId() + " and RefComPrefDgt with id " + refComPrefDgt.get().getRefComPrefDgtId();
                LOGGER.info(msg);

            }

        }

    }


}
