package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefPreferenceDataKeyDTO;
import com.afklm.cati.common.entity.RefPreferenceDataKey;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPreferenceDataKeyService;
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
 * RefPreferenceDataKeyController.java
 *
 * @author m430152
 */
@RestController
@RequestMapping("/preferenceDataKeys")
public class RefPreferenceDataKeyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefPreferenceDataKeyController.class);

    @PersistenceContext(name = "myPersistenceUnit")
    EntityManager em;

    @Autowired
    private RefPreferenceDataKeyService refPreferenceDataKeyService;

    private static final String DB_ERROR = "db error";
    private static final String FROM_DB = "from DB";
    private static final String DELETE_REF_PREF_DATA_KEY = "Can't delete refPreferenceDataKey with code ";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefPreferenceDataKey list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefPreferenceDataKey> collectionList(HttpServletRequest httpServletRequest)
            throws JrafDaoException, CatiCommonsException {

        List<RefPreferenceDataKey> refPreferenceDataKeys = null;

        try {
            refPreferenceDataKeys = refPreferenceDataKeyService.getAllRefPreferenceDataKey();
        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refPreferenceDataKey from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refPreferenceDataKeys;
    }

    /**
     * @param refPreferenceDataKey RefPreferenceDataKey to add
     * @param request              the request http object
     * @param response             the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefPreferenceDataKeyDTO refPreferenceDataKeyDTO, HttpServletRequest request,
                              HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            refPreferenceDataKeyService.addRefPreferenceDataKey(refPreferenceDataKeyDTO);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refPreferenceDataKey into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refPreferenceDataKey into DB",
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
     * Retrieve refPreferenceDataKey by code
     *
     * @param refPreferenceDataKeyCode the refPreferenceDataKey code
     * @param httpServletRequest       the Http servlet request
     * @return the refPreferenceDataKey corresponding to the specified
     * refPreferenceDataKey code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefPreferenceDataKey refPreferenceDataKeyGet(@PathVariable("code") String refPreferenceDataKeyCode,
                                                        HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefPreferenceDataKey> requestBo = null;

        try {
            requestBo = refPreferenceDataKeyService.getRefPreferenceDataKey(refPreferenceDataKeyCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refPreferenceDataKey from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBo == null || !requestBo.isPresent()) {
            String msg = "refPreferenceDataKey with code " + refPreferenceDataKeyCode + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refPreferenceDataKey
     *
     * @param p                        the RefPreferenceDataKey
     * @param refPreferenceDataKeyCode the refPreferenceDataKey code
     * @return RefPreferenceDataKey
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{code}", consumes = "application/json; charset=utf-8")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefPreferenceDataKey refPreferenceDataKeyUpdate(@Valid @RequestBody RefPreferenceDataKeyDTO p,
                                                           @PathVariable("code") String refPreferenceDataKeyCode) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<RefPreferenceDataKey> refPreferenceDataKeyToUpdate = refPreferenceDataKeyService.getRefPreferenceDataKey(refPreferenceDataKeyCode);

            if (refPreferenceDataKeyToUpdate.isPresent()) {
                RefPreferenceDataKey objectToUpdate = refPreferenceDataKeyToUpdate.get();
                // TODO: Beautify...
                objectToUpdate.setCode(p.getCode());
                objectToUpdate.setLibelleFr(p.getLibelleFr());
                objectToUpdate.setLibelleEn(p.getLibelleEn());

                refPreferenceDataKeyService.updateRefPreferenceDataKey(objectToUpdate);
            } else {
                String msg = "RefPreferenceDataKey with code " + refPreferenceDataKeyCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refPreferenceDataKey with code " + refPreferenceDataKeyCode + FROM_DB;
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refPreferenceDataKeyService.getRefPreferenceDataKey(p.getCode()).get();
    }

    /**
     * Delete a specific RefPreferenceDataKey
     *
     * @param refPreferenceDataKeyCode the refPreferenceDataKey code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{code}")
    // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refPreferenceDataKeyDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                           @PathVariable("code") String refPreferenceDataKeyCode) throws JrafDaoException, CatiCommonsException {

        Optional<RefPreferenceDataKey> refPreferenceDataKey = refPreferenceDataKeyService.getRefPreferenceDataKey(refPreferenceDataKeyCode);

        if (refPreferenceDataKey.isPresent()) {
            Long countRefPreferenceDataKey = refPreferenceDataKeyService.countRefPreferenceKeyTypeByRefPreferenceDataKey(refPreferenceDataKey.get());

            if (countRefPreferenceDataKey == 0) {
                try {
                    // TODO: Add username if needed ?
                    refPreferenceDataKeyService.removeRefPreferenceDataKey(refPreferenceDataKeyCode);
                } catch (DataAccessException | CatiCommonsException e) {
                    String msg = DELETE_REF_PREF_DATA_KEY + refPreferenceDataKeyCode + " from DB";
                    LOGGER.error(msg, e);
                    treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                String msg = DELETE_REF_PREF_DATA_KEY + refPreferenceDataKeyCode
                        + " from DB because it is used";
                LOGGER.info(msg);
                treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
            }
        } else {
            String msg = DELETE_REF_PREF_DATA_KEY + refPreferenceDataKeyCode + " from DB";
            LOGGER.error(msg);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }
}
