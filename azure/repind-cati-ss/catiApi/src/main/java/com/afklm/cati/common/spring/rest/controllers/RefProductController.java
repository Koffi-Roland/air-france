package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.entity.RefProduct;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefProductService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.spring.rest.resources.RefProductResource;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author AF-KLM
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class RefProductController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefGroupController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefProductService refProductService;

    @Autowired
    private Mapper dozerBeanMapper;

    /**
     * @param httpServletRequest; the Http servlet request
     * @return RefComPrefDomain list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefProductResource> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<Integer> refProducts = null;
        List<RefProductResource> refProductsResource = null;
        try {
            refProducts = refProductService.getAllCustomerRefProduct();
            if (refProducts != null) {
                refProductsResource = new ArrayList<RefProductResource>();
                for (Integer i : refProducts) {
                    Optional<RefProduct> refProduct = refProductService.getRefProduct(i);
                    refProductsResource.add(dozerBeanMapper.map(refProduct.get(), RefProductResource.class));
                }
            }

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refProduct from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException("db error", msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refProductsResource;
    }
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
	public List<Integer> refProductGetComPrefGroup(@PathVariable("id") Integer refProductComPrefGroupId,
			HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

		List<Integer> requestBo = null;
		try {
			RefProductComPrefGroup refProductComPrefGroup = refProductComPrefGroupService.getRefProductComPrefGroup(refProductComPrefGroupId);
			
			requestBo = refProductService.getRefComPrefGroupByRefProductComPrefGroup(refProductComPrefGroup);
			
		} catch (DataAccessException e) {
			
			String msg = "Can't retrieve asked refComPrefGroup from DB";
			LOGGER.error(msg, e);
			treatDefaultCatiException("db error", msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (requestBo == null) {
			String msg = "refComPrefGroup for this refProductComPrefGroup " + refProductComPrefGroupId
					+ " not found";
			treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
		}
		
		return requestBo;
	}*/

    /*@RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefProductsSaveOrUpdateResource refProducts,
            HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {

            if(refProducts != null && refProducts.getRefGroupsInfoId() != null) {

                RefProduct refProduct = dozerBeanMapper.map(refProducts, RefProduct.class);

                refProductService.addRefProduct(refProduct, refProducts.getRefGroupsInfoId(), refProducts.getListComPrefDgt());
            }
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refGroup into DB", e);
            treatDefaultCatiException("db error", "Can't create refGroup into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.setHeader(
                "Location",
                request.getRequestURL()
                        .append((request.getRequestURL().toString()
                                .endsWith("/") ? "" : "/"))
                .append(refGroups.getRefGroupsInfoId()).toString());

    }
*/
    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
