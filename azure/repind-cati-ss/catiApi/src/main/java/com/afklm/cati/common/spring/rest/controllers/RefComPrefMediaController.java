package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefComPrefMediaDTO;
import com.afklm.cati.common.entity.RefComPrefMedia;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefMediaService;
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
@RequestMapping("/medias")
@CrossOrigin
public class RefComPrefMediaController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefMediaController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefMediaService refComPrefMediaService;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefComPrefMedia list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefMedia> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefMedia> refComPrefs = null;

        try {
            refComPrefs = refComPrefMediaService.getAllRefComPrefMedia();

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefMedia from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refComPrefs;
    }

    /**
     * @param refComPrefMedia RefComPrefMedia to add
     * @param request         the request http object
     * @param response        the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefComPrefMediaDTO refComPrefMediaDTO,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refComPrefMediaService.addRefComPrefMedia(refComPrefMediaDTO, username);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPrefMedia into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPrefMedia into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPrefMediaDTO.getCodeMedia()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPrefMedia by code
     *
     * @param refComPrefMediaCode the refComPrefMedia code
     * @param httpServletRequest  the Http servlet request
     * @return the refComPref corresponding to the specified refComPrefMedia code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefMedia refComPrefMediaGet(@PathVariable("code") String refComPrefMediaCode,
                                              HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefMedia> requestBo = Optional.empty();
        try {
            requestBo = refComPrefMediaService.getRefComPrefMedia(refComPrefMediaCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefMedia from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!requestBo.isPresent()) {
            String msg = "RefComPrefMedia with code " + refComPrefMediaCode
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refComPrefMedia
     *
     * @param p                   the RefComPrefMedia
     * @param refComPrefMediaCode the refComPrefMedia code
     * @return RefComPrefMedia
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{code}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefMedia refComPrefUpdate(@Valid @RequestBody RefComPrefMediaDTO p,
                                            @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                            @PathVariable("code") String refComPrefMediaCode) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<RefComPrefMedia> refComPrefMediaToUpdate = refComPrefMediaService.getRefComPrefMedia(refComPrefMediaCode);
            if (refComPrefMediaToUpdate.isPresent()) {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefMediaToUpdate.get().setCodeMedia(p.getCodeMedia());
                refComPrefMediaToUpdate.get().setLibelleMedia(p.getLibelleMedia());
                refComPrefMediaToUpdate.get().setLibelleMediaEN(p.getLibelleMediaEN());

                refComPrefMediaService.updateRefComPrefMedia(refComPrefMediaToUpdate.get(), username);
            } else {
                String msg = "RefComPrefMedia with code " + refComPrefMediaCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refComPrefMedia with code " + refComPrefMediaCode + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refComPrefMediaService.getRefComPrefMedia(p.getCodeMedia()).get();
    }

    /**
     * Delete a specific refComPrefMedia
     *
     * @param refComPrefMediaCode the refComPrefMedia code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("code") String refComPrefMediaCode) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefMedia> refComPrefMedia = refComPrefMediaService.getRefComPrefMedia(refComPrefMediaCode);
        Long nbResultRefComPref = refComPrefMediaService.countRefComPrefByMedia(refComPrefMedia.get());
        if (nbResultRefComPref == 0) {
            try {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefMediaService.removeRefComPrefMedia(refComPrefMediaCode, username);
            } catch (DataAccessException e) {
                String msg = "Can't delete refComPrefMedia with code " + refComPrefMediaCode + " into DB";
                LOGGER.error(msg, e);
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            String msg = "Can't delete refComPrefMedia with code " + refComPrefMediaCode + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
