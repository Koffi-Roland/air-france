package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefGroupInfoService;
import com.afklm.cati.common.service.RefGroupProductService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefComPrefGroupInfoResource;
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
@RequestMapping("/groupInfos")
@CrossOrigin
public class RefComPrefGroupInfoController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefGroupController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefGroupInfoService refComPrefGroupInfoService;

    @Autowired
    private RefGroupProductService refGroupProductService;

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
    public List<RefComPrefGroupInfoResource> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefGroupInfoResource> refComPrefGroupInfosResources = null;

        try {
            List<RefComPrefGroupInfo> refComPrefGroupInfos = refComPrefGroupInfoService.getAllRefComPrefGroupInfo();

            if (refComPrefGroupInfos != null) {
                refComPrefGroupInfosResources = new ArrayList<RefComPrefGroupInfoResource>();
                for (RefComPrefGroupInfo refComPrefGroupInfo : refComPrefGroupInfos) {
                    RefComPrefGroupInfoResource refComPrefGroupInfosResource = dozerBeanMapper.map(refComPrefGroupInfo, RefComPrefGroupInfoResource.class);

                    Long nbProduct = refGroupProductService.countRefProductComPrefGroupByGroupInfoId(refComPrefGroupInfo.getId());
                    refComPrefGroupInfosResource.setNbProduct(nbProduct);

                    Long nbCompref = refComPrefGroupInfoService.countRefComPrefGroupByRefComPrefGroupInfo(refComPrefGroupInfo.getId());
                    refComPrefGroupInfosResource.setNbCompref(nbCompref);

                    refComPrefGroupInfosResources.add(refComPrefGroupInfosResource);
                }
            }

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefGroup from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refComPrefGroupInfosResources;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefGroupInfo RefComPrefGroupInfoGet(@PathVariable("code") int refComPrefGroupInfoCode,
                                                      HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefGroupInfo> requestBo = Optional.empty();
        try {
            requestBo = refComPrefGroupInfoService.getRefComPrefGroupInfoCode(refComPrefGroupInfoCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefGroupInfo from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!requestBo.isPresent()) {
            String msg = "RefComPrefGroupInfo with code " + refComPrefGroupInfoCode
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return requestBo.get();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefComPrefGroupInfoResource refComPrefGroupInfoResource,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {

            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
            refComPrefGroupInfo.setCode(refComPrefGroupInfoResource.getCode());
            refComPrefGroupInfo.setLibelleEN(refComPrefGroupInfoResource.getLibelleEN());
            refComPrefGroupInfo.setLibelleFR(refComPrefGroupInfoResource.getLibelleFR());
            refComPrefGroupInfo.setMandatoryOption(refComPrefGroupInfoResource.getMandatoryOption());
            refComPrefGroupInfo.setDefaultOption(refComPrefGroupInfoResource.getDefaultOption());

            refComPrefGroupInfoService.addRefComPrefGroupInfo(refComPrefGroupInfo, username);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPrefGroupInfo into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPrefGroupInfo into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPrefGroupInfoResource.getgroupInfoId()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefGroupInfoResource refComPrefGroupInfoUpdate(@Valid @RequestBody RefComPrefGroupInfoResource p,
                                                                 @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                                                 @PathVariable("id") Integer refComPrefGroupInfoId) throws CatiCommonsException {

        try {

            Optional<RefComPrefGroupInfo> refComPrefGroupInfoUpdate = refComPrefGroupInfoService.getRefComPrefGroupInfoCode(refComPrefGroupInfoId);
            if (refComPrefGroupInfoUpdate.isPresent()) {

                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }

                refComPrefGroupInfoUpdate.get().setCode(p.getCode());
                refComPrefGroupInfoUpdate.get().setDefaultOption(p.getDefaultOption());
                refComPrefGroupInfoUpdate.get().setLibelleEN(p.getLibelleEN());
                refComPrefGroupInfoUpdate.get().setLibelleFR(p.getLibelleFR());
                refComPrefGroupInfoUpdate.get().setMandatoryOption(p.getMandatoryOption());

                refComPrefGroupInfoService.updateRefComPref(refComPrefGroupInfoUpdate.get(), username);
            } else {
                String msg = "RefComPrefGroupInfo with id " + refComPrefGroupInfoId + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update RefComPrefGroupInfo with id " + refComPrefGroupInfoId + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return p;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("id") Integer refComPrefGroupInfoId) throws JrafDaoException, CatiCommonsException {


        Long nbResultRefComPref = refComPrefGroupInfoService.countRefComPrefGroupByRefComPrefGroupInfo(refComPrefGroupInfoId);
        Long nbAssociations = refGroupProductService.countRefProductComPrefGroupByGroupInfoId(refComPrefGroupInfoId);

        if (nbResultRefComPref != 0) {
            String msg = "Can't delete refComPrefGroupIndo with id " + refComPrefGroupInfoId + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("usedComPref", msg, HttpStatus.LOCKED);
        } else if (nbAssociations != 0) {
            String msg = "Can't delete refComPrefGroupIndo with id " + refComPrefGroupInfoId + " into DB because it is used";
            LOGGER.info(msg);
            treatDefaultCatiException("usedProduct", msg, HttpStatus.LOCKED);
        } else {
            try {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefGroupInfoService.removeRefComPrefGroupInfo(refComPrefGroupInfoId, username);
            } catch (DataAccessException e) {
                String msg = "Can't delete refPermissionsQuestion with id " + refComPrefGroupInfoId + " into DB";
                LOGGER.error(msg, e);
                treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
