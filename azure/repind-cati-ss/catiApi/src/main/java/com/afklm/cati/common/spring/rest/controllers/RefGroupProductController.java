package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.entity.RefComPrefGroupInfo;
import com.afklm.cati.common.entity.RefProduct;
import com.afklm.cati.common.entity.RefProductComPrefGroup;
import com.afklm.cati.common.entity.RefProductComPrefGroupId;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefGroupProductService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.LinkGroupProduct;
import com.afklm.cati.common.spring.rest.resources.RefGroupProductResource;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author AF-KLM
 */
@RestController
@RequestMapping("/groupProduct")
@CrossOrigin
public class RefGroupProductController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefGroupController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefGroupProductService refGroupProductService;

    private static final String DB_ERROR = "db error";


    /**
     * @param httpServletRequest; the Http servlet request
     * @return RefComPrefDomain list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefGroupProductResource> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {
        List<RefGroupProductResource> refGroupProductResources = new ArrayList<>();
        List<RefProductComPrefGroup> refProductComPrefGroup = null;
        try {
            refProductComPrefGroup = refGroupProductService.getAllRefGroupProduct();

            for (RefProductComPrefGroup rf : refProductComPrefGroup) {
                RefGroupProductResource res = new RefGroupProductResource();
                res.setCode(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getCode());
                res.setDateCreation(rf.getDateCreation());
                res.setDateModification(rf.getDateModification());
                res.setIdGroup(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getId());
                res.setLibelleEN(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getLibelleEN());
                res.setLibelleFR(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getLibelleFR());
                res.setProductId(rf.getRefProductComPrefGroupId().getRefProduct().getProductId());
                res.setProductName(rf.getRefProductComPrefGroupId().getRefProduct().getProductName());
                res.setProductType(rf.getRefProductComPrefGroupId().getRefProduct().getProductType());
                res.setSignatureCreation(rf.getSignatureCreation());
                res.setSignatureModification(rf.getSignatureModification());
                res.setSiteCreation(rf.getSiteCreation());
                res.setSiteModification(rf.getSiteModification());
                res.setSubProductType(rf.getRefProductComPrefGroupId().getRefProduct().getSubProductType());
                res.setDefaultOption(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getDefaultOption());
                res.setMandatoryOption(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getMandatoryOption());

                refGroupProductResources.add(res);
            }
        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refProduct from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refGroupProductResources;
    }

    /**
     * Delete a specific refComPrefDgt
     *
     * @param refComPrefDgtId the refComPrefDgt identifier
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{productId}/{groupInfoId}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void RefProductComPrefGroupDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("productId") Integer productId, @PathVariable("groupInfoId") Integer groupInfoId) throws JrafDaoException, CatiCommonsException {
        RefProduct RefProduct = new RefProduct();
        RefProduct.setProductId(productId);

        RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
        refComPrefGroupInfo.setId(groupInfoId);

        RefProductComPrefGroupId refProductComPrefGroupId = new RefProductComPrefGroupId(RefProduct, refComPrefGroupInfo);
        RefProductComPrefGroup refProductComPrefGroup = new RefProductComPrefGroup();
        refProductComPrefGroup.setRefProductComPrefGroupId(refProductComPrefGroupId);

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refGroupProductService.removeRefGroupProduct(refProductComPrefGroup, username);
        } catch (DataAccessException e) {
            String msg = "Can't delete RefProductComPrefGroup with ProductId " + refProductComPrefGroup.getRefProductComPrefGroupId().getRefProduct().getProductId() + " and GroupInfoId " + refProductComPrefGroup.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getId() + " into DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefGroupProductResource> refProductGetComPrefGroupByProductId(@PathVariable("id") Integer refProductComPrefGroupId,
                                                                              HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {
        List<RefGroupProductResource> refGroupProductResources = new ArrayList<>();
        List<RefProductComPrefGroup> requestBo = null;
        try {
            requestBo = refGroupProductService.getAllRefGroupProductByProductId(refProductComPrefGroupId);
            for (RefProductComPrefGroup rf : requestBo) {
                RefGroupProductResource res = new RefGroupProductResource();
                res.setCode(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getCode());
                res.setDateCreation(rf.getDateCreation());
                res.setDateModification(rf.getDateModification());
                res.setIdGroup(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getId());
                res.setLibelleEN(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getLibelleEN());
                res.setLibelleFR(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getLibelleFR());
                res.setProductId(rf.getRefProductComPrefGroupId().getRefProduct().getProductId());
                res.setProductName(rf.getRefProductComPrefGroupId().getRefProduct().getProductName());
                res.setProductType(rf.getRefProductComPrefGroupId().getRefProduct().getProductType());
                res.setSignatureCreation(rf.getSignatureCreation());
                res.setSignatureModification(rf.getSignatureModification());
                res.setSiteCreation(rf.getSiteCreation());
                res.setSiteModification(rf.getSiteModification());
                res.setSubProductType(rf.getRefProductComPrefGroupId().getRefProduct().getSubProductType());
                res.setDefaultOption(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getDefaultOption());
                res.setMandatoryOption(rf.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getMandatoryOption());

                refGroupProductResources.add(res);
            }
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefGroup from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (requestBo == null) {
            String msg = "refComPrefGroup for this refProductComPrefGroup " + refProductComPrefGroupId
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return refGroupProductResources;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody LinkGroupProduct linkGroupProduct,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        List<Integer> refProductComPrefGroup = linkGroupProduct.getGroupsId();
        RefProductComPrefGroup ref = null;
        try {

            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }

            for (int i = 0; i < refProductComPrefGroup.size(); i++) {
                ref = new RefProductComPrefGroup();
                // GroupId
                RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
                refComPrefGroupInfo.setId(refProductComPrefGroup.get(i));
                RefProductComPrefGroupId refProductComPrefGroupId = new RefProductComPrefGroupId();
                refProductComPrefGroupId.setRefComPrefGroupInfo(refComPrefGroupInfo);
                // Product
                RefProduct refProduct = new RefProduct();
                refProduct.setProductId(linkGroupProduct.getProductId());
                refProductComPrefGroupId.setRefProduct(refProduct);
                ref.setRefProductComPrefGroupId(refProductComPrefGroupId);

                refGroupProductService.addRefGroupProduct(ref, username);
            }
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refGroup into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refGroup into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                // REPIND-1398 : Test SONAR NPE
                .append(ref != null && ref.getRefProductComPrefGroupId() != null ? ref.getRefProductComPrefGroupId() : "").toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
