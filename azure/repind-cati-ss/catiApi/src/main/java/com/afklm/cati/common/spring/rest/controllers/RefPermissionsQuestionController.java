package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefPermissionsQuestionDTO;
import com.afklm.cati.common.entity.RefPermissionsQuestion;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPermissionsQuestionService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefPermissionsQuestionResource;
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
@RequestMapping("/permissionsQuestion")
@CrossOrigin
public class RefPermissionsQuestionController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefPermissionsQuestionController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefPermissionsQuestionService refPermissionsQuestionService;

    @Autowired
    private Mapper dozerBeanMapper;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefPermissionsQuestion list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefPermissionsQuestionResource> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefPermissionsQuestionResource> refPermissionsQuestionResources = null;

        try {
            List<RefPermissionsQuestion> refPermissionsQuestions = refPermissionsQuestionService.getAllRefPermissionsQuestion();

            if (refPermissionsQuestions != null) {
                refPermissionsQuestionResources = new ArrayList<RefPermissionsQuestionResource>();
                for (RefPermissionsQuestion refPermissionsQuestion : refPermissionsQuestions) {
                    RefPermissionsQuestionResource refPermissionsQuestionResource = dozerBeanMapper.map(refPermissionsQuestion, RefPermissionsQuestionResource.class);

                    Long nbCompref = refPermissionsQuestionService.countRefPermissionsByPermissionsQuestion(refPermissionsQuestion);
                    refPermissionsQuestionResource.setNbCompref(nbCompref);

                    refPermissionsQuestionResources.add(refPermissionsQuestionResource);
                }
            }

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refPermissionsQuestion from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refPermissionsQuestionResources;
    }

    /**
     * @param refPermissionsQuestionDTO RefPermissionsQuestion to add
     * @param request                   the request http object
     * @param response                  the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefPermissionsQuestionDTO refPermissionsQuestionDTO,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refPermissionsQuestionService.addRefPermissionsQuestion(refPermissionsQuestionDTO, username);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refPermissionsQuestion into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refPermissionsQuestion into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refPermissionsQuestionDTO.getId()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refPermissionsQuestion by id
     *
     * @param refPermissionsQuestionCode the refPermissionsQuestion id
     * @param httpServletRequest         the Http servlet request
     * @return the refPermissionsQuestion corresponding to the specified refPermissionsQuestion id
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefPermissionsQuestion refPermissionsQuestionGet(@PathVariable("id") Integer refPermissionsQuestionId,
                                                            HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefPermissionsQuestion> requestBo = Optional.empty();
        try {
            requestBo = refPermissionsQuestionService.getRefPermissionsQuestion(refPermissionsQuestionId);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refPermissionsQuestions from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!requestBo.isPresent()) {
            String msg = "RefPermissionsQuestion with id " + refPermissionsQuestionId
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    /**
     * Put a refPermissionsQuestion
     *
     * @param p                          the RefPermissionsQuestion
     * @param refPermissionsQuestionCode the refPermissionsQuestion id
     * @return RefPermissionsQuestion
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefPermissionsQuestion refComPrefUpdate(@Valid @RequestBody RefPermissionsQuestionDTO p,
                                                   @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                                   @PathVariable("id") Integer refPermissionsQuestionId) throws CatiCommonsException, JrafDaoException {

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }

            Optional<RefPermissionsQuestion> refPermissionsQuestionToUpdate = refPermissionsQuestionService.getRefPermissionsQuestion(refPermissionsQuestionId);
            if (refPermissionsQuestionToUpdate.isPresent()) {
                refPermissionsQuestionToUpdate.get().setId(p.getId());
                refPermissionsQuestionToUpdate.get().setName(p.getName());
                refPermissionsQuestionToUpdate.get().setQuestion(p.getQuestion());
                refPermissionsQuestionToUpdate.get().setQuestionEN(p.getQuestionEN());

                refPermissionsQuestionService.updateRefPermissionsQuestion(refPermissionsQuestionToUpdate.get(), username);
            } else {
                String msg = "RefPermissionsQuestion with id " + refPermissionsQuestionId + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refPermissionsQuestion with id " + refPermissionsQuestionId + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refPermissionsQuestionService.getRefPermissionsQuestion(p.getId()).get();
    }

    /**
     * Delete a specific refPermissionsQuestion
     *
     * @param refPermissionsQuestionCode the refPermissionsQuestion id
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("id") Integer refPermissionsQuestionId) throws JrafDaoException, CatiCommonsException {


        Long nbResultRefComPref = refPermissionsQuestionService.countRefPermissionsByPermissionsQuestionId(refPermissionsQuestionId);
        if (nbResultRefComPref == 0) {
            try {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refPermissionsQuestionService.removeRefPermissionsQuestion(refPermissionsQuestionId, username);
            } catch (DataAccessException e) {
                String msg = "Can't delete refPermissionsQuestion with id " + refPermissionsQuestionId + " into DB";
                LOGGER.error(msg, e);
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            String msg = "Can't delete refPermissionsQuestion with id " + refPermissionsQuestionId + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("used", msg, HttpStatus.LOCKED);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
