package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.entity.RefComPrefDgt;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.entity.RefComPrefType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefComPrefDgtResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.cati.common.util.HeadersUtil;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author AF-KLM
 */
@RestController
@RequestMapping("/communicationpreferencesdgt")
@CrossOrigin
public class RefComPrefDgtController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefDgtController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefDgtService refComPrefDgtService;

    @Autowired
    private RefComPrefTypeService refComPrefTypeService;

    @Autowired
    private RefComPrefDomainService refComPrefDomainService;

    @Autowired
    private RefComPrefGTypeService refComPrefGTypeService;

    @Autowired
    private RefComPrefService refComPrefMlService;

    @Autowired
    private RefPermissionsQuestionService refPermissionsQuestionService;

    @Autowired
    private Mapper dozerBeanMapper;

    private static final String DB_ERROR = "db error";
    private static final String NOT_FOUND = "not found";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefComPrefDgt list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefDgtResource> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefDgtResource> refComPrefDgtResources = null;

        try {

            List<RefComPrefDgt> refComPrefDgts = refComPrefDgtService.getAllRefComPrefDgt();

            if (refComPrefDgts != null) {
                refComPrefDgtResources = new ArrayList<RefComPrefDgtResource>();
                for (RefComPrefDgt refComPrefDgt : refComPrefDgts) {
                    refComPrefDgtResources.add(dozerBeanMapper.map(refComPrefDgt, RefComPrefDgtResource.class));
                }
            }

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefDgts from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refComPrefDgtResources;
    }

    /**
     * @param refComPrefDgtResource RefComPrefDgt to add
     * @param request       the request http object
     * @param response      the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefComPrefDgtResource refComPrefDgtResource,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        RefComPrefDgt refComPrefDgt = dozerBeanMapper.map(refComPrefDgtResource, RefComPrefDgt.class);
        refComPrefDgt.setRefComPrefDgtId(null);

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }

            Optional<RefComPrefType> refComPrefType = refComPrefTypeService.getRefComPrefType(refComPrefDgtResource.getType());
            Optional<RefComPrefGType> refComPrefGType = refComPrefGTypeService.getRefComPrefGType(refComPrefDgtResource.getGroupType());
            Optional<RefComPrefDomain> refComPrefDomain = refComPrefDomainService.getRefComPrefDomain(refComPrefDgtResource.getDomain());

            if (refComPrefType.isPresent() && refComPrefGType.isPresent() && refComPrefDomain.isPresent()) {
                refComPrefDgt.setGroupType(refComPrefGType.get());
                refComPrefDgt.setType(refComPrefType.get());
                refComPrefDgt.setDomain(refComPrefDomain.get());
                refComPrefDgtService.addRefComPrefDgt(refComPrefDgt, username);
            } else {
                String msg = "Domain, type or group type not found";
                treatDefaultCatiException(NOT_FOUND, msg, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPrefDgt into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPrefDgt into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPrefDgt.getRefComPrefDgtId()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPrefDgt by id
     *
     * @param refComPrefDgtId    the refComPrefDgt identifier
     * @param httpServletRequest the Http servlet request
     * @return the refComPrefDgt corresponding to the specified refComPrefDgt identifier
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefDgtResource refComPrefDgtGet(@PathVariable("id") Integer refComPrefDgtId,
                                                  HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        RefComPrefDgtResource requestBoResources = null;
        try {

            Optional<RefComPrefDgt> requestBoDgt = refComPrefDgtService.getRefComPrefDgt(refComPrefDgtId);

            if (requestBoDgt.isPresent()) {
                requestBoResources = dozerBeanMapper.map(requestBoDgt.get(), RefComPrefDgtResource.class);
            }
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefDgts from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBoResources == null) {
            String msg = "RefComPrefDgt with id " + refComPrefDgtId
                    + " not found";
            treatDefaultCatiException(NOT_FOUND, msg, HttpStatus.NOT_FOUND);
        }

        return requestBoResources;
    }

    /**
     * Put a refComPrefDgt
     *
     * @param p               the RefComPrefDgt
     * @param refComPrefDgtId the refComPrefDgt identifier
     * @return RefComPrefDgt
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefDgtResource refComPrefDgtUpdate(@Valid @RequestBody RefComPrefDgtResource p,
                                                     @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                                     @PathVariable("id") Integer refComPrefDgtId) throws CatiCommonsException {

        try {

            Optional<RefComPrefDgt> refComPrefDgtToUpdate = refComPrefDgtService.getRefComPrefDgt(refComPrefDgtId);
            if (refComPrefDgtToUpdate.isPresent()) {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }

                Optional<RefComPrefType> refComPrefType = refComPrefTypeService.getRefComPrefType(p.getType());
                Optional<RefComPrefGType> refComPrefGType = refComPrefGTypeService.getRefComPrefGType(p.getGroupType());
                Optional<RefComPrefDomain> refComPrefDomain = refComPrefDomainService.getRefComPrefDomain(p.getDomain());

                if (refComPrefType.isPresent() && refComPrefGType.isPresent() && refComPrefDomain.isPresent()) {
                    refComPrefDgtToUpdate.get().setGroupType(refComPrefGType.get());
                    refComPrefDgtToUpdate.get().setType(refComPrefType.get());
                    refComPrefDgtToUpdate.get().setDomain(refComPrefDomain.get());
                    refComPrefDgtToUpdate.get().setDescription(p.getDescription());

                    refComPrefDgtService.updateRefComPrefDgt(refComPrefDgtToUpdate.get(), username);
                } else {
                    String msg = "Domain, type or group type not found";
                    treatDefaultCatiException(NOT_FOUND, msg, HttpStatus.NOT_FOUND);
                }

            } else {
                String msg = "RefComPrefDgt with id " + refComPrefDgtId + " doesn't exist";
                treatDefaultCatiException(NOT_FOUND, msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refComPrefDgt with id " + refComPrefDgtId + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return p;
    }

    /**
     * Delete a specific refComPrefDgt
     *
     * @param refComPrefDgtId the refComPrefDgt identifier
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefDgtDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("id") Integer refComPrefDgtId) throws JrafDaoException, CatiCommonsException {

        Long nbResultRefComPref = refComPrefMlService.countRefComPrefMlByDgt(refComPrefDgtId);
        Long nbResultPermission = refPermissionsQuestionService.countRefPermissionsByPermissionsQuestionId(refComPrefDgtId);
        if ((nbResultRefComPref + nbResultPermission) == 0) {
            try {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefDgtService.removeRefComPrefDgt(refComPrefDgtId, username);
            } catch (DataAccessException e) {
                String msg = "Can't delete refComPrefDgt with id " + refComPrefDgtId + " into DB";
                LOGGER.error(msg, e);
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            String msg = "Can't delete refComPrefDgt with id " + refComPrefDgtId + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
