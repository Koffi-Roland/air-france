package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefPreferenceTypeDTO;
import com.afklm.cati.common.entity.RefPreferenceType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPreferenceTypeService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
 * RefPreferenceTypeController.java
 *
 * @author m430152
 */
@RestController
@RequestMapping("/preferenceTypes")
public class RefPreferenceTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefPreferenceTypeController.class);

    @PersistenceContext(name = "myPersistenceUnit")
    EntityManager em;

    @Autowired
    private RefPreferenceTypeService refPreferenceTypeService;

    private static final String DB_ERROR = "db error";
    private static final String FROM_DB = "from DB";
    private static final String DELETE_REF_PREF_TYPE = "Can't delete refPreferenceType with code ";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefPreferenceType list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefPreferenceType> collectionList(HttpServletRequest httpServletRequest)
            throws JrafDaoException, CatiCommonsException {

        List<RefPreferenceType> refPreferenceTypes = null;

        try {
            refPreferenceTypes = refPreferenceTypeService.getAllRefPreferenceType();
        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refPreferenceType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refPreferenceTypes;
    }

    /**
     * @param refPreferenceType RefPreferenceType to add
     * @param request           the request http object
     * @param response          the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefPreferenceTypeDTO refPreferenceTypeDTO, HttpServletRequest request,
                              HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            refPreferenceTypeService.addRefPreferenceType(refPreferenceTypeDTO);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refPreferenceType into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refPreferenceType into DB",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // TODO: Remove, Keep ?
        /*
         * response.setHeader( "Location", request.getRequestURL()
         * .append((request.getRequestURL().toString() .endsWith("/") ? "" : "/"))
         * .append(refComPrefType.getCodeType()).toString());
         */
    }

    /**
     * Retrieve refPreferenceType by code
     *
     * @param refPreferenceTypeCode the refPreferenceType code
     * @param httpServletRequest    the Http servlet request
     * @return the refPreferenceType corresponding to the specified
     * refPreferenceType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefPreferenceType refPreferenceTypeGet(@PathVariable("code") String refPreferenceTypeCode,
                                                  HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefPreferenceType> requestBo = null;
        try {
            requestBo = refPreferenceTypeService.getRefPreferenceType(refPreferenceTypeCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refPreferenceType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBo == null || !requestBo.isPresent()) {
            String msg = "refPreferenceType with code " + refPreferenceTypeCode + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refPreferenceType
     *
     * @param p                     the RefPreferenceType
     * @param refPreferenceTypeCode the refPreferenceType code
     * @return RefPreferenceType
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{code}", consumes = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefPreferenceType refPreferenceTypeUpdate(@Valid @RequestBody RefPreferenceTypeDTO p,
                                                     @PathVariable("code") String refPreferenceTypeCode) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<RefPreferenceType> refPreferenceTypeToUpdate = refPreferenceTypeService.getRefPreferenceType(refPreferenceTypeCode);
            if (refPreferenceTypeToUpdate.isPresent()) {
                // TODO: Beautify...
                RefPreferenceType objectToUpdate = refPreferenceTypeToUpdate.get();
                objectToUpdate.setCode(p.getCode());
                objectToUpdate.setLibelleFR(p.getLibelleFR());
                objectToUpdate.setLibelleEN(p.getLibelleEN());

                refPreferenceTypeService.updateRefPreferenceType(objectToUpdate);
            } else {
                String msg = "RefPreferenceType with code " + refPreferenceTypeCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refPreferenceType with code " + refPreferenceTypeCode + FROM_DB;
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refPreferenceTypeService.getRefPreferenceType(p.getCode()).get();
    }

    /**
     * Delete a specific RefPreferenceType
     *
     * @param refPreferenceTypeCode the refPreferenceType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{code}")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refPreferenceTypeDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                        @PathVariable("code") String refPreferenceTypeCode) throws JrafDaoException, CatiCommonsException {

        Optional<RefPreferenceType> refPreferenceType = refPreferenceTypeService.getRefPreferenceType(refPreferenceTypeCode);

        if (refPreferenceType.isPresent()) {
            Long countRefPrefKeyType = refPreferenceTypeService.countRefPreferenceKeyTypeByRefPreferenceType(refPreferenceType.get());

            if (countRefPrefKeyType == 0) {
                try {
                    // TODO: Add username if needed ?
                    refPreferenceTypeService.removeRefPreferenceType(refPreferenceTypeCode);
                } catch (DataAccessException | CatiCommonsException e) {
                    String msg = DELETE_REF_PREF_TYPE + refPreferenceTypeCode + FROM_DB;
                    LOGGER.error(msg, e);
                    treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                String msg = DELETE_REF_PREF_TYPE + refPreferenceTypeCode
                        + " from DB because it is used";
                LOGGER.info(msg);
                treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
            }
        } else {
            String msg = DELETE_REF_PREF_TYPE + refPreferenceTypeCode + FROM_DB;
            LOGGER.error(msg);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }
}
