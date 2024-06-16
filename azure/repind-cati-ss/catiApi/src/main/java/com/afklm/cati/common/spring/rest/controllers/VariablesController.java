package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.VariablesDTO;
import com.afklm.cati.common.entity.Variables;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.VariablesService;
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
@RequestMapping("/variables")
@CrossOrigin
public class VariablesController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(VariablesController.class);

    private static final String DB_ERROR = "db error";


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private VariablesService variablesService;

    /**
     * @param httpServletRequest the Http servlet request
     * @return Variables list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    public List<Variables> variablesList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<Variables> variables = null;

        try {

            variables = variablesService.getAllVariablesAlterable();

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked variables from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return variables;
    }

    /**
     * @param VariablesDTO Variables to add
     * @param request   the request http object
     * @param response  the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void variablesCreate(@Valid @RequestBody VariablesDTO variablesDTO,
                                HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            variablesService.addVariables(variablesDTO);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create variables into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create variables into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(variablesDTO.getEnvKey()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve Variables by id
     *
     * @param variables          the Variables identifier
     * @param httpServletRequest the Http servlet request
     * @return the variables corresponding to the specified variables identifier
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{key}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    public Variables variablesGet(@PathVariable("key") String key,
                                  HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<Variables> variables = Optional.empty();

        try {

            variables = variablesService.getVariables(key);

        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked varaibles from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!variables.isPresent()) {
            String msg = "Variables with key " + key
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return variables.get();
    }

    /**
     * Put a variables
     *
     * @param p            the Variables
     * @param VariablesDTO the variables identifier
     * @return VariablesResource
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{key}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    public Variables variablesUpdate(@Valid @RequestBody VariablesDTO p,
                                     @PathVariable("key") String key) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<Variables> variablesToUpdate = variablesService.getVariables(key);

            if (variablesToUpdate.isPresent()) {
                variablesToUpdate.get().setEnvValue(p.getEnvValue());

                variablesService.updateVariables(variablesToUpdate.get());
            } else {
                String msg = "Variables with key " + key + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update variables with key " + key + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return variablesService.getVariables(p.getEnvKey()).get();
    }

    /**
     * Delete a specific variables
     *
     * @param key the variables identifier
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{key}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void variablesDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("key") String key) throws JrafDaoException, CatiCommonsException {

        try {

            String username = "";

            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }

            variablesService.removeVariables(key, username);
        } catch (DataAccessException e) {
            String msg = "Can't delete variables with key " + key + " into DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }
}
