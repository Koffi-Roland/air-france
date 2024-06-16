package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.dto.RefPcsScoreDTO;
import com.afklm.cati.common.entity.RefPcsScore;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelRefPcsScore;
import com.afklm.cati.common.service.RefPcsScoreService;
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
@RequestMapping("/PcsContractScore")
@CrossOrigin
public class RefPcsContractScoreController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RefPcsContractScoreController.class);

    @Autowired
    private RefPcsScoreService refPcsScoreService;

    private static final String CODE_FACTOR = "C";

    private static final String FROM_DB = "from DB";
    private static final String DB_ERROR = "db error";

    /**
     * @param httpServletRequest the Http servlet request
     * @return RefPcsContractScore list matching to the parameters
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @GetMapping(value = "/", produces = "application/json; charset=utf-8")
    public ResponseEntity<?> refPcsContractScoreList(HttpServletRequest httpServletRequest) throws JrafDaoException,
            CatiCommonsException {
        try {
            List<ModelRefPcsScore> refPcsScoreListe = refPcsScoreService.getPcsScoreByFactorCode(CODE_FACTOR);
            return new ResponseEntity<>(refPcsScoreListe, HttpStatus.OK);
        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked PCS Contract Score List from DB";
            LOGGER.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param refPcsScoreDTO RefPcsContractScore to add
     * @param request        the request http object
     * @param response       the response http object
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @PostMapping(value = "/", consumes = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void collectionAdd(@Valid @RequestBody RefPcsScoreDTO refPcsScoreDTO, HttpServletRequest request,
                              HttpServletResponse response) throws JrafDaoException, CatiCommonsException {

        try {
            refPcsScoreDTO.setCodeFactor(CODE_FACTOR);
            refPcsScoreService.addRefPcsScore(refPcsScoreDTO);
        } catch (DataAccessException e) {
            LOGGER.error("Can't create refPcsScore into DB", e);
            treatDefaultCatiException(DB_ERROR, "Can't create refPcsScore into DB",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Put a refPcsScore
     *
     * @param refPcsScoreDTO     the RefPcsScore
     * @param refPcsScoreDTOCode the RefPcsScore code
     * @return refPcsScore
     * @throws CatiCommonsException
     */
    @PutMapping(value = "/{code}", consumes = "application/json; charset=utf-8")
    public RefPcsScore refPcsScoreUpdate(@Valid @RequestBody RefPcsScoreDTO refPcsScoreDTO,
                                         @PathVariable("code") String refPcsScoreDTOCode) throws CatiCommonsException, JrafDaoException {

        try {
            Optional<RefPcsScore> refPcsScoreDTOToUpdate = refPcsScoreService.getRefPcsScore(refPcsScoreDTOCode);
            if (refPcsScoreDTOToUpdate.isPresent()) {
                // TODO: ...
                RefPcsScore objectToUpdate = refPcsScoreDTOToUpdate.get();
                objectToUpdate.setCode(refPcsScoreDTO.getCode());
                objectToUpdate.setLibelle(refPcsScoreDTO.getLibelle());
                objectToUpdate.setScore(refPcsScoreDTO.getScore());

                refPcsScoreService.updateRefPcsScore(objectToUpdate);
            } else {
                String msg = "RefPcsScore with code " + refPcsScoreDTOCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't update refePcsScore with code " + refPcsScoreDTOCode + FROM_DB;
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return refPcsScoreService.getRefPcsScore(refPcsScoreDTO.getCode()).get();
    }

    /**
     * Delete a specific RefPcsContractScore
     *
     * @param refPcsScoreCode the refPcsScoreType code
     * @throws JrafDaoException
     * @throws CatiCommonsException
     */
    @DeleteMapping(value = "/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void refPcsScoreCodeDelete(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO,
                                      @PathVariable("code") String refPcsScoreCode) throws JrafDaoException, CatiCommonsException {
        try {
            Optional<RefPcsScore> refPcsScore = refPcsScoreService.getRefPcsScore(refPcsScoreCode);

            if (refPcsScore.isPresent()) {
                refPcsScoreService.removeRefPcsScore(refPcsScoreCode);
            } else {
                String msg = "RefPcsScore with code " + refPcsScoreCode + " doesn't exist";
                treatDefaultCatiException("not found", msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Can't delete RefPcsScore with code " + refPcsScoreCode + FROM_DB;
            LOGGER.error(msg, e);
            treatDefaultCatiException(DB_ERROR, msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void treatDefaultCatiException(String label, String msg, HttpStatus status) {
        throw new DefaultCatiException(new ErrorResource(label, msg, status));
    }

}
