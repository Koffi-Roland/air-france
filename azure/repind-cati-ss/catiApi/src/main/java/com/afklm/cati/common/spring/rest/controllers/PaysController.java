package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.criteria.PaysCriteria;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.model.ModelPays;
import com.afklm.cati.common.service.PaysService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pays")
@CrossOrigin
public class PaysController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PaysController.class);

    @PersistenceContext(name = "entityManagerFactorySic")
    EntityManager em;

    @Autowired
    private PaysService paysService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public ResponseEntity<?> paysList(HttpServletRequest httpServletRequest) throws JrafDaoException,
            CatiCommonsException {
        try {
            List<ModelPays> paysList = paysService.getAllPays();
            return new ResponseEntity<>(paysList, HttpStatus.OK);
        } catch (DataAccessException e) {
            String msg = "Can't retrieve asked Pays from DB";
            LOGGER.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

    }

    /**
     * update "NORMALISABLE" of Pays by its "CODEPAYS"
     *
     * @param paysResource to update
     * @param key  code pays
     * @throws CatiCommonsException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{key}", consumes = "application/json; charset=utf-8")
    public ResponseEntity<?> paysUpdate(@Valid @RequestBody ModelPays paysResource, @PathVariable("key") String key) throws CatiCommonsException {
        try {
            PaysCriteria paysCriteria = new PaysCriteria(key, paysResource.getNormalisable());
            paysService.updatePays(paysCriteria);
        } catch (IllegalArgumentException e) {
            String msg = "PUT /pays arguments are not valid";
            LOGGER.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            String msg = "Can't update Pays with codePays " + key + " from DB";
            LOGGER.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
