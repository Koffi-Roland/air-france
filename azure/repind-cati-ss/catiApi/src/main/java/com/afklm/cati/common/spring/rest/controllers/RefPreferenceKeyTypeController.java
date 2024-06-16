package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.criteria.RefPreferenceKeyTypeCriteria;
import com.afklm.cati.common.entity.RefPreferenceKeyType;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPreferenceKeyTypeService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefPreferenceKeyTypeResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.cati.common.exception.jraf.NotFoundException;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * RefPreferenceKeyTypeController.java
 *
 * @author m430152
 */
@RestController
@RequestMapping("/preferenceKeyTypes")
public class RefPreferenceKeyTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefPreferenceKeyTypeController.class);

    @PersistenceContext(name = "myPersistenceUnit")
    EntityManager em;

    @Autowired
    private RefPreferenceKeyTypeService refPreferenceKeyTypeService;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefPreferenceKeyType list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefPreferenceKeyType> collectionList(HttpServletRequest httpServletRequest)
            throws JrafDaoException, CatiCommonsException {

        List<RefPreferenceKeyType> refPreferenceKeyTypes = null;

        try {
            refPreferenceKeyTypes = refPreferenceKeyTypeService.getAllRefPreferenceKeyType();
        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refPreferenceKeyType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refPreferenceKeyTypes;
    }

    /**
     * @param body     RefPreferenceKeyType to add
     * @param request  the request http object
     * @param response the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public ResponseEntity<?> collectionAdd(@Valid @RequestBody RefPreferenceKeyTypeResource body,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            RefPreferenceKeyTypeCriteria criteria = new RefPreferenceKeyTypeCriteria(
                    body.getKey(),
                    body.getMaxLength(),
                    body.getMinLength(),
                    body.getDataType(),
                    body.getCondition(),
                    body.getListPreferenceType());

            refPreferenceKeyTypeService.addRefPreferenceKeyType(criteria);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            String msg = "POST requestbody is not valid";
            LOGGER.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String msg = "cannot create RefPreferenceKeyType";
            LOGGER.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // TODO: Remove, Keep ?
        /*
         * response.setHeader( "Location", request.getRequestURL()
         * .append((request.getRequestURL().toString() .endsWith("/") ? "" : "/"))
         * .append(refComPrefType.getCodeType()).toString());
         */
    }

    /**
     * Retrieve refPreferenceKeyType by refId
     *
     * @param refPreferenceKeyTypeRefId the refPreferenceKeyType refId
     * @param httpServletRequest        the Http servlet request
     * @return the refPreferenceKeyType corresponding to the specified
     * refPreferenceKeyType refId
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{refId}")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefPreferenceKeyType refPreferenceKeyTypeGet(@PathVariable("refId") Integer refPreferenceKeyTypeRefId,
                                                        HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefPreferenceKeyType> requestBo = null;
        try {
            requestBo = refPreferenceKeyTypeService.getRefPreferenceKeyType(refPreferenceKeyTypeRefId);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refPreferenceKeyType from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBo == null || !requestBo.isPresent()) {
            String msg = "refPreferenceType with refId " + refPreferenceKeyTypeRefId + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refPreferenceKeyType
     *
     * @param p                     the refPreferenceKeyType
     * @param refPreferenceTypeCode the refPreferenceKeyType refId
     * @return refPreferenceKeyType
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{refId}", consumes = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public ResponseEntity<?> refPreferenceKeyTypeUpdate(@Valid @RequestBody RefPreferenceKeyTypeResource body,
                                                        @PathVariable("refId") Integer refPreferenceKeyTypeRefId) throws CatiCommonsException {

        String errorMsg = "Can't update refPreferenceKeyType with refId " + refPreferenceKeyTypeRefId + " from DB";

        try {
            RefPreferenceKeyTypeCriteria criteria = new RefPreferenceKeyTypeCriteria(
                    refPreferenceKeyTypeRefId,
                    body.getKey(),
                    body.getMaxLength(),
                    body.getMinLength(),
                    body.getDataType(),
                    body.getCondition(),
                    body.getListPreferenceType());

            refPreferenceKeyTypeService.updateRefPreferenceKeyType(criteria);

        } catch (IllegalArgumentException e) {
            LOGGER.error(errorMsg, e);
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            LOGGER.error(errorMsg, e);
            return new ResponseEntity<>(errorMsg, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error(errorMsg, e);
            treatDefaultCatiException(DB_ERROR, errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete a specific RefPreferenceKeyType
     *
     * @param refPreferenceKeyTypeRefId the refPreferenceKeyType refId
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{refId}")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refPreferenceTypeDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                        @PathVariable("refId") Integer refPreferenceKeyTypeRefId) throws JrafDaoException, CatiCommonsException {

        try {
            // TODO: Add username if needed ?
            refPreferenceKeyTypeService.removeRefPreferenceKeyType(refPreferenceKeyTypeRefId);
        } catch (DataAccessException | CatiCommonsException e) {
            String msg = "Can't delete refPreferenceKeyType with refId " + refPreferenceKeyTypeRefId + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
