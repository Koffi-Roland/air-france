package com.afklm.cati.common.service;

import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefGroupService.java
 * </p>
 * Service interface to manage RefComPrefGroup
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefComPrefGroupInfoService {

    /**
     * Retrieve all refComPrefGroup
     *
     * @return list of refComPrefGroup
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefGroupInfo> getAllRefComPrefGroupInfo() throws CatiCommonsException, JrafDaoException;

    Optional<RefComPrefGroupInfo> getRefComPrefGroupInfoCode(int code) throws CatiCommonsException, JrafDaoException;

    void addRefComPrefGroupInfo(RefComPrefGroupInfo refComPrefGroupInfo, String matricule);

    void updateRefComPref(RefComPrefGroupInfo refComPrefGroupInfoUpdate, String matricule);

    Optional<RefComPrefGroupInfo> getRefComPrefGroupInfo(Integer refComPrefGroupInfoId);

    void removeRefComPrefGroupInfo(Integer id, String matricule) throws CatiCommonsException, JrafDaoException;

    public Long countRefComPrefGroupByRefComPrefGroupInfo(Integer refComPrefGroupInfoId) throws CatiCommonsException, JrafDaoException;

}
