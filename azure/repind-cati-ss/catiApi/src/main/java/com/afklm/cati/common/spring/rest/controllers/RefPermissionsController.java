package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.entity.RefPermissions;
import com.afklm.cati.common.entity.RefPermissionsQuestion;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefPermissionsQuestionService;
import com.afklm.cati.common.service.RefPermissionsService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefPermissionsSaveOrUpdateResource;
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
import java.util.List;
import java.util.Optional;

/**
 * @author AF-KLM
 */
@RestController
@RequestMapping("/permissions")
@CrossOrigin
public class RefPermissionsController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefPermissionsController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefPermissionsService refPermissionsService;

    @Autowired
    private RefPermissionsQuestionService refPermissionsQuestionService;

    @Autowired
    private Mapper dozerBeanMapper;


    /**
     * @param refPermissions RefPermissions to add
     * @param request        the request http object
     * @param response       the response http object
     * @throws JrafDaoException jraf dao exception
     * @throws CatiCommonsException common exception
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefPermissionsSaveOrUpdateResource refPermissions,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {

            if (refPermissions != null && refPermissions.getRefPermissionsQuestionId() != null) {

                RefPermissions refPermission = dozerBeanMapper.map(refPermissions, RefPermissions.class);

                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refPermissionsService.addRefPermissions(refPermission, refPermissions.getRefPermissionsQuestionId(), refPermissions.getListComPrefDgt(), username);

            }
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refPermissions into DB", e);
            treatDefaultCatiException("db error", "Can't create refPermissions into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                // REPIND-1398 : Test SONAR NPE
                .append(refPermissions != null && refPermissions.getRefPermissionsQuestionId() != null ? refPermissions.getRefPermissionsQuestionId() : "").toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPrefDgt by code
     *
     * @param refPermissionsQuestionId the refPermissionsQuestion id
     * @param httpServletRequest       the Http servlet request
     * @return list of RefComPrefDgt corresponding to the specified refPermissionsQuestion id
     * @throws JrafDaoException jraf dao exception
     * @throws CatiCommonsException common exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<Integer> refPermissionsGetRefComPrefDgt(@PathVariable("id") Integer refPermissionsQuestionId,
                                                        HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<Integer> requestBo = null;
        try {

            Optional<RefPermissionsQuestion> refPermissionsQuestion = refPermissionsQuestionService.getRefPermissionsQuestion(refPermissionsQuestionId);

            if (refPermissionsQuestion.isPresent()) {
                requestBo = refPermissionsService.getRefComPrefDgtByRefPermissionsQuestion(refPermissionsQuestion.get());
            }

        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefDgt from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException("db error", msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBo == null) {
            String msg = "RefComPrefDgt for this permissionsQuestion " + refPermissionsQuestionId
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo;
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
