package com.afklm.cati.common.service;

import com.afklm.cati.common.entity.RefPermissions;
import com.afklm.cati.common.entity.RefPermissionsQuestion;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;

/**
 * <p>
 * Title : RefPermissionsService.java
 * </p>
 * Service interface to manage RefPermissions
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefPermissionsService {

    /**
     * Retrieve refComPrefDgt
     *
     * @return list of refComPrefDgt
     * @throws CatiCommonsException, JrafDaoException
     */
    List<Integer> getRefComPrefDgtByRefPermissionsQuestion(RefPermissionsQuestion refPermissionsQuestion) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refPermissions
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefPermissions(RefPermissions refPermission, Integer permissionsQuestionId, List<Integer> listComPrefDgt, String username) throws CatiCommonsException, JrafDaoException;

}
