package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.entity.RefComPrefGroup;
import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefGroupInfoService;
import com.afklm.cati.common.service.RefComPrefGroupService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefComPrefGroupResource;
import com.afklm.cati.common.spring.rest.resources.RefGroupsSaveOrUpdateResource;
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
@RequestMapping("/groups")
@CrossOrigin
public class RefComPrefGroupController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefGroupController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefGroupService refComPrefGroupService;

    @Autowired
    private RefComPrefGroupInfoService refComPrefGroupInfoService;

    @Autowired
    private Mapper dozerBeanMapper;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest; the Http servlet request
     * @return RefComPrefDomain list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefGroupResource> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefGroupResource> refComPrefGroupsResource = null;

        try {
            List<RefComPrefGroup> refComPrefGroups = refComPrefGroupService.getAllRefComPrefGroup();

            if (refComPrefGroups != null) {
                refComPrefGroupsResource = new ArrayList<RefComPrefGroupResource>();
                for (RefComPrefGroup refComPrefGroup : refComPrefGroups) {
                    refComPrefGroupsResource.add(dozerBeanMapper.map(refComPrefGroup, RefComPrefGroupResource.class));
                }
            }

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefGroup from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refComPrefGroupsResource;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<Integer> refComPrefGroupGetRefComPrefDgt(@PathVariable("id") Integer refComPrefGroupInfoId,
                                                         HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<Integer> requestBo = null;
        try {

            Optional<RefComPrefGroupInfo> refComPrefGroupInfo = refComPrefGroupInfoService.getRefComPrefGroupInfo(refComPrefGroupInfoId);

            requestBo = refComPrefGroupService.getRefComPrefDgtByRefComPrefGroupInfo(refComPrefGroupInfo.get());

        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefDgt from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBo == null) {
            String msg = "RefComPrefDgt for this refComPrefGroupInfo " + refComPrefGroupInfoId
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo;
    }

    /**
     * @param refPermissions RefPermissions to add
     * @param request        the request http object
     * @param response       the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefGroupsSaveOrUpdateResource refGroups,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {

            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }

            if (refGroups != null && refGroups.getRefGroupsInfoId() != null) {

                RefComPrefGroup refGroup = dozerBeanMapper.map(refGroups, RefComPrefGroup.class);

                refComPrefGroupService.addRefComPrefGroup(refGroup, refGroups.getRefGroupsInfoId(), refGroups.getListComPrefDgt(), username);

            }
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refGroup into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refGroup into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                // REPIND-1398 : Test SONAR NPE
                .append(refGroups != null && refGroups.getRefGroupsInfoId() != null ? refGroups.getRefGroupsInfoId() : "").toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
