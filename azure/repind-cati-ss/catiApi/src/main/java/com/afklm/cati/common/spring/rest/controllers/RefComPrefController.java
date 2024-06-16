package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.entity.*;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.*;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefComPrefResource;
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
@RequestMapping("/communicationpreferences")
@CrossOrigin
public class RefComPrefController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private Mapper dozerBeanMapper;

    @Autowired
    private RefComPrefService refComPrefService;

    @Autowired
    private RefComPrefTypeService refComPrefTypeService;

    @Autowired
    private RefComPrefDomainService refComPrefDomainService;

    @Autowired
    private RefComPrefGTypeService refComPrefGTypeService;

    @Autowired
    private RefComPrefMediaService refComPrefMediaService;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefComPref list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefResource> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefResource> refComPrefResources = null;

        try {
            List<RefComPref> refComPrefs = refComPrefService.getAllRefComPref();

            if (refComPrefs != null) {
                refComPrefResources = new ArrayList<RefComPrefResource>();
                for (RefComPref refComPref : refComPrefs) {
                    refComPrefResources.add(dozerBeanMapper.map(refComPref, RefComPrefResource.class));
                }
            }

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefs from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refComPrefResources;
    }

    /**
     * @param refComPrefResource RefComPref to add
     * @param request    the request http object
     * @param response   the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefComPrefResource refComPrefResource,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        RefComPref refComPref = dozerBeanMapper.map(refComPrefResource, RefComPref.class);
        refComPref.setRefComprefId(null);

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }

            Optional<RefComPrefType> refComPrefType = refComPrefTypeService.getRefComPrefType(refComPrefResource.getComType());
            Optional<RefComPrefGType> refComPrefGType = refComPrefGTypeService.getRefComPrefGType(refComPrefResource.getComGroupeType());
            Optional<RefComPrefDomain> refComPrefDomain = refComPrefDomainService.getRefComPrefDomain(refComPrefResource.getDomain());

            if (refComPrefType.isPresent() && refComPrefGType.isPresent() && refComPrefDomain.isPresent()) {

                refComPref.setComGroupeType(refComPrefGType.get());
                refComPref.setComType(refComPrefType.get());
                refComPref.setDomain(refComPrefDomain.get());
                refComPrefService.addRefComPref(refComPref, username);
            } else {
                String msg = "Domain, type or group type not found";
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.NOT_FOUND);
            }

        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPref into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPref into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPref.getRefComprefId()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPref by id
     *
     * @param refComPrefId       the refComPref identifier
     * @param httpServletRequest the Http servlet request
     * @return the refComPref corresponding to the specified refComPref identifier
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefResource refComPrefGet(@PathVariable("id") Integer refComPrefId,
                                            HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        RefComPrefResource requestBoResources = null;

        try {
            Optional<RefComPref> requestBo = refComPrefService.getRefComPref(refComPrefId);

            if (requestBo.isPresent()) {
                requestBoResources = dozerBeanMapper.map(requestBo.get(), RefComPrefResource.class);
            }

        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefs from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBoResources == null) {
            String msg = "RefComPref with id " + refComPrefId
                    + " not found";
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.NOT_FOUND);
        }

        return requestBoResources;
    }

    /**
     * Put a refComPref
     *
     * @param p            the RefComPref
     * @param refComPrefId the refComPref identifier
     * @return RefComPref
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefResource refComPrefUpdate(@Valid @RequestBody RefComPrefResource p,
                                               @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                               @PathVariable("id") Integer refComPrefId) throws CatiCommonsException {

        try {

            Optional<RefComPref> refComPrefToUpdate = refComPrefService.getRefComPref(refComPrefId);
            if (refComPrefToUpdate.isPresent()) {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }


                Optional<RefComPrefType> refComPrefType = refComPrefTypeService.getRefComPrefType(p.getComType());
                Optional<RefComPrefGType> refComPrefGType = refComPrefGTypeService.getRefComPrefGType(p.getComGroupeType());
                Optional<RefComPrefDomain> refComPrefDomain = refComPrefDomainService.getRefComPrefDomain(p.getDomain());

                if (refComPrefType.isPresent() && refComPrefGType.isPresent() && refComPrefDomain.isPresent()) {

                    if (p.getMedia() != null) {
                        Optional<RefComPrefMedia> refComPrefMedia = refComPrefMediaService.getRefComPrefMedia(p.getMedia());
                        if (refComPrefMedia.isPresent()) {
                            refComPrefToUpdate.get().setMedia(refComPrefMedia.get());
                        }
                    }

                    refComPrefToUpdate.get().setComGroupeType(refComPrefGType.get());
                    refComPrefToUpdate.get().setComType(refComPrefType.get());
                    refComPrefToUpdate.get().setDefaultLanguage1(p.getDefaultLanguage1());
                    refComPrefToUpdate.get().setDefaultLanguage2(p.getDefaultLanguage2());
                    refComPrefToUpdate.get().setDefaultLanguage3(p.getDefaultLanguage3());
                    refComPrefToUpdate.get().setDefaultLanguage4(p.getDefaultLanguage4());
                    refComPrefToUpdate.get().setDefaultLanguage5(p.getDefaultLanguage5());
                    refComPrefToUpdate.get().setDefaultLanguage6(p.getDefaultLanguage6());
                    refComPrefToUpdate.get().setDefaultLanguage7(p.getDefaultLanguage7());
                    refComPrefToUpdate.get().setDefaultLanguage8(p.getDefaultLanguage8());
                    refComPrefToUpdate.get().setDefaultLanguage9(p.getDefaultLanguage9());
                    refComPrefToUpdate.get().setDefaultLanguage10(p.getDefaultLanguage10());
                    refComPrefToUpdate.get().setDescription(p.getDescription());
                    refComPrefToUpdate.get().setDomain(refComPrefDomain.get());
                    refComPrefToUpdate.get().setFieldA(p.getFieldA());
                    refComPrefToUpdate.get().setFieldN(p.getFieldN());
                    refComPrefToUpdate.get().setFieldT(p.getFieldT());
                    refComPrefToUpdate.get().setMandatoryOptin(p.getMandatoryOptin());
                    refComPrefToUpdate.get().setMarket(p.getMarket());
                    refComPrefToUpdate.get().setDateModification(p.getDateModification());
                    refComPrefToUpdate.get().setSignatureModification(p.getSignatureModification());
                    refComPrefToUpdate.get().setSiteModification(p.getSiteModification());

                    refComPrefService.updateRefComPref(refComPrefToUpdate.get(), username);
                } else {
                    String msg = "Domain, type or group type not found";
                    treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
                }
            } else {
                String msg = "RefComPref with id " + refComPrefId + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refComPref with id " + refComPrefId + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return p;
    }

    /**
     * Delete a specific refComPref
     *
     * @param refComPrefId the refComPref identifier
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("id") Integer refComPrefId) throws JrafDaoException, CatiCommonsException {

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refComPrefService.removeRefComPref(refComPrefId, username);
        } catch (DataAccessException e) {
            String msg = "Can't delete refComPref with id " + refComPrefId + " into DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
