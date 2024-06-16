package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefPcsFactorDTO;
import com.afklm.cati.common.entity.RefPcsFactor;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsFactor;
import com.afklm.cati.common.service.RefPcsFactorService;
import com.afklm.cati.common.spring.rest.exceptions.DefaultCatiException;
import com.afklm.cati.common.spring.rest.exceptions.ErrorResource;
import com.afklm.cati.common.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/PcsFactor")
@CrossOrigin
public class RefPcsFactorController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefPcsFactorController.class);

    @Autowired
    private RefPcsFactorService refPcsFactorService;

    private static final String FROM_DB = "from DB";
    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefPcsFactor list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @GetMapping(value = "/", produces = "application/json; charset=utf-8")
    public ResponseEntity<?> refPcsFactorList(HttpServletRequest httpServletRequest) throws JrafDaoException,
            CatiCommonsException {
        try {
            List<ModelRefPcsFactor> refPcsFactorList = refPcsFactorService.getAllPcsFactor();
            return new ResponseEntity<>(refPcsFactorList, HttpStatus.OK);
        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked PCS Factor from DB";
            LOGGER.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param refPcsFactorDTO RefPcsFactorType to add
     * @param request         the request http object
     * @param response        the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @PostMapping(value = "/", consumes = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefPcsFactorDTO refPcsFactorDTO, HttpServletRequest request,
                              HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            refPcsFactorService.addRefPcsFactor(refPcsFactorDTO);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refPcsFactorType into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refPcsFactorType into DB",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Put a refPcsFactorType
     *
     * @param refPcsFactorDTO     the RefPcsFactorType
     * @param refPcsFactorDTOCode the refPcsFactorType code
     * @return refPcsFactorType
     * @throws CatiCommonsException
     */
    @PutMapping(value = "/{code}", consumes = "application/json; charset=utf-8")
    public RefPcsFactor refPcsFactorUpdate(@Valid @RequestBody RefPcsFactorDTO refPcsFactorDTO,
                                           @PathVariable("code") String refPcsFactorDTOCode) throws CatiCommonsException, JrafDaoException {

        try {
            Optional<RefPcsFactor> refPcsFactorDTOToUpdate = refPcsFactorService.getRefPcsFactor(refPcsFactorDTOCode);
            if (refPcsFactorDTOToUpdate.isPresent()) {
                // TODO: ...
                RefPcsFactor objectToUpdate = refPcsFactorDTOToUpdate.get();
                objectToUpdate.setCode(refPcsFactorDTO.getCode());
                objectToUpdate.setLibelle(refPcsFactorDTO.getLibelle());
                objectToUpdate.setFactor(refPcsFactorDTO.getFactor());
                objectToUpdate.setMaxPoints(refPcsFactorDTO.getMaxPoints());

                refPcsFactorService.updateRefPcsFactor(objectToUpdate);
            } else {
                String msg = "RefPcsFactor with code " + refPcsFactorDTOCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refePcsFactor with code " + refPcsFactorDTOCode + FROM_DB;
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refPcsFactorService.getRefPcsFactor(refPcsFactorDTO.getCode()).get();
    }

    /**
     * Delete a specific RefPcsFactorType
     *
     * @param refPcsFactorCode the refPcsFactorType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @DeleteMapping(value = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void refPcsFactorCodeDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                       @PathVariable("code") String refPcsFactorCode) throws JrafDaoException, CatiCommonsException {
        try {
            Optional<RefPcsFactor> refPcsFactor = refPcsFactorService.getRefPcsFactor(refPcsFactorCode);

            if (refPcsFactor.isPresent()) {
                refPcsFactorService.removeRefPcsFactor(refPcsFactorCode);
            } else {
                String msg = "RefPcsFactor with code " + refPcsFactorCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't delete RefPcsFactor with code " + refPcsFactorCode + FROM_DB;
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }

}
