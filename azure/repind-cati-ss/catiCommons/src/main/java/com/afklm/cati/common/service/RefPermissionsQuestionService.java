package com.afklm.cati.common.service;

import com.afklm.cati.common.dto.RefPermissionsQuestionDTO;
import com.afklm.cati.common.entity.RefPermissionsQuestion;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefPermissionsQuestionService.java
 * </p>
 * Service interface to manage RefPermissionsQuestion
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public interface RefPermissionsQuestionService {

    /**
     * Retrieve all refPermissionsQuestion
     *
     * @return list of refPermissionsQuestion
     * @throws CatiCommonsException, JrafDaoException
     */
    List<RefPermissionsQuestion> getAllRefPermissionsQuestion() throws CatiCommonsException, JrafDaoException;

    /**
     * Get one refPermissionsQuestion
     *
     * @return refPermissionsQuestion
     * @throws CatiCommonsException, JrafDaoException
     */
    Optional<RefPermissionsQuestion> getRefPermissionsQuestion(Integer id) throws CatiCommonsException, JrafDaoException;

    /**
     * Post one refPermissionsQuestion
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void addRefPermissionsQuestion(RefPermissionsQuestionDTO refPermissionsQuestionDTO, String username) throws CatiCommonsException, JrafDaoException;

    /**
     * Update refPermissionsQuestion
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void updateRefPermissionsQuestion(RefPermissionsQuestion refComPrefDomain, String username) throws CatiCommonsException, JrafDaoException;

    /**
     * Remove refPermissionsQuestion
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    void removeRefPermissionsQuestion(Integer id, String matricule) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refPermissionsQuestion
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    public Long countRefPermissionsByPermissionsQuestionId(Integer refPermissionsQuestionId) throws CatiCommonsException, JrafDaoException;

    /**
     * Count refPermissionsQuestion
     *
     * @throws CatiCommonsException, JrafDaoException
     */
    public Long countRefPermissionsByPermissionsQuestion(RefPermissionsQuestion refPermissionsQuestion) throws CatiCommonsException, JrafDaoException;

}
