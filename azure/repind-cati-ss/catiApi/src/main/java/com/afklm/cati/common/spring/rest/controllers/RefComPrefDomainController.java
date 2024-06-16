package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefComPrefDomainDTO;
import com.afklm.cati.common.entity.RefComPrefDomain;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefDomainService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.cati.common.util.HeadersUtil;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import io.netty.handler.codec.http.DefaultHttpHeaders;
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
import java.util.List;
import java.util.Optional;

/**
 * @author AF-KLM
 */
@RestController
@RequestMapping("/domains")
@CrossOrigin
public class RefComPrefDomainController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefDomainController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefDomainService refComPrefDomainService;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefComPrefDomain list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefDomain> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefDomain> refComPrefs = null;

        try {
            refComPrefs = refComPrefDomainService.getAllRefComPrefDomain();

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefDomain from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refComPrefs;
    }

    /**
     * @param refComPrefDomainDTO RefComPrefDomain to add
     * @param request             the request http object
     * @param response            the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @Valid @RequestBody RefComPrefDomainDTO refComPrefDomainDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {

            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refComPrefDomainService.addRefComPrefDomain(refComPrefDomainDTO, username);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPrefDomain into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPrefDomain into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPrefDomainDTO.getCodeDomain()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPrefDomain by code
     *
     * @param refComPrefDomainCode the refComPrefDomain code
     * @param httpServletRequest   the Http servlet request
     * @return the refComPrefDomain corresponding to the specified refComPrefDomain code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefDomain refComPrefDomainGet(@PathVariable("code") String refComPrefDomainCode,
                                                HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefDomain> requestBo = Optional.empty();
        try {
            requestBo = refComPrefDomainService.getRefComPrefDomain(refComPrefDomainCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefDomains from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!requestBo.isPresent()) {
            String msg = "RefComPrefDomain with code " + refComPrefDomainCode
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refComPrefDomain
     *
     * @param p                    the RefComPrefDomain
     * @param refComPrefDomainCode the refComPrefDomain code
     * @return RefComPrefDomain
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{code}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefDomain refComPrefUpdate(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                             @Valid @RequestBody RefComPrefDomainDTO p,
                                             @PathVariable("code") String refComPrefDomainCode) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<RefComPrefDomain> refComPrefDomainToUpdate = refComPrefDomainService.getRefComPrefDomain(refComPrefDomainCode);
            if (refComPrefDomainToUpdate.isPresent()) {

                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefDomainToUpdate.get().setCodeDomain(p.getCodeDomain());
                refComPrefDomainToUpdate.get().setLibelleDomain(p.getLibelleDomain());
                refComPrefDomainToUpdate.get().setLibelleDomainEN(p.getLibelleDomainEN());

                refComPrefDomainService.updateRefComPrefDomain(refComPrefDomainToUpdate.get(), username);
            } else {
                String msg = "RefComPrefDomain with code " + refComPrefDomainCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refComPrefDomain with code " + refComPrefDomainCode + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refComPrefDomainService.getRefComPrefDomain(p.getCodeDomain()).get();
    }

    /**
     * Delete a specific refComPrefDomain
     *
     * @param refComPrefDomainCode the refComPrefDomain code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("code") String refComPrefDomainCode) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefDomain> refComPrefDomain = refComPrefDomainService.getRefComPrefDomain(refComPrefDomainCode);
        Long nbResultRefComPref = refComPrefDomainService.countRefComPrefByDomain(refComPrefDomain.get());
        if (nbResultRefComPref == 0) {
            try {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefDomainService.removeRefComPrefDomain(refComPrefDomainCode, username);
            } catch (DataAccessException e) {
                String msg = "Can't delete refComPrefDomain with code " + refComPrefDomainCode + " into DB";
                LOGGER.error(msg, e);
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            String msg = "Can't delete refComPrefDomain with code " + refComPrefDomainCode + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
