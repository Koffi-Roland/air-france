package com.afklm.cati.common.service;

import com.afklm.cati.common.entity.RefComPrefGroup;
import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;

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
public interface RefComPrefGroupService {

    /**
     * Retrieve all refComPrefGroup
     *
     * @return list of refComPrefGroup
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefComPrefGroup> getAllRefComPrefGroup() throws CatiCommonsException, JrafDaoException;

    List<Integer> getRefComPrefDgtByRefComPrefGroupInfo(RefComPrefGroupInfo refComPrefGroupInfo);

    void addRefComPrefGroup(RefComPrefGroup refComPrefGroup, Integer comPrefGroupInfoId, List<Integer> listComPrefDgt, String matricule) throws CatiCommonsException, JrafDaoException;


}
