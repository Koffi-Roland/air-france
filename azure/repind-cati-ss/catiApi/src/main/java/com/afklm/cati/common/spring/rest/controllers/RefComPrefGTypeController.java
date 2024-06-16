package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefComPrefGTypeDTO;
import com.afklm.cati.common.entity.RefComPrefGType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefGTypeService;
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
@RequestMapping("/grouptypes")
@CrossOrigin
public class RefComPrefGTypeController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefGTypeController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefGTypeService refComPrefGTypeService;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefComPrefGType list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefGType> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefGType> refComPrefs = null;

        try {
            refComPrefs = refComPrefGTypeService.getAllRefComPrefGType();

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefGType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refComPrefs;
    }

    /**
     * @param refComPrefGTypeDTO RefComPrefGType to add
     * @param request            the request http object
     * @param response           the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefComPrefGTypeDTO refComPrefGTypeDTO,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refComPrefGTypeService.addRefComPrefGType(refComPrefGTypeDTO, username);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPrefType into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPrefGType into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPrefGTypeDTO.getCodeGType()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPrefGType by code
     *
     * @param refComPrefGTypeCode the refComPrefGType code
     * @param httpServletRequest  the Http servlet request
     * @return the refComPrefGType corresponding to the specified refComPrefGType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefGType refComPrefTypeGet(@PathVariable("code") String refComPrefGTypeCode,
                                             HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefGType> requestBo = Optional.empty();
        try {
            requestBo = refComPrefGTypeService.getRefComPrefGType(refComPrefGTypeCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefGType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!requestBo.isPresent()) {
            String msg = "RefComPrefGType with code " + refComPrefGTypeCode
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refComPrefGType
     *
     * @param p                   the RefComPrefGType
     * @param refComPrefGTypeCode the refComPrefGType code
     * @return RefComPrefGType
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{code}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefGType refComPrefGTypeUpdate(@Valid @RequestBody RefComPrefGTypeDTO p,
                                                 @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                                 @PathVariable("code") String refComPrefGTypeCode) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<RefComPrefGType> refComPrefGTypeToUpdate = refComPrefGTypeService.getRefComPrefGType(refComPrefGTypeCode);
            if (refComPrefGTypeToUpdate.isPresent()) {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefGTypeToUpdate.get().setCodeGType(p.getCodeGType());
                refComPrefGTypeToUpdate.get().setLibelleGType(p.getLibelleGType());
                refComPrefGTypeToUpdate.get().setLibelleGTypeEN(p.getLibelleGTypeEN());

                refComPrefGTypeService.updateRefComPrefGType(refComPrefGTypeToUpdate.get(), username);
            } else {
                String msg = "RefComPrefGType with code " + refComPrefGTypeCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refComPrefGType with code " + refComPrefGTypeCode + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refComPrefGTypeService.getRefComPrefGType(p.getCodeGType()).get();
    }

    /**
     * Delete a specific refComPrefGType
     *
     * @param refComPrefGTypeCode the refComPrefGType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefGTypeDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("code") String refComPrefGTypeCode) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefGType> refComPrefGType = refComPrefGTypeService.getRefComPrefGType(refComPrefGTypeCode);
        Long nbResultRefComPref = refComPrefGTypeService.countRefComPrefByGType(refComPrefGType.get());
        if (nbResultRefComPref == 0) {

            try {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefGTypeService.removeRefComPrefGType(refComPrefGTypeCode, username);
            } catch (DataAccessException e) {
                String msg = "Can't delete refComPrefGType with code " + refComPrefGTypeCode + " into DB";
                LOGGER.error(msg, e);
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            String msg = "Can't delete refComPrefGType with code " + refComPrefGTypeCode + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
