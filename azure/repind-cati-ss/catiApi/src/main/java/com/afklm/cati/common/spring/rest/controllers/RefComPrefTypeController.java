package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefComPrefTypeDTO;
import com.afklm.cati.common.entity.RefComPrefType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefTypeService;
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
@RequestMapping("/communicationtypes")
@CrossOrigin
public class RefComPrefTypeController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefTypeController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefTypeService refComPrefTypeService;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefComPrefType list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefType> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefType> refComPrefs = null;

        try {
            refComPrefs = refComPrefTypeService.getAllRefComPrefType();

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refComPrefs;
    }

    /**
     * @param refComPrefTypeDTO RefComPrefType to add
     * @param request           the request http object
     * @param response          the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefComPrefTypeDTO refComPrefTypeDTO,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {

            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }

            refComPrefTypeService.addRefComPrefType(refComPrefTypeDTO, username);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPrefType into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPrefType into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPrefTypeDTO.getCodeType()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPrefType by code
     *
     * @param refComPrefTypeCode the refComPrefType code
     * @param httpServletRequest the Http servlet request
     * @return the refComPrefType corresponding to the specified refComPrefType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefType refComPrefTypeGet(@PathVariable("code") String refComPrefTypeCode,
                                            HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefType> requestBo = Optional.empty();
        try {
            requestBo = refComPrefTypeService.getRefComPrefType(refComPrefTypeCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!requestBo.isPresent()) {
            String msg = "RefComPrefType with code " + refComPrefTypeCode
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refComPrefType
     *
     * @param p                  the RefComPrefType
     * @param refComPrefTypeCode the refComPrefType code
     * @return RefComPrefType
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{code}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefType refComPrefTypeUpdate(@Valid @RequestBody RefComPrefTypeDTO p,
                                               @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                               @PathVariable("code") String refComPrefTypeCode) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<RefComPrefType> refComPrefTypeToUpdate = refComPrefTypeService.getRefComPrefType(refComPrefTypeCode);
            if (refComPrefTypeToUpdate.isPresent()) {

                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefTypeToUpdate.get().setCodeType(p.getCodeType());
                refComPrefTypeToUpdate.get().setLibelleType(p.getLibelleType());
                refComPrefTypeToUpdate.get().setLibelleTypeEN(p.getLibelleTypeEN());

                refComPrefTypeService.updateRefComPrefType(refComPrefTypeToUpdate.get(), username);
            } else {
                String msg = "RefComPrefType with code " + refComPrefTypeCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refComPrefType with code " + refComPrefTypeCode + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refComPrefTypeService.getRefComPrefType(p.getCodeType()).get();
    }

    /**
     * Delete a specific refComPrefType
     *
     * @param refComPrefTypeCode the refComPrefType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefTypeDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("code") String refComPrefTypeCode) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefType> refComPrefType = refComPrefTypeService.getRefComPrefType(refComPrefTypeCode);
        Long nbResultRefComPref = refComPrefTypeService.countRefComPrefByType(refComPrefType.get());
        if (nbResultRefComPref == 0) {
            try {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefTypeService.removeRefComPrefType(refComPrefTypeCode, username);
            } catch (DataAccessException e) {
                String msg = "Can't delete refComPrefType with code " + refComPrefTypeCode + " into DB";
                LOGGER.error(msg, e);
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            String msg = "Can't delete refComPrefType with code " + refComPrefTypeCode + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
