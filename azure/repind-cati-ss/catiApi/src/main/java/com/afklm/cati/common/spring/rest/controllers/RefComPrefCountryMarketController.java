package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefComPrefCountryMarketDTO;
import com.afklm.cati.common.entity.RefComPrefCountryMarket;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.service.RefComPrefCountryMarketService;
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
@RequestMapping("/countrymarkets")
@CrossOrigin
public class RefComPrefCountryMarketController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefComPrefCountryMarketController.class);


    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private RefComPrefCountryMarketService refComPrefCountryMarketService;

    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefComPrefCountryMarket list matching to the parameters
     * @throws JrafDaoException jraf dao exception
     * @throws CatiCommonsException common exception
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public List<RefComPrefCountryMarket> collectionList(HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        List<RefComPrefCountryMarket> refComPrefs = null;

        try {
            refComPrefs = refComPrefCountryMarketService.getAllRefComPrefCountryMarket();

        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked refComPrefCountryMarket from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return refComPrefs;
    }

    /**
     * @param refComPrefCountryMarketDTO RefComPrefCountryMarket to add
     * @param request                    the request http object
     * @param response                   the response http object
     * @throws JrafDaoException jraf dao exception
     * @throws CatiCommonsException common exception
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefComPrefCountryMarketDTO refComPrefCountryMarketDTO,
                              @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                              HttpServletRequest request, HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refComPrefCountryMarketService.addRefComPrefCountryMarket(refComPrefCountryMarketDTO, username);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refComPrefCountryMarket into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refComPref into DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.add("Location", HeadersUtil.formatRequestUrl(request)
                .append(refComPrefCountryMarketDTO.getCodePays()).toString());
        response.setHeader(headers.entries().get(0).getKey(), headers.entries().get(0).getValue());
    }


    /**
     * Retrieve refComPrefCountryMarket by code
     *
     * @param refComPrefCountryMarketCode the refComPrefCountryMarket code
     * @param httpServletRequest          the Http servlet request
     * @return the refComPrefCountryMarket corresponding to the specified refComPrefCountryMarket code
     * @throws JrafDaoException jraf dao exception
     * @throws CatiCommonsException common exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefCountryMarket refComPrefCountryMarketGet(@PathVariable("code") String refComPrefCountryMarketCode,
                                                              HttpServletRequest httpServletRequest) throws JrafDaoException, CatiCommonsException {

        Optional<RefComPrefCountryMarket> requestBo = Optional.empty();
        RefComPrefCountryMarket refComPrefCountryMarket = null;
        try {
            requestBo = refComPrefCountryMarketService.getRefComPrefCountryMarket(refComPrefCountryMarketCode);
        } catch (DataAccessException e) {

            String msg = "Can't retrieve asked refComPrefCountryMarket from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(requestBo.isPresent())
        {
            refComPrefCountryMarket =  requestBo.get();
        }
        else
        {
            String msg = "RefComPrefCountryMarket with code " + refComPrefCountryMarketCode
                    + " not found";
            treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
        }

        return refComPrefCountryMarket;
    }

    /**
     * Put a refComPrefCountryMarket
     *
     * @param p                           the RefComPrefCountryMarket
     * @param refComPrefCountryMarketCode the refComPrefCountryMarket code
     * @return RefComPrefCountryMarket
     * @throws CatiCommonsException common exception
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{code}", consumes = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    public RefComPrefCountryMarket refComPrefCountryMarketUpdate(@Valid @RequestBody RefComPrefCountryMarketDTO p,
                                                                 @AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                                                 @PathVariable("code") String refComPrefCountryMarketCode) throws CatiCommonsException, JrafDaoException {

        try {

            Optional<RefComPrefCountryMarket> refComPrefCountryMarketToUpdate = refComPrefCountryMarketService.getRefComPrefCountryMarket(refComPrefCountryMarketCode);
            if (refComPrefCountryMarketToUpdate.isPresent()) {
                String username = "";
                if (authenticatedUserDTO != null) {
                    username = authenticatedUserDTO.getUsername();
                }
                refComPrefCountryMarketToUpdate.get().setCodePays(p.getCodePays());
                refComPrefCountryMarketToUpdate.get().setMarket(p.getMarket());
                refComPrefCountryMarketToUpdate.get().setDateModification(p.getDateModification());
                refComPrefCountryMarketToUpdate.get().setSignatureModification(p.getSignatureModification());
                refComPrefCountryMarketToUpdate.get().setSiteModification(p.getSiteModification());

                refComPrefCountryMarketService.updateRefComPrefCountryMarket(refComPrefCountryMarketToUpdate.get(), username);
            } else {
                String msg = "RefComPrefCountryMarket with code " + refComPrefCountryMarketCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refComPrefCountryMarket with code " + refComPrefCountryMarketCode + " from DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refComPrefCountryMarketService.getRefComPrefCountryMarket(p.getCodePays()).get();
    }

    /**
     * Delete a specific refComPrefCountryMarket
     *
     * @param refComPrefCountryMarketCode the refComPrefCountryMarket code
     * @throws JrafDaoException jraf dao exception
     * @throws CatiCommonsException common exception
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{code}")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADMIN_COMMPREF')")
    @ResponseStatus(HttpStatus.OK)
    public void refComPrefCountryMarketDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO, @PathVariable("code") String refComPrefCountryMarketCode) throws JrafDaoException, CatiCommonsException {

        try {
            String username = "";
            if (authenticatedUserDTO != null) {
                username = authenticatedUserDTO.getUsername();
            }
            refComPrefCountryMarketService.removeRefComPrefCountryMarket(refComPrefCountryMarketCode, username);
        } catch (DataAccessException e) {
            String msg = "Can't delete refComPrefCountryMarket with code " + refComPrefCountryMarketCode + " into DB";
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }


}
